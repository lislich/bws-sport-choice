package de.bws.namedBeans;

import de.bws.entities.Stufe;
import de.bws.sessionbeans.StufeFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 * @author Lisa
 * 
 * Diese ManagedBean wird genutzt um alle Stufen zu ermitteln.
 */
@Named(value = "stufeNB")
@ViewScoped
public class StufeNB implements Serializable{

    // Schnittstelle zur Datenbank für Entitäten vom Typ Stufe
    @EJB
    private StufeFacadeLocal stufeBean;
    
    // Liste aller Stufen
    private List<Stufe> stufen;

    /**
     * @author Lisa
     * @return the stufen
     * 
     * Diese Methode sucht alle Stufen aus der Datenbank.
     */
    public List<Stufe> getStufen(){
        this.stufen = this.stufeBean.findAll();
        return this.stufen;
    }

    /**
     * @param p_stufen the stufen to set
     */
    public void setStufen(List<Stufe> p_stufen) {
        this.stufen = p_stufen;
    }
    
    
}
