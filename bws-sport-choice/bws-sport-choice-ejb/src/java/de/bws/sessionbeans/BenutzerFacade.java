package de.bws.sessionbeans;

import de.bws.ctls.BenutzerJpaController;
import de.bws.entities.Benutzer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author joshua
 * 
 * Die BenutzerFacade stellt die Schnittstelle zum Datenbank-Controller dar, für die Entität Benutzer.
 */
@Stateless
public class BenutzerFacade implements BenutzerFacadeLocal {

    // Datenbankcontroller für die Entität Benutzer
    @Inject
    private BenutzerJpaController ctrl;


    /**
     * @author Joshua
     * @param query Abfrage als String
     * @return Liste mit Benutzern
     * 
     * Diese Methode gibt die Abfrage an den Datenbankcontroller weiter und erwartet eine Ergebnisliste.
     */
    @Override
    public List<Benutzer> get(String query) {
        return this.ctrl.get(query);
    }

    /**
     * @author Joshua
     * @param benutzer Benutzer
     * 
     * Diese Methode gibt einen Benutzer an den Datenbankcontroller weiter, der der Datenbank hinzugefügt werden soll.
     */
    @Override
    public void create(Benutzer benutzer) {
        this.ctrl.addBenutzer(benutzer);
    }

    /**
     * @author Joshua
     * @param benutzer Benutzer
     * 
     * Diese Methode gibt einen Benutzer an den Datenbankcontroller weiter, der in der Datenbank aktualisiert werden soll.
     */
    @Override
    public void edit(Benutzer benutzer) {
        try {
            this.ctrl.updateBenutzer(benutzer);
        } catch (Exception ex) {
            Logger.getLogger(BenutzerFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @author Joshua
     * @param benutzer Benutzer
     * 
     * Diese Methode gibt einen Benutzer an den Datenbankcontroller weiter, der aus der Datenbank gelöscht werden soll.
     */
    @Override
    public void remove(Benutzer benutzer) {
        this.ctrl.removeBenutzer(benutzer);
    }

    /**
     * @author Joshua
     * @param id Benutzer-ID
     * @return Benutzer aus Datenbank
     * 
     * Diese Methode gibt die ID eines Benutzers an den Datenbankcontroller weiter und erwartet den dazugehörigen Benutzer aus der Datenbank.
     */
    @Override
    public Benutzer find(Object id) {
        return this.ctrl.findBenutzer((long) id);
    }

    /**
     * @author Joshua
     * @return Liste mit Benutzern
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die alle Benutzer die es in der Datenbank gibt zurückgegeben werden soll.
     */
    @Override
    public List<Benutzer> findAll() {
        return this.ctrl.get("SELECT b FROM Benutzer b");
    }

    /**
     * @author Joshua
     * @param name Benutzername
     * @return Benutzer
     * 
     * Diese Methode gibt eine Abfrage an den Datenbankcontroller weiter, über die der Benutzer der den übergebenen Benutzernamen hat in der Datenbank gefunden werden soll.
     */
    @Override
    public Benutzer getByName(String name) {
        List<Benutzer> benutzer = this.ctrl.get("SELECT b FROM Benutzer b WHERE b.benutzername = '" + name + "'");
        if(benutzer != null && !benutzer.isEmpty()){
            return benutzer.get(0);
        }
        return null;
    }
    
}
