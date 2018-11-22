package de.bws.sessionbeans;

import de.bws.entities.Wahlzeitraum;
import java.util.List;
import javax.ejb.Local;

/**
 * @author Lisa
 * 
 * Interface für die Schnittstelle zum Wahlzeitraum-Datenbankcontroller
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
