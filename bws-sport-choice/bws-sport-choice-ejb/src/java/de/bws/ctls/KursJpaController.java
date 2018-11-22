package de.bws.ctls;

import de.bws.entities.Kurs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Kurs" durch.
 * 
 * @author joshua
 */
@Stateless
public class KursJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext(unitName = "bws-sport-choice-ejbPU")
    private EntityManager em;
    
    /**
     * Schreibt den übergebenen Kurs in die Datenbank
     * 
     * @author joshua
     * @param p_kurs der zu persistierende Benutzer
     */
    public void addKurs(Kurs p_kurs){
        this.em.persist(p_kurs);
    }
    
    /**
     * Löscht einen Kurs aus der Datenbank
     * 
     * @author joshua
     * @param p_kurs 
     */     
    public void removeKurs(Kurs p_kurs){
        Kurs tmp = this.em.find(Kurs.class, p_kurs.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt Änderungen an einem Kurs in die Datenbank.
     * 
     * @author joshua
     * @param p_kurs der zu geänderte Kurs
     */
    public void upadteKurs(Kurs p_kurs){
        Kurs tmp = this.em.find(Kurs.class, p_kurs.getId());
        tmp.setBewertung(p_kurs.getBewertung());
        tmp.setHinweis(p_kurs.getHinweis());
        tmp.setJahr(p_kurs.getJahr());
        tmp.setKuerzel(p_kurs.getKuerzel());
        tmp.setLehrer(p_kurs.getLehrer());
        tmp.setStufe(p_kurs.getStufe());
        tmp.setTeilnehmer(p_kurs.getTeilnehmer());
        tmp.setTeilnehmerzahl(p_kurs.getTeilnehmerzahl());
        tmp.setThema(p_kurs.getThema());
        tmp.setThemengleich(p_kurs.getThemengleich());
        tmp.setTitel(p_kurs.getTitel());
        tmp.setBeschreibung(p_kurs.getBeschreibung());
    }
    
    /**
     * Sucht in der Datenbank nach einem Kurs mit der übergeben ID. Fall ein 
     * Kurs mit diese ID existiert, wird diese zurück gegeben. Andernfalls  gibt
     * die Methode null zurück
     * 
     * @author joshua
     * @param p_id die ID des gesuchten Kurses
     * @return der gefundene Kurs oder null
     */
    public Kurs find(long p_id){
        return this.em.find(Kurs.class, p_id);        
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Benutzer gefunden werden, werden diese zurück gegeben.
     * 
     * @author joshua
     * @param p_query ein JPQL Statement
     * @return Liste mit den gefundenen Benutzern
     */
    public List<Kurs> get(String p_query){
        Query qu = this.em.createQuery(p_query, Kurs.class);
        return qu.getResultList();
    }
}
