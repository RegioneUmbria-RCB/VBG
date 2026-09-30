package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MercatiMassiveD;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniMassiveGenDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.IstanzeGroupEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi.EventoAppIoStatoCoda;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi.EventoInviaAppIo;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.ana.SegnapostoAnaBuilder;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.aut.SegnapostoAutBuilder;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Service
public class GenInviaMailServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenInviaMailServiceImpl.class);
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private MailtipoService mailtipoService;
    private MailServiceWSClient mailServiceWSClient;
    private OggettiService oggettiService;
    private ContenttypesService contenttypesService;
    private MailConfigService mailConfigService;
    private IEventPublisher publisher;
    private IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO;
    @Autowired
    private IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO;
    @Autowired
    private IstanzeService istanzeService;

    @Autowired
    public GenInviaMailServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, MailtipoService mailtipoService,
	    MailServiceWSClient mailServiceWSClient, OggettiService oggettiService, ContenttypesService contenttypesService,
	    MailConfigService mailConfigService, IEventPublisher publisher, IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.mailtipoService = mailtipoService;
	this.mailServiceWSClient = mailServiceWSClient;
	this.oggettiService = oggettiService;
	this.contenttypesService = contenttypesService;
	this.mailConfigService = mailConfigService;
	this.publisher = publisher;
	this.comunicazioniMassiveGenDAO = comunicazioniMassiveGenDAO;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	boolean isInvioAppIo = false;
	boolean isInvioMail = false;
	MassiveDettaglio dettaglio = null;
	Mailtipo oggettoAndBodyReplaced = null;
	try {
	    //TUTTO QUESTO PEZZO LO FACCIAMO BLOCCANTE, PERCHE' DETERMINA SE SI INVIA MAIL, APPIO O TUTTE E DUE
	    dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	    isInvioAppIo = dettaglio.getMassiveTestata().getParametro("APPIO_SERVIZIO") != null;
	    isInvioMail = dettaglio.getMassiveTestata().getParametro("GESTIONE_SCELTA_MAIL_ANAGRAFE") != null;
	    if (!isInvioMail && !isInvioAppIo) {
		try {
		    EventoComunicazioneInviata eventoInviata = new EventoComunicazioneInviata(configurazione.getContesto(),
			    dettaglio.getId().getCodice());
		    eventoInviata.setWarnings(configurazione.getWarnings());
		    publisher.publishThrowOnFailure(eventoInviata);
		    return;
		} catch (Exception e) {
		    log.error("Errore durante invio comunicazione", e);
		    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, e + "");
		    return;
		}
	    }
	    if (!comunicazioniMassiveDettaglioDAO.findDettagliMailInviate(idDettaglioComunicazione).isEmpty()) {
		log.warn("Mail già inviata per la Comunicazione {}", idDettaglioComunicazione);
		isInvioMail = false; //useremo la variabile in modo da skippare l'invio dell'emailo
	    }
	    Mailtipo oggettoAndBody;
	    if (dettaglio.getMassiveTestata().getFkidMailtipo() != null) {
		oggettoAndBody = dettaglio.getMassiveTestata().getFkidMailtipo();
	    } else {
		oggettoAndBody = new Mailtipo();
		oggettoAndBody.setOggetto(dettaglio.getMassiveTestata().getParametro("OGGETTOMAIL_NAME"));
		oggettoAndBody.setCorpo(dettaglio.getMassiveTestata().getParametro("BODYMAIL_NAME"));
	    }
	    SegnapostoAnaBuilder anaBuilder = new SegnapostoAnaBuilder();
	    oggettoAndBody.setOggetto(anaBuilder.sostituisci(oggettoAndBody.getOggetto(), dettaglio.getDestinatari().getAnagrafe()));
	    oggettoAndBody.setCorpo(anaBuilder.sostituisci(oggettoAndBody.getCorpo(), dettaglio.getDestinatari().getAnagrafe()));
	    if (configurazione.getContesto() == ContestoComunicazioneEnum.ISTANZE && IstanzeGroupEnum.ISTANZE.name()
		    .equals(comunicazioniMassiveGenDAO.getGroupTypeForIstanze(dettaglio.getMassiveTestata().getId().getCodice()))) {
		Istanze istanza = comunicazioniMassiveGenDAO.getIstanzaFromDettaglio(idDettaglioComunicazione);
		if (istanza != null) {
		    oggettoAndBodyReplaced = mailtipoService.replaceOggettoCorpo(oggettoAndBody, istanza, null);
		} else {
		    oggettoAndBodyReplaced = oggettoAndBody;
		}
	    } else if (configurazione.getContesto() == ContestoComunicazioneEnum.MERCATI && dettaglio.getMercatiMassiveDs() != null
		    && dettaglio.getMercatiMassiveDs().size() == 1) {
		oggettoAndBodyReplaced = oggettoAndBody;
		for (MercatiMassiveD massiveD : dettaglio.getMercatiMassiveDs()) {
		    Autorizzazioni autorizzazione = massiveD.getAutorizzazione();
		    if (autorizzazione.getIstanza() != null) {
			SegnapostoAutBuilder segnapostoAutBuilder = new SegnapostoAutBuilder();
			oggettoAndBodyReplaced.setOggetto(segnapostoAutBuilder.sostituisci(oggettoAndBodyReplaced.getOggetto(), autorizzazione));
			oggettoAndBodyReplaced.setCorpo(segnapostoAutBuilder.sostituisci(oggettoAndBodyReplaced.getCorpo(), autorizzazione));
		    }
		    break;
		}
	    } else {
		oggettoAndBodyReplaced = oggettoAndBody;
	    }
	} catch (Exception e) {
	    log.error("Errore durante invio comunicazione", e);
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, e + "");
	    return;
	}
	if (isInvioMail) {
	    try {
		inviaMail(idDettaglioComunicazione, configurazione, dettaglio, oggettoAndBodyReplaced);
	    } catch (Exception e) {
		if (configurazione.getWarnings() == null) {
		    configurazione.setWarnings(new ArrayList<String>());
		}
		Throwable causa = e;
		while (causa.getCause() != null) {
		    causa = causa.getCause();
		}
		String messaggio = causa.getMessage();
		configurazione.getWarnings().add("Errore durante invio mail: " + messaggio);
	    }
	}
	try {
	    if (isInvioAppIo) {
		Anagrafe an = dettaglio.getDestinatari().getAnagrafe();
		boolean isCf = false;
		if (an != null && StringUtils.isNotEmpty(an.getCodicefiscale()) && an.getCodicefiscale().length() == 16) {
		    isCf = true;
		} else {
		    isCf = false;
		}
		if (appIoCodaMassiveDDAO.findByIdDettaglioMassiveD(idDettaglioComunicazione).isEmpty() && isCf) {
		    EventoInviaAppIo eventoInviaAppIo = new EventoInviaAppIo(configurazione.getContesto(), dettaglio.getId().getCodice());
		    eventoInviaAppIo.setWarnings(configurazione.getWarnings());
		    publisher.publishThrowOnFailure(eventoInviaAppIo);
		    return;
		} else if (!appIoCodaMassiveDDAO.findByIdDettaglioMassiveD(idDettaglioComunicazione).isEmpty() && isCf) {
		    EventoAppIoStatoCoda eventoAppIoStatoCoda = new EventoAppIoStatoCoda(configurazione.getContesto(), idDettaglioComunicazione);
		    eventoAppIoStatoCoda.setWarnings(configurazione.getWarnings());
		    publisher.publishThrowOnFailure(eventoAppIoStatoCoda);
		    return;
		} else {
		    //Da rivedere questo blocco, non andrebbe gestito qui
		    if (configurazione.getWarnings() != null && !configurazione.getWarnings().isEmpty()) {
			configurazione.getWarnings()
				.add("Errore durante invio appio: Non puoi inviare questa comunicazione a un soggetto che non è una persona fisica");
		    } else {
			comunicazioniMassiveDettaglioDAO.impostaStatoConCommit(idDettaglioComunicazione, "CONCLUSA_CON_APPIO_ERRORE");
			comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione,
				"Errore: Non puoi inviare questa comunicazione a un soggetto che non è una persona fisica");
			return;
		    }
		}
	    }
	    EventoComunicazioneInviata eventoInviata = new EventoComunicazioneInviata(configurazione.getContesto(), dettaglio.getId().getCodice());
	    eventoInviata.setWarnings(configurazione.getWarnings());
	    publisher.publishThrowOnFailure(eventoInviata);
	} catch (Exception e) {
	    log.error("Errore durante invio comunicazione", e);
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, e + "");
	}
    }

    public void inviaMail(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione, MassiveDettaglio dettaglio,
	    Mailtipo oggettoAndBodyReplaced) {

	// 1 RECUPERA LA MAIL dell'anagrafica
	String mail = dettaglio.getDestinatari().getMailDestinatario();
	// 2 RICHIAMA IL SERVIZIO PER SOSTITUIRE I VALORI DELLA MAIL
	// 4 RECUPERA GLI ALLEGATI DALLE TABELLE MASSIVE_D_ALLEGATI
	List<MassiveDAllegati> massiveDAllegatiByIdDettaglio = comunicazioniMassiveDettaglioDAO
		.getMassiveDAllegatiByIdDettaglio(dettaglio.getId().getCodice());
	MailMessageType messaggio = populateMessage(mail, massiveDAllegatiByIdDettaglio, configurazione, dettaglio, oggettoAndBodyReplaced);
	// COMPONE IL MESSAGGIO, AGGIUNGENDO GLI ALLEGATI
	// INVOCA IL MAILSERVICE
	try {
	    log.debug("Prima di inviare la mail per la Comunicazione {}", idDettaglioComunicazione);
	    String esito = mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(),
		    dettaglio.getMassiveTestata().getSenderAccount().getId().getCodice(), ORMHelper.getToken(), messaggio);
	    log.debug("Mail per la Comunicazione {} inviata, esito = {}", idDettaglioComunicazione, esito);
	    if (esito.equalsIgnoreCase("ok")) {
		// salva la mail inviata nelle nuove tabelle
		comunicazioniMassiveDettaglioDAO.salvaMailMessage(messaggio, dettaglio,
			dettaglio.getMassiveTestata().getSenderAccount().getId().getCodice());
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private MailMessageType populateMessage(String mail, List<MassiveDAllegati> massiveDAllegatiByIdDettaglio,
	    ConfigurazioniComunicazioneGen configurazione, MassiveDettaglio dettaglio, Mailtipo oggettoAndBodyReplaced) {

	// 3 RECUPERA L'ACCOUNT
	MailConfig senderAccount = dettaglio.getMassiveTestata().getSenderAccount();
	MailMessageType messaggio = new MailMessageType();
	messaggio.setDestinatari(mail);
	String oggetto = oggettoAndBodyReplaced.getOggetto();
	String corpo = oggettoAndBodyReplaced.getCorpo();
	messaggio.setOggetto(oggetto);
	messaggio.setCorpoMail(corpo);
	messaggio.setInviaComeHtml(true);
	messaggio.setMittente(senderAccount.getLoginname());
	messaggio.setMessageID(
		"MASSIVEDETTAGLIO_" + dettaglio.getId().getIdcomune() + "_" + dettaglio.getId().getCodice() + "_" + UUID.randomUUID().toString());
	if (!massiveDAllegatiByIdDettaglio.isEmpty()) {
	    AttachmentsType attachmentsType = new AttachmentsType();
	    for (MassiveDAllegati massiveDAllegati : massiveDAllegatiByIdDettaglio) {
		AttachmentType att = new AttachmentType();
		att.setId(massiveDAllegati.getCodiceOggetto().toString());
		Oggetti ogg = oggettiService.findById(new PkId(massiveDAllegati.getCodiceOggetto()));
		att.setDescrizione(ogg.getNomefile());
		att.setFileName(ogg.getNomefile());
		String cType = contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
		att.setMimeType(cType);
		att.setBinaryData(Utilities.bytesToDataHandler(ogg.getOggetto()));
		attachmentsType.getAttachment().add(att);
	    }
	    messaggio.setAttachments(attachmentsType);
	}
	return messaggio;
    }
}
