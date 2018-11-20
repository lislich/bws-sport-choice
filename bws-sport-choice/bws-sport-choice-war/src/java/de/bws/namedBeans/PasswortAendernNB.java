/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.security.Passwort;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author joshua
 */
@Named("passwortAendernNB")
@ViewScoped
public class PasswortAendernNB implements Serializable{
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    private Benutzer benutzer;
    private String passwortAlt;
    private String passwortNeu;
    private String passwortWiederholung;
    
    private String error;
    
    public PasswortAendernNB(){
        
    }
    
    @PostConstruct
    private void init(){
        this.setBenutzer((Benutzer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer"));
        this.setError((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastError"));
        if(this.error != null && !this.error.equals("")){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "");
        }
    }
    
    public String passwortAendern(){
        try{
            if(this.benutzer != null){

                if(this.benutzer.getPasswort().equals(Passwort.hashen(passwortAlt, this.benutzer.getSalt()))){
                    if(this.passwortNeu != null && this.passwortNeu.length() >= 6){
                        if(this.passwortNeu.equals(this.passwortWiederholung)){
                            this.benutzer.setNeuesPasswort(this.passwortNeu);
                            this.benutzerBean.edit(this.benutzer);
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Die angegebenen Passwörter stimmen nicht überein.");
                            return "passwortAendern";
                        }
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Das Passwort muss mindestens 6 Zeichen haben.");
                        return "passwortAendern";
                    }
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Zugriff verweigert: Das aktuelle Passwort stimmt nicht.");
                    return "passwortAendern";
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Benutzerdaten konnten nicht geladen werden. Bitte melden Sie sich erneut an.");
                    return "passwortAendern";
            }
        } catch (Exception e) {
            Logger.getLogger(BenutzerAnlegenNB.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Beim Ändern des Passworts ist ein Fehler aufgetreten. Versuchen Sie es erneut.");
            return "passwortAendern"; 
        }

        return "geaendert";
    }

    /**
     * @return the benutzer
     */
    public Benutzer getBenutzer() {
        return benutzer;
    }

    /**
     * @param benutzer the benutzer to set
     */
    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    /**
     * @return the passwortEingabe
     */
    public String getPasswortNeu() {
        return passwortNeu;
    }

    /**
     * @param passwortNeu
     */
    public void setPasswortNeu(String passwortNeu) {
        this.passwortNeu = passwortNeu;
    }

    /**
     * @return the passwortWiederholung
     */
    public String getPasswortWiederholung() {
        return passwortWiederholung;
    }

    /**
     * @param passwortWiederholung the passwortWiederholung to set
     */
    public void setPasswortWiederholung(String passwortWiederholung) {
        this.passwortWiederholung = passwortWiederholung;
    }

    /**
     * @return the passwortAlt
     */
    public String getPasswortAlt() {
        return passwortAlt;
    }

    /**
     * @param passwortAlt the passwortAlt to set
     */
    public void setPasswortAlt(String passwortAlt) {
        this.passwortAlt = passwortAlt;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
}
