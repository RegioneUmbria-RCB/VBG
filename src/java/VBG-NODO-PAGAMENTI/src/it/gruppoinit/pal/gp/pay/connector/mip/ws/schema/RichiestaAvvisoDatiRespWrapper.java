package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiediAvvisoDatiWrapper", propOrder = { "richiediAvvisoDatiResp" })
public class RichiestaAvvisoDatiRespWrapper {

    @XmlElement(name = "RichiediAvvisoResp")
    private RichiediAvvisoResp richiediAvvisoDatiResp;

    public RichiediAvvisoResp getRichiediAvvisoDati() {

	return richiediAvvisoDatiResp;
    }

    public void setRichiediAvvisoDati(RichiediAvvisoResp richiediAvvisoDatiResp) {

	this.richiediAvvisoDatiResp = richiediAvvisoDatiResp;
    }
}
