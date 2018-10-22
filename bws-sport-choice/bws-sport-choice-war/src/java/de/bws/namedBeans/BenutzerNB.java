/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.data.Eintrag;
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
    
    private List<Eintrag<Benutzer, Boolean>> auswahl;

    /**
     * Creates a new instance of BenutzerNB
     */
    public BenutzerNB() {
    }
    
    @PostConstruct
    private void init(){
        this.auswahl = new ArrayList<>();
        this.setAlleBenutzer(this.benutzerBean.findAll());
        this.setAuswahl(this.alleBenutzer);
    }
    
    public List<Benutzer> getByRolle(String p_rolle){
        if( p_rolle == null || p_rolle.equals("Alle")){
            return this.getAlleBenutzer();
        }
        
        List<Benutzer> nutzer = new ArrayList<>();
        for(Benutzer b:this.getAlleBenutzer()){
            if( b != null && b.getRolle().name().equals(p_rolle)){
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
    
    public void auswaehlen(String p_rolle, Stufe p_stufe){
        List<Benutzer> vorauswahl = this.getByRolle(p_rolle);
        if(p_rolle != null){
            if(p_rolle.equals(Rolle.SCHUELER.name()) && p_stufe != null){
                for(Benutzer b:vorauswahl){
                    if(b.getPerson().getStufe() != p_stufe ){
                        vorauswahl.remove(b);
                    }
                }
            }
        }
        this.setAuswahl(vorauswahl);
    }
    
    public void wahlInvertieren(long p_id){
        System.out.println("invertiere" + p_id);
        for(Eintrag<Benutzer, Boolean> e:this.auswahl){
            if(e.getKey().getId() == p_id){
                e.setValue(!e.getValue());
                break;
            }
        }
    }

    /**
     * @return the auswahl
     */
    public List<Eintrag<Benutzer, Boolean>> getAuswahl() {
        return auswahl;
    }

    /**
     * @param p_auswahl
     */
    public void setAuswahl(List<Benutzer> p_auswahl) {
        this.auswahl.clear();
        for(Benutzer b:p_auswahl){
            this.auswahl.add(new Eintrag(b, false));
        }
    }
    
    
}
