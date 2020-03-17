import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Loger {

    public enum log_type {Info, Error}
    private static Scanner reader = new Scanner(System.in);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static ReentrantLock lock = new ReentrantLock();

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

    /**
     * @param q A line with a question to the user.
     * @return User Response.
     */
    public static boolean question(String q){
        while (true) {
            System.out.printf("%s (y/n): ", q);
            String line = getInput();
            try {
                if ('y' == line.charAt(0)) {
                    return true;
                } else if ('n' == line.charAt(0)) {
                    return false;
                } else {
                    continue;
                }
            }catch (StringIndexOutOfBoundsException e){
                return false;
            }
        }
    }

    /**
     * @return User entered string.
     */
    public static String getInput(){
        lock.lock();
        String result;
        try {
            result = reader.nextLine();
        } finally {
            lock.unlock();
        }
        return result;
    }
}