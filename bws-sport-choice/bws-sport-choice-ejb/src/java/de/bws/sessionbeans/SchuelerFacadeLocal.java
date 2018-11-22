package de.bws.sessionbeans;

import de.bws.entities.Schueler;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Schüler-Datenbankcontroller
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
