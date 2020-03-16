import java.util.List;
import java.util.Scanner;

public class App {

    private static Config conf = new Config();
    private static boolean active = true;

    public static void main(String[] args) {

//        Thread thr = new WorkThread();
//        thr.start();



//        Database db = new Database(conf.getURL(), conf.getUsername(), conf.getPassword());
//        System.out.println(db.getRecordSize());
//        db.closeDB();

        while (active){
            Scanner sc = new Scanner(System.in);
            String cmd_line = sc.nextLine();
            String[] cmd = cmd_line.split(" ");
            for (String word : cmd){
                System.out.println(word);
            }
            if (cmd[0].toLowerCase().equals("q") || cmd[0].toLowerCase().equals("quit") ||
                    cmd[0].toLowerCase().equals("exit")){
                active = false;
            }
        }
    }

    private static String[] makeParam(String... val){
        String[] result = new String[val.length];
        System.arraycopy(val, 0, result, 0, val.length);
        return result;
    }



    private static class WorkThread extends Thread {

        private static int delay = 5000;

        public static void setDelay(int delay) {
            WorkThread.delay = delay;
        }

        @Override
        public void run() {
            while (active){
                Launcher l = new Launcher(makeParam("java", "-jar", conf.getUpdater_path(), "-get"),
                        conf.getCharset());
                List<String> result = l.getOut_data();
                for (String line : result){
                    System.out.println(line);
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
