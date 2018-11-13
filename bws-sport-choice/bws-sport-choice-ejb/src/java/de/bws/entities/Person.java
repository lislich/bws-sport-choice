package de.bws.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author joshua
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "NACHNAME", nullable = false)
    private String nachname;
    
    @Column(name = "VORNAME", nullable = false)
    private String vorname;
    

//****************************** Methoden *****************************
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "de.bws.entities.Person[ id=" + id + " ]";
    }
    
//************************** Getter und Setter ******************************
    
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
     * @return the nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * @param p_nachname the nachname to set
     */
    public void setNachname(String p_nachname) {
        this.nachname = p_nachname;
    }

    /**
     * @return the vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * @param p_vorname the vorname to set
     */
    public void setVorname(String p_vorname) {
        this.vorname = p_vorname;
    }
    
    /**
     * 
     * @return 
     */
    public Stufe getStufe(){
        if(this instanceof Schueler){
            return ((Schueler)this).getStufe();
        }
        Stufe stufe = new Stufe();
        stufe.setBezeichnung("");
        return stufe;
    }
    
    /**
     * 
     * @return 
     */
    public Lehrer getTutor(){
        if(this instanceof Schueler){
            return ((Schueler)this).getTutor();
        }
        Lehrer tutor = new Lehrer();
        tutor.setKuerzel("");
        return tutor;
    }
    
    /**
     * 
     * @return 
     */
    public String getKuerzel(){
        if(this instanceof Lehrer){
            return ((Schueler)this).getKuerzel();
        }

        return "";
    }
}
