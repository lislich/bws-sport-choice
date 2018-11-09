package de.bws.namedBeans;

import de.bws.data.Eintrag;
import de.bws.entities.Benutzer;
import de.bws.entities.Kurs;
import de.bws.entities.Schueler;
import de.bws.entities.Wahl;
import de.bws.entities.Wahlzeitraum;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import de.bws.sessionbeans.WahlFacadeLocal;
import de.bws.sessionbeans.WahlzeitraumFacadeLocal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 * @author Lisa
 * 
 * Diese ManagedBean dient zum Anlegen eines Wahlzeitraumes und zum Anlegen einer Wahl eines Schülers.
 */
@Named(value = "wahlNBTest")
@ViewScoped
public class WahlNBTest implements Serializable{

    // Schnittstelle zur Datenbank für Entitäten vom Typ Wahl
    @EJB
    private WahlFacadeLocal wahlBean;
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Wahlzeitraum
    @EJB
    private WahlzeitraumFacadeLocal wahlzeitraumBean;
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Kurs
    @EJB
    private KursFacadeLocal kursBean;
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Schüler
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    @Inject
    private KurslisteNB kurslisteNB;
    
    /**
     * Variablen zum Anlegen eines Wahlzeitraumes.
     */
    
    // Beginn eines Wahlzeitraumes
    private Date beginn;
    
    // Ende eines Wahlzeitraumes
    private Date ende;
    
    /**
     * Variablen zum Anlegen einer Wahl.
     */
    
    // Auslastung eines Kurses durch Erstwahlen, als Information für Schüler
    private double auslastung;
    
    // Liste für die Repräsentation der Wahl
    private List<Eintrag<Kurs, Boolean>> kurse;
    private List<Integer> wahl;
    private Schueler angemeldeterSchueler;
    
    /**
     * @author Lisa
     * 
     * Diese Methode wird beim Erzeugen der ManagedBean aufgerufen und ermittelt ob es bereits einen 
     * gespeicherten Wahlzeitraum in der Datenbank gibt.
     * Außerdem wird der akutell angemeldete Benutzer, für die Wahl Schüler, und dessen eventuell bereits 
     * gespeicherte Wahl ermittelt.
     */
    @PostConstruct
    public void init(){
        // Benutzer bzw. Schüler wird ermittelt
        this.angemeldeterSchueler = (Schueler)((Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer")).getPerson();
        if(this.angemeldeterSchueler != null){
        // Eventuell bereits gespeicherter Wahlzeitraum wird ermittelt
            Wahlzeitraum tmp;
            try{
                tmp = this.wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz").get(0);
                if (tmp.getBeginn() != null) {
                    this.setBeginn(tmp.getBeginn());
                }
                if (tmp.getEnde() != null) {
                    this.setEnde(tmp.getEnde());
                }
                
                this.kurse = this.pruefeThemengleich(kurslisteNB.getStufeKurse(), this.angemeldeterSchueler);

                // Eventuell bereits gespeicherte Wahl wird ermittelt
                Wahl wTmp = this.angemeldeterSchueler.getWahl();
                this.wahl = new ArrayList();
                if(wTmp != null){
                    int laenge = this.kurse.size();
                    for(int i = 0; i < laenge; i++){
                        if(this.kurse.get(i).getKey().equals(wTmp.getErstwahl())){
                           this.wahl.set(i, 1);
                        } else if (this.kurse.get(i).getKey().equals(wTmp.getZweitwahl())) {
                            this.wahl.set(i, 2);
                        } else if (this.kurse.get(i).getKey().equals(wTmp.getDrittwahl())) {
                            this.wahl.set(i, 3);
                        } else {
                            this.wahl.set(i, 0);
                        }
                    }
                }
            } catch(Exception e) {

            }
        }
        
    }
    
    /**
     * @author Lisa
     * 
     * Diese Methode überprüft die Eingaben der Daten und speichert bei fehlerfreien Eingaben den Wahlzeitraum, 
     * oder aktualisiert den bereits vorhandenen.
     */
    public void saveDatum() {
        // Aktueller Zeitstempel
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Prüfung ob eingegebenes Enddatum in der Vergangenheit liegt -> Fehler
        if (ende.getTime() < timestamp.getTime()) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Das Enddatum darf nicht in der Vergangenheit liegen");
        } else {
            // Prüfung ob eingegebenes Beginndatum hinter dem Enddatum liegt -> Fehler
            if (beginn.after(ende)) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Das Beginndatum muss kleiner als das Enddatum sein.");
            } else {
                // Ermitteln von bereits eingetragenen Zeiträumen
                List<Wahlzeitraum> tmpList;
                tmpList = wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz");
                
                // Wenn es keinen Eintrag gibt, wird ein neuer Zeiraum gespeichert, ansonsten der vorhandene aktualisiert.
                if (tmpList.isEmpty()) {
                    Wahlzeitraum tmp = new Wahlzeitraum();
                    tmp.setBeginn(getBeginn());
                    tmp.setEnde(getEnde());
                    this.wahlzeitraumBean.create(tmp);
                } else {
                    Wahlzeitraum tmp = tmpList.get(0);
                    tmp.setBeginn(beginn);
                    tmp.setEnde(ende);
                    this.wahlzeitraumBean.edit(tmp);
                }
            }
        }
    }

