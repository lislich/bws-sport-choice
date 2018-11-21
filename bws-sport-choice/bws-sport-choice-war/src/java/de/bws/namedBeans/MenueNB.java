package de.bws.namedBeans;

import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Kurs;
import de.bws.entities.Lehrer;
import de.bws.entities.Schueler;
import de.bws.entities.Wahlzeitraum;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.WahlzeitraumFacadeLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 * @author Lisa
 * 
 * Diese ManagedBean enthält Methoden, die das Menü des BWS-Sport-Choice für die Benutzer rendered und 
 * weitere Inhalte, die auf den Webseiten angezeigt werden.
 */
@Named(value = "menueNB")
@ViewScoped
public class MenueNB implements Serializable {

    // Schnittstelle zur Datenbank für Entitäten vom Typ Wahlzeitraum
    @EJB
    private WahlzeitraumFacadeLocal wahlzeitraumBean;
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Kurs
    @EJB
    private KursFacadeLocal kursBean;
    
    // Benutzer
    private Benutzer benutzer;
    
    // Begrüßungstext
    private String begruessungstext;

    /**
     * @author Lisa
     * 
     * Diese Methode wird beim Erzeugen der ManagedBean aufgerufen und holt den aktuellen Benutzer aus der SessionMap, dieser
     * wird in der Variable 'benutzer' gspeichert.
     */
    @PostConstruct
    private void init(){
        this.benutzer = (Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
    }
    
    /**
     * @author Lisa
     * @return true or false
     * 
     * Diese Methode ermittelt, ob der aktuelle Benutzer ein Lehrer ist.
     */
    public boolean lehrer() {
        boolean tmp = false;
        if (getBenutzer() != null) {
            if (getBenutzer().getRolle().equals(Rolle.LEHRER)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    
    /**
     * @author Lisa
     * @return true or false
     * 
     * Diese Methode ermittelt ob der aktuelle Benutzer ein Schüler ist.
     */
    public boolean schueler(){
        boolean tmp = false;
        if (getBenutzer() != null) {
            if (getBenutzer().getRolle().equals(Rolle.SCHUELER)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    
    /**
     * @author Lisa
     * @return true or false
     * 
     * Diese Methode ermittelt ob er aktuelle Benutzer ein Admin ist.
     */
    public boolean admin(){
        boolean tmp = false;
        if (getBenutzer() != null) {
            if (getBenutzer().getRolle().equals(Rolle.ADMIN)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    
    /**
     * @author Lisa
     * @return true or false
     * 
     * Diese Methode ermittelt ob der aktuelle Benutzer ein Lehrer oder Admin ist.
     */
    public boolean lehrerOrAdmin(){
        boolean tmp = false;
        if (getBenutzer() != null) {
            if (getBenutzer().getRolle().equals(Rolle.ADMIN)) {
                tmp = true;
            }
            if (getBenutzer().getRolle().equals(Rolle.LEHRER)) {
                tmp = true;
            }
        }       
        return tmp;
    }
    

    /**
     * @author Lisa
     * @return true or false
     * 
     * Diese Methode überprüft ob der aktuelle Tag im Zeitraum liegt und somit gewählt werden darf.
     */
    public boolean schuelerDarfWaehlen(){
        boolean tmp = false;
        // Sucht Wahlzeitraum aus Datenbank, Rückgabe als Liste von Wahlzeiträumen
        List<Wahlzeitraum> zeitraumListe = this.wahlzeitraumBean.findAll();
        Wahlzeitraum zeitraum = null;
        
        // Setzt den aktuellen Zeitstempel
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        // Wenn die Liste nicht leer ist und nicht 'null' ist dann wird der erste Wert der Variable zeitraum zugewiesen
        if(zeitraumListe != null && !(zeitraumListe.isEmpty())){
            zeitraum = zeitraumListe.get(0);
        }
       
        /*
         * Wenn der Zeitraum nicht 'null' ist und der Benutzer ermittelt werden kann, wird die Rolle
         * auf Schüler geprüft. Ist der Benutzer ein Schüler wird geprüft ob der Zeitstempel im Wahlzeitraum liegt.
         */
        if (zeitraum != null) {
            if (getBenutzer() != null) {
                if (getBenutzer().getRolle().equals(Rolle.SCHUELER)) {
                    Schueler s = (Schueler) getBenutzer().getPerson();
                    if (zeitraum.getBeginn().getTime() <= timestamp.getTime() && zeitraum.getEnde().getTime() >= timestamp.getTime()) {
                        tmp = true;
                    }
                }
            }
        }
        return tmp;
    }
    
    /**
     * @author joshua
     * @return null - noch nicht eingeteilt; not null - Kurs in den der Schüler eingeteilt ist
     * 
     * Diese Methode überprüft ob der angemeldete Schüler bereits in einem Kurs 
     * eingeteilt ist. Falls der Schüler bereits in einen Kurs eingetragen ist, 
     * wird der entsprechende Kurs zurückgegeben.
     */
    private Kurs bereitsInKursEingeteilt(){
        Kurs inKurs = null;
        // ZUr Sicherheit wird geprüft ob der angemeldete Benutzer ein Schüler ist
        if(this.benutzer.getPerson() instanceof Schueler){
            // alle Kurse werden geholt
            List<Kurs> kursListe = this.kursBean.get("SELECT k FROM Kurs k");
            Calendar calendar = new GregorianCalendar();
            int aktuellesJahr = Calendar.getInstance().get(Calendar.YEAR);
            for(Kurs k:kursListe){
                calendar.setTime(k.getJahr());
                // prüft ob der Kurs im aktuellen Jahr liegt
                if(calendar.get(Calendar.YEAR) == aktuellesJahr){
                    // prüft ob der Schüler ein Teilnehmer des Kurses ist
                    if(k.getTeilnehmer().contains((Schueler)this.benutzer.getPerson())){
                        inKurs = k;
                    }
                }
            }
        }
        return inKurs;
    }
    
    /**
     * @author Joshua
     * @return true or false
     * 
     * Diese Methode ermittelt ob ein Benutzer angemeldet ist.
     */
    public boolean isAngemeldet(){
        return this.benutzer != null;
    }
    
    /**
     * @author joshua
     * @return true - Wahl soll gerendert werden, false - Wahl soll nicht gerendert werden 
     * 
     * Diese Methode wählt einen Bergrüßungstext auf Basis der Rolle des 
     * angemeldeten Benutzers und dem Status der Wahl aus. 
     */
    public boolean startseiteRendern(){
        boolean kannWaehlen = false;
        if(this.benutzer != null){
            // Ist der angemeldete Benutzer ein Schüler
            if(this.benutzer.getPerson() instanceof Schueler){
                kannWaehlen = schuelerDarfWaehlen();
                // Prüft ob der Schüler wähllen kann
                if(!kannWaehlen){
                    Kurs eingeteilterKurs = this.bereitsInKursEingeteilt();
                    // Wenn er nicht wählen kann, wird je nach dem ob er in einen 
                    // Kurs eingeteilt ist eine Nachricht angezeigt.
                    if(eingeteilterKurs != null){
                        this.setBegruessungstext("Sie sind bereits in dem Kurs \"" + eingeteilterKurs.getTitel() + " (" + eingeteilterKurs.getKuerzel()+ ") " + "\" eingetragen.");
                    } else {
                        this.setBegruessungstext("Die Einteilung wurde noch nicht durchgeführt.");
                    } 
                } else {
                    this.setBegruessungstext("Sie können Ihre Wahl innerhalb des Wahlzeitraums ändern.");
                }
            // Ist der angemeldete Benutzer ein Lehrer
            } else if (this.benutzer.getPerson() instanceof Lehrer){
                this.setBegruessungstext("Hier können Sie Ihre Kurse verwalten. Zur Navigation Benutzen Sie bitte das Menu.");
            // der angemeldete Benutzer ist ein Admin
            } else {
                this.setBegruessungstext("Zur Navigation Benutzen Sie bitte das Menu.");
            }
        }
        return kannWaehlen;
    }
    
    // ##### Getter- und Setter-Methoden #########################################################
    
    /**
     * @return the benutzer
     */
    public Benutzer getBenutzer() {
        return benutzer;
    }

    /**
     * @param p_benutzer the benutzer to set
     */
    public void setBenutzer(Benutzer p_benutzer) {
        this.benutzer = p_benutzer;
    }

    /**
     * @return the begruessungstext
     */
    public String getBegruessungstext() {
        return begruessungstext;
    }

    /**
     * @param p_begruessungstext the begruessungstext to set
     */
    public void setBegruessungstext(String p_begruessungstext) {
        this.begruessungstext = p_begruessungstext;
    }
    
    
    
}
