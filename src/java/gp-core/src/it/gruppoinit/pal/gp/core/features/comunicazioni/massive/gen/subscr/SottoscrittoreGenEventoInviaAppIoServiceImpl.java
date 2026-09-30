package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi.EventoInviaAppIo;

@Service
public class SottoscrittoreGenEventoInviaAppIoServiceImpl extends SottoscrittoreEventoGenBaseImpl<EventoInviaAppIo> {

    @Override
    void onEventInternal(EventoInviaAppIo e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato a pronta per protocollazione
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
	// chiama il workflow per elaborare la riga
	configurazione.setWarnings(e.getWarnings());
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
