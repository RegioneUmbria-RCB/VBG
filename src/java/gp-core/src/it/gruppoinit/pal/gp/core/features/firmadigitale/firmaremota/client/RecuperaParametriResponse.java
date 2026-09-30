package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ConfigurazioneParametro;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ConfigurazioneParametroComparator;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecuperaParametriResponse {

    @XmlElement(name = "parametri")
    private Set<ConfigurazioneParametro> parametri;

    public Set<ConfigurazioneParametro> getParametri() {

	if (this.parametri == null) {
	    this.parametri = new TreeSet<ConfigurazioneParametro>(new ConfigurazioneParametroComparator());
	}
	return parametri;
    }

    public void setParametri(TreeSet<ConfigurazioneParametro> parametri) {

	this.parametri = parametri;
    }

    public static RecuperaParametriResponse fromConfigurazioneResponse(ConfigurazioneWsResponse response) {

	RecuperaParametriResponse retVal = new RecuperaParametriResponse();
	int i = 0;
	for (ConfigurazioneParametroWs parametro : response.getParametri()) {
	    ConfigurazioneParametro par = ConfigurazioneParametro.fromConfigurazioneParametroWs(parametro);
	    par.setOrdine(i);
	    retVal.getParametri().add(par);
	    i++;
	}
	return retVal;
    }
}
