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
        return imageUrlSmall;//String.format("https://%s.wufoo.com/images/avatars/small/%s.png", api.account, Image);
    }

    public String getImageUrlBig() {
        return imageUrlBig;//String.format("https://%s.wufoo.com/images/avatars/big/%s.png", api.account, Image);
    }

    public User(JSONObject json) throws JSONException, ParseException {
        user = json.getString("User");
        email = json.getString("Email");
        timeZone = json.getString("TimeZone");
        company = json.getString("Company");
        isAccountOwner = Utils.getBoolean(json.getString("IsAccountOwner"));
        createForms = Utils.getBoolean(json.getString("CreateForms"));
        createReports = Utils.getBoolean(json.getString("CreateReports"));
        createThemes = Utils.getBoolean(json.getString("CreateThemes"));
        adminAccess = Utils.getBoolean(json.getString("AdminAccess"));
        apiKey = json.getString("ApiKey");
        hash = json.getString("Hash");
        //this.Image = json.getString("Image");
        imageUrlSmall = json.getString("ImageUrlSmall");
        imageUrlBig = json.getString("ImageUrlBig");
    }
}
