package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProfiloWrapper {

    private Profilo profilo;

    public Profilo getProfilo() {

	return profilo;
    }

    public void setProfilo(Profilo profilo) {

	this.profilo = profilo;
    }
}
