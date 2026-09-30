package it.gruppoinit.nlapec.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailSender {

    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);
    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void send(List<String> msgs) {

	log.debug("Invio email");
	String text = templateMessage.getText();
	SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
	msg.setText(text + " " + getMsgs(msgs));
	try {
	    this.mailSender.send(msg);
	} catch (MailException ex) {
	    log.error("Errore nel metodo send(): {}", ex.getMessage());
	}
    }

    public void setMailSender(MailSender mailSender) {

	this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {

	this.templateMessage = templateMessage;
    }

    private String getMsgs(List<String> msgs) {

	StringBuffer msgBuff = new StringBuffer();
	if (msgs != null) {
	    for (String msg : msgs) {
		msgBuff.append("\n").append(msg).append("\n");
	    }
	}
	return msgBuff.toString();
    }
}
