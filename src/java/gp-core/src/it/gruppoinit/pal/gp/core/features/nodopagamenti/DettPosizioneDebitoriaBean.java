package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.util.Date;

public class DettPosizioneDebitoriaBean {

    private Integer id;
    private String stato;
    private String descStato;
    private Date dataEvento;
    private String iuv;
    private String codiceAvviso;
    private String qrCode;

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

    public String getDescStato() {

	return descStato;
    }

    public void setDescStato(String descStato) {

	this.descStato = descStato;
    }

    public Date getDataEvento() {

	return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {

	this.dataEvento = dataEvento;
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

    public String getQrCode() {

	return qrCode;
    }

    public void setQrCode(String qrCode) {

	this.qrCode = qrCode;
    }
}
