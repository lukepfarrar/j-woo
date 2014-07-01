package jWufoo;

import java.util.Date;
import java.util.Iterator;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Report {
	String name;
	Boolean isPublic;
	String url;
    String description;
    Date dateCreated;
    Date dateUpdated;
    String hash;
    String linkEntries;
    String linkFields;
    String linkEntriesCount;
    String linkWidgets;
    JWufooAPI api;
    List<Field> fields;
    List<Widget> widgets;
    
    public String getName() {return this.name; }
    public String getDescription() {return this.description;}
    public String getUrl() {return this.url;}
    public Boolean getIsPublic() {return this.isPublic;}
    public Date getDateCreated() {return this.dateCreated;}
    public Date getDateUpdated() {return this.dateUpdated;}
    public String getHash() {return this.hash;}
    
	public Report(JSONObject json, JWufooAPI api) throws JSONException, ParseException {
		this.api = api;
		this.name = json.getString("Name");
		this.description = json.getString("Description");
		this.url = json.getString("Url");
		this.isPublic = Utils.getBoolean(json.getString("IsPublic"));
		this.dateCreated = Utils.getDate(json.getString("DateCreated"));
		this.dateUpdated = Utils.getDate(json.getString("DateUpdated"));
		this.hash = json.getString("Hash");
		this.linkEntries = json.getString("LinkEntries");
		this.linkFields = json.getString("LinkFields");
		this.linkEntriesCount = json.getString("LinkEntriesCount");
		this.linkWidgets = json.getString("LinkWidgets");
	}
	
	public Field getField(String title) {
		List<Field> fields = new ArrayList<Field>();
		fields = this.getFields();
		for (Field field : fields) {
			if (field.title.contains(title)) {
				return field;
			}
			else if (field.id.contains(title)){
				return field;
			}
		}
		return null;
	}

	public List<Field> getFields() {
		if (this.fields == null) {
			this.fields = new ArrayList<Field>();
			try {
				JSONObject json = this.api.makeRequest(this.linkFields);
				JSONObject fields = json.getJSONObject("Fields");
				@SuppressWarnings("unchecked")
				Iterator<String> keys = fields.keys(); 
				while (keys.hasNext()) {
					Field field = new Field(fields.getJSONObject(keys.next()));
					this.fields.add(field);
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
		return this.fields;
	}
	
	public List<Widget> getWidgets() {
		if (this.widgets == null) {
			this.widgets = new ArrayList<Widget>();
			try {
				JSONObject json = this.api.makeRequest(this.linkWidgets);
				JSONArray rawNodes = json.getJSONArray("Widgets");
				int rawCount = rawNodes.length();
				for (int i = 0; i < rawCount; i++) {
					this.widgets.add(new Widget(rawNodes.getJSONObject(i), this));
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
		return this.widgets;
	}
	
	public List<Entry> getEntries(int pageStart, int pageSize, String sort) {
		return getEntries(pageStart, pageSize, sort, "DESC");
	}
	public List<Entry> getEntries(int pageStart, int pageSize) {
		return getEntries(pageStart, pageSize, "DateCreated", "DESC");
	}
	public List<Entry> getEntries(String sort, String sortDirection) {
		return getEntries(0, 100, sort, sortDirection);
	}
	public List<Entry> getEntries(String sort) {
		return getEntries(0, 100, sort, "DESC");
	}
	public List<Entry> getEntries() {
		return getEntries(0, 100, "DateCreated", "DESC");
	}

	public List<Entry> getEntries(int pageStart, int pageSize, String sort, String sortDirection) {
	    List<Entry> entries = new ArrayList<Entry>();
		String url = String.format("%s?system=true&pageStart=%d&pageSize=%d&sort=%s&sortDirection=%s", 
				this.linkEntries, 
				pageStart,
				pageSize,
				sort,
				sortDirection);
		System.out.println(url);
	    JSONObject json = new JSONObject();
	    JSONArray rawNodes = new JSONArray();
		int rawCount = 0;
		
	    try {
			json = this.api.makeRequest(url);
			rawNodes = json.getJSONArray("Entries");
			rawCount = rawNodes.length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		for (int i = 0; i < rawCount; i++) {
			try {
				entries.add(new Entry(rawNodes.getJSONObject(i), null));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return entries;
	}
	
	public String getLink() {
		return String.format("https://%s.wufoo.com/reports/%s/", this.api.account, this.hash);
	}
}
