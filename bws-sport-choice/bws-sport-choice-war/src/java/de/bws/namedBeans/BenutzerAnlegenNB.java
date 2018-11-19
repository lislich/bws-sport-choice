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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
    
    // Diese Attribute werden mit den Eingabe- / Auswahlfeldern auf der 
    // Oberfläche verknüpft
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
     * Erstellt eine neue Insatnz von BenutzerAnlegenNB
     */
    public BenutzerAnlegenNB() {
    }
    
    public void filter(AjaxBehaviorEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        String execute = "";
        System.out.println("#" + rolle.toString());
        
        switch (rolle) {
            case LEHRER:
                System.out.println("Test Lehrer");
                execute += "$('#tutor').attr('disabled',true);$('#stufe').attr('disabled',true);$('#kuerzel').attr('disabled',false);";
                break;
            case SCHUELER:
                System.out.println("Test Schüler");
                execute += "$('#tutor').attr('disabled',false);$('#stufe').attr('disabled',false);$('#kuerzel').attr('disabled',true);";
                break;
            case ADMIN:
                System.out.println("Test Admin");
                execute += "$('#tutor').attr('disabled',true);$('#stufe').attr('disabled',true);$('#kuerzel').attr('disabled',true);";
                break; 
            default:
                execute = "";
                break;
        }
        
        System.out.println(execute);
        context.execute(execute);
    }
    
    /**
     * Legt eine neue Person mit den vom Benutzer angegebenen Daten an. 
     * 
     * @Author Joshua
     * @return String für weitere Navigation
     */
    public String anlegen(){
        
        boolean fehler = false;
  
        Person neuePerson = this.getNeuePersonFromRolle();
        
        Benutzer neuerBenutzer = null;
        
        if (neuePerson != null) {
            neuerBenutzer = new Benutzer();
            neuerBenutzer.setBenutzername(this.benutzername);
            neuerBenutzer.setRolle(this.rolle);
            neuerBenutzer.setPerson(neuePerson);
        }

        if (neuerBenutzer != null) {
            try {
                this.passwort = Passwort.passwortGenerieren();
                neuerBenutzer.setNeuesPasswort(this.passwort);
                this.benutzerBean.create(neuerBenutzer);
            } catch (Exception ex) {
                Logger.getLogger(BenutzerAnlegenNB.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Fehler beim Anlegen des Benutzers.");
                fehler = true;
            }
            

            if (!fehler) {
                RequestContext context = RequestContext.getCurrentInstance();
                String execute = "$('#pnl').append('<p>Benutzername: ";
                execute += this.benutzername;
                execute += " Passwort: ";
                execute += this.passwort;
                execute += "</p>')";

                context.execute(execute);
                context.execute("PF('dialogErstanmeldung').show();");
            }

        }else{
            this.personBean.remove(neuePerson);
        }
        
        
        
       
        return "benutzerAnlegen";
    }
    
    /**
     * Erzeugt eine Person der angegeben Rolle
     * 
     * @Author Joshua
     * @return die erstellte Person
     */
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
    
    /**
     * Erstellt einen Schüler mit den schülerspezifischen Attributen.
     * 
     * @Author Joshua
     * @return Der erstellte Schüler
     */
    private Schueler schuelerErstellen(){
        Schueler schueler = new Schueler();
        schueler.setTutor(this.lehrerBean.find(Long.parseLong(this.tutor)));
        schueler.setStufe(this.stufeBean.find(Long.parseLong(this.stufe)));
        return (Schueler) this.personErstellen(schueler);
    }
    
    /**
     * Erstellt einen Lehrer mit den Lehrerspezifischen Attributen.
     * 
     * @Author Joshua
     * @return Der erstellet Lehrer
     */
    private Lehrer lehrerErstellen(){
        Lehrer lehrer = new Lehrer();
        lehrer.setKuerzel(this.kuerzel);
        return (Lehrer) this.personErstellen(lehrer);
    }
    
    /**
     * Erstellt eine Person und setzt deren Vor- und Nachnamen. Falls eine Person 
     * übergeben wird werden nur Vor- und Nachname gesetzt.
     * 
     * @param p_person Eine Person, deren Vor- und Nachname gesetzt werden soll
     * @return Die erstellte bzw. geänderte Person
     */
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
     * Gibt eine Liste aller Rollen zurück.
     * 
     * @return Liste aller Rollen
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
    
    /**
     * Erstellt eine Liste aller Tutoren. Aller Lehrer werden als mögliche Tutoren 
     * angesehen.
     * 
     * @return Liste aller Lehrer
     */
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
