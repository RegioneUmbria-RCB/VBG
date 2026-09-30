package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Iuv", propOrder = { "iuv", "codiceAvviso", "data", "idRichiesta", "posizione" })
public class Iuv {

    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codice_avviso")
    private String codiceAvviso;
    @XmlElement(name = "data")
    private String data;
    @XmlElement(name = "id_richiesta")
    private Integer idRichiesta;
    @XmlElement(name = "posizione")
    private Integer posizione;

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public Integer getIdRichiesta() {

	return idRichiesta;
    }

    public void setIdRichiesta(Integer idRichiesta) {

	this.idRichiesta = idRichiesta;
    }

    public Integer getPosizione() {

	return posizione;
    }

    public void setPosizione(Integer posizione) {

	this.posizione = posizione;
    }
}
