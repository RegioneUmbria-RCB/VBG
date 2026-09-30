package it.sgp.middleware.security.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.dao.SigeproBackofficeDAO;
import it.sgp.middleware.security.domain.AmbienteEnum;
import it.sgp.middleware.security.domain.CheckTokenResult;
import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityApp;
import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.domain.ComunisecurityParam;
import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.domain.ContestoEnum;
import it.sgp.middleware.security.domain.DBConnectionInfo;
import it.sgp.middleware.security.domain.InfoUtenteSigepro;
import it.sgp.middleware.security.exceptions.AmbienteNonTrovatoException;
import it.sgp.middleware.security.exceptions.InvalidCredentialsException;
import it.sgp.middleware.security.exceptions.InvalidLoginArgumentsException;
import it.sgp.middleware.security.service.ComunisecurityAppService;
import it.sgp.middleware.security.service.ComunisecurityParamService;
import it.sgp.middleware.security.service.ComunisecurityService;
import it.sgp.middleware.security.service.ComunisecuritySessionService;
import it.sgp.middleware.security.service.LoginService;
import it.sgp.middleware.security.service.RabbitPublisherService;
import it.sgp.middleware.security.service.UserSecurityService;
import it.sgp.middleware.security.utils.Utilities;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private ComunisecurityParamService comunisecurityParamService;
    private ComunisecurityService comunisecurityService;
    private ComunisecuritySessionService comunisecuritySessionService;
    private ComunisecurityAppService comunisecurityAppService;
    private UserSecurityService userSecurityService;
    private SigeproBackofficeDAO sigeproBackofficeDAO;
    private RabbitPublisherService rabbitPublisherService;
	private int validitaToken = 20; // minuti

    @Override
    public CheckTokenResult checkToken(String token) {

	if (StringUtils.isBlank(token)) {
	    log.error("checkToken(): Token nullo");
	    throw new InvalidCredentialsException("Token nullo");
	}
	CheckTokenResult result = new CheckTokenResult();
	ComunisecuritySession sessione = checkTokenInternal(token);
	result.setSession(sessione);
	if (sessione == null) {
	    result.setValid(false);
	} else {
	    result.setValid(true);
	}
	return result;
    }

    @Override
    public List<ComunisecurityParam> getApplicationInfo() {

	return comunisecurityParamService.findAll();
    }

    @Override
    public DBConnectionInfo getDBConnectionInfo(String alias, AmbienteEnum ambiente) throws AmbienteNonTrovatoException {

	if (log.isDebugEnabled()) {
	    log.debug("getDBConnectionInfo(alias:{},ambiente:{})", alias, ambiente);
	}
	if (StringUtils.isBlank(alias)) {
	    log.error("getDBConnectionInfo(): alias nullo");
	    throw new InvalidLoginArgumentsException("Alias nullo");
	}
	Comunisecurity comunisecurity = comunisecurityService.findById(alias);
	if (comunisecurity == null) {
	    log.error("Comunisecurity nullo per alias:{}", alias);
	    throw new InvalidLoginArgumentsException("Alias non valido");
	}
	if (ambiente == null) {
	    log.error("getDBConnectionInfo(): ambiente nullo");
	    throw new InvalidLoginArgumentsException("Ambiente nullo");
	}
	DBConnectionInfo result = new DBConnectionInfo();
	result.setAlias(alias);
	result.setDbMSName("");
	result.setDbUser(comunisecurity.getDbuser());
	result.setDbPassword(comunisecurity.getDbpwd());
	result.setDbOwner(comunisecurity.getOwner());
	if (StringUtils.isBlank(comunisecurity.getIdcomune())) {
	    result.setIdComune(alias);
	} else {
	    result.setIdComune(comunisecurity.getIdcomune());
	}
	boolean trovato = false;
	Set<ComunisecurityConnection> listaConnessioni = comunisecurity.getComunisecurityConnections();
	for (ComunisecurityConnection connection : listaConnessioni) {
	    String ambienteConnessione = connection.getId().getAmbiente();
	    if (ambienteConnessione.equalsIgnoreCase(ambiente.toString())) {
		trovato = true;
		result.setConnectionString(connection.getConnectionstring());
		result.setProvider(connection.getProvider());
		if (BooleanUtils.isTrue(connection.getOverrideConf())) {
		    result.setDbUser(connection.getDbuser());
		    result.setDbPassword(connection.getDbpwd());
		    result.setDbOwner(connection.getOwner());
		}
		break;
	    }
	}
	if (!trovato) {
	    log.error("getDBConnectionInfo(): Non è stata trovata la configurazione per l'alias {} e l'ambiente {}", result.getAlias(), ambiente);
	    throw new AmbienteNonTrovatoException(
		    "Non è stata trovata la configurazione per l'alias " + result.getAlias() + " e l'ambiente " + ambiente);
	}
	return result;
    }

    @Override
    public List<Comunisecurity> getSecurityList() {

	List<Comunisecurity> result = comunisecurityService.findAll(null, null);
	return result;
    }

    /**
     * la funzione valida un token per il contesto passato
     * 
     * @param token
     *            il token da validare
     * @return i dati della sessione se valida o null se il token non è valido
     * @throws InvalidCredentialsException
     *             se token è nullo o stringa vuota
     */
    private ComunisecuritySession checkTokenInternal(String token) {

	log.debug("checkTokenInternal({})", token);
	ComunisecuritySession sessione = comunisecuritySessionService.findById(token);
	if (null == sessione) {
	    log.error("checkTokenInternal(): Sessione non trovata per il token {}", token);
	    return null;
	}
	ComunisecurityParam timeoutSessione = comunisecurityParamService.findById("TOKEN_TIMEOUT");
	if (null != timeoutSessione) {
	    try {
		this.validitaToken = Integer.parseInt(timeoutSessione.getValue());
	    } catch (Exception e) {
		log.error(
			"checkTokenInternal(): Non è stato possibile convertire il valore di timeout '{}' definito nei parametri, uso il default {}",
			timeoutSessione.getValue(), this.validitaToken);
	    }
	}
	if (checkTokenValidity(this.validitaToken, sessione)) {
	    Calendar now = Calendar.getInstance();
	    sessione.setLastrequest(now.getTime());
	    comunisecuritySessionService.update(sessione);
	    return sessione;
	} else {
	    if (BooleanUtils.isTrue(sessione.getValid())) {
		sessione.setValid(Boolean.FALSE);
		comunisecuritySessionService.update(sessione);
	    }
	}
	return null;
    }

    private boolean checkTokenValidity(int tokenValidity, ComunisecuritySession session) {

	Calendar now = Calendar.getInstance();
	Calendar lastRequest = Calendar.getInstance();
	lastRequest.setTime(session.getLastrequest());
	Calendar lastRequestPlusTokenValidity = Calendar.getInstance();
	lastRequestPlusTokenValidity.setTime(session.getLastrequest());
	lastRequestPlusTokenValidity.add(Calendar.MINUTE, tokenValidity);
	int timeCompare = lastRequestPlusTokenValidity.compareTo(now);
	boolean tokenIsValid = timeCompare >= 0 ? true : false;
	if (log.isDebugEnabled()) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    log.debug("Last Request:{}, Timeout:{}min, Now:{} -> TOKEN IS VALID? {}, SESSION IS VALID? {}",
		    new Object[] { sdf.format(lastRequest.getTime()), tokenValidity, sdf.format(now.getTime()), tokenIsValid,
			    BooleanUtils.isTrue(session.getValid()) });
	}
	if (tokenIsValid && BooleanUtils.isTrue(session.getValid())) {
	    return true;
	}
	return false;
    }

    @Override
    public String login(String alias, ContestoEnum contesto, String utente, String password, String clientIp, boolean isSso) {

	validateLoginInput(alias, contesto, utente, password, clientIp, isSso);
	InfoUtenteSigepro infoUtenteSigepro = null;
	switch (contesto) {
	case AMM:
	    infoUtenteSigepro = sigeproBackofficeDAO.verificaAmministrazione(alias, utente);
	    break;
	case OPE:
	    infoUtenteSigepro = sigeproBackofficeDAO.verificaOperatore(alias, utente);
	    break;
	case UTE:
	    infoUtenteSigepro = sigeproBackofficeDAO.verificaAnagrafe(alias, utente);
	    break;
	case UTEG:
	    infoUtenteSigepro = sigeproBackofficeDAO.verificaAnagrafePg(alias, utente);
	    break;
	case APP:
	    throw new InvalidLoginArgumentsException("Il metodo non è valido per il contesto [" + contesto.toString() + "]. Utilizzare loginApp");
	default:
	    throw new InvalidLoginArgumentsException("Il contesto [" + contesto.toString() + "] non è valido");
	}
	return this.login(alias, infoUtenteSigepro, contesto, password, clientIp, isSso);
    }

    @Override
    public String loginApp(String alias, String clientIp) {

	log.debug("loginApp(): alias={}, ip={}", alias, clientIp);
	//FIXME metodo sbagliato! se l'alias passato è nullo una volta recuperato il CurrentlyAuthenticatedUser (ottenuto tramite 
	//la login di spring security che legge l'header
	//ws-security della chiamata ws) e da questo l'oggetto ComunisecurityApp allora se FK_ALIAS è nullo inserisco il token 
	//altrimenti deve essere uguale a quello passato!
	if (StringUtils.isBlank(alias)) {
	    throw new InvalidLoginArgumentsException("L'alias non può essere nullo");
	}
	UserDetails app = userSecurityService.getCurrentlyAuthenticatedUser();
	if (app != null) {
	    Comunisecurity comunisecurity = comunisecurityService.findById(alias);
	    if (comunisecurity != null) {
		ComunisecurityApp comunisecurityApp = comunisecurityAppService.findById(app.getUsername());
		if (comunisecurityApp != null) {
		    //String _alias = (String) EntityUtils.getNestedProperty(comunisecurityApp, "comunisecurity.id"); /* BOOT-DOC-ADD on abbiamo riportato EntityUtils */
		    String _alias = null;
		    if (comunisecurityApp.getComunisecurity() != null) {
			_alias = comunisecurityApp.getComunisecurity().getId();
		    }
		    if (StringUtils.isBlank(_alias) || _alias.equals(alias)) {
			return insertNewToken(alias, app.getUsername(), clientIp, Utilities.resolveIdComune(comunisecurity), ContestoEnum.APP, null);
		    } else {
			log.error("loginApp(): _alias={} and alias={}", _alias, alias);
		    }
		} else {
		    log.error("loginApp(): ComunisecurityApp is null for user={}", app.getUsername());
		}
	    } else {
		log.error("loginApp(): Comunisecurity is null for alias={}", alias);
	    }
	} else {
	    log.error("loginApp(): UserDetails is null!");
	}
	return null;
    }

    @Override
    public boolean logout(String token) {

	boolean success = false;
	ComunisecuritySession sessione = comunisecuritySessionService.findById(token);
	if (null == sessione) {
	    if (log.isDebugEnabled()) {
		log.debug("Sessione non trovata per il token: {}", token);
	    }
	} else {
	    if (checkTokenValidity(this.validitaToken, sessione)) {
		Calendar now = Calendar.getInstance();
		sessione.setLastrequest(now.getTime());
		sessione.setValid(false);
		comunisecuritySessionService.update(sessione);
		success = true;
		if (log.isDebugEnabled()) {
		    log.debug("Sessione invalidata per il token: {}", token);
		}
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("La sessione è scaduta per il token: {}", token);
		}
	    }
	}
	return success;
    }

    /**
     * metodo per la validazione dei valori passati ai metodi di login
     * 
     * @param alias
     * @param contesto
     * @param utente
     * @param password
     * @param clientIp
     * @param isSso
     */
    private void validateLoginInput(String alias, ContestoEnum contesto, String utente, String password, String clientIp, boolean isSso) {

	log.debug("validateLoginInput(alias:{},contesto:{},utente:{},password:{},clientIp:{},isSso:{})",
		new Object[] { alias, contesto, utente, password, clientIp, isSso });
	if (StringUtils.isBlank(alias)) {
	    throw new InvalidLoginArgumentsException("L'alias non può essere nullo");
	}
	if (contesto == null) {
	    throw new InvalidLoginArgumentsException("Il contesto non può essere nullo");
	}
	Comunisecurity comunisecurity = comunisecurityService.findById(alias);
	if (comunisecurity == null) {
	    throw new InvalidLoginArgumentsException("L'alias [" + alias + "] specificato non è valido");
	}
	if (BooleanUtils.isFalse(comunisecurity.getAttivo())) {
	    throw new InvalidLoginArgumentsException("L'alias [" + alias + "] specificato non è attivo");
	}
	if (StringUtils.isBlank(utente)) {
	    throw new InvalidCredentialsException("L'utente non può essere nullo");
	}
	if (!isSso) {
	    if (StringUtils.isBlank(password)) {
		throw new InvalidCredentialsException("La password non può essere nulla");
	    }
	}
    }

    private String login(String alias, InfoUtenteSigepro infoUtenteSigepro, ContestoEnum contesto, String password, String clientIp, boolean isSSO) {

	boolean success = false;
	if (infoUtenteSigepro != null) {
	    success = true;
	}
	if (!isSSO) {
	    success = success && validatePassword(password, infoUtenteSigepro.getPassword());
	}
	if (!success) {
	    throw new InvalidCredentialsException("Utente o password non validi.");
	}
	return insertNewToken(alias, infoUtenteSigepro.getUserid(), clientIp, infoUtenteSigepro.getIdcomune(), contesto,
		infoUtenteSigepro.getLivelloIdentificazione());
    }

    private boolean validatePassword(String passwordAuth, String passwordDb) {

	boolean success = false;
	if (passwordAuth.equals(passwordDb)) {
	    success = true;
	}
	return success;
    }

    /**
     * @param alias
     * @param utente
     * @param clientIp
     * @param idcomune
     * @param contesto
     * @return
     */
    private String insertNewToken(String alias, String utente, String clientIp, String idcomune, ContestoEnum contesto,
	    Integer livelloIdentificazioneUtente) {

	UUID id = UUID.randomUUID();
	String token = id.toString();
	ComunisecuritySession nuovaSessione = new ComunisecuritySession();
	nuovaSessione.setId(token);
	nuovaSessione.setAlias(alias);
	nuovaSessione.setContesto(contesto.toString());
	nuovaSessione.setFirstrequest(Calendar.getInstance().getTime());
	nuovaSessione.setLastrequest(nuovaSessione.getFirstrequest());
	nuovaSessione.setUserid(utente);
	nuovaSessione.setClientIp(clientIp);
	nuovaSessione.setValid(true);
	if (StringUtils.isNotBlank(idcomune)) {
	    nuovaSessione.setIdcomune(idcomune);
	} else {
	    nuovaSessione.setIdcomune(alias);
	}
	if (livelloIdentificazioneUtente != null) {
	    nuovaSessione.setAuthLevel(livelloIdentificazioneUtente);
	}
	if (log.isDebugEnabled()) {
	    log.debug("Inserisco una nuova sessione {}", ReflectionToStringBuilder.toString(nuovaSessione, ToStringStyle.MULTI_LINE_STYLE));
	}
	comunisecuritySessionService.insert(nuovaSessione);
	
	try {
		rabbitPublisherService.sendMessageIfRabbitEnabled(nuovaSessione);
	}catch(Exception e) {
		log.error("Errore durante la scrittura di Rabbit", e);
	}
	
	return token;
    }

    @Autowired
    public void setComunisecurityParamService(ComunisecurityParamService comunisecurityParamService) {

	this.comunisecurityParamService = comunisecurityParamService;
    }

    @Autowired
    public void setComunisecurityService(ComunisecurityService comunisecurityService) {

	this.comunisecurityService = comunisecurityService;
    }

    @Autowired
    public void setComunisecuritySessionService(ComunisecuritySessionService comunisecuritySessionService) {

	this.comunisecuritySessionService = comunisecuritySessionService;
    }

    @Autowired
    public void setComunisecurityAppService(ComunisecurityAppService comunisecurityAppService) {

	this.comunisecurityAppService = comunisecurityAppService;
    }

    @Autowired
    public void setSigeproBackofficeDAO(SigeproBackofficeDAO sigeproBackofficeDAO) {

	this.sigeproBackofficeDAO = sigeproBackofficeDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }
    
    @Autowired
    public void setRabbitPublisherService(RabbitPublisherService rabbitPublisherService) {
		
    this.rabbitPublisherService = rabbitPublisherService;
	}
}
