package jWufoo;

import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class SubField {

    String label;
    String id;

    public String getLabel() {
        return this.label;
    }

    public String getID() {
        return this.id;
    }

    public SubField(JSONObject json) throws JSONException, ParseException {
        this.label = json.getString("Label");
        this.id = json.getString("ID");
    }
}
