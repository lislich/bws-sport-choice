package de.bws.sessionbeans;

import de.bws.entities.Person;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Person-Datenbankcontroller
 */
@Local
public interface PersonFacadeLocal {

    void create(Person person);

    void edit(Person person);

    void remove(Person person);

    Person find(Object id);

    List<Person> findAll();

    List<Person> get(String query);
    
}
