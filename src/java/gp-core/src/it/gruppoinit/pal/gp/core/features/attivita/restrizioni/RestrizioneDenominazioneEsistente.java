package it.gruppoinit.pal.gp.core.features.attivita.restrizioni;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.service.VwIAttivitalistaService;

public class RestrizioneDenominazioneEsistente implements IRestrizione {

    private IVerticalizzazioneIAttivitaService verticalizzazioneIAttivitaService;
    private VwIAttivitalistaService vwIAttivitalistaService;
    private String denominazioneAttivita;
    private boolean checkAttive;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 20;
    private static final String ETICHETTA = "service_error.esiste_gia_una_attivita_con_la_denominazione";

    public RestrizioneDenominazioneEsistente(IVerticalizzazioneIAttivitaService verticalizzazioneIAttivitaService,
	    VwIAttivitalistaService vwIAttivitalistaService, String denominazioneAttivita, boolean checkAttive) {

	this.verticalizzazioneIAttivitaService = verticalizzazioneIAttivitaService;
	this.vwIAttivitalistaService = vwIAttivitalistaService;
	this.denominazioneAttivita = denominazioneAttivita;
	this.checkAttive = checkAttive;
    }

    @Override
    public RestrizioneVerificataBean verifica() {

	RestrizioneVerificataBean retVal = new RestrizioneVerificataBean();
	if (StringUtils.isBlank(this.denominazioneAttivita)) {
	    return retVal;
	}
	String[] software = null;
	String gruppoSoftware = this.verticalizzazioneIAttivitaService.getGruppoSoftware();
	//
	if (StringUtils.isNotBlank(gruppoSoftware)) {
	    software = StringUtils.stripAll(gruppoSoftware.split(","));
	}
	//
	retVal.setEtichettaEccezione(RestrizioneDenominazioneEsistente.ETICHETTA);
	retVal.setCriterioDiRicerca(this.denominazioneAttivita);
	retVal.setElencoAttivita(this.vwIAttivitalistaService.findBydenominazione(denominazioneAttivita, software, this.checkAttive,
		RestrizioneDenominazioneEsistente.FIRST_RESULT, RestrizioneDenominazioneEsistente.MAX_RESULT));
	return retVal;
    }
}
