package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecuperaParametriByIdResponse {

    @XmlElement(name = "parametri")
    private Set<ConfigurazioneParametro> parametri;

    public Set<ConfigurazioneParametro> getParametri() {

	if (this.parametri == null) {
	    this.parametri = new TreeSet<ConfigurazioneParametro>(new ConfigurazioneParametroComparator());
	}
	return parametri;
    }
}
