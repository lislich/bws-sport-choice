/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.BenutzerJpaController;
import de.bws.entities.Benutzer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            this.ctrl.updateBenutzer(benutzer);
        } catch (Exception ex) {
            Logger.getLogger(BenutzerFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        return this.ctrl.get("SELECT b FROM Benutzer b");
    }

    @Override
    public Benutzer getByName(String name) {
        List<Benutzer> benutzer = this.ctrl.get("SELECT b FROM Benutzer b WHERE b.benutzername = '" + name + "'");
        if(benutzer != null && !benutzer.isEmpty()){
            return benutzer.get(0);
        }
        return null;
    }
    
}
