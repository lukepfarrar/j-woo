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
        return commentId;
    }

    public int getEntryID() {
        return entryId;
    }

    public String getText() {
        return text;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public Date getDateCreated() {
        return new Date(dateCreated.getTime());
    }

    public Comment(JSONObject json) throws JSONException, ParseException {
        this.commentId = json.getInt("CommentId");
        this.entryId = json.getInt("EntryId");
        this.text = json.getString("Text");
        this.commentedBy = json.getString("CommentedBy");
        this.dateCreated = Utils.getDate(json.getString("DateCreated"));
    }
}
