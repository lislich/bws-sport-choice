package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.entities.Lehrer;
import de.bws.entities.Person;
import de.bws.entities.Schueler;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *  Die managed Bean für das Ändern von Benutzerdaten.
 *  Diese stellt Methoden zum Speichern der geänderten Daten zur Verfügung.
 * 
 * @author joshua
 */
@Named("benutzerAendernNB")
@ViewScoped
public class BenutzerAendernNB implements Serializable{
    // Schnittstelle zur Datenbank für Entitäten vom Typ Benutzer
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    // Schnittstelle zur Datenbank für Entitäten vom Typ Lehrer
    @EJB
    private LehrerFacadeLocal lehrerBean;
    // Schnittstelle zur Datenbank für Entitäten vom Typ Person
    @EJB
    private PersonFacadeLocal personBean;
    // Schnittstelle zur Datenbank für Entitäten vom Typ Schueler
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    // Der ausgewählte Benutzer
    private Benutzer benutzer;
    
    // Der ausgwählte neue Tutor
    private long tutorNeu;
    
    private FacesContext context;
    
    /**
     * Diese Methode wird mit der Annotation "@PostConstruct" nach dem Konstruieren aufgerufen.
     * Sie holt den ausgewählten Benutzer und die letzte Fehlermeldung.
     * 
     * @author Joshua
     */
    @PostConstruct
    private void init(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        this.benutzer = (Benutzer) sessionMap.get("gewaehlterBenutzer");
        if(this.benutzer == null){
            this.benutzer = new Benutzer();
            sessionMap.put("lastError", "Beim Laden des Benutzers ist ein Fehler aufgetreten.");
        } else {
            this.benutzer = (Benutzer) sessionMap.get("gewaehlterBenutzer");
//            this.setBenutzername(this.benutzer.getBenutzername());
            sessionMap.put("gewaehlterBenutzer", null);
        }
        if(this.benutzer.getPerson() == null){
            this.benutzer.setPerson(new Person());
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
            Person person = this.benutzer.getPerson();
            if(person != null){
                switch(this.benutzer.getRolle()){
                    case LEHRER: 
                        this.lehrerBean.edit((Lehrer)person); break;
                    case SCHUELER:
                        Schueler tmp = (Schueler)person;
                        Schueler schueler = this.schuelerBean.find(tmp.getId());
                        schueler.setTutor(this.lehrerBean.find(schueler.getTutor().getId()));
                        this.schuelerBean.edit(schueler); 
                        
                        break;
                    default:
                        this.personBean.edit(person);
                }
            }
            this.benutzerBean.edit(benutzer);
        } else {
            context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("lastError", "Beim Aktualisieren der Benutzerdaten ist ein Fehler aufgetreten.");
        }
        return "benutzerVerwalten";
    }
    
//**************************** Getter und Setter *******************************
    
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
     * @return the tutorNeu
     */
    public long getTutorNeu() {
        return tutorNeu;
    }

    /**
     * @param tutorNeu the tutorNeu to set
     */
    public void setTutorNeu(long tutorNeu) {
        this.tutorNeu = tutorNeu;
    }
    
}
