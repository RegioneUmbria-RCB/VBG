package it.gruppoinit.pal.gp.core.domain.cart;

import it.eng.suap.xengine.model.modulistica.ItemType;
import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.modulistica.RiferimentoType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerConfig;
import it.gruppoinit.pal.gp.core.domain.helper.CartFileCopyInfo;
import it.gruppoinit.pal.gp.core.domain.helper.ModulisticaSTAR;
import it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "DatiDomandaCart")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiDomandaCart", propOrder = { "datiContestoDomanda", "moduli", "modulistica", "allegati", "allegatiDaFirmare", "mdaDaFirmare",
	"distintaModello", "versione", "modulisticaSTAR", "naturaNazionale" })
public class DatiDomandaCart {

    public static final Logger log = LoggerFactory.getLogger(DatiDomandaCart.class);
    public static final String NEW_VERSION = "1";
    public static final String OLD_VERSION = "0";
    private static final String TIPO_MODULO_SEPARATOR = " ";
    @XmlElement(name = "moduli", required = true)
    private TreeSet<DatiModulo> moduli = new TreeSet<DatiModulo>();
    @XmlElement(name = "datiContestoDomanda", required = true)
    private PresentazioneDomandaCartCommand datiContestoDomanda;
    @XmlElement(name = "modulistica", required = true)
    private ModulisticaContentType modulistica;
    @XmlElement(name = "allegati", required = false)
    private List<FileInfo> allegati = new ArrayList<FileInfo>();
    @XmlTransient
    private List<AllegatoCart> fileDeletions = new ArrayList<AllegatoCart>();
    @XmlElement(name = "allegatiDaFirmare", required = false)
    private HashSet<AllegatoDaFirmare> allegatiDaFirmare = new HashSet<AllegatoDaFirmare>();
    @XmlElement(name = "mdaDaFirmare", required = false)
    private HashSet<AllegatoDaFirmare> mdaDaFirmare = new HashSet<AllegatoDaFirmare>();
    @XmlElement(name = "distintaModello", required = false)
    private AllegatoDaFirmare distintaModello;
    @XmlTransient
    private Map<String, AllegatoCart> allegatiCart = new HashMap<String, AllegatoCart>();
    @XmlElement(name = "versione", required = false)
    private String versione = OLD_VERSION;
    @XmlElement(name = "modulisticaSTAR", required = false)
    private ModulisticaSTAR modulisticaSTAR;
    @XmlTransient
    private AutocompilerConfig autocompilerConfig;
    @XmlTransient
    private FoDomande domanda;
    @XmlElement(name = "naturaNazionale", required = false)
    private String naturaNazionale = new String();
    @XmlTransient
    private boolean oneriPagati;
    @XmlTransient
    private boolean datiValidati;


    public DatiDomandaCart() {

	super();
    }

    public DatiModulo getDatiModulo(String riferimento) {

	DatiModulo retModulo = null;
	Iterator<DatiModulo> moduliIterator = moduli.iterator();
	while (moduliIterator.hasNext()) {
	    DatiModulo datiModulo = (DatiModulo) moduliIterator.next();
	    if (datiModulo.getIdModulo().equalsIgnoreCase(riferimento)) {
		retModulo = datiModulo;
		break;
	    }
	}
	return retModulo;
    }

    public List<AllegatoCart> clearDatiModulo(String refModulo) {

	DatiModulo toClear = getDatiModulo(refModulo);
	return clearDatiModulo(toClear);
    }

    public List<AllegatoCart> clearDatiModulo(DatiModulo toClear) {

	List<AllegatoCart> removedFiles = new ArrayList<AllegatoCart>();
	if (toClear != null) {
	    Set<String> idSemantici = toClear.getElencoIdSemantici();
	    //recupero l'elenco dei FileInfo per gli allegati che erano presenti nel modulo da eliminare
	    for (String idSemantico : idSemantici) {
		ValoreIdSemantico vis = toClear.getValoreIdSemantico(idSemantico);
		if (vis.isFile()) {
		    List<ValoreIdSemantico> valuesList = vis.listaValoriScalariNormalizzata();
		    /*
		    if (vis.isIndicizzato()) {
		    valuesList = vis.getValoreIndicizzato();
		    } else {
		    valuesList.add(vis);
		    }
		    */
		    for (ValoreIdSemantico valoreIdSemantico : valuesList) {
			String innerVal = valoreIdSemantico.getValoreScalare();
			if (NumberUtils.isNumber(innerVal)) {
			    FileInfo removedFile = getAllegatoByCodiceOggetto(new Integer(innerVal));
			    if (null != removedFile) {
				removedFiles.add(getAllegatoCartFromFileInfo(removedFile, null));
			    }
			}
		    }
		}
	    }
	    // quindi rimuovo l'intero modulo con tutti i suoi dati
	    this.moduli.remove(toClear);
	    //rimuovo anche i FileInfo eliminati dalla lista degli allegati e da quella degli errori di validazione della firma digitale
	    for (AllegatoCart removed : removedFiles) {
		this.removeAllegatoByCodiceOggetto(removed.getRiferimento());
		this.removeAllegatoDaFirmare(removed.getRiferimento());
	    }
	}
	return removedFiles;
    }

