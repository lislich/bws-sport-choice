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
import de.bws.security.Passwort;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

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
    private String benutzername;
    
    @PostConstruct
    private void init(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        this.benutzer = (Benutzer) sessionMap.get("gewaehlterBenutzer");
        if(this.benutzer == null){
            this.benutzer = new Benutzer();
            this.error = "Beim Laden des Benutzers ist ein Fehler aufgetreten.";
        } else {
            this.benutzer = (Benutzer) sessionMap.get("gewaehlterBenutzer");
            this.setBenutzername(this.benutzer.getBenutzername());
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
        if(this.benutzer != null){
            if(this.benutzer.getPerson() != null){
                this.personBean.edit(this.benutzer.getPerson());
            }
            this.benutzerBean.edit(benutzer);
        } else {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("lastError", "Beim Aktualisieren der Benutzerdaten ist ein Fehler aufgetreten.");
            return "benutzerVerwalten";
        }
        return "benutzerVerwalten";
    }
    
    public String passwortZuruecksetzen(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String passwortNeu = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if(this.benutzer != null) {
            try{
                passwortNeu = Passwort.passwortGenerieren();
            } catch (Exception ex) {
                Logger.getLogger(BenutzerAendernNB.class.getName()).log(Level.SEVERE, null, ex);
                sessionMap.put("lastError", "Beim Aktualisieren des Passworts ist ein Fehler aufgetreten. Das Passwort wurde nicht geändert.");
                return "benutzerVerwalten";
            }
            
            if(this.benutzer.setNeuesPasswort(passwortNeu)){
                
                String execute = "$('#pnl').append('<p>Benutzername: ";
                execute += this.benutzername;
                execute += " Passwort: ";
                execute += passwortNeu;
                execute += "</p>')";
        
//                context.execute(execute);
//                context.execute("PF('dialogZuruecksetzen').show();");
            } else {
                sessionMap.put("lastError", "Beim Aktualisieren des Passworts ist ein Fehler aufgetreten. Das Passwort wurde nicht geändert.");
                return "benutzerVerwalten";
            }
        }
        context.execute("PF('dialogZuruecksetzen').show();");
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

    /**
     * @return the benutzername
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * @param benutzername the benutzername to set
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }
    
    
}
