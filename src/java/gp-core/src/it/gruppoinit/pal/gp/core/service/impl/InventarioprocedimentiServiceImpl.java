/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.EndoConti;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.Testiestesi;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.InventarioprocedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DownloadBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.EndoprocedimentoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ModulisticaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NormativaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.OneriBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoSimpleBeanComparator;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.EndoCausaliService;
import it.gruppoinit.pal.gp.core.service.EndoContiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocLeggiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocTipititoloService;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiincompService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentioneriService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentipeopleService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeoneriDettaglioService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;
import it.gruppoinit.pal.gp.core.service.TestiestesiService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;
import it.gruppoinit.pal.gp.core.service.helper.InventarioprocedimentiFilter;
import it.gruppoinit.pal.gp.core.service.helper.VerticalizzazioneFvgSol;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
 * @author francescop
 * @author gianpaolot
 * 
 */
@Service
public class InventarioprocedimentiServiceImpl extends BaseServiceImpl<Inventarioprocedimenti, PkId> implements InventarioprocedimentiService {

    private static final Logger log = LoggerFactory.getLogger(InventarioprocedimentiServiceImpl.class);
    private InventarioprocedimentiDAO inventarioprocedimentiDAO;
    private InventarioprocedimentipeopleService inventarioprocedimentipeopleService;
    private AllegatiService allegatiService;
    private TestiestesiService testiestesiService;
    private DocumentiService documentiService;
    private InventarioprocedimentioneriService inventarioprocedimentioneriService;
    private Inventarioprocdyn2modellitService inventarioprocdyn2modellitService;
    private EndoCausaliService endoCausaliService;
    private EndoContiService endoContiService;
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    private InventarioprocedimentiincompService inventarioprocedimentiincompService;
    private AmministrazioniService amministrazioniService;
    private AmministrazionireferentiService amministrazionireferentiService;
    private NaturaendoService naturaendoService;
    private StpEndoTipo1Service stpEndoTipo1Service;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private TempificazioniService tempificazioniService;
    private TipiMovimentoService tipiMovimentoService;
    private TipiendoService tipiendoService;
    private TipifamiglieendoService tipifamiglieendoService;
    private InventarioprocLeggiService inventarioprocLeggiService;
    private MovimentiService movimentiService;
    private IstanzeoneriDettaglioService istanzeoneriDettaglioService;
    private IstanzeallegatiService istanzeallegatiService;
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private InventarioprocTipititoloService inventarioprocTipititoloService;
    private VerticalizzazioniService verticalizzazioniService;
    private InventarioprocEndoService inventarioprocEndoService;

