/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.KursJpaController;
import de.bws.entities.Kurs;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Stateless
public class KursFacade implements KursFacadeLocal {

    @Inject
    private KursJpaController ctrl;

    public KursFacade() {

    }

    @Override
    public void create(Kurs kurs) {
        this.ctrl.addKurs(kurs);
    }

    @Override
    public void edit(Kurs kurs) {
        this.ctrl.upadteKurs(kurs);
    }

    @Override
    public void remove(Kurs kurs) {
        this.ctrl.removeKurs(kurs);
    }

    @Override
    public Kurs find(Object id) {
        return this.ctrl.find((long) id);
    }

    @Override
    public List<Kurs> findAll() {
        return this.ctrl.get("SELECT k FROM kurs k");
    }

    @Override
    public List<Kurs> get(String query) {
        return this.ctrl.get(query);
    }
    
}
