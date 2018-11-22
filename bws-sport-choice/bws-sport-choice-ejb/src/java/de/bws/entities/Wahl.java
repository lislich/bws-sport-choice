package de.bws.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity-Klasse für eine Wahl.
 *
 * @author joshua
 */
@Entity
public class Wahl implements Serializable {

    private static final long serialVersionUID = 1L;
    // Die ID des Datensatzes in der Datenbank
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // Die Erstwahl des Schülers
    @ManyToOne
    @JoinColumn(name = "ERSTWAHL", referencedColumnName = "KURS_ID", nullable = false)
    private Kurs erstwahl;
    // Die Zweiwahl des Schülers
    @ManyToOne
    @JoinColumn(name = "ZWEITWAHL", referencedColumnName = "KURS_ID", nullable = false)
    private Kurs zweitwahl;
    // Die Drittwahl des Schülers
    @ManyToOne
    @JoinColumn(name = "DRITTWAHL", referencedColumnName = "KURS_ID", nullable = false)
    private Kurs drittwahl;

//**************************** generierte Methoden *****************************

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wahl)) {
            return false;
        }
        Wahl other = (Wahl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.bws.entities.Wahl[ id=" + id + " ]";
    }

//*********************** Getter und Setter *********************************
    
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
     * @return the erstwahl
     */
    public Kurs getErstwahl() {
        return erstwahl;
    }

    /**
     * @param erstwahl the erstwahl to set
     */
    public void setErstwahl(Kurs erstwahl) {
        this.erstwahl = erstwahl;
    }

    /**
     * @return the zweitwahl
     */
    public Kurs getZweitwahl() {
        return zweitwahl;
    }

    /**
     * @param zweitwahl the zweitwahl to set
     */
    public void setZweitwahl(Kurs zweitwahl) {
        this.zweitwahl = zweitwahl;
    }

    /**
     * @return the drittwahl
     */
    public Kurs getDrittwahl() {
        return drittwahl;
    }

    /**
     * @param drittwahl the drittwahl to set
     */
    public void setDrittwahl(Kurs drittwahl) {
        this.drittwahl = drittwahl;
    }
    
    /**
     * Gibt alle Gewählten Kurse des Schülers in einem Array zurück. Dabei ist 
     * das ertse Element die Ertwahl, das zweite Element die Zweitwahl und das 
     * dritte Element die Drittwahl
     * 
     * @return Array mit den gewählten Kursen
     */
    public Kurs[] getAlleKurse(){
        Kurs[] wahlen = new Kurs[3];
        wahlen[0] = this.erstwahl;
        wahlen[1] = this.zweitwahl;
        wahlen[2] = this.drittwahl;
        return wahlen;
    }
}
