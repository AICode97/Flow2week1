package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import entities.PersonDTO;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("Person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPeople(){
        List<PersonDTO> dto = new ArrayList();
        for(Person p : FACADE.getAllPeople()){
            dto.add(new PersonDTO(p));
        }
        return new Gson().toJson(dto);
    }
    
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getSingle(@PathParam("id") int id){
        PersonDTO dto = new PersonDTO(FACADE.getPerson(id));
        return new Gson().toJson(dto);
    }
    
    @Path("/delete/{id}")
    @PUT
    public String deletePerson(@PathParam("id")int id){
        return new Gson().toJson(FACADE.deletePerson(id));
    }
    
    @Path("/add/{fName}/{lName}/{phone}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(@PathParam("fName")String fName, @PathParam("lName")String lName, @PathParam("phone")String phone){
        PersonDTO dto = new PersonDTO(FACADE.addPerson(fName, lName, phone));
        return new Gson().toJson(dto);
    }
    
    
    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(new Person("Artem", "Ivanov", "50351137"));
            em.persist(new Person("Jens", "jenssen", "1234"));
          
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        
    }


}
