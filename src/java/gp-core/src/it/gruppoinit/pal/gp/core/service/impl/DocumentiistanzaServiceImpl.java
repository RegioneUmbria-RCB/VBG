package it.gruppoinit.pal.gp.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.core.Response;
import javax.xml.ws.BindingProvider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.DocumentiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCompareHelper;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoComparePropertiesHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.ICommissioniDocumentiPraticheService;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione.ESITO;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws.BinaryFile;
import it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws.RiepilogoPraticaSoap;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiStoricoService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumenticatService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.SoftwareByCodiceOggettoReaderService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.DocumentiIstanzaRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.MetadatiFunzioneEnum;
import it.gruppoinit.pal.gp.core.service.helper.RiepilogoHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoPratica;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoType;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;

@Service
public class DocumentiistanzaServiceImpl extends BaseServiceImpl<Documentiistanza, PkId>
	implements DocumentiistanzaService, SoftwareByCodiceOggettoReaderService {

    private static final String METADATO_RIEPILOGO_ATTUALIZZATO = "XX_Riepilogo_Attualizzato";
    private static final String RIEPILOGO_ATTUALIZZATO_DELLA_PRATICA = "Riepilogo attualizzato della pratica";
    private static final Logger log = LoggerFactory.getLogger(DocumentiistanzaServiceImpl.class);
    private DocumentiistanzaDAO documentiistanzaDAO;
    private IstanzeService istanzeService;
    private OggettiService oggettiService;
    private AlberoprocDocumenticatService alberoprocDocumenticatService;
    private ProtocollazioneService protocollazioneService;
    private DocumentiHelperService documentiHelperService;
    private OggettiMetadatiService oggettiMetadatiService;
    private OggettiStoricoService oggettiStoricoService;
    private DocumentiAutorizzazioneService documentiautorizzazioneService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setCommissioniDocumentiPraticheService(ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService) {

	this.commissioniDocumentiPraticheService = commissioniDocumentiPraticheService;
    }

    @Autowired
    public void setOggettiStoricoService(OggettiStoricoService oggettiStoricoService) {

	this.oggettiStoricoService = oggettiStoricoService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Autowired
    public void setDocumentiistanzaDAO(DocumentiistanzaDAO documentiistanzaDAO) {

	this.documentiistanzaDAO = documentiistanzaDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setAlberoprocDocumenticatService(AlberoprocDocumenticatService alberoprocDocumenticatService) {

	this.alberoprocDocumenticatService = alberoprocDocumenticatService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setDocumentiHelperService(DocumentiHelperService documentiHelperService) {

	this.documentiHelperService = documentiHelperService;
    }

    @Autowired
    public void setDocumentiautorizzazioneService(DocumentiAutorizzazioneService documentiautorizzazioneService) {

	this.documentiautorizzazioneService = documentiautorizzazioneService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Override
    public void delete(Documentiistanza entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    documentiistanzaDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<Documentiistanza> findAll(Integer firstResult, Integer maxResult) {

	return documentiistanzaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Documentiistanza findById(PkId id) {

	return documentiistanzaDAO.findById(id);
    }

    @Override
    public void insert(Documentiistanza entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    documentiistanzaDAO.insert(entity);
	}
    }

    @Override
    public void update(Documentiistanza entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    documentiistanzaDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    protected Class<Documentiistanza> getEntityClass() {

	return Documentiistanza.class;
    }

    @Override
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza) {

	return documentiistanzaDAO.findByIstanza(codiceIstanza);
    }

    @Override
    public List<Documentiistanza> findByIstanzaOggetto(Integer codiceistanza) {

	return documentiistanzaDAO.findByIstanzaOggetto(codiceistanza);
    }

    private void dataIntegration(Documentiistanza entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Documento passato è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getNecessario() == null) {
	    entity.setNecessario(Boolean.FALSE);
	}
	if (EntityUtils.getNestedProperty(entity.getOggetto(), "id.codice") != null) {
	    entity.setPresente(Boolean.TRUE);
	    // Controllo se è stata passata la descrizione : campo documento. Se vuoto metto come
	    // valore il nome del file
	    if (StringUtils.isBlank(entity.getDocumento())) {
		entity.setDocumento(entity.getOggetto().getNomefile());
	    }
	}
	if (entity.getPresente() == null) {
	    entity.setPresente(Boolean.FALSE);
	}
	if (entity.getFlgDaModelloDinamico() == null) {
	    entity.setFlgDaModelloDinamico(Boolean.FALSE);
	}
    }

    protected void fixMergeEntityProperties(Documentiistanza entity) {

	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanza);
	populateMetadataFunzione(entity, istanza); // VA MESSO PRIMA DELLA BINDDOMAINOBJECT DI UN OGGETTO
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetto(), PkId.class, "id.codice");
	entity.setOggetto(oggetto);
	AlberoprocDocumenticat alberoprocDocumenticat = alberoprocDocumenticatService.bindDomainObject(entity.getAlberoprocDocumenticat(), PkId.class,
		"id.codice");
	entity.setAlberoprocDocumenticat(alberoprocDocumenticat);
    }

    private void populateMetadataFunzione(Documentiistanza entity, Istanze istanza) {

	if (istanza != null) {
	    if (istanza.getId() != null) {
		if (istanza.getId().getCodice() != null) {
		    if (entity.getOggetto() != null) {
			List<MetadatiBean> s = null;
			if (entity.getOggetto().getMetadatiTransient() != null) {
			    s = entity.getOggetto().getMetadatiTransient();
			}
			if (s == null) {
			    s = new ArrayList<MetadatiBean>();
			}
			MetadatiBean mdb = new MetadatiBean();
			mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_ISTANZA.getValue());
			mdb.setValore(String.valueOf(istanza.getId().getCodice()));
			s.add(mdb);
			entity.getOggetto().setMetadatiTransient(s);
		    }
		}
	    }
	}
    }

    //    private Integer controllaCancellaOggetti(Documentiistanza entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggetto())) {
    //	    if (!(null == entity.getOggetto().getId())) {
    //		if (!(null == entity.getOggetto().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggetto().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("DOCUMENTIISTANZA", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Documentiistanza entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// se non è nullo allora sono in modifica
    //		// in inserimento non devo fare il controllo
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggetto())) {
    //		    if (!(null == entityCopy.getOggetto().getId())) {
    //			if (!(null == entityCopy.getOggetto().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggetto().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("DOCUMENTIISTANZA", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    @Override
    protected boolean isDeleteAllowed(Documentiistanza entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	boolean isExistInDocumentiAutorizzazione = documentiautorizzazioneService.isInAutorizzazione(entity.getId().getCodice(), "documentiistanza");
	if (isExistInDocumentiAutorizzazione) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DOCUMENTI_AUTORIZZAZIONE", null));
	}
	boolean isExistEntityInMovimentiZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(entity.getId().getCodice(),
		"documentiistanza");
	if (isExistEntityInMovimentiZipLogico) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI_ZIP_LOGICO", null));
	}
	if (commissioniDocumentiPraticheService.existsByDocumentiIstanza(entity.getId().getCodice())) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null,
		    "COMMEDR_DOCIST: è stato selezionato come Allegato delle commissioni / conferenze", null));
	    delete = false;
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("countByIstanza: il parametro codiceIstanza e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	filterTable.addRestriction(istanza);
	int count = documentiistanzaDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public void deleteDocumentiistanzas(List<Documentiistanza> documentiistanzas) {

	if (documentiistanzas == null) {
	    throw new IllegalArgumentException("La lista dei documentiistanza e' nulla");
	}
	if (documentiistanzas.isEmpty()) {
	    throw new IllegalArgumentException("Non è stato selezionato alcun documento da cancellare ");
	}
	for (Documentiistanza documentiistanza : documentiistanzas) {
	    this.delete(documentiistanza);
	}
    }

    @Override
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico) {

	List<Documentiistanza> documentiistanzaList = new ArrayList<Documentiistanza>();
	List<Documentiistanza> documentiistanzas = findByIstanza(codiceIstanza);
	for (Documentiistanza documentiistanza : documentiistanzas) {
	    if (flgDaModelloDinamico.equals(Boolean.FALSE)) {
		if (!BooleanUtils.isTrue(documentiistanza.getFlgDaModelloDinamico())) {
		    documentiistanzaList.add(documentiistanza);
		}
	    }
	    if (flgDaModelloDinamico.equals(Boolean.TRUE)) {
		if (BooleanUtils.isTrue(documentiistanza.getFlgDaModelloDinamico())) {
		    documentiistanzaList.add(documentiistanza);
		}
	    }
	}
	return documentiistanzaList;
    }

    @Override
    public List<Documentiistanza> findProvenientiDaSTC(Integer codiceIstanza) {

	FilterTable ft = setFilterAllegatiForIstanzaAndProvenientiSTC(codiceIstanza);
	return documentiistanzaDAO.findByFilterTable(ft);
    }

    @Override
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza) {

	FilterTable ft = setFilterAllegatiForIstanzaAndProvenientiSTC(codiceIstanza);
	if (documentiistanzaDAO.countRecord(ft) > 0) {
	    return true;
	}
	return false;
    }

    private FilterTable setFilterAllegatiForIstanzaAndProvenientiSTC(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.isNull("id.codice", "oggetto"));
	fr.addFilterField(FilterUtils.isNotNull("stcIdallegato"));
	fr.addFilterField(FilterUtils.isNotNull("stcIddocumento"));
	ft.addRestriction(fr);
	return ft;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.DocumentiistanzaService#findByNome(java.lang.Integer, java.lang.String, it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum)
     */
    @Override
    public List<Documentiistanza> findByNome(Integer codiceIstanza, String nameSearch, FieldOperationsEnum searchMode) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("id.codice", "oggetto"));
	if (searchMode == null) {
	    searchMode = FieldOperationsEnum.EQ;
	}
	FilterField<String> nameFilter = new FilterField<String>("nomefile", "oggetto", searchMode, new String[] { nameSearch }, String.class);
	fr.addFilterField(nameFilter);
	ft.addOrder(FilterUtils.order("data", OrderTypeEnum.DESC));
	return documentiistanzaDAO.findByFilterTable(ft);
    }

    @Override
    public void salvaDocumentoProtocollo(String idBase, Integer codiceIstanza, String descrizioneFile, boolean chekFileIsEsistente) {

	// Istanzio l'oggetto file
	Oggetti oggetto = new Oggetti();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	// Istanzio l'oggetto documentiistanza
	Documentiistanza documentiistanza = new Documentiistanza();
	// Recupero l'allegato dal servizio di protocollazione
	AllegatoResponseType allegatoDaSalvare = protocollazioneService.leggiAllegato(ORMHelper.getToken(), idBase, istanza.getSoftware().getCodice(),
		istanza.getComune().getCodicecomune());
	// Controllo che un file con descrizione uguale sia salvato su documenti istanza
	if (chekFileIsEsistente) {
	    List<Documentiistanza> documentiistanzas = this.findByIstanzaOggetto(codiceIstanza);
	    for (Documentiistanza documentiistanzaTemp : documentiistanzas) {
		if (StringUtils.isNotBlank(documentiistanzaTemp.getIdBase()) && documentiistanzaTemp.getIdBase().equals(idBase)) {
		    throw new RuntimeException(
			    "<br><label style=\"color: red;\">Nei documenti dell'istanza è già presente un file con questa descrizione</label>");
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
	documentiistanza.setData(Calendar.getInstance().getTime());
	if (StringUtils.isNotBlank(descrizioneFile)) {
	    documentiistanza.setDocumento(descrizioneFile);
	}
	documentiistanza.setIstanza(istanza);
	documentiistanza.setOggetto(oggetto);
	documentiistanza.setPresente(true);
	documentiistanza.setIdBase(idBase);
	this.insert(documentiistanza);
    }

    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico) {

	return documentiistanzaDAO.findDocumentiistanzaDTOByIstanza(codiceIstanza, flgDaModelloDinamico);
    }

    @Override
    public void salvaDocumentiProtocollo(Map<Integer, String> mappaGiaSalvati, List<AllegatoResponseType> allegatos, Integer codiceIstanza) {

	for (AllegatoResponseType allegato : allegatos) {
	    String nomeAllegato = allegato.getSerial();
	    if (StringUtils.isNotBlank(allegato.getCommento())) {
		nomeAllegato = allegato.getCommento();
	    }
	    if (!mappaGiaSalvati.containsValue(nomeAllegato)) {
		this.salvaDocumentoProtocollo(allegato.getIDBase(), codiceIstanza, nomeAllegato, false);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("salvaDocumentiProtocollo# L'allegato {} è già presente", new Object[] { allegato.getSerial() });
		}
	    }
	}
    }

    @Override
    public void updatePresente(Integer codiceDocIstanza, Boolean isCheked) {

	documentiistanzaDAO.updatePresente(codiceDocIstanza, isCheked);
    }

    @Override
    public void updateNecessario(Integer codiceDocIstanza, boolean necessario) {

	documentiistanzaDAO.updateNecessario(codiceDocIstanza, necessario);
    }

    @Override
    public void updateAllineaDocumenti(Istanze istanza, List<CambioInterventoCompareHelper> docs) {

	//	List<CambioInterventoCompareHelper> docs = cambioInterventoCommand.getDocs();
	List<Documentiistanza> docsi = this.findByIstanza(istanza.getId().getCodice(), false);
	if (!docs.isEmpty()) {
	    Set<Integer> docDaRimuovere = new TreeSet<Integer>();
	    for (CambioInterventoCompareHelper doc : docs) {
		CambioInterventoComparePropertiesHelper attuale = doc.getAttuale();
		CambioInterventoComparePropertiesHelper nuovo = doc.getNuovo();
		boolean rimuovi = false;
		if (attuale.isPresente()) {
		    if (!attuale.isChecked()) {
			// rimuovo da documenti istanza quello presente		    
			rimuovi = true;
		    }
		}
		boolean aggiungi = false;
		if (nuovo.isPresente()) {
		    if (nuovo.isChecked()) {
			aggiungi = true;
		    }
		}
		if (rimuovi && !aggiungi) {
		    // rimuovo dall'istanza
		    for (Documentiistanza documentiistanza : docsi) {
			if (documentiistanza.getDocumento().equalsIgnoreCase(doc.getDescrizione())) {
			    docDaRimuovere.add(documentiistanza.getId().getCodice());
			}
		    }
		} else if (aggiungi) {
		    // aggiungi
		    // verifico se l'old esiste ed è checcato. in questo caso non lo devo inserire
		    if (attuale.isPresente()) {
			if (attuale.isChecked()) {
			    aggiungi = false;
			}
		    }
		    if (aggiungi) {
			Documentiistanza nuovoDoc = new Documentiistanza();
			nuovoDoc.setDocumento(doc.getCodice());
			nuovoDoc.setIstanza(istanza);
			nuovoDoc.setFlgDaModelloDinamico(Boolean.FALSE);
			nuovoDoc.setNecessario(doc.isDocumentoRichiesto());
			this.insert(nuovoDoc);
		    }
		}
	    }
	    // elimino i documenti da rimuovere senza codiceoggetto e riferimenti a STC
	    if (!docDaRimuovere.isEmpty()) {
		for (Integer codDocIstanza : docDaRimuovere) {
		    Documentiistanza doc = this.findById(new PkId(codDocIstanza));
		    boolean rimuovi = true;
		    if (doc.getOggetto() != null) {
			if (doc.getOggetto().getId() != null) {
			    if (doc.getOggetto().getId().getCodice() != null) {
				rimuovi = false;
			    }
			}
		    }
		    if (StringUtils.isNotBlank(doc.getStcIddocumento())) {
			rimuovi = false;
		    }
		    if (rimuovi) {
			this.delete(doc);
		    }
		}
	    }
	}
    }

    @Override
    public void insertAllegatoInIstanza(Integer codiceIst, Integer codOggetto) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIst));
	Oggetti oggetto = oggettiService.findById(new PkId(codOggetto));
	Documentiistanza documentiistanza = new Documentiistanza();
	documentiistanza.setIstanza(istanza);
	documentiistanza.setOggetto(oggetto);
	documentiistanza.setData(new Date());
	documentiistanza.setPresente(true);
	documentiistanza.setNote("Allegato salvato durante la visura");
	this.insert(documentiistanza);
    }

    public void leggiDocumentiDaFileSystem() {

    }

    @Override
    public ByteArrayOutputStream downloadDocumentiZip(DocumentiHelper documentiHelper) {

	// Recuperare i file da comprimere nel all'archivio zip
	documentiHelper = documentiHelperService.findDocumentiInvioTrue(documentiHelper);
	Set<Integer> oggettis = new HashSet<Integer>();
	// Bonifico la lista dei documenti del movimento
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _movDocChiaveValoreBeans = documentiHelper.getDocumentiMovimentoList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : _movDocChiaveValoreBeans) {
	    List<MovimentiallegatiDTO> movimentiallegatis = chiaveValoreBean.getValore();
	    for (MovimentiallegatiDTO movimentiallegati : movimentiallegatis) {
		oggettis.add(movimentiallegati.getCodiceOggetto());
	    }
	}
	// Bonifico la lista dei documenti di altri mov
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _altriMovDocChiaveValoreBeans = documentiHelper.getDocumentiAltriMovimentiList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : _altriMovDocChiaveValoreBeans) {
	    List<MovimentiallegatiDTO> movimentiallegatis = chiaveValoreBean.getValore();
	    for (MovimentiallegatiDTO movimentiallegati : movimentiallegatis) {
		oggettis.add(movimentiallegati.getCodiceOggetto());
	    }
	}
	// Bonifico lista documenti istanza
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> _istanzaDocChiaveValoreBeans = documentiHelper.getDocumentiIstanzaList();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean : _istanzaDocChiaveValoreBeans) {
	    List<DocumentiistanzaDTO> documentiistanzas = chiaveValoreBean.getValore();
	    for (DocumentiistanzaDTO documentiistanza : documentiistanzas) {
		oggettis.add(documentiistanza.getCodiceOggetto());
	    }
	}
	// Bonifico lista documenti endo
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> _endoDocChiaveValoreBeans = documentiHelper.getDocumentiEndoprocedimentiList();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBean : _endoDocChiaveValoreBeans) {
	    List<IstanzeallegatiDTO> istanzeallegatis = chiaveValoreBean.getValore();
	    for (IstanzeallegatiDTO istanzeallegati : istanzeallegatis) {
		oggettis.add(istanzeallegati.getCodiceOggetto());
	    }
	}
	// Bonifico lista documenti anagrafiche
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> _anagrafeDocChiaveValoreBeans = documentiHelper.getDocumentiAnagrafeList();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> chiaveValoreBean : _anagrafeDocChiaveValoreBeans) {
	    List<AnagrafedocumentiDTO> anagrafedocumentis = chiaveValoreBean.getValore();
	    for (AnagrafedocumentiDTO anagrafedocumenti : anagrafedocumentis) {
		oggettis.add(anagrafedocumenti.getCodiceOggetto());
	    }
	}
	// Bonifico la lista documenti delle procure
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> _procureDocChiaveValoreBeans = documentiHelper.getIstanzeprocureList();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> chiaveValoreBean : _procureDocChiaveValoreBeans) {
	    List<IstanzeprocureDTO> istanzeprocures = chiaveValoreBean.getValore();
	    for (IstanzeprocureDTO istanzeprocureDTO : istanzeprocures) {
		oggettis.add(istanzeprocureDTO.getCodiceOggetto());
	    }
	}
	// Bonifico la lista documenti delle cds
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> _cdsDocChiaveValoreBeans = documentiHelper.getCdsattiList();
	for (ChiaveValoreBean<String, List<CdsattiDTO>> chiaveValoreBean : _cdsDocChiaveValoreBeans) {
	    List<CdsattiDTO> cdsattis = chiaveValoreBean.getValore();
	    for (CdsattiDTO cdsdto : cdsattis) {
		oggettis.add(cdsdto.getCodiceoggetto());
	    }
	}
	log.debug("downloadDocumentiZip# Creao l'archivio");
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ZipOutputStream zos = new ZipOutputStream(baos);
	List<String> nomeFileDuplicati = new ArrayList<String>();
	try {
	    int i = 1;
	    for (Integer o : oggettis) {
		Oggetti oggetti = oggettiService.findByIdLazy(new PkId(o));
		String nomeFile = oggetti.getNomefile();
		if (nomeFileDuplicati.contains(nomeFile)) {
		    nomeFile = i + "_" + nomeFile;
		    i++;
		}
		nomeFileDuplicati.add(oggetti.getNomefile());
		InputStream oggettoAsInputStream = oggettiService.getOggettoAsInputStream(o);
		Utilities.writeZipEntries(oggettoAsInputStream, zos, nomeFile);
	    }
	    zos.closeEntry();
	    zos.close();
	} catch (IOException e) {
	    log.error("downloadDocumentiZip#Errore durantela creazione delll'archivio zip: {}", e.getMessage());
	}
	return baos;
    }

    @Override
    public List<Documentiistanza> findByIstanzaAndOggetto(Integer codiceIstanza, Integer codiceOggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceOggetto, "oggetto", Integer.class));
	//	fr.addFilterField(FilterUtils.isNotNull("stcIddocumento"));
	//	fr.addFilterField(FilterUtils.isNotNull("stcIdallegato"));
	ft.addRestriction(fr);
	return documentiistanzaDAO.findByFilterTable(ft);
    }

    @Override
    public Integer insertMultiFile(Documentiistanza entity, List<MultipartFile> multipartFiles) {

	if (validateEntity(entity)) {
	    int cont = 1;
	    Documentiistanza documentiistanza = null;
	    Date data = entity.getData() != null ? entity.getData() : null;
	    String desc = StringUtils.isNotBlank(entity.getDocumento()) ? entity.getDocumento() : "";
	    String note = StringUtils.isNotBlank(entity.getNote()) ? entity.getNote() : "";
	    Boolean richiesto = BooleanUtils.toBoolean(entity.getNecessario());
	    Boolean presente = BooleanUtils.toBoolean(entity.getPresente());
	    Integer valido = entity.getControllook() != null ? entity.getControllook() : null;
	    Istanze istanza = entity.getIstanza();
	    for (MultipartFile multipartFile : multipartFiles) {
		Oggetti oggetto = oggettiService.insert(multipartFile);
		documentiistanza = new Documentiistanza();
		documentiistanza.setData(data);
		String prefix = StringUtils.leftPad(String.valueOf(cont), 2, "0");
		documentiistanza.setDocumento(desc + " [" + prefix + "]");
		documentiistanza.setNote(note);
		documentiistanza.setNecessario(richiesto);
		documentiistanza.setPresente(presente);
		documentiistanza.setControllook(valido);
		documentiistanza.setOggetto(oggetto);
		documentiistanza.setIstanza(istanza);
		this.insert(documentiistanza);
		cont++;
	    }
	    return documentiistanza.getId().getCodice();
	}
	return null;
    }

    @Override
    public Integer insertSingoloOrMultiFile(Documentiistanza entity, List<MultipartFile> multipartFiles) {

	Integer codice = null;
	log.debug("insertSingoloOrMultiFile# Controllo se sono in fase di inserimento singolo file (standard) o inserimento multi file");
	if (!multipartFiles.isEmpty()) {
	    log.debug("insertSingoloOrMultiFile# Inserimento tipo : multi file");
	    codice = this.insertMultiFile(entity, multipartFiles);
	} else {
	    log.debug("insertSingoloOrMultiFile# Inserimento tipo : singolo file (modalità standard)");
	    this.insert(entity);
	    codice = entity.getId().getCodice();
	}
	return codice;
    }

    @Override
    public Documentiistanza updateAggiornaRiepilogo(Istanze istanza, byte[] content, String nomeFile) {

	List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza = findDocumentiistanzaDTOByIstanza(istanza.getId().getCodice(), null);
	Integer oggettoTrovato = null;
	Integer dis = null;
	log.debug("updateAggiornaRiepilogo: istanza {}, nomeFile {}", istanza.getId().getCodice(), nomeFile);
	for (DocumentiistanzaDTO di : findDocumentiistanzaDTOByIstanza) {
	    if (di.getCodiceOggetto() != null) {
		List<OggettiMetadati> list = oggettiMetadatiService.findByOggetto(di.getCodiceOggetto(), METADATO_RIEPILOGO_ATTUALIZZATO);
		if (!list.isEmpty()) {
		    oggettoTrovato = di.getCodiceOggetto();
		    dis = di.getId().getCodice();
		}
	    }
	}
	log.debug("updateAggiornaRiepilogo: istanza {}, oggettoTrovato {}", istanza.getId().getCodice(), oggettoTrovato);
	Oggetti oggetto = new Oggetti();
	oggetto.setOggetto(content);
	oggetto.setNomefile(nomeFile);
	List<MetadatiBean> metadati = new ArrayList<MetadatiBean>();
	MetadatiBean mdb = new MetadatiBean();
	mdb.setChiave(METADATO_RIEPILOGO_ATTUALIZZATO);
	mdb.setValore(RIEPILOGO_ATTUALIZZATO_DELLA_PRATICA);
	metadati.add(mdb);
	mdb = new MetadatiBean();
	mdb.setChiave(WebConstants.OGGETTI_METADATI_TIPODOCUMENTO);
	mdb.setValore(TipoDocumentoType.RIEPILOGO_DOMANDA.value());
	metadati.add(mdb);
	oggettiService.insert(oggetto);
	log.debug("updateAggiornaRiepilogo: istanza {}, oggetto inserito {}", istanza.getId().getCodice(), oggetto.getId().getCodice());
	oggettiMetadatiService.insertMetadatiPerOggetto(oggetto.getId().getCodice(), metadati);
	log.debug("updateAggiornaRiepilogo: istanza {}, oggetti metadati inseriti per il codiceoggetto {}", istanza.getId().getCodice(),
		oggetto.getId().getCodice());
	if (oggettoTrovato == null) {
	    Documentiistanza entity = new Documentiistanza();
	    entity.setIstanza(istanza);
	    entity.setOggetto(oggetto);
	    entity.setPresente(Boolean.TRUE);
	    entity.setData(new Date());
	    entity.setDocumento(RIEPILOGO_ATTUALIZZATO_DELLA_PRATICA);
	    this.insert(entity);
	    log.debug("updateAggiornaRiepilogo: istanza {},  documenti istanza inserito {}", istanza.getId().getCodice(), entity.getId().getCodice());
	    return entity;
	} else {
	    oggettiStoricoService.updateSostituisciOggetto(istanza.getId().getCodice(), oggettoTrovato, oggetto.getId().getCodice(),
		    TipoDocumentoPratica.DOC_ISTANZA);
	    Documentiistanza dist = this.findById(new PkId(dis));
	    dist.setData(new Date());
	    this.update(dist);
	    log.debug("updateAggiornaRiepilogo: istanza {}, documenti istanza aggiornato {}", istanza.getId().getCodice(), dist.getId().getCodice());
	    return dist;
	}
    }

    @Override
    public Integer findOggettoArConsole(String valoreDaRicercare, Integer codiceIstanza) {

	Integer result = null;
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equalsIgnoreCase("stcIddocumento", valoreDaRicercare));
	fr.addFilterField(FilterUtils.isNotNull("oggettoId"));
	ft.addRestriction(fr);
	List<Documentiistanza> res = documentiistanzaDAO.findByFilterTable(ft);
	for (Documentiistanza d : res) {
	    if (d.getOggetto() != null && d.getOggetto().getId() != null && d.getOggetto().getId().getCodice() != null) {
		return d.getOggetto().getId().getCodice();
	    }
	}
	return result;
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico, Boolean isCodiceOggetto) {

	return documentiistanzaDAO.findDocumentiistanzaDTOByIstanza(codiceIstanza, flgDaModelloDinamico, isCodiceOggetto);
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza) {

	return documentiistanzaDAO.findDocumentiistanzaDTOByIstanza(codiceIstanza);
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanzaNonInDocAutorizzazione(Integer codiceistanza, Integer codiceautorizzazione) {

	List<DocumentiistanzaDTO> listDocumentiistanzaDTOs = this.findDocumentiistanzaDTOByIstanza(codiceistanza, //
		null, // DEVE ESSERE NULLO PERCHE' DEVE RECUPERARE ANCHE I DOC SALVATI SU DATI DINAMICI  
		true);
	List<DocumentiistanzaDTO> risultato = new ArrayList<DocumentiistanzaDTO>();
	for (DocumentiistanzaDTO documentiistanzaDTO : listDocumentiistanzaDTOs) {
	    // controllo che per l'autorizzazione passata il movimento allegato non sia già stato associato all'aut tramite doc autorizzazioni
	    boolean isPresente = documentiautorizzazioneService.isInAutorizzazione(codiceautorizzazione, documentiistanzaDTO.getId().getCodice(),
		    "documentiistanza");
	    log.debug(
		    "findMovimentiallegatiDTOByIstanzaNonInDocAutorizzazione# Aut = {},codice doc istanza = {}, presente in Documentiautorizzazione = {}",
		    new Object[] { codiceautorizzazione, documentiistanzaDTO.getId().getCodice(), isPresente });
	    if (!isPresente) {
		risultato.add(documentiistanzaDTO);
	    }
	}
	return risultato;
    }

    @Override
    public List<Documentiistanza> findByIstanzaNomeFile(String nomefile, Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.equals("nomefile", nomefile.trim(), "oggetto", String.class));
	ft.addRestriction(fr);
	List<Documentiistanza> list = documentiistanzaDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Documentiistanza> findByCodiceOggetto(Integer codiceOggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("oggettoId", codiceOggetto, Integer.class));
	ft.addRestriction(fr);
	List<Documentiistanza> list = documentiistanzaDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public Set<String> findSoftwareByCodiceOggetto(Integer codiceOggetto) {

	Set<String> softwaresOggetti = new HashSet<String>();
	List<Documentiistanza> findDociListByOggetto = this.findByCodiceOggetto(codiceOggetto);
	for (Documentiistanza documentiistanza : findDociListByOggetto) {
	    softwaresOggetti.add(documentiistanza.getIstanza().getSoftware().getCodice());
	}
	return softwaresOggetti;
    }

    @Override
    public Integer insertDocumentoDaHelper(Integer codiceIstanza, DocumentiIstanzaRestHelper helper, InputStream documento) throws IOException {

	if (codiceIstanza == null || helper == null || StringUtils.isBlank(helper.getNomefile()) || StringUtils.isBlank(helper.getDescrizione())) {
	    throw new IllegalArgumentException("Parametri non validi");
	}
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	byte[] bytes = IOUtils.toByteArray(documento);
	Documentiistanza entity = new Documentiistanza();
	entity.setIstanza(istanze);
	entity.setData(helper.getData());
	entity.setDocumento(helper.getDescrizione());
	entity.setNote(helper.getNote());
	entity.setPresente(Boolean.TRUE);
	entity.setControllook(helper.getVerifica());
	Oggetti oggetto = new Oggetti();
	oggetto.setOggetto(bytes);
	oggetto.setNomefile(helper.getNomefile());
	oggettiService.insert(oggetto);
	documentiistanzaDAO.flush();
	entity.setOggetto(oggetto);
	this.insert(entity);
	return oggetto.getId().getCodice();
    }

    @Override
    public BaseEsitoOperazione updateSpostaCopiaDocumenti(Integer codiceIstanza, Integer codiceIstanzaDest, boolean isCopia,
	    Set<Integer> docIstanzaId) {

	Istanze istanzaDest = istanzeService.findById(new PkId(codiceIstanzaDest));
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    validaSpostaCopia(istanza, istanzaDest, isCopia, docIstanzaId, r);
	} catch (Exception e) {
	    return new BaseEsitoOperazione(BaseEsitoOperazione.ESITO.ERROR).addError(e.getMessage());
	}
	for (Integer id : docIstanzaId) {
	    Documentiistanza doc = documentiistanzaDAO.findById(new PkId(id));
	    StringBuilder messaggio = new StringBuilder("Il documento ").append(doc).append(" è stato ");
	    StringBuilder messaggioSrc = new StringBuilder("Il documento ").append(doc).append(" è stato ");
	    if (isCopia) {
		messaggio.append(" copiato ");
		messaggioSrc.append(" copiato ");
		Documentiistanza copia = Documentiistanza.clonaDocumentoIstanza(doc);
		copia.setIstanza(istanzaDest);
		this.insert(copia);
	    } else {
		messaggio.append(" spostato ");
		messaggioSrc.append(" spostato ");
		doc.setIstanza(istanzaDest);
		this.update(doc);
	    }
	    messaggio.append(" dall' operatore ").append(r).append(" dalla pratica ").append(istanza);
	    messaggioSrc.append(" dall' operatore ").append(r).append(" nella pratica ").append(istanzaDest);
	    istanzeeventiService.insert(StringUtils.left(messaggioSrc.toString(), 4000), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null,
		    istanza);
	    istanzeeventiService.insert(StringUtils.left(messaggio.toString(), 4000), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null,
		    istanzaDest);
	    documentiistanzaDAO.flush();
	    LoggerModificheIstanze.log("#SPOSTA_COPIA_DOCUMENTI_ISTANZA#" + messaggio.toString());
	    LoggerModificheIstanze.log("#SPOSTA_COPIA_DOCUMENTI_ISTANZA#" + messaggioSrc.toString());
	}
	return new BaseEsitoOperazione(ESITO.SUCCESS);
    }

    private void validaSpostaCopia(Istanze istanza, Istanze istanzaDest, Boolean isCopia, Set<Integer> docIstanzaId, Responsabili r)
	    throws BusinessValidationException {

	if (istanza == null || istanzaDest == null) {
	    throw new BusinessValidationException("Istanze non trovate sorgente :" + istanza + ", destinazione: " + istanzaDest);
	}
	if (istanza.getId().getCodice().equals(istanzaDest.getId().getCodice())) {
	    throw new BusinessValidationException("Operazione non permessa all'interno della stessa istanza :" + istanza.getNumeroistanza());
	}
	TipoAccessoEnum checkAccessoIstanza = istanzeService.checkAccessoIstanza(istanzaDest, r);
	if (!checkAccessoIstanza.equals(TipoAccessoEnum.CONSENTITO)) {
	    throw new BusinessValidationException("L'utente non ha i permessi per spostare i documenti sull'istanza " + istanzaDest.toString());
	}
	checkAccessoIstanza = istanzeService.checkAccessoIstanza(istanza, (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
	if (!checkAccessoIstanza.equals(TipoAccessoEnum.CONSENTITO)) {
	    throw new BusinessValidationException("L'utente non ha i permessi per spostare i documenti dall'istanza " + istanza.toString());
	}
    }

    @Override
    public RiepilogoHelper rigeneraRiepilogo(Integer codiceistanza) throws Exception {

	Istanze i = istanzeService.findById(new PkId(codiceistanza));
	String wsGeneratoreRiepiloghiNew = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_GENERATORE_RIEPILOGHI);
	if (StringUtils.isBlank(wsGeneratoreRiepiloghiNew)) {
	    Verticalizzazioniparametri urlRigeneraRiepilogo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_URL_WS_RIEPILOGO_PRATICA, i.getComune().getCodicecomune(),
		    i.getSoftware().getCodice());
	    String urlWS = "";
	    if (urlRigeneraRiepilogo != null && StringUtils.isNotBlank(urlRigeneraRiepilogo.getValore())) {
		urlWS = urlRigeneraRiepilogo.getValore();
	    }
	    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	    factory.setServiceClass(RiepilogoPraticaSoap.class);
	    factory.setAddress(urlWS);
	    RiepilogoPraticaSoap port = (RiepilogoPraticaSoap) factory.create();
	    Client proxy = ClientProxy.getClient(port);
	    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	    BindingProvider bp = (BindingProvider) port;
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	    httpClientPolicy.setConnectionTimeout(12000); // Line #2  
	    httpClientPolicy.setReceiveTimeout(20000); // Line #3  
	    conduit.setClient(httpClientPolicy);
	    BinaryFile riepilogo = port.generaRiepilogo(ORMHelper.getToken(), i.getUuid());
	    return new RiepilogoHelper(riepilogo.getFileContent(), riepilogo.getFileName(), riepilogo.getMimeType());
	} else {
	    //valori di default
	    WebClient webClient = WebClient.create(wsGeneratoreRiepiloghiNew + "/api/riepilogo-istanza/" + codiceistanza);
	    HTTPConduit conduit = WebClient.getConfig(webClient).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(12000);
	    conduit.getClient().setReceiveTimeout(12000);
	    webClient.header("Authorization", "Bearer " + ORMHelper.getToken());
	    Response response = webClient.get();
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		byte[] targetArray = IOUtils.toByteArray(is);
		return new RiepilogoHelper(targetArray, // 
			"riepilogo-domanda-" + codiceistanza + "-" + Utilities.getToday("yyyy-mm-dd") + ".pdf", // 
			"application/pdf");
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("rigeneraRiepilogo errore: {}", s);
		throw new FunzioneBusinessRemotaException("Errore nella generazione del riepilogo. Dettaglio Errore: \n" + s);
	    }
	}
    }
}