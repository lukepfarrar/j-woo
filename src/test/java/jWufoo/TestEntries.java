package jWufoo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONException;

public class TestEntries extends TestBase {
	
	public void testGetFormEntries() throws IOException, JSONException, ParseException {
		Form form = api.getForms().get(0);
		ArrayList<Entry> entries = form.getEntries();
		assertEquals(3, entries.size());
		assertEquals("Robert Smith", entries.get(0).fields.get("Field1"));
	}

	public void testGetFormEntriesSorting() {
		Form form = api.getForms().get(0);
		ArrayList<Entry> entries = form.getEntries("EntryID", "DESC");
		assertEquals("Robert Smith", entries.get(0).fields.get("Field1"));
		entries = form.getEntries("EntryID", "ASC");
		assertEquals("Mark Ransom", entries.get(0).fields.get("Field1"));
	}

	public void testGetFormEntriesPaging() {
		Form form = api.getForms().get(0);
		ArrayList<Entry>entries2 = form.getEntries(2,2);
		assertEquals(1, entries2.size());
		ArrayList<Entry> entries1 = form.getEntries(0,2);
		assertEquals(2, entries1.size());
	}

	public void testSearchFormEntriesByComment() {
		Form form = api.getForms().get(0);
		Field commentsField = form.getField("Comments");
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter(commentsField, Filter.OPERATOR.Contains, "Test"));
		ArrayList<Entry> entries = form.searchEntries(filters);
		assertEquals(2, entries.size());
	}
	
	public void testSearchFormEntriesByEmail() {
		Form form = api.getForms().get(0);
		Field commentsField = form.getField("Email");
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter(commentsField, Filter.OPERATOR.Contains, "gmail"));
		ArrayList<Entry> entries = form.searchEntries(filters);
		assertEquals(1, entries.size());
	}

	public void testAddEntry() {
		Form form = api.getForms().get(1);
		Entry entry = new Entry();
		entry.fields.put(form.getField("Title").id, "Test Entry");
		entry.fields.put(form.getField("Details").id, "This is a new test entry");
		entry.fields.put(form.getField("Type").id, "Active");
		entry.fields.put(form.getField("Cost").id, 123456);
		form.addEntry(entry);
		assertFalse(0 == entry.entryId);
	}
	
	public void testAddEntryFail() {
		Form form = api.getForms().get(1);
		Entry entry = new Entry();
		entry.fields.put(form.getField("Details").id, "This is a new test entry");
		entry.fields.put(form.getField("Type").id, "Active");
		entry.fields.put(form.getField("Cost").id, 123456);
		Hashtable<String, String> errors = form.addEntry(entry);
		assertEquals(1, errors.size());
		assertEquals("This field is required. Please enter a value.", errors.get("Field1"));
	}
}
