/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AmministrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.ConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniresponsabiliService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniruoliService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.EmailService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author francescop
 * @author gianpaolot
 */
@Service
public class AmministrazioniServiceImpl extends BaseServiceImpl<Amministrazioni, PkId> implements AmministrazioniService {

    private static final Logger log = LoggerFactory.getLogger(AmministrazioniServiceImpl.class);
    private static final String ENCRYPTING_ALGORITHM = "MD5";
    private AmministrazioniDAO amministrazioniDAO;
    private AmministrazioniruoliService amministrazioniruoliService;
    private AmministrazioniresponsabiliService amministrazioniresponsabiliService;
    private ConfigurazioneService configurazioneService;
    private ConfigurazioneDAO configurazioneDAO;
    private AllegatiService allegatiService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private AmministrazionireferentiService amministrazionireferentiService;
    private ProtocolloRegistriService protocolloRegistriService;
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setConfigurazioneDAO(ConfigurazioneDAO configurazioneDAO) {

	this.configurazioneDAO = configurazioneDAO;
    }

    @Autowired
    public void setAmministrazioniruoliService(AmministrazioniruoliService amministrazioniruoliService) {

	this.amministrazioniruoliService = amministrazioniruoliService;
    }

    @Autowired
    public void setAmministrazioniresponsabiliService(AmministrazioniresponsabiliService amministrazioniresponsabiliService) {

	this.amministrazioniresponsabiliService = amministrazioniresponsabiliService;
    }

    @Autowired
    public void setAmministrazioniDAO(AmministrazioniDAO amministrazioniDAO) {

	this.amministrazioniDAO = amministrazioniDAO;
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
    public void setAmministrazionireferentiService(AmministrazionireferentiService amministrazionireferentiService) {

	this.amministrazionireferentiService = amministrazionireferentiService;
    }

    @Autowired
    public void setProtocolloRegistriService(ProtocolloRegistriService protocolloRegistriService) {

	this.protocolloRegistriService = protocolloRegistriService;
    }

    @Autowired
    public void setInventarioprocedimentisoftwareService(InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService) {

	this.inventarioprocedimentisoftwareService = inventarioprocedimentisoftwareService;
    }

    @Override
    protected Class<Amministrazioni> getEntityClass() {

	return Amministrazioni.class;
    }

    @Override
    public void delete(Amministrazioni entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    amministrazioniDAO.delete(entity);
	}
    }

