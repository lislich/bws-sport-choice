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
    
    @PersistenceContext(unitName = "bws-sport-choice-ejbPU")
    private EntityManager em;
    
    public void addKurs(Kurs p_kurs){
        this.em.persist(p_kurs);
    }
    
    public void removeKurs(Kurs p_kurs){
        Kurs tmp = this.em.find(Kurs.class, p_kurs.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
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
    
    public Kurs find(long p_id){
        return this.em.find(Kurs.class, p_id);        
    }
    
    public List<Kurs> get(String p_query){
        Query qu = this.em.createQuery(p_query, Kurs.class);
        return qu.getResultList();
    }
}
