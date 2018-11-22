package de.bws.sessionbeans;

import de.bws.entities.Wahl;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Wahl-Datenbankcontroller
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
