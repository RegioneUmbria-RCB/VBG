package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow;

import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
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
public class CommissioniInviaMailServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniCommissioni> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CommissioniInviaMailServiceImpl.class);
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private MailtipoService mailtipoService;
    private MailServiceWSClient mailServiceWSClient;
    private OggettiService oggettiService;
    private ContenttypesService contenttypesService;
    private MailConfigService mailConfigService;
    private IEventPublisher publisher;

    @Autowired
    public CommissioniInviaMailServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, MailtipoService mailtipoService,
	    MailServiceWSClient mailServiceWSClient, ContenttypesService contenttypesService, MailConfigService mailConfigService,
	    IEventPublisher publisher, OggettiService oggettiService) {

	super();
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.mailtipoService = mailtipoService;
	this.mailServiceWSClient = mailServiceWSClient;
	this.contenttypesService = contenttypesService;
	this.mailConfigService = mailConfigService;
	this.publisher = publisher;
	this.oggettiService = oggettiService;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniCommissioni configurazione) {

	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
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
	    String esito = mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), configurazione.getConfigurazioneMail().getSenderAccount(),
		    ORMHelper.getToken(), messaggio);
	    if (esito.equalsIgnoreCase("ok")) {
		// salva la mail inviata nelle nuove tabelle
		comunicazioniMassiveDettaglioDAO.salvaMailMessage(messaggio, dettaglio, configurazione.getConfigurazioneMail().getSenderAccount());
		// LANCIA L'EVENTO EventoComunicazioneInviata
		publisher.publish(new EventoComunicazioneInviata(ContestoComunicazioneEnum.COMMISSIONI, dettaglio.getId().getCodice()));
	    }
	} catch (Exception e) {
	    log.error("Errore nell'invio mail {}", e.getMessage(), e);
	}
    }

    private MailMessageType populateMessage(String mail, List<MassiveDAllegati> massiveDAllegatiByIdDettaglio,
	    ConfigurazioneComunicazioniCommissioni configurazione, MassiveDettaglio dettaglio) {

	// 3 RECUPERA L'ACCOUNT
	MailConfig senderAccount = mailConfigService.findById(new PkId(configurazione.getConfigurazioneMail().getSenderAccount()));
	Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getConfigurazioneMail().getIdMailTipo()));
	MailMessageType messaggio = new MailMessageType();
	messaggio.setDestinatari(mail);
	messaggio.setOggetto(mailtipo.getOggetto());
	messaggio.setCorpoMail(mailtipo.getCorpo());
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
