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
import de.bws.sessionbeans.StufeFacadeLocal;
import de.bws.sessionbeans.ThemaFacadeLocal;
import java.io.Serializable;
import java.sql.Timestamp;
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
public class KursNB implements Serializable{

    
     @EJB
    private KursFacadeLocal kursBean;
     
     @EJB
     private ThemaFacadeLocal themaBean;
     
     @EJB
     private StufeFacadeLocal stufeBean;
     
     @Inject
     private MenueNB menueNB;
     
    
    private Kurs kurs;
     
    private String titel;
    private String kuerzel;
    private String stufe;
    private String bewertung;
    private String hinweis;
    private int teilnehmerzahl;
    private String themengleich;
    private String beschreibung;
    
    private String bezeichnung;
    private String schwerpunkt;
    private int anteil;
    
    
//    private List<Thema> thema;
    
    @PostConstruct
    public void init(){
        try{
            this.getGewaehlterKurs();
        }catch(NullPointerException e){

        }
    }
    
    public void bearbeiten(){
        System.out.println("de.bws.namedBeans.KursNB.bearbeiten()");
        kurs.setJahr(new Timestamp(System.currentTimeMillis()));
        kurs.setBewertung(kurs.getBewertung());
        kurs.setHinweis(kurs.getHinweis());
        kurs.setKuerzel(kurs.getKuerzel());
        kurs.setTeilnehmerzahl(kurs.getTeilnehmerzahl());
        kurs.setTitel(kurs.getTitel());       
        kurs.setBeschreibung(kurs.getBeschreibung());
        //kurs.setLehrer((Lehrer)this.menueNB.getB().getPerson());
        this.kursBean.edit(kurs);
    }
    
    public void anlegen(){
        System.out.println("de.bws.namedBeans.KursNB.anlegen()");
        
        Stufe p_stufe = this.findStufe(stufe);
        
        Kurs p_kurs = this.findKurs(themengleich);
       
        Thema thema =  new Thema();
        if (bezeichnung != null) {
            thema.setAnteil(anteil);
            thema.setBezeichnung(bezeichnung);
            thema.setSchwerpunkt(schwerpunkt);
            this.themaBean.create(thema);
        }
      
        Kurs kursT = new Kurs();
        kursT.setJahr(new Timestamp(System.currentTimeMillis()));
        kursT.setBewertung(this.getBewertung());
        kursT.setHinweis(this.getHinweis());
        kursT.setKuerzel(this.getKuerzel());
        kursT.setTeilnehmerzahl(this.getTeilnehmerzahl());
        kursT.setTitel(this.getTitel());       
        kursT.setBeschreibung(beschreibung);
        
        if(p_kurs != null){
            kursT.setThemengleich(p_kurs);
        }
        
        if(p_stufe != null){
            kursT.setStufe(p_stufe);
        }
        
        if(thema.getBezeichnung() != null){
            kursT.addThema(thema);
        }
        this.kursBean.create(kursT);      
    } 
    
    public void getGewaehlterKurs(){
        this.kurs = ((Kurs) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("gewaehlterKurs"));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gewaehlterKurs");
    }

    
    private Stufe findStufe(String p_stufe){
        return this.stufeBean.find(Long.parseLong(p_stufe));
    }
    
    private Kurs findKurs(String p_themengleich){
        int tmp = Integer.parseInt(p_themengleich);
        if(tmp >= 0){
            return this.kursBean.find(Long.parseLong(p_themengleich));
        }else{
            return null;
        }
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
    
    
    
}
