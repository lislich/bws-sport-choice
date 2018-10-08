/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Wahl;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author joshua
 */
@Stateless
@LocalBean
public class WahlJpaController {

    @PersistenceContext
    private EntityManager em;
    
    public void addWahl(Wahl p_wahl){
        this.em.persist(p_wahl);
    }
    
    public void removeWahl(Wahl p_wahl){
        Wahl tmp = this.em.find(Wahl.class, p_wahl.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void updateWahl(Wahl p_wahl){
        Wahl tmp = this.em.find(Wahl.class, p_wahl.getId());
        tmp.setErstwahl(p_wahl.getErstwahl());
        tmp.setZweitwahl(p_wahl.getZweitwahl());
        tmp.setDrittwahl(p_wahl.getDrittwahl());
    }
    
    public Wahl find(long p_id){
        return this.em.find(Wahl.class, p_id);
    }
    
    public List<Wahl> get(String p_query){
        Query qu = this.em.createQuery(p_query, Wahl.class);
        return qu.getResultList();
    }
}
