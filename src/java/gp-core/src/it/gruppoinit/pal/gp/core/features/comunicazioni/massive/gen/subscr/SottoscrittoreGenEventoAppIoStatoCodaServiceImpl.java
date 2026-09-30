package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi.EventoAppIoStatoCoda;

@Service
public class SottoscrittoreGenEventoAppIoStatoCodaServiceImpl extends SottoscrittoreEventoGenBaseImpl<EventoAppIoStatoCoda> {

    @Override
    void onEventInternal(EventoAppIoStatoCoda e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// chiama il workflow per elaborare la riga
	configurazione.setWarnings(e.getWarnings());
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniGenEnum.APPIO_STATO_CODA.name());
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
