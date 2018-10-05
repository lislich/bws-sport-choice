/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Kurs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author joshua
 */
@Stateless
public class KursJpaController {
    
    @PersistenceContext
    private EntityManager em;
    
    public void addKurs(Kurs p_kurs){
        this.em.persist(p_kurs);
    }
    
    public void removeKurs(Kurs p_kurs){
        Kurs tmp = this.em.find(Kurs.class, p_kurs.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void upadteKurs(Kurs p_kursAlt, Kurs p_kursUpdate){
        Kurs tmp = this.em.find(Kurs.class, p_kursAlt.getId());
        tmp.setBewertung(p_kursUpdate.getBewertung());
        tmp.setHinweis(p_kursUpdate.getHinweis());
        tmp.setJahr(p_kursUpdate.getJahr());
        tmp.setKuerzel(p_kursUpdate.getKuerzel());
        tmp.setLehrer(p_kursUpdate.getLehrer());
        tmp.setStufe(p_kursUpdate.getStufe());
        tmp.setTeilnehmer(p_kursUpdate.getTeilnehmer());
        tmp.setTeilnehmerzahl(p_kursUpdate.getTeilnehmerzahl());
        tmp.setThema(p_kursUpdate.getThema());
        tmp.setThemengleich(p_kursUpdate.getThemengleich());
        tmp.setTitel(p_kursUpdate.getTitel());
    }
    
    public Kurs find(long p_id){
        return this.em.find(Kurs.class, p_id);        
    }
    
    public List<Kurs> get(String p_query){
        Query qu = this.em.createQuery(p_query);
        return qu.getResultList();
    }
}
