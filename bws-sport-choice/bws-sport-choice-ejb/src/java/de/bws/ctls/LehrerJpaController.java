package de.bws.ctls;

import de.bws.entities.Lehrer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Lehrer" durch.
 *
 * @author joshua
 */
@Stateless
public class LehrerJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt den übergebenen Lehrer in die Datenbank
     * 
     * @author joshua
     * @param p_lehrer der zu persistierende Lehrer
     */
    public void addLehrer(Lehrer p_lehrer){
        this.em.persist(p_lehrer);
    }
    
    /**
     * Löscht einen Lehrer aus der Datenbank
     * 
     * @author joshua
     * @param p_lehrer der zu löchende Lehrer
     */
    public void removeLehrer(Lehrer p_lehrer){
        Lehrer tmp = this.em.find(Lehrer.class, p_lehrer.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt Änderungen an einem Lehrer in die Datenbank.
     * 
     * @author joshua
     * @param p_lehrer der geänderte Lehrer
     */
    public void upadateLehrer(Lehrer p_lehrer){
        Lehrer tmp = this.em.find(Lehrer.class, p_lehrer.getId());
        tmp.setKuerzel(p_lehrer.getKuerzel());
        tmp.setNachname(p_lehrer.getNachname());
        tmp.setVorname(p_lehrer.getVorname());
    }
    
    /**
     * Sucht in der Datenbank nach einem Lehrer mit der übergeben ID. Fall ein 
     * Lehrer mit diese ID existiert, wird diese zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author joshua
     * @param p_id die ID des gesuchten Lehrers
     * @return der gefundene Lehrer oder null
     */
    public Lehrer findLehrer(long p_id){
        return this.em.find(Lehrer.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Lehrer gefunden werden, werden diese zurück gegeben.
     * 
     * @author joshua
     * @param p_query ein JPQL Statement
     * @return die gefundenen Lehrer, falls vorhanden
     */
    public List<Lehrer> get(String p_query){
        Query qu = this.em.createQuery(p_query, Lehrer.class);
        return qu.getResultList();
    }
}
