/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.StufeJpaController;
import de.bws.entities.Stufe;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class StufeFacade implements StufeFacadeLocal {

    @Inject
    private StufeJpaController ctrl;

    public StufeFacade() {
        
    }

    @Override
    public void create(Stufe stufe) {
        this.ctrl.addStufe(stufe);
    }

    @Override
    public void edit(Stufe stufe) {
        this.ctrl.updateStufe(stufe);
    }

    @Override
    public void remove(Stufe stufe) {
        this.ctrl.removeStufe(stufe);
    }

    @Override
    public Stufe find(Object id) {
        return this.ctrl.findStufe((long) id);
    }

    @Override
    public List<Stufe> findAll() {
        return this.ctrl.get("SELECT s FROM Stufe s");
    }

    @Override
    public List<Stufe> get(String query) {
        return this.ctrl.get(query);
    }
    
}
