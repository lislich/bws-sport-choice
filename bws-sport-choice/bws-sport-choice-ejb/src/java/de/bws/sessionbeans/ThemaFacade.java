/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.ThemaJpaController;
import de.bws.entities.Thema;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class ThemaFacade implements ThemaFacadeLocal {

    @Inject
    private ThemaJpaController ctrl;

    public ThemaFacade() {
        
    }

    @Override
    public void create(Thema thema) {
        this.ctrl.addThema(thema);
    }

    @Override
    public void edit(Thema thema) {
        this.ctrl.updateThema(thema);
    }

    @Override
    public void remove(Thema thema) {
        this.ctrl.removeThema(thema);
    }

    @Override
    public Thema find(Object id) {
        return this.ctrl.findThema((long) id);
    }

    @Override
    public List<Thema> findAll() {
        return this.ctrl.get("SELECT t FROM thema t");
    }

    @Override
    public List<Thema> get(String query) {
        return this.ctrl.get(query);
    }
    
}
