package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.NuovoProcessoResponse;

@XmlRootElement(name = "response")
public class AvviaProcessoResponse {

    @XmlElement(name = "sessionid")
    private String sessionId;

    public String getSessionId() {

	return sessionId;
    }

    public void setSessionId(String sessionId) {

	this.sessionId = sessionId;
    }

    public static AvviaProcessoResponse fromNuovoProcessoResponse(NuovoProcessoResponse nuovoProcesso) {

	AvviaProcessoResponse response = new AvviaProcessoResponse();
	response.setSessionId(nuovoProcesso.getSessionId());
	return response;
    }
}
