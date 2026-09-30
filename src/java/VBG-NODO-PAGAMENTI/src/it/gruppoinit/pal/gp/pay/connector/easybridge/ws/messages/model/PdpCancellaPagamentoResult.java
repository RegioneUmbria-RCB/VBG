package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpCancellaPagamentoResult", namespace = "http://easybridge.eu/bridge/")
public class PdpCancellaPagamentoResult extends EsitoOperazione {
}
