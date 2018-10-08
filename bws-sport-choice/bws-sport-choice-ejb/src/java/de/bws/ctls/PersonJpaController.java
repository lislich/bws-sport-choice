/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author joshua
 */
@Stateless
public class PersonJpaController {

    @PersistenceContext
    private EntityManager em;
    
    public void addPerson(Person p_person){
        this.em.persist(p_person);
    }
    
    public void removePerson(Person p_person){
        Person tmp = this.em.find(Person.class, p_person.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void updatePerson(Person p_person){
        Person tmp = this.em.find(Person.class, p_person.getId());
        tmp.setNachname(p_person.getNachname());
        tmp.setVorname(p_person.getVorname());
    }
    
    public Person findPerson(long p_id){
        return this.em.find(Person.class, p_id);
    }
    
    public List<Person> get(String p_query){
        Query qu = this.em.createQuery(p_query, Person.class);
        return qu.getResultList();
    }
}
