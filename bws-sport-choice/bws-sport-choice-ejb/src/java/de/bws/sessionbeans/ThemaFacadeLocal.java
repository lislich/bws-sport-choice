/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Thema;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joshua
 */
@Local
public interface ThemaFacadeLocal {

    void create(Thema thema);

    void edit(Thema thema);

    void remove(Thema thema);

    Thema find(Object id);

    List<Thema> findAll();

    List<Thema> findRange(int[] range);

    int count();
    
}