    public List<AllegatoCart> clearDatiModuli() {

	//Set<DatiModulo> modules = getModuli();
	List<FileInfo> removedFileInfo = this.getAllegati();
	List<AllegatoCart> removedFiles = new ArrayList<AllegatoCart>();
	for (FileInfo rfi : removedFileInfo) {
	    removedFiles.add(getAllegatoCartFromFileInfo(rfi, null));
	}
	/*
	Iterator<DatiModulo> modIterator = modules.iterator();
	while (modIterator.hasNext()) {
	    DatiModulo module = (DatiModulo) modIterator.next();
	    List<FileInfo> modRemoved = clearDatiModulo(module);
	    removedFiles.addAll(modRemoved);
	}
	*/
	this.moduli.clear();
	this.allegatiDaFirmare.clear();
	this.allegati = new ArrayList<FileInfo>();
	return removedFiles;
    }

    public Set<DatiModulo> getDatiModuli() {

	return this.moduli;
    }

    public void setValoreScalare(String refModulo, String idSemantico, String dato) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    datiModulo = new DatiModulo(refModulo);
	    this.moduli.add(datiModulo);
	}
	datiModulo.setValoreScalare(idSemantico, dato);
    }

    /*
     * public void setValoreScalare(String idSemantico, String dato) {
     * 
     * ValoreIdSemantico valore = this.mappaValori.get(idSemantico); if (valore
     * == null) { valore = new ValoreIdSemantico(dato);
     * this.mappaValori.put(idSemantico, valore); } else { if (!valore.isEmpty()
     * && !valore.isScalare()) { String message = MessageFormat.format(
     * "Impossibile associare il valore scalare {0} all''id semantico {1} perchè l''id semantico è associato a valori vettoriali."
     * , dato, idSemantico); throw new RuntimeException(message); } else {
     * valore.setValoreScalare(dato); } } }
     */
    public String getValoreScalare(String refModulo, String idSemantico) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    /*
	     * String message = MessageFormat .format(
	     * "Impossibile recuperare il valore scalare associato all''id semantico {0} per il modulo {1} perchè non esiste il modulo richiesto."
	     * , idSemantico, refModulo); throw new RuntimeException(message);
	     */
	    return null;
	}
	return datiModulo.getValoreScalare(idSemantico);
    }

    /*
     * public String getValoreScalare(String idSemantico) {
     * 
     * ValoreIdSemantico valore = this.mappaValori.get(idSemantico); if (null !=
     * valore) { if (!valore.isEmpty() && !valore.isScalare()) { String message
     * = MessageFormat .format(
     * "Impossibile recuperare il valore scalare associato all''id semantico {0} perchè l''id semantico è associato a valori vettoriali."
     * , idSemantico); throw new RuntimeException(message); } return
     * valore.getValoreScalare(); } else { return null; } }
     */
    public void setValoreVettoriale(String refModulo, String idSemantico, String[] dati) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    datiModulo = new DatiModulo(refModulo);
	    this.moduli.add(datiModulo);
	}
	datiModulo.setValoreVettoriale(idSemantico, dati);
    }

    /*
     * public void setValoreVettoriale(String idSemantico, String[] dati) {
     * 
     * ValoreIdSemantico valore = this.mappaValori.get(idSemantico); if (valore
     * == null) { valore = new ValoreIdSemantico(dati);
     * this.mappaValori.put(idSemantico, valore); } else { if (!valore.isEmpty()
     * && !valore.isVettoriale()) { StringBuilder sbValore = new
     * StringBuilder("["); for (int i = 0; i < dati.length; i++) {
     * sbValore.append(dati[i]).append(","); }
     * sbValore.deleteCharAt(sbValore.length() - 1); sbValore.append("]");
     * String message = MessageFormat.format(
     * "Impossibile associare il valore vettoriale {0} all''id semantico {1} perchè l''id semantico è associato a valori scalari."
     * , sbValore, idSemantico); throw new RuntimeException(message); } else {
     * valore.setValoreVettoriale(dati); } } }
     */
    public String[] getValoreVettoriale(String refModulo, String idSemantico) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    /*
	     * String message = MessageFormat .format(
	     * "Impossibile recuperare il valore vettoriale associato all''id semantico {0} per il modulo {1} perchè non esiste il modulo richiesto."
	     * , idSemantico, refModulo); throw new RuntimeException(message);
	     */
	    return null;
	}
	return datiModulo.getValoreVettoriale(idSemantico);
    }

    /*
     * public String[] getValoreVettoriale(String idSemantico) {
     * 
     * ValoreIdSemantico valore = this.mappaValori.get(idSemantico); if (null !=
     * valore) { if (!valore.isEmpty() && !valore.isVettoriale()) { String
     * message = MessageFormat .format(
     * "Impossibile recuperare il valore vettoriale associato all''id semantico {0} perchè l''id semantico non è associato a valori vettoriali."
     * , idSemantico); throw new RuntimeException(message); } return
     * valore.getValoreVettoriale(); } else { return null; } }
     */
    public void setValoriIndicizzati(String refModulo, String idSemantico, List<ValoreIdSemantico> valori) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    datiModulo = new DatiModulo(refModulo);
	    this.moduli.add(datiModulo);
	}
	datiModulo.setValoriIndicizzati(idSemantico, valori);
    }

    /*
     * public void setValoriIndicizzati(String idSemantico,
     * List<ValoreIdSemantico> valori) {
     * 
     * ValoreIdSemantico valore = this.mappaValori.get(idSemantico); if (valore
     * == null) { valore = new ValoreIdSemantico(valori);
     * this.mappaValori.put(idSemantico, valore); } else { if (!valore.isEmpty()
     * && !valore.isIndicizzato()) { String message = MessageFormat.format(
     * "Impossibile associare un valore indicizzato all''id semantico {0} perchè l''id semantico non è contenuto in una tabella."
     * , idSemantico); throw new RuntimeException(message); } else {
     * valore.setValoreIndicizzato(valori); } } }
     */
    public List<ValoreIdSemantico> getValoriIndicizzati(String refModulo, String idSemantico) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    /*
	     * String message = MessageFormat .format(
	     * "Impossibile recuperare i valori indicizzati associati all''id semantico {0} per il modulo {1} perchè non esiste il modulo richiesto."
	     * , idSemantico, refModulo); throw new RuntimeException(message);
	     */
	    return null;
	}
	return datiModulo.getValoriIndicizzati(idSemantico);
    }

    public boolean hasValore(String idSemantico) {

	return hasValore(null, idSemantico, new IndiceIdSemantico());
    }

    public boolean hasValore(String idSemantico, IndiceIdSemantico index) {

	return hasValore(null, idSemantico, index);
    }

    public boolean hasValore(String idModulo, String idSemantico, IndiceIdSemantico index) {

	boolean retval = false;
	Iterator<DatiModulo> moduliIter = this.moduli.iterator();
	while (moduliIter.hasNext() && !retval) {
	    DatiModulo modulo = moduliIter.next();
	    if (StringUtils.isNotEmpty(idModulo)) {
		if (!idModulo.equalsIgnoreCase(modulo.getIdModulo())) {
		    continue;
		}
	    }
	    if (null != index && index.contaLivelli() > 0) {
		retval = modulo.hasValoreIndicizzato(idSemantico, index);
	    } else {
		retval = modulo.hasValore(idSemantico);
	    }
	}
	return retval;
    }

    public ValoreIdSemantico getValoreIdSemantico(String refModulo, String idSemantico) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    /*
	     * String message = MessageFormat.format(
	     * "Impossibile recuperare il valore associato all''id semantico {0} per il modulo {1} perchè non esiste il modulo richiesto."
	     * , idSemantico, refModulo); throw new RuntimeException(message);
	     */
	    return null;
	}
	return datiModulo.getValoreIdSemantico(idSemantico);
    }

    public void setValoreIdSemantico(String refModulo, String idSemantico, ValoreIdSemantico valore) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    datiModulo = new DatiModulo(refModulo);
	    this.moduli.add(datiModulo);
	}
	datiModulo.setValoreIdSemantico(idSemantico, valore);
    }

    public ValoreIdSemantico getValoreIdSemanticoPerIndice(String refModulo, String idSemantico, IndiceIdSemantico index) {

	ValoreIdSemantico vis = getValoreIdSemantico(refModulo, idSemantico);
	if (vis != null && index != null) {
	    vis = getIndexedValue(vis, index);
	}
	return vis;
    }

    public void setValoreIdSemanticoPerIndice(String refModulo, String idSemantico, IndiceIdSemantico indice, ValoreIdSemantico valore) {

	if (indice != null && indice.contaLivelli() > 0) {
	    Integer[] indexValues = indice != null ? indice.vettoreIndici() : new Integer[0];
	    ValoreIdSemantico upperVis = this.getValoreIdSemantico(refModulo, idSemantico);
	    if (upperVis == null) {
		upperVis = new ValoreIdSemantico(new ArrayList<ValoreIdSemantico>());
		if (valore != null) {
		    upperVis.setFile(valore.isFile());
		}
		this.setValoreIdSemantico(refModulo, idSemantico, upperVis);
	    }
	    List<ValoreIdSemantico> innerUpperVis = upperVis.getValoreIndicizzato();
	    if (innerUpperVis == null) {
		innerUpperVis = new ArrayList<ValoreIdSemantico>();
		upperVis.setValoreIndicizzato(innerUpperVis);
	    }
	    for (int i = 0; i < indexValues.length; i++) {
		ValoreIdSemantico tempVis = null;
		while (innerUpperVis.size() < indexValues[i] + 1) {
		    ValoreIdSemantico emptyVis = new ValoreIdSemantico();
		    if (i < indexValues.length - 1) {
			emptyVis.setValoreIndicizzato(new ArrayList<ValoreIdSemantico>());
		    } else {
			if (valore.isScalare()) {
			    emptyVis.setValoreScalare("");
			} else if (valore.isVettoriale()) {
			    emptyVis.setValoreVettoriale(new String[] { "" });
			} else if (valore.isIndicizzato()) {
			    emptyVis.setValoreIndicizzato(new ArrayList<ValoreIdSemantico>());
			}
		    }
		    innerUpperVis.add(emptyVis);
		}
		if (i < indexValues.length - 1) {
		    //ai livelli di indice superiori predispongo se necessario ValoriIdSemantici con liste di valori vuote che accettino l'indice passato come argomento
		    tempVis = innerUpperVis.get(indexValues[i]);
		    if (tempVis == null) {
			tempVis = new ValoreIdSemantico(new ArrayList<ValoreIdSemantico>());
			innerUpperVis.set(indexValues[i], tempVis);
		    }
		    innerUpperVis = tempVis.getValoreIndicizzato();
		} else {
		    //all'ultimo livello richiesto setto il valore passato in input all'indice richiesto
		    innerUpperVis.set(indexValues[i], valore);
		}
	    }
	    //datiModulo.setValoreIdSemantico(idSemantico, valore);
	} else {
	    this.setValoreIdSemantico(refModulo, idSemantico, valore);
	}
    }

    public ValoreIdSemantico searchValoreIdSemantico(String idSemantico) {

	Iterator<DatiModulo> moduliIter = this.moduli.iterator();
	ValoreIdSemantico retValue = null;
	ValoreIdSemantico tempValue = null;
	while (moduliIter.hasNext()) {
	    DatiModulo datiModulo = (DatiModulo) moduliIter.next();
	    tempValue = datiModulo.getValoreIdSemantico(idSemantico);
	    if (tempValue != null) {
		retValue = tempValue;
		break;
	    }
	}
	return retValue;
    }

    public ValoreIdSemantico searchValoreIdSemanticoPerIndice(String idSemantico, IndiceIdSemantico index) {

	Iterator<DatiModulo> moduliIter = this.moduli.iterator();
	ValoreIdSemantico retValue = null;
	ValoreIdSemantico tempValue = null;
	while (moduliIter.hasNext()) {
	    DatiModulo datiModulo = (DatiModulo) moduliIter.next();
	    tempValue = datiModulo.getValoreIdSemantico(idSemantico);
	    if (tempValue != null) {
		retValue = tempValue;
		if (index != null) {
		    retValue = getIndexedValue(retValue, index);
		}
	    }
	}
	return retValue;
    }

    private ValoreIdSemantico getIndexedValue(ValoreIdSemantico vis, IndiceIdSemantico index) {

	Integer[] indexLevels = index.vettoreIndici();
	ValoreIdSemantico retVis = vis;
	for (int i = 0; i < indexLevels.length; i++) {
	    if (retVis != null && retVis.isIndicizzato()) {
		List<ValoreIdSemantico> innerValues = retVis.getValoreIndicizzato();
		if (innerValues.size() > indexLevels[i]) {
		    retVis = innerValues.get(indexLevels[i]);
		} else {
		    retVis = null;
		}
	    } else {
		String message = MessageFormat.format("Impossibile leggere dati indicizzati all''indice {0}. dati: {1}", new Object[] { index, vis });
		log.error("getIndexedValue() - Errore: {}", new Object[] { message });
		// throw new RuntimeException(message);
		retVis = null;
	    }
	}
	return retVis;
    }

    public ValoreIdSemantico removeIdSemantico(String refModulo, String idSemantico) {

	ValoreIdSemantico retVal = null;
	DatiModulo moduleData = getDatiModulo(refModulo);
	if (moduleData != null) {
	    retVal = moduleData.removeIdSemantico(idSemantico);
	}
	return retVal;
    }

    public void removeIdSemanticoWithNameLike(String refModulo, String idSemanticoThatStartWith) {

	DatiModulo moduleData = getDatiModulo(refModulo);
	if (moduleData != null) {
	    Set<String> ls = moduleData.getElencoIdSemantici();
	    for (String idSem : ls) {
		if (idSem.startsWith(idSemanticoThatStartWith)) {
		    moduleData.removeIdSemantico(idSem);
		}
	    }
	}
    }

    /*
     * public Set<String> getElencoIdSemantici(){ return
     * this.mappaValori.keySet(); }
     */
    /**
     * @return the datiContestoDomanda
     */
    public PresentazioneDomandaCartCommand getDatiContestoDomanda() {

	return datiContestoDomanda;
    }

    /**
     * @param datiContestoDomanda
     *            the datiContestoDomanda to set
     */
    public void setDatiContestoDomanda(PresentazioneDomandaCartCommand datiContestoDomanda) {

	this.datiContestoDomanda = datiContestoDomanda;
    }

    /**
     * @return the generaModuloResponse
     */
    public ModulisticaContentType getModulistica() {

	return this.modulistica;
    }

    /**
     * @param generaModuloResponse
     *            the generaModuloResponse to set
     */
    public void setModulistica(ModulisticaContentType modulistica) {

	this.modulistica = modulistica;
    }

    public ModulisticaSTAR getModulisticaSTAR() {

	return modulisticaSTAR;
    }

    public void setModulisticaSTAR(ModulisticaSTAR modulisticaSTAR) {

	this.modulisticaSTAR = modulisticaSTAR;
    }

    public AutocompilerConfig getAutocompilerConfig() {

	return autocompilerConfig;
    }

    public void setAutocompilerConfig(AutocompilerConfig autocompilerConfig) {

	this.autocompilerConfig = autocompilerConfig;
    }

    /**
     * @return the moduli
     */
    public SortedSet<DatiModulo> getModuli() {

	return moduli;
    }

    /**
     * @param moduli
     *            the moduli to set
     */
    public void setModuli(TreeSet<DatiModulo> moduli) {

	this.moduli = moduli;
    }

    /**
     * @return the fileDeletions
     */
    public List<AllegatoCart> getFileDeletions() {

	return fileDeletions;
    }

    public Set<String> getElencoEndo() {

	Set<String> endos = new HashSet<String>();
	if (null != this.datiContestoDomanda) {
	    endos = this.datiContestoDomanda.getEndoAttivi();
	}
	return endos;
    }

    public Set<String> getElencoEndoNoCART() {

	Set<String> endos = new HashSet<String>();
	if (null != this.datiContestoDomanda) {
	    endos = this.datiContestoDomanda.getEndoNoCartAttivi();
	}
	return endos;
    }

    public String getModuloAppartenenzaIdSemantico(String idSemantico) {

	String retVal = null;
	DatiModulo datiModulo = null;
	Iterator<DatiModulo> moduliIterator = this.moduli.iterator();
	while (moduliIterator.hasNext()) {
	    datiModulo = moduliIterator.next();
	    if (datiModulo.hasValore(idSemantico)) {
		retVal = datiModulo.getIdModulo();
		break;
	    }
	}
	return retVal;
    }

    /**
     * Questo metodo elimina dai dati della domanda compilati tutti quelli relativi ai moduli che non sono presenti
     * nell'attuale riferimento alla modulistica. Deve essere invocato quando dopo aver ricericato lo stato di una
     * domanda salvata dal DB l'utente richiede delle modulistiche differenti da quelle utilizzate al tempo del
     * salvataggio dei dati della domanda in modo da eliminare i dati compilati dall'utente per moduli non più
     * utilizzati. Il metodo restituisce una List di {@link FileInfo} che rappresenta l'elenco di riferimenti ai files
     * rimossi dalla domanda durante la ripulitura dei dati: tali riferimenti servono per effettuare le cancellazioni
     * fisiche dei files dal DB.
     */
    public List<AllegatoCart> cleanDatiDomanda() {

	List<AllegatoCart> removedFiles = new ArrayList<AllegatoCart>();
	if (null != this.modulistica) {
	    List<String> moduleNames = new ArrayList<String>();
	    List<ModuloType> moduliRichiesti = this.modulistica.getModulistica().getModulo();
	    for (ModuloType moduloRic : moduliRichiesti) {
		RiferimentoType rifModuloRic = moduloRic.getRiferimento();
		if (null != rifModuloRic) {
		    String modName = StringUtils.isNotEmpty(rifModuloRic.getCodiceModello()) ? rifModuloRic.getCodiceModello() : rifModuloRic
			    .getCodiceEndoProcedimento();
		    if (StringUtils.isNotBlank(modName)) {
			moduleNames.add(modName.toUpperCase());
		    }
		}
	    }
	    List<String> removeModules = new ArrayList<String>();
	    Iterator<DatiModulo> moduliIterator = this.moduli.iterator();
	    while (moduliIterator.hasNext()) {
		DatiModulo datiModulo = (DatiModulo) moduliIterator.next();
		String idModulo = datiModulo.getIdModulo();
		if (idModulo != null && !moduleNames.contains(idModulo.toUpperCase())
			&& !idModulo.equalsIgnoreCase(FACCTConstants.MODELLO_INIT_DATI_DINAMICI)) {
		    removeModules.add(idModulo);
		}
	    }
	    for (String removeMod : removeModules) {
		List<AllegatoCart> modRemoved = this.clearDatiModulo(removeMod);
		removedFiles.addAll(modRemoved);
		if (log.isDebugEnabled()) {
		    log.debug("cleanDatiDomanda() - rimossi i dati del modulo {} perché non più utilizzato nella modulistica attuale della domanda",
			    new String[] { removeMod });
		}
	    }
	}
	return removedFiles;
    }

    public void addAllegatoDaFirmare(AllegatoDaFirmare signMe) {

	if (null != signMe) {
	    this.allegatiDaFirmare.add(signMe);
	}
    }

    public void removeAllegatoDaFirmare(Integer codiceOggetto) {

	this.allegatiDaFirmare.remove(new AllegatoDaFirmare(codiceOggetto));
    }

    /**
     * Rimuove tutti gli {@link AllegatoDaFirmare} che sono associati all'id semantico e all'indice passati come
     * argomento. Se l'indice passato è diverso da null e > -1 vengono anche diminuiti di uno tutti gli indici degli
     * allegati da firmare associati allo stesso id semantico aventi indice maggiore di quello passato.
     * 
     * @param deletedRowIndex
     * @param idsemantico
     */
    public void removeAllegatiDaFirmareForIdSemantico(String idSemantico) {

	Iterator<AllegatoDaFirmare> allIt = this.allegatiDaFirmare.iterator();
	while (allIt.hasNext()) {
	    AllegatoDaFirmare all = (AllegatoDaFirmare) allIt.next();
	    if (idSemantico.equals(all.getIdSemantico())) {
		allIt.remove();
	    }
	}
    }

    public void setAllegatiDaFirmare(HashSet<AllegatoDaFirmare> allegatidaFirmare) {

	this.allegatiDaFirmare = allegatidaFirmare;
    }

    public HashSet<AllegatoDaFirmare> getAllegatiDaFirmare() {

	return this.allegatiDaFirmare;
    }

    public HashSet<AllegatoDaFirmare> getErroriFirmeDigitali() {

	HashSet<AllegatoDaFirmare> errors = new HashSet<AllegatoDaFirmare>();
	Iterator<AllegatoDaFirmare> allIter = this.allegatiDaFirmare.iterator();
	while (allIter.hasNext()) {
	    AllegatoDaFirmare all = (AllegatoDaFirmare) allIter.next();
	    if (all.isError()) {
		errors.add(all);
	    }
	}
	return errors;
    }

    public HashSet<AllegatoDaFirmare> getWarningsFirmeDigitali() {

	HashSet<AllegatoDaFirmare> warnings = new HashSet<AllegatoDaFirmare>();
	Iterator<AllegatoDaFirmare> allIter = this.allegatiDaFirmare.iterator();
	while (allIter.hasNext()) {
	    AllegatoDaFirmare all = (AllegatoDaFirmare) allIter.next();
	    if (all.isWarning()) {
		warnings.add(all);
	    }
	}
	return warnings;
    }

    public AllegatoDaFirmare getAllegatoDaFirmare(Integer codiceOggetto) {

	AllegatoDaFirmare retVal = null;
	if (codiceOggetto != null) {
	    Iterator<AllegatoDaFirmare> it = this.allegatiDaFirmare.iterator();
	    AllegatoDaFirmare findMe = new AllegatoDaFirmare(codiceOggetto);
	    while (it.hasNext() && retVal == null) {
		AllegatoDaFirmare allegatoDaFirmare = (AllegatoDaFirmare) it.next();
		if (allegatoDaFirmare.equals(findMe)) {
		    retVal = allegatoDaFirmare;
		    break;
		}
	    }
	}
	return retVal;
    }

    public List<FileInfo> getAllegati() {

	return this.allegati;
    }

    public void addAllegato(FileInfo allegato) {

	this.allegati.add(allegato);
    }

    public FileInfo getAllegatoByCodiceOggetto(Integer codiceOggetto) {

	FileInfo fi = null;
	if (null != codiceOggetto) {
	    for (FileInfo allegato : allegati) {
		if (allegato.getIdOggetto().equals(codiceOggetto)) {
		    fi = allegato;
		    break;
		}
	    }
	}
	return fi;
    }

    public List<FileInfo> getAllegatiByIdSemantico(String idSemantico) {

	List<FileInfo> files = new ArrayList<FileInfo>();
	if (StringUtils.isNotBlank(idSemantico)) {
	    for (int i = allegati.size() - 1; i > -1; i--) {
		FileInfo allegato = allegati.get(i);
		if (allegato != null && idSemantico.equals(allegato.getIdSemantico())) {
		    files.add(allegato);
		}
	    }
	}
	return files;
    }

    public List<FileInfo> getAllegatiByModulo(String modulo) {

	List<FileInfo> files = new ArrayList<FileInfo>();
	DatiModulo datiModulo = getDatiModulo(modulo);
	if (null != datiModulo) {
	    for (FileInfo allegato : this.allegati) {
		ValoreIdSemantico vis = datiModulo.getValoreIdSemantico(allegato.getIdSemantico());
		boolean isAllegato = false;
		if (vis != null && vis.isFile() && !vis.isEmpty()) {
		    isAllegato = true;
		}
		if (!isAllegato) {
		    if (vis != null && !vis.isEmpty()) {
			List<ValoreIdSemantico> valoreIndicizzato = vis.getValoreIndicizzato();
			if (valoreIndicizzato != null && !valoreIndicizzato.isEmpty()) {
			    for (ValoreIdSemantico valoreIdSemantico : valoreIndicizzato) {
				if (valoreIdSemantico != null && valoreIdSemantico.isFile()) {
				    isAllegato = true;
				    break;
				}
			    }
			}
		    }
		}
		if (isAllegato) {
		    CartFileCopyInfo ci = new CartFileCopyInfo();
		    ci.setNomeFileTemporaneo(allegato.getNomeFile());
		    ci.setIdOggetto(allegato.getIdOggetto());
		    ci.setIdSemantico(allegato.getIdSemantico());
		    ci.setIdComune(ORMHelper.getIdcomune());
		    allegato.setNomeFile(ci.buildNomeFilePresentazioneDomanda());
		    files.add(allegato);
		}
	    }
	}
	return files;
    }

    public boolean removeAllegatoByCodiceOggetto(Integer codiceOggetto) {

	FileInfo checkForMe = new FileInfo();
	checkForMe.setIdOggetto(codiceOggetto);
	removeAllegatoDaFirmare(codiceOggetto);
	return this.allegati.remove(checkForMe);
    }

    public List<FileInfo> removeAllegatiByIdSemantico(String idSemantico) {

	List<FileInfo> removed = new ArrayList<FileInfo>();
	if (StringUtils.isNotBlank(idSemantico)) {
	    for (int i = allegati.size() - 1; i > -1; i--) {
		FileInfo allegato = allegati.get(i);
		if (allegato != null && idSemantico.equals(allegato.getIdSemantico())) {
		    removed.add(allegati.remove(i));
		}
	    }
	}
	removeAllegatiDaFirmareForIdSemantico(idSemantico);
	return removed;
    }

    /**
     * @return the mdaDaFirmare
     */
    public HashSet<AllegatoDaFirmare> getMdaDaFirmare() {

	return mdaDaFirmare;
    }

    /**
     * @param mdaDaFirmare
     *            the mdaDaFirmare to set
     */
    public void setMdaDaFirmare(HashSet<AllegatoDaFirmare> mdaDaFirmare) {

	this.mdaDaFirmare = mdaDaFirmare;
    }

    /**
     * @return the distintaModello
     */
    public AllegatoDaFirmare getDistintaModello() {

	return distintaModello;
    }

    /**
     * @param distintaModello
     *            the distintaModello to set
     */
    public void setDistintaModello(AllegatoDaFirmare distintaModello) {

	this.distintaModello = distintaModello;
    }

    public String getVersione() {

	return versione;
    }

    public void setVersione(String versione) {

	this.versione = versione;
    }

    public FoDomande getDomanda() {

	return domanda;
    }

    public void setDomanda(FoDomande domanda) {

	this.domanda = domanda;
    }

    public ValoreIdSemantico removeValoreIdSemanticoPerIndice(String riferimentoModulo, String idSemantico, IndiceIdSemantico rowIndex) {

	ValoreIdSemantico vis = getValoreIdSemanticoPerIndice(riferimentoModulo, idSemantico, rowIndex);
	if (vis != null) {
	    if (vis.isScalare()) {
		vis.setValoreScalare("");
	    } else if (vis.isVettoriale()) {
		vis.setValoreVettoriale(new String[0]);
	    }
	}
	return vis;
    }

    public void registraMetadatiCampoQuadro(String refModulo, String quadro, ItemType item, IndiceIdSemantico indiceCampo, Boolean isAttivo) {

	DatiModulo datiModulo = getDatiModulo(refModulo);
	if (null == datiModulo) {
	    datiModulo = new DatiModulo(refModulo);
	    this.moduli.add(datiModulo);
	}
	datiModulo.registraMetadatiCampo(quadro, item, indiceCampo, isAttivo);
    }

    public Boolean checkStatoCampoAttivo(String refModulo, String quadro, ItemType item, IndiceIdSemantico indice) {

	Boolean retVal = null;
	if (item != null) {
	    String idItem = null;
	    //String idSemantico = null;
	    if (item.getCampo() != null) {
		idItem = item.getCampo().getId();
		//idSemantico = item.getCampo().getIdSemantico();
	    } else if (item.getFile() != null) {
		idItem = item.getFile().getId();
		//idSemantico = item.getFile().getIdSemantico();
	    }
	    if (StringUtils.isNotEmpty(idItem)) {
		if (indice == null) {
		    indice = new IndiceIdSemantico();
		}
		DatiModulo modulo = getDatiModulo(refModulo);
		if (modulo != null) {
		    retVal = modulo.getMetadatiModulo().verificaStatoCampoAttivo(quadro, item, indice, this);
		}
	    }
	}
	return retVal;
    }

    public List<MetadatiCampo> getStatoCampiPerIdSemanticoEIndice(String refModulo, String idSemantico, IndiceIdSemantico indice) {

	List<MetadatiCampo> metaCampi = null;
	DatiModulo datiModulo = getDatiModulo(refModulo);
	MetadatiModulo metaModulo = datiModulo != null ? datiModulo.getMetadatiModulo() : null;
	if (metaModulo != null) {
	    metaCampi = metaModulo.getStatoCampiPerIdSemanticoEIndice(idSemantico, indice, null);
	}
	return metaCampi;
    }

    /**
     * @return the allegatiCart
     */
    public Collection<AllegatoCart> getAllegatiCartDaSalvare() {

	return allegatiCart.values();
    }

    public void clearAllegatiCart() {

	this.allegatiCart.clear();
    }

    public List<AllegatoCart> findAllegatiCartByTipoFile(FoDomandeOggettiService.TIPO_FILE tipoFile) {

	List<AllegatoCart> retAlls = new ArrayList<AllegatoCart>();
	for (String key : this.allegatiCart.keySet()) {
	    AllegatoCart allegatoCart = this.allegatiCart.get(key);
	    if (tipoFile != null) {
		if (StringUtils.defaultString(allegatoCart.getTipoFile()).startsWith(tipoFile.toString())) {
		    retAlls.add(allegatoCart);
		}
	    } else if (allegatoCart.getTipoFile() == null) {
		retAlls.add(allegatoCart);
	    }
	}
	return retAlls;
    }

    public void setAllegatoCartByTipoEModulo(AllegatoCart all, FoDomandeOggettiService.TIPO_FILE tipoFile, String modulo) {

	String tipoStr = buildTipoAllegato(tipoFile, modulo);
	if (all != null) {
	    all.setTipoFile(tipoStr);
	}
	this.allegatiCart.put(tipoStr, all);
    }

    public AllegatoCart getAllegatoCartByTipoEModulo(FoDomandeOggettiService.TIPO_FILE tipoFile, String modulo) {

	return this.allegatiCart.get(buildTipoAllegato(tipoFile, modulo));
    }

    public String buildTipoAllegato(FoDomandeOggettiService.TIPO_FILE tipoFile, String modulo) {

	StringBuilder sb = new StringBuilder();
	if (tipoFile != null) {
	    sb.append(tipoFile.toString()).append(TIPO_MODULO_SEPARATOR);
	}
	if (StringUtils.isNotBlank(modulo)) {
	    sb.append(modulo.replaceAll(" ", "_"));
	}
	return sb.toString();
    }

    public static AllegatoCart getAllegatoCartFromFileInfo(FileInfo fInfo, IndiceIdSemantico index) {

	AllegatoCart retVal = null;
	if (fInfo != null) {
	    retVal = new AllegatoCart(fInfo.getIdOggetto());
	    retVal.setIdSemantico(fInfo.getIdSemantico());
	    retVal.setFileName(fInfo.getNomeFile());
	}
	if (retVal != null && index != null && index.contaLivelli() > 0) {
	    retVal.setRowIndex(index.getIndice());
	}
	return retVal;
    }

    public void setNaturaNazionale(String naturaNazionale) {

	this.naturaNazionale = naturaNazionale;
    }

    public String getNaturaNazionale() {

	return naturaNazionale;
    }
    
    
    public boolean isOneriPagati() {
    
        return oneriPagati;
    }

    
    public void setOneriPagati(boolean oneriPagati) {
    
        this.oneriPagati = oneriPagati;
    }

    
    public boolean isDatiValidati() {
    
        return datiValidati;
    }

    
    public void setDatiValidati(boolean datiValidati) {
    
        this.datiValidati = datiValidati;
    }

    public void startCleaningData(String rifModulo){
	DatiModulo mod = this.getDatiModulo(rifModulo);
	if (mod != null) {
	    mod.startCleaning();
	}
    }

    public void endCleaningData(String rifModulo) {

	DatiModulo mod = this.getDatiModulo(rifModulo);
	if (mod != null) {
	    mod.endCleaning();
	}
    }
}
