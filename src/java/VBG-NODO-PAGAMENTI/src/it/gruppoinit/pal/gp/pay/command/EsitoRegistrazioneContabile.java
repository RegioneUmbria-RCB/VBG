/**
 * 
 */
package it.gruppoinit.pal.gp.pay.command;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;

/**
 * @author francol
 *
 */
public class EsitoRegistrazioneContabile {

    private PayRegistrazioniContabili registrazioneContabile;
    private List<PayStatoPagamenti> esitoPosizioni;

    public PayRegistrazioniContabili getRegistrazioneContabile() {

	return registrazioneContabile;
    }

    public void setRegistrazioneContabile(PayRegistrazioniContabili registrazioneContabile) {

	this.registrazioneContabile = registrazioneContabile;
    }

    public List<PayStatoPagamenti> getEsitoPosizioni() {

	if (esitoPosizioni == null) {
	    esitoPosizioni = new ArrayList<PayStatoPagamenti>();
	}
	return esitoPosizioni;
    }

    public void setEsitoPosizioni(List<PayStatoPagamenti> esitoPosizioni) {

	this.esitoPosizioni = esitoPosizioni;
    }
}
