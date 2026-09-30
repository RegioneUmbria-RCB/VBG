package it.gruppoinit.pal.gp.core.domain.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

@XmlRootElement(name = "ValoreIdSemantico")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValoreIdSemantico", propOrder = { "valoreScalare", "valoreVettoriale", "valoreIndicizzato", "fileInfo", "isFile" })
public class ValoreIdSemantico {

    @XmlElement(name = "valoreScalare", required = false)
    private String valoreScalare;// per campi singoli a valore singolo
    @XmlElement(name = "valoreVettoriale", required = false)
    private String[] valoreVettoriale;// per campi singoli che accettano valori
				      // multipli
    @XmlElement(name = "valoreIndicizzato", required = false)
    private List<ValoreIdSemantico> valoreIndicizzato;// per campi singoli o
						      // multipli che sono
						      // indicizzati perchè si
						      // trovano all'interno
						      // delle righe di una
						      // tabella
    @XmlElement(name = "fileInfo", required = false)
    private List<FileInfo> fileInfo = new ArrayList<FileInfo>();
    @XmlAttribute(name = "isFile", required = false)
    private boolean isFile;// per campi che mappano gli id semantici di campi di
			   // tipo file

    /**
     * crea un valore voto. utilizzato solo da JAXB per operazioni di
     * marshal/unmarshal
     * 
     * @param valore
     */
    public ValoreIdSemantico() {

    }

    /**
     * crea un valore scalare
     * 
     * @param valore
     */
    public ValoreIdSemantico(String valore) {

	setValoreScalare(valore);
    }

    /**
     * crea un valore vettoriale
     * 
     * @param valore
     */
    public ValoreIdSemantico(String[] valore) {

	setValoreVettoriale(valore);
    }

    /**
     * crea un valore indicizzato
     * 
     * @param valore
     */
    public ValoreIdSemantico(List<ValoreIdSemantico> valore) {

	setValoreIndicizzato(valore);
    }

    public String getValoreScalare() {

	return valoreScalare;
    }

    public void setValoreScalare(String valoreScalare) {

	this.valoreScalare = valoreScalare;
    }

    public String[] getValoreVettoriale() {

	return valoreVettoriale;
    }

    public void setValoreVettoriale(String[] valoreVettoriale) {

	this.valoreVettoriale = valoreVettoriale;
    }

    public boolean isVettoriale() {

	return this.valoreVettoriale != null;
    }

    public boolean isIndicizzato() {

	return this.valoreIndicizzato != null;
    }

    public boolean isScalare() {

	return this.valoreScalare != null;
    }

    public boolean isEmpty() {

	return this.valoreIndicizzato == null && this.valoreVettoriale == null && this.valoreScalare == null;
    }

    public boolean hasNoValues() {
	boolean noVal = this.valoreScalare == null;
	if (noVal) {
	    noVal = this.valoreVettoriale == null || this.valoreVettoriale.length == 0;
	}
	if (noVal) {
	    if (this.valoreIndicizzato != null) {
		for (ValoreIdSemantico value : this.valoreIndicizzato) {
		    noVal &= value.hasNoValues();
		}
	    }
	}
	return noVal;
    }

    /**
     * @return the valoreIndicizzato
     */
    public List<ValoreIdSemantico> getValoreIndicizzato() {

	return valoreIndicizzato;
    }

    /**
     * @param valoreIndicizzato
     *            the valoreIndicizzato to set
     */
    public void setValoreIndicizzato(List<ValoreIdSemantico> valoreIndicizzato) {

	this.valoreIndicizzato = valoreIndicizzato;
    }

