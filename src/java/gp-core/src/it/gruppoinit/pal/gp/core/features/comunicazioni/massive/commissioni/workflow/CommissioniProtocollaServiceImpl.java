package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocolloSoggettoCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniToCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProtocollata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoProtocollazioneComunicazioneNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloSourceEnum;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.utils.IDateFormatService;
import it.gruppoinit.protocollo.schemas.messages.AllegatoType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

@Service
public class CommissioniProtocollaServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniCommissioni> {

    private static final Logger log = LoggerFactory.getLogger(CommissioniProtocollaServiceImpl.class);
    private ProtocollazioneService protocollazioneService;
    private MailtipoService mailtipoService;
    private AmministrazioniService amministrazioniService;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IDateFormatService dateFormatService;
    private IEventPublisher publisher;
    private OggettiService oggettiService;
    private IComunicazioniToCommissioniService comunicazioniToCommissioniService;

    @Autowired
    public CommissioniProtocollaServiceImpl(ProtocollazioneService protocollazioneService, MailtipoService mailtipoService,
	    AmministrazioniService amministrazioniService, IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    IDateFormatService dateFormatService, IEventPublisher publisher, OggettiService oggettiService,
	    IComunicazioniToCommissioniService comunicazioniToCommissioniService) {

	super();
	this.protocollazioneService = protocollazioneService;
	this.mailtipoService = mailtipoService;
	this.amministrazioniService = amministrazioniService;
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.dateFormatService = dateFormatService;
	this.publisher = publisher;
	this.oggettiService = oggettiService;
	this.comunicazioniToCommissioniService = comunicazioniToCommissioniService;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniCommissioni configurazione) {

	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	if (!configurazione.isRichiedeProtocollazione()) {
	    this.publisher
		    .publish(new EventoProtocollazioneComunicazioneNonNecessaria(ContestoComunicazioneEnum.COMMISSIONI, idDettaglioComunicazione));
	    return;
	}
	if (dettaglio.getDestinatari().getResponsabili() != null && dettaglio.getDestinatari().getResponsabili().getId() != null
		&& dettaglio.getDestinatari().getResponsabili().getId().getCodice() != null) {
	    // SE IL DESTINATARIO E' responsabile allora non è necessariala protocollazione non saprei come gestirla come soggetto della protocolalzione
	    // che ad oggi gestisce solamente anagrafe o amministrazione
	    // CHIARIMENTI 
	    this.publisher
		    .publish(new EventoProtocollazioneComunicazioneNonNecessaria(ContestoComunicazioneEnum.COMMISSIONI, idDettaglioComunicazione));
	    return;
	}
	// SE CONFIGURAZIONE.richiedeProtocollazione==FALSE 
	// GENERA COMMAND PER PROTOCOLLAZIONE
	// posso avere una lista???
	//	Verifico se righe si riferiscono a unico comune allora OK
	//	==> altrimenti rilancio errore "Non è possibile protocollare con bollettazioni che si riferiscono a più enti con "
	//		+ "protocolli differenti"
	// INVOCA PROTOCOLLAZIONE SERVICEIMPL
	try {
	    List<ISoftwareComuneData> softwareComuneFromIdDettaglioList = this.comunicazioniToCommissioniService
		    .getSoftwareComuneFromIdDettaglioComunicazione(idDettaglioComunicazione);
	    if (softwareComuneFromIdDettaglioList.isEmpty() || softwareComuneFromIdDettaglioList.size() > 1) {
		throw new InvalidConfigurationException(
			"Ops! sembra che il tuo configuratore non abbia fatto il suo lavoro oppure un bel BUG da schiacciare. Allontanarsi immediatamente dalla postazione...");
	    }
	    ISoftwareComuneData softwareComuneFromIdDettaglio = softwareComuneFromIdDettaglioList.get(0);
	    ORMHelper.setSoftware(softwareComuneFromIdDettaglio.getSoftware());
	    String codiceComune = softwareComuneFromIdDettaglio.getCodiceComune();
	    ProtocollazioneCommand cmd = populateProtocollazioneCommand(dettaglio, configurazione, softwareComuneFromIdDettaglio);
	    DatiProtocolloResponseType datiProtocollo = protocollazioneService.protocolla(ProtocolloSourceEnum.PROT_IST_MOV_AUT_BO, cmd,
		    ORMHelper.getSoftware(), codiceComune);
	    if (datiProtocollo.getErrore() != null && StringUtils.isNotBlank(datiProtocollo.getErrore().getDescrizione())) {
		throw new FunzioneBusinessRemotaException(datiProtocollo.getErrore().getDescrizione());
	    }
	    // SALVA I DATI NELLA TABELLA MASSIVE_DETTAGLIO
	    comunicazioniMassiveDettaglioDAO.aggiornaRiferimentiProtocollo(idDettaglioComunicazione, datiProtocollo.getNumeroProtocollo(),
		    dateFormatService.getDateDDMMYYYY(datiProtocollo.getDataProtocollo()), datiProtocollo.getIdProtocollo());
	    // LANCIA L'EVENTO EventoComunicazioneProtocollata
	    publisher.publish(new EventoComunicazioneProtocollata(ContestoComunicazioneEnum.COMMISSIONI, idDettaglioComunicazione));
	} catch (Exception e) {
	    String messaggioErrore = "Si è verificato un errore nella protocollazione " + e.getMessage();
	    log.error(messaggioErrore, e);
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
    }

    private ProtocollazioneCommand populateProtocollazioneCommand(MassiveDettaglio dettaglio, ConfigurazioneComunicazioniCommissioni configurazione,
	    ISoftwareComuneData softwareComuneFromIdDettaglio) {

	log.debug("populateProtocollazioneCommand# Inizio a popolare l'oggetto ProtocollazioneCommand....");
	ProtocollazioneCommand protocollazioneCommand = new ProtocollazioneCommand();
	protocollazioneCommand.setToken(ORMHelper.getToken());
	ParametriProtocollazione parametriProtocollazione = configurazione.getParametriProtocollazione();
	log.debug("populateProtocollazioneCommand# Popolo classifica...");
	String classifica = getParametro("classifica", parametriProtocollazione.getParametriPerEnte(), softwareComuneFromIdDettaglio);
	protocollazioneCommand.setClassifica(classifica);
	log.debug("populateProtocollazioneCommand# Popolo tipo documento...");
	String tipodocumento = getParametro("tipodocumento", parametriProtocollazione.getParametriPerEnte(), softwareComuneFromIdDettaglio);
	protocollazioneCommand.setTipoDocumento(tipodocumento);
	log.debug("populateProtocollazioneCommand# Popolo l'oggetto...");
	String oggetto = "";
	oggetto = findProtocolloOggetto(configurazione);
	protocollazioneCommand.setOggetto(oggetto);
	log.debug("populateProtocollazioneCommand# Popolo il destinatario (richiedente e/o titolare legale)");
	List<ProtocolloSoggettoCommand> protocolloSoggettoCommands = new ArrayList<ProtocolloSoggettoCommand>();
	if (dettaglio.getDestinatari().getAmministrazioni() != null) {
	    protocolloSoggettoCommands.add(ProtocolloSoggettoCommand.fromAmministrazione(dettaglio.getDestinatari().getAmministrazioni(), null));
	} else if (dettaglio.getDestinatari().getAnagrafe() != null) {
	    protocolloSoggettoCommands.add(ProtocolloSoggettoCommand.fromAltroSoggetto(dettaglio.getDestinatari().getAnagrafe(), null));
	}
	protocollazioneCommand.setDestinataris(protocolloSoggettoCommands);
	log.debug("populateProtocollazioneCommand# Popolo il flusso.....");
	protocollazioneCommand.setFlusso(WebConstants.FLUSSO_PARTENZA);
	log.debug("populateProtocollazioneCommand# Setto la protocollazione automatica....");
	protocollazioneCommand.setInserimentoAutomatico(true);
	log.debug("populateProtocollazioneCommand# Setto mittente....");
	// L'amministrazione è presa dal movimento, permette di poterla cambaire dopo aver fatto la prima
	// comunicazione in caso di errore ed elaborare nuovamente.
	Integer codiceAmministrazione = getParametro("codiceAmministrazione", parametriProtocollazione.getParametriPerEnte(),
		softwareComuneFromIdDettaglio);
	protocollazioneCommand
		.setMittente(ProtocolloSoggettoCommand.fromAmministrazione(amministrazioniService.findById(new PkId(codiceAmministrazione)), null));
	log.debug("populateProtocollazioneCommand# Setto la proveneienza....");
	protocollazioneCommand.setProvenienza(ProtocollazioneCommand.PROVENIENZA_COMUNICAZIONI_MASSIVE);
	// viene inviato al protocollo il documento salvato nella graduatoria
	List<MassiveDAllegati> massiveDAllegatiByIdDettaglio = comunicazioniMassiveDettaglioDAO
		.getMassiveDAllegatiByIdDettaglio(dettaglio.getId().getCodice());
	if (!massiveDAllegatiByIdDettaglio.isEmpty()) {
	    for (MassiveDAllegati massiveDAllegati : massiveDAllegatiByIdDettaglio) {
		AllegatoType at = new AllegatoType();
		Oggetti ogg = oggettiService.findByIdLazy(new PkId(massiveDAllegati.getCodiceOggetto()));
		at.setCod(String.valueOf(massiveDAllegati.getCodiceOggetto()));
		at.setDescrizione(ogg.getNomefile());
		at.setInviaTramitePec(Boolean.FALSE);
		protocollazioneCommand.getAllegatiGenerici().add(at);
	    }
	}
	return protocollazioneCommand;
    }

    @SuppressWarnings("unchecked")
    private <T> T getParametro(String parametro, List<ParametriProtocolloPerEnte> parametriPerEnte,
	    ISoftwareComuneData softwareComuneFromIdDettaglio) {

	for (ParametriProtocolloPerEnte ppe : parametriPerEnte) {
	    if (softwareComuneFromIdDettaglio.getCodiceComune() != null
		    && softwareComuneFromIdDettaglio.getCodiceComune().equalsIgnoreCase(ppe.getCodiceComune())) {
		if (parametro.equalsIgnoreCase("classifica")) {
		    return (T) ppe.getClassifica();
		} else if (parametro.equalsIgnoreCase("tipodocumento")) {
		    return (T) ppe.getTipodocumento();
		} else if (parametro.equalsIgnoreCase("codiceAmministrazione")) {
		    return (T) ppe.getCodiceAmministrazione();
		}
	    }
	}
	for (ParametriProtocolloPerEnte ppe : parametriPerEnte) {
	    if (StringUtils.isBlank(ppe.getCodiceComune())) {
		if (parametro.equalsIgnoreCase("classifica")) {
		    return (T) ppe.getClassifica();
		} else if (parametro.equalsIgnoreCase("tipodocumento")) {
		    return (T) ppe.getTipodocumento();
		} else if (parametro.equalsIgnoreCase("codiceAmministrazione")) {
		    return (T) ppe.getCodiceAmministrazione();
		}
	    }
	}
	throw new IllegalArgumentException("il parametro " + parametro + " non è corretto");
    }

    private String findProtocolloOggetto(ConfigurazioneComunicazioniCommissioni configurazione) {

	Mailtipo oggettoMovimento = mailtipoService.findById(new PkId(configurazione.getParametriProtocollazione().getCodiceMailtipo()));
	if (oggettoMovimento == null) {
	    throw new IllegalArgumentException(
		    "Mail tipo con id " + configurazione.getParametriProtocollazione().getCodiceMailtipo() + " non trovata");
	}
	return oggettoMovimento.getOggetto();
    }
}
