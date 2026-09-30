package it.gruppoinit.pal.gp.core.domain.helper;

import it.eng.suap.xengine.model.modulistica.ModuloType;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ModulisticaSTAR")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModulisticaSTAR", propOrder = { "moduliModelli", "moduli" })
public class ModulisticaSTAR {

    @XmlElementWrapper(name = "moduliModelli", required = false)
    private LinkedHashMap<String, ModellidinamiciHelperListWrapper> moduliModelli = new LinkedHashMap<String, ModellidinamiciHelperListWrapper>();
    /*
    @XmlElementWrapper(name = "modelli186", required = false)
    private LinkedHashMap<String, Modulo186ListWrapper> modelli186 = new LinkedHashMap<String, Modulo186ListWrapper>();
    */
    @XmlElementWrapper(name = "moduli", required = false)
    private LinkedHashMap<String, ModuloType> moduli = new LinkedHashMap<String, ModuloType>();

    public LinkedHashMap<String, ModellidinamiciHelperListWrapper> getModuliModelli() {

	return moduliModelli;
    }

    /*
    public LinkedHashMap<String, Modulo186ListWrapper> getModelli186() {

    return modelli186;
    }

    public void setModelli186(LinkedHashMap<String, Modulo186ListWrapper> modelli186) {

    this.modelli186 = modelli186;
    }
    */
    public LinkedHashMap<String, ModuloType> getModuli() {

	return moduli;
    }

    public void setModuli(LinkedHashMap<String, ModuloType> moduli) {

	this.moduli = moduli;
    }

    public Set<String> getRiferimentiModulo() {

	Set<String> result = new HashSet<String>();
	/*
	if (modelli186 != null) {
	    for (Entry<String, Modulo186ListWrapper> q186 : modelli186.entrySet()) {
		result.add(q186.getKey());
	    }
	}
	*/
	if (moduliModelli != null) {
	    for (Entry<String, ModellidinamiciHelperListWrapper> mm : moduliModelli.entrySet()) {
		result.add(mm.getKey());
	    }
	}
	if (moduli != null) {
	    for (Entry<String, ModuloType> mt : moduli.entrySet()) {
		result.add(mt.getKey());
	    }
	}
	return result;
    }
}
