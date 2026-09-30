package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ImportoHelperBean;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.PosteggitipospazioService;
import it.gruppoinit.pal.gp.core.service.StradarioService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class MercatiDServiceImpl extends BaseServiceImpl<MercatiD, PkId> implements MercatiDService {

    private static final Logger log = LoggerFactory.getLogger(MercatiDServiceImpl.class);
    private MercatiDDAO mercatiDDAO;
    private MercatiService mercatiService;
    private PosteggitipospazioService posteggitipospazioService;
    private StradarioService stradarioService;

    @Autowired
    public void setMercatiDDAO(MercatiDDAO mercatiDDAO) {

	this.mercatiDDAO = mercatiDDAO;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setPosteggitipospazioService(PosteggitipospazioService posteggitipospazioService) {

	this.posteggitipospazioService = posteggitipospazioService;
    }

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Override
    public void delete(MercatiD entity) {

	if (isDeleteAllowed(entity))
	    mercatiDDAO.delete(entity);
    }

    @Override
    public List<MercatiD> findAll(Integer firstResult, Integer maxResult) {

	return mercatiDDAO.findAll(firstResult, maxResult);
    }

    @Override
    public MercatiD findById(PkId id) {

	return mercatiDDAO.findById(id);
    }

    @Override
    public void insert(MercatiD entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity))
	    mercatiDDAO.insert(entity);
    }

    @Override
    public void update(MercatiD entity) {

	dataIntegration(entity);
	if (validateEntity(entity))
	    mercatiDDAO.update(entity);
    }

    private void dataIntegration(MercatiD entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro MercatiD non può essere nullo");
	}
	if (entity.getConsentita() == null) {
	    entity.setConsentita(Boolean.FALSE);
	}
	if (entity.getDisabilitato() == null) {
	    entity.setDisabilitato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatiD entity) {

	Mercati mercato = mercatiService.bindDomainObject(entity.getMercati(), PkId.class, "id.codice");
	entity.setMercati(mercato);
	Posteggitipospazio tipospazio = posteggitipospazioService.bindDomainObject(entity.getTipoSpazio(), PkId.class, "id.codice");
	entity.setTipoSpazio(tipospazio);
	Stradario stradario = stradarioService.bindDomainObject(entity.getStradario(), PkId.class, "id.codice");
	entity.setStradario(stradario);
    }

    @Override
    protected Class<MercatiD> getEntityClass() {

	return MercatiD.class;
    }

    @Override
    public List<MercatiD> findAllByMercato(Mercati mercati) {

	return mercatiDDAO.findByMercato(mercati, PosteggiEnum.ALL);
    }

    @Override
    public List<MercatiD> findByMercato(Mercati mercati, PosteggiEnum posteggiEnum) {

	return mercatiDDAO.findByMercato(mercati, posteggiEnum);
    }

    @Override
    public List<MercatiD> findByPosteggiConConti(Mercati mercati, Integer anno) {

	return mercatiDDAO.findByPosteggiConConti(mercati, anno);
    }

    @Override
    public List<MercatiD> mostraCostoPosteggio(Mercati mercati, Integer anno, List<MercatiConti> mercatiContiList) {

	// ritorna la lista dei posteggi con dei mercaticonti settati
	List<MercatiD> mercatiDListConContiTemp = mercatiDDAO.findByPosteggiConConti(mercati, anno);
	// ritorna la lista di tutti i posteggi per quel mercato
	List<MercatiD> mercatiDList = mercatiDDAO.findByMercato(mercati, PosteggiEnum.ALL);
	// verifica che per quel mercato sono stati settate dei conti
	if (!mercatiContiList.isEmpty()) {
	    int i = 0;
	    // controlla per ogni posteggio del mercato se esistono dei conti gia settati
	    // se li trova (nella lista "mercatiDListConContiTemp") allora li setta altrimenti
	    // setta al posteggio i conti generali associati al mercato
	    boolean isInsert = false;
	    for (MercatiD mercatiD : mercatiDList) {
		for (MercatiD mercatiD2 : mercatiDListConContiTemp) {
		    if (mercatiD.getCodiceposteggio().equals(mercatiD2.getCodiceposteggio())) {
			MercatiD mercatiDtemp = completeListConti(mercatiContiList, mercatiD2);
			mercatiDList.set(i, mercatiDtemp);
			isInsert = true;
			break;
		    }
		}
		// setto ai posteggi che non hanno conti quelli di default del mercato
		if (isInsert == false) {
		    Set<MercatiDConti> list = new HashSet<MercatiDConti>();
		    MercatiDConti mercatoContiTemp = null;
		    for (MercatiConti mercatiConti : mercatiContiList) {
			mercatoContiTemp = new MercatiDConti();
			mercatoContiTemp.setConto(mercatiConti.getConti());
			mercatoContiTemp.setContesto(mercatiConti.getContesto());
			mercatoContiTemp.setValore(mercatiConti.getValore());
			mercatoContiTemp.setAnno(mercatiConti.getAnno().shortValue());
			mercatoContiTemp.setFlagCanone(mercatiConti.getFlagCanone());
			mercatoContiTemp.setFlagValore(mercatiConti.getFlagValore());
			mercatoContiTemp.setPercentualeConsorzio(mercatiConti.getPercentualeConsorzio());
			mercatoContiTemp.setFlagImportomensile(mercatiConti.getFlagImportomensile());
			list.add(mercatoContiTemp);
		    }
		    mercatiD.setListaContiPosteggio(list);
		    mercatiDList.set(i, mercatiD);
		}
		isInsert = false;
		i++;
	    }
	} else {
	    mercatiDList = mercatiDListConContiTemp;
	}
	return mercatiDList;
    }

    private MercatiD completeListConti(List<MercatiConti> mercatiContiList, MercatiD mercatiD) {

	Set<MercatiDConti> list = mercatiD.getListaContiPosteggio();
	boolean isPresente = false;
	for (MercatiConti mercatiConti : mercatiContiList) {
	    for (MercatiDConti mercatiDConti : list) {
		if (mercatiConti.getConti().getId().getCodice().intValue() == mercatiDConti.getConto().getId().getCodice().intValue()
			&& mercatiConti.getContesto().equals(mercatiDConti.getContesto())) {
		    isPresente = true;
		    break;
		}
	    }
	    if (isPresente == false) {
		MercatiDConti mercatoContiTemp = null;
		mercatoContiTemp = new MercatiDConti();
		mercatoContiTemp.setConto(mercatiConti.getConti());
		mercatoContiTemp.setContesto(mercatiConti.getContesto());
		mercatoContiTemp.setValore(mercatiConti.getValore());
		mercatoContiTemp.setAnno(mercatiConti.getAnno().shortValue());
		mercatoContiTemp.setPercentualeConsorzio(mercatiConti.getPercentualeConsorzio());
		mercatoContiTemp.setFlagCanone(mercatiConti.getFlagCanone());
		mercatoContiTemp.setFlagValore(mercatiConti.getFlagValore());
		mercatoContiTemp.setFlagImportomensile(mercatiConti.getFlagImportomensile());
		list.add(mercatoContiTemp);
	    }
	    isPresente = false;
	}
	mercatiD.setListaContiPosteggio(list);
	return mercatiD;
    }

    @Override
    public List<MercatiD> findPosteggioByMercatiMercatoUso(Mercati mercati) {

	return mercatiDDAO.findPosteggioByMercatiMercatoUso(mercati);
    }

    @Override
    public MercatiD findPosteggioByCodicePosteggio(String codiceposteggio, Mercati mercati) {

	return mercatiDDAO.findPosteggioByCodicePosteggio(codiceposteggio, mercati);
    }

    @Override
    public List<MercatiD> findByMercatiD(MercatiD filter) {

	return mercatiDDAO.findByMercatiD(filter);
    }

    @Override
    public List<MercatiD> deleteListaPosteggi(String[] arraycodici) {

	MercatiD posteggi = null;
	List<MercatiD> lista = new ArrayList<MercatiD>();
	// vado ad estrarre i posteggi uno alla volta e li elimino
	for (int i = 0; i < arraycodici.length; i++) {
	    posteggi = mercatiDDAO.findById(new PkId(Integer.valueOf(arraycodici[i])));
	    // lo inserisco prima prima in modo che nella lista ho l'oggetto che genera l'eccezione
	    lista.add(posteggi);
	    if (isDeleteAllowed(posteggi))
		mercatiDDAO.delete(posteggi);
	}
	return lista;
    }

    @Override
    public void updateMultiPosteggi(MercatiD mercatiD, String[] codiciPosteggio) {

	// per ogni codice posteggio vado a inserire le informazioni passate
	MercatiD mercatodTempInserimento = null;
	for (int i = 0; i < codiciPosteggio.length; i++) {
	    // Oggetto temporaneo utilizzato per fare i singoli inserimenti
	    mercatodTempInserimento = new MercatiD();
	    // creao una copia temporaneo in modo da mantere lo stato dell'oggetto mercatiD uguale
	    // a quello che ho quando lo recupero dal form
	    ReflectionUtils.shallowCopyFieldState(mercatiD, mercatodTempInserimento);
	    // Recupero il posteggio corrente che stiamo modificando (lo uso per recuperare i dati che non
	    // possono essere modificati)
	    MercatiD objDB = this.findById(new PkId(Integer.valueOf(codiciPosteggio[i])));
	    // Sono i tre campi che non possono variare con la modifica collettiva (inserisco quelli già
	    // presenti nel DB)
	    mercatodTempInserimento.setId(objDB.getId());
	    mercatodTempInserimento.setCodiceposteggio(objDB.getCodiceposteggio());
	    if (objDB.getNote() != null && !objDB.getNote().equals(""))
		mercatodTempInserimento.setNote(objDB.getNote());
	    // lo metto a null in quanto se il posteggio successivo non ha note inserirebbe quelle del
	    // precedente
	    mercatodTempInserimento.setNote(null);
	    // controllo i dati che voglio modificare, se nell'oggetto tempInserimento passato non sono valorizzati
	    // metto quelli dell'oggetto originale recuperato da DB.
	    if (mercatodTempInserimento.getLarghezza() == null) {
		mercatodTempInserimento.setLarghezza(objDB.getLarghezza());
	    }
	    if (mercatodTempInserimento.getLunghezza() == null) {
		mercatodTempInserimento.setLunghezza(objDB.getLunghezza());
	    }
	    if (mercatodTempInserimento.getSuperficie() == null) {
		mercatodTempInserimento.setSuperficie(objDB.getSuperficie());
	    }
	    if (mercatodTempInserimento.getTipoSpazio() == null) {
		mercatodTempInserimento.setTipoSpazio(objDB.getTipoSpazio());
	    }
	    if (mercatodTempInserimento.getStradario() == null) {
		mercatodTempInserimento.setStradario(objDB.getStradario());
	    }
	    if (mercatodTempInserimento.getDisabilitato() == null) {
		mercatodTempInserimento.setDisabilitato(objDB.getDisabilitato());
	    }
	    // inserisco il singolo posteggio
	    this.update(mercatodTempInserimento);
	}
    }

    protected boolean isDeleteAllowed(MercatiD entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getAnagrafemercatipresenzes().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ANAGRAFEMERCATIPRESENZE", null));
	}
	// FIXME controllare autorizzazioni,concessioni,subentri collegati!!!!
	// if (entity.getConcessionis().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "CONCESSIONI", null));
	// }
	if (entity.getMercatipresenzeDs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATIPRESENZE_D", null));
	}
	if (!entity.getMercatidLettures().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATID_LETTURE", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    /**
     * Il metodo deve controllare che non esiste già un record con il codice posteggio che stiamo passando
     * 
     * @param entity
     * @return
     */
    protected boolean isInsertAllowed(MercatiD entity) {

	// controlla che già non esista un recordo con quel codice posteggio
	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	MercatiD objectDB = null;
	objectDB = mercatiDDAO.findPosteggioByCodicePosteggio(entity.getCodiceposteggio(), entity.getMercati());
	if (objectDB != null && objectDB.getId().getCodice() != null) {
	    _ivs.add(new InvalidValue("mercadid.service_error.duplicate_codice", null, null, entity.getCodiceposteggio(), null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    protected MercatiD customBindDomainObject(MercatiD entity) {

	if (entity == null) {
	    return null;
	}
	Integer codiceMercato = (Integer) EntityUtils.getNestedProperty(entity.getMercati(), "id.codice");
	String codicePosteggio = entity.getCodiceposteggio();
	if (codiceMercato != null && StringUtils.isNotBlank(codicePosteggio)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	    fr.addFilterField(FilterUtils.equals("codiceposteggio", codicePosteggio, String.class));
	    ft.addRestriction(fr);
	    List<MercatiD> posteggi = mercatiDDAO.findByFilterTable(ft, 0, 5);
	    if (posteggi.size() == 1) {
		return posteggi.get(0);
	    }
	}
	return null;
    }

    @Override
    public List<MercatiD> findByCodicePosteggioAndMercato(String textToSearch, Mercati mercati) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("mercati", mercati, MercatiUso.class));
	filterRestriction.addFilterField(FilterUtils.like("codiceposteggio", textToSearch));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("codiceposteggio"));
	return this.findByFilterTable(filterTable, null, null);
    }

    @Override
    public List<MercatiD> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return mercatiDDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public void updateCodicePosteggio(MercatiD mercatiD, String codicePosteggio) {

	if (isNotDuplicatePosteggio(mercatiD, codicePosteggio)) {
	    mercatiD.setCodiceposteggio(codicePosteggio);
	    mercatiDDAO.update(mercatiD);
	}
    }

    // controlla che per il mercato a cui appartiene il posteggio non esista già un posteggio con il codice che andiamo ad inserire.
    // Un ulteriore controllo verifica il caso che se andiamo a modificare un posteggio e lasciamo lo stesso codice, non deve dare errore.
    private boolean isNotDuplicatePosteggio(MercatiD entity, String codicePosteggio) {

	Set<MercatiD> list = entity.getMercati().getMercatiDs();
	for (MercatiD mercatiD : list) {
	    if (mercatiD.getCodiceposteggio().equals(codicePosteggio) && !mercatiD.getId().getCodice().equals(entity.getId().getCodice())) {
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		_ivs.add(new InvalidValue("mercadid.service_error.duplicate_codice", null, null, null, null));
		if (!_ivs.isEmpty()) {
		    this.throwValidationMessages(_ivs);
		}
	    }
	}
	return true;
    }

    @Override
    public PosteggioImportoHelper calcolaCostoPosteggioAnnuale(MercatiD posteggio, Integer anno, String contesto) {

	PosteggioImportoHelper result = new PosteggioImportoHelper();
	BigDecimal importoIndividualePosteggio = new BigDecimal(0);
	boolean condizioneContesto = true;
	if (contesto == null || contesto.equals("")) {
	    contesto = WebConstants.MERCATO_CONTESTO_TUTTI;
	}
	//
	BigDecimal importo = new BigDecimal(0);
	Map<String, ImportoHelperBean> righeImporti = new HashMap<String, ImportoHelperBean>();
	Set<MercatiConti> mercatoConti = posteggio.getMercati().getMercatiContis();
	String contestoConto = "";
	for (MercatiConti contoMercato : mercatoConti) {
	    contestoConto = contoMercato.getContesto();
	    if (null == contestoConto || contestoConto.equals("")) { // se nel conto contesto non è specificato allora
		// lo associo a TUTTI
		contestoConto = WebConstants.MERCATO_CONTESTO_TUTTI;
	    }
	    // condizione contesto =
	    // se contesto = "Concessionari" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Concessionari" allora false e viceversa
	    // se contesto = "Tutti" e contestoConto = "Tutti" allora true
	    condizioneContesto = (contestoConto.equalsIgnoreCase(contesto) || contestoConto.equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI) || contesto
		    .equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI));
	    if (contoMercato.getAnno().compareTo(anno) == 0 && condizioneContesto) {
		// importo = contoMercato.getValore();
		importo = recuperaImportoRidettato(contoMercato.getValore(), contoMercato.getPercentualeConsorzio());
		Boolean flagValore = contoMercato.getFlagValore();
		if (null == flagValore) {
		    flagValore = false;
		}
		ImportoHelperBean ihb = new ImportoHelperBean(contoMercato.getConti(), importo, flagValore.booleanValue(),
			contoMercato.getContesto(), BooleanUtils.isTrue(contoMercato.getFlagImportomensile()));
		righeImporti.put(String.valueOf(contoMercato.getConti().getId().getCodice()) + "-" + contoMercato.getContesto(), ihb);
	    }
	}
	Set<MercatiDConti> posteggioConti = posteggio.getListaContiPosteggio();
	for (MercatiDConti contoPosteggio : posteggioConti) {
	    contestoConto = contoPosteggio.getContesto();
	    if (null == contestoConto || contestoConto.equals("")) { // se nel conto contesto non è specificato allora
		// lo associo a TUTTI
		contestoConto = WebConstants.MERCATO_CONTESTO_TUTTI;
	    }
	    // condizione contesto
	    // se contesto = "Concessionari" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Concessionari" allora false e viceversa
	    // se contesto = "Tutti" e contestoConto = "Tutti" allora true
	    condizioneContesto = (contestoConto.equalsIgnoreCase(contesto) || contestoConto.equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI) || contesto
		    .equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI));
	    if (contoPosteggio.getAnno().shortValue() == anno.shortValue() && condizioneContesto) {
		// importo = contoPosteggio.getValore();
		importo = recuperaImportoRidettato(contoPosteggio.getValore(), contoPosteggio.getPercentualeConsorzio());
		Boolean flagValore = contoPosteggio.getFlagValore();
		if (null == flagValore) {
		    flagValore = false;
		}
		ImportoHelperBean ihb = new ImportoHelperBean(contoPosteggio.getConto(), importo, flagValore.booleanValue(),
			contoPosteggio.getContesto(), BooleanUtils.isTrue(contoPosteggio.getFlagImportomensile()));
		righeImporti.put(String.valueOf(contoPosteggio.getConto().getId().getCodice()) + "-" + contoPosteggio.getContesto(), ihb);
	    }
	}
	// elimino i duplicati usando un criterio di preferenza.
	// la riga con il contesto specifico prevale sulla riga con il contesto tutti
	for (MercatiConti contoMercato : mercatoConti) {
	    if (contoMercato.getAnno().compareTo(anno) == 0) {
		String chiaveIhbTutti = contoMercato.getConti().getId().getCodice() + "-" + WebConstants.MERCATO_CONTESTO_TUTTI;
		String chiaveIhbContestoSpecifico = contoMercato.getConti().getId().getCodice() + "-" + contesto;
		ImportoHelperBean tuttiIhb = righeImporti.get(chiaveIhbTutti);
		ImportoHelperBean contestoIhb = righeImporti.get(chiaveIhbContestoSpecifico);
		if (!chiaveIhbTutti.equalsIgnoreCase(chiaveIhbContestoSpecifico)) {
		    // rimuovo la chiave solo se la chiave del contesto specifico è <> da
		    // WebConstants.MERCATO_CONTESTO_TUTTI
		    if (tuttiIhb != null) {
			if (contestoIhb != null) {
			    righeImporti.remove(chiaveIhbTutti);
			}
		    }
		}
	    }
	}
	if (righeImporti.size() == 0) {
	    // FIXME non c'è nessuna riga di importo o lancio errore?
	    // non registro niente
	    return null;
	}
	posteggio = findById(posteggio.getId());
	List<RigaImporto> righeImportiPosteggio = new ArrayList<RigaImporto>();
	Collection<ImportoHelperBean> importi = righeImporti.values();
	for (ImportoHelperBean importoHelperBean : importi) {
	    RigaImporto riga = new RigaImporto();
	    importoIndividualePosteggio = importoHelperBean.getImporto();
	    if (log.isDebugEnabled()) {
		log.debug("calcolaCostoPosteggio(): TARIFFA Canone = " + importoIndividualePosteggio);
	    }
	    // devo fare tante registrazioni importi per quante rate sono state configurate
	    // e con la percentuale e la scadenza da spalmare sulle righe di importo
	    riga.setConto(importoHelperBean.getConto());
	    // .. IMPORTANTE SETTO LA SCALA DEL DECIMALE ALTRIMENTI DA ERRORE
	    // .. IL VALIDATORE DELL'OGGETTO DI DOMINIO
	    importoIndividualePosteggio = importoIndividualePosteggio.setScale(2, BigDecimal.ROUND_HALF_UP);
	    riga.setImporto(importoIndividualePosteggio);
	    riga.setValoreMensile(importoHelperBean.isValoreMensile());
	    Integer iva = importoHelperBean.getConto().getIva();
	    if (iva == null) {
		// iva = WebConstants.CONST_IVA;
		throw new RuntimeException("Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto "
			+ importoHelperBean.getConto().getDescrizione() + "(" + importoHelperBean.getConto().getId() + ")");
	    }
	    riga.setIva(iva);
	    righeImportiPosteggio.add(riga);
	}
	result.setListaImporti(righeImportiPosteggio);
	return result;
    }

    private BigDecimal recuperaImportoRidettato(BigDecimal importo, BigDecimal percConvenzione) {

	if (percConvenzione != null) {
	    if (percConvenzione.compareTo(BigDecimal.ZERO) != 0) {
		if (importo != null) {
		    if (importo.compareTo(BigDecimal.ZERO) != 0) {
			return importo.multiply(percConvenzione).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
		    }
		}
	    }
	}
	return importo;
    }

    @Override
    public List<MercatiD> findByMercatoOrderByPeso(Mercati mercati, PosteggiEnum posteggiEnum) {

	return mercatiDDAO.findByMercatoOrderByPeso(mercati, posteggiEnum);
    }

    @Override
    public List<CodiceDescrizioneBean> findPosteggiNonAssegnatiByMercato(Integer codiceMercato, Integer codiceMercatiUso, PosteggiEnum tipo) {

	return mercatiDDAO.findPosteggiNonAssegnatiByMercato(codiceMercato, codiceMercatiUso, tipo);
    }
}
