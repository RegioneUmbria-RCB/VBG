package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
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
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniToBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProtocollata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoProtocollazioneComunicazioneNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtMittDestAutoResolverNotAutomatica;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloAttivoBean;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloSourceEnum;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.IDateFormatService;
import it.gruppoinit.protocollo.schemas.messages.AllegatoType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

@Service
public class ProtocollaServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniBollettazione> {

    private static final Logger log = LoggerFactory.getLogger(ProtocollaServiceImpl.class);
    @Autowired
    private ProtocollazioneService protocollazioneService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    @Autowired
    private IDateFormatService dateFormatService;
    @Autowired
    private IEventPublisher publisher;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private IComunicazioniToBollettazioneService comunicazioniToBollettazioneService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private TipisoggettopeopleService tipisoggettopeopleService;
    @Autowired
    private IstanzeDAO istanzeDAO;

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniBollettazione configurazione) {

	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	if (!configurazione.isRichiedeProtocollazione()) {
	    this.publisher
		    .publish(new EventoProtocollazioneComunicazioneNonNecessaria(ContestoComunicazioneEnum.BOLLETTAZIONE, idDettaglioComunicazione));
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
	    //1. Verifico i software e gli enti coinvolti nel dettaglio della comunicazione
	    List<ISoftwareComuneData> softwareComuneFromIdDettaglioList = comunicazioniToBollettazioneService
		    .getSoftwareComuneFromIdDettaglioComunicazione(idDettaglioComunicazione);
	    //2. Verifico che tutti siano riconducibili ad una unica configurazione di protocollazione altrimenti non è possibile
	    //   procedere
	    boolean configurazioneUnica = this.verificaConfigurazioneProtocolloEnti(softwareComuneFromIdDettaglioList);
	    if (!configurazioneUnica) {
		throw new InvalidConfigurationException(
			"Non è possibile procedere con la protocollazione in quanto sono coinvolti più enti con configurazioni di protocollazione diversificate");
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
	    publisher.publish(new EventoComunicazioneProtocollata(ContestoComunicazioneEnum.BOLLETTAZIONE, idDettaglioComunicazione));
	} catch (Exception e) {
	    String messaggioErrore = "Si è verificato un errore nella protocollazione " + e.getMessage();
	    log.error(messaggioErrore, e);
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new BusinessValidationException(messaggioErrore);
	}
    }

    private boolean verificaConfigurazioneProtocolloEnti(List<ISoftwareComuneData> softwareComuneFromIdDettaglioList) {

	if (softwareComuneFromIdDettaglioList == null || softwareComuneFromIdDettaglioList.isEmpty()) {
	    throw new RuntimeException("Non è possibile risalire all'ente e al modulo coinvolto nella riga di bollettazione");
	}
	if (softwareComuneFromIdDettaglioList.size() == 1) {
	    return true;
	}
	//1. Prendo le regole di protocollazione attive per i comuni/software di riferimento
	List<ProtocolloAttivoBean> protocolliAttivi = this.protocollazioneService.verificaProtocolloAttivo(softwareComuneFromIdDettaglioList);
	//2. Verifico che siano attivi trasversalmente ai singoli comuni altrimenti non potrei protocollare
	boolean protocollazioneConsentita = true;
	for (ProtocolloAttivoBean protocolloAttivoBean : protocolliAttivi) {
	    if (Boolean.FALSE.equals(protocolloAttivoBean.isAttivo()) || !"TUTTI".equalsIgnoreCase(protocolloAttivoBean.getEnte())) {
		protocollazioneConsentita = false;
		break;
	    }
	}
	return protocollazioneConsentita;
    }

    private ProtocollazioneCommand populateProtocollazioneCommand(MassiveDettaglio dettaglio, ConfigurazioneComunicazioniBollettazione configurazione,
	    ISoftwareComuneData softwareComuneFromIdDettaglio) {

	log.debug("populateProtocollazioneCommand# Inizio a popolare l'oggetto ProtocollazioneCommand....");
	ProtocollazioneCommand protocollazioneCommand = new ProtocollazioneCommand();
	protocollazioneCommand.setForzaNonFascicolareInProtocollazioneXML(true); // NON DEVO FASCICOLARE ALTRIMENTI CREO UN FASCICOLO PER OGNI PROTOCOLLO
	protocollazioneCommand.setToken(ORMHelper.getToken());
	ParametriProtocollazione parametriProtocollazione = configurazione.getParametriProtocollazione();
	List<ParametriProtocolloPerEnte> parametriPerEnte = parametriProtocollazione.getParametriPerEnte();
	log.debug("populateProtocollazioneCommand# Popolo classifica...");
	String classifica = getParametro("classifica", parametriPerEnte, softwareComuneFromIdDettaglio);
	protocollazioneCommand.setClassifica(classifica);
	log.debug("populateProtocollazioneCommand# Popolo tipo documento...");
	String tipodocumento = getParametro("tipodocumento", parametriPerEnte, softwareComuneFromIdDettaglio);
	protocollazioneCommand.setTipoDocumento(tipodocumento);
	log.debug("populateProtocollazioneCommand# Popolo l'oggetto...");
	gestAnagrafe(dettaglio, protocollazioneCommand);
	String oggetto = oggettoProtocollo(configurazione, dettaglio.getDestinatari().getAnagrafe());
	protocollazioneCommand.setOggetto(oggetto);
	log.debug("populateProtocollazioneCommand# Popolo il flusso.....");
	protocollazioneCommand.setFlusso(WebConstants.FLUSSO_PARTENZA);
	log.debug("populateProtocollazioneCommand# Setto la protocollazione automatica....");
	protocollazioneCommand.setInserimentoAutomatico(true);
	log.debug("populateProtocollazioneCommand# Setto mittente....");
	// L'amministrazione è presa dal movimento, permette di poterla cambiare dopo aver fatto la prima
	// comunicazione in caso di errore ed elaborare nuovamente.
	Integer codiceAmministrazione = getParametro("codiceAmministrazione", parametriProtocollazione.getParametriPerEnte(),
		softwareComuneFromIdDettaglio);
	protocollazioneCommand
		.setMittente(ProtocolloSoggettoCommand.fromAmministrazione(amministrazioniService.findById(new PkId(codiceAmministrazione)), null));
	log.debug("populateProtocollazioneCommand# Setto la provenienza....");
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
	//metadati
	if (parametriPerEnte.get(0).getMetadati() != null && !parametriPerEnte.get(0).getMetadati().isEmpty()) {
	    protocollazioneCommand.getMetadati().addAll(parametriPerEnte.get(0).getMetadati());
	}
	return protocollazioneCommand;
    }

    private void gestAnagrafe(MassiveDettaglio dettaglio, ProtocollazioneCommand protocollazioneCommand) {

	log.debug("populateProtocollazioneCommand# Popolo il destinatario (richiedente e/o titolare legale)");
	// se è lo stesso richiedente per l'istanza allora torna quello 
	// altrimenti errore
	// questo solo se la massiva è delle istanze negli altri casi ritorna come ora l'anagrafe 
	// della massiva
	List<Integer> codiciIstanzeDaDettaglioForIstanzeOneri = comunicazioniMassiveDettaglioDAO
		.getCodiciIstanzeDaDettaglioForIstanzeOneri(dettaglio.getId().getCodice());
	log.debug("gestAnagrafe# verifico se legato a istanza per massiva_dettaglio {}", dettaglio.getId().getCodice());
	List<ProtocolloSoggettoCommand> protocolloSoggettoCommands = new ArrayList<ProtocolloSoggettoCommand>();
	if (codiciIstanzeDaDettaglioForIstanzeOneri.isEmpty()) {
	    log.debug("gestAnagrafe# non è legato a istanza per massiva_dettaglio {} ritorno  dettaglio.getDestinatari().getAnagrafe()",
		    dettaglio.getId().getCodice());
	    //Determino se massiva legata a istanza se non legata torno come ora ==> dettaglio.getDestinatari().getAnagrafe()
	    protocolloSoggettoCommands.add(ProtocolloSoggettoCommand.fromAltroSoggetto(dettaglio.getDestinatari().getAnagrafe(), null));
	    protocollazioneCommand.setDestinataris(protocolloSoggettoCommands);
	    return;
	}
	// se legata a istanza allora se mi torna stesso soggetto prendo quello altrimenti errore	
	// per ogni istanza chiamare ProtMittDestAutoResolverNotAutomatica.resolve
	Set<Integer> codiciAnagrafeTrovati = new HashSet<Integer>();
	Set<String> mezziTrovati = new HashSet<String>();
	int mezziNulliPerIstanze = 0;
	for (Integer codiceIstanza : codiciIstanzeDaDettaglioForIstanzeOneri) {
	    Istanze i = istanzeDAO.findById(new PkId(codiceIstanza));
	    log.debug("gestAnagrafe# massiva_dettaglio {}, elaboro l'istanza {} ", dettaglio.getId().getCodice(), i);
	    IVerticalizzazioneProtocolloAttivoService vertProtoAttivo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService,
		    i.getComune().getCodicecomune());
	    List<ProtocolloSoggettoCommand> resolveMittDestAnagrafe = new ProtMittDestAutoResolverNotAutomatica(vertProtoAttivo, i,
		    tipisoggettopeopleService).resolveMittDestAnagrafe();
	    for (ProtocolloSoggettoCommand psc : resolveMittDestAnagrafe) {
		if (psc.getAnagrafe() != null && !codiciAnagrafeTrovati.contains(psc.getAnagrafe().getId().getCodice())) {
		    log.debug("gestAnagrafe# massiva_dettaglio {}, aggiungo anagrafe {}", dettaglio.getId().getCodice(),
			    psc.getAnagrafe().getId().getCodice());
		    protocolloSoggettoCommands.add(ProtocolloSoggettoCommand.fromAltroSoggetto(psc.getAnagrafe(), psc.getMezzo()));
		    codiciAnagrafeTrovati.add(psc.getAnagrafe().getId().getCodice());
		    if (psc.getMezzo() != null && StringUtils.isNotBlank(psc.getMezzo().getCodice())) {
			log.debug("gestAnagrafe# massiva_dettaglio {}, trovato Mezzo {}", dettaglio.getId().getCodice(), psc.getMezzo().getCodice());
			mezziTrovati.add(psc.getMezzo().getCodice());
		    } else {
			log.debug("gestAnagrafe# massiva_dettaglio {}, Mezzo nullo per istanza {}", dettaglio.getId().getCodice(), i);
			mezziNulliPerIstanze++;
		    }
		}
	    }
	}
	if (mezziTrovati.size() > 1) {
	    throw new InvalidConfigurationException(
		    "Errore nel recupero dei soggetti della protocollazione. La ricerca del mezzo di protocollazione ha tornato " +
						    mezziTrovati.size() + " righe e ne è prevista 1. Lista mezzi trovati " + mezziTrovati);
	}
	if (!mezziTrovati.isEmpty() && mezziNulliPerIstanze > 0) {
	    throw new InvalidConfigurationException(
		    "Errore nel recupero dei soggetti della protocollazione. La ricerca del mezzo di protocollazione ha tornato " +
						    mezziTrovati.size() + " righe e ne è prevista 1. Lista mezzi trovati " + mezziTrovati);
	}
	protocollazioneCommand.setDestinataris(protocolloSoggettoCommands);
    }

    private String oggettoProtocollo(ConfigurazioneComunicazioniBollettazione configurazione, Anagrafe a) {

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

    private String findProtocolloOggetto(ConfigurazioneComunicazioniBollettazione configurazione) {

	Mailtipo oggettoMovimento = mailtipoService.findById(new PkId(configurazione.getParametriProtocollazione().getCodiceMailtipo()));
	if (oggettoMovimento == null) {
	    throw new IllegalArgumentException(
		    "Mail tipo con id " + configurazione.getParametriProtocollazione().getCodiceMailtipo() + " non trovata");
	}
	return oggettoMovimento.getOggetto();
    }
}