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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

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
//        this.benutzername = "Hello";
//        this.passwort = "world";
    }
    
    /**
     * 
     * @return 
     */
    public String anlegen(){
        
        Person neuePerson = this.getNeuePersonFromRolle();
            
        if(neuePerson == null){
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
            
        
        try {           
            this.passwort = Passwort.passwortGenerieren();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(BenutzerAnlegenNB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Bean-Methode \"anmelden\" wird usgefürht. B: " + this.benutzername + ", P: " + this.passwort );
        
//        String msgText = "Benutzer: " + this.benutzername + "\nPasswort: " + this.passwort;
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Anmededaten für die Erstanmeldung", msgText);
//        RequestContext.getCurrentInstance().showMessageInDialog(message);
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.update(":dialogErstanmeldung");
//        context.execute("PF('dialogErstanmeldung').open();");
        RequestContext context = RequestContext.getCurrentInstance();
        String execute = "$('#pnl').append('<p>Bentzername: ";
        execute += this.benutzername;
        execute += " Passwort: ";
        execute += this.passwort;
        execute += "</p>')";
        
        context.execute(execute);
        context.execute("PF('dialogErstanmeldung').show();");
       
        return "benutzerAnlegen";
    }
    
    private Person getNeuePersonFromRolle(){
        Person neuePerson;
        switch (rolle) {
            case LEHRER:
                neuePerson = this.lehrerErstellen();
                this.lehrerBean.create((Lehrer)neuePerson);
                break;
            case SCHUELER:
                neuePerson = this.schuelerErstellen();
                this.schuelerBean.create((Schueler)neuePerson);
                break;
            case ADMIN:
                neuePerson = this.personErstellen(null);
                this.personBean.create(neuePerson);
                break;
            default:
                neuePerson = null; 
        }
        return neuePerson;
    }
    
    private Schueler schuelerErstellen(){
        Schueler schueler = new Schueler();
        schueler.setTutor(this.lehrerBean.find(Long.parseLong(this.tutor)));
        schueler.setStufe(this.stufeBean.find(Long.parseLong(this.stufe)));
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
    public List<Rolle> getRollen(){
        List<Rolle> rollen = new ArrayList<>();
        for(int i = 0; i < Rolle.values().length; i++){
            rollen.add(Rolle.values()[i]);
        }
       return rollen;
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
