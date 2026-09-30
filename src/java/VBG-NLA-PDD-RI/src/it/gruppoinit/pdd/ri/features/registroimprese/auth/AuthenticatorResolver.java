package it.gruppoinit.pdd.ri.features.registroimprese.auth;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import it.gruppoinit.pdd.ri.features.configurazione.Certificato;
import it.gruppoinit.pdd.ri.features.configurazione.ConfigurazioneService;
import it.gruppoinit.pdd.ri.features.configurazione.Ente;

public class AuthenticatorResolver {

    private String idComuneAlias;
    private String codiceComune;
    private String tipoAutenticazione;
    private Object port;
    private String userName;
    private String password;
    private ConfigurazioneService service;
    private Logger logger;

    public AuthenticatorResolver(Logger logger, ConfigurazioneService service, AuthenticatorRequest request) {

	this.service = service;
	this.idComuneAlias = request.getIdComuneAlias();
	this.codiceComune = request.getCodiceComune();
	this.tipoAutenticazione = request.getTipoAutenticazione();
	this.port = request.getPort();
	this.userName = request.getUserName();
	this.password = request.getPassword();
	this.logger = logger;
    }

    public void authenticate() {

	logger.debug("authenticate per il codice comune " + this.codiceComune);
	Ente ente = null;
	//1. Se presente codiceComune verifico se configurata l'autenticazione con certificato client
	if (!StringUtils.isBlank(this.codiceComune)) {
	    logger.debug("ricerca certificato per il comune " + this.codiceComune);
	    ente = this.service.findByCodiceCatastale(codiceComune);
	}
	//2. Se codicecomune vuoto, recupero la lista dei comuni presenti per quello specifico idcomune e 
	//   verifico se almeno 1 ha il certificato client
	if (StringUtils.isBlank(this.codiceComune)) {
	    logger.debug("nessun codice catastale passato, viene verificato se almeno uno dei comuni associati ha il certificato su file system");
	    for (String codCom : this.service.findComuniAssociati(idComuneAlias)) {
		ente = this.service.findByCodiceCatastale(codCom);
		if (ente != null) {
		    break;
		}
	    }
	}
	if (ente != null) {
	    logger.debug("ente trovato:" + ente.getCodiceCatastale() + ", certificato " + ente.getNomeFile());
	    Certificato cert = this.service.findByNome(ente.getNomeFile());
	    //2. Imposto l'autenticazione con certficato client
	    new ClientCertAuthenticator(port, cert.getFileURI() + cert.getNomeFile(), cert.getPassword()).authenticate();
	    //1. Setto le proprietà wsse:Security header
	    new WsseSignatureTimestampAuthenticator(port, cert).authenticate();
	    return;
	}
	logger.debug("nessun ente trovato con il certificato su file system, viene verificato il tipo di autenticazione " + tipoAutenticazione);
	if (tipoAutenticazione.equalsIgnoreCase("WSSE_USERNAME_TOKEN")) {
	    new WsseUsernameTokenAuthenticator(port, userName, password).authenticate();
	    return;
	} else {// tipoAutenticazione BASIC_AUTHENTICATION
	    new BasicAuthenticator(port, userName, password).authenticate();
	    return;
	}
    }
}
