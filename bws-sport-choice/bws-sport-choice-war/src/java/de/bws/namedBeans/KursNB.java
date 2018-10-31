package de.bws.namedBeans;

import de.bws.data.Eintrag;
import de.bws.entities.Benutzer;
import de.bws.entities.Kurs;
import de.bws.entities.Lehrer;
import de.bws.entities.Schueler;
import de.bws.entities.Stufe;
import de.bws.entities.Thema;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.StufeFacadeLocal;
import de.bws.sessionbeans.ThemaFacadeLocal;
import java.io.Serializable;
import java.sql.Timestamp;
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
 * Diese Managed Bean dient zum Anlegen und Bearbeiten eines Kurses. Außerdem gibt sie den vom Benutzer
 * gewählten Kurs für die Detailansicht zurück.
 * 
 */
@Named(value = "kursNB")
@ViewScoped
public class KursNB implements Serializable {

    // Schnittstelle zur Datenbank für Entitäten vom Typ Kurs
    @EJB
    private KursFacadeLocal kursBean;

    // Schnittstelle zur Datenbank für Entitäten vom Typ Thema
    @EJB
    private ThemaFacadeLocal themaBean;

    // Schnittstell zur Datenbank für Entitäten vom Typ Stufe
    @EJB
    private StufeFacadeLocal stufeBean;

    /**
     * Variablen die zur Detailansicht und Bearbeitung eines Kurses dienen.
     */
    
    // Vom Benutzer gewählter Kurs für die Detailansicht oder Bearbeitung
    private Kurs kurs;
    
    // Stufen-ID als String - Stufe die beim Bearbeiten des Kurses neu gesetzt werden soll
    private String stufeNeu;
    
    // Kurs-ID als String - Themengleicher Kurs der beim Bearbeiten eines Kurses neu gesetzt werden soll
    private String themengleichNeu;
    
    // Liste von Schülern die einem Kurs zugewiesen sind
    private List<Schueler> schuelerInKurs;

    /**
     * Variablen die zum Anlegen eines Kurses dienen.
     */
    
    // Titel eines Kurses
    private String titel;
    
    // Kürzel eines Kurses
    private String kuerzel;
    
    // Stufe der Schüler die den Kurs wählen dürfen
    private String stufe;
    
    // Bewertung eines Kurses
    private String bewertung;
    
    // Hinweis eines Kurses
    private String hinweis;
    
    // Maximale Teilnehmerzahl eines Kurses
    private int teilnehmerzahl;
    
    // Ist die Teilnehmerzahl begrenzt oder unbegrenzt
    private boolean teilnehmerUnbegrenzt;
    
    // Kurs-ID als String - Themengleicher Kurs zum Kurs der angelegt wird
    private String themengleich;
    
    // Beschreibung des Kurses
    private String beschreibung;

     /**
     * Variablen die zum Anlegen und Bearbeiten der Unterthemen eines Kurses dienen.
     */
    
    
    // Liste der Unterthemen in einem Kurs
    private List<Eintrag<Thema, Boolean>> themen;

   
    // Bezeichnung eines Themas
    private String bezeichnung;
    
    // Schwerpunkt eines Themas
    private String schwerpunkt;
    
    // Anteil des Themas in Prozent
    private int anteil;

     /**
     * Variablen die für das Anlegen und Bearbeiten eines Kurses genutzt werden.
     */
    
    // Liste aller Kurse ausgeschlossen des gewählten Kurses
    private List<Kurs> alleAnderenKurse;
    
