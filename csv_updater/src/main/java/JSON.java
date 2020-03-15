import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class JSON {

    private JSONArray jsonArray = new JSONArray();

    /**
     * @param json_string Строка в формате JSON
     */
    public JSON(String json_string){
        // constructor
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(json_string);
            this.jsonArray = (JSONArray) obj;
        }catch (ParseException e){
            Loger.log(String.format("Json parse error. %s\n",e.getMessage()), Loger.log_type.Error);
        }
    }

    /**
     * @param item Объект одной записи
     */
    @SuppressWarnings("unchecked")
    public JSON(RecordItem item){
        // constructor
        JSONObject json_obj = new JSONObject();
        json_obj.put("name",item.getLine()[0]);
        json_obj.put("tax_num",item.getLine()[0]);
        json_obj.put("phone_num",item.getLine()[0]);
        json_obj.put("address",item.getLine()[0]);
        json_obj.put("director_name",item.getLine()[0]);
        json_obj.put("director_surname",item.getLine()[0]);

//        this.jsonArray.clear();
        this.jsonArray.add(json_obj);
    }

    /**
     * @param records Список всех записей из файла
     */
    @SuppressWarnings("unchecked")
    public JSON(List<RecordItem> records){
        // constructor
//        this.jsonArray.clear();
        for (RecordItem record : records) {
            JSONObject json_obj = new JSONObject();
            json_obj.put("name", record.getLine()[0]);
            json_obj.put("tax_num", record.getLine()[1]);
            json_obj.put("phone_num", record.getLine()[2]);
            json_obj.put("address", record.getLine()[3]);
            json_obj.put("director_name", record.getLine()[4]);
            json_obj.put("director_surname", record.getLine()[5]);
            this.jsonArray.add(json_obj);
        }
    }

    /**
     * @return Returns a JSON string of this instance
     */
    public String getJSONString(){
        return jsonArray.toJSONString();
    }
}
