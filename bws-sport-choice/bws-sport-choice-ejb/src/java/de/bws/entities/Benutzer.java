/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.entities;

import java.io.Serializable;
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
public class Benutzer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "BENUTZERNAME")
    private String benutzername;
    
    @Column(name = "SALT")
    private String salt;
    
    @Column(name = "PASSWORT")
    private String passwort;

//*************************** Methoden ****************************
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Benutzer)) {
            return false;
        }
        Benutzer other = (Benutzer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.bws.entities.Benutzer[ id=" + id + " ]";
    }
    
    //******************** Getter- und Setter *********************
    
    public Long getId() {
        return id;
    }

    private void setId(Long p_id) {
        this.id = p_id;
    }

    /**
     * @return the benutzername
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * @param p_benutzername the benutzername to set
     */
    public void setBenutzername(String p_benutzername) {
        this.benutzername = p_benutzername;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param p_salt the salt to set
     */
    private void setSalt(String p_salt) {
        this.salt = p_salt;
    }

    /**
     * @return the passwort
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * @param p_passwort the passwort to set
     */
    public void setPasswort(String p_passwort) {
        this.passwort = p_passwort;
    }
    
}
