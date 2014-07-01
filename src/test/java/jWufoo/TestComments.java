package jWufoo;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestComments extends TestBase {

    @Test
    public void testGetFormComments() {
        List<Comment> comments = api.getForms().get(0).getComments();
        assertEquals(3, comments.size());
    }

    @Test
    public void testGetEntryComments() {
        List<Comment> comments = api.getForms().get(0).getEntries().get(2).getComments();
        assertEquals(2, comments.size());
    }
}
