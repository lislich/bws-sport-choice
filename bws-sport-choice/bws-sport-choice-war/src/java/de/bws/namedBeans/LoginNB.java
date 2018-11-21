package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.security.Passwort;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 * Die LoginNB ist die managed Bean für den Login. Sie enthält die Methoden zum 
 * ein- und ausloggen.
 * 
 * @author joshua
 */
@Named(value = "loginNB")
@ViewScoped
public class LoginNB implements Serializable{
    // Schnittstelle zur Datenbank für Entitäten vom Typ Benutzer
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    
    private String passwort;
    private String benutzerName;
    
    /**
     * Dies ist die Methode zum Einloggen. Sie überprüft ob ein Benutzer mit dem 
     * angegeben Benutzernamen bereits existiert. Wenn der Benutzer existiert und 
     * das Passwort übereistimmt wird der Benutzer angemeldet.
     * 
     * @author joshua
     * @return String für die Navigation
     */
    public String login(){
        // Sessionmap in der Fehlermeldungen und der angemeldete Benutzer 
        // gespeichert werden
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        // String für Navigation
        String fuerNavigation;
        // Der Benutzer mit dem angegeben Benutzernamen
        Benutzer benutzer = this.benutzerBean.getByName(getBenutzerName());
        // prüft ob der Benutzer aus der Datenbank geholt wurde
        if(benutzer != null){
            try {
                // Prüft ob das eingegebene Passwort mit dem gespeicherten übereinstimmt.
                if(Passwort.pruefen(benutzer, this.getPasswort())){
                    // Der Benutzer wird in die Sessionmap geschrieben und somit eingeloggt.
                    sessionMap.put("benutzer", benutzer);
                    fuerNavigation = "Login";
                } else {
                    sessionMap.put("lastError", "Benutzername und/oder Passwort ungültig");
                    fuerNavigation = "Fehler";
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginNB.class.getName()).log(Level.SEVERE, null, ex);
                sessionMap.put("lastError", "Beim einloggen ist ein Problem aufgetreten. Versuchen Sie es bitte erneut.");
                fuerNavigation = "Fehler";
            }
        } else {
            sessionMap.put("lastError", "Benutzername und/oder Passwort ungültig");
            fuerNavigation = "Fehler";
        }
        return fuerNavigation;
    }
    
    /**
     * Methode zum Ausloggen. 
     * 
     * @author joshua
     * @return 
     */
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Logout";
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
        String error;
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if(sessionMap.get("lastError") != null){
            error = sessionMap.get("lastError").toString();
            sessionMap.remove("lastError");
        } else {
            error = "";
        }
        
        return error;
    }
    
}
