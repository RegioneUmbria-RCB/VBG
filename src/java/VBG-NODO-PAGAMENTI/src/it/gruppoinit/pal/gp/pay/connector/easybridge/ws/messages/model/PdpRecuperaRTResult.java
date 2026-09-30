package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpRecuperaRTResult", namespace = "http://easybridge.eu/bridge/")
public class PdpRecuperaRTResult extends EsitoOperazione {

    @XmlElement(name = "datiRestituiti", required = false)
    private PdpRecuperaRTDatiRestituiti datiRestituiti;

    public PdpRecuperaRTDatiRestituiti getDatiRestituiti() {

	return datiRestituiti;
    }

    public void setDatiRestituiti(PdpRecuperaRTDatiRestituiti datiRestituiti) {

	this.datiRestituiti = datiRestituiti;
    }
}
