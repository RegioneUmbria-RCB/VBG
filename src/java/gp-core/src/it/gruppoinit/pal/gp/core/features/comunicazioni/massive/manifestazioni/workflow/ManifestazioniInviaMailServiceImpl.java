package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.workflow;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ISostituzioneSegnapostoManifestazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.OggettoComunicazioneManifestazioni;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Service
public class ManifestazioniInviaMailServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniManifestazioni> {

    private static final Logger log = LoggerFactory.getLogger(ManifestazioniInviaMailServiceImpl.class);
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private IEventPublisher publisher;
    @Autowired
    private ISostituzioneSegnapostoManifestazioniService sostituzioneSegnapostoManifestazioniService;

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniManifestazioni configurazione) {

	MassiveDettaglio dettaglio = this.comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	// 1 RECUPERA LA MAIL dell'anagrafica
	String mail = dettaglio.getDestinatari().getMailDestinatario();
	if (mail == null) {
	    String messaggioErrore = "Attenzione!! il campo mail non è popolato";
	    this.comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
	// 2 RICHIAMA IL SERVIZIO PER SOSTITUIRE I VALORI DELLA MAIL
	// 4 RECUPERA GLI ALLEGATI DALLE TABELLE MASSIVE_D_ALLEGATI
	List<MassiveDAllegati> massiveDAllegatiByIdDettaglio = this.comunicazioniMassiveDettaglioDAO
		.getMassiveDAllegatiByIdDettaglio(dettaglio.getId().getCodice());
	MailMessageType messaggio = this.populateMessage(mail, massiveDAllegatiByIdDettaglio, configurazione, dettaglio);
	// COMPONE IL MESSAGGIO, AGGIUNGENDO GLI ALLEGATI
	// INVOCA IL MAILSERVICE
	try {
	    String esito = this.mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(),
		    configurazione.getConfigurazioneMail().getSenderAccount(), ORMHelper.getToken(), messaggio);
	    if (esito.equalsIgnoreCase("ok")) {
		// salva la mail inviata nelle nuove tabelle
		this.comunicazioniMassiveDettaglioDAO.salvaMailMessage(messaggio, dettaglio,
			configurazione.getConfigurazioneMail().getSenderAccount());
		// LANCIA L'EVENTO EventoComunicazioneInviata
		this.publisher.publish(new EventoComunicazioneInviata(ContestoComunicazioneEnum.MANIFESTAZIONI, dettaglio.getId().getCodice()));
	    }
	} catch (Exception e) {
	    String messaggioErrore = "Errore nell'invio della mail: " + e.getMessage();
	    log.error(messaggioErrore, e);
	    this.comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
    }

    private MailMessageType populateMessage(String mail, List<MassiveDAllegati> massiveDAllegatiByIdDettaglio,
	    ConfigurazioneComunicazioniManifestazioni configurazione, MassiveDettaglio dettaglio) {

	// 3 RECUPERA L'ACCOUNT
	MailConfig senderAccount = this.mailConfigService.findById(new PkId(configurazione.getConfigurazioneMail().getSenderAccount()));
	MailMessageType messaggio = new MailMessageType();
	messaggio.setDestinatari(mail);
	OggettoComunicazioneManifestazioni o = sostituzioneSegnapostoManifestazioniService
		.effettuaSostituzioniByMassiveDettaglio(configurazione.getConfigurazioneMail().getIdMailTipo(), dettaglio);
	messaggio.setOggetto(o.getOggetto());
	messaggio.setCorpoMail(o.getCorpo());
	messaggio.setInviaComeHtml(true);
	messaggio.setMittente(senderAccount.getLoginname());
	messaggio.setMessageID(
		"MASSIVEDETTAGLIO_" + dettaglio.getId().getIdcomune() + "_" + dettaglio.getId().getCodice() + "_" + UUID.randomUUID().toString());
	if (massiveDAllegatiByIdDettaglio != null && !massiveDAllegatiByIdDettaglio.isEmpty()) {
	    AttachmentsType attachmentsType = new AttachmentsType();
	    for (MassiveDAllegati massiveDAllegati : massiveDAllegatiByIdDettaglio) {
		AttachmentType att = new AttachmentType();
		att.setId(massiveDAllegati.getCodiceOggetto().toString());
		Oggetti ogg = this.oggettiService.findById(new PkId(massiveDAllegati.getCodiceOggetto()));
		att.setDescrizione(ogg.getNomefile());
		att.setFileName(ogg.getNomefile());
		String cType = this.contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
		att.setMimeType(cType);
		att.setBinaryData(Utilities.bytesToDataHandler(ogg.getOggetto()));
		attachmentsType.getAttachment().add(att);
	    }
	    messaggio.setAttachments(attachmentsType);
	}
	return messaggio;
    }
}
