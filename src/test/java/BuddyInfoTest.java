import static org.junit.Assert.*;

import main.BuddyInfo;
import org.junit.Before;
import org.junit.Test;

public class BuddyInfoTest {
    private BuddyInfo b;
    private BuddyInfo b2;

    @Before
    public void setUp() throws Exception {
        b = new BuddyInfo("Max", "Street", "613");
    }

    @Test
    public void testBuddyInfo() {
        assertNotEquals(b, null);
    }

    @Test
    public void testBuddyInfoCopy() {
        b2 = new BuddyInfo(b);
        assertEquals(b.getName(), b2.getName());
        assertEquals(b.getAddress(), b2.getAddress());
        assertEquals(b.getPhoneNumber(), b2.getPhoneNumber());
    }

    @Test
    public void testSetAge() {
        b.setAge(10);
        assertEquals(b.getAge(), 10);
    }

    @Test
    public void testIsOver18() {
        assertFalse(b.isOver18());
        b.setAge(20);
        assertTrue(b.isOver18());
    }
    @Test
    public void testGreeting() {
        assertEquals(b.greeting(), "Hey Max! How are you?");
    }

    @Test
    public void testGetName() {
        assertEquals(b.getName(), "Max");
    }

    @Test
    public void testSetName() {
        b.setName("John");
        assertEquals(b.getName(), "John");
    }

    @Test
    public void testGetAddress() {
        assertEquals(b.getAddress(), "Street");
    }

    @Test
    public void testSetAddress() {
        b.setAddress("Road");
        assertEquals(b.getAddress(), "Road");
    }

    @Test
    public void testGetPhoneNumber() {
        assertEquals(b.getPhoneNumber(), "613");
    }

    @Test
    public void testSetPhoneNumber() {
        b.setPhoneNumber("905");
        assertEquals(b.getPhoneNumber(), "905");
    }

    @Test
    public void testToString() {
        assertEquals(b.toString(), "Max Street 613");
    }

}
