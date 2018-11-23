package de.bws.ctls;

import de.bws.entities.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Person" durch.
 * 
 * @author joshua
 */
@Stateless
public class PersonJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt die übergebene Person in die Datenbank
     * 
     * @author joshua
     * @param p_person die zu persistierende Person
     */
    public void addPerson(Person p_person){
        this.em.persist(p_person);
    }
    
    /**
     * Löscht die übergebene Person aus der Datenbank
     * 
     * @author joshua
     * @param p_person die zu löschende Person
     */
    public void removePerson(Person p_person){
        Person tmp = this.em.find(Person.class, p_person.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt Änderungen an einer Person in die Datenabnk
     * 
     * @uthor joshua
     * @param p_person die geänderte Person
     */
    public void updatePerson(Person p_person){
        Person tmp = this.em.find(Person.class, p_person.getId());
        tmp.setNachname(p_person.getNachname());
        tmp.setVorname(p_person.getVorname());
    }
    
    /**
     * Sucht in der Datenbank nach einer Person mit der übergeben ID. Falls eine 
     * Person mit dieser ID existiert, wird diese zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author joshua
     * @param p_id die ID der gesuchten Person
     * @return die gefundene Person oder null
     */
    public Person findPerson(long p_id){
        return this.em.find(Person.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Personen gefunden werden, werden diese zurück gegeben.
     * 
     * @author joshua
     * @param p_query ein JPQL Statement
     * @return die gefundenen Personen, falls vorhanden
     */
    public List<Person> get(String p_query){
        Query qu = this.em.createQuery(p_query, Person.class);
        return qu.getResultList();
    }
}
