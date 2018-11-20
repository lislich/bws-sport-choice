package de.bws.namedBeans;

import de.bws.data.Eintrag;
import de.bws.entities.Kurs;
import de.bws.entities.Schueler;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import de.bws.sessionbeans.StufeFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 * @author Lisa
 * 
 * Diese ManagedBean dient zum manuellen Zuweisen der Schüler in Kurse, sowie zum Wechseln zweier Schüler in ihren Kursen.
 */
@Named(value = "kursZuweisenNB")
@ViewScoped
public class KursZuweisenNB implements Serializable{

    // Schnittstelle zur Datenbank für Entitäten vom Typ Kurs
    @EJB
    private KursFacadeLocal kursBean;
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Schüler
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Stufe
    @EJB
    private StufeFacadeLocal stufeBean;
    
    // Liste aller Schüler
    private List<Schueler> schueler;
    
    // Liste von Kursen nach Stufe gefiltert
    private List<Kurs> stufekurs;
    
    // Schüler-ID als String
    private String schuelerId;
    
    // Kurs-ID als String
    private String kursId;
    
    // Schüler-ID vom ersten Schüler
    private int schuelerEins;
    
    // Schüler-ID vom zweiten Schüler
    private int schuelerZwei;
    
    // Liste von Einträgen, die die Zuordnung von Schüler und Kurs enthalten
    private List<Eintrag<Schueler, Kurs>> zuordnung;
    
    /**
     * @author Lisa
     * 
     * Diese Methode wird beim Erzeugen der ManagedBean aufgerufen und initialisiert die Liste der Einträge.
     * Von den bereits vorhandenen Schülern werden die Zuordnungen gesetzt.
     */
    @PostConstruct
    public void init(){
        this.zuordnung = new ArrayList<>();
        this.setZuordnung(this.getSchueler());
    }
    
    /**
     * @author Lisa
     * 
     * Diese Methode geht die Liste der Einträge durch und weist jedem Schüler den zugehörigen Kurs zu.
     * Die Zuordnung wird in die Datenbank geschrieben.
     */
    public void zuweisen(){       
        // Iteration durch Liste der Einträge
        for(Eintrag e : this.zuordnung){
            // Ermittelt den Schüler
            Schueler p_schueler = (Schueler) e.getKey();  
            
            // Ermittelt die Kurs-ID des Kurses der dem Schüler zugewiesen ist, Kurs wird aus Datenbank gesucht
            Kurs kursNeu = null;
            if(e.getValue() != null){
                String p_kursId = (e.getValue().toString().split("="))[1].replace("]", "").trim();
                kursNeu = this.kursBean.find(Long.parseLong(p_kursId));
            }
            
            
            // Wenn ein Kurs gefunden wurde wird ermittelt ob der Schüler bereits eine Zuweisung zu einem anderen Kurs hat
            if(kursNeu != null){
                Kurs kursAlt = this.getAktuellerKurs(p_schueler.getId());

                // Wenn der Schüler bereits einem anderen Kurs zugewiesen war wird er dort als Teilnehmer gelöscht und der Eintrag wird in der Datenbank aktualisiert
                if(!(kursAlt.getTitel().equals("-"))){
                    Kurs tmp = this.kursBean.find(kursAlt.getId());
                    tmp.removeTeilnehmer(p_schueler);
                    this.kursBean.edit(tmp);
                }
                // Der Schüler wird dem neuen Kurs hinzugefügt und der Eintrag wird in der Datenbank aktualisiert.
                kursNeu.addTeilnehmer(p_schueler);
                this.kursBean.edit(kursNeu);   
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogKurszuweisen').show(); $('#zuweisungSpeichern').attr('disabled', true);");
            }           
        }                       
    }

    /**
     * @author Lisa
     * @return String zur Navigation zur nächsten Seite
     * 
     * Diese Methode ermittelt die beiden ausgewählen Schüler und tauscht sie in ihren Kursen, wenn möglich.
     */
    public String wechsel(){
        // Aktuelle Session
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String rueckgabe = "kursWechseln";
        
        // Suchen der gewählten Schüler aus der Datenbank über deren ID
        Schueler eins = this.schuelerBean.find((long)schuelerEins);
        Schueler zwei = this.schuelerBean.find((long)schuelerZwei);
        
        // Deklarieren und Initialisieren der Kurse der Schüler
        Kurs kursSchuelerEins = null;
        Kurs kursSchuelerZwei = null;
        
        // Suchen der Kurse der Schüler aus Datenbank, als Rückgabe bekommt man Kurslisten
        List<Kurs> listeEins = this.kursBean.get("SELECT k FROM Kurs k INNER JOIN k.teilnehmer t WHERE t.id = " + schuelerEins);
        List<Kurs> listeZwei = this.kursBean.get("SELECT k FROM Kurs k INNER JOIN k.teilnehmer t WHERE t.id = " + schuelerZwei);
        
        
        /**
         * Wenn die Listen nicht 'null' oder leer sind wird der erste Eintrag den Kursen zugewiesen.
         * Ansonsten gibt es einen Fehler und der Wechsel wird nicht durchgeführt.
         */
        if((listeEins != null && !(listeEins.isEmpty())) ){
            kursSchuelerEins = listeEins.get(0);
        }else{
            sessionMap.put("lastError", "Schüler/in: " + eins.getNachname() +", " + eins.getVorname() + " ist noch keinem Kurs zugewiesen. Ein Kurswechsel ist nicht möglich");
            rueckgabe = "wechselFehlgeschlagen";
        }
        if((listeZwei != null && !(listeZwei.isEmpty())) ){
            kursSchuelerZwei = listeZwei.get(0);
        }else{
            sessionMap.put("lastError", "Schüler/in: " + zwei.getNachname() +", " + zwei.getVorname() + " ist noch keinem Kurs zugewiesen. Ein Kurswechsel ist nicht möglich.");
            rueckgabe = "wechselFehlgeschlagen";
        }
        
        // Überprüfung ob derselbe Schüler zweimal angegeben wurde, wenn ja kann kein Tausch stattfinden, ansonsten wird getauscht
        if(eins.getId().compareTo(zwei.getId()) == 0){
            sessionMap.put("lastError", "Bitten geben Sie zwei verschiedene Schüler an");
            rueckgabe = "wechselFehlgeschlagen";
        }else{
            if(kursSchuelerEins != null && kursSchuelerZwei != null){
                kursSchuelerEins.removeTeilnehmer(eins);
                kursSchuelerZwei.addTeilnehmer(eins);
                kursSchuelerZwei.removeTeilnehmer(zwei);
                kursSchuelerEins.addTeilnehmer(zwei);
                this.kursBean.edit(kursSchuelerEins);
                this.kursBean.edit(kursSchuelerZwei);
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogKurswechsel').show(); $('#wechsel').attr('disabled', true);");
            } 
        }
        
        return rueckgabe;
        
    }
 
