/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Wahlzeitraum;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lisa
 */
@Stateless
@LocalBean
public class WahlzeitraumJpaController {
    
    @PersistenceContext
    private EntityManager em;
    
    public void addWahlzeitraum(Wahlzeitraum p_wahlzeitraum){
        this.em.persist(p_wahlzeitraum);
    }
    
    public void removeWahlzeitraum(Wahlzeitraum p_wahlzeitraum){
        Wahlzeitraum tmp = this.em.find(Wahlzeitraum.class, p_wahlzeitraum.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void updateWahlzeitraum(Wahlzeitraum p_wahlzeitraum){
        Wahlzeitraum tmp = this.em.find(Wahlzeitraum.class, p_wahlzeitraum.getId());
        tmp.setBeginn(p_wahlzeitraum.getBeginn());
        tmp.setEnde(p_wahlzeitraum.getEnde());
    }
    
    public Wahlzeitraum find(long p_id){
        return this.em.find(Wahlzeitraum.class, p_id);
    }
    
    public List<Wahlzeitraum> get(String p_query){
        Query qu = this.em.createQuery(p_query, Wahlzeitraum.class);
        return qu.getResultList();
    }
}
