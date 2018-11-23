package de.bws.ctls;

import de.bws.entities.Wahlzeitraum;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *Führt Datenbankzugriffe durch für die Entity "Wahlzeitraum" durch.
 * 
 * @author Lisa
 */
@Stateless
@LocalBean
public class WahlzeitraumJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt ein Wahlzeitraum in die Datenbank
     * 
     * @author Lisa
     * @param p_wahlzeitraum  der zu peristierende Wahlzeitraum
     */
    public void addWahlzeitraum(Wahlzeitraum p_wahlzeitraum){
        this.em.persist(p_wahlzeitraum);
    }
    
    /**
     * Löscht ein Wahlzeitraum aus der Datenbank
     * 
     * @author Lisa
     * @param p_wahlzeitraum  der zu löschende Wahlzeitraum
     */
    public void removeWahlzeitraum(Wahlzeitraum p_wahlzeitraum){
        Wahlzeitraum tmp = this.em.find(Wahlzeitraum.class, p_wahlzeitraum.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt die Anderungen an einem Wahlzeitraum in die Datenbank
     * 
     * @author Lisa
     * @param p_wahlzeitraum  das geänderte Thema
     */
    public void updateWahlzeitraum(Wahlzeitraum p_wahlzeitraum){
        Wahlzeitraum tmp = this.em.find(Wahlzeitraum.class, p_wahlzeitraum.getId());
        tmp.setBeginn(p_wahlzeitraum.getBeginn());
        tmp.setEnde(p_wahlzeitraum.getEnde());
    }
    
    /**
     * Sucht in der Datenbank nach einem Wahlzeitraum mit der übergeben ID. Falls ein 
     * Wahlzeitraum mit dieser ID existiert, wird diese zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author Lisa
     * @param p_id die ID des gesuchten Wahlzeitraumes
     * @return der gefundene Wahlzeitraum
     */
    public Wahlzeitraum find(long p_id){
        return this.em.find(Wahlzeitraum.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Wahlzeiträume gefunden werden, werden diese zurück gegeben.
     * 
     * @athor Lisa
     * @param p_query ein JPQL Statement
     * @return die gefundenen Wahlzeiträume, falls vorhanden
     */
    public List<Wahlzeitraum> get(String p_query){
        Query qu = this.em.createQuery(p_query, Wahlzeitraum.class);
        return qu.getResultList();
    }
}
