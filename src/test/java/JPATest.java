import main.AddressBook;
import main.BuddyInfo;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JPATest {

    private AddressBook ab;

    @Before
    public void setUp() throws Exception {
        ab = new AddressBook();
    }

    /**
     * This test persists a BuddyInfo object
     */
    @Test
    public void testPersistBuddy() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("max");
        buddy.setAddress("123 Test Street");
        buddy.setPhoneNumber("613-111-1212");
        buddy.setId(1L);

        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("application");

        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(buddy);

        tx.commit();

        // Querying the contents of the database using JPQL query
        Query q = em.createQuery("SELECT b FROM main.BuddyInfo b");

        @SuppressWarnings("unchecked")
        List<BuddyInfo> results = q.getResultList();

        assertEquals(results.get(0).getName(), "max");

        // Closing connection
        em.close();

        emf.close();
    }

    /**
     * This test creates an addressbook that contains two buddies and persists them
     */
    @Test
    public void testPersistAddressBook() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("max");
        buddy.setAddress("123 Test Street");
        buddy.setPhoneNumber("613-111-1212");
        buddy.setId(1L);

        BuddyInfo buddy2 = new BuddyInfo();
        buddy2.setName("john");
        buddy2.setAddress("123 Test Avenue");
        buddy2.setPhoneNumber("613-111-3333");
        buddy2.setId(2L);

        ab.setId(1L);
        ab.addBuddy(buddy);
        ab.addBuddy(buddy2);

        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("application");

        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(ab);
        em.persist(buddy);
        em.persist(buddy2);

        tx.commit();

        // Querying the contents of the database using JPQL query
        Query q = em.createQuery("SELECT b FROM main.BuddyInfo b");

        @SuppressWarnings("unchecked")
        List<BuddyInfo> results = q.getResultList();

        assertEquals(results.get(0).getName(), "max");
        assertEquals(results.get(1).getName(), "john");

        // Querying the contents of the database using JPQL query
        q = em.createQuery("SELECT a FROM main.AddressBook a");

        @SuppressWarnings("unchecked")
        List<AddressBook> abResults = q.getResultList();

        assertEquals(abResults.get(0).size(), 2);

        // Closing connection
        em.close();

        emf.close();
    }
}
