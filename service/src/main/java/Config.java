import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Config {

    /**
     * Константа, путь к файлу конфигурации
     */
    final String conf_path = "B:/java/refactored-invention/service/config.json";
    private JSONObject config;

    /**
     * When creating an object in the constructor, it reads the json configuration file,
     * if there is no file or data could not be parsed, the application terminates.
     */
    public Config() {
        JSONParser parser = new JSONParser();
        try {
            this.config = (JSONObject) parser.parse(new FileReader(this.conf_path));
        } catch (FileNotFoundException e){
            System.out.println("Configuration file not found. Press enter to exit.");
//            try {System.in.read();} catch (IOException ex){ System.out.println("Exit."); }
            System.exit(1);
        } catch (ParseException e) {
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return URL String from settings.
     */
    public String getURL(){
        String result;
        try {
            JSONObject mysql_conf = (JSONObject) this.config.get("mysql_server");
            result = mysql_conf.get("url").toString();
        } catch (Exception e){
            return null;
        }
        return result;
    }

    /**
     * @return username String from settings.
     */
    public String getUsername(){
        String result;
        try {
            JSONObject mysql_conf = (JSONObject) this.config.get("mysql_server");
            result = mysql_conf.get("username").toString();
        } catch (Exception e){
            return null;
        }
        return result;
    }

    /**
     * @return password String from settings.
     */
    public String getPassword(){
        String result;
        try {
            JSONObject mysql_conf = (JSONObject) this.config.get("mysql_server");
            result = mysql_conf.get("password").toString();
        } catch (Exception e){
            return null;
        }
        return result;
    }

    /**
     * @return A line from the settings indicating the location of the updater
     */
    public String getUpdater_path(){
        String result;
        try {
            result = this.config.get("updater_path").toString();
        } catch (Exception e){
            result = null;
        }
        return result;
    }

    /**
     * @return Used character encoding from settings.
     */
    public String getCharset(){
        String result;
        try {
            result = this.config.get("charset_name").toString();
        } catch (Exception e){
            result = null;
        }
        return result;
    }

    /**
     * @return The delay value between updates specified in the settings.
     */
    public int getUpdateDelay(){
        int result = 20000;
        try {
            result = (int) this.config.get("update_delay");
        } catch (Exception e){
//            result = 20000;
        }
        return result;
    }
}
