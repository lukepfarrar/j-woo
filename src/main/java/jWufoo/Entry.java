package jWufoo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Entry {
	int entryId;
    Hashtable<String, Object> fields;
	String createdBy;
	Date dateCreated;
	String updatedBy;
	Date dateUpdated;
	Form form;
	List<Comment> comments;
	
    public int getEntryId() {return this.entryId; }
    public Hashtable<String, Object> getFields() {return this.fields;}
    public String getCreatedBy() {return this.createdBy; }
    public Date getDateCreated() {return this.dateCreated; }
    public String getUpdatedBy() {return this.updatedBy; }
    public Date getDateUpdated() {return this.dateUpdated; }
    public List<Comment> getComments() {
    	if (this.comments == null)  {
    		String url = String.format("https://%s.wufoo.com/api/v3/forms/%s/comments.json?entryId=%s", this.form.api.account, this.form.hash, this.entryId);
    		this.comments = new ArrayList<Comment>();
    		try {
    			JSONObject json = this.form.api.makeRequest(url);
				JSONArray rawNodes = json.getJSONArray("Comments");
				int rawCount = rawNodes.length();
				for (int i = 0; i < rawCount; i++) {
					this.comments.add(new Comment(rawNodes.getJSONObject(i)));
				}
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} 
    	return this.comments;     		
    }
		
    public Entry() {
    	this.fields = new Hashtable<String, Object>();
    }
    
	public Entry(JSONObject json, Form form) throws JSONException, ParseException {
		this.entryId = json.getInt("EntryId");
		this.createdBy = json.optString("CreatedBy", "");
		this.dateCreated = Utils.getDate(json.optString("DateCreated", ""));
		this.updatedBy = json.optString("UpdatedBy", "");
		this.dateUpdated = Utils.getDate(json.optString("DateUpdated", ""));
		this.fields = new Hashtable<String, Object>();
		this.form = form;
		
		JSONArray fields = json.names();
		for (int x = 0; x < fields.length(); x++){
			String field = (String) fields.get(x);
			//if (field.startsWith("Field")) {
			this.fields.put(field, json.get(field));
			//}
		}
	}
}
