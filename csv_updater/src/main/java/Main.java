import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    final static String help = "How use: \njava -jar test_api.jar [options]\n"+
            "Where options include:\n"+
            "  -help\t\t\t\tDisplays this help\n"+
            "  -get\t\t\t\tGet data into json\n"+
            "  -add <json_data>\t\tAdds new data to a CSV file\n"+
            "  -delete <name company>\tDelete record from file\n"+
            "  -delete <tax number>\tDelete record from file.";

    final static String csv_path = "B:/java/refactored-invention/csv_updater/data.csv";

    public static void main(String[] args) {
        if (args.length >= 1){
            if (1 == args.length && args[0].equals("-help") ){
                //help return
                System.out.println(help);
            } else if (1 == args.length && args[0].equals("-get") ){
                System.out.println(getData());
            } else if (args.length > 2 && args[0].equals("-add")){
                // args.length > 2 Because with the -add parameter, <company name> and <tax number> are required
                String[] params = new String[args.length - 1];
                System.arraycopy(args, 1, params, 0, args.length - 1);
                addData(params);
            } else if (2 == args.length && args[0].equals("-delete") ){
                deleteData(args[1]);
            } else {System.out.println("Command not recognized.\n\n"+help); }
        } else {
            System.out.println(help);
        }
    }

    /**
     * @return Returns a string with data in JSON format.
     */
    private static String getData(){
        String json_str;
        CSV csv_file = new CSV(csv_path);
        List<RecordItem> all_records =  csv_file.getRecords();
        json_str = new JSON(all_records).getJSONString();
        return json_str;
    }

    private static void addData(String[] values){
        // нет порверки символов на валидность (12 цифр)
        CSV csv_file = new CSV(csv_path);
        csv_file.addRecord(new RecordItem(values));
    }

    /**
     * @param value The string value of the company name or tax number whose data you want to delete.
     */
    private static void deleteData(String value){
        CSV csv_file = new CSV(csv_path);
        csv_file.deleteRecord(value);
    }
}