    @Override
    public List<Amministrazioni> findAll(Integer firstResult, Integer maxResult) {

	Integer[] codiciAmministrazioniInterne = configurazioneDAO.getCodiciTutteEStessaAmministrazioniSistema();
	List<Amministrazioni> amministrazionis = amministrazioniDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "amministrazione",
		DAOOrderTypeEnum.ASC);
	List<Amministrazioni> listaAmmDaRimuovere = new ArrayList<Amministrazioni>();
	if (!amministrazionis.isEmpty()) {
	    if (null != codiciAmministrazioniInterne && codiciAmministrazioniInterne.length > 0) {
		for (Amministrazioni amministrazione : amministrazionis) {
		    Integer codAmm = amministrazione.getId().getCodice();
		    for (Integer codAmmInterna : codiciAmministrazioniInterne) {
			if (codAmmInterna.intValue() == codAmm.intValue()) {
			    listaAmmDaRimuovere.add(amministrazione);
			    break;
			}
		    }
		}
		// elimino dalla lista visibile all'utente le amministrazioni di sistema
		for (Amministrazioni amministrazione : listaAmmDaRimuovere) {
		    amministrazionis.remove(amministrazione);
		}
	    }
	}
	return amministrazionis;
    }

    @Override
    public Amministrazioni findById(PkId id) {

	return amministrazioniDAO.findById(id);
    }

    @Override
    public void insert(Amministrazioni entity) {

	dataIntegration(entity);
	checkPassword(entity, false);
	if (validateEntity(entity)) {
	    amministrazioniDAO.insert(entity);
	}
    }

    @Override
    protected boolean validateEntity(Amministrazioni entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	if (StringUtils.isBlank(entity.getCodiceancitel())) {
	//	    _ivs.add(new InvalidValue("alert.required", entity.getClass(), "codiceancitel", entity.getCodiceancitel(), entity));
	//	} else {
	//	    it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap[] valoris = it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap
	//		    .values();
	//	    boolean trovato = false;
	//	    for (it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap attoreReteSuap : valoris) {
	//		if (attoreReteSuap.name().equals(entity.getCodiceancitel())) {
	//		    trovato = true;
	//		    break;
	//		}
	//	    }
	//	    if (!trovato) {
	//		String valoriAmmessi = "";
	//		for (it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap attoreReteSuap : valoris) {
	//		    valoriAmmessi += attoreReteSuap.name() + ",";
	//		}
	//		_ivs.add(new InvalidValue("Sono ammessi solamente i valori " + valoriAmmessi, entity.getClass(), "codiceancitel", entity
	//			.getCodiceancitel(), entity));
	//	    }
	//	}
	if (_ivs.size() > 0) {
	    throw new EntityValidationException(_ivs, getMessageFromBundle("error.entity_error_message", null), null);
	}
	return super.validateEntity(entity);
    }

    private void dataIntegration(Amministrazioni entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'amministrazione passata è nulla");
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	}
	if (entity.getFlagAmministrazioneinterna() == null) {
	    entity.setFlagAmministrazioneinterna(Boolean.FALSE);
	}
    }

    public static void main(String[] args) {

	System.out.println(it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap.values());
    }

    @Override
    public void update(Amministrazioni entity) {

	dataIntegration(entity);
	checkPassword(entity, true);
	if (validateEntity(entity)) {
	    amministrazioniDAO.update(entity);
	    entity = this.findById(entity.getId());
	    if (entity.getFlagAmministrazioneinterna() == false) {
		//		Set<Amministrazioniresponsabili> amministrazioniresponsabiliSet = entity.getAmministrazioniresponsabilis();
		//		List<Amministrazioniresponsabili> listresp = new ArrayList<Amministrazioniresponsabili>();
		//		for (Iterator<Amministrazioniresponsabili> iterator = amministrazioniresponsabiliSet.iterator(); iterator.hasNext();) {
		//		    Amministrazioniresponsabili amministrazioniresponsabili = iterator.next();
		//		    listresp.add(amministrazioniresponsabili);
		//		}
		//		for (int i = 0; i < listresp.size(); i++) {
		//		    if (!listresp.isEmpty()) {
		//			Amministrazioniresponsabili amministrazioniresponsabili = listresp.get(i);
		//			listresp.remove(amministrazioniresponsabili);
		//			amministrazioniresponsabiliSet.remove(amministrazioniresponsabili);
		//			amministrazioniresponsabiliService.delete(amministrazioniresponsabili);
		//			amministrazioniDAO.update(entity);
		//			i--;
		//		    }
		//		}
		//		Set<Amministrazioniruoli> amministrazioniruoliSet = entity.getAmministrazioniruolis();
		//		List<Amministrazioniruoli> listruoli = new ArrayList<Amministrazioniruoli>();
		//		for (Iterator<Amministrazioniruoli> iterator = amministrazioniruoliSet.iterator(); iterator.hasNext();) {
		//		    Amministrazioniruoli amministrazioniruoli = iterator.next();
		//		    listruoli.add(amministrazioniruoli);
		//		}
		//		for (int i = 0; i < listruoli.size(); i++) {
		//		    if (!listruoli.isEmpty()) {
		//			Amministrazioniruoli amministrazioniruoli = listruoli.get(i);
		//			listruoli.remove(amministrazioniruoli);
		//			amministrazioniruoliSet.remove(amministrazioniruoli);
		//			amministrazioniruoliService.delete(amministrazioniruoli);
		//			amministrazioniDAO.update(entity);
		//			i--;
		//		    }
		//		}
	    }
	}
    }

    @Override
    public List<Amministrazioni> findByAmministrazione(String amministrazione, boolean tutteLeAmministrazioni, boolean includiDisabilitate,
	    Integer[] codiciAmministrazioniEscluse) {

	return amministrazioniDAO.findByAmministrazione(amministrazione, tutteLeAmministrazioni, includiDisabilitate, codiciAmministrazioniEscluse);
    }

    @Override
    public List<Amministrazioni> findAmministrazioniByDescrizione(String amministrazione) {

	return amministrazioniDAO.findAmministrazioniByDescrizione(amministrazione);
    }

    @Override
    public List<Amministrazioni> findAmministrazioniByDescrizioneForProtocolloRegistri(String amministrazione, boolean includiDisabilitate,
	    String codiceComune, String software) {

	return amministrazioniDAO.findAmministrazioniByDescrizioneForProtocolloRegistri(amministrazione, includiDisabilitate, codiceComune, software);
    }

    @Override
    public boolean isAmministrazioneInternaEsiste() {

	return amministrazioniDAO.isAmministrazioneInternaEsiste();
    }

    @Override
    public List<AmministrazioniHelper> findAllDTO(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	return amministrazioniDAO.findAllDTO(firstResult, maxResult, whereClauseMandatoryFields, orderProperty, orderType);
    }

    protected boolean isDeleteAllowed(Amministrazioni entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	if (!emailService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "EMAIL", null));
	//	}
	if (!allegatiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALLEGATI", null));
	}
	//	if (!documentiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DOCUMENTI", null));
	//	}
	if (!inventarioprocedimentiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTI", null));
	}
	if (!amministrazionireferentiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "AMMINISTRAZIONIREFERENTI", null));
	}
	if (!protocolloRegistriService.findByAmministrazioniMittente(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI_MITTENTE", null));
	}
	//	if (!protocolloRegistriService.findByAmministrazioniDestinatario(entity.getId().getCodice(), 0, 1).isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI_DESTINATARIO", null));
	//	}
	if (!inventarioprocedimentisoftwareService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTISOFTWARE", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Amministrazioni> findByAmministrazioniInterne() {

	return amministrazioniDAO.findByAmministrazioniInterne();
    }

    @Override
    public Amministrazioni findAmministrazioniByCodiceancitel(String codiceancitel) {

	return amministrazioniDAO.findAmministrazioniByCodiceancitel(codiceancitel);
    }

    /**
     * La funzione controlla se la password è stata passata e nel caso la cripta con l'algoritmo MD5
     * 
     * @param entity
     * @param isUpdate
     */
    private void checkPassword(Amministrazioni entity, boolean isUpdate) {

	String password = "";
	if (StringUtils.isBlank(entity.getPasswordClear())) {
	    if (isUpdate) {
		Amministrazioni copy = this.findById(entity.getId());
		password = copy.getPassword();
		amministrazioniDAO.evict(copy);
		entity.setPassword(password);
		return;
	    }
	} else {
	    String passwordClear = entity.getPasswordClear();
	    password = Utilities.getHashText(passwordClear, ENCRYPTING_ALGORITHM, false);
	    entity.setPassword(password);
	    entity.setPasswordClear(null);
	}
    }

    /**
     * @see AmministrazioniService#findAmministrazioniSTC()
     */
    @Override
    public List<Amministrazioni> findAmministrazioniSTC() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.isNotNull("stcIdnodo"));
	restriction.addFilterField(FilterUtils.isNotNull("stcIdente"));
	restriction.addFilterField(FilterUtils.isNotNull("stcIdsportello"));
	filterTable.addRestriction(restriction);
	return amministrazioniDAO.findByFilterTable(filterTable);
    }

    @Override
    public Amministrazioni findAmministrazioneSportelloUnico() {

	Configurazione conf = configurazioneService.findById(new ConfigurazioneId(WebConstants.SOFTWARE_TT));
	if (conf != null) {
	    Integer codammsportellounico = conf.getCodammsportellounico();
	    if (codammsportellounico != null) {
		return this.findById(new PkId(codammsportellounico));
	    }
	}
	return null;
    }

    @Override
    protected void childDelete(Amministrazioni entity) {

	//Set<Amministrazioniruoli> amministrazioniruolis = entity.getAmministrazioniruolis();
	//	for (Amministrazioniruoli amministrazioniruoli : amministrazioniruolis) {
	//	    amministrazioniruoliService.delete(amministrazioniruoli);
	//	}
	//	Set<Amministrazioniresponsabili> amministrazioniresponsabilis = entity.getAmministrazioniresponsabilis();
	//	for (Amministrazioniresponsabili amministrazioniresponsabili : amministrazioniresponsabilis) {
	//	    amministrazioniresponsabiliService.delete(amministrazioniresponsabili);
	//	}
    }

    @Override
    public Amministrazioni findAmministrazioniSTC(String idNodo, String idEnte, String idSportello) {

	Assert.hasText(idNodo, "Il parametro idNodo non può essere vuoto");
	Assert.hasText(idEnte, "Il parametro idEnte non può essere vuoto");
	Assert.hasText(idSportello, "Il parametro idSportello non può essere vuoto");
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("stcIdnodo", idNodo, String.class));
	restriction.addFilterField(FilterUtils.equals("stcIdente", idEnte, String.class));
	restriction.addFilterField(FilterUtils.equals("stcIdsportello", idSportello, String.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderDesc("id.codice"));
	List<Amministrazioni> amms = amministrazioniDAO.findByFilterTable(filterTable);
	if (!amms.isEmpty()) {
	    return amms.get(0);
	}
	return null;
    }

    @Override
    public boolean checkSeDisabilitare(Amministrazioni amministrazioni) {

	List<Inventarioprocedimenti> procs = inventarioprocedimentiService.findByAmministrazioni(amministrazioni.getId().getCodice(), 0, 2);
	if (procs.size() > 0) {
	    return false;
	}
	List<ProtocolloRegistri> prergs = protocolloRegistriService.findByAmministrazioniMittente(amministrazioni.getId().getCodice(), 0, 2);
	if (prergs.size() > 0) {
	    return false;
	}
	prergs = protocolloRegistriService.findByAmministrazioniDestinatario(amministrazioni.getId().getCodice(), 0, 2);
	if (prergs.size() > 0) {
	    return false;
	}
	return true;
    }

    @Override
    public Amministrazioni findByCodiceamministrazioneCart(String amministrazioneCart) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceCart", amministrazioneCart, String.class));
	ft.addRestriction(fr);
	List<Amministrazioni> amministrazionis = amministrazioniDAO.findByFilterTable(ft);
	if (!amministrazionis.isEmpty()) {
	    return amministrazionis.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public boolean updateAmministrazioniCartAndValidateConfiguration(String[] codiciAmministrazioni, String[] codiciAmministrazioniCart) {

	if (log.isDebugEnabled()) {
	    log.debug("updateAmministrazioniCartAndValidateConfiguration# Inizio aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
	}
	boolean checkValue = true;
	List<String> codiceAmministrazioniNonValutati = new ArrayList<String>();
	// inserisco le configurazione dei codici amministrazione cart 
	for (int i = 0; i < codiciAmministrazioniCart.length; i++) {
	    if (i < codiciAmministrazioni.length && StringUtils.isNotBlank(codiciAmministrazioni[i])) {
		String codiceAmmCart = codiciAmministrazioniCart[i];
		// Controllo se per il codAmministrazione c'era già un amministrazione configurata
		// Se si devo togliere la configurazione
		Amministrazioni amministrazioniConfigurata = this.findByCodiceamministrazioneCart(codiceAmmCart);
		if (amministrazioniConfigurata != null) {
		    amministrazioniConfigurata.setCodiceCart(null);
		    this.update(amministrazioniConfigurata);
		}
		// Aggiono la configurazione alla nuova amministrazione
		Amministrazioni amministrazioni = amministrazioniDAO.findById(new PkId(Integer.parseInt(codiciAmministrazioni[i].trim())));
		amministrazioni.setCodiceCart(codiceAmmCart);
		this.update(amministrazioni);
	    } else {
		codiceAmministrazioniNonValutati.add(codiciAmministrazioniCart[i].trim());
	    }
	}
	// Controllo se i codici amministrazione cart non valutati sono già stati associati
	for (String codAmmCart : codiceAmministrazioniNonValutati) {
	    String codice = codAmmCart;
	    Amministrazioni amministrazioniTemp = this.findByCodiceamministrazioneCart(codice);
	    if (amministrazioniTemp == null) {
		if (log.isDebugEnabled()) {
		    log.debug(
			    "updateAmministrazioniCartAndValidateConfiguration# Trovato codice amministrazione cart {} non valutato (potrebbero essere presenti altri)",
			    codice);
		}
		checkValue = false;
		break;
	    }
	}
	// Devo committare perchè i dati appena inseriti mi servono per il metodo del service
	// updateTipimovimentoCartCartAndValidateConfiguration(..)
	amministrazioniDAO.flush();
	if (log.isDebugEnabled()) {
	    log.debug("updateAmministrazioniCartAndValidateConfiguration# Fine aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
	}
	return checkValue;
    }

    //    @Override
    //    public boolean updateTipimovimentoCartAndValidateConfiguration(String[] codiciTipoMov, String[] codiciAmministrazioniCart) {
    //
    //	if (log.isDebugEnabled()) {
    //	    log.debug("updateAmministrazioniCartAndValidateConfiguration# Inizio aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
    //	}
    //	boolean checkValue = true;
    //	List<String> codiceAmministrazioniNonValutati = new ArrayList<String>();
    //	for (int i = 0; i < codiciAmministrazioniCart.length; i++) {
    //	    if (i < codiciTipoMov.length && StringUtils.isNotBlank(codiciTipoMov[i])) {
    //		// Controllo se esiste un amministrazione con il codiciAmministrazioniCart i-esimo
    //		Amministrazioni amministrazioniTemp = this.findByCodiceamministrazioneCart(codiciAmministrazioniCart[i].trim());
    //		if (amministrazioniTemp != null) {
    //		    Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(codiciTipoMov[i].trim()));
    //		    amministrazioniTemp.setTipimovimento(tipimovimento);
    //		    this.update(amministrazioniTemp);
    //		} else {
    //		    codiceAmministrazioniNonValutati.add(codiciAmministrazioniCart[i].trim());
    //		}
    //	    } else {
    //		codiceAmministrazioniNonValutati.add(codiciAmministrazioniCart[i].trim());
    //	    }
    //	}
    //	// Controllo se i codici amministrazione cart non valutati sono già stati associati
    //	for (String codAmmCart : codiceAmministrazioniNonValutati) {
    //	    String codice = codAmmCart;
    //	    Amministrazioni amministrazioniTemp = this.findByCodiceamministrazioneCart(codice);
    //	    if (amministrazioniTemp != null) {
    //		if (EntityUtils.getNestedProperty(amministrazioniTemp.getTipimovimento(), "id.tipomovimento") == null) {
    //		    checkValue = false;
    //		    break;
    //		}
    //	    } else {
    //		checkValue = false;
    //		break;
    //	    }
    //	}
    //	if (log.isDebugEnabled()) {
    //	    log.debug("updateAmministrazioniCartAndValidateConfiguration# Fine aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
    //	}
    //	return checkValue;
    //    }
    @Override
    public List<Amministrazioni> findAmministrazioniWithEmailByDescrizione(String amministrazione) {

	return amministrazioniDAO.findAmministrazioniWithEmailByDescrizione(amministrazione);
    }

    @Override
    public List<Amministrazioni> findAmministrazioniByPECAddress(String pecAddress) {

	List<Amministrazioni> results = new ArrayList<Amministrazioni>();
	if (StringUtils.isNotBlank(pecAddress)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction notDisabled = new FilterRestriction();
	    notDisabled.addFilterField(FilterUtils.equals("flagDisabilitato", Boolean.FALSE, Boolean.class));
	    ft.addRestriction(notDisabled);
	    FilterRestriction byPec = new FilterRestriction();
	    byPec.addFilterField(FilterUtils.equals("pec", pecAddress, String.class));
	    ft.addRestriction(byPec);
	    results = amministrazioniDAO.findByFilterTable(ft);
	}
	return results;
    }
}
