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
 * Die LoginNB ist die backing Bean für den Login. Sie enthält die Methoden zum 
 * ein- und ausloggen.
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
    private boolean angemeldet;
    
    /**
     * Erstellt eine neue Instanz von LoginNB
     */
    public LoginNB() {
        
    }
    
    /**
     * 
     * 
     * @return String für die Navigation
     */
    public String login(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        angemeldet = false;

        Benutzer benutzer = this.benutzerBean.getByName(getBenutzerName());
        if(benutzer != null){
            try {
                if(Passwort.pruefen(benutzer, this.getPasswort())){
                    sessionMap.put("benutzer", benutzer);
                    angemeldet = true;
                    return "Login";
                } else {
                    sessionMap.put("lastError", "Benutzername und/oder Passwort ungültig");
                    angemeldet = false;
                      return "Fehler";
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginNB.class.getName()).log(Level.SEVERE, null, ex);
                sessionMap.put("lastError", "Beim einloggen ist ein Problem aufgetreten. Versuchen Sie es bitte erneut.");
                angemeldet = false;
                return "Fehler";
            }
        } else {
            sessionMap.put("lastError", "Benutzername und/oder Passwort ungültig");
            angemeldet = false;
            return "Fehler";
        }
    }
    
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

    /**
     * @return the angemeldet
     */
    public boolean isAngemeldet() {
        return angemeldet;
    }

    /**
     * @param angemeldet the angemeldet to set
     */
    public void setAngemeldet(boolean angemeldet) {
        this.angemeldet = angemeldet;
    }
    
}
