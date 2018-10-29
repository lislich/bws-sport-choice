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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author joshua
 */
@Named(value = "benutzerNB")
@Dependent
public class BenutzerNB implements Serializable{
    
    // Schnittstelle zur Datenbank für Entitäten vom Typ Benutzer
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    // Liste aller Benutzer
    private List<Benutzer> alleBenutzer;
    
    // gefilterte Liste von Benutzern mit zugehörigem Boolean für Checkboxen auf 
    // der Oberfläche
    private List<Eintrag<Benutzer, Boolean>> auswahl;

    /**
     * Erstellt eine neue Instanz der BenutzerNB
     */
    public BenutzerNB() {
    }
    
    /**
     * List direkt nach dem Erzeugen einer Instanz dieser Klasse alle Benutzer 
     * aus der Datenbank aus und speichert sie in "alleBenutzer" und "auswahl",
     * da zu dieser Zeit noch kein Filter ausgewählt ist.
     */
    @PostConstruct
    private void init(){
        this.auswahl = new ArrayList<>();
        this.setAlleBenutzer(this.benutzerBean.findAll());
        this.setAuswahl(this.alleBenutzer);
    }
    
    /**
     * Gibt alle Benutzer einer bestimmten Rolle zurück.
     * @param p_rolle Die Rolle nach der gefiltert werden soll
     * @return Liste von Benutzern mit derentsprechenden Rolle
     */
    public List<Benutzer> getByRolle(String p_rolle){
        if( p_rolle == null || p_rolle.equals("Alle")){
            return this.getAlleBenutzer();
        }
        
        List<Benutzer> nutzer = new ArrayList<>();
        for(Benutzer b:this.getAlleBenutzer()){
            if( b != null && b.getRolle().name().equals(p_rolle)){
                nutzer.add(b);
            }
        }
        return nutzer;
    }

    /**
     * @return Lste aller Benutzer
     */
    public List<Benutzer> getAlleBenutzer() {
        return alleBenutzer;
    }

    /**
     * @param alleBenutzer Liste aller Benutzer
     */
    public void setAlleBenutzer(List<Benutzer> alleBenutzer) {
        this.alleBenutzer = alleBenutzer;
    }
    
    public void auswaehlen(String p_rolle, Stufe p_stufe){
        List<Benutzer> vorauswahl = this.getByRolle(p_rolle);
        if(p_rolle != null){
            if(p_rolle.equals(Rolle.SCHUELER.name()) && p_stufe != null){
                for(Benutzer b:vorauswahl){
                    if(b.getPerson().getStufe() != p_stufe ){
                        vorauswahl.remove(b);
                    }
                }
            }
        }
        this.setAuswahl(vorauswahl);
    }

    /**
     * @return Die gefilterte Liste von Einträgen mit Benutzer/Boolean Paaren
     */
    public List<Eintrag<Benutzer, Boolean>> getAuswahl() {
        return auswahl;
    }

    /**
     * @param p_auswahl Eine gefilterte Liste von Einträgen mit Benutzer/Boolean Paaren
     */
    public void setAuswahl(List<Benutzer> p_auswahl) {
        System.out.println("de.bws.namedBeans.BenutzerNB.setAuswahl()");
        this.auswahl.clear();
        for(Benutzer b:p_auswahl){
            this.auswahl.add(new Eintrag(b, false));
        }
    }
    
    
}
