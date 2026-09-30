package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "configurazione")
public class ConfigurazioneWs {

    @JsonProperty("params")
    private List<ConfigurazioneParametroWs> parametri;

    public List<ConfigurazioneParametroWs> getParametri() {

	if (this.parametri == null) {
	    this.parametri = new ArrayList<ConfigurazioneParametroWs>();
	}
	return parametri;
    }

    public void setParametri(List<ConfigurazioneParametroWs> parametri) {

	this.parametri = parametri;
    }
}
