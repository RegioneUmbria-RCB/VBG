package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.IWorkFlowComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoFirmaDocumentiNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class SottoscrittoreCommissioniEventoAllegatiDelDettaglioElaboratiServiceImpl
	extends SottoscrittoreEventoCommissioniBase<EventoAllegatiDelDettaglioElaborati> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IEventPublisher eventPublisher;
    private IWorkFlowComunicazioniCommissioniService workflowService;

    @Autowired
    public SottoscrittoreCommissioniEventoAllegatiDelDettaglioElaboratiServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IEventPublisher eventPublisher,
	    IWorkFlowComunicazioniCommissioniService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.eventPublisher = eventPublisher;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoAllegatiDelDettaglioElaborati e) {

	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Se configurazione non prevede firma o se non sono presenti documenti per la comunicazione allora genera evento FirmaNonNecessaria
	if (!configurazione.contieneAllegati() || !configurazione.prevedeFirma()) {
	    this.eventPublisher
		    .publish(new EventoFirmaDocumentiNonNecessaria(ContestoComunicazioneEnum.COMMISSIONI, e.getIdDettaglioComunicazione()));
	    return;
	}
	// Se comunicazione prevede firma imposta lo stato PRONTA_PER_FIRMA
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.PRONTA_ALLA_FIRMA.name());
	// Invoca l'elaborazione del workflow
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}