package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeprocureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegati;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegatiId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.VwIstanzesoggetticollegatiService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.MetadatiFunzioneEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzeprocureServiceImpl extends BaseServiceImpl<Istanzeprocure, PkId> implements IstanzeprocureService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeprocureServiceImpl.class);
    private AnagrafeService anagrafeService;
    private IstanzeprocureDAO istanzeprocureDAO;
    private IstanzeService istanzeService;
    private OggettiService oggettiService;
    private DocumentiAutorizzazioneService documentiAutorizzazioneService;
    private VwIstanzesoggetticollegatiService vwIstanzesoggetticollegatiService;
    private MovimentiZipLogicoService movimentiZipLogicoService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setIstanzeprocureDAO(IstanzeprocureDAO istanzeprocureDAO) {

	this.istanzeprocureDAO = istanzeprocureDAO;
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
    public void setDocumentiAutorizzazioneService(DocumentiAutorizzazioneService documentiAutorizzazioneService) {

	this.documentiAutorizzazioneService = documentiAutorizzazioneService;
    }

    @Autowired
    public void setVwIstanzesoggetticollegatiService(VwIstanzesoggetticollegatiService vwIstanzesoggetticollegatiService) {

	this.vwIstanzesoggetticollegatiService = vwIstanzesoggetticollegatiService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Override
    protected Class<Istanzeprocure> getEntityClass() {

	return Istanzeprocure.class;
    }

    @Override
    public List<Istanzeprocure> findAll(Integer firstResult, Integer maxResult) {

	return istanzeprocureDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzeprocure entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    istanzeprocureDAO.insert(entity);
	}
    }

    @Override
    public Istanzeprocure findById(PkId id) {

	return istanzeprocureDAO.findById(id);
    }

    @Override
    public void update(Istanzeprocure entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    Integer codiceOggettoDaCancellareDocIdent = controllaCancellaOggetti(entity, "oggettiDocIdent", false, entity.getId());
	    istanzeprocureDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    if (codiceOggettoDaCancellareDocIdent != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellareDocIdent));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    protected boolean validateEntity(Istanzeprocure entity) {

	super.validateEntity(entity);
	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doBusinessValidation = true;
	if (validationRule != null) {
	    doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	}
	// 2. CONTROLLO CHE PERSONE INDICATE COME RICHIEDENTI ABBIANO IL CF SETTATO E CHE SIANO PERSONE FISICHE
	if (doBusinessValidation) {
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    if (EntityUtils.getNestedProperty(entity.getAnagrafeProcuratore(), "id.codice") != null) {
		ivs.addAll(checkPfAndCfAnagrafe(entity.getAnagrafeProcuratore().getId().getCodice(), "anagrafeProcuratore", entity));
	    }
	    if (EntityUtils.getNestedProperty(entity.getAnagrafeRappresentato(), "id.codice") != null) {
		ivs.addAll(checkPfAndCfAnagrafe(entity.getAnagrafeRappresentato().getId().getCodice(), "anagrafeRappresentato", entity));
	    }
	    if (ivs.size() > 0) {
		throwValidationMessages(ivs);
	    }
	}
	return true;
    }

    private List<InvalidValue> checkPfAndCfAnagrafe(Integer codice, String property, Istanzeprocure entity) {

	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	if (anagrafe != null) {
	    if (StringUtils.isBlank(anagrafe.getCodicefiscale())) {
		InvalidValue iv = new InvalidValue("service_error.istanzeprocure.codice_fiscale_non_presente", entity.getClass(), property, null,
			entity);
		ivs.add(iv);
	    }
	    if (!StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), "").equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		InvalidValue iv = new InvalidValue("service_error.richiedente_deve_essere_pf", entity.getClass(), property, null, entity);
		ivs.add(iv);
	    }
	}
	return ivs;
    }

    private void dataIntegration(Istanzeprocure entity, boolean isUpdate) {

	if (entity == null) {
	    throw new IllegalArgumentException("È stato passato un oggetto Istanzeprocure nullo");
	}
	if (!isUpdate) {
	    if (entity.getData() == null) {
		entity.setData(new Date());
	    }
	}
	fixMergeEntityProperties(entity);
	gestAnagrafeStorico(entity);
    }

    private void gestAnagrafeStorico(Istanzeprocure entity) {

	if (EntityUtils.getNestedProperty(entity.getIstanze(), "id.codice") != null) {
	    if (EntityUtils.getNestedProperty(entity, "anagrafeRappresentato.id.codice") != null) {
		checkStoricoAnagrafe(entity, "anagrafeRappresentato", "anagrafeRappresentatoStorico", entity.getIstanze().getData());
	    } else {
		// .. rappresentato è nullo azzero anche il rappresentato storico
		if (EntityUtils.getNestedProperty(entity.getAnagrafeRappresentatoStorico(), "id.codice") != null) {
		    entity.setAnagrafeRappresentatoStorico(null);
		}
	    }
	    if (EntityUtils.getNestedProperty(entity, "anagrafeProcuratore.id.codice") != null) {
		checkStoricoAnagrafe(entity, "anagrafeProcuratore", "anagrafeProcuratoreStorico", entity.getIstanze().getData());
	    } else {
		// .. procuratore è nullo azzero anche il procuratore storico
		if (EntityUtils.getNestedProperty(entity.getAnagrafeProcuratoreStorico(), "id.codice") != null) {
		    entity.setAnagrafeProcuratoreStorico(null);
		}
	    }
	}
    }

    private void checkStoricoAnagrafe(Object entity, String mainProperty, String storicoProperty, Date dataStorico) {

	try {
	    if (EntityUtils.getNestedProperty(entity, mainProperty + ".id.codice") != null) {
		String codiceAnagrafeStr = BeanUtils.getProperty(entity, mainProperty + ".id.codice");
		Integer codiceAnagrafe = null;
		if (StringUtils.isNotBlank(codiceAnagrafeStr)) {
		    codiceAnagrafe = Integer.valueOf(codiceAnagrafeStr);
		}
		Anagrafe richiedente = anagrafeService.findById(new PkId(codiceAnagrafe));
		if (EntityUtils.getNestedProperty(entity, storicoProperty + ".id.codice") == null) {
		    Anagrafestorico richiedenteStorico = anagrafeService.findStoricoId(richiedente, dataStorico);
		    BeanUtils.setProperty(entity, storicoProperty, richiedenteStorico);
		} else {
		    Integer codicestorico = (Integer) EntityUtils.getNestedProperty(entity, storicoProperty + ".id.codice");
		    Anagrafestorico as = null;
		    if (codicestorico != null) {
			PkId idStorico = new PkId(codicestorico);
			as = anagrafeService.findAnagrafeStoricoById(idStorico);
			if (as != null) {
			    if (!as.getAnagrafe().getId().getCodice().equals(richiedente.getId().getCodice())) {
				Anagrafestorico richiedenteStorico = anagrafeService.findStoricoId(richiedente, dataStorico);
				BeanUtils.setProperty(entity, storicoProperty, richiedenteStorico);
			    }
			} else {
			    log.error("checkStoricoAnagrafe: non è stata trovato nessun record in anagrafestorico con id={}", codicestorico);
			}
		    } else {
			log.error("checkStoricoAnagrafe: anomalia in idstorico {} della property {}",
				EntityUtils.getNestedProperty(entity, storicoProperty + ".id.codice"), storicoProperty);
		    }
		}
	    }
	} catch (IllegalAccessException e) {
	    log.error("checkStoricoAnagrafe[{}]-[{}]-[{}]: {}",
		    new Object[] { entity.getClass().getName(), mainProperty, storicoProperty, e.getMessage() });
	    throw new RuntimeException("Errore nella gestione del campo anagrafeStorico. rif[checkStoricoAnagrafe][" + entity.getClass().getName()
		    + "]-[" + mainProperty + "]-[" + storicoProperty + "]", e);
	} catch (InvocationTargetException e) {
	    log.error("checkStoricoAnagrafe[{}]-[{}]-[{}]: {}",
		    new Object[] { entity.getClass().getName(), mainProperty, storicoProperty, e.getMessage() });
	    throw new RuntimeException("Errore nella gestione del campo anagrafeStorico. rif[checkStoricoAnagrafe][" + entity.getClass().getName()
		    + "]-[" + mainProperty + "]-[" + storicoProperty + "]", e);
	} catch (NoSuchMethodException e) {
	    log.error("checkStoricoAnagrafe[{}]-[{}]-[{}]: {}",
		    new Object[] { entity.getClass().getName(), mainProperty, storicoProperty, e.getMessage() });
	    throw new RuntimeException("Errore nella gestione del campo anagrafeStorico. rif[checkStoricoAnagrafe][" + entity.getClass().getName()
		    + "]-[" + mainProperty + "]-[" + storicoProperty + "]", e);
	}
    }

    @Override
    protected void fixMergeEntityProperties(Istanzeprocure entity) {

	Anagrafe procuratore = anagrafeService.bindDomainObject(entity.getAnagrafeProcuratore(), PkId.class, "id.codice");
	entity.setAnagrafeProcuratore(procuratore);
	Anagrafe rappresentato = anagrafeService.bindDomainObject(entity.getAnagrafeRappresentato(), PkId.class, "id.codice");
	entity.setAnagrafeRappresentato(rappresentato);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	populateMetadataFunzione(entity, istanza);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetto);
	Oggetti oggettodocIdent = oggettiService.bindDomainObject(entity.getOggettiDocIdent(), PkId.class, "id.codice");
	entity.setOggettiDocIdent(oggettodocIdent);
    }

    private void populateMetadataFunzione(Istanzeprocure entity, Istanze istanza) {

	if (istanza != null) {
	    if (istanza.getId() != null) {
		if (istanza.getId().getCodice() != null) {
		    if (entity.getOggetti() != null) {
			List<MetadatiBean> s = null;
			if (entity.getOggetti().getMetadatiTransient() != null) {
			    s = entity.getOggetti().getMetadatiTransient();
			}
			if (s == null) {
			    s = new ArrayList<MetadatiBean>();
			}
			MetadatiBean mdb = new MetadatiBean();
			mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_ISTANZA.getValue());
			mdb.setValore(String.valueOf(istanza.getId().getCodice()));
			s.add(mdb);
			entity.getOggetti().setMetadatiTransient(s);
		    }
		}
	    }
	}
    }

    @Override
    public void delete(Istanzeprocure entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    Integer codiceOggettoDaCancellareDocIdent = controllaCancellaOggetti(entity, "oggettiDocIdent", true, entity.getId());
	    istanzeprocureDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    if (codiceOggettoDaCancellareDocIdent != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellareDocIdent));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(Istanzeprocure entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean isExistInDocumentiAutorizzazione = documentiAutorizzazioneService.isInAutorizzazione(entity.getId().getCodice(), "istanzeprocure");
	if (isExistInDocumentiAutorizzazione) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DOCUMENTI_AUTORIZZAZIONE", null));
	}
	boolean isExistEntityInMovimentiZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(entity.getId().getCodice(),
		"istanzeprocure");
	if (isExistEntityInMovimentiZipLogico) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI_ZIP_LOGICO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Istanzeprocure> findByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("findByIstanza: Il codice istanza non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("nominativo", "anagrafeProcuratore"));
	ft.addOrder(FilterUtils.orderAsc("nome", "anagrafeProcuratore"));
	return istanzeprocureDAO.findByFilterTable(ft);
    }

    @Override
    public List<Istanzeprocure> findByAnagrafeProcuratore(Integer codiceAnagrafe) {

	return findByIntegerProperty("anagrafeProcuratoreId", codiceAnagrafe);
    }

    @Override
    public List<Istanzeprocure> findByAnagrafeRappresentato(Integer codiceAnagrafe) {

	return findByIntegerProperty("anagrafeRappresentatoId", codiceAnagrafe);
    }

    @Override
    public List<Istanzeprocure> findByAnagrafeStoricoRappresentato(Integer idAnagrafeStorico) {

	return findByIntegerProperty("anagrafeRappresentatoStoricoId", idAnagrafeStorico);
    }

    @Override
    public List<Istanzeprocure> findByAnagrafeStoricoProcuratore(Integer idAnagrafeStorico) {

	return findByIntegerProperty("anagrafeProcuratoreStoricoId", idAnagrafeStorico);
    }

    private List<Istanzeprocure> findByIntegerProperty(String propertyDesc, Integer propertyValue) {

	if (propertyValue == null) {
	    throw new IllegalArgumentException("Il parametro del metodo per la property " + propertyDesc + " non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals(propertyDesc, propertyValue, Integer.class));
	ft.addRestriction(fr);
	return istanzeprocureDAO.findByFilterTable(ft);
    }

    private int countByIntegerProperty(String propertyDesc, Integer propertyValue) {

	if (propertyValue == null) {
	    throw new IllegalArgumentException("Il parametro del metodo per la property " + propertyDesc + " non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals(propertyDesc, propertyValue, Integer.class));
	ft.addRestriction(fr);
	return istanzeprocureDAO.countRecord(ft);
    }

    @Override
    public int countByIstanze(Integer codiceIstanza) {

	return countByIntegerProperty("istanzeId", codiceIstanza);
    }

    @Override
    public int countByAnagrafeRappresentato(Integer codiceAnagrafe) {

	return countByIntegerProperty("anagrafeRappresentatoId", codiceAnagrafe);
    }

    @Override
    public int countByAnagrafeProcuratore(Integer codiceAnagrafe) {

	return countByIntegerProperty("anagrafeProcuratoreId", codiceAnagrafe);
    }

    @Override
    public List<Istanzeprocure> findByIstanzaAndAnagrafe(Integer codiceIstanza, Integer codiceAnagrafe) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	FilterRestriction anagrafeRestr = new FilterRestriction();
	anagrafeRestr.setAndOrRestriction(AndOrRestriction.OR);
	anagrafeRestr.addFilterField(FilterUtils.equals("anagrafeRappresentatoId", codiceAnagrafe, Integer.class));
	anagrafeRestr.addFilterField(FilterUtils.equals("anagrafeProcuratoreId", codiceAnagrafe, Integer.class));
	ft.addRestriction(fr);
	ft.addRestriction(anagrafeRestr);
	return istanzeprocureDAO.findByFilterTable(ft);
    }

    @Override
    public boolean checkValiditaProcuraInIstanza(Integer codiceIstanza) {

	VwIstanzesoggetticollegatiId id = new VwIstanzesoggetticollegatiId();
	id.setCodiceistanza(codiceIstanza);
	List<VwIstanzesoggetticollegati> soggIst = vwIstanzesoggetticollegatiService.findByIstanza(codiceIstanza);
	Set<Integer> codiciRichiedenti = new HashSet<Integer>();
	for (VwIstanzesoggetticollegati vwIsc : soggIst) {
	    codiciRichiedenti.add(vwIsc.getId().getCodicerichiedente());
	    if (vwIsc.getCodiceanagrafecoll() != null) {
		codiciRichiedenti.add(vwIsc.getCodiceanagrafecoll());
	    }
	    if (vwIsc.getCodiceprocuratore() != null) {
		codiciRichiedenti.add(vwIsc.getCodiceprocuratore());
	    }
	}
	List<Istanzeprocure> procures = this.findByIstanza(codiceIstanza);
	boolean errore = false;
	String messaggioErroreProcuraPre = "I richiedenti [";
	String messaggioErroreProcuraPost = "] presenti nelle procure dell'istanza non sono censiti tra i soggetti dell'istanza";
	List<String> anagrafeErr = new ArrayList<String>();
	for (Istanzeprocure ip : procures) {
	    if (EntityUtils.getNestedProperty(ip.getAnagrafeProcuratore(), "id.codice") != null) {
		if (!codiciRichiedenti.contains(ip.getAnagrafeProcuratore().getId().getCodice())) {
		    anagrafeErr.add(ip.getAnagrafeProcuratore().getDescrizioneRichiedente());
		    errore = true;
		}
	    }
	    if (EntityUtils.getNestedProperty(ip.getAnagrafeRappresentato(), "id.codice") != null) {
		if (!codiciRichiedenti.contains(ip.getAnagrafeRappresentato().getId().getCodice())) {
		    anagrafeErr.add(ip.getAnagrafeRappresentato().getDescrizioneRichiedente());
		    errore = true;
		}
	    }
	}
	if (errore) {
	    String anag = "";
	    if (anagrafeErr.size() > 0) {
		String[] a = new String[anagrafeErr.size()];
		a = anagrafeErr.toArray(a);
		anag = StringUtils.join(a, ",");
	    }
	    String errMesg = messaggioErroreProcuraPre + anag + messaggioErroreProcuraPost;
	    throw new BusinessValidationException(errMesg);
	}
	return true;
    }

    @Override
    public List<Istanzeprocure> findProvenientiDaSTC(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.isNull("oggettiId"));
	fr.addFilterField(FilterUtils.isNotNull("stcIdDocumento"));
	fr.addFilterField(FilterUtils.isNotNull("stcIdAllegato"));
	ft.addRestriction(fr);
	return istanzeprocureDAO.findByFilterTable(ft);
    }

    @Override
    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza) {

	return istanzeprocureDAO.findIstanzeprocureDTOByIstanza(codiceIstanza);
    }

    @Override
    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza, TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum) {

	return istanzeprocureDAO.findIstanzeprocureDTOByIstanza(codiceIstanza, tipoRicercaDocumentoEnum);
    }

    @Override
    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanzaNonInDocAutorizzazione(Integer codiceIstanza,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum, Integer codiceAutorizzazione) {

	List<IstanzeprocureDTO> istanzeprocureDTOs = this.findIstanzeprocureDTOByIstanza(codiceIstanza, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO);
	List<IstanzeprocureDTO> risultato = new ArrayList<IstanzeprocureDTO>();
	for (IstanzeprocureDTO istanzeprocureDTO : istanzeprocureDTOs) {
	    // controllo che per l'autorizzazione passata la procura non sia già stata associata all'aut tramite doc autorizzazioni
	    boolean isPresente = documentiAutorizzazioneService.isInAutorizzazione(codiceAutorizzazione, istanzeprocureDTO.getId().getCodice(),
		    "istanzeprocure");
	    log.debug(
		    "findIstanzeprocureDTOByIstanzaNonInDocAutorizzazione# Aut = {},codice doc anagrafe = {}, presente in Documentiautorizzazione = {}",
		    new Object[] { codiceAutorizzazione, istanzeprocureDTO.getId().getCodice(), isPresente });
	    if (!isPresente) {
		risultato.add(istanzeprocureDTO);
	    }
	}
	return risultato;
    }
}
