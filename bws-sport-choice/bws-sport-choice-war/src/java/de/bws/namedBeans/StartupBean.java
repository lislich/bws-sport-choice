package de.bws.namedBeans;

import de.bws.data.Rolle;
import de.bws.entities.Benutzer;
import de.bws.entities.Stufe;
import de.bws.sessionbeans.BenutzerFacadeLocal;
import de.bws.sessionbeans.StufeFacadeLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Diese Methode legt einen Admin-Benutzer und die Stufen an.
 * Durch @Startup wird diese Bean immer beim Start der Applikation erzeugt.
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
     * Ruft die Methoden zum Erstellen vom Admin-Benutzer und den Stufen auf.
     * Wird nach dem Erstellen der Bean aufgerufen.
     * 
     * @author joshua
     */
    @PostConstruct
    private void startUp(){
        this.createRootUser();
        this.createStufen();
    }
    
    /**
     * Methode, die einen Adminbenutzer anlegt.
     * Anmeldedaten des Benutzers werden vor dem Release geändert. 
     * 
     * @author joshua
     */
    private void createRootUser(){
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
    
    /**
     * Methode, die die Stufen 12 und 13 anlegt, wenn diese noch nicht vorhanden sind.
     * 
     * @author Lisa
     */
    private void createStufen(){
        Stufe zwoelf = null;
        Stufe dreizehn = null;
        
        // Sucht alle eingetragenen Stufen aus der Datenbank und ermittelt, ob Stufe "12" und "13" vorhanden sind
        List<Stufe> stufen = this.stufeBean.findAll();
        if(stufen != null && !(stufen.isEmpty())){
            for(Stufe s : stufen){
                if(s.getBezeichnung().equals("12")){
                    zwoelf = s;
                }
                if(s.getBezeichnung().equals("13")){
                    dreizehn = s;
                }
            }
        }
        
        // Wenn keine Stufe "12" vorhanden ist wird diese angelegt
        if(zwoelf == null){
            zwoelf = new Stufe();
            zwoelf.setBezeichnung("12");
            this.stufeBean.create(zwoelf);
        }
        // Wenn keine Stufe "13" vorhanden ist wird diese angelegt
        if(dreizehn == null){
            dreizehn = new Stufe();
            dreizehn.setBezeichnung("13");
            this.stufeBean.create(dreizehn);
        }       
        
    }
}
