/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;


import de.bws.data.Eintrag;
import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Named(value = "benutzerVerwaltenNB")
@ViewScoped
public class BenutzerVerwaltenNB implements Serializable{

    @EJB
    private PersonFacadeLocal personBean;
    
    @EJB
    private LehrerFacadeLocal lehrerBean;
    
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
        
    @Inject
    private BenutzerNB benutzerNB;
    
    private final Logger log = Logger.getLogger("BenutzerVerwaltenNB");
    
    private Stufe stufe;
    private String rolle;
    private Eintrag<Benutzer, Boolean> testEintrag;
    
    /**
     * Creates a new instance of BenutzerVerwaltenNB
     */
    public BenutzerVerwaltenNB() {
    }

    
    @PostConstruct
    private void init(){
        this.testEintrag = new Eintrag<>(benutzerNB.getAlleBenutzer().get(0), false);
    }
    
    public String loeschen(){
        System.out.println("start löschen");
//        for(Eintrag<Benutzer, Boolean> e:this.benutzerNB.getAuswahl()){
//            log.log(Level.WARNING, "Entry: {0} ({1})", new Object[]{e.getKey().getBenutzername(), e.getValue()});
//            if(e.getValue()){
//                log.warning("lösche " + e.getKey().getBenutzername());
//                this.benutzerBean.remove(e.getKey());
//            }
//        }
        System.out.println(this.testEintrag.getKey().getBenutzername() + " | " + this.testEintrag.getValue());
        return "benutzerVerwalten";
    }
    
    public String aendern(){
        Benutzer benutzer = null;
        for(Eintrag<Benutzer, Boolean> e:this.benutzerNB.getAuswahl()){
            if(e.getValue()){
                if(benutzer == null){
                    benutzer = e.getKey();
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Sie können nur die Benutzerdaten einzelner Benutzer ändern.");
                    return "benutzerVerwalten";
                }
            }
        }
        if(benutzer == null){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Bitte wählen Sie einen Beutzer.");
            return "benutzerVerwalten";
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("gewaehlterBenutzer", benutzer);
        return "benutzerAendern";
    }
    
    public String hochstufen(){
        
        return null;
    }
    
    public String abstufen(){
        
        return null;
    }
    
    /**
     * 
     * @return 
     */
    public List<String> getAlleRollen(){
        List<String> rollen = new ArrayList<>();
        rollen.add("Alle");
        for(Rolle r:Rolle.values()){
            rollen.add(r.name());
        }
        
        return rollen;
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
     * @return the rolle
     */
    public String getRolle() {
        return rolle;
    }

    /**
     * @param rolle the rolle to set
     */
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
    
    public List<Benutzer> getAlleBenutzer() {
        return this.benutzerBean.findAll();
    }
    
    public void auswaehlen(){
        this.benutzerNB.auswaehlen(this.rolle, this.stufe);
    }

    /**
     * @return the testEintrag
     */
    public Eintrag<Benutzer, Boolean> getTestEintrag() {
        return testEintrag;
    }

    /**
     * @param testEintrag the testEintrag to set
     */
    public void setTestEintrag(Eintrag<Benutzer, Boolean> testEintrag) {
        this.testEintrag = testEintrag;
    }

}
