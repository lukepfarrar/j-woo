package jWufoo;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.json.JSONException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestReports extends TestBase {

    @Test
    public void testGetReports() throws IOException, JSONException, ParseException {
        List<Report> reports = api.getReports();
        assertEquals(2, reports.size());
        assertEquals("Untitled Report", reports.get(1).getName());
    }

    @Test
    public void testGetReportEntries() throws IOException, JSONException, ParseException {
        Report report = api.getReports().get(1);
        List<Entry> entries = report.getEntries();
        assertEquals(3, entries.size());
        assertEquals("Mark Ransom", entries.get(0).getFields().get("Field1"));
    }

    @Test
    public void testGetReportFields() throws IOException, JSONException, ParseException {
        Report report = api.getReports().get(0);
        Entry entry = report.getEntries().get(0);

        Field type_field = report.getField("Type");
        assertTrue(entry.getFields().containsKey(type_field.id));
        Field details_field = report.getField("Details");
        assertFalse(entry.getFields().containsKey(details_field.id));
    }

    @Test
    public void testReportEntriesAndFieldsMatch() throws IOException, JSONException, ParseException {
        Report report = api.getReports().get(1);
        List<Field> fields = report.getFields();
        Entry entry = report.getEntries().get(0);
        for (Field field : fields) {
            if (!field.id.contains("LastUpdated")) {
                if (field.getSubFields().size() > 0) {
                    for (SubField subfield : field.getSubFields()) {
                        assertTrue(entry.fields.containsKey(subfield.id));
                    }
                } else {
                    if (field.getIsRequired()) {
                        assertTrue(entry.fields.containsKey(field.id));
                    }
                }
            }
        }
    }

    @Test
    public void testReportWidgetsGetter() throws IOException, JSONException, ParseException {
        Report report = api.getReports().get(1);
        List<Widget> widgets = report.getWidgets();
        assertEquals(1, widgets.size());
        assertEquals("fieldChart", widgets.get(0).getType());
    }

    @Test
    public void testReportWidgetCodeGetter() throws IOException, JSONException, ParseException {
        Report report = api.getReports().get(1);
        Widget widget = report.getWidgets().get(0);
        assertEquals("<script type=\"text/javascript\">var host = ((\"https:\" == document.location.protocol) ? \"https://\" : \"http://\");document.write(unescape(\"%3Cscript src=\'\" + host + \"apprabbit.wufoo.com/scripts/widget/embed.js?w=JlLuLGXrZ8sGfSR6D2lwuslashIQwuBey8dw1CIWDhDIJyLHRJQY=\' type=\'text/javascript\'%3E%3C/script%3E\"));</script>", widget.getEmbedCode());
    }

    @Test
    public void testReportLinkGetter() throws IOException, JSONException, ParseException {
        Report report = api.getReports().get(0);
        assertEquals("https://apprabbit.wufoo.com/reports/m5p8q8/", report.getLink());
    }
}
