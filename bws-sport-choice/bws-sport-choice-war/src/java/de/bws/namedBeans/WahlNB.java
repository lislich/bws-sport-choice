/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.entities.Kurs;
import de.bws.entities.Person;
import de.bws.entities.Schueler;
import de.bws.entities.Wahl;
import de.bws.entities.Wahlzeitraum;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import de.bws.sessionbeans.WahlFacadeLocal;
import de.bws.sessionbeans.WahlzeitraumFacadeLocal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lisa
 */
@Named(value = "wahlNB")
@ViewScoped
public class WahlNB implements Serializable{

    @EJB
    private WahlFacadeLocal wahlBean;
    
    @EJB
    private WahlzeitraumFacadeLocal wahlzeitraumBean;
    
    @EJB
    private KursFacadeLocal kursBean;
    
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    private Date beginn;
    private Date ende;
    
    private String ersteWahl;
    private String zweiteWahl;
    private String dritteWahl;
    
    private double auslastung;
    
    @PostConstruct
    public void init(){
        Schueler sTmp = (Schueler)((Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer")).getPerson();
        Wahlzeitraum tmp;
        try{
            tmp = wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz").get(0);
            if (tmp.getBeginn() != null) {
                this.setBeginn(tmp.getBeginn());
            }
            if (tmp.getEnde() != null) {
                this.setEnde(tmp.getEnde());
            }
            
            Wahl wTmp = sTmp.getWahl();
            if(wTmp != null){
                 this.setErsteWahl(wTmp.getErstwahl().getId().toString());
                 this.setZweiteWahl(wTmp.getZweitwahl().getId().toString());
                 this.setDritteWahl(wTmp.getDrittwahl().getId().toString());
            }
        }catch(Exception e){
            
        }
        
    }
    
    public void saveDatum() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (ende.getTime() < timestamp.getTime()) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Das Enddatum darf nicht in der Vergangenheit liegen");
        } else {
            if (beginn.after(ende)) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Das Beginndatum muss kleiner als das Enddatum sein.");
            } else {
                List<Wahlzeitraum> tmpList;
                tmpList = wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz");
                if (tmpList.isEmpty()) {
                    Wahlzeitraum tmp = new Wahlzeitraum();
                    tmp.setBeginn(getBeginn());
                    tmp.setEnde(getEnde());
                    this.wahlzeitraumBean.create(tmp);
                } else {
                    System.out.println("edit");
                    Wahlzeitraum tmp = tmpList.get(0);
                    tmp.setBeginn(beginn);
                    tmp.setEnde(ende);
                    this.wahlzeitraumBean.edit(tmp);
                }
            }
        }

    }

    
    public String saveWahl(){
        String rueckgabe = "Fehler";
        Benutzer b = (Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        Schueler s  = (Schueler)b.getPerson();
        if (ersteWahl.equals(zweiteWahl) || ersteWahl.equals(dritteWahl) || zweiteWahl.equals(dritteWahl)) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Bitte wählen Sie unterschiedliche Kurse.");
        } else {
            Kurs p_eins = this.kursBean.find(Long.parseLong(getErsteWahl()));
            Kurs p_zwei = this.kursBean.find(Long.parseLong(getZweiteWahl()));
            Kurs p_drei = this.kursBean.find(Long.parseLong(getDritteWahl()));

            Wahl wahl = s.getWahl();
            if (wahl == null) {
                wahl = new Wahl();
                wahl.setErstwahl(p_eins);
                wahl.setZweitwahl(p_zwei);
                wahl.setDrittwahl(p_drei);
                this.wahlBean.create(wahl);
            }else{
                wahl.setErstwahl(p_eins);
                wahl.setZweitwahl(p_zwei);
                wahl.setDrittwahl(p_drei);
                this.wahlBean.edit(wahl);
            }

            s.setWahl(wahl);
            this.schuelerBean.edit(s);
            
            rueckgabe = "gewaehlt";
        }

        return rueckgabe;
    }



    /**
     * @return the beginn
     */
    public Date getBeginn() {
        return beginn;
    }

    /**
     * @param beginn the beginn to set
     */
    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    /**
     * @return the ende
     */
    public Date getEnde() {
        return ende;
    }

    /**
     * @param ende the ende to set
     */
    public void setEnde(Date ende) {
        this.ende = ende;
    }

    /**
     * @return the ersteWahl
     */
    public String getErsteWahl() {
        return ersteWahl;
    }

    /**
     * @param ersteWahl the ersteWahl to set
     */
    public void setErsteWahl(String ersteWahl) {
        this.ersteWahl = ersteWahl;
    }

    /**
     * @return the zweiteWahl
     */
    public String getZweiteWahl() {
        return zweiteWahl;
    }

    /**
     * @param zweiteWahl the zweiteWahl to set
     */
    public void setZweiteWahl(String zweiteWahl) {
        this.zweiteWahl = zweiteWahl;
    }

    /**
     * @return the dritteWahl
     */
    public String getDritteWahl() {
        return dritteWahl;
    }

    /**
     * @param dritteWahl the dritteWahl to set
     */
    public void setDritteWahl(String dritteWahl) {
        this.dritteWahl = dritteWahl;
    }

    /**
     * @return the auslastung
     */
    public double getAuslastung(Kurs p_kurs) {
        Kurs tmp = this.kursBean.find(p_kurs.getId());
        double anzahlTeilnehmer = (double)tmp.getTeilnehmerzahl();
        double anzahlGewaehlt = 0.0;
        List<Wahl> gewaehlt = this.wahlBean.get("SELECT w FROM Wahl w WHERE w.erstwahl.id = " + tmp.getId());
        if(gewaehlt != null){
            System.out.println("#Size# " + gewaehlt.size());
            anzahlGewaehlt = gewaehlt.size();
        }
        if(anzahlGewaehlt != 0){
            System.out.println("#Rechnung#");
            auslastung = (anzahlGewaehlt/anzahlTeilnehmer)*100;
        }else{
            auslastung = anzahlGewaehlt;
        }
        
        return auslastung;
    }

    /**
     * @param auslastung the auslastung to set
     */
    public void setAuslastung(double auslastung) {
        this.auslastung = auslastung;
    }

    
}
