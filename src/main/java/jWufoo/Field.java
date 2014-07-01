package jWufoo;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Field {
	String title;
    String type;
    String id;
    Boolean isRequired;
    ArrayList<SubField> subFields;
    
    public String getTitle() {return this.title; }
    public String getType() {return this.type;}
    public String getID() {return this.id;}
    public Boolean getIsRequired() {return this.isRequired;}
    public ArrayList<SubField> getSubFields() {return this.subFields;}
    
	public Field(JSONObject json) throws JSONException, ParseException {
		this.title = json.getString("Title");
		this.type = json.getString("Type");
		this.id = json.getString("ID");
		if (json.has("IsRequired")) {
			this.isRequired = Utils.getBoolean(json.getString("IsRequired"));
		}
		else {
			this.isRequired = false;
		}
		this.subFields = new ArrayList<SubField>();
		if (json.has("SubFields")) {
			JSONArray subFieldArray = json.getJSONArray("SubFields");
			for (int x = 0; x < subFieldArray.length(); x++) {
				this.subFields.add(new SubField(subFieldArray.getJSONObject(x)));
			}
		}
		
	}
}
