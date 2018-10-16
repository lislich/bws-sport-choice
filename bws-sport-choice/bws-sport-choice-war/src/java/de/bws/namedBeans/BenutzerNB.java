/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.entities.Person;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author joshua
 */
@Named(value = "benutzerNB")
@ViewScoped
public class BenutzerNB implements Serializable{
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    private List<Benutzer> alleBenutzer;
    
    private List<Benutzer> auswahl;

    /**
     * Creates a new instance of BenutzerNB
     */
    public BenutzerNB() {
    }
    
    @PostConstruct
    private void init(){
        this.setAlleBenutzer(this.benutzerBean.findAll());
    }
    
    public List<Benutzer> getByRolle(String p_rolle){
        if(p_rolle.equals("Alle")){
            return this.getAlleBenutzer();
        }
        
        List<Benutzer> nutzer = new ArrayList<>();
        for(Benutzer b:this.getAlleBenutzer()){
            if(b.getRolle().name().equals(p_rolle)){
                nutzer.add(b);
            }
        }
        return nutzer;
    }

    /**
     * @return the alleBenutzer
     */
    public List<Benutzer> getAlleBenutzer() {
        return alleBenutzer;
    }

    /**
     * @param alleBenutzer the alleBenutzer to set
     */
    public void setAlleBenutzer(List<Benutzer> alleBenutzer) {
        this.alleBenutzer = alleBenutzer;
    }
    
}
