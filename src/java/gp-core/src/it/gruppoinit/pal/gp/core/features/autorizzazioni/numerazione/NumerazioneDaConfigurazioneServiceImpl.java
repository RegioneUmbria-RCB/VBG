package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;

public class NumerazioneDaConfigurazioneServiceImpl implements NumerazioneService {

    private Integer idRegistro;
    private String numeroAutorizzazione;
    private Date dataAutorizzazione;
    private TipologiaregistriService registriService;
    public static NumerazioneEnum TipoNumerazione = NumerazioneEnum.DA_CONFIGURAZIONE;

    public NumerazioneDaConfigurazioneServiceImpl(ConfigurazioneService configurazioneService, TipologiaregistriService registriService,
	    Tipologiaregistri registro, Autorizzazioni aut) {

	this.registriService = registriService;
	this.idRegistro = registro.getId().getCodice();
	if (aut.getAutorizdata() == null) {
	    this.dataAutorizzazione = Boolean.TRUE.equals(registro.getTrFlagdataauto()) ? null : Calendar.getInstance().getTime();
	} else {
	    this.dataAutorizzazione = aut.getAutorizdata();
	}
	if (StringUtils.isNotBlank(aut.getAutoriznumero())) {
	    this.numeroAutorizzazione = aut.getAutoriznumero();
	} else {
	    if (Boolean.TRUE.equals(registro.getFlagUsaProgrConf())) {
		this.numeroAutorizzazione = configurazioneService.findById(new ConfigurazioneId(registro.getSoftware().getCodice()))
			.getProgressivoRegistriAut();
		return;
	    }
	    this.numeroAutorizzazione = registro.getTrProgressivo();
	}
    }

    @Override
    public EstremiAutorizzazione get() {

	return new EstremiAutorizzazione(null, this.numeroAutorizzazione, this.dataAutorizzazione);
    }

    @Override
    public EstremiAutorizzazione assegnaNumero() {

	this.registriService.scriviProgressivoRegistro(idRegistro, this.numeroAutorizzazione);
	return new EstremiAutorizzazione(null, this.numeroAutorizzazione, null);
    }
}
