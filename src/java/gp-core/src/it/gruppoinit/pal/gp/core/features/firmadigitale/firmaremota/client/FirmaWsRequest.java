package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class FirmaWsRequest {

    @JsonProperty("configurazione")
    private ConfigurazioneWs configurazione;
    @JsonProperty("documenti")
    private List<String> guidDocumenti;

    public FirmaWsRequest(ConfigurazioneWs configurazione, List<String> guidDocumenti) {

	this.configurazione = configurazione;
	this.guidDocumenti = guidDocumenti;
    }

    public ConfigurazioneWs getConfigurazione() {

	return configurazione;
    }

    public void setConfigurazione(ConfigurazioneWs configurazione) {

	this.configurazione = configurazione;
    }

    public List<String> getGuidDocumenti() {

	return guidDocumenti;
    }

    public void setGuidDocumenti(List<String> guidDocumenti) {

	this.guidDocumenti = guidDocumenti;
    }
}
