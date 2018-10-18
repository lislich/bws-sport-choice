/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.namedBeans;


import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.LehrerFacadeLocal;
import de.bws.sessionbeans.PersonFacadeLocal;
import de.bws.sessionbeans.SchuelerFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author joshua
 */
@Named(value = "benutzerVerwaltenNB")
@ViewScoped
public class BenutzerVerwaltenNB implements Serializable{

    @EJB
    private PersonFacadeLocal personBean;
    
    @EJB
    private LehrerFacadeLocal lehrerBean;
    
    @EJB
    private SchuelerFacadeLocal schuelerBean;
    
    @EJB
    private BenutzerFacadeLocal benutzerBean;
        
    @Inject
    private BenutzerNB benutzerNB;
    
    private Stufe stufe;
    private String rolle;
    
    /**
     * Creates a new instance of BenutzerVerwaltenNB
     */
    public BenutzerVerwaltenNB() {
    }

    
    @PostConstruct
    private void init(){
        
    }
    
    public String loeschen(){
        List<Benutzer> zumLoeschen = new ArrayList<>();
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        Iterator<UIComponent> children = viewRoot.getFacetsAndChildren();
        UIComponent child;
        while(children.hasNext()){
            child = children.next();
            if(child.getAttributes().get("name") == "wahl" && (boolean)child.getAttributes().get("checked")){
                this.benutzerBean.remove(this.benutzerBean.find(child.getAttributes().get("value")));
            }
        }
        
        return "benutzerVerwalten";
    }
    
    public String aendern(){
        
        return "benutzerAendern";
    }
    
    public String hochstufen(){
        
        return null;
    }
    
    public String abstufen(){
        
        return null;
    }
    
    
    
    /**
     * 
     * @return 
     */
    public List<String> getAlleRollen(){
        List<String> rollen = new ArrayList<>();
        rollen.add("Alle");
        for(Rolle r:Rolle.values()){
            rollen.add(r.name());
        }
        
        return rollen;
    }
    
    /**
     * @return the stufe
     */
    public Stufe getStufe() {
        return stufe;
    }

    /**
     * @param stufe the stufe to set
     */
    public void setStufe(Stufe stufe) {
        this.stufe = stufe;
    }

    /**
     * @return the rolle
     */
    public String getRolle() {
        return rolle;
    }

    /**
     * @param rolle the rolle to set
     */
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
    
    public List<Benutzer> getAlleBenutzer() {
        return this.benutzerBean.findAll();
    }
    
    public void auswaehlen(){
        this.benutzerNB.auswaehlen(this.rolle, this.stufe);
    }
}
