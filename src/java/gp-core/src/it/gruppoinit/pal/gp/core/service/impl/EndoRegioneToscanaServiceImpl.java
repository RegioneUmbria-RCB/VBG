package it.gruppoinit.pal.gp.core.service.impl;

import it.eng.suap.xengine.model.service.xcommon.AzioneType;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.cart.AlberoEndo;
import it.gruppoinit.pal.gp.core.domain.cart.ElenchiEndoFACCT;
import it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ModalitaAperturaEndoTipo2;
import it.gruppoinit.sigepro.cart.service.utils.XmlUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndoRegioneToscanaServiceImpl implements EndoRegioneToscanaService {

    private static final Logger log = LoggerFactory.getLogger(EndoRegioneToscanaServiceImpl.class);
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AlberoprocArendoService alberoprocArendoService;
    @Autowired
    private StpEndoTipo2Service stpEndoService;
    @Autowired
    private InventarioprocedimentiService inventarioProcService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private Inventarioprocdyn2modellitService inventarioprocdyn2modellitService;

    public AlberoprocEndoService getAlberoprocEndoService() {

	return alberoprocEndoService;
    }

    public void setAlberoprocEndoService(AlberoprocEndoService alberoProcEndoService) {

	this.alberoprocEndoService = alberoProcEndoService;
    }

    /**
     * @return the stpEndoService
     */
    public StpEndoTipo2Service getStpEndoService() {

	return stpEndoService;
    }

    /**
     * @param stpEndoService
     *            the stpEndoService to set
     */
    public void setStpEndoService(StpEndoTipo2Service stpEndoService) {

	this.stpEndoService = stpEndoService;
    }

    /**
     * @return the inventarioProcService
     */
    public InventarioprocedimentiService getInventarioProcService() {

	return inventarioProcService;
    }

    /**
     * @param inventarioProcService
     *            the inventarioProcService to set
     */
    public void setInventarioProcService(InventarioprocedimentiService inventarioProcService) {

	this.inventarioProcService = inventarioProcService;
    }

    @Override
    public List<EndoFACCT> findElencoEndoCARTPerAttivita(Integer idAlberoProc) {

	List<EndoFACCT> endosBDR = new ArrayList<EndoFACCT>();
	// §§§BEGIN§§§
	Properties p = loadFromFile("deploy.properties");
	Set<String> elencoEndoAttivabili = new HashSet<String>();
	if (p != null) {
	    String codiciEndoAttivabili = p.getProperty("cart.codici_endo_regionali.attivabili");
	    elencoEndoAttivabili = stringToSet(codiciEndoAttivabili, ",");
	}
	List<AlberoprocEndo> endos = alberoprocEndoService.findAllByAlberoproc(idAlberoProc, true);
	for (Iterator<AlberoprocEndo> iterator = endos.iterator(); iterator.hasNext();) {
	    AlberoprocEndo alberoprocEndo = (AlberoprocEndo) iterator.next();
	    Inventarioprocedimenti invProc = alberoprocEndo.getInventarioprocedimento();
	    if (invProc == null) {
		log.warn(
			"findElencoEndoPerAttivita() - l'endoprocedimento avente ID = {} non dispone di informazioni sul nome del procedimento: sarà scartato.",
			alberoprocEndo.getId().getFkscid());
		continue;
	    }
	    StpEndoTipo1 endoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(invProc);
	    boolean insertEndo = false;
	    if (endoTipo1 != null) {
		if (elencoEndoAttivabili.size() > 0) {
		    if (elencoEndoAttivabili.contains(endoTipo1.getCodiceEndoRegionale())) {
			insertEndo = true;
		    }
		} else {
		    insertEndo = true;
		}
		if (insertEndo) {
		    EndoFACCT endoBdr = new EndoFACCT();
		    endoBdr.setDescrizione(invProc.getProcedimento());
		    endoBdr.setCodiceInventario(invProc.getId().getCodice());
		    endoBdr.setObbligatorio(alberoprocEndo.getFlagRichiesto());
		    endoBdr.setCodiceBDR(invProc.getCodiceancitel());
		    endosBDR.add(endoBdr);
		}
	    }
	}
	// §§§END§§§
	return endosBDR;
    }

    @Override
    public ElenchiEndoFACCT getElenchiEndoPerAttivita(Integer idAlberoProc, boolean flagLavoriSuFabbricati, AzioneType tipoAzione) {

	ElenchiEndoFACCT retVal = new ElenchiEndoFACCT();
	AlberoEndo endoCART = new AlberoEndo();
	AlberoEndo endoNecessari = new AlberoEndo();
	AlberoEndo endoRicorrenti = new AlberoEndo();
	AlberoEndo altriEndo = new AlberoEndo();
	Set<Integer> loadedCodes = new HashSet<Integer>();
	Properties p = loadFromFile("deploy.properties");
	Set<String> elencoEndoAttivabili = new HashSet<String>();
	if (p != null) {
	    String codiciEndoAttivabili = p.getProperty("cart.codici_endo_regionali.attivabili");
	    elencoEndoAttivabili = stringToSet(codiciEndoAttivabili, ",");
	}
	Alberoproc aProc = new Alberoproc();
	aProc.setId(new PkId(idAlberoProc));
	AlberoprocHelper aProcHelper = alberoprocService.findAlberoprocHelper(aProc);
	Set<AlberoprocEndo> aProcEndos = aProcHelper.getAlberoprocEndos();
	for (Iterator<AlberoprocEndo> endoIter = aProcEndos.iterator(); endoIter.hasNext();) {
	    AlberoprocEndo alberoprocEndo = (AlberoprocEndo) endoIter.next();
	    if (Boolean.FALSE.equals(alberoprocEndo.getFlagPubblica())) {
		// endoprocedimento non pubblicato non viene proposto
		// nell'interfaccia del frontoffice
		continue;
	    }
	    Inventarioprocedimenti invProc = alberoprocEndo.getInventarioprocedimento();
	    if (invProc == null) {
		log.warn(
			"findElencoEndoPerAttivita() - l'endoprocedimento avente ID = {} non dispone di informazioni sul nome del procedimento: sarà scartato.",
			alberoprocEndo.getId().getFkscid());
		continue;
	    }
	    Tipiendo categoriaEndo = invProc.getTipoendo();
	    String categoria = "Nessuna categoria";
	    Tipifamiglieendo famigliaEndo = null;
	    String famiglia = "Nessuna famiglia";
	    if (categoriaEndo != null) {
		categoria = categoriaEndo.getTipo();
		famigliaEndo = categoriaEndo.getTipifamiglieendo();
		if (famigliaEndo != null) {
		    famiglia = famigliaEndo.getTipo();
		}
	    }
	    StpEndoTipo1 endoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(invProc);
	    boolean insertEndo = false;
	    EndoFACCT endo = new EndoFACCT();
	    endo.setDescrizione(invProc.getProcedimento());
	    endo.setCodiceInventario(invProc.getId().getCodice());
	    loadedCodes.add(invProc.getId().getCodice());
	    endo.setObbligatorio(alberoprocEndo.getFlagRichiesto());
	    if (endoTipo1 != null) {
		if (elencoEndoAttivabili.size() > 0) {
		    if (elencoEndoAttivabili.contains(endoTipo1.getCodiceEndoRegionale())) {
			insertEndo = true;
		    }
		} else {
		    insertEndo = true;
		}
	    }
	    /*
	     * TODO per adesso le domande STD_2 che prevedono modulistica
	     * specifica per gli endo esistono solo per azioni di tipo 'Avvio'
	     * Se la regione Toscana dovesse implementare modulistiche di tipo
	     * STD_2 per azioni diverse da 'Avvio' la logica dell'app dovrà
	     * essere rivista in più punti perchè noi diamo per scontato che se
	     * non è 'Avvio' allora non è STD_2.
	     */
	    // if (insertEndo && !flagLavoriSuFabbricati &&
	    // tipoAzione.equals(AzioneType.AVVIO)) {
	    /*
	     * Lion 04/03/2015 modificata logica per il caricamento dell'elenco
	     * degli endoprocedimenti CART: Si vuole far caricare le
	     * modulistiche specifiche per gli endo CART anche per domande non
	     * STD_2 perciò l'elenco degli endo cart potrà essere presente anche
	     * per domande STD_0 se per l'intervento selezionato esistono endo
	     * CART (che hanno StpEndoTipo1 valorizzato)
	     */
	    if (insertEndo) {
		endo.setCodiceBDR(endoTipo1.getCodiceEndoRegionale());
		endo.setEndoCART(true);
		endoCART.addEndo(endo, categoria, famiglia);
	    } else {
		// endo.setCodiceBDR(invProc.getId().getCodice().toString());
		if (Boolean.TRUE.equals(alberoprocEndo.getFlagPrincipale()) || Boolean.TRUE.equals(alberoprocEndo.getFlagRichiesto())) {
		    endoNecessari.addEndo(endo, categoria, famiglia);
		} else {
		    endoRicorrenti.addEndo(endo, categoria, famiglia);
		}
	    }
	}
	/*
	 * Lion 01/04/2015 aggiunto il caricamento di un nuovo elenco di endo
	 * chiamato 'Altri endo'. Gli endo sono caricati in base alle famiglie e
	 * alle categorie configurate in alberoproc_arendo
	 */
	List<AlberoprocArendo> mearendo = alberoprocArendoService.findByAlberoProc(idAlberoProc);
	for (AlberoprocArendo arendo : mearendo) {
	    List<Inventarioprocedimenti> procs = inventarioProcService.findByAlberoprocArendo(arendo);
	    for (Inventarioprocedimenti proc : procs) {
		String categoria = "Nessuna categoria";
		String famiglia = "Nessuna famiglia";
		Tipiendo tipoEndo = proc.getTipoendo();
		if (tipoEndo != null && tipoEndo.getId().getCodice() != null) {
		    categoria = tipoEndo.getTipo();
		    famiglia = tipoEndo.getTipifamiglieendo().getTipo();
		}
		EndoFACCT endo = new EndoFACCT();
		endo.setDescrizione(proc.getProcedimento());
		endo.setCodiceInventario(proc.getId().getCodice());
		// non esite un record di alberoproc_endo per gli endo derivati
		// da alberoproc_arendo => non sappiamo se è obbligatorio
		// endo.setObbligatorio(alberoprocEndo.getFlagRichiesto());
		boolean added = loadedCodes.add(proc.getId().getCodice());
		if (added) {
		    altriEndo.addEndo(endo, categoria, famiglia);
		}
	    }
	}
	retVal.setEndoCart(endoCART);
	retVal.setEndoNecessari(endoNecessari);
	retVal.setEndoRicorrenti(endoRicorrenti);
	retVal.setAltriEndo(altriEndo);
	return retVal;
    }

    @Override
    public List<EndoFACCT> getEndoLocaliSelezionati(Integer idAlberoProc, List<String> codiciEndoSelez, String searchCodiceEndo) {

	List<EndoFACCT> retEndos = new ArrayList<EndoFACCT>();
	Alberoproc aProc = new Alberoproc();
	aProc.setId(new PkId(idAlberoProc));
	AlberoprocHelper aProcHelper = alberoprocService.findAlberoprocHelper(aProc);
	Set<AlberoprocEndo> aProcEndos = aProcHelper.getAlberoprocEndos();
	for (Iterator<AlberoprocEndo> endoIter = aProcEndos.iterator(); endoIter.hasNext();) {
	    AlberoprocEndo alberoprocEndo = (AlberoprocEndo) endoIter.next();
	    Inventarioprocedimenti invProc = alberoprocEndo.getInventarioprocedimento();
	    if (invProc == null) {
		log.warn(
			"findElencoEndoPerAttivita() - l'endoprocedimento avente ID = {} non dispone di informazioni sul nome del procedimento: sarà scartato.",
			alberoprocEndo.getId().getFkscid());
		continue;
	    }
	    if (codiciEndoSelez.contains(invProc.getId().getCodice().toString())) {
		if (StringUtils.isNotBlank(searchCodiceEndo)) {
		    StringBuilder codEndoSb = new StringBuilder("E[").append(invProc.getId().getCodice()).append("]");
		    if (!codEndoSb.toString().contains(searchCodiceEndo)) {
			continue;
		    }
		}
		EndoFACCT endo = new EndoFACCT();
		endo.setDescrizione(invProc.getProcedimento());
		endo.setCodiceInventario(invProc.getId().getCodice());
		endo.setObbligatorio(alberoprocEndo.getFlagRichiesto());
		retEndos.add(endo);
	    }
	}
	Collections.sort(retEndos);
	return retEndos;
    }

    @Override
    public List<Allegati> getAllegatiEndoAttivi(List<String> codiciEndoAttivi, String idProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle) {

	List<Allegati> results = new ArrayList<Allegati>();
	if (searchAllegatoStyle == null) {
	    searchAllegatoStyle = FieldOperationsEnum.CONTAINS;
	}
	if (codiciEndoAttivi != null) {
	    for (String endo : codiciEndoAttivi) {
		boolean endoMatches = true;
		if (StringUtils.isNotEmpty(idProc)) {
		    if (searchAllegatoStyle.equals(FieldOperationsEnum.EQIGNORECASE)) {
			endoMatches = endo.equalsIgnoreCase(idProc);
		    } else if (searchAllegatoStyle.equals(FieldOperationsEnum.CONTAINS)) {
			endoMatches = endo.contains(idProc);
		    } else if (searchAllegatoStyle.equals(FieldOperationsEnum.STARTSWITH)) {
			endoMatches = endo.startsWith(idProc);
		    } else if (searchAllegatoStyle.equals(FieldOperationsEnum.ENDSWITH)) {
			endoMatches = endo.endsWith(idProc);
		    } else {
			endoMatches = endo.equals(idProc);
		    }
		}
		if (!endoMatches) {
		    continue;
		}
		FilterTable filters = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction qFilter = new FilterRestriction();
		if (StringUtils.isNotEmpty(descAllegato)) {
		    FilterField<String> ffAllegato = new FilterField<String>("allegato", searchAllegatoStyle, new String[] { descAllegato },
			    Allegati.class);
		    qFilter.addFilterField(ffAllegato);
		}
		Inventarioprocedimenti invProc = null;
		if (NumberUtils.isNumber(endo)) {
		    invProc = new Inventarioprocedimenti();
		    invProc.setId(new PkId(new Integer(endo)));
		} else {
		    StpEndoTipo1 stp1s = stpEndoTipo1Service.findByCodiceEndoRegionale(endo);
		    if (stp1s != null) {
			invProc = stp1s.getInventarioprocedimenti();
		    }
		}
		if (invProc != null) {
		    FilterField<Inventarioprocedimenti> ffProc = new FilterField<Inventarioprocedimenti>("inventarioprocedimento",
			    new Inventarioprocedimenti[] { invProc }, Allegati.class);
		    qFilter.addFilterField(ffProc);
		}
		filters.addRestriction(qFilter);
		List<Allegati> allegatiEndo = allegatiService.findByFilterTable(filters);
		results.addAll(allegatiEndo);
	    }
	}
	return results;
    }

    @Override
    public List<AlberoprocDocumenti> getDocumentiEreditatiEndoAttivi(Integer idAlberoProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle) {

	List<AlberoprocDocumenti> retDocs = new ArrayList<AlberoprocDocumenti>();
	if (searchAllegatoStyle == null) {
	    searchAllegatoStyle = FieldOperationsEnum.CONTAINS;
	}
	if (idAlberoProc != null) {
	    Alberoproc nodo = new Alberoproc();
	    nodo.setId(new PkId(idAlberoProc));
	    AlberoprocHelper nodoHelper = alberoprocService.findAlberoprocHelper(nodo);
	    if (nodoHelper != null) {
		Set<AlberoprocDocumenti> docs = nodoHelper.getAlberoprocDocumentis();
		Iterator<AlberoprocDocumenti> docsIter = docs.iterator();
		AlberoprocDocumenti doc = null;
		while (docsIter.hasNext()) {
		    doc = docsIter.next();
		    if (doc.getPubblica() > 2 || doc.getPubblica() < 1) {
			continue;
		    }
		    boolean matches = true;
		    if (StringUtils.isNotEmpty(descAllegato)) {
			switch (searchAllegatoStyle) {
			case EQIGNORECASE:
			    matches = doc.getDescrizione().equalsIgnoreCase(descAllegato);
			    break;
			case CONTAINS:
			    matches = doc.getDescrizione().contains(descAllegato);
			    break;
			case STARTSWITH:
			    matches = doc.getDescrizione().startsWith(descAllegato);
			    break;
			case ENDSWITH:
			    matches = doc.getDescrizione().endsWith(descAllegato);
			    break;
			default:
			    matches = doc.getDescrizione().equals(descAllegato);
			    break;
			}
		    }
		    if (matches) {
			retDocs.add(doc);
		    }
		}
	    }
	}
	return retDocs;
    }

    @Override
    public List<Inventarioprocdyn2modellit> getSchedeDinamicheEndoAttivi(List<String> codiciEndoAttivi, String idProc, String descDynModello,
	    FieldOperationsEnum searchAllegatoStyle) {

	List<Inventarioprocdyn2modellit> results = new ArrayList<Inventarioprocdyn2modellit>();
	if (searchAllegatoStyle == null) {
	    searchAllegatoStyle = FieldOperationsEnum.CONTAINS;
	}
	if (codiciEndoAttivi != null) {
	    for (String endo : codiciEndoAttivi) {
		boolean endoMatches = true;
		if (StringUtils.isNotEmpty(idProc)) {
		    if (searchAllegatoStyle.equals(FieldOperationsEnum.EQIGNORECASE)) {
			endoMatches = endo.equalsIgnoreCase(idProc);
		    } else if (searchAllegatoStyle.equals(FieldOperationsEnum.CONTAINS)) {
			endoMatches = endo.contains(idProc);
		    } else if (searchAllegatoStyle.equals(FieldOperationsEnum.STARTSWITH)) {
			endoMatches = endo.startsWith(idProc);
		    } else if (searchAllegatoStyle.equals(FieldOperationsEnum.ENDSWITH)) {
			endoMatches = endo.endsWith(idProc);
		    } else {
			endoMatches = endo.equals(idProc);
		    }
		}
		if (!endoMatches) {
		    continue;
		}
		FilterTable filters = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction qFilter = new FilterRestriction();
		if (StringUtils.isNotEmpty(descDynModello)) {
		    FilterField<String> ffModello = new FilterField<String>("descrizione", "dyn2Modellit", searchAllegatoStyle,
			    new String[] { descDynModello }, Inventarioprocdyn2modellit.class);
		    qFilter.addFilterField(ffModello);
		}
		Inventarioprocedimenti invProc = null;
		if (NumberUtils.isNumber(endo)) {
		    invProc = new Inventarioprocedimenti();
		    invProc.setId(new PkId(new Integer(endo)));
		} else {
		    StpEndoTipo1 stp1s = stpEndoTipo1Service.findByCodiceEndoRegionale(endo);
		    if (stp1s != null) {
			invProc = stp1s.getInventarioprocedimenti();
		    }
		}
		if (invProc != null) {
		    FilterField<Inventarioprocedimenti> ffProc = new FilterField<Inventarioprocedimenti>("inventarioprocedimenti",
			    new Inventarioprocedimenti[] { invProc }, Inventarioprocdyn2modellit.class);
		    qFilter.addFilterField(ffProc);
		}
		filters.addRestriction(qFilter);
		List<Inventarioprocdyn2modellit> procResults = this.inventarioprocdyn2modellitService.findByFilterTable(filters, null, null);
		results.addAll(procResults);
	    }
	}
	return results;
    }

    @Override
    // TODO implementare l'interrogazione del codice regionale dell'attività
    // utilizzando un WS esposto da (BO oppure FO?)
    public StpEndoTipo2 getDatiAttivitaBdr(Integer idAttivita) {

	// §§§BEGIN§§§
	StpEndoTipo2 stpEndo = stpEndoService.findbyAlberoproc(idAttivita);
	return stpEndo;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public AzioneType checkAzione(Integer idAttivita) {

	AzioneType result = AzioneType.AVVIO;
	PkId id = new PkId(idAttivita);
	Alberoproc alberoproc = alberoprocService.findById(id);
	if (alberoproc == null) {
	    throw new RuntimeException("Non è stata trovata nessuna voce dell'albero corrispondente al codice: " + id);
	}
	StpEndoTipo2 stpEndoTipo2 = stpEndoService.findbyAlberoproc(idAttivita);
	if (stpEndoTipo2 == null) {
	    // se non esiste stpEndoTipo2 per la voce dell'albero allora azione
	    // avviost0
	    return AzioneType.AVVIO_ST_0;
	}
	if (result.equals(AzioneType.AVVIO)) {
	    if (stpEndoTipo2.getStpTipologieEndo2() != null) {
		// Se esiste codicetipologiaendo allora azione è
		// STP_TIPOLOGIE_ENDO2.DESCRIZIONE
		try {
		    result = AzioneType.fromValue(stpEndoTipo2.getStpTipologieEndo2().getDescrizione());
		} catch (IllegalArgumentException e) {
		    log.error("checkAzione - azione non mappata in AzioneType: " + stpEndoTipo2.getStpTipologieEndo2().getDescrizione()
			    + ". Sarà gestita come AVVIO_ST_0");
		    result = AzioneType.AVVIO_ST_0;
		}
	    } else {
		// ALTRIMENTI E' avvio_st_0
		result = AzioneType.AVVIO_ST_0;
	    }
	}
	if (result.equals(AzioneType.AVVIO)) {
	    result = AzioneType.AVVIO_ST_0;
	    // se non è stata definita la sceda di spiegazione e non è una scia
	    // allora è AVVIO_ST_0
	    // Si capisce dalla scheda di spiegazione
	    // ParteRegionaleSchedaEndoTipo2#getModalitaAperturaStandard()
	    // ModalitaAperturaEndoTipo2.SEGNALAZIONE_CERTIFICATA_INIZIO_ATTIVITÀ;
	    if (stpEndoTipo2.getOggetti() != null) {
		// è stata definita la scheda di spiegazione
		Oggetti schedaSpiegazione = oggettiService.findById(new PkId(stpEndoTipo2.getOggetti().getId().getCodice()));
		String xmlString = "";
		try {
		    xmlString = new String(schedaSpiegazione.getOggetto(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
		    log.error("ERRORE(UnsupportedEncoding) schedaSpiegazioneEndo2 in StpController: " + e1.getMessage());
		}
		if (StringUtils.isNotBlank(xmlString)) {
		    // la scheda di spiegazione non è vuota
		    try {
			InvioSchedaEndoTipo2 isc2 = (InvioSchedaEndoTipo2) XmlUtils.unMarshallString(xmlString, InvioSchedaEndoTipo2.class);
			if (isc2 != null) {
			    if (isc2.getParteRegionaleSchedaEndoTipo2() != null) {
				if (isc2.getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard() != null) {
				    if (isc2.getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard()
					    .equals(ModalitaAperturaEndoTipo2.SEGNALAZIONE_CERTIFICATA_INIZIO_ATTIVITÀ)) {
					// solo in caso di SCIA è avvio
					// altrimenti AVVIO_ST_0
					result = AzioneType.AVVIO;
				    }
				}
			    }
			}
		    } catch (Exception e) {
			// non fare niente
		    }
		}
	    }
	}
	return result;
    }

    private Set<String> stringToSet(String codiciEndoAttivabili, String separatorChar) {

	if (StringUtils.isNotBlank(codiciEndoAttivabili)) {
	    String[] codici = codiciEndoAttivabili.split(separatorChar);
	    Set<String> result = new HashSet<String>();
	    for (String codice : codici) {
		if (StringUtils.isNotBlank(codice)) {
		    result.add(codice.trim());
		}
	    }
	    return result;
	} else {
	    return new HashSet<String>();
	}
    }

    private Properties loadFromFile(String filename) {

	Properties result = new Properties();
	try {
	    result = Utilities.loadPropertiesFromClasspath(filename);
	} catch (IOException e) {
	    // log.error("Errore durante il caricamento del file {}: {}",
	    // filename, e.getMessage());
	    throw new RuntimeException("Errore durante il caricamento della configurazione del database: " + e);
	}
	return result;
    }
}
