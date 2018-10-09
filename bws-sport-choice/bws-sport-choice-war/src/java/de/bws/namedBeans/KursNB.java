/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Kurs;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.KursFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lisa
 */
@Named(value = "kursNB")
@ViewScoped
public class KursNB implements Serializable{

    /**
     * Creates a new instance of KursNB
     */
    public KursNB() {
    }
    
     @EJB
    private KursFacadeLocal kursBean;
    
    private Kurs kurs;
     
    private String titel;
    private String kuerzel;
    private Stufe stufe;
    private String bewertung;
    private String hinweis;
    private int teilnehmerzahl;
    private Kurs themengleich;
//    private List<Thema> thema;
    
    @PostConstruct
    public void init(){
        try{
            this.getGewaehlterKurs();
        }catch(NullPointerException e){

        }
    }
    
    public void anlegen(){
        System.out.println("de.bws.namedBeans.KursNB.anlegen()");
        Kurs kurs = new Kurs();
        kurs.setBewertung(this.getBewertung());
        kurs.setHinweis(this.getHinweis());
        kurs.setKuerzel(this.getKuerzel());
        kurs.setTeilnehmerzahl(this.getTeilnehmerzahl());
        kurs.setTitel(this.getTitel());
        this.kursBean.create(kurs);
    } 
    
    public void getGewaehlterKurs(){
        this.kurs = ((Kurs) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("gewaehlterKurs"));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gewaehlterKurs");
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
    public Stufe getStufe() {
        return stufe;
    }

    /**
     * @param stufe the stufe to set
     */
    public void setStufe(Stufe stufe) {
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
    public Kurs getThemengleich() {
        return themengleich;
    }

    /**
     * @param themengleich the themengleich to set
     */
    public void setThemengleich(Kurs themengleich) {
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
    
    
    
}
