package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeallegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeAllegatiControlloHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.ICommissioniDocumentiPraticheService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.helper.MetadatiFunzioneEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IstanzeallegatiServiceImpl extends BaseServiceImpl<Istanzeallegati, PkId> implements IstanzeallegatiService {

    private static final Logger log = LoggerFactory.getLogger(DocumentiistanzaServiceImpl.class);
    private IstanzeallegatiDAO istanzeallegatiDAO;
    private IstanzeService istanzeService;
    private OggettiService oggettiService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private AllegatiService allegatiService;
    private DocumentiAutorizzazioneService documentiautorizzazioneService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService;

    @Autowired
    public void setCommissioniDocumentiPraticheService(ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService) {

	this.commissioniDocumentiPraticheService = commissioniDocumentiPraticheService;
    }

    @Autowired
    public void setDocumentiautorizzazioneService(DocumentiAutorizzazioneService documentiautorizzazioneService) {

	this.documentiautorizzazioneService = documentiautorizzazioneService;
    }

    @Autowired
    public void setAllegatiService(AllegatiService allegatiService) {

	this.allegatiService = allegatiService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeallegatiDAO(IstanzeallegatiDAO istanzeallegatiDAO) {

	this.istanzeallegatiDAO = istanzeallegatiDAO;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Override
    protected Class<Istanzeallegati> getEntityClass() {

	return Istanzeallegati.class;
    }

    @Override
    public void delete(Istanzeallegati entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    istanzeallegatiDAO.delete(entity);
	}
    }

    @Override
    public List<Istanzeallegati> findAll(Integer firstResult, Integer maxResult) {

	return istanzeallegatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Istanzeallegati findById(PkId id) {

	return istanzeallegatiDAO.findById(id);
    }

    @Override
    public void insert(Istanzeallegati entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    Allegati allegati = allegatiService.findById(entity.getAllegati().getId());
	    entity.setAllegati(allegati);
	    istanzeallegatiDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    // private int nextId(Istanze istanza, Inventarioprocedimenti inventarioprocedimenti) {
    //
    // FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
    // FilterRestriction fr = new FilterRestriction();
    // fr.addFilterField(FilterUtils.equals("id.codiceinventario", inventarioprocedimenti.getId().getCodice(),
    // Integer.class));
    // fr.addFilterField(FilterUtils.equals("id.codiceistanza", istanza.getId().getCodice(), Integer.class));
    // ft.addRestriction(fr);
    // ft.addOrder(FilterUtils.orderDesc("id.numeroallegato"));
    // List<Istanzeallegati> list = istanzeallegatiDAO.findByFilterTable(ft);
    // if (list.size() > 0) {
    // int numeroallegato = list.get(0).getId().getNumeroallegato().intValue();
    // numeroallegato++;
    // return numeroallegato;
    // }
    // return 1;
    // }
    @Override
    public void update(Istanzeallegati entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    istanzeallegatiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    childDataUpdate(entity);
	}
    }

    @Override
    public List<Istanzeallegati> findByIstanza(int codiceIstanza) {

	return istanzeallegatiDAO.findByIstanza(codiceIstanza);
    }

    @Override
    public List<Istanzeallegati> findByIstanzaAndEndo(int codiceIstanza, int codiceInventario) {

	return istanzeallegatiDAO.findByIstanzaAndEndo(codiceIstanza, codiceInventario);
    }

    @Override
    public List<Istanzeallegati> findByIstanzaOggetto(int codiceIstanza) {

	return istanzeallegatiDAO.findByIstanzaOggetto(codiceIstanza);
    }

    @Override
    public List<IstanzeallegatiHelper> findIstanzeallegatiHelper(Istanze istanza) {

	List<IstanzeallegatiHelper> list = new ArrayList<IstanzeallegatiHelper>();
	IstanzeallegatiHelper istanzeallegatiHelper = null;
	// mi permette di ricavare tutti gli inventari
	List<Inventarioprocedimenti> listProcedimentiistanza = inventarioprocedimentiService.findByIstanza(istanza);
	for (Inventarioprocedimenti inventarioprocedimenti : listProcedimentiistanza) {
	    List<Istanzeallegati> listaAllegatiForIstanzaAndInventarioproced = this.findByIstanzaAndEndo(istanza.getId().getCodice(),
		    inventarioprocedimenti.getId().getCodice().intValue());
	    istanzeallegatiHelper = new IstanzeallegatiHelper();
	    istanzeallegatiHelper.setInventarioprocedimenti(inventarioprocedimenti);
	    istanzeallegatiHelper.setIstanzeAllegatis(listaAllegatiForIstanzaAndInventarioproced);
	    list.add(istanzeallegatiHelper);
	}
	return list;
    }

    @Override
    public void insertDaEndo(Istanzeallegati entity) {

	if (validateInserimentoDaEndo(entity)) {
	    entity.setSeendo(true);
	    this.insert(entity);
	}
    }

    private void childDataInsert(Istanzeallegati entity) {

	// TODO Auto-generated method stub
    }

    private void childDataUpdate(Istanzeallegati entity) {

	// TODO Auto-generated method stub
    }

    private void dataIntegration(Istanzeallegati entity, boolean isUpdate) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'allegato dell'istanza è nullo");
	}
	//	if (entity.getControllook() == null) {
	//	    entity.setControllook(Boolean.FALSE);
	//	}
	if (entity.getSeendo() == null) {
	    entity.setSeendo(Boolean.FALSE);
	}
	if (entity.getSelezionato() == null) {
	    entity.setSelezionato(Boolean.FALSE);
	}
	if (entity.getVerificato() == null) {
	    entity.setVerificato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
	if (EntityUtils.getNestedProperty(entity.getOggetto(), "id.codice") != null) {
	    entity.setPresente(Boolean.TRUE);
	}
	if (entity.getPresente() == null) {
	    entity.setPresente(Boolean.FALSE);
	}
	if (!isUpdate) {
	    if (entity.getData() == null) {
		entity.setData(new Date());
	    }
	}
    }

    protected void fixMergeEntityProperties(Istanzeallegati entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanze);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(),
		PkId.class, "id.codice");
	entity.setInventarioprocedimenti(inventarioprocedimenti);
	populateMetadataFunzione(entity, inventarioprocedimenti); // va prima del binddomainopbject di oggetti	
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetto(), PkId.class, "id.codice");
	entity.setOggetto(oggetto);
    }

    @Override
    protected boolean isDeleteAllowed(Istanzeallegati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean isExistIstanzaAllegati = documentiautorizzazioneService.isInAutorizzazione(entity.getId().getCodice(), "istanzeallegati");
	if (isExistIstanzaAllegati) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DOCUMENTI_AUTORIZZAZIONE", null));
	}
	boolean isExistEntityInMovimentiZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(entity.getId().getCodice(),
		"istanzeallegati");
	if (isExistEntityInMovimentiZipLogico) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI_ZIP_LOGICO", null));
	}
	if (commissioniDocumentiPraticheService.existsByIstanzeallegati(entity.getId().getCodice())) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null,
		    "COMMEDR_ISTALL: è stato selezionato come Allegato delle commissioni / conferenze", null));
	    delete = false;
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private void populateMetadataFunzione(Istanzeallegati entity, Inventarioprocedimenti endoprocedimento) {

	if (endoprocedimento != null) {
	    if (endoprocedimento.getId() != null) {
		if (endoprocedimento.getId().getCodice() != null) {
		    if (entity.getOggetto() != null) {
			List<MetadatiBean> s = null;
			if (entity.getOggetto().getMetadatiTransient() != null) {
			    s = entity.getOggetto().getMetadatiTransient();
			}
			if (s == null) {
			    s = new ArrayList<MetadatiBean>();
			}
			MetadatiBean mdb = new MetadatiBean();
			mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_ENDO.getValue());
			mdb.setValore(String.valueOf(endoprocedimento.getId().getCodice()));
			s.add(mdb);
			if (entity.getIstanza() != null) {
			    if (entity.getIstanza().getId() != null) {
				if (entity.getIstanza().getId().getCodice() != null) {
				    mdb = new MetadatiBean();
				    mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_ISTANZA.getValue());
				    mdb.setValore(String.valueOf(entity.getIstanza().getId().getCodice()));
				    s.add(mdb);
				}
			    }
			}
			entity.getOggetto().setMetadatiTransient(s);
		    }
		}
	    }
	}
    }

    //
    //    private Integer controllaCancellaOggetti(Istanzeallegati entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("ISTANZEALLEGATI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Istanzeallegati entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
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
    //			if (oggettiService.controllaCancellaOggetto("ISTANZEALLEGATI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    private boolean validateInserimentoDaEndo(Istanzeallegati entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getAllegati().getId() != null && entity.getAllegati().getId().getCodice() == null) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", null, null, entity.getAllegati(), null));
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public void insertcopiaAllegatoStc(Istanzeallegati istanzeallegati) {

	//	// recupero il movimento per recuperare il codice movimento
	//	Movimenti movimento = movimentiService.findMovimentoSTCCheHaCreatoIstanza(istanzeallegati.getIstanza());
	//	AllegatoBinarioResponse allegatoBinarioResponse = null;
	//	allegatoBinarioResponse = stcService.allegatoBinario(movimento.getId().getCodice(), istanzeallegati.getStcIddocumento(),
	//		istanzeallegati.getStcIdallegato());
	//	// creo l'oggetto che andrò a salvare su DB
	//	Oggetti oggetto = new Oggetti();
	//	oggetto.setNomefile(allegatoBinarioResponse.getFileName());
	//	oggetto.setOggetto(allegatoBinarioResponse.getBinaryData());
	//	oggettiService.insert(oggetto);
	//	// collego l'oggetto creato all' istanza allegato
	//	istanzeallegati.setOggetto(oggetto);
	//	istanzeallegatiDAO.insert(istanzeallegati);
    }

    @Override
    public IstanzeAllegatiControlloHelper controlloDocumentazione(Istanze istanza) {

	List<Istanzeallegati> allegatis = this.findByIstanza(istanza.getId().getCodice());
	int allegatiPresentati = 0;
	int allegatiNonValidi = 0;
	int allegatiValidi = 0;
	for (Istanzeallegati istanzeallegati : allegatis) {
	    if (BooleanUtils.isTrue(istanzeallegati.getPresente())) {
		allegatiPresentati++;
	    }
	    //CONTROLLARE SE FUNZIONA
	    if (istanzeallegati.getControllook() != null) {
		if (istanzeallegati.getControllook().equals(Integer.valueOf(1))) {
		    allegatiValidi++;
		}
		if (istanzeallegati.getControllook().equals(Integer.valueOf(0))) {
		    allegatiNonValidi++;
		}
	    }
	}
	IstanzeAllegatiControlloHelper helper = new IstanzeAllegatiControlloHelper();
	helper.setAllegatiPresentati(allegatiPresentati);
	helper.setAllegatiRichiesti(allegatis.size());
	helper.setAllegatiValidi(allegatiValidi);
	helper.setAllegatiNonValidi(allegatiNonValidi);
	return helper;
    }

    @Override
    public int countByInventarioprocedimento(Integer codiceProcedimento) {

	if (codiceProcedimento == null) {
	    throw new IllegalArgumentException("countByInventarioprocedimento: il parametro codiceProcedimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceProcedimento, "inventarioprocedimenti", Integer.class));
	filterTable.addRestriction(fr);
	int count = istanzeallegatiDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public int updateResettaRiferimentoAllegatoEndo(Allegati allegato) {

	return istanzeallegatiDAO.updateResettaRiferimentoAllegatoEndo(allegato);
    }

    @Override
    public List<Istanzeallegati> findByInventarioprocedimenti(Integer codiceProcedimento, Integer firstResult, Integer maxResult) {

	if (codiceProcedimento == null) {
	    throw new IllegalArgumentException("findByInventarioprocedimenti: il parametro codiceProcedimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceProcedimento, "inventarioprocedimenti", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeallegatiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanzeallegati> findProvenientiDaSTC(Integer codiceIstanza) {

	FilterTable ft = setFilterAllegatiForIstanzaAndProvenientiSTC(codiceIstanza);
	return istanzeallegatiDAO.findByFilterTable(ft);
    }

    @Override
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza) {

	FilterTable ft = setFilterAllegatiForIstanzaAndProvenientiSTC(codiceIstanza);
	if (istanzeallegatiDAO.countRecord(ft) > 0) {
	    return true;
	}
	return false;
    }

    private FilterTable setFilterAllegatiForIstanzaAndProvenientiSTC(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.isNull("id.codice", "oggetto"));
	fr.addFilterField(FilterUtils.isNotNull("stcIddocumento"));
	fr.addFilterField(FilterUtils.isNotNull("stcIdallegato"));
	ft.addRestriction(fr);
	return ft;
    }

    @Override
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza) {

	return istanzeallegatiDAO.findIstanzeallegatiDTOByIstanza(codiceIstanza);
    }

    @Override
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanzaAndEndo(Integer codiceIstanza, Integer codicendo,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum) {

	return istanzeallegatiDAO.findIstanzeallegatiDTOByIstanzaAndEndo(codiceIstanza, codicendo, tipoRicercaDocumentoEnum);
    }

    @Override
    public List<Istanzeallegati> findByIstanzaAndOggetto(Integer codiceIstanza, Integer codiceOggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceOggetto, "oggetto", Integer.class));
	//	fr.addFilterField(FilterUtils.isNotNull("stcIddocumento"));
	//	fr.addFilterField(FilterUtils.isNotNull("stcIdallegato"));
	ft.addRestriction(fr);
	return istanzeallegatiDAO.findByFilterTable(ft);
    }

    @Override
    public Integer insertMultiFile(Istanzeallegati entity, List<MultipartFile> multipartFiles) {

	if (validateEntity(entity)) {
	    int cont = 1;
	    Istanzeallegati istanzeallegati = null;
	    String desc = StringUtils.isNotBlank(entity.getAllegatoextra()) ? entity.getAllegatoextra() : "";
	    String note = StringUtils.isNotBlank(entity.getNote()) ? entity.getNote() : "";
	    Boolean richiesto = BooleanUtils.toBoolean(entity.getNecessario());
	    Boolean presente = BooleanUtils.toBoolean(entity.getPresente());
	    Integer valido = entity.getControllook() != null ? entity.getControllook() : null;
	    Istanze istanza = entity.getIstanza();
	    Inventarioprocedimenti inventarioprocedimenti = entity.getInventarioprocedimenti();
	    for (MultipartFile multipartFile : multipartFiles) {
		Oggetti oggetto = oggettiService.insert(multipartFile);
		istanzeallegati = new Istanzeallegati();
		String prefix = StringUtils.leftPad(String.valueOf(cont), 2, "0");
		istanzeallegati.setAllegatoextra(desc + " [" + prefix + "]");
		istanzeallegati.setNote(note);
		istanzeallegati.setNecessario(richiesto);
		istanzeallegati.setPresente(presente);
		istanzeallegati.setControllook(valido);
		istanzeallegati.setOggetto(oggetto);
		istanzeallegati.setIstanza(istanza);
		istanzeallegati.setInventarioprocedimenti(inventarioprocedimenti);
		this.insert(istanzeallegati);
		cont++;
	    }
	    return istanzeallegati.getId().getCodice();
	}
	return null;
    }

    @Override
    public Integer insertSingoloOrMultiFile(Istanzeallegati entity, List<MultipartFile> lMultipartFiles) {

	Integer codice = null;
	log.debug("insertSingoloOrMultiFile# Controllo se sono in fase di inserimento singolo file (standard) o inserimento multi file");
	if (!lMultipartFiles.isEmpty()) {
	    log.debug("insertSingoloOrMultiFile# Inserimento tipo : multi file");
	    codice = this.insertMultiFile(entity, lMultipartFiles);
	} else {
	    log.debug("insertSingoloOrMultiFile# Inserimento tipo : singolo file (modalità standard)");
	    this.insert(entity);
	    codice = entity.getId().getCodice();
	}
	return codice;
    }

    @Override
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto) {

	return istanzeallegatiDAO.findIstanzeallegatiDTOByIstanza(codiceIstanza, isCodiceOggetto);
    }

    @Override
    public List<IstanzeallegatiDTO> findIstanzeAllegatiDTOByIstanzaNonInDocAutorizzazione(Integer codiceistanza, Integer codiceautorizzazione) {

	List<IstanzeallegatiDTO> listIstanzeallegatiDTOs = this.findIstanzeallegatiDTOByIstanza(codiceistanza, true);
	List<IstanzeallegatiDTO> risultato = new ArrayList<IstanzeallegatiDTO>();
	for (IstanzeallegatiDTO istanzeallegatiDTO : listIstanzeallegatiDTOs) {
	    // controllo che per l'autorizzazione passata istanze documenti non sia già stato associato all'aut tramite doc autorizzazioni
	    boolean isPresente = documentiautorizzazioneService.isInAutorizzazione(codiceautorizzazione, istanzeallegatiDTO.getId().getCodice(),
		    "istanzeallegati");
	    log.debug(
		    "findIstanzeAllegatiDTOByIstanzaNonInDocAutorizzazione# Aut = {},codice istanze allegato = {}, presente in Documentiautorizzazione = {}",
		    new Object[] { codiceautorizzazione, istanzeallegatiDTO.getId().getCodice(), isPresente });
	    if (!isPresente) {
		risultato.add(istanzeallegatiDTO);
	    }
	}
	return risultato;
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
	int count = istanzeallegatiDAO.countRecord(filterTable);
	return count;
    }
}
