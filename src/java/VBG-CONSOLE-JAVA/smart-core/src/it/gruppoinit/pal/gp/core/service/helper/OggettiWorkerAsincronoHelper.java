package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.service.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class OggettiWorkerAsincronoHelper implements Runnable {

    public static Set<String> codiciOggettoDaElaborare = new HashSet<String>();
    private static final Logger log = LoggerFactory.getLogger(OggettiWorkerAsincronoHelper.class);
    private static final long TIME_IN_MILLIS = 300000;
    private TaskExecutor taskExecutor;
    // BEGIN variabili ORMHelper // USATE SOLO NEL METODO RUN
    private String idcomuneAlias;
    private String idcomune;
    private String software;
    private String token;
    private String hibernateSfKey;
    // END VARIABILI ORMHELPER
    private Integer codiceOggetto;
    private ApplicationContext applicationContext;

    public OggettiWorkerAsincronoHelper() {

	super();
    }

    public OggettiWorkerAsincronoHelper(String idComuneAlias, String idComune, String software, String token, String hibernateSfKey,
	    Integer codiceOggetto, ApplicationContext applicationContext) {

	this();
	this.idcomuneAlias = idComuneAlias;
	this.idcomune = idComune;
	this.software = software;
	this.token = token;
	this.hibernateSfKey = hibernateSfKey;
	this.codiceOggetto = codiceOggetto;
	this.applicationContext = applicationContext;
    }

    @Override
    protected void finalize() throws Throwable {

	super.finalize();
    }

    public synchronized void eseguiTask(String idcomunealias, String idcomune, String software, String token, String hibernateSfKey,
	    Integer codiceOggetto, ApplicationContext applicationContext) {

	//	try {
	//	    if (codiceOggetto != null) {
	//		String key = getKey(codiceOggetto, idcomunealias);
	//		boolean isInElaborazione = codiciOggettoDaElaborare.contains(key);
	//		if (!isInElaborazione) {
	//		    codiciOggettoDaElaborare.add(key);
	//		    taskExecutor.execute(new OggettiWorkerAsincronoHelper(idcomunealias, idcomune, software, token, hibernateSfKey, codiceOggetto,
	//			    applicationContext));
	//		}
	//	    }
	//	} catch (Exception e) {
	//	    log.error("eseguiTask# Errore nell' esecuzione dell'operazione automatica per l'oggetto", new Object[] { idcomune, codiceOggetto, e });
	//	} finally {
	//	}
    }

    private String getKey(Integer codiceOggetto, String idcomunealias) {

	return idcomunealias + "-" + codiceOggetto.intValue();
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {

	this.taskExecutor = taskExecutor;
    }

    @Override
    public void run() {

	if (codiceOggetto == null) {
	    return;
	}
	if (log.isDebugEnabled()) {
	    log.debug("run# eseguo le operazioni automatiche per l'oggetto [{}-{}]", idcomune, codiceOggetto);
	}
	if (log.isDebugEnabled()) {
	    log.debug("run# Setto le variabili ORMHelper idcomunealias: {}, idcomune: {}, software: {}", new Object[] { idcomuneAlias, idcomune,
		    software });
	}
	ORMHelper.setIdcomuneAlias(idcomuneAlias);
	ORMHelper.setIdcomune(idcomune);
	ORMHelper.setHibernateSFKey(hibernateSfKey);
	ORMHelper.setSoftware(software);
	ORMHelper.setToken(token);
	try {
	    if (log.isDebugEnabled()) {
		log.debug("run# due secondi e mezzo di attesa");
	    }
	    Thread.sleep(TIME_IN_MILLIS);
	    if (log.isDebugEnabled()) {
		log.debug("run# recupero i service. Setto l'autenticazione");
	    }
	    SecurityContext context = SecurityContextHolder.getContext();
	    Authentication authentication = context.getAuthentication();
	    if (log.isDebugEnabled()) {
		log.debug("run# recupero authentication");
	    }
	    if (authentication == null) {
		if (log.isDebugEnabled()) {
		    log.debug("run# recupero usersecurity service");
		}
		UserSecurityService ss = (UserSecurityService) applicationContext.getBean("userSecurityService", UserSecurityService.class);
		if (log.isDebugEnabled()) {
		    log.debug("run# recuperato");
		}
		try {
		    if (log.isDebugEnabled()) {
			log.debug("run# prima di caricare utente amministratore");
		    }
		    UserDetails ud = ss.loadAdministratorUser();
		    if (log.isDebugEnabled()) {
			log.debug("run# carico l'utente amministratore");
		    }
		    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities()));
		} catch (Exception e) {
		    log.error("Non è stato possibile recuperare l'utente autenticato a causa di: {}", e);
		    throw new SecurityException("Non è stato possibile recuperare l'utente autenticato a causa di: " + e.getMessage(), e);
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug("run# recupero oggetti service");
	    }
	    OggettiService os = (OggettiService) applicationContext.getBean("oggettiServiceImpl", OggettiService.class);
	    if (log.isDebugEnabled()) {
		log.debug("run# OggettiService recuperato {}. recupero l'input stream dal file o dal DB", (os == null ? false : true));
	    }
	    Oggetti o = os.findByIdLazy(new PkId(idcomune, codiceOggetto));
	    if (o != null) {
		InputStream is = os.getOggettoAsInputStream(codiceOggetto);
		if (log.isDebugEnabled()) {
		    log.debug("run# InpuntStream recuperato {}, calcolo MD5", (is == null ? Boolean.FALSE : Boolean.TRUE));
		}
		if (is == null) {
		    throw new RuntimeException("Non è stato possibile estrarre il contenuto del file");
		}
		String md5Val = DigestUtils.md5Hex(is);
		if (log.isDebugEnabled()) {
		    log.debug("run# md5 calcolato del file {}", md5Val);
		}
		if (StringUtils.isNotBlank(md5Val)) {
		    if (log.isDebugEnabled()) {
			log.debug("run# recupero OggettiMetadatiService");
		    }
		    OggettiMetadatiService omds = (OggettiMetadatiService) applicationContext.getBean("oggettiMetadatiServiceImpl",
			    OggettiMetadatiService.class);
		    if (log.isDebugEnabled()) {
			log.debug("run# recuperato OggettiMetadatiService: {}", (omds == null ? false : true));
		    }
		    List<MetadatiBean> metadati = new ArrayList<MetadatiBean>();
		    MetadatiBean md = new MetadatiBean();
		    md.setChiave(OggettiMetadatiService.MD5_SUM_MD);
		    md.setValore(md5Val);
		    metadati.add(md);
		    if (log.isDebugEnabled()) {
			log.debug("run# inserisco nei metadati");
		    }
		    if (omds != null) {
			omds.insertMetadatiPerOggetto(codiceOggetto, metadati, idcomune);
		    } else {
			log.warn("OggettiMetadati nullo");
		    }
		}
	    } else {
		log.error("run# codice oggetto non trovato {}", codiceOggetto);
	    }
	} catch (Exception e) {
	    log.error("run# Errore nel calcolo del checksum MD5 per l'oggetto {}, {}", codiceOggetto, e);
	    return;
	} finally {
	    codiciOggettoDaElaborare.remove(getKey(codiceOggetto, idcomuneAlias));
	    ORMHelper.destroyORMHelper();
	}
    }
}
