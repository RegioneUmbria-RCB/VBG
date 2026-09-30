package it.gruppoinit.pal.gp.core.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OperazioniPentaho;
import it.gruppoinit.pal.gp.core.dao.impl.SostituzioniValoriConQueryWork;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.PentahoJobResponse;
import it.gruppoinit.pal.gp.core.domain.helper.PentahoJobstatus;
import it.gruppoinit.pal.gp.core.exception.SecurityException;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class PentahoServiceImpl extends BaseServiceImpl<Esportazioni, PkId> implements PentahoService {

    private static final Logger log = LoggerFactory.getLogger(PentahoServiceImpl.class);
    private ContenttypesService contenttypesService;
    private PentahocfgService pentahocfgService;

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }

    @Autowired
    public void setPentahocfgService(PentahocfgService pentahocfgService) {

	this.pentahocfgService = pentahocfgService;
    }

    @Override
    public String createRequestParameter(Set<Parametriesportazione> parametriesportazione) throws SecurityException {

	if (parametriesportazione == null) {
	    return "";
	}
	StringBuilder parameter = new StringBuilder();
	for (Parametriesportazione parametroesportazione : parametriesportazione) {
	    if (!SostituzioniValoriConQueryWork.controllaSicurezzaQuery(parametroesportazione.getValue())) {
		log.error("L'utente ha impostato un parametro non valido {}-{}", parametroesportazione.getParametro(),
			parametroesportazione.getValue());
		throw new SecurityException("Parametro non valido");
	    }
	    parameter.append(parametroesportazione.getParametro()).append("=").append(StringUtils.defaultString(parametroesportazione.getValue()))
		    .append("&");
	}
	String parametri = parameter.toString();
	if (StringUtils.defaultString(parametri).endsWith("&")) {
	    parametri = parametri.substring(0, parametri.length() - 1);
	}
	return parametri;
    }

    @Override
    public String createUrlOperazionePentaho(OperazioniPentaho job, String trasformazione, String parametriEsportazione, String sessionId) {

	String SECURITY_PENTAHO_URL_CARTE = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.PENTAHO_URL_CARTE);
	StringBuffer urlPrefix = new StringBuffer();
	StringBuffer url = new StringBuffer();
	String result = "";
	switch (job) {
	case RUN_JOB:
	    urlPrefix = urlPrefix.append(SECURITY_PENTAHO_URL_CARTE).append(job.getValue());
	    url = url.append(trasformazione).append("&SESSIONID=").append(sessionId).append("&PENTAHO_EXP_PATH=");
	    String exp_path = "";
	    try {
		exp_path = URLEncoder.encode(WebConstants.getSecurityParamValue(SecurityParams.PENTAHO_EXP_PATH), "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		log.error(e.getMessage());
	    }
	    url = url.append(exp_path);
	    if (StringUtils.isNotBlank(parametriEsportazione)) {
		url = url.append("&").append(parametriEsportazione);
	    }
	    break;
	case STATUS_JOB:
	case STOP_JOB:
	case REMOVE_JOB:
	    //"jobStatus/?name={name_trasformazione}&id={id_trasformazione}&xml=y"
	    String jobUrl = job.getValue().replace("${name_trasformazione}", "").replace("${id_trasformazione}", sessionId);
	    urlPrefix = urlPrefix.append(SECURITY_PENTAHO_URL_CARTE).append(jobUrl);
	    break;
	default:
	    break;
	}
	result += urlPrefix.toString() + url;
	return result;
    }

    @Override
    public String callTrasformazione(String urlTrasformazione, Set<Parametriesportazione> parametriesportazione, String sessionId,
	    HttpServletResponse response) throws Exception {

	// estraggo dall'oggetto esportazioni il percorso della trasformazione e creo una query string della degli eventuali parametri	
	String parametriEsportazione = this.createRequestParameter(parametriesportazione);
	// Creo il link da chiamare
	String urlPentahoRunJob = this.createUrlOperazionePentaho(OperazioniPentaho.RUN_JOB, urlTrasformazione, parametriEsportazione, sessionId);
	log.debug("callTrasformazione# Effettuo chiamata http al link: {}", urlPentahoRunJob);
	PentahoJobstatus jobStatus = null;
	//	try {
	Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
	InputStream inputStream = null;
	if (pentahocfg != null && StringUtils.isNotBlank(pentahocfg.getUserid()) && StringUtils.isNotBlank(pentahocfg.getPassword())) {
	    inputStream = Utilities.callHttp(urlPentahoRunJob, true, pentahocfg.getUserid(), pentahocfg.getPassword(), response);
	} else {
	    log.error("callTrasformazione# Non è stato possibile chiamare la trasformazione, userid e/o password non configurate");
	    throw new RuntimeException("Non è stato possibile chiamare la trasformazione, userid e/o password non configurate");
	}
	// Trasformo l'imput stream in stringa (formato xml)
	String resultInvokeJob = Utilities.inputStreamToString(inputStream);
	// Faccio l'unMarshallString della  stringa nell'ogetto PentahoJobResponse
	PentahoJobResponse jobResponse = (PentahoJobResponse) Utilities.unMarshallString(resultInvokeJob, PentahoJobResponse.class);
	String uri = "";
	if (StringUtils.isNotBlank(jobResponse.getResult()) && jobResponse.getResult().equalsIgnoreCase("OK")) {
	    log.debug("callTrasformazione# Esportazione avviata correttamente");
	    // Creo la string per verificare lo stato della trasformazione
	    String urlPentahoStatusJob = this.createUrlOperazionePentaho(OperazioniPentaho.STATUS_JOB, null, null, jobResponse.getId());
	    InputStream _inputStream = Utilities.callHttp(urlPentahoStatusJob, true, pentahocfg.getUserid(), pentahocfg.getPassword(), response);
	    // Trasformo lo stream in string
	    String resultStatusJob = Utilities.inputStreamToString(_inputStream);
	    // // Faccio l'unMarshallString della  stringa nell'ogetto PentahoJobstatus
	    jobStatus = (PentahoJobstatus) Utilities.unMarshallString(resultStatusJob, PentahoJobstatus.class);
	    // Inizio il ciclo di verifica sullo stato della trasformazione, dovò avere il valore di status_desc == Finished
	    // Il controllo dello stato veràà fatto per 30 minuni , dopo verrà rilanciato l'errore
	    String _status = jobStatus.getStatusDesc();
	    long t_start = System.currentTimeMillis();
	    long t_intermedio = 0;
	    while (!_status.equalsIgnoreCase("Finished")) {
		_inputStream = Utilities.callHttp(urlPentahoStatusJob, true, pentahocfg.getUserid(), pentahocfg.getPassword(), response);
		resultStatusJob = Utilities.inputStreamToString(_inputStream);
		log.debug("callTrasformazione _status {},resultStatusJob:[{}]", _status, resultStatusJob);
		jobStatus = (PentahoJobstatus) Utilities.unMarshallString(resultStatusJob, PentahoJobstatus.class);
		_status = jobStatus.getStatusDesc();
		if (_status.equalsIgnoreCase("Finished (with errors)")) {
		    log.error("====================callTrasformazione Finished (with errors)============================");
		    log.error("Finished (with errors) su esportazione {}", urlPentahoRunJob);
		    String errorText = "";
		    if (jobStatus != null && jobStatus.getResult() != null) {
			errorText = jobStatus.getResult().getLogText();
		    }
		    log.error("errore pentaho {}", errorText);
		    log.error("====================callTrasformazione Finished (with errors)============================");
		    throw new RuntimeException("Trasformazione terminata con errore: <pre>" + errorText + "</pre>");
		}
		t_intermedio = System.currentTimeMillis();
		if (t_intermedio - t_start > 1800000) {
		    throw new RuntimeException("Il tempo dell'elaborazione ha superato i 30 min, la trasformazione è stata interrotta");
		}
		// Il controllo lo faccio ogni 3 secondi
		Thread.sleep(3000);
	    }
	    // Recupero dall' oggetto jobStatus il percorso del file
	    String pathFile = "";
	    String filename = "";
	    if (jobStatus == null || jobStatus.getResult() == null) {
		log.error("====================callTrasformazione============================");
		log.error("NPE su avvio esportazione {}", urlPentahoRunJob);
		log.error("NPE su avvio esportazione resultStatusJob: {}", resultStatusJob);
		log.error("NPE su avvio esportazione resultInvokeJob: {}", resultInvokeJob);
		log.error("====================callTrasformazione============================");
		throw new RuntimeException(
			"Si è verificato un problema nell'elaborazione della richiesta. Rilanciare nuovamente l'esportazione. Dettaglio dell'errore <pre>" +
					   resultStatusJob + "</pre>");
	    }
	    if (jobStatus.getResult().getResultFile() != null) {
		pathFile = jobStatus.getResult().getResultFile().getResultFile().getFile();
		filename = StringUtils.substringAfterLast(pathFile, "/");
	    } else {
		filename = sessionId + ".zip";
	    }
	    uri = WebConstants.getSecurityParamValue(SecurityParams.PENTAHO_EXP_PATH) + "/" + sessionId + "/" + filename;
	    log.debug("callTrasformazione#file path: {} ", uri);
	    File file = new File(uri);
	    if (!file.exists()) {
		log.error("callTrasformazione# File  = {} non trovato", uri);
		throw new RuntimeException("File  = " + uri + " non trovato");
	    }
	    byte[] responseByte = Utilities.getBytesFromFile(file);
	    if (StringUtils.isNotBlank(filename)) {
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("Content-transfer-encoding", "binary");
		String cType = contenttypesService.findMimeTypeByFileName(filename);
		response.setContentType(cType);
		//response.setContentLength(responseByte.length);
		ServletOutputStream out = response.getOutputStream();
		out.write(responseByte);
		out.flush();
	    } else {
		throw new RuntimeException("File senza nome.");
	    }
	} else {
	    // Si è verificato un errore da gestire 
	    //throw new RuntimeException(jobResponse.getMessage());
	    // TODO
	    this.throwValidationMessage(new InvalidValue(jobResponse.getMessage(), null, null, "", null));
	}
	//} 
	return uri;
    }

    @Override
    public void insert(Esportazioni entity) {

	throw new it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException("Metodo non implementato");
    }

    @Override
    public void update(Esportazioni entity) {

	throw new it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException("Metodo non implementato");
    }

    @Override
    public void delete(Esportazioni entity) {

	throw new it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException("Metodo non implementato");
    }

    @Override
    public List<Esportazioni> findAll(Integer firstResult, Integer maxResult) {

	throw new it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException("Metodo non implementato");
    }

    @Override
    public Esportazioni findById(PkId id) {

	throw new it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException("Metodo non implementato");
    }

    @Override
    protected Class<Esportazioni> getEntityClass() {

	throw new it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException("Metodo non implementato");
    }
}
