package de.bws.namedBeans;

import de.bws.entities.Wahlzeitraum;
import de.bws.sessionbeans.WahlzeitraumFacadeLocal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 * @author Lisa
 * 
 * Diese ManagedBean dient zum Anlegen eines Wahlzeitraumes
 */
@Named(value = "wahlzeitraumNB")
@ViewScoped
public class WahlzeitraumNB implements Serializable{    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Wahlzeitraum
    @EJB
    private WahlzeitraumFacadeLocal wahlzeitraumBean;
    
    /**
     * Variablen zum Anlegen eines Wahlzeitraumes.
     */
    
    // Beginn eines Wahlzeitraumes
    private Date beginn;
    
    // Ende eines Wahlzeitraumes
    private Date ende;
    
 
    /**
     * @author Lisa
     * 
     * Diese Methode wird beim Erzeugen der ManagedBean aufgerufen und ermittelt ob es bereits einen 
     * gespeicherten Wahlzeitraum in der Datenbank gibt.
     * Außerdem wird der akutell angemeldete Benutzer, für die Wahl Schüler, und dessen eventuell bereits 
     * gespeicherte Wahl ermittelt.
     */
    @PostConstruct
    public void init(){
        // Eventuell bereits gespeicherter Wahlzeitraum wird ermittelt
        Wahlzeitraum tmp;
        try{
            tmp = wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz").get(0);
            if (tmp.getBeginn() != null) {
                this.setBeginn(tmp.getBeginn());
            }
            if (tmp.getEnde() != null) {
                this.setEnde(tmp.getEnde());
            }

        }catch(Exception e){
            
        }
        
    }
    
    /**
     * @author Lisa
     * 
     * Diese Methode überprüft die Eingaben der Daten und speichert bei fehlerfreien Eingaben den Wahlzeitraum, 
     * oder aktualisiert den bereits vorhandenen.
     */
    public void saveDatum() {
        // Aktueller Zeitstempel
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Prüfung ob eingegebenes Enddatum in der Vergangenheit liegt -> Fehler
        if (dateFormat.format(ende.getTime()).compareTo(dateFormat.format(timestamp.getTime())) > 0) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Das Enddatum darf nicht in der Vergangenheit liegen");
        } else {
            // Prüfung ob eingegebenes Beginndatum hinter dem Enddatum liegt -> Fehler
            if (beginn.after(ende)) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastError", "Das Beginndatum muss kleiner als das Enddatum sein.");
            } else {
                // Ermitteln von bereits eingetragenen Zeiträumen
                List<Wahlzeitraum> tmpList;
                tmpList = wahlzeitraumBean.get("SELECT wz FROM Wahlzeitraum wz");
                
                // Wenn es keinen Eintrag gibt, wird ein neuer Zeiraum gespeichert, ansonsten der vorhandene aktualisiert.
                if (tmpList.isEmpty()) {
                    Wahlzeitraum tmp = new Wahlzeitraum();
                    tmp.setBeginn(getBeginn());
                    tmp.setEnde(getEnde());
                    this.wahlzeitraumBean.create(tmp);
                } else {
                    Wahlzeitraum tmp = tmpList.get(0);
                    tmp.setBeginn(beginn);
                    tmp.setEnde(ende);
                    this.wahlzeitraumBean.edit(tmp);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogWahlzeitraum').show(); $('#saveDatum').attr('disabled', true);");
            }
        }
    }

    
    // #### Getter- und Setter-Methoden ############################################################
    /**
     * @return the beginn
     */
    public Date getBeginn() {
        return beginn;
    }

    /**
     * @param p_beginn the beginn to set
     */
    public void setBeginn(Date p_beginn) {
        this.beginn = p_beginn;
    }

    /**
     * @return the ende
     */
    public Date getEnde() {
        return ende;
    }

    /**
     * @param p_ende the ende to set
     */
    public void setEnde(Date p_ende) {
        this.ende = p_ende;
    }

}

