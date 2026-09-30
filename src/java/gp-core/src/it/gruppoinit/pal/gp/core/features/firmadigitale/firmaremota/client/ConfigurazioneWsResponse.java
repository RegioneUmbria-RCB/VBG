package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import java.util.List;

public class ConfigurazioneWsResponse {

    private List<ConfigurazioneParametroWs> parametri;

    public List<ConfigurazioneParametroWs> getParametri() {

	return parametri;
    }

    public void setParametri(List<ConfigurazioneParametroWs> parametri) {

	this.parametri = parametri;
    }
}
