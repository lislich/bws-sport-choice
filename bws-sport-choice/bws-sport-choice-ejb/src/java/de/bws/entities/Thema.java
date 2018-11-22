package de.bws.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity-Klasse für einen Thema.
 * 
 * @author joshua
 */
@Entity
public class Thema implements Serializable {

    private static final long serialVersionUID = 1L;
    // Die ID des Datensatzes in der Datenbank
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "THEMA_ID")
    private Long id;
    
    // Der Anteil am Kurs in Prozent
    @Column(name = "ANTEIL", nullable = false)
    private int anteil;
    // Die Bezeichnung des Themas
    @Column(name = "BEZEICHNUNG", nullable = false)
    private String bezeichnung;
    // Der Schwerpunkt des Kurses
    @Column(name = "SCHWERPUNKT", nullable = false)
    private String schwerpunkt;

//***************************** generierte Methoden ****************************

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Thema)) {
            return false;
        }
        Thema other = (Thema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.bws.entities.Thema[ id=" + id + " ]";
    }
    
//***************************** Getter und Setter ******************************
    
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
     * @return the anteil
     */
    public int getAnteil() {
        return anteil;
    }

    /**
     * @param p_anteil the anteil to set
     */
    public void setAnteil(int p_anteil) {
        this.anteil = p_anteil;
    }



    /**
     * @return the schwerpunkt
     */
    public String getSchwerpunkt() {
        return schwerpunkt;
    }

    /**
     * @param schwerpunkt the schwerpunkt to set
     */
    public void setSchwerpunkt(String schwerpunkt) {
        this.schwerpunkt = schwerpunkt;
    }

    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * @param bezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    
}
