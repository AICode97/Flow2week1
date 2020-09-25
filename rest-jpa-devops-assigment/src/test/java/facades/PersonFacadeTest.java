package facades;

import utils.EMF_Creator;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1,p2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUpClassV2() {
        EntityManager em = emf.createEntityManager();
       facade = PersonFacade.getPersonFacade(emf);
       p1 = new Person("Artem", "Ivanov", "12345");
       p2 = new Person("Alex", "Damja", "54321");
       
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
   @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    /**
     * Test of addPerson method, of class PersonFacade.
     */
    @Test
    public void testAddPerson() {
        System.out.println("addPerson");
        Person p = facade.addPerson("test", "test", "test12");
        assertEquals("test", facade.getPerson(p.getId()).getFirstName());
    }

    /**
     * Test of getPerson method, of class PersonFacade.
     */
    @Test
    public void testGetPerson() {
        System.out.println("getPerson");
        Person result = facade.getPerson(p1.getId());
        assertEquals(p1.getFirstName(), result.getFirstName());
    }

    /**
     * Test of getAllPeople method, of class PersonFacade.
     */
    @Test
    public void testGetAllPeople() {
        System.out.println("getAllPeople");
        List<Person> result = facade.getAllPeople();
        assertEquals(2, result.size());
    }

    /**
     * Test of deletePerson method, of class PersonFacade.
     */
    @Test
    public void testDeletePerson() {
        System.out.println("deletePerson");
        assertEquals("Artem", facade.deletePerson(p1.getId()).getFirstName());
    }

}



