package de.bws.sessionbeans;

import de.bws.ctls.StufeJpaController;
import de.bws.entities.Stufe;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Stufe.
 */
@Stateless
public class StufeFacade implements StufeFacadeLocal {

    // Datenbankcontroller für die Entität Stufe
    @Inject
    private StufeJpaController ctrl;

    /**
     * @author Joshua
     * @param stufe Stufe
     * 
     * Diese Methode gibt eine Stufe an den Datenbankcontroller weiter, die der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Stufe stufe) {
        this.ctrl.addStufe(stufe);
    }

    /**
     * @author Joshua
     * @param stufe Stufe
     * 
     * Diese Methode gibt eine Stufe an den Datenbankcontroller weiter, die in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Stufe stufe) {
        this.ctrl.updateStufe(stufe);
    }

    /**
     * @author Joshua
     * @param stufe Stufe
     * 
     * Diese Methode gibt eine Stufe an den Datenbankcontroller weiter, die aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Stufe stufe) {
        this.ctrl.removeStufe(stufe);
    }
    
    /**
     * @author Joshua
     * @param id Stufe-ID
     * @return Stufe aus Datenbank
     * 
     * Diese Methode gibt die ID einer Stufe an den Datenbankcontroller weiter und erwartet die dazugehörige Stufe aus der Datenbank.
     */
    @Override
    public Stufe find(Object id) {
        return this.ctrl.findStufe((long) id);
    }

    /**
     * @author Joshua
     * @return Liste mit Stufen
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Stufen die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Stufe> findAll() {
        return this.ctrl.get("SELECT s FROM Stufe s");
    }

    /**
     * @author Joshua
     * @param query Abfrage als String
     * @return Liste mit Stufen
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Stufen.
     */
    @Override
    public List<Stufe> get(String query) {
        return this.ctrl.get(query);
    }
    
}
