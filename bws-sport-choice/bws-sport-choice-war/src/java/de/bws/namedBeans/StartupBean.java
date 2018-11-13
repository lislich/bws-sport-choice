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
import de.bws.sessionbeans.StufeFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author joshua
 */
@Singleton
@Startup
public class StartupBean {
    @EJB
    private BenutzerFacadeLocal benutzerBean;
    
    @EJB
    private StufeFacadeLocal stufeBean;
    
    /**
     * Methode, die für Testzwecke einen Adminbenutzer anlegt.
     * Sei wird vor dem Release entfernt. Diese Methode wird einmalig beim Deploy
     * aufgerufen.
     */
    @PostConstruct
    private void createRootUser(){
        this.createStufen();
        Benutzer admin = this.benutzerBean.getByName("ChoiceRoot");
        if(admin == null){
            admin = new Benutzer();
        
            try {
                admin.setBenutzername("ChoiceRoot");
                admin.setNeuesPasswort("H444bicht");
                admin.setRolle(Rolle.ADMIN);
            } catch (Exception ex) {
                Logger.getLogger(LoginNB.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.benutzerBean.create(admin);
        }
    }
    
    private void createStufen(){
        Stufe zwoelf = new Stufe();
        zwoelf.setBezeichnung("12");
        Stufe dreizehn = new Stufe();
        dreizehn.setBezeichnung("13");
        
        
        this.stufeBean.create(zwoelf);
        this.stufeBean.create(dreizehn);
        
        
    }
}
