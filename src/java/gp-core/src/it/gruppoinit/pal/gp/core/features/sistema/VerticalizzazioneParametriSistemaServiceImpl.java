package it.gruppoinit.pal.gp.core.features.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VerticalizzazioneParametriSistemaServiceImpl implements IVerticalizzazioneParametriSistemaService {

    @Autowired
    private VerticalizzazioniService service;

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(IVerticalizzazioneParametriSistemaService.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public boolean attivaAuditWeb() {

	if (!this.isAttiva()) {
	    this.ThrowExceptionVerticalizzazioneNonAttiva();
	}
	return this.service.getBoolean(IVerticalizzazioneParametriSistemaService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneParametriSistemaService.PAR_ATTIVA_AUDIT_WEB, "S", false);
    }

    @Override
    public boolean attivaComportamentiSicurezza() {

	return this.service.isAttivaSicurezzaSistema();
    }

    @Override
    public boolean nascondiScriptLocation() {

	if (!this.isAttiva()) {
	    this.ThrowExceptionVerticalizzazioneNonAttiva();
	}
	return this.service.getBoolean(IVerticalizzazioneParametriSistemaService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneParametriSistemaService.PAR_NASCONDI_SCRIPT_LOCATION, "1", false);
    }

    @Override
    public String overrideUrlGeneraAllegato() {

	if (!this.isAttiva()) {
	    this.ThrowExceptionVerticalizzazioneNonAttiva();
	}
	return this.service.getString(IVerticalizzazioneParametriSistemaService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneParametriSistemaService.PAR_OVERRIDE_URL_GENERA_ALLEGATO);
    }

    @Override
    public Long pecClientCallTimeout() {

	if (!this.isAttiva()) {
	    this.ThrowExceptionVerticalizzazioneNonAttiva();
	}
	Integer valore = this.service.getInteger(IVerticalizzazioneParametriSistemaService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneParametriSistemaService.PAR_PEC_CLIENT_CALL_TIMEOUT);
	return valore == null ? null : Long.valueOf(valore);
    }

    @Override
    public boolean verificaFirmaOggettiInseriti() {

	if (!this.isAttiva()) {
	    this.ThrowExceptionVerticalizzazioneNonAttiva();
	}
	return this.service.getBoolean(IVerticalizzazioneParametriSistemaService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneParametriSistemaService.PAR_VERIFICA_FIRMA_OGG_INSERITI, "1", false);
    }

    private void ThrowExceptionVerticalizzazioneNonAttiva() {

	throw new IllegalArgumentException(
		"La verticalizzazione " + IVerticalizzazioneParametriSistemaService.NOME_VERTICALIZZAZIONE + " non è attiva.");
    }
}
