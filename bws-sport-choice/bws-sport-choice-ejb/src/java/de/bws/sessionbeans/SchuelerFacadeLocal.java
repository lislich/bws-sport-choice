/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Schueler;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author joshua
 */
@Local
public interface SchuelerFacadeLocal {

    void create(Schueler schueler);

    void edit(Schueler schueler);

    void remove(Schueler schueler);

    Schueler find(Object id);

    List<Schueler> findAll();

    List<Schueler> get(String query);
    
}
