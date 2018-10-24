/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.faces.component.UIData;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lisa
 */
@Named(value = "kursZuweisenNB")
@ViewScoped
public class KursZuweisenNB implements Serializable{

    @EJB
    private KursFacadeLocal kursBean;
    
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    @EJB
    private StufeFacadeLocal stufeBean;
    
    private String error;
    
    private List<Schueler> schueler;
    private List<Kurs> stufekurs;
    private String schuelerId;
    private String kursId;
    
    private int schuelerEins;
    private int schuelerZwei;
    
    private List<Eintrag<Schueler, Kurs>> zuordnung;
    
    /**
     * Creates a new instance of KursZuweisenNB
     */
    public KursZuweisenNB() {
    }
    
    @PostConstruct
    public void init(){
        this.zuordnung = new ArrayList<>();
        this.setZuordnung(this.getSchueler());
    }
    
    public void zuweisen(){       
        for(Eintrag e : this.zuordnung){
            Schueler p_schueler = (Schueler) e.getKey();                        
            String kursId = (e.getValue().toString().split("="))[1].replace("]", "").trim();

            Kurs kursNeu = this.kursBean.find(Long.parseLong(kursId));
            if(kursNeu != null){
                Kurs kursAlt = this.getAktuellerKurs(p_schueler.getId());

                if(kursAlt != null){
                    Kurs tmp = this.kursBean.find(kursAlt.getId());
                    tmp.removeTeilnehmer(p_schueler);
                    this.kursBean.edit(tmp);
                }
                kursNeu.addTeilnehmer(p_schueler);
                this.kursBean.edit(kursNeu);
                
            }           
        }
        
        
        
    }
    
    public void einzelZuweisen(){
//        Schueler p_schueler = this.schuelerBean.find(Long.parseLong(schuelerId));
//        
//        System.out.println(kursId);
//        System.out.println(p_schueler.getNachname());
//        Kurs tmpKurs;
//        Schueler tmpSchueler;
//        tmpKurs = this.kursBean.find(Long.parseLong(kursId));
//        tmpSchueler = this.schuelerBean.find(p_schueler.getId());
//        
//        if(tmpKurs != null && tmpSchueler != null){
//            tmpKurs.addTeilnehmer(p_schueler);
//            this.kursBean.edit(tmpKurs);
//        }
//        
//        Kurs aktKurs = this.getAktuellerKurs(p_schueler);
//        System.out.println("aktueller kurs: " + aktKurs.getTitel());
//        if(!(aktKurs.getTitel().equals("-"))){
//            tmpKurs = this.kursBean.find(aktKurs.getId());
//            tmpKurs.removeTeilnehmer(p_schueler);
//            this.kursBean.edit(tmpKurs);
//        }
        

    }
    
    public String wechsel(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String rueckgabe = "kursWechseln";
        
        Schueler eins = this.schuelerBean.find((long)schuelerEins);
        Schueler zwei = this.schuelerBean.find((long)schuelerZwei);
        
        Kurs kE = null;
        Kurs kZ = null;
        
        List<Kurs> kEins = this.kursBean.get("SELECT k FROM Kurs k INNER JOIN k.teilnehmer t WHERE t.id = " + schuelerEins);
        List<Kurs> kZwei = this.kursBean.get("SELECT k FROM Kurs k INNER JOIN k.teilnehmer t WHERE t.id = " + schuelerZwei);
        
        
        
        if((kEins != null && !(kEins.isEmpty())) ){
            kE = kEins.get(0);
        }else{
            sessionMap.put("lastError", "Schüler/in: " + eins.getNachname() +", " + eins.getVorname() + " ist noch keinem Kurs zugewiesen. Ein Kurswechsel ist nicht möglich");
            rueckgabe = "wechselFehlgeschlagen";
        }
        if((kZwei != null && !(kZwei.isEmpty())) ){
            kZ = kZwei.get(0);
        }else{
            sessionMap.put("lastError", "Schüler/in: " + zwei.getNachname() +", " + zwei.getVorname() + " ist noch keinem Kurs zugewiesen. Ein Kurswechsel ist nicht möglich.");
            rueckgabe = "wechselFehlgeschlagen";
        }
        
        
        if(eins.getId().compareTo(zwei.getId()) == 0){
            sessionMap.put("lastError", "Bitten geben Sie zwei verschiedene Schüler an");
            rueckgabe = "wechselFehlgeschlagen";
        }else{
            if(kE != null && kZ != null){
                kE.removeTeilnehmer(eins);
                kZ.addTeilnehmer(eins);
                kZ.removeTeilnehmer(zwei);
                kE.addTeilnehmer(zwei);
                this.kursBean.edit(kE);
                this.kursBean.edit(kZ);
            } 
        }
        
        return rueckgabe;
        
    }
   
    public Kurs getAktuellerKurs(long p_schuelerId){
        Kurs gesucht = null;
        List<Kurs> tmp = this.kursBean.get("SELECT k FROM Kurs k");
        for(Kurs k : tmp){
            for(Schueler s : k.getTeilnehmer()){
                if(s.getId().compareTo(p_schuelerId) == 0){
                    gesucht = k;
                }
            }
        }
        if(gesucht == null){
            gesucht = new Kurs();
            gesucht.setTitel("-");
        }
        
        return gesucht;
    }
    
    /**
     * @return the schueler
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
     * @param schueler the schueler to set
     */
    public void setSchueler(List<Schueler> schueler) {
        this.schueler = schueler;
    }

    /**
     * @return the schuelerId
     */
    public String getSchuelerId() {
        return schuelerId;
    }

    /**
     * @param schuelerId the schuelerId to set
     */
    public void setSchuelerId(String schuelerId) {
        this.schuelerId = schuelerId;
    }

    /**
     * @return the kursId
     */
    public String getKursId() {
        return kursId;
    }

    /**
     * @param kursId the kursId to set
     */
    public void setKursId(String kursId) {
        this.kursId = kursId;
    }

    /**
     * @param p_schueler
     * @return the kurs
     */
    public List<Kurs> getStufeKurs(long p_schuelerId) {
        Schueler p_schueler = this.schuelerBean.find(p_schuelerId);
        
        Stufe stufe = this.stufeBean.find(p_schueler.getStufe().getId());
        List<Kurs> tmp = this.kursBean.get("SELECT k FROM Kurs k WHERE k.stufe.id = " + stufe.getId() );
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.stufekurs = tmp;
        return this.stufekurs;
    }

    /**
     * @param kurs the kurs to set
     */
    public void setStufeKurs(List<Kurs> kurs) {
        this.stufekurs = kurs;
    }

    /**
     * @return the schuelerEins
     */
    public int getSchuelerEins() {
        return schuelerEins;
    }

    /**
     * @param schuelerEins the schuelerEins to set
     */
    public void setSchuelerEins(int schuelerEins) {
        this.schuelerEins = schuelerEins;
    }

    /**
     * @return the schuelerZwei
     */
    public int getSchuelerZwei() {
        return schuelerZwei;
    }

    /**
     * @param schuelerZwei the schuelerZwei to set
     */
    public void setSchuelerZwei(int schuelerZwei) {
        this.schuelerZwei = schuelerZwei;
    }

    /**
     * @return the zuordnung
     */
    public List<Eintrag<Schueler, Kurs>> getZuordnung() {
        return zuordnung;
    }

    /**
     * @param p_schueler the zuordnung to set
     */
    public void setZuordnung(List<Schueler> p_schueler) {
        this.zuordnung.clear();
        for(Schueler s : p_schueler){
            this.zuordnung.add(new Eintrag(s, getAktuellerKurs(s.getId())));
        }
    }

    
}
