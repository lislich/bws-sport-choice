/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Schueler;
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
public class SchuelerJpaController {

    @PersistenceContext
    private EntityManager em;
    
    public void addSchueler(Schueler p_schueler){
        this.em.persist(p_schueler);
    }
    
    public void removeSchueler(Schueler p_schueler){
        Schueler tmp = this.em.find(Schueler.class, p_schueler.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void updateSchueler(Schueler p_schueler){
        Schueler tmp = this.em.find(Schueler.class, p_schueler.getId());
        tmp.setStufe(p_schueler.getStufe());
        tmp.setNachname(p_schueler.getNachname());
        tmp.setVorname(p_schueler.getVorname());
        tmp.setWahl(p_schueler.getWahl());
    }
    
    public Schueler findSchueler(long p_id){
        return this.em.find(Schueler.class, p_id);
    }
    
    public List<Schueler> get(String p_query){
        Query qu = this.em.createQuery(p_query, Schueler.class);
        return qu.getResultList();
    } 
}
