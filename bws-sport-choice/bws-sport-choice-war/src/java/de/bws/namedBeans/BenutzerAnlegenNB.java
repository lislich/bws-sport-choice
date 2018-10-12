/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;

import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Lehrer;
import de.bws.entities.Person;
import de.bws.entities.Stufe;
import de.bws.security.Passwort;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import de.bws.sessionbeans.StufeFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author joshua
 */
@Named(value = "benutzerAnlegenNB")
@ViewScoped
public class BenutzerAnlegenNB implements Serializable{

    @EJB
    private StufeFacadeLocal stufeBean;
    
    @EJB
    private PersonFacadeLocal personBean;
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    private Rolle rolle;
    private String benutzername;
    private String nachname;
    private String vorname;
    private String passwort;
    private Stufe stufe;
    private String tutor;
    private String kuerzel;
    
    /**
     * Creates a new instance of BenutzerAnlegenNB
     */
    public BenutzerAnlegenNB() {
    }
    
    /**
     * 
     * @return 
     */
    public String anlegen(){
        if(null == rolle){
            
        } else switch (rolle) {
            case LEHRER:
                break;
            case SCHUELER:
                break;
            case ADMIN:
                break;
            default:
                break;
        }
        
        Person neuePerson = new Person();
        neuePerson.setNachname(this.nachname);
        neuePerson.setVorname(this.vorname);
        
        this.personBean.create(neuePerson);
        
        Benutzer neuerBenutzer = new Benutzer();
        neuerBenutzer.setPerson(neuePerson);
        neuerBenutzer.setBenutzername(this.benutzername);
        neuerBenutzer.setRolle(this.rolle);
        try {
            neuerBenutzer.setNeuesPasswort(Passwort.passwortGenerieren());
            this.benutzerBean.create(neuerBenutzer);
        } catch (Exception ex) {
            Logger.getLogger(BenutzerAnlegenNB.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Fehler beim Anlegen des Benutzers.");
        }
       
        return "Angelegt";
    }
    
    private Lehrer lehrerAnlegen(){
        Lehrer lehrer = new Lehrer();
        return lehrer;
    }
    
    /**
     * @return the rolle
     */
    public Rolle getRolle() {
        return rolle;
    }

    /**
     * @param rolle the rolle to set
     */
    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    /**
     * @return the benutzername
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * @param benutzername the benutzername to set
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    /**
     * @return the nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * @return the vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * @param vorname the vorname to set
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * @return the passwort
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * @param passwort the passwort to set
     */
    public void setPasswort(String passwort) {
        this.passwort = passwort;
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
     * 
     * @return 
     */
    public Rolle[] getRollen(){
        return Rolle.values();
    }
    
    /**
     * 
     * @return 
     */
    public List<String> getStufen(){
        List<Stufe> stufen = this.stufeBean.findAll();
        List<String> stufenStrings = new ArrayList<>();
        
        for(Stufe s:stufen){
            stufenStrings.add(s.getBezeichnung());
        }
        return stufenStrings;
    }

    /**
     * @return the tutor
     */
    public String getTutor() {
        return tutor;
    }

    /**
     * @param tutor the tutor to set
     */
    public void setTutor(String tutor) {
        this.tutor = tutor;
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
    
}
