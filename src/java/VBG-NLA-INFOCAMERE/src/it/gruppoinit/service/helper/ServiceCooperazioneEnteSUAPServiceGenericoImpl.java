package it.gruppoinit.service.helper;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.Intestazione;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gruppoinit.domain.helper.ParametriHelper;
import it.gruppoinit.impresainungiorno.PddServiceSUAPWS;
import it.gruppoinit.utilities.LoggerArchiviocomunicazioni;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.definitions.StcWSClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceCooperazioneEnteSUAPServiceGenericoImpl<String> extends ServiceCooperazioneEnteSUAPService<String> {

    private static final Logger log = LoggerFactory.getLogger(ServiceCooperazioneEnteSUAPServiceGenericoImpl.class);

    public ServiceCooperazioneEnteSUAPServiceGenericoImpl(InserimentoAttivitaNLARequest inserimentoAttivitaNLARequest,
	    ParametriHelper parametriHelper, PddServiceSUAPWS pddServiceSUAPWS, StcWSClient stcWSClient) {

	super(parametriHelper, pddServiceSUAPWS, stcWSClient);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected String buildAndSendMessage(ParametriHelper parametriHelper, PddServiceSUAPWS pddServiceSUAPWS, CooperazioneEnteSUAP cooperazioneEnteSUAP)
	    throws Exception {

	Intestazione intestazione = cooperazioneEnteSUAP.getIntestazione();
	OggettoCooperazione oggettoCooperazione = new OggettoCooperazione();
	oggettoCooperazione.setTipoCooperazione(parametriHelper.getCodificaNotificaTipoOperazione());
	oggettoCooperazione.setValue(StringUtils.defaultIfEmpty(parametriHelper.getDescrizioneNotificaTipoOperazione(),
		parametriHelper.getCodificaNotificaTipoOperazione()));
	intestazione.setOggettoComunicazione(oggettoCooperazione);
	// duplico la chiamata perchè non si riesce  a fare il marshall degli allegato con il dataHandler popolato
	CooperazioneEnteSUAP copiaSenzaFileNegliAllegati = duplicaChiamataConAllegatiSenzaFile(cooperazioneEnteSUAP);
	try {
	    LoggerArchiviocomunicazioni.logComunicazioneEnteSuap(copiaSenzaFileNegliAllegati.getIntestazione().getOggettoComunicazione()
		    .getTipoCooperazione(), copiaSenzaFileNegliAllegati.getIntestazione().getCodicePratica(),
		    Utilities.marshallObject(copiaSenzaFileNegliAllegati));
	} catch (Exception e) {
	    log.debug("buildAndSendMessage#Errore durante il marshalling della chiamata = {}. Codice pratica = {} ", cooperazioneEnteSUAP
		    .getIntestazione().getOggettoComunicazione().getTipoCooperazione(), cooperazioneEnteSUAP.getIntestazione().getCodicePratica());
	}
	String r = (String) "";
	try {
	    r = (String) pddServiceSUAPWS.inviaEnteSUAP(parametriHelper.getVerticalizzazioniHelper(), cooperazioneEnteSUAP);
	} catch (Exception e) {
	    throw e;
	}
	return r;
    }
}