    /**
     * @author Lisa
     * 
     * Diese Methode wird bei Erzeugung der ManagedBean aufgerufen und Initialisiert die Liste der Unterthemen.
     * Beim Anlegen eines Kurses ist der gewählte Kurs 'null' und es gibt noch keine Unterthemen.
     * Beim Bearbeiten eines Kurses werden die Themen die dem Kurs bereits zugewiesen sind in die Variable geschrieben.
     */
    @PostConstruct
    public void init() {
        try {
            this.getGewaehlterKurs();
            if(this.kurs != null){
                Thema[] p_themen = new Thema[kurs.getThema().size()];
                for(int i = 0; i < kurs.getThema().size(); i++){
                    p_themen[i] = kurs.getThema().get(i);
                }
                this.setThemen(new ArrayList<>());
                for(int j = 0; j < p_themen.length; j++){
                    this.getThemen().add(new Eintrag(p_themen[j], false));
                }
                if(kurs.getTeilnehmerzahl() == 999){
                    this.setTeilnehmerUnbegrenzt(true);
                }
            }
        } catch (NullPointerException e) {

        }
    }
    
    
    /**
     * @author Lisa
     * 
     * Diese Methode ruft eine andere Methode der Klasse auf und
     * wird für das Bearbeiten eines Kurses genutzt.
     */
    public void bearbeitenAddThema(){
        Thema t = this.addThema();   
    }
    
    /**
     * @author Lisa
     * @param p_thema Thema wird übergeben
     * 
     * Diese Methode ruft eine andere Methode der Klasse auf und
     * wird für das Bearbeiten eines Kurses genutzt.
     */
    public void bearbeitenRemoveThema(){
        this.removeThema();
    }

    /**
     * @author Lisa
     * 
     * Diese Methode speichert die Änderungen an einem Kurs in der Datenbank.
     * 
     * @return String zur Navigation auf nächste Seite
     */
    public String bearbeiten() {
        // Wenn eine neue Stufe gesetzt wurde wird diese aus der Datenbank gesucht
        if (stufeNeu != null) {
            kurs.setStufe(this.findStufe(stufeNeu));
        }
        
        // Wenn ein neuer Themengleicher Kurs gesetzt wurde wird dieser aus der Datenbank gesucht
        if (themengleichNeu != null) {
            Kurs k = this.findKurs(themengleichNeu);
            kurs.setThemengleich(k);
        }
        
        // Wenn das Häkchen in Teilnehmerzahl-Unbegrenzt gesetzt ist, wird 999 gespeichert
        if(teilnehmerUnbegrenzt){
            kurs.setTeilnehmerzahl(999);
        }
       
        // Iteration über die Themen, die dem Kurs zugewiesen/entfernt werden sollen
        List<Thema> tmpList = new ArrayList<>();
        for (Eintrag e : this.getThemen()) {
            Thema t = (Thema)e.getKey();
            if(t.getId() == null){
                this.themaBean.create(t);
            }         
            tmpList.add(t);
        }
        for(Thema t : kurs.getThema()){
            if(!(tmpList.contains(t))){
                this.themaBean.remove(t);
            }
        }
        kurs.setThema(tmpList);

        this.kursBean.edit(kurs);
        for (Thema t : kurs.getThema()){
            System.out.println("##THEMEN## " + t.getBezeichnung());
        }
        return "kursBearbeitet";
    }

