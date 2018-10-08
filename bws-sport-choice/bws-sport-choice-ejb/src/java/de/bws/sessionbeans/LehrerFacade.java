/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.LehrerJpaController;
import de.bws.entities.Lehrer;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class LehrerFacade implements LehrerFacadeLocal {

    @Inject
    private LehrerJpaController ctrl;
            
    public LehrerFacade() {
        
    }

    @Override
    public void create(Lehrer lehrer) {
        this.ctrl.addLehrer(lehrer);
    }

    @Override
    public void edit(Lehrer lehrer) {
        this.ctrl.upadateLehrer(lehrer);
    }

    @Override
    public void remove(Lehrer lehrer) {
        this.ctrl.removeLehrer(lehrer);
    }

    @Override
    public Lehrer find(Object id) {
        return this.ctrl.findLehrer((long) id);
    }

    @Override
    public List<Lehrer> findAll() {
        return this.ctrl.get("SELECT l FROM Lehrer l");
    }

    @Override
    public List<Lehrer> get(String query) {
        return this.ctrl.get(query);
    }

}
