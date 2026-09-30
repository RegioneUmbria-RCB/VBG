package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggiornaAvvisoRespWrapper", propOrder = { "aggiornaAvvisoResp" })
public class AggiornaAvvisoRespWrapper {

    @XmlElement(name = "AggiornaAvvisoResp")
    private AggiornaAvvisoResp aggiornaAvvisoResp;

    public AggiornaAvvisoResp getAggiornaAvvisoResp() {

	return aggiornaAvvisoResp;
    }

    public void setAggiornaAvvisoResp(AggiornaAvvisoResp aggiornaAvvisoResp) {

	this.aggiornaAvvisoResp = aggiornaAvvisoResp;
    }
}
