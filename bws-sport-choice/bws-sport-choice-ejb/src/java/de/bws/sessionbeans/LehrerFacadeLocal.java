package de.bws.sessionbeans;

import de.bws.entities.Lehrer;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Lehrer-Datenbankcontroller
 */
@Local
public interface LehrerFacadeLocal {

    void create(Lehrer lehrer);

    void edit(Lehrer lehrer);

    void remove(Lehrer lehrer);

    Lehrer find(Object id);

    List<Lehrer> findAll();

    List<Lehrer> get(String query);
    
}
