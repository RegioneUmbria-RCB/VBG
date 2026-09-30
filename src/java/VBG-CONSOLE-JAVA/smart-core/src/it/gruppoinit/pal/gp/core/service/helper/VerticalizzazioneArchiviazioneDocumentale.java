package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class VerticalizzazioneArchiviazioneDocumentale {

    private String dataFolderPath;
    private String service;
    private String endUserId;
    private String nome;
    private String feedbackEmail;
    private String[] fileExtensions;
    private String sgd;
    private String dataMaxCons;
    private Integer maxNumFiles;
    private Integer maxNumIstanzePerQuery;
    private Integer maxFileSize;
    private Integer maxFileNameLength;
    private Date dallaData;
    private Date allaData;
    private String[] documentoPrincipaleSearchString;
    private String caratteriNonAmmessi;

    public VerticalizzazioneArchiviazioneDocumentale(VerticalizzazioniService verticalizzazioniService) {

	if (!verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC, WebConstants.SOFTWARE_TT)) {
	    throw new RuntimeException("La verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC + " non è attiva.");
	}
	Map<String, String> vertParams = verticalizzazioniService
		.getVerticalizzazioniparametriMap(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC);
	dataFolderPath = vertParams.get("DATA_FOLDER_PATH");
	if (StringUtils.isBlank(dataFolderPath)) {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro DATA_FOLDER_PATH vuoto.");
	}
	service = vertParams.get("SERVICE");
	if (StringUtils.isBlank(service)) {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro SERVICE vuoto.");
	}
	endUserId = vertParams.get("END_USER_ID");
	nome = vertParams.get("NOME");
	feedbackEmail = vertParams.get("FEEDBACK_EMAIL");
	String _fileExtensions = vertParams.get("FILE_EXTENSIONS");
	if (StringUtils.isNotBlank(_fileExtensions)) {
	    fileExtensions = _fileExtensions.split(";");
	}
	sgd = vertParams.get("SGD");
	if (StringUtils.isBlank(sgd)) {
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC + ": parametro SGD vuoto.");
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
	    throw new RuntimeException("Verticalizzazione " + WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC
		    + ": parametro MAX_NUM_ISTANZE_PER_QUERY vuoto.");
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
    }

    public String getDataFolderPath() {

	return dataFolderPath;
    }

    public String getService() {

	return service;
    }

    public String getEndUserId() {

	return endUserId;
    }

    public String getNome() {

	return nome;
    }

    public String getFeedbackEmail() {

	return feedbackEmail;
    }

    public String[] getFileExtensions() {

	return fileExtensions;
    }

    public String getSgd() {

	return sgd;
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
}
