package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
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
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProtocollata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoProtocollazioneComunicazioneNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloSourceEnum;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.utils.IDateFormatService;
import it.gruppoinit.protocollo.schemas.messages.AllegatoType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

@Service
public class ManifestazioniProtocollaServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniManifestazioni> {

    private static final Logger log = LoggerFactory.getLogger(ManifestazioniProtocollaServiceImpl.class);
    private IComunicazioniManifestazioniService comunicazioniManifestazioniService;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IEventPublisher publisher;
    private ProtocollazioneService protocollazioneService;
    private IDateFormatService dateFormatService;
    private AmministrazioniService amministrazioniService;
    private OggettiService oggettiService;
    private MailtipoService mailtipoService;

    @Autowired
    public void setComunicazioniManifestazioniService(IComunicazioniManifestazioniService comunicazioniManifestazioniService) {

	this.comunicazioniManifestazioniService = comunicazioniManifestazioniService;
    }

    @Autowired
    public void setComunicazioniMassiveDettaglioDAO(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Autowired
    public void setPublisher(IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setDateFormatService(IDateFormatService dateFormatService) {

	this.dateFormatService = dateFormatService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniManifestazioni configurazione) {

	MassiveDettaglio dettaglio = this.comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	if (!configurazione.isRichiedeProtocollazione()) {
	    this.publisher
		    .publish(new EventoProtocollazioneComunicazioneNonNecessaria(ContestoComunicazioneEnum.MANIFESTAZIONI, idDettaglioComunicazione));
	    return;
	}
	try {
	    List<ISoftwareComuneData> softwareComuneFromIdDettaglioList = this.comunicazioniManifestazioniService
		    .getSoftwareComuneFromIdDettaglioComunicazione(idDettaglioComunicazione);
	    if (softwareComuneFromIdDettaglioList.isEmpty() || softwareComuneFromIdDettaglioList.size() > 1) {
		throw new InvalidConfigurationException(
			"Ops! sembra che il tuo configuratore non abbia fatto il suo lavoro oppure un bel BUG da schiacciare. Allontanarsi immediatamente dalla postazione...");
	    }
	    ISoftwareComuneData softwareComuneFromIdDettaglio = softwareComuneFromIdDettaglioList.get(0);
	    ORMHelper.setSoftware(softwareComuneFromIdDettaglio.getSoftware());
	    String codiceComune = softwareComuneFromIdDettaglio.getCodiceComune();
	    ProtocollazioneCommand cmd = this.populateProtocollazioneCommand(dettaglio, configurazione, softwareComuneFromIdDettaglio);
	    DatiProtocolloResponseType datiProtocollo = this.protocollazioneService.protocolla(ProtocolloSourceEnum.PROT_IST_MOV_AUT_BO, cmd,
		    ORMHelper.getSoftware(), codiceComune);
	    if (datiProtocollo.getErrore() != null && StringUtils.isNotBlank(datiProtocollo.getErrore().getDescrizione())) {
		throw new FunzioneBusinessRemotaException(datiProtocollo.getErrore().getDescrizione());
	    }
	    // SALVA I DATI NELLA TABELLA MASSIVE_DETTAGLIO
	    this.comunicazioniMassiveDettaglioDAO.aggiornaRiferimentiProtocollo(idDettaglioComunicazione, datiProtocollo.getNumeroProtocollo(),
		    this.dateFormatService.getDateDDMMYYYY(datiProtocollo.getDataProtocollo()), datiProtocollo.getIdProtocollo());
	    // LANCIA L'EVENTO EventoComunicazioneProtocollata
	    this.publisher.publish(new EventoComunicazioneProtocollata(ContestoComunicazioneEnum.MANIFESTAZIONI, idDettaglioComunicazione));
	} catch (Exception e) {
	    String messaggioErrore = "Si è verificato un errore nella protocollazione " + e.getMessage();
	    log.error(messaggioErrore, e);
	    this.comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
    }

    private ProtocollazioneCommand populateProtocollazioneCommand(MassiveDettaglio dettaglio,
	    ConfigurazioneComunicazioniManifestazioni configurazione, ISoftwareComuneData softwareComuneFromIdDettaglio) {

	log.debug("populateProtocollazioneCommand# Inizio a popolare l'oggetto ProtocollazioneCommand....");
	ProtocollazioneCommand protocollazioneCommand = new ProtocollazioneCommand();
	protocollazioneCommand.setForzaNonFascicolareInProtocollazioneXML(true); // NON DEVO FASCICOLARE ALTRIMENTI CREO UN FASCICOLO PER OGNI PROTOCOLLO
	protocollazioneCommand.setToken(ORMHelper.getToken());
	ParametriProtocollazione parametriProtocollazione = configurazione.getParametriProtocollazione();
	log.debug("populateProtocollazioneCommand# Popolo classifica...");
	String classifica = getParametro("classifica", parametriProtocollazione.getParametriPerEnte(), softwareComuneFromIdDettaglio);
	protocollazioneCommand.setClassifica(classifica);
	log.debug("populateProtocollazioneCommand# Popolo tipo documento...");
	String tipodocumento = getParametro("tipodocumento", parametriProtocollazione.getParametriPerEnte(), softwareComuneFromIdDettaglio);
	protocollazioneCommand.setTipoDocumento(tipodocumento);
	log.debug("populateProtocollazioneCommand# Popolo l'oggetto...");
	Anagrafe a = dettaglio.getDestinatari().getAnagrafe();
	String oggetto = oggettoProtocollo(configurazione, a);
	protocollazioneCommand.setOggetto(oggetto);
	log.debug("populateProtocollazioneCommand# Popolo il destinatario (richiedente e/o titolare legale)");
	List<ProtocolloSoggettoCommand> protocolloSoggettoCommands = new ArrayList<ProtocolloSoggettoCommand>();
	protocolloSoggettoCommands.add(ProtocolloSoggettoCommand.fromAltroSoggetto(a, null));
	protocollazioneCommand.setDestinataris(protocolloSoggettoCommands);
	log.debug("populateProtocollazioneCommand# Popolo il flusso.....");
	protocollazioneCommand.setFlusso(WebConstants.FLUSSO_PARTENZA);
	log.debug("populateProtocollazioneCommand# Setto la protocollazione automatica....");
	protocollazioneCommand.setInserimentoAutomatico(true);
	log.debug("populateProtocollazioneCommand# Setto mittente....");
	// L'amministrazione è presa dal movimento, permette di poterla cambiare dopo aver fatto la prima
	// comunicazione in caso di errore ed elaborare nuovamente.
	Integer codiceAmministrazione = getParametro("codiceAmministrazione", parametriProtocollazione.getParametriPerEnte(),
		softwareComuneFromIdDettaglio);
	protocollazioneCommand.setMittente(
		ProtocolloSoggettoCommand.fromAmministrazione(this.amministrazioniService.findById(new PkId(codiceAmministrazione)), null));
	log.debug("populateProtocollazioneCommand# Setto la provenienza....");
	protocollazioneCommand.setProvenienza(ProtocollazioneCommand.PROVENIENZA_COMUNICAZIONI_MASSIVE);
	// viene inviato al protocollo il documento salvato nella graduatoria
	List<MassiveDAllegati> massiveDAllegatiByIdDettaglio = comunicazioniMassiveDettaglioDAO
		.getMassiveDAllegatiByIdDettaglio(dettaglio.getId().getCodice());
	if (!massiveDAllegatiByIdDettaglio.isEmpty()) {
	    for (MassiveDAllegati massiveDAllegati : massiveDAllegatiByIdDettaglio) {
		AllegatoType at = new AllegatoType();
		Oggetti ogg = this.oggettiService.findByIdLazy(new PkId(massiveDAllegati.getCodiceOggetto()));
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

    private String oggettoProtocollo(ConfigurazioneComunicazioniManifestazioni configurazione, Anagrafe a) {

	String oggetto = StringUtils.defaultString(findProtocolloOggetto(configurazione));
	if (a != null) {
	    String richiedente = "";
	    if (a.getNominativo() != null) {
		richiedente = a.getNominativo() + " ";
	    }
	    if (a.getNome() != null) {
		richiedente = richiedente.concat(a.getNome());
	    }
	    oggetto = oggetto.replace("[1]", StringUtils.defaultIfEmpty(richiedente, "").trim().toUpperCase());
	    oggetto = oggetto.replace("[RIC_CF]", StringUtils.defaultString(a.getCodicefiscale()).trim().toUpperCase());
	}
	return oggetto;
    }

    private String findProtocolloOggetto(ConfigurazioneComunicazioniManifestazioni configurazione) {

	Mailtipo oggettoMovimento = mailtipoService.findById(new PkId(configurazione.getParametriProtocollazione().getCodiceMailtipo()));
	if (oggettoMovimento == null) {
	    throw new IllegalArgumentException(
		    "Mail tipo con id " + configurazione.getParametriProtocollazione().getCodiceMailtipo() + " non trovata");
	}
	return oggettoMovimento.getOggetto();
    }
}
