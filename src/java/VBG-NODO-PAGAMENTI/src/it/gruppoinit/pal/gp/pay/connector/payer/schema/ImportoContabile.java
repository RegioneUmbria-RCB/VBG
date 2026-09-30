package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "identificativo", "valore" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportoContabile {

    @XmlElement(name = "Identificativo")
    private IdentificativoImporto identificativo;
    @XmlElement(required = true, name = "Valore")
    private Integer valore;

    public IdentificativoImporto getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(IdentificativoImporto identificativo) {

	this.identificativo = identificativo;
    }

    public Integer getValore() {

	return valore;
    }

    public void setValore(Integer valore) {

	this.valore = valore;
    }
}
