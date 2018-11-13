package de.bws.entities;

import de.bws.data.Rolle;
import de.bws.security.Passwort;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
    
    @Column(name = "BENUTZERNAME", nullable = false)
    private String benutzername;
    
    @Column(name = "SALT", nullable = false)
    private byte[] salt;
    
    @Column(name = "PASSWORT", nullable = false)
    private String passwort;

    @Column(name = "ROLLE", nullable = false)
    private Rolle rolle;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID", nullable = true)
    private Person person;
    
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
    public byte[] getSalt() {
        return salt;
    }

    /**
     * @param p_salt the salt to set
     */
    public void setSalt(byte[] p_salt) {
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
     * @throws java.lang.Exception
     */
    public void setPasswort(String p_passwort) throws Exception {
        this.passwort = p_passwort;
    }
    
    public boolean setNeuesPasswort(String p_passwort)  {
        byte[] saltNeu = Passwort.saltGenerieren();
        try{
            this.passwort = Passwort.hashen(p_passwort, saltNeu);
        } catch (Exception ex) {
            Logger.getLogger(Benutzer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        this.setSalt(saltNeu);
        return true;
    }

    /**
     * @return the rolle
     */
    public Rolle getRolle() {
        return rolle;
    }

    /**
     * @param rolle the rolle to set
     */
    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
//        if(this.person == null){
//            EntityManager em = Persistence.createEntityManagerFactory("bws-sport-choice-ejbPU").createEntityManager();
//            Query q = em.createQuery("SELECT b.person FROM Benutzer b WHERE b.id = :id");
//            q.setParameter("id", this.id);
//            this.person = (Person) q.getSingleResult();
//        }
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }
    
}
