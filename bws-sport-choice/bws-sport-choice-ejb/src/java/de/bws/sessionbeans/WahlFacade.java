/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.WahlJpaController;
import de.bws.entities.Wahl;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class WahlFacade implements WahlFacadeLocal {

    @Inject
    private WahlJpaController ctrl;

    public WahlFacade() {
        
    }

    @Override
    public void create(Wahl wahl) {
        this.ctrl.addWahl(wahl);
    }

    @Override
    public void edit(Wahl wahl) {
        this.ctrl.updateWahl(wahl);
    }

    @Override
    public void remove(Wahl wahl) {
        this.ctrl.removeWahl(wahl);
    }

    @Override
    public Wahl find(Object id) {
        return this.ctrl.find((long) id);
    }

    @Override
    public List<Wahl> findAll() {
        return this.ctrl.get("SELECT w FROM Wahl w");
    }

    @Override
    public List<Wahl> get(String query) {
        return this.ctrl.get(query);
    }
    
}
