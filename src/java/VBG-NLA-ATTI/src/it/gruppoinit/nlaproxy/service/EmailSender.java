package it.gruppoinit.nlaproxy.service;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailSender {

    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);
    private MailSender mailSender;
    private SimpleMailMessage templateMessage;
    private String cc;
    private String useAuth;

    public String send(List<String> msgs) {

	log.debug("Invio email esito processamento...");
	String result = "";
	String text = templateMessage.getText();
	SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
	String[] cc = this.getCCSplitted();
	if (cc != null) {
	    msg.setCc(cc);
	}
	msg.setText(text + " " + getMsgs(msgs));
	if ("true".equals(this.useAuth)) {
	    Properties javaMailProperties = new Properties();
	    javaMailProperties.put("mail.smtp.auth", "true");
	    ((JavaMailSenderImpl) mailSender).setJavaMailProperties(javaMailProperties);
	}
	try {
	    this.mailSender.send(msg);
	    log.debug("Invio email esito processamento...done!");
	    result = "Invio mail eseguito correttamente";
	} catch (MailException ex) {
	    log.error("Errore durante l'invio mail: {}", ex.getMessage());
	    result = "Errore durante l'invio mail: " + ex.getMessage();
	}
	return result;
    }

    public void setMailSender(MailSender mailSender) {

	this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {

	this.templateMessage = templateMessage;
    }

    public void setCc(String cc) {

	this.cc = cc;
    }

    public void setUseAuth(String useAuth) {

	this.useAuth = useAuth;
    }

    private String[] getCCSplitted() {

	String[] cc = null;
	if (StringUtils.isNotBlank(this.cc)) {
	    cc = this.cc.split(";");
	}
	return cc;
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