    public boolean hasValue() {

	boolean retVal = false;
	// §§§BEGIN§§§
	if (isScalare()) {
	    return StringUtils.isNotEmpty(getValoreScalare());
	} else if (isVettoriale()) {
	    String[] values = getValoreVettoriale();
	    for (int i = 0; i < values.length; i++) {
		if (StringUtils.isNotEmpty(values[i])) {
		    retVal = true;
		    break;
		}
	    }
	    return retVal;
	} else if (isIndicizzato()) {
	    List<ValoreIdSemantico> indexedValues = getValoreIndicizzato();
	    ValoreIdSemantico value = null;
	    for (int i = 0; i < indexedValues.size(); i++) {
		value = indexedValues.get(i);
		if (value.hasValue()) {
		    retVal = true;
		    break;
		}
	    }
	}
	// §§§END§§§
	return retVal;
    }

    public boolean isFile() {

	return isFile;
    }

    public void setFile(boolean isFile) {

	this.isFile = isFile;
    }

    public List<FileInfo> getFileInfo() {

	return this.fileInfo;
    }

    public FileInfo getFileInfoForFileName(String fileName) {

	// if(fileName.contains(s))
	FileInfo retVal = null;
	for (FileInfo fi : this.fileInfo) {
	    if (fi.getNomeFile().equals(fileName)) {
		retVal = fi;
		break;
	    }
	}
	return retVal;
    }

    public FileInfo getFileInfoById(Integer idOggetto) {

	// if(fileName.contains(s))
	FileInfo retVal = null;
	for (FileInfo fi : this.fileInfo) {
	    if (fi.getIdOggetto().equals(idOggetto)) {
		retVal = fi;
		break;
	    }
	}
	return retVal;
    }

    /**
     * Rimuove l'allegato avente il codice oggetto passato come argomento dai
     * valori dell'id semantico
     * 
     * @param codiceOggetto
     * @return
     */
    public boolean removeAllegato(Integer codiceOggetto) {

	boolean removed = false;
	if (this.isFile() && codiceOggetto != null) {
	    /*
	     * for (int i = 0; i < fileInfo.size(); i++) { FileInfo fi =
	     * fileInfo.get(i); if(codiceOggetto.equals(fi.getIdOggetto())){
	     * fileInfo.remove(i); removed = true; break; } }
	     */
	    if (isScalare()) {
		if (NumberUtils.isNumber(getValoreScalare()) && codiceOggetto.equals(new Integer(getValoreScalare()))) {
		    this.setValoreScalare("");
		    removed = true;
		}
	    } else if (isVettoriale()) {
		String[] vectValues = this.getValoreVettoriale();
		for (int i = 0; i < vectValues.length; i++) {
		    String val = vectValues[i];
		    if (NumberUtils.isNumber(val) && codiceOggetto.equals(new Integer(val))) {
			vectValues[i] = "";
			removed = true;
			break;
		    }
		}
	    }
	}
	if (!removed && this.isIndicizzato()) {
	    List<ValoreIdSemantico> ivis = this.getValoreIndicizzato();
	    for (ValoreIdSemantico vis : ivis) {
		if (vis.removeAllegato(codiceOggetto)) {
		    removed = true;
		    break;
		}
	    }
	}
	return removed;
    }

    public List<ValoreIdSemantico> listaValoriScalariNormalizzata() {
	List<ValoreIdSemantico> retList = new ArrayList<ValoreIdSemantico>();
	if (isScalare()) {
	    retList.add(new ValoreIdSemantico(valoreScalare));
	} else if (isVettoriale()) {
	    for (int i = 0; i < valoreVettoriale.length; i++) {
		retList.add(new ValoreIdSemantico(valoreVettoriale[i]));
	    }
	} else if (isIndicizzato()) {
	    for (int i = 0; i < valoreIndicizzato.size(); i++) {
		ValoreIdSemantico temp = valoreIndicizzato.get(i);
		retList.addAll(temp.listaValoriScalariNormalizzata());
	    }
	}
	return retList;
    }

