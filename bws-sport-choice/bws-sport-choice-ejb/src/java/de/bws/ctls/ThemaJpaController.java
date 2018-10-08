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
 *
 * @author joshua
 */
@Stateless
public class ThemaJpaController {

    @PersistenceContext
    private EntityManager em;
    
    public void addThema(Thema p_thema){
        this.em.persist(p_thema);
    }
    
    public void removeThema(Thema p_thema){
        Thema tmp = this.em.find(Thema.class, p_thema.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void updateThema(Thema p_thema){
        Thema tmp = this.em.find(Thema.class, p_thema.getId());
        tmp.setAnteil(p_thema.getAnteil());
        tmp.setBeschreibung(p_thema.getBeschreibung());
    }
    
    public Thema findThema(long p_id){
        return this.em.find(Thema.class, p_id);
    }
    
    public List<Thema> get(String p_query){
        Query qu = this.em.createQuery(p_query, Thema.class);
        return qu.getResultList();
    }
}
