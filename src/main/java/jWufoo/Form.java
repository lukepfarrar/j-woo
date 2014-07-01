package jWufoo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.io.IOException;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Form {
	String name;
    String description;
    String redirectMessage;
    String url;
    String email;
    Boolean isPublic;
    String language;
    Date startDate;
    Date endDate;
    int entryLimit;
    Date dateCreated;
    Date dateUpdated;
    String hash;
    String linkEntries;
    String linkFields;
    String linkEntriesCount;
    JWufooAPI api;
    ArrayList<Field> fields;
    ArrayList<Comment> comments;
	
    public String getName() {return this.name; }
    public String getDescription() {return this.description;}
    public String getRedirectMessage() {return this.redirectMessage;}
    public String getUrl() {return this.url;}
    public String getEmail() {return this.email;}
    public Boolean getIsPublic() {return this.isPublic;}
    public String getLanguage() {return this.language;}
    public Date getStartDate() {return this.startDate;}
    public Date getEndDate() {return this.endDate;}
    public int getEntryLimit() {return this.entryLimit;}
    public Date getDateCreated() {return this.dateCreated;}
    public Date getDateUpdated() {return this.dateUpdated;}
    public String getHash() {return this.hash;}
    
	public Form(JSONObject json, JWufooAPI api) throws JSONException, ParseException {
		this.api = api;
		this.name = json.getString("Name");
		this.description = json.getString("Description");
		this.redirectMessage = json.getString("RedirectMessage");
		this.url = json.getString("Url");
		this.email = json.getString("Email");
		this.isPublic = Utils.getBoolean(json.getString("IsPublic"));
		this.language = json.getString("Language");
		this.startDate = Utils.getDate(json.getString("StartDate"));
		this.endDate = Utils.getDate(json.getString("EndDate"));
		this.entryLimit = json.getInt("EntryLimit");
		this.dateCreated = Utils.getDate(json.getString("DateCreated"));
		this.dateUpdated = Utils.getDate(json.getString("DateUpdated"));
		this.hash = json.getString("Hash");
		this.linkEntries = json.getString("LinkEntries");
		this.linkFields = json.getString("LinkFields");
		this.linkEntriesCount = json.getString("LinkEntriesCount");
	}
	
	public Field getField(String title) {
		ArrayList<Field> fields = new ArrayList<Field>();
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

	public ArrayList<Field> getFields() {
		if (this.fields == null) {
			this.fields = new ArrayList<Field>();
			try {
				JSONObject json = this.api.makeRequest(this.linkFields);
				JSONArray rawNodes = json.getJSONArray("Fields");
				int rawCount = rawNodes.length();
				for (int i = 0; i < rawCount; i++) {
					this.fields.add(new Field(rawNodes.getJSONObject(i)));
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
	
	public ArrayList<Entry> getEntries(int pageStart, int pageSize, String sort) {
		return getEntries(pageStart, pageSize, sort, "DESC");
	}
	public ArrayList<Entry> getEntries(int pageStart, int pageSize) {
		return getEntries(pageStart, pageSize, "DateCreated", "DESC");
	}
	public ArrayList<Entry> getEntries(String sort, String sortDirection) {
		return getEntries(0, 100, sort, sortDirection);
	}
	public ArrayList<Entry> getEntries(String sort) {
		return getEntries(0, 100, sort, "DESC");
	}
	public ArrayList<Entry> getEntries() {
		return getEntries(0, 100, "DateCreated", "DESC");
	}

	public ArrayList<Entry> getEntries(int pageStart, int pageSize, String sort, String sortDirection) {
	    ArrayList<Entry> entries = new ArrayList<Entry>();
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
				entries.add(new Entry(rawNodes.getJSONObject(i), this));
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

	public ArrayList<Entry> searchEntries(ArrayList<Filter> filters, int pageStart, int pageSize, String sort) {
		return searchEntries(filters, pageStart, pageSize, sort, "DESC");
	}
	public ArrayList<Entry> searchEntries(ArrayList<Filter> filters, int pageStart, int pageSize) {
		return searchEntries(filters, pageStart, pageSize, "DateCreated", "DESC");
	}
	public ArrayList<Entry> searchEntries(ArrayList<Filter> filters, String sort, String sortDirection) {
		return searchEntries(filters, 0, 100, sort, sortDirection);
	}
	public ArrayList<Entry> searchEntries(ArrayList<Filter> filters, String sort) {
		return searchEntries(filters, 0, 100, sort, "DESC");
	}
	public ArrayList<Entry> searchEntries(ArrayList<Filter> filters) {
		return searchEntries(filters, 0, 100, "DateCreated", "DESC");
	}

	public ArrayList<Entry> searchEntries(ArrayList<Filter> filters, int pageStart, int pageSize, String sort, String sortDirection) {
	    ArrayList<Entry> entries = new ArrayList<Entry>();
		StringBuilder filtersBuilder = new StringBuilder();
		for (int looper = 0; looper < filters.size(); looper++) {
			Filter filter = filters.get(looper);
			filtersBuilder.append(String.format("&Filter%s=%s+%s+%s", looper+1, filter.getField().id, filter.getOperator(), filter.getValue()));
		}
	    String filtersString = filtersBuilder.toString();
	    
	    String url = String.format("%s?system=true&pageStart=%d&pageSize=%d&sort=%s&sortDirection=%s%s", 
				this.linkEntries, 
				pageStart,
				pageSize,
				sort,
				sortDirection,
				filtersString);
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
				entries.add(new Entry(rawNodes.getJSONObject(i), this));
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
	
	public ArrayList<Comment> getComments() {
    	if (this.comments == null)  {
    		String url = String.format("https://%s.wufoo.com/api/v3/forms/%s/comments.json", this.api.account, this.hash);
    		this.comments = new ArrayList<Comment>();
    		try {
    			JSONObject json = this.api.makeRequest(url);
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
	
	public Hashtable<String, String> addEntry(Entry entry) {
		String url = String.format("https://%s.wufoo.com/api/v3/forms/%s/entries.json", this.api.account, this.getHash());
		Hashtable<String, String> errors = new Hashtable<String, String>();
		try {
			JSONObject response = this.api.makeRequest(url, entry.getFields());
			if (response.getInt("Success") == 1) {
				entry.entryId = response.getInt("EntryId");
			}
			else{
				JSONArray jsonErrors = response.getJSONArray("FieldErrors");
				for (int looper = 0; looper < jsonErrors.length(); looper++) {
					JSONObject error = jsonErrors.getJSONObject(looper);
					errors.put(error.getString("ID"), error.getString("ErrorText"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errors;
	}

	public String getLinkUrl() {
		return String.format("http://%s.wufoo.com/forms/%s/", this.api.getAccount(), this.hash);
	}
	
	public String getEmbedUrl() {
		String account = this.api.getAccount();
		String hash = this.getHash();
		return String.format("<script type=\"text/javascript\">var host = ((\"https:\" == document.location.protocol) ? \"https://secure.\" : \"http://\");document.write(unescape(\"%%3Cscript src='\" + host + \"wufoo.com/scripts/embed/form.js' type='text/javascript'%%3E%%3C/script%%3E\"));</script>\n<script type=\"text/javascript\">\nvar %s = new WufooForm();\n%s.initialize({\n'userName':'%s',\n'formHash':'%s', \n'autoResize':true,\n'height':'607'});\n%s.display();\n</script>", hash, hash, account, hash, hash);
	}
	
	public String getIframeEmbedUrl() {
		String account = this.api.getAccount();
		String hash = this.getHash();
		String title = this.getName();
		return String.format("<iframe height=\"607\" allowTransparency=\"true\" frameborder=\"0\" scrolling=\"no\" style=\"width:100%%;border:none\"  src=\"http://%s.wufoo.com/embed/%s/\"><a href=\"http://%s.wufoo.com/forms/%s/\" title=\"%s\" rel=\"nofollow\">Fill out my Wufoo form!</a></iframe>", account, hash, account, hash, title);
	}
	

	public String addWebHook(String hookUrl, String handshakeKey, boolean returnMetadata) {
		String url = String.format("https://%s.wufoo.com/api/v3/forms/%s/webhooks.json", this.api.account, this.getHash());
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("url", hookUrl);
		params.put("handshakeKey", handshakeKey);
		params.put("metadata", returnMetadata);

		try {
			JSONObject response = this.api.makeRequest(url, params, "PUT");
			response = response.getJSONObject("WebHookPutResult");
			return response.getString("Hash");
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String deleteWebHook(String webHookHash) {
		String url = String.format("https://%s.wufoo.com/api/v3/forms/%s/webhooks/%s.json", this.api.account, this.getHash(), webHookHash);
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("hash", webHookHash);

		try {
			JSONObject response = this.api.makeRequest(url, params, "DELETE");
			response = response.getJSONObject("WebHookDeleteResult");
			return response.getString("Hash");
	       
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
