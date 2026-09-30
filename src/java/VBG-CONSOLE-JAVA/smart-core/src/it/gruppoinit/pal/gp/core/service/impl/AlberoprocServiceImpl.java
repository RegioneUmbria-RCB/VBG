package it.gruppoinit.pal.gp.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ConfigurazionePreferenzeUsoPerMercatoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoCausali;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocModelli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Azioni;
// import it.gruppoinit.pal.gp.core.domain.CartServiziDizionario;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.InventarioprocedimentiWrapper;
import it.gruppoinit.pal.gp.core.domain.helper.PercorsoAlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.StepsEnum;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocChildrenCommand;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DownloadBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.EndoprocedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.EndoprocedimentoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FamiglieEndoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBeanComparator;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ModulisticaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NormativaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.OneriBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PercorsoBean;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoCausaliService;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocAtecoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocComuniEsclusiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoLocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocLeggiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocModelliService;
import it.gruppoinit.pal.gp.core.service.AlberoprocOneriService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocRuoliService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisoggettoService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RiTipiinterventoService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.rules.AlberoprocBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class AlberoprocServiceImpl extends BaseServiceImpl<Alberoproc, PkId> implements AlberoprocService {

    public static final int CODICE_AZIONE_DEFAULT = 2;
    //
    //
    // ATTENZIONE !!! I METODI CHE MODIFICANO RECORD DEI ALBEROPROC E TABELLE CORRELATE DEVONO SVUOTARE LA CACHE DI
    // ALBEROPROC CHIAMANDO IL METODO
    // alberoprocService.updateAlberoprocCache();
    //
    //
    private static final Logger log = LoggerFactory.getLogger(AlberoprocServiceImpl.class);
    private AlberoprocDAO alberoprocDAO;
    private AlberoCausaliService alberoCausaliService;
    private AlberoprocArendoService alberoprocArendoService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private AlberoprocAtecoService alberoprocAtecoService;
    private AlberoprocComuniEsclusiService alberoprocComuniEsclusiService;
    private AlberoprocRuoliService alberoprocRuoliService;
    private AlberoprocDocumentiService alberoprocDocumentiService;
    private AlberoprocEndoService alberoprocEndoService;
    private AlberoprocEndoLocService alberoprocEndoLocService;
    private AlberoprocLeggiService alberoprocLeggiService;
    private AlberoprocOneriService alberoprocOneriService;
    private AlberoprocDyn2modellitService alberoprocDyn2modellitService;
    private AlberoprocModelliService alberoprocModelliService;
    private AlberoprocTipisoggettoService alberoprocTipisoggettoService;
    private FoArjStepsService foArjStepsService;
    private AzioniService azioniService;
    private CacheManager cacheManager;
    private ConfigurazioneService configurazioneService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private MercatiService mercatiService;
    private MercatiUsoService mercatiUsoService;
    private OggettiService oggettiService;
    private ResponsabiliService responsabiliService;
    private RiTipiinterventoService riTipiinterventoService;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private TipiprocedureService tipiprocedureService;
    private TipologiaregistriService tipologiaregistriService;
    private InventarioprocEndoService inventarioprocEndoService;

    @Autowired
    public void setInventarioprocEndoService(InventarioprocEndoService inventarioprocEndoService) {

	this.inventarioprocEndoService = inventarioprocEndoService;
    }

    @Autowired
    public void setAlberoprocProtocolloService(AlberoprocProtocolloService alberoprocProtocolloService) {

	this.alberoprocProtocolloService = alberoprocProtocolloService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setAlberoprocEndoLocService(AlberoprocEndoLocService alberoprocEndoLocService) {

	this.alberoprocEndoLocService = alberoprocEndoLocService;
    }

    @Autowired
    public void setFoArjStepsService(FoArjStepsService foArjStepsService) {

	this.foArjStepsService = foArjStepsService;
    }

    @Autowired
    public void setAlberoCausaliService(AlberoCausaliService alberoCausaliService) {

	this.alberoCausaliService = alberoCausaliService;
    }

    @Autowired
    public void setAlberoprocArendoService(AlberoprocArendoService alberoprocArendoService) {

	this.alberoprocArendoService = alberoprocArendoService;
    }

    @Autowired
    public void setAlberoprocAtecoService(AlberoprocAtecoService alberoprocAtecoService) {

	this.alberoprocAtecoService = alberoprocAtecoService;
    }

    @Autowired
    public void setAlberoprocComuniEsclusiService(AlberoprocComuniEsclusiService alberoprocComuniEsclusiService) {

	this.alberoprocComuniEsclusiService = alberoprocComuniEsclusiService;
    }

    @Autowired
    public void setAlberoprocModelliService(AlberoprocModelliService alberoprocModelliService) {

	this.alberoprocModelliService = alberoprocModelliService;
    }

    @Autowired
    public void setAlberoprocDyn2modellitService(AlberoprocDyn2modellitService alberoprocDyn2modellitService) {

	this.alberoprocDyn2modellitService = alberoprocDyn2modellitService;
    }

    @Autowired
    public void setAlberoprocOneriService(AlberoprocOneriService alberoprocOneriService) {

	this.alberoprocOneriService = alberoprocOneriService;
    }

    @Autowired
    public void setAlberoprocLeggiService(AlberoprocLeggiService alberoprocLeggiService) {

	this.alberoprocLeggiService = alberoprocLeggiService;
    }

    @Autowired
    public void setAlberoprocEndoService(AlberoprocEndoService alberoprocEndoService) {

	this.alberoprocEndoService = alberoprocEndoService;
    }

    @Autowired
    public void setAlberoprocDocumentiService(AlberoprocDocumentiService alberoprocDocumentiService) {

	this.alberoprocDocumentiService = alberoprocDocumentiService;
    }

    @Autowired
    public void setAlberoprocRuoliService(AlberoprocRuoliService alberoprocRuoliService) {

	this.alberoprocRuoliService = alberoprocRuoliService;
    }

    @Autowired
    public void setAlberoprocTipisoggettoService(AlberoprocTipisoggettoService alberoprocTipisoggettoService) {

	this.alberoprocTipisoggettoService = alberoprocTipisoggettoService;
    }

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    @Autowired
    public void setAzioniService(AzioniService azioniService) {

	this.azioniService = azioniService;
    }

    @Autowired(required = false)
    public void setCacheManager(CacheManager cacheManager) {

	this.cacheManager = cacheManager;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setRiTipiinterventoService(RiTipiinterventoService riTipiinterventoService) {

	this.riTipiinterventoService = riTipiinterventoService;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setTipologiaregistriService(TipologiaregistriService tipologiaregistriService) {

	this.tipologiaregistriService = tipologiaregistriService;
    }

    @Override
    public void delete(Alberoproc entity) {

	deleteInternal(entity, true);
    }

    private void deleteInternal(Alberoproc entity, boolean deleteChilds) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity, deleteChilds);
	    String scCodice = entity.getScCodice();
	    String scCodicePadre = scCodice.substring(0, scCodice.length() - 2);
	    alberoprocDAO.delete(entity);
	    if (deleteChilds) {
		if (StringUtils.isNotBlank(scCodicePadre)) {
		    List<Alberoproc> figli = alberoprocDAO.findAlberoprocFigli(entity.getId().getIdcomune(), scCodicePadre, true,
			    DAOOrderTypeEnum.ASC, false);
		    if (figli.isEmpty()) {
			Alberoproc alberoprocPadre = this.findByScCodice(entity.getId().getIdcomune(), scCodicePadre);
			alberoprocPadre.setScPadre(false);
			this.update(alberoprocPadre);
		    }
		}
	    }
	    updateAlberoprocCache();
	}
    }

    protected void childDelete(Alberoproc entity, boolean deleteChilds) {

	Set<AlberoprocDocumenti> alberoprocDocumentis = entity.getAlberoprocDocumentis();
	if (alberoprocDocumentis != null) {
	    if (!alberoprocDocumentis.isEmpty()) {
		for (AlberoprocDocumenti alberoprocDocumenti : alberoprocDocumentis) {
		    alberoprocDocumentiService.delete(alberoprocDocumenti);
		}
		entity.setAlberoprocDocumentis(null);
	    }
	}
	Set<AlberoprocEndo> alberoprocEndos = entity.getAlberoprocEndos();
	if (alberoprocEndos != null) {
	    if (!alberoprocEndos.isEmpty()) {
		for (AlberoprocEndo alberoprocEndo : alberoprocEndos) {
		    alberoprocEndoService.delete(alberoprocEndo);
		}
		entity.setAlberoprocEndos(null);
	    }
	}
	Set<AlberoprocLeggi> alberoprocLeggis = entity.getAlberoprocLeggis();
	if (alberoprocLeggis != null) {
	    if (!alberoprocLeggis.isEmpty()) {
		for (AlberoprocLeggi alberoprocLeggi : alberoprocLeggis) {
		    alberoprocLeggiService.delete(alberoprocLeggi);
		}
		entity.setAlberoprocLeggis(null);
	    }
	}
	Set<AlberoprocDyn2modellit> alberoprocDyn2modellits = entity.getAlberoprocDyn2modellits();
	if (alberoprocDyn2modellits != null) {
	    if (!alberoprocDyn2modellits.isEmpty()) {
		for (AlberoprocDyn2modellit alberoprocDyn2modellit : alberoprocDyn2modellits) {
		    alberoprocDyn2modellitService.delete(alberoprocDyn2modellit);
		}
		entity.setAlberoprocDyn2modellits(null);
	    }
	}
	Set<AlberoprocModelli> alberoprocModellis = entity.getAlberoprocModellis();
	if (alberoprocModellis != null) {
	    if (!alberoprocModellis.isEmpty()) {
		for (AlberoprocModelli alberoprocModelli : alberoprocModellis) {
		    alberoprocModelliService.delete(alberoprocModelli);
		}
		entity.setAlberoprocModellis(null);
	    }
	}
	Set<StpEndoTipo2> stpEndoTipo2s = entity.getStpEndoTipo2s();
	if (stpEndoTipo2s != null) {
	    if (!stpEndoTipo2s.isEmpty()) {
		for (StpEndoTipo2 stpEndoTipo2 : stpEndoTipo2s) {
		    stpEndoTipo2Service.delete(stpEndoTipo2);
		}
		entity.setStpEndoTipo2s(null);
	    }
	}
	Set<AlberoprocAteco> alberoprocAtecos = entity.getAlberoprocAtecos();
	if (alberoprocAtecos != null) {
	    if (!alberoprocAtecos.isEmpty()) {
		for (AlberoprocAteco alberoprocAteco : alberoprocAtecos) {
		    alberoprocAtecoService.delete(alberoprocAteco);
		}
		entity.setAlberoprocAtecos(null);
	    }
	}
	Set<AlberoprocComuniEsclusi> comuniEsclusi = entity.getComuniEsclusi();
	if (comuniEsclusi != null) {
	    if (!comuniEsclusi.isEmpty()) {
		for (AlberoprocComuniEsclusi comuneEscluso : comuniEsclusi) {
		    this.alberoprocComuniEsclusiService.delete(comuneEscluso);
		}
		entity.setComuniEsclusi(null);
	    }
	}
	if (deleteChilds) {
	    List<Alberoproc> figli = this.findAlberoprocFigli(entity.getId().getIdcomune(), entity.getScCodice(), false, DAOOrderTypeEnum.DESC,
		    false);
	    if (figli.size() > 0) {
		Integer id = entity.getId().getCodice();
		for (Alberoproc figlio : figli) {
		    if (id.compareTo(figlio.getId().getCodice()) != 0) {
			this.deleteInternal(figlio, false);
		    }
		}
	    }
	}
	// Spostato in fondo per permette la cancellazione ricorsiva a partire dal nodo principale 
	Set<AlberoprocOneri> alberoprocOneris = entity.getAlberoprocOneris();
	if (alberoprocOneris != null) {
	    if (!alberoprocOneris.isEmpty()) {
		for (AlberoprocOneri alberoprocOneri : alberoprocOneris) {
		    alberoprocOneriService.delete(alberoprocOneri);
		}
		entity.setAlberoprocOneris(null);
	    }
	}
	Set<AlberoprocRuoli> alberoprocRuolis = entity.getAlberoprocRuolis();
	if (alberoprocRuolis != null) {
	    if (!alberoprocRuolis.isEmpty()) {
		for (AlberoprocRuoli alberoprocRuoli : alberoprocRuolis) {
		    alberoprocRuoliService.delete(alberoprocRuoli);
		}
		entity.setAlberoprocRuolis(null);
	    }
	}
	Set<AlberoCausali> alberoCausalis = entity.getAlberoCausalis();
	if (alberoCausalis != null) {
	    if (!alberoCausalis.isEmpty()) {
		for (AlberoCausali alberoCausali : alberoCausalis) {
		    alberoCausaliService.delete(alberoCausali);
		}
		entity.setAlberoCausalis(null);
	    }
	}
	Set<AlberoprocArendo> alberoprocArendos = entity.getAlberoprocArendos();
	if (alberoprocArendos != null) {
	    if (!alberoprocArendos.isEmpty()) {
		for (AlberoprocArendo alberoprocArendo : alberoprocArendos) {
		    alberoprocArendoService.delete(alberoprocArendo);
		}
		entity.setAlberoprocArendos(null);
	    }
	}
	List<AlberoprocTipisoggetto> alberoprocTipisoggettos = alberoprocTipisoggettoService.findByAlberoprocId(entity.getId().getIdcomune(),
		entity.getId().getCodice(), null, null);
	if (alberoprocTipisoggettos != null) {
	    if (!alberoprocTipisoggettos.isEmpty()) {
		for (AlberoprocTipisoggetto alberoprocTipisoggetto : alberoprocTipisoggettos) {
		    alberoprocTipisoggettoService.delete(alberoprocTipisoggetto);
		}
	    }
	}
    }

    @Override
    public List<Alberoproc> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Alberoproc findById(PkId id) {

	return alberoprocDAO.findById(id);
    }

    @Override
    public void insert(Alberoproc entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    entity.setScStatoControllo("I");
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggettoWorkflowAreaRis", true, entity.getId());
	    alberoprocDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    updateAlberoprocCache();
	}
    }

    @Override
    protected boolean validateEntity(Alberoproc entity) {

	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doBusinessValidation = true;
	if (validationRule != null) {
	    doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	}
	if (doBusinessValidation) {
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    if (!EntityUtils.isNestedPropertyBlank(entity, "foArjStepsTestata.id.codice")) {
		if (log.isDebugEnabled()) {
		    log.debug("validateEntity# valido la testata del workflow: {}", entity.getFoArjStepsTestata().getId().getCodice());
		}
		String stepIntervento = StepsEnum.INTERVENTO.name();
		List<FoArjSteps> arjSteps = foArjStepsService.findByTestataAndNomeStepBase(entity.getFoArjStepsTestata().getId().getIdcomune(),
			entity.getFoArjStepsTestata().getId().getCodice(), stepIntervento);
		if (!arjSteps.isEmpty()) {
		    InvalidValue iv = new InvalidValue("service_error.alberoproc.foArjStepsTestata_step_con_intervento", entity.getClass(),
			    "foArjStepsTestata", null, entity);
		    ivs.add(iv);
		}
	    }
	    if (ivs.size() > 0) {
		throwValidationMessages(ivs);
	    }
	}
	// VALIDO L'OGGETTO DI DOMINIO
	return super.validateEntity(entity);
    }

    @Override
    public void updateAlberoproc(Alberoproc alberoproc, StpEndoTipo2 stpEndoTipo2Dto) {

	validateEndoTipo2(stpEndoTipo2Dto);
	this.update(alberoproc);
	gestisciStpEndo2(alberoproc, stpEndoTipo2Dto);
    }

    private void gestisciStpEndo2(Alberoproc alberoproc, StpEndoTipo2 stpEndoTipo2Dto) {

	//	if (stpEndoTipo2Dto != null) {
	//	    StpEndoTipo2 s2 = new StpEndoTipo2();
	//	    boolean isInsert = true;
	//	    if (stpEndoTipo2Dto.getId() != null) {
	//		if (stpEndoTipo2Dto.getId().getCodice() != null) {
	//		    isInsert = false;
	//		    s2 = stpEndoTipo2Service.findById(new PkId(alberoproc.getId().getIdcomune(), stpEndoTipo2Dto.getId().getCodice()));
	//		}
	//	    }
	//	    s2.setAlberoproc(alberoproc);
	//	    s2.setCodiceEndoRegionale(stpEndoTipo2Dto.getCodiceEndoRegionale());
	//	    s2.setTipo(stpEndoTipo2Dto.getTipo());
	//	    s2.setCodiceStp(stpEndoTipo2Dto.getCodiceStp());
	//	    s2.setStpTipologieEndo2(stpEndoTipo2Dto.getStpTipologieEndo2());
	//	    if (isInsert) {
	//		stpEndoTipo2Service.insert(s2);
	//	    } else {
	//		stpEndoTipo2Service.update(s2);
	//	    }
	//	}
    }

    @Override
    public void update(Alberoproc entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    entity.setScStatoControllo("M");
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggettoWorkflowAreaRis", true, entity.getId());
	    alberoprocDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    updateAlberoprocCache();
	}
    }

    private void dataIntegration(Alberoproc entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro alberoproc passato è nullo");
	}
	if (entity.getControllamq() == null) {
	    entity.setControllamq(Boolean.FALSE);
	}
	if (entity.getFlagescludisorteggio() == null) {
	    entity.setFlagescludisorteggio(Boolean.FALSE);
	}
	if (entity.getFlagReplicaistanze() == null) {
	    entity.setFlagReplicaistanze(Boolean.FALSE);
	}
	if (entity.getScAttivo() == null) {
	    entity.setScAttivo(Boolean.FALSE);
	}
	if (entity.getScOrdine() == null) {
	    entity.setScOrdine(new Integer(0));
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Alberoproc entity) {

	Tipologiaregistri tipologiaregistro = tipologiaregistriService.bindDomainObject(entity.getTipologiaregistro(), PkId.class, "id.codice");
	entity.setTipologiaregistro(tipologiaregistro);
	Tipiprocedure tipoProcedura = tipiprocedureService.bindDomainObject(entity.getTipoProcedura(), PkId.class, "id.codice");
	entity.setTipoProcedura(tipoProcedura);
	Mercati mercato = mercatiService.bindDomainObject(entity.getMercato(), PkId.class, "id.codice");
	entity.setMercato(mercato);
	MercatiUso mercatoUso = mercatiUsoService.bindDomainObject(entity.getMercatoUso(), PkId.class, "id.codice");
	entity.setMercatoUso(mercatoUso);
	Responsabili responsabile = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabile);
	Responsabili respistruttoria = responsabiliService.bindDomainObject(entity.getRespistruttoria(), PkId.class, "id.codice");
	entity.setRespistruttoria(respistruttoria);
	Responsabili operatoreSc = responsabiliService.bindDomainObject(entity.getOperatoreStc(), PkId.class, "id.codice");
	entity.setOperatoreStc(operatoreSc);
	Azioni azione = azioniService.bindDomainObject(entity.getAzione(), String.class, "azId");
	entity.setAzione(azione);
	Oggetti oggetti = oggettiService.bindDomainObject(entity.getOggettoWorkflowAreaRis(), PkId.class, "id.codice");
	entity.setOggettoWorkflowAreaRis(oggetti);
	RiTipiintervento ritipi = riTipiinterventoService.bindDomainObject(entity.getRiTipiintervento(), String.class, "codice");
	entity.setRiTipiintervento(ritipi);
    }

    @Override
    protected Class<Alberoproc> getEntityClass() {

	return Alberoproc.class;
    }

    @Override
    public List<Alberoproc> findByCriteria(DetachedCriteria criteria) {

	return alberoprocDAO.findByCriteria(criteria);
    }

    // METODI AGGIUNTI PER L'INTEGRAZIONE CON SIMO.
    @Override
    public Tipiprocedure getProcedura(Integer codiceProcedimento) {

	PkId id = new PkId(ORMHelper.getIdcomune(), codiceProcedimento);
	Tipiprocedure tipiprocedure = new Tipiprocedure();
	// il primo procedimento e vedo se ha un aprocedura configurata.
	// se no risalgo l'albero per vedere se ne trovo uno altrimenti questo è il procedimento
	// cercato
	Alberoproc alberoproc = this.findById(id);
	// serve per il controllo se un esiste un procedimento con quel codice
	if (alberoproc != null) {
	    // serve per farlo entrare la prima volta nel while
	    String scCodice = alberoproc.getScCodice();
	    // se non lo trovo risalgo l'albero fino ad arrivare alla radice (lung di sc_codice=2)
	    // se non trovo un procedimento con un aprocudura collegata sull'labero non è possibile
	    // trovare i movimenti per quella procedura.
	    if (alberoproc.getTipoProcedura() == null) {
		scCodice = alberoproc.getScCodice().subSequence(0, alberoproc.getScCodice().length() - 2).toString();
		while (scCodice.length() >= 2) {
		    DetachedCriteria criteria = getCriteriaAlberoProc(alberoproc, scCodice);
		    List<Alberoproc> listalberoproc = this.findByCriteria(criteria);
		    // verificare se è giusto
		    if (listalberoproc.size() > 0) {
			alberoproc = listalberoproc.get(0);
		    } else {
			tipiprocedure = new Tipiprocedure();
			break;
		    }
		    // appena trova una procedura collegata deve uscire dal while
		    if (alberoproc.getTipoProcedura() != null && alberoproc.getTipoProcedura().getId().getCodice() != null) {
			break;
		    }
		    // toglie dal codice gli ultimi due caratteri
		    scCodice = alberoproc.getScCodice().subSequence(0, alberoproc.getScCodice().length() - 1).toString();
		}
	    }
	    // controlla che al procedimento sia stata trovata un aprocedura collegata
	    if (alberoproc.getTipoProcedura() != null && alberoproc.getTipoProcedura().getId().getCodice() != null) {
		tipiprocedure = alberoproc.getTipoProcedura();
		// se non la trova restituisce un oggetto tipoprocedura vuoto
	    } else {
		tipiprocedure = new Tipiprocedure();
	    }
	    // se non esiste un procedimento con quel codice mi comporto come se non ci fosse una
	    // procedura collegata.
	} else {
	    tipiprocedure = new Tipiprocedure();
	}
	return tipiprocedure;
    }

    private DetachedCriteria getCriteriaAlberoProc(Alberoproc alberoproc, String scCodice) {

	DetachedCriteria criteria = DetachedCriteria.forClass(Alberoproc.class);
	criteria.add(Restrictions.eq("scCodice", scCodice));
	criteria.add(Restrictions.eq("software", alberoproc.getSoftware()));
	criteria.add(Restrictions.eq("id.idcomune", alberoproc.getId().getIdcomune()));
	return criteria;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void updateAlberoprocCache() {

	if (cacheManager != null) {
	    AlberoprocBusinessRules rules = (AlberoprocBusinessRules) SigeproBusinessRules.getClassRules(AlberoprocBusinessRules.class);
	    boolean eseguiOperazioniSuCache = rules.getCustomRule(AlberoprocBusinessRules.CustomRuleEnum.eseguiOperazioniSuCache.name());
	    if (eseguiOperazioniSuCache) {
		Cache cache = cacheManager.getCache(WebConstants.CACHE_ALBEROPROC_KEY);
		String chiaveAlberoprocInCachePrefix = getAlberoprocCachePrefix(null);
		List keys = cache.getKeys();
		for (Object key : keys) {
		    String k = (String) key;
		    if (k.startsWith(chiaveAlberoprocInCachePrefix)) {
			cache.remove(key);
		    }
		}
		cache.flush();
	    }
	}
    }

    private String getAlberoprocCachePrefix(Integer rootCodiceAlbero) {

	String key = ORMHelper.getIdcomunebase() + "_" + ORMHelper.getSoftware() + "_";
	if (rootCodiceAlbero != null) {
	    key += String.valueOf(rootCodiceAlbero) + "_";
	}
	return key;
    }

    @Override
    public Alberoproc findByScCodice(String idcomune, String sccodice) {

	return alberoprocDAO.findByScCodice(idcomune, sccodice);
    }

    @Override
    public void saveRuoli(Alberoproc alberoproc, Set<AlberoprocRuoli> alberoprocRuolis) {

	Alberoproc temp = this.findById(alberoproc.getId());
	Set<AlberoprocRuoli> tempAlberoRuolis = temp.getAlberoprocRuolis();
	for (AlberoprocRuoli alberoprocRuoli : tempAlberoRuolis) {
	    boolean trovato = false;
	    for (AlberoprocRuoli tempRuoli : alberoprocRuolis) {
		if (alberoprocRuoli.getId().getFkIdruolo().intValue() == tempRuoli.getId().getFkIdruolo().intValue()) {
		    trovato = true;
		    break;
		}
	    }
	    if (!trovato) {
		alberoprocRuoliService.delete(alberoprocRuoli);
	    }
	}
	alberoproc.setAlberoprocRuolis(alberoprocRuolis);
	this.update(alberoproc);
    }

    @Override
    public AlberoprocCommand findAlberoprocFigli(String idcomune, String scCodice) {

	List<AlberoprocCommand> alberoprocCommands = this.findAlberoprocHierarchy(idcomune, null);
	for (AlberoprocCommand alberoprocCommand : alberoprocCommands) {
	    if (alberoprocCommand.getId().intValue() != 0) {
		Alberoproc alberoproc = this.findById(new PkId(alberoprocCommand.getId()));
		String scCodiceCfr = (String) EntityUtils.getNestedProperty(alberoproc, "scCodice");
		if (StringUtils.isBlank(scCodiceCfr)) {
		    throw new RuntimeException("Trovato alberoproc nullo");
		}
		if (scCodiceCfr.equals(scCodice)) {
		    return alberoprocCommand;
		}
	    }
	}
	return null;
    }

    public boolean findSeDisabilitato(String idcomune, String scCodice) {

	boolean isDisabilitato = false;
	Alberoproc nodo = this.findByScCodice(idcomune, scCodice);
	if (BooleanUtils.isTrue(nodo.getScAttivo())) {
	    isDisabilitato = true;
	} else {
	    List<Alberoproc> padri = new ArrayList<Alberoproc>();
	    this.findAlberoprocPadri(padri, idcomune, scCodice);
	    for (Alberoproc alberoproc : padri) {
		if (BooleanUtils.isTrue(alberoproc.getScAttivo())) {
		    isDisabilitato = true;
		    break;
		}
	    }
	}
	return isDisabilitato;
    }

    /**
     * metodo per il recupero di tutti i padri di un determinato nodo
     * 
     * @param padri
     * @param scCodice
     */
    private void findAlberoprocPadri(List<Alberoproc> padri, String idcomune, String scCodice) {

	if (StringUtils.isNotBlank(scCodice)) {
	    Alberoproc padre = this.findByScCodice(idcomune, scCodice);
	    padri.add(padre);
	    if (scCodice.length() > 2) {
		scCodice = scCodice.substring(0, scCodice.length() - 2);
		this.findAlberoprocPadri(padri, idcomune, scCodice);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoprocCommand> findAlberoprocHierarchy(String idcomune, Integer rootCodiceAlbero) {

	List<AlberoprocCommand> resultList = new ArrayList<AlberoprocCommand>();
	String chiaveAlberoprocInCache = getAlberoprocCachePrefix(rootCodiceAlbero);
	if (cacheManager != null) {
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_ALBEROPROC_KEY);
	    Element obj = cache.get(chiaveAlberoprocInCache);
	    if (obj != null) {
		resultList = (List<AlberoprocCommand>) obj.getObjectValue();
	    } else {
		resultList = populateAlberoprocHierarchy(idcomune, rootCodiceAlbero);
		Element element = new Element(chiaveAlberoprocInCache, resultList);
		cache.put(element);
		cache.flush();
	    }
	} else {
	    resultList = populateAlberoprocHierarchy(idcomune, rootCodiceAlbero);
	}
	return resultList;
    }

    /**
     */
    private List<AlberoprocCommand> populateAlberoprocHierarchy(String idcomune, Integer rootCodiceAlbero) {

	List<AlberoprocCommand> resultList = new ArrayList<AlberoprocCommand>();
	resultList = alberoprocDAO.findAlberoprocCommand(idcomune, rootCodiceAlbero);
	boolean firstRound = true;
	for (AlberoprocCommand alberoprocCommand : resultList) {
	    alberoprocCommand.setDisabilitato(String.valueOf(BooleanUtils.toBoolean(alberoprocCommand.getScAttivo())));
	    alberoprocCommand.setChildren(inspectHierarchy(resultList, alberoprocCommand.getCodice()));
	    if (alberoprocCommand.getCodice().length() == 2) {
		alberoprocCommand.setRoot(1);
	    } else {
		if (rootCodiceAlbero != null && firstRound) {
		    firstRound = false;
		    alberoprocCommand.setRoot(1);
		} else {
		    alberoprocCommand.setRoot(0);
		}
	    }
	}
	return resultList;
    }

    private List<AlberoprocChildrenCommand> inspectHierarchy(List<AlberoprocCommand> alberoprocs, String scCodice) {

	int length = scCodice.length();
	String alberoprocScCodice = "";
	List<AlberoprocChildrenCommand> childrens = new ArrayList<AlberoprocChildrenCommand>();
	for (AlberoprocCommand alberoprocCommand : alberoprocs) {
	    alberoprocScCodice = alberoprocCommand.getCodice();
	    if (alberoprocScCodice.startsWith(scCodice) && alberoprocScCodice.length() == (length + 2)) {
		AlberoprocChildrenCommand alberoprocChildrenCommand = new AlberoprocChildrenCommand();
		alberoprocChildrenCommand.set_reference(alberoprocCommand.getId());
		childrens.add(alberoprocChildrenCommand);
		// inspectHierarchy(alberoprocs, alberoprocScCodice);
	    }
	}
	return childrens.size() > 0 ? childrens : null;
    }

    //    private List<AlberoprocChildrenCommand> inspectHierarchy(List<Alberoproc> alberoprocs, String scCodice) {
    //
    //	int length = scCodice.length();
    //	String alberoprocScCodice = "";
    //	List<AlberoprocChildrenCommand> childrens = new ArrayList<AlberoprocChildrenCommand>();
    //	for (Alberoproc alberoproc : alberoprocs) {
    //	    alberoprocScCodice = alberoproc.getScCodice();
    //	    if (alberoprocScCodice.startsWith(scCodice) && alberoprocScCodice.length() == (length + 2)) {
    //		AlberoprocChildrenCommand alberoprocChildrenCommand = new AlberoprocChildrenCommand();
    //		alberoprocChildrenCommand.set_reference(alberoproc.getId().getCodice());
    //		childrens.add(alberoprocChildrenCommand);
    //		// inspectHierarchy(alberoprocs, alberoprocScCodice);
    //	    }
    //	}
    //	return childrens.size() > 0 ? childrens : null;
    //    }
    @Override
    public List<Alberoproc> findRootsAlberoProc() {

	List<Alberoproc> alberoprocs = alberoprocDAO.findAll(null, null);
	List<Alberoproc> roots = new ArrayList<Alberoproc>();
	for (Alberoproc alberoproc : alberoprocs) {
	    if (alberoproc.getScCodice().length() == 2) {
		roots.add(alberoproc);
	    }
	}
	return roots;
    }

    @Override
    public String findDescrizionePrimaVoceAlberoproc(String idcomune, String scCodice) {

	return alberoprocDAO.findDescrizionePrimaVoceAlberoproc(idcomune, scCodice);
    }

    protected boolean isDeleteAllowed(Alberoproc entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public void insertAlberoproc(Alberoproc alberoproc, Alberoproc alberoprocPadre, StpEndoTipo2 stpEndoTipo2) {

	if (alberoproc.getScOrdine() == null) {
	    alberoproc.setScOrdine(new Integer(0));
	}
	validateEndoTipo2(stpEndoTipo2);
	if (validateEntity(alberoproc)) {
	    boolean areaprimaria = alberoproc.getAreaPrimaria();
	    String scCodiceFiglio = "";
	    if (!areaprimaria) {
		if (alberoprocPadre == null) {
		    areaprimaria = true;
		}
	    }
	    if (areaprimaria) {
		List<Alberoproc> alberoprocs = this.findRootsAlberoProc();
		List<String> scCodiciList = new ArrayList<String>();
		for (Alberoproc temp : alberoprocs) {
		    scCodiciList.add(temp.getScCodice());
		}
		if (!scCodiciList.isEmpty()) {
		    Collections.sort(scCodiciList);
		    String sccodiceFiglioLast = scCodiciList.get(scCodiciList.size() - 1);
		    Integer scCodiceFiglio_int = null;
		    if (sccodiceFiglioLast.startsWith("0")) {
			sccodiceFiglioLast = sccodiceFiglioLast.substring(1, 2);
			scCodiceFiglio_int = Integer.parseInt(sccodiceFiglioLast) + 1;
			scCodiceFiglio = scCodiceFiglio_int.toString();
			if (scCodiceFiglio.length() == 1) {
			    scCodiceFiglio = "0" + scCodiceFiglio;
			}
		    } else {
			scCodiceFiglio_int = Integer.parseInt(sccodiceFiglioLast) + 1;
			scCodiceFiglio = scCodiceFiglio_int.toString();
		    }
		} else {
		    scCodiceFiglio = "01";
		}
		/*
		 * Setto l'alberoproc come cartella
		 */
		// BOCCI/CHIOCCI 2011-11-10 UNA VOCE COME AREA PRIMARIA NON DEVE ESSERE NECESSARIAMENTE PADRE
		// alberoproc.setScPadre(true);
		alberoproc.setScPadre(Boolean.FALSE);
	    } else {
		/*
		 * Recupero sc_codice del figlio che inserisco
		 */
		String sc_codice_padre = alberoprocPadre.getScCodice();
		List<Alberoproc> figli = this.findAlberoprocFigli(alberoprocPadre.getId().getIdcomune(), sc_codice_padre, true, DAOOrderTypeEnum.ASC,
			Boolean.TRUE);
		if (figli.size() > 0) {
		    for (Alberoproc alberoproc2 : figli) {
			scCodiceFiglio = alberoproc2.getScCodice();
		    }
		    scCodiceFiglio = calcolaProssimoCodice(scCodiceFiglio);
		} else {
		    scCodiceFiglio = sc_codice_padre + "01";
		}
		/*
		 * Aggiorno il padre
		 */
		alberoprocPadre.setScPadre(true);
		this.update(alberoprocPadre);
		/*
		 * Setto l'alberoproc come foglia
		 */
		alberoproc.setScPadre(false);
	    }
	    alberoproc.setScCodice(scCodiceFiglio);
	    this.insert(alberoproc);
	    stpEndoTipo2.setFlagRegionale(Boolean.TRUE);
	    stpEndoTipo2.setAlberoproc(alberoproc);
	    stpEndoTipo2Service.insert(stpEndoTipo2);
	}
	updateAlberoprocCache();
    }

    private void validateEndoTipo2(StpEndoTipo2 stpEndoTipo2) {

	//	if (null == stpEndoTipo2) {
	//	    throw new BusinessValidationException("Attenzione! non sono stati passati i parametri CART");
	//	}
	//	if (StringUtils.isBlank(stpEndoTipo2.getCodiceEndoRegionale()) || StringUtils.isBlank(stpEndoTipo2.getTipo())) {
	//	    throw new BusinessValidationException("Attenzione! non sono stati passati i parametri CART");
	//	}
	//	if (StringUtils.defaultString(stpEndoTipo2.getTipo()).equalsIgnoreCase("ENDO")) {
	//	    if (stpEndoTipo2.getStpTipologieEndo2() == null || stpEndoTipo2.getStpTipologieEndo2().getId() == null
	//		    || stpEndoTipo2.getStpTipologieEndo2().getId().getCodice() == null) {
	//		throw new BusinessValidationException("Attenzione! Nei parametri CART è stato indicato ENDO ma non è stata impostata la tipologia");
	//	    }
	//	}
    }

    public String calcolaProssimoCodice(String scCodiceFiglio) {

	String temp = scCodiceFiglio.substring(scCodiceFiglio.length() - 2);
	Integer codice = Integer.parseInt(temp);
	codice++;
	if (codice < 10) {
	    temp = "0".concat(String.valueOf(codice));
	} else {
	    temp = String.valueOf(codice);
	}
	return scCodiceFiglio.substring(0, scCodiceFiglio.length() - 2).concat(temp);
    }

    @Override
    public String findProgressivo(Alberoproc alberoproc) {

	String progressivo = "";
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction progressivoNotNull = new FilterRestriction();
	progressivoNotNull.addFilterField(FilterUtils.isNotNull("progressivoistanze"));
	ft.addRestriction(progressivoNotNull);
	if (StringUtils.isNotBlank(alberoproc.getScCodice()) || alberoproc.getScCodice().length() > 2) {
	    FilterRestriction padri = new FilterRestriction();
	    padri.setAndOrRestriction(AndOrRestriction.OR);
	    String scCodice = alberoproc.getScCodice();
	    for (int i = 0; i < scCodice.length(); i += 2) {
		padri.addFilterField(FilterUtils.equals("scCodice", scCodice.substring(0, (scCodice.length() - i)), String.class));
	    }
	    ft.addRestriction(padri);
	}
	ft.addOrder(FilterUtils.orderDesc("scCodice"));
	List<Alberoproc> list = alberoprocDAO.findByFilterTable(ft);
	if (list.size() > 0) {
	    for (Alberoproc alberoproc2 : list) {
		progressivo = alberoproc2.getProgressivoistanze();
		break;
	    }
	}
	return progressivo;
    }

    @Override
    public Object findParametroprotocollo(Alberoproc alberoproc, String propertyName, String codiceComune) {

	Object result = null;
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(alberoproc.getScCodice()) || alberoproc.getScCodice().length() > 2) {
	    FilterRestriction padri = new FilterRestriction();
	    String scCodice = alberoproc.getScCodice();
	    List<String> codiciPadre = new ArrayList<String>();
	    for (int i = 0; i < scCodice.length(); i += 2) {
		codiciPadre.add(scCodice.substring(0, (scCodice.length() - i)));
	    }
	    padri.addFilterField(FilterUtils.in("scCodice", codiciPadre.toArray(), String.class));
	    ft.addRestriction(padri);
	}
	ft.addOrder(FilterUtils.orderDesc("scCodice"));
	List<Alberoproc> list = alberoprocDAO.findByFilterTable(ft);
	if (list.size() > 0) {
	    for (Alberoproc alberoproc2 : list) {
		result = alberoprocProtocolloService.findProprietaByAlberoprocId(alberoproc2.getId().getCodice(), propertyName, codiceComune);
		// result = ReflectionUtils.getField(field, alberoproc2);
		if (result != null) {
		    // BOCCI AGGIUNTO QUESTO CONTROLLO PERCHE' IN MYSQL LE STRINGHE VUOTE VENGONO SALVATE COME '' E NON
		    // COME NULL E LA RICERCA DEI PARAMETRI FALLISCE PERCHE' RITORNA IL PRIMO VALORE NON NULLO
		    // CON LA MODIFICA VIENE PRESO IL PRIMO VALORE NON NULLO E NON VUOTO
		    if (result instanceof String) {
			if (StringUtils.isNotBlank((String) result)) {
			    break;
			}
		    } else {
			break;
		    }
		}
	    }
	}
	return result;
    }

    @Override
    public AlberoprocHelper findAlberoprocHelper(Alberoproc alberoproc, String codiceComuneDiCompetenza) {

	alberoproc = findById(new PkId(alberoproc.getId().getIdcomune(), alberoproc.getId().getCodice()));
	AlberoprocHelper alberoprocHelper = new AlberoprocHelper();
	List<CodiceDescrizioneBean> descrizionis = new ArrayList<CodiceDescrizioneBean>();
	alberoprocHelper.setCurrentAlberoproc(alberoproc);
	String scCodice = alberoproc.getScCodice();
	int lengthCodice = scCodice.length();
	int lengthTree = lengthCodice / 2;
	if (EntityUtils.getNestedProperty(alberoproc.getAzione(), "azId") != null) {
	    Azioni azione = azioniService.bindDomainObject(alberoproc.getAzione(), Integer.class, "azId");
	    alberoprocHelper.setAzione(azione);
	}
	String progressivoIstanze = "";
	if (StringUtils.isNotBlank(alberoproc.getProgressivoistanze())) {
	    progressivoIstanze = alberoproc.getProgressivoistanze();
	    alberoprocHelper.setProgressivoistanze(progressivoIstanze);
	}
	if (EntityUtils.getNestedProperty(alberoproc.getResponsabile(), "id.codice") != null) {
	    Responsabili responsabile = responsabiliService.findById(new PkId(alberoproc.getResponsabile().getId().getCodice()));
	    alberoprocHelper.setResponsabile(responsabile);
	}
	if (EntityUtils.getNestedProperty(alberoproc.getRespistruttoria(), "id.codice") != null) {
	    Responsabili responsabile = responsabiliService.findById(new PkId(alberoproc.getRespistruttoria().getId().getCodice()));
	    alberoprocHelper.setRespistruttoria(responsabile);
	}
	if (EntityUtils.getNestedProperty(alberoproc.getOperatoreStc(), "id.codice") != null) {
	    Responsabili responsabile = responsabiliService.findById(new PkId(alberoproc.getOperatoreStc().getId().getCodice()));
	    alberoprocHelper.setOperatoreStc(responsabile);
	}
	if (EntityUtils.getNestedProperty(alberoproc.getTipoProcedura(), "id.codice") != null) {
	    Tipiprocedure procedura = tipiprocedureService.findById(new PkId(alberoproc.getTipoProcedura().getId().getCodice()));
	    alberoprocHelper.setTipoProcedura(procedura);
	    //	    if (procedura.getTipiProcedureavvios().size() > 0) {
	    //		for (Tipiprocedureavvio avvio : procedura.getTipiProcedureavvios()) {
	    //		    if (BooleanUtils.isTrue(avvio.getDefaultsn())) {
	    //			alberoprocHelper.setMovAvvio(avvio.getTipoMovimento());
	    //			break;
	    //		    }
	    //		}
	    //	    }
	}
	Set<AlberoprocLeggi> alberoprocLeggis = new HashSet<AlberoprocLeggi>(0);
	Set<AlberoprocDocumenti> alberoprocDocumentis = new HashSet<AlberoprocDocumenti>(0);
	Set<AlberoprocEndo> alberoprocEndos = new HashSet<AlberoprocEndo>();
	Set<AlberoprocDyn2modellit> alberoprocDyn2modellits = new HashSet<AlberoprocDyn2modellit>();
	Set<AlberoprocRuoli> alberoprocRuolis = new HashSet<AlberoprocRuoli>();
	Set<AlberoprocOneri> alberoprocOneris = new HashSet<AlberoprocOneri>();
	Set<AlberoprocAteco> alberoprocAtecos = new HashSet<AlberoprocAteco>();
	Set<AlberoprocTipisoggetto> alberoprocTipisoggettos = new HashSet<AlberoprocTipisoggetto>();
	Set<AlberoprocArendo> alberoprocArendos = new HashSet<AlberoprocArendo>();
	List<String> listaScNote = new ArrayList<String>();
	List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = new ArrayList<PercorsoAlberoprocHelper>();
	boolean arEndoRegionaliRecuperati = false;
	boolean arEndoPerComuneRecuperati = false;
	boolean arEndoPerTuttiComuniRecuperati = false;
	Boolean presentabileOnline = false;
	for (int i = 0; i < lengthTree; i++) {
	    scCodice = scCodice.substring(0, lengthCodice);
	    Alberoproc alberoprocTemp = alberoprocDAO.findByScCodice(alberoproc.getId().getIdcomune(), scCodice);
	    if (alberoprocTemp != null) {
		if (alberoprocTemp.getAlberoprocLeggis() != null && !alberoprocTemp.getAlberoprocLeggis().isEmpty()) {
		    alberoprocLeggis.addAll(alberoprocTemp.getAlberoprocLeggis());
		}
		if (alberoprocTemp.getAlberoprocDocumentis() != null && !alberoprocTemp.getAlberoprocDocumentis().isEmpty()) {
		    alberoprocDocumentis.addAll(alberoprocTemp.getAlberoprocDocumentis());
		}
		if (alberoprocTemp.getAlberoprocEndos() != null && !alberoprocTemp.getAlberoprocEndos().isEmpty()) {
		    List<AlberoprocEndo> endos = alberoprocEndoService.findAllByAlberoproc(alberoproc.getId().getIdcomune(),
			    alberoprocTemp.getId().getCodice(), true);
		    alberoprocEndos.addAll(endos);
		}
		if (alberoprocTemp.getAlberoprocDyn2modellits() != null && !alberoprocTemp.getAlberoprocDyn2modellits().isEmpty()) {
		    alberoprocDyn2modellits.addAll(alberoprocTemp.getAlberoprocDyn2modellits());
		}
		if (alberoprocTemp.getAlberoprocRuolis() != null && !alberoprocTemp.getAlberoprocRuolis().isEmpty()) {
		    alberoprocRuolis.addAll(alberoprocTemp.getAlberoprocRuolis());
		}
		if (alberoprocTemp.getAlberoprocOneris() != null && !alberoprocTemp.getAlberoprocOneris().isEmpty()) {
		    List<AlberoprocOneri> oneris = alberoprocOneriService.findAllByAlberoproc(alberoproc.getId().getIdcomune(),
			    alberoprocTemp.getId().getCodice());
		    for (AlberoprocOneri alberoprocOneri : oneris) {
			boolean onereDisabilitato = false;
			if (alberoprocOneri.getTipicausalioneri() != null) {
			    if (alberoprocOneri.getTipicausalioneri().getCoDisabilitato() != null) {
				onereDisabilitato = alberoprocOneri.getTipicausalioneri().getCoDisabilitato().booleanValue();
			    }
			}
			if (!onereDisabilitato) {
			    alberoprocOneris.add(alberoprocOneri);
			}
		    }
		}
		if (alberoprocTemp.getAlberoprocAtecos() != null && !alberoprocTemp.getAlberoprocAtecos().isEmpty()) {
		    alberoprocAtecos.addAll(alberoprocTemp.getAlberoprocAtecos());
		}
		List<AlberoprocTipisoggetto> alberoprocTipisoggettos2 = alberoprocTipisoggettoService
			.findByAlberoprocId(alberoproc.getId().getIdcomune(), alberoprocTemp.getId().getCodice(), null, null);
		if (!alberoprocTipisoggettos2.isEmpty()) {
		    alberoprocTipisoggettos.addAll(alberoprocTipisoggettos2);
		}
		if (!arEndoRegionaliRecuperati) {
		    List<AlberoprocArendo> arendosTTR = alberoprocArendoService.findByAlberoProc(alberoprocTemp.getId().getCodice(),
			    alberoproc.getId().getIdcomune(), alberoproc.getId().getIdcomune());
		    if (!arendosTTR.isEmpty()) {
			alberoprocArendos.addAll(arendosTTR);
			arEndoRegionaliRecuperati = true;
		    }
		}
		if (ORMHelper.isConsoleLocale()) {
		    if (!arEndoPerComuneRecuperati) {
			if (StringUtils.isNotBlank(codiceComuneDiCompetenza)) {
			    List<AlberoprocArendo> arendosLOC = alberoprocArendoService.findByAlberoProc(alberoprocTemp.getId().getCodice(),
				    alberoproc.getId().getIdcomune(), ORMHelper.getIdcomune());
			    if (!arendosLOC.isEmpty()) {
				// ciclo i record che valgono per lo specifico comune
				for (AlberoprocArendo ar : arendosLOC) {
				    List<Inventarioprocedimenti> s = inventarioprocedimentiService.findByAlberoprocArendo(ar);
				    for (Inventarioprocedimenti ip : s) {
					if (ip.getComune() != null && StringUtils.isNotBlank(ip.getComune().getCodicecomune())) {
					    if (codiceComuneDiCompetenza.equals(ip.getComune().getCodicecomune())) {
						alberoprocArendos.add(ar);
						arEndoPerComuneRecuperati = true;
						break;
					    }
					}
				    }
				}
			    }
			}
		    }
		    if (!arEndoPerTuttiComuniRecuperati) {
			List<AlberoprocArendo> arendosLOC = alberoprocArendoService.findByAlberoProc(alberoprocTemp.getId().getCodice(),
				alberoproc.getId().getIdcomune(), ORMHelper.getIdcomune());
			if (!arendosLOC.isEmpty()) {
			    // ciclo i record che valgono per lo specifico comune
			    for (AlberoprocArendo ar : arendosLOC) {
				List<Inventarioprocedimenti> s = inventarioprocedimentiService.findByAlberoprocArendo(ar);
				for (Inventarioprocedimenti ip : s) {
				    if (ip.getComune() == null) {
					alberoprocArendos.add(ar);
					arEndoPerTuttiComuniRecuperati = true;
					break;
				    }
				}
			    }
			}
		    }
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getAzione(), "azId") == null) {
		    if (EntityUtils.getNestedProperty(alberoprocTemp.getAzione(), "azId") != null) {
			Azioni azione = azioniService.bindDomainObject(alberoprocTemp.getAzione(), Integer.class, "azId");
			alberoprocHelper.setAzione(azione);
		    }
		}
		if (StringUtils.isBlank(progressivoIstanze)) {
		    if (StringUtils.isNotBlank(alberoprocTemp.getProgressivoistanze())) {
			progressivoIstanze = alberoprocTemp.getProgressivoistanze();
			alberoprocHelper.setProgressivoistanze(progressivoIstanze);
		    }
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getResponsabile(), "id.codice") == null) {
		    if (EntityUtils.getNestedProperty(alberoprocTemp.getResponsabile(), "id.codice") != null) {
			Responsabili responsabile = responsabiliService.findById(new PkId(alberoprocTemp.getResponsabile().getId().getCodice()));
			alberoprocHelper.setResponsabile(responsabile);
		    }
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getRespistruttoria(), "id.codice") == null) {
		    if (EntityUtils.getNestedProperty(alberoprocTemp.getRespistruttoria(), "id.codice") != null) {
			Responsabili responsabile = responsabiliService.findById(new PkId(alberoprocTemp.getRespistruttoria().getId().getCodice()));
			alberoprocHelper.setRespistruttoria(responsabile);
		    }
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getOperatoreStc(), "id.codice") == null) {
		    if (EntityUtils.getNestedProperty(alberoprocTemp.getOperatoreStc(), "id.codice") != null) {
			Responsabili responsabile = responsabiliService.findById(new PkId(alberoprocTemp.getOperatoreStc().getId().getCodice()));
			alberoprocHelper.setOperatoreStc(responsabile);
		    }
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getTipoProcedura(), "id.codice") == null) {
		    if (EntityUtils.getNestedProperty(alberoprocTemp.getTipoProcedura(), "id.codice") != null) {
			Tipiprocedure procedura = tipiprocedureService.findById(new PkId(alberoprocTemp.getTipoProcedura().getId().getCodice()));
			alberoprocHelper.setTipoProcedura(procedura);
		    }
		}
		// Recupera la tipologia del registro risalendo l'albero.
		if (EntityUtils.getNestedProperty(alberoprocHelper.getTipologiaregistro(), "id.codice") == null) {
		    if (EntityUtils.getNestedProperty(alberoprocTemp.getTipologiaregistro(), "id.codice") != null) {
			Tipologiaregistri tipologiaregistri = tipologiaregistriService
				.findById(new PkId(alberoprocTemp.getTipologiaregistro().getId().getCodice()));
			alberoprocHelper.setTipologiaregistro(tipologiaregistri);
		    }
		}
		if (StringUtils.isNotBlank(alberoprocTemp.getScNote())) {
		    listaScNote.add(alberoprocTemp.getScNote());
		}
		// Verifichiamo se nella catena dal figlio al padre esiste almeno una voce dell'albero che abbia
		// popolato con il valore 1 (Area riservata e frontoffice) o  2 (Solo Area Riservata) il campo "scPubblica"
		if (!presentabileOnline && alberoprocTemp.getScPubblica() != null
			&& (alberoprocTemp.getScPubblica().intValue() == 1 || alberoprocTemp.getScPubblica().intValue() == 2)) {
		    presentabileOnline = true;
		}
		// Popola un oggetto Helper che contiene il percorso della voce dell'albero passata
		PercorsoAlberoprocHelper percorsoAlberoprocHelper = new PercorsoAlberoprocHelper();
		percorsoAlberoprocHelper.setId(alberoprocTemp.getId().getCodice());
		percorsoAlberoprocHelper.setDescrizione(alberoprocTemp.getScDescrizione());
		percorsoAlberoprocHelpers.add(percorsoAlberoprocHelper);
	    }
	    lengthCodice = lengthCodice - 2;
	}
	scCodice = alberoproc.getScCodice();
	for (int i = 0; i <= scCodice.length(); i += 2) {
	    String scCodiceTemp = scCodice.substring(0, i);
	    Alberoproc alberoprocTemp = alberoprocDAO.findByScCodice(alberoproc.getId().getIdcomune(), scCodiceTemp);
	    if (alberoprocTemp != null) {
		CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
		cdb.setCodice(alberoprocTemp.getScDescrizione());
		cdb.setDescrizione(alberoprocTemp.getScNote());
		descrizionis.add(cdb);
	    }
	}
	alberoprocHelper.setDescrizionis(descrizionis);
	alberoprocHelper.setAlberoprocLeggis(alberoprocLeggis);
	alberoprocHelper.setAlberoprocDocumentis(alberoprocDocumentis);
	alberoprocHelper.setAlberoprocEndos(alberoprocEndos);
	alberoprocHelper.setAlberoprocDyn2modellits(alberoprocDyn2modellits);
	alberoprocHelper.setAlberoprocRuolis(alberoprocRuolis);
	alberoprocHelper.setAlberoprocOneris(alberoprocOneris);
	alberoprocHelper.setAlberoprocAtecos(alberoprocAtecos);
	alberoprocHelper.setAlberoprocTipisoggettos(alberoprocTipisoggettos);
	alberoprocHelper.setAlberoprocArendos(alberoprocArendos);
	alberoprocHelper.setListNote(listaScNote);
	alberoprocHelper.setPresentabileOnline(presentabileOnline);
	alberoprocHelper.setPercorsoAlberoprocHelpers(percorsoAlberoprocHelpers);
	Collections.reverse(listaScNote);
	Collections.reverse(percorsoAlberoprocHelpers);
	if (alberoprocHelper.getAzione() == null) {
	    // setto azione di default
	    Azioni azioneDefault = azioniService.findById(CODICE_AZIONE_DEFAULT);
	    alberoprocHelper.setAzione(azioneDefault);
	}
	if (EntityUtils.getNestedProperty(alberoprocHelper.getResponsabile(), "id.codice") == null) {
	    Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId(alberoproc.getSoftware().getCodice()));
	    if (configurazione != null) {
		if (EntityUtils.getNestedProperty(configurazione.getResponsabili(), "id.codice") != null) {
		    Responsabili responsabile = responsabiliService.findById(new PkId(configurazione.getResponsabili().getId().getCodice()));
		    alberoprocHelper.setResponsabile(responsabile);
		}
	    }
	}
	return alberoprocHelper;
    }

    @Override
    public void updateProgressivo(Alberoproc alberoproc, String progressivo) {

	if (EntityUtils.getNestedProperty(alberoproc, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro Alberoproc non può essere vuoto");
	}
	Assert.hasText(progressivo, "updateProgressivo: Il parametro progressivo non pùò essere vuoto");
	alberoproc = this.bindDomainObject(alberoproc, PkId.class, "id.codice");
	if (StringUtils.isNotBlank(alberoproc.getProgressivoistanze())) {
	    alberoproc.setProgressivoistanze(progressivo);
	    this.update(alberoproc);
	} else {
	    String scCodice = alberoproc.getScCodice();
	    int lengthCodice = scCodice.length();
	    int lengthTree = lengthCodice / 2;
	    for (int i = 0; i < lengthTree; i++) {
		scCodice = scCodice.substring(0, lengthCodice);
		Alberoproc alberoprocTemp = alberoprocDAO.findByScCodice(alberoproc.getId().getIdcomune(), scCodice);
		if (StringUtils.isNotBlank(alberoprocTemp.getProgressivoistanze())) {
		    alberoprocTemp.setProgressivoistanze(progressivo);
		    this.update(alberoprocTemp);
		    return;
		}
		lengthCodice = lengthCodice - 2;
	    }
	}
    }

    @Override
    public Alberoproc findByIdAndCurrentSoftware(String idcomune, Integer id, Boolean hideDisabled) {

	Assert.notNull(id, "findByIdAndCurrentSoftware: id alberoproc nullo!");
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", id, Integer.class));
	filterRestriction.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	filterRestriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	filterTable.addRestriction(filterRestriction);
	if (BooleanUtils.isTrue(hideDisabled)) {
	    FilterRestriction fr_scAttivo = new FilterRestriction();
	    fr_scAttivo.addFilterField(FilterUtils.equals("scAttivo", Boolean.FALSE, Boolean.class));
	    filterTable.addRestriction(fr_scAttivo);
	}
	List<Alberoproc> list = alberoprocDAO.findByFilterTable(filterTable);
	Alberoproc alberoproc = null;
	if (!list.isEmpty()) {
	    alberoproc = list.get(0);
	    if (BooleanUtils.isTrue(hideDisabled)) {
		boolean isDisabled = this.findSeDisabilitato(idcomune, alberoproc.getScCodice());
		if (isDisabled) {
		    alberoproc = null;
		}
	    }
	}
	return alberoproc;
    }

    @Override
    public List<Alberoproc> findAlberoprocFigli(String idcomune, String scCodicePadre, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    Boolean isPerCalcoloprogressivo) {

	return alberoprocDAO.findAlberoprocFigli(idcomune, scCodicePadre, soloPrimoLivello, tipoOrdinamento, isPerCalcoloprogressivo);
    }

    @Override
    public Azioni findAzioniDaEndoOAlberoproc(Alberoproc alberoproc, List<Integer> codiciInventarioList) {

	if (alberoproc == null) {
	    throw new IllegalArgumentException("Il parametro alberoproc non può essere nullo");
	}
	alberoproc = bindDomainObject(alberoproc, PkId.class, "id.codice");
	Azioni azione = null;
	Set<Integer> codiciInventarioSet = new HashSet<Integer>();
	for (Integer codiceinventario : codiciInventarioList) {
	    codiciInventarioSet.add(codiceinventario);
	}
	/**
	 * La funzione torna un oggetto azione a partire da una voce di albero scelta ed una lista di endoprocedimenti
	 * passati. Se lista di endo procedimenti non è vuota allora cerca l'endo principale per quella voce di albero e
	 * ne prende l'azione. Se non c'è un endo principale cicla tra quelli e se ne trova uno prende in ordine azione
	 * (+) poi (-). Se non ce ne sono altri prende l'azione di alberoproc risalendo la gerarchia. Nel caso non venga
	 * trovata l'azione viene restituita una di default con valore (=).
	 */
	List<AlberoprocEndo> endos = alberoprocEndoService.findEndoprocedimentiHierarchy(alberoproc);
	for (AlberoprocEndo alberoprocEndo : endos) {
	    if (codiciInventarioSet.contains(alberoprocEndo.getId().getCodiceinventario())) {
		// boolean isPrincipale = alberoprocEndo.getFlagPrincipale() == null ? false : alberoprocEndo.getFlagPrincipale().booleanValue();
		boolean isPrincipale = alberoprocEndoService.isPrincipale(alberoprocEndo.getInventarioprocedimento().getId().getIdcomune(),
			alberoprocEndo.getAlberoproc().getId().getCodice(), alberoprocEndo.getInventarioprocedimento().getId().getCodice());
		if (isPrincipale) {
		    if (alberoprocEndo.getAzione() != null) {
			azione = alberoprocEndo.getAzione();
			break;
		    }
		}
	    }
	}
	if (azione == null) {
	    for (AlberoprocEndo alberoprocEndo : endos) {
		if (codiciInventarioSet.contains(alberoprocEndo.getId().getCodiceinventario())) {
		    if (alberoprocEndo.getAzione() != null) {
			if (alberoprocEndo.getAzione().getAzAzione().equalsIgnoreCase("+")) {
			    azione = alberoprocEndo.getAzione();
			    break;
			}
		    }
		}
	    }
	}
	if (azione == null) {
	    for (AlberoprocEndo alberoprocEndo : endos) {
		if (codiciInventarioSet.contains(alberoprocEndo.getId().getCodiceinventario())) {
		    if (alberoprocEndo.getAzione() != null) {
			if (alberoprocEndo.getAzione().getAzAzione().equalsIgnoreCase("-")) {
			    azione = alberoprocEndo.getAzione();
			    break;
			}
		    }
		}
	    }
	}
	if (azione == null) {
	    // l'azione non è stata trovata risalire a ritroso a partire dall'alberoproc per trovare l'azione
	    AlberoprocHelper helper = findAlberoprocHelper(alberoproc, null);
	    azione = helper.getAzione();
	    if (azione == null) {
		// alla fine di tutto torno l'azione di default
		azione = azioniService.findById(CODICE_AZIONE_DEFAULT);
	    }
	}
	return azione;
    }

    @Override
    public void spostaVoceAlbero(String idcomune, Integer sorgente, Integer destinazione) {

	if (sorgente == null) {
	    throw new BusinessValidationException("Attenzione! La voce dell'albero selezionata come sorgente non può essere nulla o uguale a 0");
	}
	if (destinazione == null) {
	    throw new BusinessValidationException("Attenzione! La voce dell'albero selezionata come destinazione non può essere nulla o uguale a 0");
	}
	// 1 CONTROLLARE CHE LE VOCI ESISTANO E APPARTENGONO ALLO STESSO SOFTWARE
	Alberoproc alberoSrc = this.findById(new PkId(sorgente));
	if (alberoSrc == null) {
	    throw new BusinessValidationException("Attenzione! La voce dell'albero selezionata come sorgente non esiste [" + sorgente + "]");
	}
	Alberoproc alberoDest = this.findById(new PkId(destinazione));
	if (alberoDest == null && !destinazione.equals(0)) {
	    throw new BusinessValidationException("Attenzione! La voce dell'albero selezionata come destinazione non esiste [" + destinazione + "]");
	}
	String scCodiceDest = "";
	if (!destinazione.equals(0)) {
	    if (!alberoSrc.getSoftware().getCodice().equals(alberoDest.getSoftware().getCodice())) {
		throw new BusinessValidationException("Attenzione! Le voci dell'albero selezionate non appartengono allo stesso software");
	    }
	    scCodiceDest = alberoDest.getScCodice();
	} else {
	    // devo inserire una voce radice
	    List<Alberoproc> figli = this.findAlberoprocFigli(idcomune, "", true, DAOOrderTypeEnum.ASC, Boolean.FALSE);
	    if (figli.size() > 0) {
		for (Alberoproc alberoproc2 : figli) {
		    scCodiceDest = alberoproc2.getScCodice();
		}
		int codicePadre = Integer.parseInt(scCodiceDest);
		codicePadre++;
		scCodiceDest = (codicePadre < 10) ? "0" + codicePadre : String.valueOf(codicePadre);
	    } else {
		scCodiceDest = "01";
	    }
	}
	// 2 CONTROLLARE CHE LA VOCE SORGENTE NON SIA UNA CARTELLA PADRE DELLA VOCE DESTINAZIONE
	String scCodiceSrc = alberoSrc.getScCodice();
	if (scCodiceDest.startsWith(scCodiceSrc)) {
	    throw new BusinessValidationException("Attenzione! Non è possibile spostare una cartella in una sua sottocartella");
	}
	AlberoprocBusinessRules rules = (AlberoprocBusinessRules) SigeproBusinessRules.getClassRules(AlberoprocBusinessRules.class);
	rules.setCustomRule(AlberoprocBusinessRules.CustomRuleEnum.eseguiOperazioniSuCache.name(), false);
	SigeproBusinessRules.setClassRules(AlberoprocBusinessRules.class, rules);
	// 3 CALCOLCARE IL NUOVO SC_CODICE SU DESTINAZIONE E SETTARLO SU ALBEROPROC SORGENTE
	List<Alberoproc> figli = this.findAlberoprocFigli(idcomune, scCodiceDest, true, DAOOrderTypeEnum.ASC, Boolean.FALSE);
	String scCodiceUltimo = "";
	if (figli.size() > 0) {
	    for (Alberoproc alberoproc2 : figli) {
		scCodiceUltimo = alberoproc2.getScCodice();
	    }
	    scCodiceUltimo = calcolaProssimoCodice(scCodiceUltimo);
	} else {
	    if (scCodiceDest.length() > 2) {
		scCodiceUltimo = scCodiceDest + "01";
	    } else { // nodo radice
		scCodiceUltimo = scCodiceDest;
	    }
	}
	// 4 SE ALBEROPROC HA DEI FIGLI AGGIORNARE A CASCATA LE VOCI DEI FIGLI ALTRIMENTI SI SCOLLEGANO
	List<Alberoproc> figliDaAggiornare = this.findAlberoprocFigli(idcomune, scCodiceSrc, false, DAOOrderTypeEnum.ASC, Boolean.FALSE);
	String nuovoCodice = "";
	try {
	    for (Alberoproc alberoproc : figliDaAggiornare) {
		if (alberoproc.getScCodice().length() > scCodiceSrc.length()) {
		    nuovoCodice = alberoproc.getScCodice().replaceFirst(scCodiceSrc, scCodiceUltimo);
		    alberoproc.setScCodice(nuovoCodice);
		    this.update(alberoproc);
		}
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    SigeproBusinessRules.buildDefaultRules();
	}
	alberoSrc.setScCodice(scCodiceUltimo);
	// 5 AGGIORNARE LA VOCE DI ALBEROPROC SORGENTE
	try {
	    this.update(alberoSrc);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void flush() {

	alberoprocDAO.flush();
    }

    @Override
    public void clear() {

	alberoprocDAO.clear();
    }

    @Override
    public List<Alberoproc> findByResponsabileprocedimento(Responsabili responsabileprocedimento, Integer firstResult, Integer maxResult) {

	return _findByOperatore(responsabileprocedimento, "responsabileId", firstResult, maxResult);
    }

    @Override
    public List<Alberoproc> findByResponsabileistruttoria(Responsabili responsabileistruttoria, Integer firstResult, Integer maxResult) {

	return _findByOperatore(responsabileistruttoria, "respistruttoriaId", firstResult, maxResult);
    }

    @Override
    public List<Alberoproc> findByOperatoreSTC(Responsabili operatoreSTC, Integer firstResult, Integer maxResult) {

	return _findByOperatore(operatoreSTC, "operatoreStcId", firstResult, maxResult);
    }

    private List<Alberoproc> _findByOperatore(Responsabili responsabile, String propertyPath, Integer firstResult, Integer maxResult) {

	if (EntityUtils.isNestedPropertyBlank(responsabile, "id.codice")) {
	    return new ArrayList<Alberoproc>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals(propertyPath, responsabile.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("software.codice"));
	ft.addOrder(FilterUtils.orderAsc("scOrdine"));
	ft.addOrder(FilterUtils.orderAsc("scDescrizione"));
	return alberoprocDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<Alberoproc> findByTipiprocedure(Integer codice, Integer firstResult, Integer maxResult) {

	if (codice == null) {
	    return new ArrayList<Alberoproc>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipoProceduraId", codice, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("software.codice"));
	ft.addOrder(FilterUtils.orderAsc("scOrdine"));
	ft.addOrder(FilterUtils.orderAsc("scDescrizione"));
	return alberoprocDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public AlberoprocDocumenti findModelloDomandaFO(String idcomune, Integer id) {

	AlberoprocDocumenti modelloDomandaFO = null;
	Alberoproc alberoproc = this.findById(new PkId(idcomune, id));
	List<AlberoprocDocumenti> alberoprocDocs = alberoprocDocumentiService.findByAlberoProc(id);
	for (AlberoprocDocumenti alberoprocDoc : alberoprocDocs) {
	    if (BooleanUtils.isTrue(alberoprocDoc.getFlgDomandafo())) {
		modelloDomandaFO = alberoprocDoc;
		break;
	    }
	}
	if (modelloDomandaFO == null) {
	    String scCodice = alberoproc.getScCodice();
	    int lengthCodice = scCodice.length();
	    int lengthTree = lengthCodice / 2;
	    for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		String sccodicePadre = scCodice.substring(0, lengthCodice);
		Alberoproc alberoprocPadre = this.findByScCodice(idcomune, sccodicePadre);
		List<AlberoprocDocumenti> alberoprocDocsEreditati = alberoprocDocumentiService.findByAlberoProc(alberoprocPadre.getId().getCodice());
		for (AlberoprocDocumenti alberoprocDocEreditato : alberoprocDocsEreditati) {
		    if (BooleanUtils.isTrue(alberoprocDocEreditato.getFlgDomandafo())) {
			modelloDomandaFO = alberoprocDocEreditato;
			break;
		    }
		}
		if (modelloDomandaFO != null) {
		    break;
		}
	    }
	}
	return modelloDomandaFO;
    }

    @Override
    public void updateScCodice(String idcomune, Integer codiceAlberoproc, String scCodice) {

	alberoprocDAO.updateScCodice(idcomune, codiceAlberoproc, scCodice);
    }

    @Override
    public ConfigurazionePreferenzeUsoPerMercatoEnum isGestisceMercatoAndUso(Integer codiceAlberoproc) {

	Alberoproc alberoproc = this.findById(new PkId(codiceAlberoproc));
	if (EntityUtils.getNestedProperty(alberoproc.getMercato(), "id.codice") != null) {
	    if (EntityUtils.getNestedProperty(alberoproc.getMercatoUso(), "id.codice") != null) {
		return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_DA_ALBERO;
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("isGestisceMercatoAndUso# Non è configurato nessun uso per il mercato/fiera per la voce dell'albero {}",
			    codiceAlberoproc);
		}
		return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_NON_DA_ALBERO;
	    }
	} else {
	    if (log.isDebugEnabled()) {
		log.debug("isGestisceMercatoAndUso# Non è configurato nessun mercato/fiera per la voce dell'albero {}", codiceAlberoproc);
	    }
	    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_NON_CONFIG_SU_ALBERO;
	}
    }

    @Override
    public FoArjStepsTestata findFoArjStepsTestata(String idcomune, Integer codice) {

	Alberoproc nodo = this.findById(new PkId(idcomune, codice));
	List<Alberoproc> padri = new ArrayList<Alberoproc>();
	this.findAlberoprocPadri(padri, idcomune, nodo.getScCodice());
	for (Alberoproc alberoproc : padri) {
	    if (!EntityUtils.isNestedPropertyBlank(alberoproc, "foArjStepsTestata.id.codice")) {
		return alberoproc.getFoArjStepsTestata();
	    }
	}
	return null;
    }

    @Override
    public boolean checkSePubblicabileSuCART(String idcomune, Integer codiceAlberoproc) {

	String scCodice = "";
	DynaProperty[] properties = { new DynaProperty("scCodice", String.class), new DynaProperty("software_codice", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("AlberoprocDC", null, properties);
	DynaBean alberoproc = alberoprocDAO.findDynaBeanById(idcomune, codiceAlberoproc, userDynaClass, Alberoproc.class);
	if (alberoproc != null) {
	    boolean condizioneFogliaAlbero = false;
	    boolean condizioneDirettoGenitoreCART = false;
	    boolean condizioneStpendoNoTipologia = true;
	    scCodice = (String) alberoproc.get("scCodice");
	    if (StringUtils.isNotBlank(scCodice)) {
		AlberoprocCommand cmd = this.findAlberoprocFigli(idcomune, scCodice);
		if (cmd != null) {
		    if (cmd.getChildren() == null || cmd.getChildren().isEmpty()) {
			// CONDIZIONE È una foglia dell'albero e non una cartella 
			condizioneFogliaAlbero = true;
		    }
		}
		if (scCodice.length() > 2) {
		    Alberoproc padre = this.findByScCodice(idcomune, StringUtils.left(scCodice, scCodice.length() - 2));
		    if (padre.getStpEndoTipo2s() != null) {
			if (padre.getStpEndoTipo2s().size() > 0) {
			    // CONDIZIONE Il diretto genitore abbia un record collegato in STP_ENDO_TIPO2, dal quale recuperare il 47.100R 
			    condizioneDirettoGenitoreCART = true;
			}
		    }
		}
		StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(idcomune, codiceAlberoproc);
		if (stp2 != null) {
		    if (stp2.getStpTipologieEndo2() != null) {
			// CONDIZIONE Se ha un record collegato in STP_ENDO_TIPO2 non deve avere il campo CODICE_TIPOLOGIA_ENDO
			// valorizzato ( l'aggiornamento del dizionario prevede la valorizzazione di quel campo) 
			condizioneStpendoNoTipologia = false;
		    }
		}
	    }
	    return condizioneFogliaAlbero && condizioneDirettoGenitoreCART && condizioneStpendoNoTipologia;
	}
	return false;
    }

    @Override
    public void updatePubblicaSuCart(String idcomune, Integer codiceAlberoproc, boolean pubblica) {

	StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(idcomune, codiceAlberoproc);
	if (stp2 == null) {
	    if (pubblica) {
		Alberoproc ap = this.findById(new PkId(codiceAlberoproc));
		String scCodice = ap.getScCodice();
		if (scCodice.length() > 2) {
		    Alberoproc padre = this.findByScCodice(idcomune, StringUtils.left(scCodice, scCodice.length() - 2));
		    StpEndoTipo2 sppadre = stpEndoTipo2Service.findbyAlberoproc(idcomune, padre.getId().getCodice());
		    if (sppadre == null) {
			throw new BusinessValidationException("ERR002 - la voce di intervento padre non è una voce CART");
		    }
		    String codiceEndoRegionale = sppadre.getCodiceEndoRegionale();
		    Integer codiceStp = sppadre.getCodiceStp();
		    stp2 = new StpEndoTipo2();
		    stp2.setAlberoproc(ap);
		    stp2.setCodiceEndoRegionale(codiceEndoRegionale);
		    stp2.setCodiceStp(codiceStp);
		    stp2.setTipo(StpEndoTipo2Service.TIPO_ENDO);
		    stpEndoTipo2Service.insert(stp2);
		} else {
		    throw new BusinessValidationException("ERR001 - non è possibile pubblicare un intervento radice");
		}
	    }
	} else {
	    if (!pubblica) {
		if (stp2.getStpTipologieEndo2() != null) {
		    throw new BusinessValidationException(
			    "ERR003 - Non è possibile eliminare la pubblicazione in quanto proviene da aggiornamento del dizionario regionale");
		} else {
		    stpEndoTipo2Service.delete(stp2);
		}
	    }
	}
    }

    @Override
    public List<InterventoSimpleBean> findListaInterventiSottonodiDi(String idcomune, Integer codiceAlberoproc, boolean soloModulisticaNazionale,
	    boolean isAreaRiservata, boolean isUtenteTester, String codiceComune) {

	if (codiceAlberoproc == null) {
	    throw new BusinessValidationException("il parametro codiceAlberoproc non può essere nullo");
	}
	String scCodice = "";
	if (codiceAlberoproc.intValue() != -1) {
	    scCodice = findScCodice(idcomune, codiceAlberoproc);
	}
	List<InterventoSimpleBean> list = new ArrayList<InterventoSimpleBean>();
	List<Alberoproc> aps = alberoprocDAO.findAlberoprocFigli(idcomune, scCodice, true, DAOOrderTypeEnum.ASC, Boolean.FALSE);
	int pos = 0;
	Map<Integer, String> codiciIntervento = new HashMap<Integer, String>();
	for (Alberoproc ap : aps) {
	    if (isPubblicaFrontoffice(ap.getScPubblica(), ap.getScAttivo(), isAreaRiservata, isUtenteTester)) {
		boolean aggiungi = true;
		if (soloModulisticaNazionale) {
		    aggiungi = alberoprocDAO.checkModulisticaNazionale(ap);
		}
		if (aggiungi) {
		    codiciIntervento.put(ap.getId().getCodice(), ap.getScCodice());
		    InterventoSimpleBean isb = new InterventoSimpleBean();
		    isb.setId(ap.getId().getCodice());
		    isb.setText(ap.getScDescrizione());
		    isb.setScCodice(ap.getScCodice());
		    isb.setScOrdine(ap.getScOrdine());
		    int c = alberoprocDAO.countAlberoprocFigli(idcomune, ap.getScCodice(), true);
		    isb.setHasChilds(Boolean.valueOf((c > 0)));
		    list.add(pos, isb);
		    pos++;
		}
	    }
	}
	Set<Integer> listaEffettiva = alberoprocDAO.verificaInterventiConEndoPrincipale(codiciIntervento, codiceComune);
	List<InterventoSimpleBean> result = new ArrayList<InterventoSimpleBean>(listaEffettiva.size());
	pos = 0;
	for (InterventoSimpleBean isb : list) {
	    if (listaEffettiva.contains(isb.getId())) {
		result.add(pos, isb);
		pos++;
	    }
	}
	return result;
    }

    @Override
    public List<Integer> findGerarchiaNodiPadre(String idcomune, Integer codiceAlberoproc, boolean soloModulisticaNazionale, boolean isAreaRiservata,
	    boolean isUtenteTester, String codiceComune) {

	return findGerarchiaNodiPadre(idcomune, codiceAlberoproc, false, soloModulisticaNazionale, isAreaRiservata, isUtenteTester, codiceComune);
    }

    /**
     * *
     * 
     * <pre>
     *  <option value="">Eredita dal padre</option>
     * 	<option value="0">Non pubblicare</option>
     * 	<option value="1">Area Riservata e Front Office</option>
     * 	<option value="2">Solo Area Riservata</option>
     * 	<option value="3">Solo Front Office</option>
     * </pre>
     * 
     * @param scPubblica
     * @return
     */
    private boolean isPubblicaFrontoffice(Integer scPubblica, Boolean scAttivo, boolean isAreaRiservata, boolean isUtenteTester) {

	if (scAttivo != null) {
	    if (scAttivo.booleanValue() && !isUtenteTester) { // se true allora la voce è disabilitata
		return false;
	    }
	}
	if (scPubblica == null) {
	    return true; // EREDITA DAL PADRE
	}
	if (scPubblica.intValue() == 0 && isUtenteTester) {
	    return true;
	}
	if (isAreaRiservata) {
	    if (scPubblica.intValue() == 1) {
		return true;
	    }
	} else {
	    if (scPubblica.intValue() == 1 || scPubblica.intValue() == 3) {
		// "1">Area Riservata e Front Office
		// "3">Solo Front Office
		return true;
	    }
	}
	return false;
    }

    /**
     * 
     * 
     * @param codiceAlberoproc
     * @param isInversa
     * @return
     */
    private List<Integer> findGerarchiaNodiPadre(String idcomune, Integer codiceAlberoproc, boolean isInversa, boolean soloModulisticaNazionale,
	    boolean isAreaRiservata, boolean isUtenteTester, String codiceComune) {

	// TODO
	List<Integer> gerarchia = new ArrayList<Integer>();
	String scCodice = findScCodice(idcomune, codiceAlberoproc);
	int pos = 0;
	String scCodicePadre = "";
	Map<Integer, String> codiciIntervento = new HashMap<Integer, String>();
	if ((isInversa)) {
	    int lengthCodice = scCodice.length();
	    int lengthTree = lengthCodice / 2;
	    for (int i = 0; i < lengthTree; i++) {
		scCodice = scCodice.substring(0, lengthCodice);
		Alberoproc ap = alberoprocDAO.findByScCodice(idcomune, scCodice);
		if (!isPubblicaFrontoffice(ap.getScPubblica(), ap.getScAttivo(), isAreaRiservata, isUtenteTester)) {
		    return gerarchia;
		}
		codiciIntervento.put(ap.getId().getCodice(), ap.getScCodice());
		gerarchia.add(pos, ap.getId().getCodice());
		pos++;
		lengthCodice = lengthCodice - 2;
	    }
	} else {
	    int step = 0;
	    int stepEnd = 2;
	    while (step < scCodice.length()) {
		scCodicePadre = scCodice.substring(0, stepEnd);
		Alberoproc ap = findByScCodice(idcomune, scCodicePadre);
		if (!isPubblicaFrontoffice(ap.getScPubblica(), ap.getScAttivo(), isAreaRiservata, isUtenteTester)) {
		    return gerarchia;
		}
		codiciIntervento.put(ap.getId().getCodice(), ap.getScCodice());
		gerarchia.add(pos, ap.getId().getCodice());
		step += 2;
		stepEnd += 2;
		pos++;
	    }
	}
	Set<Integer> listaEffettiva = alberoprocDAO.verificaInterventiConEndoPrincipale(codiciIntervento, codiceComune);
	List<Integer> result = new ArrayList<Integer>(listaEffettiva.size());
	pos = 0;
	for (Integer isb : gerarchia) {
	    if (listaEffettiva.contains(isb)) {
		result.add(pos, isb);
		pos++;
	    }
	}
	return result;
    }

    @Override
    public List<Integer> findGerarchiaNodiPadreInversa(String idcomune, Integer codiceAlberoproc, boolean soloModulisticaNazionale) {

	// TODO
	return findGerarchiaNodiPadre(idcomune, codiceAlberoproc, true, false, false, null);
    }

    private String findScCodice(String idcomune, Integer codiceAlberoproc) {

	DynaProperty[] properties = { new DynaProperty("scCodice", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("AlberoprocDC", null, properties);
	DynaBean albp = alberoprocDAO.findDynaBeanById(idcomune, codiceAlberoproc, userDynaClass, Alberoproc.class);
	if (albp == null) {
	    throw new BusinessValidationException("Non è stato trovato l'intervento con codice " + codiceAlberoproc);
	}
	String scCodice = (String) albp.get("scCodice");
	if (StringUtils.isBlank(scCodice)) {
	    throw new BusinessValidationException("Non è stato trovato l'intervento con codice " + codiceAlberoproc);
	}
	return scCodice;
    }

    @Override
    public InterventoBean findInterventoBean(String idcomune, Integer codiceAlberoproc, String codiceComune, boolean isAreaRiservata,
	    boolean isUtenteTester) {

	InterventoBean result = new InterventoBean();
	Alberoproc ap = findById(new PkId(idcomune, codiceAlberoproc));
	result.setId(codiceAlberoproc);
	result.setInformazioni(ap.getScNote());
	result.setNome(ap.getScDescrizione());
	AlberoprocHelper ah = findAlberoprocHelper(ap, codiceComune);
	result.setPresentabileOnline(ah.getPresentabileOnline());
	log.debug("findInterventoBean# recupero le note risalendo dalla foglia al padre");
	List<String> listScNote = ah.getListNote();
	if (!listScNote.isEmpty()) {
	    result.setNote(listScNote);
	}
	if (log.isDebugEnabled()) {
	    log.debug("findInterventoBean# recupero le normative");
	}
	Set<AlberoprocLeggi> leggis = ah.getAlberoprocLeggis();
	if (leggis.size() > 0) {
	    List<NormativaBean> normatives = new ArrayList<NormativaBean>();
	    for (AlberoprocLeggi al : leggis) {
		if (al.getLegge() != null) {
		    NormativaBean n = new NormativaBean();
		    n.setDescrizione(al.getLegge().getLeDescrizione());
		    n.setLink(al.getLegge().getLeLink());
		    if (al.getLegge().getOggetto() != null) {
			Integer codiceOggetto = al.getLegge().getOggetto().getId().getCodice();
			if (codiceOggetto != null) {
			    n.setCodiceOggetto(oggettiService.insertOrGetUID(codiceOggetto, al.getLegge().getOggetto().getId().getIdcomune()));
			}
		    }
		    if (al.getLegge().getLeggitipi() != null) {
			n.setTipologia(al.getLegge().getLeggitipi().getLtDescrizione());
		    }
		    normatives.add(n);
		}
	    }
	    result.setNormativa(normatives);
	}
	log.debug("findInterventoBean# recupero le fasi attuative dalla procedura{}");
	//	Tipiprocedure tp = ah.getTipoProcedura();
	//	if (tp != null) {
	//	    Set<Subprocedure> fasi = tp.getSubprocedures();
	//	    if (fasi.size() > 0) {
	//		List<FasiattuativeBean> fasiAttuative = new ArrayList<FasiattuativeBean>();
	//		for (Subprocedure sp : fasi) {
	//		    FasiattuativeBean fa = new FasiattuativeBean();
	//		    fa.setDescrizione(sp.getSubprocedura());
	//		    fa.setTitolo(sp.getTitolosubprocedura());
	//		    fasiAttuative.add(fa);
	//		}
	//		result.setFasiAttuative(fasiAttuative);
	//	    }
	//	}
	log.debug("findInterventoBean# carico la modulistica");
	Set<AlberoprocDocumenti> ads = ah.getAlberoprocDocumentis();
	Boolean modelloDomandaPresente = false;
	if (ads.size() > 0) {
	    List<ModulisticaBean> mod = new ArrayList<ModulisticaBean>();
	    for (AlberoprocDocumenti apd : ads) {
		// Controllo se l'oggetto ha il flgDomandafo == true, una volta che trovo nella gerarchina
		// un documento con il flag a true non devo più entrare e la mia variabile 
		// dovrà rimanere settata a true
		if (!modelloDomandaPresente && BooleanUtils.toBoolean(apd.getFlgDomandafo())) {
		    log.debug("findInterventoBean# E' presente un documento nella gerarchia con il flag flgDomandafo == true");
		    modelloDomandaPresente = true;
		}
		int pubblica = 0;
		if (apd.getPubblica() != null) {
		    pubblica = apd.getPubblica().intValue();
		}
		if ((pubblica == 1 || pubblica == 3) || isUtenteTester) { //1= Area riservata e frontoffice, 3=Solofrontoffice
		    ModulisticaBean m = new ModulisticaBean();
		    m.setDescrizione(apd.getDescrizione());
		    m.setObbligatorio(apd.getRichiesto() == null ? Boolean.FALSE : apd.getRichiesto().booleanValue());
		    if (apd.getOggetto() != null) {
			Integer codiceOggetto = apd.getOggetto().getId().getCodice();
			if (codiceOggetto != null) {
			    String uid = oggettiService.insertOrGetUID(codiceOggetto, apd.getOggetto().getId().getIdcomune());
			    String formati = apd.getFoTipodownload();
			    List<DownloadBean> dnld = new ArrayList<DownloadBean>();
			    if (StringUtils.isBlank(formati)) {
				DownloadBean db = new DownloadBean();
				db.setCodiceOggetto(uid);
				db.setFormato("");
				dnld.add(db);
			    } else {
				String[] formatis = formati.split(",");
				for (String f : formatis) {
				    DownloadBean db = new DownloadBean();
				    db.setCodiceOggetto(uid);
				    db.setFormato(f);
				    dnld.add(db);
				}
			    }
			    m.setDownloads(dnld);
			}
		    }
		    mod.add(m);
		}
	    }
	    result.setModulistica(mod);
	    result.setModelloDomandaPresente(modelloDomandaPresente);
	}
	//
	log.debug(
		"findInterventoBean# Popolo il percorso della voce dell'albero passata ogni passo del percorso sarà composto da [codice,descrizione]");
	List<PercorsoBean> percorso = new ArrayList<PercorsoBean>();
	List<PercorsoAlberoprocHelper> alberoprocHelpers = ah.getPercorsoAlberoprocHelpers();
	PercorsoBean p = null;
	for (PercorsoAlberoprocHelper percorsoAlberoprocHelper : alberoprocHelpers) {
	    p = new PercorsoBean();
	    p.setId(percorsoAlberoprocHelper.getId());
	    p.setDescrizione(percorsoAlberoprocHelper.getDescrizione());
	    percorso.add(p);
	}
	result.setPercorso(percorso);
	//
	log.debug("findInterventoBean# Gestione degli endoprocedimenti");
	Set<AlberoprocEndo> endos = ah.getAlberoprocEndos();
	Set<InventarioprocedimentiWrapper> endoNecessari = new HashSet<InventarioprocedimentiWrapper>();
	Set<InventarioprocedimentiWrapper> endoRicorrenti = new HashSet<InventarioprocedimentiWrapper>();
	Set<String> principali = new HashSet<String>();
	boolean isPresenteAlberoprocEndo = false;
	for (AlberoprocEndo ape : endos) {
	    boolean pubblica = ape.getFlagPubblica() == null ? false : ape.getFlagPubblica().booleanValue();
	    if (pubblica || isUtenteTester) {
		boolean necessario = ape.getFlagRichiesto() == null ? false : ape.getFlagRichiesto().booleanValue();
		// boolean principale = ape.getFlagPrincipale() == null ? false : ape.getFlagPrincipale().booleanValue();
		StpEndoTipo2 stp2 = stpEndoTipo2Service.findByAlberoprocInventarioproc(ape.getInventarioprocedimento().getId().getIdcomune(),
			ape.getAlberoproc().getId().getCodice(), ape.getInventarioprocedimento().getId().getCodice());
		boolean intervento = stp2 != null;
		isPresenteAlberoprocEndo = true;
		if (necessario) {
		    InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
		    wip.setId(ape.getInventarioprocedimento().getId());
		    wip.setIp(ape.getInventarioprocedimento());
		    wip.setIntervento(intervento);
		    endoNecessari.add(wip);
		} else {
		    InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
		    wip.setId(ape.getInventarioprocedimento().getId());
		    wip.setIp(ape.getInventarioprocedimento());
		    wip.setIntervento(intervento);
		    endoRicorrenti.add(wip);
		}
		if (intervento) {
		    String key = inventarioprocedimentiService.getEndoprocedimentoKey(ape.getInventarioprocedimento());
		    principali.add(key);
		}
	    }
	}
	List<AlberoprocEndoLoc> alocs = alberoprocEndoLocService.findEndoprocedimentiFromAlbero(idcomune, codiceAlberoproc, codiceComune);
	for (AlberoprocEndoLoc ape : alocs) {
	    boolean pubblica = ape.getFlagPubblica() == null ? false : ape.getFlagPubblica().booleanValue();
	    if (pubblica || isUtenteTester) {
		boolean necessario = ape.getFlagNecessario() == null ? false : ape.getFlagNecessario().booleanValue();
		if (necessario) {
		    InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
		    wip.setId(ape.getInventarioprocedimenti().getId());
		    wip.setIp(ape.getInventarioprocedimenti());
		    endoNecessari.add(wip);
		} else {
		    InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
		    wip.setId(ape.getInventarioprocedimenti().getId());
		    wip.setIp(ape.getInventarioprocedimenti());
		    endoRicorrenti.add(wip);
		}
	    }
	}
	EndoprocedimentiHelper epnh = new EndoprocedimentiHelper(inventarioprocedimentiService, inventarioprocEndoService);
	List<EndoprocedimentoSimpleBean> interventiLocali = new ArrayList<EndoprocedimentoSimpleBean>();
	if (!isPresenteAlberoprocEndo) { // SOLAMENTE SE NON PRESENTI RECORD IN ALBEROPROCENDO
	    List<AlberoprocEndoLoc> aintlocs = alberoprocEndoLocService.findInterventiPubblicatiFromAlbero(idcomune, codiceAlberoproc);
	    for (AlberoprocEndoLoc ape : aintlocs) {
		boolean pubblica = ape.getFlagPubblica() == null ? false : ape.getFlagPubblica().booleanValue();
		if (pubblica || isUtenteTester) {
		    EndoprocedimentoSimpleBean esb = epnh.elaboraIntervento(ape, codiceComune);
		    interventiLocali.add(esb);
		}
	    }
	}
	if (interventiLocali.size() > 0) {
	    result.setInterventiLocali(interventiLocali);
	}
	List<FamiglieEndoBean> endoNecessariModel = epnh.elaboraEndo(endoNecessari, principali, codiceComune);
	List<FamiglieEndoBean> endoRicorrentiModel = epnh.elaboraEndo(endoRicorrenti, principali, codiceComune);
	result.setProcedimentiNecessari(endoNecessariModel);
	result.setProcedimentiRicorrenti(endoRicorrentiModel);
	// recupero procedimenti eventuali  
	Set<AlberoprocArendo> ars = ah.getAlberoprocArendos();
	Map<String, InventarioprocedimentiWrapper> endoEventualisM = new HashMap<String, InventarioprocedimentiWrapper>();
	if (ars != null) {
	    if (ars.size() > 0) {
		for (AlberoprocArendo aare : ars) {
		    Integer codiceTipoEndo = null;
		    Integer codiceFamigliaEndo = null;
		    String idComuneTipologia = null;
		    String idComuneFamiglia = null;
		    if (aare.getTipiendo() != null) {
			codiceTipoEndo = aare.getTipiendo().getId().getCodice();
			idComuneTipologia = aare.getTipiendo().getId().getIdcomune();
		    } else if (aare.getTipifamiglieendo() != null) {
			codiceFamigliaEndo = aare.getTipifamiglieendo().getId().getCodice();
			idComuneFamiglia = aare.getTipifamiglieendo().getId().getIdcomune();
		    }
		    List<Inventarioprocedimenti> endosT = null;
		    if (codiceTipoEndo != null) {
			endosT = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologia(null, null, null, codiceTipoEndo,
				idComuneTipologia, true);
		    } else if (codiceFamigliaEndo != null) {
			endosT = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologia(null, codiceFamigliaEndo, idComuneFamiglia,
				null, null, true);
		    }
		    if (endosT != null) {
			for (Inventarioprocedimenti ip : endosT) {
			    boolean processa = true;
			    if (ip.getComune() != null) {
				if (!ip.getComune().getCodicecomune().equalsIgnoreCase(codiceComune)) {
				    processa = false;
				}
			    }
			    if (processa) {
				InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
				wip.setId(ip.getId());
				wip.setIp(ip);
				String key = inventarioprocedimentiService.getEndoprocedimentoKey(ip);
				endoEventualisM.put(key, wip);
			    }
			}
		    }
		}
	    }
	}
	if (!endoEventualisM.isEmpty()) {
	    Set<InventarioprocedimentiWrapper> endoEventuali = new HashSet<InventarioprocedimentiWrapper>();
	    for (Map.Entry<String, InventarioprocedimentiWrapper> m : endoEventualisM.entrySet()) {
		endoEventuali.add(m.getValue());
	    }
	    List<FamiglieEndoBean> endoEventualiModel = epnh.elaboraEndo(endoEventuali, null, codiceComune);
	    result.setProcedimentiEventuali(endoEventualiModel);
	}
	// end 
	// 
	log.debug("findInterventoBean# Gestione degli oneri");
	Set<AlberoprocOneri> oneris = ah.getAlberoprocOneris();
	if (oneris.size() > 0) {
	    List<OneriBean> oneri = new ArrayList<OneriBean>();
	    for (AlberoprocOneri ao : oneris) {
		if (ao.getTipicausalioneri() != null) {
		    if (ao.getAoImportocausale() != null) {
			OneriBean o = new OneriBean();
			o.setCausale(ao.getTipicausalioneri().getCoDescrizione());
			o.setImporto(ao.getAoImportocausale().doubleValue());
			o.setNote(ao.getNote());
			oneri.add(o);
		    }
		}
	    }
	    result.setOneri(oneri);
	}
	return result;
    }

    @Override
    public List<InterventoSimpleBean> findInterventiByDescrizione(String idcomune, String testoDaCercare, String tipoRicerca, String campiRicerca,
	    Integer firstResult, Integer maxResults, boolean filtraSoloComunica, boolean isAreaRiservata, boolean isUtenteTester,
	    String codiceComune) {

	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<InterventoSimpleBean>();
	}
	try {
	    testoDaCercare = URLDecoder.decode(testoDaCercare, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	}
	testoDaCercare = testoDaCercare.replaceAll("%", "");
	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<InterventoSimpleBean>();
	}
	List<InterventoSimpleBean> list = new ArrayList<InterventoSimpleBean>();
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction base = new FilterRestriction();
	base.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	base.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(base);
	tipoRicerca = StringUtils.defaultIfEmpty(tipoRicerca, "tutteParole");
	campiRicerca = StringUtils.defaultIfEmpty(campiRicerca, "titoli");
	if (tipoRicerca.equals("tutteParole")) {
	    String[] valori = testoDaCercare.split(" ");
	    for (String v : valori) {
		FilterRestriction fr = new FilterRestriction();
		fr.setAndOrRestriction(AndOrRestriction.OR);
		fr.addFilterField(FilterUtils.like("scDescrizione", v));
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    fr.addFilterField(FilterUtils.like("scNote", v));
		}
		// ateco
		fr.addFilterField(FilterUtils.like("titolo", v, "alberoprocAtecos.ateco"));
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    fr.addFilterField(FilterUtils.like("descrizione", v, "alberoprocAtecos.ateco"));
		}
		ft.addRestriction(fr);
	    }
	} else if (tipoRicerca.equals("interaFrase")) {
	    FilterRestriction fr = new FilterRestriction();
	    if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		fr.setAndOrRestriction(AndOrRestriction.OR);
		fr.addFilterField(FilterUtils.like("scNote", testoDaCercare));
	    }
	    fr.addFilterField(FilterUtils.like("scDescrizione", testoDaCercare));
	    // ateco
	    fr.addFilterField(FilterUtils.like("titolo", testoDaCercare, "alberoprocAtecos.ateco"));
	    if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		fr.addFilterField(FilterUtils.like("descrizione", testoDaCercare, "alberoprocAtecos.ateco"));
	    }
	    ft.addRestriction(fr);
	} else { // almenoUnaParola
	    String[] valori = testoDaCercare.split(" ");
	    FilterRestriction fr = new FilterRestriction();
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    for (String v : valori) {
		fr.addFilterField(FilterUtils.like("scDescrizione", v));
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    fr.addFilterField(FilterUtils.like("scNote", v));
		}
		// ateco
		fr.addFilterField(FilterUtils.like("titolo", v, "alberoprocAtecos.ateco"));
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    fr.addFilterField(FilterUtils.like("descrizione", v, "alberoprocAtecos.ateco"));
		}
	    }
	    ft.addRestriction(fr);
	}
	FilterRestriction abilitati = new FilterRestriction();
	abilitati.addFilterField(FilterUtils.notEquals("scAttivo", Boolean.TRUE, Boolean.class));
	//	     *  <option value="">Eredita dal padre</option>
	//	     * 	<option value="0">Non pubblicare</option>
	//	     * 	<option value="1">Area Riservata e Front Office</option>
	//	     * 	<option value="2">Solo Area Riservata</option>
	//	     * 	<option value="3">Solo Front Office</option>
	ft.addRestriction(abilitati);
	FilterRestriction abilitatiFo = new FilterRestriction();
	abilitatiFo.setAndOrRestriction(AndOrRestriction.OR);
	abilitatiFo.addFilterField(FilterUtils.isNull("scPubblica"));
	abilitatiFo.addFilterField(FilterUtils.in("scPubblica", new Integer[] { 1, 3 }, Integer.class));
	ft.addRestriction(abilitatiFo);
	List<Alberoproc> res = alberoprocDAO.findByFilterTable(ft, firstResult, maxResults);
	int pos = 0;
	for (Alberoproc ap : res) {
	    // devo verificare se nella gerarchia di questa voce dell'albero non ci sia un elemento in cui 
	    // la proprietà scpubblica sia uguale a 0 (Non pubblicare)
	    boolean isDaPubblicare = checkIsDaPubblicare(ap.getId().getIdcomune(), ap.getId().getCodice(), false, isAreaRiservata, isUtenteTester,
		    codiceComune);
	    if (isDaPubblicare) {
		boolean aggiungi = true;
		if (filtraSoloComunica) {
		    aggiungi = alberoprocDAO.checkComunica(ap);
		}
		if (aggiungi) {
		    InterventoSimpleBean isb = new InterventoSimpleBean();
		    isb.setId(ap.getId().getCodice());
		    isb.setText(ap.getScDescrizione());
		    isb.setScCodice(ap.getScCodice());
		    isb.setScOrdine(ap.getScOrdine());
		    int c = alberoprocDAO.countAlberoprocFigli(idcomune, ap.getScCodice(), true);
		    isb.setHasChilds(Boolean.valueOf((c > 0)));
		    list.add(pos, isb);
		    pos++;
		}
	    }
	}
	Collections.sort(list, new InterventoSimpleBeanComparator());
	return list;
    }

    @Override
    public List<InterventoSimpleBean> findInterventiByDescrizioneNew(String idcomune, String testoDaCercare, String tipoRicerca, String campiRicerca,
	    Integer firstResult, Integer maxResults, boolean filtraSoloComunica, boolean soloModulisticaNazionale, boolean isAreaRiservata,
	    boolean isUtenteTester, String codiceComune) {

	List<InterventoSimpleBean> list = new ArrayList<InterventoSimpleBean>();
	List<InterventoSimpleBean> listQuery = alberoprocDAO.findInterventiByDescrizione(idcomune, testoDaCercare, tipoRicerca, campiRicerca,
		filtraSoloComunica, firstResult, maxResults, soloModulisticaNazionale);
	int pos = 0;
	Map<Integer, String> codiciIntervento = new HashMap<Integer, String>();
	for (InterventoSimpleBean ap : listQuery) {
	    // devo verificare se nella gerarchia di questa voce dell'albero non ci sia un elemento in cui 
	    // la proprietà scpubblica sia uguale a 0 (Non pubblicare)
	    boolean isDaPubblicare = checkIsDaPubblicare(idcomune, ap.getId(), soloModulisticaNazionale, isAreaRiservata, isUtenteTester,
		    codiceComune);
	    if (isDaPubblicare) {
		codiciIntervento.put(ap.getId(), ap.getScCodice());
		list.add(pos, ap);
		pos++;
	    }
	}
	// il controllo della verifica dell'endo principale lo fa già il checkIsDaPubblicare che chiama findGerarchiaNodiPadre
	//	Set<Integer> listaEffettiva = alberoprocDAO.verificaInterventiConEndoPrincipale(codiciIntervento, codiceComune);
	//	List<InterventoSimpleBean> result = new ArrayList<InterventoSimpleBean>(listaEffettiva.size());
	//	pos = 0;
	//	for (InterventoSimpleBean isb : list) {
	//	    if (listaEffettiva.contains(isb.getId())) {
	//		result.add(pos, isb);
	//		pos++;
	//	    }
	//	}
	//	Collections.sort(result, new InterventoSimpleBeanComparator());
	//	return result;
	Collections.sort(list, new InterventoSimpleBeanComparator());
	return list;
    }

    /**
     * Il metodo controlla se per quella voce dell'albero esiste un livello gerarchicamente superiore della catena che
     * ha la proprieta scpubblica == 0 (Non pubblicare)
     * 
     * @param isUtenteTester
     * @param isAreaRiservata
     * 
     * @param scAttivo
     * @return
     */
    private boolean checkIsDaPubblicare(String idcomune, Integer codiceAlberoProc, boolean soloModulisticaNazionale, boolean isAreaRiservata,
	    boolean isUtenteTester, String codiceComune) {

	// TODO 
	List<Integer> listCodiceAlberoProc = this.findGerarchiaNodiPadre(idcomune, codiceAlberoProc, soloModulisticaNazionale, isAreaRiservata,
		isUtenteTester, codiceComune);
	if (listCodiceAlberoProc != null) {
	    return listCodiceAlberoProc.contains(codiceAlberoProc);
	}
	return false;
    }

    @Override
    public String findDescrizioneAlberoproc(String idcomune, Integer codiceAlberoproc) {

	return alberoprocDAO.findDescrizioneAlberoproc(idcomune, codiceAlberoproc);
    }

    @Override
    public List<AlberoprocCommand> findAlberoprocCommand(String idcomunebase, Integer rootCodiceAlbero) {

	return alberoprocDAO.findAlberoprocCommand(idcomunebase, rootCodiceAlbero);
    }
    /*    @Override
        public void richiediAggiornamentoDizionario() throws FunzioneBusinessRemotaException {
    
    	CartServiziDizionario cartServiziDizionario = cartServiziDizionarioDAO.getCartServiziDizionario();
    	String urlServizioNuovoDizionario = cartServiziDizionario.getUrlServizioNuovoDizionario();
    	HttpClient cli = new HttpClient();
    	HttpMethod method = new GetMethod(urlServizioNuovoDizionario);
    	method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
    	log.debug("prima di eseguire la chiamata al servizio di download Scheda {}", urlServizioNuovoDizionario);
    	int status = 0;
    	String codice = "";
    	try {
    	    status = cli.executeMethod(method);
    	    codice = new String(method.getResponseBody());
    	} catch (HttpException e) {
    	    throw new FunzioneBusinessRemotaException("Errore nell'invocazione dell'aggiornamento dizionario " + urlServizioNuovoDizionario, e);
    	} catch (IOException e) {
    	    throw new FunzioneBusinessRemotaException("Errore nell'invocazione dell'aggiornamento dizionario " + urlServizioNuovoDizionario, e);
    	}
    	log.debug("la chiamata al servizio di creazione allegato ha tornato status {}", status);
    	if (status != 200) {
    	    log.error("Attenzione! errore nell'invocazione della chiamata {} Status : {}, Response : {}", new String[] { urlServizioNuovoDizionario,
    		    String.valueOf(status), codice });
    	    throw new FunzioneBusinessRemotaException("Attenzione! errore nell'invocazione della chiamata " + urlServizioNuovoDizionario
    		    + " Status : " + status + ", response : " + codice);
    	}
        }
        */
}
