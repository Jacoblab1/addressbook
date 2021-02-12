import static org.junit.Assert.*;

import main.AddressBook;
import main.BuddyInfo;
import org.junit.Before;
import org.junit.Test;

public class AddressBookTest {

    private AddressBook ab;
    private BuddyInfo b1;
    private BuddyInfo b2;

    @Before
    public void setUp() throws Exception {
        ab = new AddressBook();
        b1 = new BuddyInfo("Max", "Street", "613");
        b2 = new BuddyInfo("John", "Road", "905");
    }

    @Test
    public void testAddBuddy() {
        assertEquals(ab.size(), 0);
        ab.addBuddy(b1);
        assertEquals(ab.size(), 1);
    }

    @Test
    public void testRemoveBuddy() {
        ab.removeBuddy(0);
        assertEquals(ab.size(), 0);
    }

    @Test
    public void testSize() {
        ab.addBuddy(b1);
        ab.addBuddy(b2);
        assertEquals(ab.size(), 2);

    }

    @Test
    public void testClear() {
        ab.addBuddy(b1);
        ab.addBuddy(b2);
        ab.clear();
        assertEquals(ab.size(), 0);
    }
}