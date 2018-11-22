package de.bws.sessionbeans;

import de.bws.ctls.WahlzeitraumJpaController;
import de.bws.entities.Wahlzeitraum;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Lisa
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Wahlzeitraum.
 */
@Stateless
public class WahlzeitraumFacade implements WahlzeitraumFacadeLocal {

    // Datenbankcontroller für die Entität Wahlzeitraum
    @Inject
    private WahlzeitraumJpaController ctrl;


    /**
     * @author Lisa
     * @param wahlzeitraum Wahlzeitraum
     * 
     * Diese Methode gibt einen Wahlzeitraum an den Datenbankcontroller weiter, der der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Wahlzeitraum wahlzeitraum) {
    this.ctrl.addWahlzeitraum(wahlzeitraum);    
        }

    /**
     * @author Lisa
     * @param wahlzeitraum Wahlzeitraum
     * 
     * Diese Methode gibt einen Wahlzeitraum an den Datenbankcontroller weiter, der in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Wahlzeitraum wahlzeitraum) {
        this.ctrl.updateWahlzeitraum(wahlzeitraum);
    }

    /**
     * @author Lisa
     * @param wahlzeitraum Wahlzeitraum
     * 
     * Diese Methode gibt einen Wahlzeitraum an den Datenbankcontroller weiter, der aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Wahlzeitraum wahlzeitraum) {
        this.ctrl.removeWahlzeitraum(wahlzeitraum);
    }

    /**
     * @author Lisa
     * @param id Wahlzeitraum-ID
     * @return Wahlzeitraum
     * 
     * Diese Methode gibt die ID eines Wahlzeitraumes an den Datenbankcontroller weiter und erwartet den dazugehörigen Wahlzeitraum aus der Datenbank.
     */
    @Override
    public Wahlzeitraum find(Object id) {
        return this.ctrl.find((long) id);
    }

    /**
     * @author Lisa
     * @return Liste mit Wahlzeiträumen
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Wahlzeiträume die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Wahlzeitraum> findAll() {
        return this.ctrl.get("SELECT wz FROM Wahlzeitraum wz");
    }

    /**
     * @author Lisa
     * @param query Abfrage als String
     * @return Liste mit Wahlzeiträumen
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Wahlzeiträumen.
     */
    @Override
    public List<Wahlzeitraum> get(String query) {
        return this.ctrl.get(query);
    }

    
   
}
