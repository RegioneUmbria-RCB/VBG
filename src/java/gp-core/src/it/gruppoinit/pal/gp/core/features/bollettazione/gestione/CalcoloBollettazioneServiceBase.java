package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class CalcoloBollettazioneServiceBase {

    protected VerticalizzazioniService verticalizzazioniService;

    public CalcoloBollettazioneServiceBase(VerticalizzazioniService verticalizzazioniService) {

	super();
	this.verticalizzazioniService = verticalizzazioniService;
    }

    public void validaConfigurazioneBollettazione(Set<String> codiciComune) throws BusinessValidationException {

	boolean nodoPagamentiCheck = true;
	//	Se nodo pagamenti attivato solamente per alcuni comuni interessati
	//	Se ho configurazioni diverse per i comuni interessate
	//	Se hanno codice connettore diverso
	Map<String, String> comuneEnteCreditore = new HashMap<String, String>();
	Set<String> arEntiCreditori = new HashSet<String>();
	List<String> errorMessage = new ArrayList<String>();
	for (String cComune : codiciComune) {
	    VerticalizzazioneNodoPagamentiServiceImpl v = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, cComune);
	    if (v.isAttiva()) {
		nodoPagamentiCheck = true;
		String arCodFiscEnteCreditore = v.arCodFiscEnteCreditore();
		if (StringUtils.isBlank(arCodFiscEnteCreditore)) {
		    // non configurata correttamente VerticalizzazioneNodoPagamentiServiceImpl.AR_COD_FISC_ENTE_CREDITORE
		    errorMessage.add("Parametro di verticalizzazione " +
			    VerticalizzazioneNodoPagamentiServiceImpl.AR_COD_FISC_ENTE_CREDITORE +
			    " non configurato per il comune " +
			    cComune +
			    " e software " +
			    ORMHelper.getSoftware());
		}
		arEntiCreditori.add(arCodFiscEnteCreditore);
		comuneEnteCreditore.put(cComune, arCodFiscEnteCreditore);
	    }
	}
	if (nodoPagamentiCheck) { // almeno una configurazione attiva faccio la verifica
	    if (arEntiCreditori.size() > 1) {
		// Se hanno codice connettore diverso
		// Se ho configurazioni diverse per i comuni interessate
		// il set contiene Stringhe non duplicate quindi alla fine del controllo dovrei avere solamente un codice connettore
		errorMessage.add(
			"Nella Bollettazione è possibile inviare le posizioni debitorie ad un solo connettore e sono presenti configurazioni differenti " +
				VerticalizzazioneNodoPagamentiServiceImpl.AR_COD_FISC_ENTE_CREDITORE +
				arEntiCreditori +
				" per i comuni " +
				codiciComune +
				" e software " +
				ORMHelper.getSoftware());
	    }
	    // Se nodo pagamenti attivato solamente per alcuni comuni interessati
	    for (String c : codiciComune) {
		if (comuneEnteCreditore.get(c) == null) {
		    //
		    errorMessage.add("Non è stata trovata la configurazione del nodo pagamenti per il comune " + c);
		}
	    }
	}
	if (!errorMessage.isEmpty()) {
	    StringBuilder message = new StringBuilder();
	    message.append(
		    "Non è possibile procedere: è attivato il servizio di collegamento con il nodo dei pagamenti ma non sono valide le seguenti configurazioni: ");
	    for (String s : errorMessage) {
		message.append("<br />").append(s);
	    }
	    throw new BusinessValidationException(message.toString());
	}
    }
}
