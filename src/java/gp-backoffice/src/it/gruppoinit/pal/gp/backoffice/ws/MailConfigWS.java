package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.ActionType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.MailConfigRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.MailConfigRequest2;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.MailConfigResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.MailConfigResponse2;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailconfig.ProtocolType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityMailParams;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.utils.CryptoUtils;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.math.BigInteger;
import java.util.List;
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
    private SoftwareService softwareService;
    private ComuniService comuniService;
    private CryptoUtils crypto = new CryptoUtils();

    @Autowired
    public void setMailConfigService(MailConfigService mailConfigService) {

	this.mailConfigService = mailConfigService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
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

    @Override
    public MailConfigResponse2 mailConfig2(MailConfigRequest2 mailConfigRequest2) {

	log.debug("mailConfig2#token={},idcomune={} software={},codicecomune={},idAccount={}", new Object[] { mailConfigRequest2.getToken(),
		ORMHelper.getIdcomune(), mailConfigRequest2.getSoftware(), StringUtils.defaultIfEmpty(mailConfigRequest2.getCodicecomune(), ""),
		mailConfigRequest2.getSoftware(), mailConfigRequest2.getIdAccount() });
	MailConfigResponse2 mailConfigResponse2 = null;
	setORMHelper(mailConfigRequest2.getSoftware(), mailConfigRequest2.getToken());
	MailConfig mailConfig = null;
	//@gianpaolot: per configurazione uguale a NULL si è deciso di non riportare il comportamento del precedente metodo che recuperava 
	// informazioni sulla security
	if (mailConfigRequest2.getIdAccount() != null) {
	    log.debug("mailConfig2# Ricerca mailconfig per idcomune={}, idAccount={}",
		    new Object[] { ORMHelper.getIdcomune(), mailConfigRequest2.getIdAccount() });
	    // L'id account passato è il codice della PkId della tabella Mailconfig
	    mailConfig = mailConfigService.findById(new PkId(new Integer(mailConfigRequest2.getIdAccount().intValue())));
	} else {
	    if (StringUtils.isBlank(mailConfigRequest2.getCodicecomune())) {
		log.debug("mailConfig2# Ricerca mailconfig per idcomune={}, software={}",
			new Object[] { ORMHelper.getIdcomune(), mailConfigRequest2.getSoftware() });
		mailConfig = _getMailConfigBySoftware(mailConfigRequest2.getSoftware());
		if (mailConfig == null) {
		    throw new RuntimeException("Configurazione non trovata per idcomune=" + ORMHelper.getIdcomune() + ",software="
			    + mailConfigRequest2.getSoftware() + "(o software=TT)");
		}
	    } else {
		log.debug("mailConfig2# Ricerca mailconfig per idcomune={}, software={},codicecomune={}", new Object[] { ORMHelper.getIdcomune(),
			mailConfigRequest2.getSoftware(), mailConfigRequest2.getCodicecomune() });
		mailConfig = _getMailConfigBySoftwareAndCodicecomune(mailConfigRequest2.getSoftware(), mailConfigRequest2.getCodicecomune());
		if (mailConfig == null) {
		    throw new RuntimeException("Configurazione non trovata per idcomune=" + ORMHelper.getIdcomune() + ",software="
			    + mailConfigRequest2.getSoftware() + "(o software=TT),codice comune=" + mailConfigRequest2.getCodicecomune()
			    + "(o Tutti i comuni [NULL]");
		}
	    }
	}
	if (mailConfig != null) {
	    //this.mailConfigService.decryptPassword(mailConfig);
	    Software software = softwareService.findById(mailConfig.getSoftware().getCodice());
	    if (mailConfig.getComuni() != null && StringUtils.isNotBlank(mailConfig.getComuni().getCodicecomune())) {
		Comuni comuni = comuniService.findById(mailConfig.getComuni().getCodicecomune());
		mailConfig.setComuni(comuni);
	    } else {
		mailConfig.setComuni(null);
	    }
	    mailConfig.setSoftware(software);
	    log.debug("mailConfig2# recuperato mailconfig idAccount={},descrizione={}[{}]",
		    new Object[] { mailConfig.getId().getCodice(), mailConfig.getDescrizione(), mailConfig.getId().getCodice() });
	    //mailConfig = mailConfigService.findById(new PkId(mailConfig.getId().getCodice()));
	    mailConfigResponse2 = populateMailConfigResponse2(mailConfig, mailConfigRequest2.getAction());
	}
	try {
	    validateResponse2(mailConfigResponse2, mailConfigRequest2.getAction());
	} catch (Exception e) {
	    log.error("mailConfig2# Errore nella verifica della configurazione mailservice: " + e.getMessage());
	    throw new RuntimeException("Errore nella verifica della configurazione mailservice: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return mailConfigResponse2;
    }

    ///////////////////////////////////////////////////////// 	METODI PRIVATI PER MAILCONFIG2 ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * <pre>
     * Cerca in ordine per (ordinate per il principale e abilitati) 
     * 		1. idcomune,software,codicecomune    	 --> se esiste ritorna il primo della lista (se esiste sarà il principale)
     *          2. idcomune,software,tutti comunie(NULL) --> se esiste ritorna il primo della lista (se esiste sarà il principale)
     *          3. idcomune,TT,codicecomune --> se esiste ritorna il primo della lista (se esiste sarà il principale)
     *          4. idcomune,TT,tutti comunie(NULL) --> se esiste ritorna il primo della lista (se esiste sarà il principale)
     *          5. se non esiste in nessuno dei 4 casi ritorna NULL
     * @param software
     * @return
     * </pre>
     */
    private MailConfig _getMailConfigBySoftwareAndCodicecomune(String software, String codicecomune) {

	MailConfig mailConfig = null;
	// Cerco per idcomune,software, codicecomune
	List<MailConfig> m1 = mailConfigService.findBySoftwareAndCodiceComune(software, codicecomune, true);
	if (!m1.isEmpty()) {
	    log.debug("_getMailConfigBySoftwareAndCodicecomune# mailconfig trovato per idcomune={},software={},codicecomune={}", new Object[] {
		    ORMHelper.getIdcomune(), software, codicecomune });
	    return m1.get(0);
	}
	// Cerco per idcomune,software, Tutti comuni (NULL)
	List<MailConfig> m2 = mailConfigService.findBySoftwareAndCodiceComune(software, null, true);
	if (!m2.isEmpty()) {
	    log.debug("_getMailConfigBySoftwareAndCodicecomune# mailconfig trovato per idcomune={},software={},codicecomune={}", new Object[] {
		    ORMHelper.getIdcomune(), software, null });
	    return m2.get(0);
	}
	// Cerco per idcomune,TT, codicecomune
	List<MailConfig> m3 = mailConfigService.findBySoftwareAndCodiceComune(WebConstants.SOFTWARE_TT, codicecomune, true);
	if (!m3.isEmpty()) {
	    log.debug("_getMailConfigBySoftwareAndCodicecomune# mailconfig trovato per idcomune={},software={},codicecomune={}", new Object[] {
		    ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT, codicecomune });
	    return m3.get(0);
	}
	// cerco per idcomune, TT, Tutti comuni (NULL)
	List<MailConfig> m4 = mailConfigService.findBySoftwareAndCodiceComune(WebConstants.SOFTWARE_TT, null, true);
	if (!m4.isEmpty()) {
	    log.debug("_getMailConfigBySoftwareAndCodicecomune# mailconfig trovato per idcomune={},software={},codicecomune={}", new Object[] {
		    ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT, null });
	    return m4.get(0);
	}
	return mailConfig;
    }

    /**
     * <pre>
     * Cerca in ordine per (ordinate per il principale e abilitati) 
     * 		1. idcomune software    --> se esiste ritorna il primo della lista (se esiste sarà il principale)
     *          2. idcomune software TT --> se esiste ritorna il primo della lista (se esiste sarà il principale)
     *          3. se non esiste in nessuno dei due casi ritorna NULL
     * @param software
     * @return
     * </pre>
     */
    private MailConfig _getMailConfigBySoftware(String software) {

	MailConfig mailConfig = null;
	List<MailConfig> m1 = mailConfigService.findBySoftware(software, true);
	// 2. cerco per idcomune e software
	if (!m1.isEmpty()) {
	    log.debug("_getMailConfigBySoftware# mailconfig trovato per idcomune={},software={}", ORMHelper.getIdcomune(), software);
	    return m1.get(0);
	}
	// 2. cerco per idcomune e TT
	List<MailConfig> m2 = mailConfigService.findBySoftware(WebConstants.SOFTWARE_TT, true);
	if (!m2.isEmpty()) {
	    log.debug("_getMailConfigBySoftware# mailconfig trovato per idcomune={},software={}", ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT);
	    return m2.get(0);
	}
	return mailConfig;
    }

    private MailConfigResponse2 populateMailConfigResponse2(MailConfig mailConfig, ActionType action) {

	MailConfigResponse2 mailConfigResponse2 = new MailConfigResponse2();
	// Nuovi campi della response MailConfigResponse2
	if (mailConfig.getComuni() != null && StringUtils.isNotBlank(mailConfig.getComuni().getCodicecomune())) {
	    mailConfigResponse2.setCodiceComune(mailConfig.getComuni().getCodicecomune());
	    mailConfigResponse2.setComune(mailConfig.getComuni().getComune());
	}
	if (mailConfig.getSoftware() != null && StringUtils.isNotBlank(mailConfig.getSoftware().getCodice())) {
	    mailConfigResponse2.setCodiceSoftware(mailConfig.getSoftware().getCodice());
	}
	mailConfigResponse2.setDescrizione(mailConfig.getDescrizione());
	mailConfigResponse2.setIdAccount(new BigInteger(mailConfig.getId().getCodice().toString()));
	String pwd = "";
	if (ActionType.SEND.equals(action)) {
	    mailConfigResponse2.setUser(mailConfig.getLoginname());
	    if(StringUtils.isNotBlank(mailConfig.getLoginpass())){
		pwd = crypto.decrypt(CryptoUtils.DEFAULT_SECRET_KEY, mailConfig.getLoginpass());
	    }
	    mailConfigResponse2.setPassword(pwd);
	    mailConfigResponse2.setUrl(mailConfig.getMailserver());
	    /////
	    try {
		BigInteger port = BigInteger.valueOf(mailConfig.getPort());
		mailConfigResponse2.setPort(port);
	    } catch (Exception e) {
		log.error("populateMailConfigResponse2: MAILCONFIG PORT non è un numero.");
	    }
	    mailConfigResponse2.setSenderEmailAddress(mailConfig.getSenderaddress());
	    mailConfigResponse2.setUseAuthentication(BooleanUtils.isTrue(mailConfig.getUseauthentication()));
	    Integer protocol = mailConfig.getUsessl();
	    if (protocol == null) {
		log.error("populateMailConfigResponse2: MAILCONFIG USESSL è obbligatorio.");
	    } else {
		switch (protocol) {
		case 0:
		    mailConfigResponse2.setProtocol(ProtocolType.SMTP);
		    break;
		case 1:
		    mailConfigResponse2.setProtocol(ProtocolType.SSMTP_SMTPS);
		    break;
		case 2:
		    mailConfigResponse2.setProtocol(ProtocolType.SMTP_SSL);
		    break;
		default:
		    log.error("populateMailConfigResponse2: MAILCONFIG USESSL non è corretto: {}", protocol);
		    break;
		}
	    }
	} else {
	    mailConfigResponse2.setUser(mailConfig.getInLoginname());
	    if(StringUtils.isNotBlank(mailConfig.getInLoginpass())){
		pwd = crypto.decrypt(CryptoUtils.DEFAULT_SECRET_KEY, mailConfig.getInLoginpass());
	    }
	    mailConfigResponse2.setPassword(pwd);
	    mailConfigResponse2.setUrl(mailConfig.getInMailserver());
	    try {
		BigInteger inPort = BigInteger.valueOf(mailConfig.getInPort());
		mailConfigResponse2.setPort(inPort);
	    } catch (Exception e) {
		log.error("populateMailConfigResponse2: MAILCONFIG IN_PORT non è un numero.");
	    }
	    mailConfigResponse2.setUseAuthentication(BooleanUtils.isTrue(mailConfig.getInUseauthentication()));
	    Integer inProtocol = mailConfig.getInUsessl();
	    if (inProtocol == null) {
		log.error("populateMailConfigResponse2: MAILCONFIG IN_USESSL è obbligatorio.");
	    } else {
		switch (inProtocol) {
		case 0:
		    mailConfigResponse2.setProtocol(ProtocolType.POP_3);
		    break;
		case 1:
		    mailConfigResponse2.setProtocol(ProtocolType.IMAP);
		    break;
		case 2:
		    mailConfigResponse2.setProtocol(ProtocolType.SSL_POP_3);
		    break;
		case 3:
		    mailConfigResponse2.setProtocol(ProtocolType.SSL_IMAP);
		    break;
		default:
		    log.error("populateMailConfigResponse2: MAILCONFIG IN_USESSL non è corretto: {}", inProtocol);
		    break;
		}
	    }
	}
	return mailConfigResponse2;
    }

    private void validateResponse2(MailConfigResponse2 mailConfigResponse2, ActionType action) throws Exception {

	if (mailConfigResponse2 == null) {
	    throw new RuntimeException("Nessuna configurazione trovata.");
	}
	if (mailConfigResponse2.isUseAuthentication()) {
	    if (StringUtils.isBlank(mailConfigResponse2.getUser())) {
		throw new RuntimeException("Utente non specificato");
	    }
	    if (StringUtils.isBlank(mailConfigResponse2.getPassword())) {
		throw new RuntimeException("Password non specificata");
	    }
	}
	if (mailConfigResponse2.getPort() == null) {
	    throw new RuntimeException("Porta mail server non specificata");
	}
	if (mailConfigResponse2.getProtocol() == null) {
	    throw new RuntimeException("Protocollo mail server non specificato");
	}
	if (ActionType.SEND.equals(action)) {
	    if (StringUtils.isBlank(mailConfigResponse2.getSenderEmailAddress())) {
		throw new RuntimeException("Indirizzo mail mittente non specificato");
	    }
	}
	if (StringUtils.isBlank(mailConfigResponse2.getUrl())) {
	    throw new RuntimeException("Indirizzo mail server non specificato");
	}
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////// 	METODI PRIVATI PER MAILCONFIG ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
