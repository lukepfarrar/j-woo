package jWufoo;

import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class SubField {

    String label;
    String id;

    public String getLabel() {
        return label;
    }

    public String getID() {
        return id;
    }

    public SubField(JSONObject json) throws JSONException, ParseException {
        label = json.getString("Label");
        id = json.getString("ID");
    }
}
