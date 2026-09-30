package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpPaymentNoticeResult", namespace = "http://easybridge.eu/bridge/")
public class PdpPaymentNoticeResult extends EsitoOperazione {

    @XmlElement(name = "datiRestituiti", required = false)
    private PdpPaymentNoticeDatiRestituiti datiRestituiti;

    public PdpPaymentNoticeDatiRestituiti getDatiRestituiti() {

	return datiRestituiti;
    }

    public void setDatiRestituiti(PdpPaymentNoticeDatiRestituiti datiRestituiti) {

	this.datiRestituiti = datiRestituiti;
    }
}
