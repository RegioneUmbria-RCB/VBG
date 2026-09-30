package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.ActionType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.MailConfigRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.MailConfigResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.ProtocolType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityMailParams;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.math.BigInteger;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(serviceName = "MailConfigService", portName = "MailConfigSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/mailconfig", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.mailconfig.MailConfig")
public class MailConfigWS extends BaseWS implements it.gruppoinit.pal.gp.backoffice.definitions.mailconfig.MailConfig {

    private static final Logger log = LoggerFactory.getLogger(MailConfigWS.class);
    private MailConfigService mailConfigService;

    @Autowired
    public void setMailConfigService(MailConfigService mailConfigService) {

	this.mailConfigService = mailConfigService;
    }

    public MailConfigResponse mailConfig(MailConfigRequest mailConfigRequest) {

	log.debug("mailConfig(token={}, software={})", mailConfigRequest.getToken(), mailConfigRequest.getSoftware());
	MailConfigResponse mailConfigResponse = null;
	setORMHelper(mailConfigRequest.getSoftware(), mailConfigRequest.getToken());
	MailConfig mailConfig = mailConfigService.findMailConfig();
	if (mailConfig == null) {
	    if (isSENDAction(mailConfigRequest)) {
		mailConfigResponse = populateMailConfigResponseFromSigeproSecurity();
	    }
	} else {
	    mailConfigResponse = populateMailConfigResponse(mailConfig, mailConfigRequest.getAction());
	}
	try {
	    validateResponse(mailConfigResponse, mailConfigRequest.getAction());
	} catch (Exception e) {
	    log.error("Errore nella verifica della configurazione mailservice: " + e.getMessage());
	    throw new RuntimeException("Errore nella verifica della configurazione mailservice: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return mailConfigResponse;
    }

    private MailConfigResponse populateMailConfigResponse(MailConfig mailConfig, ActionType action) {

	MailConfigResponse mailConfigResponse = new MailConfigResponse();
	if (ActionType.SEND.equals(action)) {
	    mailConfigResponse.setUser(mailConfig.getLoginname());
	    mailConfigResponse.setPassword(mailConfig.getLoginpass());
	    mailConfigResponse.setUrl(mailConfig.getMailserver());
	    try {
		BigInteger port = BigInteger.valueOf(mailConfig.getPort());
		mailConfigResponse.setPort(port);
	    } catch (Exception e) {
		log.error("populateMailConfigResponse: MAILCONFIG PORT non è un numero.");
	    }
	    mailConfigResponse.setSenderEmailAddress(mailConfig.getSenderaddress());
	    mailConfigResponse.setUseAuthentication(BooleanUtils.isTrue(mailConfig.getUseauthentication()));
	    Integer protocol = mailConfig.getUsessl();
	    if (protocol == null) {
		log.error("populateMailConfigResponse: MAILCONFIG USESSL è obbligatorio.");
	    } else {
		switch (protocol) {
		case 0:
		    mailConfigResponse.setProtocol(ProtocolType.SMTP);
		    break;
		case 1:
		    mailConfigResponse.setProtocol(ProtocolType.SSMTP_SMTPS);
		    break;
		case 2:
		    mailConfigResponse.setProtocol(ProtocolType.SMTP_SSL);
		    break;
		default:
		    log.error("populateMailConfigResponse: MAILCONFIG USESSL non è corretto: {}", protocol);
		    break;
		}
	    }
	} else {
	    mailConfigResponse.setUser(mailConfig.getInLoginname());
	    mailConfigResponse.setPassword(mailConfig.getInLoginpass());
	    mailConfigResponse.setUrl(mailConfig.getInMailserver());
	    try {
		BigInteger inPort = BigInteger.valueOf(mailConfig.getInPort());
		mailConfigResponse.setPort(inPort);
	    } catch (Exception e) {
		log.error("populateMailConfigResponse: MAILCONFIG IN_PORT non è un numero.");
	    }
	    mailConfigResponse.setUseAuthentication(BooleanUtils.isTrue(mailConfig.getInUseauthentication()));
	    Integer inProtocol = mailConfig.getInUsessl();
	    if (inProtocol == null) {
		log.error("populateMailConfigResponse: MAILCONFIG IN_USESSL è obbligatorio.");
	    } else {
		switch (inProtocol) {
		case 0:
		    mailConfigResponse.setProtocol(ProtocolType.POP_3);
		    break;
		case 1:
		    mailConfigResponse.setProtocol(ProtocolType.IMAP);
		    break;
		case 2:
		    mailConfigResponse.setProtocol(ProtocolType.SSL_POP_3);
		    break;
		case 3:
		    mailConfigResponse.setProtocol(ProtocolType.SSL_IMAP);
		    break;
		default:
		    log.error("populateMailConfigResponse: MAILCONFIG IN_USESSL non è corretto: {}", inProtocol);
		    break;
		}
	    }
	}
	return mailConfigResponse;
    }

    private MailConfigResponse populateMailConfigResponseFromSigeproSecurity() {

	Map<String, String> mailParams = WebConstants.getSecurityMailParams();
	MailConfigResponse mailConfigResponse = new MailConfigResponse();
	mailConfigResponse.setUser(mailParams.get(SecurityMailParams.LOGINNAME.name()));
	mailConfigResponse.setPassword(mailParams.get(SecurityMailParams.PASSWORD.name()));
	mailConfigResponse.setUrl(mailParams.get(SecurityMailParams.MAILSERVER.name()));
	try {
	    BigInteger port = new BigInteger(mailParams.get(SecurityMailParams.SMTP_PORT.name()));
	    mailConfigResponse.setPort(port);
	} catch (Exception e) {
	    log.error("populateMailConfigResponseFromSigeproSecurity: MAIL.SMTP_PORT deve essere un numero.");
	}
	mailConfigResponse.setSenderEmailAddress(mailParams.get(SecurityMailParams.SENDER.name()));
	try {
	    Integer useAuth = Integer.valueOf(mailParams.get(SecurityMailParams.USE_AUTHENTICATION.name()));
	    switch (useAuth) {
	    case 0:
		mailConfigResponse.setUseAuthentication(Boolean.FALSE);
		break;
	    case 1:
		mailConfigResponse.setUseAuthentication(Boolean.TRUE);
		break;
	    default:
		break;
	    }
	} catch (Exception e) {
	    log.error("populateMailConfigResponseFromSigeproSecurity: MAIL.USE_AUTHENTICATION deve essere un numero.");
	}
	try {
	    Integer protocol = Integer.valueOf((mailParams.get(SecurityMailParams.USE_SSL.name())));
	    switch (protocol) {
	    case 0:
		mailConfigResponse.setProtocol(ProtocolType.SMTP);
		break;
	    case 1:
		mailConfigResponse.setProtocol(ProtocolType.SSMTP_SMTPS);
		break;
	    case 2:
		mailConfigResponse.setProtocol(ProtocolType.SMTP_SSL);
		break;
	    default:
		break;
	    }
	} catch (Exception e) {
	    log.error("populateMailConfigResponseFromSigeproSecurity: MAIL.USE_SSL deve essere un numero.");
	}
	return mailConfigResponse;
    }

    private void validateResponse(MailConfigResponse mailConfigResponse, ActionType action) throws Exception {

	if (mailConfigResponse == null) {
	    throw new RuntimeException("Nessuna configurazione trovata.");
	}
	if (mailConfigResponse.isUseAuthentication()) {
	    if (StringUtils.isBlank(mailConfigResponse.getUser())) {
		throw new RuntimeException("Utente non specificato");
	    }
	    if (StringUtils.isBlank(mailConfigResponse.getPassword())) {
		throw new RuntimeException("Password non specificata");
	    }
	}
	if (mailConfigResponse.getPort() == null) {
	    throw new RuntimeException("Porta mail server non specificata");
	}
	if (mailConfigResponse.getProtocol() == null) {
	    throw new RuntimeException("Protocollo mail server non specificato");
	}
	if (ActionType.SEND.equals(action)) {
	    if (StringUtils.isBlank(mailConfigResponse.getSenderEmailAddress())) {
		throw new RuntimeException("Indirizzo mail mittente non specificato");
	    }
	}
	if (StringUtils.isBlank(mailConfigResponse.getUrl())) {
	    throw new RuntimeException("Indirizzo mail server non specificato");
	}
    }

    /**
     * se il tag action è popolato a SEND restituisce true altrimenti false
     * 
     * @param mailConfigRequest
     * @return
     */
    private boolean isSENDAction(MailConfigRequest mailConfigRequest) {

	boolean esito = false;
	if (ActionType.SEND.equals(mailConfigRequest.getAction())) {
	    esito = true;
	}
	return esito;
    }
}
