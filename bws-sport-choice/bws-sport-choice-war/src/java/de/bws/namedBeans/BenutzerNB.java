/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author joshua
 */
@Named(value = "benutzerNB")
@Dependent
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
        this.auswahl = this.alleBenutzer;
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
    
    public void auswaehlen(Rolle p_rolle, Stufe p_stufe){
        if(p_rolle == null){
            this.auswahl = this.alleBenutzer;
        } else {
            this.setAuswahl(new ArrayList<>());

            for(Benutzer b:this.alleBenutzer){
                if(b.getRolle().equals(p_rolle)){
                    if(b.getPerson().getStufe() == p_stufe || p_stufe == null){
                        this.auswahl.add(b);
                    }
                }
            }
        }
    }

    /**
     * @return the auswahl
     */
    public List<Benutzer> getAuswahl() {
        return auswahl;
    }

    /**
     * @param auswahl the auswahl to set
     */
    public void setAuswahl(List<Benutzer> auswahl) {
        this.auswahl = auswahl;
    }
    
}
