/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.entities.Lehrer;
import de.bws.entities.Person;
import de.bws.entities.Schueler;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author joshua
 */
@Named("benutzerAendernNB")
@ViewScoped
public class BenutzerAendernNB implements Serializable{
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    @EJB
    private LehrerFacadeLocal lehrerBean;
    
    @EJB
    private PersonFacadeLocal personBean;
    
    private Benutzer benutzer;
    private String error;
    
    @PostConstruct
    private void init(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        this.benutzer = (Benutzer) sessionMap.get("gewaehlterBenutzer");
        if(this.benutzer == null){
            this.benutzer = new Benutzer(); 
            this.error = "Beim Laden des Benutzers ist ein Fehler aufgetreten.";
        } else {
            this.benutzer = (Benutzer) sessionMap.get("gewaehlterBenutzer");
            sessionMap.put("gewaehlterBenutzer", null);
        }
        if(this.benutzer.getPerson() == null){
            this.benutzer.setPerson(new Person());
        }
        
        if(sessionMap.get("lastError") != null || ((String)sessionMap.get("lastError")).length() == 0){
            this.error = (String) sessionMap.get("lastError");
            sessionMap.put("lastError", "");
        }
        
    }

    public String aenderungenSpeichern(){
        System.out.println("Änderungen speichern");
        if(this.benutzer != null){
            if(this.benutzer.getPerson() != null){
                this.personBean.edit(this.benutzer.getPerson());
            }
            System.out.println("Ändern: " + benutzer.getBenutzername());
            this.benutzerBean.edit(benutzer);
        } else {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("lastError", "Beim Aktualisieren der Benutzerdaten ist ein Fehler aufgetreten.");
            return "benutzerVerwalten";
        }
        return "benutzerVerwalten";
    }
    
    public boolean isSchueler(){
        return this.benutzer.getPerson() instanceof Schueler;
    }
    
    public boolean isLehrer(){
        return this.benutzer.getPerson() instanceof Lehrer;
    }
    
    public List<Lehrer> getTutoren(){
        return this.lehrerBean.findAll();
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
