import java.sql.*;

public class Database {

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public Database(String url, String username, String password){
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
        } catch (SQLException se) {
            Loger.log("Error connecting to database: " + se.getMessage(), Loger.log_type.Error);
            System.exit(1);
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

    public int getRecordSize(){
        String query = "SELECT COUNT(*) as size FROM `data_table`;";
        int result = -1;
        try {
            rs = stmt.executeQuery(query);
            rs.first();
            result = rs.getInt("size");
        } catch (NullPointerException e) {
//                Loger.log("No database connection." + e.getMessage(), Loger.log_type.Error);
        } catch (SQLException se) {
            Loger.log("Error requesting data." + se.getMessage(), Loger.log_type.Error);
        }
        return result;
    }


}
