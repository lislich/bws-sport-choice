/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Benutzer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joshua
 */
@Local
public interface BenutzerFacadeLocal {

    void create(Benutzer benutzer);

    void edit(Benutzer benutzer);

    void remove(Benutzer benutzer);

    Benutzer find(Object id);

    List<Benutzer> findAll();
    
    List<Benutzer> get(String query);
    
    Benutzer getByName(String name);
    
}
