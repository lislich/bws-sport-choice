/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

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
import javax.inject.Inject;

/**
 *
 * @author Lisa
 */
@Named(value = "kursNB")
@ViewScoped
public class KursNB implements Serializable {

    @EJB
    private KursFacadeLocal kursBean;

    @EJB
    private ThemaFacadeLocal themaBean;

    @EJB
    private StufeFacadeLocal stufeBean;


    private Kurs kurs;
    private String stufeNeu;
    private String themengleichNeu;
    private List<Schueler> schuelerInKurs;

    private String titel;
    private String kuerzel;
    private String stufe;
    private String bewertung;
    private String hinweis;
    private int teilnehmerzahl;
    private boolean teilnehmerUnbegrenzt;
    private String themengleich;
    private String beschreibung;

    private List<Thema> themen;

    private String bezeichnung;
    private String schwerpunkt;
    private int anteil;

    private List<Kurs> alleAnderenKurse;
    
    @PostConstruct
    public void init() {
        try {
            this.getGewaehlterKurs();
            if(this.kurs != null){
                this.setThemen(kurs.getThema());
                for(Thema tmp : themen){
                    System.out.println("##THEMEN## " + tmp.getBezeichnung());
                }
            }
        } catch (NullPointerException e) {

        }
    }
    
    public void bearbeitenAddThema(){
        Thema t = this.addThema();   
    }
    
    public void bearbeitenRemoveThema(Thema t){
        this.removeThema(t);
    }

    public String bearbeiten() {
        kurs.setJahr(new Timestamp(System.currentTimeMillis()));
        if (stufeNeu != null) {
            kurs.setStufe(this.findStufe(stufeNeu));
        }
        if (themengleichNeu != null) {
            Kurs k = this.findKurs(themengleichNeu);
            kurs.setThemengleich(k);
        }
       
        for(Thema t : themen){
            System.out.println("Bearbeitet: " + t.getBezeichnung());
            if(t.getId() == null){
                this.themaBean.create(t);
            }else{
                this.themaBean.find(t.getId());
            }           
        }
        
        for(Thema p : kurs.getThema()){
            System.out.println("Kursthemen: " + p.getBezeichnung());
        }
        this.kursBean.edit(kurs);
        return "kursBearbeitet";
    }

    public String anlegen() {       
        String rueckgabe = "kursAngelegt";

        Benutzer p_benutzer = (Benutzer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        Lehrer p_lehrer = (Lehrer) p_benutzer.getPerson();

        Stufe p_stufe = this.findStufe(stufe);

        Kurs p_kurs = this.findKurs(themengleich);
        int zahlTmp = this.getTeilnehmerzahl();
        
        if(this.teilnehmerUnbegrenzt == true){
            this.setTeilnehmerzahl(999);
        }

        if(zahlTmp < 0){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Die Teilnehmerzahl muss größer als 0 sein.");
            rueckgabe = "Fehler";
        }       

        if (Integer.parseInt(themengleich) > 0 && p_kurs == null) {
            rueckgabe = "Fehler";
            
        }

        if (p_stufe == null) {
            rueckgabe = "Fehler";
            
        }

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

            for (Thema t : themen) {
                this.themaBean.create(t);
            }
            kursT.setThema(themen);
            this.kursBean.create(kursT);           
        }

        return rueckgabe;
    }

    public void getGewaehlterKurs() {
        this.kurs = ((Kurs) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("gewaehlterKurs"));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gewaehlterKurs");
    }

    private Stufe findStufe(String p_stufe) {
        return this.stufeBean.find(Long.parseLong(p_stufe));
    }

    private Kurs findKurs(String p_themengleich) {
        int tmp = Integer.parseInt(p_themengleich);
        Kurs k = null;
        if (tmp >= 0) {
            k = this.kursBean.find(Long.parseLong(p_themengleich));
        }
        return k;
    }

    /**
     * @return the titel
     */
    public String getTitel() {
        return titel;
    }

    /**
     * @param titel the titel to set
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * @return the kuerzel
     */
    public String getKuerzel() {
        return kuerzel;
    }

    /**
     * @param kuerzel the kuerzel to set
     */
    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    /**
     * @return the stufe
     */
    public String getStufe() {
        return stufe;
    }

    /**
     * @param stufe the stufe to set
     */
    public void setStufe(String stufe) {
        this.stufe = stufe;
    }

    /**
     * @return the bewertung
     */
    public String getBewertung() {
        return bewertung;
    }

    /**
     * @param bewertung the bewertung to set
     */
    public void setBewertung(String bewertung) {
        this.bewertung = bewertung;
    }

    /**
     * @return the hinweis
     */
    public String getHinweis() {
        return hinweis;
    }

