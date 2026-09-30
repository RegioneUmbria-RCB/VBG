/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;

/**
 * @author francol Command per le funzioni di amministrazione delle regole dinamiche
 * 
 */
public class Dyn2RegoleCommand {

    private Dyn2Regole regola;
    private List<Dyn2Espressioni> espressioni = new ArrayList<Dyn2Espressioni>();
    private List<Dyn2Espressioni> espressioniCancellate = new ArrayList<Dyn2Espressioni>();
    // Utilizzato per gestire il campo "dyn2Modellit" nella funzionalità di pannello di ricerca del campo dinamico
    private Dyn2Modellit dyn2Modellit;
    private Boolean popup;
    private String popupCaller;

    /**
     * 
     */
    public Dyn2RegoleCommand() {

	this.dyn2Modellit = new Dyn2Modellit();
    }

    public Dyn2Regole getRegola() {

	return regola;
    }

    public void setRegola(Dyn2Regole regola) {

	this.regola = regola;
	//	if (regola != null) {
	//	    this.espressioni = new ArrayList<Dyn2Espressioni>(regola.getDyn2Espressionis());
	//	}
    }

    public List<Dyn2Espressioni> getEspressioni() {

	return espressioni;
    }

    public List<Dyn2Espressioni> getEspressioniCancellate() {

	return espressioniCancellate;
    }

    public void setEspressioniCancellate(List<Dyn2Espressioni> espressioniCancellate) {

	this.espressioniCancellate = espressioniCancellate;
    }

    public void setEspressioni(List<Dyn2Espressioni> espressioni) {

	this.espressioni = espressioni;
    }

    public Dyn2Modellit getDyn2Modellit() {

	return dyn2Modellit;
    }

    public void setDyn2Modellit(Dyn2Modellit dyn2Modellit) {

	this.dyn2Modellit = dyn2Modellit;
    }

    public String getPrefixPopup() {

	return BooleanUtils.isTrue(getPopup()) == true ? "popup" : "";
    }

    public Boolean getPopup() {

	return popup;
    }

    public void setPopup(Boolean popup) {

	this.popup = popup;
    }

    public String getPopupCaller() {

	return popupCaller;
    }

    public void setPopupCaller(String popupCaller) {

	this.popupCaller = popupCaller;
    }
}
