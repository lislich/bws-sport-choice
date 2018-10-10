/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.entities.Kurs;
import de.bws.entities.Wahl;
import de.bws.entities.Wahlzeitraum;
import de.bws.sessionbeans.WahlFacadeLocal;
import de.bws.sessionbeans.WahlzeitraumFacadeLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    
    private Date beginn;
    private Date ende;
    
    private Kurs ersteWahl;
    private Kurs zweiteWahl;
    private Kurs dritteWahl;
    
    @PostConstruct
    public void init(){
        Wahlzeitraum tmp;
        try{
            tmp = wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz").get(0);
            System.out.println(tmp.getBeginn().toString());
            if (tmp.getBeginn() != null) {
                this.setBeginn(tmp.getBeginn());
            }
            if (tmp.getEnde() != null) {
                this.setEnde(tmp.getEnde());
            }
        }catch(Exception e){
            
        }
        
    }
    
    public void saveDatum(){
        List<Wahlzeitraum> tmpList;
        tmpList = wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz");
        if(tmpList.isEmpty()){
            Wahlzeitraum tmp = new Wahlzeitraum();
            tmp.setBeginn(getBeginn());
            tmp.setEnde(getEnde());
            this.wahlzeitraumBean.create(tmp);
        }else{
            System.out.println("edit");
            Wahlzeitraum tmp = tmpList.get(0);
            this.wahlzeitraumBean.edit(tmp);
        }
    }
    
    
    public String saveWahl(){
        System.out.println("de.bws.namedBeans.WahlNB.saveWahl()");
        Wahl wahl = new Wahl();
        wahl.setErstwahl(ersteWahl);
        wahl.setZweitwahl(zweiteWahl);
        wahl.setDrittwahl(dritteWahl);
        this.wahlBean.create(wahl);
        return "gewaehlt";
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
    public Kurs getErsteWahl() {
        return ersteWahl;
    }

    /**
     * @param ersteWahl the ersteWahl to set
     */
    public void setErsteWahl(Kurs ersteWahl) {
        this.ersteWahl = ersteWahl;
    }

    /**
     * @return the zweiteWahl
     */
    public Kurs getZweiteWahl() {
        return zweiteWahl;
    }

    /**
     * @param zweiteWahl the zweiteWahl to set
     */
    public void setZweiteWahl(Kurs zweiteWahl) {
        this.zweiteWahl = zweiteWahl;
    }

    /**
     * @return the dritteWahl
     */
    public Kurs getDritteWahl() {
        return dritteWahl;
    }

    /**
     * @param dritteWahl the dritteWahl to set
     */
    public void setDritteWahl(Kurs dritteWahl) {
        this.dritteWahl = dritteWahl;
    }
    
    
    
}
