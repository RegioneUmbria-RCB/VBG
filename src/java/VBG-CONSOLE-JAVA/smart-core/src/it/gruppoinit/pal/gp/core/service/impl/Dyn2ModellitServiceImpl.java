package it.gruppoinit.pal.gp.core.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.FieldError;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Dyn2ModellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CampoSchedaHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SchedaHelper;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciColonnaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciRigaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciTabellaHelper;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService;
import it.gruppoinit.pal.gp.core.service.MappatureService;
import it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.helper.SchedeDinamicheTL;
import it.gruppoinit.pal.gp.core.service.regole.Dyn2RegoleHelper;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;
import it.gruppoinit.pal.gp.core.utils.Dyn2Utils;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ValoreParametroType;
import it.sigepro.init.SchedeDinamicheWebServiceLocator;
import it.sigepro.init.SchedeDinamicheWebServiceSoap;

@Service
public class Dyn2ModellitServiceImpl extends BaseServiceImpl<Dyn2Modellit, PkId> implements Dyn2ModellitService {

    private static final Logger log = LoggerFactory.getLogger(Dyn2ModellitServiceImpl.class);
    private Dyn2CampiService dyn2CampiService;
    private Dyn2ModellitDAO dyn2ModellitDAO;
    private Dyn2ModellidService dyn2ModellidService;
    //    private Istanzedyn2datiService istanzedyn2datiService;
    //    private IAttivitadyn2datiService iAttivitadyn2datiService;
    //    private IstanzeService istanzeService;
    //    private IAttivitaService iAttivitaService;
    private ModelliDinamiciFormuleService modelliDinamiciFormuleService;
    //private OggettiService oggettiservice;
    private VelocityEngine templateEngine;
    private Dyn2RegoleService dyn2RegoleService;
    private OggettiService oggettiService;

    //    private NlaHelperService nlaHelperService;
    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setDyn2ModellidService(Dyn2ModellidService dyn2ModellidService) {

	this.dyn2ModellidService = dyn2ModellidService;
    }

    @Autowired
    public void setDyn2ModellitDAO(Dyn2ModellitDAO dyn2ModellitDAO) {

	this.dyn2ModellitDAO = dyn2ModellitDAO;
    }

    //    @Autowired
    //    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {
    //
    //	this.istanzedyn2datiService = istanzedyn2datiService;
    //    }
    //
    //    @Autowired
    //    public void setiAttivitadyn2datiService(IAttivitadyn2datiService iAttivitadyn2datiService) {
    //
    //	this.iAttivitadyn2datiService = iAttivitadyn2datiService;
    //    }
    //
    //    @Autowired
    //    public void setIstanzeService(IstanzeService istanzeService) {
    //
    //	this.istanzeService = istanzeService;
    //    }
    //
    //    @Autowired
    //    public void setiAttivitaService(IAttivitaService iAttivitaService) {
    //
    //	this.iAttivitaService = iAttivitaService;
    //    }
    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setTemplateEngine(VelocityEngine engine) {

	this.templateEngine = engine;
    }

    @Autowired
    public void setDyn2RegoleService(Dyn2RegoleService dyn2RegoleService) {

	this.dyn2RegoleService = dyn2RegoleService;
    }

