package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;




import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoFirmaDocumentiNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;



@Service
public class SottoscrittoreGenEventoAllegatiDelDettaglioElaboratiServiceImpl
	extends SottoscrittoreEventoGenBaseImpl<EventoAllegatiDelDettaglioElaborati> {

    @Override
    void onEventInternal(EventoAllegatiDelDettaglioElaborati e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Se configurazione non prevede firma o se non sono presenti documenti per la comunicazione allora genera evento FirmaNonNecessaria
	if (!configurazione.contieneAllegati() || !configurazione.prevedeFirma()) {
	    this.eventPublisher
		    .publish(new EventoFirmaDocumentiNonNecessaria(e.getContesto(), e.getIdDettaglioComunicazione()));
	    return;
	}
	// Se comunicazione prevede firma imposta lo stato PRONTA_PER_FIRMA
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.PRONTA_ALLA_FIRMA.name());
	// Invoca l'elaborazione del workflow
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
	
    }

    
}