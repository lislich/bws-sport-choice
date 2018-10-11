/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.converter;

import de.bws.entities.Stufe;
import de.bws.sessionbeans.StufeFacadeLocal;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author joshua
 */
@FacesConverter("StufeConverter")
public class StufeConverter implements Converter{
    
    @EJB
    private StufeFacadeLocal stufeBean;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return this.stufeBean.get("SELECT s FROM Stufe s WHERE s.bezeichnung = " + value + "'").get(0);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Stufe)value).getBezeichnung();
    }
    
}