    /**
     * @param hinweis the hinweis to set
     */
    public void setHinweis(String hinweis) {
        this.hinweis = hinweis;
    }

    /**
     * @return the teilnehmerzahl
     */
    public int getTeilnehmerzahl() {
        return teilnehmerzahl;
    }

    /**
     * @param teilnehmerzahl the teilnehmerzahl to set
     */
    public void setTeilnehmerzahl(int teilnehmerzahl) {
        this.teilnehmerzahl = teilnehmerzahl;
    }

    /**
     * @return the themengleich
     */
    public String getThemengleich() {
        return themengleich;
    }

    /**
     * @param themengleich the themengleich to set
     */
    public void setThemengleich(String themengleich) {
        this.themengleich = themengleich;
    }

    /**
     * @return the kurs
     */
    public Kurs getKurs() {
        return kurs;
    }

    /**
     * @param kurs the kurs to set
     */
    public void setKurs(Kurs kurs) {
        this.kurs = kurs;
    }

    /**
     * @return the beschreibung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * @param beschreibung the beschreibung to set
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * @param bezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * @return the schwerpunkt
     */
    public String getSchwerpunkt() {
        return schwerpunkt;
    }

    /**
     * @param schwerpunkt the schwerpunkt to set
     */
    public void setSchwerpunkt(String schwerpunkt) {
        this.schwerpunkt = schwerpunkt;
    }

    /**
     * @return the anteil
     */
    public int getAnteil() {
        return anteil;
    }

    /**
     * @param anteil the anteil to set
     */
    public void setAnteil(int anteil) {
        this.anteil = anteil;
    }

    /**
     * @return the themen
     */
    public List<Thema> getThemen() {
        if (themen == null) {
            themen = new ArrayList<>();
        }
        return themen;
    }

    /**
     * @param themen the themen to set
     */
    public void setThemen(List<Thema> themen) {
        this.themen = themen;
    }

    public Thema addThema() {
        if(bezeichnung.equals("") || schwerpunkt.equals("")){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Bitte geben Sie eine Bezeichnung und einen Schwerpunkt an.");
            return null;
        }
        if(anteil <= 0){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Der Anteil muss größer 0 sein.");
            return null;
        }
        
        int gesamtAnteil = 0;
        if (!this.getThemen().isEmpty()) {
            for (Thema t : this.getThemen()) {
                gesamtAnteil += t.getAnteil();
            }
        }
        if ((gesamtAnteil + anteil) <= 100) {
            Thema tmp = new Thema();
            tmp.setAnteil(anteil);
            tmp.setBezeichnung(bezeichnung);
            tmp.setSchwerpunkt(schwerpunkt);
            this.themen.add(tmp);
            return tmp;
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Die Summe der Themen-Anteile darf maximal 100 betragen!");
        }
        return null;
    }

    public void removeThema(Thema p_thema) {
        this.themen.remove(p_thema);
    }

    /**
     * @return the stufeNeu
     */
    public String getStufeNeu() {
        return stufeNeu;
    }

    /**
     * @param stufeNeu the stufeNeu to set
     */
    public void setStufeNeu(String stufeNeu) {
        this.stufeNeu = stufeNeu;
    }

    /**
     * @return the themengleichNeu
     */
    public String getThemengleichNeu() {
        return themengleichNeu;
    }

    /**
     * @param themengleichNeu the themengleichNeu to set
     */
    public void setThemengleichNeu(String themengleichNeu) {
        this.themengleichNeu = themengleichNeu;
    }

    /**
     * @return the alleAnderenKurse
     */
    public List<Kurs> getAlleAnderenKurse() {
        List<Kurs> tmpList = this.kursBean.findAll();
        int index = tmpList.indexOf(this.getKurs());
        tmpList.remove(index);
        this.alleAnderenKurse = tmpList;
        return alleAnderenKurse;
    }

    /**
     * @param alleAnderenKurse the alleAnderenKurse to set
     */
    public void setAlleAnderenKurse(List<Kurs> alleAnderenKurse) {
        this.alleAnderenKurse = alleAnderenKurse;
    }

    /**
     * @return the schuelerInKurs
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
     * @param schuelerInKurs the schuelerInKurs to set
     */
    public void setSchuelerInKurs(List<Schueler> schuelerInKurs) {
        this.schuelerInKurs = schuelerInKurs;
    }

    /**
     * @return the teilnehmerUnbegrenzt
     */
    public boolean isTeilnehmerUnbegrenzt() {
        return teilnehmerUnbegrenzt;
    }

    /**
     * @param teilnehmerUnbegrenzt the teilnehmerUnbegrenzt to set
     */
    public void setTeilnehmerUnbegrenzt(boolean teilnehmerUnbegrenzt) {
        this.teilnehmerUnbegrenzt = teilnehmerUnbegrenzt;
    }


}
