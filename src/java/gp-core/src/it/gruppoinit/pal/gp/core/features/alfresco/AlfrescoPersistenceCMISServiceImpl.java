package it.gruppoinit.pal.gp.core.features.alfresco;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Property;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class AlfrescoPersistenceCMISServiceImpl implements IAlfrescoPersistenceCMISService {

    private static final Logger log = LoggerFactory.getLogger(AlfrescoPersistenceCMISServiceImpl.class);
    private static final String CMIS_DOCUMENT = "cmis:document";
    private static final String CMIS_FOLDER = "cmis:folder";
    private Map<String, Session> cmisSessionMap;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private OggettiDAO oggettiDAO;

    @Override
    public String creaDocumento(Integer codiceOggetto, String nomeFile, byte[] content) {

	Session session = getSession();
	Verticalizzazioniparametri rootFolder = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_DOCUMENT_ROOT_FOLDER);
	Verticalizzazioniparametri tipoDocumento = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		"TIPO_DOCUMENTO_CMIS"/*WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_DOCUMENT_ROOT_FOLDER*/);
	String documentType = CMIS_DOCUMENT;
	if (tipoDocumento != null && StringUtils.isNotBlank(StringUtils.defaultString(tipoDocumento.getValore()).trim())) {
	    documentType = tipoDocumento.getValore().trim();
	}
	String rootFolderId = rootFolder.getValore();
	String[] descrizioneCartella = Utilities.foldersFromCodiceOggetto(codiceOggetto);
	OperationContext operationContext = getCMISOperationContext(session);
	Folder rootDocumenti = (Folder) session.getObject(rootFolderId, operationContext);
	// VERIFICA STRUTTURA CARTELLE BEGIN
	Folder cartella = null;
	StringBuilder pathCartelle = new StringBuilder();
	for (int i = 0; i < descrizioneCartella.length; i++) {
	    pathCartelle.append(descrizioneCartella[i]).append("/");
	}
	log.debug("cartella del file {}", pathCartelle);
	try {
	    cartella = (Folder) session.getObjectByPath(rootDocumenti.getPath() + "/" + pathCartelle, operationContext);
	} catch (Exception e) {
	    log.warn("creaDocumentoConCMIS 1) La cartella {} non esiste", (rootDocumenti.getPath() + "/" + pathCartelle));
	}
	pathCartelle = new StringBuilder();
	if (cartella == null) {
	    for (int i = 0; i < descrizioneCartella.length; i++) {
		Folder cartellaPadre = (Folder) session.getObjectByPath(rootDocumenti.getPath() + "/" + pathCartelle, operationContext);
		pathCartelle.append(descrizioneCartella[i]).append("/");
		try {
		    cartella = (Folder) session.getObjectByPath(rootDocumenti.getPath() + "/" + pathCartelle, operationContext);
		} catch (Exception e) {
		    cartella = null;
		    log.warn("creaDocumentoConCMIS 2) La cartella {} non esiste ", (rootDocumenti.getPath() + "/" + pathCartelle));
		}
		if (cartella == null) {
		    Map<String, String> newFolderProps = new HashMap<String, String>();
		    newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, CMIS_FOLDER);
		    newFolderProps.put(PropertyIds.NAME, descrizioneCartella[i]);
		    try {
			cartella = cartellaPadre.createFolder(newFolderProps);
		    } catch (Exception e) {
			log.warn("creaDocumentoConCMIS 3) La cartella {} non esiste ", (rootDocumenti.getPath() + "/" + pathCartelle));
		    }
		}
	    }
	}
	if (cartella == null) {
	    throw new InvalidConfigurationException("Non è stato possibile risalire alla cartella contenitore. Controllare la configurazione");
	}
	//VERIFICA STRUTTURA CARTELLE END
	String textFileName = nomeFile;
	String cType = "application/octet-stream";//contenttypesService.findMimeTypeByFileName(textFileName);
	ByteArrayInputStream input = new ByteArrayInputStream(content);
	ContentStream contentStream = session.getObjectFactory().createContentStream(textFileName, content.length, cType, input);
	// Create the Document Object
	Map<String, Object> properties = new HashMap<String, Object>();
	properties.put(PropertyIds.OBJECT_TYPE_ID, documentType);
	log.debug("creaDocumentoConCMIS: codiceoggetto={},nomefile={}", codiceOggetto, textFileName);
	if (!textFileName.startsWith(codiceOggetto + "-")) {
	    textFileName = codiceOggetto + "-" + nomeFile;
	}
	properties.put(PropertyIds.NAME, textFileName);
	try {
	    // verificare se documento è versionabile
	    Document id = cartella.createDocument(properties, contentStream, VersioningState.MINOR);
	    log.debug("creaDocumentoConCMIS: File creato: {}", id.getVersionSeriesId());
	    return id.getId();
	} catch (CmisRuntimeException e) {
	    log.error("creaDocumentoConCMIS: {}", e.getErrorContent(), e);
	    throw new RuntimeException("E' gia' presente un file con nome [" + textFileName + "]", e);
	} catch (Exception e) {
	    log.error("creaDocumentoConCMIS: {}", e.getMessage(), e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public String aggiornaDocumento(Integer codiceOggetto, String nomeFile, String percorso, byte[] content) {

	String nuovoId = null;
	String pathNodeID = percorso;
	if (!pathNodeID.startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
	    pathNodeID = IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + percorso;
	}
	Session session = getSession();
	OperationContext operationContext = getCMISOperationContext(session);
	log.debug("aggiornaDocumentoCMis: recupero da CMIS l'oggetto [{}]", pathNodeID);
	try {
	    CmisObject obj = session.getObject(pathNodeID, operationContext);
	    log.debug("aggiornaDocumentoCMis: recuperato da CMIS l'oggetto [{}]", obj);
	    Document file = null;
	    if (obj != null) {
		log.debug("aggiornaDocumentoCMis: casto l'oggetto a Document");
		file = (Document) obj;
		log.debug("aggiornaDocumentoCMis: recupero lo stream");
		InputStream stream = new ByteArrayInputStream(content);
		String contentType = "application/octet-stream"; //contenttypesService.findMimeTypeByFileName(nomeFile);
		ContentStream contentStream = new ContentStreamImpl(nomeFile, BigInteger.valueOf(content.length), contentType, stream);
		file.setContentStream(contentStream, true);
		if (Boolean.FALSE.equals(file.isLatestVersion())) {
		    file = file.getObjectOfLatestVersion(false);
		}
		nuovoId = file.getId();
		log.debug("aggiornaDocumentoCMis: Converto l'inputStream in array di byte");
	    }
	} catch (Exception e) {
	    log.error("aggiornaDocumentoCMis: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}",
		    new Object[] { nomeFile, pathNodeID, e.getMessage() });
	    throw new RuntimeException("Non è stato possibile recuperare il file " + nomeFile + " con objId[" + percorso +
				       "] dal sistema CMIS a causa di " + e.getMessage(),
		    e);
	}
	return nuovoId;
    }

    private Session getSession() {

	log.debug("getSession: recupero la sessione CMIS");
	if (cmisSessionMap == null) {
	    cmisSessionMap = new HashMap<String, Session>();
	}
	if (cmisSessionMap.get(ORMHelper.getIdcomuneAlias()) == null) {
	    SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
	    Map<String, String> parameter = new HashMap<String, String>();
	    Verticalizzazioniparametri atom = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_ATOM_URL);
	    String atomPubUrl = atom.getValore();
	    Verticalizzazioniparametri vuser = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_USERNAME);
	    String user = vuser.getValore();
	    Verticalizzazioniparametri vpwd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_PASSWORD);
	    String password = vpwd.getValore();
	    parameter.put(SessionParameter.USER, user);
	    parameter.put(SessionParameter.PASSWORD, password);
	    parameter.put(SessionParameter.ATOMPUB_URL, atomPubUrl);
	    parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
	    parameter.put(SessionParameter.CONNECT_TIMEOUT, "3000");
	    parameter.put(SessionParameter.READ_TIMEOUT, "360000");
	    log.debug("getSession: Accessing ATOMPUB_URL: {} userid: {}", atomPubUrl, user);
	    // find all the repositories at this URL - there should only be one.
	    List<Repository> repositories = sessionFactory.getRepositories(parameter);
	    if (log.isDebugEnabled()) {
		for (Repository r : repositories) {
		    log.debug("Trovato il repository: {}", r.getName());
		}
	    }
	    // create session with the first (and only) repository
	    log.debug("getSession: recupero il repository ");
	    Repository repository = repositories.get(0);
	    log.debug("getSession: repository recuperato con id: {}", repository.getId());
	    parameter.put(SessionParameter.REPOSITORY_ID, repository.getId());
	    log.debug("getSession: creo la sessione CMIS");
	    Session session = sessionFactory.createSession(parameter);
	    log.debug("getSession: Sessione CMIS creata");
	    cmisSessionMap.put(ORMHelper.getIdcomuneAlias(), session);
	}
	return cmisSessionMap.get(ORMHelper.getIdcomuneAlias());
    }

    private OperationContext getCMISOperationContext(Session session) {

	OperationContext operationContext = session.createOperationContext();
	operationContext.setCacheEnabled(false);
	operationContext.setIncludeAllowableActions(false);
	return operationContext;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.cmisSessionMap = new HashMap<String, Session>();
    }

    @Override
    public InputStream getContenuto(Integer codiceOggetto, String nomeFile, String percorso) {

	// get from cmis
	Session session = getSession();
	OperationContext operationContext = getCMISOperationContext(session);
	log.debug("retrieveOggetto: recupero da CMIS l'oggetto [{}]", percorso);
	try {
	    String pathNodeID = percorso;
	    if (!pathNodeID.startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		pathNodeID = IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + percorso;
	    }
	    CmisObject obj = session.getObject(pathNodeID, operationContext);
	    log.debug("retrieveOggetto: recuperato da CMIS l'oggetto [{}]", obj);
	    Document file = null;
	    if (obj != null) {
		log.debug("retrieveOggetto: casto l'oggetto a Document");
		file = (Document) obj;
		log.debug("retrieveOggetto: recupero lo stream");
		return file.getContentStream().getStream();
	    }
	} catch (Exception e) {
	    log.error("retrieveOggetto: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}",
		    new Object[] { nomeFile, percorso, e.getMessage() });
	    throw new RuntimeException("Non è stato possibile recuperare il file " + nomeFile + " con objId[" + percorso +
				       "] dal sistema CMIS a causa di " + e.getMessage(),
		    e);
	}
	throw new RuntimeException("Non è stato possibile recuperare il file " + nomeFile + " con objId[" + percorso + "]");
    }

    @Override
    public void aggiornaMetadati(Integer codiceOggetto, String nomeFile, String percorso, Map<String, CodiceDescrizioneBean> mdcmisss) {

	if (!mdcmisss.isEmpty()) {
	    Session session = getSession();
	    OperationContext operationContext = getCMISOperationContext(session);
	    log.debug("aggiornaMetadatiCMIS: recupero da CMIS l'oggetto [{}]", percorso);
	    try {
		String objectId = percorso;
		if (!objectId.startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		    objectId = IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + percorso;
		}
		CmisObject obj = session.getObject(objectId, operationContext);
		log.debug("aggiornaMetadatiCMIS: recuperato da CMIS l'oggetto [{}]", obj);
		Document file = null;
		if (obj != null) {
		    log.debug("aggiornaMetadatiCMIS: casto l'oggetto a Document");
		    file = (Document) obj;
		    log.debug("aggiornaMetadatiCMIS: recupero lo stream");
		    List<Property<?>> ps = file.getProperties();
		    Map<String, Object> properties = new HashMap<String, Object>();
		    for (Property<?> p : ps) {
			String idproprieta = p.getDefinition().getId();
			// String valore = p.getValuesAsString();
			if (p.getValues().isEmpty()) { // solo se valore è nullo
			    if (StringUtils.isNotBlank(idproprieta)) {
				if (mdcmisss.containsKey(idproprieta)) {
				    CodiceDescrizioneBean cdb = mdcmisss.get(idproprieta);
				    if (cdb != null) {
					String nuovoValore = cdb.getDescrizione();
					if (StringUtils.isNotBlank(nuovoValore)) {
					    Object o = convertValoreToType(nuovoValore, cdb.getCodice());
					    properties.put(idproprieta, o);
					}
				    }
				}
			    }
			}
		    }
		    if (properties.size() > 0) {
			log.debug("aggiornaMetadatiCMIS: aggiorno le seguenti proprietà per l'oggetto {}-{}", codiceOggetto, properties);
			if (!file.isLatestVersion()) {
			    file = file.getObjectOfLatestVersion(false);
			}
			ObjectId nuovoId = file.updateProperties(properties, true);
			log.debug("aggiornaMetadatiCMIS: nuovoId {}", nuovoId.getId());
		    }
		    if (!file.isLatestVersion()) {
			file = file.getObjectOfLatestVersion(false);
		    }
		    String preFixPercorso = file.getId();
		    if (!file.getId().startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
			preFixPercorso = IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + file.getId();
		    }
		    oggettiDAO.updatePercorsoOggetto(codiceOggetto, preFixPercorso);
		    oggettiDAO.flush();
		}
	    } catch (Exception e) {
		log.error("aggiornaMetadatiCMIS: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}",
			new Object[] { nomeFile, percorso, e.getMessage() });
		throw new RuntimeException("Non è stato possibile recuperare il file " + nomeFile + " con objId[" + percorso +
					   "] dal sistema CMIS a causa di " + e.getMessage(),
			e);
	    }
	} else {
	    Session session = getSession();
	    OperationContext operationContext = getCMISOperationContext(session);
	    log.debug("aggiornaMetadatiCMIS: recupero da CMIS l'oggetto [{}]", percorso);
	    try {
		String objectId = percorso;
		if (!objectId.startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		    objectId = IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + percorso;
		}
		CmisObject obj = session.getObject(objectId, operationContext);
		if (obj != null) {
		    log.debug("aggiornaMetadatiCMIS: casto l'oggetto a Document");
		    Document file = (Document) obj;
		    if (!file.isLatestVersion()) {
			file = file.getObjectOfLatestVersion(false);
		    }
		    String preFixPercorso = file.getId();
		    if (!file.getId().startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
			preFixPercorso = IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + file.getId();
		    }
		    oggettiDAO.updatePercorsoOggetto(codiceOggetto, preFixPercorso);
		    oggettiDAO.flush();
		}
	    } catch (Exception e) {
		log.error("aggiornaMetadatiCMIS: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}",
			new Object[] { nomeFile, percorso, e.getMessage() });
		throw new RuntimeException("Non è stato possibile recuperare il file " + nomeFile + " con objId[" + percorso +
					   "] dal sistema CMIS a causa di " + e.getMessage(),
			e);
	    }
	}
    }

    private Object convertValoreToType(String nuovoValore, String tipoConversione) {

	if (tipoConversione.equals("DATE")) {
	    GregorianCalendar d = Utilities.getDate(nuovoValore, WebConstants.DATE_FORMAT_PATTERN);
	    return d;
	}
	return nuovoValore;
    }

    private static String convertdateIsoFormat(String reciviedDate) throws ParseException {

	SimpleDateFormat in = new SimpleDateFormat("dd/mm/yyyy");
	Date dateRecivied = in.parse(reciviedDate);
	return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(dateRecivied);
    }
}