    //    @Autowired
    //    public void setNlaHelperService(NlaHelperService nlaHelperService) {
    //
    //	this.nlaHelperService = nlaHelperService;
    //    }
    @Override
    public void delete(Dyn2Modellit entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    dyn2ModellitDAO.delete(entity);
	}
    }

    @Autowired
    private AlberoprocDyn2modellitService alberoprocDyn2modellitService;
    @Autowired
    private MappatureService mappatureService;

    @Override
    protected void childDelete(Dyn2Modellit entity) {

	List<Dyn2Modellid> s = dyn2ModellidService.findByModelloT(entity);
	for (Dyn2Modellid dyn2Modellid : s) {
	    dyn2ModellidService.delete(dyn2Modellid);
	}
	dyn2ModellitDAO.flush();
    }

    @Autowired
    public void setModelliDinamiciFormuleService(ModelliDinamiciFormuleService modelliDinamiciFormuleService) {

	this.modelliDinamiciFormuleService = modelliDinamiciFormuleService;
    }

    @Override
    public List<Dyn2Modellit> findAll(Integer firstResult, Integer maxResult) {

	Dyn2Modellit example = new Dyn2Modellit();
	return dyn2ModellitDAO.findByDescrizioneAndSoftware(example, ORMHelper.getSoftware());
    }

    @Override
    public Dyn2Modellit findById(PkId id) {

	return dyn2ModellitDAO.findById(id);
    }

    @Override
    public void insert(Dyn2Modellit entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dyn2ModellitDAO.insert(entity);
	    if (StringUtils.isBlank(entity.getCodiceScheda())) {
		entity.setCodiceScheda(WebConstants.SCHEDA + entity.getId().getCodice());
		this.update(entity);
	    }
	}
    }

    @Override
    public void update(Dyn2Modellit entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dyn2ModellitDAO.update(entity);
	}
    }

    @Override
    protected Class<Dyn2Modellit> getEntityClass() {

	return Dyn2Modellit.class;
    }

    @Override
    public List<Dyn2Modellit> findByDescrizione(Dyn2Modellit entity, boolean isComuneBase) {

	return dyn2ModellitDAO.findByDescrizione(entity, isComuneBase);
    }

    @Override
    public List<Dyn2Modellit> findAllByDescrizione(String descrizione) {

	return dyn2ModellitDAO.findAllByDescrizione(descrizione);
    }

    @Override
    public List<Dyn2Modellit> findByDescrizioneAndSoftware(Dyn2Modellit entity, String codicesoftware) {

	return dyn2ModellitDAO.findByDescrizioneAndSoftware(entity, codicesoftware);
    }

    private void dataIntegration(Dyn2Modellit entity) {

	if (entity.getFlgReadonlyWeb() == null) {
	    entity.setFlgReadonlyWeb(false);
	}
	if (entity.getFlgStoricizza() == null) {
	    entity.setFlgStoricizza(false);
	}
	if (entity.getModellomultiplo() == null) {
	    entity.setModellomultiplo(false);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Dyn2Modellit entity) {

    }

    @SuppressWarnings("unused")
    protected ModellidinamiciHelper populateModellodinamicoHelper(String idcomune, int codiceModello) {

	Dyn2Modellit modello = this.findById(new PkId(idcomune, codiceModello));
	if (modello == null) {
	    throw new RuntimeException("Modello con codice [" + codiceModello + "] non trovato");
	}
	ModellidinamiciHelper helper = new ModellidinamiciHelper(idcomune, codiceModello, modello.getDescrizione(),
		modello.getBasecontesti().getId());
	ModellidinamiciTabellaHelper tabellaPrincipale = new ModellidinamiciTabellaHelper();
	List<Dyn2Modellid> righe = dyn2ModellidService.findRigheModello(idcomune, codiceModello);
	List<ModellidinamiciRigaHelper> righeTabella = new ArrayList<ModellidinamiciRigaHelper>();
	int codiceRiga = -1;
	Map<Integer, ModellidinamiciRigaHelper> mapRiga = new HashMap<Integer, ModellidinamiciRigaHelper>();
	Map<String, ModellidinamiciRigaHelper> mapRigheNested = new HashMap<String, ModellidinamiciRigaHelper>();
	Map<String, ModellidinamiciTabellaHelper> mapTabelleNested = new HashMap<String, ModellidinamiciTabellaHelper>();
	Map<String, ModellidinamiciColonnaHelper> mapColonneNested = new HashMap<String, ModellidinamiciColonnaHelper>();
	Map<String, ModellidinamiciColonnaHelper> mapColonne = new HashMap<String, ModellidinamiciColonnaHelper>();
	Map<String, ModellidinamiciRigaHelper> mapRigheAggiunte = new HashMap<String, ModellidinamiciRigaHelper>();
	Map<String, ModellidinamiciRigaHelper> mapRigheNestedAggiunte = new HashMap<String, ModellidinamiciRigaHelper>();
	int i = 0;
	String tableKey = "";
	int numerotabella = 1;
	if (righe.size() > 0) {
	    // ciclo le righe del modello
	    for (Dyn2Modellid d2md : righe) {
		boolean isMultiplo = d2md.getFlgMultiplo() == null ? false : d2md.getFlgMultiplo().booleanValue();
		ModellidinamiciRigaHelper rigaH = null;
		codiceRiga = d2md.getPosverticale();
		if (mapRiga.get(codiceRiga) == null) {
		    rigaH = new ModellidinamiciRigaHelper(codiceRiga);
		    mapRiga.put(codiceRiga, rigaH);
		} else {
		    rigaH = mapRiga.get(codiceRiga);
		}
		ModellidinamiciColonnaHelper colonna = null;
		if (isMultiplo) {
		    tableKey = String.valueOf(numerotabella) + "_TAB";
		    // devo gestire ricorsivamente una tabella
		    if (mapColonne.get(tableKey) != null) {
			colonna = mapColonne.get(tableKey);
		    } else {
			colonna = new ModellidinamiciColonnaHelper(d2md.getPosverticale());
		    }
		    ModellidinamiciTabellaHelper tabellaNested = null;
		    if (mapTabelleNested.get(tableKey) != null) {
			tabellaNested = mapTabelleNested.get(tableKey);
		    } else {
			tabellaNested = new ModellidinamiciTabellaHelper();
			mapTabelleNested.put(tableKey, tabellaNested);
		    }
		    ModellidinamiciRigaHelper rigaNested = null;
		    String rowKey = tableKey + "_" + codiceRiga + "_ROW";
		    if (mapRigheNested.get(rowKey) == null) {
			rigaNested = new ModellidinamiciRigaHelper(codiceRiga);
			mapRigheNested.put(rowKey, rigaNested);
		    } else {
			rigaNested = mapRigheNested.get(rowKey);
		    }
		    ModellidinamiciColonnaHelper colonnaNested = null;
		    String colKey = rowKey + "_" + d2md.getPosorizzontale() + "_COL";
		    if (mapColonneNested.get(colKey) != null) {
			colonnaNested = mapColonneNested.get(colKey);
		    } else {
			colonnaNested = new ModellidinamiciColonnaHelper(d2md.getPosorizzontale());
			mapColonneNested.put(colKey, colonnaNested);
		    }
		    popolaColonnaHelper(colonnaNested, d2md, true);
		    rigaNested.getColonne().add(colonnaNested);
		    if (mapRigheNestedAggiunte.get(String.valueOf(d2md.getPosverticale())) == null) {
			tabellaNested.getRighe().add(rigaNested);
			mapRigheNestedAggiunte.put(String.valueOf(d2md.getPosverticale()), rigaNested);
		    }
		    List<ModellidinamiciTabellaHelper> tabelle = new ArrayList<ModellidinamiciTabellaHelper>();
		    tabelle.add(tabellaNested);
		    colonna.setTabelle(tabelle);
		    if (mapColonne.get(tableKey) == null) {
			mapColonne.put(tableKey, colonna);
			rigaH.getColonne().add(colonna);
			if (mapRigheAggiunte.get(String.valueOf(d2md.getPosverticale())) == null) {
			    righeTabella.add(rigaH);
			    mapRigheAggiunte.put(String.valueOf(d2md.getPosverticale()), rigaH);
			}
		    }
		} else {
		    numerotabella += 1;
		    // riga singola		   
		    colonna = popolaColonnaHelper(null, d2md, false);
		    rigaH.getColonne().add(colonna);
		    // aggiungere una volta sola
		    if (mapRigheAggiunte.get(String.valueOf(d2md.getPosverticale())) == null) {
			righeTabella.add(rigaH);
			mapRigheAggiunte.put(String.valueOf(d2md.getPosverticale()), rigaH);
		    }
		}
		i++;
	    }
	    tabellaPrincipale.setRighe(righeTabella);
	}
	helper.setTabella(tabellaPrincipale);
	return helper;
    }

    private ModellidinamiciColonnaHelper popolaColonnaHelper(ModellidinamiciColonnaHelper col, Dyn2Modellid d2md, boolean loadRegoleDipendenti) {

	if (col == null) {
	    col = new ModellidinamiciColonnaHelper(d2md.getPosorizzontale());
	}
	if (d2md != null) {
	    col.setDyn2Modellid(d2md);
	    Dyn2Campi d2c = d2md.getDyn2Campi();
	    if (d2c != null) {
		ModellidinamiciCampoHelper campoH = new ModellidinamiciCampoHelper();
		campoH.setDyn2Campi(d2c);
		campoH.setNomeCampo(d2c.getNomecampo());
		campoH.setApplicationContext(context);
		Boolean obbligatorio = d2md.getFlgObbligatorio();
		/*
		if (obbligatorio == null) {
		    String obblString = campoH.getProprietaCampo(ProprietaCampi.Obbligatorio, "false");
		    if (StringUtils.isNotEmpty(obblString)) {
			obbligatorio = new Boolean("true".equalsIgnoreCase(obblString));
		    }
		    if (obbligatorio == null) {
			obbligatorio = d2c.getObbligatorio();
		    }
		    if (obbligatorio == null) {
			obbligatorio = Boolean.FALSE;
		    }
		}
		*/
		campoH.setObbligatorio(obbligatorio);
		Integer idModellot = d2md.getDyn2Modellit().getId().getCodice();
		campoH.setIdModello(idModellot);
		if (loadRegoleDipendenti) {
		    campoH.setRegoleDipendenti(this.dyn2RegoleService.findRegoleDipendentiDaCampoInModello(d2c.getId().getCodice(), idModellot));
		}
		col.setCampo(campoH);
	    }
	}
	return col;
    }

    /*
    private ModellidinamiciCampoHelper popolaCampoHelper(){
    
    }
    */
    private ModellidinamiciHelper populateModelForPreview(ModellidinamiciHelper helper) {

	ModellidinamiciTabellaHelper tabellaprincipale = helper.getTabella();
	List<ModellidinamiciRigaHelper> righe = tabellaprincipale.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			if (colonna.getTabelle() != null) {
			    if (colonna.getTabelle().size() > 0) {
				ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(0);
				tabellaNested = Dyn2Utils.tabellaDTO(new ModellidinamiciTabellaHelper(), tabellaNested, true);
				colonna.getTabelle().add(tabellaNested);
				colonna.getTabelle().add(Dyn2Utils.tabellaDTO(new ModellidinamiciTabellaHelper(), tabellaNested, true));
			    }
			}
		    }
		}
	    }
	}
	return helper;
    }

    /*
    @Override
    public ModellidinamiciHelper populateModellodinamicoForIstanza(int codiceModello, Integer codiceIstanza) {
    
    if (codiceIstanza == null) {
        throw new RuntimeException("Non è possibile popolare la scheda per una istanza nulla");
    }
    PkId idIstanza = new PkId(codiceIstanza);
    Istanze istanza = istanzeService.findById(idIstanza);
    if (istanza == null) {
        log.error("populateModellodinamicoForIstanza: L'istanza [{}] non è stata trovata", idIstanza);
        throw new RuntimeException("L'istanza [" + new PkId(codiceIstanza) + "] non è stata trovata");
    }
    try {
        modelliDinamiciFormuleService.updateLoadModelloIstanza(codiceIstanza, codiceModello);
    } catch (Exception e) {
        log.error("populateModellodinamicoForIstanza: updateLoadModelloIstanza: {}", e.getMessage());
    }
    ModellidinamiciHelper helper = populateModellodinamicoHelper(codiceModello);
    Dyn2RegoleHelper regoleHelper = new Dyn2RegoleHelper(dyn2ModellidService, dyn2CampiService);
    RichiestaPraticaNLAResponse nlaResp = nlaHelperService.populateRichiestaPraticaNLAResponse(istanza);
    DettaglioPraticaType stcDomain = null;
    if (null != nlaResp) {
        stcDomain = nlaResp.getDettaglioPratica().getDettaglioPratica();
    }
    regoleHelper.setStcDomain(stcDomain);
    helper.setRegoleHelper(regoleHelper);
    //PkId idModello = new PkId(codiceModello);
    // List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndModello(idIstanza, idModello);
    ModellidinamiciTabellaHelper tabellaprincipale = helper.getTabella();
    List<ModellidinamiciRigaHelper> righe = tabellaprincipale.getRighe();
    if (righe != null) {
        for (ModellidinamiciRigaHelper riga : righe) {
    	List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
    	if (colonne != null) {
    	    for (ModellidinamiciColonnaHelper colonna : colonne) {
    		if (colonna.getTabelle() != null) {
    		    if (colonna.getTabelle().size() > 0) {
    			ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(0);
    			int numTabelle = trovaNumTabelle(tabellaNested, codiceIstanza);
    			//				List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
    			//					d2c.getId().getCodice(), 0);
    			//				colonna.getTabelle().add(tabellaNested);
    			//				colonna.getTabelle().add(tabellaNested);
    			colonna.setTabelle(new ArrayList<ModellidinamiciTabellaHelper>());
    			for (int i = 0; i < numTabelle; i++) {
    			    ModellidinamiciTabellaHelper tabella = popolaTabellaIstanza(istanza, tabellaNested, i);
    			    colonna.getTabelle().add(i, tabella);
    			}
    		    }
    		}
    		if (colonna.getCampo() != null) {
    		    Dyn2Modellid d2md = colonna.getDyn2Modellid();
    		    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
    		    d2c = dyn2CampiService.findById(d2c.getId());
    		    if (d2c != null) {
    			try {
    			    modelliDinamiciFormuleService.updateLoadCampoIstanza(codiceIstanza, d2c.getId().getCodice());
    			} catch (Exception e) {
    			    log.error("populateModellodinamicoForIstanza: updateLoadCampoIstanza: {}", e.getMessage());
    			}
    			List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
    				d2c.getId().getCodice(), 0);
    			if (datis.size() > 0) {
    			    Istanzedyn2dati dato = datis.get(0);
    			    colonna.getCampo().setValore(dato.getValore());
    			    colonna.getCampo().setValoreDecodificato(dato.getValoredecodificato());
    			    colonna.getCampo().setIndice(dato.getId().getIndice());
    			    colonna.getCampo().setIndicemolteplicita(dato.getId().getIndiceMolteplicita());
    			}
    			if (datis.size() > 1) {
    			    log.error("populateModellodinamicoForIstanza: Trovati più valori per il campo [" + d2c.getId() + "]:"
    				    + d2c.getNomecampo());
    			}
    		    }
    		}
    	    }
    	}
        }
    }
    return helper;
    }
    
    private ModellidinamiciTabellaHelper popolaTabellaIstanza(Istanze istanza, ModellidinamiciTabellaHelper tabellaNested, int i) {
    
    ModellidinamiciTabellaHelper result = new ModellidinamiciTabellaHelper();
    Dyn2Utils.tabellaDTO(result, tabellaNested, false);
    for (ModellidinamiciRigaHelper rc : result.getRighe()) {
        List<ModellidinamiciColonnaHelper> colCopy = rc.getColonne();
        for (ModellidinamiciColonnaHelper colonna : colCopy) {
    	Dyn2Modellid d2md = colonna.getDyn2Modellid();
    	if (colonna.getCampo() != null) {
    	    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
    	    d2c = dyn2CampiService.findById(d2c.getId());
    	    if (d2c != null) {
    		Istanzedyn2datiId id = new Istanzedyn2datiId(istanza.getId().getCodice(), d2c.getId().getCodice(), 0, i);
    		Istanzedyn2dati datis = istanzedyn2datiService.findById(id);
    		if (datis != null) {
    		    colonna.getCampo().setValore(datis.getValore());
    		    colonna.getCampo().setValoreDecodificato(datis.getValoredecodificato());
    		    colonna.getCampo().setIndice(datis.getId().getIndice());
    		    colonna.getCampo().setIndicemolteplicita(datis.getId().getIndiceMolteplicita());
    		    if (BooleanUtils.isTrue(d2md.getFlgMultiplo())) {
    			colonna.getCampo().setIndiceBlocco(datis.getId().getIndiceMolteplicita());
    		    }
    		}
    		//			else {
    		//			    log.error("popolaTabellaIstanza: Trovati più valori per il campo [" + d2c.getId() + "]:" + d2c.getNomecampo());
    		//			    throw new RuntimeException("Trovati più valori per il campo [" + d2c.getId() + "]:" + d2c.getNomecampo());
    		//			}
    	    }
    	}
        }
    }
    return result;
    }
    
    private ModellidinamiciTabellaHelper popolaTabellaIattivita(IAttivita iAttivita, ModellidinamiciTabellaHelper tabellaNested, int i) {
    
    ModellidinamiciTabellaHelper result = new ModellidinamiciTabellaHelper();
    Dyn2Utils.tabellaDTO(result, tabellaNested, false);
    for (ModellidinamiciRigaHelper rc : result.getRighe()) {
        List<ModellidinamiciColonnaHelper> colCopy = rc.getColonne();
        for (ModellidinamiciColonnaHelper colonna : colCopy) {
    	Dyn2Modellid d2md = colonna.getDyn2Modellid();
    	if (colonna.getCampo() != null) {
    	    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
    	    d2c = dyn2CampiService.findById(d2c.getId());
    	    if (d2c != null) {
    		IAttivitadyn2datiId id = new IAttivitadyn2datiId(iAttivita.getId().getCodice(), d2c.getId().getCodice(), 0, i);
    		//Istanzedyn2datiId id = new Istanzedyn2datiId(istanza.getId().getCodice(), d2c.getId().getCodice(), 0, i);
    		//Istanzedyn2dati datis = istanzedyn2datiService.findById(id);
    		IAttivitadyn2dati datis = iAttivitadyn2datiService.findById(id);
    		if (datis != null) {
    		    colonna.getCampo().setValore(datis.getValore());
    		    colonna.getCampo().setValoreDecodificato(datis.getValoredecodificato());
    		    colonna.getCampo().setIndice(datis.getId().getIndice());
    		    colonna.getCampo().setIndicemolteplicita(datis.getId().getIndiceMolteplicita());
    		    if (BooleanUtils.isTrue(d2md.getFlgMultiplo())) {
    			colonna.getCampo().setIndiceBlocco(datis.getId().getIndiceMolteplicita());
    		    }
    		}
    		//			else {
    		//			    log.error("popolaTabellaIstanza: Trovati più valori per il campo [" + d2c.getId() + "]:" + d2c.getNomecampo());
    		//			    throw new RuntimeException("Trovati più valori per il campo [" + d2c.getId() + "]:" + d2c.getNomecampo());
    		//			}
    	    }
    	}
        }
    }
    return result;
    }
    
    private int trovaNumTabelle(ModellidinamiciTabellaHelper tabellaNested, Integer codiceIstanza) {
    
    List<ModellidinamiciRigaHelper> righe = tabellaNested.getRighe();
    int numTabelle = 0;
    if (righe != null) {
        for (ModellidinamiciRigaHelper riga : righe) {
    	List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
    	if (colonne != null) {
    	    for (ModellidinamiciColonnaHelper colonna : colonne) {
    		int numTabelleColonna = 0;
    		if (colonna.getCampo() != null) {
    		    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
    		    d2c = dyn2CampiService.findById(d2c.getId());
    		    if (d2c != null) {
    			List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
    				d2c.getId().getCodice(), 0);
    			//prendo l'indice molteplicità dell'ultima riga della scheda 0
    			//TODO gestire indice scheda
    			Istanzedyn2dati istanzedyn2dati = datis.get(datis.size() - 1);
    			numTabelleColonna = istanzedyn2dati.getId().getIndiceMolteplicita() == null ? 0 : istanzedyn2dati.getId()
    				.getIndiceMolteplicita();
    		    }
    		}
    		if (numTabelle < numTabelleColonna) {
    		    numTabelle = numTabelleColonna;
    		}
    	    }
    	}
        }
    }
    return numTabelle + 1;
    }
    
    private int trovaNumTabelleIattivita(ModellidinamiciTabellaHelper tabellaNested, Integer codiceattivita) {
    
    List<ModellidinamiciRigaHelper> righe = tabellaNested.getRighe();
    int numTabelle = 0;
    if (righe != null) {
        for (ModellidinamiciRigaHelper riga : righe) {
    	List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
    	if (colonne != null) {
    	    for (ModellidinamiciColonnaHelper colonna : colonne) {
    		int numTabelleColonna = 0;
    		if (colonna.getCampo() != null) {
    		    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
    		    d2c = dyn2CampiService.findById(d2c.getId());
    		    if (d2c != null) {
    			List<IAttivitadyn2dati> datis = iAttivitadyn2datiService.findByAttivitaAndDyn2Campi(codiceattivita, d2c.getId()
    				.getCodice(), 0);
    			//				List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
    			//					d2c.getId().getCodice(), 0);
    			for (IAttivitadyn2dati iAttivitadyn2dati : datis) {
    			    numTabelleColonna = iAttivitadyn2dati.getId().getIndiceMolteplicita() == null ? 0 : iAttivitadyn2dati.getId()
    				    .getIndiceMolteplicita();
    			    break;
    			}
    		    }
    		}
    		if (numTabelle < numTabelleColonna) {
    		    numTabelle = numTabelleColonna;
    		}
    	    }
    	}
        }
    }
    return numTabelle + 1;
    }
    */
    @Override
    public ModellidinamiciHelper populateModellodinamicoForAnagrafe(int codiceModello, Integer codiceAnagrafe) {

	// TODO Auto-generated method stub
	return null;
    }

    /*
        @Override
        public ModellidinamiciHelper populateModellodinamicoForAttivita(int codiceModello, Integer codiceAttivita) {
    
    	if (codiceAttivita == null) {
    	    throw new RuntimeException("Non è possibile popolare la scheda per una attività nulla");
    	}
    	PkId iaId = new PkId(codiceAttivita);
    	IAttivita iAttivita = iAttivitaService.findById(iaId);
    	if (iAttivita == null) {
    	    log.error("populateModellodinamicoForAttivita: L'attivita [{}] non è stata trovata", iaId);
    	    throw new RuntimeException("L'attività [" + new PkId(codiceAttivita) + "] non è stata trovata");
    	}
    	try {
    	    //DA VALUTARE
    	    // modelliDinamiciFormuleService.updateLoadModelloIstanza(codiceIstanza, codiceModello);
    	} catch (Exception e) {
    	    log.error("populateModellodinamicoForAttivita: updateLoadModelloIstanza: {}", e.getMessage());
    	}
    	Dyn2RegoleHelper regoleHelper = new Dyn2RegoleHelper(dyn2ModellidService, dyn2CampiService);
    	
    	ModellidinamiciHelper helper = populateModellodinamicoHelper(codiceModello);
    	helper.setRegoleHelper(regoleHelper);
    	//PkId idModello = new PkId(codiceModello);
    	// List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndModello(idIstanza, idModello);
    	ModellidinamiciTabellaHelper tabellaprincipale = helper.getTabella();
    	List<ModellidinamiciRigaHelper> righe = tabellaprincipale.getRighe();
    	if (righe != null) {
    	    for (ModellidinamiciRigaHelper riga : righe) {
    		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
    		if (colonne != null) {
    		    for (ModellidinamiciColonnaHelper colonna : colonne) {
    			if (colonna.getTabelle() != null) {
    			    if (colonna.getTabelle().size() > 0) {
    				ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(0);
    				//int numTabelle = trovaNumTabelle(tabellaNested, codiceIstanza);
    				int numTabelle = trovaNumTabelleIattivita(tabellaNested, codiceAttivita);
    				//				List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
    				//					d2c.getId().getCodice(), 0);
    				//				colonna.getTabelle().add(tabellaNested);
    				//				colonna.getTabelle().add(tabellaNested);
    				colonna.setTabelle(new ArrayList<ModellidinamiciTabellaHelper>());
    				for (int i = 0; i < numTabelle; i++) {
    				    // ModellidinamiciTabellaHelper tabella = popolaTabellaIstanza(istanza, tabellaNested, i);
    				    ModellidinamiciTabellaHelper tabella = popolaTabellaIattivita(iAttivita, tabellaNested, i);
    				    colonna.getTabelle().add(i, tabella);
    				}
    			    }
    			}
    			if (colonna.getCampo() != null) {
    			    Dyn2Modellid d2md = colonna.getDyn2Modellid();
    			    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
    			    d2c = dyn2CampiService.findById(d2c.getId());
    			    if (d2c != null) {
    				try {
    				    //DA VALUTARE
    				    //modelliDinamiciFormuleService.updateLoadCampoIstanza(codiceIstanza, d2c.getId().getCodice());
    				} catch (Exception e) {
    				    log.error("populateModellodinamicoForAttivita: updateLoadCampoIstanza: {}", e.getMessage());
    				}
    				List<IAttivitadyn2dati> datis = iAttivitadyn2datiService.findByAttivitaAndDyn2Campi(codiceAttivita, d2c.getId()
    					.getCodice(), 0);
    				//				List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
    				//					d2c.getId().getCodice(), 0);
    				if (datis.size() > 0) {
    				    IAttivitadyn2dati dato = datis.get(0);
    				    colonna.getCampo().setValore(dato.getValore());
    				    colonna.getCampo().setValoreDecodificato(dato.getValoredecodificato());
    				    colonna.getCampo().setIndice(dato.getId().getIndice());
    				    colonna.getCampo().setIndicemolteplicita(dato.getId().getIndiceMolteplicita());
    				}
    				if (datis.size() > 1) {
    				    log.error("populateModellodinamicoForIstanza: Trovati più valori per il campo [" + d2c.getId() + "]:"
    					    + d2c.getNomecampo());
    				}
    			    }
    			}
    		    }
    		}
    	    }
    	}
    	return helper;
        }
    */
    @Override
    public ModellidinamiciHelper populateModellodinamicoForPreview(String idcomune, int codiceModello) {

	ModellidinamiciHelper helper = populateModellodinamicoHelper(idcomune, codiceModello);
	helper.setBaseContesto("");
	Dyn2RegoleHelper regoleHelper = new Dyn2RegoleHelper(dyn2ModellidService, dyn2CampiService);
	regoleHelper.setStcDomain(new DettaglioPraticaType());
	helper.setRegoleHelper(regoleHelper);
	return populateModelForPreview(helper);
    }

    @Override
    public ModellidinamiciHelper populateModellodinamicoForStar(String idcomune, int codiceModello, DatiDomandaCart datiDomandaStar) {

	ModellidinamiciHelper helper = populateModellodinamicoHelper(idcomune, codiceModello);
	helper.setBaseContesto("");
	Dyn2RegoleHelper regoleHelper = new Dyn2RegoleHelper(dyn2ModellidService, dyn2CampiService);
	regoleHelper.setCartDomain(datiDomandaStar);
	helper.setRegoleHelper(regoleHelper);
	return helper;
    }

    @Override
    public String render(ModellidinamiciHelper helper, Boolean flagDomandaDinamica) {

	ModellidinamiciTabellaHelper tabellaPrincipale = helper.getTabella();
	boolean oldRender = false;
	String output = "";
	if (oldRender) {
	    CustomHtmlBuilder html = new CustomHtmlBuilder();
	    if (tabellaPrincipale != null) {
		gestTabella(html, tabellaPrincipale);
	    }
	    output = html.toString();
	} else {
	    Map<String, Object> context = new HashMap<String, Object>();
	    context.put("modelloHelper", helper);
	    context.put("oggettiService", this.oggettiService);
	    //context.put("StringUtils", StringUtils.class);
	    context.put("esc", new EscapeTool());
	    if (BooleanUtils.isTrue(flagDomandaDinamica)) {
		output = VelocityEngineUtils.mergeTemplateIntoString(templateEngine, "modellidinamici_v1_5/modello.vm", context);
	    } else {
		output = VelocityEngineUtils.mergeTemplateIntoString(templateEngine, "modellidinamici/modello.vm", context);
	    }
	}
	return output;
    }

    private CustomHtmlBuilder gestTestoEsteso(CustomHtmlBuilder html, String testoesteso) {

	html.span().styleClass("testoesteso").style("font-style: italic;").close();
	html.append(StringUtils.defaultIfEmpty(testoesteso, "")).spanEnd();
	return html;
    }

    private CustomHtmlBuilder gestTesto(CustomHtmlBuilder html, String testo) {

	html.div().width("100%").styleClass("titoloSezione").style("margin-top: 0px;").close();
	html.append(StringUtils.defaultIfEmpty(testo, "")).divEnd();
	return html;
    }

    private CustomHtmlBuilder gestTabella(CustomHtmlBuilder html, ModellidinamiciTabellaHelper tabella) {

	int numColonne = tabella.calcolaNumeroColonne();
	List<ModellidinamiciRigaHelper> righe = tabella.getRighe();
	if (righe.size() > 0) {
	    html.table(0).cellpadding("0").cellspacing("2").border("0").width("100%").close();
	    for (ModellidinamiciRigaHelper riga : righe) {
		html.tr(1).close();
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		int colspanLeft = numColonne;
		int colonneRiga = colonne.size();
		int colonneCampo = riga.contaColonneCampo();
		for (ModellidinamiciColonnaHelper colonna : colonne) {
		    if (colonna.getCampo() != null) {
			//set application context reference in helper for message bundles
			colonna.getCampo().setApplicationContext(context);
			Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			d2c = dyn2CampiService.findById(d2c.getId());
			//
			if (d2c != null) {
			    colspanLeft = (numColonne) - (colonneRiga + colonneCampo);
			    String etichetta = d2c.getEtichetta();
			    html.td(3).width("10%").close();
			    html.newline();
			    html.tabs(4);
			    String id = colonna.getCampo().getNameOrIdFromCampo(true);
			    if (d2c.getTipodato().equalsIgnoreCase(TipoControlloEnum.Checkbox.name())) {
				id = "TMP_" + id;
			    }
			    String obbligStr = colonna.getCampo().isObbligatorio() ? "&nbsp;*" : "&nbsp;";
			    html.span().styleClass("etichettaControllo").style("vertical-align: top;").close().label().forAttr(id).close()
				    .append(etichetta).append(obbligStr).labelEnd().spanEnd();
			    html.tdEnd();
			    html.newline();
			    html.tabs(4);
			    html.td(3).colspan("" + colspanLeft).close();
			    html.span().styleClass("controllo").close();
			    String controllo = colonna.getCampo().renderCampo();
			    html.append(controllo);
			    html.append(colonna.getCampo().renderHelp(id));
			    /*
			    html.append("<span class=\"help_image\" id=\"" + id + "_HELP\"><label>help</label></span><div id=\"" + id
			    	+ "_HELP_tooltip\" dojoType=\"dijit.Tooltip\" connectId=\"" + id
			    	+ "_HELP\" position=\"after\" style=\"display: none;\">" + d2c.getDescrizione() + "</div>");
			    */
			    html.spanEnd();
			    html.tdEnd();
			    html.newline();
			}
		    } else {
			colspanLeft = (numColonne + 1) - colonneRiga;
			html.newline();
			html.td(3).colspan("" + colspanLeft);
			html.close();
			html.newline();
			html.tabs(4);
			if (colonna.getTabelle() != null) {
			    if (colonna.getTabelle().size() > 0) {
				int i = 0;
				for (ModellidinamiciTabellaHelper tabellaHelper : colonna.getTabelle()) {
				    if (!(tabellaHelper.getRighe() == null || tabellaHelper.getRighe().size() == 0)) {
					//////
					if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
					    html.newline();
					    html.tdEnd();
					    html.newline();
					    html.trEnd(1);
					    html.newline();
					    html.tableEnd(0);
					    html.newline();
					}
					///////
					if (!BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
					    html.div().style("margin-bottom: 3px; margin-top: 3px;border: 1px solid #000000").close();
					    //il tasto elimina non compare sul primo dei blocchi multipli
					    if (i > 0) {
						html.div().styleClass("divEliminazioneBlocco")
							.style("font-weight: bold;text-align: right;width: 100%; padding-right: 2px;").close();
						html.a().href("javascript:eliminaBlocco('" + riga.getNumRiga() + "'," + i + ")").close();
						html.append(/* TODO da label*/"Elimina").aEnd().divEnd();
					    }
					}
					html = gestTabella(html, tabellaHelper);
					if (!BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
					    html.divEnd();
					} else {
					    html.br();
					}
					///					
					if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
					    html.table(0).cellpadding("0").cellspacing("2").border("0").width("100%").close();
					    html.tr(1).close();
					    html.newline();
					    html.td(3).colspan("" + colspanLeft);
					    html.close();
					    html.newline();
					    html.tabs(4);
					}
					///
					i++;
				    }
				}
				//html.div().id("functions").ul().close().li().close();
				if (!BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
				    html.div().id("functions").close().ul().close().li().close();
				    html.a().href("javascript:aggiungiBlocco('" + riga.getNumRiga() + "'," + (i++) + ")").close()
					    .append(/* TODO da label*/"aggiungi blocco").aEnd();
				    html.liEnd().ulEnd().divEnd();
				}
			    }
			} else if (colonna.getTesto() != null) {
			    html = gestTesto(html, colonna.getTesto());
			} else {
			    html = gestTestoEsteso(html, colonna.getTestoesteso());
			}
			html.newline();
			html.tdEnd();
			html.newline();
		    }
		}
		html.trEnd(1);
	    }
	}
	html.newline();
	html.tableEnd(0);
	html.newline();
	return html;
    }

    @Override
    public String renderCampo(CustomHtmlBuilder html, TipoControlloEnum tipodato, Dyn2Campi d2c, ModellidinamiciCampoHelper campo) {

	String campoHtml = campo.renderCampo();
	return campoHtml;
    }

    /*
    protected String getProprietaCampo(Set<Dyn2Campiproprieta> proprietaCampo, ProprietaCampi proprieta, String defaultValue) {
    
    String result = StringUtils.defaultIfEmpty(defaultValue, "");
    for (Dyn2Campiproprieta dyn2Campiproprieta : proprietaCampo) {
        if (dyn2Campiproprieta.getId().getProprieta().equalsIgnoreCase(proprieta.name())) {
    	result = dyn2Campiproprieta.getValore();
        }
    }
    return result;
    }
    */
    @Override
    public boolean checkCampoUsedForModello(Integer codiceModello, Integer codiceCampo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceModello, Integer.class));
	FilterRestriction existsCampoInModellid = new FilterRestriction();
	String hierarchy = "dyn2Modellids";
	FilterField<Integer> existsCampo = new FilterField<Integer>("dyn2CampiId", hierarchy, FieldOperationsEnum.EXISTS,
		new Integer[] { codiceCampo }, Dyn2Modellid.class);
	existsCampo.setExistsChildEntityId("dyn2Modellit.id");
	existsCampo.setExistsParentEntityId("id");
	existsCampoInModellid.addFilterField(existsCampo);
	ft.addRestriction(existsCampoInModellid);
	int count = dyn2ModellitDAO.countRecord(ft);
	if (count > 0) {
	    return true;
	}
	return false;
    }

    @Override
    public void aggiungiBloccoAScheda(String idcomuneModello, ModellidinamiciHelper helperScheda, Integer codiceModello, Integer numeroRiga,
	    Integer indice, Integer indiceMolteplicita) {

	ModellidinamiciTabellaHelper tabella = helperScheda.getTabella();
	List<ModellidinamiciRigaHelper> righe = tabella.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		if (riga.getNumRiga() == numeroRiga.intValue()) {
		    List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		    if (colonne != null) {
			for (ModellidinamiciColonnaHelper colonna : colonne) {
			    List<ModellidinamiciTabellaHelper> tabelle = colonna.getTabelle();
			    if (tabelle.size() > 0) {
				//ModellidinamiciTabellaHelper tabellaDaCopiare = tabelle.get(0);
				ModellidinamiciTabellaHelper tabellaDaCopiare = (ModellidinamiciTabellaHelper) tabelle.get(tabelle.size() - 1);
				if (tabellaDaCopiare.getRighe() == null || tabellaDaCopiare.getRighe().size() == 0) {
				    tabellaDaCopiare = nuovaTabellaDaModello(idcomuneModello, codiceModello, numeroRiga, indice, indiceMolteplicita);
				}
				ModellidinamiciTabellaHelper result = new ModellidinamiciTabellaHelper();
				Dyn2Utils.tabellaDTO(result, tabellaDaCopiare, true);
				tabelle.add(result);
			    }
			}
		    }
		}
	    }
	}
    }

    private ModellidinamiciTabellaHelper nuovaTabellaDaModello(String idComuneModello, Integer codiceModello, Integer numeroRiga, Integer indice,
	    Integer indiceMolteplicita) {

	List<Dyn2Modellid> righe = dyn2ModellidService.findRigheModello(idComuneModello, codiceModello);
	List<Dyn2Modellid> righeTabella = new ArrayList<Dyn2Modellid>();
	boolean inizioTabella = false;
	for (Dyn2Modellid riga : righe) {
	    if (inizioTabella) {
		if (BooleanUtils.isTrue(riga.getFlgMultiplo())) {
		    righeTabella.add(riga);
		} else {
		    break;
		}
	    }
	    if (riga.getPosverticale().equals(numeroRiga)) {
		righeTabella.add(riga);
		inizioTabella = true;
	    }
	}
	ModellidinamiciTabellaHelper tabellaHelper = new ModellidinamiciTabellaHelper();
	if (righeTabella.size() > 0) {
	    List<ModellidinamiciRigaHelper> nuoveRighe = new ArrayList<ModellidinamiciRigaHelper>();
	    Map<Integer, ModellidinamiciRigaHelper> mapRiga = new HashMap<Integer, ModellidinamiciRigaHelper>();
	    Map<String, ModellidinamiciRigaHelper> mapRigheAggiunte = new HashMap<String, ModellidinamiciRigaHelper>();
	    for (Dyn2Modellid d2d : righeTabella) {
		Integer codiceRiga = d2d.getPosverticale();
		ModellidinamiciRigaHelper rigaH = null;
		if (mapRiga.get(codiceRiga) == null) {
		    rigaH = new ModellidinamiciRigaHelper(codiceRiga);
		    mapRiga.put(codiceRiga, rigaH);
		} else {
		    rigaH = mapRiga.get(codiceRiga);
		}
		ModellidinamiciColonnaHelper colonna = new ModellidinamiciColonnaHelper(codiceRiga);
		colonna.setDyn2Modellid(d2d);
		if (d2d.getDyn2Campi() != null) {
		    ModellidinamiciCampoHelper campo = new ModellidinamiciCampoHelper();
		    campo.setApplicationContext(context);
		    campo.setDyn2Campi(d2d.getDyn2Campi());
		    Integer idDynCampo = d2d.getDyn2Campi().getId().getCodice();
		    campo.setRegoleDipendenti(this.dyn2RegoleService.findRegoleDipendentiDaCampoInModello(idDynCampo, codiceModello));
		    campo.setIdModello(codiceModello);
		    colonna.setCampo(campo);
		}
		rigaH.getColonne().add(colonna);
		// aggiungere una volta sola
		if (mapRigheAggiunte.get(String.valueOf(codiceRiga)) == null) {
		    nuoveRighe.add(rigaH);
		    mapRigheAggiunte.put(String.valueOf(codiceRiga), rigaH);
		}
	    }
	    tabellaHelper.setRighe(nuoveRighe);
	}
	return tabellaHelper;
    }

    @Override
    public void eliminaBloccoDaScheda(String idcomuneModello, ModellidinamiciHelper helperScheda, Integer numeroRiga, Integer indice,
	    Integer indiceMolteplicita) {

	ModellidinamiciTabellaHelper tabella = helperScheda.getTabella();
	List<ModellidinamiciRigaHelper> righe = tabella.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		if (riga.getNumRiga() == numeroRiga.intValue()) {
		    List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		    if (colonne != null) {
			for (ModellidinamiciColonnaHelper colonna : colonne) {
			    if (colonna.getTabelle() != null) {
				List<ModellidinamiciTabellaHelper> tabelle = colonna.getTabelle();
				//int j = 0;
				int j = indiceMolteplicita.intValue();
				//				for (j = 0; j < tabelle.size(); j++) {
				//				    if (j == indiceMolteplicita.intValue()) {
				//					break;
				//				    }
				//				}
				//				for (int a = j - 1; a < tabelle.size(); a++) {
				//				    if (!(a < 0)) {
				//					ModellidinamiciTabellaHelper tabellanest = tabelle.get(a);
				//					riduciIndiceMolteplicitaTabella(tabellanest);
				//				    }
				//				}
				if (j < tabelle.size() - 1) {
				    for (int a = j; a < tabelle.size(); a++) {
					if (a >= 0) {
					    ModellidinamiciTabellaHelper tabellanest = (ModellidinamiciTabellaHelper) tabelle.get(a);
					    riduciIndiceMolteplicitaTabella(tabellanest);
					}
				    }
				}
				tabelle.remove(j);
				if (tabelle.size() == 0) {
				    ModellidinamiciTabellaHelper tabellaVuota = new ModellidinamiciTabellaHelper();
				    tabelle.add(tabellaVuota);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    @Override
    public List<Dyn2Modellit> findByDescrizioneAndSoftwareAndContesto(String descrizione, String codicesoftware, String contesto) {

	if (StringUtils.isBlank(contesto)) {
	    throw new RuntimeException("Parametro \"contesto\" non inserito, il valore è obbligartio");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("descrizione", descrizione));
	if (StringUtils.isNotBlank(codicesoftware)) {
	    fr.addFilterField(FilterUtils.equals("codice", codicesoftware, "software", String.class));
	} else {
	    fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	}
	fr.addFilterField(FilterUtils.equals("id", contesto, "basecontesti", String.class));
	ft.addRestriction(fr);
	return dyn2ModellitDAO.findByFilterTable(ft);
    }

    private void riduciIndiceMolteplicitaTabella(ModellidinamiciTabellaHelper tabella) {

	List<ModellidinamiciRigaHelper> righe = tabella.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			if (colonna.getCampo() != null) {
			    ModellidinamiciCampoHelper campo = colonna.getCampo();
			    Integer indiceMolteplicita = campo.getIndicemolteplicita();
			    if (indiceMolteplicita != null) {
				//				int indiceM = indiceMolteplicita.intValue();
				//				indiceM -= 1;
				//				campo.setIndicemolteplicita(indiceM);
				int indiceM = indiceMolteplicita.intValue();
				if (indiceM > 0) {
				    indiceM--;
				}
				campo.setIndicemolteplicita(Integer.valueOf(indiceM));
			    } else {
				campo.setIndicemolteplicita(0);
			    }
			    campo.setIndiceBlocco(campo.getIndicemolteplicita());
			}
		    }
		}
	    }
	}
    }

    @Override
    public String validaCampo(String idcomuneModello, Integer codiceModello, Integer codiceCampo, String valore) {

	Dyn2Campi d2c = dyn2CampiService.findById(new PkId(idcomuneModello, codiceCampo));
	if (d2c == null) {
	    throw new RuntimeException("Il campo dinamico ricercato [" + codiceCampo + "] non esiste");
	}
	/*
	 * se si valida il campo senza specificare il modello di appartenenza le informazioni sull'obbligatorietà 
	 * non potranno essere recuperate da dyn2modellild ma solo da dyn2campiproprieta (alla vecchia maniera)
	 */
	if (null != codiceModello) {
	    Dyn2Modellit modello = this.findById(new PkId(idcomuneModello, codiceModello));
	    if (modello == null) {
		throw new RuntimeException("Il modello ricercato [" + codiceModello + "] non esiste");
	    }
	    // TODO controllare se il campo appartiene al modello
	}
	ModellidinamiciCampoHelper campoH = new ModellidinamiciCampoHelper();
	campoH.setApplicationContext(context);
	campoH.setDyn2Campi(d2c);
	campoH.setValore(valore);
	campoH.setIdModello(codiceModello);
	campoH.setNomeCampo(d2c.getNomecampo());
	return validaCampo(idcomuneModello, campoH, valore);
    }

    @Override
    public String validaCampo(String idcomuneModello, ModellidinamiciCampoHelper campoHelper, String valore) {

	Dyn2Campi d2c = campoHelper.getDyn2Campi();
	Boolean obbligatorio = dyn2ModellidService.findObbligatorioByModelloAndCampo(idcomuneModello, campoHelper.getIdModello(),
		d2c.getId().getCodice());
	campoHelper.setObbligatorio(obbligatorio);
	campoHelper.setValore(valore);
	String msg = "";
	List<FieldError> fieldErrors = campoHelper.validaCampo();
	if (fieldErrors.size() > 0) {
	    msg = campoHelper.renderError(fieldErrors.get(0));
	}
	return msg;
    }

    /*
    @Override
    public String[] eseguiScriptSchedeIstanza(Istanze istanza) {
    
    SchedeDinamicheWebServiceSoap port = getPortWS();
    EseguiScriptSchedaIstanzaRequest eseguiScriptSchedaIstanzaRequest = new EseguiScriptSchedaIstanzaRequest();
    eseguiScriptSchedaIstanzaRequest.setCodiceIstanza(istanza.getId().getCodice());
    eseguiScriptSchedaIstanzaRequest.setEseguiScriptCaricamento(true);
    eseguiScriptSchedaIstanzaRequest.setEseguiScriptSalvataggio(true);
    RisultatoEsecuzioneScript risultatoEsecuzioneScript = new RisultatoEsecuzioneScript();
    String token = ORMHelper.getToken();
    try {
        log.debug("Eseguo il metodo eseguiScriptSchedeIstanza con codice istanza [{}]", istanza.getId());
        risultatoEsecuzioneScript = port.eseguiScriptSchedeIstanza(token, eseguiScriptSchedaIstanzaRequest);
        log.debug("Eseguito il metodo eseguiScriptSchedeIstanza");
        // recupero gli errori che eventualmente restituisce il WS
        String[] result = risultatoEsecuzioneScript.getErroriSalvataggio();
        return result;
    } catch (RemoteException e) {
        log.error("Errore nel salvataggio degli script delle schede dinamiche: {}", e);
        throw new RuntimeException("Errore nel salvataggio degli script delle schede dinamiche: " + e.getMessage(), e);
    }
    }
    */
    /**
     * 
     * @param request
     *            Recupera la connessione al WS da invocare
     */
    private SchedeDinamicheWebServiceSoap getPortWS() {

	SchedeDinamicheWebServiceLocator locator = new SchedeDinamicheWebServiceLocator();
	String webServiceUrl = BackofficeNETConstants.getURL_WS_SCHEDE_DINAMICHE();
	SchedeDinamicheWebServiceSoap port = null;
	try {
	    port = locator.getSchedeDinamicheWebServiceSoap(new URL(webServiceUrl));
	} catch (MalformedURLException e) {
	    log.debug("Servizio non funzionante non disponibile non disponibile: " + e.getMessage());
	    throw new RuntimeException("Servizio WS temporaneamente non disponibile: " + e.getMessage());
	} catch (ServiceException e) {
	    log.debug("Servizio non funzionante non disponibile: " + e.getMessage());
	    throw new RuntimeException("Servizio WS temporaneamente non disponibile: " + e.getMessage());
	}
	return port;
    }

    @Override
    public Dyn2Modellit findByCodiceScheda(String idcomuneModello, String codiceScheda) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomuneModello, String.class));
	fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	fr.addFilterField(FilterUtils.equals("codiceScheda", codiceScheda, String.class));
	ft.addRestriction(fr);
	List<Dyn2Modellit> rs = dyn2ModellitDAO.findByFilterTable(ft);
	if (rs.size() == 1) {
	    return rs.get(0);
	}
	ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	fr.addFilterField(FilterUtils.equals("codice", WebConstants.SOFTWARE_TT, "software", String.class));
	ft.addRestriction(fr);
	rs = dyn2ModellitDAO.findByFilterTable(ft);
	if (rs.size() == 1) {
	    return rs.get(0);
	}
	return null;
    }

    @Override
    public ModellidinamiciHelper populateScheda(String idcomune, SchedaHelper schedaH) {

	ModellidinamiciHelper helper = populateModellodinamicoHelper(idcomune, Integer.valueOf(schedaH.getScheda().getCodice()).intValue());
	ModellidinamiciTabellaHelper tabellaprincipale = helper.getTabella();
	List<ModellidinamiciRigaHelper> righe = tabellaprincipale.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			if (colonna.getTabelle() != null) {
			    // caso blocchi multipli
			    if (colonna.getTabelle().size() > 0) {
				ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(0);
				int molteplicita = trovaMolteplicitaTabelleBlocchiMultipli(tabellaNested, schedaH);
				colonna.setTabelle(new ArrayList<ModellidinamiciTabellaHelper>());
				for (int i = 0; i < molteplicita; i++) {
				    ModellidinamiciTabellaHelper tabella = populateTabellaBloccoMultiplo(schedaH, tabellaNested, i, null);
				    colonna.getTabelle().add(i, tabella);
				}
			    }
			}
			if (colonna.getCampo() != null) {
			    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			    //d2c = dyn2CampiService.findById(new PkId(idcomune, d2c.getId().getCodice()));
			    if (d2c != null) {
				for (CampoSchedaHelper dato : schedaH.getCampi()) {
				    if (dato.getCampo().getCodice().equals(d2c.getId().getCodice().toString())) {
					List<ElementoValoreCampoDinamicoType> valori = dato.getCampo().getCampoDinamico().getValoreUtente()
						.getValore();
					for (int i = 0; i < valori.size(); i++) {
					    colonna.getCampo().setValore(valori.get(i).getCodice());
					    colonna.getCampo().setValoreDecodificato(valori.get(i).getDescrizione());
					    colonna.getCampo().setIndice(0);
					    colonna.getCampo().setIndicemolteplicita(i);
					}
					break;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return helper;
    }

    @Override
    public ModellidinamiciHelper populateSchedaAsTemplate(String idcomune, SchedaHelper schedaH, String codScheda) {

	ModellidinamiciHelper helper = populateModellodinamicoHelper(idcomune, Integer.valueOf(schedaH.getScheda().getCodice()).intValue());
	helper.setTemplateFor(codScheda);
	ModellidinamiciTabellaHelper tabellaprincipale = helper.getTabella();
	List<ModellidinamiciRigaHelper> righe = tabellaprincipale.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			if (colonna.getTabelle() != null) {
			    // caso blocchi multipli
			    if (colonna.getTabelle().size() > 0) {
				ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(0);
				int molteplicita = trovaMolteplicitaTabelleBlocchiMultipli(tabellaNested, schedaH);
				colonna.setTabelle(new ArrayList<ModellidinamiciTabellaHelper>());
				for (int i = 0; i < molteplicita; i++) {
				    ModellidinamiciTabellaHelper tabella = populateTabellaBloccoMultiplo(schedaH, tabellaNested, i, codScheda);
				    colonna.getTabelle().add(i, tabella);
				}
			    }
			}
			if (colonna.getCampo() != null) {
			    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			    if (StringUtils.isNotBlank(codScheda)) {
				colonna.getCampo()
					.setNomeCampo(CartModuloHelper.getIdSemanticoForTemplate(codScheda, colonna.getCampo().getNomeCampo()));
			    }
			    //d2c = dyn2CampiService.findById(new PkId(idcomune, d2c.getId().getCodice()));
			    if (d2c != null) {
				for (CampoSchedaHelper dato : schedaH.getCampi()) {
				    if (dato.getCampo().getCodice().equals(d2c.getId().getCodice().toString())) {
					List<ElementoValoreCampoDinamicoType> valori = dato.getCampo().getCampoDinamico().getValoreUtente()
						.getValore();
					for (int i = 0; i < valori.size(); i++) {
					    colonna.getCampo().setValore(valori.get(i).getCodice());
					    colonna.getCampo().setValoreDecodificato(valori.get(i).getDescrizione());
					    colonna.getCampo().setIndice(0);
					    colonna.getCampo().setIndicemolteplicita(i);
					}
					break;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return helper;
    }

    /**
     * metodo per conteggio del numero di blocchi multipli
     * 
     * @param tabellaNested
     * @param schedaH
     * @return
     */
    private int trovaMolteplicitaTabelleBlocchiMultipli(ModellidinamiciTabellaHelper tabellaNested, SchedaHelper schedaH) {

	List<ModellidinamiciRigaHelper> righe = tabellaNested.getRighe();
	int numTabelle = 0;
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			int numTabelleColonna = 0;
			if (colonna.getCampo() != null) {
			    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			    d2c = dyn2CampiService.findById(d2c.getId());
			    if (d2c != null) {
				for (CampoSchedaHelper campo : schedaH.getCampi()) {
				    if (campo.getCampo().getCodice().equals(d2c.getId().getCodice().toString())) {
					numTabelleColonna = campo.getCampo().getCampoDinamico().getValoreUtente().getValore().size();
					break;
				    }
				}
			    }
			}
			if (numTabelle < numTabelleColonna) {
			    numTabelle = numTabelleColonna;
			}
		    }
		}
	    }
	}
	return numTabelle == 0 ? numTabelle + 1 : numTabelle;
    }

    /**
     * metodo per il popolamento di un blocco multiplo
     * 
     * @param schedaH
     * @param tabellaNested
     * @param i
     * @return
     */
    private ModellidinamiciTabellaHelper populateTabellaBloccoMultiplo(SchedaHelper schedaH, ModellidinamiciTabellaHelper tabellaNested, int i,
	    String codiceScheda) {

	ModellidinamiciTabellaHelper result = new ModellidinamiciTabellaHelper();
	Dyn2Utils.tabellaDTO(result, tabellaNested, false);
	for (ModellidinamiciRigaHelper rc : result.getRighe()) {
	    List<ModellidinamiciColonnaHelper> colCopy = rc.getColonne();
	    for (ModellidinamiciColonnaHelper colonna : colCopy) {
		if (colonna.getCampo() != null) {
		    //Dyn2Modellid d2md = colonna.getDyn2Modellid();
		    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
		    if (StringUtils.isNotBlank(codiceScheda)) {
			colonna.getCampo().setNomeCampo(CartModuloHelper.getIdSemanticoForTemplate(codiceScheda, colonna.getCampo().getNomeCampo()));
		    }
		    //d2c = dyn2CampiService.findById(d2c.getId());
		    if (d2c != null) {
			for (int j = 0; j < schedaH.getCampi().size(); j++) {
			    if (schedaH.getCampi().get(j).getCampo().getCodice().equals(d2c.getId().getCodice().toString())) {
				ValoreCampoDinamicoType valoreUtente = schedaH.getCampi().get(j).getCampo().getCampoDinamico().getValoreUtente();
				ValoreParametroType val = new ValoreParametroType();
				if (valoreUtente.getValore().size() > i) {
				    val = valoreUtente.getValore().get(i);
				}
				colonna.getCampo().setValore(val.getCodice());
				colonna.getCampo().setValoreDecodificato(val.getDescrizione());
				colonna.getCampo().setIndice(0);
				colonna.getCampo().setIndicemolteplicita(i);
				colonna.getCampo().setIndiceBlocco(i);
			    }
			}
		    }
		}
	    }
	}
	return result;
    }

    @Override
    public Dyn2Modellit copiaModello(Dyn2Modellit copiaDa) {

	Dyn2Modellit retMod = null;
	if (copiaDa != null) {
	    retMod = new Dyn2Modellit();
	    retMod.setCodiceScheda(copiaDa.getCodiceScheda() + " COPIA");
	    retMod.setDescrizione(retMod.getCodiceScheda());
	    retMod.setFlgReadonlyWeb(BooleanUtils.isTrue(copiaDa.getFlgReadonlyWeb()));
	    retMod.setBasecontesti(copiaDa.getBasecontesti());
	    Software sw = new Software();
	    sw.setCodice(ORMHelper.getSoftware());
	    retMod.setSoftware(sw);
	    this.insert(retMod);
	    Set<Dyn2Modellid> mds = copiaDa.getDyn2Modellids();
	    Iterator<Dyn2Modellid> it = mds.iterator();
	    int i = 0;
	    for (; it.hasNext(); i++) {
		Dyn2Modellid md = it.next();
		Dyn2Modellid mdCopy = new Dyn2Modellid();
		if (md.getDyn2Campi() != null) {
		    mdCopy.setDyn2Campi(md.getDyn2Campi());
		    mdCopy.setTipocampoTransient(WebConstants.CAMPO_DINAMICO);
		} else if (md.getDyn2Modellidtesti() != null) {
		    Dyn2Modellidtesti txt = new Dyn2Modellidtesti();
		    txt.setDyn2Basetipitesto(md.getDyn2Modellidtesti().getDyn2Basetipitesto());
		    txt.setTesto(md.getDyn2Modellidtesti().getTesto());
		    mdCopy.setDyn2Modellidtesti(txt);
		    mdCopy.setTipocampoTransient(WebConstants.CAMPO_TESTO);
		}
		mdCopy.setDyn2Modellidtesti(md.getDyn2Modellidtesti());
		mdCopy.setDyn2Modellit(retMod);
		mdCopy.setDyn2RegoleAttivo(md.getDyn2RegoleAttivo());
		mdCopy.setFlgMultiplo(md.getFlgMultiplo());
		mdCopy.setFlgObbligatorio(md.getFlgObbligatorio());
		mdCopy.setPosorizzontale(md.getPosorizzontale());
		mdCopy.setPosverticale(md.getPosverticale());
		this.dyn2ModellidService.insert(mdCopy);
	    }
	    //rileggo dal DB i dati del modello appena inserito
	    retMod = this.findById(new PkId(retMod.getId().getCodice()));
	    if (log.isDebugEnabled()) {
		log.debug("copiaModello - creata copia del modello {} assegnata all'id {}, {} campi del modello sono stati clonati",
			new Object[] { copiaDa.getCodiceScheda(), retMod.getId().getCodice(), new Integer(i) });
	    }
	}
	return retMod;
    }

    @Override
    public void riordinaRigheModello(String idcomuneModello, Integer codiceModello) {

	int step = 10;
	PkId pk = new PkId(idcomuneModello, codiceModello);
	Dyn2Modellit modT = this.findById(pk);
	if (modT != null) {
	    List<Dyn2Modellid> modDs = this.dyn2ModellidService.findByModelloT(modT);
	    int rowCount = 0;
	    int prevRow = -1;
	    for (Dyn2Modellid modD : modDs) {
		Integer row = modD.getPosverticale();
		if (row == null || row.intValue() != prevRow) {
		    rowCount++;
		}
		prevRow = row.intValue();
		modD.setPosverticale(rowCount * step);
		this.dyn2ModellidService.update(modD);
	    }
	} else {
	    log.error("riordinaRigheModello - impossibile individuare il modello avente PK: {}", new Object[] { pk });
	}
    }
}
