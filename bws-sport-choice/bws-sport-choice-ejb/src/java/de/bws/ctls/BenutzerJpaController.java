/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.ctls;

import de.bws.entities.Benutzer;
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
public class BenutzerJpaController {

    @PersistenceContext
    private EntityManager em;
    
    public void addBenutzer(Benutzer p_benutzer){
        this.em.persist(p_benutzer);
    }
    
    public void removeBenutzer(Benutzer p_benutzer){
        Benutzer tmp = this.em.find(Benutzer.class, p_benutzer.getId());
        this.em.merge(tmp);
        this.em.remove(tmp);
    }
    
    public void updateBenutzer(Benutzer p_benutzerAlt, Benutzer p_benutzerUpdate){
        Benutzer tmp = this.em.find(Benutzer.class, p_benutzerAlt.getId());
        tmp.setBenutzername(p_benutzerUpdate.getBenutzername());
        tmp.setPasswort(p_benutzerUpdate.getPasswort());
        tmp.setSalt(p_benutzerUpdate.getSalt());
    }
    
    public Benutzer findBenutzer(long p_id){
        return this.em.find(Benutzer.class, p_id);
    }
    
    public List<Benutzer> get(String p_query){
        Query qu = this.em.createQuery(p_query);
        return qu.getResultList();
    }
}
