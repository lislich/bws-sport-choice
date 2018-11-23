package de.bws.namedBeans;

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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 * Die ManagedBean zum Ändern des Passworts des angemeldeten Benutzers.
 * 
 * @author joshua
 */
@Named("passwortAendernNB")
@ViewScoped
public class PasswortAendernNB implements Serializable{
    // Schnittstelle zur Datanbank für Entities vom Typ Benutzer
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    // Der angemeldete Benutzer
    private Benutzer benutzer;
    
    // Diese Attribute sind mit den Eingabefeldern auf der Oberfläche verbunden 
    // und nehmen deren Werte entgegen
    private String passwortAlt;
    private String passwortNeu;
    private String passwortWiederholung;
    
// ******************************* Methoden ************************************
    
    /**
     * Holt den aktuell angemeldeten Benutzer.
     * Diese Methode wird mit der Annotation "@PostConstruct" nach dem erzeugen 
     * der ManagedBean aufgerufen.
     * 
     * @author joshua
     */
    @PostConstruct
    private void init(){
        this.setBenutzer((Benutzer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer"));
    }
    
    /**
     * Ändert das Passwort des angemeldeten Benutzers, sofern er das alte Passwort 
     * korrekt eingibt und das neue Passwort in beiden Eigabefeldern übereinstimmt.
     * 
     * @author joshua
     * @return String zur Navigation
     */
    public String passwortAendern(){
        Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        try{
            if(this.benutzer != null){

                if(this.benutzer.getPasswort().equals(Passwort.hashen(passwortAlt, this.benutzer.getSalt()))){
                    if(this.passwortNeu != null && this.passwortNeu.length() >= 6){
                        if(this.passwortNeu.equals(this.passwortWiederholung)){
                            this.benutzer.setNeuesPasswort(this.passwortNeu);
                            this.benutzerBean.edit(this.benutzer);
                        } else {
                            sessionMap.put("lastError", "Die angegebenen Passwörter stimmen nicht überein.");
                            return "passwortAendern";
                        }
                    } else {
                        sessionMap.put("lastError", "Das Passwort muss mindestens 6 Zeichen haben.");
                        return "passwortAendern";
                    }
                } else {
                    sessionMap.put("lastError", "Zugriff verweigert: Das aktuelle Passwort stimmt nicht.");
                    return "passwortAendern";
                }
            } else {
                sessionMap.put("lastError", "Benutzerdaten konnten nicht geladen werden. Bitte melden Sie sich erneut an.");
                    return "passwortAendern";
            }
        } catch (Exception e) {
            Logger.getLogger(BenutzerAnlegenNB.class.getName()).log(Level.SEVERE, null, e);
            sessionMap.put("lastError", "Beim Ändern des Passworts ist ein Fehler aufgetreten. Versuchen Sie es erneut.");
            return "passwortAendern"; 
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogPasswortgeaendert').show(); $('#passwortAendern').attr('disabled', true);");
        return "geaendert";
    }

//*************************** Getter und Setter ********************************   

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
}
