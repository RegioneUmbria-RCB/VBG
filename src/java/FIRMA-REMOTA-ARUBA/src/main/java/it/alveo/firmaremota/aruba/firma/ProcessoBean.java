package it.alveo.firmaremota.aruba.firma;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.alveo.firmaremota.aruba.configurazione.ConfigurazioneBean;

public class ProcessoBean {

    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("stato")
    private String stato;
    @JsonProperty("configurazione")
    private ConfigurazioneBean configurazione;
    @JsonProperty("documenti")
    private List<DocumentoBean> documenti;

    public ProcessoBean() {

    }

    public String getSessionId() {

	return sessionId;
    }

    public void setSessionId(String sessionId) {

	this.sessionId = sessionId;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public ConfigurazioneBean getConfigurazione() {

	return configurazione;
    }

    public void setConfigurazione(ConfigurazioneBean configurazione) {

	this.configurazione = configurazione;
    }

    public List<DocumentoBean> getDocumenti() {

	if (this.documenti == null) {
	    this.documenti = new ArrayList<>();
	}
	return this.documenti;
    }

    public void setDocumenti(List<DocumentoBean> documenti) {

	this.documenti = documenti;
    }
}