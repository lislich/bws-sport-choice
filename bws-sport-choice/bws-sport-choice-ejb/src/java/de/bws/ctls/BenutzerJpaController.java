package de.bws.ctls;

import de.bws.entities.Benutzer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Benutzer" durch.
 *
 * @author joshua
 */
@Stateless
public class BenutzerJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt den übergebenen Benutzer in die Datenbank
     * 
     * @author johsua
     * @param p_benutzer der zu persistierende Benutzer
     */
    public void addBenutzer(Benutzer p_benutzer){
        this.em.persist(p_benutzer);
    }
    
    /**
     * Löscht einen Benutzer aus der Datenbank
     * 
     * @author joshua
     * @param p_benutzer der zu löschende Benutzer
     */
    public void removeBenutzer(Benutzer p_benutzer){
        Benutzer tmp = this.em.find(Benutzer.class, p_benutzer.getId());    
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt Änderungen an einem Benutzer in die Datenbank.
     * 
     * @author joshua
     * @param p_benutzer der zu geänderte Benutzer
     */
    public void updateBenutzer(Benutzer p_benutzer) {
        Benutzer tmp = this.em.find(Benutzer.class, p_benutzer.getId());
        tmp.setBenutzername(p_benutzer.getBenutzername());
        tmp.setPasswort(p_benutzer.getPasswort());
        tmp.setSalt(p_benutzer.getSalt());
        tmp.setRolle(p_benutzer.getRolle());
    }
    
    /**
     * Sucht in der Datenbank nach einem Benutzer mit der übergeben ID. Falls ein 
     * Benutzer mit dieser ID existiert, wird dieser zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author joshua
     * @param p_id die ID des gesuchten Benutzers
     * @return Den gefundenen Benutzer oder null
     */
    public Benutzer findBenutzer(long p_id){
        return this.em.find(Benutzer.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Benutzer gefunden werden, werden diese zurück gegeben.
     * 
     * @author joshua
     * @param p_query ein JPQL Statement
     * @return Liste mit den gefundenen Benutzern
     */
    public List<Benutzer> get(String p_query){
        Query qu = this.em.createQuery(p_query, Benutzer.class);
        return qu.getResultList();
    }
}
