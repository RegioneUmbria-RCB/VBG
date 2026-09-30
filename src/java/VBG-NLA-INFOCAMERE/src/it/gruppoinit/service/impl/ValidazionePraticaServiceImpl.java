package it.gruppoinit.service.impl;

import it.gruppoinit.service.FilePropertiesService;
import it.gruppoinit.service.ValidazionePraticaService;
import it.gruppoinit.service.VerticalizzazioniHelperService;
import it.gruppoinit.sigepro.definitions.wssit.SitWSClient;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.definitions.StcWSClient;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.TipoAttivitaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidazionePraticaServiceImpl implements ValidazionePraticaService {

    private static final Logger log = LoggerFactory.getLogger(ValidazionePraticaServiceImpl.class);
    private FilePropertiesService filePropertiesService;
    private VerticalizzazioniHelperService verticalizzazioniHelperService;
    private StcWSClient stcWSClient;
    private SitWSClient sitWSClient;

    @Autowired
    public void setFilePropertiesService(FilePropertiesService filePropertiesService) {

	this.filePropertiesService = filePropertiesService;
    }

    @Autowired
    public void setVerticalizzazioniHelperService(VerticalizzazioniHelperService verticalizzazioniHelperService) {

	this.verticalizzazioniHelperService = verticalizzazioniHelperService;
    }

    @Autowired
    public void setStcWSClient(StcWSClient stcWSClient) {

	this.stcWSClient = stcWSClient;
    }

    @Autowired
    public void setSitWSClient(SitWSClient sitWSClient) {

	this.sitWSClient = sitWSClient;
    }

    @Override
    public NotificaAttivitaResponse insertAttivitaDiValidazione(DettaglioPraticaType dettaglioPraticaType, SportelloType nodoMittente,
	    SportelloType nodoDestinatario, String software, String tokenApp, String tokenStc) {

	NotificaAttivitaResponse notificaAttivitaResponse = null;
	log.debug("insertAttivitaDiValidazione# Start Valida istanza in base a zona ....");
	boolean isValida = validaIstanzaInBaseAZona(dettaglioPraticaType, software, tokenApp);
	dettaglioPraticaType.getCodicePraticaTelematica();
	String codiceMovimentoRientroValidazione = "";
	if (isValida) {
	    log.debug("insertAttivitaDiValidazione# Istanza valida per zona......");
	    codiceMovimentoRientroValidazione = verticalizzazioniHelperService.getRegola("COD_MOV_PRATICA_CONFORME", software, null, tokenApp);
	} else {
	    log.debug("insertAttivitaDiValidazione# Istanza non valida per zona......");
	    codiceMovimentoRientroValidazione = verticalizzazioniHelperService.getRegola("COD_MOV_PRATICA_NON_CONFORME", software, null, tokenApp);
	}
	if (StringUtils.isNotBlank(codiceMovimentoRientroValidazione)) {
	    DettaglioAttivitaType dettaglioAttivitaType = getDettaglioAttivitaType(dettaglioPraticaType.getCodicePraticaTelematica(),
		    dettaglioPraticaType.getIdPratica(), codiceMovimentoRientroValidazione);
	    try {
		notificaAttivitaResponse = stcWSClient.notificaAttivita(nodoMittente, nodoDestinatario, dettaglioAttivitaType, tokenStc, null);
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	} else {
	    log.error("insertAttivitaDiValidazione# Non è stato possibili recuperare un movimento di rientro configurato in verticalizzazione NLA_INFOCAMERE.");
	}
	log.debug("insertAttivitaDiValidazione# End Valida istanza in base a zona ....");
	return notificaAttivitaResponse;
    }

    private boolean validaIstanzaInBaseAZona(DettaglioPraticaType dettaglioPraticaType, String software, String tokenApp) {

	boolean isValida = true;
	log.debug("validaIstanzaInBaseAZona# Load procedimenti incompatibili per zona");
	Map<String, List<String>> zoneProcedimenti = filePropertiesService.loadZoneProcedimenti();
	List<LocalizzazioneNelComuneType> localizzazioneNelComuneTypes = dettaglioPraticaType.getLocalizzazione();
	for (LocalizzazioneNelComuneType localizzazioneNelComuneType : localizzazioneNelComuneTypes) {
	    String civico = localizzazioneNelComuneType.getCivico();
	    String esponente = localizzazioneNelComuneType.getEsponente();
	    String colore = localizzazioneNelComuneType.getColore();
	    String codiceViario = localizzazioneNelComuneType.getCodiceViario();
	    log.debug("validaIstanzaInBaseAZona# Recupero zone per codice viario = {}, Civico = {}, Lettera civico = {}, Colore Civico = {}",
		    codiceViario, civico, esponente, colore);
	    List<String> zone = getZoneByAccesso(codiceViario, civico, esponente, colore, software, tokenApp);
	    log.debug("validaIstanzaInBaseAZona# Zone recuperate = {}", Arrays.toString(zone.toArray()));
	    List<ProcedimentoType> procedimentoTypes = dettaglioPraticaType.getProcedimenti();
	    for (String zona : zone) {
		List<String> listaProcedimentiNonCompatibiliPerZona = zoneProcedimenti.get(zona);
		for (ProcedimentoType procedimentoType : procedimentoTypes) {
		    String codiceprocedimento = procedimentoType.getCodice();
		    String procedimento = procedimentoType.getDescrizione();
		    log.debug("validaIstanzaInBaseAZona# check compatibilità procedimento = {} []{}, zona = {}", procedimento, codiceprocedimento,
			    zona);
		    boolean isvalido = checkValidateProcedimentoAndZona(codiceprocedimento, listaProcedimentiNonCompatibiliPerZona);
		    if (!isvalido) {
			log.debug("validaIstanzaInBaseAZona# procedimento = {} []{}, zona = {} non compatibili esco e ritorno false", procedimento,
				codiceprocedimento, zona);
			isValida = false;
			break;
		    }
		}
	    }
	}
	return isValida;
    }

    private List<String> getZoneByAccesso(String codiceViario, String civico, String esponente, String colore, String software, String tokenApp) {

	List<String> zone = new ArrayList<String>();
	try {
	    zone = sitWSClient.getListaZone(codiceViario, civico, esponente, colore, tokenApp, software);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	return zone;
    }

    private boolean checkValidateProcedimentoAndZona(String codicePorcedimento, List<String> listaProcedimentiNonCompatibiliPerZona) {

	boolean isValido = true;
	for (String codProcIncopatibile : listaProcedimentiNonCompatibiliPerZona) {
	    if (codProcIncopatibile.equals(codicePorcedimento)) {
		return false;
	    }
	}
	return isValido;
    }

    private DettaglioAttivitaType getDettaglioAttivitaType(String codicePratica, String idPratica, String codicemovimento) {

	log.debug("getDettaglioAttivitaType# Creazione DettaglioAttivitaType");
	DettaglioAttivitaType datiAttivita = new DettaglioAttivitaType();
	datiAttivita.setDataAttivita(Utilities.getToday());
	datiAttivita.setOraDataAttivita(Utilities.getOrariosistema(3));
	datiAttivita.setEsito(true);
	if (StringUtils.isBlank(codicePratica)) {
	    datiAttivita.setIdPratica(idPratica);
	} else {
	    if (StringUtils.contains(codicePratica, "PROT")) {
		datiAttivita.setIdPratica(idPratica);
	    } else {
		datiAttivita.setIdPratica(codicePratica);
	    }
	}
	TipoAttivitaType attivitaType = new TipoAttivitaType();
	attivitaType.setCodice(codicemovimento);
	datiAttivita.setTipoAttivita(attivitaType);
	log.debug("getDettaglioAttivitaType# End Creazione DettaglioAttivitaType");
	return datiAttivita;
    }
}
