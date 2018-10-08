/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Kurs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joshua
 */
@Local
public interface KursFacadeLocal {

    void create(Kurs kurs);

    void edit(Kurs kurs);

    void remove(Kurs kurs);

    Kurs find(Object id);

    List<Kurs> findAll();
    
    List<Kurs> get(String query);
    
}
