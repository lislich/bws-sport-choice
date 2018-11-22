package de.bws.sessionbeans;

import de.bws.ctls.SchuelerJpaController;
import de.bws.entities.Schueler;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Schüler.
 */
@Stateless
public class SchuelerFacade implements SchuelerFacadeLocal {

    // Datenbankcontroller für die Entität Schüler
    @Inject
    private SchuelerJpaController ctrl;


    /**
     * @author Joshua
     * @param schueler Schüler
     * 
     * Diese Methode gibt einen Schüler an den Datenbankcontroller weiter, der der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Schueler schueler) {
        this.ctrl.addSchueler(schueler);
    }

    /**
     * @author Joshua
     * @param schueler Schüler
     * 
     * Diese Methode gibt einen Schüler an den Datenbankcontroller weiter, der in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Schueler schueler) {
        this.ctrl.updateSchueler(schueler);
    }

    /**
     * @author Joshua
     * @param schueler Schüler
     * 
     * Diese Methode gibt einen Schüler an den Datenbankcontroller weiter, der aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Schueler schueler) {
        this.ctrl.removeSchueler(schueler);
    }

    /**
     * @author Joshua
     * @param id Schüler-ID
     * @return Schüler aus Datenbank
     * 
     * Diese Methode gibt die ID eines Schülers an den Datenbankcontroller weiter und erwartet den dazugehörigen Schüler aus der Datenbank.
     */
    @Override
    public Schueler find(Object id) {
        return this.ctrl.findSchueler((long) id);
    }

    /**
     * @author Joshua
     * @return Liste mit Schülern
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Schüler die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Schueler> findAll() {
        return this.get("SELECT s FROM Schueler s");
    }

    /**
     * @author Joshua
     * @param query Abfrage als String
     * @return Liste mit Schülern
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Schülern.
     */
    @Override
    public List<Schueler> get(String query) {
        return this.ctrl.get(query);
    }
    
}
