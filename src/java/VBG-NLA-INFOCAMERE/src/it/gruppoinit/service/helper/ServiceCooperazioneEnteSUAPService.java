package it.gruppoinit.service.helper;

import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.InfoSchema;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.Intestazione;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gruppoinit.domain.helper.ParametriHelper;
import it.gruppoinit.impresainungiorno.PddServiceSUAPWS;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.definitions.StcWSClient;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ParametroType;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServiceCooperazioneEnteSUAPService<E> {

    private static final Logger log = LoggerFactory.getLogger(ServiceCooperazioneEnteSUAPService.class);
    private PddServiceSUAPWS pddServiceSUAPWS;
    private ParametriHelper parametriHelper;
    private StcWSClient stcWSClient;

    public ServiceCooperazioneEnteSUAPService(ParametriHelper parametriHelper, PddServiceSUAPWS pddServiceSUAPWS, StcWSClient stcWSClient) {

	this.parametriHelper = parametriHelper;
	this.pddServiceSUAPWS = pddServiceSUAPWS;
	this.stcWSClient = stcWSClient;
    }

    public E sendMessage(InserimentoAttivitaNLARequest inserimentoAttivitaNLARequest) throws Exception {

	CooperazioneEnteSUAP cooperazioneEnteSUAP = new CooperazioneEnteSUAP();
	log.debug("sendMessage# Popolo sezione info schema....");
	InfoSchema infoSchema = new InfoSchema();
	infoSchema.setData(Utilities.getXMLGregorianCalendar(new Date()));
	infoSchema.setVersione(parametriHelper.getVerticalizzazioniHelper().getINFO_SCHEMA_VERSIONE());
	//
	Intestazione intestazione = new Intestazione();
	EstremiEnte estremiEnte = new EstremiEnte();
	estremiEnte.setCodiceAmministrazione(StringUtils.defaultIfEmpty(
		parametriHelper.getVerticalizzazioniHelper().getCODICE_AMMINISTRAZIONE_DEST(), "ND"));
	estremiEnte.setCodiceAoo(StringUtils.defaultIfEmpty(parametriHelper.getVerticalizzazioniHelper().getCODICE_AOO_DEST(), "ND"));
	estremiEnte.setPec(StringUtils.defaultIfEmpty(parametriHelper.getVerticalizzazioniHelper().getPEC_SPORTELLO_DEST(), "ND@PEC.COM"));
	estremiEnte.setValue(StringUtils.defaultIfEmpty(parametriHelper.getVerticalizzazioniHelper().getCODICE_AMMINISTRAZIONE_DEST(), "Backoffice"));
	//
	EstremiSuap estremiSuap = new EstremiSuap();
	estremiSuap.setCodiceAmministrazione(parametriHelper.getVerticalizzazioniHelper().getCODICE_AMMINISTRAZIONE());
	estremiSuap.setCodiceAoo(parametriHelper.getVerticalizzazioniHelper().getCODICE_AOO());
	estremiSuap.setIdentificativoSuap(new BigInteger(parametriHelper.getVerticalizzazioniHelper().getIDENTIFICATIVO_SUAP()));
	estremiSuap.setValue(parametriHelper.getVerticalizzazioniHelper().getDESCRIZIONE_SUAP());
	//
	intestazione.setEnteMittente(estremiEnte);
	intestazione.setSuapCompetente(estremiSuap);
	String codicePraticatelematica = this.codicePraticaTelematicaFromRequest(inserimentoAttivitaNLARequest);
	log.debug("sendMessage# codice pratica = {}", StringUtils.defaultIfEmpty(codicePraticatelematica, "Errore: Codice non impostato"));
	intestazione.setCodicePratica(codicePraticatelematica);
	intestazione.setTestoComunicazione(inserimentoAttivitaNLARequest.getDatiAttivita().getParere());
	// deve essere settato nella classe che implementa il tip comunicazione 
	//intestazione.setOggettoComunicazione(value);
	//
	cooperazioneEnteSUAP.setInfoSchema(infoSchema);
	cooperazioneEnteSUAP.setIntestazione(intestazione);
	// documenti
	AllegatoCooperazione allegatoCooperazione = null;
	List<DocumentiType> documentiTypes = inserimentoAttivitaNLARequest.getDatiAttivita().getDocumenti();
	for (DocumentiType documentiType : documentiTypes) {
	    allegatoCooperazione = new AllegatoCooperazione();
	    allegatoCooperazione.setDescrizione(documentiType.getDocumento());
	    allegatoCooperazione.setEmbeddedFileRef(documentiType.getAllegati().getFile().getBinaryData());
	    allegatoCooperazione.setNomeFile(documentiType.getAllegati().getAllegato());
	    allegatoCooperazione.setNomeFileOriginale(documentiType.getAllegati().getAllegato());
	    cooperazioneEnteSUAP.getAllegato().add(allegatoCooperazione);
	}
	E result = buildAndSendMessage(parametriHelper, pddServiceSUAPWS, cooperazioneEnteSUAP);
	return result;
    }

    private String codicePraticaTelematicaFromRequest(InserimentoAttivitaNLARequest inserimentoAttivitaNLARequest) {

	List<ParametroType> altriDati = inserimentoAttivitaNLARequest.getDatiAttivita().getAltriDati();
	if (altriDati != null) {
	    for (ParametroType parametroType : altriDati) {
		if (parametroType.getNome().equalsIgnoreCase("$DOMANDESTC.ID_DOMANDAMITT$")) {
		    return StringUtils.defaultString(parametroType.getValore().get(0).getCodice()).trim();
		}
	    }
	}
	return null;
    }

    protected CooperazioneEnteSUAP duplicaChiamataConAllegatiSenzaFile(CooperazioneEnteSUAP cooperazioneEnteSUAP) {

	CooperazioneEnteSUAP copia = new CooperazioneEnteSUAP();
	try {
	    BeanUtils.copyProperties(copia, cooperazioneEnteSUAP);
	    List<AllegatoCooperazione> origin = cooperazioneEnteSUAP.getAllegato();
	    for (AllegatoCooperazione allegatoCooperazione : origin) {
		AllegatoCooperazione target = new AllegatoCooperazione();
		BeanUtils.copyProperties(target, allegatoCooperazione);
		copia.getAllegato().add(target);
	    }
	} catch (IllegalAccessException e) {
	    log.debug("duplicaChiamataConAllegatiSenzaFile# ");
	} catch (InvocationTargetException e) {
	    log.debug("duplicaChiamataConAllegatiSenzaFile# ");
	}
	List<AllegatoCooperazione> l = copia.getAllegato();
	for (AllegatoCooperazione allegatoCooperazione : l) {
	    allegatoCooperazione.setEmbeddedFileRef(null);
	}
	return copia;
    }

    protected abstract E buildAndSendMessage(ParametriHelper parametriHelper, PddServiceSUAPWS pddServiceSUAPWS,
	    CooperazioneEnteSUAP cooperazioneEnteSUAP) throws Exception;
}
