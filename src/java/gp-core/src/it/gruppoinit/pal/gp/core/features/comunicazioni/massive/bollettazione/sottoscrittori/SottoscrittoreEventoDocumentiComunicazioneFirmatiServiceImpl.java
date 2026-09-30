package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow.IWorkFlowComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoDocumentiComunicazioneFirmati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoProtocollazioneComunicazioneNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class SottoscrittoreEventoDocumentiComunicazioneFirmatiServiceImpl
	extends SottoscrittoreEventoBollettazioneBase<EventoDocumentiComunicazioneFirmati> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IEventPublisher eventPublisher;
    private IWorkFlowComunicazioniBollettazioneService workflowService;

    @Autowired
    public SottoscrittoreEventoDocumentiComunicazioneFirmatiServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IEventPublisher eventPublisher,
	    IWorkFlowComunicazioniBollettazioneService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.eventPublisher = eventPublisher;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoDocumentiComunicazioneFirmati e) {

	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Se la configurazione non prevede protocollazione genera l'evento ProtocollazioneNonNecessaria
	if (!configurazione.isRichiedeProtocollazione()) {
	    this.eventPublisher.publish(
		    new EventoProtocollazioneComunicazioneNonNecessaria(ContestoComunicazioneEnum.BOLLETTAZIONE, e.getIdDettaglioComunicazione()));
	}
	// Imposta lo stato a pronta per protocollazione
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniBollettazioneEnum.PRONTA_PER_PROTOCOLLAZIONE.name());
	// chiama il workflow per elaborare la riga
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
