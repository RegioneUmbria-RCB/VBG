package it.gruppoinit.pal.gp.core.features.oggetti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CfgMetadatiCmisDAO;
import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CfgMetadatiCmis;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.OggettiStorico;
import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.alfresco.IAlfrescoPersistenceService;
import it.gruppoinit.pal.gp.core.features.alfresco.IAlfrescoPersistenceService.STRATEGY;
import it.gruppoinit.pal.gp.core.features.alfresco.config.IVerticalizzazioneNodoAlfrescoAPIService;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.IDocumentiCondivisiDAO;
import it.gruppoinit.pal.gp.core.features.firmadigitale.IVerticalizzazioneComportamentoComponenteFirmaService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneTipoInstallazioneService;
import it.gruppoinit.pal.gp.core.features.sistema.TecnologiaPaginaEnum;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.rest.client.DSSClientException;
import it.gruppoinit.pal.gp.core.rest.client.DSSRestClient;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.SignatureSummaryItem;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.ValidationResultDTO;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

@Service
public class OggettiServiceImpl extends BaseServiceImpl<Oggetti, PkId> implements OggettiService {

    private static final Logger log = LoggerFactory.getLogger(OggettiServiceImpl.class);
    private OggettiDAO oggettiDAO;
    private OggettiMetadatiService oggettiMetadatiService;
    private ContenttypesService contenttypesService;
    private VerticalizzazioniService verticalizzazioniService;
    private IVerticalizzazioneTipoInstallazioneService verticalizzazioneTipoInstallazioneService;
    private IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService;
    private OggettiinfoService oggettiinfoService;
    private ResponsabiliService responsabiliService;
    private LetteretipoService letteretipoService;
    private CfgMetadatiCmisDAO cfgMetadatiCmisDAO;
    private OggettiStoricoService oggettiStoricoService;
    private DocumentMergeService documentMergeService;
    private MovimentiallegatiService movimentiallegatiService;
    private MovimentiService movimentiService;
    private IDocumentiCondivisiDAO iDocumentiCondivisiDAO;
    private IVerticalizzazioneComportamentoComponenteFirmaService verticalizzazioneComportamentoComponenteFirmaService;
    private IVerticalizzazioneNodoAlfrescoAPIService verticalizzazioneNodoAlfrescoAPIService;
    private IAlfrescoPersistenceService alfrescoPersistenceService;

    @Autowired
    public void setAlfrescoPersistenceService(IAlfrescoPersistenceService alfrescoPersistenceService) {

	this.alfrescoPersistenceService = alfrescoPersistenceService;
    }

    @Autowired
    public void setVerticalizzazioneNodoAlfrescoAPIService(IVerticalizzazioneNodoAlfrescoAPIService verticalizzazioneNodoAlfrescoAPIService) {

	this.verticalizzazioneNodoAlfrescoAPIService = verticalizzazioneNodoAlfrescoAPIService;
    }

    @Autowired
    public void setVerticalizzazioneComportamentoComponenteFirmaService(
	    IVerticalizzazioneComportamentoComponenteFirmaService verticalizzazioneComportamentoComponenteFirmaService) {

	this.verticalizzazioneComportamentoComponenteFirmaService = verticalizzazioneComportamentoComponenteFirmaService;
    }

