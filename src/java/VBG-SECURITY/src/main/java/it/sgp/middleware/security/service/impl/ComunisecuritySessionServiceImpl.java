package it.sgp.middleware.security.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.configuration.SecurityConfiguration;
import it.sgp.middleware.security.dao.ComunisecuritySessionDAO;
import it.sgp.middleware.security.domain.ComuniSecuritySessMetadati;
import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.domain.ComunisecurityTpartnerapp;
import it.sgp.middleware.security.service.ComuniSecuritySessMetadatiService;
import it.sgp.middleware.security.service.ComunisecuritySessionService;
import it.sgp.middleware.security.service.ComunisecurityTpartnerappService;

/**
 * 
 * @author
 */
@Service
@Transactional
public class ComunisecuritySessionServiceImpl extends BaseServiceImpl<ComunisecuritySession, String> implements ComunisecuritySessionService {

    private static Logger log = LoggerFactory.getLogger(ComunisecuritySessionServiceImpl.class);
    private ComunisecuritySessionDAO comunisecuritysessionDAO;
    private ComunisecurityTpartnerappService comunisecurityTpartnerappService;
    private SecurityConfiguration securityConfiguration;
    private ComuniSecuritySessMetadatiService comuniSecuritySessMetadatiService;

    @Autowired
    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {

	this.securityConfiguration = securityConfiguration;
    }

    @Autowired
    public void setComunisecurityTpartnerappService(ComunisecurityTpartnerappService comunisecurityTpartnerappService) {

	this.comunisecurityTpartnerappService = comunisecurityTpartnerappService;
    }

    @Autowired
    public void setComunisecuritySessionDAO(ComunisecuritySessionDAO comunisecuritysessionDAO) {

	this.comunisecuritysessionDAO = comunisecuritysessionDAO;
    }
    
    @Autowired
    public void setComuniSecuritySessMetadatiService(ComuniSecuritySessMetadatiService comuniSecuritySessMetadatiService) {
		this.comuniSecuritySessMetadatiService = comuniSecuritySessMetadatiService;
	}

	@Override
    protected Class<ComunisecuritySession> getEntityClass() {

	return ComunisecuritySession.class;
    }

    @Override
    public List<ComunisecuritySession> findAll(Integer firstResult, Integer maxResult) {

	if (firstResult == null || maxResult == null) {
	    return comunisecuritysessionDAO.findAll();
	}
	Page<ComunisecuritySession> allRecords = comunisecuritysessionDAO
		.findAll(PageRequest.of(firstResult, maxResult).withSort(Sort.by("firstrequest")));
	return allRecords.hasContent() ? allRecords.getContent() : Collections.emptyList();
    }

    @Override
    public void insert(ComunisecuritySession entity) {

	if (validateEntity(entity)) {
	    comunisecuritysessionDAO.saveAndFlush(entity);
	}
    }

    @Override
    public ComunisecuritySession findById(String id) {

	return comunisecuritysessionDAO.findById(id).orElse(null);
    }

    @Override
    public void update(ComunisecuritySession entity) {

	if (validateEntity(entity)) {
	    comunisecuritysessionDAO.saveAndFlush(entity);
	}
    }

