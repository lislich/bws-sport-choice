/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Wahl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joshua
 */
@Local
public interface WahlFacadeLocal {

    void create(Wahl wahl);

    void edit(Wahl wahl);

    void remove(Wahl wahl);

    Wahl find(Object id);

    List<Wahl> findAll();
    
    List<Wahl> get(String query);
    
}