    /**
     * @author Lisa
     * 
     * Diese Methode dient zum Anlegen eines Kurses. Die Variablen, die benötigt werden, werden überprüft.
     * Bei fehlerfreien Angaben wird der Kurs in die Datenbank geschrieben.
     * 
     * @return String zur Navigation auf nächste Seite
     */
    public String anlegen() {       
        String rueckgabe = "kursAngelegt";

        // Aktueller Benutzer und zugehörige Person, hier Lehrer
        Benutzer p_benutzer = (Benutzer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        Lehrer p_lehrer = (Lehrer) p_benutzer.getPerson();

        /**
         * Angegebene Stufe wird aus der Datenbank gesucht.
         * Wenn die Variable p_stufe den Wert 'null' behält gibt es einen Fehler und der Kurs wird nicht angelegt.
         */
        Stufe p_stufe = this.findStufe(stufe);
        if (p_stufe == null) {
            rueckgabe = "Fehler";
            
        }
        
        /**
         * Angegebener Themengleicher Kurs wird aus der Datenbank gesucht.
         * Wenn die Kurs-ID vom Themengleichen Kurs kleiner 0 ist gibt es keinen Themengleichen Kurs.
         * Sollte die Variable p_kurs trotzdem 'null' sein, gibt es einen Fehler und der Kurs kann nicht angelegt werden.
         */
        Kurs p_kurs = this.findKurs(themengleich);
        if (Integer.parseInt(themengleich) > 0 && p_kurs == null) {
            rueckgabe = "Fehler";
            
        }
                
        // Maximale Teilnehmerzahl wird in neuer Variable gespeichert
        int zahlTmp = this.getTeilnehmerzahl();
        
        // Wenn angegeben ist, dass die Teilnehmerzahl unbegrenzt ist wird 999 als Wert gespeichert
        if(this.teilnehmerUnbegrenzt == true){
            this.setTeilnehmerzahl(999);
        }

        // Teilnehmerzahl wird auf Werte kleiner als 0 geprüft, diese sind nicht zulässig. Der Kurs wird dann nicht angelegt.
        if(zahlTmp < 0){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Die Teilnehmerzahl muss größer als 0 sein.");
            rueckgabe = "Fehler";
        }       
     
        if(this.getThemen().isEmpty()){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Bitte legen Sie mindestens 1 Thema an.");
            rueckgabe = "Fehler";
        }
        /**
         * Sollten während der vorherigen Überprüfungen keine Fehler aufgetreten sein, wird der Kurs in der Datenbank angelegt.
         * Außerdem werden die Themen angelegt und dem Kurs hinzugefügt.
         */        
        if (!(rueckgabe.equals("Fehler"))) {
            Kurs kursT = new Kurs();
            kursT.setJahr(new Timestamp(System.currentTimeMillis()));
            kursT.setBewertung(this.getBewertung());
            kursT.setHinweis(this.getHinweis());
            kursT.setKuerzel(this.getKuerzel());
            kursT.setTitel(this.getTitel());
            kursT.setBeschreibung(beschreibung);
            kursT.setLehrer(p_lehrer);
            kursT.setTeilnehmerzahl(this.getTeilnehmerzahl());
            kursT.setThemengleich(p_kurs);
            kursT.setStufe(p_stufe);

            for (Eintrag e : this.getThemen()) {
                this.themaBean.create((Thema) e.getKey());
            }

            List<Thema> tmp = new ArrayList<>();
            for (Eintrag e : this.getThemen()) {
                tmp.add((Thema) e.getKey());
            }
            kursT.setThema(tmp);
            this.kursBean.create(kursT);
        }
        return rueckgabe;
    }

    /**
     * @author Lisa
     *
     * Diese Methode holt den ausgewählten Kurs, der angeschaut oder bearbeitet
     * werden soll, aus dem akutellen Kontext.
     */
    public void getGewaehlterKurs() {
        this.kurs = ((Kurs) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("gewaehlterKurs"));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gewaehlterKurs");
    }

    /**
     * @author Lisa
 
     * @param p_stufe Stufe-ID als String
     * @return Stufe
     * 
     * Diese Methode sucht anhand des übergebenen String eine Stufe aus der Datenbank.
     */
    private Stufe findStufe(String p_stufe) {
        return this.stufeBean.find(Long.parseLong(p_stufe));
    }

    /**
     * @author Lisa
     * @param p_themengleich Kurs-ID als String
     * @return Kurs
     * 
     * Diese Methode sucht anhand des übergebenen String einen Kurs aus der Datenbank.
     */
    private Kurs findKurs(String p_themengleich) {
        int tmp = Integer.parseInt(p_themengleich);
        Kurs k = null;
        if (tmp >= 0) {
            k = this.kursBean.find(Long.parseLong(p_themengleich));
        }
        return k;
    }
    
    /**
     * @author Lisa
     * @return Thema
     * 
     * Diese Methode überprüft die Variablen die für ein Thema benötigt werden. Bei fehlerfreien Angaben
     * wird ein neues Thema erstellt und der Liste der Themen hinzugefügt.
     */
    
    public Thema addThema() {
        System.out.println("de.bws.namedBeans.KursNB.addThema()");
        // Bezeichnung und Schwerpunkt werden auf leeren String überprüft, da dies nicht zulässig ist.
        if(bezeichnung.equals("") || schwerpunkt.equals("")){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Bitte geben Sie eine Bezeichnung und einen Schwerpunkt an.");
            return null;
        }
        
        // Anteil wird auf Werte kleiner gleich 0 geprüft, diese sind nicht zulässig.
        if(anteil <= 0){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Der Anteil muss größer 0 sein.");
            return null;
        }
        
        // Die Anteile der bereits vorhandenen Themen werden summiert.
        int gesamtAnteil = 0;
        if (!this.getThemen().isEmpty()) {
            for (Eintrag e : this.getThemen()) {
                Thema t = (Thema) e.getKey();
                gesamtAnteil += t.getAnteil();
            }
        }
        
        // Der summierte Anteil plus den neuen Anteil dürfen in der Summe nicht größer als 100 sein.
        if ((gesamtAnteil + anteil) <= 100) {
            Thema tmp = new Thema();
            tmp.setAnteil(anteil);
            tmp.setBezeichnung(bezeichnung);
            tmp.setSchwerpunkt(schwerpunkt);
            this.getThemen().add(new Eintrag(tmp, false));
            return tmp;
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Die Summe der Themen-Anteile darf maximal 100 betragen!");
        }
        return null;
    }

    /**
     * @author Lisa
     * 
     * Die ausgewählten Themen werden aus der Liste der Themen entfernt
     */   
    public void removeThema() {
        List<Eintrag> themenToRemove = new ArrayList<>();
        for(Eintrag e : this.themen){
            Thema t = (Thema)e.getKey();
            System.out.println("##Einträge: " + t.getBezeichnung() + " " + (Boolean)e.getValue());
            if((Boolean)e.getValue()){
                System.out.println("######Zu löschen: " + t.getBezeichnung());
                themenToRemove.add(e);
            }
        }
        for(Eintrag e : themenToRemove){
            System.out.println("## Index-of:" + ((Thema)e.getKey()).getBezeichnung());
            this.themen.remove(e);
        }
    }    
    
    //  ################# Getter- und Setter-Methoden. ########################################
     
    /**
     * @return the titel
     */
    public String getTitel() {
        return titel;
    }

    /**
     * @param p_titel the titel to set
     */
    public void setTitel(String p_titel) {
        this.titel = p_titel;
    }

    /**
     * @return the kuerzel
     */
    public String getKuerzel() {
        return kuerzel;
    }

    /**
     * @param p_kuerzel the kuerzel to set
     */
    public void setKuerzel(String p_kuerzel) {
        this.kuerzel = p_kuerzel;
    }

    /**
     * @return the stufe
     */
    public String getStufe() {
        return stufe;
    }

    /**
     * @param p_stufe the stufe to set
     */
    public void setStufe(String p_stufe) {
        this.stufe = p_stufe;
    }

    /**
     * @return the bewertung
     */
    public String getBewertung() {
        return bewertung;
    }

    /**
     * @param p_bewertung the bewertung to set
     */
    public void setBewertung(String p_bewertung) {
        this.bewertung = p_bewertung;
    }

    /**
     * @return the hinweis
     */
    public String getHinweis() {
        return hinweis;
    }

    /**
     * @param p_hinweis the hinweis to set
     */
    public void setHinweis(String p_hinweis) {
        this.hinweis = p_hinweis;
    }

    /**
     * @return the teilnehmerzahl
     */
    public int getTeilnehmerzahl() {
        return teilnehmerzahl;
    }

    /**
     * @param p_teilnehmerzahl the teilnehmerzahl to set
     */
    public void setTeilnehmerzahl(int p_teilnehmerzahl) {
        this.teilnehmerzahl = p_teilnehmerzahl;
    }

    /**
     * @return the themengleich
     */
    public String getThemengleich() {
        return themengleich;
    }

    /**
     * @param p_themengleich the themengleich to set
     */
    public void setThemengleich(String p_themengleich) {
        this.themengleich = p_themengleich;
    }

    /**
     * @return the kurs
     */
    public Kurs getKurs() {
        return kurs;
    }

    /**
     * @param p_kurs the kurs to set
     */
    public void setKurs(Kurs p_kurs) {
        this.kurs = p_kurs;
    }

    /**
     * @return the beschreibung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * @param p_beschreibung the beschreibung to set
     */
    public void setBeschreibung(String p_beschreibung) {
        this.beschreibung = p_beschreibung;
    }

    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * @param p_bezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String p_bezeichnung) {
        this.bezeichnung = p_bezeichnung;
    }

