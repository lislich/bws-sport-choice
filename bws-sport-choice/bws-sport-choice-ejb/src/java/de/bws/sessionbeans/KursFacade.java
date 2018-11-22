package de.bws.sessionbeans;

import de.bws.ctls.KursJpaController;
import de.bws.entities.Kurs;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die KursFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Kurs.
 */
@Stateless
public class KursFacade implements KursFacadeLocal {

    // Datenbankcontroller für die Entität Kurs
    @Inject
    private KursJpaController ctrl;

    /**
     * @author joshua
     * @param kurs Kurs
     * 
     * Diese Methode gibt einen Kurs an den Datenbankcontroller weiter, der der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Kurs kurs) {
        this.ctrl.addKurs(kurs);
    }

    /**
     * @author joshua
     * @param kurs Kurs
     * 
     * Diese Methode gibt einen Kurs an den Datenbankcontroller weiter, der in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Kurs kurs) {
        this.ctrl.upadteKurs(kurs);
    }

    /**
     * @author joshua
     * @param kurs Kurs
     * 
     * Diese Methode gibt einen Kurs an den Datenbankcontroller weiter, der aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Kurs kurs) {
        this.ctrl.removeKurs(kurs);
    }

    /**
     * @author joshua
     * @param id Kurs-ID
     * @return Kurs aus Datenbank
     * 
     * Diese Methode gibt die ID eines Kurses an den Datenbankcontroller weiter und erwartet den dazugehörigen Kurs aus der Datenbank.
     */
    @Override
    public Kurs find(Object id) {
        return this.ctrl.find((long) id);
    }

    /**
     * @author joshua
     * @return Liste mit Kursen
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Kurse die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Kurs> findAll() {
        return this.ctrl.get("SELECT k FROM Kurs k");
    }

    /**
     * @author joshua
     * @param query Abfrage als String
     * @return Liste mit Kursen
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste mit Kursen.
     */
    @Override
    public List<Kurs> get(String query) {
        return this.ctrl.get(query);
    }
    
}
