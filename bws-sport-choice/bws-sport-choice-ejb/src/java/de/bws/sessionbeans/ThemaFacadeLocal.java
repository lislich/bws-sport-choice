package de.bws.sessionbeans;

import de.bws.entities.Thema;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Thema-Datenbankcontroller
 */
@Local
public interface ThemaFacadeLocal {

    void create(Thema thema);

    void edit(Thema thema);

    void remove(Thema thema);

    Thema find(Object id);

    List<Thema> findAll();

    List<Thema> get(String query);
    
}
