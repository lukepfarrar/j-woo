package jWufoo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.List;
import org.json.JSONException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestForms extends TestBase {

    @Test
    public void testGetFormFields() throws IOException, JSONException, ParseException {
        List<Field> fields = api.getForms().get(0).getFields();
        assertEquals(11, fields.size());
        Field nameField = api.getForms().get(0).getField("Customer");
        assertEquals("Field1", nameField.id);
        Form form = api.getForms().get(0);
        assertEquals("Robert Smith", form.getEntries().get(0).getFields().get(nameField.id));
    }

    @Test
    public void testFormEntriesAndFieldsMatch() {
        Form form = api.getForms().get(0);
        List<Field> fields = form.getFields();
        Entry entry = form.getEntries().get(0);
        for (Field field : fields) {
            System.out.println(field.id);
            if (!field.id.contains("LastUpdated")) {
                assertTrue(entry.fields.containsKey(field.id));
            }
        }
    }

    @Test
    public void testGetForms() {
        List<Form> forms = api.getForms();
        assertEquals(2, forms.size());
        assertEquals("Contact Form", forms.get(0).getName());
        assertEquals("This is my form. Please fill it out. It's awesome!", forms.get(0).getDescription());
        assertTrue(forms.get(0).getIsPublic());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.US);
        try {
            assertEquals(sdf.parse("2000-01-01 12:00:00"), forms.get(0).getStartDate());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals("Contact Form", forms.get(0).getName());
    }

    @Test
    public void testGetFormsWithoutCredentials() {
        JWufooAPI bad_api = new JWufooAPI("", "");
        List<Form> forms = bad_api.getForms();
        assertEquals(0, forms.size());
    }

    @Test
    public void testGetLinkUrl() {
        Form form = api.getForms().get(1);
        assertEquals("http://apprabbit.wufoo.com/forms/m7x3k1/", form.getLinkUrl());
    }

    @Test
    public void testGetEmbedUrl() {
        Form form = api.getForms().get(1);
        String embedUrl = "<script type=\"text/javascript\">var host = ((\"https:\" == document.location.protocol) ? \"https://secure.\" : \"http://\");document.write(unescape(\"%3Cscript src='\" + host + \"wufoo.com/scripts/embed/form.js' type='text/javascript'%3E%3C/script%3E\"));</script>\n<script type=\"text/javascript\">\nvar m7x3k1 = new WufooForm();\nm7x3k1.initialize({\n'userName':'apprabbit',\n'formHash':'m7x3k1', \n'autoResize':true,\n'height':'607'});\nm7x3k1.display();\n</script>";
        assertEquals(embedUrl, form.getEmbedUrl());
    }

    @Test
    public void testGetIframeUrl() {
        Form form = api.getForms().get(1);
        String embedUrl = "<iframe height=\"607\" allowTransparency=\"true\" frameborder=\"0\" scrolling=\"no\" style=\"width:100%;border:none\"  src=\"http://apprabbit.wufoo.com/embed/m7x3k1/\"><a href=\"http://apprabbit.wufoo.com/forms/m7x3k1/\" title=\"Order Form\" rel=\"nofollow\">Fill out my Wufoo form!</a></iframe>";
        assertEquals(embedUrl, form.getIframeEmbedUrl());
    }
}
