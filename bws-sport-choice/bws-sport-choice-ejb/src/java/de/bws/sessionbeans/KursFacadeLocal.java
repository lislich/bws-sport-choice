package de.bws.sessionbeans;

import de.bws.entities.Kurs;
import java.util.List;
import javax.ejb.Local;

/**
 * @author joshua
 * 
 * Interface für die Schnittstelle zum Kurs-Datenbankcontroller
 */
@Local
public interface KursFacadeLocal {

    void create(Kurs kurs);

    void edit(Kurs kurs);

    void remove(Kurs kurs);

    Kurs find(Object id);

    List<Kurs> findAll();
    
    List<Kurs> get(String query);
    
}
