package facades;

import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getPersonCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
        
    }
    
    @Override
    public Person addPerson(String fName, String lName, String phone){
        EntityManager em = emf.createEntityManager();
        Person p = new Person(fName, lName, phone);
        try{
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        }finally{
            em.close();
        }
    }
    
    @Override
    public Person getPerson(int id){
       EntityManager em = emf.createEntityManager();
       Person p;
       try{
           em.getTransaction().begin();
           p = em.find(Person.class, id);
           em.getTransaction().commit();
       }finally{
           em.close();
       }
       return p;
    }
    
    @Override
    public List<Person> getAllPeople(){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
             return query.getResultList();
        }finally{
            em.close();
        }
    }
    
   @Override 
    public Person deletePerson(int id){
        EntityManager em = emf.createEntityManager();
        Person p;
        try{
            em.getTransaction().begin();
            p = em.find(Person.class, id);
            em.remove(p);
            em.getTransaction().commit();
            return p;
        }finally{
            em.close();
        }
    }


    @Override
    public Person editPerson(Person p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

   
    
    

}
