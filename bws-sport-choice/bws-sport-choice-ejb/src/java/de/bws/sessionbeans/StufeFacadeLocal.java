package de.bws.sessionbeans;

import de.bws.entities.Stufe;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Stufe-Datenbankcontroller
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
