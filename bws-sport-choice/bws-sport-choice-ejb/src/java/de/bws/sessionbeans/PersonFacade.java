/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.PersonJpaController;
import de.bws.entities.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class PersonFacade implements PersonFacadeLocal {

    @Inject
    private PersonJpaController ctrl;

    public PersonFacade() {
        
    }

    @Override
    public void create(Person person) {
        this.ctrl.addPerson(person);
    }

    @Override
    public void edit(Person person) {
        this.ctrl.updatePerson(person);
    }

    @Override
    public void remove(Person person) {
        this.ctrl.removePerson(person);
    }

    @Override
    public Person find(Object id) {
        return this.ctrl.findPerson((long) id);
    }

    @Override
    public List<Person> findAll() {
        return this.ctrl.get("SELECT p FROM Person p");
    }

    @Override
    public List<Person> get(String query) {
        return this.ctrl.get(query);
    }
    
}
