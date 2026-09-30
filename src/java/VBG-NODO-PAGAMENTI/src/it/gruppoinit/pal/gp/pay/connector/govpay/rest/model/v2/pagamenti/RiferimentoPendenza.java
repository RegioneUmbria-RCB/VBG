package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoPendenza", propOrder = { "idA2A", "idPendenza" })
public class RiferimentoPendenza {

    @XmlElement(name = "idA2A")
    private String idA2A;
    @XmlElement(name = "idPendenza")
    private String idPendenza;

    public String getIdA2A() {

	return idA2A;
    }

    public void setIdA2A(String idA2A) {

	this.idA2A = idA2A;
    }

    public String getIdPendenza() {

	return idPendenza;
    }

    public void setIdPendenza(String idPendenza) {

	this.idPendenza = idPendenza;
    }
}
