package it.gruppoinit.pal.gp.pay.features.interfaccia;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "campi_gestiti")
public class CampiGestitiBean {

    @XmlElement(name = "pay_connector_config")
    private PayConnectorConfigBean payConnectorConfig;
    @XmlElement(name = "pay_profili_enti_creditori")
    private PayProfiliEntiCreditoriBean payProfiliEntiCreditori;

    public CampiGestitiBean() {

	// costruttore
    }

    public PayConnectorConfigBean getPayConnectorConfig() {

	return payConnectorConfig;
    }

    public void setPayConnectorConfig(PayConnectorConfigBean value) {

	this.payConnectorConfig = value;
    }

    public PayProfiliEntiCreditoriBean getPayProfiliEntiCreditori() {

	return payProfiliEntiCreditori;
    }

    public void setPayProfiliEntiCreditori(PayProfiliEntiCreditoriBean value) {

	this.payProfiliEntiCreditori = value;
    }
}
