package it.gruppoinit.pal.gp.core.features.rubrica.job;

import java.util.HashMap;
// TODO nome tutto minuscolo
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.rubrica.IRubricaService;
import it.gruppoinit.pal.gp.core.features.rubrica.LDAPProperties;
import it.gruppoinit.pal.gp.core.jobs.BaseJob;
import it.gruppoinit.pal.gp.core.utils.CryptoUtils;

public class PopolaRubricaJob extends BaseJob implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(PopolaRubricaJob.class);

    public enum PARAMS {

	LDAP_HOST("Hostname del server LDAP"),
	LDAP_PORT("Porta del server LDAP"),
	LDAP_USER_DN("LDAP_USER_DN"),
	LDAP_LOGIN_TYPE("Indica il tipo di login; può assumere i valori bind o search. bind: Effettua la login con l'utente specificato nel form; search: effettua la login con l'utente specificato nei parametri LDAP_USER e LDAP_PWD e poi una search dell'utente del form sulla lista utenti tornata ed effettua una seconda login con le credenziali del form"),
	LDAP_USER("Nome utente, da popolare in caso di LDAP_LOGIN_TYPE è impostato a 'search'."),
	LDAP_PWD("Password, da popolare in caso di LDAP_LOGIN_TYPE è impostato a 'search'."),
	LDAP_MEMBER_OF_ARRAY("Usato per specificare un filtro sui gruppi di appartenenza degli utenti. Es. si vogliono autenticare solo gli utinti dello sviluppo"),
	LDAP_CN("Nome dell'attributo CN da utilizzare per la login"),
	LDAP_UID("Nome dell'attributo contenente la userid. Se la loginType = search ed il server ldap è activeDirectory allora LDAP_UID=sAMAccountName"),
	LDAP_USER_ATTRS("Attributi che si vogliono recuperare dalla chiamata al metodo getUserAttributes(separati da ;)"),
	LDAP_SEARCH_BASE_DN("Path dove effettuare la search dell'utente (utilizzato per la login in modalita search)"),
	LDAP_SEARCH_FILTER("Filtri per la ricerca dei record"),
	LDAP_SSL_RELAX("Se bypassare il controllo ssl, default true (accettati true/false)");

	private String descrizione;

	PARAMS(String descrizione) {

	    this.descrizione = descrizione;
	}

	public String descrizione() {

	    return descrizione;
	}
    }

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("RecuperoMailFromLDAPJob: begin");
	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	ORMHelper.setIdcomuneAlias(idcomunealias);
	setORMHelper(idcomunealias, software);
	// 1. Svuotare il DB all'avvio
	IRubricaService rubricaService = (IRubricaService) ContextLoader.getCurrentWebApplicationContext().getBean("rubricaServiceImpl");
	// 2. Recuperare le mail dalla rubrica LDAP
	try {
	    rubricaService.updateAllineaDaLDAP(popolaLDAP(ctx));
	} catch (Exception e) {
	    log.error("{}", e.getMessage(), e);
	}
	log.info("RecuperoMailFromLDAPJob: end");
    }

    private LDAPProperties popolaLDAP(JobExecutionContext ctx) {

	String ldapHOST = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_HOST.name());
	String ldapCN = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_CN.name());
	String ldapLoginType = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_LOGIN_TYPE.name());
	String portS = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_PORT.name());
	int port = Integer.parseInt(portS);
	String password = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_PWD.name());
	password = new CryptoUtils().decrypt(CryptoUtils.DEFAULT_SECRET_KEY, password);
	String baseDn = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_SEARCH_BASE_DN.name());
	String ldapUId = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_UID.name());
	String ldapUSER = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_USER.name());
	String ldapUSERAttrs = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_USER_ATTRS.name());
	String ldapUSERDn = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_USER_DN.name());
	String ldapFilter = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_SEARCH_FILTER.name());
	String sslrelax = (String) ctx.getJobDetail().getJobDataMap().get(PARAMS.LDAP_SSL_RELAX.name());
	return new LDAPProperties(ldapCN, ldapHOST, ldapLoginType, port, password, baseDn, ldapUId, ldapUSER, ldapUSERAttrs, ldapUSERDn, ldapFilter,
		StringUtils.defaultIfEmpty(sslrelax, "true").equalsIgnoreCase("true"));
    }

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAMS.LDAP_HOST.name(), PARAMS.LDAP_HOST.descrizione());
	m.put(PARAMS.LDAP_PORT.name(), PARAMS.LDAP_PORT.descrizione());
	m.put(PARAMS.LDAP_USER_DN.name(), PARAMS.LDAP_USER_DN.descrizione());
	m.put(PARAMS.LDAP_LOGIN_TYPE.name(), PARAMS.LDAP_LOGIN_TYPE.descrizione());
	m.put(PARAMS.LDAP_USER.name(), PARAMS.LDAP_USER.descrizione());
	m.put(PARAMS.LDAP_PWD.name(), PARAMS.LDAP_PWD.descrizione());
	m.put(PARAMS.LDAP_CN.name(), PARAMS.LDAP_CN.descrizione());
	m.put(PARAMS.LDAP_UID.name(), PARAMS.LDAP_UID.descrizione());
	m.put(PARAMS.LDAP_USER_ATTRS.name(), PARAMS.LDAP_USER_ATTRS.descrizione());
	m.put(PARAMS.LDAP_SEARCH_BASE_DN.name(), PARAMS.LDAP_SEARCH_BASE_DN.descrizione());
	m.put(PARAMS.LDAP_SEARCH_FILTER.name(), PARAMS.LDAP_SEARCH_FILTER.descrizione);
	m.put(PARAMS.LDAP_SSL_RELAX.name(), PARAMS.LDAP_SSL_RELAX.descrizione);
	return m;
    }
}
