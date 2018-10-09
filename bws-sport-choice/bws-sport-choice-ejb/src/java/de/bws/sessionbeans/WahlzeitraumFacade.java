/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.ctls.WahlzeitraumJpaController;
import de.bws.entities.Wahlzeitraum;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Lisa
 */
@Stateless
public class WahlzeitraumFacade implements WahlzeitraumFacadeLocal {

  @Inject
    private WahlzeitraumJpaController ctrl;

    public WahlzeitraumFacade() {
        
    }

    @Override
    public void create(Wahlzeitraum wahlzeitraum) {
    this.ctrl.addWahlzeitraum(wahlzeitraum);    
        }

    @Override
    public void edit(Wahlzeitraum wahlzeitraum) {
        this.ctrl.updateWahlzeitraum(wahlzeitraum);
    }

    @Override
    public void remove(Wahlzeitraum wahlzeitraum) {
        this.ctrl.removeWahlzeitraum(wahlzeitraum);
    }

    @Override
    public Wahlzeitraum find(Object id) {
        return this.ctrl.find((long) id);
    }

    @Override
    public List<Wahlzeitraum> findAll() {
        return this.ctrl.get("SELECT wz FROM Wahlzeitraum wz");
    }

    @Override
    public List<Wahlzeitraum> get(String query) {
        return this.ctrl.get(query);
    }

    
   
}
