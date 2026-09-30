package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "enteDestinatario" })
@XmlAccessorType(XmlAccessType.FIELD)
public class EntiDestinatari {

    @XmlElement(required = true, name = "EnteDestinatario")
    private List<EnteDestinatario> enteDestinatario = new ArrayList<>();

    public List<EnteDestinatario> getEnteDestinatario() {

	return enteDestinatario;
    }

    public void setEnteDestinatario(List<EnteDestinatario> enteDestinatario) {

	this.enteDestinatario = enteDestinatario;
    }
}