    /**
     * @author Lisa, Joshua
     * @return String zur Navigation zur nächsten Seite
     */
    public String saveWahl() {
        String rueckgabe = "Fehler";
        
        if(this.angemeldeterSchueler != null){
            // Falls der Schueler bereits gewählt hat, 
            // ansonsten gibt es eine neue Wahl
            if(this.angemeldeterSchueler.getWahl() == null){
                this.angemeldeterSchueler.setWahl(new Wahl());
            }

            // Die Liste der Wahlen wird durchlaufen um die Erst-, Zweit- und Drittwahl zu finden
            // und diese in "kurswahl" zu schreiben
            int laenge = this.wahl.size();
            for(int i = 0; i < laenge; i++){
                switch(this.wahl.get(i)){
                    case 1: 
                        this.angemeldeterSchueler.getWahl().setErstwahl(this.kurse.get(i).getKey());
                        break;
                    case 2:
                        this.angemeldeterSchueler.getWahl().setZweitwahl(this.kurse.get(i).getKey());
                        break;
                    case 3: 
                        this.angemeldeterSchueler.getWahl().setDrittwahl(this.kurse.get(i).getKey());
                        break;
                }
            }

            // nur wenn alle drei Stimmen abgegeben wurden wird die Wahl in de Datenbank geschrieben
            if(this.angemeldeterSchueler.getWahl().getErstwahl() != null && this.angemeldeterSchueler.getWahl().getZweitwahl() != null 
                    && this.angemeldeterSchueler.getWahl().getDrittwahl() != null){
                this.wahlBean.create(this.angemeldeterSchueler.getWahl());
                this.schuelerBean.edit(this.angemeldeterSchueler);
                rueckgabe = "Erfolg";
            }
        }
        
        return rueckgabe;
    }
    
    /**
     * Überprüft die Liste der verfügbaren Kurse auf Themengleichheit. 
     * 
     * @author Joshua
     * @param p_aktuelleKurse Liste der verfügbaren Kurse
     * @param p_schueler Der wählende Schüler
     * @return Liste mit Einträgen, die einen Kurs und ein Booleanwert enthalten (true = themengleich, false = nicht themengleich))
     */
    private List<Eintrag<Kurs, Boolean>> pruefeThemengleich(List<Kurs> p_aktuelleKurse, Schueler p_schueler){
        List<Eintrag<Kurs, Boolean>> kurs = new ArrayList<>();
        for(Kurs k:p_aktuelleKurse){
            if(k.getThemengleich() != null && k.getThemengleich().getTeilnehmer().contains(p_schueler)){
                kurs.add(new Eintrag(k, true));
            } else {
                kurs.add(new Eintrag(k, false));
            }
        }
        return kurs;
    }

