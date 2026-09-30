package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnectorCaricamentoMassivo;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.IElaborazioneCaricamentoMassivoService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;

public class ElaborazioneCaricamentoMassivoServiceImpl implements IElaborazioneCaricamentoMassivoService {

    private static final Logger log = LoggerFactory.getLogger(ElaborazioneCaricamentoMassivoServiceImpl.class);
    private static final String SERVICE_NAME = "ELABORAZIONE_CARICAMENTO_MASSIVO";
    private IPayConnector iPayConnector;
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private PayProfiliEntiCreditoriService profiliEntiCreditoriService;
    private ConfigurazionePagamentiService configurazionePagamentiService;
    private String idComune = null;
    private String codiceConnettore = null;
    private String quartzExpression = null;
    private String codiceProfilo;

    public ElaborazioneCaricamentoMassivoServiceImpl(IPayConnector iPayConnector, PayConnectorConfigValuesService payConnectorConfigValuesService,
	    PayProfiliEntiCreditoriService profiliEntiCreditoriService, ConfigurazionePagamentiService configurazionePagamentiService)
	    throws PayConfigurationException {

	super();
	this.iPayConnector = iPayConnector;
	this.payConnectorConfigValuesService = payConnectorConfigValuesService;
	this.profiliEntiCreditoriService = profiliEntiCreditoriService;
	this.configurazionePagamentiService = configurazionePagamentiService;
	this.codiceConnettore = iPayConnector.getConnectorCode();
	String idComuneOrig = ORMHelper.getIdcomune();
	PayProfiliEntiCreditori pprof = this.profiliEntiCreditoriService.findByConnectorCode(this.codiceConnettore);
	this.codiceProfilo = pprof.getCfCodiceProfilo();
	this.idComune = pprof.getId().getIdcomune();
	ORMHelper.setIdcomune(idComune);
	parametri();
	ORMHelper.setIdcomune(idComuneOrig);
    }

    private void parametri() {

	quartzExpression = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_CARICAM_MASS_QUARTZEXP,
		iPayConnector.getConnectorCode());
    }

    @Override
    public String getServiceName() {

	return SERVICE_NAME;
    }

    @Override
    public boolean isAttivo() {

	return StringUtils.defaultIfEmpty(payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_CARICAM_MASS_ATTIVO,
		iPayConnector.getConnectorCode()), "false").equalsIgnoreCase("true");
    }

    @Override
    public EsitoElaborazione elabora() {

	log.debug("Elaboro i caricamenti massivi per codiceprofilo {}", this.codiceProfilo);
	try {
	    configurazionePagamentiService.configuraRequestPerEnteCreditore(this.codiceProfilo);
	} catch (PayConfigurationException e) {
	    log.error("Errore nella configurazione della request per codiceprofilo " + this.codiceProfilo, e);
	    return newEsitoConErrore(e);
	}
	if (!isAttivo()) {
	    return newEsitoConErrore("Servizio non attivo da parametro di configurazione " + ConfigParamNames.SCHED_CARICAM_MASS_ATTIVO + "=" +
				     payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_CARICAM_MASS_ATTIVO,
					     iPayConnector.getConnectorCode()));
	}
	if (!(this.getIPayConnector() instanceof IPayConnectorCaricamentoMassivo)) {
	    return newEsitoConErrore("Il connettore " + getIPayConnector().getConnectorCode() + " non implementa l'interfaccia " +
				     IPayConnectorCaricamentoMassivo.class);
	}
	return ((IPayConnectorCaricamentoMassivo) this.getIPayConnector()).elaboraCaricamentoMassivoPosizioni(getParams());
    }

    private Map<String, String> getParams() {

	Map<String, String> params = new HashMap<>();
	params.put(ConfigParamNames.SCHED_CARICAM_MASS_STRATEGIA.name(), StringUtils.defaultIfEmpty(payConnectorConfigValuesService
		.getValoreParametroConfigurazione(ConfigParamNames.SCHED_CARICAM_MASS_STRATEGIA, iPayConnector.getConnectorCode()), "EMPTY"));
	params.put(IPayConnectorCaricamentoMassivo.PARAMS_ELABORAZIONE.CODICE_PROFILO.name(), codiceProfilo);
	params.put(IPayConnectorCaricamentoMassivo.PARAMS_ELABORAZIONE.CODICE_CONNETTORE.name(), codiceConnettore);
	return params;
    }

    @Override
    public IPayConnector getIPayConnector() {

	return this.iPayConnector;
    }

    @Override
    public String getQuartzScheduleExpression() {

	return quartzExpression;
    }

    private EsitoElaborazione newEsitoConErrore(Exception e) {

	log.error("", e);
	return new EsitoElaborazione(false, e.getMessage());
    }

    private EsitoElaborazione newEsitoConErrore(String msg) {

	log.error("{}", msg);
	return new EsitoElaborazione(false, msg);
    }
}
