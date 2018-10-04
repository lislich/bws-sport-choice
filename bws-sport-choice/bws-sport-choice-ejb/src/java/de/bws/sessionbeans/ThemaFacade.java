/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Thema;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joshua
 */
@Stateless
public class ThemaFacade extends AbstractFacade<Thema> implements ThemaFacadeLocal {

    @PersistenceContext(unitName = "bws-sport-choice-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ThemaFacade() {
        super(Thema.class);
    }
    
}
