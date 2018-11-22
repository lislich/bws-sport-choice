/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Thema;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Führt Datenbankzugriffe durch für die Entity "Thema" durch.
 * 
 * @author joshua
 */
@Stateless
public class ThemaJpaController {
    // Schnittstelle zum Persistence Context
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Schreibt ein Thema in die Datenbank
     * 
     * @author joshua
     * @param p_thema das zu peristierende Thema
     */
    public void addThema(Thema p_thema){
        this.em.persist(p_thema);
    }
    
    /**
     * Löscht ein Thema aus der Datenbank
     * 
     * @author joshua
     * @param p_thema das zu löschende Thema
     */
    public void removeThema(Thema p_thema){
        Thema tmp = this.em.find(Thema.class, p_thema.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    /**
     * Schreibt die Anderungen an einem Thema in die Datenbank
     * 
     * @author joshua
     * @param p_thema das geänderte Thema
     */
    public void updateThema(Thema p_thema){
        Thema tmp = this.em.find(Thema.class, p_thema.getId());
        tmp.setAnteil(p_thema.getAnteil());
        tmp.setBezeichnung(p_thema.getBezeichnung());
        tmp.setSchwerpunkt(p_thema.getSchwerpunkt());
    }
    
    /**
     * Sucht in der Datenbank nach einem Thema mit der übergeben ID. Fall ein 
     * Thema mit diese ID existiert, wird diese zurück gegeben. Andernfalls 
     * gibt die Methode null zurück.
     * 
     * @author joshua
     * @param p_id die ID des gesuchten Themas
     * @return das gefundene Thema
     */
    public Thema findThema(long p_id){
        return this.em.find(Thema.class, p_id);
    }
    
    /**
     * Führt das Übergebene JPQL (Java Persistence Query Language) Statement aus.
     * Falls dabei Thema gefunden werden, werden diese zurück gegeben.
     * 
     * @athor joshua
     * @param p_query ein JPQL Statement
     * @return die gefundenen Themen, falls vorhanden
     */
    public List<Thema> get(String p_query){
        Query qu = this.em.createQuery(p_query, Thema.class);
        return qu.getResultList();
    }
}
