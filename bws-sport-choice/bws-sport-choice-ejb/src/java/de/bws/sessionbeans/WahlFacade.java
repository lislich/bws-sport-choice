package de.bws.sessionbeans;

import de.bws.ctls.WahlJpaController;
import de.bws.entities.Wahl;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Wahl.
 */
@Stateless
public class WahlFacade implements WahlFacadeLocal {
    
    // Datenbankcontroller für die Entität Wahl
    @Inject
    private WahlJpaController ctrl;

    /**
     * @author Joshua
     * @param wahl Wahl
     * 
     * Diese Methode gibt eine Wahl an den Datenbankcontroller weiter, die der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Wahl wahl) {
        this.ctrl.addWahl(wahl);
    }

    /**
     * @author Joshua
     * @param wahl Wahl
     * 
     * Diese Methode gibt eine Wahl an den Datenbankcontroller weiter, die in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Wahl wahl) {
        this.ctrl.updateWahl(wahl);
    }

    /**
     * @author Joshua
     * @param wahl Wahl
     * 
     * Diese Methode gibt eine Wahl an den Datenbankcontroller weiter, die aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Wahl wahl) {
        this.ctrl.removeWahl(wahl);
    }

    /**
     * @author Joshua
     * @param id Wahl-ID
     * @return Wahl aus Datenbank
     * 
     * Diese Methode gibt die ID einer Wahl an den Datenbankcontroller weiter und erwartet die dazugehörige Wahl aus der Datenbank.
     */
    @Override
    public Wahl find(Object id) {
        return this.ctrl.find((long) id);
    }

    /**
     * @author Joshua
     * @return Liste mit Wahlen
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Wahlen die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Wahl> findAll() {
        return this.ctrl.get("SELECT w FROM Wahl w");
    }

    /**
     * @author Joshua
     * @param query Abfrage als String
     * @return Liste mit Wahlen
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Wahlen.
     */
    @Override
    public List<Wahl> get(String query) {
        return this.ctrl.get(query);
    }
    
}
