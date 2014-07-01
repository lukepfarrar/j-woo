package jWufoo;

import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class Widget {

    String name;
    String size;
    String type;
    String typeDesc;
    String hash;
    Report report;

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getTextDesc() {
        return typeDesc;
    }

    public String getHash() {
        return hash;
    }

    public Widget(JSONObject json, Report report) throws JSONException, ParseException {
        name = json.getString("Name");
        size = json.getString("Size");
        type = json.getString("Type");
        typeDesc = json.getString("TypeDesc");
        hash = json.getString("Hash");
        this.report = report;
    }

    public String getEmbedCode() {
        return String.format("<script type=\"text/javascript\">var host = ((\"https:\" == document.location.protocol) ? \"https://\" : \"http://\");document.write(unescape(\"%%3Cscript src=\'\" + host + \"%s.wufoo.com/scripts/widget/embed.js?w=%s\' type=\'text/javascript\'%%3E%%3C/script%%3E\"));</script>", this.report.api.account, this.hash);
    }
}
