package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.sottoscrittori;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDocdafirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow.IWorkFlowComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.IWorkFlowComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IWorkFlowComunicazioniGenService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.EventoDocumentoDaFirmareModificato;

@Service
public class SottoscrittoreEventoDocumentoDaFirmareModificatoServiceImpl implements IEventSubscriber<EventoDocumentoDaFirmareModificato> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SottoscrittoreEventoDocumentoDaFirmareModificatoServiceImpl.class);
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniBollettazioneService workflowBollettazioneService;
    private IWorkFlowComunicazioniCommissioniService workflowCommissioniService;
    private IWorkFlowComunicazioniGenService workFlowComunicazioniMercatiService;
    private IWorkFlowComunicazioniGenService workFlowComunicazioniIstanzeService;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;

    @Autowired
    public SottoscrittoreEventoDocumentoDaFirmareModificatoServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService,
	    IWorkFlowComunicazioniBollettazioneService workflowBollettazioneService,
	    IWorkFlowComunicazioniCommissioniService workflowCommissioniService, 
	    @Qualifier("workFlowComunicazioniMercatiService") IWorkFlowComunicazioniGenService workFlowComunicazioniMercatiService,
	    @Qualifier("workFlowComunicazioniIstanzeService") IWorkFlowComunicazioniGenService workFlowComunicazioniIstanzeService,
	    IComunicazioniMassiveDAO comunicazioniMassiveDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowBollettazioneService = workflowBollettazioneService;
	this.workflowCommissioniService = workflowCommissioniService;
	this.workFlowComunicazioniMercatiService = workFlowComunicazioniMercatiService;
	this.workFlowComunicazioniIstanzeService = workFlowComunicazioniIstanzeService;
	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
    }

    @Override
    public void onEvent(EventoDocumentoDaFirmareModificato e) {

	// verifico se il documento è da firmare è legato alle tabelle delle massive
	List<MassiveDettDocdafirmare> list = comunicazioniMassiveDettaglioDAO.findDocumentiDaFirmareByIdDocDaFirmare(e.getIdDocumentoDaFirmare());
	if (list.isEmpty()) {
	    // se non è legato esco senza far niente
	    return;
	}
	//appartiene aldettaglio  di una massiva verifico
	MassiveDettDocdafirmare dett = list.get(0);
	MassiveDettaglio dettaglioMassiva = dett.getMassiveDettaglio();
	String statoAttuale = dettaglioMassiva.getUltimoStatoCompletato();
	int idDettaglioMassiva = dettaglioMassiva.getId().getCodice();
	DocumentiDaFirmare daFirmare = dett.getDocumentiDaFirmare();
	//1 -- se la firma su questa è completa
	if (daFirmare.isInCorso()) {
	    // NON faccio niente
	    return;
	}
	if (daFirmare.isRigettato()) {
	    // verifico se firmato negativamente salvo l'errore nella tabella del dettaglio
	    String messaggioErrore = getMessaggioErrore(daFirmare);
	    log.error(messaggioErrore, e);
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioMassiva, messaggioErrore);
	    log.error(messaggioErrore);
	    return;
	}
	if (daFirmare.isFirmatoConSuccesso()) {
	    List<MassiveDettDocdafirmare> findDocDaFirmarePerDettaglio = comunicazioniMassiveDettaglioDAO
		    .findDocDaFirmarePerDettaglio(idDettaglioMassiva);
	    //-- se gli altri documenti da firmare di quel dettaglio sono completi
	    boolean tuttiFirmati = true;
	    for (MassiveDettDocdafirmare massiveDettDocdafirmare : findDocDaFirmarePerDettaglio) {
		tuttiFirmati = tuttiFirmati && massiveDettDocdafirmare.getDocumentiDaFirmare().isFirmatoConSuccesso();
	    }
	    if (tuttiFirmati) {
		Integer idTestata = dettaglioMassiva.getMassiveTestata().getId().getCodice();
		ContestoComunicazioneEnum contestoMassiva = comunicazioniMassiveDAO.getContestoMassiva(idTestata);
		switch (contestoMassiva) {
		case BOLLETTAZIONE:
		    processaBollettazione(idDettaglioMassiva, statoAttuale);
		    break;
		case COMMISSIONI:
		    //-- se tutte le condizioni sono vere lancio l'evento legato alla bollettazione che mi  permette di procedere con la protocollazione o il successivo step
		    processaCommissioni(idDettaglioMassiva, statoAttuale);
		    break;
		case ISTANZE:    
		case MERCATI:
		    processaGen(idDettaglioMassiva, statoAttuale, contestoMassiva);
		    break;
		default:
		    throw new NotImplementedException("Contesto " + contestoMassiva + " non implementato");
		}
	    }
	}
    }

    private void processaBollettazione(int idDettaglioMassiva, String statoAttuale) {

	//-- se tutte le condizioni sono vere lancio l'evento legato alla bollettazione che mi  permette di procedere con la protocollazione o il successivo step
	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(idDettaglioMassiva, configurazione);
	String prossimoStato = StatoComunicazioniBollettazioneEnum.PRONTA_ALL_INVIO.name();
	if (configurazione.isRichiedeProtocollazione()) {
	    prossimoStato = StatoComunicazioniBollettazioneEnum.PRONTA_PER_PROTOCOLLAZIONE.name();
	}
	this.comunicazioniMassiveDettaglioDAO.impostaStatoConCommit(idDettaglioMassiva, prossimoStato);
	// Invoca l'elaborazione del workflow
	try {
	    workflowBollettazioneService.elabora(idDettaglioMassiva, configurazione);
	} catch (Exception e1) {
	    log.error("{}", e1);
	    this.comunicazioniMassiveDettaglioDAO.impostaStatoConCommit(idDettaglioMassiva, statoAttuale);
	}
    }

    private void processaCommissioni(int idDettaglioMassiva, String statoAttuale) {

	//-- se tutte le condizioni sono vere lancio l'evento legato alla Commissioni che mi  permette di procedere con la protocollazione o il successivo step
	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(idDettaglioMassiva, configurazione);
	String prossimoStato = StatoComunicazioniCommissioniEnum.PRONTA_ALL_INVIO.name();
	if (configurazione.isRichiedeProtocollazione()) {
	    prossimoStato = StatoComunicazioniCommissioniEnum.PRONTA_PER_PROTOCOLLAZIONE.name();
	}
	this.comunicazioniMassiveDettaglioDAO.impostaStatoConCommit(idDettaglioMassiva, prossimoStato);
	// Invoca l'elaborazione del workflow
	try {
	    workflowCommissioniService.elabora(idDettaglioMassiva, configurazione);
	} catch (Exception e1) {
	    log.error("{}", e1);
	    this.comunicazioniMassiveDettaglioDAO.impostaStatoConCommit(idDettaglioMassiva, statoAttuale);
	}
    }
    
    private void processaGen(int idDettaglioMassiva, String statoAttuale, ContestoComunicazioneEnum contesto) {

	
	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(contesto);
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(idDettaglioMassiva, configurazione);
	String prossimoStato = StatoComunicazioniBollettazioneEnum.PRONTA_ALL_INVIO.name();
	if (configurazione.isRichiedeProtocollazione()) {
	    prossimoStato = StatoComunicazioniBollettazioneEnum.PRONTA_PER_PROTOCOLLAZIONE.name();
	}
	this.comunicazioniMassiveDettaglioDAO.impostaStatoConCommit(idDettaglioMassiva, prossimoStato);
	// Invoca l'elaborazione del workflow
	try {
	    giveWorkflowComunicazioniService(contesto).elabora(idDettaglioMassiva, configurazione);
	} catch (Exception e1) {
	    log.error("{}", e1);
	    this.comunicazioniMassiveDettaglioDAO.impostaStatoConCommit(idDettaglioMassiva, statoAttuale);
	}
    }
    private IWorkFlowComunicazioniGenService giveWorkflowComunicazioniService(ContestoComunicazioneEnum contesto) {
	if(contesto == ContestoComunicazioneEnum.MERCATI) {
	    return workFlowComunicazioniMercatiService;
	}else if(contesto == ContestoComunicazioneEnum.ISTANZE) {
	    return workFlowComunicazioniIstanzeService;
	}
	
	throw new RuntimeException("giveWorkflowComunicazioniService: No valid comunicazione setted ");
    }

    public String getMessaggioErrore(DocumentiDaFirmare daFirmare) {

	return String.format("La firma del documento %s è stata rigettata dal firmatario %s con la seguente motivazione %s",
		daFirmare.getOggetti().getNomefile(), daFirmare.getFirmatario().getResponsabile(),
		StringUtils.defaultIfEmpty(daFirmare.getAnnotazioniFirmatario(), "Il firmatario non ha specificato osservazioni"));
    }
}
