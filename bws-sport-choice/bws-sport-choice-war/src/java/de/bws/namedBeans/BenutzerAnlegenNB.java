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
import de.bws.entities.Schueler;
import de.bws.security.Passwort;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import de.bws.sessionbeans.StufeFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
    private LehrerFacadeLocal lehrerBean;
    
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    private Rolle rolle;
    private String benutzername;
    private String nachname;
    private String vorname;
    private String passwort;
    private String stufe;
    private String tutor;
    private String kuerzel;
    
//*************************** Methoden *****************************************
//******************************************************************************
    
    /**
     * Creates a new instance of BenutzerAnlegenNB
     */
    public BenutzerAnlegenNB() {
    }
    
    @PostConstruct
    private void init(){
//        Lehrer lehrer = new Lehrer();
//        lehrer.setKuerzel("GG");
//        lehrer.setNachname("Grüning");
//        lehrer.setVorname("Peter");
//        this.personBean.create(lehrer);
    }
    
    /**
     * 
     * @return 
     */
    public String anlegen(){
        System.out.println("Benutzer anlegen");
        Person neuePerson = null;
        switch (rolle) {
            case LEHRER:
                this.lehrerBean.create(this.lehrerErstellen());
                break;
            case SCHUELER:
                this.schuelerBean.create(this.schuelerErstellen());
                break;
            case ADMIN:
                this.personBean.create(this.personErstellen(null));
                break;
            default:
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lasterror", "Fehler beim zuweisen der Rolle.");
                return "Anlegen";
        }
        
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
       
        return "benutzerAnlegen";
    }
    
    private Schueler schuelerErstellen(){
        Schueler schueler = new Schueler();
        schueler.setTutor(this.lehrerBean.find(this.tutor));
        schueler.setStufe(this.stufeBean.find(this.stufe));
        return (Schueler) this.personErstellen(schueler);
    }
    
    private Lehrer lehrerErstellen(){
        Lehrer lehrer = new Lehrer();
        lehrer.setKuerzel(this.kuerzel);
        return (Lehrer) this.personErstellen(lehrer);
    }
    
    private Person personErstellen(Person p_person){
        Person person;
        if(p_person != null){
            person = p_person;
        } else {
            person = new Person();
        }
        person.setVorname(this.vorname);
        person.setNachname(this.nachname);
        return person;
    }
    
 //**************************** Setter und Getter ******************************
 //*****************************************************************************   
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
     * 
     * @return 
     */
    public Rolle[] getRollen(){
        return Rolle.values();
    }
    
    /**
     * @return the tutor
     */
    public String getTutor() {
        return tutor;
    }
    
    public List<Lehrer> getTutoren(){
        return this.lehrerBean.findAll();
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
