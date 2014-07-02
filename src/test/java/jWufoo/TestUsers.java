package jWufoo;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUsers extends TestBase {

    @Test
    public void testGetUsers() {
        List<User> users = api.getUsers();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals(api.account, user.getUser());
        assertTrue(user.getIsAccountOwner());
        assertEquals("bangordevelopment@thebookpeople.co.uk", user.getEmail());
    }

    @Test
    public void testGetUserImages() {
        List<User> users = api.getUsers();
        User user = users.get(0);
        assertEquals("https://wufoo.com/images/avatars/big/animal_10.png", user.getImageUrlBig());
        assertEquals("https://wufoo.com/images/avatars/small/animal_10.png", user.getImageUrlSmall());
    }
}
