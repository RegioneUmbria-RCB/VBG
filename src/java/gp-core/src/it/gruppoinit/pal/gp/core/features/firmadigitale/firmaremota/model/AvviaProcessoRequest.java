package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvviaProcessoRequest {

    @XmlElement(name = "idConfigurazione")
    private Integer idConfigurazione;
    @XmlElement(name = "parametri")
    List<ConfigurazioneParametro> parametri;

    public Integer getIdConfigurazione() {

	return idConfigurazione;
    }

    public void setIdConfigurazione(Integer idConfigurazione) {

	this.idConfigurazione = idConfigurazione;
    }

    public List<ConfigurazioneParametro> getParametri() {

	if (this.parametri == null) {
	    this.parametri = new ArrayList<ConfigurazioneParametro>();
	}
	return parametri;
    }

    public void setParametri(List<ConfigurazioneParametro> parametri) {

	this.parametri = parametri;
    }
}
