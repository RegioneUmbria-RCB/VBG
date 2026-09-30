package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow;

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
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Service
public class InviaMailServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniBollettazione> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(InviaMailServiceImpl.class);
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private MailtipoService mailtipoService;
    private MailServiceWSClient mailServiceWSClient;
    private OggettiService oggettiService;
    private ContenttypesService contenttypesService;
    private MailConfigService mailConfigService;
    private IEventPublisher publisher;

    @Autowired
    public InviaMailServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, MailtipoService mailtipoService,
	    MailServiceWSClient mailServiceWSClient, OggettiService oggettiService, ContenttypesService contenttypesService,
	    MailConfigService mailConfigService, IEventPublisher publisher) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.mailtipoService = mailtipoService;
	this.mailServiceWSClient = mailServiceWSClient;
	this.oggettiService = oggettiService;
	this.contenttypesService = contenttypesService;
	this.mailConfigService = mailConfigService;
	this.publisher = publisher;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniBollettazione configurazione) {

	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	if (!comunicazioniMassiveDettaglioDAO.findDettagliMailInviate(idDettaglioComunicazione).isEmpty()) {
	    log.warn("Mail già inviata per la Comunicazione {}", idDettaglioComunicazione);
	    publisher.publish(new EventoComunicazioneInviata(ContestoComunicazioneEnum.BOLLETTAZIONE, dettaglio.getId().getCodice()));
	    return;
	}
	// 1 RECUPERA LA MAIL dell'anagrafica
	String mail = dettaglio.getDestinatari().getMailDestinatario();
	// 2 RICHIAMA IL SERVIZIO PER SOSTITUIRE I VALORI DELLA MAIL
	// 4 RECUPERA GLI ALLEGATI DALLE TABELLE MASSIVE_D_ALLEGATI
	List<MassiveDAllegati> massiveDAllegatiByIdDettaglio = comunicazioniMassiveDettaglioDAO
		.getMassiveDAllegatiByIdDettaglio(dettaglio.getId().getCodice());
	MailMessageType messaggio = populateMessage(mail, massiveDAllegatiByIdDettaglio, configurazione, dettaglio);
	// COMPONE IL MESSAGGIO, AGGIUNGENDO GLI ALLEGATI
	// INVOCA IL MAILSERVICE
	try {
	    log.debug("Prima di inviare la mail per la Comunicazione {}", idDettaglioComunicazione);
	    String esito = mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), configurazione.getConfigurazioneMail().getSenderAccount(),
		    ORMHelper.getToken(), messaggio);
	    log.debug("Mail per la Comunicazione {} inviata, esito = {}", idDettaglioComunicazione, esito);
	    if (esito.equalsIgnoreCase("ok")) {
		// salva la mail inviata nelle nuove tabelle
		comunicazioniMassiveDettaglioDAO.salvaMailMessage(messaggio, dettaglio, configurazione.getConfigurazioneMail().getSenderAccount());
		// LANCIA L'EVENTO EventoComunicazioneInviata
		publisher.publish(new EventoComunicazioneInviata(ContestoComunicazioneEnum.BOLLETTAZIONE, dettaglio.getId().getCodice()));
	    }
	} catch (Exception e) {
	    String messaggioErrore = "Errore nell'invio mail " + e.getMessage();
	    log.error(messaggioErrore, e);
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
    }

    private MailMessageType populateMessage(String mail, List<MassiveDAllegati> massiveDAllegatiByIdDettaglio,
	    ConfigurazioneComunicazioniBollettazione configurazione, MassiveDettaglio dettaglio) {

	// 3 RECUPERA L'ACCOUNT
	MailConfig senderAccount = mailConfigService.findById(new PkId(configurazione.getConfigurazioneMail().getSenderAccount()));
	Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getConfigurazioneMail().getIdMailTipo()));
	MailMessageType messaggio = new MailMessageType();
	messaggio.setDestinatari(mail);
	Anagrafe a = dettaglio.getDestinatari().getAnagrafe();
	String oggetto = oggettoMail(mailtipo.getOggetto(), a);
	String corpo = oggettoMail(mailtipo.getCorpo(), a);
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

    private String oggettoMail(String oggetto, Anagrafe a) {

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
}
