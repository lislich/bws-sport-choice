package de.bws.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Entity-Klasse für einen Schüler. 
 * 
 * @author joshua
 */
@Entity
public class Schueler extends Person implements Serializable{

    private static final long serialVersionUID = 1L;
    // Die Stufe des Schülers
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STUFE_ID", nullable = false)
    private Stufe stufe;
    // DIe Wahl des Schülers
    @OneToOne
    @JoinColumn(name = "WAHL", referencedColumnName = "ID")
    private Wahl wahl;
    // Der Tutor des Schülers
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TUTOR_ID", nullable = false)
    private Lehrer tutor;

 //*************************** generierte Methoden *****************************
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schueler)) {
            return false;
        }
        Schueler other = (Schueler) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.bws.entities.Schueler[ id=" + super.getId() + " ]";
    }

//***************************** Getter und Setter ******************************
    
    /**
     * @return the stufe
     */
    @Override
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
     * @return the wahl
     */
    public Wahl getWahl() {
        return wahl;
    }

    /**
     * @param wahl the wahl to set
     */
    public void setWahl(Wahl wahl) {
        this.wahl = wahl;
    }

    /**
     * @return the tutor
     */
    @Override
    public Lehrer getTutor() {
        return tutor;
    }

    /**
     * @param tutor the tutor to set
     */
    public void setTutor(Lehrer tutor) {
        this.tutor = tutor;
    }
    
}
