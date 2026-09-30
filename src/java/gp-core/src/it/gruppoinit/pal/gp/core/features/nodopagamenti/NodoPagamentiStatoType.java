package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class NodoPagamentiStatoType {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "data")
    private Date data;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codiceaavviso")
    private String codiceAvviso;
    @XmlElement(name = "qrcode")
    private String qrcode;
    @XmlElement(name = "descrizionecausale")
    private String descrizioneCausale;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

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

    public String getQrcode() {

	return qrcode;
    }

    public void setQrcode(String qrcode) {

	this.qrcode = qrcode;
    }

    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    public void setDescrizioneCausale(String descrizioneCausale) {

	this.descrizioneCausale = descrizioneCausale;
    }
}
