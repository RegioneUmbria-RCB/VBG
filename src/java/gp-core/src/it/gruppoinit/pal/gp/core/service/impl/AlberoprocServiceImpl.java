package it.gruppoinit.pal.gp.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import it.gruppoinit.pal.gp.core.domain.AlberoprocBolkestein;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLimiti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocModelli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeoplehref;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeopleoper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.LdpDecodifiche;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Subprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocDocumentiComparator;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.InventarioprocedimentiWrapper;
import it.gruppoinit.pal.gp.core.domain.helper.PercorsoAlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.StepsEnum;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocChildrenCommand;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DownloadBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.EndoprocedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FamiglieEndoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FasiattuativeBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBeanComparator;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ModulisticaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NormativaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.OneriBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PercorsoBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoCausaliService;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocAtecoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocBolkesteinService;
import it.gruppoinit.pal.gp.core.service.AlberoprocD2modtattService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocGruppiSmistService;
import it.gruppoinit.pal.gp.core.service.AlberoprocLeggiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocLimitiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocModelliService;
import it.gruppoinit.pal.gp.core.service.AlberoprocOneriService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocRuoliService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisogBackService;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisoggettoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeoplehrefService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeopleoperService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.BandiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.LdpDecodificheService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RiTipiinterventoService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.VwAlberoprocService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.rules.AlberoprocBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
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
    private AlberoprocRuoliService alberoprocRuoliService;
    private AlberoprocDocumentiService alberoprocDocumentiService;
    private AlberoprocEndoService alberoprocEndoService;
    private AlberoprocLeggiService alberoprocLeggiService;
    private AlberoprocOneriService alberoprocOneriService;
    private AlberoprocDyn2modellitService alberoprocDyn2modellitService;
    private AlberoprocLimitiService alberoprocLimitiService;
    private AlberoprocModelliService alberoprocModelliService;
    private AlberoprocpeoplehrefService alberoprocpeoplehrefService;
    private AlberoprocpeopleoperService alberoprocpeopleoperService;
    private AlberoprocD2modtattService alberoprocD2modtattService;
    private AlberoprocTipisoggettoService alberoprocTipisoggettoService;
    private AlberoprocTipisogBackService alberoprocTipisogBackService;
    private AmministrazioniService amministrazioniService;
    private FoArjStepsService foArjStepsService;
    private AzioniService azioniService;
    private BandiService bandiService;
    private CacheManager cacheManager;
    private ConfigurazioneService configurazioneService;
    private GruppiIstruttoriService gruppiIstruttoriService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private IstanzeService istanzeService;
    private MercatiService mercatiService;
    private MercatiUsoService mercatiUsoService;
    private OggettiService oggettiService;
    private ResponsabiliService responsabiliService;
    private RiTipiinterventoService riTipiinterventoService;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private TipiprocedureService tipiprocedureService;
    private TipologiaregistriService tipologiaregistriService;
    private AlberoprocBolkesteinService AlberoprocBolkesteinService;
    private LdpDecodificheService ldpDecodificheService;
    private VwAlberoprocService vwAlberoprocService;
    private AlberoprocGruppiSmistService alberoprocGruppiSmistService;
    private InventarioprocEndoService inventarioprocEndoService;

    @Autowired
    public void setInventarioprocEndoService(InventarioprocEndoService inventarioprocEndoService) {

	this.inventarioprocEndoService = inventarioprocEndoService;
    }

    @Autowired
    public void setAlberoprocGruppiSmistService(AlberoprocGruppiSmistService alberoprocGruppiSmistService) {

	this.alberoprocGruppiSmistService = alberoprocGruppiSmistService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setVwAlberoprocService(VwAlberoprocService vwAlberoprocService) {

	this.vwAlberoprocService = vwAlberoprocService;
    }

    @Autowired
    public void setLdpDecodificheService(LdpDecodificheService ldpDecodificheService) {

	this.ldpDecodificheService = ldpDecodificheService;
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
    public void setAlberoprocLimitiService(AlberoprocLimitiService alberoprocLimitiService) {

	this.alberoprocLimitiService = alberoprocLimitiService;
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
    public void setAlberoprocpeoplehrefService(AlberoprocpeoplehrefService alberoprocpeoplehrefService) {

	this.alberoprocpeoplehrefService = alberoprocpeoplehrefService;
    }

    @Autowired
    public void setAlberoprocpeopleoperService(AlberoprocpeopleoperService alberoprocpeopleoperService) {

	this.alberoprocpeopleoperService = alberoprocpeopleoperService;
    }

    @Autowired
    public void setAlberoprocD2modtattService(AlberoprocD2modtattService alberoprocD2modtattService) {

	this.alberoprocD2modtattService = alberoprocD2modtattService;
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
    public void setAlberoprocTipisogBackService(AlberoprocTipisogBackService alberoprocTipisogBackService) {

	this.alberoprocTipisogBackService = alberoprocTipisogBackService;
    }

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    @Autowired
    public void setAzioniService(AzioniService azioniService) {

	this.azioniService = azioniService;
    }

    @Autowired
    public void setBandiService(BandiService bandiService) {

	this.bandiService = bandiService;
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
    public void setGruppiIstruttoriService(GruppiIstruttoriService gruppiIstruttoriService) {

	this.gruppiIstruttoriService = gruppiIstruttoriService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
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

    @Autowired
    public void setAlberoprocBolkesteinService(AlberoprocBolkesteinService alberoprocBolkesteinService) {

	AlberoprocBolkesteinService = alberoprocBolkesteinService;
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
	    if (deleteChilds && StringUtils.isNotBlank(scCodicePadre)) {
		List<Alberoproc> figli = alberoprocDAO.findAlberoprocFigli(scCodicePadre, true, DAOOrderTypeEnum.ASC, false);
		if (figli.isEmpty()) {
		    Alberoproc alberoprocPadre = this.findByScCodice(scCodicePadre);
		    alberoprocPadre.setScPadre(false);
		    this.update(alberoprocPadre);
		}
	    }
	    updateAlberoprocCache();
	}
    }

    protected void childDelete(Alberoproc entity, boolean deleteChilds) {

	Set<AlberoprocDocumenti> alberoprocDocumentis = entity.getAlberoprocDocumentis();
	if (alberoprocDocumentis != null && !alberoprocDocumentis.isEmpty()) {
	    for (AlberoprocDocumenti alberoprocDocumenti : alberoprocDocumentis) {
		alberoprocDocumentiService.delete(alberoprocDocumenti);
	    }
	    entity.setAlberoprocDocumentis(null);
	}
	Set<AlberoprocEndo> alberoprocEndos = entity.getAlberoprocEndos();
	if (alberoprocEndos != null && !alberoprocEndos.isEmpty()) {
	    for (AlberoprocEndo alberoprocEndo : alberoprocEndos) {
		alberoprocEndoService.delete(alberoprocEndo);
	    }
	    entity.setAlberoprocEndos(null);
	}
	Set<AlberoprocLeggi> alberoprocLeggis = entity.getAlberoprocLeggis();
	if (alberoprocLeggis != null && !alberoprocLeggis.isEmpty()) {
	    for (AlberoprocLeggi alberoprocLeggi : alberoprocLeggis) {
		alberoprocLeggiService.delete(alberoprocLeggi);
	    }
	    entity.setAlberoprocLeggis(null);
	}
	Set<AlberoprocDyn2modellit> alberoprocDyn2modellits = entity.getAlberoprocDyn2modellits();
	if (alberoprocDyn2modellits != null && !alberoprocDyn2modellits.isEmpty()) {
	    for (AlberoprocDyn2modellit alberoprocDyn2modellit : alberoprocDyn2modellits) {
		alberoprocDyn2modellitService.delete(alberoprocDyn2modellit);
	    }
	    entity.setAlberoprocDyn2modellits(null);
	}
	Set<AlberoprocLimiti> alberoprocLimitis = entity.getAlberoprocLimitis();
	if (alberoprocLimitis != null && !alberoprocLimitis.isEmpty()) {
	    for (AlberoprocLimiti alberoprocLimiti : alberoprocLimitis) {
		alberoprocLimitiService.delete(alberoprocLimiti);
	    }
	    entity.setAlberoprocLimitis(null);
	}
	Set<AlberoprocModelli> alberoprocModellis = entity.getAlberoprocModellis();
	if (alberoprocModellis != null && !alberoprocModellis.isEmpty()) {
	    for (AlberoprocModelli alberoprocModelli : alberoprocModellis) {
		alberoprocModelliService.delete(alberoprocModelli);
	    }
	    entity.setAlberoprocModellis(null);
	}
	Set<StpEndoTipo2> stpEndoTipo2s = entity.getStpEndoTipo2s();
	if (stpEndoTipo2s != null && !stpEndoTipo2s.isEmpty()) {
	    for (StpEndoTipo2 stpEndoTipo2 : stpEndoTipo2s) {
		stpEndoTipo2Service.delete(stpEndoTipo2);
	    }
	    entity.setStpEndoTipo2s(null);
	}
	Set<AlberoprocAteco> alberoprocAtecos = entity.getAlberoprocAtecos();
	if (alberoprocAtecos != null && !alberoprocAtecos.isEmpty()) {
	    for (AlberoprocAteco alberoprocAteco : alberoprocAtecos) {
		alberoprocAtecoService.delete(alberoprocAteco);
	    }
	    entity.setAlberoprocAtecos(null);
	}
	if (deleteChilds) {
	    List<Alberoproc> figli = this.findAlberoprocFigli(entity.getScCodice(), false, DAOOrderTypeEnum.DESC, false);
	    if (!figli.isEmpty()) {
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
	if (alberoprocOneris != null && !alberoprocOneris.isEmpty()) {
	    for (AlberoprocOneri alberoprocOneri : alberoprocOneris) {
		alberoprocOneriService.delete(alberoprocOneri);
	    }
	    entity.setAlberoprocOneris(null);
	}
	Set<AlberoprocRuoli> alberoprocRuolis = entity.getAlberoprocRuolis();
	if (alberoprocRuolis != null && !alberoprocRuolis.isEmpty()) {
	    for (AlberoprocRuoli alberoprocRuoli : alberoprocRuolis) {
		alberoprocRuoliService.delete(alberoprocRuoli);
	    }
	    entity.setAlberoprocRuolis(null);
	}
	Set<AlberoCausali> alberoCausalis = entity.getAlberoCausalis();
	if (alberoCausalis != null && !alberoCausalis.isEmpty()) {
	    for (AlberoCausali alberoCausali : alberoCausalis) {
		alberoCausaliService.delete(alberoCausali);
	    }
	    entity.setAlberoCausalis(null);
	}
	Set<Alberoprocpeoplehref> alberoprocpeoplehrefs = entity.getAlberoprocpeoplehrefs();
	if (alberoprocpeoplehrefs != null && !alberoprocpeoplehrefs.isEmpty()) {
	    for (Alberoprocpeoplehref alberoprocpeoplehref : alberoprocpeoplehrefs) {
		alberoprocpeoplehrefService.delete(alberoprocpeoplehref);
	    }
	    entity.setAlberoprocpeoplehrefs(null);
	}
	Set<Alberoprocpeopleoper> alberoprocpeopleopers = entity.getAlberoprocpeopleopers();
	if (alberoprocpeopleopers != null && !alberoprocpeopleopers.isEmpty()) {
	    for (Alberoprocpeopleoper alberoprocpeopleoper : alberoprocpeopleopers) {
		alberoprocpeopleoperService.delete(alberoprocpeopleoper);
	    }
	    entity.setAlberoprocpeopleopers(null);
	}
	Set<AlberoprocArendo> alberoprocArendos = entity.getAlberoprocArendos();
	if (alberoprocArendos != null && !alberoprocArendos.isEmpty()) {
	    for (AlberoprocArendo alberoprocArendo : alberoprocArendos) {
		alberoprocArendoService.delete(alberoprocArendo);
	    }
	    entity.setAlberoprocArendos(null);
	}
	List<AlberoprocTipisoggetto> alberoprocTipisoggettos = alberoprocTipisoggettoService.findByAlberoprocId(entity.getId().getCodice(), null,
		null);
	if (alberoprocTipisoggettos != null && !alberoprocTipisoggettos.isEmpty()) {
	    for (AlberoprocTipisoggetto alberoprocTipisoggetto : alberoprocTipisoggettos) {
		alberoprocTipisoggettoService.delete(alberoprocTipisoggetto);
	    }
	}
	List<AlberoprocProtocollo> alberoprocProtocollos = alberoprocProtocolloService.findByAlberoprocId(entity.getId().getCodice(), null, null);
	if (alberoprocProtocollos != null && !alberoprocProtocollos.isEmpty()) {
	    for (AlberoprocProtocollo alberoprocProtocollo : alberoprocProtocollos) {
		alberoprocProtocolloService.delete(alberoprocProtocollo);
	    }
	}
	List<AlberoprocTipisogBack> alberoprocTipisogBacks = alberoprocTipisogBackService.findByAlberoproc(entity.getId().getCodice());
	if (alberoprocTipisogBacks != null && !alberoprocTipisogBacks.isEmpty()) {
	    for (AlberoprocTipisogBack alberoprocTipisogBack : alberoprocTipisogBacks) {
		alberoprocTipisogBackService.delete(alberoprocTipisogBack);
	    }
	}
	List<AlberoprocBolkestein> alberoprocBolkesteins = AlberoprocBolkesteinService.findByAlberoProc(entity.getId().getCodice());
	if (alberoprocBolkesteins != null && !alberoprocBolkesteins.isEmpty()) {
	    for (AlberoprocBolkestein alberoprocBolkestein : alberoprocBolkesteins) {
		AlberoprocBolkesteinService.delete(alberoprocBolkestein);
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
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggettoWorkflowAreaRis", false, entity.getId());
	    alberoprocDAO.insert(entity);
	    alberoprocDAO.flush();
	    updateDescrizioneCompleta(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    updateAlberoprocCache();
	}
    }

    private void updateDescrizioneCompleta(Alberoproc entity) {

	VwAlberoproc vwAp = vwAlberoprocService.findById(new PkId(entity.getId().getCodice()));
	String descrizione = vwAp.getScDescrizione();
	entity.setDescrizioneCompleta(descrizione);
	alberoprocDAO.update(entity);
	alberoprocDAO.flush();
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
		List<FoArjSteps> arjSteps = foArjStepsService.findByTestataAndNomeStepBase(entity.getFoArjStepsTestata().getId().getCodice(),
			stepIntervento);
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
	// validazione campi data_inizio e data_fine. Non sono obbligatori, ma se uno dei due è popolato allora 
	// anche l'altro lo deve essere
	if (entity.getInizioValidita() != null || entity.getFineValidita() != null) {
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    if (entity.getInizioValidita() == null) {
		InvalidValue iv = new InvalidValue("service_error.campi_data_inizio_validita_non_popolato", entity.getClass(), "inizioValidita", null,
			entity);
		ivs.add(iv);
		throwValidationMessages(ivs);
	    }
	    if (entity.getFineValidita() == null) {
		InvalidValue iv = new InvalidValue("service_error.campi_data_fine_validita_non_popolato", entity.getClass(), "fineValidita",
			new Alberoproc(), entity);
		ivs.add(iv);
		throwValidationMessages(ivs);
	    }
	}
	// VALIDO L'OGGETTO DI DOMINIO
	return super.validateEntity(entity);
    }

    @Override
    public void update(Alberoproc entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    entity.setScStatoControllo("M");
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggettoWorkflowAreaRis", false, entity.getId());
	    alberoprocDAO.update(entity);
	    alberoprocDAO.flush();
	    updateDescrizioneCompleta(entity);
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
	if (entity.getGrpFlagAccettazione() == null) {
	    entity.setGrpFlagAccettazione(Boolean.FALSE);
	}
	if (entity.getGrpFlagAssegnazioneAut() == null) {
	    entity.setGrpFlagAssegnazioneAut(Boolean.FALSE);
	}
	if (StringUtils.isNotBlank(entity.getOraInizioValidita())) {
	    Date date = entity.getInizioValidita();
	    date = Utilities.addTime(date, entity.getOraInizioValidita());
	    entity.setInizioValidita(date);
	}
	if (StringUtils.isNotBlank(entity.getOraFineValidita())) {
	    Date date = entity.getFineValidita();
	    date = Utilities.addTime(date, entity.getOraFineValidita());
	    entity.setFineValidita(date);
	}
	if (entity.getFlagProgAttOsserv() == null) {
	    entity.setFlagProgAttOsserv(Boolean.FALSE);
	}
	if (entity.getFlagArRedirect() == null) {
	    entity.setFlagArRedirect(Boolean.FALSE);
	}
	if (entity.getFlagUnicaDomanda() == null) {
	    entity.setFlagUnicaDomanda(Boolean.FALSE);
	}
	if (entity.getFlagAccessoAtti() == null) {
	    entity.setFlagAccessoAtti(Boolean.FALSE);
	}
	if (entity.getFlagGestioneSpuntisti() == null) {
	    entity.setFlagGestioneSpuntisti(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Alberoproc entity) {

	Amministrazioni amm = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amm);
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
	GruppiIstruttori gruppiIstruttori = gruppiIstruttoriService.bindDomainObject(entity.getGruppiIstruttori(), PkId.class, "id.codice");
	entity.setGruppiIstruttori(gruppiIstruttori);
	LdpDecodifiche ldpoccupazione = ldpDecodificheService.bindDomainObject(entity.getLdpOccupazionis(), PkId.class, "id.codice");
	entity.setLdpOccupazionis(ldpoccupazione);
	LdpDecodifiche ldpgeometrie = ldpDecodificheService.bindDomainObject(entity.getLdpGeometries(), PkId.class, "id.codice");
	entity.setLdpGeometries(ldpgeometrie);
	LdpDecodifiche ldpperiodo = ldpDecodificheService.bindDomainObject(entity.getLdpPeriodis(), PkId.class, "id.codice");
	entity.setLdpPeriodis(ldpperiodo);
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

    public Tipimovimento getMovimentoDefault(Integer codiceProcedimento) {

	Tipiprocedure tipiprocedure = this.getProcedura(codiceProcedimento);
	Tipimovimento tipimovimento = null;
	if (tipiprocedure != null) {
	    if (tipiprocedure.getId().getCodice() != null) {
		// Scorre la lista delle procedure di avvio eprende quella di default
		Set<Tipiprocedureavvio> set = tipiprocedure.getTipiProcedureavvios();
		for (Tipiprocedureavvio tipiprocedureavvio : set) {
		    if (tipiprocedureavvio != null && tipiprocedureavvio.getDefaultsn() == true) {
			tipimovimento = tipiprocedureavvio.getTipoMovimento();
			break;
		    }
		}
	    }
	} else {
	    tipimovimento = new Tipimovimento();
	}
	return tipimovimento;
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

	String key = ORMHelper.getIdcomuneAlias() + "_" + ORMHelper.getSoftware() + "_";
	if (rootCodiceAlbero != null) {
	    key += String.valueOf(rootCodiceAlbero) + "_";
	}
	return key;
    }

    @Override
    public Alberoproc findByScCodice(String sccodice) {

	return this.findBySoftwareAndScCodice(ORMHelper.getSoftware(), sccodice);
    }

    @Override
    public Alberoproc findBySoftwareAndScCodice(String software, String sccodice) {

	return alberoprocDAO.findBySoftwareAndScCodice(software, sccodice);
    }

    @Override
    public void saveRuoli(Alberoproc alberoproc, Set<AlberoprocRuoli> alberoprocRuolis) {

	alberoprocRuoliService.deleteByAlberoprocId(alberoproc.getId().getCodice());
	for (AlberoprocRuoli tempRuoli : alberoprocRuolis) {
	    alberoprocRuoliService.insert(tempRuoli);
	}
    }

    @Override
    public AlberoprocCommand findAlberoprocFigli(String scCodice) {

	List<AlberoprocCommand> alberoprocCommands = this.findAlberoprocHierarchy(null);
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

    public boolean findSeDisabilitato(String scCodice) {

	boolean isDisabilitato = false;
	Alberoproc nodo = this.findByScCodice(scCodice);
	if (BooleanUtils.isTrue(nodo.getScAttivo())) {
	    isDisabilitato = true;
	} else {
	    List<Alberoproc> padri = new ArrayList<Alberoproc>();
	    this.findAlberoprocPadri(padri, scCodice);
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
    private void findAlberoprocPadri(List<Alberoproc> padri, String scCodice) {

	if (StringUtils.isNotBlank(scCodice)) {
	    Alberoproc padre = this.findByScCodice(scCodice);
	    padri.add(padre);
	    if (scCodice.length() > 2) {
		scCodice = scCodice.substring(0, scCodice.length() - 2);
		this.findAlberoprocPadri(padri, scCodice);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoprocCommand> findAlberoprocHierarchy(Integer rootCodiceAlbero) {

	List<AlberoprocCommand> resultList = new ArrayList<AlberoprocCommand>();
	String chiaveAlberoprocInCache = getAlberoprocCachePrefix(rootCodiceAlbero);
	if (cacheManager != null) {
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_ALBEROPROC_KEY);
	    Element obj = cache.get(chiaveAlberoprocInCache);
	    if (obj != null) {
		resultList = (List<AlberoprocCommand>) obj.getObjectValue();
	    } else {
		resultList = populateAlberoprocHierarchy(rootCodiceAlbero);
		Element element = new Element(chiaveAlberoprocInCache, resultList);
		cache.put(element);
		cache.flush();
	    }
	} else {
	    resultList = populateAlberoprocHierarchy(rootCodiceAlbero);
	}
	return resultList;
    }
    
    @Override
    public List<AlberoprocCommand> findAlberoprocHierarchyNoCache(Integer rootCodiceAlbero) {
	return alberoprocDAO.findAlberoprocCommand(rootCodiceAlbero);
    }

    /**
     */
    private List<AlberoprocCommand> populateAlberoprocHierarchy(Integer rootCodiceAlbero) {

	List<AlberoprocCommand> resultList = new ArrayList<AlberoprocCommand>();
	resultList = alberoprocDAO.findAlberoprocCommand(rootCodiceAlbero);
	boolean firstRound = true;
	for (AlberoprocCommand alberoprocCommand : resultList) {
	    alberoprocCommand.setDisabilitato(String.valueOf(BooleanUtils.toBoolean(alberoprocCommand.getScAttivo())));
	    alberoprocCommand.setChildren(inspectHierarchy(resultList, alberoprocCommand.getCodice()));
	    if (alberoprocCommand.getCodice().length() == 2) {
		alberoprocCommand.setRoot(1);
		firstRound = false;
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
	    }
	}
	return !childrens.isEmpty() ? childrens : null;
    }

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
    public String findDescrizionePrimaVoceAlberoproc(String scCodice) {

	return alberoprocDAO.findDescrizionePrimaVoceAlberoproc(scCodice);
    }

    protected boolean isDeleteAllowed(Alberoproc entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!bandiService.findByAlberoproc(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "BANDI", null));
	}
	if (!istanzeService.findByAlberoproc(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
	}
	if (!alberoprocGruppiSmistService.findByAlberoproc(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC_GRUPPI_SMIST", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public void insertAlberoproc(Alberoproc alberoproc, Alberoproc alberoprocPadre) {

	if (alberoproc.getScOrdine() == null) {
	    alberoproc.setScOrdine(new Integer(0));
	}
	if (validateEntity(alberoproc)) {
	    boolean areaprimaria = alberoproc.getAreaPrimaria();
	    log.debug("insertAlberoproc# areaprimaria {}", areaprimaria);
	    String scCodiceFiglio = "";
	    if (!areaprimaria && alberoprocPadre == null) {
		areaprimaria = true;
	    }
	    log.debug("insertAlberoproc# areaprimaria {}", areaprimaria);
	    if (areaprimaria) {
		List<Alberoproc> alberoprocs = this.findRootsAlberoProc();
		List<String> scCodiciList = new ArrayList<String>();
		for (Alberoproc temp : alberoprocs) {
		    scCodiciList.add(temp.getScCodice());
		}
		if (!scCodiciList.isEmpty()) {
		    log.debug("insertAlberoproc# scCodiciList {}", scCodiciList);
		    Collections.sort(scCodiciList);
		    String sccodiceFiglioLast = scCodiciList.get(scCodiciList.size() - 1);
		    Integer scCodiceFiglioInt = null;
		    if (sccodiceFiglioLast.startsWith("0")) {
			sccodiceFiglioLast = sccodiceFiglioLast.substring(1, 2);
			scCodiceFiglioInt = Integer.parseInt(sccodiceFiglioLast) + 1;
			scCodiceFiglio = scCodiceFiglioInt.toString();
			if (scCodiceFiglio.length() == 1) {
			    scCodiceFiglio = "0" + scCodiceFiglio;
			}
		    } else {
			scCodiceFiglioInt = Integer.parseInt(sccodiceFiglioLast) + 1;
			scCodiceFiglio = scCodiceFiglioInt.toString();
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
		log.debug("insertAlberoproc# sc_codice_padre {}", sc_codice_padre);
		List<Alberoproc> figli = this.findAlberoprocFigli(sc_codice_padre, true, DAOOrderTypeEnum.ASC, true);
		if (!figli.isEmpty()) {
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
	    log.debug("insertAlberoproc# scCodiceFiglio {}", scCodiceFiglio);
	    alberoproc.setScCodice(scCodiceFiglio);
	    this.insert(alberoproc);
	}
	updateAlberoprocCache();
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
	if (!list.isEmpty()) {
	    for (Alberoproc alberoproc2 : list) {
		return alberoproc2.getProgressivoistanze();
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
	if (!list.isEmpty()) {
	    for (Alberoproc alberoproc2 : list) {
		result = alberoprocProtocolloService.findProprietaByAlberoprocId(alberoproc2.getId().getCodice(), propertyName, codiceComune);
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

    private boolean isFlagOggettoPraticaDefault(Integer flagOggettoPraticaDefault) {

	return (flagOggettoPraticaDefault != null && flagOggettoPraticaDefault.intValue() > 1);
    }

    @Override
    public AlberoprocHelper findAlberoprocHelper(Integer idAlberoProc) {

	return this.findAlberoprocHelper(this.findById(new PkId(idAlberoProc)));
    }

    @Override
    public AlberoprocHelper findAlberoprocHelper(Alberoproc alberoproc) {

	alberoproc = bindDomainObject(alberoproc, PkId.class, "id.codice");
	AlberoprocHelper alberoprocHelper = new AlberoprocHelper();
	alberoprocHelper.setCurrentAlberoproc(alberoproc);
	String scCodice = alberoproc.getScCodice();
	int lengthCodice = scCodice.length();
	int lengthTree = lengthCodice / 2;
	if (EntityUtils.getNestedProperty(alberoproc.getAzione(), "azId") != null) {
	    Azioni azione = azioniService.bindDomainObject(alberoproc.getAzione(), Integer.class, "azId");
	    alberoprocHelper.setAzione(azione);
	}
	boolean flagOggettoPraticaDefault = isFlagOggettoPraticaDefault(alberoproc.getFlagOggettoPraticaDefault());
	if (flagOggettoPraticaDefault) {
	    alberoprocHelper.setFlagOggettoPraticaDefault(flagOggettoPraticaDefault);
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
	    if (!procedura.getTipiProcedureavvios().isEmpty()) {
		for (Tipiprocedureavvio avvio : procedura.getTipiProcedureavvios()) {
		    if (BooleanUtils.isTrue(avvio.getDefaultsn())) {
			alberoprocHelper.setMovAvvio(avvio.getTipoMovimento());
			break;
		    }
		}
	    }
	}
	if (EntityUtils.getNestedProperty(alberoproc.getLdpGeometries(), "id.codice") != null) {
	    alberoprocHelper.setLdpGeometries(alberoproc.getLdpGeometries());
	}
	if (EntityUtils.getNestedProperty(alberoproc.getLdpOccupazionis(), "id.codice") != null) {
	    alberoprocHelper.setLdpOccupazionis(alberoproc.getLdpOccupazionis());
	}
	if (EntityUtils.getNestedProperty(alberoproc.getLdpPeriodis(), "id.codice") != null) {
	    alberoprocHelper.setLdpPeriodis(alberoproc.getLdpPeriodis());
	}
	if (EntityUtils.getNestedProperty(alberoproc.getAmministrazioni(), "id.codice") != null) {
	    alberoprocHelper.setAmministrazioni(alberoproc.getAmministrazioni());
	}
	Set<AlberoprocLeggi> alberoprocLeggis = new HashSet<AlberoprocLeggi>(0);
	List<AlberoprocDocumenti> alberoprocDocumentis = new ArrayList<AlberoprocDocumenti>(0);
	Set<AlberoprocDocumenti> alberoprocDocumentisSet = new HashSet<AlberoprocDocumenti>();
	Set<AlberoprocEndo> alberoprocEndos = new HashSet<AlberoprocEndo>();
	Set<AlberoprocDyn2modellit> alberoprocDyn2modellits = new HashSet<AlberoprocDyn2modellit>();
	Set<AlberoprocRuoli> alberoprocRuolis = new HashSet<AlberoprocRuoli>();
	Set<AlberoprocOneri> alberoprocOneris = new HashSet<AlberoprocOneri>();
	Set<AlberoprocAteco> alberoprocAtecos = new HashSet<AlberoprocAteco>();
	Set<AlberoprocD2modtatt> alberoprocD2modtatts = new HashSet<AlberoprocD2modtatt>();
	Set<AlberoprocTipisoggetto> alberoprocTipisoggettos = new HashSet<AlberoprocTipisoggetto>();
	Set<AlberoprocArendo> alberoprocArendos = new HashSet<AlberoprocArendo>();
	Set<AlberoprocTipisogBack> alberoprocTipisogBacks = new HashSet<AlberoprocTipisogBack>();
	List<String> listaScNote = new ArrayList<String>();
	List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = new ArrayList<PercorsoAlberoprocHelper>();
	// variabile che viene popolata in base ahi valori che assume il campo scPubblica di ogni voce dell'albero della 
	// catena, se esiste almeno una con valore scPubblica 1 o 2 allora true , alteimenti false.
	Boolean presentabileOnline = false;
	for (int i = 0; i < lengthTree; i++) {
	    scCodice = scCodice.substring(0, lengthCodice);
	    Alberoproc alberoprocTemp = alberoprocDAO.findByScCodice(scCodice);
	    if (alberoprocTemp != null) {
		// LDP  BEGIN
		if (alberoprocHelper.getLdpGeometries() == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getLdpGeometries(), "id.codice") != null) {
		    alberoprocHelper.setLdpGeometries(alberoprocTemp.getLdpGeometries());
		}
		if (alberoprocHelper.getLdpOccupazionis() == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getLdpOccupazionis(), "id.codice") != null) {
		    alberoprocHelper.setLdpOccupazionis(alberoprocTemp.getLdpOccupazionis());
		}
		if (alberoprocHelper.getLdpPeriodis() == null) {
		    if (EntityUtils.getNestedProperty(alberoprocTemp.getLdpPeriodis(), "id.codice") != null) {
			alberoprocHelper.setLdpPeriodis(alberoprocTemp.getLdpPeriodis());
		    }
		}
		//LDP END
		if (alberoprocHelper.getAmministrazioni() == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getAmministrazioni(), "id.codice") != null) {
		    alberoprocHelper.setAmministrazioni(alberoprocTemp.getAmministrazioni());
		}
		if (!flagOggettoPraticaDefault) {
		    flagOggettoPraticaDefault = isFlagOggettoPraticaDefault(alberoprocTemp.getFlagOggettoPraticaDefault());
		    if (flagOggettoPraticaDefault) {
			alberoprocHelper.setFlagOggettoPraticaDefault(flagOggettoPraticaDefault);
		    }
		}
		if (alberoprocTemp.getAlberoprocLeggis() != null && !alberoprocTemp.getAlberoprocLeggis().isEmpty()) {
		    alberoprocLeggis.addAll(alberoprocTemp.getAlberoprocLeggis());
		}
		if (alberoprocTemp.getAlberoprocDocumentis() != null && !alberoprocTemp.getAlberoprocDocumentis().isEmpty()) {
		    alberoprocDocumentis.addAll(alberoprocTemp.getAlberoprocDocumentis());
		    alberoprocDocumentisSet.addAll(alberoprocTemp.getAlberoprocDocumentis());
		}
		if (alberoprocTemp.getAlberoprocEndos() != null && !alberoprocTemp.getAlberoprocEndos().isEmpty()) {
		    List<AlberoprocEndo> endos = alberoprocEndoService.findAllByAlberoproc(alberoprocTemp.getId().getCodice(), true);
		    alberoprocEndos.addAll(endos);
		}
		if (alberoprocTemp.getAlberoprocDyn2modellits() != null && !alberoprocTemp.getAlberoprocDyn2modellits().isEmpty()) {
		    alberoprocDyn2modellits.addAll(alberoprocTemp.getAlberoprocDyn2modellits());
		}
		if (alberoprocTemp.getAlberoprocRuolis() != null && !alberoprocTemp.getAlberoprocRuolis().isEmpty()) {
		    alberoprocRuolis.addAll(alberoprocTemp.getAlberoprocRuolis());
		}
		if (alberoprocTemp.getAlberoprocOneris() != null && !alberoprocTemp.getAlberoprocOneris().isEmpty()) {
		    List<AlberoprocOneri> oneris = alberoprocOneriService.findAllByAlberoproc(alberoprocTemp.getId().getCodice());
		    for (AlberoprocOneri alberoprocOneri : oneris) {
			boolean onereDisabilitato = false;
			if (alberoprocOneri.getTipicausalioneri() != null && alberoprocOneri.getTipicausalioneri().getCoDisabilitato() != null) {
			    onereDisabilitato = alberoprocOneri.getTipicausalioneri().getCoDisabilitato().booleanValue();
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
			.findByAlberoprocId(alberoprocTemp.getId().getCodice(), null, null);
		if (!alberoprocTipisoggettos2.isEmpty()) {
		    alberoprocTipisoggettos.addAll(alberoprocTipisoggettos2);
		}
		List<AlberoprocD2modtatt> listalberoprocD2modtatts = alberoprocD2modtattService.findByAlberoProc(alberoprocTemp.getId().getCodice());
		if (!listalberoprocD2modtatts.isEmpty()) {
		    alberoprocD2modtatts.addAll(listalberoprocD2modtatts);
		}
		List<AlberoprocArendo> arendos = alberoprocArendoService.findByAlberoProc(alberoprocTemp.getId().getCodice());
		if (!arendos.isEmpty()) {
		    alberoprocArendos.addAll(arendos);
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getAzione(), "azId") == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getAzione(), "azId") != null) {
		    Azioni azione = azioniService.bindDomainObject(alberoprocTemp.getAzione(), Integer.class, "azId");
		    alberoprocHelper.setAzione(azione);
		}
		if (StringUtils.isBlank(progressivoIstanze) && StringUtils.isNotBlank(alberoprocTemp.getProgressivoistanze())) {
		    progressivoIstanze = alberoprocTemp.getProgressivoistanze();
		    alberoprocHelper.setProgressivoistanze(progressivoIstanze);
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getResponsabile(), "id.codice") == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getResponsabile(), "id.codice") != null) {
		    Responsabili responsabile = responsabiliService.findById(new PkId(alberoprocTemp.getResponsabile().getId().getCodice()));
		    alberoprocHelper.setResponsabile(responsabile);
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getRespistruttoria(), "id.codice") == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getRespistruttoria(), "id.codice") != null) {
		    Responsabili responsabile = responsabiliService.findById(new PkId(alberoprocTemp.getRespistruttoria().getId().getCodice()));
		    alberoprocHelper.setRespistruttoria(responsabile);
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getOperatoreStc(), "id.codice") == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getOperatoreStc(), "id.codice") != null) {
		    Responsabili responsabile = responsabiliService.findById(new PkId(alberoprocTemp.getOperatoreStc().getId().getCodice()));
		    alberoprocHelper.setOperatoreStc(responsabile);
		}
		if (EntityUtils.getNestedProperty(alberoprocHelper.getTipoProcedura(), "id.codice") == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getTipoProcedura(), "id.codice") != null) {
		    Tipiprocedure procedura = tipiprocedureService.findById(new PkId(alberoprocTemp.getTipoProcedura().getId().getCodice()));
		    alberoprocHelper.setTipoProcedura(procedura);
		    if (alberoprocHelper.getMovAvvio() == null && !procedura.getTipiProcedureavvios().isEmpty()) {
			for (Tipiprocedureavvio avvio : procedura.getTipiProcedureavvios()) {
			    if (BooleanUtils.isTrue(avvio.getDefaultsn())) {
				alberoprocHelper.setMovAvvio(avvio.getTipoMovimento());
				break;
			    }
			}
		    }
		}
		// Recupera la tipologia del registro risalendo l'albero.
		if (EntityUtils.getNestedProperty(alberoprocHelper.getTipologiaregistro(), "id.codice") == null
			&& EntityUtils.getNestedProperty(alberoprocTemp.getTipologiaregistro(), "id.codice") != null) {
		    Tipologiaregistri tipologiaregistri = tipologiaregistriService
			    .findById(new PkId(alberoprocTemp.getTipologiaregistro().getId().getCodice()));
		    alberoprocHelper.setTipologiaregistro(tipologiaregistri);
		}
		if (StringUtils.isNotBlank(alberoprocTemp.getScNote())) {
		    listaScNote.add(alberoprocTemp.getScNote());
		}
		// Verifichiamo se nella catena dal figlio al padre esiste almeno una voce dell'albero che abbia
		// popolato con il valore 1 (Area riservata e frontoffice) o  2 (Solo Area Riservata) il campo "scPubblica" o 4 domanda on line
		if (!presentabileOnline && alberoprocTemp.getScPubblica() != null
			&& (isPresentabileOnline(alberoprocTemp.getScPubblica(), alberoprocTemp.getScAttivo()))) {
		    presentabileOnline = true;
		}
		List<AlberoprocTipisogBack> alberoprocTipisogBackTemps = alberoprocTipisogBackService
			.findByAlberoproc(alberoprocTemp.getId().getCodice());
		if (!alberoprocTipisogBackTemps.isEmpty()) {
		    alberoprocTipisogBacks.addAll(alberoprocTipisogBackTemps);
		}
		// Recupera le informazioni su Gestione norme anticorruzione. verifica la prima voce dell'albero, partendo dalla foglia scelta, in cui è  
		// è popolato il campo gruppiIstruttori.
		if (alberoprocHelper != null && EntityUtils.getNestedProperty(alberoprocHelper.getGruppiIstruttori(), "id.codice") == null) {
		    if (alberoprocTemp != null && EntityUtils.getNestedProperty(alberoprocTemp.getGruppiIstruttori(), "id.codice") != null) {
			GruppiIstruttori gruppiIstruttori = gruppiIstruttoriService
				.findById(new PkId(alberoprocTemp.getGruppiIstruttori().getId().getCodice()));
			alberoprocHelper.setGruppiIstruttori(gruppiIstruttori);
			alberoprocHelper.setGrpFlagAssegnazioneAut(alberoprocTemp.getGrpFlagAssegnazioneAut());
			alberoprocHelper.setGrpFlagAccettazione(alberoprocTemp.getGrpFlagAccettazione());
		    }
		}
		// Popola un oggetto Helper che contiene il percorso della voce dell'albero passata
		PercorsoAlberoprocHelper percorsoAlberoprocHelper = new PercorsoAlberoprocHelper();
		percorsoAlberoprocHelper.setId(alberoprocTemp.getId().getCodice());
		percorsoAlberoprocHelper.setDescrizione(alberoprocTemp.getScDescrizione());
		percorsoAlberoprocHelpers.add(percorsoAlberoprocHelper);
	    }
	    lengthCodice = lengthCodice - 2;
	}
	alberoprocHelper.setAlberoprocLeggis(alberoprocLeggis);
	// Devo prima ordinarlo per ordine
	Collections.sort(alberoprocDocumentis, new AlberoprocDocumentiComparator());
	alberoprocHelper.setAlberoprocDocumentiList(alberoprocDocumentis);
	alberoprocHelper.setAlberoprocDocumentis(alberoprocDocumentisSet);
	alberoprocHelper.setAlberoprocEndos(alberoprocEndos);
	alberoprocHelper.setAlberoprocDyn2modellits(alberoprocDyn2modellits);
	alberoprocHelper.setAlberoprocRuolis(alberoprocRuolis);
	alberoprocHelper.setAlberoprocOneris(alberoprocOneris);
	alberoprocHelper.setAlberoprocAtecos(alberoprocAtecos);
	alberoprocHelper.setAlberoprocD2modtatts(alberoprocD2modtatts);
	alberoprocHelper.setAlberoprocTipisoggettos(alberoprocTipisoggettos);
	alberoprocHelper.setAlberoprocArendos(alberoprocArendos);
	alberoprocHelper.setListNote(listaScNote);
	alberoprocHelper.setPresentabileOnline(presentabileOnline);
	alberoprocHelper.setAlberoprocTipisogBacks(alberoprocTipisogBacks);
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
	    if (configurazione != null && EntityUtils.getNestedProperty(configurazione.getResponsabili(), "id.codice") != null) {
		Responsabili responsabile = responsabiliService.findById(new PkId(configurazione.getResponsabili().getId().getCodice()));
		alberoprocHelper.setResponsabile(responsabile);
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
		Alberoproc alberoprocTemp = alberoprocDAO.findByScCodice(scCodice);
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
    public Alberoproc findByIdAndCurrentSoftware(Integer id, Boolean hideDisabled) {

	Assert.notNull(id, "findByIdAndCurrentSoftware: id alberoproc nullo!");
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", id, Integer.class));
	filterTable.addRestriction(filterRestriction);
	if (BooleanUtils.isTrue(hideDisabled)) {
	    FilterRestriction frScAttivo = new FilterRestriction();
	    frScAttivo.addFilterField(FilterUtils.equals("scAttivo", Boolean.FALSE, Boolean.class));
	    filterTable.addRestriction(frScAttivo);
	}
	List<Alberoproc> list = alberoprocDAO.findByFilterTable(filterTable);
	Alberoproc alberoproc = null;
	if (!list.isEmpty()) {
	    alberoproc = list.get(0);
	    if (BooleanUtils.isTrue(hideDisabled)) {
		boolean isDisabled = this.findSeDisabilitato(alberoproc.getScCodice());
		if (isDisabled) {
		    alberoproc = null;
		}
	    }
	}
	return alberoproc;
    }

    @Override
    public List<Alberoproc> findAlberoprocFigli(String scCodicePadre, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    boolean isPerCalcoloprogressivo) {

	return alberoprocDAO.findAlberoprocFigli(scCodicePadre, soloPrimoLivello, tipoOrdinamento, isPerCalcoloprogressivo);
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
		boolean isPrincipale = alberoprocEndo.getFlagPrincipale() == null ? false : alberoprocEndo.getFlagPrincipale().booleanValue();
		if (isPrincipale && alberoprocEndo.getAzione() != null) {
		    azione = alberoprocEndo.getAzione();
		    break;
		}
	    }
	}
	if (azione == null) {
	    for (AlberoprocEndo alberoprocEndo : endos) {
		if (codiciInventarioSet.contains(alberoprocEndo.getId().getCodiceinventario()) && alberoprocEndo.getAzione() != null
			&& alberoprocEndo.getAzione().getAzAzione().equalsIgnoreCase("+")) {
		    azione = alberoprocEndo.getAzione();
		    break;
		}
	    }
	}
	if (azione == null) {
	    for (AlberoprocEndo alberoprocEndo : endos) {
		if (codiciInventarioSet.contains(alberoprocEndo.getId().getCodiceinventario()) && alberoprocEndo.getAzione() != null
			&& alberoprocEndo.getAzione().getAzAzione().equalsIgnoreCase("-")) {
		    azione = alberoprocEndo.getAzione();
		    break;
		}
	    }
	}
	if (azione == null) {
	    // l'azione non è stata trovata risalire a ritroso a partire dall'alberoproc per trovare l'azione
	    AlberoprocHelper helper = findAlberoprocHelper(alberoproc);
	    azione = helper.getAzione();
	    if (azione == null) {
		// alla fine di tutto torno l'azione di default
		azione = azioniService.findById(CODICE_AZIONE_DEFAULT);
	    }
	}
	return azione;
    }

    @Override
    public void spostaVoceAlbero(Integer sorgente, Integer destinazione) {

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
	    List<Alberoproc> figli = this.findAlberoprocFigli("", true, DAOOrderTypeEnum.ASC, true);
	    if (!figli.isEmpty()) {
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
	List<Alberoproc> figli = this.findAlberoprocFigli(scCodiceDest, true, DAOOrderTypeEnum.ASC, true);
	String scCodiceUltimo = "";
	if (!figli.isEmpty()) {
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
	List<Alberoproc> figliDaAggiornare = this.findAlberoprocFigli(scCodiceSrc, false, DAOOrderTypeEnum.ASC, true);
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

	return findByOperatore(responsabileprocedimento, "responsabileId", firstResult, maxResult);
    }

    @Override
    public List<Alberoproc> findByResponsabileistruttoria(Responsabili responsabileistruttoria, Integer firstResult, Integer maxResult) {

	return findByOperatore(responsabileistruttoria, "respistruttoriaId", firstResult, maxResult);
    }

    @Override
    public List<Alberoproc> findByOperatoreSTC(Responsabili operatoreSTC, Integer firstResult, Integer maxResult) {

	return findByOperatore(operatoreSTC, "operatoreStcId", firstResult, maxResult);
    }

    private List<Alberoproc> findByOperatore(Responsabili responsabile, String propertyPath, Integer firstResult, Integer maxResult) {

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
    public AlberoprocDocumenti findModelloDomandaFO(Integer id) {

	AlberoprocDocumenti modelloDomandaFO = null;
	Alberoproc alberoproc = this.findById(new PkId(id));
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
		Alberoproc alberoprocPadre = this.findByScCodice(sccodicePadre);
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
    public void updateScCodice(Integer codiceAlberoproc, String scCodice) {

	alberoprocDAO.updateScCodice(codiceAlberoproc, scCodice);
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
    public FoArjStepsTestata findFoArjStepsTestata(Integer codice) {

	Alberoproc nodo = this.findById(new PkId(codice));
	List<Alberoproc> padri = new ArrayList<Alberoproc>();
	this.findAlberoprocPadri(padri, nodo.getScCodice());
	for (Alberoproc alberoproc : padri) {
	    if (!EntityUtils.isNestedPropertyBlank(alberoproc, "foArjStepsTestata.id.codice")) {
		return alberoproc.getFoArjStepsTestata();
	    }
	}
	return null;
    }

    @Override
    public boolean checkSePubblicabileSuCART(Integer codiceAlberoproc) {

	String scCodice = "";
	DynaProperty[] properties = { new DynaProperty("scCodice", String.class), new DynaProperty("software_codice", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("AlberoprocDC", null, properties);
	DynaBean alberoproc = alberoprocDAO.findDynaBeanById(ORMHelper.getIdcomune(), codiceAlberoproc, userDynaClass, Alberoproc.class);
	if (alberoproc != null) {
	    boolean condizioneFogliaAlbero = false;
	    boolean condizioneDirettoGenitoreCART = false;
	    boolean condizioneStpendoNoTipologia = true;
	    scCodice = (String) alberoproc.get("scCodice");
	    if (StringUtils.isNotBlank(scCodice)) {
		AlberoprocCommand cmd = this.findAlberoprocFigli(scCodice);
		if (cmd != null && cmd.getChildren() == null || cmd.getChildren().isEmpty()) {
		    // CONDIZIONE È una foglia dell'albero e non una cartella 
		    condizioneFogliaAlbero = true;
		}
		if (scCodice.length() > 2) {
		    Alberoproc padre = this.findByScCodice(StringUtils.left(scCodice, scCodice.length() - 2));
		    if (padre.getStpEndoTipo2s() != null && !padre.getStpEndoTipo2s().isEmpty()) {
			// CONDIZIONE Il diretto genitore abbia un record collegato in STP_ENDO_TIPO2, dal quale recuperare il 47.100R 
			condizioneDirettoGenitoreCART = true;
		    }
		}
		StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(codiceAlberoproc);
		if (stp2 != null && stp2.getStpTipologieEndo2() != null) {
		    // CONDIZIONE Se ha un record collegato in STP_ENDO_TIPO2 non deve avere il campo CODICE_TIPOLOGIA_ENDO
		    // valorizzato ( l'aggiornamento del dizionario prevede la valorizzazione di quel campo) 
		    condizioneStpendoNoTipologia = false;
		}
	    }
	    return condizioneFogliaAlbero && condizioneDirettoGenitoreCART && condizioneStpendoNoTipologia;
	}
	return false;
    }

    @Override
    public void updatePubblicaSuCart(Integer codiceAlberoproc, boolean pubblica) {

	StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(codiceAlberoproc);
	if (stp2 == null) {
	    if (pubblica) {
		Alberoproc ap = this.findById(new PkId(codiceAlberoproc));
		String scCodice = ap.getScCodice();
		if (scCodice.length() > 2) {
		    Alberoproc padre = this.findByScCodice(StringUtils.left(scCodice, scCodice.length() - 2));
		    StpEndoTipo2 sppadre = stpEndoTipo2Service.findbyAlberoproc(padre.getId().getCodice());
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
    public List<InterventoSimpleBean> findListaInterventiSottonodiDi(Integer codiceAlberoproc) {

	if (codiceAlberoproc == null) {
	    throw new BusinessValidationException("il parametro codiceAlberoproc non può essere nullo");
	}
	String scCodice = "";
	if (codiceAlberoproc.intValue() != -1) {
	    scCodice = findScCodice(codiceAlberoproc);
	}
	List<InterventoSimpleBean> list = new ArrayList<InterventoSimpleBean>();
	List<Alberoproc> aps = alberoprocDAO.findAlberoprocFigli(scCodice, true, DAOOrderTypeEnum.ASC, false);
	int pos = 0;
	for (Alberoproc ap : aps) {
	    if (isPubblicaFrontoffice(ap.getScPubblica(), ap.getScAttivo())) {
		InterventoSimpleBean isb = new InterventoSimpleBean();
		isb.setId(ap.getId().getCodice());
		isb.setText(ap.getScDescrizione());
		int c = alberoprocDAO.countAlberoprocFigli(ap.getScCodice(), true);
		isb.setHasChilds(Boolean.valueOf((c > 0)));
		list.add(pos, isb);
		pos++;
	    }
	}
	return list;
    }

    @Override
    public List<Integer> findGerarchiaNodiPadre(Integer codiceAlberoproc, boolean ancheLeVociDisabilitateoNonPubblicate) {

	List<Integer> ret = new ArrayList<Integer>();
	List<InterventoSimpleBean> isbs = findGerarchiaNodiPadre(codiceAlberoproc, false, ancheLeVociDisabilitateoNonPubblicate);
	for (InterventoSimpleBean interventoSimpleBean : isbs) {
	    ret.add(interventoSimpleBean.getId());
	}
	return ret;
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
     *  <option value="4">Pubblica su domanda on line</option>
     * </pre>
     * 
     * @param scPubblica
     * @return
     */
    private boolean isPubblicaFrontoffice(Integer scPubblica, Boolean scAttivo) {

	if (scAttivo != null && scAttivo.booleanValue()) { // se true allora la voce è disabilitata
	    return false;
	}
	if (scPubblica == null) {
	    return true; // EREDITA DAL PADRE
	}
	// "1">Area Riservata, Front Office e Domanda on line
	// "3">Solo Front Office
	// "4">Solo Pubblica su domanda on line
	return (scPubblica.intValue() == 1 || scPubblica.intValue() == 3 || scPubblica.intValue() == 4);
    }

    private boolean isPresentabileOnline(Integer scPubblica, Boolean scAttivo) {

	if (scAttivo != null && scAttivo.booleanValue()) { // se true allora la voce è disabilitata
	    return false;
	}
	if (scPubblica == null) {
	    return false; // EREDITA DAL PADRE
	}
	// "1">Area Riservata, Front Office e Domanda on line 
	// "2">Solo Area riservata
	// "4">Solo Domanda on line
	return (scPubblica.intValue() == 1 || scPubblica.intValue() == 2 || scPubblica.intValue() == 4);
    }

    /**
     * 
     * 
     * @param codiceAlberoproc
     * @param isInversa
     * @return
     */
    private List<InterventoSimpleBean> findGerarchiaNodiPadre(Integer codiceAlberoproc, boolean isInversa,
	    boolean ancheLeVociDisabilitateONonPubblicate) {

	List<InterventoSimpleBean> gerarchia = new ArrayList<InterventoSimpleBean>();
	String scCodice = findScCodice(codiceAlberoproc);
	int pos = 0;
	String scCodicePadre = "";
	if ((isInversa)) {
	    int lengthCodice = scCodice.length();
	    int lengthTree = lengthCodice / 2;
	    for (int i = 0; i < lengthTree; i++) {
		scCodice = scCodice.substring(0, lengthCodice);
		Alberoproc ap = alberoprocDAO.findByScCodice(scCodice);
		if (!ancheLeVociDisabilitateONonPubblicate && !isPubblicaFrontoffice(ap.getScPubblica(), ap.getScAttivo())) {
		    return gerarchia;
		}
		gerarchia.add(pos, newInterventoSimpleBean(ap));
		pos++;
		lengthCodice = lengthCodice - 2;
	    }
	} else {
	    int step = 0;
	    int stepEnd = 2;
	    while (step < scCodice.length()) {
		scCodicePadre = scCodice.substring(0, stepEnd);
		Alberoproc ap = findByScCodice(scCodicePadre);
		if (!ancheLeVociDisabilitateONonPubblicate && !isPubblicaFrontoffice(ap.getScPubblica(), ap.getScAttivo())) {
		    return gerarchia;
		}
		gerarchia.add(pos, newInterventoSimpleBean(ap));
		step += 2;
		stepEnd += 2;
		pos++;
	    }
	}
	return gerarchia;
    }

    private InterventoSimpleBean newInterventoSimpleBean(Alberoproc ap) {

	InterventoSimpleBean isb = new InterventoSimpleBean();
	isb.setId(ap.getId().getCodice());
	isb.setText(ap.getScDescrizione());
	int c = alberoprocDAO.countAlberoprocFigli(ap.getScCodice(), true);
	isb.setHasChilds(Boolean.valueOf((c > 0)));
	return isb;
    }

    @Override
    public List<Integer> findGerarchiaNodiPadreInversa(Integer codiceAlberoproc, boolean ancheLeVociDisabilitateoNonPubblicate) {

	List<Integer> ret = new ArrayList<Integer>();
	List<InterventoSimpleBean> isbs = findGerarchiaNodiPadre(codiceAlberoproc, true, ancheLeVociDisabilitateoNonPubblicate);
	for (InterventoSimpleBean interventoSimpleBean : isbs) {
	    ret.add(interventoSimpleBean.getId());
	}
	return ret;
    }

    @Override
    public List<InterventoSimpleBean> findGerarchiaDettaglioNodiPadre(Integer codiceIntervento, boolean ancheLeVociDisabilitateoNonPubblicate) {

	return findGerarchiaNodiPadre(codiceIntervento, false, ancheLeVociDisabilitateoNonPubblicate);
    }

    private String findScCodice(Integer codiceAlberoproc) {

	DynaProperty[] properties = { new DynaProperty("scCodice", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("AlberoprocDC", null, properties);
	DynaBean albp = alberoprocDAO.findDynaBeanById(ORMHelper.getIdcomune(), codiceAlberoproc, userDynaClass, Alberoproc.class);
	String scCodice = (String) albp.get("scCodice");
	if (StringUtils.isBlank(scCodice)) {
	    throw new BusinessValidationException("Non è stato trovato l'intervento con codice " + codiceAlberoproc);
	}
	return scCodice;
    }

    @Override
    public InterventoBean findInterventoBean(Integer codiceAlberoproc) {

	InterventoBean result = new InterventoBean();
	Alberoproc ap = findById(new PkId(codiceAlberoproc));
	result.setId(codiceAlberoproc);
	result.setInformazioni(ap.getScNote());
	result.setNome(ap.getScDescrizione());
	AlberoprocHelper ah = findAlberoprocHelper(ap);
	log.debug("findInterventoBean# Popolo il campo presentabileOnline");
	// se nella gerarchia esiste un voce con scPubblica= 1 (Area riservata e frontoffice) || 2 (Solo Area Riservata) allora true, altrimenti false.
	result.setPresentabileOnline(ah.getPresentabileOnline());
	if (log.isDebugEnabled()) {
	    log.debug("findInterventoBean# recupero le normative");
	}
	log.debug("findInterventoBean# recupero le note risalendo dalla foglia al padre");
	List<String> listScNote = ah.getListNote();
	String informazioni = StringUtils.defaultString(result.getInformazioni());
	if (!listScNote.isEmpty()) {
	    if (result.getNote() == null) {
		result.setNote(new ArrayList<String>());
	    }
	    for (String nota : listScNote) {
		if (!informazioni.equalsIgnoreCase(StringUtils.defaultString(nota))) {
		    // ci metto solamente le informazioni che non riguardano al stessa voce dell'albero
		    result.getNote().add(nota);
		}
	    }
	}
	Set<AlberoprocLeggi> leggis = ah.getAlberoprocLeggis();
	if (!leggis.isEmpty()) {
	    List<NormativaBean> normatives = new ArrayList<NormativaBean>();
	    for (AlberoprocLeggi al : leggis) {
		if (al.getLegge() != null) {
		    NormativaBean n = new NormativaBean();
		    n.setDescrizione(al.getLegge().getLeDescrizione());
		    n.setLink(al.getLegge().getLeLink());
		    if (al.getLegge().getOggetto() != null) {
			Integer codiceOggetto = al.getLegge().getOggetto().getId().getCodice();
			if (codiceOggetto != null) {
			    n.setCodiceOggetto(oggettiService.insertOrGetUID(codiceOggetto));
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
	log.debug("findInterventoBean# recupero le fasi attuative dalla procedura");
	Tipiprocedure tp = ah.getTipoProcedura();
	if (tp != null) {
	    Set<Subprocedure> fasi = tp.getSubprocedures();
	    if (!fasi.isEmpty()) {
		List<FasiattuativeBean> fasiAttuative = new ArrayList<FasiattuativeBean>();
		for (Subprocedure sp : fasi) {
		    FasiattuativeBean fa = new FasiattuativeBean();
		    fa.setDescrizione(sp.getSubprocedura());
		    fa.setTitolo(sp.getTitolosubprocedura());
		    fasiAttuative.add(fa);
		}
		result.setFasiAttuative(fasiAttuative);
	    }
	}
	log.debug("findInterventoBean# carico la modulistica");
	List<AlberoprocDocumenti> ads = ah.getAlberoprocDocumentiList();
	Boolean modelloDomandaPresente = false;
	if (!ads.isEmpty()) {
	    List<ModulisticaBean> mod = new ArrayList<ModulisticaBean>();
	    for (AlberoprocDocumenti apd : ads) {
		int pubblica = 0;
		if (apd.getPubblica() != null) {
		    pubblica = apd.getPubblica().intValue();
		}
		if (pubblica == 1 || pubblica == 3 /* || pubblica == 2*/) { //1= Area riservata e frontoffice, 3=Solofrontoffice, 
		    // 2= Solo Area Riservata 20180628 BOCCI DOPO TELEFONATA LIVORNO NON DEVONO ESSERE PUBBLICATI I DOCUMENTI SOLO AREA RISERVATA
		    ModulisticaBean m = new ModulisticaBean();
		    m.setDescrizione(apd.getDescrizione());
		    m.setObbligatorio(apd.getRichiesto() == null ? Boolean.FALSE : apd.getRichiesto().booleanValue());
		    if (apd.getOggetto() != null) {
			Integer codiceOggetto = apd.getOggetto().getId().getCodice();
			if (codiceOggetto != null) {
			    String uid = oggettiService.insertOrGetUID(codiceOggetto);
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
			    // Controllo se l'oggetto ha il flgDomandafo == true, una volta che trovo nella gerarchina
			    // un documento con il flag a true non devo più entrare e la mia variabile 
			    // dovrà rimanere settata a true
			    if (!modelloDomandaPresente && BooleanUtils.toBoolean(apd.getFlgDomandafo())) {
				log.debug("findInterventoBean# E' presente un documento nella gerarchia con il flag flgDomandafo == true");
				modelloDomandaPresente = true;
			    }
			}
		    }
		    if (pubblica == 1 || pubblica == 3) { // Devono essere mostrati solo i documenti - 1= Area riservata e frontoffice, 3=Solofrontoffice
			mod.add(m);
		    }
		}
	    }
	    result.setModulistica(mod);
	    result.setModelloDomandaPresente(modelloDomandaPresente);
	}
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
	Set<Integer> principali = new HashSet<Integer>();
	for (AlberoprocEndo ape : endos) {
	    boolean pubblica = ape.getFlagPubblica() == null ? false : ape.getFlagPubblica().booleanValue();
	    // Devo controllare se l'invetario procedimento è configurato come pubblica sull'albero proc 
	    // e che inventario procedimento il tipo endo e la famiglia endo a cui appartiene sono 
	    // configurati a pubblica==true
	    if (pubblica && checkPubblicaEndoDaAlberoProc(ape)) {
		boolean necessario = ape.getFlagRichiesto() == null ? false : ape.getFlagRichiesto().booleanValue();
		boolean principale = ape.getFlagPrincipale() == null ? false : ape.getFlagPrincipale().booleanValue();
		if (necessario) {
		    InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
		    wip.setId(ape.getInventarioprocedimento().getId());
		    wip.setIp(ape.getInventarioprocedimento());
		    wip.setIntervento(principale);
		    endoNecessari.add(wip);
		} else {
		    InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
		    wip.setId(ape.getInventarioprocedimento().getId());
		    wip.setIp(ape.getInventarioprocedimento());
		    wip.setIntervento(principale);
		    endoRicorrenti.add(wip);
		}
		if (principale) {
		    principali.add(ape.getInventarioprocedimento().getId().getCodice());
		}
	    }
	}
	EndoprocedimentiHelper epnh = new EndoprocedimentiHelper(inventarioprocedimentiService, inventarioprocEndoService);
	List<FamiglieEndoBean> endoNecessariModel = epnh.elaboraEndo(endoNecessari, principali);
	List<FamiglieEndoBean> endoRicorrentiModel = epnh.elaboraEndo(endoRicorrenti, principali);
	result.setProcedimentiNecessari(endoNecessariModel);
	result.setProcedimentiRicorrenti(endoRicorrentiModel);
	// recupero procedimenti eventuali  
	String scCodice = ap.getScCodice();
	int lengthCodice = scCodice.length();
	int lengthTree = lengthCodice / 2;
	List<AlberoprocArendo> ars = new ArrayList<AlberoprocArendo>();
	for (int i = 0; i < lengthTree; i++) {
	    scCodice = scCodice.substring(0, lengthCodice);
	    Alberoproc alberoprocTemp = alberoprocDAO.findByScCodice(scCodice);
	    if (alberoprocTemp != null) {
		ars = alberoprocArendoService.findByAlberoProc(alberoprocTemp.getId().getCodice());
		if (!ars.isEmpty()) {
		    break;
		}
	    }
	    lengthCodice = lengthCodice - 2;
	}
	Map<Integer, InventarioprocedimentiWrapper> endoEventualisM = new HashMap<Integer, InventarioprocedimentiWrapper>();
	if (!ars.isEmpty()) {
	    for (AlberoprocArendo aare : ars) {
		Integer codiceTipoEndo = null;
		Integer codiceFamigliaEndo = null;
		if (aare.getTipiendo() != null) {
		    codiceTipoEndo = aare.getTipiendo().getId().getCodice();
		} else if (aare.getTipifamiglieendo() != null) {
		    codiceFamigliaEndo = aare.getTipifamiglieendo().getId().getCodice();
		}
		List<Inventarioprocedimenti> endosT = null;
		if (codiceTipoEndo != null) {
		    endosT = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologia(null, null, codiceTipoEndo, true);
		} else if (codiceFamigliaEndo != null) {
		    endosT = inventarioprocedimentiService.findByDescrizioneFamigliaendoETipologia(null, codiceFamigliaEndo, null, true);
		}
		if (endosT != null) {
		    for (Inventarioprocedimenti ip : endosT) {
			InventarioprocedimentiWrapper wip = new InventarioprocedimentiWrapper();
			wip.setId(ip.getId());
			wip.setIp(ip);
			endoEventualisM.put(ip.getId().getCodice(), wip);
		    }
		}
	    }
	}
	if (!endoEventualisM.isEmpty()) {
	    Set<InventarioprocedimentiWrapper> endoEventuali = new HashSet<InventarioprocedimentiWrapper>();
	    for (Map.Entry<Integer, InventarioprocedimentiWrapper> m : endoEventualisM.entrySet()) {
		endoEventuali.add(m.getValue());
	    }
	    List<FamiglieEndoBean> endoEventualiModel = epnh.elaboraEndo(endoEventuali, null);
	    result.setProcedimentiEventuali(endoEventualiModel);
	}
	// end 
	// 
	log.debug("findInterventoBean# Gestione degli oneri");
	Set<AlberoprocOneri> oneris = ah.getAlberoprocOneris();
	if (!oneris.isEmpty()) {
	    List<OneriBean> oneri = new ArrayList<OneriBean>();
	    for (AlberoprocOneri ao : oneris) {
		if (ao.getTipicausalioneri() != null && ao.getAoImportocausale() != null) {
		    OneriBean o = new OneriBean();
		    o.setCausale(ao.getTipicausalioneri().getCoDescrizione());
		    o.setImporto(ao.getAoImportocausale().doubleValue());
		    o.setNote(ao.getNote());
		    oneri.add(o);
		}
	    }
	    result.setOneri(oneri);
	}
	return result;
    }

    private boolean checkPubblicaEndoDaAlberoProc(AlberoprocEndo ape) {

	boolean pubb = false;
	// Controllo l'invetario procedimento
	if (BooleanUtils.isTrue(ape.getInventarioprocedimento().getFlagPubblica())) {
	    log.debug("checkPubblicaEndoDaAlberoProc# Inventarioprocedimento {}  valore flagPubblica = {} ",
		    new Object[] { ape.getInventarioprocedimento().getProcedimento(), ape.getInventarioprocedimento().getFlagPubblica() });
	    pubb = true;
	    if (ape.getInventarioprocedimento().getTipoendo() != null) {
		if (BooleanUtils.isTrue(ape.getInventarioprocedimento().getTipoendo().getFlagPubblica())) {
		    log.debug("checkPubblicaEndoDaAlberoProc# Inventarioprocedimento {}  del  tipo endo {}  con valore flagPubblica = {} ",
			    new Object[] { ape.getInventarioprocedimento().getProcedimento(), ape.getInventarioprocedimento().getTipoendo().getTipo(),
				ape.getInventarioprocedimento().getTipoendo().getFlagPubblica() });
		    if (ape.getInventarioprocedimento().getTipoendo().getTipifamiglieendo() != null) {
			if (BooleanUtils.isTrue(ape.getInventarioprocedimento().getTipoendo().getTipifamiglieendo().getFlagPubblica())) {
			    log.debug(
				    "checkPubblicaEndoDaAlberoProc# Inventarioprocedimento {}  della tipo famiglia  {}  ha valore flagPubblica = {} ",
				    new Object[] { ape.getInventarioprocedimento().getProcedimento(),
					ape.getInventarioprocedimento().getTipoendo().getTipo(),
					ape.getInventarioprocedimento().getTipoendo().getFlagPubblica() });
			    return pubb;
			} else {
			    return false;
			}
		    } else {
			return pubb;
		    }
		} else {
		    return false;
		}
	    } else {
		log.debug(
			"checkPubblicaEndoDaAlberoProc# Inventarioprocedimento {} non pubblicato, non ha un tipo  tipo endo configurato. Pubblica = {} ",
			new Object[] { ape.getInventarioprocedimento().getProcedimento(), pubb });
		return pubb;
	    }
	} else {
	    log.debug("checkPubblicaEndoDaAlberoProc# Inventarioprocedimento {} non pubblicato, valore flagPubblica = false ",
		    ape.getInventarioprocedimento().getProcedimento());
	}
	return pubb;
    }

    @Override
    public List<InterventoSimpleBean> findInterventiByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca, Integer firstResult,
	    Integer maxResults) {

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
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
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
		    // Nuova ricerca per parole chiave
		    fr.addFilterField(FilterUtils.like("paroleChiave", v));
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
		// Nuova ricerca per parole chiave
		fr.addFilterField(FilterUtils.like("paroleChiave", testoDaCercare));
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
		    // Nuova ricerca per parole chiave
		    fr.addFilterField(FilterUtils.like("paroleChiave", v));
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
	    boolean isDaPubblicare = checkIsDaPubblicare(ap.getId().getCodice());
	    if (isDaPubblicare) {
		InterventoSimpleBean isb = new InterventoSimpleBean();
		isb.setId(ap.getId().getCodice());
		isb.setText(ap.getScDescrizione());
		isb.setNote(ap.getScNote());
		int c = alberoprocDAO.countAlberoprocFigli(ap.getScCodice(), true);
		isb.setHasChilds(Boolean.valueOf((c > 0)));
		list.add(pos, isb);
		pos++;
	    }
	}
	Collections.sort(list, new InterventoSimpleBeanComparator());
	return list;
    }

    /**
     * Il metodo controlla se per quella voce dell'albero esiste un livello gerarchicamente superiore della catena che
     * ha la proprieta scpubblica == 0 (Non pubblicare)
     * 
     * @param scAttivo
     * @return
     */
    private boolean checkIsDaPubblicare(Integer codiceAlberoProc) {

	List<Integer> listCodiceAlberoProc = this.findGerarchiaNodiPadre(codiceAlberoProc, false);
	if (listCodiceAlberoProc != null) {
	    return listCodiceAlberoProc.contains(codiceAlberoProc);
	}
	return false;
    }

    //    
    @Override
    public String findDescrizioneInterventoFromLivello(Integer codiceInteventoPartenza, Integer livello) {

	StringBuilder descrizione = new StringBuilder();
	List<Integer> codiciInterventi = findGerarchiaNodiPadreInversa(codiceInteventoPartenza, true);
	int ultimoElemento = codiciInterventi.size() >= livello ? livello : codiciInterventi.size();
	int countLivelli = 1;
	for (int i = ultimoElemento - 1; i >= 0; i--) {
	    if (countLivelli <= livello) {
		Alberoproc alberoproc = this.findById(new PkId(codiciInterventi.get(i)));
		descrizione.append(alberoproc.getScDescrizione()).append(" - ");
	    }
	    countLivelli++;
	}
	String res = "";
	int lastCaratherIndex = StringUtils.lastIndexOf(descrizione.toString(), "-");
	if (StringUtils.isNotBlank(descrizione.toString())) {
	    res = descrizione.toString().substring(0, lastCaratherIndex).trim();
	}
	return res;
    }

    @Override
    public String findGerarchiaAlberoGruppi(Integer idAlberoproc, Integer codiceGruppo) {

	Alberoproc ap = this.findById(new PkId(idAlberoproc));
	if (ap == null) {
	    throw new RuntimeException("Intervento NON trovato con codice " + idAlberoproc);
	}
	if (ap.getGruppiIstruttori() != null && ap.getGruppiIstruttori().getId() != null && ap.getGruppiIstruttori().getId().getCodice() != null) {
	    if (ap.getGruppiIstruttori().getId().getCodice().equals(codiceGruppo)) {
		return ap.getScCodice();
	    }
	}
	String scCodice = ap.getScCodice();
	if (scCodice.length() == 2) {
	    return null;
	}
	if (scCodice.length() > 2) {
	    scCodice = scCodice.substring(0, (scCodice.length() - 2));
	}
	Alberoproc ap2 = this.findByScCodice(scCodice);
	return findGerarchiaAlberoGruppi(ap2.getId().getCodice(), codiceGruppo);
    }

    @Override
    public List<AlberoprocHelper> findModelliTIstanzaEreditati(String scCodiceInterveto) {

	List<AlberoprocHelper> alberoprocHelpers = new ArrayList<AlberoprocHelper>();
	AlberoprocHelper helper = null;
	String scCodice = scCodiceInterveto;
	int lengthCodice = scCodice.length();
	int lengthTree = lengthCodice / 2;
	for (int i = 0; i < lengthTree - 1; i++) {
	    helper = new AlberoprocHelper();
	    lengthCodice = lengthCodice - 2;
	    String sccodicePadre = scCodice.substring(0, lengthCodice);
	    Alberoproc alberoprocTemp = this.findByScCodice(sccodicePadre);
	    if (alberoprocTemp.getAlberoprocDyn2modellits() != null && !alberoprocTemp.getAlberoprocDyn2modellits().isEmpty()) {
		helper.setCurrentAlberoproc(alberoprocTemp);
		helper.setAlberoprocDyn2modellits(alberoprocTemp.getAlberoprocDyn2modellits());
		alberoprocHelpers.add(helper);
	    }
	}
	Collections.reverse(alberoprocHelpers);
	return alberoprocHelpers;
    }

    @Override
    public int countByAmministrazioni(Integer codiceAmministrazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	ft.addRestriction(fr);
	return alberoprocDAO.countRecord(ft);
    }

    @Override
    public List<AlberoprocCommand> findAlberoprocHierarchyPerRuoli(Integer codiceResponsabile) {

	List<AlberoprocCommand> resultList = new ArrayList<AlberoprocCommand>();
	resultList = populateAlberoprocHierarchy(null);
	Set<Integer> vociPerRuoliDelResponsabile = alberoprocRuoliService.trovaVociPerRuoliDelResponsabile(codiceResponsabile);
	Set<String> scCodici = new HashSet<String>();
	//Popolo gli scCodice per i quali devo andare a cercare i padri e i figli
	for (AlberoprocCommand apc : resultList) {
	    if (vociPerRuoliDelResponsabile.contains(apc.getId())) {
		scCodici.add(apc.getCodice());
	    }
	}
	// le voci da togliere in quanto non presenti nella gerarchia scCodici
	Set<Integer> riferimentiDaTogliere = new HashSet<Integer>();
	for (AlberoprocCommand apc : resultList) {
	    String scCodiceVoce = apc.getCodice();
	    for (String codice : scCodici) {
		if (!(scCodiceVoce.startsWith(codice) /*i figli*/
			|| scCodiceVoce.equals(codice) /*se stessa*/
			|| verificaSePadre(codice, scCodiceVoce))) {
		    riferimentiDaTogliere.add(apc.getId());
		} else {
		    riferimentiDaTogliere.remove(apc.getId());
		    break;
		}
	    }
	}
	Iterator<AlberoprocCommand> it = resultList.iterator();
	while (it.hasNext()) {
	    AlberoprocCommand apc = it.next();
	    Integer codice = apc.getId();
	    boolean rimosso = false;
	    if (riferimentiDaTogliere.contains(codice)) {
		it.remove();
		rimosso = true;
	    }
	    if (!rimosso && apc.getChildren() != null) {
		Iterator<AlberoprocChildrenCommand> itChilds = apc.getChildren().iterator();
		while (itChilds.hasNext()) {
		    AlberoprocChildrenCommand figlio = itChilds.next();
		    if (riferimentiDaTogliere.contains(figlio.get_reference())) {
			itChilds.remove();
		    }
		}
	    }
	}
	return resultList;
    }

    private boolean verificaSePadre(String codicePadre, String scCodiceVoce) {

	//	Es scCodiceVoce = 050302
	//	Es codicePadre = 03 == false
	//	Es codicePadre = 05 == true
	//	Es codicePadre = 0502 == false
	//	Es codicePadre = 0503 == true
	Set<String> gerarchia = new HashSet<String>();
	String scCodice = scCodiceVoce;
	String codiceDaValutare = codicePadre;
	if (codicePadre.length() > scCodiceVoce.length()) {
	    scCodice = codicePadre;
	    codiceDaValutare = scCodiceVoce;
	}
	while (scCodice.length() >= 2) {
	    scCodice = scCodice.subSequence(0, scCodice.length() - 2).toString();
	    gerarchia.add(scCodice);
	}
	return gerarchia.contains(codiceDaValutare);
    }
}
