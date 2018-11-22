package de.bws.sessionbeans;

import de.bws.entities.Benutzer;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Benutzer-Datenbankcontroller
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
