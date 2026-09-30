/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BandiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ConfigurazionePreferenzeUsoPerMercatoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.BandiAlberoproc;
import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDCritass;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;
import it.gruppoinit.pal.gp.core.domain.TipigraduatorietEsprArt;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeFiere;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietPianoRotazioneDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.ConcessioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.GraduatorieHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.BandiAlberoprocService;
import it.gruppoinit.pal.gp.core.service.BandiService;
import it.gruppoinit.pal.gp.core.service.ConcessionitipiService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietComService;
import it.gruppoinit.pal.gp.core.service.GraduatorietPianorotazioneService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.VwConcessionilistaService;
import it.gruppoinit.pal.gp.core.service.VwEntilocaliService;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

/**
 * @author lucap
 * 
 */
@Service
public class BandiServiceImpl extends BaseServiceImpl<Bandi, PkId> implements BandiService {

    private static final Logger log = LoggerFactory.getLogger(BandiServiceImpl.class);
    //    private static final String MERCATO_USO_DA_ALBERO="mercato_uso_da_albero";
    //    private static final String MERCATO_NON_CONFIG_SU_ALBERO="mercato_non_config_su_albero";
    //    private static final String MERCATO_USO_DA_CONFIG_MERCATI="mercato_uso_da_config_mercato";
    //    private static final String PREFERENZA_USO_NON_CONFIG="preferenza_uso_non_config";
    private BandiDAO bandiDAO;
    private AutorizzazioniService autorizzazioniService;
    private ConcessionitipiService concessionitipiService;
    private MercatiService mercatiService;
    private GraduatoriedService graduatoriedService;
    private VwConcessionilistaService vwConcessionilistaService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private MercatiUsoService mercatiUsoService;
    private MercatiDService mercatiDService;
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    private TipologiaregistriService tipologiaregistriService;
    private GraduatorietService graduatorietService;
    private BandiAlberoprocService bandiAlberoprocService;
    private IstanzeService istanzeService;
    private GraduatorietPianorotazioneService graduatorietPianorotazioneService;
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private VwEntilocaliService vwEntilocaliService;
    private AnagrafeService anagrafeService;
    private IstanzeeventiService istanzeeventiService;
    private GraduatorietComService graduatorietComService;

    @Autowired
    public void setGraduatorietComService(GraduatorietComService graduatorietComService) {

	this.graduatorietComService = graduatorietComService;
    }

    @Autowired
    public void setGraduatorietService(GraduatorietService graduatorietService) {

	this.graduatorietService = graduatorietService;
    }

