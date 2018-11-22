package de.bws.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *  Entity-Klasse für einen Lehrer. 
 * 
 * @author joshua
 */
@Entity
public class Lehrer extends Person implements Serializable {

    private static final long serialVersionUID = 1L;
    // Das Kürzel des Lehrers
    @Column(name="KUERZEL", nullable = false)
    private String kuerzel;

    //****************************** generierte Methoden **************************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lehrer)) {
            return false;
        }
        Lehrer other = (Lehrer) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.bws.entities.Lehrer[ id=" + super.getId() + " ]";
    }

    //******************************* Getter und Setter ****************************
    
    /**
     * @return the kuerzel
     */
    @Override
    public String getKuerzel() {
        return kuerzel;
    }

    /**
     * @param p_kuerzel the kuerzel to set
     */
    public void setKuerzel(String p_kuerzel) {
        this.kuerzel = p_kuerzel;
    }
    
}
