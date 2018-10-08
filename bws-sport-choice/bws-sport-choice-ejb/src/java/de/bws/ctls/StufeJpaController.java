/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Stufe;
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
public class StufeJpaController {

    @PersistenceContext
    private EntityManager em;
    
    public void addStufe(Stufe p_stufe){
        this.em.persist(p_stufe);
    }
    
    public void removeStufe(Stufe p_stufe){
        Stufe tmp = this.em.find(Stufe.class, p_stufe.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void updateStufe(Stufe p_stufe){
        Stufe tmp = this.em.find(Stufe.class, p_stufe.getId());
        tmp.setBezeichnung(p_stufe.getBezeichnung());
    }
    
    public Stufe findStufe(long p_id){
        return this.em.find(Stufe.class, p_id);
    }
    
    public List<Stufe> get(String p_query){
        Query qu = this.em.createQuery(p_query, Stufe.class);
        return qu.getResultList();
    }
}
