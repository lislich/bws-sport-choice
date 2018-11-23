package de.bws.ctls;

import de.bws.entities.Stufe;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Stufe" durch.
 * 
 * @author joshua
 */
@Stateless
public class StufeJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt eine Stufe in die Datenbank
     * 
     * @author jsohua
     * @param p_stufe die zu persistierende Stufe
     */
    public void addStufe(Stufe p_stufe){
        this.em.persist(p_stufe);
    }
    
    /**
     * Löscht eine Stufe aus der Datenabnk
     * 
     * @author joshua
     * @param p_stufe die zu löschende Stufe
     */
    public void removeStufe(Stufe p_stufe){
        Stufe tmp = this.em.find(Stufe.class, p_stufe.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt die Änderungen an einer Stufe in die Datenbank
     * 
     * @author joshua
     * @param p_stufe die geänderte Stufe
     */
    public void updateStufe(Stufe p_stufe){
        Stufe tmp = this.em.find(Stufe.class, p_stufe.getId());
        tmp.setBezeichnung(p_stufe.getBezeichnung());
    }
    
    /**
     * Sucht in der Datenbank nach einem Stufe mit der übergeben ID. Falls eine 
     * Stufe mit dieser ID existiert, wird diese zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author joshua
     * @param p_id die ID der gesuchten Stufe
     * @return die gefundene Stufe
     */
    public Stufe findStufe(long p_id){
        return this.em.find(Stufe.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Stufen gefunden werden, werden diese zurück gegeben.
     * 
     * @author joshua
     * @param p_query ein JPQL Statement
     * @return die gefundenen Stufen, falls vorhanden
     */
    public List<Stufe> get(String p_query){
        Query qu = this.em.createQuery(p_query, Stufe.class);
        return qu.getResultList();
    }
}
