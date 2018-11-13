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
 *  Die named Bean für das Ändern von Benutzerdaten.
 *  Diese stellt Methoden zum Speichern der geänderten Daten un zum Zurücksetzen 
 *  des Passworts zur Verfügung.
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
    
    /**
     * Diese Methode wird mit der Annotation "@PostConstruct" nach dem Konstruieren aufgerufen.
     * Sie holt den ausgewählten Benutzer und die letzte Fehlermeldung.
     * 
     * @Author Joshua
     */
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

    /**
     * Die Änderungen am Benutzer (und der zugehörigen Person) werden in der Datenbank
     * gespeichert.
     * 
     * @Author Joshua
     * @return String für weitere Navigation
     */
    public String aenderungenSpeichern(){ 
        if(this.benutzer != null){
            if(this.benutzer.getPerson() != null){
                this.personBean.edit(this.benutzer.getPerson());
            }
            this.benutzerBean.edit(benutzer);
        } else {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("lastError", "Beim Aktualisieren der Benutzerdaten ist ein Fehler aufgetreten.");
        }
        return "benutzerVerwalten";
    }
    
    /**
     * Diese Methode setzt generiert ein neues, zufälliges Passwort und zeigt dieses einmalig in einem Dialog.
     * 
     * @Author Joshua
     * @return String für weitere Navigation
     */
    public String passwortZuruecksetzen(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String passwortNeu = "";
        RequestContext context = RequestContext.getCurrentInstance();
        
        // Prüft ob ein Benutzer ausgewählt ist.
        if(this.benutzer != null) {
            try{
                // Das passwort wird generiert.
                passwortNeu = Passwort.passwortGenerieren();
            } catch (Exception ex) {
                Logger.getLogger(BenutzerAendernNB.class.getName()).log(Level.SEVERE, null, ex);
                sessionMap.put("lastError", "Beim Aktualisieren des Passworts ist ein Fehler aufgetreten. Das Passwort wurde nicht geändert.");
                return "benutzerVerwalten";
            }
            
            // Wenn das Passwort geändert wurde, wird es einmalig in einem Dialog angezeigt.
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
    
    /**
     * Prüft ob  der ausgewählte Benutzer ein Schüler ist.
     * 
     * @Author Joshua
     * @return true - der ausgewählte Benutzer ist ein Schüler, false der ausgewählte Benutzer ist kein Schüler
     */
    public boolean isSchueler(){
        return this.benutzer.getPerson() instanceof Schueler;
    }
    
    /**
     * Prüft ob  der ausgewählte Benutzer ein Lehrer ist.
     * 
     * @Author Joshua
     * @return true - der ausgewählte Benutzer ist ein Lehrer, false der ausgewählte Benutzer ist kein Lehrer
     */
    public boolean isLehrer(){
        return this.benutzer.getPerson() instanceof Lehrer;
    }
    
    /**
     * Gibt eine Liste aller Lehrer zurück. 
     * (Jeder Lehrer wir als möglicher Tutor gewärtet.)
     * 
     * @Author Joshua
     * @return Liste aller Lehrer
     */
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
