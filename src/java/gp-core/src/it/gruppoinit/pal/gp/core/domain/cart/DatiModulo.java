package it.gruppoinit.pal.gp.core.domain.cart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DatiModulo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiModulo", propOrder = { "idModulo", "valoreModulo" })
public class DatiModulo implements Comparable<DatiModulo> {

    @XmlElement(name = "valoreModulo", required = true)
    //private Map<String, ValoreIdSemantico> mappaValori = new LinkedHashMap<String, ValoreIdSemantico>();
    private ArrayList<MappingIdSemantico> valoreModulo = new ArrayList<MappingIdSemantico>();
    @XmlAttribute(name = "idModulo", required = true)
    private String idModulo = "";
    @XmlTransient
    private SortedSet<String> elencoIdSemantici;

    public DatiModulo() {

    }

    public DatiModulo(String idModulo) {

	if (null == idModulo) {
	    idModulo = "";
	}
	this.idModulo = idModulo;
    }

    public String getIdModulo() {

	return this.idModulo;
    }

    public void setValoreScalare(String idSemantico, String dato) {

	// §§§BEGIN§§§
	ValoreIdSemantico valore = getValoreIdSemantico(idSemantico);
	if (valore == null) {
	    valore = new ValoreIdSemantico(dato);
	    MappingIdSemantico mapping = new MappingIdSemantico(idSemantico, valore);
	    this.valoreModulo.add(mapping);
	    //Collections.so
	    this.elencoIdSemantici = null;
	} else {
	    if (!valore.isEmpty() && !valore.isScalare()) {
		String message = MessageFormat.format(
			"Impossibile associare il valore scalare {0} all''id semantico {1} perchè l''id semantico è associato a valori vettoriali.",
			dato, idSemantico);
		throw new RuntimeException(message);
	    } else {
		valore.setValoreScalare(dato);
	    }
	}
	// §§§END§§§
    }

