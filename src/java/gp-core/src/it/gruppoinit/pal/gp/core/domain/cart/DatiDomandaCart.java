package it.gruppoinit.pal.gp.core.domain.cart;

import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.modulistica.RiferimentoType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
	"distintaModello" })
public class DatiDomandaCart {

    public static final Logger log = LoggerFactory.getLogger(DatiDomandaCart.class);
    @XmlElement(name = "moduli", required = true)
    private TreeSet<DatiModulo> moduli = new TreeSet<DatiModulo>();
    @XmlElement(name = "datiContestoDomanda", required = true)
    private PresentazioneDomandaCartCommand datiContestoDomanda;
    @XmlElement(name = "modulistica", required = true)
    private ModulisticaContentType modulistica;
    @XmlElement(name = "allegati", required = false)
    private List<FileInfo> allegati = new ArrayList<FileInfo>();
    @XmlTransient
    private List<FileInfo> fileDeletions = new ArrayList<FileInfo>();
    @XmlElement(name = "allegatiDaFirmare", required = false)
    private HashSet<AllegatoDaFirmare> allegatiDaFirmare = new HashSet<AllegatoDaFirmare>();
    @XmlElement(name = "mdaDaFirmare", required = false)
    private HashSet<AllegatoDaFirmare> mdaDaFirmare = new HashSet<AllegatoDaFirmare>();
    @XmlElement(name = "distintaModello", required = false)
    private AllegatoDaFirmare distintaModello;

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

    public List<FileInfo> clearDatiModulo(String refModulo) {

	DatiModulo toClear = getDatiModulo(refModulo);
	return clearDatiModulo(toClear);
    }

    public List<FileInfo> clearDatiModulo(DatiModulo toClear) {

	List<FileInfo> removedFiles = new ArrayList<FileInfo>();
	if (toClear != null) {
	    // elimino tutti gli allegati da firmare del modulo che sono salvati
	    // in una mappa globale
	    Set<String> idSemantici = toClear.getElencoIdSemantici();
	    for (String idSemantico : idSemantici) {
		ValoreIdSemantico vis = toClear.getValoreIdSemantico(idSemantico);
		if (vis.isFile()) {
		    List<ValoreIdSemantico> valuesList = vis.listaValoriScalariNormalizzata();
		    if (vis.isIndicizzato()) {
			valuesList = vis.getValoreIndicizzato();
		    } else {
			valuesList.add(vis);
		    }
		    for (ValoreIdSemantico valoreIdSemantico : valuesList) {
			String innerVal = valoreIdSemantico.getValoreScalare();
			if (NumberUtils.isNumber(innerVal)) {
			    FileInfo removedFile = getAllegatoByCodiceOggetto(new Integer(innerVal));
			    if (null != removedFile) {
				removedFiles.add(removedFile);
			    }
			}
		    }
		}
	    }
	    // quindi rimuovo l'intero modulo con tutti i suoi dati
	    this.moduli.remove(toClear);
	}
	return removedFiles;
    }

    public List<FileInfo> clearDatiModuli() {

	List<FileInfo> removedFiles = this.getAllegati();
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

	return hasValore(idSemantico, new IndiceIdSemantico());
    }

    public boolean hasValore(String idSemantico, IndiceIdSemantico index) {

	boolean retval = false;
	Iterator<DatiModulo> moduliIter = this.moduli.iterator();
	while (moduliIter.hasNext() && !retval) {
	    DatiModulo modulo = moduliIter.next();
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
	if (vis != null) {
	    vis = getIndexedValue(vis, index);
	}
	return vis;
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
    public List<FileInfo> getFileDeletions() {

	return fileDeletions;
    }

    public List<String> getElencoEndo() {

	List<String> endos = new ArrayList<String>();
	if (null != this.datiContestoDomanda) {
	    endos = this.datiContestoDomanda.getEndoAttivi();
	}
	return endos;
    }

    public List<String> getElencoEndoNoCART() {

	List<String> endos = new ArrayList<String>();
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
     * Questo metodo elimina dai dati della domanda compilati tutti quelli
     * relativi ai moduli che non sono presenti nell'attuale riferimento alla
     * modulistica. Deve essere invocato quando dopo aver ricericato lo stato di
     * una domanda salvata dal DB l'utente richiede delle modulistiche
     * differenti da quelle utilizzate al tempo del salvataggio dei dati della
     * domanda in modo da eliminare i dati compilati dall'utente per moduli non
     * più utilizzati. Il metodo restituisce una List di {@link FileInfo} che
     * rappresenta l'elenco di riferimenti ai files rimossi dalla domanda
     * durante la ripulitura dei dati: tali riferimenti servono per effettuare
     * le cancellazioni fisiche dei files dal DB.
     */
    public List<FileInfo> cleanDatiDomanda() {

	List<FileInfo> removedFiles = new ArrayList<FileInfo>();
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
		List<FileInfo> modRemoved = this.clearDatiModulo(removeMod);
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
     * Rimuove tutti gli {@link AllegatoDaFirmare} che sono associati all'id
     * semantico e all'indice passati come argomento. Se l'indice passato è
     * diverso da null e > -1 vengono anche diminuiti di uno tutti gli indici
     * degli allegati da firmare associati allo stesso id semantico aventi
     * indice maggiore di quello passato.
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

    public void removeValoreIdSemanticoPerIndice(String riferimentoModulo, String idSemantico, IndiceIdSemantico rowIndex) {

	DatiModulo moduleData = getDatiModulo(riferimentoModulo);
	if (moduleData != null) {
	    moduleData.removeValoreIdSemanticoIndicizzato(idSemantico, rowIndex.getIndice());
	}
    }
}
