package de.bws.sessionbeans;

import de.bws.ctls.ThemaJpaController;
import de.bws.entities.Thema;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Thema.
 */
@Stateless
public class ThemaFacade implements ThemaFacadeLocal {

    // Datenbankcontroller für die Entität Thema
    @Inject
    private ThemaJpaController ctrl;

    /**
     * @author Joshua
     * @param thema Thema
     * 
     * Diese Methode gibt ein Thema an den Datenbankcontroller weiter, das der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Thema thema) {
        this.ctrl.addThema(thema);
    }

    /**
     * @author Joshua
     * @param thema Thema
     * 
     * Diese Methode gibt ein Thema an den Datenbankcontroller weiter, das in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Thema thema) {
        this.ctrl.updateThema(thema);
    }

    /**
     * @author Joshua
     * @param thema Thema
     * 
     * Diese Methode gibt ein Thema an den Datenbankcontroller weiter, das aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Thema thema) {
        this.ctrl.removeThema(thema);
    }

    /**
     * @author Joshua
     * @param id Thema-ID
     * @return Thema aus Datenbank
     * 
     * Diese Methode gibt die ID eines Themas an den Datenbankcontroller weiter und erwartet das dazugehörige Thema aus der Datenbank.
     */
    @Override
    public Thema find(Object id) {
        return this.ctrl.findThema((long) id);
    }

    /**
     * @author Joshua
     * @return Liste mit Themen
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Themen die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Thema> findAll() {
        return this.ctrl.get("SELECT t FROM Thema t");
    }

    /**
     * @author Joshua
     * @param query Abfrage als String
     * @return Liste mit Themen
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Themen.
     */
    @Override
    public List<Thema> get(String query) {
        return this.ctrl.get(query);
    }
    
}
