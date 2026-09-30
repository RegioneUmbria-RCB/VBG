package it.gruppoinit.pal.gp.core.service.impl;

import it.eng.suap.xengine.model.service.xcommon.AzioneType;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.autocompiler.utils.DomainObjectsUtils;
import it.gruppoinit.pal.gp.core.domain.autocompiler.utils.ModuloEndoprocedimentoWrapper;
import it.gruppoinit.pal.gp.core.domain.cart.AlberoEndo;
import it.gruppoinit.pal.gp.core.domain.cart.ElenchiEndoFACCT;
import it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoLocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private AlberoprocEndoLocService alberoprocEndoLocService;
    @Autowired
    private InventarioprocEndoService inventarioprocEndoService;

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
    public List<EndoFACCT> findElencoEndoCARTPerAttivita(String idcomune, Integer idAlberoProc) {

	List<EndoFACCT> endosBDR = new ArrayList<EndoFACCT>();
	// §§§BEGIN§§§
	Properties p = loadFromFile("deploy.properties");
	Set<String> elencoEndoAttivabili = new HashSet<String>();
	if (p != null) {
	    String codiciEndoAttivabili = p.getProperty("cart.codici_endo_regionali.attivabili");
	    elencoEndoAttivabili = stringToSet(codiciEndoAttivabili, ",");
	}
	List<AlberoprocEndo> endos = alberoprocEndoService.findAllByAlberoproc(idcomune, idAlberoProc, true);
	for (Iterator<AlberoprocEndo> iterator = endos.iterator(); iterator.hasNext();) {
	    AlberoprocEndo alberoprocEndo = (AlberoprocEndo) iterator.next();
	    Inventarioprocedimenti invProc = alberoprocEndo.getInventarioprocedimento();
	    if (invProc == null) {
		log.warn(
			"findElencoEndoPerAttivita() - l'endoprocedimento avente ID = {} non dispone di informazioni sul nome del procedimento: sarà scartato.",
			alberoprocEndo.getId().getFkscid());
		continue;
	    }
	    StpEndoTipo1 endoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(idcomune, invProc.getId().getCodice());
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

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService#checkValiditaEndoAttivi(java.lang.String, java.lang.Integer, java.util.List, java.lang.String)
     */
    @Override
    public boolean checkValiditaEndoAttivi(String idcomune, Integer idAlberoProc, Collection<String> interventiLocali, String codiceCatastaleComune,
	    Collection<String> codiciEndoReg, Collection<String> codiciEndoLoc) {

	//boolean stillValid = true;
	ElenchiEndoFACCT endoTrees = getElenchiEndoPerAttivita(idcomune, idAlberoProc, interventiLocali, codiceCatastaleComune);
	if (endoTrees != null) {
	    List<EndoFACCT> endoCart = endoTrees.getEndoCart().linearizeTree();
	    List<EndoFACCT> endoRic = endoTrees.getEndoRicorrenti().linearizeTree();
	    List<EndoFACCT> endoNec = endoTrees.getEndoNecessari().linearizeTree();
	    List<EndoFACCT> endoAlt = endoTrees.getAltriEndo().linearizeTree();
	    boolean stillActive = false;
	    EndoFACCT endo = null;
	    for (String codReg : codiciEndoReg) {
		for (int i = 0; i < endoCart.size() && !stillActive; i++) {
		    endo = endoCart.get(i);
		    stillActive = matchEndoRegionale(codReg, endo);
		}
		for (int i = 0; i < endoRic.size() && !stillActive; i++) {
		    endo = endoRic.get(i);
		    stillActive = matchEndoRegionale(codReg, endo);
		}
		for (int i = 0; i < endoNec.size() && !stillActive; i++) {
		    endo = endoNec.get(i);
		    stillActive = matchEndoRegionale(codReg, endo);
		}
		for (int i = 0; i < endoAlt.size() && !stillActive; i++) {
		    endo = endoAlt.get(i);
		    stillActive = matchEndoRegionale(codReg, endo);
		}
		if (!stillActive) {
		    return false;
		}
	    }
	    for (String codLoc : codiciEndoLoc) {
		stillActive = false;
		for (int i = 0; i < endoCart.size() && !stillActive; i++) {
		    endo = endoCart.get(i);
		    stillActive = matchEndoLocale(codLoc, endo);
		}
		for (int i = 0; i < endoRic.size() && !stillActive; i++) {
		    endo = endoRic.get(i);
		    stillActive = matchEndoLocale(codLoc, endo);
		}
		for (int i = 0; i < endoNec.size() && !stillActive; i++) {
		    endo = endoNec.get(i);
		    stillActive = matchEndoLocale(codLoc, endo);
		}
		for (int i = 0; i < endoAlt.size() && !stillActive; i++) {
		    endo = endoAlt.get(i);
		    stillActive = matchEndoLocale(codLoc, endo);
		}
		if (!stillActive) {
		    return false;
		}
	    }
	}
	return true;
    }

    private boolean matchEndoRegionale(String codEndoReg, EndoFACCT endo) {

	boolean isActive = false;
	if (StringUtils.isNotBlank(codEndoReg)) {
	    if (StringUtils.isNotBlank(endo.getCodiceBDR()) && endo.getCodiceBDR().equals(codEndoReg)) {
		isActive = true;
	    } else {
		List<EndoFACCT> subendos = endo.getSubEndos();
		if (subendos != null) {
		    Iterator<EndoFACCT> endoIter = subendos.iterator();
		    while (endoIter.hasNext() && !isActive) {
			EndoFACCT subendo = (EndoFACCT) endoIter.next();
			isActive = matchEndoRegionale(codEndoReg, subendo);
		    }
		}
	    }
	}
	return isActive;
    }

    private boolean matchEndoLocale(String codEndoLoc, EndoFACCT endo) {

	boolean isActive = false;
	if (StringUtils.isNotBlank(codEndoLoc)) {
	    PkId pkEndo = inventarioProcService.getIdFromEndoprocedimentoKey(codEndoLoc);
	    PkId pkCheck = inventarioProcService.getIdFromEndoprocedimentoKey(new StringBuilder(endo.getIdcomune()).append("|")
		    .append(endo.getCodiceInventario()).toString());
	    if (pkCheck.equals(pkEndo)) {
		isActive = true;
	    } else {
		List<EndoFACCT> subendos = endo.getSubEndos();
		if (subendos != null) {
		    Iterator<EndoFACCT> endoIter = subendos.iterator();
		    while (endoIter.hasNext() && !isActive) {
			EndoFACCT subendo = (EndoFACCT) endoIter.next();
			isActive = matchEndoLocale(codEndoLoc, subendo);
		    }
		}
	    }
	}
	return isActive;
    }

    @Override
    public boolean checkPresenzaEndoCollegato(Integer idAlberoProc, Collection<String> codiciEndoAttivi, Collection<String> codiciEndoReg,
	    Collection<String> codiciEndoLoc) {

	boolean mustCheck = false;
	for (Iterator<String> iteratorEndo = codiciEndoReg.iterator(); iteratorEndo.hasNext() && !mustCheck;) {
	    String codEndo = iteratorEndo.next();
	    PkId pk = inventarioProcService.getIdFromEndoprocedimentoKey(codEndo);
	    AlberoprocEndoId aeid = new AlberoprocEndoId(ORMHelper.getIdcomunebase(), idAlberoProc, pk.getCodice());
	    AlberoprocEndo albEndo = alberoprocEndoService.findById(aeid);
	    if (albEndo != null && BooleanUtils.isTrue(albEndo.getFlagRichiedeEndo())) {
		mustCheck = true;
	    }
	}
	for (Iterator<String> iteratorEndo = codiciEndoAttivi.iterator(); iteratorEndo.hasNext() && !mustCheck;) {
	    String codEndo = iteratorEndo.next();
	    List<StpEndoTipo2> stp2s = stpEndoService.findbyTipoAndCodiceEndoRegionale(codEndo, StpEndoTipo2Service.TIPO_ENDO,
		    ORMHelper.getIdcomunebase());
	    for (StpEndoTipo2 stp2 : stp2s) {
		Inventarioprocedimenti invProc = stp2.getInventarioprocedimenti();
		if (invProc != null) {
		    AlberoprocEndoId aeid = new AlberoprocEndoId(ORMHelper.getIdcomunebase(), idAlberoProc, invProc.getId().getCodice());
		    AlberoprocEndo albEndo = alberoprocEndoService.findById(aeid);
		    if (albEndo != null && BooleanUtils.isTrue(albEndo.getFlagRichiedeEndo())) {
			mustCheck = true;
			break;
		    }
		}
	    }
	}
	int endoLocCount = 0;
	//per un misterioso bug a volte la lista dei codici endo locali contiene un valore null
	for (String cod : codiciEndoLoc) {
	    if (StringUtils.isNotEmpty(cod)) {
		endoLocCount++;
	    }
	}
	if (mustCheck) {
	    return (codiciEndoAttivi.size() + codiciEndoReg.size() + endoLocCount) > 1;
	} else {
	    return true;
	}
    }

    @Override
    public ElenchiEndoFACCT getElenchiEndoPerAttivita(String idcomune, Integer idAlberoProc, Collection<String> interventiLocali,
	    String codiceCatastaleComune) {

	ElenchiEndoFACCT retVal = new ElenchiEndoFACCT();
	retVal.setEndoCart(new AlberoEndo());
	retVal.setEndoNecessari(new AlberoEndo());
	retVal.setEndoRicorrenti(new AlberoEndo());
	retVal.setAltriEndo(new AlberoEndo());
	Set<String> loadedCodes = new HashSet<String>();
	Properties p = loadFromFile("deploy.properties");
	//eliminato l'elenco degli endo CART attivabili da file properties
	Set<String> elencoEndoAttivabili = new HashSet<String>();
	if (p != null) {
	    String codiciEndoAttivabili = p.getProperty("cart.codici_endo_regionali.attivabili");
	    elencoEndoAttivabili = stringToSet(codiciEndoAttivabili, ",");
	}
	Alberoproc aProc = new Alberoproc();
	aProc.setId(new PkId(idcomune, idAlberoProc));
	AlberoprocHelper aProcHelper = alberoprocService.findAlberoprocHelper(aProc, codiceCatastaleComune);
	Set<AlberoprocEndo> aProcEndos = aProcHelper.getAlberoprocEndos();
	Boolean obbligatorio = null;
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
	    if (!BooleanUtils.isTrue(invProc.getDisabilitato())) {
		boolean isPrincipale = alberoprocEndoService.isPrincipale(invProc.getId().getIdcomune(), alberoprocEndo.getAlberoproc().getId()
			.getCodice(), invProc.getId().getCodice());
		obbligatorio = isPrincipale || BooleanUtils.isTrue(alberoprocEndo.getFlagRichiesto());
		putEndoInTheRightTree(retVal, alberoprocEndo, obbligatorio, loadedCodes, elencoEndoAttivabili, null, codiceCatastaleComune, null);
	    }
	}
	// ENDOPROCEDIMENTI LOCALI
	List<AlberoprocEndoLoc> endoProcLocs = alberoprocEndoLocService.findEndoprocedimentiFromAlbero(idcomune, idAlberoProc, codiceCatastaleComune);
	for (AlberoprocEndoLoc ael : endoProcLocs) {
	    if (Boolean.FALSE.equals(ael.getFlagPubblica())) {
		// endoprocedimento non pubblicato non viene proposto
		// nell'interfaccia del frontoffice
		continue;
	    }
	    Inventarioprocedimenti invProc = ael.getInventarioprocedimenti();
	    if (!BooleanUtils.isTrue(invProc.getDisabilitato())) {
		obbligatorio = BooleanUtils.isTrue(ael.getFlagNecessario());
		putEndoInTheRightTree(retVal, invProc, obbligatorio, "", loadedCodes, elencoEndoAttivabili, null, codiceCatastaleComune, null);
	    }
	}
	boolean interventiLocaliFatti = false;
	if (interventiLocali != null) {
	    if (interventiLocali.size() > 0) {
		for (String invKey : interventiLocali) {
		    if (StringUtils.isNotBlank(invKey)) {
			Inventarioprocedimenti invProc = null;
			String descrizione = "";
			PkId endoLoc = null;
			if (invKey.startsWith("ALOCS--")) {
			    endoLoc = inventarioProcService.getIdFromEndoprocedimentoKey(invKey.replace("ALOCS--", ""));
			    AlberoprocEndoLoc eloc = alberoprocEndoLocService.findById(endoLoc);
			    descrizione = StringUtils.defaultString(eloc.getDescrizione()).trim();
			    if (eloc != null) {
				invProc = inventarioProcService.findById(new PkId(eloc.getInventarioprocedimenti().getId().getIdcomune(), eloc
					.getInventarioprocedimenti().getId().getCodice()));
				if (StringUtils.isBlank(descrizione)) {
				    descrizione = eloc.getInventarioprocedimenti().getProcedimento();
				}
			    }
			} else {
			    PkId idInventario = inventarioProcService.getIdFromEndoprocedimentoKey(invKey);
			    invProc = inventarioProcService.findById(idInventario);
			    if (invProc == null) {
				log.warn(
					"findElencoEndoPerAttivita() - l'endoprocedimento avente ID = {} non dispone di informazioni sul nome del procedimento: sarà scartato.",
					idAlberoProc);
				continue;
			    }
			    List<AlberoprocEndoLoc> l = alberoprocEndoLocService.findInterventiPubblicatiFromAlbero(idcomune, idAlberoProc,
				    idInventario.getCodice(), idInventario.getIdcomune());
			    if (l.size() > 0) {
				descrizione = l.get(0).getDescrizione();
			    }
			}
			obbligatorio = Boolean.TRUE; // E' OBBLIGATORIO PERCHE' SCELTO DALL'UTENTE
			if (!BooleanUtils.isTrue(invProc.getDisabilitato())) {
			    putEndoInTheRightTree(retVal, invProc, obbligatorio, "", loadedCodes, elencoEndoAttivabili, descrizione,
				    codiceCatastaleComune, endoLoc);
			    interventiLocaliFatti = true;
			}
		    }
		}
	    }
	}
	//	if (!interventiLocaliFatti) {
	//	    // NON SONO STATI INDICATI INTERVENTILOCALI VERIFICO SE LA CONFIGURAZIONE LI PREVEDE.
	//	    // SE LIPREVEDE LI METTO COMUNQUE
	//	    List<AlberoprocEndoLoc> interventiLocs = alberoprocEndoLocService.findInterventiFromAlbero(idcomune, idAlberoProc);
	//	    for (AlberoprocEndoLoc ael : interventiLocs) {
	//		if (Boolean.FALSE.equals(ael.getFlagPubblica())) {
	//		    // endoprocedimento non pubblicato non viene proposto
	//		    // nell'interfaccia del frontoffice
	//		    continue;
	//		}
	//		Inventarioprocedimenti invProc = ael.getInventarioprocedimenti();
	//		obbligatorio = BooleanUtils.isTrue(ael.getFlagNecessario());
	//		putEndoInTheRightTree(retVal, invProc, obbligatorio, loadedCodes, elencoEndoAttivabili);
	//	    }
	//	}
	/*
	 * Lion 01/04/2015 aggiunto il caricamento di un nuovo elenco di endo
	 * chiamato 'Altri endo'. Gli endo sono caricati in base alle famiglie e
	 * alle categorie configurate in alberoproc_arendo
	 */
	Set<AlberoprocArendo> mearendo = aProcHelper.getAlberoprocArendos();
	obbligatorio = null;// flag obbligatorio == null distingue gli endo che vanno nell'elenco 'altri endo' 
	for (AlberoprocArendo arendo : mearendo) {
	    List<Inventarioprocedimenti> procs = inventarioProcService.findByAlberoprocArendo(arendo);
	    for (Inventarioprocedimenti proc : procs) {
		if (proc.getComune() == null || proc.getComune().getCodicecomune().equalsIgnoreCase(codiceCatastaleComune)) {
		    if (!BooleanUtils.isTrue(proc.getDisabilitato())) {
			putEndoInTheRightTree(retVal, proc, null, "", loadedCodes, elencoEndoAttivabili, null, codiceCatastaleComune, null);
		    }
		}
	    }
	}
	return retVal;
    }

    private void putEndoInTheRightTree(ElenchiEndoFACCT trees, AlberoprocEndo aProc, Boolean obbligatorio, Set<String> loadedCodes,
	    Set<String> codiciEndoAttivabili, String descrizioneEndo, String codiceComune, PkId endoLocPkId) {

	putEndoInTheRightTree(trees, aProc.getInventarioprocedimento(), obbligatorio, aProc.getNote(), loadedCodes, codiciEndoAttivabili,
		descrizioneEndo, codiceComune, endoLocPkId);
    }

    private void putEndoInTheRightTree(ElenchiEndoFACCT trees, Inventarioprocedimenti invProc, Boolean obbligatorio, String noteHtml,
	    Set<String> loadedCodes, Set<String> codiciEndoAttivabili, String descrizioneEndo, String codiceComune, PkId endoLocPkId) {

	String key = inventarioProcService.getEndoprocedimentoKey(invProc);
	boolean added = loadedCodes.add(key);
	if (added) {
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
	    EndoFACCT endo = newEndoFACCT(invProc, (obbligatorio == null ? Boolean.FALSE : obbligatorio), codiciEndoAttivabili, endoLocPkId);
	    if (StringUtils.isNotBlank(descrizioneEndo)) {
		endo.setDescrizione(descrizioneEndo);
	    }
	    if (StringUtils.isNotBlank(noteHtml)) {
		noteHtml = noteHtml.replaceAll("\"", "\\\\\"");
		endo.setNote(Utilities.stripNonPrintableCharacters(noteHtml));
	    }
	    List<InventarioprocEndo> subEndos = inventarioprocEndoService.findByInventarioprocT(invProc.getId().getIdcomune(), invProc.getId()
		    .getCodice(), Boolean.TRUE, null, null, codiceComune, true);
	    for (InventarioprocEndo ipe : subEndos) {
		EndoFACCT ef = newEndoFACCT(ipe.getInventarioprocEndoD(), BooleanUtils.isTrue(ipe.getFlagNecessario()), codiciEndoAttivabili, null);
		endo.getSubEndos().add(ef);
	    }
	    /*
	     * Lion 01/02/2016 eliminato l'elenco 'Endo Attivabili' che conteneva solo gli endo CART (STP_ENDO_TIPO1 != null) sia obbligatori che non
	     * Ora gli endo non sono più divisi in base al fatto se siano endo cart oppure no ma secondo il seguente criterio
	     * 	1)endo necessari: tutti gli endo associati all'intervento in ALBEROPROC_ENDO con FLAG_PRINCIPALE = true OR FLAG_RICHIESTO = true
	     *  2)endo ricorrenti: tutti gli endo associati all'intervento in ALBEROPROC_ENDO con FLAG_PRINCIPALE = false AND FLAG_RICHIESTO = false
	     *  3)altri endo: tutti gli endo appartenenti a famiglie o categorie configurate in ALBEROPROC_ARENDO
	     */
	    AlberoEndo tree = null;
	    if (obbligatorio == null) {
		tree = trees.getAltriEndo();
	    } else if (obbligatorio) {
		tree = trees.getEndoNecessari();
	    } else {
		tree = trees.getEndoRicorrenti();
	    }
	    tree.addEndo(endo, categoria, famiglia);
	}
    }

    private EndoFACCT newEndoFACCT(Inventarioprocedimenti invProc, boolean obbligatorio, Set<String> elencoEndoAttivabili, PkId endoLocPkId) {

	EndoFACCT endo = new EndoFACCT();
	endo.setDescrizione(invProc.getProcedimento());
	endo.setCodiceInventario(invProc.getId().getCodice());
	endo.setIdcomune(invProc.getId().getIdcomune());
	endo.setObbligatorio(BooleanUtils.isTrue(obbligatorio));
	endo.setIdEndoLoc(endoLocPkId);
	StpEndoTipo1 endoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(invProc.getId().getIdcomune(), invProc.getId().getCodice());
	boolean insertEndo = false;
	if (endoTipo1 != null) {
	    if (elencoEndoAttivabili.size() > 0) {
		if (elencoEndoAttivabili.contains(endoTipo1.getCodiceEndoRegionale())) {
		    insertEndo = true;
		}
	    } else {
		insertEndo = true;
	    }
	}
	if (endoTipo1 != null) {
	    if (insertEndo) {
		if (invProc.getId().getIdcomune().equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
		    endo.setCodiceBDR(endoTipo1.getCodiceEndoRegionale());
		    endo.setEndoCART(Boolean.TRUE);
		}
	    }
	}
	return endo;
    }

    //    @Override
    //    public List<EndoFACCT> getEndoLocaliSelezionati(String idcomune, Integer idAlberoProc, List<String> codiciEndoSelez, String searchCodiceEndo) {
    //
    //	List<EndoFACCT> retEndos = new ArrayList<EndoFACCT>();
    //	Alberoproc aProc = new Alberoproc();
    //	aProc.setId(new PkId(idcomune, idAlberoProc));
    //	AlberoprocHelper aProcHelper = alberoprocService.findAlberoprocHelper(aProc);
    //	Set<AlberoprocEndo> aProcEndos = aProcHelper.getAlberoprocEndos();
    //	for (Iterator<AlberoprocEndo> endoIter = aProcEndos.iterator(); endoIter.hasNext();) {
    //	    AlberoprocEndo alberoprocEndo = (AlberoprocEndo) endoIter.next();
    //	    Inventarioprocedimenti invProc = alberoprocEndo.getInventarioprocedimento();
    //	    if (invProc == null) {
    //		log.warn(
    //			"findElencoEndoPerAttivita() - l'endoprocedimento avente ID = {} non dispone di informazioni sul nome del procedimento: sarà scartato.",
    //			alberoprocEndo.getId().getFkscid());
    //		continue;
    //	    }
    //	    if (codiciEndoSelez.contains(invProc.getId().getCodice().toString())) {
    //		if (StringUtils.isNotBlank(searchCodiceEndo)) {
    //		    StringBuilder codEndoSb = new StringBuilder("E[").append(invProc.getId().getCodice()).append("]");
    //		    if (!codEndoSb.toString().contains(searchCodiceEndo)) {
    //			continue;
    //		    }
    //		}
    //		EndoFACCT endo = new EndoFACCT();
    //		endo.setDescrizione(invProc.getProcedimento());
    //		endo.setCodiceInventario(invProc.getId().getCodice());
    //		endo.setObbligatorio(alberoprocEndo.getFlagRichiesto());
    //		retEndos.add(endo);
    //	    }
    //	}
    //	Collections.sort(retEndos);
    //	return retEndos;
    //    }
    @Override
    public List<Allegati> getAllegatiEndoAttivi(Set<String> codiciEndoAttivi, String idProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle, String codiceComune) {

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
		Inventarioprocedimenti invProc = null;
		if (endo.startsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_CODICE_ENDOLCALE_PREFIX)) {
		    endo = endo.substring(FACCTConstants.PRESENTAZIONE_DOMANDA_CODICE_ENDOLCALE_PREFIX.length());
		}
		StpEndoTipo1 stp1s = stpEndoTipo1Service.findByCodiceEndoRegionale(ORMHelper.getIdcomunebase(), endo);
		if (stp1s != null) {
		    invProc = stp1s.getInventarioprocedimenti();
		} else {
		    List<StpEndoTipo2> stp2s = stpEndoService.findbyTipoAndCodiceEndoRegionale(endo, StpEndoTipo2Service.TIPO_ENDO,
			    ORMHelper.getIdcomunebase());
		    if (stp2s.size() > 0) {
			invProc = stp2s.get(0).getInventarioprocedimenti();
		    }
		}
		if (invProc == null) {
		    endo = endo.replaceAll("-", "|");
		    PkId id = inventarioProcService.getIdFromEndoprocedimentoKey(endo);
		    invProc = new Inventarioprocedimenti();
		    invProc.setId(id);
		}
		if (invProc != null && invProc.getId() != null && StringUtils.isNotBlank(invProc.getId().getIdcomune())) {
		    String idcomune = invProc.getId().getIdcomune();
		    if (ORMHelper.getIdcomunebase().equalsIgnoreCase(idcomune)) {
			results.addAll(getAllegatiFromProcedimentoRegionale(descAllegato, searchAllegatoStyle, invProc, codiceComune));
		    } else {
			results.addAll(getAllegatiFromProcedimentoLocale(descAllegato, searchAllegatoStyle, invProc, codiceComune));
		    }
		} else {
		    // se non ho trovato l'endo allora esco con la lista vuota
		    return results;
		}
	    }
	}
	return results;
    }

    /**
     * recupera gli allegati
     * 
     * @param descAllegato
     * @param searchAllegatoStyle
     * @param invProc
     * @return
     */
    @Override
    public List<Allegati> getAllegatiFromProcedimentoLocale(String descAllegato, FieldOperationsEnum searchAllegatoStyle,
	    Inventarioprocedimenti invProc, String codiceComune) {

	List<Allegati> result = new ArrayList<Allegati>();
	FilterTable filters = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction qFilter = new FilterRestriction();
	if (StringUtils.isNotEmpty(descAllegato)) {
	    FilterField<String> ffAllegato = new FilterField<String>("allegato", searchAllegatoStyle, new String[] { descAllegato }, Allegati.class);
	    qFilter.addFilterField(ffAllegato);
	}
	qFilter.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	qFilter.addFilterField(FilterUtils.equals("id.codice", invProc.getId().getCodice(), "inventarioprocedimento", Integer.class));
	qFilter.addFilterField(FilterUtils.equals("id.idcomune", invProc.getId().getIdcomune(), "inventarioprocedimento", String.class));
	qFilter.addFilterField(FilterUtils.equals("pubblica", Integer.valueOf(1), Integer.class));
	filters.addRestriction(qFilter);
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction comunerestriction = new FilterRestriction();
	    comunerestriction.setAndOrRestriction(AndOrRestriction.OR);
	    comunerestriction.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    comunerestriction.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    filters.addRestriction(comunerestriction);
	}
	result = allegatiService.findByFilterTable(filters);
	return result;
    }

    @Override
    public List<Allegati> getAllegatiFromProcedimentoRegionale(String descAllegato, FieldOperationsEnum searchAllegatoStyle,
	    Inventarioprocedimenti invProc, String codiceComune) {

	List<Allegati> resultReg = new ArrayList<Allegati>();
	List<Allegati> resultLoc = new ArrayList<Allegati>();
	List<Allegati> result = new ArrayList<Allegati>();
	FilterTable filters = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction qFilter = new FilterRestriction();
	if (StringUtils.isNotEmpty(descAllegato)) {
	    FilterField<String> ffAllegato = new FilterField<String>("allegato", searchAllegatoStyle, new String[] { descAllegato }, Allegati.class);
	    qFilter.addFilterField(ffAllegato);
	}
	qFilter.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomunebase(), String.class));
	qFilter.addFilterField(FilterUtils.equals("id.codice", invProc.getId().getCodice(), "inventarioprocedimento", Integer.class));
	qFilter.addFilterField(FilterUtils.equals("id.idcomune", invProc.getId().getIdcomune(), "inventarioprocedimento", String.class));
	qFilter.addFilterField(FilterUtils.equals("pubblica", Integer.valueOf(1), Integer.class));
	filters.addRestriction(qFilter);
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction comunerestriction = new FilterRestriction();
	    comunerestriction.setAndOrRestriction(AndOrRestriction.OR);
	    comunerestriction.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    comunerestriction.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    filters.addRestriction(comunerestriction);
	}
	resultReg = allegatiService.findByFilterTable(filters);
	// BOCCI-CHIOCCI 2017 06 30 COMMENTATO PERCHé SONO POSSIBILI PERSONALIZZAZIONI PER QUESTI ENDO
	// VERIFICARE CON REGIONE SE VA BENE PER ENDO DELL'EDILIZIA
	//	if (result.size() > 0) {
	//	    return result;
	//	}
	// se non ci sono "CONFIGRUAZIONI REGIONALI" allora verifico che il comune non abbia personalizzato l'allegato
	filters = new FilterTable(DAOEnum.FIND_ALL);
	qFilter = new FilterRestriction();
	if (StringUtils.isNotEmpty(descAllegato)) {
	    FilterField<String> ffAllegato = new FilterField<String>("allegato", searchAllegatoStyle, new String[] { descAllegato }, Allegati.class);
	    qFilter.addFilterField(ffAllegato);
	}
	qFilter.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	qFilter.addFilterField(FilterUtils.equals("id.codice", invProc.getId().getCodice(), "inventarioprocedimento", Integer.class));
	qFilter.addFilterField(FilterUtils.equals("id.idcomune", invProc.getId().getIdcomune(), "inventarioprocedimento", String.class));
	qFilter.addFilterField(FilterUtils.equals("pubblica", Integer.valueOf(1), Integer.class));
	filters.addRestriction(qFilter);
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction comunerestriction = new FilterRestriction();
	    comunerestriction.setAndOrRestriction(AndOrRestriction.OR);
	    comunerestriction.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    comunerestriction.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    filters.addRestriction(comunerestriction);
	}
	resultLoc = allegatiService.findByFilterTable(filters);
	if (resultReg.size() > 0) {
	    result.addAll(resultReg);
	}
	if (resultLoc.size() > 0) {
	    result.addAll(resultLoc);
	}
	return result;
    }

    @Override
    public List<AlberoprocDocumenti> getDocumentiEreditatiEndoAttivi(Integer idAlberoProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle) {

	List<AlberoprocDocumenti> retDocs = new ArrayList<AlberoprocDocumenti>();
	if (searchAllegatoStyle == null) {
	    searchAllegatoStyle = FieldOperationsEnum.CONTAINS;
	}
	//	if (idAlberoProc != null) {
	//	    Alberoproc nodo = new Alberoproc();
	//	    nodo.setId(new PkId(ORMHelper.getIdcomunebase(), idAlberoProc));
	//	    AlberoprocHelper nodoHelper = alberoprocService.findAlberoprocHelper(nodo);
	//	    if (nodoHelper != null) {
	//		Set<AlberoprocDocumenti> docs = nodoHelper.getAlberoprocDocumentis();
	//		Iterator<AlberoprocDocumenti> docsIter = docs.iterator();
	//		AlberoprocDocumenti doc = null;
	//		while (docsIter.hasNext()) {
	//		    doc = docsIter.next();
	//		    if (doc.getPubblica() > 2 || doc.getPubblica() < 1) {
	//			continue;
	//		    }
	//		    boolean matches = true;
	//		    if (StringUtils.isNotEmpty(descAllegato)) {
	//			switch (searchAllegatoStyle) {
	//			case EQIGNORECASE:
	//			    matches = doc.getDescrizione().equalsIgnoreCase(descAllegato);
	//			    break;
	//			case CONTAINS:
	//			    matches = doc.getDescrizione().contains(descAllegato);
	//			    break;
	//			case STARTSWITH:
	//			    matches = doc.getDescrizione().startsWith(descAllegato);
	//			    break;
	//			case ENDSWITH:
	//			    matches = doc.getDescrizione().endsWith(descAllegato);
	//			    break;
	//			default:
	//			    matches = doc.getDescrizione().equals(descAllegato);
	//			    break;
	//			}
	//		    }
	//		    if (matches) {
	//			retDocs.add(doc);
	//		    }
	//		}
	//	    }
	//	}
	return retDocs;
    }

    @Override
    public List<Inventarioprocdyn2modellit> getSchedeDinamicheEndoAttivi(Set<String> codiciEndoAttivi, String idProc, String descDynModello,
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
		FilterTable filters = new FilterTable(DAOEnum.FIND_ALL);
		FilterRestriction qFilter = new FilterRestriction();
		if (StringUtils.isNotEmpty(descDynModello)) {
		    FilterField<String> ffModello = new FilterField<String>("descrizione", "dyn2Modellit", searchAllegatoStyle,
			    new String[] { descDynModello }, Inventarioprocdyn2modellit.class);
		    qFilter.addFilterField(ffModello);
		}
		Inventarioprocedimenti invProc = null;
		String idcomune = ORMHelper.getIdcomunebase();
		if (endo.indexOf("|") > 0) {
		    PkId id = inventarioProcService.getIdFromEndoprocedimentoKey(endo);
		    idcomune = id.getIdcomune();
		    invProc = new Inventarioprocedimenti();
		    invProc.setId(id);
		} else {
		    StpEndoTipo1 stp1s = stpEndoTipo1Service.findByCodiceEndoRegionale(ORMHelper.getIdcomunebase(), endo);
		    if (stp1s != null) {
			invProc = stp1s.getInventarioprocedimenti();
		    }
		}
		qFilter.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
		if (invProc != null) {
		    FilterField<Inventarioprocedimenti> ffProc = new FilterField<Inventarioprocedimenti>("inventarioprocedimenti",
			    new Inventarioprocedimenti[] { invProc }, Inventarioprocdyn2modellit.class);
		    qFilter.addFilterField(ffProc);
		} else {
		    return results;
		}
		filters.addRestriction(qFilter);
		List<Inventarioprocdyn2modellit> procResults = this.inventarioprocdyn2modellitService.findByFilterTable(filters, null, null);
		results.addAll(procResults);
	    }
	}
	return results;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService#getAllegatiDocumentiSchedeEndoAttivi(java.util.List, java.lang.String, java.lang.String, it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum)
     */
    @Override
    public List<ModuloEndoprocedimentoWrapper> getAllegatiDocumentiSchedeEndoAttivi(Set<String> codiciEndoAttivi, Integer idAlberoproc,
	    String idProcFilter, String descAllegatoFilter, FieldOperationsEnum searchAllegatoStyle, Boolean flagDomandaDinamica, String codiceComune) {

	List<ModuloEndoprocedimentoWrapper> results = new ArrayList<ModuloEndoprocedimentoWrapper>();
	//recupero l'elenco degli endo non CART selezionati nella domanda corrente
	if (codiciEndoAttivi == null) {
	    codiciEndoAttivi = new HashSet<String>();
	}
	List<Allegati> allegati = new ArrayList<Allegati>();
	List<AlberoprocDocumenti> alberoDocs = new ArrayList<AlberoprocDocumenti>();
	List<Inventarioprocdyn2modellit> schede = new ArrayList<Inventarioprocdyn2modellit>();
	ModuloEndoprocedimentoWrapper wrapper = null;
	allegati = this.getAllegatiEndoAttivi(codiciEndoAttivi, idProcFilter, descAllegatoFilter, searchAllegatoStyle, codiceComune);
	allegati = DomainObjectsUtils.getDomainObjectListAsPojoList(allegati, Allegati.class);
	for (Allegati allegato : allegati) {
	    wrapper = new ModuloEndoprocedimentoWrapper();
	    /*
	     * il download dell'allegato nei vari formati previsti dal BO 
	     * è consentito solo se il formato di partenza dell'allegato è HTML, RTF, DOC o DOCX
	     */
	    Oggetti fileObj = allegato.getOggetti();
	    Integer pkObj = allegato.getOggetti().getId().getCodice();
	    fileObj = oggettiService.findById(new PkId(allegato.getId().getIdcomune(), pkObj));
	    if (fileObj != null && fileObj.getOggetto() != null && fileObj.getOggetto().length > 0) {
		String nomeFile = fileObj.getNomefile() != null ? fileObj.getNomefile() : "";
		String extension = FilenameUtils.getExtension(nomeFile);
		if (extension == null) {
		    extension = "";
		}
		if (!FACCTConstants.CONVERTIBLE_EXTENSIONS.contains(extension.toUpperCase())) {
		    allegato.setFoTipodownload("");
		}
	    }
	    allegato = (Allegati) DomainObjectsUtils.getDomainObjectAsPojo(allegato, Allegati.class, true);
	    wrapper.setAllegato(allegato);
	    results.add(wrapper);
	}
	alberoDocs = this.getDocumentiEreditatiEndoAttivi(idAlberoproc, descAllegatoFilter, searchAllegatoStyle);
	alberoDocs = DomainObjectsUtils.getDomainObjectListAsPojoList(alberoDocs, AlberoprocDocumenti.class);
	for (AlberoprocDocumenti alberoDoc : alberoDocs) {
	    wrapper = new ModuloEndoprocedimentoWrapper();
	    Oggetti fileObj = alberoDoc.getOggetto();
	    if (fileObj != null && fileObj.getId() != null && fileObj.getId().getCodice() != null) {
		Integer pkObj = fileObj.getId().getCodice();
		fileObj = oggettiService.findById(new PkId(ORMHelper.getIdcomunebase(), pkObj));
		if (fileObj != null && fileObj.getOggetto() != null && fileObj.getOggetto().length > 0) {
		    String nomeFile = fileObj.getNomefile() != null ? fileObj.getNomefile() : "";
		    String extension = FilenameUtils.getExtension(nomeFile);
		    if (extension == null) {
			extension = "";
		    }
		    if (!FACCTConstants.CONVERTIBLE_EXTENSIONS.contains(extension.toUpperCase())) {
			alberoDoc.setFoTipodownload("");
		    }
		}
	    }
	    alberoDoc = (AlberoprocDocumenti) DomainObjectsUtils.getDomainObjectAsPojo(alberoDoc, AlberoprocDocumenti.class, true);
	    wrapper.setDocumentoAlbero(alberoDoc);
	    results.add(wrapper);
	}
	schede = this.getSchedeDinamicheEndoAttivi(codiciEndoAttivi, idProcFilter, descAllegatoFilter, searchAllegatoStyle);
	schede = DomainObjectsUtils.getDomainObjectListAsPojoList(schede, Inventarioprocdyn2modellit.class);
	for (Inventarioprocdyn2modellit scheda : schede) {
	    wrapper = new ModuloEndoprocedimentoWrapper();
	    wrapper.setSchedaDinamica(scheda);
	    results.add(wrapper);
	}
	//ordinamento della lista per ordine e descrizione
	Collections.sort(results);
	return results;
    }

    @Override
    // TODO implementare l'interrogazione del codice regionale dell'attività
    // utilizzando un WS esposto da (BO oppure FO?)
    public StpEndoTipo2 getDatiAttivitaBdr(String idcomune, Integer idAttivita) {

	// §§§BEGIN§§§
	StpEndoTipo2 stpEndo = stpEndoService.findbyAlberoproc(idcomune, idAttivita);
	return stpEndo;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public AzioneType checkAzione(String idcomune, Integer idAttivita, boolean isDomandaDinamica) {

	AzioneType result = AzioneType.AVVIO;
	//	PkId id = new PkId(idcomune, idAttivita);
	//	Alberoproc alberoproc = alberoprocService.findById(id);
	//	if (alberoproc == null) {
	//	    throw new RuntimeException("Non è stata trovata nessuna voce dell'albero corrispondente al codice: " + id);
	//	}
	//	StpEndoTipo2 stpEndoTipo2 = stpEndoService.findbyAlberoproc(idcomune, idAttivita);
	//	if (stpEndoTipo2 == null) {
	//	    // se non esiste stpEndoTipo2 per la voce dell'albero allora azione
	//	    // avviost0
	//	    return AzioneType.AVVIO_ST_0;
	//	}
	//	if (result.equals(AzioneType.AVVIO)) {
	//	    if (stpEndoTipo2.getStpTipologieEndo2() != null) {
	//		// Se esiste codicetipologiaendo allora azione è
	//		// STP_TIPOLOGIE_ENDO2.DESCRIZIONE
	//		try {
	//		    String descrizione = StringUtils.defaultString(stpEndoTipo2.getStpTipologieEndo2().getDescrizione());
	//		    if (descrizione.equalsIgnoreCase(AzioneType.AVVIO.value())) {
	//			result = AzioneType.AVVIO;
	//		    } else if (descrizione.equalsIgnoreCase(AzioneType.VARIAZIONE.value())) {
	//			result = AzioneType.VARIAZIONE;
	//		    } else if (descrizione.equalsIgnoreCase(AzioneType.CHIUSURA.value()) || descrizione.equalsIgnoreCase("cessazione")) {
	//			result = AzioneType.CHIUSURA;
	//		    } else if (descrizione.equalsIgnoreCase(AzioneType.SUBINGRESSO.value()) || descrizione.equalsIgnoreCase("subentro")) {
	//			result = AzioneType.SUBINGRESSO;
	//		    } else if (descrizione.equalsIgnoreCase(AzioneType.COMUNICAZIONE.value())) {
	//			result = AzioneType.COMUNICAZIONE;
	//		    } else {
	//			log.error("checkAzione - azione non mappata in AzioneType: " + stpEndoTipo2.getStpTipologieEndo2().getDescrizione()
	//				+ ". Sarà gestita come AVVIO_ST_0");
	//			result = AzioneType.AVVIO_ST_0;
	//		    }
	//		    // result = AzioneType.fromValue(stpEndoTipo2.getStpTipologieEndo2().getDescrizione());
	//		} catch (IllegalArgumentException e) {
	//		    log.error("checkAzione - azione non mappata in AzioneType: " + stpEndoTipo2.getStpTipologieEndo2().getDescrizione()
	//			    + ". Sarà gestita come AVVIO_ST_0");
	//		    result = AzioneType.AVVIO_ST_0;
	//		}
	//	    } else {
	//		// ALTRIMENTI E' avvio_st_0
	//		result = AzioneType.AVVIO_ST_0;
	//	    }
	//	}
	//	if (result.equals(AzioneType.AVVIO) && !isDomandaDinamica) {
	//	    result = AzioneType.AVVIO_ST_0;
	//	    // se non è stata definita la sceda di spiegazione e non è una scia
	//	    // allora è AVVIO_ST_0
	//	    // Si capisce dalla scheda di spiegazione
	//	    // ParteRegionaleSchedaEndoTipo2#getModalitaAperturaStandard()
	//	    // ModalitaAperturaEndoTipo2.SEGNALAZIONE_CERTIFICATA_INIZIO_ATTIVITÀ;
	//	    CartServiziDizionario csd = cartServiziDizionarioDAO.getCartServiziDizionario();
	//	    String url = csd.getUrlDownloadSchedaTipo2();
	//	    String codificaEnteRfc53 = codificaEnti53Service.findBySdeProxyID(ORMHelper.getIdente());
	//	    //if (stpEndoTipo2.getCodiceStp() != null) { // BOCCI 2012-12-20 NEL CASO CHE NON SIA STATO INDICATO IL CODICE STP (ES DALLA FUNZIONALITA' CHE CREA LE VOCI NON PRESENTI PER LA TIPOLOGIA ENDO		
	//	    try {
	//		InvioSchedaEndoTipo2 isc2 = cartInvioSchedaEndo2Service.downloadMessaggioSchedaEndo2(idcomune, stpEndoTipo2.getCodiceStp(), url,
	//			codificaEnteRfc53, stpEndoTipo2.getCodiceEndoRegionale());
	//		if (isc2 != null) {
	//		    if (isc2.getParteRegionaleSchedaEndoTipo2() != null) {
	//			if (isc2.getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard() != null) {
	//			    if (isc2.getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard()
	//				    .equals(ModalitaAperturaEndoTipo2.SEGNALAZIONE_CERTIFICATA_INIZIO_ATTIVITÀ)) {
	//				// solo in caso di SCIA è avvio
	//				// altrimenti AVVIO_ST_0
	//				result = AzioneType.AVVIO;
	//			    }
	//			}
	//		    }
	//		}
	//	    } catch (Exception e) {
	//		// non fare niente
	//	    }
	//	    // }
	//	}
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

    @Override
    public EndoFACCT newEndoFacct(Inventarioprocedimenti ipComunica, boolean obbligatorio, Set<String> elencoEndoAttivabili) {

	if (ipComunica == null) {
	    return null;
	}
	return newEndoFACCT(ipComunica, obbligatorio, elencoEndoAttivabili, null);
    }
}
