import java.text.SimpleDateFormat;
import java.util.Date;

public class Loger {

    public enum log_type {Info, Error}
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void log(String msg, log_type tag){
        String time = format.format(new Date());
        if(tag == log_type.Error) {
            System.err.printf("[%s] {%s} : %s\n", time, tag.toString(), msg);
        } else {
            System.out.printf("[%s] {%s} : %s\n", time, tag.toString(), msg);
        }
    }

    public static void log(String msg){
        String time = format.format(new Date());
        System.out.printf("[%s] {%s} => %s\n", time, "Info", msg);
    }
}