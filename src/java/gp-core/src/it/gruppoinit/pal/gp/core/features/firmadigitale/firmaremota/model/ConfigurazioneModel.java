package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ConfigurazioneModel {

    @XmlElement(name = "parametri")
    private List<ConfigurazioneParametro> parametri;

    public List<ConfigurazioneParametro> getParametri() {

	return parametri;
    }

    public void setParametri(List<ConfigurazioneParametro> parametri) {

	this.parametri = parametri;
    }
}
