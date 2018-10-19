/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Schueler;
import de.bws.entities.Wahlzeitraum;
import de.bws.sessionbeans.WahlzeitraumFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lisa
 */
@Named(value = "menueNB")
@ViewScoped
public class MenueNB implements Serializable {

    @EJB
    private WahlzeitraumFacadeLocal wahlzeitraumBean;
    
    /**
     * Creates a new instance of MenueNB
     */
    public MenueNB() {
    }
    
    private Benutzer b = (Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
    
    
    public boolean lehrer() {
        boolean tmp = false;
        if (getB() != null) {
            if (getB().getRolle().equals(Rolle.LEHRER)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    
    public boolean schueler(){
        boolean tmp = false;
        if (getB() != null) {
            if (getB().getRolle().equals(Rolle.SCHUELER)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    
    public boolean admin(){
        boolean tmp = false;
        if (getB() != null) {
            if (getB().getRolle().equals(Rolle.ADMIN)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    
    public boolean lehrerOrAdmin(){
        boolean tmp = false;
        if (getB() != null) {
            if (getB().getRolle().equals(Rolle.ADMIN)) {
                tmp = true;
            }
            if (getB().getRolle().equals(Rolle.LEHRER)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    
    public boolean schuelerDarfNichtWaehlen(){
        boolean tmp = false;
        List<Wahlzeitraum> zeitraumListe = this.wahlzeitraumBean.findAll();
        Wahlzeitraum zeitraum = null;
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        
        
        if(zeitraumListe != null && !(zeitraumListe.isEmpty())){
            zeitraum = zeitraumListe.get(0);
        }
        
        if(getB() != null){
            if(getB().getRolle().equals(Rolle.SCHUELER)){
                Schueler s = (Schueler) getB().getPerson();
                if(zeitraum.getBeginn().getTime() > timestamp.getTime() || zeitraum.getEnde().getTime() < timestamp.getTime()){
                        tmp = true;                   
                }
            }
        }
        
        System.out.println(timestamp.getTime());
        System.out.println(zeitraum.getBeginn().getTime());
        return tmp;
    }

    public boolean schuelerDarfWaehlen(){
        boolean tmp = false;
        List<Wahlzeitraum> zeitraumListe = this.wahlzeitraumBean.findAll();
        Wahlzeitraum zeitraum = null;
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        
        if(zeitraumListe != null && !(zeitraumListe.isEmpty())){
            zeitraum = zeitraumListe.get(0);
        }
        
        if(getB() != null){
            if(getB().getRolle().equals(Rolle.SCHUELER)){
                Schueler s = (Schueler) getB().getPerson();
                if(zeitraum.getBeginn().getTime() <= timestamp.getTime() && zeitraum.getEnde().getTime() >= timestamp.getTime()){
                        tmp = true;                   
                }
            }
        }
        return tmp;
    }
    
    /**
     * @return the b
     */
    public Benutzer getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(Benutzer b) {
        this.b = b;
    }
    
    
    
}
