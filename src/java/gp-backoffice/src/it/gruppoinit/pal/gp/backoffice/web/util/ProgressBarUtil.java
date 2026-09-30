package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Classe di utilità per alimentare una progress bar su jsp.<br/>
 * La classe si istanzia passando come argomento il numero di iterazioni da monitorare, tale valore sarà poi
 * normalizzato a 100 per poter alimentare una progress bar che lavora da 0 a 100.
 * 
 * @author fabrizioc
 * 
 */
public class ProgressBarUtil {

    private int progBarVal;
    private int counter;
    private double currGap;
    private double gap;

    public ProgressBarUtil(int iterationSize) {

	this.progBarVal = 0;
	this.counter = 0;
	this.currGap = 0;
	this.gap = ((double) iterationSize) / 100;
    }

    /**
     * metodo da richiamare ogni volta che si vuole aggiornare il valore della progress bar.<br/>
     * questo metodo inserisce nella session http l'attributo WebConstants.PROGRESS_BAR con valore intero aggiornato
     * ogni volta che il metodo è invocato.<br/>
     * L'attributo registrato assume valori che vanno da 0 a 100
     * 
     * @param request
     */
    public void updateProgressBarValue(HttpServletRequest request) {

	if (this.gap >= 1) {
	    if (this.counter > this.currGap) {
		this.progBarVal++;
		this.currGap += this.gap;
	    }
	} else {
	    this.progBarVal += ((int) (1 / this.gap));
	}
	this.counter++;
	if (this.progBarVal > 100) {
	    this.progBarVal = 100;
	}
	request.getSession().setAttribute(WebConstants.PROGRESS_BAR, this.progBarVal);
    }

    /**
     * metodo per il reset della progress bar.<br/>
     * questo metodo rimuove dalla sessione http l'attributo WebConstants.PROGRESS_BAR
     * 
     * @param request
     */
    public void removeProgressBarValue(HttpServletRequest request) {

	request.getSession().removeAttribute(WebConstants.PROGRESS_BAR);
    }
}
