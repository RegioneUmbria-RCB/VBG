package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneEmail;
import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.ManifAreePubbliche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.helper.EmailHelper;
import it.gruppoinit.pal.gp.core.helper.TIPO_MANIFESTAZIONE;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneEmailService;
import it.gruppoinit.pal.gp.core.service.FesteSagreService;
import it.gruppoinit.pal.gp.core.service.FiereMostreService;
import it.gruppoinit.pal.gp.core.service.MailService;
import it.gruppoinit.pal.gp.core.service.ManifAreePubblicheService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private ConfigurazioneEmailService configurazioneEmailService;
    @Autowired
    private ManifAreePubblicheService manifAreePubblicheService;
    @Autowired
    private FesteSagreService festeSagreService;
    @Autowired
    private FiereMostreService fiereMostreService;

    @Override
    public boolean sendEmail(String oggetto, String corpo) {

	log.debug("sendMail");
	ConfigurazioneEmail conf = configurazioneEmailService.findByInstallazione();
	if (conf != null && !conf.getMailInvioDisabilitato()) {
	    final String username = conf.getMailUser();
	    final String password = conf.getMailPassword();
	    Properties props = new Properties();
	    if (conf.isMailUseAutentication()) {
		props.put("mail.smtp.auth", "true");
	    } else {
		props.put("mail.smtp.auth", "false");
	    }
	    //props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", StringUtils.defaultIfEmpty(conf.getMailServer(), ""));
	    props.put("mail.smtp.port", StringUtils.defaultIfEmpty(conf.getMailPort(), ""));
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() {

		protected PasswordAuthentication getPasswordAuthentication() {

		    return new PasswordAuthentication(username, password);
		}
	    });
	    try {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(StringUtils.defaultIfEmpty(conf.getMailFrom(), "")));
		String destinatari = StringUtils.replace(conf.getMailTo(), ";", ",");
		InternetAddress[] to = InternetAddress.parse(destinatari);
		message.setRecipients(Message.RecipientType.TO, to);
		message.setSubject(oggetto);
		message.setText(corpo);
		Transport.send(message);
		log.debug("sendMail#done...");
		return true;
	    } catch (MessagingException e) {
		throw new RuntimeException(e);
	    }
	} else {
	    log.info("sedEmail#Sezione invio email non configurata o disabilitata ");
	    return false;
	}
    }

    @Override
    public EmailHelper pupolateEmail(Integer codiceManifestazione, TIPO_MANIFESTAZIONE tipo_MANIFESTAZIONE) {

	ConfigurazioneEmail conf = configurazioneEmailService.findByInstallazione();
	EmailHelper emailHelper = new EmailHelper();
	if (conf != null) {
	    if (StringUtils.isNotBlank(conf.getMailCorpo())) {
		String corpo = sostituzioneSegnaPosto(conf.getMailCorpo(), tipo_MANIFESTAZIONE, codiceManifestazione);
		emailHelper.setCorpo(corpo);
	    }
	    if (StringUtils.isNotBlank(conf.getMailOggetto())) {
		String oggetto = sostituzioneSegnaPosto(conf.getMailOggetto(), tipo_MANIFESTAZIONE, codiceManifestazione);
		emailHelper.setOggetto(oggetto);
	    }
	    return emailHelper;
	}
	return null;
    }

    private String sostituzioneSegnaPosto(String template, TIPO_MANIFESTAZIONE tipo_MANIFESTAZIONE, Integer codiceManifestazione) {

	String denominazione = "";
	String datInserimento = "";
	String comuneSvolgimento = "";
	switch (tipo_MANIFESTAZIONE) {
	case FIERE_MOSTRE:
	    FiereMostre fiereMostre = fiereMostreService.findById(new PkId(codiceManifestazione));
	    denominazione = fiereMostre.getDenominazione();
	    datInserimento = Utilities.formatDate(fiereMostre.getDataInserimento(), false);
	    comuneSvolgimento = fiereMostre.getComuneSvolgimento().getComune();
	    break;
	case SAGRE_FESTE:
	    FesteSagre festeSagre = festeSagreService.findById(new PkId(codiceManifestazione));
	    denominazione = festeSagre.getDenominazione();
	    datInserimento = Utilities.formatDate(festeSagre.getDataInserimento(), false);
	    comuneSvolgimento = festeSagre.getComuneSvolgimento().getComune();
	    break;
	case MANIFESTAZIONI_AREE_PUBBLICHE:
	    ManifAreePubbliche manifAreePubbliche = manifAreePubblicheService.findById(new PkId(codiceManifestazione));
	    denominazione = manifAreePubbliche.getDenominazione();
	    datInserimento = Utilities.formatDate(manifAreePubbliche.getDataInserimento(), false);
	    comuneSvolgimento = manifAreePubbliche.getComuni().getComune();
	    break;
	default:
	    break;
	}
	template = replaceStringIfExsist("[DENOMINAZIONE]", denominazione, template);
	template = replaceStringIfExsist("[DATA_INSERIMENTO]", datInserimento, template);
	template = replaceStringIfExsist("[COMUNE_SVOLGIMENTO]", comuneSvolgimento, template);
	return template;
    }

    private String replaceStringIfExsist(String old, String nuova, String template) {

	if (StringUtils.contains(template, old)) {
	    template = template.replace(old, nuova);
	}
	return template;
    }
}
