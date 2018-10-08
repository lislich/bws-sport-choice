/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Kurs;
import de.bws.sessionbeans.KursFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Lisa
 */
@Named("kurslisteBean")
@ViewScoped
public class KurslisteNB implements Serializable {
    @EJB
    private KursFacadeLocal kursBean;
    
    private List<Kurs> alleKurse;
    
    public List<Kurs> getAlleKurse(){
        this.alleKurse = this.kursBean.findAll();
        return this.alleKurse;
    }

    /**
     * @param alleKurse the alleKurse to set
     */
    public void setAlleKurse(List<Kurs> alleKurse) {
        this.alleKurse = alleKurse;
    }
    
    
}
