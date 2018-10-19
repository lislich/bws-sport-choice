/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Kurs;
import de.bws.entities.Lehrer;
import de.bws.entities.Schueler;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.KursFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Lisa
 */
@Named("kurslisteBean")
@ViewScoped
public class KurslisteNB implements Serializable {
    @EJB
    private KursFacadeLocal kursBean;
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    @EJB
    private PersonFacadeLocal personBean;
    
    private List<Kurs> alleKurse;
    
    private List<Kurs> lehrerKurse;
    
    private List<Schueler> schuelerKurse;
    
    private List<Kurs> stufeKurse;
    
    
//    @PostConstruct
//    public void init(){
//                Benutzer admin = new Benutzer();
//        
//        try {
//            admin.setBenutzername("MarSax");
//            admin.setNeuesPasswort("marsax");
//            admin.setRolle(Rolle.LEHRER);
//        } catch (Exception ex) {
//            Logger.getLogger(LoginNB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        this.benutzerBean.create(admin);
//    }
    
    public List<Kurs> getAlleKurse(){
        List<Kurs> tmp = this.kursBean.findAll();
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.alleKurse = tmp;
        return this.alleKurse;
    }

    /**
     * @param alleKurse the alleKurse to set
     */
    public void setAlleKurse(List<Kurs> alleKurse) {
        this.alleKurse = alleKurse;
    }
    
    
    public String setGewaehlterKursSchueler(Kurs kurs){
        System.out.println("de.bws.namedBeans.KurslisteNB.setGewaehlterKurs()" + kurs.getTitel());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("gewaehlterKurs", kurs);
        return "schuelerEinsehen";
    }
    
    public String setGewaehlterKursBearbeiten(Kurs kurs){
        System.out.println("de.bws.namedBeans.KurslisteNB.setGewaehlterKurs()" + kurs.getTitel());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("gewaehlterKurs", kurs);
        return "kursBearbeiten";
    }
    
    public String setGewaehlterKurs(Kurs kurs){
        System.out.println("de.bws.namedBeans.KurslisteNB.setGewaehlterKurs()" + kurs.getTitel());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("gewaehlterKurs", kurs);
        return "kursAnschauen";
    }

    /**
     * @return the lehrerKurse
     */
    public List<Kurs> getLehrerKurse() {
        Benutzer b = (Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        Lehrer l = (Lehrer) b.getPerson();
        List<Kurs> tmp = this.kursBean.get("SELECT k FROM Kurs k WHERE k.lehrer.id =" + l.getId());
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.lehrerKurse = tmp;
        return lehrerKurse;
    }

    /**
     * @param lehrerKurse the lehrerKurse to set
     */
    public void setLehrerKurse(List<Kurs> lehrerKurse) {
        this.lehrerKurse = lehrerKurse;
    }

    /**
     * @return the schuelerKurse
     */
    public List<Schueler> getSchuelerKurse() {
        Kurs k = ((Kurs) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("gewaehlterKurs"));
        List<Schueler> tmp = k.getTeilnehmer();
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.schuelerKurse = tmp;
        return this.schuelerKurse;
    }

    /**
     * @param schuelerKurse the schuelerKurse to set
     */
    public void setSchuelerKurse(List<Schueler> schuelerKurse) {
        this.schuelerKurse = schuelerKurse;
    }

    /**
     * @return the stufeKurse
     */
    public List<Kurs> getStufeKurse() {
        Benutzer b = (Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        Stufe stufe = b.getPerson().getStufe();
        List<Kurs> tmpList = this.kursBean.get("SELECT k FROM Kurs k WHERE k.stufe.id = " + stufe.getId());
        stufeKurse = tmpList;
        return stufeKurse;
    }

    /**
     * @param stufeKurse the stufeKurse to set
     */
    public void setStufeKurse(List<Kurs> stufeKurse) {
        this.stufeKurse = stufeKurse;
    }
}
