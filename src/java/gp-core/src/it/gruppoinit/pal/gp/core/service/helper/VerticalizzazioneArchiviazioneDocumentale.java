package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Contenttypes;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.impl.ArchiviazioniManagerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class VerticalizzazioneArchiviazioneDocumentale {

    private String dataFolderPath;
    //
    private String operation;
    private String bucket;
    private String policy;
    //
    private String nome;
    private String service;
    private String feedbackEmail;
    //
    private String[] fileExtensions;
    private List<String> fileExtensionsType = new ArrayList<String>();
    private String dataMaxCons;
    private Integer maxNumFiles;
    private Integer maxNumIstanzePerQuery;
    private Integer maxFileSize;
    private Integer maxFileNameLength;
    private Date dallaData;
    private Date allaData;
    private String[] documentoPrincipaleSearchString;
    private String caratteriNonAmmessi;
    // nuovi parametri per versione conservazione con servizi rest
    private String LEGALDOC_INDEX_LABEL;
    private String LEGALDOC_INDEX_DOCUMENT_CLASS;
    private String LEGALDOC_URL_REST;
    private String LEGALDOC_TRIGGER_PATH;
    private String TIPO_SERVIZIO;
    private String USER_NAME_REST_SERVICE;
    private String PSW_NAME_REST_SERVICE;
    private String ALGORITMO_ARCHIVIAZIONE;
    private String SOLO_FIRMATI;
    private List<ChiaveValoreBean<String, String>> fileExtensionsReplacement;
    private Map<String, String> prefissoNomePacchetto = new HashMap<String, String>();

    public VerticalizzazioneArchiviazioneDocumentale(VerticalizzazioniService verticalizzazioniService, SoftwareService softwareService,
	    ContenttypesService contenttypesService) {

	if (!verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC, WebConstants.SOFTWARE_TT)) {
	    throw new RuntimeException("La verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC + " non è attiva.");
	}
	Map<String, String> vertParams = verticalizzazioniService
		.getVerticalizzazioniparametriMap(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC);
	dataFolderPath = vertParams.get("DATA_FOLDER_PATH");
	if (StringUtils.isBlank(dataFolderPath)) {
	    //	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
	    //		    + ": parametro DATA_FOLDER_PATH vuoto.");
	}
	//
	//	String _TIPO_SERVIZIO = vertParams.get("TIPO_SERVIZIO");
	//TIPO_SERVIZIO = StringUtils.defaultIfEmpty(_TIPO_SERVIZIO, "WS");
	TIPO_SERVIZIO = "WS";
	//
	service = vertParams.get("SERVICE");
	if (StringUtils.isBlank(service)) {
	    if (!TIPO_SERVIZIO.equals("WS")) {
		throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
			+ ": parametro SERVICE vuoto.");
	    }
	}
	operation = vertParams.get("OPERATION");
	if (StringUtils.isBlank(operation)) {
	    if (!TIPO_SERVIZIO.equals("WS")) {
		throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
			+ ": parametro OPERATION vuoto.");
	    }
	}
	bucket = vertParams.get("BUCKET");
	if (StringUtils.isBlank(bucket)) {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC + ": parametro BUCKET vuoto.");
	}
	policy = vertParams.get("POLICY");
	if (StringUtils.isBlank(policy)) {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC + ": parametro POLICY vuoto.");
	}
	nome = vertParams.get("NOME");
	feedbackEmail = vertParams.get("FEEDBACK_EMAIL");
	String _fileExtensions = vertParams.get("FILE_EXTENSIONS");
	if (StringUtils.isBlank(_fileExtensions)) {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro FILE_EXTENSIONS vuoto.");
	}
	if (StringUtils.isNotBlank(_fileExtensions)) {
	    fileExtensions = _fileExtensions.split(";");
	}
	List<Contenttypes> list = contenttypesService.findAll(null, null);
	for (Contenttypes contenttypes : list) {
	    for (int i = 0; i < fileExtensions.length; i++) {
		String ultimaEstensione = StringUtils.substringAfterLast(fileExtensions[i], ".");
		if (contenttypes.getId().getCtExtension().contains(ultimaEstensione)) {
		    fileExtensionsType.add(contenttypes.getId().getCtMimetype());
		    break;
		}
	    }
	}
	dataMaxCons = vertParams.get("DATA_MAX_CONS");
	String _maxNumFiles = vertParams.get("MAX_NUM_FILES");
	if (StringUtils.isNotBlank(_maxNumFiles)) {
	    maxNumFiles = Integer.valueOf(_maxNumFiles.trim());
	} else {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro MAX_NUM_FILES vuoto.");
	}
	String _maxNumIstanzePerQuery = vertParams.get("MAX_NUM_ISTANZE_PER_QUERY");
	if (StringUtils.isNotBlank(_maxNumIstanzePerQuery)) {
	    maxNumIstanzePerQuery = Integer.valueOf(_maxNumIstanzePerQuery.trim());
	} else {
	    //	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
	    //		    + ": parametro MAX_NUM_ISTANZE_PER_QUERY vuoto.");
	}
	String _maxFileSize = vertParams.get("MAX_FILE_SIZE");
	if (StringUtils.isNotBlank(_maxFileSize)) {
	    maxFileSize = Integer.valueOf(_maxFileSize.trim());
	} else {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro MAX_FILE_SIZE vuoto.");
	}
	String _maxFileNameLength = vertParams.get("MAX_FILE_NAME_LENGTH");
	if (StringUtils.isNotBlank(_maxFileNameLength)) {
	    maxFileNameLength = Integer.valueOf(_maxFileNameLength.trim());
	} else {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro MAX_FILE_NAME_LENGTH vuoto.");
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String _dallaData = vertParams.get("DALLA_DATA");
	if (StringUtils.isNotBlank(_dallaData)) {
	    try {
		dallaData = sdf.parse(_dallaData.trim());
	    } catch (ParseException e) {
		throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
			+ ": parametro DALLA_DATA non valido.");
	    }
	} else {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro DALLA_DATA vuoto.");
	}
	String _allaData = vertParams.get("ALLA_DATA");
	if (StringUtils.isNotBlank(_allaData)) {
	    try {
		allaData = sdf.parse(_allaData.trim());
	    } catch (ParseException e) {
		throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
			+ ": parametro ALLA_DATA non valido.");
	    }
	}
	String _documentoPrincipaleSearchString = vertParams.get("DOC_PRINCIPALE_SEARCH_STRING");
	if (StringUtils.isNotBlank(_documentoPrincipaleSearchString)) {
	    documentoPrincipaleSearchString = _documentoPrincipaleSearchString.split(";");
	} else {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro DOC_PRINCIPALE_SEARCH_STRING vuoto.");
	}
	//
	caratteriNonAmmessi = vertParams.get("CARATTERI_NON_AMMESSI");
	try {
	    String _fileExtRep = vertParams.get("FILE_EXTENSIONS_REPLACEMENT");
	    if (StringUtils.isNotBlank(_fileExtRep)) {
		String[] _fileExtRepTokens = _fileExtRep.split(";");
		fileExtensionsReplacement = new ArrayList<ChiaveValoreBean<String, String>>();
		for (String _fileExtRepToken : _fileExtRepTokens) {
		    String[] token = _fileExtRepToken.split(",");
		    ChiaveValoreBean<String, String> cvb = new ChiaveValoreBean<String, String>();
		    cvb.setChiave(token[0]);
		    cvb.setValore(token[1]);
		    fileExtensionsReplacement.add(cvb);
		}
	    }
	} catch (Exception e) {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro FILE_EXTENSIONS_REPLACEMENT non corretto.");
	}
	List<Software> softwares = softwareService.findSoftwareAttivi(false);
	for (Software software : softwares) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC, "PREFIX_NAME_PACHETTO", software.getCodice());
	    if (vp != null && StringUtils.isNotBlank(vp.getValore())) {
		prefissoNomePacchetto.put(software.getCodice(), vp.getValore());
	    }
	}
	//
	String _LEGALDOC_INDEX_LABEL = vertParams.get("LEGALDOC_INDEX_LABEL");
	LEGALDOC_INDEX_LABEL = StringUtils.defaultIfEmpty(_LEGALDOC_INDEX_LABEL, "");
	//
	String _LEGALDOC_INDEX_DOCUMENT_CLASS = vertParams.get("LEGALDOC_INDEX_DOCUMENT_CLASS");
	LEGALDOC_INDEX_DOCUMENT_CLASS = StringUtils.defaultIfEmpty(_LEGALDOC_INDEX_DOCUMENT_CLASS, "");
	//
	String _LEGALDOC_URL_REST = vertParams.get("LEGALDOC_URL_REST");
	if (StringUtils.isNotBlank(_LEGALDOC_URL_REST)) {
	    LEGALDOC_URL_REST = _LEGALDOC_URL_REST.trim();
	}
	//
	String _USER_NAME_REST_SERVICE = vertParams.get("USER_NAME_REST_SERVICE");
	USER_NAME_REST_SERVICE = StringUtils.defaultIfEmpty(_USER_NAME_REST_SERVICE, "");
	//
	String _PSW_NAME_REST_SERVICE = vertParams.get("PSW_NAME_REST_SERVICE");
	PSW_NAME_REST_SERVICE = StringUtils.defaultIfEmpty(_PSW_NAME_REST_SERVICE, "");
	//
	String _LEGALDOC_TRIGGER_PATH = vertParams.get("LEGALDOC_TRIGGER_PATH");
	if (StringUtils.isNotBlank(_LEGALDOC_TRIGGER_PATH)) {
	    LEGALDOC_TRIGGER_PATH = _LEGALDOC_TRIGGER_PATH.trim();
	} else {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro LEGALDOC_TRIGGER_PATH vuoto.");
	}
	String _ALGORITMO_ARCHIVIAZIONE = vertParams.get(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC_ALGORITMO_ARCHIVIAZIONE);
	//ALGORITMO_ARCHIVIAZIONE = StringUtils.defaultIfEmpty(_ALGORITMO_ARCHIVIAZIONE, ArchiviazioniManagerImpl.ARCHIVIAZIONE_PER_OGGETTO);
	ALGORITMO_ARCHIVIAZIONE = ArchiviazioniManagerImpl.ARCHIVIAZIONE_PER_OGGETTO;
	String _SOLO_FIRMATI = vertParams.get(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC_SOLO_FIRMATI);
	SOLO_FIRMATI = StringUtils.defaultIfEmpty(_SOLO_FIRMATI, "");
    }

    public static void main(String[] args) {

	String e1 = ".pdf";
	String e2 = ".jpg.p7m";
	String e3 = ".pdf.p7m.tsd";
	List<String> es = new ArrayList<String>();
	es.add(e1);
	es.add(e2);
	es.add(e3);
	for (String string : es) {
	    System.out.println(StringUtils.substringAfterLast(string, "."));
	}
    }

    public String getDataFolderPath() {

	return dataFolderPath;
    }

    public String getService() {

	return service;
    }

    public String getNome() {

	return nome;
    }

    public String getOperation() {

	return operation;
    }

    public String getBucket() {

	return bucket;
    }

    public String getPolicy() {

	return policy;
    }

    public String getFeedbackEmail() {

	return feedbackEmail;
    }

    public String[] getFileExtensions() {

	return fileExtensions;
    }

    public String getDataMaxCons() {

	return dataMaxCons;
    }

    public Integer getMaxNumFiles() {

	return maxNumFiles;
    }

    public Integer getMaxFileSize() {

	return maxFileSize;
    }

    public Integer getMaxFileNameLength() {

	return maxFileNameLength;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public Integer getMaxNumIstanzePerQuery() {

	return maxNumIstanzePerQuery;
    }

    public String[] getDocumentoPrincipaleSearchString() {

	return documentoPrincipaleSearchString;
    }

    public String getCaratteriNonAmmessi() {

	return caratteriNonAmmessi;
    }

    public List<ChiaveValoreBean<String, String>> getFileExtensionsReplacement() {

	return fileExtensionsReplacement;
    }

    public Map<String, String> getPrefissoNomePacchetto() {

	return prefissoNomePacchetto;
    }

    public String getLEGALDOC_INDEX_LABEL() {

	return LEGALDOC_INDEX_LABEL;
    }

    public void setLEGALDOC_INDEX_LABEL(String lEGALDOC_INDEX_LABEL) {

	LEGALDOC_INDEX_LABEL = lEGALDOC_INDEX_LABEL;
    }

    public String getLEGALDOC_INDEX_DOCUMENT_CLASS() {

	return LEGALDOC_INDEX_DOCUMENT_CLASS;
    }

    public void setLEGALDOC_INDEX_DOCUMENT_CLASS(String lEGALDOC_INDEX_DOCUMENT_CLASS) {

	LEGALDOC_INDEX_DOCUMENT_CLASS = lEGALDOC_INDEX_DOCUMENT_CLASS;
    }

    public String getLEGALDOC_URL_REST() {

	return LEGALDOC_URL_REST;
    }

    public void setLEGALDOC_URL_REST(String lEGALDOC_URL_REST) {

	LEGALDOC_URL_REST = lEGALDOC_URL_REST;
    }

    public String getLEGALDOC_TRIGGER_PATH() {

	return LEGALDOC_TRIGGER_PATH;
    }

    public void setLEGALDOC_TRIGGER_PATH(String lEGALDOC_TRIGGER_PATH) {

	LEGALDOC_TRIGGER_PATH = lEGALDOC_TRIGGER_PATH;
    }

    public String getTIPO_SERVIZIO() {

	return TIPO_SERVIZIO;
    }

    public void setTIPO_SERVIZIO(String tIPO_SERVIZIO) {

	TIPO_SERVIZIO = tIPO_SERVIZIO;
    }

    public String getPSW_NAME_REST_SERVICE() {

	return PSW_NAME_REST_SERVICE;
    }

    public void setUSER_NAME_REST_SERVICE(String uSER_NAME_REST_SERVICE) {

	USER_NAME_REST_SERVICE = uSER_NAME_REST_SERVICE;
    }

    public String getUSER_NAME_REST_SERVICE() {

	return USER_NAME_REST_SERVICE;
    }

    public void setPSW_NAME_REST_SERVICE(String pSW_NAME_REST_SERVICE) {

	PSW_NAME_REST_SERVICE = pSW_NAME_REST_SERVICE;
    }

    public String getALGORITMO_ARCHIVIAZIONE() {

	return ALGORITMO_ARCHIVIAZIONE;
    }

    public String getSOLO_FIRMATI() {

	return SOLO_FIRMATI;
    }

    public void setSOLO_FIRMATI(String sOLO_FIRMATI) {

	SOLO_FIRMATI = sOLO_FIRMATI;
    }

    public List<String> getFileExtensionsType() {

	return fileExtensionsType;
    }
}
