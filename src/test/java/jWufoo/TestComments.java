package jWufoo;

import java.util.List;

public class TestComments extends TestBase {

	public void testGetFormComments() {
		List<Comment> comments = api.getForms().get(0).getComments();
		assertEquals(3, comments.size());
	}
	
	public void testGetEntryComments() {
		List<Comment> comments = api.getForms().get(0).getEntries().get(2).getComments();
		assertEquals(2, comments.size());
	}
}
