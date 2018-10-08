/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Kurs;
import de.bws.entities.Stufe;
import de.bws.entities.Thema;
import de.bws.sessionbeans.KursFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Lisa
 */
@Named("kursBean")
@ViewScoped
public class KursNB implements Serializable {
    @EJB
    private KursFacadeLocal kursBean;
    
    private String titel;
    private String kuerzel;
    private Stufe stufe;
    private String bewertung;
    private String hinweis;
    private int teilnehmerzahl;
    private Kurs themengleich;
    private List<Thema> thema;
    
    public boolean anlegen(){
        Kurs kurs = new Kurs();
        kurs.setBewertung(this.bewertung);
        kurs.setHinweis(this.hinweis);
        kurs.setKuerzel(this.kuerzel);
        kurs.setStufe(this.stufe);
        kurs.setTeilnehmerzahl(this.teilnehmerzahl);
        kurs.setThema(this.thema);
        kurs.setTitel(this.titel);
        this.kursBean.create(kurs);
        return true;
    } 

    /**
     * @return the titel
     */
    public String getTitel() {
        return titel;
    }

    /**
     * @return the kuerzel
     */
    public String getKuerzel() {
        return kuerzel;
    }

    /**
     * @return the stufe
     */
    public Stufe getStufe() {
        return stufe;
    }

    /**
     * @return the bewertung
     */
    public String getBewertung() {
        return bewertung;
    }

    /**
     * @return the hinweis
     */
    public String getHinweis() {
        return hinweis;
    }

    /**
     * @return the teilnehmerzahl
     */
    public int getTeilnehmerzahl() {
        return teilnehmerzahl;
    }

    /**
     * @return the themengleich
     */
    public Kurs getThemengleich() {
        return themengleich;
    }

    /**
     * @return the thema
     */
    public List<Thema> getThema() {
        return thema;
    }
    
    
    
}
