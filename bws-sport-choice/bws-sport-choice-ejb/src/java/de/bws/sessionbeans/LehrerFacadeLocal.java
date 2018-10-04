/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Lehrer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joshua
 */
@Local
public interface LehrerFacadeLocal {

    void create(Lehrer lehrer);

    void edit(Lehrer lehrer);

    void remove(Lehrer lehrer);

    Lehrer find(Object id);

    List<Lehrer> findAll();

    List<Lehrer> findRange(int[] range);

    int count();
    
}