    @Autowired
    public void setiDocumentiCondivisiDAO(IDocumentiCondivisiDAO iDocumentiCondivisiDAO) {

	this.iDocumentiCondivisiDAO = iDocumentiCondivisiDAO;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setDocumentMergeService(DocumentMergeService documentMergeService) {

	this.documentMergeService = documentMergeService;
    }

    @Autowired
    public void setCfgMetadatiCmisDAO(CfgMetadatiCmisDAO cfgMetadatiCmisDAO) {

	this.cfgMetadatiCmisDAO = cfgMetadatiCmisDAO;
    }

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
    public void setVerticalizzazioneParametriSistemaService(IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService) {

	this.verticalizzazioneParametriSistemaService = verticalizzazioneParametriSistemaService;
    }

    @Autowired
    public void setVerticalizzazioneTipoInstallazioneService(IVerticalizzazioneTipoInstallazioneService verticalizzazioneTipoInstallazioneService) {

	this.verticalizzazioneTipoInstallazioneService = verticalizzazioneTipoInstallazioneService;
    }

    @Autowired
    public void setLetteretipoService(LetteretipoService letteretipoService) {

	this.letteretipoService = letteretipoService;
    }

    @Autowired
    public void setOggettiStoricoService(OggettiStoricoService oggettiStoricoService) {

	this.oggettiStoricoService = oggettiStoricoService;
    }

    @Override
    protected Class<Oggetti> getEntityClass() {

	return Oggetti.class;
    }

    @Override
    public void delete(Oggetti entity) {

	if (!isUsatoInLibreria(entity)) {
	    try {
		//elimino gli oggetti dalla tabella DocumentiCondivisi
		iDocumentiCondivisiDAO.deleteByCodiceOggetto(entity.getId().getCodice());
		// Controllo se è usato in Oggetto_storico, recupero tutti i record in cui è presente come campo OGGETTI_STORICO.CODICEOGGETTO
		// elimino tutti i record e l'oggetto del recordo referenziato nel campo OGGETTI_STORICO.CODICEOGGETTO_SOSTIT
		List<OggettiStorico> list = oggettiStoricoService.findbyCodiceOggetto(entity.getId().getCodice());
		for (OggettiStorico oggettiStorico : list) {
		    Oggetti oggettoSostituito = this.findById(new PkId(oggettiStorico.getOggettoVecchio().getId().getCodice()));
		    oggettiStoricoService.delete(oggettiStorico);
		    this.delete(oggettoSostituito);
		}
		oggettiMetadatiService.deleteByOggetto(entity.getId().getCodice());
		oggettiDAO.delete(entity);
		oggettiDAO.flush();
		// BOCCI(20111026) NON FA NIENTE (NE RIMOZIONE NE RINOMINA FILE) ALTRIMENTI DA ERRORE IN CANCELLAZIONE ISTANZA 
		// IN QUESTO CASO GLI OGGETTI RIMANGONO NEL FILESYSTEM ED È NECESSARIA UNA PROCEDURA CHE ELIMINA I FILE A POSTERIORI
	    } catch (DataIntegrityViolationException e) {
		// si è verificato un errore in cancellazione
		// è possibile che l'oggetto sia usato da altre tabelle e non lo elimino
		log.warn("Non è stato possibile cancellare l'oggetto {} a causa di: {}", entity.getId(), e.getMostSpecificCause());
	    } catch (Exception e) {
		log.warn("Non è stato possibile cancellare l'oggetto {} a causa di: {}", entity.getId(), e);
	    }
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

    @Override
    public void insert(Oggetti entity) {

	if (validateEntity(entity)) {
	    entity.setNomefile(Utilities.eliminaCaratteriNonAscii(entity.getNomefile()));
	    byte[] content = entity.getOggetto();
	    int dimensioneFile = 0;
	    if (null != content) {
		dimensioneFile = content.length;
	    }
	    entity.setDimensioneFile(dimensioneFile);
	    if (entity.getId() != null && entity.getId().getCodice() == null) {
		// BOCCI 2013-02-21 problema in crea repliche e import pratiche
		entity.setId(null);
	    }
	    oggettiDAO.insert(entity);
	    if (null != content) {
		insertFileExt(entity, content, false);
		oggettiDAO.update(entity);
	    }
	    gestisciUIDForInsert(entity);
	    childDataUpdate(entity, content);
	    try {
		processaMetadatiFirmaOggetto(entity);
	    } catch (DSSClientException e) { // non deve fare rollback, operazione non bloccante
		log.warn("processaMetadatiFirmaOggetto: " + entity.getId(), e);
	    }
	}
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
	    if (mdb.getChiave() != null && mdb.getChiave().equals(WebConstants.OGGETTI_FILE_UID) && StringUtils.isNotBlank(mdb.getValore())) {
		presente = true;
		break;
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

    private void childDataUpdate(Oggetti entity, byte[] content) {

	if (entity.getMetadatiTransient() == null) {
	    entity.setMetadatiTransient(new ArrayList<MetadatiBean>());
	}
	MetadatiBean mdsize = new MetadatiBean();
	Integer dimensioneFile = entity.getDimensioneFile() == null ? 0 : entity.getDimensioneFile();
	mdsize.setChiave(OggettiMetadatiService.FILE_SIZE_MD);
	mdsize.setValore(String.valueOf(dimensioneFile));
	entity.getMetadatiTransient().add(mdsize);
	MetadatiBean mdctype = new MetadatiBean();
	mdctype.setChiave(OggettiMetadatiService.FILE_CONTENT_TYPE_MD);
	String contentType = contenttypesService.findMimeTypeByFileName(entity.getNomefile());
	mdctype.setValore(contentType);
	entity.getMetadatiTransient().add(mdctype);
	if (content != null) {
	    String metaVal = DigestUtils.sha1Hex(content);
	    MetadatiBean mdsha = new MetadatiBean();
	    mdsha.setChiave(OggettiMetadatiService.SHA1_HASH_MD);
	    mdsha.setValore(metaVal);
	    entity.getMetadatiTransient().add(mdsha);
	    metaVal = DigestUtils.sha256Hex(content);
	    mdsha = new MetadatiBean();
	    mdsha.setChiave(OggettiMetadatiService.SHA256_HASH_MD);
	    mdsha.setValore(metaVal);
	    entity.getMetadatiTransient().add(mdsha);
	}
	oggettiMetadatiService.insertMetadatiPerOggetto(entity.getId().getCodice(), entity.getMetadatiTransient());
    }

    @Override
    public void aggiornaSenzaStoricizzare(Oggetti entity) {

	this.update(entity, false);
    }

    @Override
    public void update(Oggetti entity) {

	this.update(entity, true);
    }

    private void update(Oggetti entity, boolean storicizza) {

	if (validateEntity(entity)) {
	    evict(entity);
	    if (storicizza) {
		this.storicizzaOggetto(entity.getId().getCodice());
	    }
	    entity.setNomefile(Utilities.eliminaCaratteriNonAscii(entity.getNomefile()));
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
	    childDataUpdate(entity, content);
	    try {
		processaMetadatiFirmaOggetto(entity);
	    } catch (DSSClientException e) { // non deve fare rollback, operazione non bloccante
		log.warn("processaMetadatiFirmaOggetto: " + entity.getId(), e);
	    }
	}
    }

    private void storicizzaOggetto(Integer codiceOggetto) {

	Oggetti oggettoDB = this.oggettiDAO.findByIdLazyComplete(codiceOggetto);
	Oggetti nuovoOggetto = new Oggetti();
	nuovoOggetto.setDimensioneFile(oggettoDB.getDimensioneFile());
	nuovoOggetto.setMetadatiTransient(oggettoDB.getMetadatiTransient());
	nuovoOggetto.setNomefile(oggettoDB.getNomefile());
	nuovoOggetto.setOggetto(this.retrieveOggetto(oggettoDB));
	if (StringUtils.isNotBlank(oggettoDB.getPercorso())
		&& oggettoDB.getPercorso().startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
	    // SETTO IL PERCORSO PERCHE' SE READONLY AL RIPRISTINO RECUPERO ANCHE IL VECCHIO PERCORSO  ESEMPIO UID ALFRESCO
	    nuovoOggetto.setPercorso(oggettoDB.getPercorso());
	}
	this.insert(nuovoOggetto);
	OggettiStorico storico = new OggettiStorico(codiceOggetto, nuovoOggetto.getId().getCodice());
	this.oggettiStoricoService.insert(storico);
    }

    @Override
    public void updateOggettoFirmatoCAdES(Oggetti oggetti) {

	log.debug("updateOggettoFirmatoCAdES# Aggiorno file dopo firma CAdES. Aggiungo estensione p7m");
	String nomefile = new StringBuilder(oggetti.getNomefile()).append(".p7m").toString();
	String vp = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA,
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_FIRMA_CADES_NO_EXT_MULTI_P7M);
	if ("1".equals(vp)) {
	    log.debug("updateOggettoFirmatoCAdES# Comportamento {}.{}:{} ",
		    new Object[] { WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA,
			    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_FIRMA_CADES_NO_EXT_MULTI_P7M, vp });
	    nomefile = StringUtils.substring(nomefile, 0, StringUtils.indexOf(nomefile, ".p7m") + 4);
	    log.debug("updateOggettoFirmatoCAdES# file bonificato: {}", nomefile);
	}
	oggetti.setNomefile(nomefile);
	this.update(oggetti);
    }

    @Override
    public void updateOggettoFirmatoPAdES(Oggetti oggetti) {

	log.debug("updateOggettoFirmatoCAdES# Aggiorno file dopo firma PAdES. Aggiungo sign al nome del file");
	StringBuilder nomefile = new StringBuilder();
	if (!oggetti.getNomefile().startsWith("sign_")) {
	    nomefile = nomefile.append("sign_");
	}
	nomefile = nomefile.append(oggetti.getNomefile());
	oggetti.setNomefile(nomefile.toString());
	this.update(oggetti);
    }

    @Override
    public void updateFilesBloccaModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	log.debug("#updateFilesBloccaModifica");
	if (codiceoggetto == null || codiceResponsabile == null) {
	    return;
	}
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

    @Override
    public void updateFilesRimuoviBloccoModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	log.debug("#updateFilesRimuoviBloccoModifica");
	if (codiceoggetto == null || codiceResponsabile == null) {
	    return;
	}
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
			throw new SecurityException("Il File " + getDescrizioneFile(co) + " è bloccato da " + r.getResponsabile() +
						    " e può essere sbloccato solamente da lui.");
		    }
		}
	    }
	}
    }

    @Override
    public void updateFileRimuoviBloccoModifica(Integer codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	Integer[] i = new Integer[1];
	i[0] = codiceoggetto;
	updateFilesRimuoviBloccoModifica(i, codiceResponsabile);
    }

    /**
     * Il metodo torna il percorso recuperato dal parametro verticalizzazione <b>FILESYSTEM-->DIRECTORY_LOCALE</b> se
     * impostata altrimenti il valore del parametro <b>FILESYSTEM-->SHAREDPATH</b>
     * 
     * @return
     */
    private String getPrivateFileRepositoryPath() {

	String path = null;
	Verticalizzazioniparametri sharedPath = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_SHAREDPATH);
	Verticalizzazioniparametri localDirectory = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_DIRECTORY_LOCALE);
	// se il tomcat che serve l'applicativo è installato su macchina linux il file deve 
	// essere letto da condivisione tramite SAMBA ed il parametro per leggere/scrivere il contenuto di un
	// file deve puntare al path locale es: /home/shares/public.
	if (localDirectory != null) {
	    path = localDirectory.getValore();
	}
	if (StringUtils.isBlank(path) && sharedPath != null) {
	    path = sharedPath.getValore();
	}
	return path;
    }

    private void insertFileExt(Oggetti entity, byte[] content, boolean isUpdate) {

	if (content == null || content.length == 0) {
	    String message = "Non è possibile inserire/aggiornare documenti con contenuto nullo o vuoto: rif: [" + ORMHelper.getIdcomuneAlias() +
			     "-" + entity.getNomefile() + "]";
	    log.error(message);
	    throw new IllegalArgumentException(message);
	}
	boolean eCMIS = isVerticalizzazioneCMIS();
	boolean eApiAlfresco = isVerticalizzazioneApiAlfresco();
	if (scriviSuAlfrescoOCMIS(eCMIS, eApiAlfresco, isVerticalizzazioneApiAlfrescoSolaLettura())) {
	    STRATEGY s = eCMIS ? STRATEGY.CMIS : STRATEGY.API_REST;
	    String idDocumento = null;
	    if (!isUpdate) {
		idDocumento = alfrescoPersistenceService.creaDocumento(entity.getId().getCodice(), entity.getNomefile(), content, s);
	    } else {
		if (StringUtils.isBlank(entity.getPercorso())
			|| !(entity.getPercorso().startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER))) {
		    idDocumento = alfrescoPersistenceService.creaDocumento(entity.getId().getCodice(), entity.getNomefile(), content, s);
		} else {
		    idDocumento = alfrescoPersistenceService.aggiornaDocumento(entity.getId().getCodice(), entity.getNomefile(), entity.getPercorso(),
			    content, s);
		}
	    }
	    entity.setPercorso(getIdDocumentoAlfrescoPerSalvataggioDB(idDocumento));
	    entity.setNonUsareContenutoBLOB(null);
	} else if (isVerticalizzazioneFileSystem()) {
	    if (isVerticalizzazioneFileSystemReadonly()) {
		entity.setNonUsareContenutoBLOB(content);
	    } else {
		String path = getPrivateFileRepositoryPath();
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
		} else {
		    entity.setNonUsareContenutoBLOB(content);
		}
	    }
	} else {
	    entity.setNonUsareContenutoBLOB(content);
	}
    }

    private String getIdDocumentoAlfrescoPerSalvataggioDB(String idDocumento) {

	if (!idDocumento.startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
	    return IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + idDocumento;
	}
	return idDocumento;
    }

    private boolean isVerticalizzazioneFileSystem() {

	return verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM);
    }

    private boolean isVerticalizzazioneFileSystemChmod() {

	Verticalizzazioniparametri params = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_ESEGUI_CHMOD_FILE);
	return (params != null && StringUtils.defaultIfEmpty(params.getValore(), "0").equalsIgnoreCase("1"));
    }

    private boolean isVerticalizzazioneFileSystemReadonly() {

	Verticalizzazioniparametri params = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM,
		WebConstants.VERTICALIZZAZIONE_FILESYSTEM_READONLY);
	return (params != null && StringUtils.defaultIfEmpty(params.getValore(), "0").equalsIgnoreCase("1"));
    }

    private boolean isVerticalizzazioneCMIS() {

	return verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM_CMIS);
    }

    private boolean isVerticalizzazioneApiAlfresco() {

	return verticalizzazioneNodoAlfrescoAPIService.isAttiva();
    }

    private boolean isVerticalizzazioneApiAlfrescoSolaLettura() {

	return verticalizzazioneNodoAlfrescoAPIService.solaLettura();
    }

    private String getSharedPath() {

	String path = null;
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService
		.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FILESYSTEM, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_SHAREDPATH);
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

	long length = file.length();
	if (length > Integer.MAX_VALUE) {
	    throw new IOException("File troppo grande!");
	}
	byte[] bytes = new byte[(int) length];
	int offset = 0;
	int numRead = 0;
	InputStream is = null;
	try {
	    is = new FileInputStream(file);
	    while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
		offset += numRead;
	    }
	    if (offset < bytes.length) {
		is.close();
		throw new IOException("Errore durante la lettura del file: " + file.getName());
	    }
	} finally {
	    if (is != null) {
		is.close();
	    }
	}
	return bytes;
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
	    if (!cartellecreate) {
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
	// verticalizzazioniService
	if (isVerticalizzazioneFileSystemChmod()) {
	    Utilities.permessiFSLinux(out, 777);
	}
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
	    List<MetadatiBean> mds = null;
	    Integer codiceOggetto = null;
	    // if (rule.isUpdate() || rule.isInsert()) {
	    if (entity != null) {
		if (entity.getId() != null) {
		    codiceOggetto = entity.getId().getCodice();
		}
		mds = entity.getMetadatiTransient();
	    }
	    if (oggFromBind != null) {
		if (oggFromBind.getId() != null) {
		    codiceOggetto = oggFromBind.getId().getCodice();
		}
		if (mds == null) {
		    mds = oggFromBind.getMetadatiTransient();
		}
	    }
	    // }
	    if (mds != null && !mds.isEmpty() && codiceOggetto != null) {
		oggettiMetadatiService.insertMetadatiPerOggetto(codiceOggetto, mds);
	    }
	}
	return oggFromBind;
    }

    @Override
    public String getSharedFileLink(Integer codiceOggetto) {

	Oggetti entity = findByIdLazy(new PkId(codiceOggetto));
	if (entity == null) {
	    throw new IllegalArgumentException("Nessun oggetto trovato con codice [ " + ORMHelper.getIdcomune() + "," + codiceOggetto + "]");
	}
	if (!isVerticalizzazioneFileSystemReadonly()) {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
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
		if ((isVerticalizzazioneCMIS() || isVerticalizzazioneApiAlfresco())
			&& StringUtils.defaultString(percorso).startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
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

    private boolean isContenutoCLOBNullo(Integer codiceOggetto) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
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
	    if (StringUtils.isNotBlank(elemento) && !elemento.equalsIgnoreCase("/")) {
		linkCorretto = linkCorretto.concat(elemento).concat("\\");
	    }
	}
	if (linkCorretto.endsWith("\\")) {
	    linkCorretto = StringUtils.left(linkCorretto, linkCorretto.length() - 1);
	}
	return linkCorretto;
    }

    private boolean isUsatoInLibreria(Oggetti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro oggetto non può essere nullo");
	}
	if (EntityUtils.getNestedProperty(entity, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro codiceoggetto non può essere nullo");
	}
	Oggettiinfo oggettoLibreria = oggettiinfoService.findById(new PkId(entity.getId().getCodice()));
	return (oggettoLibreria != null);
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
	boolean eCMIS = isVerticalizzazioneCMIS();
	boolean eApiAlfresco = isVerticalizzazioneApiAlfresco();
	if (isVerticalizzazioneFileSystem() || eCMIS || eApiAlfresco) {
	    if (entity.getNonUsareContenutoBLOB() != null) {
		// nel caso di verticalizzazione FILESYSTEM e se l'oggetto BLOB sia valorizzato allora il contenuto del file
		// valido è quello della tabella
		log.warn("retrieveOggetto: L'oggetto [{}] è stato recuperato dalla collonna BLOB anche se impostato il percorso {}",
			new Object[] { entity.getId(), StringUtils.defaultIfEmpty(entity.getPercorso(), "") });
		return entity.getNonUsareContenutoBLOB();
	    }
	    // i persorsi NOT NULL che non iniziano con CMIS_WORKSPACE_FILE_IDENTIFIER = "workspace://SpacesStore/"
	    // saranno cercati su FILESYSTEM
	    if (StringUtils.defaultString(entity.getPercorso()).startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		STRATEGY s = eCMIS ? STRATEGY.CMIS : STRATEGY.API_REST;
		InputStream is = alfrescoPersistenceService.getContenuto(entity.getId().getCodice(), entity.getNomefile(), entity.getPercorso(), s);
		try {
		    content = IOUtils.toByteArray(is);
		} catch (IOException e) {
		    log.error("retrieveOggetto: Non è stato possibile recuperare lo stream di bytes del file {} dal sistema CMIS a causa di {}",
			    entity.getNomefile(), e.getMessage());
		    throw new RuntimeException(
			    "Non è stato possibile recuperare lo stream di bytes del file " + entity.getNomefile() + " dal sistema CMIS a causa di ",
			    e);
		} catch (Exception e) {
		    log.error("retrieveOggetto: Non è stato possibile recuperare il file {} con objId[{}] dal sistema CMIS a causa di {}",
			    new Object[] { entity.getNomefile(), entity.getPercorso(), e.getMessage() });
		    throw new RuntimeException("Non è stato possibile recuperare il file " + entity.getNomefile() + " con objId[" +
					       entity.getPercorso() + "] dal sistema CMIS a causa di " + e.getMessage(),
			    e);
		}
		try {
		    IOUtils.closeQuietly(is);
		    log.debug("retrieveOggetto: Chiudo lo input Stream");
		} catch (Exception e) {
		    log.debug("retrieveOggetto: errore nella chiusura dello input Stream: {}", e.getMessage());
		}
	    } else {
		// get from fs
		String path = getPrivateFileRepositoryPath();
		if (StringUtils.isNotBlank(path)) {
		    if (StringUtils.isNotBlank(entity.getPercorso())) {
			path += File.separatorChar + entity.getPercorso();
		    }
		    path += File.separator;
		    log.debug("retrieveOggetto: path [{}] ", path);
		    String baseSearchFolder = Utilities.normalizzaPath(path);
		    log.debug("retrieveOggetto: baseSearchFolder[{}] ", baseSearchFolder);
		    path = baseSearchFolder + entity.getNomefile();
		    log.debug("retrieveOggetto: filepath [{}] ", path);
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
	    log.debug("cerco il file " + nomeFile + " nella directory: " + searchDir);
	    String fileSenzaCUTF8 = Utilities.eliminaCaratteriNonAscii(nomeFile);
	    log.debug("nome file file ripulito " + fileSenzaCUTF8);
	    if (!nomeFile.equalsIgnoreCase(fileSenzaCUTF8)) {
		File[] files = searchDir.listFiles();
		for (File file : files) {
		    if (!file.isDirectory()) {
			String name = file.getName();
			log.warn("\tvaluto il file " + name);
			name = Utilities.eliminaCaratteriNonAscii(name);
			log.warn("\tfile ripulito " + name);
			if (name.equalsIgnoreCase(fileSenzaCUTF8)) {
			    log.warn("E' stato trovato ed associato il seguente file " + name + " provo a ");
			    try {
				byte[] content = getBytesFromFile(file);
				return content;
			    } catch (IOException e) {
				log.error("Errore nel recupero del file da FileSystem. Non è stato possibile recuperare il file [" + nomeFile +
					  "] dal percorso [" + searchDir + "]" + e.getMessage() + ": {}",
					e);
				throw new RuntimeException("Errore nel recupero del file da FileSystem. Non è stato possibile recuperare il file [" +
							   nomeFile + "] dal percorso [" + searchDir + "]" + e.getMessage(),
					e);
			    }
			}
		    }
		}
	    }
	}
	throw new RuntimeException("Errore nel recupero del file da FileSystem. Non è stato possibile recuperare il file [" + nomeFile +
				   "] dal percorso [" + searchDir + "]");
    }

    @Override
    public Oggetti findByIdLazy(PkId id) {

	return oggettiDAO.findByIdLazy(id);
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

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

    private String getDescrizioneFile(Integer codiceOggetto) {

	log.debug("#getDescrizioneFile");
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
    public InputStream getOggettoAsInputStream(Integer codiceOggetto) {

	if (log.isDebugEnabled()) {
	    log.debug("getOggettoAsInputStream# entro nel metodo");
	}
	boolean readFromFS = false;
	boolean eCMIS = isVerticalizzazioneCMIS();
	boolean eApiAlfresco = isVerticalizzazioneApiAlfresco();
	if (isVerticalizzazioneFileSystem() || eCMIS || eApiAlfresco) {
	    boolean isNullCLOB = isContenutoCLOBNullo(codiceOggetto);
	    if (log.isDebugEnabled()) {
		log.debug("getOggettoAsInputStream# isNullCLOB={} ", isNullCLOB);
	    }
	    if (!isNullCLOB) {
		return oggettiDAO.getInputStreamFromBLOB(codiceOggetto);
	    }
	    Oggetti oggettiLazy = this.findByIdLazy(new PkId(codiceOggetto));
	    // i persorsi NOT NULL che non iniziano con CMIS_WORKSPACE_FILE_IDENTIFIER = "workspace://SpacesStore/"
	    // saranno cercati su FILESYSTEM
	    if (StringUtils.defaultString(oggettiLazy.getPercorso()).startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		STRATEGY s = eCMIS ? STRATEGY.CMIS : STRATEGY.API_REST;
		return alfrescoPersistenceService.getContenuto(oggettiLazy.getId().getCodice(), oggettiLazy.getNomefile(), oggettiLazy.getPercorso(),
			s);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("getOggettoAsInputStream# verticalizzazione filesystem attiva");
		}
		String path = getPrivateFileRepositoryPath();
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
	    Oggetti oggettiLazy = this.findByIdLazy(new PkId(codiceOggetto));
	    try {
		return retrieveInputStreamFromFile(oggettiLazy);
	    } catch (FileNotFoundException e) {
		throw new RuntimeException("File non trovato", e);
	    }
	} else {
	    return oggettiDAO.getInputStreamFromBLOB(codiceOggetto);
	}
    }

    private InputStream retrieveInputStreamFromFile(Oggetti oggettiLazy) throws FileNotFoundException {

	if (log.isDebugEnabled()) {
	    log.debug("retrieveInputStreamFromFile# entro nel metodo");
	}
	String path = getPrivateFileRepositoryPath();
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

	log.debug("#evict");
	oggettiDAO.evict(entity);
    }

    @Override
    public String insertOrGetUID(Integer codiceOggetto) {

	log.debug("#insertOrGetUID");
	if (codiceOggetto == null) {
	    return "";
	}
	OggettiMetadati omd = oggettiMetadatiService.findById(new OggettiMetadatiId(codiceOggetto, WebConstants.OGGETTI_FILE_UID));
	String valore = "";
	if (omd != null) {
	    valore = StringUtils.defaultString(omd.getValore());
	    if (StringUtils.isBlank(valore)) {
		valore = UUID.randomUUID().toString();
		//		omd.setValore(valore);
		//		oggettiMetadatiService.update(omd);
		//		oggettiDAO.flush();
		oggettiMetadatiService.updateInNewTransaction(codiceOggetto, WebConstants.OGGETTI_FILE_UID, valore);
	    }
	} else {
	    //	    omd = new OggettiMetadati();
	    //	    omd.setId(new OggettiMetadatiId(codiceOggetto, WebConstants.OGGETTI_FILE_UID));
	    valore = UUID.randomUUID().toString();
	    //	    omd.setValore(valore);
	    //	    oggettiMetadatiService.insert(omd);
	    //	    oggettiDAO.flush();
	    oggettiMetadatiService.insertInNewTransaction(codiceOggetto, WebConstants.OGGETTI_FILE_UID, valore);
	}
	return valore;
    }

    @Override
    public String insertOrGetSHA256(Integer codiceOggetto) {

	log.debug("#insertOrGetSHA256");
	if (codiceOggetto == null) {
	    return "";
	}
	OggettiMetadati omd = oggettiMetadatiService.findById(new OggettiMetadatiId(codiceOggetto, OggettiMetadatiService.SHA256_HASH_MD));
	String valore = "";
	InputStream is = null;
	if (omd != null) {
	    valore = StringUtils.defaultString(omd.getValore());
	    if (StringUtils.isBlank(valore)) {
		is = this.getOggettoAsInputStream(codiceOggetto);
		try {
		    valore = DigestUtils.sha256Hex(is);
		} catch (IOException e) {
		    log.error("{}", e);
		}
		oggettiMetadatiService.updateInNewTransaction(codiceOggetto, OggettiMetadatiService.SHA256_HASH_MD, valore);
	    }
	} else {
	    is = this.getOggettoAsInputStream(codiceOggetto);
	    try {
		valore = DigestUtils.sha256Hex(is);
	    } catch (IOException e) {
		log.error("{}", e);
	    }
	    oggettiMetadatiService.insertInNewTransaction(codiceOggetto, OggettiMetadatiService.SHA256_HASH_MD, valore);
	}
	if (is != null) {
	    try {
		is.close();
	    } catch (Exception e) {
		log.error("{}", e);
	    }
	}
	return valore;
    }

    @Override
    public void updateSbloccaOggetto(OggettiMetadati oggettiMetadati) {

	log.debug("#updateSbloccaOggetto");
	oggettiMetadatiService.delete(oggettiMetadati);
    }

    @Override
    public String creaSingoloLinkAllegati(Integer codiceOggetto) {

	// Metodo esposto per recuperare il link
	// Verifico che sia attiva la verticalizzazione
	if (!verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC)) {
	    return " "; // torno lo spazio perché la tabella non accetta valori nulli e 
			// l'obiettivo è tornare una stringa vuota che sarà sostituita nel tag
	}
	//Verifico la verticalizzazione contenete l'url del servizio per il recupero dei file fisici
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC, WebConstants.VERTICALIZZAZIONE_PARAMETRI_URL_SERVIZIO_RECUPERO_DOC);
	Verticalizzazioniparametri senzapin = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC,
		WebConstants.VERTICALIZZAZIONE_PARAMETRI_ALLEGATI_PEC_DOWNLOAD_SENZA_PIN);
	String urlPath = "";
	boolean senzaPinBool = false;
	String parametriUrl = "get.htm?m={md5_oggetto}&a=" + ORMHelper.getIdcomuneAlias();
	if (senzapin != null) {
	    senzaPinBool = StringUtils.defaultString(senzapin.getValore(), "N").equalsIgnoreCase("S");
	    if (senzaPinBool) {
		parametriUrl = "view.htm?m={md5_oggetto}&a=" + ORMHelper.getIdcomuneAlias() + "&pin_document=" + codiceOggetto;
	    }
	}
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
	List<OggettiMetadati> listaMetadati = oggettiMetadatiService.findByOggetto(codiceOggetto, OggettiMetadatiService.MD5_SUM_MD);
	if (!listaMetadati.isEmpty()) {
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
	    oggettiMetadatiService.insertMetadatiPerOggetto(codiceOggetto, metadati);
	}
	// Creao il path : urlServizio+urlMethodEsposto
	urlPath += parametriUrl.replace("{md5_oggetto}", md5);
	return urlPath;
    }

    @Override
    public Integer creaDocumentoConLinkOggetti(Letteretipo lettereTipo, Integer codiceIst, Integer codiceMov, String codiceTipoMov, String uuid) {

	Integer codiceLetteraTipo = lettereTipo.getId().getCodice();
	lettereTipo = letteretipoService.findById(new PkId(codiceLetteraTipo));
	Integer codiceOggetto = (Integer) EntityUtils.getNestedProperty(lettereTipo.getFile(), "id.codice");
	Integer codiceAllegatoCreato = null;
	if (codiceOggetto != null) {
	    boolean paginaStampaDocTipoJava = TecnologiaPaginaEnum.JAVA.equals(this.verticalizzazioneTipoInstallazioneService.paginaStampeDocTipo());
	    // controllare se installazione STANDARD o ENTERPRISE e chiamare il servizio di generazione documenti automatica 
	    if (lettereTipo.getFile().getNomefile().toLowerCase().endsWith(".odt") || paginaStampaDocTipoJava) {
		DocumentMergeHelper documentMergeHelper = new DocumentMergeHelper();
		documentMergeHelper.setUuidLinkTemp(uuid);
		try {
		    Oggetti oggetto = documentMergeService.createAllegatoDaDocumentoTipo(lettereTipo.getId().getCodice(), codiceIst, codiceMov,
			    documentMergeHelper);
		    codiceAllegatoCreato = oggetto.getId().getCodice();
		    Movimentiallegati movimentiallegati = new Movimentiallegati();
		    Movimenti movimento = movimentiService.findById(new PkId(codiceMov));
		    movimentiallegati.setMovimento(movimento);
		    movimentiallegati.setOggetto(oggetto);
		    movimentiallegati.setDescrizione(lettereTipo.getDescrizione());
		    movimentiallegatiService.insert(movimentiallegati);
		} catch (Exception e) {
		    log.error("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," + lettereTipo.getDescrizione() +
			      "]: " + e,
			    e);
		    throw new RuntimeException("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," +
					       lettereTipo.getDescrizione() + "]: " + e,
			    e);
		}
	    } else {
		if (verticalizzazioniService.isInstallazioneEnterprise()) {
		    // commentata logica perchè la stessa logica è stata messa nella 
		    // documentMergeService.getUrlGeneraAllegato() e se:
		    // 1. non è attiva VERTICALIZZAZIONE_PARAMETRI_SISTEMA_OVERRIDE_URL_GENERA_ALLEGATO
		    // 2. baseUrl non popolata sulla security
		    // allora si duplica la prima parte del link
		    // String baseUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.BASE_URL);
		    String urlStampaEnterprise = documentMergeService.getUrlGeneraAllegato();
		    //		    if (StringUtils.isBlank(baseUrl)) {
		    //			// la base url non è settata cerco di recuperarla da wshosturlaspnet
		    //			log.error("Attenzione!!! Non è stato settato il parametro BASE_URL nell'applicativo di Security tento di recuperarlo da aspnetbaseurl");
		    //			baseUrl = getBaseUrlFromAspnetBaseURL();
		    //			log.error("Attenzione!!! la BASE_URL recuperata da aspnetbaseurl è {}", baseUrl);
		    //			urlStampaEnterprise = baseUrl + urlStampaEnterprise;
		    //		     
		    //		    }
		    // 1. invocare con httpClient il servizio Enterprise asp 
		    // 2. Attendere la la risposta della chiamata e controllare se su movimenti allegati sono presenti record 
		    urlStampaEnterprise += "?codiceDocumento=" + codiceLetteraTipo + "&codiceIstanza=" + codiceIst + "&codiceMovimento=" + codiceMov +
					   "&TipoMovimento=" + codiceTipoMov + "&" + "ALLEGATI_UUID" + "=" + uuid + "&" + WebConstants.SOFTWARE +
					   "=" + ORMHelper.getSoftware() + "&" + WebConstants.TOKEN + "=" + ORMHelper.getToken();
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
			    log.error("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," +
				      lettereTipo.getDescrizione() + "] Status : " + status);
			    throw new RuntimeException("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," +
						       lettereTipo.getDescrizione() + "] Status : " + status);
			}
			String codice = new String(method.getResponseBody());
			codiceAllegatoCreato = Integer.valueOf(codice);
			documentMergeService.verificaConvertiRtfInOdt(codiceAllegatoCreato, true);
		    } catch (Exception e) {
			log.error("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," + lettereTipo.getDescrizione() +
				  "] {}",
				e);
			throw new RuntimeException("Attenzione non è stato possibile generare l'allegato [" + codiceLetteraTipo + "," +
						   lettereTipo.getDescrizione() + "] " + e.getMessage(),
				e);
		    }
		}
	    }
	}
	return codiceAllegatoCreato;
    }

    @Override
    public byte[] trasformRtfInPdf(Oggetti oggettoRtf) {

	log.debug("trasformRtfInPdf");
	return trasformInPdf(oggettoRtf, "RTF");
    }

    @Override
    public byte[] trasformInPdf(Oggetti oggetto, String estensione) {

	if (StringUtils.isBlank(estensione)) {
	    estensione = checkExtensionallowed(oggetto.getNomefile());
	}
	byte[] b = oggetto.getOggetto();
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), b, estensione, "PDF");
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
    public void aggiornaMetadatiCMIS(Integer codiceOggetto) {

	boolean eCMIS = isVerticalizzazioneCMIS();
	boolean eApiAlfresco = isVerticalizzazioneApiAlfresco();
	// verifica se filesystem_cmis
	if (eCMIS || eApiAlfresco) {
	    Oggetti entity = this.findByIdLazy(new PkId(codiceOggetto));
	    if (entity.getPercorso() != null && entity.getPercorso().startsWith(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER)) {
		// Verifica se oggetto salvato su cmis
		// effettua query per vedere se sono gestiti i metadati
		if (cfgMetadatiCmisDAO.existConfigurazioneByIdComune(ORMHelper.getIdcomune())) {
		    List<CfgMetadatiCmis> metadatis = cfgMetadatiCmisDAO.findByIdComune(ORMHelper.getIdcomune());
		    Map<String, CfgMetadatiCmis> mds = new HashMap<String, CfgMetadatiCmis>();
		    for (CfgMetadatiCmis md : metadatis) {
			mds.put(md.getId().getFkMetadatoBase(), md);
		    }
		    if (!mds.isEmpty()) {
			// cerco i metadati dell'oggetto
			List<OggettiMetadati> s = oggettiMetadatiService.findByOggetto(codiceOggetto);
			Map<String, CodiceDescrizioneBean> mdcmisss = new HashMap<String, CodiceDescrizioneBean>();
			for (OggettiMetadati omd : s) {
			    if (StringUtils.isNotBlank(omd.getValore())) {
				String keyBase = omd.getId().getChiave();
				CfgMetadatiCmis mdcmis = mds.get(keyBase);
				if (mdcmis != null) {
				    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
				    cdb.setCodice(mdcmis.getTipoDatoCmis());
				    cdb.setDescrizione(omd.getValore());
				    mdcmisss.put(mdcmis.getMappingCmis(), cdb);
				}
			    }
			}
			STRATEGY strategy = eCMIS ? STRATEGY.CMIS : STRATEGY.API_REST;
			alfrescoPersistenceService.aggiornaMetadati(codiceOggetto, entity.getNomefile(), entity.getPercorso(), strategy, mdcmisss);
		    }
		}
	    }
	}
    }

    @Override
    public Oggetti insert(MultipartFile multipartFile) {

	Oggetti oggetti = new Oggetti();
	oggetti.setNomefile(multipartFile.getOriginalFilename());
	oggetti.setDimensioneFile(new Integer(new Long(multipartFile.getSize()).toString()));
	try {
	    oggetti.setOggetto(multipartFile.getBytes());
	    this.insert(oggetti);
	} catch (IOException e) {
	    log.error("insert: Non è stato possibile recuperare lo stream di bytes del file {}  a causa di {}", multipartFile.getOriginalFilename(),
		    e.getMessage());
	    throw new RuntimeException(
		    "Non è stato possibile recuperare lo stream di bytes del file " + multipartFile.getOriginalFilename() + " a causa di ", e);
	} catch (Exception e) {
	    log.error("insert: Non è stato possibile inserire l'oggetto file {}  a causa di {}", multipartFile.getOriginalFilename(), e.getMessage());
	    throw new RuntimeException("Non è stato possibile inserire l'oggetto file " + multipartFile.getOriginalFilename() + " a causa di ", e);
	}
	return oggetti;
    }

    @Override
    @Transactional(noRollbackFor = { FunzioneBusinessRemotaException.class, RuntimeException.class })
    public Oggetti convertFileInPdf(Integer codiceOggetto) {

	try {
	    return convertFileInPdf(this.findById(new PkId(codiceOggetto)));
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public Oggetti convertFileInPdf(Oggetti obj) throws FunzioneBusinessRemotaException {

	String contentType = checkExtensionallowed(obj.getNomefile());
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest req = new ConvertBinaryRequest();
	req.setToken(ORMHelper.getToken());
	req.setConversionType(FileConverterWsClient.ConversionType.PDF.name());
	req.setContentType(contentType);
	req.setBinaryData(obj.getOggetto());
	ConvertBinaryResponse resp = null;
	try {
	    resp = fileConverterWsClient.convertBinary(req);
	} catch (Exception e) {
	    log.error("Errore durante la conversione del file in PDF: {}, \n{}", e.getMessage(), e);
	    throw new FunzioneBusinessRemotaException("Errore durante la conversione del file in PDF: " + e.getMessage(), e);
	}
	Oggetti nuovoOggetto = new Oggetti();
	String nomeFile = obj.getNomefile().replaceAll("\\.", "");
	nuovoOggetto.setNomefile(nomeFile + ".pdf");
	nuovoOggetto.setOggetto(resp.getBinaryData());
	return nuovoOggetto;
    }

    @Override
    public Oggetti convertFileInPdfAndSostituisci(Integer codiceOggetto) {

	return convertFileInPdfAndSostituisci(this.findById(new PkId(codiceOggetto)));
    }

    @Override
    public Oggetti convertFileInPdfAndSostituisci(Oggetti obj) {

	Oggetti o2;
	try {
	    o2 = convertFileInPdf(obj);
	    obj.setNomefile(o2.getNomefile());
	    obj.setOggetto(o2.getOggetto());
	    this.update(obj);
	    oggettiDAO.flush();
	    return obj;
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    private String checkExtensionallowed(String nomeFile) {

	if (StringUtils.isNotBlank(nomeFile)) {
	    if (nomeFile.indexOf(".") > -1) {
		String extension = nomeFile.substring(nomeFile.lastIndexOf("."));
		extension = extension.replace(".", "").toUpperCase();
		if (extension.equalsIgnoreCase("RTF") || extension.equalsIgnoreCase("TXT") || extension.equalsIgnoreCase("DOC")
			|| extension.equalsIgnoreCase("ODT") || extension.equalsIgnoreCase("HTML")) {
		    return extension;
		}
	    }
	    return contenttypesService.findMimeTypeByFileName(nomeFile);
	}
	return "TXT";
    }

    @Override
    public void processaMetadatiFirmaOggetto(Oggetti oggetto) throws DSSClientException {

	if (oggetto.getOggetto() == null) {
	    return; // non calcolo il metadato firmato.
		    // dovrebbe essere il caso dell'import che setta a nullo il contenuto
	}
	boolean verificaFirmaOggettiInseriti = this.verticalizzazioneParametriSistemaService.isAttiva()
		&& this.verticalizzazioneParametriSistemaService.verificaFirmaOggettiInseriti();
	if (verificaFirmaOggettiInseriti) {
	    DataHandler dh = Utilities.bytesToDataHandler(oggetto.getOggetto());
	    DSSRestClient client = new DSSRestClient();
	    ValidationResultDTO report = client.checkFirmaReport(dh, oggetto.getNomefile(), true, false);
	    try {
		boolean isFirmato = (report != null && report.getSimpleReportSummary() != null
			&& report.getSimpleReportSummary().getSignatureSummaries() != null
			&& !report.getSimpleReportSummary().getSignatureSummaries().isEmpty());
		List<MetadatiBean> listMD = new ArrayList<MetadatiBean>();
		MetadatiBean md1 = new MetadatiBean();
		md1.setChiave(OggettiMetadatiService.CHIAVE_FIRMA_DIGITALE_PRESENTE);
		if (!isFirmato) {
		    md1.setValore(OggettiMetadatiService.VALORE_FIRMA_DIGITALE_PRESENTE_FALSE);
		} else {
		    md1.setValore(OggettiMetadatiService.VALORE_FIRMA_DIGITALE_PRESENTE_TRUE);
		    StringBuilder sb = new StringBuilder();
		    boolean tutteFirmeValidi = true;
		    Map<String, Boolean> firmeMap = new HashMap<String, Boolean>();
		    for (SignatureSummaryItem signatureInfo : report.getSimpleReportSummary().getSignatureSummaries()) {
			if (signatureInfo.getIndication() == null || !signatureInfo.getIndication().endsWith("PASSED")) {
			    tutteFirmeValidi = false;
			    firmeMap.put(signatureInfo.getSignatureId(), false);
			    continue;
			}
			firmeMap.put(signatureInfo.getSignatureId(), true);
		    }
		    String diagnosticDataXml = report.getDiagnosticDataXml();
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setNamespaceAware(false);
		    factory.setXIncludeAware(false);
		    factory.setExpandEntityReferences(false);
		    factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		    factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		    factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		    factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new InputSource(new StringReader(diagnosticDataXml)));
		    NodeList signatures = doc.getElementsByTagName("Signature");
		    Map<String, String> signCertMap = new HashMap<String, String>();
		    for (int i = 0; i < signatures.getLength(); i++) {
			Element signature = (Element) signatures.item(i);
			Element signingCertificate = (Element) signature.getElementsByTagName("SigningCertificate").item(0);
			signCertMap.put(signingCertificate.getAttribute("Certificate"), signature.getAttribute("Id"));
		    }
		    NodeList certificates = doc.getElementsByTagName("Certificate");
		    for (int j = 0; j < certificates.getLength(); j++) {
			Element cert = (Element) certificates.item(j);
			String certId = cert.getAttribute("Id");
			if (!signCertMap.containsKey(certId)) {
			    continue;
			}
			String signId = signCertMap.get(certId);
			if (!firmeMap.containsKey(signId) || !firmeMap.get(signId).booleanValue()) {
			    continue;
			}
			String cognome = "";
			String nome = "n/a";
			String codicefiscale = "n/a";
			String surnameTag = getTextValueOnTag(cert, "Surname");
			String nomeTag = getTextValueOnTag(cert, "GivenName");
			String subjectSerialNumberTag = getTextValueOnTag(cert, "SubjectSerialNumber");
			if (!StringUtils.isBlank(surnameTag)) {
			    cognome = surnameTag;
			} else {
			    String cnTag = getTextValueOnTag(cert, "CountryName");
			    if (StringUtils.isBlank(cnTag)) {
				cognome = cnTag;
			    }
			}
			if (!StringUtils.isBlank(nomeTag)) {
			    nome = nomeTag;
			}
			if (!StringUtils.isBlank(subjectSerialNumberTag)) {
			    codicefiscale = subjectSerialNumberTag;
			}
			sb.append(cognome).append(" ").append(nome).append("|").append(codicefiscale).append("#");
		    }
		    MetadatiBean md2 = new MetadatiBean();
		    md2.setChiave("FIRMA_DIGITALE_VALIDA");
		    MetadatiBean md4 = new MetadatiBean("FIRMA_DIGITALE_FIRMATARI", sb.toString());
		    if (tutteFirmeValidi) {
			md2.setValore("S");
		    } else {
			md2.setValore("N");
		    }
		    MetadatiBean md3 = new MetadatiBean("FIRMA_DIGITALE_NUMERO_FIRME",
			    String.valueOf(report.getSimpleReportSummary().getSignaturesCount()));
		    listMD.add(md2);
		    listMD.add(md3);
		    listMD.add(md4);
		}
		listMD.add(md1);
		oggettiMetadatiService.insertMetadatiPerOggetto(oggetto.getId().getCodice(), listMD);
	    } catch (Exception e) {
		throw new DSSClientException(e);
	    }
	}
    }

    private String getTextValueOnTag(Element el, String tagName) {

	NodeList tagList = el.getElementsByTagName(tagName);
	if (tagList == null || tagList.getLength() < 1) {
	    return null;
	}
	return tagList.item(0).getTextContent();
    }

    @Override
    public Oggetti verificaConvertiPdf(Oggetti oggetti) {

	if (verticalizzazioneComportamentoComponenteFirmaService.convertibileInPdf(oggetti.getNomefile())) {
	    return this.convertFileInPdfAndSostituisci(oggetti);
	}
	return oggetti;
    }

    @Override
    public boolean scriviSuAlfrescoOCMIS(boolean eCMIS, boolean eApiAlfresco, boolean eApiAlfrescoSolaLettura) {

	return eCMIS || (eApiAlfresco && !eApiAlfrescoSolaLettura);
    }
}