    public List<ValoreIdSemantico> listaValoriNormalizzata() {
	List<ValoreIdSemantico> retList = new ArrayList<ValoreIdSemantico>();
	if (isScalare() || isVettoriale()) {
	    retList.add(this);
	} else if (isIndicizzato()) {
	    for (int i = 0; i < valoreIndicizzato.size(); i++) {
		ValoreIdSemantico temp = valoreIndicizzato.get(i);
		retList.addAll(temp.listaValoriNormalizzata());
	    }
	}
	return retList;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	if (isVettoriale()) {
	    sb.append("[");
	    for (int i = 0; i < valoreVettoriale.length; i++) {
		sb.append(valoreVettoriale[i]).append(",");
	    }
	    if (valoreVettoriale.length > 0) {
		sb.deleteCharAt(sb.length() - 1);
	    }
	    sb.append("]");
	} else if (isIndicizzato()) {
	    sb.append("[");
	    List<ValoreIdSemantico> innerValues = this.getValoreIndicizzato();
	    for (int i = 0; i < innerValues.size(); i++) {
		ValoreIdSemantico vis = innerValues.get(i);
		sb.append(StringUtils.leftPad(Integer.toString(i), 2)).append(": ").append(vis.toString()).append(",");
	    }
	    if (!innerValues.isEmpty()) {
		sb.deleteCharAt(sb.length() - 1);
	    }
	    sb.append("]");
	} else if (isScalare()) {
	    sb.append(getValoreScalare());
	}
	return sb.toString();
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fileInfo == null) ? 0 : fileInfo.hashCode());
	result = prime * result + (isFile ? 1231 : 1237);
	result = prime * result + ((valoreIndicizzato == null) ? 0 : valoreIndicizzato.hashCode());
	result = prime * result + ((valoreScalare == null) ? 0 : valoreScalare.hashCode());
	result = prime * result + Arrays.hashCode(valoreVettoriale);
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ValoreIdSemantico other = (ValoreIdSemantico) obj;
	if (fileInfo == null) {
	    if (other.fileInfo != null)
		return false;
	} else if (!fileInfo.equals(other.fileInfo))
	    return false;
	if (isFile != other.isFile)
	    return false;
	if (valoreIndicizzato == null) {
	    if (other.valoreIndicizzato != null)
		return false;
	} else if (!valoreIndicizzato.equals(other.valoreIndicizzato))
	    return false;
	if (valoreScalare == null) {
	    if (other.valoreScalare != null)
		return false;
	} else if (!valoreScalare.equals(other.valoreScalare))
	    return false;
	if (!Arrays.equals(valoreVettoriale, other.valoreVettoriale))
	    return false;
	return true;
    }

    public IndiceIdSemantico indexOfValue(String serchForValue) {
	IndiceIdSemantico index = new IndiceIdSemantico();
	return _indexOfValue(serchForValue, index);
    }

    IndiceIdSemantico _indexOfValue(String serchForValue, IndiceIdSemantico index) {

	if (isScalare()) {
	    if (getValoreScalare().equals(serchForValue)) {
		return index;
	    } else {
		return null;
	    }
	} else if (isVettoriale()) {
	    String[] vals = getValoreVettoriale();
	    boolean found = false;
	    for (int i = 0; i < vals.length; i++) {
		if (vals[i].equals(serchForValue)) {
		    found = true;
		    break;
		}
	    }
	    if (found) {
		return index;
	    } else {
		return null;
	    }
	} else if (isIndicizzato()) {
	    List<ValoreIdSemantico> indexedValues = getValoreIndicizzato();
	    index.incrementaLivello(new Object());
	    for (Iterator<ValoreIdSemantico> iterator = indexedValues.iterator(); iterator.hasNext();) {
		ValoreIdSemantico valoreIdSemantico = (ValoreIdSemantico) iterator.next();
		IndiceIdSemantico foundIndex = valoreIdSemantico._indexOfValue(serchForValue, index);
		if (foundIndex != null) {
		    return foundIndex;
		}
		index.incrementaIndice();
	    }
	    index.decrementaLivello();
	    return null;
	} else {
	    return null;
	}
    }
}