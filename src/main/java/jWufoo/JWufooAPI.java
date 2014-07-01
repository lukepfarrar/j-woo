package jWufoo;

import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

public class JWufooAPI {

    String key;
    String account;
    List<Form> forms;
    List<Report> reports;
    List<User> users;

    public JWufooAPI(String key, String account) {
        this.key = key;
        this.account = account;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        key = key;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        account = account;
    }

    public List<Form> getForms() {
        if (forms == null) {
            forms = new ArrayList<Form>();
            try {
                JSONObject json = this.makeRequest("https://" + this.account + ".wufoo.com/api/v3/forms.json");
                JSONArray rawNodes = json.getJSONArray("Forms");
                int rawCount = rawNodes.length();
                for (int i = 0; i < rawCount; i++) {
                    forms.add(new Form(rawNodes.getJSONObject(i), this));
                }
            } catch (IOException ex) {
            } catch (ParseException ex) {
            } catch (JSONException ex) {
            }
        }
        return forms;
    }

    public List<Report> getReports() {
        if (reports == null) {
            reports = new ArrayList<Report>();
            try {
                JSONObject json = this.makeRequest("https://" + this.account + ".wufoo.com/api/v3/reports.json");
                JSONArray rawNodes = json.getJSONArray("Reports");
                int rawCount = rawNodes.length();
                for (int i = 0; i < rawCount; i++) {
                    reports.add(new Report(rawNodes.getJSONObject(i), this));
                }
            } catch (IOException ex) {
            } catch (ParseException ex) {
            } catch (JSONException ex) {
            }
        }
        return reports;
    }

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<User>();
            JSONObject json;
            JSONArray rawNodes;
            try {
                json = this.makeRequest("https://" + this.account + ".wufoo.com/api/v3/users.json");
                rawNodes = json.getJSONArray("Users");
                int rawCount = rawNodes.length();
                for (int i = 0; i < rawCount; i++) {
                    users.add(new User(rawNodes.getJSONObject(i)));
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
        return users;
    }

    public JSONObject makeRequest(String url) throws IOException, JSONException {
        return this.makeRequest(url, null);
    }

    public JSONObject makeRequest(String url, Hashtable<String, Object> postData) throws IOException, JSONException {
        return this.makeRequest(url, postData, "POST");
    }

    public JSONObject makeRequest(String url, Hashtable<String, Object> postData, String method) throws IOException, JSONException {
        HttpClient client = new HttpClient();
        client.getState().setCredentials(
                new AuthScope(String.format("%s.wufoo.com", this.account), 443, "Wufoo Secret Lab"),
                new UsernamePasswordCredentials(this.key, "footastic"));
        String json = "";

        if (postData != null) {
            Enumeration<String> keys = postData.keys();
            MultiPurposeMethod post = new MultiPurposeMethod(url, method);
            post.setDoAuthentication(true);
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                post.addParameter(key, postData.get(key).toString());
            }
            try {
                int status = client.executeMethod(post);
                json = post.getResponseBodyAsString();
                System.out.println(status + "\n" + json);
            } finally {
                post.releaseConnection();
            }
        } else {
            GetMethod get = new GetMethod(url);
            get.setDoAuthentication(true);
            try {
                int status = client.executeMethod(get);
                json = get.getResponseBodyAsString();
                System.out.println(status + "\n" + json);
            } finally {
                get.releaseConnection();
            }
        }
        return new JSONObject(json);
    }
}
