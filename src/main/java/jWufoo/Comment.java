package jWufoo;

import java.text.ParseException;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class Comment {

    int commentId;
    int entryId;
    String text;
    String commentedBy;
    Date dateCreated;

    public int getCommentID() {
        return this.commentId;
    }

    public int getEntryID() {
        return this.entryId;
    }

    public String getText() {
        return this.text;
    }

    public String getCommentedBy() {
        return this.commentedBy;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public Comment(JSONObject json) throws JSONException, ParseException {
        this.commentId = json.getInt("CommentId");
        this.entryId = json.getInt("EntryId");
        this.text = json.getString("Text");
        this.commentedBy = json.getString("CommentedBy");
        this.dateCreated = Utils.getDate(json.getString("DateCreated"));
    }
}
