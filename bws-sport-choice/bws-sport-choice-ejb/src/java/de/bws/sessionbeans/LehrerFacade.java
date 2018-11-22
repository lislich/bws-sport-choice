package de.bws.sessionbeans;

import de.bws.ctls.LehrerJpaController;
import de.bws.entities.Lehrer;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Lehrer.
 */
@Stateless
public class LehrerFacade implements LehrerFacadeLocal {

    // Datenbankcontroller für die Entität Lehrer
    @Inject
    private LehrerJpaController ctrl;

    /**
     * @author Joshua
     * @param lehrer Lehrer
     * 
     * Diese Methode gibt einen Lehrer an den Datenbankcontroller weiter, der der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Lehrer lehrer) {
        this.ctrl.addLehrer(lehrer);
    }

    /**
     * @author Joshua
     * @param lehrer Lehrer
     * 
     * Diese Methode gibt einen Lehrer an den Datenbankcontroller weiter, der in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Lehrer lehrer) {
        this.ctrl.upadateLehrer(lehrer);
    }

    /**
     * @author Joshua
     * @param lehrer Lehrer
     * 
     * Diese Methode gibt einen Lehrer an den Datenbankcontroller weiter, der aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Lehrer lehrer) {
        this.ctrl.removeLehrer(lehrer);
    }

    /**
     * @author Joshua
     * @param id Lehrer-ID
     * @return Lehrer aus Datenbank
     * 
     * Diese Methode gibt die ID eines Lehrers an den Datenbankcontroller weiter und erwartet den dazugehörigen Lehrer aus der Datenbank.
     */
    @Override
    public Lehrer find(Object id) {
        return this.ctrl.findLehrer((long) id);
    }

    /**
     * @author Joshua
     * @return Liste mit Lehrern
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Lehrer die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Lehrer> findAll() {
        return this.ctrl.get("SELECT l FROM Lehrer l");
    }

    /**
     * @author Joshua
     * @param query Abfrage als String
     * @return Liste mit Lehrern
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Lehrern.
     */
    @Override
    public List<Lehrer> get(String query) {
        return this.ctrl.get(query);
    }

}
