package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.IWorkFlowComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoDocumentiComunicazioneFirmati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoProtocollazioneComunicazioneNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class SottoscrittoreCommissioniEventoDocumentiComunicazioneFirmatiServiceImpl
	extends SottoscrittoreEventoCommissioniBase<EventoDocumentiComunicazioneFirmati> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IEventPublisher eventPublisher;
    private IWorkFlowComunicazioniCommissioniService workflowService;

    @Autowired
    public SottoscrittoreCommissioniEventoDocumentiComunicazioneFirmatiServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IEventPublisher eventPublisher,
	    IWorkFlowComunicazioniCommissioniService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.eventPublisher = eventPublisher;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoDocumentiComunicazioneFirmati e) {

	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Se la configurazione non prevede protocollazione genera l'evento ProtocollazioneNonNecessaria
	if (!configurazione.isRichiedeProtocollazione()) {
	    this.eventPublisher.publish(
		    new EventoProtocollazioneComunicazioneNonNecessaria(ContestoComunicazioneEnum.COMMISSIONI, e.getIdDettaglioComunicazione()));
	}
	// Imposta lo stato a pronta per protocollazione
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.PRONTA_PER_PROTOCOLLAZIONE.name());
	// chiama il workflow per elaborare la riga
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
