package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProntaAllInvioAppIo;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;

@Service
public class SottoscrittoreGenEventoComunicazioneProntaAllInvioAppIoServiceImpl
	extends SottoscrittoreEventoGenBaseImpl<EventoComunicazioneProntaAllInvioAppIo> {

    @Override
    void onEventInternal(EventoComunicazioneProntaAllInvioAppIo e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioCOmunicazione(), configurazione);
	// Imposta lo stato a "Inviata"
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioCOmunicazione(), StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
	// chiama il workflow per elaborare la riga (qui la vita del dettaglio dovrebbe essere finita)
	
	configurazione.setWarnings(e.getWarnings());
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioCOmunicazione(), configurazione);
    }
}