    @Autowired
    public void setInventarioprocEndoService(InventarioprocEndoService inventarioprocEndoService) {

	this.inventarioprocEndoService = inventarioprocEndoService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setTipifamiglieendoService(TipifamiglieendoService tipifamiglieendoService) {

	this.tipifamiglieendoService = tipifamiglieendoService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setIstanzeoneriDettaglioService(IstanzeoneriDettaglioService istanzeoneriDettaglioService) {

	this.istanzeoneriDettaglioService = istanzeoneriDettaglioService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setIstanzeprocedimentiService(IstanzeprocedimentiService istanzeprocedimentiService) {

	this.istanzeprocedimentiService = istanzeprocedimentiService;
    }

    @Autowired
    public void setNaturaendoService(NaturaendoService naturaendoService) {

	this.naturaendoService = naturaendoService;
    }

    @Autowired
    public void setStpEndoTipo1Service(StpEndoTipo1Service stpEndoTipo1Service) {

	this.stpEndoTipo1Service = stpEndoTipo1Service;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setTempificazioniService(TempificazioniService tempificazioniService) {

	this.tempificazioniService = tempificazioniService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setTipiendoService(TipiendoService tipiendoService) {

	this.tipiendoService = tipiendoService;
    }

    @Autowired
    public void setAmministrazionireferentiService(AmministrazionireferentiService amministrazionireferentiService) {

	this.amministrazionireferentiService = amministrazionireferentiService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setEndoContiService(EndoContiService endoContiService) {

	this.endoContiService = endoContiService;
    }

    @Autowired
    public void setInventarioprocedimentiincompService(InventarioprocedimentiincompService inventarioprocedimentiincompService) {

	this.inventarioprocedimentiincompService = inventarioprocedimentiincompService;
    }

    @Autowired
    public void setInventarioprocedimentisoftwareService(InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService) {

	this.inventarioprocedimentisoftwareService = inventarioprocedimentisoftwareService;
    }

    @Autowired
    public void setEndoCausaliService(EndoCausaliService endoCausaliService) {

	this.endoCausaliService = endoCausaliService;
    }

    @Autowired
    public void setInventarioprocdyn2modellitService(Inventarioprocdyn2modellitService inventarioprocdyn2modellitService) {

	this.inventarioprocdyn2modellitService = inventarioprocdyn2modellitService;
    }

    @Autowired
    public void setInventarioprocedimentioneriService(InventarioprocedimentioneriService inventarioprocedimentioneriService) {

	this.inventarioprocedimentioneriService = inventarioprocedimentioneriService;
    }

    @Autowired
    public void setDocumentiService(DocumentiService documentiService) {

	this.documentiService = documentiService;
    }

    @Autowired
    public void setTestiestesiService(TestiestesiService testiestesiService) {

	this.testiestesiService = testiestesiService;
    }

    @Autowired
    public void setInventarioprocedimentiDAO(InventarioprocedimentiDAO inventarioprocedimentiDAO) {

	this.inventarioprocedimentiDAO = inventarioprocedimentiDAO;
    }

    @Autowired
    public void setInventarioprocedimentipeopleService(InventarioprocedimentipeopleService inventarioprocedimentipeopleService) {

	this.inventarioprocedimentipeopleService = inventarioprocedimentipeopleService;
    }

    @Autowired
    public void setAllegatiService(AllegatiService allegatiService) {

	this.allegatiService = allegatiService;
    }

    @Autowired
    public void setInventarioprocLeggiService(InventarioprocLeggiService inventarioprocLeggiService) {

	this.inventarioprocLeggiService = inventarioprocLeggiService;
    }

    @Autowired
    public void setInventarioprocTipititoloService(InventarioprocTipititoloService inventarioprocTipititoloService) {

	this.inventarioprocTipititoloService = inventarioprocTipititoloService;
    }

    @Override
    protected Class<Inventarioprocedimenti> getEntityClass() {

	return Inventarioprocedimenti.class;
    }

    @Override
    public void delete(Inventarioprocedimenti entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    inventarioprocedimentiDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Inventarioprocedimenti entity) {

	Set<Inventarioprocedimentipeople> inventarioprocedimentipeoples = entity.getInventarioprocedimentipeoples();
	for (Inventarioprocedimentipeople inventarioprocedimentipeople : inventarioprocedimentipeoples) {
	    inventarioprocedimentipeopleService.delete(inventarioprocedimentipeople);
	}
	Set<InventarioprocLeggi> inventarioprocLeggis = entity.getInventarioprocLeggis();
	for (InventarioprocLeggi inventarioprocLeggi : inventarioprocLeggis) {
	    inventarioprocLeggiService.delete(inventarioprocLeggi);
	}
	Set<Allegati> allegatis = entity.getAllegatis();
	for (Allegati allegati : allegatis) {
	    allegatiService.delete(allegati);
	}
	Set<Documenti> documentis = entity.getDocumentis();
	for (Documenti documenti : documentis) {
	    documentiService.delete(documenti);
	}
	Set<Inventarioprocedimentiincomp> inventarioprocedimentiincomps = entity.getInventarioprocedimentiincomps();
	for (Inventarioprocedimentiincomp inventarioprocedimentiincomp : inventarioprocedimentiincomps) {
	    inventarioprocedimentiincompService.delete(inventarioprocedimentiincomp);
	}
	Set<Inventarioprocedimentiincomp> inventarioprocedimentiincompcols = entity.getInventarioprocedimentiincompsCol();
	for (Inventarioprocedimentiincomp inventarioprocedimentiincompcol : inventarioprocedimentiincompcols) {
	    inventarioprocedimentiincompService.delete(inventarioprocedimentiincompcol);
	}
	Set<Testiestesi> testiestesis = entity.getTestiestesis();
	for (Testiestesi testiestesi : testiestesis) {
	    testiestesiService.delete(testiestesi);
	}
	Set<Inventarioprocdyn2modellit> inventarioprocdyn2modellits = entity.getInventarioprocdyn2modellits();
	for (Inventarioprocdyn2modellit inventarioprocdyn2modellit : inventarioprocdyn2modellits) {
	    inventarioprocdyn2modellitService.delete(inventarioprocdyn2modellit);
	}
	Set<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares = entity.getInventarioprocedimentisoftwares();
	for (Inventarioprocedimentisoftware inventarioprocedimentisoftware : inventarioprocedimentisoftwares) {
	    inventarioprocedimentisoftwareService.delete(inventarioprocedimentisoftware);
	}
	Set<Inventarioprocedimentioneri> inventarioprocedimentioneris = entity.getInventarioprocedimentioneris();
	for (Inventarioprocedimentioneri inventarioprocedimentioneri : inventarioprocedimentioneris) {
	    inventarioprocedimentioneriService.delete(inventarioprocedimentioneri);
	}
	Set<EndoCausali> endoCausalis = entity.getEndoCausalis();
	for (EndoCausali endoCausali : endoCausalis) {
	    endoCausaliService.delete(endoCausali);
	}
	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(entity);
	if (stpEndoTipo1 != null) {
	    stpEndoTipo1Service.delete(stpEndoTipo1);
	}
	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findByInventarioproc(entity.getId().getCodice());
	if (stpEndoTipo2 != null) {
	    stpEndoTipo2Service.delete(stpEndoTipo2);
	}
	Set<InventarioprocTipititolo> inventarioprocTipititolos = entity.getInventarioprocTipititolos();
	for (InventarioprocTipititolo inventarioprocTipititolo : inventarioprocTipititolos) {
	    inventarioprocTipititoloService.delete(inventarioprocTipititolo);
	}
	List<InventarioprocEndo> inventarioprocEndos = inventarioprocEndoService.findByInventarioprocT(entity.getId().getIdcomune(),
		entity.getId().getCodice(), null, null, null, null, false);
	for (InventarioprocEndo inventarioprocEndo : inventarioprocEndos) {
	    inventarioprocEndoService.delete(inventarioprocEndo);
	}
    }

    @Override
    public List<Inventarioprocedimenti> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocedimentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Inventarioprocedimenti findById(PkId id) {

	return inventarioprocedimentiDAO.findById(id);
    }

    @Override
    public void insert(Inventarioprocedimenti entity) {

	if (isInsertAllowed(entity)) {
	    dataIntegration(entity);
	    if (validateEntity(entity)) {
		inventarioprocedimentiDAO.insert(entity);
	    }
	}
    }

    private boolean isInsertAllowed(Inventarioprocedimenti entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	VerticalizzazioneFvgSol verticalizzazioneFvgSol = new VerticalizzazioneFvgSol(verticalizzazioniService, true);
	if (verticalizzazioneFvgSol.isConsolleAttiva()) {
	    log.debug("isInsertAllowed# Attivata modalità console FVG");
	    log.debug(
		    "isInsertAllowed# Controllo che in verticalizzazione sia configurati i parametri AMMINISTRAZIONE_ENDO,NATURA_ENDO,TEMPIFICAZIONE_ENDO..");
	    log.debug("isInsertAllowed# Controllo AMMINISTRAZIONE_ENDO");
	    if (StringUtils.isNotBlank(verticalizzazioneFvgSol.getAMMINISTRAZIONE_ENDO_DEFAULT())) {
		Integer codice = null;
		try {
		    codice = Integer.parseInt(verticalizzazioneFvgSol.getAMMINISTRAZIONE_ENDO_DEFAULT());
		    Amministrazioni amm = amministrazioniService.findById(new PkId(codice));
		    if (EntityUtils.getNestedProperty(amm, "id.codice") == null) {
			log.error("getCodiceDaParametroVerticalizzazione#Amministrazione non trovata per il codice {} ", codice);
			String m = getMessageFromBundle("service_error.invetarioprocedimento_no_valore_default",
				new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_AMMINISTRAZIONE_ENDO_DEFAULT,
					verticalizzazioneFvgSol.getAMMINISTRAZIONE_ENDO_DEFAULT() });
			_ivs.add(new InvalidValue(m, null, null, null, null));
		    } else {
			entity.setAmministrazioni(amm);
		    }
		} catch (NumberFormatException ne) {
		    log.error("getCodiceDaParametroVerticalizzazione#Errore nella conversione in Integer del valore del parametro {}",
			    verticalizzazioneFvgSol.getAMMINISTRAZIONE_ENDO_DEFAULT());
		    String m = getMessageFromBundle("service_error.invetarioprocedimento_codice_non_numerico",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_AMMINISTRAZIONE_ENDO_DEFAULT,
				    verticalizzazioneFvgSol.getAMMINISTRAZIONE_ENDO_DEFAULT() });
		    _ivs.add(new InvalidValue(m, null, null, null, null));
		}
	    } else {
		log.error("getCodiceDaParametroVerticalizzazione# Valore del parametro AMMINISTRAZIONE_ENDO non configurato");
		String m = getMessageFromBundle("service_error.invetarioprocedimento_codice_non configurato",
			new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_AMMINISTRAZIONE_ENDO_DEFAULT });
		_ivs.add(new InvalidValue(m, null, null, null, null));
	    }
	    //.............
	    log.debug("isInsertAllowed# Controllo CODICENATURA_ENDO");
	    if (StringUtils.isNotBlank(verticalizzazioneFvgSol.getCODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT())) {
		Integer codice = null;
		try {
		    codice = Integer.parseInt(verticalizzazioneFvgSol.getCODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT());
		    Naturaendo nEndo = naturaendoService.findById(new NaturaendoId(codice));
		    if (EntityUtils.getNestedProperty(nEndo, "id.codice") == null) {
			log.error("getCodiceDaParametroVerticalizzazione#Natura endo non trovata per il codice {} ", codice);
			String m = getMessageFromBundle("service_error.invetarioprocedimento_no_valore_default",
				new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_CODICENATURA_ENDO_DEFAULT, codice });
			_ivs.add(new InvalidValue(m, null, null, null, null));
		    } else {
			entity.setNaturaendo(nEndo);
		    }
		} catch (NumberFormatException ne) {
		    log.error("getCodiceDaParametroVerticalizzazione#Errore nella conversione in Integer del valore del parametro {}",
			    WebConstants.VERTICALIZZAZIONE_FVG_SOL_CODICENATURA_ENDO_DEFAULT);
		    String m = getMessageFromBundle("service_error.invetarioprocedimento_codice_non_numerico",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_CODICENATURA_ENDO_DEFAULT,
				    verticalizzazioneFvgSol.getCODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT() });
		    _ivs.add(new InvalidValue(m, null, null, null, null));
		}
	    } else {
		log.error("getCodiceDaParametroVerticalizzazione# Valore del parametro NATURA_ENDO non configurato");
		String m = getMessageFromBundle("service_error.invetarioprocedimento_codice_non configurato",
			new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_CODICENATURA_ENDO_DEFAULT });
		_ivs.add(new InvalidValue(m, null, null, null, null));
	    }
	    //..............................
	    log.debug("isInsertAllowed# Controllo TEMPIFICAZIONE ENDO");
	    if (StringUtils.isNotBlank(verticalizzazioneFvgSol.getTEMPIFICAZIONE_ENDO_DEFAULT())) {
		Integer codice = null;
		try {
		    codice = Integer.parseInt(verticalizzazioneFvgSol.getTEMPIFICAZIONE_ENDO_DEFAULT());
		    Tempificazioni tempificazioni = tempificazioniService.findById(new PkId(codice));
		    if (EntityUtils.getNestedProperty(tempificazioni, "id.codice") == null) {
			log.error("getCodiceDaParametroVerticalizzazione#Tempificazione endo non trovata per il codice {} ", codice);
			String m = getMessageFromBundle("service_error.invetarioprocedimento_no_valore_default",
				new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_TEMPIFICAZIONE_ENDO_DEFAULT, codice });
			_ivs.add(new InvalidValue(m, null, null, null, null));
		    } else {
			entity.setTempificazione(tempificazioni);
		    }
		} catch (NumberFormatException ne) {
		    log.error("getCodiceDaParametroVerticalizzazione#Errore nella conversione in Integer del valore del parametro {}",
			    WebConstants.VERTICALIZZAZIONE_FVG_SOL_TEMPIFICAZIONE_ENDO_DEFAULT);
		    String m = getMessageFromBundle("service_error.invetarioprocedimento_codice_non_numerico",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_TEMPIFICAZIONE_ENDO_DEFAULT,
				    verticalizzazioneFvgSol.getTEMPIFICAZIONE_ENDO_DEFAULT() });
		    _ivs.add(new InvalidValue(m, null, null, null, null));
		}
	    } else {
		log.error("getCodiceDaParametroVerticalizzazione# Valore del parametro TEMPIFICAZIONE_ENDO non configurato");
		String m = getMessageFromBundle("service_error.invetarioprocedimento_codice_non configurato",
			new Object[] { WebConstants.VERTICALIZZAZIONE_FVG_SOL_TEMPIFICAZIONE_ENDO_DEFAULT });
		_ivs.add(new InvalidValue(m, null, null, null, null));
	    }
	}
	//..
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    @Override
    public void update(Inventarioprocedimenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentiDAO.update(entity);
	}
    }

    @Override
    public List<Inventarioprocedimenti> findByTipoendo(Tipiendo tipiendo) {

	return inventarioprocedimentiDAO.findByTipoendo(tipiendo);
    }

    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologia(String textToSearch, Integer codiceFamiglia, Integer codiceTipologia,
	    Boolean escludiDisabilitati) {

	return inventarioprocedimentiDAO.findByDescrizioneFamigliaendoETipologia(textToSearch, codiceFamiglia, codiceTipologia, escludiDisabilitati);
    }

    public Inventarioprocedimenti insertCopia(Inventarioprocedimenti entity) {

	InventarioprocedimentiHelper copy = new InventarioprocedimentiHelper();
	if (validateCopia(entity) && isInsertAllowedCopia(entity)) {
	    copy = inventarioprocedimentiDTO(entity, copy);
	    inventarioprocedimentiDAO.insert(copy.getInventarioprocedimenti());
	    Set<Allegati> listAllegatis = copy.getAllegatis();
	    for (Allegati allegati : listAllegatis) {
		allegati.setInventarioprocedimento(copy.getInventarioprocedimenti());
		allegatiService.insert(allegati);
	    }
	    Set<Testiestesi> testiestesis = copy.getTestiestesis();
	    for (Testiestesi testiestesi : testiestesis) {
		testiestesi.setInventarioprocedimento(copy.getInventarioprocedimenti());
		testiestesiService.insert(testiestesi);
	    }
	    Set<Documenti> documentis = copy.getDocumentis();
	    for (Documenti documenti : documentis) {
		documenti.setInventarioprocedimento(copy.getInventarioprocedimenti());
		documentiService.insert(documenti);
	    }
	    Set<Inventarioprocedimentioneri> inventarioprocedimentioneris = copy.getInventarioprocedimentioneris();
	    for (Inventarioprocedimentioneri inventarioprocedimentioneri : inventarioprocedimentioneris) {
		inventarioprocedimentioneri.setInventarioprocedimenti(copy.getInventarioprocedimenti());
		inventarioprocedimentioneriService.insert(inventarioprocedimentioneri);
	    }
	    Set<Inventarioprocdyn2modellit> inventarioprocdyn2modellits = copy.getInventarioprocdyn2modellits();
	    for (Inventarioprocdyn2modellit inventarioprocdyn2modellit : inventarioprocdyn2modellits) {
		Inventarioprocdyn2modellitId idcopy = inventarioprocdyn2modellit.getId();
		idcopy.setCodiceinventario(copy.getInventarioprocedimenti().getId().getCodice());
		inventarioprocdyn2modellit.setId(idcopy);
		inventarioprocdyn2modellit.setInventarioprocedimenti(copy.getInventarioprocedimenti());
		inventarioprocdyn2modellitService.insert(inventarioprocdyn2modellit);
	    }
	    Set<EndoCausali> endoCausalis = copy.getEndoCausalis();
	    for (EndoCausali endoCausali : endoCausalis) {
		endoCausali.setInventarioprocedimenti(copy.getInventarioprocedimenti());
		endoCausaliService.insert(endoCausali);
		Set<EndoConti> endoContis = endoCausali.getEndoContis();
		for (EndoConti endoConti : endoContis) {
		    endoConti.setEndoCausali(endoCausali);
		    endoCausali.setInventarioprocedimenti(copy.getInventarioprocedimenti());
		    endoContiService.insert(endoConti);
		}
	    }
	    Set<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares = copy.getInventarioprocedimentisoftwares();
	    for (Inventarioprocedimentisoftware inventarioprocedimentisoftware : inventarioprocedimentisoftwares) {
		inventarioprocedimentisoftware.setInventarioprocedimento(copy.getInventarioprocedimenti());
		inventarioprocedimentisoftwareService.insert(inventarioprocedimentisoftware);
	    }
	    Set<Inventarioprocedimentiincomp> inventarioprocedimentiincomps = copy.getInventarioprocedimentiincomps();
	    for (Inventarioprocedimentiincomp inventarioprocedimentiincomp : inventarioprocedimentiincomps) {
		inventarioprocedimentiincomp.setInventarioprocedimento(copy.getInventarioprocedimenti());
		inventarioprocedimentiincompService.insert(inventarioprocedimentiincomp);
	    }
	    Set<InventarioprocLeggi> inventarioprocLeggis = copy.getInventarioprocLeggis();
	    for (InventarioprocLeggi inventarioprocLeggi : inventarioprocLeggis) {
		inventarioprocLeggi.setInventarioprocedimenti(copy.getInventarioprocedimenti());
		inventarioprocLeggiService.insert(inventarioprocLeggi);
	    }
	}
	return copy.getInventarioprocedimenti();
    }

    private boolean validateCopia(Inventarioprocedimenti entity) {

	Boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity == null) {
	    _ivs.add(new InvalidValue("inventarioprocedimenti.service_error.inventario_procedimento_non_scelto", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    @Override
    public List<Inventarioprocedimenti> findAllNonStp() {

	return inventarioprocedimentiDAO.findAllNonStp();
    }

    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoAndSoftware(String descrizione, String codicesoftware) {

	return inventarioprocedimentiDAO.findByDescrizioneFamigliaendoAndSoftware(descrizione, codicesoftware);
    }

    @Override
    public List<Inventarioprocedimenti> findByIstanza(Istanze istanze) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	// criterio.addFilterField(new FilterField<String>("cittadinanza", FieldOperationsEnum.CONTAINS, new String[] {
	// param }, String.class));
	criterio.addFilterField(
		new FilterField<Istanze>("istanza", "istanzeprocedimentis", FieldOperationsEnum.EQ, new Istanze[] { istanze }, Istanze.class));
	ft.addRestriction(criterio);
	// FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("cittadinanza",
	// null, String.class));
	// ft.addOrder(orderByScDescrizione);
	List<Inventarioprocedimenti> list = inventarioprocedimentiDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Inventarioprocedimenti> findByTipiendoAndNonAttivatiPerIstanza(Tipiendo tipiendo, Tipifamiglieendo tipifamiglieendo, Istanze istanza,
	    List<String> listCodici, String descrizione, boolean isFiltraTipiEndoNull, Integer maxResult) {

	//	if (tipiendo == null) {
	//	    throw new IllegalArgumentException("Il tipo endo passato è nullo");
	//	}
	if (istanza == null) {
	    throw new IllegalArgumentException("L'istanza passata è nulla");
	}
	return inventarioprocedimentiDAO.findByTipiendoAndNonAttivatiPerIstanza(tipiendo, tipifamiglieendo, istanza, listCodici, descrizione,
		isFiltraTipiEndoNull, maxResult);
    }

    /**
     * Il metodo prende in ingresso due oggetti Inventarioprocedimento (master) e InventarioprocedimentiHelper(copy). Il
     * metodo de costruire a partire dall'oggetto master un oggetto copy (l'oggetto copy conterrà un oggetto
     * Inventarioprocedimento e tutte le liste presenti in inventario procedimento) Tutti gli i campi popolati
     * nell'oggetto copy saranno uguali. Lo scopo del metodo è quello fornire al service gli oggetti necessari per
     * creare una copia esatta dell'oggetto master ma che abbia la chiave nulla in modo che possa essere inserito con un
     * nuovo oggeto nel DB.
     * 
     * @param master
     *            :oggetto originale da copiare
     * @param copy
     *            :oggetto che sarà la copia restituita dal metodo
     * @return un oggetto InventarioprocedimentoHelper identi a quello passato come master
     */
    private InventarioprocedimentiHelper inventarioprocedimentiDTO(Inventarioprocedimenti master, InventarioprocedimentiHelper copy) {

	// copio i campi dell'entity principale nell'oggeto invetarioprocedimento dell'oggetto Helper
	copy.getInventarioprocedimenti().setProcedimento(WebConstants.COPIA_PROCEDIMENTO.concat(master.getProcedimento()));
	if (master.getDatigenerali() != null)
	    copy.getInventarioprocedimenti().setDatigenerali(master.getDatigenerali());
	if (master.getDataaggiornamento() != null)
	    copy.getInventarioprocedimenti().setDataaggiornamento(master.getDataaggiornamento());
	if (master.getCampoapplicazione() != null)
	    copy.getInventarioprocedimenti().setCampoapplicazione(master.getCampoapplicazione());
	if (master.getNormativaue() != null)
	    copy.getInventarioprocedimenti().setNormativaue(master.getNormativaue());
	if (master.getNormativana() != null)
	    copy.getInventarioprocedimenti().setNormativana(master.getNormativana());
	if (master.getNormativare() != null)
	    copy.getInventarioprocedimenti().setNormativare(master.getNormativare());
	if (master.getRegolamenti() != null)
	    copy.getInventarioprocedimenti().setRegolamenti(master.getRegolamenti());
	if (master.getAdempimenti() != null)
	    copy.getInventarioprocedimenti().setAdempimenti(master.getAdempimenti());
	if (master.getCollaudo() != null)
	    copy.getInventarioprocedimenti().setCollaudo(master.getCollaudo());
	if (master.getPerprovvedimento() != null)
	    copy.getInventarioprocedimenti().setPerprovvedimento(master.getPerprovvedimento());
	if (master.getDisabilitato() != null)
	    copy.getInventarioprocedimenti().setDisabilitato(master.getDisabilitato());
	if (master.getOrdine() != null)
	    copy.getInventarioprocedimenti().setOrdine(master.getOrdine());
	if (master.getDirittiistruttoria() != null)
	    copy.getInventarioprocedimenti().setDirittiistruttoria(master.getDirittiistruttoria());
	if (master.getNoneseguecontromovobblig() != null)
	    copy.getInventarioprocedimenti().setNoneseguecontromovobblig(master.getNoneseguecontromovobblig());
	if (master.getCodiceancitel() != null)
	    copy.getInventarioprocedimenti().setCodiceancitel(master.getCodiceancitel());
	if (master.getAmministrazioni() != null)
	    copy.getInventarioprocedimenti().setAmministrazioni(master.getAmministrazioni());
	if (master.getTempificazione() != null)
	    copy.getInventarioprocedimenti().setTempificazione(master.getTempificazione());
	if (master.getTipoendo() != null)
	    copy.getInventarioprocedimenti().setTipoendo(master.getTipoendo());
	if (master.getAmministrazionireferente() != null)
	    copy.getInventarioprocedimenti().setAmministrazionireferente(master.getAmministrazionireferente());
	if (master.getTipomovimento() != null)
	    copy.getInventarioprocedimenti().setTipomovimento(master.getTipomovimento());
	if (master.getNaturaendo() != null)
	    copy.getInventarioprocedimenti().setNaturaendo(master.getNaturaendo());
	if (master.getSoftware() != null)
	    copy.getInventarioprocedimenti().setSoftware(master.getSoftware());
	// Creazione della replica di inevtario procedimenti peolple
	Set<Inventarioprocedimentipeople> peolplesMaster = master.getInventarioprocedimentipeoples();
	Set<Inventarioprocedimentipeople> peoplesCopy = copy.getInventarioprocedimentipeoples();
	Inventarioprocedimentipeople peopleCopy = null;
	for (Inventarioprocedimentipeople peopleMaster : peolplesMaster) {
	    peopleCopy = new Inventarioprocedimentipeople();
	    if (peopleMaster.getCodProcPeople() != null)
		peopleCopy.setCodProcPeople(peopleMaster.getCodProcPeople());
	    peoplesCopy.add(peopleCopy);
	}
	copy.setInventarioprocedimentipeoples(peoplesCopy);
	// Creazione della replica della lista degli allegati
	Set<Allegati> allegatisMaster = master.getAllegatis();
	Set<Allegati> allegatisCopy = copy.getAllegatis();
	Allegati allegatiCopy = null;
	for (Allegati allegatiMaster : allegatisMaster) {
	    allegatiCopy = new Allegati();
	    if (StringUtils.isNotBlank(allegatiMaster.getAllegato()))
		allegatiCopy.setAllegato(allegatiMaster.getAllegato());
	    if (allegatiMaster.getAmministrazioni() != null)
		allegatiCopy.setAmministrazioni(allegatiMaster.getAmministrazioni());
	    if (StringUtils.isNotBlank(allegatiMaster.getModello()))
		allegatiCopy.setModello(allegatiMaster.getModello());
	    if (allegatiMaster.getCosto() != null)
		allegatiCopy.setCosto(allegatiMaster.getCosto());
	    if (StringUtils.isNotBlank(allegatiMaster.getIndirizzoweb()))
		allegatiCopy.setIndirizzoweb(allegatiMaster.getIndirizzoweb());
	    if (allegatiMaster.getOggetti() != null)
		allegatiCopy.setOggetti(allegatiMaster.getOggetti());
	    if (allegatiMaster.getPubblica() != null)
		allegatiCopy.setPubblica(allegatiMaster.getPubblica());
	    if (allegatiMaster.getRichiesto() != null)
		allegatiCopy.setRichiesto(allegatiMaster.getRichiesto());
	    if (allegatiMaster.getOrdine() != null)
		allegatiCopy.setOrdine(allegatiMaster.getOrdine());
	    if (allegatiMaster.getFoRichiedefirma() != null)
		allegatiCopy.setFoRichiedefirma(allegatiMaster.getFoRichiedefirma());
	    if (StringUtils.isNotBlank(allegatiMaster.getFoTipodownload()))
		allegatiCopy.setFoTipodownload(allegatiMaster.getFoTipodownload());
	    if (StringUtils.isNotBlank(allegatiMaster.getNoteFrontend())) {
		allegatiCopy.setNoteFrontend(allegatiMaster.getNoteFrontend());
	    }
	    allegatisCopy.add(allegatiCopy);
	}
	copy.setAllegatis(allegatisCopy);
	// Creazione della replica della lista dei testi estesi
	Set<Testiestesi> testisMaster = master.getTestiestesis();
	Set<Testiestesi> testiestesisCopy = copy.getTestiestesis();
	Testiestesi testiestesiCopy = null;
	for (Testiestesi testiestesiMaster : testisMaster) {
	    testiestesiCopy = new Testiestesi();
	    if (testiestesiMaster.getOggetti() != null)
		testiestesiCopy.setOggetti(testiestesiMaster.getOggetti());
	    if (testiestesiMaster.getNormativa() != null)
		testiestesiCopy.setNormativa(testiestesiMaster.getNormativa());
	    if (testiestesiMaster.getNormative() != null)
		testiestesiCopy.setNormative(testiestesiMaster.getNormative());
	    if (testiestesiMaster.getNomefile() != null)
		testiestesiCopy.setNomefile(testiestesiMaster.getNomefile());
	    if (testiestesiMaster.getIndirizzoweb() != null)
		testiestesiCopy.setIndirizzoweb(testiestesiMaster.getIndirizzoweb());
	    testiestesisCopy.add(testiestesiCopy);
	}
	copy.setTestiestesis(testiestesisCopy);
	// Creazione della replica della lista dei documenti
	Set<Documenti> documentisMaster = master.getDocumentis();
	Set<Documenti> documentisCopy = copy.getDocumentis();
	Documenti documentiCopy = null;
	for (Documenti documentiMaster : documentisMaster) {
	    documentiCopy = new Documenti();
	    if (documentiMaster.getOggetti() != null)
		documentiCopy.setOggetti(documentiMaster.getOggetti());
	    if (documentiMaster.getAmministrazioni() != null)
		documentiCopy.setAmministrazioni(documentiMaster.getAmministrazioni());
	    if (documentiMaster.getDocumento() != null)
		documentiCopy.setDocumento(documentiMaster.getDocumento());
	    if (documentiMaster.getIndirizzoweb() != null)
		documentiCopy.setIndirizzoweb(documentiMaster.getIndirizzoweb());
	    documentisCopy.add(documentiCopy);
	}
	copy.setDocumentis(documentisCopy);
	// Creazione della replica della lista dei invetario procedimenti oneri
	Set<Inventarioprocedimentioneri> invenOnerisMaster = master.getInventarioprocedimentioneris();
	Set<Inventarioprocedimentioneri> invenOnerisCopy = copy.getInventarioprocedimentioneris();
	Inventarioprocedimentioneri invenOneriCopy = null;
	for (Inventarioprocedimentioneri invenOneriMaster : invenOnerisMaster) {
	    invenOneriCopy = new Inventarioprocedimentioneri();
	    if (invenOneriMaster.getSoftware() != null)
		invenOneriCopy.setSoftware(invenOneriMaster.getSoftware());
	    if (invenOneriMaster.getTipimodalitapagamento() != null)
		invenOneriCopy.setTipimodalitapagamento(invenOneriMaster.getTipimodalitapagamento());
	    if (invenOneriMaster.getTipicausalioneri() != null)
		invenOneriCopy.setTipicausalioneri(invenOneriMaster.getTipicausalioneri());
	    if (invenOneriMaster.getImporto() != null)
		invenOneriCopy.setImporto(invenOneriMaster.getImporto());
	    if (invenOneriMaster.getImportoistruttoria() != null)
		invenOneriCopy.setImportoistruttoria(invenOneriMaster.getImportoistruttoria());
	    // if (invenOneriMaster.getFlagPagato() != null)
	    invenOneriCopy.setFlagPagato(invenOneriMaster.getFlagPagato());
	    invenOnerisCopy.add(invenOneriCopy);
	}
	copy.setInventarioprocedimentioneris(invenOnerisCopy);
	// Creazione della replica della lista dei modelli
	Set<Inventarioprocdyn2modellit> modellisMaster = master.getInventarioprocdyn2modellits();
	Set<Inventarioprocdyn2modellit> modellisCopy = copy.getInventarioprocdyn2modellits();
	Inventarioprocdyn2modellit modelliCopy = null;
	for (Inventarioprocdyn2modellit modelliMaster : modellisMaster) {
	    modelliCopy = new Inventarioprocdyn2modellit();
	    if (modelliMaster.getId() != null) {
		Inventarioprocdyn2modellitId idcopy = new Inventarioprocdyn2modellitId();
		idcopy.setFkD2mtId(modelliMaster.getDyn2Modellit().getId().getCodice());
		modelliCopy.setId(idcopy);
	    }
	    if (modelliMaster.getDyn2Modellit() != null) {
		modelliCopy.setDyn2Modellit(modelliMaster.getDyn2Modellit());
	    }
	    modelliCopy.setFlagFacoltativa(modelliMaster.getFlagFacoltativa());
	    modelliCopy.setFlagPubblica(modelliMaster.getFlagPubblica());
	    modelliCopy.setFlagTipofirma(modelliMaster.getFlagTipofirma());
	    modelliCopy.setOrdine(modelliMaster.getOrdine());
	    modellisCopy.add(modelliCopy);
	}
	copy.setInventarioprocdyn2modellits(modellisCopy);
	// Creazione della replica della lista degli endo incompatibili
	Set<Inventarioprocedimentiincomp> endoincompsMaster = master.getInventarioprocedimentiincomps();
	Set<Inventarioprocedimentiincomp> endoincompsCopy = copy.getInventarioprocedimentiincomps();
	Inventarioprocedimentiincomp endoincompCopy = null;
	for (Inventarioprocedimentiincomp endoincompMaster : endoincompsMaster) {
	    endoincompCopy = new Inventarioprocedimentiincomp();
	    if (endoincompMaster.getInventarioprocedimentoincompatibile() != null)
		endoincompCopy.setInventarioprocedimentoincompatibile(endoincompMaster.getInventarioprocedimentoincompatibile());
	    endoincompsCopy.add(endoincompCopy);
	}
	copy.setInventarioprocedimentiincomps(endoincompsCopy);
	// Creazione della replica della lista delle causali
	Set<EndoCausali> causalisMaster = master.getEndoCausalis();
	Set<EndoCausali> causalisCopy = copy.getEndoCausalis();
	EndoCausali causaliCopy = null;
	for (EndoCausali causaliMaster : causalisMaster) {
	    causaliCopy = new EndoCausali();
	    if (causaliMaster.getRegistrazioniCausali() != null)
		causaliCopy.setRegistrazioniCausali(causaliMaster.getRegistrazioniCausali());
	    if (!causaliMaster.getEndoContis().isEmpty())
		causaliCopy.setEndoContis(causaliMaster.getEndoContis());
	    causalisCopy.add(causaliCopy);
	}
	copy.setEndoCausalis(causalisCopy);
	// Creazione della replica della lista dei modulo attivi
	Set<Inventarioprocedimentisoftware> modulisMaster = master.getInventarioprocedimentisoftwares();
	Set<Inventarioprocedimentisoftware> modulisCopy = copy.getInventarioprocedimentisoftwares();
	Inventarioprocedimentisoftware moduliCopy = null;
	for (Inventarioprocedimentisoftware moduliMaster : modulisMaster) {
	    moduliCopy = new Inventarioprocedimentisoftware();
	    moduliCopy.setSoftware(moduliMaster.getSoftware());
	    if (moduliMaster.getTipimovimento() != null) {
		moduliCopy.setTipimovimento(moduliMaster.getTipimovimento());
	    }
	    if (moduliMaster.getAmministrazioni() != null) {
		moduliCopy.setAmministrazioni(moduliMaster.getAmministrazioni());
	    }
	    modulisCopy.add(moduliCopy);
	}
	copy.setInventarioprocedimentisoftwares(modulisCopy);
	// Creazione della replica della lista delle normative configurate
	Set<InventarioprocLeggi> normativesMaster = master.getInventarioprocLeggis();
	Set<InventarioprocLeggi> normativesCopy = copy.getInventarioprocLeggis();
	InventarioprocLeggi normativeCopy = null;
	for (InventarioprocLeggi normativeMaster : normativesMaster) {
	    normativeCopy = new InventarioprocLeggi();
	    if (normativeMaster.getLeggi() != null) {
		normativeCopy.setLeggi(normativeMaster.getLeggi());
	    }
	    normativesCopy.add(normativeCopy);
	}
	copy.setInventarioprocedimentisoftwares(modulisCopy);
	return copy;
    }

    @Override
    public List<Inventarioprocedimenti> findInventarioprocedimentiAttivabili(Istanze istanze) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction criterio = new FilterRestriction();
	String hierarchy = "istanzeprocedimentis";
	FilterField<Istanze> filterIstanze = new FilterField<Istanze>("istanza", hierarchy, FieldOperationsEnum.NOTEXISTS, new Istanze[] { istanze },
		Istanze.class);
	criterio.addFilterField(filterIstanze);
	ft.addRestriction(criterio);
	List<Inventarioprocedimenti> list = inventarioprocedimentiDAO.findByFilterTable(ft);
	return list;
    }

    protected boolean isInsertAllowedCopia(Inventarioprocedimenti entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity != null && entity.getId().getCodice() == null) {
	    _ivs.add(new InvalidValue("service_error.endoprocedimento_non_selezionato", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    protected boolean isDeleteAllowed(Inventarioprocedimenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getAlberoprocEndos().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC_ENDO", null));
	}
	if (entity.getControlloverifiches().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CONTROLLOVERIFICHE", null));
	}
	if (istanzeallegatiService.countByInventarioprocedimento(entity.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEALLEGATI", null));
	}
	if (istanzeprocedimentiService.countByInventarioprocedimento(entity.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEPROCEDIMENTI", null));
	}
	if (movimentiService.countByInventarioprocedimento(entity.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI", null));
	}
	if (entity.getRegistrazionis().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "REGISTRAZIONI", null));
	}
	//	if (entity.getStpEndoTipo1s().size() > 0) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "STP_ENDO_TIPO1", null));
	//	}
	//	if (entity.getStpEndoTipo2s().size() > 0) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "STP_ENDO_TIPO2", null));
	//	}
	if (entity.getTipimovimentoDises().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOVIMENTO_DIS", null));
	}
	if (istanzeoneriDettaglioService.countByInventarioprocedimento(entity.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEONERI_DETTAGLIO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public void checkEndoIncompatibili(Set<Integer> endoprocedimentis) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	for (Integer codiceInventario : endoprocedimentis) {
	    Inventarioprocedimenti inv = this.findById(new PkId(codiceInventario));
	    Set<Inventarioprocedimentiincomp> incomps = inv.getInventarioprocedimentiincomps();
	    for (Inventarioprocedimentiincomp inventarioprocedimentiincomp : incomps) {
		Integer codiceIncompatibile = inventarioprocedimentiincomp.getInventarioprocedimentoincompatibile().getId().getCodice();
		if (endoprocedimentis.contains(codiceIncompatibile)) {
		    String message = "Esiste una incompatibilità tra gli endoprocedimenti [" +
			    inv.getProcedimento() +
			    "] e [" +
			    inventarioprocedimentiincomp.getInventarioprocedimentoincompatibile().getProcedimento() +
			    "]";
		    _ivs.add(new InvalidValue(WebConstants.ALERT_ENDO_INCOMPATIBILI, null, null, message, null));
		}
	    }
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
    }

    private void dataIntegration(Inventarioprocedimenti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro inventarioprocedimenti è nullo");
	}
	if (entity.getCollaudo() == null) {
	    entity.setCollaudo(Boolean.FALSE);
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setCollaudo(Boolean.FALSE);
	}
	if (entity.getDisabilitato() == null) {
	    entity.setDisabilitato(Boolean.FALSE);
	}
	if (entity.getPerprovvedimento() == null) {
	    entity.setPerprovvedimento(Boolean.FALSE);
	}
	if (entity.getNoneseguecontromovobblig() == null) {
	    entity.setNoneseguecontromovobblig(Boolean.FALSE);
	}
	if (entity.getFlagtipotitolo() == null) {
	    entity.setFlagtipotitolo(Boolean.FALSE);
	}
	if (entity.getDataaggiornamento() == null) {
	    entity.setDataaggiornamento(Calendar.getInstance().getTime());
	}
	if (entity.getOrdine() == null) {
	    entity.setOrdine(0);
	}
	entity.setDataaggiornamento(Calendar.getInstance().getTime());
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Inventarioprocedimenti entity) {

	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipomovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipomovimento(tipimovimento);
	Tipiendo tipiendo = tipiendoService.bindDomainObject(entity.getTipoendo(), PkId.class, "id.codice");
	entity.setTipoendo(tipiendo);
	Naturaendo naturaendo = naturaendoService.bindDomainObject(entity.getNaturaendo(), NaturaendoId.class, "id.codice");
	entity.setNaturaendo(naturaendo);
	Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService.bindDomainObject(entity.getAmministrazionireferente(),
		PkId.class, "id.codice");
	entity.setAmministrazionireferente(amministrazionireferenti);
	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	Tempificazioni tempificazioni = tempificazioniService.bindDomainObject(entity.getTempificazione(), PkId.class, "id.codice");
	entity.setTempificazione(tempificazioni);
    }

    //----------------------------------------------------------------------------------------------------------------------------------///
    //------------------------------------------SEZIONE DEDICATA ALLA NUOVA GESTIONE JMESA----------------------------------------------///
    //----------------------------------------------------------------------------------------------------------------------------------///
    @Override
    public int countRecordByFilter(Inventarioprocedimenti inventarioprocedimenti) {

	return inventarioprocedimentiDAO.countRecordByFilter(inventarioprocedimenti);
    }

    @Override
    public List<Inventarioprocedimenti> findByInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti, Integer startRowPage,
	    Integer endRowPage) {

	return inventarioprocedimentiDAO.findByInventarioprocedimenti(inventarioprocedimenti, startRowPage, endRowPage);
    }

    //----------------------------------------------------------------------------------------------------------------------------------///
    //---------------------------------------------------------------END----------------------------------------------------------------///
    //----------------------------------------------------------------------------------------------------------------------------------///
    @Override
    public List<Inventarioprocedimenti> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return inventarioprocedimentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countRecord(FilterTable filterTable) {

	return inventarioprocedimentiDAO.countRecord(filterTable);
    }

    @Override
    public FilterTable createFilterTableByEntity(Inventarioprocedimenti inventarioprocedimenti) {

	if (inventarioprocedimenti == null) {
	    throw new IllegalArgumentException("Inventarioprocedimenti non può essere null");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction restriction = new FilterRestriction();
	// Criterio di ricerca da applicare ad entrambe le query, conteggio record e ricerca record
	if (inventarioprocedimenti.getId().getCodice() != null) {
	    restriction.addFilterField(FilterUtils.equals("id.codice", inventarioprocedimenti.getId().getCodice(), Integer.class));
	}
	if (inventarioprocedimenti != null && StringUtils.isNotBlank(inventarioprocedimenti.getProcedimento())) {
	    restriction.addFilterField(FilterUtils.like("procedimento", inventarioprocedimenti.getProcedimento()));
	}
	if (inventarioprocedimenti.getTipoendo() != null && StringUtils.isNotBlank(inventarioprocedimenti.getTipoendo().getTipo())) {
	    restriction.addFilterField(FilterUtils.like("tipo", inventarioprocedimenti.getTipoendo().getTipo(), "tipoendo"));
	}
	if (inventarioprocedimenti.getTipoendo() != null && inventarioprocedimenti.getTipoendo().getTipifamiglieendo() != null
		&& StringUtils.isNotBlank(inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getTipo())) {
	    restriction.addFilterField(
		    FilterUtils.like("tipo", inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getTipo(), "tipoendo.tipifamiglieendo"));
	}
	if (StringUtils.isNotBlank(inventarioprocedimenti.getTransientListaCodiciPeople())) {
	    restriction.addFilterField(
		    FilterUtils.like("codProcPeople", inventarioprocedimenti.getTransientListaCodiciPeople(), "inventarioprocedimentipeoples"));
	}
	if (inventarioprocedimenti.getDisabilitato() != null) {
	    restriction.addFilterField(FilterUtils.equals("disabilitato", inventarioprocedimenti.getDisabilitato(), Boolean.class));
	}
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return filterTable;
    }

    @Override
    public FilterTable createFilterTableByEntity(Inventarioprocedimentisoftware inventarioprocedimentisoftware) {

	if (inventarioprocedimentisoftware == null) {
	    throw new IllegalArgumentException("Inventarioprocedimentisoftware non può essere null");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("codice", WebConstants.SOFTWARE_TT, "software", String.class));
	if (inventarioprocedimentisoftware.getInventarioprocedimento().getId().getCodice() != null) {
	    restriction.addFilterField(
		    FilterUtils.equals("id.codice", inventarioprocedimentisoftware.getInventarioprocedimento().getId().getCodice(), Integer.class));
	}
	if (inventarioprocedimentisoftware.getInventarioprocedimento() != null
		&& StringUtils.isNotBlank(inventarioprocedimentisoftware.getInventarioprocedimento().getProcedimento())) {
	    restriction
		    .addFilterField(FilterUtils.like("procedimento", inventarioprocedimentisoftware.getInventarioprocedimento().getProcedimento()));
	}
	if (inventarioprocedimentisoftware.getInventarioprocedimento().getTipoendo() != null
		&& StringUtils.isNotBlank(inventarioprocedimentisoftware.getInventarioprocedimento().getTipoendo().getTipo())) {
	    restriction.addFilterField(
		    FilterUtils.like("tipo", inventarioprocedimentisoftware.getInventarioprocedimento().getTipoendo().getTipo(), "tipoendo"));
	}
	if (inventarioprocedimentisoftware.getInventarioprocedimento().getTipoendo() != null
		&& inventarioprocedimentisoftware.getInventarioprocedimento().getTipoendo().getTipifamiglieendo() != null
		&& StringUtils.isNotBlank(inventarioprocedimentisoftware.getInventarioprocedimento().getTipoendo().getTipifamiglieendo().getTipo())) {
	    restriction.addFilterField(
		    FilterUtils.like("tipo", inventarioprocedimentisoftware.getInventarioprocedimento().getTipoendo().getTipifamiglieendo().getTipo(),
			    "tipoendo.tipifamiglieendo"));
	}
	if (StringUtils.isNotBlank(inventarioprocedimentisoftware.getInventarioprocedimento().getTransientListaCodiciPeople())) {
	    restriction.addFilterField(FilterUtils.like("codProcPeople",
		    inventarioprocedimentisoftware.getInventarioprocedimento().getTransientListaCodiciPeople(), "inventarioprocedimentipeoples"));
	}
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return filterTable;
    }

    @Override
    public FilterTable createFilterTableByInventarioprocFilter(InventarioprocedimentiFilter inventarioprocedimentiFilter) {

	if (inventarioprocedimentiFilter.getInventarioprocedimenti() == null) {
	    throw new IllegalArgumentException("Inventarioprocedimenti non può essere null");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction restriction = new FilterRestriction();
	Map<String, String> sortFilters = inventarioprocedimentiFilter.getOrdinamentoMap();
	if (inventarioprocedimentiFilter.getInventarioprocedimenti().getId().getCodice() != null) {
	    restriction.addFilterField(
		    FilterUtils.equals("id.codice", inventarioprocedimentiFilter.getInventarioprocedimenti().getId().getCodice(), Integer.class));
	}
	if (inventarioprocedimentiFilter.getInventarioprocedimenti() != null
		&& StringUtils.isNotBlank(inventarioprocedimentiFilter.getInventarioprocedimenti().getProcedimento())) {
	    restriction.addFilterField(FilterUtils.like("procedimento", inventarioprocedimentiFilter.getInventarioprocedimenti().getProcedimento()));
	}
	if (inventarioprocedimentiFilter.getInventarioprocedimenti().getTipoendo() != null
		&& StringUtils.isNotBlank(inventarioprocedimentiFilter.getInventarioprocedimenti().getTipoendo().getTipo())) {
	    restriction.addFilterField(
		    FilterUtils.like("tipo", inventarioprocedimentiFilter.getInventarioprocedimenti().getTipoendo().getTipo(), "tipoendo"));
	}
	if (inventarioprocedimentiFilter.getInventarioprocedimenti().getTipoendo() != null
		&& inventarioprocedimentiFilter.getInventarioprocedimenti().getTipoendo().getTipifamiglieendo() != null
		&& StringUtils.isNotBlank(inventarioprocedimentiFilter.getInventarioprocedimenti().getTipoendo().getTipifamiglieendo().getTipo())) {
	    restriction.addFilterField(
		    FilterUtils.like("tipo", inventarioprocedimentiFilter.getInventarioprocedimenti().getTipoendo().getTipifamiglieendo().getTipo(),
			    "tipoendo.tipifamiglieendo"));
	}
	if (StringUtils.isNotBlank(inventarioprocedimentiFilter.getInventarioprocedimenti().getTransientListaCodiciPeople())) {
	    restriction.addFilterField(FilterUtils.like("codProcPeople",
		    inventarioprocedimentiFilter.getInventarioprocedimenti().getTransientListaCodiciPeople(), "inventarioprocedimentipeoples"));
	}
	if (inventarioprocedimentiFilter.getInventarioprocedimenti().getDisabilitato() != null) {
	    restriction.addFilterField(
		    FilterUtils.equals("disabilitato", inventarioprocedimentiFilter.getInventarioprocedimenti().getDisabilitato(), Boolean.class));
	}
	filterTable.addRestriction(restriction);
	setSortFilter(sortFilters, filterTable);
	return filterTable;
    }

    /**
     * Metodo di utilità custom che imposta i parametri e le modalità di ordinamento scelti dall'utente. I parametri e
     * le modalità di ordinamento vengono recuperati dall'oggetto sortFilters che mappa il parametro di ricerca con la
     * modalità di ordinamento, es. se l'utente vuole visualizzare la lista degli endoprocedimenti ordinati per
     * procedimento in modo ascendente allora la mappa sarà <procedimento, asc>. Questi valori sono recuperati
     * dall'oggetto di tipo SortSet di jmesa.
     * 
     * @param sortFilters
     * @param filterTable
     */
    private void setSortFilter(Map<String, String> sortFilters, FilterTable filterTable) {

	if (!sortFilters.isEmpty()) {
	    for (Map.Entry<String, String> entry : sortFilters.entrySet()) {
		String k = entry.getKey(); // recupero il parametro nel campo key
		String order = entry.getValue(); // recupero la modalità di ordinamento nel  campo value
		OrderTypeEnum o = OrderTypeEnum.ASC;
		if (order.equalsIgnoreCase("desc")) {
		    o = OrderTypeEnum.DESC;
		}
		if (k.equalsIgnoreCase("disabilitato") || k.equalsIgnoreCase("id.codice") || k.equalsIgnoreCase("procedimento")
			|| k.equalsIgnoreCase("ordine")) {
		    filterTable.addOrder(FilterUtils.order(k, o));
		} else {
		    if (k.equalsIgnoreCase("tipoendo.tipo")) {
			filterTable.addOrder(FilterUtils.order("tipo", "tipoendo", o));
		    } else if (k.equalsIgnoreCase("tipoendo.tipifamiglieendo.tipo")) {
			filterTable.addOrder(FilterUtils.order("tipo", "tipoendo.tipifamiglieendo", o));
		    }
		}
	    }
	}
    }

    @Override
    public boolean isExsistWithoutTipiEndo(Istanze istanza, List<String> listCodici) {

	List<Inventarioprocedimenti> list = inventarioprocedimentiDAO.findByTipiendoAndNonAttivatiPerIstanza(null, null, istanza, listCodici, null,
		true, null);
	if (!list.isEmpty()) {
	    return true;
	}
	return false;
    }

    @Override
    public Integer countByTipiendoAndNonAttivatiPerIstanza(Tipiendo tipiendo, Tipifamiglieendo tipifamiglieendo, Istanze istanza,
	    List<String> listCodici, String descrizione) {

	return inventarioprocedimentiDAO.countByTipiendoAndNonAttivatiPerIstanza(tipiendo, tipifamiglieendo, istanza, listCodici, descrizione);
    }

    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologiaAndSoftware(String textToSearch, Integer codiceFamiglia,
	    Integer codiceTipologia, String codicesoftware) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = createFilterRestrictionForDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(textToSearch, codiceFamiglia,
		codiceTipologia, codicesoftware, null, false, true);
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	return inventarioprocedimentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Inventarioprocedimenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return inventarioprocedimentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countByTipiendo(Tipiendo tipiendo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction basefilter = new FilterRestriction();
	basefilter.addFilterField(FilterUtils.equals("tipiEndoId", tipiendo.getId().getCodice(), Integer.class));
	filterTable.addRestriction(basefilter);
	return inventarioprocedimentiDAO.countRecord(filterTable);
    }

    @Override
    public List<Inventarioprocedimentisoftware> findInventarioprocedimentisoftwareByFilterTable(FilterTable filterTable, Integer firstResult,
	    Integer maxResult) {

	List<Inventarioprocedimentisoftware> list = new ArrayList<Inventarioprocedimentisoftware>();
	List<Inventarioprocedimenti> inventarioprocedimentis = inventarioprocedimentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
	for (Inventarioprocedimenti inventarioprocedimento : inventarioprocedimentis) {
	    Boolean trovato = false;
	    for (Inventarioprocedimentisoftware inventarioprocedimentisoftware : inventarioprocedimento.getInventarioprocedimentisoftwares()) {
		if (!trovato && inventarioprocedimentisoftware.getSoftware().getCodice().equals(ORMHelper.getSoftware())) {
		    inventarioprocedimentisoftware.setAttivo(true);
		    list.add(inventarioprocedimentisoftware);
		    trovato = true;
		}
	    }
	    if (!trovato) {
		Inventarioprocedimentisoftware inventarioprocedimentisoftware = new Inventarioprocedimentisoftware();
		inventarioprocedimentisoftware.setInventarioprocedimento(inventarioprocedimento);
		inventarioprocedimentisoftware.setAttivo(false);
		list.add(inventarioprocedimentisoftware);
	    }
	}
	return list;
    }

    @Override
    public List<Inventarioprocedimenti> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("findByTipimovimento: il parametro tipomovimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipomovimentoId", tipomovimento, String.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("ordine", "software"));
	filterTable.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	filterTable.addOrder(FilterUtils.orderAsc("ordine"));
	filterTable.addOrder(FilterUtils.orderAsc("procedimento"));
	return inventarioprocedimentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(String textToSearch, Integer codiceFamiglia,
	    Integer codiceTipologia, String codicesoftware, List<Integer> listCodicinature, List<Integer> listCodiciEndoAttivati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = createFilterRestrictionForDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(textToSearch, codiceFamiglia,
		codiceTipologia, codicesoftware, listCodicinature, true, true);
	if (listCodiciEndoAttivati != null && !listCodiciEndoAttivati.isEmpty()) {
	    FilterField<Inventarioprocedimenti> notIN = new FilterField("id.codice", FieldOperationsEnum.NOTIN, listCodiciEndoAttivati.toArray(),
		    Integer.class);
	    fr.addFilterField(notIN);
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	return inventarioprocedimentiDAO.findByFilterTable(ft);
    }

    /**
     * <pre>
     * Crea una filter table per i campi passati, se il valori vengono passati a null, il filtro per quel particolare
     * campo non viene configurato.
     * 
     * &#64;param textToSearch
     * &#64;param codiceFamiglia
     * &#64;param codiceTipologia
     * &#64;param codicesoftware
     * &#64;param listCodicinature
     * &#64;param isFindToCurrentSoftware : se true cerca per il codice passato e per il software corrente, se false cerca solo per 
     *  				il codice software passato
     * &#64;return
     * </pre>
     */
    private FilterRestriction createFilterRestrictionForDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(String textToSearch,
	    Integer codiceFamiglia, Integer codiceTipologia, String codicesoftware, List<Integer> listCodicinature, boolean isFindToCurrentSoftware,
	    boolean escludiDisabilitati) {

	//FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	// Filtro per famiglia se è passato il codice
	if (codiceFamiglia != null) {
	    filterRestriction.addFilterField(FilterUtils.equals("id.codice", codiceFamiglia, "tipoendo.tipifamiglieendo", Integer.class));
	}
	// Filtro per categoria se è passato il codice
	if (codiceTipologia != null) {
	    filterRestriction.addFilterField(FilterUtils.equals("id.codice", codiceTipologia, "tipoendo", Integer.class));
	}
	// Filtro per codice o per descrizione
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		filterRestriction.addFilterField(FilterUtils.equals("id.codice", Integer.parseInt(textToSearch.replaceAll("%", "")), Integer.class));
	    } catch (Exception e) {
		filterRestriction.addFilterField(FilterUtils.like("procedimento", textToSearch));
	    }
	}
	// Filtro per software,il filtro software è sempre impostato. E quello presente sull' ORMHELPER
	// se non viene passato, altrimenti viene impostato quello passato alla signatura del metodo. nel caso isFindToCurrentSoftware =true
	// allora verrà imposta oltre al software passato anche quello corrente
	if (StringUtils.isNotBlank(codicesoftware)) {
	    if (isFindToCurrentSoftware) {
		filterRestriction
			.addFilterField(FilterUtils.in("codice", new Object[] { codicesoftware, ORMHelper.getSoftware() }, "software", String.class));
	    } else {
		filterRestriction.addFilterField(FilterUtils.equals("codice", codicesoftware, "software", String.class));
	    }
	} else {
	    filterRestriction.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	}
	//	}
	// Imposto filtro per le nature endo
	if (listCodicinature != null && !listCodicinature.isEmpty()) {
	    filterRestriction.addFilterField(FilterUtils.in("id.codice", listCodicinature.toArray(), "naturaendo", Integer.class));
	}
	// Imposto filtro per il campo disabilitati
	if (escludiDisabilitati == true) {
	    filterRestriction.addFilterField(FilterUtils.notEquals("disabilitato", true, Boolean.class));
	}
	//	ft.addRestriction(filterRestriction);
	return filterRestriction;
    }

    @Override
    public List<Integer> findCodiciEndoPerSoftware(String software) {

	return inventarioprocedimentiDAO.findCodiciEndoPerSoftware(software);
    }

    @Override
    public List<Inventarioprocedimenti> findByAlberoprocArendo(AlberoprocArendo alberoprocArendo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	// Filtro per famiglia (campo obbligatorio di alberoprocarendo)
	fr.addFilterField(FilterUtils.equals("id.codice", alberoprocArendo.getTipifamiglieendo().getId().getCodice(), "tipoendo.tipifamiglieendo",
		Integer.class));
	// Filtro per categoria
	if (alberoprocArendo.getTipiendo() != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", alberoprocArendo.getTipiendo().getId().getCodice(), "tipoendo", Integer.class));
	}
	fr.addFilterField(FilterUtils.notEquals("disabilitato", true, Boolean.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("ordine"));
	filterTable.addOrder(FilterUtils.orderAsc("procedimento"));
	return inventarioprocedimentiDAO.findByFilterTable(filterTable, null, null);
    }

    @Override
    public List<ProcedimentoSimpleBean> findListaSottonodiDi(String codiceElemento, FlagPubblicaEnum pubblicaEnum) {

	List<ProcedimentoSimpleBean> result = new ArrayList<ProcedimentoSimpleBean>();
	if (StringUtils.defaultIfEmpty(codiceElemento, "-1").equals("-1")) {
	    // GIANPAOLO
	    List<Tipifamiglieendo> famiglie = findTutteFamiglieEndoByCurrSoftwareAndTT(pubblicaEnum);
	    for (Tipifamiglieendo tfe : famiglie) {
		ProcedimentoSimpleBean psb = new ProcedimentoSimpleBean();
		if (tfe.getId().getCodice() == null) {
		    psb.setId("F-1");
		    psb.setText("ENDOPROCEDIMENTI");
		} else {
		    psb.setId("F" + tfe.getId().getCodice());
		    psb.setText(tfe.getTipo());
		}
		psb.setHasChilds(Boolean.TRUE);
		result.add(psb);
	    }
	} else {
	    if (codiceElemento.startsWith("F")) {
		String codiceFamigliaStr = codiceElemento.replaceAll("F", "").trim();
		Integer codiceFamiglia = Integer.parseInt(codiceFamigliaStr);
		// GIANPAOLO
		List<Tipiendo> tipiE = findTutteTipologieEndoEndoByCurrSoftwareAndTT(codiceFamiglia, pubblicaEnum);
		for (Tipiendo tfe : tipiE) {
		    ProcedimentoSimpleBean psb = new ProcedimentoSimpleBean();
		    if (tfe.getId().getCodice() == null) {
			psb.setId("T-1");
			psb.setText("ENDOPROCEDIMENTI");
		    } else {
			psb.setId("T" + tfe.getId().getCodice());
			psb.setText(tfe.getTipo());
		    }
		    psb.setHasChilds(Boolean.TRUE);
		    result.add(psb);
		}
	    } else if (codiceElemento.startsWith("T")) {
		String codiceTipiEndoStr = codiceElemento.replaceAll("T", "").trim();
		Integer codiceTipiEndo = Integer.parseInt(codiceTipiEndoStr);
		FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction software = new FilterRestriction();
		software.setAndOrRestriction(AndOrRestriction.OR);
		software.addFilterField(FilterUtils.equals("software.codice", WebConstants.SOFTWARE_TT, String.class));
		software.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
		ft.addRestriction(software);
		FilterRestriction disabilitato = new FilterRestriction();
		disabilitato.addFilterField(FilterUtils.notEquals("disabilitato", true, Boolean.class));
		ft.addRestriction(disabilitato);
		FilterRestriction endofr = new FilterRestriction();
		if (codiceTipiEndo == null || codiceTipiEndo.intValue() == -1) {
		    endofr.addFilterField(FilterUtils.isNull("tipiEndoId"));
		} else {
		    endofr.addFilterField(FilterUtils.equals("tipiEndoId", codiceTipiEndo, Integer.class));
		}
		ft.addRestriction(endofr);
		Boolean pubblica = null;
		switch (pubblicaEnum) {
		case DA_PUBBLICARE:
		    pubblica = Boolean.TRUE;
		    break;
		case NON_PUBBLICARE:
		    pubblica = Boolean.FALSE;
		    break;
		default:
		    break;
		}
		if (pubblica) {
		    FilterRestriction flagPubblicaFr = new FilterRestriction();
		    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", pubblica, Boolean.class));
		    ft.addRestriction(flagPubblicaFr);
		}
		ft.addOrder(FilterUtils.orderAsc("ordine"));
		ft.addOrder(FilterUtils.orderAsc("procedimento"));
		// // GIANPAOLO aggiungegere al ft il filtro per pubblica==true
		List<Inventarioprocedimenti> procs = this.findByFilterTable(ft, null, null);
		for (Inventarioprocedimenti tfe : procs) {
		    ProcedimentoSimpleBean psb = new ProcedimentoSimpleBean();
		    psb.setId(String.valueOf(tfe.getId().getCodice()));
		    psb.setText(tfe.getProcedimento());
		    psb.setHasChilds(Boolean.FALSE);
		    result.add(psb);
		}
	    }
	}
	return result;
    }

    @Override
    public List<String> findGerarchiaNodiPadre(String codiceElemento) {

	List<String> ret = new ArrayList<String>();
	List<ProcedimentoSimpleBean> s = this.findGerarchiaNodiPadreDettaglio(codiceElemento);
	for (ProcedimentoSimpleBean procedimentoSimpleBean : s) {
	    ret.add(procedimentoSimpleBean.getId());
	}
	return ret;
    }

    @Override
    public List<ProcedimentoSimpleBean> findGerarchiaNodiPadreDettaglio(String codiceElemento) {

	List<ProcedimentoSimpleBean> result = new ArrayList<ProcedimentoSimpleBean>();
	codiceElemento = StringUtils.defaultIfEmpty(codiceElemento, "");
	String descrizione = "";
	boolean hasChilds = false;
	int pos = 0;
	if (codiceElemento.startsWith("F")) {
	    String codiceTipiEndoStr = codiceElemento.replaceAll("F", "").trim();
	    Integer codiceTipiendo = Integer.parseInt(codiceTipiEndoStr);
	    Tipifamiglieendo te = tipifamiglieendoService.findById(new PkId(codiceTipiendo));
	    descrizione = te.getTipo();
	    hasChilds = true;
	} else if (codiceElemento.startsWith("T")) {
	    String codiceTipiEndoStr = codiceElemento.replaceAll("T", "").trim();
	    Integer codiceTipiendo = Integer.parseInt(codiceTipiEndoStr);
	    Tipiendo te = tipiendoService.findById(new PkId(codiceTipiendo));
	    descrizione = te.getTipo();
	    hasChilds = true;
	    if (te != null && BooleanUtils.toBoolean(te.getFlagPubblica())) {
		if (te.getTipifamiglieendo() != null && BooleanUtils.toBoolean(te.getTipifamiglieendo().getFlagPubblica())) {
		    result.add(pos++, newProcedimentoSimpleBean("F" + te.getTipifamiglieendo().getId().getCodice().intValue(),
			    te.getTipifamiglieendo().getTipo(), true));
		}
	    }
	} else {
	    Integer codiceendo = Integer.parseInt(codiceElemento);
	    Inventarioprocedimenti p = this.findById(new PkId(codiceendo));
	    descrizione = p.getProcedimento();
	    hasChilds = false;
	    if (p != null && BooleanUtils.toBoolean(p.getFlagPubblica())) {
		if (!BooleanUtils.isTrue(p.getDisabilitato())) {
		    if (p.getTipoendo() != null && BooleanUtils.toBoolean(p.getTipoendo().getFlagPubblica())) {
			if (p.getTipoendo().getTipifamiglieendo() != null
				&& BooleanUtils.toBoolean(p.getTipoendo().getTipifamiglieendo().getFlagPubblica())) {
			    result.add(pos++, newProcedimentoSimpleBean("F" + p.getTipoendo().getTipifamiglieendo().getId().getCodice(),
				    p.getTipoendo().getTipifamiglieendo().getTipo(), true));
			}
			result.add(pos++, newProcedimentoSimpleBean("T" + p.getTipoendo().getId().getCodice(), p.getTipoendo().getTipo(), true));
		    }
		}
	    }
	}
	result.add(pos++, newProcedimentoSimpleBean(codiceElemento, descrizione, hasChilds));
	return result;
    }

    private ProcedimentoSimpleBean newProcedimentoSimpleBean(String string, String tipo, boolean b) {

	ProcedimentoSimpleBean ret = new ProcedimentoSimpleBean();
	ret.setId(string);
	ret.setText(tipo);
	ret.setHasChilds(b);
	return ret;
    }

    @Override
    public List<Tipifamiglieendo> findTutteFamiglieEndoByCurrSoftwareAndTT(FlagPubblicaEnum flagPubblicaEnum) {

	return inventarioprocedimentiDAO.findTutteFamiglieEndoByCurrSoftwareAndTT(flagPubblicaEnum);
    }

    @Override
    public List<Tipiendo> findTutteTipologieEndoEndoByCurrSoftwareAndTT(Integer codiceFamiglia, FlagPubblicaEnum pubblicaEnum) {

	return inventarioprocedimentiDAO.findTutteTipologieEndoByCurrSoftwareAndTT(codiceFamiglia, pubblicaEnum);
    }

    @Override
    public ProcedimentoBean findProcedimentoBean(Integer codiceProcedimento) {

	Inventarioprocedimenti ip = this.findById(new PkId(codiceProcedimento));
	ProcedimentoBean result = new ProcedimentoBean();
	if (ip != null) {
	    result.setId(codiceProcedimento);
	    result.setNome(ip.getProcedimento());
	    result.setDescrizione(ip.getDatigenerali());
	    result.setRequisiti(ip.getCampoapplicazione());
	    if (ip.getDataaggiornamento() != null) {
		String date = Utilities.formatDate(ip.getDataaggiornamento(), false);
		result.setDataAggiornamento(date);
	    }
	    String amministrazione = "";
	    //	    if (ip.getSoftware().getCodice().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    // al momento l'amministrazione è solo quella dell'endo
	    //		// SE TT INVENTARIOPROCEDIMENTISOFTWARE
	    //		Software s = softwareService.findById(ORMHelper.getSoftware());
	    //		if (s != null) {
	    //		    List<Inventarioprocedimentisoftware> softw = inventarioprocedimentisoftwareService.findByEndoAndSoftware(ip, s);
	    //		}
	    //	    }
	    if (StringUtils.isNotBlank(amministrazione)) {
		if (ip.getAmministrazioni() != null) {
		    amministrazione = ip.getAmministrazioni().getAmministrazione();
		}
	    }
	    result.setAmministrazione(amministrazione);
	    result.setAdempimenti(ip.getAdempimenti());
	    if (ip.getNaturaendo() != null) {
		result.setNatura(ip.getNaturaendo().getNatura());
	    }
	    if (ip.getTipoendo() != null && BooleanUtils.toBoolean(ip.getTipoendo().getFlagPubblica())) {
		result.setTipologia(ip.getTipoendo().getTipo());
	    }
	    List<OneriBean> oneri = new ArrayList<OneriBean>();
	    List<Inventarioprocedimentioneri> ivs = inventarioprocedimentioneriService.findByCodiceInventario(codiceProcedimento, true);//?
	    for (Inventarioprocedimentioneri ips : ivs) {
		if (ips.getImporto() != null) {
		    if (ips.getImporto().compareTo(BigDecimal.ZERO) > 0) {
			OneriBean o = new OneriBean();
			o.setCausale(ips.getTipicausalioneri().getCoDescrizione());
			o.setImporto(ips.getImporto().doubleValue());
			o.setNote(ips.getNote());
			oneri.add(o);
		    }
		}
	    }
	    result.setOneri(oneri);
	    List<NormativaBean> normativa = new ArrayList<NormativaBean>();
	    List<InventarioprocLeggi> leggis = inventarioprocLeggiService.findByCodiceinventario(codiceProcedimento);//???
	    for (InventarioprocLeggi ipl : leggis) {
		NormativaBean n = new NormativaBean();
		n.setDescrizione(ipl.getLeggi().getLeDescrizione());
		n.setLink(ipl.getLeggi().getLeLink());
		if (ipl.getLeggi().getLeggitipi() != null) {
		    n.setTipologia(ipl.getLeggi().getLeggitipi().getLtDescrizione());
		}
		if (ipl.getLeggi().getOggetto() != null) {
		    String uid = oggettiService.insertOrGetUID(ipl.getLeggi().getOggetto().getId().getCodice());
		    n.setCodiceOggetto(uid);
		}
		normativa.add(n);
	    }
	    result.setNormativa(normativa);
	    List<ModulisticaBean> modulistica = new ArrayList<ModulisticaBean>();
	    List<Allegati> alls = allegatiService.findByInventarioprocedimenti(codiceProcedimento);
	    for (Allegati all : alls) {
		int pubblica = 0;
		if (all.getPubblica() != null) {
		    pubblica = all.getPubblica().intValue();
		}
		if (pubblica == 1 || pubblica == 3) { //1= Area riservata e frontoffice, 3=Solofrontoffice
		    ModulisticaBean m = new ModulisticaBean();
		    m.setDescrizione(all.getAllegato());
		    m.setObbligatorio(all.getRichiesto() == null ? Boolean.FALSE : all.getRichiesto().booleanValue());
		    m.setLink(all.getIndirizzoweb());
		    if (all.getOggetti() != null) {
			Integer codiceOggetto = all.getOggetti().getId().getCodice();
			if (codiceOggetto != null) {
			    String uid = oggettiService.insertOrGetUID(codiceOggetto);
			    String formati = all.getFoTipodownload();
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
		    modulistica.add(m);
		}
	    }
	    result.setModulistica(modulistica);
	    List<InventarioprocEndo> s = inventarioprocEndoService.findByInventarioprocT(ip.getId().getIdcomune(), ip.getId().getCodice(),
		    Boolean.TRUE, null, null, null, true);
	    if (s != null) {
		List<EndoprocedimentoSimpleBean> lsub = new ArrayList<EndoprocedimentoSimpleBean>();
		for (InventarioprocEndo inventarioprocEndo : s) {
		    EndoprocedimentoSimpleBean es = new EndoprocedimentoSimpleBean();
		    es.setId(inventarioprocEndo.getInventarioprocEndoD().getId().getCodice());
		    es.setNome(inventarioprocEndo.getInventarioprocEndoD().getProcedimento());
		    es.setOrdine(inventarioprocEndo.getInventarioprocEndoD().getOrdine() == null ? 0
			    : inventarioprocEndo.getInventarioprocEndoD().getOrdine().intValue());
		    es.setPrincipale(Boolean.FALSE);
		    lsub.add(es);
		}
		if (lsub.size() > 0) {
		    result.setProcedimentiCollegati(lsub);
		}
	    }
	}
	return result;
    }

    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    private SoftwareService softwareService;

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    public List<ProcedimentoSimpleBean> findProcedimentiByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca,
	    FlagPubblicaEnum pubblicaEnum, Integer firstResult, Integer maxResults) {

	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<ProcedimentoSimpleBean>();
	}
	try {
	    testoDaCercare = URLDecoder.decode(testoDaCercare, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	}
	testoDaCercare = testoDaCercare.replaceAll("%", "");
	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<ProcedimentoSimpleBean>();
	}
	List<ProcedimentoSimpleBean> list = new ArrayList<ProcedimentoSimpleBean>();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction software = new FilterRestriction();
	software.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, String.class));
	ft.addRestriction(software);
	tipoRicerca = StringUtils.defaultIfEmpty(tipoRicerca, "tutteParole");
	campiRicerca = StringUtils.defaultIfEmpty(campiRicerca, "titoli");
	if (tipoRicerca.equals("tutteParole")) {
	    String[] valori = testoDaCercare.split(" ");
	    for (String v : valori) {
		FilterRestriction fr = new FilterRestriction();
		fr.setAndOrRestriction(AndOrRestriction.OR);
		fr.addFilterField(FilterUtils.like("procedimento", v));
		// Nuova ricerca per parole chiave
		fr.addFilterField(FilterUtils.like("paroleChiave", v));
		ft.addRestriction(fr);
	    }
	} else if (tipoRicerca.equals("interaFrase")) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    fr.addFilterField(FilterUtils.like("procedimento", testoDaCercare));
	    // Nuova ricerca per parole chiave
	    fr.addFilterField(FilterUtils.like("paroleChiave", testoDaCercare));
	    ft.addRestriction(fr);
	} else { // almenoUnaParola
	    String[] valori = testoDaCercare.split(" ");
	    FilterRestriction fr = new FilterRestriction();
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    for (String v : valori) {
		fr.addFilterField(FilterUtils.like("procedimento", v));
		// Nuova ricerca per parole chiave
		fr.addFilterField(FilterUtils.like("paroleChiave", v));
	    }
	    ft.addRestriction(fr);
	}
	FilterRestriction abilitati = new FilterRestriction();
	abilitati.addFilterField(FilterUtils.notEquals("disabilitato", Boolean.TRUE, Boolean.class));
	ft.addRestriction(abilitati);
	// Controlla se deve filtrare per il flag_pubblica
	FilterRestriction flagPubblicaFr = new FilterRestriction();
	switch (pubblicaEnum) {
	case DA_PUBBLICARE:
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.TRUE, Boolean.class));
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.TRUE, "tipoendo", Boolean.class));
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.TRUE, "tipoendo.tipifamiglieendo", Boolean.class));
	    ft.addRestriction(flagPubblicaFr);
	    break;
	case NON_PUBBLICARE:
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.FALSE, Boolean.class));
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.FALSE, "tipoendo.flagPubblica", Boolean.class));
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.FALSE, "tipoendo.tipifamiglieendo.flagPubblica", Boolean.class));
	    ft.addRestriction(flagPubblicaFr);
	    break;
	default:
	    break;
	}
	List<Inventarioprocedimenti> res = inventarioprocedimentiDAO.findByFilterTable(ft, firstResult, maxResults);
	int pos = 0;
	for (Inventarioprocedimenti ap : res) {
	    ProcedimentoSimpleBean isb = new ProcedimentoSimpleBean();
	    isb.setId(String.valueOf(ap.getId().getCodice()));
	    isb.setText(ap.getProcedimento());
	    isb.setHasChilds(Boolean.FALSE);
	    list.add(pos, isb);
	    pos++;
	}
	// ricerca in tipiendo
	// GIANPAOLO aggiungere alla ft filtro flagPubblica== true usare ENUM? (verificare se usato nel back)
	List<Tipiendo> tes = tipiendoService.findTipiendoByDescrizione(testoDaCercare, tipoRicerca, campiRicerca, pubblicaEnum, firstResult,
		maxResults);
	for (Tipiendo ap : tes) {
	    ProcedimentoSimpleBean isb = new ProcedimentoSimpleBean();
	    isb.setId("T" + String.valueOf(ap.getId().getCodice()));
	    isb.setText(ap.getTipo());
	    // verifica se ha figli
	    isb.setHasChilds(Boolean.TRUE);
	    list.add(pos, isb);
	    pos++;
	}
	// ricerca in tipifamiglieendo
	// GIANPAOLO aggiungere alla ft filtro flagPubblica== true usare ENUM? (verificare se usato nel back)
	List<Tipifamiglieendo> tfs = tipifamiglieendoService.findTipifamigliaByDescrizione(testoDaCercare, tipoRicerca, campiRicerca, pubblicaEnum,
		firstResult, maxResults);
	for (Tipifamiglieendo ap : tfs) {
	    ProcedimentoSimpleBean isb = new ProcedimentoSimpleBean();
	    isb.setId("F" + String.valueOf(ap.getId().getCodice()));
	    isb.setText(ap.getTipo());
	    // verifica se ha figli
	    isb.setHasChilds(Boolean.TRUE);
	    list.add(pos, isb);
	    pos++;
	}
	Collections.sort(list, new ProcedimentoSimpleBeanComparator());
	return list;
    }

    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologiaGruppiEndo(String textToSearch, Integer codiceFamiglia,
	    Integer codiceTipologia, boolean escludiDisabilitati) {

	return inventarioprocedimentiDAO.findByDescrizioneFamigliaendoETipologiaGruppiEndo(textToSearch, codiceFamiglia, codiceTipologia,
		escludiDisabilitati);
    }

    @Override
    public List<Inventarioprocedimenti> findByCodiciInventario(Set<Integer> codiciEndoprocedimenti) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	Integer[] codiciEndo = codiciEndoprocedimenti.toArray(new Integer[codiciEndoprocedimenti.size()]);
	fr.addFilterField(FilterUtils.in("id.codice", codiciEndo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("procedimento"));
	return inventarioprocedimentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Inventarioprocedimenti> findProcedimentiPrincipaliByFilter(String filter) {

	return this.inventarioprocedimentiDAO.findProcedimentiPrincipaliByFilter(filter);
    }
}
