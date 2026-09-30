package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi.EventoAppIoSchedulata;

@Service
public class SottoscrittoreGenEventoAppIoSchedulataServiceImpl extends SottoscrittoreEventoGenBaseImpl<EventoAppIoSchedulata> {

    @Override
    void onEventInternal(EventoAppIoSchedulata e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato a pronta per protocollazione
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name());
	// chiama il workflow per elaborare la riga
	configurazione.setWarnings(e.getWarnings());
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