    /**
     * @author Lisa
     * @param p_schuelerId
     * @return 
     * 
     * Diese Methode ermittelt den Kurs dem der Schüler zugewiesen ist. Der 
     * Schüler kann anhand der übergebenen Schüler-ID ermittelt werden.
     */
    public Kurs getAktuellerKurs(long p_schuelerId){
        Kurs gesucht = null;
        // Ermitteln aller Kurse aus der Datenbank
        List<Kurs> tmp = this.kursBean.get("SELECT k FROM Kurs k");
        
        /** Iteration über alle Kurse und deren Teilnehmer, wenn ein Teilnehmer der Schüler-ID entspricht
         * ist der aktuelle Kurs gefunden.
         */
        for(Kurs k : tmp){
            for(Schueler s : k.getTeilnehmer()){
                if(s.getId().compareTo(p_schuelerId) == 0){
                    gesucht = k;
                }
            }
        }
        
        // Wenn kein Kurs gefunden wurde, dann wird ein neuer Kurs erstellt zwecks Anzeige auf der Webseite
        if(gesucht == null){
            gesucht = new Kurs();
            gesucht.setTitel("-");
        }
        
        return gesucht;
    }
    
    // #### Getter- und Setter-Methoden ###########################################################
    
    /**
     * @author Lisa
     * @return the schueler
     * 
     * Alle Schüler werden aus der Datenbank gesucht.
     */
    public List<Schueler> getSchueler() {
        List<Schueler> tmp = this.schuelerBean.findAll();
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.schueler = tmp;
        return this.schueler;
    }

    /**
     * @param p_schueler the schueler to set
     */
    public void setSchueler(List<Schueler> p_schueler) {
        this.schueler = p_schueler;
    }

    /**
     * @return the schuelerId
     */
    public String getSchuelerId() {
        return schuelerId;
    }

    /**
     * @param p_schuelerId the schuelerId to set
     */
    public void setSchuelerId(String p_schuelerId) {
        this.schuelerId = p_schuelerId;
    }

    /**
     * @return the kursId
     */
    public String getKursId() {
        return kursId;
    }

    /**
     * @param p_kursId the kursId to set
     */
    public void setKursId(String p_kursId) {
        this.kursId = p_kursId;
    }

    /**
     * author Lisa
     * @param p_schuelerId
     * @return the stufekurs
     * 
     * Es wird eine Liste von Kursen ermittelt, die der Stufe des Schülern entsprechen, dessen
     * ID übergeben wird.
     */
    public List<Kurs> getStufeKurs(long p_schuelerId) {
        // Suchen des Schülers aus Datenbank
        Schueler p_schueler = this.schuelerBean.find(p_schuelerId);
        
        // Ermitteln der Stufe des Schülers und Suchen der entsprechenden Kurse
        Stufe stufe = this.stufeBean.find(p_schueler.getStufe().getId());
        List<Kurs> tmp = this.kursBean.get("SELECT k FROM Kurs k WHERE k.stufe.id = " + stufe.getId() );
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.stufekurs = tmp;
        return this.stufekurs;
    }

    /**
     * @param p_kurs the kurs to set
     */
    public void setStufeKurs(List<Kurs> p_kurs) {
        this.stufekurs = p_kurs;
    }

    /**
     * @return the schuelerEins
     */
    public int getSchuelerEins() {
        return schuelerEins;
    }

    /**
     * @param p_schuelerEins the schuelerEins to set
     */
    public void setSchuelerEins(int p_schuelerEins) {
        this.schuelerEins = p_schuelerEins;
    }

    /**
     * @return the schuelerZwei
     */
    public int getSchuelerZwei() {
        return schuelerZwei;
    }

    /**
     * @param p_schuelerZwei the schuelerZwei to set
     */
    public void setSchuelerZwei(int p_schuelerZwei) {
        this.schuelerZwei = p_schuelerZwei;
    }

    /**
     * @return the zuordnung
     */
    public List<Eintrag<Schueler, Kurs>> getZuordnung() {
        return zuordnung;
    }

    /**
     * @author Lisa
     * @param p_schueler the zuordnung to set
     * 
     * Hier wird die Liste der Einträge mit den bereits vorhandenen Zuordnungen gefüllt.
     */
    public void setZuordnung(List<Schueler> p_schueler) {
        this.zuordnung.clear();
        for(Schueler s : p_schueler){
            this.zuordnung.add(new Eintrag(s, getAktuellerKurs(s.getId())));
        }
    }

    
}
