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
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Named(value = "benutzerVerwaltenNB")
@ViewScoped
public class BenutzerVerwaltenNB implements Serializable{

    @EJB
    private PersonFacadeLocal personBean;
    
    @EJB
    private LehrerFacadeLocal lehrerBean;
    
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
        
    @Inject
    private BenutzerNB benutzerNB;
    
    private final Logger log = Logger.getLogger("BenutzerVerwaltenNB");
    
    private Stufe stufe;
    private String rolle;
    
    private String error;
    
    private List<Eintrag<Benutzer, Boolean>> auswahl;
    
    /** 
     * Creates a new instance of BenutzerVerwaltenNB
     */
    public BenutzerVerwaltenNB() {
        
    }

    
    @PostConstruct
    private void init(){
        this.error = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastError");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "");
        
        this.auswahl = new ArrayList<>();
        this.setAuswahl(this.benutzerBean.findAll());
    }
    
    /**
     * Löscht alle ausgewählten Benutzer
     * @return 
     */
    public String loeschen(){
        for(Benutzer b: this.getAusgewaehlteBenutzer()){
            this.benutzerBean.remove(b);
        }
        return "benutzerVerwalten";
    }
    
    /**
     * Speichert den ausgewählten Benutzer in der SessionMap und leitet den 
     * Benutzer auf die Seite zum Ändern eines Benutzers weiter.Im Fall, dass 
     * mehrere Benutzer ausgewählt sind wird eine Fehlermeldung ausgegeben.
     * 
     * @return String für die Navigation
     */
    public String aendern(){
        List<Benutzer> ausgewaelteBenutzer = this.getAusgewaehlteBenutzer();
        
        if(ausgewaelteBenutzer.size() != 1){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Sie können nur die Benutzerdaten einzelner Benutzer ändern.");
            return "benutzerVerwalten";
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("gewaehlterBenutzer", ausgewaelteBenutzer.get(0));
        return "benutzerAendern";
    }
    
    /**
     * Stuft die ausgewählten Schüler im eine Stufe hoch. Hierbei wird von einer 
     * Stufenbezeichnung bestehend aus Stufe und Schulform gearbeitet.
     * Beispiel:
     * Die Stufe 12 des Berufsgymnasiums hätte die Bezeichnung 12BG.
     * 
     * @return String für die Navigation
     */
    public String hochstufen(){
        
        
        return null;
    }
    
    /**
     * Stuft die ausgewählten Schüler im eine Stufe ab. Hierbei wird von einer 
     * Stufenbezeichnung bestehend aus Stufe und Schulform gearbeitet.
     * Beispiel:
     * Die Stufe 12 des Berufsgymnasiums hätte die Bezeichnung 12BG.
     * 
     * @return String für die Navigation
     */
    public String abstufen(){
        
        return null;
    }
    
    /**
     * Gibt eine Liste mit den ausgewählten Benutzern zurück.
     * 
     * @return Liste der Benutzer, die auf der Oberfläche ausgewählt wurden
     */
    private List<Benutzer> getAusgewaehlteBenutzer(){
        List<Benutzer> ausgewaelteBenutzer = new ArrayList<>();
        for(Eintrag<Benutzer, Boolean> e: this.auswahl){
            if(e.getValue()){
                ausgewaelteBenutzer.add(e.getKey());
            }
        }
        return ausgewaelteBenutzer;
    }
    
    /**
     * Erstellt eine Liste mit den Bezeichnungen aller Rollen und dem Wort 
     * "Alle". Dies wird für den Filter der Benutzerverwaltung benötigt.
     * 
     * @return Liste mit den Bezeichnungen der Rollen
     */
    public List<String> getAlleRollen(){
        List<String> rollen = new ArrayList<>();
        rollen.add("Alle");
        for(Rolle r:Rolle.values()){
            rollen.add(r.name());
        }
        return rollen;
    }
    
    /**
     * @return die Stufe
     */
    public Stufe getStufe() {
        return stufe;
    }

    /**
     * @param stufe Die zu setzende Stufe
     */
    public void setStufe(Stufe stufe) {
        this.stufe = stufe;
    }

    /**
     * @return Die Rolle
     */
    public String getRolle() {
        return rolle;
    }

    /**
     * @param rolle Die zu setzende Rolle
     */
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
    
//    public List<Benutzer> getAlleBenutzer() {
//        return this.benutzerBean.findAll();
//    }
    
    /**
     * Ändert bei einer Änderung des Filters die Tabelle mit den ausgewählten 
     * Benutzern.
     */
    public void auswaehlen(){
        this.benutzerNB.auswaehlen(this.rolle, this.stufe);
    }

    /**
     * @return Die auf der Seite angezeigte Fehlermeldung
     */
    public String getError() {
        return error;
    }

    /**
     * @param error Die Fehlermeldung, die auf der Seite angezeigt werden soll
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return Die gefilterte Benutzerliste mit Booleanwerten zur Auswahl einzelner 
     * oder mehrerer Benutzer
     */
    public List<Eintrag<Benutzer, Boolean>> getAuswahl() {
        return auswahl;
    }

    /**
     * @param p_benutzer Eine Liste mit Booleanwerten für die Auswahl von Benutzern
     */
    public void setAuswahl(List<Benutzer> p_benutzer) {
        System.out.println("de.bws.namedBeans.BenutzerNB.setAuswahl()");
        this.auswahl.clear();
        for(Benutzer b: p_benutzer){
            this.auswahl.add(new Eintrag(b, false));
        }
    }
}