    /**
     * @return the schwerpunkt
     */
    public String getSchwerpunkt() {
        return schwerpunkt;
    }

    /**
     * @param p_schwerpunkt the schwerpunkt to set
     */
    public void setSchwerpunkt(String p_schwerpunkt) {
        this.schwerpunkt = p_schwerpunkt;
    }

    /**
     * @return the anteil
     */
    public int getAnteil() {
        return anteil;
    }

    /**
     * @param p_anteil the anteil to set
     */
    public void setAnteil(int p_anteil) {
        this.anteil = p_anteil;
    }

    /**
     * @return the themen
     */
    public List<Eintrag<Thema, Boolean>> getThemen() {
        if(this.themen == null){
            themen = new ArrayList<>();
        }
        return themen;
    }

    /**
     * @param themen the themen to set
     */
    public void setThemen(List<Eintrag<Thema, Boolean>> themen) {
        this.themen = themen;
    }



    /**
     * @return the stufeNeu
     */
    public String getStufeNeu() {
        return stufeNeu;
    }

    /**
     * @param p_stufeNeu the stufeNeu to set
     */
    public void setStufeNeu(String p_stufeNeu) {
        this.stufeNeu = p_stufeNeu;
    }

    /**
     * @return the themengleichNeu
     */
    public String getThemengleichNeu() {
        return themengleichNeu;
    }

