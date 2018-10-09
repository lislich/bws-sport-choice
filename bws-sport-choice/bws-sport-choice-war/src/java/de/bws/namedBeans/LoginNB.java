/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.security.Passwort;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author joshua
 */
@Named(value = "loginNB")
@ViewScoped
public class LoginNB implements Serializable{

    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    private String passwort;
    private String benutzerName;
    private String error;
    
    /**
     * Creates a new instance of LoginNB
     */
    public LoginNB() {
        
    }
    
    @PostConstruct
    private void init(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if(sessionMap.get("lastError") != null){
            this.error = sessionMap.get("lastError").toString();
            sessionMap.remove("lastError");
        }
        
        /* Einkommentieren um den Root-Benutzer neu zu erstellen */
        this.createRootUser();
    }
    
    private void createRootUser(){
        Benutzer admin = this.benutzerBean.getByName("");
        this.benutzerBean.remove(admin);
        
        admin = new Benutzer();
        
        try {
            admin.setBenutzername("ChoiceRoot");
            admin.setNeuesPasswort("H444bicht");
        } catch (Exception ex) {
            Logger.getLogger(LoginNB.class.getName()).log(Level.SEVERE, null, ex);
        }
        admin.setRolle(Rolle.ADMIN);
        
        this.benutzerBean.create(admin);
    }
    
    public String login(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        if(this.isNotNullOrEmpty(this.getBenutzerName()) && this.isNotNullOrEmpty(this.getPasswort())){
            Benutzer benutzer = this.benutzerBean.getByName(getBenutzerName());
            if(benutzer != null){
                try {
                    if(Passwort.pruefen(benutzer, this.getPasswort())){
                        sessionMap.put("benutzer", benutzer);
                        return benutzer.getRolle().name();
                    } else {
                        return "Login";
                    }
                } catch (Exception ex) {
                    Logger.getLogger(LoginNB.class.getName()).log(Level.SEVERE, null, ex);
                    sessionMap.put("lastError", "Beim eiloggen ist ein Problem aufgetreten. Versuchen Sie es bitte erneut.");
                    return "Login";
                }
            } else {
                sessionMap.put("lastError", "Benutzername oder Passwort ist falsch.");
                return "Login";
            }
        } else {
            sessionMap.put("lastError", "Benutzername und Passwort müssen angegeben werden.");
            return "Login";
        }
    }
    
    private boolean isNotNullOrEmpty(String p_string){
        return p_string != null && p_string.equals("");
    }

    /**
     * @return the passwort
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * @param passwort the passwort to set
     */
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    /**
     * @return the benutzerName
     */
    public String getBenutzerName() {
        return benutzerName;
    }

    /**
     * @param benutzerName the benutzerName to set
     */
    public void setBenutzerName(String benutzerName) {
        this.benutzerName = benutzerName;
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
