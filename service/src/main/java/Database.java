import org.json.simple.JSONObject;
import org.omg.CORBA.WStringSeqHelper;

import java.sql.*;
import java.util.*;

public class Database {

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public Database(String url, String username, String password){
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
        } catch (SQLException se) {
            Loger.log("Error connecting to database. Check the parameters:\n" + se.getMessage(), Loger.log_type.Error);
            System.exit(1);
        }
        try{
            if (0 == stmt.executeUpdate("SHOW DATABASES WHERE `Database` = 'test_db';")){
                // creating base
                if (Loger.question("The database \"test_db\" is missing. Create a new one?")) {
                    createDB();
                    stmt.execute("USE `test_db`;");
                    createTable();
                }
            }
            stmt.execute("USE `test_db`;");
        } catch (SQLException se) {
            Loger.log("Error connecting to database. The required database has not been created.\n", Loger.log_type.Error);
        }
    }

    /**
     * Attempt to close MySQL connection.
     */
    public void closeDB(){
        try {
            this.stmt.close();      //!
            this.con.close();
        } catch (NullPointerException e){
//            Loger.log("No database connection." + e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("Error closing database.", Loger.log_type.Error);
        }
    }

    /**
     * The method creates a test_db database in the current connection.
     */
    private void createDB(){
        final String query = "CREATE DATABASE IF NOT EXISTS `test_db` " +
                "DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;";
        try{
            stmt.execute(query);
        } catch (NullPointerException e){
//            Loger.log("No database connection." + e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("An error occurred while creating the table. " +
                    "Perhaps you do not have enough rights to do this.\n" + se.getMessage(), Loger.log_type.Error);
        }
    }

    /**
     * The method creates a "data_table" data table in the current database.
     */
    private void createTable(){
        final String query = "CREATE TABLE IF NOT EXISTS `data_table` (\n" +
                "  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "  `company_name` char(32) COLLATE utf8_unicode_ci NOT NULL,\n" +
                "  `tax_number` char(12) COLLATE utf8_unicode_ci NOT NULL,\n" +
                "  `phone_number` char(15) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `address` char(255) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `first_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `surname` char(20) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "   PRIMARY KEY (`id`), UNIQUE(`tax_number`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
        try{
            stmt.execute(query);
        } catch (NullPointerException e){
//            Loger.log("No database connection." + e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("An error occurred while creating the table. " +
                    "Perhaps you do not have enough rights to do this.\n" + se.getMessage(), Loger.log_type.Error);
        }
    }

    /**
     * @param csv_items List of objects with company information from the csv file.
     */
    public void dbUpdate(List<RecordItem> csv_items){
        List<String> remove_list = new ArrayList<>();
        List<RecordItem> add_list = new ArrayList<>();

        List<RecordItem> from_db = getDBObjectList();

         // цикл по данным из базы данных, для построение списка на удаление.
        for (RecordItem item_db : from_db){
            boolean finded = false;
            for (RecordItem item_csv : csv_items){
                if (item_db.compare(item_csv)){
                    finded = true;
                    break;
                }
            }
             // если не найден, в список его
            if (!finded){remove_list.add(item_db.getTax_number());}
        }

         // цикл по данным из файла, для построения списка на добавление.
        for (RecordItem item_csv : csv_items){
            boolean finded = false;
            for (RecordItem item_db : from_db){
                if (item_csv.compare(item_db)){
                    finded = true;
                    break;
                }
            }
            // если не найден, в список его
            if (!finded){add_list.add(item_csv);}
        }

         // add in database
        if (!add_list.isEmpty()) {
            for (RecordItem item : add_list){
                addRecord(item.getCompany_name(),item.getTax_number(),item.getPhone_number(),
                        item.getAddress(),item.getDirector_name(),item.getDirector_surname());
            }
            System.out.printf("\rAdded to database %d value\n>> ", add_list.size());
            App.addCounterInsert(add_list.size());
        }
         // Remove record from database
        if (!remove_list.isEmpty()) {
            for (String item : remove_list){
                removeRecord(item);
            }
            System.out.printf("\rRemoved from database %d values\n>> ", remove_list.size());
            App.deleteCounterInsert(remove_list.size());
        }
    }

    private void addRecord(String company_name, String tax_number, String phone_number,
                           String address, String first_name, String second_name){
        String query = String.format("INSERT INTO `data_table`(`company_name`, `tax_number`, `phone_number`, " +
                "`address`, `first_name`, `surname`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
                company_name, tax_number, phone_number, address, first_name, second_name);
        try {
            stmt.execute(query);
        } catch (NullPointerException e) {
//                Loger.log(e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("Error requesting data.\n" + se.getMessage(), Loger.log_type.Error);
        }
    }

    private void removeRecord(String tax_number){
        String query = String.format("DELETE FROM `data_table` WHERE `tax_number` = '%s';",
                tax_number);
        try {
            stmt.execute(query);
        } catch (NullPointerException e) {
//                Loger.log(e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("Error requesting data.\n" + se.getMessage(), Loger.log_type.Error);
        }
    }

    /**
     * @return List of objects with company information from the database.
     */
    private List<RecordItem> getDBObjectList(){
        String query = "SELECT `company_name`, `tax_number`, `phone_number`, `address`, `first_name`," +
                " `surname` FROM `data_table`;";
        List<RecordItem> result = new ArrayList<>();
        try {
            rs = stmt.executeQuery(query);
            rs.first();
            do {
                result.add(new RecordItem(rs.getNString("company_name"),
                                rs.getNString("tax_number"),
                                rs.getNString("phone_number"),
                                rs.getNString("address"),
                                rs.getNString("first_name"),
                                rs.getNString("surname")
                        )
                );
            } while (rs.next());
        } catch (NullPointerException e) {
//                Loger.log("No database connection." + e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("Error requesting data.\n" + se.getMessage(), Loger.log_type.Error);
        }
        return result;
    }

    public long getSize(){
        String query = "SELECT COUNT(*) as db_size FROM `data_table`;";
        try {
            rs = stmt.executeQuery(query);
            rs.first();
            return rs.getInt("db_size");
        } catch (NullPointerException e) {
//                Loger.log(e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("Error requesting data.\n" + se.getMessage(), Loger.log_type.Error);
        }
        return 0;
    }
}
