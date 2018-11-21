/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;


import de.bws.data.Eintrag;
import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Schueler;
import de.bws.entities.Stufe;
import de.bws.security.Passwort;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import de.bws.sessionbeans.StufeFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

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
    private StufeFacadeLocal stufeBean;
    
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
     * Löscht alle ausgewählten Benutzer, außer es ist der angemeldete Benutzer 
     * oder der Rootadmin.
     * 
     * @return 
     */
    public String loeschen(){
        Benutzer angemeldeterBenutzer = (Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        for(Benutzer b: this.getAusgewaehlteBenutzer()){
            // prüft ob der zu löschende Benutzer der angemeldete Benutzer ist
            if(b.equals(angemeldeterBenutzer)){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Sie können sich nicht selbst löschen.");
                continue;
            // prüft ob der zu löschende Benutzer der Rootadmin ist
            } else if(b.getBenutzername().equals("ChoiceRoot")) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Der Benutzer \"ChoiceRoot\" darf nicht gelöscht werden.");
                continue;
            }
            // Benutzer wird samt zugehöriger Person (bzw. Lehrer, Schüler) gelöscht
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
     * Stuft die ausgewählten Schüler im eine Stufe hoch.
     * 
     * @return String für die Navigation
     */
    public String hochstufen(){
        List<Benutzer> ausgewaehlteBenutzer = this.getAusgewaehlteBenutzer();
        String neueStufeBezeichnung = null;
        List<Stufe> neueStufe;
        for(Benutzer b:ausgewaehlteBenutzer){
            if(b.getPerson() instanceof Schueler){
                neueStufeBezeichnung = "" + (Integer.parseInt(b.getPerson().getStufe().getBezeichnung()) + 1);
                neueStufe = this.stufeBean.get("SELECT s FROM Stufe s WHERE s.bezeichnung = " + neueStufeBezeichnung);
                if(neueStufe != null && !neueStufe.isEmpty()){
                    ((Schueler)b.getPerson()).setStufe(neueStufe.get(0));
                    this.schuelerBean.edit((Schueler)b.getPerson());
                } else {
                    this.benutzerBean.remove(b);
                }
            }
        }
        
        return "benutzerVerwalten";
    }
    
    /**
     * Stuft die ausgewählten Schüler im eine Stufe ab.
     * 
     * @return String für die Navigation
     */
    public String abstufen(){
        List<Benutzer> ausgewaehlteBenutzer = this.getAusgewaehlteBenutzer();
        String neueStufeBezeichnung;
        List<Stufe> neueStufe;
        for(Benutzer b:ausgewaehlteBenutzer){
            if(b.getPerson() instanceof Schueler){
                neueStufeBezeichnung = "" + (Integer.parseInt(b.getPerson().getStufe().getBezeichnung()) - 1);
                neueStufe = this.stufeBean.get("SELECT s FROM Stufe s WHERE s.bezeichnung = " + neueStufeBezeichnung);
                if(neueStufe != null && !neueStufe.isEmpty()){
                    ((Schueler)b.getPerson()).setStufe(neueStufe.get(0));
                    this.schuelerBean.edit((Schueler)b.getPerson());
                }
            }
        }
        
        return "benutzerVerwalten";
    }
    
    /**
     * Diese Methode setzt generiert ein neues, zufälliges Passwort und zeigt dieses einmalig in einem Dialog.
     * 
     * @param p_benutzer der ausgewählte Benutzer
     * @Author Joshua
     * @return String für weitere Navigation
     */
    public String passwortZuruecksetzen(Benutzer p_benutzer){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String passwortNeu;
        RequestContext request = RequestContext.getCurrentInstance();
        
        // Prüft ob ein Benutzer ausgewählt ist.
        if(p_benutzer != null) {
            try{
                // Das passwort wird generiert.
                passwortNeu = Passwort.passwortGenerieren();
            } catch (Exception ex) {
                Logger.getLogger(BenutzerAendernNB.class.getName()).log(Level.SEVERE, null, ex);
                sessionMap.put("lastError", "Beim Aktualisieren des Passworts ist ein Fehler aufgetreten. Das Passwort wurde nicht geändert.");
                return "benutzerVerwalten";
            }
            
            boolean isGeaendert = p_benutzer.setNeuesPasswort(passwortNeu);
            this.benutzerBean.edit(p_benutzer);
            // Wenn das Passwort geändert wurde, wird es einmalig in einem Dialog angezeigt.
            if(isGeaendert){
                String execute = "$('#pnl').append('<p> ";
                execute += "Benutzer: ";
                execute += p_benutzer.getBenutzername();
                execute += " Passwort: ";
                execute += passwortNeu;
                execute += "</p>')";
                request.execute(execute);
                request.execute("PF('dialogZuruecksetzen').show();");
                
            } else {
                sessionMap.put("lastError", "Beim Aktualisieren des Passworts ist ein Fehler aufgetreten. Das Passwort wurde nicht geändert.");
                return "benutzerVerwalten";
            }
        }
        return "benutzerVerwalten";
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
        this.auswahl.clear();
        for(Benutzer b: p_benutzer){
            this.auswahl.add(new Eintrag(b, false));
        }
    }
}
