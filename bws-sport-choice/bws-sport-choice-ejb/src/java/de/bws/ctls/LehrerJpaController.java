/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Lehrer;
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
public class LehrerJpaController {

    @PersistenceContext
    private EntityManager em;
    
    public void addLehrer(Lehrer p_lehrer){
        this.em.persist(p_lehrer);
    }
    
    public void removeLehrer(Lehrer p_lehrer){
        Lehrer tmp = this.em.find(Lehrer.class, p_lehrer.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void upadateLehrer(Lehrer p_lehrerAlt, Lehrer p_lehrerUpdate){
        Lehrer tmp = this.em.find(Lehrer.class, p_lehrerAlt.getId());
        tmp.setKuerzel(p_lehrerUpdate.getKuerzel());
        tmp.setNachname(p_lehrerUpdate.getNachname());
        tmp.setVorname(p_lehrerUpdate.getVorname());
    }
    
    public Lehrer findLehrer(long p_id){
        return this.em.find(Lehrer.class, p_id);
    }
    
    public List<Lehrer> get(String p_query){
        Query qu = this.em.createQuery(p_query);
        return qu.getResultList();
    }
}
