/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Stufe;
import de.bws.sessionbeans.StufeFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lisa
 */
@Named(value = "stufeNB")
@ViewScoped
public class StufeNB implements Serializable{

    @EJB
    private StufeFacadeLocal stufeBean;
    /**
     * Creates a new instance of StufeNB
     */
    public StufeNB() {
    }
    
    private List<Stufe> stufen;

    public List<Stufe> getAlleStufen(){
        this.stufen = this.stufeBean.findAll();
        return this.stufen;
    }
    
    /**
     * @return the stufen
     */
    public List<Stufe> getStufen() {
        return stufen;
    }

    /**
     * @param stufen the stufen to set
     */
    public void setStufen(List<Stufe> stufen) {
        this.stufen = stufen;
    }
    
    
}
