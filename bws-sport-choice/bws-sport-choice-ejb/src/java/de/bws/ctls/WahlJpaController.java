package de.bws.ctls;

import de.bws.entities.Wahl;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Wahl" durch.
 * 
 * @author joshua
 */
@Stateless
@LocalBean
public class WahlJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt eine Wahl in die Datenbank
     * 
     * @author joshua
     * @param p_wahl  die zu peristierende Wahl
     */
    public void addWahl(Wahl p_wahl){
        this.em.persist(p_wahl);
    }
    
    /**
     * Löscht eine Wahl aus der Datenbank
     * 
     * @author joshua
     * @param p_wahl die zu löschende Wahl
     */
    public void removeWahl(Wahl p_wahl){
        Wahl tmp = this.em.find(Wahl.class, p_wahl.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt die Anderungen an einer Wahl in die Datenbank
     * 
     * @author joshua
     * @param p_wahl die geänderte Wahl
     */
    public void updateWahl(Wahl p_wahl){
        Wahl tmp = this.em.find(Wahl.class, p_wahl.getId());
        tmp.setErstwahl(p_wahl.getErstwahl());
        tmp.setZweitwahl(p_wahl.getZweitwahl());
        tmp.setDrittwahl(p_wahl.getDrittwahl());
    }
    
    /**
     * Sucht in der Datenbank nach einer Wahl mit der übergeben ID. Falls eine 
     * Wahl mit dieser ID existiert, wird diese zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author joshua
     * @param p_id die ID der gesuchten Wahl
     * @return die gefundene Wahl
     */
    public Wahl find(long p_id){
        return this.em.find(Wahl.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Wahlen gefunden werden, werden diese zurück gegeben.
     * 
     * @athor joshua
     * @param p_query ein JPQL Statement
     * @return die gefundenen Wahlen, falls vorhanden
     */
    public List<Wahl> get(String p_query){
        Query qu = this.em.createQuery(p_query, Wahl.class);
        return qu.getResultList();
    }
}