    @Autowired
    public void setBandiDAO(BandiDAO bandiDAO) {

	this.bandiDAO = bandiDAO;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setConcessionitipiService(ConcessionitipiService concessionitipiService) {

	this.concessionitipiService = concessionitipiService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    private AlberoprocService alberoprocService;

    @Autowired
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
    }

    @Autowired
    public void setVwConcessionilistaService(VwConcessionilistaService vwConcessionilistaService) {

	this.vwConcessionilistaService = vwConcessionilistaService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatiConfigurazioneService(MercatiConfigurazioneService mercatiConfigurazioneService) {

	this.mercatiConfigurazioneService = mercatiConfigurazioneService;
    }

    @Autowired
    public void setTipologiaregistriService(TipologiaregistriService tipologiaregistriService) {

	this.tipologiaregistriService = tipologiaregistriService;
    }

    @Autowired
    public void setBandiAlberoprocService(BandiAlberoprocService bandiAlberoprocService) {

	this.bandiAlberoprocService = bandiAlberoprocService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setGraduatorietPianorotazioneService(GraduatorietPianorotazioneService graduatorietPianorotazioneService) {

	this.graduatorietPianorotazioneService = graduatorietPianorotazioneService;
    }

    @Autowired
    public void setAutorizzazioniConcessioniService(AutorizzazioniConcessioniService autorizzazioniConcessioniService) {

	this.autorizzazioniConcessioniService = autorizzazioniConcessioniService;
    }

    @Autowired
    public void setVwEntilocaliService(VwEntilocaliService vwEntilocaliService) {

	this.vwEntilocaliService = vwEntilocaliService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Override
    protected Class<Bandi> getEntityClass() {

	return Bandi.class;
    }

    @Override
    public void delete(Bandi entity) {

	// §§§BEGIN§§§
	childDelete(entity);
	bandiDAO.delete(entity);
	// §§§END§§§
    }

    protected void childDelete(Bandi entity) {

	List<BandiAlberoproc> bandiAlberoprocs = bandiAlberoprocService.findByBandi(entity.getId().getCodice());
	for (BandiAlberoproc bandiAlberoproc : bandiAlberoprocs) {
	    bandiAlberoprocService.delete(bandiAlberoproc);
	}
    }

    @Override
    public List<Bandi> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return bandiDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Bandi findById(PkId id) {

	// §§§BEGIN§§§
	return bandiDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Bandi entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validate(entity)) {
	    if (validateBandiInput(entity)) {
		if (validateEntity(entity)) {
		    bandiDAO.insert(entity);
		}
	    }
	}
	// §§§END§§§
    }

    private void dataIntegration(Bandi entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare tipo bando nulla");
	}
	//	int max = this.findMaxOrdine(entity.getBandi().getId().getCodice());
	//	entity.setOrdine(max + 1);
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Bandi entity) {

	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
    }

    @Override
    public void update(Bandi entity) {

	// §§§BEGIN§§§
	if (validate(entity)) {
	    if (validateBandiInput(entity)) {
		if (validateEntity(entity)) {
		    bandiDAO.update(entity);
		}
	    }
	}
	// §§§END§§§
    }

    private boolean validateBandiInput(Bandi entity) {

	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<Bandiinput> bandiinputs = entity.getBandiinputs();
	int i = 0;
	for (Bandiinput bandiinput : bandiinputs) {
	    if (bandiinput.getValore() == null) {
		_ivs.add(new InvalidValue("validator.nonvuoto", entity.getClass(), "bandiinputs[" + i + "].valore", "", entity));
	    }
	    i++;
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return true;
    }

    /**
     * verifica se il bando ha un alberoproc configurato per i mercati. in questo caso recupera tutti i bandi che hanno
     * quell'alberoproc. se ne esiste uno torna errore (questo per le modifiche fatte ai mercati)
     * 
     * @param entity
     * @return
     */
    private boolean validate(Bandi entity) {

	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (EntityUtils.getNestedProperty(entity.getAlberoproc(), "id.codice") != null) {
	    Alberoproc alberoproc = entity.getAlberoproc();
	    alberoproc = alberoprocService.findById(new PkId(alberoproc.getId().getCodice()));
	    if (alberoproc.getMercato() != null && alberoproc.getMercato().getId().getCodice() != null && alberoproc.getMercatoUso() != null
		    && alberoproc.getMercatoUso().getId().getCodice() != null) {
		List<Bandi> bandiList = this.findByAlberoproc(alberoproc);
		if (!bandiList.isEmpty()) {
		    for (Bandi bandi : bandiList) {
			if (entity.getId() != null && entity.getId().getCodice() != null
				&& (bandi.getId().getCodice().intValue() == entity.getId().getCodice().intValue())) {
			    continue;
			} else {
			    _ivs.add(new InvalidValue("service_error.bando_esistente_per_alberoproc", entity.getClass(), "alberoproc", "", entity));
			}
		    }
		}
	    }
	}
	if (BooleanUtils.isFalse(entity.getTipibando().getFlagMultiintervento())
		&& EntityUtils.getNestedProperty(entity.getAlberoproc(), "id.codice") == null) {
	    _ivs.add(new InvalidValue("validator.notEmpty", entity.getClass(), "alberoproc", "", entity));
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return true;
    }

    @Override
    public List<Bandi> findByAlberoproc(Alberoproc alberoproc) {

	// §§§BEGIN§§§
	return bandiDAO.findByAlberoproc(alberoproc);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<AnagrafeFiere> findIstanzeAnagrafeGraduatoria(Alberoproc alberoproc, Anagrafe anagrafe) {

	// §§§BEGIN§§§
	return bandiDAO.findIstanzeAnagrafeGraduatoria(alberoproc, anagrafe);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Bandi> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAlberoproc == null) {
	    throw new IllegalArgumentException("findByAlberoproc: il parametro codiceAlberoproc e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAlberoproc, "alberoproc", Integer.class));
	filterTable.addRestriction(fr);
	return bandiDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertConcessioniAlleIstanzeInGratuatoria(Graduatoriet graduatoriet) {

	if (log.isDebugEnabled()) {
	    log.debug("updateConcessioniAlleIstanzeInGratuatoria# Controllo preliminare per assegnazione concessioni in modo automatico");
	}
	// Per far si che si possano associare le concessioni automaticamente  all' istanze presenti sulla graduatori passata, deve essere configurato
	// per l'albero associato :
	// 1- Un mercato
	// 2- Un uso (MercatiUso) del mercato
	// Nel caso non sia specifica l'uso sull' albero dei procedimenti, ma solo il mercato questo dovrà esse configurato come campo dinamico 
	//sulla tabella  MERCATI a cui è asscoato il bando
	if (log.isDebugEnabled()) {
	    log.debug("updateConcessioniAlleIstanzeInGratuatoria# Controllo se l'albero dei procedimenti ha un mercato ed un uso configurato");
	}
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	ConfigurazionePreferenzeUsoPerMercatoEnum conf = null;
	if (BooleanUtils.toBoolean(graduatoriet.getTipigraduatoriet().getTipibando().getFlagMultiintervento())) {
	    conf = getTipologiaConfigurazionePreferenzaUsoMercatoMultiintervento(graduatoriet.getBandi());
	} else {
	    conf = getTipologiaConfigurazionePreferenzaUsoMercato(graduatoriet.getBandi().getAlberoproc());
	}
	Set<GraduatoriedDTO> graduatoria = new HashSet<GraduatoriedDTO>();
	// Nel caso sia multi intervento, non sarà congigurato l'albero proc su bandi, ma ci sarà una tabella
	// contenete gli N interventi configurati
	MercatiUso mercatiUso = null;
	Mercati mercati = null;
	if (BooleanUtils.toBoolean(graduatoriet.getTipigraduatoriet().getTipibando().getFlagMultiintervento())) {
	    List<Integer> lcm = bandiAlberoprocService.findDistinctMercatiByBando(graduatoriet.getBandi().getId().getCodice());
	    if (!lcm.isEmpty() && lcm.get(0) != null) {
		mercati = mercatiService.findById(new PkId(lcm.get(0)));
	    }
	    List<Integer> lcmU = bandiAlberoprocService.findDistinctMercatiUsoByBando(graduatoriet.getBandi().getId().getCodice());
	    if (lcmU.get(0) != null) {
		mercatiUso = mercatiUsoService.findById(new PkId(lcmU.get(0)));
	    }
	} else {
	    mercatiUso = graduatoriet.getBandi().getAlberoproc().getMercatoUso();
	    mercati = graduatoriet.getBandi().getAlberoproc().getMercato();
	}
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	if (EntityUtils.getNestedProperty(mercatiConfigurazione, "registroConcessioni.id.codice") != null) {
	    Tipologiaregistri registroConcessione = tipologiaregistriService.findById(mercatiConfigurazione.getRegistroConcessioni().getId());
	    // Controllo se il registro delle concessioni impostato abbia settato il progressivo, se non non posso 
	    // associare concessioni
	    if (registroConcessione.getTrFlagprotocollo() == null) {
		registroConcessione.setTrFlagprotocollo(false);
	    }
	    if (StringUtils.isBlank(registroConcessione.getTrProgressivo()) && registroConcessione.getTrFlagprotocollo().equals(false)) {
		_ivs.add(new InvalidValue("service_error.nessuna_num_automatica_sul_registro", null, null, null, null));
		if (!_ivs.isEmpty()) {
		    this.throwValidationMessages(_ivs);
		}
	    }
	}
	switch (conf) {
	case MERCATO_USO_DA_ALBERO:
	    if (log.isDebugEnabled()) {
		log.debug("updateConcessioniAlleIstanzeInGratuatoria# Configurazione preferenza uso presa da albero");
		log.debug("updateConcessioniAlleIstanzeInGratuatoria# Recupero l'uso del mercato settato sull'albero");
	    }
	    // Recupero l'uso settato sulla l'albero dei procedimenti 
	    // mercatiUso = graduatoriet.getBandi().getAlberoproc().getMercatoUso();
	    if (log.isDebugEnabled()) {
		log.debug("updateConcessioniAlleIstanzeInGratuatoria# L'uso recuperato è : {} ({})",
			new Object[] { mercatiUso.getDescrizione(), mercatiUso.getId().getCodice() });
	    }
	    graduatoria = getGraduatoriedDTO(graduatoriet);
	    insertRilascioConcessioniAlleIstanza(graduatoria, mercati, ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_DA_ALBERO,
		    mercatiUso.getId().getCodice(), graduatoriet);
	    break;
	case MERCATO_USO_CONFIG_DA_MERCATI:
	    if (log.isDebugEnabled()) {
		log.debug("updateConcessioniAlleIstanzeInGratuatoria# Configurazione preferenza uso presa campo dinamico del mercato");
		log.debug("updateConcessioniAlleIstanzeInGratuatoria# Recupero dal mercato la preferenza configurata....");
	    }
	    // Il campo dyn2 due dati presente sul mercato deve indicare che il mercato gestisce la preferenza tramite il mercato e 
	    // tramite la voce dell'albero che viene associato alle istanze.
	    Dyn2Campi campiPreferenza = mercati.getDyn2Campi();
	    if (log.isDebugEnabled()) {
		log.debug("updateConcessioniAlleIstanzeInGratuatoria# La preferenza uso è gestita tramite il campo dinamico : {} ({})",
			new Object[] { campiPreferenza.getNomecampo(), campiPreferenza.getId().getCodice() });
	    }
	    graduatoria = getGraduatoriedDTO(graduatoriet);
	    insertRilascioConcessioniAlleIstanza(graduatoria, mercati, ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_CONFIG_DA_MERCATI,
		    campiPreferenza.getId().getCodice(), graduatoriet);
	    break;
	case PREFERENZA_USO_NON_CONFIG:
	    _ivs.add(new InvalidValue("service_error.nessuna_preferenza_di_uso_configurata", null, null, null, null));
	    if (!_ivs.isEmpty()) {
		this.throwValidationMessages(_ivs);
	    }
	case MERCATO_NON_CONFIG_SU_ALBERO:
	    _ivs.add(new InvalidValue("service_error.nessun_mercato_configurato_albero", null, null, null, null));
	    if (!_ivs.isEmpty()) {
		this.throwValidationMessages(_ivs);
	    }
	case MERCATI_CONFIG_SU_ALBERO:
	    _ivs.add(new InvalidValue("service_error.piu_mercati_configurati_per_bando", null, null, null, null));
	    if (!_ivs.isEmpty()) {
		this.throwValidationMessages(_ivs);
	    }
	case MERCATI_USO_CONFIG_SU_ALBERO:
	    _ivs.add(new InvalidValue("service_error.piu_mercati_uso_configurati_per_bando", null, null, null, null));
	    if (!_ivs.isEmpty()) {
		this.throwValidationMessages(_ivs);
	    }
	default:
	    throw new RuntimeException("Errore impossibile trovare una configurazione valida: " + conf.value());
	}
    }

    /**
     * Recupera tutte le righe della graduatoria(Sono tutte le istanze che effettuate per il rilascio di una concessione
     * per il mercato)
     * 
     * @param graduatoriet
     * @return
     */
    private Set<GraduatoriedDTO> getGraduatoriedDTO(Graduatoriet graduatoriet) {

	if (log.isDebugEnabled()) {
	    log.debug("getGraduatoriedDTO# Recupero la graduatoria del bando.....");
	}
	Set<GraduatoriedDTO> graduatoriedDTO2s = new LinkedHashSet<GraduatoriedDTO>();
	List<GraduatoriedDTO> graduatoriedDTOs = graduatoriedService.findByGraduatoriet(graduatoriet);
	GraduatoriedDTO temp = null;
	for (GraduatoriedDTO graduatoried : graduatoriedDTOs) {
	    // IstanzeDTO istanza = graduatoried.getIstanza();
	    // per ogni campo di output recupero il valore (dyn2dati) della scheda dinamica dell'istanza e lo inserisco
	    // in una lista di oggetti bandiOutput
	    if (temp == null) {
		temp = graduatoried;
		graduatoriedDTO2s.add(graduatoried);
	    } else {
		if (temp.getId().getCodice().equals(graduatoried.getId().getCodice())) {
		    if (graduatoried.getConcessione() != null && graduatoried.getConcessione().getCodiceposteggio() != null) {
			graduatoriedDTO2s.remove(temp);
			temp = graduatoried;
			graduatoriedDTO2s.add(graduatoried);
		    }
		} else {
		    temp = graduatoried;
		    graduatoriedDTO2s.add(graduatoried);
		}
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("getGraduatoriedDTO# Recuperata  graduatoria del bando.....");
	}
	return graduatoriedDTO2s;
    }

    private void insertRilascioConcessioniAlleIstanza(Set<GraduatoriedDTO> graduatoriets, Mercati mercati,
	    ConfigurazionePreferenzeUsoPerMercatoEnum config, Integer codicePreferenzaGiornata, Graduatoriet graduatoriet) {

	boolean conessioniRilasciate = false;
	if (!conessioniRilasciate && BooleanUtils.toBoolean(graduatoriet.getTipigraduatoriet().getFlagEsprArtTemp())) {
	    log.debug("insertRilascioConcessioniAlleIstanza# Rilascio concessioni massive temporanee");
	    insertRilascioConcessioniAlleIstanzaTemporanee(graduatoriets, mercati, config, codicePreferenzaGiornata, graduatoriet);
	    conessioniRilasciate = true;
	}
	if (!conessioniRilasciate) {
	    log.debug("insertRilascioConcessioniAlleIstanza# Rilascio concessioni massive standard");
	    insertRilascioConcessioniAlleIstanzaStandard(graduatoriets, mercati, config, codicePreferenzaGiornata);
	}
    }

    /**
     * <pre>
     * Il metodo rilascia una concessione se l'istanza rispetta i parametri richiesti ogni autorizzazione che viene rilasciata avrà
     * valore biennale. 
     *  
     * Es. autorizzazione richiesta per 02/15 al 05/15, verranno rilasciate due autorizzazioni 
     *     1- 02/15 al 05/15
     *     2- 02/16 al 05/16
     * 
     * Regole per rilasciare le autorizzazioni:
     *   
     *     1. Si recuperano le preferenze dalla scheda dinamica associata all'istanza andando a confrontare i campi dinamici presenti 
     *     sulla configurazione della tipo graduatoria (Tabella TIPIGRADUATORIE_ESPR_ART)
     *     2. Se per il periodo richiesto il posteggio è libero rilascio le autorizzazioni.
     *     3. Un singolo richiedente può avere un autorizzazione per singolo mese e non può superare i 7 giorni consecutivi.
     *     
     *  Casi particolari:
     *  1. Posteggio occupato parzialmente:
     *     1.a	: sono lineri N giorni precedenti rilascio autorizzazione solo per gli N giorni precedenti
     *     	  Es. post1  06/02 a 9/02 occupato , richista succ 04/02 a 07/02 rilascio con solo per 04/02 a 05/02
     *     1.b	: sono lineri N giorni successivi rilascio autorizzazione solo per gli N giorni successivi
     *            Es. post1  06/02 a 7/02 occupato , richista succ 07/02 a 09/02 rilascio con solo per 08/02 a 09/02
     *  2. Richieste più lunghe di 7 giorni:
     *     2.a	: 01/02 09/02 richiesta scartata       	          
     *     2.b	: 27/02 al 6/03 posso rilasciare sono 2 giorni di febb e 6 marzo   	
     * 
     * 
     * &#64;param graduatoriets
     * &#64;param mercati
     * &#64;param config
     * &#64;param codicePreferenzaGiornata
     * </pre>
     */
    private void insertRilascioConcessioniAlleIstanzaTemporanee(Set<GraduatoriedDTO> graduatoriets, Mercati mercati,
	    ConfigurazionePreferenzeUsoPerMercatoEnum config, Integer codicePreferenzaGiornata, Graduatoriet graduatoriet) {

	// recupero la configurazione per filtrare il posteggio da assegnare e in quale periodo
	Set<TipigraduatorietEsprArt> tipigraduatorietEsprArts = graduatoriet.getTipigraduatoriet().getTipigraduatorietEsprArts();
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (tipigraduatorietEsprArts.isEmpty()) {
	    _ivs.add(new InvalidValue("service_error.configurazione_tipi_grad_artistiche_non_presente", null, null, null, null));
	}
	if (EntityUtils.getNestedProperty(graduatoriet.getTipologiaregistri(), "id.codice") == null) {
	    _ivs.add(new InvalidValue("service_error.configurazione_tipologia_registri", null, null, null, null));
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
	// Recupero tutte le informazioni fisse
	List<TipigraduatorietEsprArt> list = new ArrayList<TipigraduatorietEsprArt>(tipigraduatorietEsprArts);
	TipigraduatorietEsprArt tipigraduatorietEsprArt = list.get(0);
	// Ciclo la graduatoria e prendo la prima istanza
	MercatiUso mercatiUso = mercatiUsoService.findByMercato(mercati).get(0);
	Tipologiaregistri tipologiaregistri = graduatoriet.getTipologiaregistri();
	// Ciclo tutti i record della graduatoria creata
	for (GraduatoriedDTO graduatoriedDTO : graduatoriets) {
	    Istanze istanze = istanzeService.findById(new PkId(graduatoriedDTO.getIstanza().getId().getCodice()));
	    // Cerco nelle schede dinamiche dell'istanza in graduatoria se è presente il campo dinamico che rappresneta il posteggio
	    // Il campo che rappresenta il posteggio è configurato nella tabella "TipigraduatorietEsprArt"
	    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(
		    graduatoriedDTO.getIstanza().getId().getCodice(), tipigraduatorietEsprArt.getCampiPosteggio().getId().getCodice());
	    // Posso configurare sulla scheda N preferenze (N posteggi), ciclo tutti i posteggi trovati per l'istanza
	    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
		// Recupero il posteggio salvato sulle schede dinamiche
		MercatiD mercatiD = mercatiDService.findById(new PkId(Integer.parseInt(istanzedyn2dati.getValore())));
		// Dai campi dinamici dell'istanza recupero il periodo per cui si vuole la concessione del posteggio
		// I campi che rappresentano le date "Da" e "A" vengono configurati nella tabella "TipigraduatorietEsprArt"
		log.debug(
			"insertRilascioConcessioniAlleIstanzaTemporanee# Recupero data di partenza richiesta per il posteggio {} e richiedete {} [{}]",
			new Object[] { mercatiD.getCodiceposteggio(), graduatoriedDTO.getIstanza().getRichiedente(),
				graduatoriedDTO.getIstanza().getId().getCodice() });
		Istanzedyn2datiId id = new Istanzedyn2datiId();
		id.setCodiceistanza(graduatoriedDTO.getIstanza().getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		id.setIndice(0);
		id.setIndiceMolteplicita(istanzedyn2dati.getId().getIndiceMolteplicita());
		id.setFkD2cId(tipigraduatorietEsprArt.getCampiDa().getId().getCodice());
		Istanzedyn2dati istanzedyn2datiDa = istanzedyn2datiService.findById(id);
		log.debug("insertRilascioConcessioniAlleIstanzaTemporanee# Data Da", istanzedyn2datiDa.getValore());
		// recupero data a
		id.setFkD2cId(tipigraduatorietEsprArt.getCampiA().getId().getCodice());
		Istanzedyn2dati istanzedyn2datiA = istanzedyn2datiService.findById(id);
		log.debug("insertRilascioConcessioniAlleIstanzaTemporanee# Data A", istanzedyn2datiA.getValore());
		/////////////// Eseguo un controllo se sono rispettate le regole per provare a rilasciare ////////////
		////////////////////////////////       una concessione ///////////////////////////////////////////////
		List<AutorizzazioniFilter> autorizzazioniFilters = new ArrayList<AutorizzazioniFilter>();
		log.debug("insertRilascioConcessioniAlleIstanzaTemporanee# Verifico che siano state inserite la data di inzio e fine ");
		if (StringUtils.isNotBlank(istanzedyn2datiDa.getValoredecodificato())
			&& StringUtils.isNotBlank(istanzedyn2datiA.getValoredecodificato())) {
		    // Il medoto crea in base all 'informazioni recuperate un filtro per verificare se sono presenti già nel DB 
		    // autorizzazione con le stesse carateristiche. Per poter creare dei filtri per la verifica devono essere rispettate
		    // delle regole, altrimenti verranno subito scartate.
		    autorizzazioniFilters = createFilterAutorizzazioni(istanzedyn2datiDa.getValoredecodificato(),
			    istanzedyn2datiA.getValoredecodificato(), mercati, mercatiUso, mercatiD, istanze);
		    ///////////////////////////////////////////////////////////////////////////////////////////
		    for (AutorizzazioniFilter autorizzazioniFilter : autorizzazioniFilters) {
			autorizzazioniFilter.setTipologiaregistro(graduatoriet.getTipologiaregistri());
			autorizzazioniFilter.setOrderBy("ASC");
			// Ricerco se ci sono autorizzazioni con queste caratteristiche
			List<Autorizzazioni> autorizzazionis = autorizzazioniService.findByAutorizzazioniFilter(autorizzazioniFilter, null, null);
			Map<String, Boolean> mapGiorni = new HashMap<String, Boolean>();
			// Ciclo tutte le autorizzazioni trovate e creo una mappa contenete i giorni occupati da quelle
			// autorizzazioni
			for (Autorizzazioni autorizzazioni : autorizzazionis) {
			    int diffDate = Utilities.calculateDifferenceInDays(autorizzazioni.getAutorizdata(), autorizzazioni.getDatascadenza());
			    for (int i = 0; i <= diffDate; i++) {
				Date date = Utilities.addDays(autorizzazioni.getAutorizdata(), i);
				mapGiorni.put(Utilities.formatDate(date, false), true);
			    }
			}
			String dateDA = Utilities.formatDate(autorizzazioniFilter.getDallaData(), false);
			String dateA = Utilities.formatDate(autorizzazioniFilter.getAllaDataScadenza(), false);
			Date dateStart = null;
			Date dateEnd = null;
			// Controllo se la mappa è stata popolata
			// 1. No :allora la datainizio e datafine sono quelli impostate sui dati dinamici dell'istanza
			// 2. Si :verifico se trovo un buco libero ed eventualmente crea una concessione per un unico 
			//        più piccolo di quello richiesto. Es AUT_1 [Esistente dal 01/02 al 04/02], 
			//                                            AUT_2 [Esistente dal 06/02 al 06/02]
			//                                            AUT_3 [Da creare dal 01/02 al 07/02]
			// Creerà una AUT_3 per che va da 05/02 a 05/02 primo buco trovato. Non creerà quella
			// dal 07/02 al 07/02 perchè per un mese si può chiedere una sola concessione.
			if (!mapGiorni.isEmpty()) {
			    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
			    // Verifico se trovo un buco per inserire una la concessione
			    // Calcolo diff date
			    int _diffDate = Utilities.calculateDifferenceInDays(Utilities.parseDateString(dateDA, false),
				    Utilities.parseDateString(dateA, false));
			    boolean isDateStart = false;
			    for (int i = 0; i < _diffDate; i++) {
				Date date = Utilities.addDays(Utilities.parseDateString(dateDA, false), i);
				// La converto in stringa e verifico se esiste nella mappa
				if (!mapGiorni.containsKey(Utilities.formatDate(date, false))) {
				    if (!isDateStart) {
					dateStart = date;
					isDateStart = true;
				    }
				} else {
				    if (isDateStart) {
					dateEnd = Utilities.removeDays(date, 1);
					break;
				    }
				}
			    }
			} else {
			    dateStart = autorizzazioniFilter.getDallaData();
			    dateEnd = autorizzazioniFilter.getAllaDataScadenza();
			}
			if (dateStart != null && dateEnd != null) {
			    // Devo verificare se già esiste un'autorizzazione per la stessa istanza in quel mese
			    Calendar _dateStart = Utilities.getDate(Utilities.formatDate(dateStart, false), "");
			    int daysInMonth = _dateStart.getActualMaximum(Calendar.DAY_OF_MONTH);
			    Calendar inizioMese = new GregorianCalendar();
			    inizioMese.set(_dateStart.get(Calendar.YEAR), _dateStart.get(Calendar.MONTH), 1);
			    // .set(_dateStart.get(Calendar.YEAR), _dateStart.get(Calendar.MONTH), 1);
			    Calendar fineMese = new GregorianCalendar();
			    fineMese.set(_dateStart.get(Calendar.YEAR), _dateStart.get(Calendar.MONTH), daysInMonth);
			    AutorizzazioniFilter filter = new AutorizzazioniFilter();
			    filter.setMercati(mercati);
			    filter.setMercatiUso(mercatiUso);
			    //filter.setMercatiD(mercatiD);
			    Date dallaData = inizioMese.getTime();
			    filter.setDallaData(dallaData);
			    Date dallaDataScadenza = fineMese.getTime();
			    filter.setAllaDataScadenza(dallaDataScadenza);
			    IstanzeFilter istanzeFilter = new IstanzeFilter();
			    istanzeFilter.setCodiceIstanza(istanze.getId().getCodice());
			    filter.setOrderBy("ASC");
			    filter.setIstanzeFilter(istanzeFilter);
			    List<Autorizzazioni> auts = autorizzazioniService.findByAutorizzazioniFilter(filter, null, null);
			    if (!auts.isEmpty()) {
				Istanzeeventi istanzeeventi = new Istanzeeventi();
				istanzeeventi.setIstanze(istanze);
				istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_ASSEGNAZIONE_POSTEGGIO_DA_GRADUATORIA);
				istanzeeventi.setData(Calendar.getInstance().getTime());
				StringBuffer buffer = new StringBuffer("Non è stato possibile assegnare il posteggio ")
					.append(mercatiD.getCodiceposteggio()).append(" per il giorno ").append(mercatiUso.getDescrizione())
					.append(". Erano già presenti autorizzazione per questo mese assegnate al richedente ");
				istanzeeventi.setDescrizione(buffer.toString());
				istanzeeventi.setSoftware(istanze.getSoftware());
				istanzeeventiService.insert(istanzeeventi);
			    } else {
				Autorizzazioni autorizzazioni = new Autorizzazioni();
				autorizzazioni = createAutorizzazioni(tipologiaregistri, graduatoriedDTO, dateStart, dateEnd);
				autorizzazioniService.insertAutorizzazione(autorizzazioni);
				AutorizzazioniConcessioni autorizzazioniConcessioni = new AutorizzazioniConcessioni();
				autorizzazioniConcessioni = createAutorizzazioniConcessioni(autorizzazioni, mercati, mercatiUso, mercatiD, dateEnd);
				autorizzazioniConcessioniService.insert(autorizzazioniConcessioni);
				// Aggiungo l'autorizzazione e concessione per l'anno successivo
				Autorizzazioni autorizzazioniNextYear = new Autorizzazioni();
				autorizzazioniNextYear = createAutorizzazioni(tipologiaregistri, graduatoriedDTO, Utilities.addYear(dateStart, 1),
					Utilities.addYear(dateEnd, 1));
				autorizzazioniService.insertAutorizzazione(autorizzazioniNextYear);
				AutorizzazioniConcessioni autorizzazioniConcessioniNextYear = new AutorizzazioniConcessioni();
				autorizzazioniConcessioniNextYear = createAutorizzazioniConcessioni(autorizzazioniNextYear, mercati, mercatiUso,
					mercatiD, Utilities.addYear(dateEnd, 1));
				autorizzazioniConcessioniService.insert(autorizzazioniConcessioniNextYear);
				// NON AGGIORNA LA NUMERAZIONE AUTOMATICA DEL REGISTRO
				//			    bandiDAO.flush();
				//			    bandiDAO.clear();
			    }
			}
		    }
		} else {
		    Istanzeeventi istanzeeventi = new Istanzeeventi();
		    istanzeeventi.setIstanze(istanze);
		    istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_ASSEGNAZIONE_POSTEGGIO_DA_GRADUATORIA);
		    istanzeeventi.setData(Calendar.getInstance().getTime());
		    StringBuffer buffer = new StringBuffer("Non è stato possibile assegnare il posteggio ").append(mercatiD.getCodiceposteggio())
			    .append(" per il giorno ").append(mercatiUso.getDescrizione()).append(". Data inizio e/o data fine non presente ");
		    istanzeeventi.setDescrizione(buffer.toString());
		    istanzeeventi.setSoftware(istanze.getSoftware());
		    istanzeeventiService.insert(istanzeeventi);
		}
	    }
	}
    }

    /**
     * Il metodo crea una lista di oggetti Autorizzazione filter per verificare se esistono già salvati delle
     * autorizzazione con le stesse caratteristiche.
     * 
     * @param _istanzedyn2datiDa
     * @param _istanzedyn2datiA
     * @param mercati
     * @param mercatiUso
     * @param mercatiD
     * @return
     */
    private List<AutorizzazioniFilter> createFilterAutorizzazioni(String _istanzedyn2datiDa, String _istanzedyn2datiA, Mercati mercati,
	    MercatiUso mercatiUso, MercatiD mercatiD, Istanze istanze) {

	List<AutorizzazioniFilter> autorizzazioniFilters = new ArrayList<AutorizzazioniFilter>();
	// Calcolo il numero dei giorni per cui è stata richiesta la concessione, se è minore di 7 allora creo il filtro
	// altrimenti, prima di scartare devo verificare se i giorni per cui è stata richiesta sono a cavallo di due mesi differenti:
	// 1. caso siano nello stesso mese, scarto la richeista
	// 2. caso mesi differenti :
	//  2.1 Controllo se data inizio e data fine mese < 7 giorni. Si creo filtro, altrimenti scarto
	//  2.2 Controllo se data inizio mese e data fine concessione < 7 giorni. Si creo filtro, altrimenti scarto
	int diffGiorniRichiesti = Utilities.calculateDifferenceInDays(Utilities.parseDateString(_istanzedyn2datiDa, false),
		Utilities.parseDateString(_istanzedyn2datiA, false));
	// Se è maggio di 7 devo verificare che siano due messi differenti , altrimenti scarto
	//boolean richiestaConcessioneOk = true;
	if (diffGiorniRichiesti > 7) {
	    Calendar c = new GregorianCalendar();
	    c.setTime(Utilities.parseDateString(_istanzedyn2datiDa, false));
	    int mese = c.get(Calendar.MONTH);
	    Calendar c1 = new GregorianCalendar();
	    c1.setTime(Utilities.parseDateString(_istanzedyn2datiA, false));
	    int mese1 = c1.get(Calendar.MONTH);
	    if (mese != mese1) {
		int giorno = c.get(Calendar.DAY_OF_MONTH);
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (daysInMonth - giorno < 7) {
		    AutorizzazioniFilter filter = new AutorizzazioniFilter();
		    filter.setMercati(mercati);
		    filter.setMercatiUso(mercatiUso);
		    filter.setMercatiD(mercatiD);
		    Date dallaData = Utilities.parseDateString(_istanzedyn2datiDa, false);
		    filter.setDallaData(dallaData);
		    Calendar _dallaDataScadenza = new GregorianCalendar();
		    _dallaDataScadenza.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), daysInMonth);
		    Date dallaDataScadenza = _dallaDataScadenza.getTime();
		    filter.setAllaDataScadenza(dallaDataScadenza);
		    autorizzazioniFilters.add(filter);
		} else {
		    StringBuffer message = new StringBuffer("Non è stato possibile assegnare il posteggio ").append(mercatiD.getCodiceposteggio())
			    .append(" per il giorno ").append(mercatiUso.getDescrizione()).append(" richiesta non ammessa, durata troppo lunga")
			    .append("(").append(_istanzedyn2datiDa).append("al ").append(_istanzedyn2datiA);
		    createAndInsertEvento(istanze, message);
		}
		int giorno1 = c1.get(Calendar.DAY_OF_MONTH);
		if (giorno1 <= 7) {
		    AutorizzazioniFilter filter1 = new AutorizzazioniFilter();
		    filter1.setMercati(mercati);
		    filter1.setMercatiUso(mercatiUso);
		    filter1.setMercatiD(mercatiD);
		    Calendar _dallaDataScadenza1 = new GregorianCalendar();
		    _dallaDataScadenza1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 1);
		    filter1.setDallaData(_dallaDataScadenza1.getTime());
		    Date AllaData = Utilities.parseDateString(_istanzedyn2datiA, false);
		    filter1.setAllaDataScadenza(AllaData);
		    autorizzazioniFilters.add(filter1);
		} else {
		    StringBuffer message = new StringBuffer("Non è stato possibile assegnare il posteggio ").append(mercatiD.getCodiceposteggio())
			    .append(" per il giorno ").append(mercatiUso.getDescrizione()).append(" richiesta non ammessa, durata troppo lunga")
			    .append("(").append(_istanzedyn2datiDa).append("al ").append(_istanzedyn2datiA).append("");
		    createAndInsertEvento(istanze, message);
		}
	    } else {
		StringBuffer message = new StringBuffer("Non è stato possibile assegnare il posteggio ").append(mercatiD.getCodiceposteggio())
			.append(" per il giorno ").append(mercatiUso.getDescrizione()).append(" richiesta non ammessa, durata troppo lunga")
			.append("(").append(_istanzedyn2datiDa).append("al ").append(_istanzedyn2datiA).append("");
		createAndInsertEvento(istanze, message);
	    }
	} else {
	    AutorizzazioniFilter filter = new AutorizzazioniFilter();
	    filter.setMercati(mercati);
	    filter.setMercatiUso(mercatiUso);
	    filter.setMercatiD(mercatiD);
	    Date dallaData = Utilities.parseDateString(_istanzedyn2datiDa, false);
	    filter.setDallaData(dallaData);
	    Date dallaDataScadenza = Utilities.parseDateString(_istanzedyn2datiA, false);
	    filter.setAllaDataScadenza(dallaDataScadenza);
	    autorizzazioniFilters.add(filter);
	}
	return autorizzazioniFilters;
    }

    private void createAndInsertEvento(Istanze istanze, StringBuffer message) {

	Istanzeeventi istanzeeventi = new Istanzeeventi();
	istanzeeventi.setIstanze(istanze);
	istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_ASSEGNAZIONE_POSTEGGIO_DA_GRADUATORIA);
	istanzeeventi.setData(Calendar.getInstance().getTime());
	istanzeeventi.setDescrizione(message.toString());
	istanzeeventi.setSoftware(istanze.getSoftware());
	istanzeeventiService.insert(istanzeeventi);
    }

    private Autorizzazioni createAutorizzazioni(Tipologiaregistri tipologiaregistro, GraduatoriedDTO graduatoriedDTO, Date dateStart, Date dateEnd) {

	Autorizzazioni autorizzazioni = new Autorizzazioni();
	// Se non è settato che succede, controllare se non viene passato la tipologia l momento della creazione che succede?
	if (StringUtils.isNotBlank(tipologiaregistro.getTrProgressivo())) {
	    autorizzazioni.setAutoriznumero(tipologiaregistro.getTrProgressivo());
	}
	Istanze istanze = istanzeService.findById(new PkId(graduatoriedDTO.getIstanza().getId().getCodice()));
	VwEntilocali autorizcomune = vwEntilocaliService.findById(istanze.getComune().getCodicecomune());
	autorizzazioni.setAutorizcomune(autorizcomune);
	autorizzazioni.setAutorizdataregistr(new Date());
	autorizzazioni.setAutorizdata(dateStart);
	autorizzazioni.setDataRilascio(dateStart);
	autorizzazioni.setDatascadenza(dateEnd);
	autorizzazioni.setIstanza(istanze);
	autorizzazioni.setAnagrafe(istanze.getTitolareLegaleORichiedente());
	autorizzazioni.setOccupante(istanze.getTitolareLegaleORichiedente());
	autorizzazioni.setFlagAttiva(true);
	autorizzazioni.setTipologiaregistro(tipologiaregistro);
	return autorizzazioni;
    }

    private AutorizzazioniConcessioni createAutorizzazioniConcessioni(Autorizzazioni autorizzazioni, Mercati mercati, MercatiUso mercatiUso,
	    MercatiD mercatiD, Date dateEnd) {

	AutorizzazioniConcessioni autorizzazioniConcessioni = new AutorizzazioniConcessioni();
	autorizzazioniConcessioni.setDatascadenza(dateEnd);
	autorizzazioniConcessioni.setAutorizzazioniByFkAutconcAutatt(autorizzazioni);
	Concessionitipi concessionitipi = concessionitipiService.findById("T");
	autorizzazioniConcessioni.setConcessionitipi(concessionitipi);
	autorizzazioniConcessioni.setConcessionitipi(concessionitipi);
	autorizzazioniConcessioni.setMercati(mercati);
	autorizzazioniConcessioni.setMercatiUso(mercatiUso);
	autorizzazioniConcessioni.setMercatiD(mercatiD);
	return autorizzazioniConcessioni;
    }

    /**
     * <pre>
     * Il metodo rilascia una concessione se l'istanza rispetta i parametri richiesti:
     * 	
     * 		1- L'istanza non deve già avere una concessione per quel mercato
     *          2- Deve esse libero un posteggio che abbia dei criteri compatibili con quelli specificati nell'istanza o
     *             o che non abbia criteri
     *          3- Verranno prima cercati i posteggi liberi (ordinati per PESO DESC) per la giornata configurata sull'albero dei procedimenti, nel caso non sia specificato
     *             si andrà a valutare la preferenza impostata sui dati dinamici dell'istanza. 
     *          4- Successivamente saranno valutati gli altri uso del mercato ordinati per il campo PESO DESC     
     * 
     * &#64;param graduatoriets
     * &#64;param mercati
     * &#64;param config
     * &#64;param codicePreferenzaGiornata
     * </pre>
     */
    private void insertRilascioConcessioniAlleIstanzaStandard(Set<GraduatoriedDTO> graduatoriets, Mercati mercati,
	    ConfigurazionePreferenzeUsoPerMercatoEnum config, Integer codicePreferenzaGiornata) {

	if (log.isDebugEnabled()) {
	    log.debug("insertRilascioConcessioniAlleIstanza# Inizio rilascio delle concessioni per la graduatoria");
	}
	for (GraduatoriedDTO graduatoriedDTO : graduatoriets) {
	    if (log.isDebugEnabled()) {
		log.debug("insertRilascioConcessioniAlleIstanza# Controllo se l' istanza {} ({}) in graduatoria abbia già una concessione",
			new Object[] { graduatoriedDTO.getIstanza().getNumeroistanza(), graduatoriedDTO.getIstanza().getId().getCodice() });
	    }
	    // Per vedere se è già presente una concessione per l'istanza in esame facio una query su vwconcessioni lista filtrando per :
	    // istanza e codice mercato. Si suppone che l'istanza abbia una sola concessione per mercato (Non viene considerato l'uso).
	    boolean isPresente = vwConcessionilistaService.isConcessionePresenteByMercato(graduatoriedDTO.getIstanza().getId().getCodice(),
		    mercati.getId().getCodice());
	    if (!isPresente) {
		if (log.isDebugEnabled()) {
		    log.debug("insertRilascioConcessioniAlleIstanza# Recupero l'uso del mercato per cui si vuole rilasciare la concessione....");
		}
		// L'uso viene recuperato o dalla configurazione dell'albero se prensete, nel caso non sia presente viene fatto una ricerca del 
		//  campo dinamico presente sulla tabella dei mercati in iistanzedyn2dati dell'istanza in esame, se vien etrovato il valore decodificato sarà
		// il codice del mercato uso preferito. Nel caso sull'istanza non sia stata configurata una preferenza allora viene preso il primo mercato uso in ordine
		// del campo "PESO desc"
		Integer codiceMercatoUsoPreferito = getMercatoUso(config, codicePreferenzaGiornata, graduatoriedDTO.getIstanza().getId().getCodice(),
			mercati);
		if (log.isDebugEnabled()) {
		    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUsoPreferito));
		    log.debug("insertRilascioConcessioniAlleIstanza# Recuperato l'uso del mercato: [{}] ({})",
			    new Object[] { mercatiUso.getDescrizione(), codiceMercatoUsoPreferito });
		}
		if (log.isDebugEnabled()) {
		    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUsoPreferito));
		    log.debug(
			    "insertRilascioConcessioniAlleIstanza# Estraggo il primo posteggio libero secondo la preferenza data dal mercato uso scelto:[{}] ({})",
			    mercatiUso.getDescrizione(), codiceMercatoUsoPreferito);
		}
		MercatiDHelper mercatiDHelper = getPosteggioLiberoAndCompatibile(codiceMercatoUsoPreferito, mercati,
			graduatoriedDTO.getIstanza().getId().getCodice());
		if (mercatiDHelper != null) {
		    ///////////////////////////////////////// RECUPERA DATI CONCESSIONE ////////////////////////////////////////////
		    // Recupero tutti i dati necessari per creare la concessione, di default supponiamo che la concessione sia sempre di tipo permanente
		    Concessionitipi concessionitipi = concessionitipiService.findById("P");
		    AutorizzazioniConcessioni concessione = autorizzazioniService
			    .precompilaConcessione(graduatoriedDTO.getIstanza().getId().getCodice());
		    concessione.getAutorizzazioniByFkAutconcAutatt().setFlagAttiva(true);
		    concessione.setConcessionitipi(concessionitipi);
		    concessione.setAutorizzazioniByFkAutconcAutcoll(null);
		    // Inserisco i dati recuperati tramite la configurazione dell manifestazione: mercato,uso ,posteggio
		    concessione.setMercati(mercati);
		    concessione.setMercatiD(mercatiDHelper.getMercatiD());
		    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(mercatiDHelper.getCodiceMercatoUso()));
		    concessione.setMercatiUso(mercatiUso);
		    /////////////////////////////////////////////////////////////////////////////////////////////////////////
		    try {
			///////////////////////////////////////// INSERT CONCESSIONE ////////////////////////////////////////////
			autorizzazioniService.insertConcessione(concessione);
			// Altrimenti non riesce a trovare i posteggi occupati al passa precedente
			bandiDAO.flush();
			bandiDAO.commit();
			//////////////////////////////////////////////////////////////////////////////////////////////////////////
			log.debug(
				"insertRilascioConcessioniAlleIstanza#Rilasciata concessione numero {} ({}) sul posteggio cod. {} ({}) del mercato {} ({}) per il giorno {} ({}) associata l'istanza {} ({}) ",
				new Object[] { concessione.getAutorizzazioniByFkAutconcAutatt().getAutoriznumero(), concessione.getId().getCodice(),
					mercatiDHelper.getMercatiD().getCodiceposteggio(), mercatiDHelper.getMercatiD().getId().getCodice(),
					mercati.getDescrizione(), mercati.getId().getCodice(), mercatiUso.getDescrizione(),
					mercatiUso.getId().getCodice(), graduatoriedDTO.getIstanza().getNumeroistanza(),
					graduatoriedDTO.getIstanza().getId().getCodice() });
		    } catch (Exception exception) {
			log.error("Non è stato possibile inserire la concessione per l'istanza {} ({}) a causa dell' errore {} ({})",
				new Object[] { graduatoriedDTO.getIstanza().getNumeroistanza(), graduatoriedDTO.getIstanza().getId().getCodice(),
					exception.getMessage(), exception });
		    }
		} else {
		    log.debug(
			    "insertRilascioConcessioniAlleIstanza#Non esiste un posteggio libero per il mercato {} ({}) compatibile con l'istanza {} ({}) ",
			    new Object[] { mercati.getDescrizione(), mercati.getId().getCodice(), graduatoriedDTO.getIstanza().getNumeroistanza(),
				    graduatoriedDTO.getIstanza().getId().getCodice() });
		}
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("insertRilascioConcessioniAlleIstanza# L' istanza {} ({}) ha già una concessione per il mercato in esame",
			    new Object[] { graduatoriedDTO.getIstanza().getNumeroistanza(), graduatoriedDTO.getIstanza().getId().getCodice() });
		}
	    }
	}
    }

    /**
     * Verifica se l'istanza e i criteri di assegnazione del posteggio sono compatibili
     * 
     * @param mercatiDCritasses
     * @param codice
     * @return
     */
    private boolean checkCompatibilitaPosteggio(Set<MercatiDCritass> mercatiDCritasses, Integer codiceIstanza) {

	boolean isCompatibili = true;
	// Se ilposteggio contienti dei criteri di assegnazione faccio il controllo con i criteri impostati sull'istanza,
	// atrimenti ritorno subito true
	if (!mercatiDCritasses.isEmpty()) {
	    for (MercatiDCritass mercatiDCritass : mercatiDCritasses) {
		// Prendo il campo dai criteri di assegnazione dle posteggio
		Dyn2Campi campiCritAssegnazionePosteggio = mercatiDCritass.getDyn2Campi();
		// Verifico se esiste sulle schede dell'istanza
		List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
			campiCritAssegnazionePosteggio.getId().getCodice());
		// se esiste e il valore decodificato è lo stesso per tutti i criteri ritorno true , altrimwenti false
		if (istanzedyn2datis.isEmpty() || !istanzedyn2datis.get(0).getValore().equalsIgnoreCase(mercatiDCritass.getValore())) {
		    isCompatibili = false;
		    if (log.isDebugEnabled()) {
			log.debug("checkCompatibilitaPosteggio# Posteggio con codice [{}] ({}) non compatibile per l'istanza {}",
				new Object[] { mercatiDCritass.getMercatiD().getCodiceposteggio(), mercatiDCritass.getMercatiD().getId().getCodice(),
					codiceIstanza });
		    }
		    break;
		}
	    }
	}
	return isCompatibili;
    }

    /**
     * Controllo se per il mercato uso preferito esiste un posteggio libero e compatibile con l'istanza passata, se si
     * ritorno il posteggio, altrimenti se non trovo il posteggio ritorno null. L'algoritmo prima controlla se il
     * posteggio è libero per il mercato uso preferito passato. Nel caso nonci sfosse inizia a controllare per i
     * rimanenti usi ordinandoli per il campo PESO DESC
     * 
     * @param codiceMercatoUsoPreferito
     * @param mercati
     * @return
     */
    private MercatiDHelper getPosteggioLiberoAndCompatibile(Integer codiceMercatoUsoPreferito, Mercati mercati, Integer codiceIstanza) {

	MercatiDHelper risultato = new MercatiDHelper();
	List<MercatiUso> listMercatiUsos = mercatiUsoService.findByMercato(mercati);
	List<MercatiD> listPosteggi = new ArrayList<MercatiD>();
	if (EntityUtils.getNestedProperty(mercati.getDyn2CampiPrefPosteggio(), "id.codice") != null) {
	    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
		    mercati.getDyn2CampiPrefPosteggio().getId().getCodice());
	    MercatiD d = null;
	    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
		d = mercatiDService.findById(new PkId(Integer.parseInt(istanzedyn2dati.getValore())));
		listPosteggi.add(d);
	    }
	} else {
	    listPosteggi = mercatiDService.findByMercatoOrderByPeso(mercati, PosteggiEnum.ACTIVE);
	}
	if (log.isDebugEnabled()) {
	    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUsoPreferito));
	    log.debug("getPosteggioLibero# Controllo se per il mercato uso preferito: [{}] ({}) è presente un posteggio libero",
		    new Object[] { mercatiUso.getDescrizione(), codiceMercatoUsoPreferito });
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// VERIFICO DISPONIBILITA SUL MERCATO USA DEFINITO /////////////////////////////////////
	////////////////////////////////////////////// SULL'ALBERO O SULLE PREFERENZE DEL MERCATO /////////////////////////////////////////
	// Ciclo tutti i posteggi 
	for (MercatiD mercatiD : listPosteggi) {
	    // Cerco per ogni posteggio per l'uso preferito se esiste un posteggio senza concenssione
	    if (log.isDebugEnabled()) {
		MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUsoPreferito));
		log.debug("getPosteggioLibero# Verifico il posteggio [{}] ({}) per l'uso [{}] ({})", new Object[] { mercatiD.getCodiceposteggio(),
			mercatiD.getId().getCodice(), mercatiUso.getDescrizione(), codiceMercatoUsoPreferito });
	    }
	    boolean isFree = vwConcessionilistaService.isConcessionePresenteByMercatoAndUsoAndPosteggio(mercati.getId().getCodice(),
		    codiceMercatoUsoPreferito, mercatiD.getId().getCodice());
	    // Se lo trovo ritorno il posteggio 
	    if (isFree) {
		if (log.isDebugEnabled()) {
		    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUsoPreferito));
		    log.debug("getPosteggioLibero# Trovato posteggio {} ({}) libero per il mercato uso preferito {} ({})",
			    new Object[] { mercatiD.getCodiceposteggio(), mercatiD.getId().getCodice(), mercatiUso.getDescrizione(),
				    mercatiUso.getId().getCodice() });
		}
		boolean isCompatibile = checkCompatibilitaPosteggio(mercatiD.getMercatidcritasses(), codiceIstanza);
		if (isCompatibile) {
		    if (log.isDebugEnabled()) {
			MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUsoPreferito));
			log.debug("getPosteggioLibero#  Posteggio {} ({}) per l'uso preferito {} ({}) compatibile   ",
				new Object[] { mercatiD.getCodiceposteggio(), mercatiD.getId().getCodice(), mercatiUso.getDescrizione(),
					mercatiUso.getId().getCodice() });
		    }
		    risultato.setMercatiD(mercatiD);
		    risultato.setCodiceMercatoUso(codiceMercatoUsoPreferito);
		    return risultato;
		}
	    } else {
		MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUsoPreferito));
		log.debug("getPosteggioLibero# Il posteggio {} ({}) libero per il mercato uso preferito {} ({}) occupato, passo al successivo",
			new Object[] { mercatiD.getCodiceposteggio(), mercatiD.getId().getCodice(), mercatiUso.getDescrizione(),
				mercatiUso.getId().getCodice() });
	    }
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// VERIFICO DISPONIBILITA SU GLI LATRI MERCATO USO  /////////////////////////////////////
	////////////////////////////////////////////// ////////// ASSOCIATI AL MERCATO/////////////////////////////////////////
	if (log.isDebugEnabled()) {
	    log.debug(
		    "getPosteggioLibero# Per il mercato uso preferito non sono presenti posteggi liberi, passo al successivo del mercato USO ordinatio per peso");
	}
	// Ciclo gli usi rimasti per il mercato
	for (MercatiUso mercatiUso : listMercatiUsos) {
	    if (!mercatiUso.getId().getCodice().equals(codiceMercatoUsoPreferito)) {
		// ciclo i posteggi
		for (MercatiD mercatiD : listPosteggi) {
		    // Cerco per ogni posteggio per l'uso in esame se esiste un posteggio  senza concenssione
		    boolean isFree = vwConcessionilistaService.isConcessionePresenteByMercatoAndUsoAndPosteggio(mercati.getId().getCodice(),
			    codiceMercatoUsoPreferito, mercatiD.getId().getCodice());
		    // Se lo trovo ritorno il posteggio 
		    if (isFree) {
			if (log.isDebugEnabled()) {
			    log.debug("getPosteggioLibero# Trovato posteggio {} ({}) libero se per il mercato uso preferito {} ({})",
				    new Object[] { mercatiD.getCodiceposteggio(), mercatiD.getId().getCodice(), mercatiUso.getDescrizione(),
					    mercatiUso.getId().getCodice() });
			}
			boolean isCompatibile = checkCompatibilitaPosteggio(mercatiD.getMercatidcritasses(), codiceIstanza);
			if (isCompatibile) {
			    if (log.isDebugEnabled()) {
				log.debug("getPosteggioLibero#  Posteggio {} ({}) per l'uso {} ({}) compatibile   ",
					new Object[] { mercatiD.getCodiceposteggio(), mercatiD.getId().getCodice(), mercatiUso.getDescrizione(),
						mercatiUso.getId().getCodice() });
			    }
			    risultato.setMercatiD(mercatiD);
			    risultato.setCodiceMercatoUso(mercatiUso.getId().getCodice());
			    return risultato;
			}
		    }
		}
	    }
	}
	// Nessun posteggio libero trovato
	return null;
    }

    /**
     * Ritorna il codice del mercato uso scelto come preferenza. Nel caso sia configurato sull'albero viene scelto
     * quello , nel caso invece non sia presente viene recuperato andando a vedere il valore decodificato del campo
     * dinamico sull'istanza (istanzeDyn2dati) che corrisponde allo stesso campo dinamico presente sulla tabella
     * mercati. Nel caso non sia presente questa associazioni di campi allora il codice ritornato sarà quello del primo
     * mercato suo configurato ordinando la lista per il campo "PESO" DESC.
     * 
     * @param config
     * @param codicePreferenzaGiornata
     * @param codiceIstanza
     * @return
     */
    private Integer getMercatoUso(ConfigurazionePreferenzeUsoPerMercatoEnum config, Integer codicePreferenzaGiornata, Integer codiceIstanza,
	    Mercati mercati) {

	switch (config) {
	case MERCATO_USO_DA_ALBERO:
	    if (log.isDebugEnabled()) {
		log.debug("getMercatoUso# la preferenza [{}] è stata presa dall'albero dei procedimenti", codicePreferenzaGiornata);
	    }
	    // allora il codice dell'uso del mercato  corrisponde a codice preferenza
	    return codicePreferenzaGiornata;
	case MERCATO_USO_CONFIG_DA_MERCATI:
	    // Il codice preferenza definisce il campo dinamico che setta la preferenza sull'uso tramite 
	    // il mercato. (Cmapo dyn2dati di mercato)
	    // Controllo se l'istanza in esame abbiamo un campo dinamico con questo codice, se si il valore decodificato rappresenta 
	    // il codice uso cercato
	    if (log.isDebugEnabled()) {
		log.debug("getMercatoUso# cerco la preferenza dai dati dinamici dell'istanza [{}] dal campo[{}] ", codiceIstanza,
			codicePreferenzaGiornata);
	    }
	    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza, codicePreferenzaGiornata);
	    if (log.isDebugEnabled()) {
		log.debug("getMercatoUso# tornate {} preferenze dai campi dinamici", istanzedyn2datis.size());
	    }
	    if (!istanzedyn2datis.isEmpty()) {
		// Sono sicuro che sarà il primo in quanto ho ordinato per molteplicità, anche se ci sono pià campi uguali
		// tra i modelli il valore sarà sempre lo stesso. (Regola di inserimenti dei valori di campi dinamici)
		if (log.isDebugEnabled()) {
		    log.debug("getMercatoUso# valore preferenza recuperata dai dati dinamici: [{}]", istanzedyn2datis.get(0).getValoredecodificato());
		}
		return Integer.parseInt(istanzedyn2datis.get(0).getValore());
	    }
	default:
	    if (log.isDebugEnabled()) {
		log.debug("getMercatoUso# tipologia di configurazione [{}] non gestita", config);
	    }
	    break;
	}
	if (log.isDebugEnabled()) {
	    log.debug("getMercatoUso# cerco la giornata per il mercato ordinata per peso");
	}
	// Non è stata trovata una preferenza prendo il primo record dell'uso per il mercato in esame ordinando per il campo PESO DESC.
	//List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercati);
	List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercati);
	if (!mercatiUsos.isEmpty()) {
	    return mercatiUsos.get(0).getId().getCodice();
	} else {
	    throw new RuntimeException(
		    "Attenzione non è stato possibile recuperare una giuornata per il mercato. Controllare nella configurazione del " +
				       "mercato la presenza di almeno una giornata");
	}
    }

    private ConfigurazionePreferenzeUsoPerMercatoEnum getTipologiaConfigurazionePreferenzaUsoMercato(Alberoproc alberoproc) {

	if (log.isDebugEnabled()) {
	    log.debug("getTipologiaConfigurazionePreferenzaUsoMercato# Controllo prima la configurazione sulla voce dell'albero {} ({})",
		    new Object[] { alberoproc.getVwAlberoproc().getScDescrizione(), alberoproc.getId().getCodice() });
	}
	ConfigurazionePreferenzeUsoPerMercatoEnum conf = alberoprocService.isGestisceMercatoAndUso(alberoproc.getId().getCodice());
	//Mercato ed uso configurati sull'albero ritorno questa condizione
	if (conf.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_DA_ALBERO) == 0) {
	    return conf;
	}
	//Sull'albero non è configurato un mercato non posso andare avanti, ritorno condizion eper restituirre l'errore
	if (conf.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_NON_CONFIG_SU_ALBERO) == 0) {
	    return conf;
	}
	// Sull'albero confifurato il mercato e non l'uso, controllo se la preferenza si trova sui dati dinamici del mercato
	if (conf.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_NON_DA_ALBERO) == 0) {
	    ConfigurazionePreferenzeUsoPerMercatoEnum confMercato = mercatiService
		    .isPreferenzaUsoConfigurata(alberoproc.getMercato().getId().getCodice());
	    if (confMercato.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_NON_CONFIG_DA_MERCATI) == 0) {
		return ConfigurazionePreferenzeUsoPerMercatoEnum.PREFERENZA_USO_NON_CONFIG;
	    } else {
		return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_CONFIG_DA_MERCATI;
	    }
	}
	return conf;
    }

    private ConfigurazionePreferenzeUsoPerMercatoEnum getTipologiaConfigurazionePreferenzaUsoMercatoMultiintervento(Bandi bandi) {

	if (log.isDebugEnabled()) {
	    log.debug(
		    "getTipologiaConfigurazionePreferenzaUsoMercatoMultiintervento# Controllo prima la configurazione sulla lista delle voci dell'albero per il bando {} ({})",
		    new Object[] { bandi.getDescrizione(), bandi.getId().getCodice() });
	}
	// Recupero la lista distinct dei codici mercato su tutti gli interventi collegati al bando
	List<Integer> listaCodiciMercati = bandiAlberoprocService.findDistinctMercatiByBando(bandi.getId().getCodice());
	// Sono più di uno allora errore, non possono esiste più mercato configurati per lo stesso bando anche se abbiamo interventi diversi
	if (listaCodiciMercati.isEmpty()) {
	    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_NON_CONFIG_SU_ALBERO;
	}
	if (listaCodiciMercati.size() > 1) {
	    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATI_CONFIG_SU_ALBERO;
	}
	// C'è un solo valore, ma è null, quindi non posso rilasciare concessioni
	if (listaCodiciMercati.get(0) == null) {
	    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_NON_CONFIG_SU_ALBERO;
	}
	// Recupero la lista distinct dei codici mercato uso su tutti gli interventi collegati al bando
	List<Integer> listaCodiciMercatiUso = bandiAlberoprocService.findDistinctMercatiUsoByBando(bandi.getId().getCodice());
	// Sono più di uno allora errore, non possono esiste più mercati uso configurati per lo stesso bando anche se abbiamo interventi diversi
	if (listaCodiciMercatiUso.size() > 1) {
	    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATI_USO_CONFIG_SU_ALBERO;
	} else {
	    // Se ce ne è uno allora ed è null verifico se c'è la configurazione sulle preferenze del mercato
	    if (listaCodiciMercatiUso.get(0) == null) {
		ConfigurazionePreferenzeUsoPerMercatoEnum confMercato = mercatiService.isPreferenzaUsoConfigurata(listaCodiciMercati.get(0));
		if (confMercato.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_NON_CONFIG_DA_MERCATI) == 0) {
		    return ConfigurazionePreferenzeUsoPerMercatoEnum.PREFERENZA_USO_NON_CONFIG;
		} else {
		    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_CONFIG_DA_MERCATI;
		}
	    } else { // ce ne è uno solo è popolato, allora il mercato suo sarà preso da una delle voci dell'albero.
		return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_DA_ALBERO;
	    }
	}
	//	ConfigurazionePreferenzeUsoPerMercatoEnum conf = alberoprocService.isGestisceMercatoAndUso(bandi.getId().getCodice());
	//	//Mercato ed uso configurati sull'albero ritorno questa condizione
	//	if (conf.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_DA_ALBERO) == 0) {
	//	    return conf;
	//	}
	//	//Sull'albero non è configurato un mercato non posso andare avanti, ritorno condizion eper restituirre l'errore
	//	if (conf.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_NON_CONFIG_SU_ALBERO) == 0) {
	//	    return conf;
	//	}
	//	// Sull'albero confifurato il mercato e non l'uso, controllo se la preferenza si trova sui dati dinamici del mercato
	//	if (conf.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_NON_DA_ALBERO) == 0) {
	//	    ConfigurazionePreferenzeUsoPerMercatoEnum confMercato = mercatiService.isPreferenzaUsoConfigurata(alberoproc.getMercato().getId()
	//		    .getCodice());
	//	    if (confMercato.compareTo(ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_NON_CONFIG_DA_MERCATI) == 0) {
	//		return ConfigurazionePreferenzeUsoPerMercatoEnum.PREFERENZA_USO_NON_CONFIG;
	//	    } else {
	//		return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_CONFIG_DA_MERCATI;
	//	    }
	//	}
	//	return conf;
    }

    @Override
    public GraduatorieHelper findHelperGraduatoria(Integer graduatoriaid) {

	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(graduatoriaid));
	Alberoproc alberoproc = graduatoriet.getBandi().getAlberoproc();
	// Nel caso di tipo bando multi intervento, alla creazione del bando, l'albero proc non è obbl.
	// in quanto potranno essere configurati N interventi per il bando. Il valore Mercati deve essere recuperato
	// dalla voce dell'albero principale se tipibando.flagMultiintervento==false, altrimenti dall'intervento configurato nella 
	// tabella BANDI_ALBEROPROC con ordine minore. 
	Mercati mercati = null;
	boolean flagMultiIntervento = BooleanUtils.toBoolean(graduatoriet.getBandi().getTipibando().getFlagMultiintervento());
	if (!flagMultiIntervento) {
	    log.debug("findHelperGraduatoria# tipo bando non gestiste il multiintervento. FlagMultiintervento : {}", flagMultiIntervento);
	    // Mantiene la retrocompatibilità con la gestione tradizionale
	    mercati = alberoproc.getMercato();
	} else {
	    log.debug("findHelperGraduatoria# tipo bando  gestiste il multiintervento. FlagMultiintervento : {}", flagMultiIntervento);
	    // Gestisce la modalità multi intervento
	    // cerco l'intervento in BANDI_ALBEROPROC con ordine minore (è il primo della lista)
	    // Sono ordinati per ordine ASC, prendo il primo
	    //1. Controllo che per tutti gli interventi configurati sia presente lo stesso mercato o non siano presenti mercati
	    // configurati
	    boolean isCheckStessoMercato = true;
	    List<BandiAlberoproc> list = bandiAlberoprocService.findByBandi(graduatoriet.getBandi().getId().getCodice());
	    BandiAlberoproc primoBandiAlberoproc = list.get(0);
	    for (BandiAlberoproc bandiAlberoproc : list) {
		boolean isStessoMercato = checkStessoMercato(primoBandiAlberoproc, bandiAlberoproc);
		if (!isStessoMercato) {
		    log.debug("findHelperGraduatoria# Non tutti i mercati configurati per gli alberi proc sono uguali");
		    isCheckStessoMercato = false;
		}
	    }
	    // Se sono tutti ugulai prendo il primo
	    if (isCheckStessoMercato) {
		log.debug("findHelperGraduatoria# bandiAlberoproc con codice {} e ordine {} ",
			new Object[] { primoBandiAlberoproc.getAlberoproc().getId().getCodice(), primoBandiAlberoproc.getOrdine() });
		mercati = primoBandiAlberoproc.getAlberoproc().getMercato();
	    } else {
		//TODO rilancio eccezzione??????????????
		// Rilancio eccezione???!?!?!?!
	    }
	}
	GraduatorieHelper helper = new GraduatorieHelper();
	boolean isManifestazione = false;
	if (mercati != null && mercati.getId().getCodice() != null) {
	    isManifestazione = true;
	}
	/**
	 * Sezione che valuta se devono comparire i bottoni rilascia e Assegna concessione accanto ad ogni istanza
	 * associata alla graduatoria.
	 */
	boolean mostraRilasciaEdAssegnaConcessione = checkMostraRilascaEdAssegnaConcessione(graduatoriet);
	helper.setMostraRilasciaEdAssegnaConcessione(mostraRilasciaEdAssegnaConcessione);
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Sezione che valuta se devono comparire i bottoni rilascia e Assegna concessione accanto ad ogni istanza
	 * associata alla graduatoria.
	 */
	boolean mostraBottoneRilasciaConcessione = checkMostraBottoneRilascaConcessioni(graduatoriet);
	helper.setMostraButtonRilasciaConcessione(mostraBottoneRilasciaConcessione);
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	List<Tipibandooutput> tipibandooutputList = new ArrayList<Tipibandooutput>();
	// copio tutti i tipibandooutput (cioè i campi di output del tipo di graduatoria) dal Set alla Lista per
	// mantenerli ordinati
	tipibandooutputList.addAll(graduatoriet.getTipigraduatoriet().getTipibandooutputs());
	// itero il set che contiene la graduatoria
	//Set<Graduatoried> graduatorieSet = graduatoriet.getGraduatorieds();
	Set<GraduatoriedDTO> graduatoriedDTO2s = new LinkedHashSet<GraduatoriedDTO>();
	List<GraduatoriedDTO> graduatoriedDTOs = graduatoriedService.findByGraduatoriet(graduatoriet);
	GraduatoriedDTO temp = null;
	for (GraduatoriedDTO graduatoried : graduatoriedDTOs) {
	    IstanzeDTO istanza = graduatoried.getIstanza();
	    // per ogni campo di output recupero il valore (dyn2dati) della scheda dinamica dell'istanza e lo inserisco
	    // in una lista di oggetti bandiOutput
	    graduatoried = graduatoriedService.populateListaCampigraduatoria(graduatoried, istanza);
	    if (temp == null) {
		temp = graduatoried;
		graduatoriedDTO2s.add(graduatoried);
	    } else {
		if (temp.getId().getCodice().equals(graduatoried.getId().getCodice())) {
		    if (graduatoried.getConcessione() != null && graduatoried.getConcessione().getCodiceposteggio() != null) {
			graduatoriedDTO2s.remove(temp);
			temp = graduatoried;
			graduatoriedDTO2s.add(graduatoried);
		    }
		} else {
		    temp = graduatoried;
		    graduatoriedDTO2s.add(graduatoried);
		}
	    }
	    graduatoried.setBandoOutputList(istanzedyn2datiService.findBandoOutput(graduatoried.getId().getCodice()));
	    // se la graduatoria è per un mercato allora recupero anche la concessione rilasciata
	    if (isManifestazione) {
		graduatoried.setConcESub(autorizzazioniService.findConcESub(istanza.getId().getCodice()));
	    }
	}
	//	model.addAttribute("mercatoIsPresente", isManifestazione);
	helper.setMercatoIsPresente(Boolean.valueOf(isManifestazione));
	//	model.addAttribute("graduatoriet", graduatoriet);
	helper.setGraduatoriet(graduatoriet);
	//	model.addAttribute("tipibandooutputList", tipibandooutputList);
	helper.setTipibandooutputList(tipibandooutputList);
	//	model.addAttribute("graduatoriedCampi", graduatoriedDTO2s);
	helper.setGraduatoriedDTO2s(graduatoriedDTO2s);
	//	model.addAttribute("sizeCriteri", graduatoriedDTOs.size());
	helper.setSizeCriteri(graduatoriedDTO2s.size());
	if (EntityUtils.getNestedProperty(mercati, "id.codice") != null) {
	    helper.setMostraButtonRilasciaPosizione(mercati.getFlagGestisciPosizioni());
	}
	int c = graduatorietComService.countByGraduatoriet(graduatoriaid);
	helper.setPresentiComunicazioni(c > 0);
	return helper;
    }

    /**
     * Gestisce tutte le regole che permettono di visualizzare o no i link crea e assegna concessione accando ad ogni
     * istanza associata alla graduatori
     * 
     * @param graduatoriet
     * @return
     */
    private boolean checkMostraRilascaEdAssegnaConcessione(Graduatoriet graduatoriet) {

	boolean risultato = true;
	if (BooleanUtils.toBoolean(graduatoriet.getTipigraduatoriet().getFlagPianorotazione())) {
	    return false;
	}
	if (BooleanUtils.toBoolean(graduatoriet.getTipigraduatoriet().getFlagEsprArtTemp())) {
	    return false;
	}
	return risultato;
    }

    /**
     * <pre>
     * 
     * Considero che l'albero proc abbia il mercato uguale se: 
     * 		1. Entrambi nulli 
     * 		2. Stesso record del DB
     * 
     * &#64;param primoBandiAlberoproc
     * &#64;param bandiAlberoproc
     * &#64;return
     * </pre>
     */
    private boolean checkStessoMercato(BandiAlberoproc primoBandiAlberoproc, BandiAlberoproc bandiAlberoproc) {

	boolean risultato = true;
	if (EntityUtils.getNestedProperty(primoBandiAlberoproc.getAlberoproc().getMercato(), "id.codice") == null
		&& EntityUtils.getNestedProperty(bandiAlberoproc.getAlberoproc().getMercato(), "id.codice") == null) {
	    return risultato;
	} else {
	    if (EntityUtils.equals(primoBandiAlberoproc.getAlberoproc().getMercato(), bandiAlberoproc.getAlberoproc().getMercato())) {
		return risultato;
	    } else {
		return false;
	    }
	}
    }

    private boolean checkMostraBottoneRilascaConcessioni(Graduatoriet graduatoriet) {

	boolean risultato = true;
	if (BooleanUtils.toBoolean(graduatoriet.getTipigraduatoriet().getFlagPianorotazione())) {
	    return false;
	}
	return risultato;
    }

    @Override
    public void insertConcessioniPianoRotazione(Graduatoriet graduatoriet, Date dateRilascio, Tipologiaregistri tipologiaregistri) {

	tipologiaregistri = tipologiaregistriService.findById(tipologiaregistri.getId());
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (EntityUtils.getNestedProperty(tipologiaregistri, "id.codice") == null) {
	    _ivs.add(new InvalidValue("validator.notEmpty", ConcessioniCommand.class, "Il registro", null, new ConcessioniCommand()));
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
	List<GraduatoriedDTO> list = graduatoriedService.findByGraduatoriet(graduatoriet);
	Mercati mercato = graduatoriet.getBandi().getAlberoproc().getMercato();
	for (GraduatoriedDTO graduatoriedDTO : list) {
	    Autorizzazioni autorizzazioni = new Autorizzazioni();
	    Istanze istanza = istanzeService.findById(new PkId(graduatoriedDTO.getIstanza().getId().getCodice()));
	    autorizzazioni.setAnagrafe(istanza.getTitolareLegaleORichiedente());
	    autorizzazioni.setOccupante(istanza.getTitolareLegaleORichiedente());
	    autorizzazioni.setAutorizdata(dateRilascio);
	    autorizzazioni.setDataRilascio(dateRilascio);
	    autorizzazioni.setAutorizdataregistr(new Date());
	    Date datascadenza = dateRilascio;
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(datascadenza);
	    calendar.add(Calendar.YEAR, 2);
	    datascadenza = calendar.getTime();
	    autorizzazioni.setDataCessazione(datascadenza);
	    autorizzazioni.setDatascadenza(datascadenza);
	    autorizzazioni.setFlagAttiva(true);
	    autorizzazioni.setIstanza(istanza);
	    // Se non è settato che succede, controllare se non viene passato la tipologia l momento della creazione che succede?
	    if (StringUtils.isNotBlank(tipologiaregistri.getTrProgressivo())) {
		autorizzazioni.setAutoriznumero(tipologiaregistri.getTrProgressivo());
	    }
	    VwEntilocali autorizcomune = vwEntilocaliService.findById(istanza.getComune().getCodicecomune());
	    autorizzazioni.setAutorizcomune(autorizcomune);
	    Tipologiaregistri tipologiaregistro = tipologiaregistriService.findById(new PkId(tipologiaregistri.getId().getCodice()));
	    autorizzazioni.setTipologiaregistro(tipologiaregistro);
	    autorizzazioniService.insertAutorizzazione(autorizzazioni);
	    List<GraduatorietPianoRotazioneDTO> graduatorietPianoRotazioneDTOs = graduatorietPianorotazioneService
		    .findByGraduatorieTAndIstanza(graduatoriet, graduatoriedDTO.getIstanza().getId().getCodice());
	    AutorizzazioniConcessioni autorizzazioniConcessioni = null;
	    for (GraduatorietPianoRotazioneDTO graduatorietPianoRotazioneDTO : graduatorietPianoRotazioneDTOs) {
		autorizzazioniConcessioni = new AutorizzazioniConcessioni();
		autorizzazioniConcessioni.setAutorizzazioniByFkAutconcAutatt(autorizzazioni);
		Concessionitipi concessionitipi = concessionitipiService.findById("T");
		autorizzazioniConcessioni.setConcessionitipi(concessionitipi);
		autorizzazioniConcessioni.setDatascadenza(datascadenza);
		autorizzazioniConcessioni.setMercati(mercato);
		MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(graduatorietPianoRotazioneDTO.getMercatiUso().getId().getCodice()));
		autorizzazioniConcessioni.setMercatiUso(mercatiUso);
		MercatiD mercatiD = mercatiDService.findById(new PkId(graduatorietPianoRotazioneDTO.getMercatiD().getId().getCodice()));
		autorizzazioniConcessioni.setMercatiD(mercatiD);
		autorizzazioniConcessioniService.insert(autorizzazioniConcessioni);
	    }
	    //	    bandiDAO.flush();
	    //	    bandiDAO.clear();
	}
    }

    @Override
    public List<Bandi> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("descrizione", textToSearch));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return bandiDAO.findByFilterTable(ft);
    }
}
