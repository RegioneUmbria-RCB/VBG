package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MovimentiallegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.LayerPDFHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.ICommissioniDocumentiPraticheService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.suapxml.ISuapXmlService;
import it.gruppoinit.pal.gp.core.features.suapxml.SuapXmlServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.ManipulatePdfService;
import it.gruppoinit.pal.gp.core.service.MovimentiContromovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.QrcodeService;
import it.gruppoinit.pal.gp.core.service.QrcodeService.TipoImmagine;
import it.gruppoinit.pal.gp.core.service.SoftwareByCodiceOggettoReaderService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.AnnotazionePropertyPdfHelper;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.MetadatiFunzioneEnum;
import it.gruppoinit.pal.gp.core.service.helper.ZipLogicoLinkHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.PdfUtilsWSClient;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;

@Service
public class MovimentiallegatiServiceImpl extends BaseServiceImpl<Movimentiallegati, PkId>
	implements MovimentiallegatiService, SoftwareByCodiceOggettoReaderService {

    private static final Logger log = LoggerFactory.getLogger(MovimentiallegatiServiceImpl.class);
    private MovimentiallegatiDAO movimentiallegatiDAO;
    private MovimentiService movimentiService;
    private MovimentiContromovimentiService movimentiContromovimentiService;
    private OggettiService oggettiService;
    private ProtocollazioneService protocollazioneService;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private TempLinkallegatiService tempLinkallegatiService;
    private LetteretipoService letteretipoService;
    private DocumentMergeService documentMergeService;
    private VerticalizzazioniService verticalizzazioniService;
    private ManipulatePdfService manipulatePdfService;
    private MailtipoService mailtipoService;
    private ISuapXmlService suapXmlService;
    private QrcodeService qrcodeService;
    private DocumentiAutorizzazioneService documentiautorizzazioneService;
    private UserSecurityService userSecurityService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService;

    @Autowired
    public void setCommissioniDocumentiPraticheService(ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService) {

	this.commissioniDocumentiPraticheService = commissioniDocumentiPraticheService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setQrcodeService(QrcodeService qrcodeService) {

	this.qrcodeService = qrcodeService;
    }

    @Autowired
    public void setDocumentiDaFirmareService(DocumentiDaFirmareService documentiDaFirmareService) {

	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @Autowired
    public void setMovimentiallegatiDAO(MovimentiallegatiDAO movimentiallegatiDAO) {

	this.movimentiallegatiDAO = movimentiallegatiDAO;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiContromovimentiService(MovimentiContromovimentiService movimentiContromovimentiService) {

	this.movimentiContromovimentiService = movimentiContromovimentiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setTempLinkallegatiService(TempLinkallegatiService tempLinkallegatiService) {

	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    @Autowired
    public void setLetteretipoService(LetteretipoService letteretipoService) {

	this.letteretipoService = letteretipoService;
    }

    @Autowired
    public void setDocumentMergeService(DocumentMergeService documentMergeService) {

	this.documentMergeService = documentMergeService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setManipulatePdfService(ManipulatePdfService manipulatePdfService) {

	this.manipulatePdfService = manipulatePdfService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setDocumentiautorizzazioneService(DocumentiAutorizzazioneService documentiautorizzazioneService) {

	this.documentiautorizzazioneService = documentiautorizzazioneService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Autowired
    public void setSuapXmlService(ISuapXmlService suapXmlService) {

	this.suapXmlService = suapXmlService;
    }

    @Override
    protected Class<Movimentiallegati> getEntityClass() {

	return Movimentiallegati.class;
    }

    @Override
    public void delete(Movimentiallegati entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    childDelete(entity);
	    movimentiallegatiDAO.delete(entity);
	    gestCancellaOggetto(codiceOggettoDaCancellare);
	}
    }

    @Override
    protected boolean isDeleteAllowed(Movimentiallegati entity) {

	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() != null) {
	    // Cancellazione dei record movimenti allegati: Se ci sono record in metti alla firma e questi non sono stati completati 
	    // impossibile completare la cancellazione ma va dato un messaggio chiaro.
	    int ddfs = documentiDaFirmareService.countDocumentiDafirmarePerOggetto(entity.getOggetto().getId().getCodice());
	    if (ddfs > 0) {
		String report = documentiDaFirmareService.findReportHTMLOggettoDaFirmare(entity.getOggetto().getId().getCodice());
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		// 1. Verifica che il movimento non sia legato ad una CDS
		ivs.add(new InvalidValue(WebConstants.DOCUMENTI_DA_FIRMARE_ERRORE_CANCELLAZIONE, null, "", report, null));
		this.throwValidationMessages(ivs);
	    }
	}
	boolean delete = true;
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_DIS_CANC_MOV_PROTOCOLLATI);
	    if (vp != null && StringUtils.defaultIfEmpty(vp.getValore(), "0").trim().equalsIgnoreCase("1")
		    && StringUtils.isNotBlank(entity.getMovimento().getNumeroprotocollo())) {
		ivs.add(new InvalidValue("alert.delete.movimenti.allegati.protocollati", null, "", "", null));
		delete = false;
	    }
	    vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_BLOCCA_DEL_MOVALL_OPE_MOV);
	    if (vp != null && StringUtils.defaultIfEmpty(vp.getValore(), "0").trim().equalsIgnoreCase("1") && entity.getMovimento() != null
		    && entity.getMovimento().getId() != null && entity.getMovimento().getId().getCodice() != null) {
		Movimenti mov = movimentiService.findById(new PkId(entity.getMovimento().getId().getCodice()));
		if (mov != null && mov.getResponsabile() != null && mov.getResponsabile().getId() != null
			&& mov.getResponsabile().getId().getCodice() != null) {
		    Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
		    if (r != null && !r.getId().getCodice().equals(mov.getResponsabile().getId().getCodice())) {
			ivs.add(new InvalidValue("alert.delete.movimenti.allegati.altro.operatore", null, "", "", null));
			delete = false;
		    }
		}
	    }
	}
	//Controllo se esiste qualche collegamento con DocumentiAutorizzazione
	boolean isExistInDocAutorizzazione = documentiautorizzazioneService.isInAutorizzazione(entity.getId().getCodice(), "movimentiallegati");
	if (isExistInDocAutorizzazione) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DOCUMENTI_AUTORIZZAZIONE", null));
	    delete = false;
	}
	boolean isExistEntityInMovimentiZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(entity.getId().getCodice(),
		"movimentiallegati");
	if (isExistEntityInMovimentiZipLogico) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI_ZIP_LOGICO", null));
	    delete = false;
	}
	if (commissioniDocumentiPraticheService.existsByMovimentiAllegati(entity.getId().getCodice())) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null,
		    "COMMEDR_MOVALL: è stato selezionato come Allegato delle commissioni / conferenze", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(ivs);
	}
	return true;
    }

    @Override
    protected void childDelete(Movimentiallegati entity) {

	List<DocumentiDaFirmare> ddf = documentiDaFirmareService.findByMovimentiallegati(entity.getId().getCodice());
	for (DocumentiDaFirmare documentiDaFirmare : ddf) {
	    documentiDaFirmareService.delete(documentiDaFirmare);
	}
	MovimentiZipLogicoTestata t = movimentiZipLogicoService.findTestataByCodiceMovimento(entity.getMovimento().getId().getCodice());
	if (t != null && t.getCodiceoggettoDocAll() != null) {
	    FlashMessages.getWarnings().add("Il documento è associato allo ZIP LOGICO del movimento e non è stato rimosso da quella sezione");
	}
    }

    @Override
    public List<Movimentiallegati> findAll(Integer firstResult, Integer maxResult) {

	return movimentiallegatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Movimentiallegati findById(PkId id) {

	return movimentiallegatiDAO.findById(id);
    }

    @Override
    public void insert(Movimentiallegati movimentiallegati) {

	dataIntegration(movimentiallegati);
	if (validateEntity(movimentiallegati)) {
	    movimentiallegatiDAO.insert(movimentiallegati);
	}
    }

    @Override
    public void update(Movimentiallegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    if (codiceOggettoDaCancellare != null) {
		isDeleteAllowed(entity);
	    }
	    movimentiallegatiDAO.update(entity);
	    gestCancellaOggetto(codiceOggettoDaCancellare);
	}
    }

    private void dataIntegration(Movimentiallegati entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro movimentiallegati è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getDataregistrazione() == null) {
	    entity.setDataregistrazione(Calendar.getInstance().getTime());
	}
	if (entity.getFlagPubblica() == null && EntityUtils.getNestedProperty(entity, "movimento.id.codice") != null) {
	    Boolean pubblica = entity.getMovimento().getTipomovimento().getFlagPubblicaallegati();
	    if (pubblica == null) {
		pubblica = Boolean.FALSE;
	    }
	    entity.setFlagPubblica(pubblica);
	}
    }

    protected void fixMergeEntityProperties(Movimentiallegati entity) {

	Movimenti movimento = movimentiService.bindDomainObject(entity.getMovimento(), PkId.class, "id.codice");
	entity.setMovimento(movimento);
	populateMetadataFunzione(entity, movimento); // va prima del binddomainopbject di oggetti
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetto(), PkId.class, "id.codice");
	entity.setOggetto(oggetto);
    }

    private void populateMetadataFunzione(Movimentiallegati entity, Movimenti movimento) {

	if (movimento != null && movimento.getId() != null && movimento.getId().getCodice() != null && entity.getOggetto() != null) {
	    List<MetadatiBean> s = null;
	    if (entity.getOggetto().getMetadatiTransient() != null) {
		s = entity.getOggetto().getMetadatiTransient();
	    }
	    if (s == null) {
		s = new ArrayList<MetadatiBean>();
	    }
	    MetadatiBean mdb = new MetadatiBean();
	    mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_MOVIMENTO.getValue());
	    mdb.setValore(String.valueOf(movimento.getId().getCodice()));
	    s.add(mdb);
	    if (movimento.getIstanza() != null && movimento.getIstanza().getId() != null && movimento.getIstanza().getId().getCodice() != null) {
		mdb = new MetadatiBean();
		mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_ISTANZA.getValue());
		mdb.setValore(String.valueOf(movimento.getIstanza().getId().getCodice()));
		s.add(mdb);
	    }
	    entity.getOggetto().setMetadatiTransient(s);
	}
    }

    @Override
    public List<Movimentiallegati> findByIstanza(int codiceIstanza) {

	return movimentiallegatiDAO.findByIstanza(codiceIstanza);
    }

    @Override
    public List<Movimentiallegati> findByMovimento(int codiceMovimento) {

	return movimentiallegatiDAO.findByMovimento(codiceMovimento);
    }

    @Override
    public List<Movimentiallegati> findByMovimento(int codiceMovimento, boolean escludiAllegatiSenzaOggetto) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	if (escludiAllegatiSenzaOggetto) {
	    fr.addFilterField(FilterUtils.isNotNull("id.codice", "oggetto"));
	}
	filterTable.addRestriction(fr);
	List<Movimentiallegati> movimentiallegatis = movimentiallegatiDAO.findByFilterTable(filterTable);
	return movimentiallegatis;
    }

    @Override
    public List<Movimentiallegati> findByIstanzaOggetto(int codiceIstanza, int codicemovimento) {

	return movimentiallegatiDAO.findByIstanzaOggetto(codiceIstanza, codicemovimento);
    }

    /**
     * @param codiceOggettoDaCancellare
     */
    private void gestCancellaOggetto(Integer codiceOggettoDaCancellare) {

	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public int countByMovimento(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("countByMovimento: il parametro codiceMovimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	filterTable.addRestriction(istanza);
	return this.movimentiallegatiDAO.countRecord(filterTable);
    }

    @Override
    public List<Movimentiallegati> findProvenientiDaSTC(Integer codiceIstanza) {

	FilterTable ft = setFilterAllegatiForIstanzaAndProvenientiSTC(codiceIstanza);
	return movimentiallegatiDAO.findByFilterTable(ft);
    }

    @Override
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza) {

	FilterTable ft = setFilterAllegatiForIstanzaAndProvenientiSTC(codiceIstanza);
	if (movimentiallegatiDAO.countRecord(ft) > 0) {
	    return true;
	}
	return false;
    }

    @Override
    public void salvaDocumentoProtocollo(String idBase, Integer codiceMovimento, String descrizioneFile, boolean chekFileIsEsistente) {

	// Istanzio l'oggetto file
	Oggetti oggetto = new Oggetti();
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	// Istanzio l'oggetto Movimentiallegati
	Movimentiallegati movimentiallegati = new Movimentiallegati();
	// Recupero l'allegato dal servizio di protocollazione
	AllegatoResponseType allegatoDaSalvare = protocollazioneService.leggiAllegato(ORMHelper.getToken(), idBase,
		movimento.getIstanza().getSoftware().getCodice(), movimento.getIstanza().getComune().getCodicecomune());
	// Controllo che un file con descrizione uguale sia salvato su movimenti  allegati
	if (chekFileIsEsistente) {
	    List<Movimentiallegati> movimentiallegatis = this.findByMovimento(codiceMovimento);
	    for (Movimentiallegati movimentiallegatiTemp : movimentiallegatis) {
		if (StringUtils.isNotBlank(movimentiallegatiTemp.getIdBase()) && movimentiallegatiTemp.getIdBase().equals(idBase)) {
		    throw new RuntimeException(
			    "<br><label style=\"color: red;\">Nel movimento è già presente un file con questa descrizione</label>");
		}
	    }
	}
	// Creo il file oggetto e lo salvo su DB
	if (allegatoDaSalvare != null) {
	    byte[] fileContent = Utilities.dataHandlerToBytes(allegatoDaSalvare.getImage());
	    oggetto.setOggetto(fileContent);
	    if (fileContent == null || fileContent.length == 0) {
		throw new RuntimeException("Errore nel recupero del file dal protocollo: il file è vuoto");
	    }
	}
	oggetto.setNomefile(allegatoDaSalvare.getSerial());
	oggettiService.insert(oggetto);
	// Popolo e inserisco l'oggetto documento istanza
	movimentiallegati.setDataregistrazione((Calendar.getInstance().getTime()));
	if (StringUtils.isNotBlank(descrizioneFile)) {
	    movimentiallegati.setDescrizione(descrizioneFile);
	}
	movimentiallegati.setMovimento(movimento);
	movimentiallegati.setOggetto(oggetto);
	movimentiallegati.setIdBase(idBase);
	this.insert(movimentiallegati);
    }

    private FilterTable setFilterAllegatiForIstanzaAndProvenientiSTC(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "movimento.istanza", Integer.class));
	fr.addFilterField(FilterUtils.isNull("id.codice", "oggetto"));
	fr.addFilterField(FilterUtils.isNotNull("stcIdallegato"));
	fr.addFilterField(FilterUtils.isNotNull("stcIddocumento"));
	ft.addRestriction(fr);
	return ft;
    }

    @Override
    public boolean existsProvenientiDaSTCPerMovimenti(Integer codiceMovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	ft.addRestriction(fr);
	FilterRestriction fr1 = new FilterRestriction();
	fr1.setAndOrRestriction(AndOrRestriction.OR);
	fr1.addFilterField(FilterUtils.isNotNull("stcIdallegato"));
	fr1.addFilterField(FilterUtils.isNotNull("stcIddocumento"));
	ft.addRestriction(fr1);
	return movimentiallegatiDAO.countRecord(ft) > 0;
    }

    @Override
    public void insertTrasformaInPdf(Integer codiceMovimentoAllegato, Integer codiceOggetto) {

	PkId id = new PkId(codiceMovimentoAllegato);
	Movimentiallegati entity = this.findById(id);
	if (EntityUtils.isNestedPropertyBlank(entity.getOggetto(), "id.codice")) {
	    throw new RuntimeException("Il record di movimenti allegati non contiene file");
	}
	Oggetti convertFileInPdfAndSostituisci = oggettiService.convertFileInPdfAndSostituisci(codiceOggetto);
	entity.setOggetto(convertFileInPdfAndSostituisci);
	this.update(entity);
    }

    @Override
    public void insertTrasformaInPdf(Integer codiceMovimentoAllegato) {

	Movimentiallegati entity = this.findById(new PkId(codiceMovimentoAllegato));
	insertTrasformaInPdf(codiceMovimentoAllegato, entity.getOggetto().getId().getCodice());
    }

    @Override
    public List<Movimentiallegati> findByIstanzaAndExcludeMoviemento(Integer codiceIstanza, Integer codiceMovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "movimento.istanza", Integer.class));
	fr.addFilterField(FilterUtils.notEquals("id.codice", codiceMovimento, "movimento", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return movimentiallegatiDAO.findByFilterTable(ft);
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza) {

	return movimentiallegatiDAO.findMovimentiallegatiDTOByIstanza(codiceIstanza);
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceMovimento) {

	return movimentiallegatiDAO.findMovimentiallegatiDTOByMovimenti(codiceMovimento);
    }

    @Override
    public Movimentiallegati findByOggetto(Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", codice, "oggetto", Integer.class));
	filterTable.addRestriction(filterRestriction);
	List<Movimentiallegati> list = movimentiallegatiDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public void salvaDocumentiProtocollo(Map<Integer, String> mappaGiaSalvati, List<AllegatoResponseType> allegatos, Integer codiceMovimento) {

	for (AllegatoResponseType allegato : allegatos) {
	    String nomeAllegato = allegato.getSerial();
	    if (StringUtils.isNotBlank(allegato.getCommento())) {
		nomeAllegato = allegato.getCommento();
	    }
	    if (!mappaGiaSalvati.containsValue(nomeAllegato)) {
		this.salvaDocumentoProtocollo(allegato.getIDBase(), codiceMovimento, nomeAllegato, false);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("salvaDocumentiProtocollo# L'allegato {} è già presente", new Object[] { allegato.getSerial() });
		}
	    }
	}
    }

    @Override
    public Movimentiallegati findUltimoMovimentiallegatiConOggettoByMovimenti(Integer codiceMovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNotNull("oggettoId"));
	fr.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataregistrazione"));
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<Movimentiallegati> mas = movimentiallegatiDAO.findByFilterTable(ft, 0, 1);
	if (!mas.isEmpty()) {
	    return mas.get(0);
	}
	return null;
    }

    @Override
    public Movimentiallegati findbyMessageId(String idmessage) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("messageId", idmessage, String.class));
	ft.addRestriction(fr);
	List<Movimentiallegati> mas = movimentiallegatiDAO.findByFilterTable(ft, 0, 1);
	if (!mas.isEmpty()) {
	    return mas.get(0);
	}
	return null;
    }

    @Override
    public Movimentiallegati findMovimentiallegatiConOggettoByMovimenti(Integer codiceMovimento, Integer codiceoggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("oggettoId", codiceoggetto, Integer.class));
	fr.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	ft.addRestriction(fr);
	List<Movimentiallegati> mas = movimentiallegatiDAO.findByFilterTable(ft, 0, 1);
	if (!mas.isEmpty()) {
	    return mas.get(0);
	}
	return null;
    }

    @Override
    public Integer createDocumentoConLink(Movimenti movimento, Integer codiceLetteraTipo, DocumentiHelper documentiHelper, Boolean isZipLogico) {

	String uuid = UUID.randomUUID().toString();
	Letteretipo lettereTipo = letteretipoService.findById(new PkId(codiceLetteraTipo));
	if (!documentiHelper.getDocumentiMovimentoList().isEmpty()) {
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> list = documentiHelper.getDocumentiMovimentoList();
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : list) {
		if (!chiaveValoreBean.getValore().isEmpty()) {
		    List<MovimentiallegatiDTO> movimentiallegatiDTOs = chiaveValoreBean.getValore();
		    for (MovimentiallegatiDTO movimentiallegatiDTO : movimentiallegatiDTOs) {
			if (movimentiallegatiDTO.isTransientSegnaPerInvio() && movimentiallegatiDTO.getCodiceOggetto() != null) {
			    TempLinkallegati tempLinkallegati = populateTempLinkallegati(movimentiallegatiDTO.getCodiceOggetto(),
				    movimentiallegatiDTO.getNomeFile(), uuid, movimentiallegatiDTO.getDescrizione());
			    tempLinkallegatiService.insert(tempLinkallegati);
			}
		    }
		}
	    }
	}
	if (isZipLogico != null && isZipLogico) {
	    populateZipLogicoLink(movimento.getId().getCodice(), uuid);
	}
	if (!documentiHelper.getDocumentiAltriMovimentiList().isEmpty()) {
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> list = documentiHelper.getDocumentiAltriMovimentiList();
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : list) {
		if (!chiaveValoreBean.getValore().isEmpty()) {
		    List<MovimentiallegatiDTO> movimentiallegatiDTOs = chiaveValoreBean.getValore();
		    for (MovimentiallegatiDTO movimentiallegatiDTO : movimentiallegatiDTOs) {
			if (movimentiallegatiDTO.isTransientSegnaPerInvio() && movimentiallegatiDTO.getCodiceOggetto() != null) {
			    TempLinkallegati tempLinkallegati = populateTempLinkallegati(movimentiallegatiDTO.getCodiceOggetto(),
				    movimentiallegatiDTO.getNomeFile(), uuid, movimentiallegatiDTO.getDescrizione());
			    tempLinkallegatiService.insert(tempLinkallegati);
			}
		    }
		}
	    }
	}
	if (!documentiHelper.getDocumentiIstanzaList().isEmpty()) {
	    List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> list = documentiHelper.getDocumentiIstanzaList();
	    for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean : list) {
		if (!chiaveValoreBean.getValore().isEmpty()) {
		    List<DocumentiistanzaDTO> documentiistanzaDTOs = chiaveValoreBean.getValore();
		    for (DocumentiistanzaDTO documentiistanzaDTO : documentiistanzaDTOs) {
			if (documentiistanzaDTO.getTransientSegnaPerInvio() && documentiistanzaDTO.getCodiceOggetto() != null) {
			    TempLinkallegati tempLinkallegati = populateTempLinkallegati(documentiistanzaDTO.getCodiceOggetto(),
				    documentiistanzaDTO.getNomeFile(), uuid, documentiistanzaDTO.getDocumento());
			    tempLinkallegatiService.insert(tempLinkallegati);
			}
		    }
		}
	    }
	}
	if (!documentiHelper.getDocumentiEndoprocedimentiList().isEmpty()) {
	    List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> list = documentiHelper.getDocumentiEndoprocedimentiList();
	    for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBean : list) {
		if (!chiaveValoreBean.getValore().isEmpty()) {
		    List<IstanzeallegatiDTO> istanzeallegatiDTOs = chiaveValoreBean.getValore();
		    for (IstanzeallegatiDTO istanzeallegatiDTO : istanzeallegatiDTOs) {
			if (istanzeallegatiDTO.isTransientSegnaPerInvio() && istanzeallegatiDTO.getCodiceOggetto() != null) {
			    TempLinkallegati tempLinkallegati = populateTempLinkallegati(istanzeallegatiDTO.getCodiceOggetto(),
				    istanzeallegatiDTO.getNomeFile(), uuid, istanzeallegatiDTO.getAllegatoextra());
			    tempLinkallegatiService.insert(tempLinkallegati);
			}
		    }
		}
	    }
	}
	if (!documentiHelper.getDocumentiAnagrafeList().isEmpty()) {
	    List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> list = documentiHelper.getDocumentiAnagrafeList();
	    for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> chiaveValoreBean : list) {
		if (!chiaveValoreBean.getValore().isEmpty()) {
		    List<AnagrafedocumentiDTO> anagrafedocumentiDTOs = chiaveValoreBean.getValore();
		    for (AnagrafedocumentiDTO anagrafedocumentiDTO : anagrafedocumentiDTOs) {
			if (anagrafedocumentiDTO.isTransientSegnaPerInvio() && anagrafedocumentiDTO.getCodiceOggetto() != null) {
			    TempLinkallegati tempLinkallegati = populateTempLinkallegati(anagrafedocumentiDTO.getCodiceOggetto(),
				    anagrafedocumentiDTO.getNomeFile(), uuid, anagrafedocumentiDTO.getDocumento());
			    tempLinkallegatiService.insert(tempLinkallegati);
			}
		    }
		}
	    }
	}
	// Devo committare i link inseriti, altrimenti il servizio esterno che crea il documento non
	// li trova.
	movimentiallegatiDAO.flush();
	movimentiallegatiDAO.commit();
	log.debug("Creazione allegato dalla lettera tipo con codice {}", codiceLetteraTipo);
	Integer codiceOggettoRTF = oggettiService.creaDocumentoConLinkOggetti(lettereTipo, movimento.getIstanza().getId().getCodice(),
		movimento.getId().getCodice(), movimento.getTipomovimento().getId().getTipomovimento(), uuid);
	if (BooleanUtils.isTrue(isZipLogico)) {
	    // lego il documento alla testata dello zip logico
	    MovimentiZipLogicoTestata testata = movimentiZipLogicoService.findTestataByCodiceMovimento(movimento.getId().getCodice());
	    if (testata.getCodiceoggettoDocAll() == null) {
		movimentiZipLogicoService.updateDocAllegatoTestata(movimento.getId().getCodice(), codiceOggettoRTF);
	    } else {
		FlashMessages.getWarnings().add(
			"Il movimento, che ha configurato uno zip logico, ha già associato un documento principale e questo non è stato associato allo zip logico. È possibile verificare il documento associato allo zip logico nella funzionalità \"ZIP LOGICO\"");
	    }
	}
	Movimentiallegati movimentiallegati = this.findByOggetto(codiceOggettoRTF);
	return movimentiallegati.getId().getCodice();
    }

    @Override
    public TempLinkallegati populateTempLinkallegati(Integer codiceOggetto, String nomeFile, String UUID, String descrizioneDocumento) {

	TempLinkallegati tempLinkallegati = new TempLinkallegati();
	tempLinkallegati.setUuid(UUID);
	String link = oggettiService.creaSingoloLinkAllegati(codiceOggetto);
	tempLinkallegati.setLink(link);
	tempLinkallegati.setCodiceoggetto(codiceOggetto);
	tempLinkallegati.setPin(codiceOggetto);
	// Nuovo campo inserito
	tempLinkallegati.setNomedocumento(nomeFile);
	tempLinkallegati.setDescrizioneDocumento(descrizioneDocumento);
	return tempLinkallegati;
    }

    private void populateZipLogicoLink(Integer codicemovimento, String uuid) {

	TempLinkallegati tempLinkallegati = new TempLinkallegati();
	tempLinkallegati.setUuid(uuid);
	ZipLogicoLinkHelper link = movimentiZipLogicoService.creaLinkZipLogico(codicemovimento);
	tempLinkallegati.setLink(link.getUrl());
	tempLinkallegati.setPin(link.getPin());
	// Nuovo campo inserito
	tempLinkallegati.setNomedocumento(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_NOMEFILE);
	tempLinkallegati.setDescrizioneDocumento(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_DESCRIZIONE);
	tempLinkallegatiService.insert(tempLinkallegati);
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceIstanza, Integer codiceMovFiglio,
	    String codiceTipoMovAllegati) {

	// recupero il codice movimento.
	Integer codiceMovimento = getCodiceMovimento(codiceIstanza, codiceMovFiglio, codiceTipoMovAllegati);
	List<MovimentiallegatiDTO> list = new ArrayList<MovimentiallegatiDTO>();
	if (codiceMovimento != null) {
	    list = this.findMovimentiallegatiDTOByMovimenti(codiceMovimento);
	} else {
	    log.error(
		    "findMovimentiallegatiDTOByMovimenti# Codice movimento non trovato per Istanza {}, Movimento figlio {}, Tipo Movimento {} ricera allegati ",
		    new Object[] { codiceIstanza, codiceMovFiglio, codiceTipoMovAllegati });
	}
	return list;
    }

    private Integer getCodiceMovimento(Integer codiceIstanza, Integer codiceMovFiglio, String codiceTipoMovAllegati) {

	Integer codiceMov = null;
	if (StringUtils.isNotBlank(codiceTipoMovAllegati)) {
	    if (!codiceTipoMovAllegati.equalsIgnoreCase("MOVPADRE")) {
		// Ricerco per iltipo mov passato
		FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction fr = new FilterRestriction();
		fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
		fr.addFilterField(FilterUtils.equals("id.tipomovimento", codiceTipoMovAllegati, "tipomovimento", String.class));
		ft.addRestriction(fr);
		ft.addOrder(FilterUtils.orderDesc("data"));
		ft.addOrder(FilterUtils.orderDesc("ordineInserimento"));
		List<Movimenti> movimentis = movimentiService.findByFilterTable(ft);
		if (!movimentis.isEmpty()) {
		    codiceMov = movimentis.get(0).getId().getCodice();
		}
	    } else {
		if (codiceMovFiglio != null) {
		    movimentiService.flush();
		    Movimenti movimentiFiglio = movimentiService.findById(new PkId(codiceMovFiglio));
		    List<MovimentiContromovimenti> movPadres = movimentiContromovimentiService.findByMovimentoByFkFiglio(movimentiFiglio);
		    if (!movPadres.isEmpty()) {
			codiceMov = movPadres.get(0).getMovimentoByFkPadre().getId().getCodice();
		    }
		} else {
		    new RuntimeException("Attenzione non è stato passato il parametro codiceTipoMovFiglio: null ");
		}
	    }
	} else {
	    log.debug("getCodiceMovimento# codiceTipoMovAllegati non passato");
	}
	return codiceMov;
    }

    @Override
    public void flush() {

	movimentiallegatiDAO.flush();
    }

    @Override
    public Integer createAndInsertMovimentoAllegato(Letteretipo lettera, Integer codiceIstanza, Integer codiceMovimento) {

	Oggetti oggetto = documentMergeService.createAllegatoDaDocumentoTipo(lettera.getId().getCodice(), codiceIstanza, codiceMovimento,
		new DocumentMergeHelper());
	Movimentiallegati movimentiallegati = new Movimentiallegati();
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	movimentiallegati.setMovimento(movimento);
	movimentiallegati.setOggetto(oggetto);
	movimentiallegati.setDescrizione(lettera.getDescrizione());
	if (StringUtils.isBlank(movimentiallegati.getDescrizione())) {
	    if (oggetto != null) {
		movimentiallegati.setDescrizione(StringUtils.defaultIfEmpty(oggetto.getNomefile(),
			"Allegato generato automaticamente da lettera " + lettera.getId().getCodice()));
	    }
	}
	this.insert(movimentiallegati);
	movimentiallegatiDAO.flush();
	movimentiallegatiDAO.commit();
	return movimentiallegati.getId().getCodice();
    }

    @Override
    public Integer insertMultiFile(Movimentiallegati entity, List<MultipartFile> multipartFiles) {

	if (validateEntity(entity)) {
	    int cont = 1;
	    Movimentiallegati movimentiallegati = null;
	    String desc = StringUtils.isNotBlank(entity.getDescrizione()) ? entity.getDescrizione() : "";
	    String note = StringUtils.isNotBlank(entity.getNote()) ? entity.getNote() : "";
	    Boolean pubblica = BooleanUtils.toBoolean(entity.getFlagPubblica());
	    Integer valido = entity.getControllook() != null ? entity.getControllook() : null;
	    Movimenti movimenti = entity.getMovimento();
	    for (MultipartFile multipartFile : multipartFiles) {
		Oggetti oggetto = oggettiService.insert(multipartFile);
		movimentiallegati = new Movimentiallegati();
		String prefix = StringUtils.leftPad(String.valueOf(cont), 2, "0");
		movimentiallegati.setDescrizione(desc + " [" + prefix + "]");
		movimentiallegati.setNote(note);
		movimentiallegati.setFlagPubblica(pubblica);
		movimentiallegati.setControllook(valido);
		movimentiallegati.setOggetto(oggetto);
		movimentiallegati.setMovimento(movimenti);
		movimentiallegati.setDataregistrazione(new Date());
		this.insert(movimentiallegati);
		cont++;
	    }
	    return movimentiallegati.getId().getCodice();
	}
	return null;
    }

    @Override
    public Integer insertSingoloOrMultiFile(Movimentiallegati movimentiallegati, List<MultipartFile> lMultipartFiles) {

	Integer codice = null;
	log.debug("insertSingoloOrMultiFile# Controllo se sono in fase di inserimento singolo file (standard) o inserimento multi file");
	if (!lMultipartFiles.isEmpty()) {
	    log.debug("insertSingoloOrMultiFile# Inserimento tipo : multi file");
	    codice = this.insertMultiFile(movimentiallegati, lMultipartFiles);
	} else {
	    log.debug("insertSingoloOrMultiFile# Inserimento tipo : singolo file (modalità standard)");
	    this.insert(movimentiallegati);
	    codice = movimentiallegati.getId().getCodice();
	}
	return codice;
    }

    @Deprecated
    @Override
    public Oggetti applicaLayerProtocolloPdf(Movimenti mov, Oggetti o) throws FunzioneBusinessRemotaException, Exception {

	try {
	    Verticalizzazioniparametri vpX = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_X);
	    Verticalizzazioniparametri vpY = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_Y);
	    Verticalizzazioniparametri vpNomelayer = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_NOME_LAYER);
	    Verticalizzazioniparametri vpEtichettaNum = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_ETICHETTA_NUM_PROT);
	    Verticalizzazioniparametri vpEtichettaDat = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_ETICHETTA_DATA_PROT);
	    Verticalizzazioniparametri vpFontSize = verticalizzazioniService
		    .getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, "FONT_SIZE");
	    Verticalizzazioniparametri vpFontType = verticalizzazioniService
		    .getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, "FONT_TYPE");
	    String dataProt = "";
	    if (mov.getDataprotocollo() != null) {
		dataProt = StringUtils.defaultIfEmpty(Utilities.formatDate(mov.getDataprotocollo(), false), "");
	    }
	    String numeroProt = StringUtils.defaultIfEmpty(mov.getNumeroprotocollo(), "");
	    PdfUtilsWSClient pdfu = new PdfUtilsWSClient();
	    List<String> testo = new ArrayList<String>();
	    if (StringUtils.isNotBlank(dataProt) && StringUtils.isNotBlank(numeroProt)) {
		if (StringUtils.isNotBlank(numeroProt)) {
		    String strVpEtichettaNum = (vpEtichettaNum != null && StringUtils.isNotBlank(vpEtichettaNum.getValore())
			    ? vpEtichettaNum.getValore()
			    : "");
		    StringBuilder num = new StringBuilder(strVpEtichettaNum);
		    num.append(numeroProt);
		    testo.add(num.toString());
		}
		if (StringUtils.isNotBlank(dataProt)) {
		    String strVpEtichettaDat = (vpEtichettaDat != null && StringUtils.isNotBlank(vpEtichettaDat.getValore())
			    ? vpEtichettaDat.getValore()
			    : "");
		    StringBuilder data = new StringBuilder(strVpEtichettaDat);
		    data.append(dataProt);
		    testo.add(data.toString());
		}
	    } else {
		throw new Exception("Impossibile applicare il layer. Numero protocollo e Data Protocollo non presenti");
	    }
	    LayerPDFHelper layerPDFHelper = new LayerPDFHelper();
	    String x = (vpX != null && StringUtils.isNotBlank(vpX.getValore()) ? vpX.getValore() : "0");
	    layerPDFHelper.setPositionX(new Integer(x));
	    String y = (vpY != null && StringUtils.isNotBlank(vpY.getValore()) ? vpY.getValore() : "0");
	    layerPDFHelper.setPositionY(new Integer(y));
	    String nome = (vpNomelayer != null && StringUtils.isNotBlank(vpNomelayer.getValore()) ? vpNomelayer.getValore() : "");
	    layerPDFHelper.setNomelayer(nome);
	    String fontsize = (vpFontSize != null && StringUtils.isNotBlank(vpFontSize.getValore()) ? vpFontSize.getValore() : "");
	    layerPDFHelper.setDimensionFont(new Integer(fontsize));
	    String fonttype = (vpFontType != null && StringUtils.isNotBlank(vpFontType.getValore()) ? vpFontType.getValore() : "");
	    layerPDFHelper.setTypeFont(fonttype);
	    DataHandler dh = pdfu.addLayerPDF(testo, o, layerPDFHelper);
	    byte[] b = Utilities.dataHandlerToBytes(dh);
	    o.setOggetto(b);
	} catch (InvalidConfigurationException e) {
	    log.error("applicaLayerProtocolloPdf#", e);
	    throw new RuntimeException(e.getMessage());
	} catch (FunzioneBusinessRemotaException e1) {
	    log.error("applicaLayerProtocolloPdf#", e1);
	    throw new RuntimeException(e1.getMessage());
	} catch (Exception e2) {
	    log.error("applicaLayerProtocolloPdf#", e2);
	    throw new RuntimeException(e2.getMessage());
	}
	return o;
    }

    @Override
    public void insertCreaAllegatoXmlDomandaSuapRegistroImprese(Integer codiceMovimento) {

	try {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
	    Integer codiceIstanza = movimento.getIstanza().getId().getCodice();
	    byte[] bytes = this.suapXmlService.generaSuapXML(codiceComune, codiceIstanza);
	    if (bytes.length == 0) {
		return;
	    }
	    Movimentiallegati movimentiallegati = new Movimentiallegati();
	    // pratica_suap_[yyyddgg].xml
	    String nomeFile = SuapXmlServiceImpl.PREFIX_NAME_PRATICA_XML_SUAP + Utilities.formatDate(new Date(), "yyyyMMdd");
	    movimentiallegati.setMovimento(movimento);
	    movimentiallegati.setDescrizione(nomeFile);
	    movimentiallegati.setNote(SuapXmlServiceImpl.ANNOTAZIONI_DOCUMENTO);
	    Oggetti oggetto = new Oggetti();
	    oggetto.setDimensioneFile(bytes.length);
	    oggetto.setNonUsareContenutoBLOB(bytes);
	    oggetto.setNomefile(nomeFile + ".xml");
	    oggettiService.insert(oggetto);
	    movimentiallegati.setOggetto(oggetto);
	    this.insert(movimentiallegati);
	} catch (Exception e) {
	    log.error("generaPraticaXml# Errore", e);
	    throw new RuntimeException("Impossibile generare l'xml della domanda: " + e);
	}
    }

    private String getValoreODefault(String modulo, String parametro, String valoreDefault) {

	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(modulo, parametro);
	if (vp != null && vp.getValore() != null) {
	    String valore = StringUtils.defaultIfEmpty(vp.getValore(), "").trim();
	    if (StringUtils.isNotBlank(valore)) {
		return valore;
	    }
	}
	return valoreDefault;
    }

    @Override
    public void updateApplicaQRCode(Integer codiceMovimentiAllegati) {

	Movimentiallegati mova = this.findById(new PkId(codiceMovimentiAllegati));
	Oggetti o = oggettiService.findById(new PkId(mova.getOggetto().getId().getCodice()));
	if (o.getNomefile().toLowerCase().endsWith(".pdf")) {
	    String url = getValoreODefault(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_URL_DOWNLOAD, "");
	    String width = getValoreODefault(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_WIDTH, "150");
	    String heigth = getValoreODefault(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_HEIGHT, "150");
	    String posX = getValoreODefault(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_POS_X, "100");
	    String posY = getValoreODefault(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_POS_Y, "100");
	    String pageNum = getValoreODefault(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_PAGE_NUM, "1");
	    String guiid = oggettiService.insertOrGetUID(o.getId().getCodice());
	    String urlContent = url + ORMHelper.getIdcomuneAlias() + "/" + ORMHelper.getSoftware() + "/" + guiid;
	    byte[] qrcode = qrcodeService.createQRcode(urlContent, Integer.valueOf(width), Integer.valueOf(heigth), TipoImmagine.PNG);
	    Integer page = Integer.parseInt(pageNum);
	    byte[] pdfModificato = manipulatePdfService.addImageToPDF(o.getOggetto(), qrcode, page, Integer.valueOf(posX), Integer.valueOf(posY));
	    o.setOggetto(pdfModificato);
	    oggettiService.update(o);
	}
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto) {

	return movimentiallegatiDAO.findMovimentiallegatiDTOByIstanza(codiceIstanza, isCodiceOggetto);
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanzaNonInDocAutorizzazione(Integer codiceIstanza, Integer codiceAut) {

	List<MovimentiallegatiDTO> listMovimentiallegatiDTOs = this.findMovimentiallegatiDTOByIstanza(codiceIstanza, true);
	List<MovimentiallegatiDTO> risultato = new ArrayList<MovimentiallegatiDTO>();
	for (MovimentiallegatiDTO movimentiallegatiDTO : listMovimentiallegatiDTOs) {
	    // controllo che per l'autorizzazione passata il movimento allegato non sia già stato associato all'aut tramite doc autorizzazioni
	    boolean isPresente = documentiautorizzazioneService.isInAutorizzazione(codiceAut, movimentiallegatiDTO.getId().getCodice(),
		    "movimentiallegati");
	    log.debug(
		    "findMovimentiallegatiDTOByIstanzaNonInDocAutorizzazione# Aut = {},codice mov allegato = {}, presente in Documentiautorizzazione = {}",
		    new Object[] { codiceAut, movimentiallegatiDTO.getId().getCodice(), isPresente });
	    if (!isPresente) {
		risultato.add(movimentiallegatiDTO);
	    }
	}
	return risultato;
    }

    @Override
    public boolean isPresenteInDocAut(Integer codiceMovAllegato, Integer codiceAutorizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceMovAllegato, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.idautorizzazione", codiceAutorizzazione, "documentiAutorizzaziones", Integer.class));
	ft.addRestriction(fr);
	return movimentiallegatiDAO.existsRecords(ft);
    }

    @Override
    public List<Movimentiallegati> findListByOggetto(Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", codice, "oggetto", Integer.class));
	filterTable.addRestriction(filterRestriction);
	return movimentiallegatiDAO.findByFilterTable(filterTable);
    }

    @Override
    public Set<String> findSoftwareByCodiceOggetto(Integer codiceOggetto) {

	List<Movimentiallegati> findListByOggetto = this.findListByOggetto(codiceOggetto);
	Set<String> softwaresOggetti = new HashSet<String>();
	for (Movimentiallegati movimentiallegati : findListByOggetto) {
	    softwaresOggetti.add(movimentiallegati.getMovimento().getIstanza().getSoftware().getCodice());
	}
	return softwaresOggetti;
    }

    @Override
    public Oggetti applicaAnnotazioneProtocolloPdf(Istanze istanza, Movimenti mov, Oggetti o) {

	AnnotazionePropertyPdfHelper annotazionePropertyPdfHelper = new AnnotazionePropertyPdfHelper(verticalizzazioniService);
	Mailtipo mailtipo = null;
	if (annotazionePropertyPdfHelper.getCodiceMailTipo() != null || annotazionePropertyPdfHelper.getCodiceMailTipoMovimento() != null) {
	    mailtipo = istanza != null ? mailtipoService.findById(new PkId(annotazionePropertyPdfHelper.getCodiceMailTipo()))
		    : mailtipoService.findById(new PkId(annotazionePropertyPdfHelper.getCodiceMailTipoMovimento()));
	    if (EntityUtils.getNestedProperty(mailtipo, "id.codice") != null) {
		mailtipo = mailtipoService.replaceOggettoCorpo(mailtipo, istanza, mov);
		annotazionePropertyPdfHelper.setAnnotatazione(mailtipo.getOggetto());
	    }
	}
	annotazionePropertyPdfHelper.setFilePdf(o.getOggetto());
	byte[] b = manipulatePdfService.addAnnotation(annotazionePropertyPdfHelper);
	o.setOggetto(b);
	return o;
    }
}
