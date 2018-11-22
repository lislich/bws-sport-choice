package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.entities.Kurs;
import de.bws.entities.Schueler;
import de.bws.entities.Wahl;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import de.bws.sessionbeans.WahlFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 * @author Lisa
 * 
 * Diese ManagedBean dient zum Anlegen eines Wahlzeitraumes und zum Anlegen einer Wahl eines Schülers.
 */
@Named(value = "wahlNB")
@ViewScoped
public class WahlNB implements Serializable{

    // Schnittstelle zur Datenbank für Entitäten vom Typ Wahl
    @EJB
    private WahlFacadeLocal wahlBean;

    // Schnittstelle zur Datenbank für Entitäten vom Typ Kurs
    @EJB
    private KursFacadeLocal kursBean;
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Schüler
    @EJB
    private SchuelerFacadeLocal schuelerBean;

    
    /**
     * Variablen zum Anlegen einer Wahl.
     */
    
    // Erstwahl eines Schülers
    private String ersteWahl;
    
    // Zweitwahl eines Schülers
    private String zweiteWahl;
    
    // Drittwahl eines Schülers
    private String dritteWahl;
    
    // Auslastung eines Kurses durch Erstwahlen, als Information für Schüler
    private double auslastung;
    
// ################################## Methoden #################################
    
    /**
     * @author Lisa
     * 
     * Diese Methode wird beim Erzeugen der ManagedBean aufgerufen.
     * Es wird der akutell angemeldete Benutzer, für die Wahl Schüler, und dessen eventuell bereits 
     * gespeicherte Wahl ermittelt.
     */
    @PostConstruct
    public void init(){
        // Benutzer bzw. Schüler wird ermittelt
        Schueler sTmp = (Schueler)((Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer")).getPerson();
        try{   
            // Eventuell bereits gespeicherte Wahl wird ermittelt
            Wahl wTmp = sTmp.getWahl();
            if(wTmp != null){
                 this.setErsteWahl(wTmp.getErstwahl().getId().toString());
                 this.setZweiteWahl(wTmp.getZweitwahl().getId().toString());
                 this.setDritteWahl(wTmp.getDrittwahl().getId().toString());
            }
        }catch(Exception e){
            
        }      
    }

    /**
     * @author Lisa
     * @return String zur Navigation zur nächsten Seite
     * 
     * In dieser Methode werden die ausgewählten Kurse des Schülers als seine Wahl gespeichert.
     */
    public String saveWahl() {
        String rueckgabe = "Fehler";
        
        // Aktueller Benutzer, hier Schüler, wird ermittelt
        Benutzer b = (Benutzer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        Schueler s = (Schueler) b.getPerson();
        
        // Prüfung ob die Erst-, Zweit- und Drittwahl dieselben Kurse enthalten -> Fehler
        if (ersteWahl.equals(zweiteWahl) || ersteWahl.equals(dritteWahl) || zweiteWahl.equals(dritteWahl)) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Bitte wählen Sie unterschiedliche Kurse.");
        } else {
            // Suchen der gewählten Kurse aus der Datenbank
            Kurs p_eins = this.kursBean.find(Long.parseLong(getErsteWahl()));
            Kurs p_zwei = this.kursBean.find(Long.parseLong(getZweiteWahl()));
            Kurs p_drei = this.kursBean.find(Long.parseLong(getDritteWahl()));

            // Wenn noch keine Wahl eingetragen ist, wird eine neue angelegt, ansonsten die vorhandene aktualisiert.
            Wahl wahl = s.getWahl();
            if (wahl == null) {
                wahl = new Wahl();
                wahl.setErstwahl(p_eins);
                wahl.setZweitwahl(p_zwei);
                wahl.setDrittwahl(p_drei);
                this.wahlBean.create(wahl);
            } else {
                wahl.setErstwahl(p_eins);
                wahl.setZweitwahl(p_zwei);
                wahl.setDrittwahl(p_drei);
                this.wahlBean.edit(wahl);
            }

            // Der Schüler bekommt die Wahl zugewiesen und wird in der Datenbank aktualisiert.
            s.setWahl(wahl);
            this.schuelerBean.edit(s);

            rueckgabe = "gewaehlt";
            }

        return rueckgabe;
    }

    /**
     * @author joshua
     * @param p_aktuelleKurse Liste der möglichen Kurse
     * @return Liste der nicht-themengleichen Kurse
     * 
     * Erstellt eine Liste der Kurse, die nicht Themengleich mit der Wahl des 
     * Vorjahres sind.
     */
    public List<Kurs> getNichtThemengleich(List<Kurs> p_aktuelleKurse){
        List<Kurs> kurs = new ArrayList<>();
        Schueler schueler = (Schueler) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        for(Kurs k:p_aktuelleKurse){
            if(k.getThemengleich() == null || !k.getThemengleich().getTeilnehmer().contains(schueler)){
                kurs.add(k);
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

    
// ############################ Getter- und Setter-Methoden ####################

    /**
     * @return the ersteWahl
     */
    public String getErsteWahl() {
        return ersteWahl;
    }

    /**
     * @param p_ersteWahl the ersteWahl to set
     */
    public void setErsteWahl(String p_ersteWahl) {
        this.ersteWahl = p_ersteWahl;
    }

    /**
     * @return the zweiteWahl
     */
    public String getZweiteWahl() {
        return zweiteWahl;
    }

    /**
     * @param p_zweiteWahl the zweiteWahl to set
     */
    public void setZweiteWahl(String p_zweiteWahl) {
        this.zweiteWahl = p_zweiteWahl;
    }

    /**
     * @return the dritteWahl
     */
    public String getDritteWahl() {
        return dritteWahl;
    }

    /**
     * @param p_dritteWahl the dritteWahl to set
     */
    public void setDritteWahl(String p_dritteWahl) {
        this.dritteWahl = p_dritteWahl;
    }

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
            auslastung = ((int)((anzahlGewaehlt/anzahlTeilnehmer)*10000))/100.0;
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

    
}
