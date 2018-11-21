package de.bws.namedBeans;

import de.bws.entities.Benutzer;
import de.bws.entities.Kurs;
import de.bws.entities.Lehrer;
import de.bws.entities.Schueler;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.KursFacadeLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * @author Lisa
 * 
 * Diese ManagedBean ermittelt verschiedene Kurslisten.
 */
@Named("kurslisteBean")
@ViewScoped
public class KurslisteNB implements Serializable {
    // Schnittstelle zur Datenbank für Entitäten vom Typ Kurs
    @EJB
    private KursFacadeLocal kursBean;

    // Liste von allen Kursen
    private List<Kurs> alleKurse;
    
    // Liste von Kursen eines Lehrers
    private List<Kurs> lehrerKurse;
    
    // Liste von Schülern eines Kurses
    private List<Schueler> schuelerKurse;
    
    // Liste von Kursen einer Stufe
    private List<Kurs> stufeKurse;

    /**
     * @author Lisa
     * @param p_kurs
     * @return String zur Navigation zur nächsten Seite
     * 
     * Diese Methode setzt den übergebenen Kurs als 'gewaehlterKurs' in der SessionMap, damit man diesen über den
     * Schlüssel auch in anderen ManagedBeans ermitteln kann und zum Bearbeiten des Kurses verwenden kann.
     */
    public String setGewaehlterKursBearbeiten(Kurs p_kurs){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("gewaehlterKurs", p_kurs);
        return "kursBearbeiten";
    }
    
    /**
     * @author Lisa
     * @param p_kurs
     * @return String zur Navigation zur nächsten Seite
     * 
     * Diese Methode setzt den übergebenen Kurs als 'gewaehlterKurs' in der SessionMap, damit man diesen über den
     * Schlüssel auch in anderen ManagedBeans ermitteln kann und zum Anschauen des Kurses verwenden kann.
     */
    public String setGewaehlterKurs(Kurs p_kurs){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("gewaehlterKurs", p_kurs);
        return "kursAnschauen";
    }
    
    // ##### Getter- und Setter-Methoden #################################################################
    
    /**
     * @author joshua
     * @param p_kurse Liste, die gefiltert werden soll
     * @return Liste mit Kursen des aktuellen Jahres
     * 
     * Selektiert alle Kurse des aktuellen Jahres aus einer Liste von Kursen.
     */
    public List<Kurs> getAktuelleKurseFromList(List<Kurs> p_kurse){
        Date jetzt = new Date();
        List<Kurs> aktuelleKurse = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        for(Kurs k:p_kurse){
            if(dateFormat.format(k.getJahr()).equals(dateFormat.format(jetzt))){
                aktuelleKurse.add(k);
            }
        }
        return aktuelleKurse;
    }
    
    /**
     * @author joshua
     * @return Liste mit Kursen des Vorjahrs der Stufe 12
     * 
     * Gibt eine Liste alle Kurse des Vorjahrs der Stufe 12 aus. Diese können 
     * Thmenegleiche Kurse in der Stufe 13 sein.
     */
    public List<Kurs> getKurseVorjahr(){
        List<Kurs> kurseVorjahr = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String vorjahr = "" + (Integer.parseInt(dateFormat.format(new Date())) - 1);
        for(Kurs k: this.getAlleKurse()){
            if(k.getStufe().getBezeichnung().equals("12") && dateFormat.format(k.getJahr()).equals(vorjahr)){
                kurseVorjahr.add(k);
            }
        }
        return kurseVorjahr;
    }
    
    /**
     * @author Lisa
     * @return the alleKurse
     * 
     * Diese Methode sucht alle Kurse aus der Datenbank.
     */
    public List<Kurs> getAlleKurse(){
        List<Kurs> tmp = this.kursBean.findAll();
        if(tmp == null){
            tmp = new ArrayList<>();
        }
        this.alleKurse = tmp;
        return this.alleKurse;
    }

    /**
     * @param p_alleKurse the alleKurse to set
     */
    public void setAlleKurse(List<Kurs> p_alleKurse) {
        this.alleKurse = p_alleKurse;
    }
    
    

    /**
     * @author Lisa
     * @return the lehrerKurse
     * 
     * Diese Methode ermittelt den aktuellen Benutzer und seine Person dazu, hier Lehrer, und sucht
     * die zum Lehrer gehörigen Kurse aus der Datenbank.
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
     * @param p_lehrerKurse the lehrerKurse to set
     */
    public void setLehrerKurse(List<Kurs> p_lehrerKurse) {
        this.lehrerKurse = p_lehrerKurse;
    }

    /**
     * @author Lisa
     * @return the schuelerKurse
     * 
     * Diese Methode ermittelt den gewaehlten Kurs und gibt die Teilnehmer zurück.
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
     * @param p_schuelerKurse the schuelerKurse to set
     */
    public void setSchuelerKurse(List<Schueler> p_schuelerKurse) {
        this.schuelerKurse = p_schuelerKurse;
    }

    /**
     * @author Lisa
     * @return the stufeKurse
     * 
     * Diese Methode ermittelt den aktuellen Benutzer, hier Schüler. Gesucht werden dann die Kurse,
     * die für die Stufe des Schülers angelegt wurden.
     */
    public List<Kurs> getStufeKurse() {
        Benutzer b = (Benutzer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("benutzer");
        Stufe stufe = b.getPerson().getStufe();
        List<Kurs> tmpList = this.kursBean.get("SELECT k FROM Kurs k WHERE k.stufe.id = " + stufe.getId());
        stufeKurse = this.getAktuelleKurseFromList(tmpList);
        return stufeKurse;
    }

    /**
     * @param p_stufeKurse the stufeKurse to set
     */
    public void setStufeKurse(List<Kurs> p_stufeKurse) {
        this.stufeKurse = p_stufeKurse;
    }
}
