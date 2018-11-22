package de.bws.ctls;

import de.bws.entities.Schueler;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Schueler" durch.
 * 
 * @author joshua
 */
@Stateless
public class SchuelerJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt einen Schüler in die Datenbank
     * 
     * @author joshua
     * @param p_schueler der zu persistierende Schüler
     */
    public void addSchueler(Schueler p_schueler){
        this.em.persist(p_schueler);
    }
    
    /**
     * Löscht einen Schüler aus der Datenabnk
     * 
     * @author joshau
     * @param p_schueler der zu löschnde Schüler
     */
    public void removeSchueler(Schueler p_schueler){
        Schueler tmp = this.em.find(Schueler.class, p_schueler.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt Änderungen an einem Schüler in die Datenbank
     * 
     * @author joshua
     * @param p_schueler der geänderte Schüler
     */
    public void updateSchueler(Schueler p_schueler){
        Schueler tmp = this.em.find(Schueler.class, p_schueler.getId());
        tmp.setStufe(p_schueler.getStufe());
        tmp.setNachname(p_schueler.getNachname());
        tmp.setVorname(p_schueler.getVorname());
        tmp.setWahl(p_schueler.getWahl());
    }
    
    /**
     * Sucht in der Datenbank nach einem Schüler mit der übergeben ID. Fall ein 
     * Schüler mit diese ID existiert, wird diese zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author joshua
     * @param p_id die ID des gesuchten Schülers
     * @return den gefundenen Schüler oder null
     */
    public Schueler findSchueler(long p_id){
        return this.em.find(Schueler.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Schüler gefunden werden, werden diese zurück gegeben.
     * 
     * @author joshua 
     * @param p_query ein JPQL Statement
     * @return die gefundenen Schüler, falls vorhanden
     */
    public List<Schueler> get(String p_query){
        Query qu = this.em.createQuery(p_query, Schueler.class);
        return qu.getResultList();
    } 
}
