package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportoContabile", propOrder = { "identificativo", "valore" })
@XmlRootElement(name = "ImportoContabile")
public class ImportoContabile {

    @XmlElement(name = "Identificativo")
    private String identificativo;
    @XmlElement(name = "Valore")
    private Integer valore;

    public String getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(String identificativo) {

	this.identificativo = identificativo;
    }

    public Integer getValore() {

	return valore;
    }

    public void setValore(Integer valore) {

	this.valore = valore;
    }
}