    @Override
    public void delete(ComunisecuritySession entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    comunisecuritysessionDAO.delete(entity);
	}
    }

    private void childDelete(ComunisecuritySession entity) {

	List<ComunisecurityTpartnerapp> l = comunisecurityTpartnerappService.findByToken(entity.getId());
	for (ComunisecurityTpartnerapp comunisecurityTpartnerapp : l) {
	    comunisecurityTpartnerappService.delete(comunisecurityTpartnerapp);
	}
	
	List<ComuniSecuritySessMetadati> l2 = comuniSecuritySessMetadatiService.findAllByToken(entity.getId());
	if (l2 != null && !l2.isEmpty()) {
		for (ComuniSecuritySessMetadati comuniSecuritySessMetadati : l2) {
			comuniSecuritySessMetadatiService.delete(comuniSecuritySessMetadati);
		}
	}
    }

    protected boolean isDeleteAllowed(ComunisecuritySession entity) {

	boolean delete = true;
	//	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	// esempio:
	//	// if (entity.getList().size() > 0) {
	//	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//	// }
	//	if (!_ivs.isEmpty()) {
	//	    this.throwValidationMessages(_ivs);
	//	}
	return delete;
    }

    @Override
    public List<ComunisecuritySession> findAll() {

	return this.findAll(null, null);
    }

    @Override
    public void deleteAll() {

	comunisecuritysessionDAO.deleteAll();
    }
    //    @Override
    //    public int countBeforeDate(Date date) {
    //
    //	return comunisecuritysessionDAO.countBeforeDate(date);
    //    }
    //    @Override
    //    public List<ComunisecuritySession> findBeforeDate(Date date, Integer firstResult, Integer maxResult) {
    //
    //	Calendar calendar = new GregorianCalendar();
    //	calendar.setTime(date);
    //	calendar.set(Calendar.HOUR, 0);
    //	calendar.set(Calendar.MINUTE, 0);
    //	calendar.set(Calendar.SECOND, 0);
    //	calendar.set(Calendar.MILLISECOND, 0);
    //	if (firstResult == null || maxResult == null) {
    //	    return comunisecuritysessionDAO.findByLastrequestLessThan(calendar.getTime());
    //	}
    //	return comunisecuritysessionDAO.findByLastrequestLessThan(calendar.getTime(), PageRequest.of(firstResult, maxResult));
    //    }

    public void writeComunisecuritySessionOnFile(StringBuffer testo) {

	log.debug("Recupero dalle properties il path della CATALINA_HOME {}", System.getProperty("catalina.base"));
	File file = getFileExportPath();
	if (log.isDebugEnabled()) {
	    log.debug("writeComunisecuritySessionOnFile# il file da salvare è {}", file.getAbsolutePath());
	}
	try {
	    log.debug("Apro il FileOutputStream");
	    FileOutputStream fileout = new FileOutputStream(file);
	    PrintStream output = new PrintStream(fileout);
	    log.debug("Scrivo la stringa sul file {}", file);
	    output.println(testo.toString());
	    log.debug("Chiudo Stream");
	    output.close();
	} catch (IOException e) {
	    log.error("Errore in scrittura del file: {}", e.getMessage());
	}
    }

    public void writeComunisecuritySessionOnFile(StringBuilder testo, PrintStream output) {

	log.debug("Scrivo la stringa sul file");
	output.print(testo.toString());
	log.debug("Chiuso Stream");
    }

    private File getFileExportPath() {

	File basePath = new File(securityConfiguration.getLogsFileDir());
	basePath.mkdirs();
	basePath = new File(basePath, "audit");
	basePath.mkdirs();
	// creo il nome del file da salvare secondo la struttura lista_token_2012_06_28.log
	String nameFile = "lista_token_" + it.sgp.middleware.security.utils.Utilities.formatDate(new Date()) + "." + System.currentTimeMillis() +
			  ".log";
	return new File(basePath, nameFile);
    }

    @Override
    public void deleteBeforeDate(Date date, boolean saveTextFile) {

	Calendar calendar = new GregorianCalendar();
	calendar.setTime(date);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MILLISECOND, 0);
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	StringBuilder testo = new StringBuilder();
	File f = null;
	FileOutputStream fileout = null;
	PrintStream output = null;
	if (saveTextFile) {
	    f = getFileExportPath();
	    log.debug("Apro il FileOutputStream {}", f);
	    try {
		fileout = new FileOutputStream(f, true);
		output = new PrintStream(fileout);
	    } catch (FileNotFoundException e) {
		log.debug("File nontrovato");
	    }
	}
	Window<ComunisecuritySession> cs = comunisecuritysessionDAO.findFirst1000ByLastrequestLessThan(calendar.getTime(), ScrollPosition.offset());
	int i = 0;
	do {
	    for (ComunisecuritySession comunisecuritySession : cs) {
		// consume the records
		testo.append(comunisecuritySession.getId()).append(",").append(comunisecuritySession.getUserid()).append(",")
			.append(comunisecuritySession.getContesto()).append(",").append(comunisecuritySession.getAlias()).append(",")
			.append(comunisecuritySession.getIdcomune()).append(",").append(comunisecuritySession.getClientIp()).append(",")
			.append(comunisecuritySession.getFirstrequest()).append(",").append(comunisecuritySession.getLastrequest()).append(",")
			.append(comunisecuritySession.getValid()).append("\n");
		// Cancello l'oggetto comunisecuritySession
		this.delete(comunisecuritySession);
		// comunisecuritysessionDAO.commit();
		comunisecuritysessionDAO.flush();
	    }
	    if (saveTextFile) {
		log.info("Inizio scrittura file con i token eliminati");
		this.writeComunisecuritySessionOnFile(testo, output);
		log.info("Terminata scrittura file con i token eliminati");
	    }
	    testo.delete(0, testo.length());
	    log.debug("Ciclo num. {} terminato", i++);
	    // obtain the next Scroll
	    // ScrollPosition positionAt = cs.positionAt(cs.size() - 1);
	    cs = comunisecuritysessionDAO.findFirst1000ByLastrequestLessThan(calendar.getTime(), ScrollPosition.offset());
	} while (!cs.isEmpty());
	if (saveTextFile) {
	    try {
		fileout.close();
	    } catch (IOException e) {
		log.debug("Non è stato possibile chiudere il file " + f, e);
	    }
	    output.flush();
	    output.close();
	    log.info("Fine scrittura del file {}", f);
	}
    }
    //    @Override
    //    public DetachedCriteria createDetachedCriteriaByEntity(ComunisecuritySession comunisecuritySession) {
    //
    //	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
    //	if (StringUtils.isNotBlank(comunisecuritySession.getId())) {
    //	    criteria.add(Restrictions.eq("id", comunisecuritySession.getId()));
    //	}
    //	if (StringUtils.isNotBlank(comunisecuritySession.getContesto())) {
    //	    criteria.add(Restrictions.eq("contesto", comunisecuritySession.getContesto()));
    //	}
    //	if (StringUtils.isNotBlank(comunisecuritySession.getClientIp())) {
    //	    criteria.add(Restrictions.eq("clientIp", comunisecuritySession.getClientIp()));
    //	}
    //	if (StringUtils.isNotBlank(comunisecuritySession.getAlias())) {
    //	    criteria.add(Restrictions.eq("alias", comunisecuritySession.getAlias()));
    //	}
    //	if (StringUtils.isNotBlank(comunisecuritySession.getIdcomune())) {
    //	    criteria.add(Restrictions.eq("idcomune", comunisecuritySession.getIdcomune()));
    //	}
    //	if (StringUtils.isNotBlank(comunisecuritySession.getUserid())) {
    //	    criteria.add(Restrictions.eq("userid", comunisecuritySession.getUserid()));
    //	}
    //	if (comunisecuritySession.getValid() != null) {
    //	    criteria.add(Restrictions.eq("valid", comunisecuritySession.getValid()));
    //	}
    //	if (comunisecuritySession.getFirstrequest() != null) {
    //	    criteria.add(Restrictions.eq("firstrequest", comunisecuritySession.getFirstrequest()));
    //	}
    //	if (comunisecuritySession.getLastrequest() != null) {
    //	    criteria.add(Restrictions.eq("lastrequest", comunisecuritySession.getLastrequest()));
    //	}
    //	return criteria;
    //    }
    //    @Override
    //    public int countRecord(DetachedCriteria criteria) {
    //
    //	return comunisecuritysessionDAO.countRecord(criteria);
    //    }
    //
    //    @Override
    //    public List<ComunisecuritySession> findComunisecuritySessionServiceByCriteria(DetachedCriteria criteria, Integer firstResult, Integer maxResult) {
    //
    //	return comunisecuritysessionDAO.findComunisecuritySessionServiceByCriteria(criteria, firstResult, maxResult);
    //    }

    @Override
    public void setTokenPartnerApp(String token, String tokenPartnerApp) {

	ComunisecuritySession session = this.findById(token);
	if (session != null) {
	    session.setTokenPartnerApp(tokenPartnerApp);
	    this.update(session);
	} else {
	    throw new RuntimeException("Token nullo o non valido");
	}
    }

    @Override
    public String getTokenPartnerApp(String token) {

	ComunisecuritySession session = this.findById(token);
	if (session != null) {
	    return session.getTokenPartnerApp();
	} else {
	    throw new RuntimeException("Token nullo o non valido");
	}
    }

    @Override
    public void setAuthLevel(String token, Integer authLevel) {

	ComunisecuritySession session = this.findById(token);
	if (session != null) {
	    session.setAuthLevel(authLevel);
	    this.update(session);
	} else {
	    throw new RuntimeException("Token nullo o non valido");
	}
    }

    @Override
    public Integer getAuthLevel(String token) {

	ComunisecuritySession session = this.findById(token);
	if (session != null) {
	    return session.getAuthLevel();
	} else {
	    throw new RuntimeException("Token nullo o non valido");
	}
    }

    @Override
    public Page<ComunisecuritySession> findAllByExamplePaginated(PageRequest pageable, Example<ComunisecuritySession> comunisecuritySession) {

	return comunisecuritysessionDAO.findAll(comunisecuritySession, pageable);
    }
}