    /**
     * @param p_themengleichNeu the themengleichNeu to set
     */
    public void setThemengleichNeu(String p_themengleichNeu) {
        this.themengleichNeu = p_themengleichNeu;
    }

    /**
     * @author Lisa
     * @return the alleAnderenKurse
     * Die Kurse werden aus der Datenbank gesucht und der gewählte Kurs wird herausgefiltert.
     */
    public List<Kurs> getAlleAnderenKurse() {
        List<Kurs> tmpList = this.kursBean.findAll();
        int index = tmpList.indexOf(this.getKurs());
        tmpList.remove(index);
        this.alleAnderenKurse = tmpList;
        return alleAnderenKurse;
    }

    /**
     * @param p_alleAnderenKurse the alleAnderenKurse to set
     */
    public void setAlleAnderenKurse(List<Kurs> p_alleAnderenKurse) {
        this.alleAnderenKurse = p_alleAnderenKurse;
    }

    /**
     * @author Lisa
     * @return the schuelerInKurs
     * Die Teilnehmer des gewählten Kurses werden gesetzt.
     */
    public List<Schueler> getSchuelerInKurs() {
        List<Schueler> tmp = this.kurs.getTeilnehmer();
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.schuelerInKurs = tmp;
        return this.schuelerInKurs;
    }

    /**
     * @param p_schuelerInKurs the schuelerInKurs to set
     */
    public void setSchuelerInKurs(List<Schueler> p_schuelerInKurs) {
        this.schuelerInKurs = p_schuelerInKurs;
    }

    /**
     * @return the teilnehmerUnbegrenzt
     */
    public boolean isTeilnehmerUnbegrenzt() {
        return teilnehmerUnbegrenzt;
    }

    /**
     * @param p_teilnehmerUnbegrenzt the teilnehmerUnbegrenzt to set
     */
    public void setTeilnehmerUnbegrenzt(boolean p_teilnehmerUnbegrenzt) {
        this.teilnehmerUnbegrenzt = p_teilnehmerUnbegrenzt;
    }


}
