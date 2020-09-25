package facades;

import Exceptions.PersonNotFoundException;
import entities.Person;
import java.util.List;


public interface IPersonFacade {

    public Person addPerson(String fName, String lName, String phone);

    public Person deletePerson(int id) throws PersonNotFoundException;

    public Person getPerson(int id) throws PersonNotFoundException;

    public List<Person> getAllPeople();

    public Person editPerson(Person p) throws PersonNotFoundException;

}
