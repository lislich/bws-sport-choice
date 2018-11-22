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
 * Entity-Klasse für eine Person.
 * Durch den InheritanceType "JOINED" werden die erbenden Klassen zwar in eigene 
 * Tabellen geschrieben, haben jedoch keine eigene ID. In der Datenbank erhalten 
 * sie die ID der  zugehörigen Person. Wird eine erbende Entity aus der Datenbank 
 * geholt, werden auch die Attribute der zugehörigen vererbenden Klasse (in diesem 
 * Fall Person) geholt und das ganze als eine Entity dargestellt.
 *
 * @author joshua
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    // Die ID des Datensatzes in der Datenbank
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // Der Nachname der Person
    @Column(name = "NACHNAME", nullable = false)
    private String nachname;
    // Der Vorname der Person
    @Column(name = "VORNAME", nullable = false)
    private String vorname;
    

//****************************** generierte Methoden *****************************
    
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
     * Falls die Person ein Schüler ist, gibt die Methode dessen Stufe zurück. 
     * Ansonsten wird eine Stufe mit leerer Bezeichnung zurück gegeben.
     * 
     * @author joshua
     * @return die Stufe des Schülers oder eine Stufe mit der leeren Bezeichnung
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
     * Falls die Person ein Schüler ist, gibt die Methode dessen Tutor zurück. 
     * Ansonsten wird ein Lehrer mit leerem Kürzel zurück gegeben.
     * 
     * @author joshua
     * @return Der Tutor des Schuelers oder in Lehrer mit leerem Kuerzel
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
     * Falls die Person ein Lehrer ist, wird das Kürzel zurückgegeben. Andernfalls 
     * ein leerer String.
     * 
     * @author joshua
     * @return Das Kürzel des Lehrers oder ein leerer String
     */
    public String getKuerzel(){
        if(this instanceof Lehrer){
            return ((Schueler)this).getKuerzel();
        }

        return "";
    }
}