    public String getValoreScalare(String idSemantico) {

	// §§§BEGIN§§§
	ValoreIdSemantico valore = getValoreIdSemantico(idSemantico);
	if (null != valore) {
	    if (!valore.isEmpty() && !valore.isScalare()) {
		String message = MessageFormat
			.format("Impossibile recuperare il valore scalare associato all''id semantico {0} perchè l''id semantico è associato a valori vettoriali.",
				idSemantico);
		throw new RuntimeException(message);
	    }
	    return valore.getValoreScalare();
	} else {
	    return null;
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public void setValoreVettoriale(String idSemantico, String[] dati) {

	// §§§BEGIN§§§
	ValoreIdSemantico valore = getValoreIdSemantico(idSemantico);
	if (valore == null) {
	    valore = new ValoreIdSemantico(dati);
	    MappingIdSemantico mapping = new MappingIdSemantico(idSemantico, valore);
	    this.valoreModulo.add(mapping);
	    this.elencoIdSemantici = null;
	} else {
	    if (!valore.isEmpty() && !valore.isVettoriale()) {
		StringBuilder sbValore = new StringBuilder("[");
		for (int i = 0; i < dati.length; i++) {
		    sbValore.append(dati[i]).append(",");
		}
		sbValore.deleteCharAt(sbValore.length() - 1);
		sbValore.append("]");
		String message = MessageFormat.format(
			"Impossibile associare il valore vettoriale {0} all''id semantico {1} perchè l''id semantico è associato a valori scalari.",
			sbValore, idSemantico);
		throw new RuntimeException(message);
	    } else {
		valore.setValoreVettoriale(dati);
	    }
	}
	// §§§END§§§
    }

    public String[] getValoreVettoriale(String idSemantico) {

	// §§§BEGIN§§§
	ValoreIdSemantico valore = getValoreIdSemantico(idSemantico);
	if (null != valore) {
	    if (!valore.isEmpty() && !valore.isVettoriale()) {
		String message = MessageFormat
			.format("Impossibile recuperare il valore vettoriale associato all''id semantico {0} perchè l''id semantico non è associato a valori vettoriali.",
				idSemantico);
		throw new RuntimeException(message);
	    }
	    return valore.getValoreVettoriale();
	} else {
	    return null;
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public List<ValoreIdSemantico> getValoriIndicizzati(String idSemantico) {

	// §§§BEGIN§§§
	ValoreIdSemantico valore = getValoreIdSemantico(idSemantico);
	if (null != valore) {
	    if (!valore.isEmpty() && !valore.isIndicizzato()) {
		String message = MessageFormat
			.format("Impossibile recuperare il valore indicizzato associato all''id semantico {0} perchè l''id semantico non è associato a valori indicizzati (non è contenuto in una tabella).",
				idSemantico);
		throw new RuntimeException(message);
	    }
	    return valore.getValoreIndicizzato();
	} else {
	    return null;
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public void setValoriIndicizzati(String idSemantico, List<ValoreIdSemantico> valori) {

	// §§§BEGIN§§§
	ValoreIdSemantico valore = getValoreIdSemantico(idSemantico);
	if (valore == null) {
	    valore = new ValoreIdSemantico(valori);
	    MappingIdSemantico mapping = new MappingIdSemantico(idSemantico, valore);
	    this.valoreModulo.add(mapping);
	    this.elencoIdSemantici = null;
	} else {
	    if (!valore.isEmpty() && !valore.isIndicizzato()) {
		String message = MessageFormat.format(
			"Impossibile associare un valore indicizzato all''id semantico {0} perchè l''id semantico non è contenuto in una tabella.",
			idSemantico);
		throw new RuntimeException(message);
	    } else {
		valore.setValoreIndicizzato(valori);
	    }
	}
	// §§§END§§§
    }

    public boolean hasValore(String idSemantico) {

	ValoreIdSemantico value = getValoreIdSemantico(idSemantico);
	if (null != value) {
	    return value.hasValue();
	} else {
	    return false;
	}
    }

    public boolean hasValoreIndicizzato(String idSemantico, IndiceIdSemantico index) {

	Integer[] indici = index.vettoreIndici();
	if (indici.length == 0) {
	    return hasValore(idSemantico);
	} else {
	    ValoreIdSemantico vis = getValoreIdSemantico(idSemantico);
	    for (int i = 0; i < indici.length; i++) {
		if (vis != null && vis.isIndicizzato()) {
		    List<ValoreIdSemantico> innerValues = vis.getValoreIndicizzato();
		    if (innerValues.size() > indici[i]) {
			vis = innerValues.get(indici[i]);
		    } else {
			return false;
		    }
		} else {
		    return false;
		}
	    }
	    return vis != null && vis.hasValue();
	}
    }

    public ValoreIdSemantico getValoreIdSemantico(String idSemantico) {

	ValoreIdSemantico valore = null;
	// §§§BEGIN§§§
	//int index = Collections.binarySearch(this.valoreModulo, new MappingIdSemantico(idSemantico, null));
	int index = this.valoreModulo.indexOf(new MappingIdSemantico(idSemantico, null));
	if (index > -1) {
	    MappingIdSemantico mapping = this.valoreModulo.get(index);
	    valore = mapping.getValore();
	}
	// §§§END§§§
	return valore;
    }

    public void setValoreIdSemantico(String idSemantico, ValoreIdSemantico valore) {

	int index = this.valoreModulo.indexOf(new MappingIdSemantico(idSemantico, null));
	if (index > -1) {
	    MappingIdSemantico mapping = this.valoreModulo.get(index);
	    mapping.setValore(valore);
	} else {
	    this.valoreModulo.add(new MappingIdSemantico(idSemantico, valore));
	    this.elencoIdSemantici = null;
	}
    }

    public ValoreIdSemantico removeIdSemantico(String idSemantico) {

	int index = this.valoreModulo.indexOf(new MappingIdSemantico(idSemantico, null));
	ValoreIdSemantico retVal = null;
	if (index > -1) {
	    MappingIdSemantico mapping = this.valoreModulo.remove(index);
	    retVal = mapping.getValore();
	}
	return retVal;
    }

    public void removeValoreIdSemanticoIndicizzato(String idSemantico, Integer indice) {

	int index = this.valoreModulo.indexOf(new MappingIdSemantico(idSemantico, null));
	if (index > -1) {
	    MappingIdSemantico mapping = this.valoreModulo.get(index);
	    ValoreIdSemantico s = mapping.getValore();
	    if (s.isIndicizzato()) {
		List<ValoreIdSemantico> vals = s.getValoreIndicizzato();
		for (int i = 0; i < vals.size(); i++) {
		    ValoreIdSemantico vis = vals.get(i);
		    if (i == indice) {
			vis.setValoreScalare("");
			break;
		    }
		}
		this.valoreModulo.set(index, mapping);
	    }
	}
    }

    public Set<String> getElencoIdSemantici() {

	// §§§BEGIN§§§
	if (this.elencoIdSemantici == null) {
	    elencoIdSemantici = new TreeSet<String>();
	    for (MappingIdSemantico mapping : this.valoreModulo) {
		elencoIdSemantici.add(mapping.getIdSemantico());
	    }
	}
	// §§§END§§§
	return this.elencoIdSemantici;
    }

    public List<MappingIdSemantico> getValoreModulo() {

	return this.valoreModulo;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DatiModulo o) {

	if (null == o) {
	    return -1;
	}
	return this.getIdModulo().compareTo(o.getIdModulo());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DatiModulo other = (DatiModulo) obj;
	if (idModulo == null) {
	    if (other.idModulo != null)
		return false;
	} else if (!idModulo.equals(other.idModulo))
	    return false;
	return true;
    }
}
