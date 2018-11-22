package de.bws.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity-Klasse für einen Kurs. 
 * 
 * @author joshua
 */
@Entity
public class Kurs implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // Die ID des Datensatzes in der Datenbank
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "KURS_ID")
    private Long id;
    // Der Titel des Kurses
    @Column(name = "TITEL", nullable = false)
    private String titel;
    // Das Kürzel des Kurses
    @Column(name = "KUERZEL", nullable = false)
    private String kuerzel;
    // Die Erklärung zur Bewetung des Kurses
    @Column(name = "BEWERTUNG", nullable = false)
    private String bewertung;
    // Die Anzahl der eingetragenen Teilnehmer
    @Column(name = "TEILNEHMERZAHL", nullable = false)
    private int teilnehmerzahl;
    // Das Erstelldatum des Kurses
    @Temporal(value = TemporalType.DATE)
    @Column(name = "JAHR", nullable = false)
    private Date jahr;
    // Der Hinweistext des Kurses
    @Column(name = "HINWEIS", nullable = false)
    private String hinweis;
    // Die Beschreibung des Kurses
    @Column(name = "BESCHREIBUNG", nullable = false)
    private String beschreibung;
    // Die Liste der Themen des Kurses mit Anteil in Prozent
    @OneToMany
    @JoinColumn(referencedColumnName = "KURS_ID", name = "KURS_ID")
    private List<Thema> thema = new ArrayList<>();    
    // Der Themengleiche Kurs des Vorjahres
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "THEMENGLEICH_ID", nullable = true)
    private Kurs themengleich;
    // Der Lehrer, der den Kurs leitet
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEHRER_ID", nullable = false)
    private Lehrer lehrer;
    // Die teilnehmenden Schueler
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SCHUELER_KURS",
            joinColumns = @JoinColumn(name = "KURS_ID", referencedColumnName = "KURS_ID"),
            inverseJoinColumns = @JoinColumn(name = "SCHUELER_ID", referencedColumnName = "ID"))
    private List<Schueler> teilnehmer = new ArrayList<>();
    // Die Stufe für die der Kurs angeboten wird
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STUFE_ID", nullable = false)
    private Stufe stufe;

//****************************** Generierte Methoden **************************************
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kurs)) {
            return false;
        }
        Kurs other = (Kurs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.bws.entities.Kurs[ id=" + id + " ]";
    }
    
