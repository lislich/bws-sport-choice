package de.bws.sessionbeans;

import de.bws.ctls.PersonJpaController;
import de.bws.entities.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Person.
 */
@Stateless
public class PersonFacade implements PersonFacadeLocal {

    // Datenbankcontroller für die Entität Person
    @Inject
    private PersonJpaController ctrl;

    /**
     * @author Joshua
     * @param person Person
     * 
     * Diese Methode gibt eine Person an den Datenbankcontroller weiter, die der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Person person) {
        this.ctrl.addPerson(person);
    }

    /**
     * @author Joshua
     * @param person Person
     * 
     * Diese Methode gibt eine Person an den Datenbankcontroller weiter, die in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Person person) {
        this.ctrl.updatePerson(person);
    }

    /**
     * @author Joshua
     * @param person Person
     * 
     * Diese Methode gibt eine Person an den Datenbankcontroller weiter, die aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Person person) {
        this.ctrl.removePerson(person);
    }

    /**
     * @author Joshua
     * @param id Person-ID
     * @return Person aus Datenbank
     * 
     * Diese Methode gibt die ID eine Person an den Datenbankcontroller weiter und erwartet die dazugehörigen Person aus der Datenbank.
     */
    @Override
    public Person find(Object id) {
        return this.ctrl.findPerson((long) id);
    }

    /**
     * @author Joshua
     * @return Liste mit Personen
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Personen die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Person> findAll() {
        return this.ctrl.get("SELECT p FROM Person p");
    }

    /**
     * @author Joshua
     * @param query Abfrage als String
     * @return  Liste mit Personen
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Personen.
     */
    @Override
    public List<Person> get(String query) {
        return this.ctrl.get(query);
    }
    
}
