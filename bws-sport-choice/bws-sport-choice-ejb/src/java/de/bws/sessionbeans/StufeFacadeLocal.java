/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Stufe;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joshua
 */
@Local
public interface StufeFacadeLocal {

    void create(Stufe stufe);

    void edit(Stufe stufe);

    void remove(Stufe stufe);

    Stufe find(Object id);

    List<Stufe> findAll();

    List<Stufe> get(String query);
    
}