//******************************* Getter und Setter ****************************
    
    /**
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param p_id the id to set
     */
    public void setId(Long p_id) {
        this.id = p_id;
    }

    /**
     * @return the titel
     */
    public String getTitel() {
        return titel;
    }

    /**
     * @param p_titel the titel to set
     */
    public void setTitel(String p_titel) {
        this.titel = p_titel;
    }

    /**
     * @return the kuerzel
     */
    public String getKuerzel() {
        return kuerzel;
    }

    /**
     * @param p_kuerzel the kuerzel to set
     */
    public void setKuerzel(String p_kuerzel) {
        this.kuerzel = p_kuerzel;
    }

    /**
     * @return the bewertung
     */
    public String getBewertung() {
        return bewertung;
    }

    /**
     * @param p_bewertung the bewertung to set
     */
    public void setBewertung(String p_bewertung) {
        this.bewertung = p_bewertung;
    }

    /**
     * @return the teilnehmerzahl
     */
    public int getTeilnehmerzahl() {
        return teilnehmerzahl;
    }

    /**
     * @param p_teilnehmerzahl the teilnehmerzahl to set
     */
    public void setTeilnehmerzahl(int p_teilnehmerzahl) {
        this.teilnehmerzahl = p_teilnehmerzahl;
    }

    /**
     * @return the jahr
     */
    public Date getJahr() {
        return jahr;
    }

    /**
     * @param p_jahr the jahr to set
     */
    public void setJahr(Date p_jahr) {
        this.jahr = p_jahr;
    }

    /**
     * @return the hinweis
     */
    public String getHinweis() {
        return hinweis;
    }

    /**
     * @param p_hinweis the hinweis to set
     */
    public void setHinweis(String p_hinweis) {
        this.hinweis = p_hinweis;
    }

    /**
     * @return the thema
     */
    public List<Thema> getThema() {
        return thema;
    }

    /**
     * @param p_thema the thema to set
     */
    public void setThema(List<Thema> p_thema) {
        this.thema= new ArrayList<>();
        for(Thema t : p_thema){
            this.thema.add(t);
        }

    }

    /**
     * @return the themengleich
     */
    public Kurs getThemengleich() {
        return themengleich;
    }

    /**
     * @param p_themengleich the themengleich to set
     */
    public void setThemengleich(Kurs p_themengleich) {
        this.themengleich = p_themengleich;
    }

    /**
     * @return the lehrer
     */
    public Lehrer getLehrer() {
        return lehrer;
    }

    /**
     * @param p_lehrer the lehrer to set
     */
    public void setLehrer(Lehrer p_lehrer) {
        this.lehrer = p_lehrer;
    }

    /**
     * @return the teilnehmer
     */
    public List<Schueler> getTeilnehmer() {
        return teilnehmer;
    }

    /**
     * @param p_teilnehmer the teilnehmer to set
     */
    public void setTeilnehmer(List<Schueler> p_teilnehmer) {
        this.teilnehmer = p_teilnehmer;
    }
    

    /**
     * @return the stufe
     */
    public Stufe getStufe() {
        return stufe;
    }

    /**
     * @param p_stufe the stufe to set
     */
    public void setStufe(Stufe p_stufe) {
        this.stufe = p_stufe;
    }

    /**
     * @return the beschreibung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * @param beschreibung the beschreibung to set
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    
    /**
     * Fügt der Teilnehmerliste einen Schüler hinzu, sofern er noch nicht eingetragen ist.
     * 
     * @author joshua
     * @param p_teilnehmer der Teilnehmer, der hinzugefügt werden soll
     * @return Diese Methode gibt true zurück, wenn der Teilnehmer hinzugefügt wurde, und
     * false, falls der Teilnehmer bereits in der Liste vorhanden ist
     */
    public boolean addTeilnehmer(Schueler p_teilnehmer){
        for(Schueler s:this.teilnehmer){
            if(s.equals(p_teilnehmer)){
                return false;
            }
        }
        
        this.teilnehmer.add(p_teilnehmer);
        return true;
    }
    
    /**
     * Entfernt einen Teilnehmer aus der Liste.
     * 
     * @author joshua
     * @param p_teilnehmer der Teilnehner, der aus dem Kurs entfernt werden soll
     * @return Diese Methode gibt true zurück, wenn der Teilnehmer entfernt wurde, 
     * und false, falls der Teilnehmer nicht in der Liste ist.
     */
    public boolean removeTeilnehmer(Schueler p_teilnehmer){
        return this.teilnehmer.remove(p_teilnehmer);
    }
    
    /**
     * @param p_thema  das Thema, das hinzugefügt werden soll
     * @return Diese Methode gibt true zurück, wenn das Thema hinzugefügt wurde, und
     * false, falls das Thema bereits in der Liste vorhanden ist
     */
    public boolean addThema(Thema p_thema){
        for(Thema t:this.thema){
            if(t.equals(p_thema)){
                return false;
            }
        }
        
        this.thema.add(p_thema);
        return true;
    }
    
    /**
     * Enfernt ein Thema aus der Liste.
     * 
     * @author joshua
     * @param p_thema  das Thema, das aus dem Kurs entfernt werden soll
     * @return Diese Methode gibt true zurück, wenn das Thema entfernt wurde, 
     * und false, falls das Thema nicht in der Liste ist.
     */
    public boolean removeThema(Thema p_thema){
        int index = this.thema.indexOf(p_thema);
        this.thema.remove(index);
        return true;
    }
    
}
