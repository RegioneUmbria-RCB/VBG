package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnullaAvvisoRespWrapper", propOrder = { "annullaAvvisoResp" })
public class AnnullaAvvisoRespWrapper {

    @XmlElement(name = "AnnullaAvvisoResp")
    private AnnullaAvvisoResp annullaAvvisoResp;

    public AnnullaAvvisoResp getAnnullaAvvisoResp() {

	return annullaAvvisoResp;
    }

    public void setAnnullaAvvisoResp(AnnullaAvvisoResp annullaAvvisoResp) {

	this.annullaAvvisoResp = annullaAvvisoResp;
    }
}
