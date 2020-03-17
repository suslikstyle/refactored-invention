import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {

    private static Config conf = new Config();
    private static Database db;
    private static boolean active = true;       // application work flag. If false, the application terminates.
    private static boolean runing = false;      // flag of the thread in which the database is updated. If false, the thread terminates and the application does not update the data.
    private static long add_counter = 0;
    private static long delete_counter = 0;
    private static long start_time = System.currentTimeMillis();


    private static final String help = "How to use:\n"+
            " enter \"help\" or \"h\"\t\t\t\t\t\t- Show this message.\n"+
            " enter \"start\"\t\t\t\t\t\t\t\t- To start, enter the command \"start\"\n"+
            " enter \"stop\"\t\t\t\t\t\t\t\t- To stop, enter the command \"stop\"\n"+
            " enter \"status\"\t\t\t\t\t\t\t\t- To find out the current state of the application.\n"+
            " enter \"exit\" or \"close\" or \"quit\" or \"q\" \t- Close application.\n"+
            " set delay <integer_milliseconds>\t\t\t- Set polling frequency. Once per <value> millisecond.\n";

    public static void main(String[] args) {

        if (0 < args.length && args[0].toLowerCase().equals("-start")){
            App.runing = true;
        }

        Thread thr = new WorkThread();
//        thr.setDaemon(true);  // демонизация не реализована
        thr.start();

        while (active){
            try {
                if (db == null) {
                    Thread.sleep(20);
                    continue;
                }
                System.out.print(">> ");
                readInput(Loger.getInput().split(" "));
            } catch (StringIndexOutOfBoundsException e){
//                    Loger.log(e.getMessage());
            } catch (InterruptedException e) {
                Loger.log(e.getMessage(), Loger.log_type.Error);
            }
        }
        runing = false;
        try {
            thr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Array of strings consisting of a sequence of commands.
     */
    private static void readInput(String[] args) {
        if (args.length < 1) return;
        if (args[0].toLowerCase().equals("q") || args[0].toLowerCase().equals("quit") ||
                args[0].toLowerCase().equals("exit") || args[0].toLowerCase().equals("close")) {
            Loger.log("Application shutdown.");
            active = runing = false;
        } else if (args[0].toLowerCase().equals("h") || args[0].toLowerCase().equals("help")) {
            System.out.println(help);
        } else if (args[0].toLowerCase().equals("start")) {
            setRuning(true);
        } else if (args[0].toLowerCase().equals("stop")) {
            setRuning(false);
        } else if (2 <= args.length && args[0].toLowerCase().equals("set")) {
            if (3 == args.length && args[1].toLowerCase().equals("delay")) {
                try {
                    int delay = Integer.parseInt(args[2]);
                    if (delay < 120000) {
                        WorkThread.setDelay(delay);
                    } else {
                        System.out.printf("\rThe entered value is very large. Current value is %d ms.\n", WorkThread.getDelay());
                    }
                } catch (NumberFormatException e) {
                    Loger.log("set delay <integer_milliseconds>");
                }
            } else {
                System.out.println(help);
            }
        } else if (args[0].toLowerCase().equals("status")){
            String threadStatus = App.runing ? "work" : "not work";
            System.out.printf("\rCurrent status: %s\nAdded entries for the current session: %d\n" +
                    "Deleted entries for the current session: %d\nElapsed time since launch: %s\n" +
                    "Number of records in the database: %d\n", threadStatus, add_counter, delete_counter,
                    upTime(), db.getSize());
        }else {System.out.println("Not recognize it. Enter \"help\".");}
    }

    /**
     * @param runing Setting state for data updater thread.
     */
    public static void setRuning(boolean runing) {
        App.runing = runing;
    }

    /**
     * @param val A set of text options.
     * @return Generates an array of strings consisting of input parameters.
     */
    private static String[] makeParam(String... val){
        String[] result = new String[val.length];
        System.arraycopy(val, 0, result, 0, val.length);
        return result;
    }

    public static void addCounterInsert(long value){
        add_counter += value;
    }
    public static void deleteCounterInsert(long value){
        delete_counter += value;
    }

    private static String upTime(){
        long millis = System.currentTimeMillis() - start_time;
        return String.format("%d days %h hours %d minutes, %d seconds",
                TimeUnit.MILLISECONDS.toDays(millis),
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }


    /**
     * The class of the data update thread.
     */
    private static class WorkThread extends Thread {

        /**
         * Delay between updates
         */
        private static int delay = 5000;
        public static void setDelay(int delay) {    //setter
            WorkThread.delay = delay;
        }   // setter
        public static int getDelay() { return delay; }  // getter

        @Override
        public void run() {
            db = new Database(conf.getURL(), conf.getUsername(), conf.getPassword());

            while (active && !this.isInterrupted()){
                while (runing){
                    Launcher updater = new Launcher(makeParam("java", "-jar", conf.getUpdater_path(), "-get"),
                            conf.getCharset());
                    List<String> result = updater.getOut_data();
                    for (String line : result) {
                        try {
                            // попытки интерпритировать вывод как строку массива JSON
                            JSON obj = new JSON(line);
                            db.dbUpdate( obj.getCSVObjectList() );
                        } catch (ParseException e){
//                            System.out.println(line);
                        }
                    }
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Loger.log(e.getMessage(), Loger.log_type.Error);
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Loger.log(e.getMessage(), Loger.log_type.Error);
                }
            }
            db.closeDB();
        }

    }
}
