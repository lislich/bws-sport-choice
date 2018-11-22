package de.bws.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity-Klasse für den Wahlzeitraum.
 * 
 * @author Lisa
 */
@Entity
public class Wahlzeitraum implements Serializable {

    private static final long serialVersionUID = 1L;
    // Die ID des Datenstazes in der Datenbank
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // Der Beginn des Wahlzeitraums
    @Temporal(TemporalType.DATE)
    @Column(name = "BEGINN", nullable = false)
    private Date beginn;
    // Das Ende des Wahlzeitraums
    @Temporal(TemporalType.DATE)
    @Column(name = "ENDE", nullable = false)
    private Date ende;
    
    //************************* generierte Methoden ****************************

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wahlzeitraum)) {
            return false;
        }
        Wahlzeitraum other = (Wahlzeitraum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.bws.entities.Wahlzeitraum[ id=" + id + " ]";
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
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the beginn
     */
    public Date getBeginn() {
        return beginn;
    }

    /**
     * @param beginn the beginn to set
     */
    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    /**
     * @return the ende
     */
    public Date getEnde() {
        return ende;
    }

    /**
     * @param ende the ende to set
     */
    public void setEnde(Date ende) {
        this.ende = ende;
    }
    
}
