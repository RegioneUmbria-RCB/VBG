package it.gruppoinit.nlapec.service;

import it.gruppoinit.nlapec.schema.mailservice.MailMessageType;
import it.gruppoinit.nlapec.service.helper.PopulateGenericPec;
import it.gruppoinit.nlapec.service.helper.PopulateMailDPR160Art5Comma5;
import it.gruppoinit.nlapec.service.mailservice.MailServiceWSClient;
import it.gruppoinit.sigepro.schemas.messages.mailtipo.MailtipoResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PECSender {

    private static final Logger log = LoggerFactory.getLogger(PECSender.class);
    private MailServiceWSClient mailServiceWSClient;

    public void sendPECMessage(InserimentoPraticaNLARequest request, MailtipoResponse mailtipo, boolean isDpr160Art5Comma5, String token,
	    String mailSenderUrl) {

	MailMessageType message = null;
	try {
	    if (isDpr160Art5Comma5) {
		PopulateMailDPR160Art5Comma5 pecSenderHelper = new PopulateMailDPR160Art5Comma5(request, mailtipo);
		message = pecSenderHelper.populateMail();
	    } else {
		PopulateGenericPec g = new PopulateGenericPec(request, mailtipo);
		message = g.populateMail();
	    }
	} catch (Exception e) {
	    log.error("sendPECMessage: Error during populateMail", e);
	    throw new RuntimeException("Errore durante la preparazione della mail: " + e.getMessage());
	}
	//FIXME recuperare il cod mov dall'xml di InserimentoPraticaNLARequest se non è presente il campo forse dovremmo settarlo su altri dati
	Integer codMov = null;
	//il mittente, l'host del server mail e le credenziali per accedere le inserisce il ws mailservice
	mailServiceWSClient.sendMail(mailSenderUrl, codMov, request.getSportelloMittente().getIdSportello(), token, message);
    }

    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }
}