    /**
     * @author Lisa
     * @param p_themengleich Kurs
     * @param p_schueler Schueler
     * @return true or false
     * 
     * Diese Methode soll überprüfen, ob der übergebene Schüler dem übergebenen Kurs zugewiesen ist.
     * Sie wird verwendet um zu ermitteln ob ein Schüler bereits in einem Themengleichen Kurs war.
     */
    private boolean pruefeThemengleich(Kurs p_themengleich, Schueler p_schueler) {
        List<Schueler> tmp;
        boolean fehler = false;
        if (p_themengleich != null) {
            tmp = p_themengleich.getTeilnehmer();
            if (tmp != null && !(tmp.isEmpty())) {
                if (tmp.contains(p_schueler)) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Ihre Drittwahl ist Themengleich zu Ihrem Kurs im Vorjahr. Bitte wählen Sie einen anderen Kurs.");
                    fehler = true;
                }
            }
        }
        return fehler;
    }

    
    // #### Getter- und Setter-Methoden ############################################################
    /**
     * @return the beginn
     */
    public Date getBeginn() {
        return beginn;
    }

    /**
     * @param p_beginn the beginn to set
     */
    public void setBeginn(Date p_beginn) {
        this.beginn = p_beginn;
    }

    /**
     * @return the ende
     */
    public Date getEnde() {
        return ende;
    }

    /**
     * @param p_ende the ende to set
     */
    public void setEnde(Date p_ende) {
        this.ende = p_ende;
    }

//    /**
//     * @return the ersteWahl
//     */
//    public String getErsteWahl() {
//        return ersteWahl;
//    }
//
//    /**
//     * @param p_ersteWahl the ersteWahl to set
//     */
//    public void setErsteWahl(String p_ersteWahl) {
//        this.ersteWahl = p_ersteWahl;
//    }
//
//    /**
//     * @return the zweiteWahl
//     */
//    public String getZweiteWahl() {
//        return zweiteWahl;
//    }
//
//    /**
//     * @param p_zweiteWahl the zweiteWahl to set
//     */
//    public void setZweiteWahl(String p_zweiteWahl) {
//        this.zweiteWahl = p_zweiteWahl;
//    }
//
//    /**
//     * @return the dritteWahl
//     */
//    public String getDritteWahl() {
//        return dritteWahl;
//    }
//
//    /**
//     * @param p_dritteWahl the dritteWahl to set
//     */
//    public void setDritteWahl(String p_dritteWahl) {
//        this.dritteWahl = p_dritteWahl;
//    }

    /**
     * @author Lisa
     * @param p_kurs
     * @return the auslastung
     * 
     * Diese Methode ermittelt die prozentuale Auslastung des übergebenen Kurses durch die bereits gesetzten
     * Erstwahlen der Schüler.
     */
    public double getAuslastung(Kurs p_kurs) {
        // Sucht den übergebenen Kurs aus der Datenbank
        Kurs tmp = this.kursBean.find(p_kurs.getId());
        
        // Ermittelt die maximale Teilnehmerzahl des Kurses
        double anzahlTeilnehmer = (double)tmp.getTeilnehmerzahl();
        
        // Ermittelt die Liste von Wahlen, die den Kurs als Erstwahl beinhalten
        double anzahlGewaehlt = 0.0;
        List<Wahl> gewaehlt = this.wahlBean.get("SELECT w FROM Wahl w WHERE w.erstwahl.id = " + tmp.getId());
        if(gewaehlt != null){
            anzahlGewaehlt = gewaehlt.size();
        }
        
        // Wenn der Kurs mehr als 0 mal gewählt wurde, wird die Auslastung berechnet
        if(anzahlGewaehlt != 0){
            auslastung = (anzahlGewaehlt/anzahlTeilnehmer)*100;
        }else{
            auslastung = anzahlGewaehlt;
        }
        
        return auslastung;
    }

    /**
     * @param p_auslastung the auslastung to set
     */
    public void setAuslastung(double p_auslastung) {
        this.auslastung = p_auslastung;
    }

    public List<Eintrag<Kurs, Integer>> getKurseFuerWahl(){
        List<Eintrag<Kurs, Integer>> listeFuerWahl = new ArrayList<>();
        for(Kurs k:this.kurslisteNB.getStufeKurse()){
            listeFuerWahl.add(new Eintrag<>(k, 0));
        }
        return listeFuerWahl;
    }

    /**
     * @return the kurse
     */
    public List<Eintrag<Kurs, Boolean>> getKurse() {
        return kurse;
    }

    /**
     * @param kurse the kurse to set
     */
    public void setKurse(List<Eintrag<Kurs, Boolean>> kurse) {
        this.kurse = kurse;
    }

    /**
     * @return the wahl
     */
    public List<Integer> getWahl() {
        return wahl;
    }

    /**
     * @param wahl the wahl to set
     */
    public void setWahl(List<Integer> wahl) {
        this.wahl = wahl;
    }
}
