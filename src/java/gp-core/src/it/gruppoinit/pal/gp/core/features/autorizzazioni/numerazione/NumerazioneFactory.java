package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.custom.NumerazioneCustomFactory;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

public class NumerazioneFactory {

    private Map<NumerazioneEnum, NumerazioneService> numeratori;
    private Tipologiaregistri registro;

    public NumerazioneFactory(ConfigurazioneService configurazioneService, TipologiaregistriService registriService,
	    UserSecurityService userSecurityService, ProtocollazioneService protocollazioneService, Autorizzazioni aut) {

	this.registro = aut.getTipologiaregistro();
	this.numeratori = new HashMap<NumerazioneEnum, NumerazioneService>();
	this.numeratori.put(NumerazioneVuotaServiceImpl.TipoNumerazione, new NumerazioneVuotaServiceImpl());
	this.numeratori.put(NumerazioneDaConfigurazioneServiceImpl.TipoNumerazione,
		new NumerazioneDaConfigurazioneServiceImpl(configurazioneService, registriService, registro, aut));
	this.numeratori.put(NumerazioneDaProtocolloServiceImpl.TipoNumerazione,
		new NumerazioneDaProtocolloServiceImpl(registriService, aut, userSecurityService, protocollazioneService));
	this.numeratori.put(NumerazioneCustomFactory.TipoNumerazione, new NumerazioneCustomFactory(aut));
    }

    public NumerazioneService getService() {

	return this.numeratori.get(NumerazioneEnum.daRegistro(this.registro));
    }
}
