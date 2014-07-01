package jWufoo;

import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
	String user;
	String email;
	String timeZone;
	String company;
	Boolean isAccountOwner;
	Boolean createForms;
	Boolean createReports;
	Boolean createThemes;
	Boolean adminAccess;
	String apiKey;
	String hash;
	//String Image;
	String imageUrlSmall;
	String imageUrlBig;
	
	public String getUser() {
		return user;
	}
	public String getEmail() {
		return email;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public String getCompany() {
		return company;
	}
	public Boolean getIsAccountOwner() {
		return isAccountOwner;
	}
	public Boolean getCreateForms() {
		return createForms;
	}
	public Boolean getCreateReports() {
		return createReports;
	}
	public Boolean getCreateThemes() {
		return createThemes;
	}
	public Boolean getAdminAccess() {
		return adminAccess;
	}
	public String getApiKey() {
		return apiKey;
	}
	public String getHash() {
		return hash;
	}
	public String getImageUrlSmall() {
		return this.imageUrlSmall;//String.format("https://%s.wufoo.com/images/avatars/small/%s.png", api.account, Image);
	}
	public String getImageUrlBig() {
		return this.imageUrlBig;//String.format("https://%s.wufoo.com/images/avatars/big/%s.png", api.account, Image);
	}

	
	public User(JSONObject json) throws JSONException, ParseException {
		this.user = json.getString("User");
		this.email = json.getString("Email");
		this.timeZone = json.getString("TimeZone");
		this.company = json.getString("Company");
		this.isAccountOwner = Utils.getBoolean(json.getString("IsAccountOwner"));
		this.createForms = Utils.getBoolean(json.getString("CreateForms"));
		this.createReports = Utils.getBoolean(json.getString("CreateReports"));
		this.createThemes = Utils.getBoolean(json.getString("CreateThemes"));
		this.adminAccess = Utils.getBoolean(json.getString("AdminAccess"));
		this.apiKey = json.getString("ApiKey");
		this.hash = json.getString("Hash");
		//this.Image = json.getString("Image");
		this.imageUrlSmall = json.getString("ImageUrlSmall");
		this.imageUrlBig = json.getString("ImageUrlBig");
	}
}
