package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow.IWorkFlowComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoFirmaDocumentiNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class SottoscrittoreEventoAllegatiDelDettaglioElaboratiServiceImpl
	extends SottoscrittoreEventoBollettazioneBase<EventoAllegatiDelDettaglioElaborati> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IEventPublisher eventPublisher;
    private IWorkFlowComunicazioniBollettazioneService workflowService;

    @Autowired
    public SottoscrittoreEventoAllegatiDelDettaglioElaboratiServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IEventPublisher eventPublisher,
	    IWorkFlowComunicazioniBollettazioneService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.eventPublisher = eventPublisher;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoAllegatiDelDettaglioElaborati e) {

	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Se configurazione non prevede firma o se non sono presenti documenti per la comunicazione allora genera evento FirmaNonNecessaria
	if (!configurazione.contieneAllegati() || !configurazione.prevedeFirma()) {
	    this.eventPublisher
		    .publish(new EventoFirmaDocumentiNonNecessaria(ContestoComunicazioneEnum.BOLLETTAZIONE, e.getIdDettaglioComunicazione()));
	    return;
	}
	// Se comunicazione prevede firma imposta lo stato PRONTA_PER_FIRMA
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniBollettazioneEnum.PRONTA_ALLA_FIRMA.name());
	// Invoca l'elaborazione del workflow
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
