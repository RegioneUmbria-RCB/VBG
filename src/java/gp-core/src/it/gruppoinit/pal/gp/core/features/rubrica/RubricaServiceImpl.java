package it.gruppoinit.pal.gp.core.features.rubrica;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPJSSESecureSocketFactory;
import com.novell.ldap.LDAPSearchConstraints;
import com.novell.ldap.LDAPSearchResults;
import com.novell.ldap.LDAPSocketFactory;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Rubrica;
import it.gruppoinit.pal.gp.core.features.rubrica.dao.IRubricaDAO;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.proxy.TrustAllX509TrustManager;

@Service
public class RubricaServiceImpl extends BaseServiceImpl<Rubrica, PkId> implements IRubricaService {

    public static final Logger log = LoggerFactory.getLogger(RubricaServiceImpl.class);
    private IRubricaDAO rubricaDAO;

    @Autowired
    public RubricaServiceImpl(IRubricaDAO rubricaDAO) {

	this.rubricaDAO = rubricaDAO;
    }

    @Override
    public List<RisultatoRicercaRubrica> ricercaIndirizzo(String partial, int resultCount) {

	List<RisultatoRicercaRubrica> result = new ArrayList<RisultatoRicercaRubrica>();
	List<Rubrica> indirizzi = this.rubricaDAO.ricercaIndirizzo(partial, resultCount);
	for (Rubrica r : indirizzi) {
	    result.add(new RisultatoRicercaRubrica(r.getDescrizione(), r.getMail()));
	}
	return result;
    }

    @Override
    public void insert(Rubrica entity) {

	if (validateEntity(entity)) {
	    this.rubricaDAO.insert(entity);
	}
    }

    @Override
    public void update(Rubrica entity) {

	if (validateEntity(entity)) {
	    this.rubricaDAO.update(entity);
	}
    }

    @Override
    public void delete(Rubrica entity) {

	if (validateEntity(entity)) {
	    this.rubricaDAO.delete(entity);
	}
    }

