package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.OggettoProtocolloNonValidoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;

public class OggettoResolver {

    private IVerticalizzazioneProtocolloAttivoService vertProtAttivoService;
    private OggettoECorpoMailProtocollo mailProtocollo;

    public OggettoResolver(IVerticalizzazioneProtocolloAttivoService vertProtAttivoService, OggettoECorpoMailProtocollo mailProtocollo) {

	this.vertProtAttivoService = vertProtAttivoService;
	this.mailProtocollo = mailProtocollo;
    }

    public String resolve() throws OggettoProtocolloNonValidoException {

	if (this.mailProtocollo == null || StringUtils.isBlank(this.mailProtocollo.getOggetto())) {
	    return null;
	}
	String oggetto = mailProtocollo.getOggetto();
	if (this.vertProtAttivoService.trasformaOggettoProtocolloUpperCase()) {
	    oggetto = oggetto.toUpperCase();
	}
	Integer lunghezzaMassima = this.vertProtAttivoService.lunghezzaMassimaOggettoProtocollo();
	if (lunghezzaMassima != null && lunghezzaMassima > oggetto.length()) {
	    throw new OggettoProtocolloNonValidoException(
		    "LA LUNGHEZZA DELL'OGGETTO SUPERA LA QUOTA MASSIMA CONSENTITA DAL PARAMETRO NUM_CARATTERI_OGGETTO DELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO");
	}
	return oggetto;
    }
}
