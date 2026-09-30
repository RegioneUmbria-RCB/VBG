package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.dao.OggettiDaCancellareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiDaCancellare;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.OperationContext;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class OggettiServiceImpl extends BaseServiceImpl<Oggetti, PkId> implements OggettiService {

    private static final String CMIS_WORKSPACE_FILE_IDENTIFIER = "workspace://";
    private static final String CMIS_DOCUMENT = "cmis:document";
    private static final String CMIS_FOLDER = "cmis:folder";
    private static final Logger log = LoggerFactory.getLogger(OggettiServiceImpl.class);
    private OggettiDAO oggettiDAO;
    private OggettiMetadatiService oggettiMetadatiService;
    private ContenttypesService contenttypesService;
    private VerticalizzazioniService verticalizzazioniService;
    private OggettiinfoService oggettiinfoService;
    private Map<String, Session> cmisSessionMap;
    private ResponsabiliService responsabiliService;
    private LetteretipoService letteretipoService;
    @Autowired
    private OggettiDaCancellareDAO oggettiDaCancellareDAO;

    @Autowired
    public void setOggettiDAO(OggettiDAO oggettiDAO) {

	this.oggettiDAO = oggettiDAO;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }

    @Autowired
    public void setOggettiinfoService(OggettiinfoService oggettiinfoService) {

	this.oggettiinfoService = oggettiinfoService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setLetteretipoService(LetteretipoService letteretipoService) {

	this.letteretipoService = letteretipoService;
    }

    @Override
    protected Class<Oggetti> getEntityClass() {

	return Oggetti.class;
    }

    @Override
    public void delete(Oggetti entity) {

	if (isUsatoInLibreria(entity) == false) {
	    try {
		log.error("###CANCELLAZIONE OGGETTO [{},{}]", entity.getId().getCodice(), entity.getId().getIdcomune());
		oggettiMetadatiService.deleteByOggetto(entity.getId().getCodice(), entity.getId().getIdcomune());
		oggettiDAO.delete(entity);
		oggettiDAO.flush();
		deleteFileExt(entity);
	    } catch (DataIntegrityViolationException e) {
		// si è verificato un errore in cancellazione
		// è possibile che l'oggetto sia usato da altre tabelle e non lo elimino
		log.warn("Non è stato possibile cancellare l'oggetto {} a causa di: {}", entity.getId(), e.getMostSpecificCause());
	    } catch (Exception e) {
		log.warn("Non è stato possibile cancellare l'oggetto {} a causa di: {}", entity.getId(), e);
	    }
	    log.error("###CANCELLAZIONE OGGETTO [{},{}] ESEGUITA", entity.getId().getCodice(), entity.getId().getIdcomune());
	}
    }

    @Override
    public void deleteAll(List<Integer> objectsIdsToDelete) {

	for (int i = 0; i < objectsIdsToDelete.size(); i++) {
	    Oggetti deleteMe = this.findByIdLazy(new PkId(objectsIdsToDelete.get(i)));
	    if (deleteMe != null) {
		this.delete(deleteMe);
	    }
	}
    }

    @Override
    public List<Oggetti> findAll(Integer firstResult, Integer maxResult) {

	return oggettiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Oggetti findById(PkId id) {

	Oggetti oggetto = oggettiDAO.findById(id);
	if (oggetto != null) {
	    byte[] contenutoFile = retrieveOggetto(oggetto);
	    oggetto.setOggetto(contenutoFile);
	}
	return oggetto;
    }

    @Override
    public String findNomeById(PkId id) {

	return oggettiDAO.findNomeById(id);
    }

    @Override
    public String findNomeByCodice(Integer intId) {

	return oggettiDAO.findNomeById(new PkId(intId));
    }

    @Override
    public String findNomeByCodice(String strId) {

	String nome = "";
	if (NumberUtils.isNumber(strId)) {
	    nome = findNomeByCodice(NumberUtils.toInt(strId));
	}
	return nome;
    }

    private Session getSession(boolean ignoraVerticalizzazioneAttiva) {

	log.debug("getSession: recupero la sessione CMIS");
	if (cmisSessionMap == null) {
	    cmisSessionMap = new HashMap<String, Session>();
	}
	if (cmisSessionMap.get(ORMHelper.getIdcomuneAlias()) == null) {
	    SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
	    Map<String, String> parameter = new HashMap<String, String>();
	    Verticalizzazioniparametri atom = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_ATOM_URL, ignoraVerticalizzazioneAttiva);
	    String atomPubUrl = atom.getValore();//"http://10.10.45.200:8080/alfresco/s/cmis";
	    Verticalizzazioniparametri vuser = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_USERNAME, ignoraVerticalizzazioneAttiva);
	    String user = vuser.getValore();// "admin";
	    Verticalizzazioniparametri vpwd = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS,
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_PASSWORD, ignoraVerticalizzazioneAttiva);
	    String password = vpwd.getValore(); // "admin";
	    parameter.put(SessionParameter.USER, user);
	    parameter.put(SessionParameter.PASSWORD, password);
	    parameter.put(SessionParameter.ATOMPUB_URL, atomPubUrl);
	    parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
	    parameter.put(SessionParameter.CONNECT_TIMEOUT, "3000");
	    parameter.put(SessionParameter.READ_TIMEOUT, "360000");
	    log.debug("getSession: Accessing ATOMPUB_URL: {} userid: {}", atomPubUrl, user);
	    // find all the repositories at this URL - there should only be one.
	    List<Repository> repositories = new ArrayList<Repository>();
	    repositories = sessionFactory.getRepositories(parameter);
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

    @Override
    public void insert(Oggetti entity) {

	if (validateEntity(entity)) {
	    entity.setNomefile(Utilities.correggiNomeFile(entity.getNomefile()));
	    byte[] content = entity.getOggetto();
	    int dimensioneFile = 0;
	    if (null != content) {
		dimensioneFile = content.length;
	    }
	    entity.setDimensioneFile(dimensioneFile);
	    if (entity.getId() != null) {
		// BOCCI 2013-02-21 problema in crea repliche e import pratiche
		if (entity.getId().getCodice() == null) {
		    entity.setId(null);
		}
	    }
	    oggettiDAO.insert(entity);
	    if (null != content) {
		insertFileExt(entity, content, false);
		oggettiDAO.update(entity);
	    }
	    gestisciUIDForInsert(entity);
	    childDataUpdate(entity, true, content);
	}
	// computeMd5CheckSum(entity.getId().getCodice());
    }

    /**
     * Gestisce in inserimento il metadato UID
     * 
     * @param entity
     */
    private void gestisciUIDForInsert(Oggetti entity) {

	List<MetadatiBean> mds = entity.getMetadatiTransient();
	boolean presente = false;
	if (entity.getMetadatiTransient() == null) {
	    mds = new ArrayList<MetadatiBean>();
	}
	for (MetadatiBean mdb : mds) {
	    if (mdb.getChiave() != null) {
		if (mdb.getChiave().equals(WebConstants.OGGETTI_FILE_UID)) {
		    if (StringUtils.isNotBlank(mdb.getValore())) {
			presente = true;
			break;
		    }
		}
	    }
	}
	if (!presente) {
	    MetadatiBean uid = new MetadatiBean();
	    uid.setChiave(WebConstants.OGGETTI_FILE_UID);
	    uid.setValore(UUID.randomUUID().toString());
	    mds.add(uid);
	    entity.setMetadatiTransient(mds);
	}
    }

    //    private void computeMd5CheckSum(Integer codiceOggetto) {
    //
    //	// oggettiWorkerAsincronoHelper.eseguiTask(ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken(),
    //	// 	ORMHelper.getHibernateSFKeyUrl(), codiceOggetto, context);
    //    }
    private void childDataUpdate(Oggetti entity, boolean isInsert, byte[] content) {

	if (entity.getMetadatiTransient() == null) {
	    entity.setMetadatiTransient(new ArrayList<MetadatiBean>());
	}
	MetadatiBean mdsize = new MetadatiBean();
	Integer dimensioneFile = entity.getDimensioneFile() == null ? 0 : entity.getDimensioneFile();
	mdsize.setChiave(OggettiMetadatiService.FILE_SIZE_MD);
	mdsize.setValore(String.valueOf(dimensioneFile));
	entity.getMetadatiTransient().add(mdsize);
	MetadatiBean mdctype = new MetadatiBean();
	mdctype.setChiave(OggettiMetadatiService.CONTENT_TYPE_MD);
	String contentType = contenttypesService.findMimeTypeByFileName(entity.getNomefile());
	if (StringUtils.isBlank(contentType)) {
	    contentType = "application/octect-stream";
	}
	mdctype.setValore(contentType);
	entity.getMetadatiTransient().add(mdctype);
	if (content != null) {
	    String metaVal = DigestUtils.sha1Hex(content);
	    MetadatiBean mdsha1 = new MetadatiBean();
	    mdsha1.setChiave(OggettiMetadatiService.SHA1_HASH_MD);
	    mdsha1.setValore(metaVal);
	    entity.getMetadatiTransient().add(mdsha1);
	}
	if (entity.getMetadatiTransient() != null && entity.getMetadatiTransient().size() > 0) {
	    if (log.isDebugEnabled()) {
		log.debug("childDataUpdate# cancello i metadati per l'oggetto {}", entity.getId());
	    }
	    oggettiMetadatiService.insertMetadatiPerOggetto(entity.getId().getCodice(), entity.getMetadatiTransient(), entity.getId().getIdcomune());
	}
    }

    @Override
    public void update(Oggetti entity) {

	if (validateEntity(entity)) {
	    entity.setNomefile(Utilities.correggiNomeFile(entity.getNomefile()));
	    byte[] content = entity.getOggetto();
	    int dimensioneFile = 0;
	    if (null != content) {
		dimensioneFile = content.length;
	    }
	    entity.setDimensioneFile(dimensioneFile);
	    if (null != content) {
		insertFileExt(entity, content, true);
	    }
	    oggettiDAO.update(entity);
	    childDataUpdate(entity, false, content);
	}
	// computeMd5CheckSum(entity.getId().getCodice());
    }

    //    /**
    //     * @see OggettiService#getBytes(Oggetti)
    //     */
    //    // @Override
    //    private byte[] getBytes(Oggetti entity) {
    //
    //	byte[] content = null;
    //	String path = getPrivateFileRepositoryPath();
    //	if (StringUtils.isNotBlank(path)) {
    //	    if (StringUtils.isNotBlank(entity.getPercorso())) {
    //		path += File.separatorChar + entity.getPercorso();
    //	    }
    //	    path += File.separator + entity.getNomefile();
    //	    try {
    //		content = getBytesFromFile(new File(path));
    //	    } catch (IOException e) {
    //		throw new RuntimeException(e.getMessage());
    //	    }
    //	} else {
    //	    content = entity.getContenutoBLOB();
    //	}
    //	return content;
    //    }
    /**
     * Il metodo torna il percorso recuperato dal parametro verticalizzazione <b>FILESYSTEM-->DIRECTORY_LOCALE</b> se
     * impostata altrimenti il valore del parametro <b>FILESYSTEM-->SHAREDPATH</b>
     * 
     * @return
     */
    private String getPrivateFileRepositoryPath(boolean ignoraVerticalizzazioneAttiva) {

	String path = null;
	Verticalizzazioniparametri sharedPath = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_SHAREDPATH, ignoraVerticalizzazioneAttiva);
	Verticalizzazioniparametri localDirectory = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_DIRECTORY_LOCALE, ignoraVerticalizzazioneAttiva);
	// se il tomcat che serve l'applicativo è installato su macchina linux il file deve 
	// essere letto da condivisione tramite SAMBA ed il parametro per leggere/scrivere il contenuto di un
	// file deve puntare al path locale es: /home/shares/public.
	if (localDirectory != null) {
	    path = localDirectory.getValore();
	}
	if (StringUtils.isBlank(path)) {
	    if (sharedPath != null) {
		path = sharedPath.getValore();
	    }
	}
	return path;
    }

    private void insertFileExt(Oggetti entity, byte[] content, boolean isUpdate) {

	if (isVerticalizzazioneCMIS()) {
	    if (!isUpdate) {
		String idDocumentoCMIS = creaDocumentoConCMIS(entity, content);
		entity.setPercorso(idDocumentoCMIS);
	    } else {
		if (StringUtils.isBlank(entity.getPercorso()) || !(entity.getPercorso().startsWith(CMIS_WORKSPACE_FILE_IDENTIFIER))) {
		    // non ancora salvato in CMIS perché su BLOB o FileSystem
		    String idDocumentoCMIS = creaDocumentoConCMIS(entity, content);
		    entity.setPercorso(idDocumentoCMIS);
		} else {
		    aggiornaDocumentoCMis(entity, content);
		}
	    }
	    entity.setNonUsareContenutoBLOB(null);
	    // oggettiDAO.update(entity);
	} else if (isVerticalizzazioneFileSystem()) {
	    if (isVerticalizzazioneFileSystemReadonly()) {
		entity.setNonUsareContenutoBLOB(content);
		// oggettiDAO.update(entity);
	    } else {
		String path = getPrivateFileRepositoryPath(false);
		if (StringUtils.isNotBlank(path)) {
		    entity.setNonUsareContenutoBLOB(null);
		    String filename = StringUtils.defaultIfEmpty(entity.getNomefile(), "");
		    if (isUpdate) {
			String codiceOggetto = String.valueOf(entity.getId().getCodice());
			if (!filename.startsWith(codiceOggetto + "-")) {
			    filename = entity.getId().getCodice() + "-" + entity.getNomefile();
			}
		    } else {
			filename = entity.getId().getCodice() + "-" + entity.getNomefile();
		    }
		    // BOCCI 2012-07-18
		    // Salvataggio file su filesystem: è successo che a cesena arrivino file da PEOPLE con nomi del tipo
		    // 188562-domandaCLNMRK77R09E256S-A999524-1906775/1.xml 
		    // Ad esempio Quando si va a salvare su filesystem la / è un carattere riservato e non può essere usato.
		    // la funzione clearFileName ripulisce la stringa che rappresenta il nome del file dai caratteri non ammessi
		    filename = clearFileName(filename);
		    entity.setNomefile(filename);
		    int codiceOggetto = entity.getId().getCodice() == null ? 0 : entity.getId().getCodice().intValue();
		    String todayStoreFolder = Utilities.buildPathFromCodiceOggetto(codiceOggetto);
		    if (StringUtils.isNotBlank(entity.getNomefile())) {
			storeFile(path, todayStoreFolder, filename, content);
		    }
		    entity.setPercorso(todayStoreFolder);
		    // oggettiDAO.update(entity);
		} else {
		    entity.setNonUsareContenutoBLOB(content);
		    // oggettiDAO.update(entity);
		}
	    }
	} else {
	    entity.setNonUsareContenutoBLOB(content);
	    // oggettiDAO.update(entity);
	}
    }

    private void aggiornaDocumentoCMis(Oggetti entity, byte[] content) {

	Session session = getSession(false);
	OperationContext operationContext = session.createOperationContext();
	operationContext.setCacheEnabled(false);
	log.debug("retrieveOggetto: recupero da CMIS l'oggetto [{}]", entity.getPercorso());
	try {
	    CmisObject obj = session.getObject(entity.getPercorso(), operationContext);
	    log.debug("retrieveOggetto: recuperato da CMIS l'oggetto [{}]", obj);
	    Document file = null;
	    if (obj != null) {
		log.debug("retrieveOggetto: casto l'oggetto a Document");
		file = (Document) obj;
		log.debug("retrieveOggetto: recupero lo stream");
		InputStream stream = new ByteArrayInputStream(content);
		String contentType = contenttypesService.findMimeTypeByFileName(entity.getNomefile());
		ContentStream contentStream = new ContentStreamImpl(entity.getNomefile(), BigInteger.valueOf(content.length), contentType, stream);
		file.setContentStream(contentStream, true);
		log.debug("retrieveOggetto: Converto l'inputStream in array di byte");
	    }
	} catch (Exception e) {
	    log.error("retrieveOggetto: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}", new Object[] {
		    entity.getNomefile(), entity.getPercorso(), e.getMessage() });
	    throw new RuntimeException("Non è stato possibile recuperare il file " + entity.getNomefile() + " con objId[" + entity.getPercorso()
		    + "] dal sistema CMIS a causa di " + e.getMessage(), e);
	}
    }

    private String creaDocumentoConCMIS(Oggetti entity, byte[] content) {

	Session session = getSession(false);
	Verticalizzazioniparametri rootFolder = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS_DOCUMENT_ROOT_FOLDER);
	String rootFolderId = rootFolder.getValore(); // "workspace://SpacesStore/765dfb20-1e32-4283-a702-80abc2a9500f";
	// String descrizioneCartella = software.getDescrizione();
	String descrizioneCartella[] = Utilities.foldersFromCodiceOggetto(entity.getId().getCodice());
	OperationContext operationContext = session.createOperationContext();
	operationContext.setCacheEnabled(false);
	Folder rootDocumenti = (Folder) session.getObject(rootFolderId, operationContext);
	// VERIFICA STRUTTURA CARTELLE BEGIN
	Folder cartella = null;
	String pathCartelle = "";
	for (int i = 0; i < descrizioneCartella.length; i++) {
	    pathCartelle += descrizioneCartella[i] + "/";
	}
	try {
	    cartella = (Folder) session.getObjectByPath(rootDocumenti.getPath() + "/" + pathCartelle, operationContext);
	} catch (Exception e) {
	    log.warn("La cartella {} non esiste", (rootDocumenti.getPath() + "/" + pathCartelle));
	}
	pathCartelle = "";
	if (cartella == null) {
	    for (int i = 0; i < descrizioneCartella.length; i++) {
		Folder cartellaPadre = (Folder) session.getObjectByPath(rootDocumenti.getPath() + "/" + pathCartelle, operationContext);
		pathCartelle += descrizioneCartella[i] + "/";
		try {
		    cartella = (Folder) session.getObjectByPath(rootDocumenti.getPath() + "/" + pathCartelle, operationContext);
		} catch (Exception e) {
		    cartella = null;
		    log.warn("La cartella {} non esiste", (rootDocumenti.getPath() + "/" + pathCartelle));
		}
		if (cartella == null) {
		    Map<String, String> newFolderProps = new HashMap<String, String>();
		    newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, CMIS_FOLDER);
		    newFolderProps.put(PropertyIds.NAME, descrizioneCartella[i]);
		    try {
			cartella = cartellaPadre.createFolder(newFolderProps);
		    } catch (Exception e) {
			log.warn("La cartella {} non esiste", (rootDocumenti.getPath() + "/" + pathCartelle));
		    }
		}
	    }
	}
	//VERIFICA STRUTTURA CARTELLE END
	String textFileName = entity.getNomefile();
	log.debug("creaDocumentoConCMIS: nomefile: {}", textFileName);
	String cType = contenttypesService.findMimeTypeByFileName(textFileName);
	ByteArrayInputStream input = new ByteArrayInputStream(content);
	ContentStream contentStream = session.getObjectFactory().createContentStream(textFileName, content.length, cType, input);
	// Create the Document Object
	Map<String, Object> properties = new HashMap<String, Object>();
	//	List<Object> aspects = doc.getProperty("cmis:secondaryObjectTypeIds").getValues();
	//	if (!aspects.contains("P:cm:geographic")) {
	//		aspects.add("P:cm:geographic");
	//		HashMap<String, Object> props = new HashMap<String, Object>();
	//		props.put("cmis:secondaryObjectTypeIds", aspects);
	//		doc.updateProperties(props);
	//		System.out.println("Added aspect");
	//	} else {
	//		System.out.println("Doc already had aspect");
	//	}
	// TODO ALFRESCO	properties.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT + ",P:init:documentoPratica");
	// TODO ALFRESCO	properties.put("init:sportelloPratica", ORMHelper.getSoftware());
	// TODO ALFRESCO	properties.put("init:comunePratica", ORMHelper.getIdcomuneAlias());
	properties.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT);
	String codiceOggetto = String.valueOf(entity.getId().getCodice());
	if (!textFileName.startsWith(codiceOggetto + "-")) {
	    textFileName = entity.getId().getCodice() + "-" + entity.getNomefile();
	}
	properties.put(PropertyIds.NAME, textFileName);
	try {
	    // verificare se documento è versionabile
	    Document id = cartella.createDocument(properties, contentStream, VersioningState.MINOR);
	    return id.getVersionSeriesId();
	} catch (CmisRuntimeException e) {
	    log.error("creaDocumentoConCMIS: {}", e.getErrorContent());
	    throw new RuntimeException("E' gia' presente un file con nome [" + textFileName + "]");
	} catch (Exception e) {
	    log.error("creaDocumentoConCMIS: {}", e.getMessage());
	    throw new RuntimeException(e);
	}
    }

    private boolean isVerticalizzazioneFileSystem() {

	return verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM);
    }

    private boolean isVerticalizzazioneFileSystemReadonly() {

	boolean result = false;
	Verticalizzazioniparametri params = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_READONLY);
	if (params != null) {
	    if (StringUtils.defaultIfEmpty(params.getValore(), "0").equalsIgnoreCase("1")) {
		result = true;
	    }
	}
	return result;
    }

    private boolean isVerticalizzazioneCMIS() {

	return verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS);
    }

    private String getSharedPath() {

	String path = null;
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_SHAREDPATH);
	if (verticalizzazioniparametri != null) {
	    path = verticalizzazioniparametri.getValore();
	}
	return path;
    }

    /**
     * metodo che restituisce un array di byte a partire da un oggetto File
     * 
     * @param file
     * @return
     * @throws IOException
     */
    private byte[] getBytesFromFile(File file) throws IOException {

	InputStream is = new FileInputStream(file);
	long length = file.length();
	if (length > Integer.MAX_VALUE) {
	    is.close();
	    throw new IOException("File troppo grande!");
	}
	byte[] bytes = new byte[(int) length];
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}
	if (offset < bytes.length) {
	    is.close();
	    throw new IOException("Errore durante la lettura del file: " + file.getName());
	}
	is.close();
	return bytes;
    }

    /**
     * Metodo per la cancellazione di un file da file system. Il metodo non cancella il file ma lo rinomina col vecchio
     * nomefile + System.cuurentTimeMillis()
     * 
     * @param path
     *            uri del repository
     * @param filename
     *            nome del file
     */
    private void deleteFile(String path, String filename) {

	// BOCCI(20111026) NON FA NIENTE (NE RIMOZIONE NE RINOMINA FILE) ALTRIMENTI DA ERRORE IN CANCELLAZIONE ISTANZA 
	// IN QUESTO CASO GLI OGGETTI RIMANGONO NEL FILESYSTEM ED È NECESSARIA UNA PROCEDURA CHE ELIMINA I FILE A POSTERIORI
	//	File file = new File(path + File.separator + filename);
	//	if (file.exists()) {
	//	    if (!file.renameTo(new File(path + File.separator + filename + ".DEL" + System.currentTimeMillis()))) {
	//		throw new RuntimeException("Errore durante la cancellazione del file");
	//	    }
	//	}
    }

    /**
     * metodo per la scrittura di un file su file system
     * 
     * @param path
     *            uri del repository
     * @param entity
     *            oggetto
     */
    private void storeFile(String path, String storeFolder, String nomefile, byte[] content) {

	String filePath = recuperaPercorso(path, storeFolder);
	File folders = new File(filePath);
	if (!folders.exists()) {
	    boolean cartellecreate = folders.mkdirs();
	    if (cartellecreate == false) {
		throw new RuntimeException("Non è stato possibile creare le cartelle per il file [" + filePath + "]");
	    }
	}
	File out = new File(filePath + nomefile);
	FileOutputStream fos = null;
	try {
	    fos = new FileOutputStream(out);
	    fos.write(content);
	} catch (IOException e) {
	    throw new RuntimeException("Errore nella scrittura del file [" + filePath + nomefile + "] causato da=" + e.getMessage());
	} finally {
	    if (fos != null) {
		try {
		    fos.close();
		} catch (IOException e) {
		    // log error
		}
	    }
	}
	Utilities.permessiFSLinux(out, 777);
    }

    /**
     * La funzione concatena path e storeFolder inserendo i corretti valori per File.Separator. <br/>
     * Se ad esempio path = c:\temp e storeFolder è 000\01\00 torna una stringa del tipo c:\tem\000\01\00\
     * 
     * @param path
     * @param storeFolder
     * @return
     */
    private String recuperaPercorso(String path, String storeFolder) {

	String filePath = "";
	if (StringUtils.isBlank(path)) {
	    path = File.separator;
	} else {
	    if (!path.endsWith("/") && !path.endsWith("\\")) {
		path = path + File.separator;
	    }
	}
	filePath = path;
	if (StringUtils.isNotBlank(storeFolder)) {
	    if (storeFolder.startsWith("\\")) {
		storeFolder = storeFolder.replaceFirst("\\\\", "");
	    }
	    if (storeFolder.startsWith("/")) {
		storeFolder = storeFolder.replaceFirst("/", "");
	    }
	    if (storeFolder.endsWith("\\")) {
		storeFolder = storeFolder.substring(0, storeFolder.length() - 1) + File.separator;
	    }
	    if (storeFolder.endsWith("/")) {
		storeFolder = storeFolder.substring(0, storeFolder.length() - 1) + File.separator;
	    }
	    if (!storeFolder.endsWith("/") && !storeFolder.endsWith("\\")) {
		storeFolder += File.separator;
	    }
	    filePath += storeFolder;
	}
	if (!filePath.endsWith("/") && !filePath.endsWith("\\")) {
	    filePath += File.separator;
	}
	filePath = Utilities.normalizzaPath(filePath);
	return filePath;
    }

    @Override
    public Oggetti bindDomainObject(Oggetti entity, Class<?> idClass, String idPath) {

	// BOCCI 2012-11-27 GESTIONE DEI METADATI IN CASO DI BINDDOMAINOBJECT
	List<MetadatiBean> md = null;
	if (entity != null) {
	    // PASSO EVENTUALI METADATI DALL'OGGETTO DA BINDARE IN UNA VARIABILE
	    md = entity.getMetadatiTransient();
	}
	// END
	Oggetti ogg = super.bindDomainObject(entity, idClass, idPath);
	// BOCCI 2012-11-27 GESTIONE DEI METADATI IN CASO DI BINDDOMAINOBJECT
	// RIPASSO EVENTUALI METADATI NELLA VARIABILE AGLI OGGETTI BINDATI
	// IN QUESTO MODO SE LE RULES PREVEDONO L'UPDATE O L'INSERT ALLORA VERRANNO SALVATI
	if (ogg != null) {
	    ogg.setMetadatiTransient(md);
	}
	if (entity != null) {
	    entity.setMetadatiTransient(md);
	}
	// BOCCI 2012-11-27 GESTIONE DEI METADATI IN CASO DI BINDDOMAINOBJECT
	return afterBindDomainObject(ogg, entity);
    }

    private Oggetti afterBindDomainObject(Oggetti oggFromBind, Oggetti entity) {

	OggettiBusinessRules rule = (OggettiBusinessRules) SigeproBusinessRules.getClassRules(OggettiBusinessRules.class);
	if (rule != null) {
	    if (oggFromBind != null) {
		// aggiorno i campi dell'oggetto recuperato da DB se specificato da configurazione
		if (rule.isUpdate()) {
		    boolean updateRecordLocal = false;
		    if (entity != null) {
			if (StringUtils.isNotBlank(entity.getNomefile())) {
			    oggFromBind.setNomefile(entity.getNomefile());
			    updateRecordLocal = true;
			}
			if (entity.getOggetto() != null && entity.getOggetto().length > 0) {
			    oggFromBind.setOggetto(entity.getOggetto());
			    updateRecordLocal = true;
			}
		    }
		    if (!updateRecordLocal) {
			log.warn("afterBindDomainObject: L'oggetto con id={} non può essere aggiornato.", oggFromBind.getId().getCodice());
		    }
		    if (updateRecordLocal) {
			this.update(oggFromBind);
		    }
		}
	    } else {
		// inserisco la entity su DB se specificato da configurazione
		if (rule.isInsert()) {
		    boolean insertRecordLocal = false;
		    if (entity != null) {
			if (StringUtils.isNotBlank(entity.getNomefile())) {
			    insertRecordLocal = true;
			} else {
			    log.warn("afterBindDomainObject: L'oggetto non ha il nomefile settato.");
			}
		    }
		    if (!insertRecordLocal) {
			log.warn("afterBindDomainObject: L'oggetto non può essere inserito.");
		    }
		    if (insertRecordLocal) {
			this.insert(entity);
			return entity;
		    }
		}
	    }
	    if (rule.isUpdate() || rule.isInsert()) {
		List<MetadatiBean> mds = null;
		Integer codiceOggetto = null;
		String idcomune = "";
		if (entity != null) {
		    if (entity.getId() != null) {
			idcomune = entity.getId().getIdcomune();
			codiceOggetto = entity.getId().getCodice();
		    }
		    mds = entity.getMetadatiTransient();
		}
		if (oggFromBind != null) {
		    if (oggFromBind.getId() != null) {
			idcomune = entity.getId().getIdcomune();
			codiceOggetto = oggFromBind.getId().getCodice();
		    }
		    if (mds == null) {
			mds = oggFromBind.getMetadatiTransient();
		    }
		}
		if (mds != null && mds.size() > 0) {
		    if (codiceOggetto != null) {
			oggettiMetadatiService.insertMetadatiPerOggetto(codiceOggetto, mds, idcomune);
		    }
		}
	    }
	}
	return oggFromBind;
    }

    // NON VA FATTA LA CUSTOM BIND DOMAIN OBJECT CON NOMEFILE PERCHÈ 
    // DA PRATICHE PROVENIENTI DALL'ESTERNO PRENDE ALLEGATI PROENIENTI DA ALTRE PRATICHE
    // vedi BUGZILLA BUG 154
    ///**
    // * Ricerca un oggetto per nomefile {@link Oggetti#getNomefile()}
    // */
    //    @Override
    //    protected Oggetti customBindDomainObject(Oggetti entity) {
    //
    //	if (entity == null) {
    //	    return null;
    //	}
    //	
    //	//	if (StringUtils.isNotBlank(entity.getNomefile())) {
    //	//	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
    //	//	    FilterRestriction filterRestriction = new FilterRestriction();
    //	//	    filterRestriction.addFilterField(FilterUtils.equals("nomefile", entity.getNomefile(), String.class));
    //	//	    filterTable.addRestriction(filterRestriction);
    //	//	    List<Oggetti> list = oggettiDAO.findByFilterTable(filterTable);
    //	//	    if (list.size() == 1) {
    //	//		return list.get(0);
    //	//	    }
    //	//	}
    //	return entity;
    //    }
    //    /**
    //     * Torna una stringa che rappresenta una cartella con le seguenti caratteristiche /YYYY/MM/DD/ Es.:
    //     * <b>/2011/01/24/</b>
    //     * 
    //     * @return
    //     */
    //    private String todayStoreFolder() {
    //
    //	Calendar c = Calendar.getInstance();
    //	String year = String.valueOf(c.get(Calendar.YEAR));
    //	String month = StringUtils.leftPad(String.valueOf((c.get(Calendar.MONTH) + 1)), 2, '0');
    //	String day = StringUtils.leftPad(String.valueOf((c.get(Calendar.DATE))), 2, '0');
    //	String result = File.separatorChar + year + File.separatorChar + month + File.separatorChar + day + File.separatorChar;
    //	return result;
    //    }
    @Override
    public String getSharedFileLink(String idcomune, Integer codiceOggetto) {

	Oggetti entity = findByIdLazy(new PkId(idcomune, codiceOggetto));
	if (entity == null) {
	    throw new IllegalArgumentException("Nessun oggetto trovato con codice [ " + idcomune + "," + codiceOggetto + "]");
	}
	if (!isVerticalizzazioneFileSystemReadonly()) {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceOggetto, Integer.class));
	    fr.addFilterField(FilterUtils.isNull("nonUsareContenutoBLOB"));
	    filterTable.addRestriction(fr);
	    int count = oggettiDAO.countRecord(filterTable);
	    if (count == 1) {// BOCCI 2012-11-09 NEL CASO CHE IL FILE SIA PRESENTE 
		// ANCHE SULLA COLONNA BLOB ALLORA QUESTA HA LA PRECEDENZA
		String sharedPath = getSharedPath();
		if (StringUtils.isBlank(sharedPath)) {
		    return null;
		}
		String percorso = StringUtils.defaultIfEmpty(entity.getPercorso(), "");
		if (isVerticalizzazioneCMIS() && StringUtils.defaultString(percorso).startsWith(CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		    return null;
		}
		String nomeFile = entity.getNomefile();
		String result = sharedPath + "\\" + percorso + "\\" + nomeFile;
		result = correggiLink(result);
		log.debug("getSharedFileLink: link al file [{}]", result);
		return result;
	    }
	}
	return null;
    }

    private boolean isContenutoCLOBNullo(String idcomune, Integer codiceOggetto) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceOggetto, Integer.class));
	fr.addFilterField(FilterUtils.isNull("nonUsareContenutoBLOB"));
	filterTable.addRestriction(fr);
	int count = oggettiDAO.countRecord(filterTable);
	return count > 0;
    }

    private String correggiLink(String result) {

	result = result.replace("/", "\\");
	String[] elementi = result.split("\\\\");
	String linkCorretto = "\\\\";
	for (String elemento : elementi) {
	    if (StringUtils.isNotBlank(elemento)) {
		if (!elemento.equalsIgnoreCase("/")) {
		    linkCorretto = linkCorretto.concat(elemento).concat("\\");
		}
	    }
	}
	if (linkCorretto.endsWith("\\")) {
	    linkCorretto = StringUtils.left(linkCorretto, linkCorretto.length() - 1);
	}
	return linkCorretto;
    }

    private void deleteFileExt(Oggetti entity) {

	if (isVerticalizzazioneFileSystem()) {
	    String path = getPrivateFileRepositoryPath(false);
	    String filename = entity.getNomefile();
	    String percorso = entity.getPercorso();
	    if (StringUtils.isNotBlank(path)) {
		if (filename != null && !filename.equals("")) {
		    deleteFile(path + percorso, filename);
		}
	    }
	} else if (isVerticalizzazioneCMIS()) {
	    // TODO CANCELLARE IL FILE SU ALFRESCO???
	    //	    Session session = getSession();
	    // Document documentoDaCancellare = (Document) session.getObject(entity.getPercorso());
	    //	    documentoDaCancellare.delete(true);
	    // TODO oppure aggiornare le proprietà
	}
    }

    private boolean isUsatoInLibreria(Oggetti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro oggetto non può essere nullo");
	}
	if (EntityUtils.getNestedProperty(entity, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro codiceoggetto non può essere nullo");
	}
	Oggettiinfo oggettoLibreria = oggettiinfoService.findById(new PkId(entity.getId().getCodice()));
	if (oggettoLibreria == null) {
	    return false;
	} else {
	    return true;
	}
    }

    /**
     * <pre>
     *      	- se clob!=nullo leggo sempre quello (ha precedenza su tutto)
     *      	- verticalizzazione CMIS ha precedenza su FileSystem
     *      	- se verticalizzazione CMIS allora controllo che il percorso inizi con CMIS_WORKSPACE_FILE_IDENTIFIER
     *      	  altrimenti potrebbe essere salvato su filesystem piuttosto che su CMIS.
     * </pre>
     */
    private byte[] retrieveOggetto(Oggetti entity) {

	byte[] content = null;
	if (isVerticalizzazioneFileSystem() || isVerticalizzazioneCMIS()) {
	    if (entity.getNonUsareContenutoBLOB() != null) {
		// nel caso di verticalizzazione FILESYSTEM e se l'oggetto BLOB sia valorizzato allora il contenuto del file
		// valido è quello della tabella
		log.warn("retrieveOggetto: L'oggetto [{}] è stato recuperato dalla collonna BLOB anche se impostato il percorso {}", new Object[] {
			entity.getId(), StringUtils.defaultIfEmpty(entity.getPercorso(), "") });
		return entity.getNonUsareContenutoBLOB();
	    }
	    if (StringUtils.defaultString(entity.getPercorso()).startsWith(CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		// get from cmis
		content = getCMISFile(entity.getNomefile(), entity.getPercorso());
	    } else {
		// get from fs
		String path = getPrivateFileRepositoryPath(false);
		if (StringUtils.isNotBlank(path)) {
		    if (StringUtils.isNotBlank(entity.getPercorso())) {
			path += File.separatorChar + entity.getPercorso();
		    }
		    path += File.separator;
		    String baseSearchFolder = Utilities.normalizzaPath(path);
		    path = baseSearchFolder + entity.getNomefile();
		    File f = new File(path);
		    if (f.isFile()) {
			try {
			    content = getBytesFromFile(f);
			} catch (IOException e) {
			    throw new RuntimeException(e.getMessage());
			}
		    } else {
			// PROBABILMENTE IL FILE CONTIENE CARATTERI STRANI PROVO A RECUPERARLO TRAMITE QUESTA FUNZIONE E SOLO IN SEGUITO RILANCIO L'ERRORE
			content = getContentFromFileWithNonAsciiChars(entity.getNomefile(), new File(baseSearchFolder));
		    }
		} else {
		    log.warn("retrieveOggetto: L'oggetto [{}] è stato recuperato dalla collonna BLOB (Il percorso non è impostato) ", entity.getId());
		    content = entity.getNonUsareContenutoBLOB();
		}
	    }
	} else {
	    content = entity.getNonUsareContenutoBLOB();
	}
	return content;
    }

    /**
     * la funzione cicla tutti i file di una directory di ricerca per vedere se esite un file con il nome ripulito di
     * caratteri non ASCII tornato dalla funzione Utilities.eliminaCaratteriNonAscii(nomeFile);
     * 
     * @param nomeFile
     * @param searchDir
     * @return
     */
    private byte[] getContentFromFileWithNonAsciiChars(String nomeFile, File searchDir) {

	if (searchDir != null && searchDir.isDirectory()) {
	    log.warn("cerco il file " + nomeFile + " nella directory: " + searchDir);
	    String fileSenzaCUTF8 = Utilities.correggiNomeFile(nomeFile);
	    log.warn("nome file file ripulito " + fileSenzaCUTF8);
	    if (!nomeFile.equalsIgnoreCase(fileSenzaCUTF8)) {
		File[] files = searchDir.listFiles();
		for (File file : files) {
		    if (!file.isDirectory()) {
			String name = file.getName();
			log.warn("\tvaluto il file " + name);
			name = Utilities.correggiNomeFile(name);
			log.warn("\tfile ripulito " + name);
			if (name.equalsIgnoreCase(fileSenzaCUTF8)) {
			    log.warn("E' stato trovato ed associato il seguente file " + name + " provo a ");
			    try {
				byte[] content = getBytesFromFile(file);
				return content;
			    } catch (IOException e) {
				log.error("Errore nel recupero del file da FileSystem. Non è stato possibile recuperare il file [" + nomeFile
					+ "] dal percorso [" + searchDir + "]" + e.getMessage() + ": {}", e);
				throw new RuntimeException("Errore nel recupero del file da FileSystem. Non è stato possibile recuperare il file ["
					+ nomeFile + "] dal percorso [" + searchDir + "]" + e.getMessage(), e);
			    }
			}
		    }
		}
	    }
	}
	throw new RuntimeException("Errore nel recupero del file da FileSystem. Non è stato possibile recuperare il file [" + nomeFile
		+ "] dal percorso [" + searchDir + "]");
    }

    @Override
    public Oggetti findByIdLazy(PkId id) {

	return oggettiDAO.findByIdLazy(id);
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.cmisSessionMap = new HashMap<String, Session>();
    }

    /**
     * la funzione ripulisce la stringa che rappresenta il nome del file dai caratteri \/":*?<>| (non permessi in
     * ambienti windows) sostituendoli con stringa vuota
     * 
     * @param fileName
     * @return
     */
    private String clearFileName(String fileName) {

	if (StringUtils.isNotBlank(fileName)) {
	    if (fileName.indexOf("\"") >= 0) {
		fileName = fileName.replaceAll("\"", "");
	    }
	    if (fileName.indexOf("\\") >= 0) {
		fileName = fileName.replaceAll("\\\\", "");
	    }
	    if (fileName.indexOf("/") >= 0) {
		fileName = fileName.replaceAll("/", "");
	    }
	    if (fileName.indexOf(":") >= 0) {
		fileName = fileName.replaceAll(":", "");
	    }
	    if (fileName.indexOf("*") >= 0) {
		fileName = fileName.replaceAll("\\*", "");
	    }
	    if (fileName.indexOf("?") >= 0) {
		fileName = fileName.replaceAll("\\?", "");
	    }
	    if (fileName.indexOf("<") >= 0) {
		fileName = fileName.replaceAll("<", "");
	    }
	    if (fileName.indexOf(">") >= 0) {
		fileName = fileName.replaceAll(">", "");
	    }
	    if (fileName.indexOf("|") >= 0) {
		fileName = fileName.replaceAll("\\|", "");
	    }
	}
	return fileName;
    }

    @Override
    public void updateFilesBloccaModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	if (codiceoggetto != null) {
	    if (codiceResponsabile != null) {
		for (Integer co : codiceoggetto) {
		    OggettiMetadatiId id = new OggettiMetadatiId(co, WebConstants.OGGETTI_FILE_LOCKED_BY);
		    OggettiMetadati om = oggettiMetadatiService.findById(id);
		    if (om != null) {
			String cr = StringUtils.defaultString(om.getValore()).trim();
			if (Utilities.isInteger(cr)) {
			    Integer codiceUtenteCheHaBloccato = Integer.parseInt(cr);
			    if (!codiceUtenteCheHaBloccato.equals(codiceResponsabile)) {
				Responsabili r = responsabiliService.findById(new PkId(codiceUtenteCheHaBloccato));
				throw new SecurityException("Il File " + getDescrizioneFile(co) + " e' gia' bloccato da " + r.getResponsabile());
			    }
			}
		    } else {
			om = new OggettiMetadati();
			om.setId(id);
			om.setValore(String.valueOf(codiceResponsabile));
			oggettiMetadatiService.insert(om);
		    }
		}
	    }
	}
    }

    @Override
    public void updateFilesRimuoviBloccoModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	if (codiceoggetto != null) {
	    if (codiceResponsabile != null) {
		for (Integer co : codiceoggetto) {
		    OggettiMetadatiId id = new OggettiMetadatiId(co, WebConstants.OGGETTI_FILE_LOCKED_BY);
		    OggettiMetadati om = oggettiMetadatiService.findById(id);
		    if (om != null) {
			String cr = StringUtils.defaultString(om.getValore()).trim();
			if (Utilities.isInteger(cr)) {
			    Integer codiceUtenteCheHaBloccato = Integer.parseInt(cr);
			    if (codiceUtenteCheHaBloccato.equals(codiceResponsabile)) {
				oggettiMetadatiService.delete(om);
			    } else {
				Responsabili r = responsabiliService.findById(new PkId(codiceUtenteCheHaBloccato));
				throw new SecurityException("Il File " + getDescrizioneFile(co) + " è bloccato da " + r.getResponsabile()
					+ " e può essere sbloccato solamente da lui.");
			    }
			}
		    }
		}
	    }
	}
    }

    private String getDescrizioneFile(Integer codiceOggetto) {

	String result = "";
	Oggetti o = this.findByIdLazy(new PkId(codiceOggetto));
	if (o != null) {
	    result = o.getNomefile() + " (" + codiceOggetto + ")";
	}
	return result;
    }

    /**
     * 
     * 
     * <pre>
     *      	- se clob!=nullo leggo sempre quello (ha precedenza su tutto)
     *      	- verticalizzazione CMIS ha precedenza su FileSystem
     *      	- se verticalizzazione CMIS allora controllo che il percorso inizi con CMIS_WORKSPACE_FILE_IDENTIFIER
     *      	  altrimenti potrebbe essere salvato su filesystem piuttosto che su CMIS.
     * </pre>
     */
    @Override
    public InputStream getOggettoAsInputStream(String idcomune, Integer codiceOggetto) {

	if (log.isDebugEnabled()) {
	    log.debug("getOggettoAsInputStream# entro nel metodo");
	}
	boolean readFromFS = false;
	if (isVerticalizzazioneFileSystem() || isVerticalizzazioneCMIS()) {
	    boolean isNullCLOB = isContenutoCLOBNullo(idcomune, codiceOggetto);
	    if (log.isDebugEnabled()) {
		log.debug("getOggettoAsInputStream# isNullCLOB={} ", isNullCLOB);
	    }
	    if (!isNullCLOB) {
		return oggettiDAO.getInputStreamFromBLOB(idcomune, codiceOggetto);
	    }
	    Oggetti oggettiLazy = this.findByIdLazy(new PkId(idcomune, codiceOggetto));
	    if (StringUtils.defaultString(oggettiLazy.getPercorso()).startsWith(CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		Session session = getSession(false);
		OperationContext operationContext = session.createOperationContext();
		operationContext.setCacheEnabled(false);
		log.debug("retrieveOggetto: recupero da CMIS l'oggetto [{}]", oggettiLazy.getPercorso());
		try {
		    CmisObject obj = session.getObject(oggettiLazy.getPercorso(), operationContext);
		    log.debug("retrieveOggetto: recuperato da CMIS l'oggetto [{}]", obj);
		    Document file = null;
		    if (obj != null) {
			log.debug("retrieveOggetto: casto l'oggetto a Document");
			file = (Document) obj;
			log.debug("retrieveOggetto: recupero lo stream");
			InputStream is = file.getContentStream().getStream();
			return is;
		    }
		    return null;
		} catch (Exception e) {
		    log.error("retrieveOggetto: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}",
			    new Object[] { oggettiLazy.getNomefile(), oggettiLazy.getPercorso(), e.getMessage() });
		    throw new RuntimeException("Non è stato possibile recuperare il file " + oggettiLazy.getNomefile() + " con objId["
			    + oggettiLazy.getPercorso() + "] dal sistema CMIS a causa di " + e.getMessage(), e);
		}
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("getOggettoAsInputStream# verticalizzazione filesystem attiva");
		}
		String path = getPrivateFileRepositoryPath(false);
		if (StringUtils.isNotBlank(path)) {
		    if (log.isDebugEnabled()) {
			log.debug("getOggettoAsInputStream# path valorizzato {}, verifico se ContenutoCLOB è nullo", path);
		    }
		    // verifico se blob non è nullo
		    if (isNullCLOB) {
			readFromFS = true;
		    }
		}
	    }
	}
	if (readFromFS) {
	    if (log.isDebugEnabled()) {
		log.debug("getOggettoAsInputStream# l'oggetto va preso da FileSytem");
	    }
	    Oggetti oggettiLazy = this.findByIdLazy(new PkId(idcomune, codiceOggetto));
	    try {
		return retrieveInputStreamFromFile(oggettiLazy, false);
	    } catch (FileNotFoundException e) {
		throw new RuntimeException("File non trovato", e);
	    }
	} else {
	    return oggettiDAO.getInputStreamFromBLOB(idcomune, codiceOggetto);
	}
    }

    /**
     * 
     * 
     * <pre>
     *      	- se clob!=nullo leggo sempre quello (ha precedenza su tutto)
     *      	- verticalizzazione CMIS ha precedenza su FileSystem
     *      	- se verticalizzazione CMIS allora controllo che il percorso inizi con CMIS_WORKSPACE_FILE_IDENTIFIER
     *      	  altrimenti potrebbe essere salvato su filesystem piuttosto che su CMIS.
     * </pre>
     */
    @Override
    public InputStream getOggettoAsInputStream(Integer codiceOggetto) {

	return getOggettoAsInputStream(ORMHelper.getIdcomune(), codiceOggetto);
    }

    private InputStream retrieveInputStreamFromFile(Oggetti oggettiLazy, boolean ignoraVerticalizzazioneAttiva) throws FileNotFoundException {

	if (log.isDebugEnabled()) {
	    log.debug("retrieveInputStreamFromFile# entro nel metodo");
	}
	String path = getPrivateFileRepositoryPath(ignoraVerticalizzazioneAttiva);
	if (StringUtils.isNotBlank(path)) {
	    if (StringUtils.isNotBlank(oggettiLazy.getPercorso())) {
		path += File.separatorChar + oggettiLazy.getPercorso();
	    }
	    path += File.separator;
	    String baseSearchFolder = Utilities.normalizzaPath(path);
	    path = baseSearchFolder + oggettiLazy.getNomefile();
	    File f = new File(path);
	    if (log.isDebugEnabled()) {
		log.debug("retrieveInputStreamFromFile# cerco di recuperare l'inputstream dal file {}", f);
	    }
	    InputStream result = new FileInputStream(f);
	    return result;
	}
	return null;
    }

    @Override
    public void evict(Oggetti entity) {

	oggettiDAO.evict(entity);
    }

    @Override
    public String insertOrGetUID(Integer codiceOggetto, String idcomune) {

	if (codiceOggetto == null) {
	    return "";
	}
	OggettiMetadati omd = oggettiMetadatiService.findById(new OggettiMetadatiId(idcomune, codiceOggetto, WebConstants.OGGETTI_FILE_UID));
	String valore = "";
	if (omd != null) {
	    valore = StringUtils.defaultString(omd.getValore());
	    if (StringUtils.isBlank(valore)) {
		valore = UUID.randomUUID().toString();
		//		omd.setValore(valore);
		//		oggettiMetadatiService.update(omd);
		//		oggettiDAO.flush();
		oggettiMetadatiService.updateInNewTransaction(codiceOggetto, WebConstants.OGGETTI_FILE_UID, valore, idcomune);
	    }
	} else {
	    //	    omd = new OggettiMetadati();
	    //	    omd.setId(new OggettiMetadatiId(codiceOggetto, WebConstants.OGGETTI_FILE_UID));
	    valore = UUID.randomUUID().toString();
	    //	    omd.setValore(valore);
	    //	    oggettiMetadatiService.insert(omd);
	    //	    oggettiDAO.flush();
	    oggettiMetadatiService.insertInNewTransaction(codiceOggetto, WebConstants.OGGETTI_FILE_UID, valore, idcomune);
	}
	return valore;
    }

    @Override
    public void updateSbloccaOggetto(OggettiMetadati oggettiMetadati) {

	oggettiMetadatiService.delete(oggettiMetadati);
    }

    @Override
    public String creaSingoloLinkAllegati(Integer codiceOggetto, String idcomune) {

	// Metodo esposto per recuperare il link
	String parametriUrl = "get.htm?m={md5_oggetto}&a=" + ORMHelper.getIdcomuneAlias();
	// Verifico che sia attiva la verticalizzazione
	if (!verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC)) {
	    log.error("creaSingoloLinkAllegati# La verticalizzazione : {}. Non è attiva ", WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC);
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    _ivs.add(new InvalidValue("service_error.verticalizzazione_allegati_pec_non_attiva", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	//Verifico la verticalizzazione contenete l'url del servizio per il recupero dei file fisici
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC, WebConstants.VERTICALIZZAZIONE_PARAMETRI_URL_SERVIZIO_RECUPERO_DOC);
	String urlPath = "";
	if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
	    urlPath = verticalizzazioniparametri.getValore();
	    log.debug("creaSingoloLinkAllegati# Url trovato : {}", urlPath);
	} else {
	    log.error(
		    "creaSingoloLinkAllegati# Controllare la configurazione della verticalizzazione : {}. Non è stato impostato il path del servizio di recupero file ",
		    WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC);
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    _ivs.add(new InvalidValue("service_error.configurazione_verticalizzazione_allegati_pec", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	String md5 = "";
	// Recupero se già presente MD5 del file
	List<OggettiMetadati> listaMetadati = oggettiMetadatiService.findByOggetto(codiceOggetto, OggettiMetadatiService.MD5_SUM_MD, idcomune);
	if (listaMetadati != null) {
	    log.debug("creaSingoloLinkAllegati# MD5 del file trovato");
	    md5 = listaMetadati.get(0).getValore();
	} else {
	    // Non era presente, lo creo
	    log.debug("creaSingoloLinkAllegati# MD5 del file non trovato, lo creo e lo inserisco");
	    md5 = oggettiMetadatiService.calcolaMd5(codiceOggetto);
	    List<MetadatiBean> metadati = new ArrayList<MetadatiBean>();
	    MetadatiBean md = new MetadatiBean();
	    md.setChiave(OggettiMetadatiService.MD5_SUM_MD);
	    md.setValore(md5);
	    metadati.add(md);
	    oggettiMetadatiService.insertMetadatiPerOggetto(codiceOggetto, metadati, idcomune);
	}
	// Creao il path : urlServizio+urlMethodEsposto
	urlPath += parametriUrl.replace("{md5_oggetto}", md5);
	//ret = "file: " + urlPath + " , PIN: " + codiceOggetto + " <br />";
	return urlPath;
    }

    @Override
    public Integer creaDocumentoConLinkOggetti(Letteretipo lettereTipo, Integer codiceIst, Integer codiceMov, String codiceTipoMov, String uuid) {

	Integer codiceLetteraTipo = lettereTipo.getId().getCodice();
	lettereTipo = letteretipoService.findById(new PkId(codiceLetteraTipo));
	Integer codiceOggetto = (Integer) EntityUtils.getNestedProperty(lettereTipo.getFile(), "id.codice");
	Integer codiceAllegatoCreato = null;
	if (codiceOggetto != null) {
	    // controllare se installazione STANDARD o ENTERPRISE e chiamare il servizio di generazione documenti automatica 
	    if (verticalizzazioniService.isInstallazioneEnterprise()) {
		String baseUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.BASE_URL);
		String urlStampaEnterprise = BackofficeNETConstants.getURL_STAMPE_MOVIMENTO_CREA_ALLEGATO_INTERNAL();
		if (StringUtils.isBlank(baseUrl)) {
		    // la base url non è settata cerco di recuperarla da wshosturlaspnet
		    log.error("Attenzione!!! Non è stato settato il parametro BASE_URL nell'applicativo di Security tento di recuperarlo da aspnetbaseurl");
		    baseUrl = getBaseUrlFromAspnetBaseURL();
		    log.error("Attenzione!!! la BASE_URL recuperata da aspnetbaseurl è {}", baseUrl);
		    urlStampaEnterprise = baseUrl + urlStampaEnterprise;
		}
		// 1. invocare con httpClient il servizio Enterprise asp 
		// 2. Attendere la la risposta della chiamata e controllare se su movimenti allegati sono presenti record 
		urlStampaEnterprise += "?codiceDocumento=" + codiceLetteraTipo + "&codiceIstanza=" + codiceIst + "&codiceMovimento=" + codiceMov
			+ "&TipoMovimento=" + codiceTipoMov + "&" + "ALLEGATI_UUID" + "=" + uuid + "&" + WebConstants.SOFTWARE + "="
			+ ORMHelper.getSoftware() + "&" + WebConstants.TOKEN + "=" + ORMHelper.getToken();
		String urlTo = urlStampaEnterprise;
		log.debug("Chiamo il servizio esterno di creazione allegato [{}]", urlStampaEnterprise.replaceAll(WebConstants.TOKEN, "t"));
		HttpClient cli = new HttpClient();
		HttpMethod method = new GetMethod(urlTo);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
		    log.debug("prima di eseguire la chiamata al servizio di creazione allegato");
		    int status = cli.executeMethod(method);
		    log.debug("la chiamata al servizio di creazione allegato ha tornato status {}", status);
		    if (status != 200) {
			log.error("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," + lettereTipo.getDescrizione()
				+ "] Status : " + status);
			throw new RuntimeException("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + ","
				+ lettereTipo.getDescrizione() + "] Status : " + status);
		    }
		    String codice = new String(method.getResponseBody());
		    codiceAllegatoCreato = Integer.valueOf(codice);
		} catch (Exception e) {
		    log.error("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," + lettereTipo.getDescrizione()
			    + "] ");
		    throw new RuntimeException("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + ","
			    + lettereTipo.getDescrizione() + "] ");
		}
	    }
	}
	return codiceAllegatoCreato;
    }

    private String getBaseUrlFromAspnetBaseURL() {

	String result = BackofficeNETConstants.getWsHostUrl();
	String aspNetApp = BackofficeNETConstants.getAPP_ASPNET();
	if (StringUtils.isNotBlank(result)) {
	    if (StringUtils.isNotBlank(aspNetApp)) {
		result = result.replaceAll("/" + aspNetApp, "/");
	    }
	    return result;
	}
	return "";
    }

    @Override
    public byte[] trasformRtfInPdf(Oggetti oggettoRtf) {

	byte[] b = oggettoRtf.getOggetto();
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), b, "RTF", "PDF");
	ConvertBinaryResponse resp = null;
	try {
	    resp = fileConverterWsClient.convertBinary(cbr);
	} catch (Exception e) {
	    log.error("Errore durante la conversione del file in PDF: {}, \n{}", e.getMessage(), e);
	    throw new RuntimeException("Errore durante la conversione del file in PDF: " + e.getMessage(), e);
	}
	return resp.getBinaryData();
    }

    @Override
    public void flush() {

	oggettiDAO.flush();
    }

    @Override
    public void segnaDaCancellare(Integer codiceOggettoDaCancellare) {

	OggettiDaCancellare entity = new OggettiDaCancellare();
	entity.setCodiceoggetto(codiceOggettoDaCancellare);
	oggettiDaCancellareDAO.insert(entity);
    }

    @Override
    public int spostaDaCMISaBLOB(int numFilesDaSpostare, boolean verificaNellaSottoTabella) {

	int numFiles = 0;
	// cerca numFileDaSpostare (colonnaBLOB nulla e percorso valorizzato che contiene workspace)
	List<Integer> oggetti = oggettiDAO.trovaOggettiCMIS(numFilesDaSpostare, verificaNellaSottoTabella);
	for (Integer codiceoggetto : oggetti) {
	    Oggetti o = this.findByIdLazy(new PkId(codiceoggetto));
	    // per ogni file lo recupera da CMIS
	    byte[] content = getCMISFile(o.getNomefile(), o.getPercorso());
	    if (content != null) {
		// salva su colonna CLOB e rimuove il riferimento ad Alfresco/FileSystem
		oggettiDAO.updateBLOB(codiceoggetto, content, true);
		numFiles++;
	    }
	}
	return numFiles;
    }

    public int spostaDaFileSystemABLOB(int numFilesDaSpostare, boolean verificaNellaSottoTabella) {

	int numFiles = 0;
	// cerca numFileDaSpostare (colonnaBLOB nulla e percorso valorizzato che contiene workspace)
	List<Integer> oggetti = oggettiDAO.trovaOggettiFilesystem(numFilesDaSpostare, verificaNellaSottoTabella);
	for (Integer codiceoggetto : oggetti) {
	    Oggetti o = this.findByIdLazy(new PkId(codiceoggetto));
	    // per ogni file lo recupera da CMIS
	    byte[] content = getFileSystemFileDaSpostare(o);
	    if (content != null) {
		// salva su colonna CLOB e rimuove il riferimento ad Alfresco/FileSystem
		oggettiDAO.updateBLOB(codiceoggetto, content, true);
		numFiles++;
	    }
	}
	return numFiles;
    }

    private byte[] getFileSystemFileDaSpostare(Oggetti oggettiLazy) {

	try {
	    InputStream is = retrieveInputStreamFromFile(oggettiLazy, true);
	    byte[] byteArray = IOUtils.toByteArray(is);
	    is.close();
	    return byteArray;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private byte[] getCMISFile(String nomeFile, String percorso) {

	byte[] content = null;
	Session session = getSession(true);
	OperationContext operationContext = session.createOperationContext();
	operationContext.setCacheEnabled(false);
	log.debug("retrieveOggetto: recupero da CMIS l'oggetto [{}]", percorso);
	try {
	    CmisObject obj = session.getObject(percorso, operationContext);
	    log.debug("retrieveOggetto: recuperato da CMIS l'oggetto [{}]", obj);
	    Document file = null;
	    if (obj != null) {
		log.debug("retrieveOggetto: casto l'oggetto a Document");
		file = (Document) obj;
		log.debug("retrieveOggetto: recupero lo stream");
		InputStream is = file.getContentStream().getStream();
		log.debug("retrieveOggetto: Converto l'inputStream in array di byte");
		content = IOUtils.toByteArray(is);
	    }
	} catch (IOException e) {
	    log.error("retrieveOggetto: Non è stato possibile recuperare lo stream di bytes del file {} dal sistema CMIS a causa di {}", nomeFile,
		    e.getMessage());
	    throw new RuntimeException("Non è stato possibile recuperare lo stream di bytes del file " + nomeFile + " dal sistema CMIS a causa di ",
		    e);
	} catch (Exception e) {
	    log.error("retrieveOggetto: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}", new Object[] {
		    nomeFile, percorso, e.getMessage() });
	    throw new RuntimeException("Non è stato possibile recuperare il file " + nomeFile + " con objId[" + percorso
		    + "] dal sistema CMIS a causa di " + e.getMessage(), e);
	}
	return content;
    }
}
