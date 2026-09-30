package it.gruppoinit.pal.gp.backoffice.ws;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.definitions.movimentimail.MovimentiMail;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimentimail.AllegatoType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimentimail.MailRequestBase;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimentimail.MovimentiMailRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimentimail.MovimentiMailRequest2;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimentimail.MovimentiMailResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.movimentimail.MovimentiMailResponse2;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.messaggimail.IMessaggiMailService;
import it.gruppoinit.pal.gp.core.features.messaggimail.model.AllegatoMailDTO;
import it.gruppoinit.pal.gp.core.features.messaggimail.model.IAllegatoMail;
import it.gruppoinit.pal.gp.core.features.messaggimail.model.IMessaggiMail;
import it.gruppoinit.pal.gp.core.features.messaggimail.model.MessaggiMailDTO;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

@WebService(serviceName = "MovimentiMailService", portName = "MovimentiMailSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/movimentimail", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.movimentimail.MovimentiMail")
public class MovimentiMailWS extends BaseWS implements MovimentiMail {

    private static final Logger log = LoggerFactory.getLogger(MovimentiMailWS.class);
    private MovimentimailService movimentimailService;
    private MailConfigService mailConfigService;
    @Autowired
    private IMessaggiMailService messaggiMailService;
    @Autowired
    private OggettiService oggettiService;

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setMailConfigService(MailConfigService mailConfigService) {

	this.mailConfigService = mailConfigService;
    }

    public MovimentiMailResponse movimentiMail(MovimentiMailRequest movimentiMailRequest) {

	log.debug("movimentiMail: software={}, token={}, codicemovimento={}, message-id={}, mittente={}, destinatario={}, oggetto={}",
		new Object[] { movimentiMailRequest.getSoftware(), movimentiMailRequest.getToken(), movimentiMailRequest.getCodicemovimento(),
			movimentiMailRequest.getMessageId(), movimentiMailRequest.getMittente(), movimentiMailRequest.getDestinatario(),
			movimentiMailRequest.getOggetto() });
	MovimentiMailResponse movimentiMailResponse = new MovimentiMailResponse();
	setORMHelper(movimentiMailRequest.getSoftware(), movimentiMailRequest.getToken());
	Movimentimail movimentimail = populateMovimentiMail(movimentiMailRequest);
	OggettiBusinessRules rule = (OggettiBusinessRules) SigeproBusinessRules.getClassRules(OggettiBusinessRules.class);
	rule.setInsert(true);
	SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, rule);
	try {
	    if (StringUtils.isBlank(movimentiMailRequest.getCodicemovimento())) {
		//se codicemovimento è null allora il messaggio è un messaggio di controllo.
		//in questo caso utilizzo il messageId per recuperare il movimento ed inserire in movimentimail
		//i messaggi di controllo con ID_PADRE.
		if (StringUtils.isBlank(movimentiMailRequest.getMessageId())) {
		    throw new RuntimeException("Se codicemovimento è nullo Message-ID è obbligatorio.");
		} else {
		    List<Movimentimail> msgs = movimentimailService.findByMessageId(movimentiMailRequest.getMessageId());
		    if (msgs != null && !msgs.isEmpty()) {
			Movimentimail msgPadre = msgs.get(0);
			movimentimail.setMessageId(null);
			movimentimailService.insertChildMessage(msgPadre, movimentimail);
			movimentiMailResponse.setId(BigInteger.valueOf(movimentimail.getId().getCodice()));
		    }
		}
	    } else {
		if (StringUtils.isNotBlank(movimentiMailRequest.getMessageId())) {
		    List<Movimentimail> msgs = movimentimailService.findByMessageId(movimentiMailRequest.getMessageId());
		    if (msgs != null && !msgs.isEmpty()) {
			throw new RuntimeException("Esiste già un record in MOVIMENTIMAIL con messageId=" + movimentiMailRequest.getMessageId());
		    }
		}
		movimentimailService.insert(movimentimail);
		movimentiMailResponse.setId(BigInteger.valueOf(movimentimail.getId().getCodice()));
	    }
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error("movimentiMail: software={}, token={}, codicemovimento={}, message-id={}, mittente={}, destinatario={}, oggetto={}, ERRORE={}",
		    new Object[] { movimentiMailRequest.getSoftware(), movimentiMailRequest.getToken(), movimentiMailRequest.getCodicemovimento(),
			    movimentiMailRequest.getMessageId(), movimentiMailRequest.getMittente(), movimentiMailRequest.getDestinatario(),
			    movimentiMailRequest.getOggetto(), err });
	    throw new RuntimeException(err);
	} finally {
	    resetThreadLocalVars();
	}
	return movimentiMailResponse;
    }

    /**
     * L'id tornato potrebbe non essere quello di movimentimail, Il ritorno serve a Nla-PEC per smarcare come lette le
     * PEC
     */
    @Override
    public MovimentiMailResponse2 movimentiMail2(MovimentiMailRequest2 movimentiMailRequest2) {

	log.debug("movimentiMail2: software={}, token={}, idaccounti={}, codicemovimento={}, message-id={}, mittente={}, destinatario={}, oggetto={}",
		new Object[] { movimentiMailRequest2.getSoftware(), movimentiMailRequest2.getToken(), movimentiMailRequest2.getIdaccount(),
			movimentiMailRequest2.getCodicemovimento(), movimentiMailRequest2.getMessageId(), movimentiMailRequest2.getMittente(),
			movimentiMailRequest2.getDestinatario(), movimentiMailRequest2.getOggetto() });
	MovimentiMailResponse2 movimentiMailResponse2 = new MovimentiMailResponse2();
	setORMHelper(movimentiMailRequest2.getSoftware(), movimentiMailRequest2.getToken());
	Movimentimail movimentimail = populateMovimentiMail2(movimentiMailRequest2);
	OggettiBusinessRules rule = (OggettiBusinessRules) SigeproBusinessRules.getClassRules(OggettiBusinessRules.class);
	rule.setInsert(true);
	SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, rule);
	try {
	    if (StringUtils.isBlank(movimentiMailRequest2.getCodicemovimento())) {
		//se codicemovimento è null allora il messaggio è un messaggio di controllo.
		//in questo caso utilizzo il messageId per recuperare il movimento ed inserire in movimentimail
		//i messaggi di controllo con ID_PADRE.
		if (StringUtils.isBlank(movimentiMailRequest2.getMessageId())) {
		    throw new RuntimeException("Se codicemovimento è nullo Message-ID è obbligatorio.");
		} else {
		    boolean empty = true;
		    log.debug("Cerco il message id come messaggio dei movimenti");
		    List<Movimentimail> msgs = movimentimailService.findByMessageId(movimentiMailRequest2.getMessageId());
		    if (msgs != null && !msgs.isEmpty()) {
			empty = false;
			Movimentimail msgPadre = msgs.get(0);
			movimentimail.setMessageId(null);
			movimentimailService.insertChildMessage(msgPadre, movimentimail);
			movimentiMailResponse2.setId(BigInteger.valueOf(movimentimail.getId().getCodice()));
		    }
		    log.debug("Processo il messaggio {} cercando tra i messaggi delle comunicazioni massive {} ",
			    movimentiMailRequest2.getMessageId(), empty);
		    if (empty) {
			movimentiMailResponse2.setId(verificaMessaggiMassive(MailRequestBase.fromMovimentiMailRequest2(movimentiMailRequest2)));
		    }
		}
	    } else {
		if (StringUtils.isNotBlank(movimentiMailRequest2.getMessageId())) {
		    List<Movimentimail> msgs = movimentimailService.findByMessageId(movimentiMailRequest2.getMessageId());
		    if (msgs != null && !msgs.isEmpty()) {
			throw new RuntimeException("Esiste già un record in MOVIMENTIMAIL con messageId=" + movimentiMailRequest2.getMessageId());
		    }
		}
		
		movimentimail.setBozza(false);
		
		movimentimailService.insert(movimentimail);
		movimentiMailResponse2.setId(BigInteger.valueOf(movimentimail.getId().getCodice()));
	    }
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error(
		    "movimentiMail: software={}, token={}, idaccount={}, codicemovimento={}, message-id={}, mittente={}, destinatario={}, oggetto={}, ERRORE={}",
		    new Object[] { movimentiMailRequest2.getSoftware(), movimentiMailRequest2.getToken(), movimentiMailRequest2.getIdaccount(),
			    movimentiMailRequest2.getCodicemovimento(), movimentiMailRequest2.getMessageId(), movimentiMailRequest2.getMittente(),
			    movimentiMailRequest2.getDestinatario(), movimentiMailRequest2.getOggetto(), err });
	    throw new RuntimeException(err);
	} finally {
	    resetThreadLocalVars();
	}
	return movimentiMailResponse2;
    }

    private Movimentimail populateMovimentiMail(MovimentiMailRequest movimentiMailRequest) {

	Movimentimail movimentimail = new Movimentimail();
	movimentimail.setMessageId(movimentiMailRequest.getMessageId());
	try {
	    if (StringUtils.isNotBlank(movimentiMailRequest.getCodicemovimento())) {
		Integer codMov = Integer.valueOf(movimentiMailRequest.getCodicemovimento());
		movimentimail.getMovimento().getId().setCodice(codMov);
	    }
	} catch (NumberFormatException e) {
	    log.warn("populateMovimentiMail: codicemovimento deve essere un numero: {}", movimentiMailRequest.getCodicemovimento());
	}
	movimentimail.setCorpo(movimentiMailRequest.getCorpo());
	movimentimail.setDatainvio(new Date());
	movimentimail.setDestinatario(movimentiMailRequest.getDestinatario());
	movimentimail.setDestinatariobcc(movimentiMailRequest.getDestinatariobcc());
	movimentimail.setDestinatariocc(movimentiMailRequest.getDestinatariocc());
	movimentimail.setMittente(movimentiMailRequest.getMittente());
	movimentimail.setOggetto(movimentiMailRequest.getOggetto());
	List<AllegatoType> allegati = movimentiMailRequest.getAllegati();
	if (allegati != null) {
	    for (AllegatoType allegatoType : allegati) {
		Movimentimailallegati movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		if (StringUtils.isNotBlank(allegatoType.getDescrizione())) {
		    movimentimailallegati.setDocumento(allegatoType.getDescrizione());
		} else {
		    movimentimailallegati.setDocumento(allegatoType.getFileName());
		}
		Oggetti oggetto = new Oggetti();
		if (allegatoType.getId() != null) {
		    oggetto.getId().setCodice(allegatoType.getId().intValue());
		} else {
		    oggetto.setNomefile(allegatoType.getFileName());
		    oggetto.setOggetto(Utilities.dataHandlerToBytes(allegatoType.getBinaryData()));
		}
		movimentimailallegati.setOggetto(oggetto);
		movimentimail.getMovimentimailallegatis().add(movimentimailallegati);
	    }
	}
	return movimentimail;
    }

    private Movimentimail populateMovimentiMail2(MovimentiMailRequest2 movimentiMailRequest2) {

	Movimentimail movimentimail = new Movimentimail();
	movimentimail.setMessageId(movimentiMailRequest2.getMessageId());
	MailConfig mcfg = null;
	if (movimentiMailRequest2.getIdaccount() != null) {
	    mcfg = mailConfigService.findById(new PkId(new Integer(movimentiMailRequest2.getIdaccount().toString())));
	}
	movimentimail.setMailConfig(mcfg);
	try {
	    if (StringUtils.isNotBlank(movimentiMailRequest2.getCodicemovimento())) {
		Integer codMov = Integer.valueOf(movimentiMailRequest2.getCodicemovimento());
		movimentimail.getMovimento().getId().setCodice(codMov);
	    }
	} catch (NumberFormatException e) {
	    log.warn("populateMovimentiMail: codicemovimento deve essere un numero: {}", movimentiMailRequest2.getCodicemovimento());
	}
	movimentimail.setCorpo(movimentiMailRequest2.getCorpo());
	movimentimail.setDatainvio(new Date());
	movimentimail.setDestinatario(movimentiMailRequest2.getDestinatario());
	movimentimail.setDestinatariobcc(movimentiMailRequest2.getDestinatariobcc());
	movimentimail.setDestinatariocc(movimentiMailRequest2.getDestinatariocc());
	movimentimail.setMittente(movimentiMailRequest2.getMittente());
	movimentimail.setOggetto(movimentiMailRequest2.getOggetto());
	List<AllegatoType> allegati = movimentiMailRequest2.getAllegati();
	if (allegati != null) {
	    for (AllegatoType allegatoType : allegati) {
		Movimentimailallegati movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		if (StringUtils.isNotBlank(allegatoType.getDescrizione())) {
		    movimentimailallegati.setDocumento(allegatoType.getDescrizione());
		} else {
		    movimentimailallegati.setDocumento(allegatoType.getFileName());
		}
		Oggetti oggetto = new Oggetti();
		if (allegatoType.getId() != null) {
		    oggetto.getId().setCodice(allegatoType.getId().intValue());
		} else {
		    oggetto.setNomefile(allegatoType.getFileName());
		    oggetto.setOggetto(Utilities.dataHandlerToBytes(allegatoType.getBinaryData()));
		}
		movimentimailallegati.setOggetto(oggetto);
		movimentimail.getMovimentimailallegatis().add(movimentimailallegati);
	    }
	}
	return movimentimail;
    }

    private BigInteger verificaMessaggiMassive(MailRequestBase mail) {

	Integer idPadre = messaggiMailService.trovaPadreDelMessaggioConId(mail.getMessageId());
	if (idPadre == null) {
	    log.warn("Messaggio padre non trovato per id {}", mail.getMessageId());
	    return null;
	}
	IMessaggiMail ricevuta = new MessaggiMailDTO();
	if (mail.getIdaccount() != null) {
	    ricevuta.setAccountId(mail.getIdaccount().intValue());
	}
	ricevuta.setCorpo(mail.getCorpo());
	ricevuta.setDataInvio(new Date());
	ricevuta.setDestinatario(mail.getDestinatario());
	ricevuta.setDestinatariobcc(mail.getDestinatariobcc());
	ricevuta.setDestinatariocc(mail.getDestinatariocc());
	ricevuta.setMessageId(mail.getMessageId());
	ricevuta.setMittente(mail.getMittente());
	ricevuta.setOggetto(mail.getOggetto());
	List<AllegatoType> allegati = mail.getAllegati();
	for (AllegatoType allegatoType : allegati) {
	    IAllegatoMail a = new AllegatoMailDTO();
	    if (allegatoType.getId() != null) {
		a.setCodiceOggetto(allegatoType.getId().intValue());
	    } else {
		Oggetti o = new Oggetti();
		o.setNomefile(allegatoType.getFileName());
		o.setOggetto(Utilities.dataHandlerToBytes(allegatoType.getBinaryData()));
		oggettiService.insert(o);
		a.setCodiceOggetto(o.getId().getCodice());
	    }
	    ricevuta.getAllegati().add(a);
	}
	Integer ret = messaggiMailService.collegaRicevutaAMessaggioMail(ricevuta, idPadre);
	if (ret != null) {
	    return BigInteger.valueOf(ret);
	}
	return null;
    }
}
