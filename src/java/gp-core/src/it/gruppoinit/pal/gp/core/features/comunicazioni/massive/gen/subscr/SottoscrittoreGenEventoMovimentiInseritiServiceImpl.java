package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;




import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoFirmaDocumentiNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoMovimentiInseriti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;



@Service
public class SottoscrittoreGenEventoMovimentiInseritiServiceImpl
	extends SottoscrittoreEventoGenBaseImpl<EventoMovimentiInseriti> {

    @Override
    void onEventInternal(EventoMovimentiInseriti e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Se comunicazione prevede firma imposta lo stato PRONTA_PER_FIRMA
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniGenEnum.MOVIMENTI_INSERITI.name());
	// Invoca l'elaborazione del workflow
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
	
    }

    
}