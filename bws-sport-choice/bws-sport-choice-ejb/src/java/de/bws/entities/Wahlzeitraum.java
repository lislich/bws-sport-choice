/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Lisa
 */
@Entity
public class Wahlzeitraum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "BEGINN")
    private Date beginn;
    
    @Column(name = "ENDE")
    private Date ende;
    
    //************************* Methoden **************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
