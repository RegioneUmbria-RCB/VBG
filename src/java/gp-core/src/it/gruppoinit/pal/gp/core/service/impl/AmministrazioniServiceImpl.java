/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AmministrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.ConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniAnagrafe;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.TipimovStcModelli;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.amministrazioni.eventi.EventoEmailAmministrazioneAggiornata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniAnagrafeService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniresponsabiliService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniruoliService;
import it.gruppoinit.pal.gp.core.service.CdsinvitatiService;
import it.gruppoinit.pal.gp.core.service.CommedilizieAppelloService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.EmailService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniInOutService;
import it.gruppoinit.pal.gp.core.service.TempirispostaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovStcAlberoprocService;
import it.gruppoinit.pal.gp.core.service.TipimovStcAltridatiService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.TipimovStcModelliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

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
    private TipicontromovimentoService tipicontromovimentoService;
    private ConfigurazioneService configurazioneService;
    private ConfigurazioneDAO configurazioneDAO;
    private RegistrazioniInOutService registrazioniInOutService;
    private TipimovStcMappingService tipimovStcMappingService;
    private TipimovStcAltridatiService tipimovStcAltridatiService;
    private EmailService emailService;
    private AllegatiService allegatiService;
    private CdsinvitatiService cdsinvitatiService;
    private DocumentiService documentiService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private AlboPubblicazioniService alboPubblicazioniService;
    private MovimentiService movimentiService;
    private CommedilizieAppelloService commedilizieAppelloService;
    private AmministrazionireferentiService amministrazionireferentiService;
    private ProtocolloRegistriService protocolloRegistriService;
    private CommedilizieTipologieService commedilizieTipologieService;
    private TempirispostaService tempirispostaService;
    private TipimovStcAlberoprocService tipimovStcAlberoprocService;
    private TipimovStcModelliService tipimovStcModelliService;
    private TipiMovimentoService tipiMovimentoService;
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    private IstanzeService istanzeService;
    private AlberoprocService alberoprocService;
    private AmministrazioniAnagrafeService amministrazioniAnagrafeService;
    private ComuniService comuniService;
    @Autowired
    private IEventPublisher eventPublisher;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setConfigurazioneDAO(ConfigurazioneDAO configurazioneDAO) {

	this.configurazioneDAO = configurazioneDAO;
    }

    @Autowired
    public void setTipicontromovimentoService(TipicontromovimentoService tipicontromovimentoService) {

	this.tipicontromovimentoService = tipicontromovimentoService;
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
    public void setRegistrazioniInOutService(RegistrazioniInOutService registrazioniInOutService) {

	this.registrazioniInOutService = registrazioniInOutService;
    }

    @Autowired
    public void setTipimovStcMappingService(TipimovStcMappingService tipimovStcMappingService) {

	this.tipimovStcMappingService = tipimovStcMappingService;
    }

    @Autowired
    public void setTipimovStcAltridatiService(TipimovStcAltridatiService tipimovStcAltridatiService) {

	this.tipimovStcAltridatiService = tipimovStcAltridatiService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {

	this.emailService = emailService;
    }

    @Autowired
    public void setAllegatiService(AllegatiService allegatiService) {

	this.allegatiService = allegatiService;
    }

    @Autowired
    public void setCdsinvitatiService(CdsinvitatiService cdsinvitatiService) {

	this.cdsinvitatiService = cdsinvitatiService;
    }

    @Autowired
    public void setDocumentiService(DocumentiService documentiService) {

	this.documentiService = documentiService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setAlboPubblicazioniService(AlboPubblicazioniService alboPubblicazioniService) {

	this.alboPubblicazioniService = alboPubblicazioniService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setCommedilizieAppelloService(CommedilizieAppelloService commedilizieAppelloService) {

	this.commedilizieAppelloService = commedilizieAppelloService;
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
    public void setCommedilizieTipologieService(CommedilizieTipologieService commedilizieTipologieService) {

	this.commedilizieTipologieService = commedilizieTipologieService;
    }

    @Autowired
    public void setTempirispostaService(TempirispostaService tempirispostaService) {

	this.tempirispostaService = tempirispostaService;
    }

    @Autowired
    public void setTipimovStcAlberoprocService(TipimovStcAlberoprocService tipimovStcAlberoprocService) {

	this.tipimovStcAlberoprocService = tipimovStcAlberoprocService;
    }

    @Autowired
    public void setTipimovStcModelliService(TipimovStcModelliService tipimovStcModelliService) {

	this.tipimovStcModelliService = tipimovStcModelliService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setInventarioprocedimentisoftwareService(InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService) {

	this.inventarioprocedimentisoftwareService = inventarioprocedimentisoftwareService;
    }

    @Autowired
    public void setAmministrazioniAnagrafeService(AmministrazioniAnagrafeService amministrazioniAnagrafeService) {

	this.amministrazioniAnagrafeService = amministrazioniAnagrafeService;
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
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Amministrazioni entity) {

	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
    }

    @Override
    public void update(Amministrazioni entity) {

	dataIntegration(entity);
	checkPassword(entity, true);
	if (validateEntity(entity)) {
	    amministrazioniDAO.update(entity);
	    entity = this.findById(entity.getId());
	    if (entity.getFlagAmministrazioneinterna() == false) {
		Set<Amministrazioniresponsabili> amministrazioniresponsabiliSet = entity.getAmministrazioniresponsabilis();
		List<Amministrazioniresponsabili> listresp = new ArrayList<Amministrazioniresponsabili>();
		for (Iterator<Amministrazioniresponsabili> iterator = amministrazioniresponsabiliSet.iterator(); iterator.hasNext();) {
		    Amministrazioniresponsabili amministrazioniresponsabili = iterator.next();
		    listresp.add(amministrazioniresponsabili);
		}
		for (int i = 0; i < listresp.size(); i++) {
		    if (!listresp.isEmpty()) {
			Amministrazioniresponsabili amministrazioniresponsabili = listresp.get(i);
			listresp.remove(amministrazioniresponsabili);
			amministrazioniresponsabiliSet.remove(amministrazioniresponsabili);
			amministrazioniresponsabiliService.delete(amministrazioniresponsabili);
			amministrazioniDAO.update(entity);
			i--;
		    }
		}
		Set<Amministrazioniruoli> amministrazioniruoliSet = entity.getAmministrazioniruolis();
		List<Amministrazioniruoli> listruoli = new ArrayList<Amministrazioniruoli>();
		for (Iterator<Amministrazioniruoli> iterator = amministrazioniruoliSet.iterator(); iterator.hasNext();) {
		    Amministrazioniruoli amministrazioniruoli = iterator.next();
		    listruoli.add(amministrazioniruoli);
		}
		for (int i = 0; i < listruoli.size(); i++) {
		    if (!listruoli.isEmpty()) {
			Amministrazioniruoli amministrazioniruoli = listruoli.get(i);
			listruoli.remove(amministrazioniruoli);
			amministrazioniruoliSet.remove(amministrazioniruoli);
			amministrazioniruoliService.delete(amministrazioniruoli);
			amministrazioniDAO.update(entity);
			i--;
		    }
		}
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
	if (!registrazioniInOutService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "REGISTRAZIONI_IN_OUT", null));
	}
	if (!tipimovStcMappingService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_MAPPING.CODICEAMMINISTRAZIONE", null));
	}
	if (!tipimovStcMappingService.findByAmministrazioneMittente(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_MAPPING.PROTOCOLLO_MITTENTE", null));
	}
	if (!tipimovStcAltridatiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_ALTRIDATI", null));
	}
	if (!tipimovStcModelliService.findByAmministrazione(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_MODELLI", null));
	}
	if (!emailService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "EMAIL", null));
	}
	if (!allegatiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALLEGATI", null));
	}
	if (!cdsinvitatiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CDSINVITATI", null));
	}
	if (!documentiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DOCUMENTI", null));
	}
	if (!inventarioprocedimentiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTI", null));
	}
	if (!alboPubblicazioniService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBO_PUBBLICAZIONI", null));
	}
	if (!movimentiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI", null));
	}
	List<Tipicontromovimento> listTipicontromovimentoAmministrazioniTipiContromovimento = tipicontromovimentoService
		.findTipicontromovimentiByAmministrazioniTipiContromovimento(entity);
	List<Tipicontromovimento> listTipicontromovimentoAmministrazioniTipiMovimento = tipicontromovimentoService
		.findTipicontromovimentiByAmministrazioniTipiMovimento(entity);
	if (!listTipicontromovimentoAmministrazioniTipiContromovimento.isEmpty() || !listTipicontromovimentoAmministrazioniTipiMovimento.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPICONTROMOVIMENTO", null));
	}
	if (!commedilizieAppelloService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMEDILIZIE_APPELLO", null));
	}
	if (!amministrazionireferentiService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "AMMINISTRAZIONIREFERENTI", null));
	}
	if (!protocolloRegistriService.findByAmministrazioniMittente(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI_MITTENTE", null));
	}
	if (!protocolloRegistriService.findByAmministrazioniDestinatario(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI_DESTINATARIO", null));
	}
	if (!commedilizieTipologieService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMEDILIZIE_TIPOLOGIE", null));
	}
	if (!tempirispostaService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TEMPIRISPOSTA", null));
	}
	if (!inventarioprocedimentisoftwareService.findByAmministrazioni(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTISOFTWARE", null));
	}
	if (alberoprocService.countByAmministrazioni(entity.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC", null));
	}
	if (istanzeService.countByAmministrazioni(entity.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
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
	filterTable.addOrder(FilterUtils.orderAsc("amministrazione"));
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

	Set<Amministrazioniruoli> amministrazioniruolis = entity.getAmministrazioniruolis();
	for (Amministrazioniruoli amministrazioniruoli : amministrazioniruolis) {
	    amministrazioniruoliService.delete(amministrazioniruoli);
	}
	Set<Amministrazioniresponsabili> amministrazioniresponsabilis = entity.getAmministrazioniresponsabilis();
	for (Amministrazioniresponsabili amministrazioniresponsabili : amministrazioniresponsabilis) {
	    amministrazioniresponsabiliService.delete(amministrazioniresponsabili);
	}
	Set<TipimovStcAlberoproc> tipimovStcAlberoprocs = entity.getTipimovStcAlberoprocs();
	for (TipimovStcAlberoproc tipimovStcAlberoproc : tipimovStcAlberoprocs) {
	    tipimovStcAlberoprocService.delete(tipimovStcAlberoproc);
	}
	List<AmministrazioniAnagrafe> amministrazioniAnagrafes = amministrazioniAnagrafeService.findAmministrazione(entity.getId().getCodice());
	for (AmministrazioniAnagrafe amministrazioniAnagrafe : amministrazioniAnagrafes) {
	    amministrazioniAnagrafeService.delete(amministrazioniAnagrafe);
	}
    }

    @Override
    public Amministrazioni findAmministrazioneSTC(String idNodo, String idEnte, String idSportello, Integer codiceAmministrazioneMittente) {

	List<Amministrazioni> lista = this.findAmministrazioniSTC(idNodo, idEnte, idSportello);
	if (lista.isEmpty()) {
	    return null;
	}
	if (lista.size() == 1 || codiceAmministrazioneMittente == null) {
	    return lista.get(0);
	}
	for (Amministrazioni amministrazione : lista) {
	    if (codiceAmministrazioneMittente.equals(amministrazione.getId().getCodice())) {
		return amministrazione;
	    }
	}
	return lista.get(0);
    }

    private List<Amministrazioni> findAmministrazioniSTC(String idNodo, String idEnte, String idSportello) {

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
	return amministrazioniDAO.findByFilterTable(filterTable);
    }

    @Override
    public int countAmministrazioniSTC(String idNodo, String idEnte, String idSportello) {

	Assert.hasText(idNodo, "Il parametro idNodo non può essere vuoto");
	Assert.hasText(idEnte, "Il parametro idEnte non può essere vuoto");
	Assert.hasText(idSportello, "Il parametro idSportello non può essere vuoto");
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("stcIdnodo", idNodo, String.class));
	restriction.addFilterField(FilterUtils.equals("stcIdente", idEnte, String.class));
	restriction.addFilterField(FilterUtils.equals("stcIdsportello", idSportello, String.class));
	filterTable.addRestriction(restriction);
	return amministrazioniDAO.countRecord(filterTable);
    }

    @Override
    public boolean checkSeDisabilitare(Amministrazioni amministrazioni) {

	List<Inventarioprocedimenti> procs = inventarioprocedimentiService.findByAmministrazioni(amministrazioni.getId().getCodice(), 0, 2);
	if (procs.size() > 0) {
	    return false;
	}
	List<TipimovStcAlberoproc> tapc = tipimovStcAlberoprocService.findByAmministrazioni(amministrazioni.getId().getCodice(), 0, 2);
	if (tapc.size() > 0) {
	    return false;
	}
	List<TipimovStcAltridati> tmovs = tipimovStcAltridatiService.findByAmministrazioni(amministrazioni.getId().getCodice(), 0, 2);
	if (tmovs.size() > 0) {
	    return false;
	}
	List<TipimovStcMapping> tmovmapps = tipimovStcMappingService.findByAmministrazioni(amministrazioni.getId().getCodice(), 0, 2);
	if (tmovmapps.size() > 0) {
	    return false;
	}
	List<TipimovStcMapping> tmovmappmits = tipimovStcMappingService.findByAmministrazioneMittente(amministrazioni.getId().getCodice(), 0, 2);
	if (tmovmappmits.size() > 0) {
	    return false;
	}
	List<TipimovStcModelli> tmovmods = tipimovStcModelliService.findByAmministrazione(amministrazioni.getId().getCodice(), 0, 2);
	if (tmovmods.size() > 0) {
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
	if (commedilizieTipologieService.findByAmministrazioni(amministrazioni.getId().getCodice(), 0, 2).size() > 0) {
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
	    log.debug(
		    "updateAmministrazioniCartAndValidateConfiguration# Inizio aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
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
	    log.debug(
		    "updateAmministrazioniCartAndValidateConfiguration# Fine aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
	}
	return checkValue;
    }

    @Override
    public boolean updateTipimovimentoCartAndValidateConfiguration(String[] codiciTipoMov, String[] codiciAmministrazioniCart) {

	if (log.isDebugEnabled()) {
	    log.debug(
		    "updateAmministrazioniCartAndValidateConfiguration# Inizio aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
	}
	boolean checkValue = true;
	List<String> codiceAmministrazioniNonValutati = new ArrayList<String>();
	for (int i = 0; i < codiciAmministrazioniCart.length; i++) {
	    if (i < codiciTipoMov.length && StringUtils.isNotBlank(codiciTipoMov[i])) {
		// Controllo se esiste un amministrazione con il codiciAmministrazioniCart i-esimo
		Amministrazioni amministrazioniTemp = this.findByCodiceamministrazioneCart(codiciAmministrazioniCart[i].trim());
		if (amministrazioniTemp != null) {
		    Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(codiciTipoMov[i].trim()));
		    amministrazioniTemp.setTipimovimento(tipimovimento);
		    this.update(amministrazioniTemp);
		} else {
		    codiceAmministrazioniNonValutati.add(codiciAmministrazioniCart[i].trim());
		}
	    } else {
		codiceAmministrazioniNonValutati.add(codiciAmministrazioniCart[i].trim());
	    }
	}
	// Controllo se i codici amministrazione cart non valutati sono già stati associati
	for (String codAmmCart : codiceAmministrazioniNonValutati) {
	    String codice = codAmmCart;
	    Amministrazioni amministrazioniTemp = this.findByCodiceamministrazioneCart(codice);
	    if (amministrazioniTemp != null) {
		if (EntityUtils.getNestedProperty(amministrazioniTemp.getTipimovimento(), "id.tipomovimento") == null) {
		    checkValue = false;
		    break;
		}
	    } else {
		checkValue = false;
		break;
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug(
		    "updateAmministrazioniCartAndValidateConfiguration# Fine aggiornamento configurazione delle amministrazioni per le tipologie endo 1.....");
	}
	return checkValue;
    }

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

    @Override
    public List<Amministrazioni> findByCodicecomune(String codiceComune, Integer firstResult, Integer maxResults) {

	List<Amministrazioni> results = new ArrayList<Amministrazioni>();
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction notDisabled = new FilterRestriction();
	    notDisabled.addFilterField(FilterUtils.notEquals("flagDisabilitato", Boolean.TRUE, Boolean.class));
	    ft.addRestriction(notDisabled);
	    FilterRestriction byComune = new FilterRestriction();
	    byComune.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    ft.addRestriction(byComune);
	    results = amministrazioniDAO.findByFilterTable(ft, firstResult, maxResults);
	}
	return results;
    }

    @Override
    public void aggiornaMailEPec(Integer codiceAmministrazione, String email, String pec) {

	if (StringUtils.isBlank(email) && StringUtils.isBlank(pec)) {
	    throw new IllegalArgumentException("Il campo mail o pec non può essere nullo");
	}
	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("Il campo codiceAnagrafe non può essere nullo");
	}
	Amministrazioni amm = amministrazioniDAO.findById(new PkId(codiceAmministrazione));
	if (amm == null) {
	    throw new IllegalArgumentException("Amministrazione con codice " + codiceAmministrazione + " non trovata");
	}
	if (StringUtils.isNotBlank(pec)) {
	    amm.setPec(pec);
	}
	if (StringUtils.isNotBlank(email)) {
	    amm.setEmail(email);
	}
	this.update(amm);
	this.eventPublisher.publish(new EventoEmailAmministrazioneAggiornata(codiceAmministrazione, email, pec));
    }
}