    @Override
    public List<Rubrica> findAll(Integer firstResult, Integer maxResult) {

	return this.rubricaDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Rubrica findById(PkId id) {

	return this.rubricaDAO.findById(id);
    }

    @Override
    protected Class<Rubrica> getEntityClass() {

	return Rubrica.class;
    }

    @Override
    public void updateAllineaDaLDAP(LDAPProperties ldapProperties) throws Exception {

	List<Rubrica> rubricas = allineaDaLDAP(ldapProperties);
	if (rubricas.isEmpty()) {
	    // non ho trovato record esco senza svuotare la rubrica
	    return;
	}
	this.svuotaDB();
	for (Rubrica rubrica : rubricas) {
	    this.insert(rubrica);
	}
    }

    private List<Rubrica> allineaDaLDAP(LDAPProperties ldapProperties) throws Exception {

	List<Rubrica> rubricas = new ArrayList<Rubrica>();
	LDAPConnection connectAndBind = this.connectAndBind(ldapProperties);
	if (connectAndBind == null) {
	    throw new LDAPException("LDAPConnection is null. Call connectAndBind first.", LDAPException.LOCAL_ERROR, (String) null);
	}
	LDAPSearchConstraints cons = connectAndBind.getSearchConstraints();
	cons.setReferralFollowing(true);
	cons.setMaxResults(10000);
	connectAndBind.setConstraints(cons);
	log.info("inizio della scansione");
	// scope doppio perché non tirava fuori tutti i record
	int scope = LDAPConnection.SCOPE_SUB;
	ldapProperties.setLdapSearchFilter("(&(objectCategory=person)(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=2)))");
	List<Rubrica> rubSub = navigateTree(scope, connectAndBind, ldapProperties);
	if (!rubSub.isEmpty()) {
	    rubricas.addAll(rubSub);
	}
	scope = LDAPConnection.SCOPE_ONE;
	List<Rubrica> rub = navigateTree(scope, connectAndBind, ldapProperties);
	if (!rub.isEmpty()) {
	    rubricas.addAll(rub);
	}
	//scope = LDAPConnection.SCOPE_SUB;
	//ldapProperties.setLdapSearchFilter("(|(objectClass=container)(objectClass=organizationalUnit))");
	//trovaBaseDN(scope, connectAndBind, ldapProperties);
	log.info("fine della scansione");
	connectAndBind.disconnect();
	return rubricas;
    }

    private List<Rubrica> navigateTree(int scope, LDAPConnection conn, LDAPProperties ldapProperties) throws LDAPException {

	List<Rubrica> rubricas = new ArrayList<Rubrica>();
	LDAPSearchResults searchResults;
	searchResults = conn.search(ldapProperties.getLdapSearchBaseDn(), scope, ldapProperties.getLdapFilter(), ldapProperties.getLdapUserAttrs(),
		false);
	while (searchResults.hasMore()) {
	    LDAPEntry nextEntry = null;
	    try {
		nextEntry = searchResults.next();
	    } catch (LDAPException e) {
		if (e.getResultCode() == LDAPException.LDAP_TIMEOUT || e.getResultCode() == LDAPException.CONNECT_ERROR)
		    break;
		else
		    continue;
	    }
	    LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
	    Iterator allAttributes = attributeSet.iterator();
	    String mail = "";
	    String descrizione = "";
	    String chiave = "";
	    while (allAttributes.hasNext()) {
		LDAPAttribute attribute = (LDAPAttribute) allAttributes.next();
		String attributeName = attribute.getName();
		if (attributeName.equalsIgnoreCase("mail") || attributeName.equalsIgnoreCase("displayName") || attributeName.equalsIgnoreCase("cn")) {
		    Enumeration allValues = attribute.getStringValues();
		    if (allValues != null) {
			while (allValues.hasMoreElements()) {
			    String value = (String) allValues.nextElement();
			    if (attributeName.equalsIgnoreCase("mail")) {
				mail = value;
			    } else if (attributeName.equalsIgnoreCase("displayName")) {
				descrizione = value;
			    } else if (attributeName.equalsIgnoreCase("cn")) {
				chiave = value;
			    }
			}
		    }
		}
	    }
	    if (StringUtils.isNotEmpty(mail)) {
		rubricas.add(new Rubrica(descrizione, mail, chiave));
	    }
	}
	return rubricas;
    }

    /**
     * metodo per attivare una connessione LDAP con il server ed eseguire il bind dell'utente
     * 
     * @return
     * @throws LDAPException
     */
    private LDAPConnection connectAndBind(LDAPProperties ldapProperties) throws LDAPException {

	log.info("connectAndBind#Connect to LDAP server (host: {} , port: {})", ldapProperties.getLdapHost(), ldapProperties.getLdapPort());
	LDAPConnection conn = connectToLDAP(ldapProperties);
	conn.connect(ldapProperties.getLdapHost(), ldapProperties.getLdapPort());
	boolean connectSuccess = conn.isConnected();
	boolean boundSuccess = false;
	log.debug("connectAndBind#Connect to LDAP server: {}", connectSuccess);
	try {
	    if (ldapProperties.getLdapLoginType().equalsIgnoreCase("bind")) {
		String userDNInt = getUserDN(ldapProperties.getLdapUserDn(), ldapProperties.getLdapUser());
		log.debug("connectAndBind#Bind user '{}' to LDAP server with: {} ", ldapProperties.getLdapUser(), userDNInt);
		conn.bind(LDAPConnection.LDAP_V3, userDNInt, ldapProperties.getLdapPsw().getBytes("UTF-8"));
		boundSuccess = conn.isBound();
		log.debug("connectAndBind#Bind user {}: {}", ldapProperties.getLdapUser(), boundSuccess);
	    } else {
		String userDNInt = getUserDN(ldapProperties.getLdapUid(), ldapProperties.getLdapUser());
		log.debug("connectAndBind#Bind admin user '{}' to LDAP server with: {} ", ldapProperties.getLdapUser(), userDNInt);
		conn.bind(LDAPConnection.LDAP_V3, userDNInt, ldapProperties.getLdapPsw().getBytes("UTF-8"));
		boundSuccess = conn.isBound();
		log.debug("connectAndBind#Bind user {}: {}", ldapProperties.getLdapUser(), boundSuccess);
	    }
	} catch (UnsupportedEncodingException e) {
	    throw new LDAPException("UTF-8 Invalid Encoding", LDAPException.LOCAL_ERROR, (String) null, e);
	}
	log.info("connectAndBind#Connect and Bind: {}", (connectSuccess && boundSuccess));
	if (connectSuccess && boundSuccess) {
	    return conn;
	}
	try {
	    conn.disconnect();
	} catch (Exception e) {
	    // non faccio niente
	}
	return null;
    }

    private String getUserDN(String userDN, String user) {

	log.debug("getUserDN# {} with user {} ", userDN, user);
	userDN = userDN.replace("#LOGIN#", user);
	userDN = userDN.replace("#PARAM#", user);
	return userDN;
    }

    private void svuotaDB() {

	// ciclo i record della tabella record e elimino 
	List<Rubrica> list = this.findAll(null, null);
	for (Rubrica rubrica : list) {
	    this.delete(rubrica);
	}
    }

    private LDAPConnection connectToLDAP(LDAPProperties props) {

	if (props.isSslRelax()) {
	    log.debug("connectToLDAP# props.isSslRelax(): {} ", props.isSslRelax());
	    TrustManager[] trustManagers = new TrustManager[] { new TrustAllX509TrustManager() };
	    SSLContext context;
	    try {
		context = SSLContext.getInstance("TLSv1.2");
		context.init(null, trustManagers, new SecureRandom());
		SSLSocketFactory sslSocketFactory = context.getSocketFactory();
		LDAPSocketFactory f = new LDAPJSSESecureSocketFactory(sslSocketFactory);
		return new LDAPConnection(f);
	    } catch (NoSuchAlgorithmException e) {
		log.error("Errore nella creazione del contesto SSL ", e);
	    } catch (KeyManagementException e) {
		log.error("Errore nella creazione della socket factory ", e);
	    }
	}
	return new LDAPConnection();
    }
}
