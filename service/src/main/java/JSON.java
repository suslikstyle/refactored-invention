import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSON {

    private JSONArray jsonArray;

    public JSON(String json_string) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json_string);
        this.jsonArray = (JSONArray) obj;
    }

    public int getSize(){
        return jsonArray.size();
    }

    /**
     * @return Generates a list of objects containing information about the company.
     */
    public List<RecordItem> getCSVObjectList(){
        List<RecordItem> result = new ArrayList<>();
        for (int i = 1; i < jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            result.add(new RecordItem((String) object.get("name"), (String) object.get("tax_num"),
                    (String) object.get("phone_num"), (String) object.get("address"),
                    (String) object.get("director_name"), (String) object.get("director_surname")
                    )
            );
        }
        return result;
    }
}
