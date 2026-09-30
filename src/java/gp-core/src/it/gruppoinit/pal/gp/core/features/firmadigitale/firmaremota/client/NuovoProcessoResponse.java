package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "response")
public class NuovoProcessoResponse {

    @JsonProperty("sessionid")
    private String sessionId;

    public String getSessionId() {

	return sessionId;
    }

    public void setSessionId(String sessionId) {

	this.sessionId = sessionId;
    }
}
