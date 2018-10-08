/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.BenutzerJpaController;
import de.bws.entities.Benutzer;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class BenutzerFacade implements BenutzerFacadeLocal {

    @Inject
    private BenutzerJpaController ctrl;

    public BenutzerFacade() {
        
    }

    @Override
    public List<Benutzer> get(String query) {
        return this.ctrl.get(query);
    }

    @Override
    public void create(Benutzer benutzer) {
        this.ctrl.addBenutzer(benutzer);
    }

    @Override
    public void edit(Benutzer benutzer) {
        this.ctrl.updateBenutzer(benutzer);
    }

    @Override
    public void remove(Benutzer benutzer) {
        this.ctrl.removeBenutzer(benutzer);
    }

    @Override
    public Benutzer find(Object id) {
        return this.ctrl.findBenutzer((long) id);
    }

    @Override
    public List<Benutzer> findAll() {
        return this.ctrl.get("SELECT b FROM benutzer b");
    }
    
}
