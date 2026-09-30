/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Comuni;
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
import it.gruppoinit.pal.gp.core.domain.Naturaendobase;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Testiestesi;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.helper.InventarioprocedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DownloadBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.EndoprocedimentoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ModulisticaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NormativaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.OneriBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoSimpleBeanComparator;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.EndoCausaliService;
import it.gruppoinit.pal.gp.core.service.EndoContiService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocLeggiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocTipititoloService;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiincompService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentioneriService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentipeopleService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.NaturaendobaseService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;
import it.gruppoinit.pal.gp.core.service.TestiestesiService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
    private ComuniService comuniService;
    private DocumentiService documentiService;
    private InventarioprocedimentioneriService inventarioprocedimentioneriService;
    private Inventarioprocdyn2modellitService inventarioprocdyn2modellitService;
    private EndoCausaliService endoCausaliService;
    private EndoContiService endoContiService;
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    private InventarioprocedimentiincompService inventarioprocedimentiincompService;
    private AmministrazioniService amministrazioniService;
    private AmministrazionireferentiService amministrazionireferentiService;
    private NaturaendobaseService naturaendobaseService;
    private StpEndoTipo1Service stpEndoTipo1Service;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private TempificazioniService tempificazioniService;
    private TipiendoService tipiendoService;
    private TipifamiglieendoService tipifamiglieendoService;
    private InventarioprocLeggiService inventarioprocLeggiService;
    private InventarioprocTipititoloService inventarioprocTipititoloService;
    private InventarioprocEndoService inventarioprocEndoService;
    private AlberoprocService alberoprocService;
    private AlberoprocEndoService alberoprocEndoService;
    private EndoRegioneToscanaService endoRegioneToscanaService;

    @Autowired
    public void setEndoRegioneToscanaService(EndoRegioneToscanaService endoRegioneToscanaService) {

	this.endoRegioneToscanaService = endoRegioneToscanaService;
    }

    @Autowired
    public void setAlberoprocEndoService(AlberoprocEndoService alberoprocEndoService) {

	this.alberoprocEndoService = alberoprocEndoService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setTipifamiglieendoService(TipifamiglieendoService tipifamiglieendoService) {

	this.tipifamiglieendoService = tipifamiglieendoService;
    }

    @Autowired
    public void setNaturaendoService(NaturaendobaseService naturaendoService) {

	this.naturaendobaseService = naturaendoService;
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

    @Autowired
    public void setInventarioprocEndoService(InventarioprocEndoService inventarioprocEndoService) {

	this.inventarioprocEndoService = inventarioprocEndoService;
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

	//	Set<Inventarioprocedimentipeople> inventarioprocedimentipeoples = entity.getInventarioprocedimentipeoples();
	//	for (Inventarioprocedimentipeople inventarioprocedimentipeople : inventarioprocedimentipeoples) {
	//	    inventarioprocedimentipeopleService.delete(inventarioprocedimentipeople);
	//	}
	Set<InventarioprocLeggi> inventarioprocLeggis = entity.getInventarioprocLeggis();
	for (InventarioprocLeggi inventarioprocLeggi : inventarioprocLeggis) {
	    inventarioprocLeggiService.delete(inventarioprocLeggi);
	}
	Set<Allegati> allegatis = entity.getAllegatis();
	for (Allegati allegati : allegatis) {
	    allegatiService.delete(allegati);
	}
	//	Set<Documenti> documentis = entity.getDocumentis();
	//	for (Documenti documenti : documentis) {
	//	    documentiService.delete(documenti);
	//	}
	Set<Inventarioprocedimentiincomp> inventarioprocedimentiincomps = entity.getInventarioprocedimentiincomps();
	for (Inventarioprocedimentiincomp inventarioprocedimentiincomp : inventarioprocedimentiincomps) {
	    inventarioprocedimentiincompService.delete(inventarioprocedimentiincomp);
	}
	Set<Inventarioprocedimentiincomp> inventarioprocedimentiincompcols = entity.getInventarioprocedimentiincompsCol();
	for (Inventarioprocedimentiincomp inventarioprocedimentiincompcol : inventarioprocedimentiincompcols) {
	    inventarioprocedimentiincompService.delete(inventarioprocedimentiincompcol);
	}
	//	Set<Testiestesi> testiestesis = entity.getTestiestesis();
	//	for (Testiestesi testiestesi : testiestesis) {
	//	    testiestesiService.delete(testiestesi);
	//	}
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
	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(entity.getId().getIdcomune(), entity.getId().getCodice());
	if (stpEndoTipo1 != null) {
	    stpEndoTipo1Service.delete(stpEndoTipo1);
	}
	List<StpEndoTipo2> endoTipo2 = stpEndoTipo2Service.findByInventarioproc(entity.getId().getIdcomune(), entity.getId().getCodice());
	for (StpEndoTipo2 stpEndo : endoTipo2) {
	    stpEndo.setInventarioprocedimenti(null);
	    stpEndoTipo2Service.update(stpEndo);
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

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentiDAO.insert(entity);
	}
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
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologia(String textToSearch, Integer codiceFamiglia, String idComuneFamiglia,
	    Integer codiceTipologia, String idComuneTipologia, Boolean escludiDisabilitati) {

	return inventarioprocedimentiDAO.findByDescrizioneFamigliaendoETipologia(textToSearch, codiceFamiglia, idComuneFamiglia, codiceTipologia,
		idComuneTipologia, escludiDisabilitati);
    }

    public Inventarioprocedimenti insertCopia(Inventarioprocedimenti entity) {

	InventarioprocedimentiHelper copy = new InventarioprocedimentiHelper();
	if (validateCopia(entity) && isInsertAllowed(entity)) {
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
	//	if (master.getTempificazione() != null)
	//	    copy.getInventarioprocedimenti().setTempificazione(master.getTempificazione());
	if (master.getTipoendo() != null)
	    copy.getInventarioprocedimenti().setTipoendo(master.getTipoendo());
	if (master.getAmministrazionireferente() != null)
	    copy.getInventarioprocedimenti().setAmministrazionireferente(master.getAmministrazionireferente());
	if (master.getNaturaendo() != null)
	    copy.getInventarioprocedimenti().setNaturaendo(master.getNaturaendo());
	if (master.getSoftware() != null)
	    copy.getInventarioprocedimenti().setSoftware(master.getSoftware());
	// Creazione della replica di inevtario procedimenti peolple
	// Set<Inventarioprocedimentipeople> peolplesMaster = master.getInventarioprocedimentipeoples();
	Set<Inventarioprocedimentipeople> peoplesCopy = copy.getInventarioprocedimentipeoples();
	Inventarioprocedimentipeople peopleCopy = null;
	//	for (Inventarioprocedimentipeople peopleMaster : peolplesMaster) {
	//	    peopleCopy = new Inventarioprocedimentipeople();
	//	    if (peopleMaster.getCodProcPeople() != null)
	//		peopleCopy.setCodProcPeople(peopleMaster.getCodProcPeople());
	//	    peoplesCopy.add(peopleCopy);
	//	}
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
	// Set<Testiestesi> testisMaster = master.getTestiestesis();
	Set<Testiestesi> testiestesisCopy = copy.getTestiestesis();
	Testiestesi testiestesiCopy = null;
	//	for (Testiestesi testiestesiMaster : testisMaster) {
	//	    testiestesiCopy = new Testiestesi();
	//	    if (testiestesiMaster.getOggetti() != null)
	//		testiestesiCopy.setOggetti(testiestesiMaster.getOggetti());
	//	    if (testiestesiMaster.getNormativa() != null)
	//		testiestesiCopy.setNormativa(testiestesiMaster.getNormativa());
	//	    if (testiestesiMaster.getNormative() != null)
	//		testiestesiCopy.setNormative(testiestesiMaster.getNormative());
	//	    if (testiestesiMaster.getNomefile() != null)
	//		testiestesiCopy.setNomefile(testiestesiMaster.getNomefile());
	//	    if (testiestesiMaster.getIndirizzoweb() != null)
	//		testiestesiCopy.setIndirizzoweb(testiestesiMaster.getIndirizzoweb());
	//	    testiestesisCopy.add(testiestesiCopy);
	//	}
	copy.setTestiestesis(testiestesisCopy);
	// Creazione della replica della lista dei documenti
	// Set<Documenti> documentisMaster = master.getDocumentis();
	Set<Documenti> documentisCopy = copy.getDocumentis();
	Documenti documentiCopy = null;
	//	for (Documenti documentiMaster : documentisMaster) {
	//	    documentiCopy = new Documenti();
	//	    if (documentiMaster.getOggetti() != null)
	//		documentiCopy.setOggetti(documentiMaster.getOggetti());
	//	    if (documentiMaster.getAmministrazioni() != null)
	//		documentiCopy.setAmministrazioni(documentiMaster.getAmministrazioni());
	//	    if (documentiMaster.getDocumento() != null)
	//		documentiCopy.setDocumento(documentiMaster.getDocumento());
	//	    if (documentiMaster.getIndirizzoweb() != null)
	//		documentiCopy.setIndirizzoweb(documentiMaster.getIndirizzoweb());
	//	    documentisCopy.add(documentiCopy);
	//	}
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
	    //invenOneriCopy.setFlagPagato(invenOneriMaster.getFlagPagato());
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

    protected boolean isInsertAllowed(Inventarioprocedimenti entity) {

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
	//	if (entity.getStpEndoTipo1s().size() > 0) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "STP_ENDO_TIPO1", null));
	//	}
	//	if (entity.getStpEndoTipo2s().size() > 0) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "STP_ENDO_TIPO2", null));
	//	}
	if (!inventarioprocEndoService.findByInventarioprocD(entity.getId().getIdcomune(), entity.getId().getCodice(), null).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROC_ENDO", null));
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
		    String message = "Esiste una incompatibilità tra gli endoprocedimenti [" + inv.getProcedimento() + "] e ["
			    + inventarioprocedimentiincomp.getInventarioprocedimentoincompatibile().getProcedimento() + "]";
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
	if (entity.getDisabilitato() == null) {
	    entity.setDisabilitato(Boolean.FALSE);
	}
	if (entity.getPerprovvedimento() == null) {
	    entity.setPerprovvedimento(Boolean.FALSE);
	}
	if (entity.getNoneseguecontromovobblig() == null) {
	    entity.setNoneseguecontromovobblig(Boolean.FALSE);
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

	Tipiendo tipiendo = tipiendoService.bindDomainObject(entity.getTipoendo(), PkId.class, "id.codice");
	entity.setTipoendo(tipiendo);
	Naturaendobase naturaendo = naturaendobaseService.bindDomainObject(entity.getNaturaendo(), Integer.class, "id");
	entity.setNaturaendo(naturaendo);
	Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService.bindDomainObject(entity.getAmministrazionireferente(),
		PkId.class, "id.codice");
	entity.setAmministrazionireferente(amministrazionireferenti);
	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	//	Tempificazioni tempificazioni = tempificazioniService.bindDomainObject(entity.getTempificazione(), PkId.class, "id.codice");
	//	entity.setTempificazione(tempificazioni);
	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
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
	if (BooleanUtils.isTrue(inventarioprocedimenti.getFlagTransientIsTipo2())
		|| BooleanUtils.isTrue(inventarioprocedimenti.getFlagTransientIsTipo1())) {
	    filterTable = new FilterTable(DAOEnum.FIND_ALL);
	    restriction.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomunebase(), String.class));
	    restriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	}
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
	if (inventarioprocedimenti.getAmministrazioni() != null
		&& StringUtils.isNotBlank(inventarioprocedimenti.getAmministrazioni().getAmministrazione())) {
	    restriction.addFilterField(
		    FilterUtils.like("amministrazione", inventarioprocedimenti.getAmministrazioni().getAmministrazione(), "amministrazioni"));
	}
	filterTable.addRestriction(restriction);
	//	if (BooleanUtils.isTrue(inventarioprocedimenti.getFlagTransientIsTipo2())) {
	//	    restriction.addFilterField(FilterUtils.isNotEmpty("stpEndoTipo2s"));
	//	}
	//	if (BooleanUtils.isTrue(inventarioprocedimenti.getFlagTransientIsTipo1())) {
	//	    restriction.addFilterField(FilterUtils.isNotEmpty("stpEndoTipo1s"));
	//	}
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
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologiaAndSoftware(String textToSearch, Integer codiceFamiglia,
	    Integer codiceTipologia, String[] codicesoftware, String idcomunebase, String tipoEndo) {

	FilterTable ft = null;
	FilterRestriction fr = createFilterRestrictionForDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(textToSearch, codiceFamiglia,
		codiceTipologia, codicesoftware, null, false, true, tipoEndo);
	if (StringUtils.isNotBlank(idcomunebase)) {
	    ft = new FilterTable(DAOEnum.FIND_ALL);
	    fr.addFilterField(FilterUtils.equals("id.idcomune", idcomunebase, String.class));
	} else {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	}
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
	String[] software = new String[1];
	software[0] = codicesoftware;
	FilterRestriction fr = createFilterRestrictionForDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(textToSearch, codiceFamiglia,
		codiceTipologia, software, listCodicinature, true, true, null);
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
	    Integer codiceFamiglia, Integer codiceTipologia, String[] codicesoftware, List<Integer> listCodicinature, boolean isFindToCurrentSoftware,
	    boolean escludiDisabilitati, String tipoEndo) {

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
	if (codicesoftware != null && codicesoftware.length > 0) {
	    if (isFindToCurrentSoftware) {
		filterRestriction
			.addFilterField(FilterUtils.in("codice", new Object[] { codicesoftware, ORMHelper.getSoftware() }, "software", String.class));
	    } else {
		filterRestriction.addFilterField(FilterUtils.in("codice", codicesoftware, "software", String.class));
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
	if (ORMHelper.isConsoleRegionale()) { // la tipologia la disinguo solo in caso di consolle regionale
	    if (StringUtils.isNotBlank(tipoEndo)) {
		//		if (tipoEndo.equalsIgnoreCase("STP2")) {
		//		    /**
		//		     * dove existsTipoMovimento.setExistsChildEntityId("istanza.id") rappresenta la relazione di join
		//		     * tra la tabella movimenti con istanze (istanza.id appunto) e
		//		     * existsTipoMovimento.setExistsParentEntityId("id") rappresenta l'identificativo della tabella
		//		     * specificata da getEntityClass()e che genera i detached criteria (nel nostro caso Istanze)
		//		     */
		//		    // FilterRestriction stpendotipo2 = new FilterRestriction();
		//		    String hierarchy = "stpEndoTipo2s";
		//		    FilterField existsStpEndo2 = new FilterField("tipo", hierarchy, FieldOperationsEnum.EXISTS, new String[] { "ENDO" },
		//			    StpEndoTipo2.class);
		//		    existsStpEndo2.setExistsChildEntityId("inventarioprocedimenti.id");
		//		    existsStpEndo2.setExistsParentEntityId("id");
		//		    // stpendotipo2.addFilterField(existsStpEndo2);
		//		    filterRestriction.addFilterField(existsStpEndo2);
		//		} else {
		//		    String hierarchy = "stpEndoTipo2s";
		//		    FilterField existsStpEndo2 = new FilterField("tipo", hierarchy, FieldOperationsEnum.NOTEXISTS, new String[] { "ENDO" },
		//			    StpEndoTipo2.class);
		//		    existsStpEndo2.setExistsChildEntityId("inventarioprocedimenti.id");
		//		    existsStpEndo2.setExistsParentEntityId("id");
		//		    // stpendotipo2.addFilterField(existsStpEndo2);
		//		    filterRestriction.addFilterField(existsStpEndo2);
		//		}
	    }
	}
	return filterRestriction;
    }

    @Override
    public List<Integer> findCodiciEndoPerSoftware(String software) {

	return inventarioprocedimentiDAO.findCodiciEndoPerSoftware(software);
    }

    @Override
    public List<Inventarioprocedimenti> findByAlberoprocArendo(AlberoprocArendo alberoprocArendo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", alberoprocArendo.getId().getIdcomune(), String.class));
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
		ft.addOrder(FilterUtils.orderAsc("ordine"));
		ft.addOrder(FilterUtils.orderAsc("procedimento"));
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

	List<String> result = new ArrayList<String>();
	codiceElemento = StringUtils.defaultIfEmpty(codiceElemento, "");
	int pos = 0;
	if (codiceElemento.startsWith("F")) {
	    //   
	} else if (codiceElemento.startsWith("T")) {
	    String codiceTipiEndoStr = codiceElemento.replaceAll("T", "").trim();
	    Integer codiceTipiendo = Integer.parseInt(codiceTipiEndoStr);
	    Tipiendo te = tipiendoService.findById(new PkId(codiceTipiendo));
	    if (te != null) {
		if (te.getTipifamiglieendo() != null) {
		    result.add(pos++, "F" + te.getTipifamiglieendo().getId().getCodice().intValue());
		}
	    }
	} else {
	    Integer codiceendo = Integer.parseInt(codiceElemento);
	    Inventarioprocedimenti p = this.findById(new PkId(codiceendo));
	    if (p != null) {
		if (!BooleanUtils.isTrue(p.getDisabilitato())) {
		    if (p.getTipoendo() != null) {
			if (p.getTipoendo().getTipifamiglieendo() != null) {
			    result.add(pos++, "F" + p.getTipoendo().getTipifamiglieendo().getId().getCodice());
			}
			result.add(pos++, "T" + p.getTipoendo().getId().getCodice());
		    }
		}
	    }
	}
	result.add(pos++, codiceElemento);
	return result;
    }

    @Override
    public List<Tipifamiglieendo> findTutteFamiglieEndoByCurrSoftwareAndTT(FlagPubblicaEnum flagPubblicaEnum) {

	return inventarioprocedimentiDAO.findTutteFamiglieEndoByCurrSoftwareAndTT(flagPubblicaEnum);
    }

    @Override
    public List<Tipiendo> findTutteTipologieEndoEndoByCurrSoftwareAndTT(Integer codiceFamiglia, FlagPubblicaEnum flagPubblicaEnum) {

	return inventarioprocedimentiDAO.findTutteTipologieEndoByCurrSoftwareAndTT(codiceFamiglia, flagPubblicaEnum);
    }

    @Override
    public ProcedimentoBean findProcedimentoBean(Integer codiceProcedimento, boolean isRegionale, String codiceComune) {

	Inventarioprocedimenti ip = null;
	if (isRegionale) {
	    ip = this.findById(new PkId(ORMHelper.getIdcomunebase(), codiceProcedimento));
	} else {
	    ip = this.findById(new PkId(codiceProcedimento));
	}
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
	    if (ip.getTipoendo() != null) {
		result.setTipologia(ip.getTipoendo().getTipo());
	    }
	    List<OneriBean> oneri = new ArrayList<OneriBean>();
	    List<Inventarioprocedimentioneri> ivs = inventarioprocedimentioneriService.findByCodiceInventario(codiceProcedimento, true,
		    ip.getId().getIdcomune(), codiceComune);
	    if (!ivs.isEmpty()) {
		if (StringUtils.isNotBlank(codiceComune)) {
		    Map<String, List<Inventarioprocedimentioneri>> lista = new HashMap<String, List<Inventarioprocedimentioneri>>();
		    Set<Integer> causali = new HashSet<Integer>();
		    for (Inventarioprocedimentioneri ips : ivs) {
			causali.add(ips.getTipicausalioneri().getId().getCodice());
			String key = StringUtils.defaultIfEmpty(ips.getCodiceComune(), "TUTTI") + "-"
				+ ips.getTipicausalioneri().getId().getCodice().intValue();
			List<Inventarioprocedimentioneri> l = lista.get(key);
			if (l == null) {
			    l = new ArrayList<Inventarioprocedimentioneri>();
			}
			l.add(ips);
			lista.put(key, l);
		    }
		    List<Inventarioprocedimentioneri> finale = new ArrayList<Inventarioprocedimentioneri>();
		    for (Integer codiceCausale : causali) {
			List<Inventarioprocedimentioneri> perTutti = lista.get("TUTTI-" + codiceCausale.intValue());
			List<Inventarioprocedimentioneri> perComune = lista.get(codiceComune + "-" + codiceCausale.intValue());
			if (!(perComune == null || perComune.isEmpty())) {
			    finale.addAll(perComune);
			} else {
			    if (!(perTutti == null || perTutti.isEmpty())) {
				finale.addAll(perTutti);
			    }
			}
			// recupero la lista degli oneri per causale 
			// vedo se cè la lista che sovrascrive per comune se si metto questa altrimenti quella generiacf
		    }
		    for (Inventarioprocedimentioneri ips : finale) {
			if (ips.getImporto() != null && ips.getImporto().compareTo(BigDecimal.ZERO) > 0) {
			    OneriBean o = new OneriBean();
			    o.setCausale(ips.getTipicausalioneri().getCoDescrizione());
			    o.setImporto(ips.getImporto().doubleValue());
			    o.setNote(ips.getNote());
			    oneri.add(o);
			}
		    }
		} else {
		    for (Inventarioprocedimentioneri ips : ivs) {
			if (ips.getImporto() != null && ips.getImporto().compareTo(BigDecimal.ZERO) > 0) {
			    OneriBean o = new OneriBean();
			    o.setCausale(ips.getTipicausalioneri().getCoDescrizione());
			    o.setImporto(ips.getImporto().doubleValue());
			    o.setNote(ips.getNote());
			    oneri.add(o);
			}
		    }
		}
		result.setOneri(oneri);
	    }
	    List<NormativaBean> normativa = new ArrayList<NormativaBean>();
	    List<InventarioprocLeggi> leggis = inventarioprocLeggiService.findByCodiceinventario(codiceProcedimento, ip.getId().getIdcomune());
	    for (InventarioprocLeggi ipl : leggis) {
		NormativaBean n = new NormativaBean();
		n.setDescrizione(ipl.getLeggi().getLeDescrizione());
		n.setLink(ipl.getLeggi().getLeLink());
		if (ipl.getLeggi().getLeggitipi() != null) {
		    n.setTipologia(ipl.getLeggi().getLeggitipi().getLtDescrizione());
		}
		if (ipl.getLeggi().getOggetto() != null) {
		    String uid = oggettiService.insertOrGetUID(ipl.getLeggi().getOggetto().getId().getCodice(),
			    ipl.getLeggi().getOggetto().getId().getIdcomune());
		    n.setCodiceOggetto(uid);
		}
		normativa.add(n);
	    }
	    result.setNormativa(normativa);
	    List<ModulisticaBean> modulistica = new ArrayList<ModulisticaBean>();
	    List<Allegati> alls = null;
	    if (ORMHelper.getIdcomunebase().equalsIgnoreCase(ip.getId().getIdcomune())) { // IN CASO DI ENDO REGIONALE
		alls = endoRegioneToscanaService.getAllegatiFromProcedimentoRegionale("", FieldOperationsEnum.EQIGNORECASE, ip, codiceComune);
	    } else {
		alls = endoRegioneToscanaService.getAllegatiFromProcedimentoLocale("", FieldOperationsEnum.EQIGNORECASE, ip, codiceComune);
	    }
	    // List<Allegati> alls = allegatiService.findByInventarioprocedimenti(ip.getId().getIdcomune(), codiceProcedimento);
	    for (Allegati all : alls) {
		int pubblica = 0;
		if (all.getPubblica() != null) {
		    pubblica = all.getPubblica().intValue();
		}
		if (pubblica == 1 || pubblica == 3) { //1= Area riservata e frontoffice, 3=Solofrontoffice
		    ModulisticaBean m = new ModulisticaBean();
		    m.setDescrizione(all.getAllegato());
		    m.setObbligatorio(all.getRichiesto() == null ? Boolean.FALSE : all.getRichiesto().booleanValue());
		    if (all.getOggetti() != null) {
			Integer codiceOggetto = all.getOggetti().getId().getCodice();
			if (codiceOggetto != null) {
			    String uid = oggettiService.insertOrGetUID(codiceOggetto, all.getOggetti().getId().getIdcomune());
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
		    Boolean.TRUE, null, null, codiceComune, true);
	    if (s != null) {
		List<EndoprocedimentoSimpleBean> lsub = new ArrayList<EndoprocedimentoSimpleBean>();
		for (InventarioprocEndo inventarioprocEndo : s) {
		    EndoprocedimentoSimpleBean es = new EndoprocedimentoSimpleBean();
		    es.setId(inventarioprocEndo.getInventarioprocEndoD().getId().getCodice());
		    es.setIdcomune(inventarioprocEndo.getInventarioprocEndoD().getId().getIdcomune());
		    es.setNome(inventarioprocEndo.getInventarioprocEndoD().getProcedimento());
		    es.setOrdine(inventarioprocEndo.getInventarioprocEndoD().getOrdine() == null ? 0
			    : inventarioprocEndo.getInventarioprocEndoD().getOrdine().intValue());
		    es.setPrincipale(Boolean.FALSE);
		    if (ORMHelper.getIdcomunebase().equalsIgnoreCase(inventarioprocEndo.getInventarioprocEndoD().getId().getIdcomune())) {
			es.setRegionale(Boolean.TRUE);
		    } else {
			es.setRegionale(Boolean.FALSE);
		    }
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
	    Integer firstResult, Integer maxResults) {

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
		fr.addFilterField(FilterUtils.like("procedimento", v));
		ft.addRestriction(fr);
	    }
	} else if (tipoRicerca.equals("interaFrase")) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.like("procedimento", testoDaCercare));
	    ft.addRestriction(fr);
	} else { // almenoUnaParola
	    String[] valori = testoDaCercare.split(" ");
	    FilterRestriction fr = new FilterRestriction();
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    for (String v : valori) {
		fr.addFilterField(FilterUtils.like("procedimento", v));
	    }
	    ft.addRestriction(fr);
	}
	FilterRestriction abilitati = new FilterRestriction();
	abilitati.addFilterField(FilterUtils.notEquals("disabilitato", Boolean.TRUE, Boolean.class));
	ft.addRestriction(abilitati);
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
	List<Tipiendo> tes = tipiendoService.findTipiendoByDescrizione(testoDaCercare, tipoRicerca, campiRicerca, firstResult, maxResults);
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
	List<Tipifamiglieendo> tfs = tipifamiglieendoService.findTipifamigliaByDescrizione(testoDaCercare, tipoRicerca, campiRicerca, firstResult,
		maxResults);
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
    public String getEndoprocedimentoKey(Inventarioprocedimenti inventarioprocedimenti) {

	if (inventarioprocedimenti != null) {
	    return inventarioprocedimenti.getId().getIdcomune() + "|" + inventarioprocedimenti.getId().getCodice();
	}
	return null;
    }

    @Override
    public PkId getIdFromEndoprocedimentoKey(String endoprocedimentoKey) {

	if (StringUtils.isNotBlank(endoprocedimentoKey) && endoprocedimentoKey.indexOf("|") > 0) {
	    String[] val = endoprocedimentoKey.split("\\|");
	    return new PkId(val[0], Integer.valueOf(val[1]));
	}
	return null;
    }

    @Override
    public void insertEndo2(Inventarioprocedimenti entity, Integer codicealberoproc) {

	this.insert(entity);
	StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), codicealberoproc);
	Alberoproc ap = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codicealberoproc));
	if (stp2 == null) {
	    if (ap == null) {
		throw new RuntimeException("Attenzione non è stata trovata l'attività con codice " + codicealberoproc);
	    }
	    throw new RuntimeException(
		    "Attenzione non sono stati trovati i parametri regionali per l'attività [" + ap.getId() + "] " + ap.getScDescrizione());
	}
	stp2.setInventarioprocedimenti(entity);
	stpEndoTipo2Service.update(stp2);
	AlberoprocEndo ape = new AlberoprocEndo();
	ape.setAlberoproc(ap);
	ape.setInventarioprocedimento(entity);
	ape.setFlagPubblica(Boolean.TRUE);
	ape.setFlagRegionale(Boolean.TRUE);
	ape.setFlagIntervento(Boolean.TRUE);
	ape.setFlagRichiesto(Boolean.TRUE);
	ape.setAzione(null);
	AlberoprocEndoId apeid = new AlberoprocEndoId(ORMHelper.getIdcomunebase(), codicealberoproc, entity.getId().getCodice());
	ape.setId(apeid);
	alberoprocEndoService.insert(ape);
    }

    @Override
    public void insertEndo1(Inventarioprocedimenti entity, StpEndoTipo1 stpEndoTipo1) {

	this.insert(entity);
	stpEndoTipo1.setInventarioprocedimenti(entity);
	stpEndoTipo1Service.insert(stpEndoTipo1);
    }

    @Override
    public void updateEndo1(Inventarioprocedimenti entity, StpEndoTipo1 stpEndoTipo1) {

	this.update(entity);
	stpEndoTipo1Service.update(stpEndoTipo1);
    }

    @Override
    public String getChiaveEndoLocale(Inventarioprocedimenti ip) {

	return FACCTConstants.PRESENTAZIONE_DOMANDA_CODICE_ENDOLCALE_PREFIX + ip.getId().getIdcomune() + "-" + ip.getId().getCodice();
    }
}
