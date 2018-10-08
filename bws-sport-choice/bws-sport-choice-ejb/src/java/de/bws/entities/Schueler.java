/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author joshua
 */
@Entity
public class Schueler extends Person implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STUFE_ID")
    private Stufe stufe;
    
    @OneToOne
    @JoinColumn(name = "WAHL", referencedColumnName = "ID")
    private Wahl wahl;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

//******************************** Methoden ***********************************

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

//******************************** Setter und Getter ***************************
    
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
    
}
