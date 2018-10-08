/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.SchuelerJpaController;
import de.bws.entities.Schueler;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class SchuelerFacade implements SchuelerFacadeLocal {

    @Inject
    private SchuelerJpaController ctrl;

    public SchuelerFacade() {
        
    }

    @Override
    public void create(Schueler schueler) {
        this.ctrl.addSchueler(schueler);
    }

    @Override
    public void edit(Schueler schueler) {
        this.ctrl.updateSchueler(schueler);
    }

    @Override
    public void remove(Schueler schueler) {
        this.ctrl.removeSchueler(schueler);
    }

    @Override
    public Schueler find(Object id) {
        return this.ctrl.findSchueler((long) id);
    }

    @Override
    public List<Schueler> findAll() {
        return this.get("SELECT s FROM Schueler s");
    }

    @Override
    public List<Schueler> get(String query) {
        return this.ctrl.get(query);
    }
    
}
