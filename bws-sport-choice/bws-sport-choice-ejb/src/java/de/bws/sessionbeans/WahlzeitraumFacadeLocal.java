/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.sessionbeans;

import de.bws.entities.Wahlzeitraum;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lisa
 */
@Local
public interface WahlzeitraumFacadeLocal {

    void create(Wahlzeitraum wahlzeitraum);

    void edit(Wahlzeitraum wahlzeitraum);

    void remove(Wahlzeitraum wahlzeitraum);

    Wahlzeitraum find(Object id);

    List<Wahlzeitraum> findAll();

    List<Wahlzeitraum> get(String query);
    
}
