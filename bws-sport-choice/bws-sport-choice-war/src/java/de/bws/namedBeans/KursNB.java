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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lisa
 */
@ManagedBean
//@Named(value = "kursNB")
@SessionScoped
public class KursNB implements Serializable{

    /**
     * Creates a new instance of KursNB
     */
    public KursNB() {
    }
    
     @EJB
    private KursFacadeLocal kursBean;
    
    private String titel;
    private String kuerzel;
    private Stufe stufe;
    private String bewertung;
    private String hinweis;
    private int teilnehmerzahl;
    private Kurs themengleich;
//    private List<Thema> thema;
    
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
    
    
    
}
