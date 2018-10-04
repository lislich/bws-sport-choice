/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Benutzer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joshua
 */
@Stateless
public class BenutzerFacade extends AbstractFacade<Benutzer> implements BenutzerFacadeLocal {

    @PersistenceContext(unitName = "bws-sport-choice-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BenutzerFacade() {
        super(Benutzer.class);
    }
    
}
