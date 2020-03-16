import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

    private List<String> out_data = new ArrayList<>();

    public Launcher(String[] args, String charset){

//        ProcessBuilder builder = new ProcessBuilder("java", "-jar",
//                "B:/java/refactored-invention/csv_updater/target/csv_updater-0.1.jar", "-add", "ООО \"test\"",
//                "110000000000", "+79991234567", "пр. Кирова 93", "Павел", "Константинович");

//        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "B:/java/refactored-invention/csv_updater/target/csv_updater-0.1.jar", "-get");
        ProcessBuilder builder = new ProcessBuilder(args);
        builder.redirectErrorStream( true );
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert process != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), charset) ) ) {
            String line;
            while ((line = br.readLine()) != null) {
                this.out_data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> getOut_data() {
        return out_data;
    }
}
