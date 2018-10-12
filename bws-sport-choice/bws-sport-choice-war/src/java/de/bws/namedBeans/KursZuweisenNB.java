/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Kurs;
import de.bws.entities.Person;
import de.bws.entities.Schueler;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
    
    private String error;
    
    private List<Schueler> schueler;
    private List<Kurs> kurs;
    private int schuelerId;
    private int kursId;
    
    private int schuelerEins;
    private int schuelerZwei;
    
    /**
     * Creates a new instance of KursZuweisenNB
     */
    public KursZuweisenNB() {
    }
    
    @PostConstruct
    public void init(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if(sessionMap.get("lastError") != null){
            this.setError(sessionMap.get("lastError").toString());
            sessionMap.remove("lastError");
        }
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
        
        
        
        if(kEins != null){
            kE = kEins.get(0);
        }else{
            sessionMap.put("lastError", "Schüler: " + eins + " ist noch keinem Kurs zugewiesen.");
            rueckgabe = "wechselFehlgeschlagen";
        }
        if(kZwei != null){
            kZ = kZwei.get(0);
        }else{
            sessionMap.put("lastError", "Schüler: " + zwei + " ist noch keinem Kurs zugewiesen.");
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
   
    /**
     * @return the schueler
     */
    public List<Schueler> getSchueler() {        
        return this.schuelerBean.findAll();
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
    public int getSchuelerId() {
        return schuelerId;
    }

    /**
     * @param schuelerId the schuelerId to set
     */
    public void setSchuelerId(int schuelerId) {
        this.schuelerId = schuelerId;
    }

    /**
     * @return the kursId
     */
    public int getKursId() {
        return kursId;
    }

    /**
     * @param kursId the kursId to set
     */
    public void setKursId(int kursId) {
        this.kursId = kursId;
    }

    /**
     * @return the kurs
     */
    public List<Kurs> getKurs() {
        return this.kursBean.findAll();
    }

    /**
     * @param kurs the kurs to set
     */
    public void setKurs(List<Kurs> kurs) {
        this.kurs = kurs;
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
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
    
    
    
}
