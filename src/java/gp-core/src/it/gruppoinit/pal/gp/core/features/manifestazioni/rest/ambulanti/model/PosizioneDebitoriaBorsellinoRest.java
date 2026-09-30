package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class PosizioneDebitoriaBorsellinoRest {

    @XmlElement(name = "id_posizione_debitoria")
    private Integer idPosizioneDebitoria;
    @XmlElement(name = "pagabile")
    private Boolean pagabile;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codice_avviso")
    private String codiceAvviso;
    @XmlElement(name = "qrcode")
    private String qrcode;
    @XmlElement(name = "idBollettino")
    private String idBollettino;
    @XmlElement(name = "idRicevuta")
    private String idRicevuta;
    @XmlElement(name = "importo")
    private double importo;
    @XmlElement(name = "pagata")
    private boolean pagata;
    private boolean annullata;

    public PosizioneDebitoriaBorsellinoRest() {

	super();
    }

    public Integer getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public void setIdPosizioneDebitoria(Integer idPosizioneDebitoria) {

	this.idPosizioneDebitoria = idPosizioneDebitoria;
    }

    public Boolean getPagabile() {

	return pagabile;
    }

    public void setPagabile(Boolean pagabile) {

	this.pagabile = pagabile;
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

    public String getIdBollettino() {

	return idBollettino;
    }

    public void setIdBollettino(String idBollettino) {

	this.idBollettino = idBollettino;
    }

    public String getIdRicevuta() {

	return idRicevuta;
    }

    public void setIdRicevuta(String idRicevuta) {

	this.idRicevuta = idRicevuta;
    }

    public double getImporto() {

	return importo;
    }

    public void setImporto(double importo) {

	this.importo = importo;
    }

    public boolean isPagata() {

	return pagata;
    }

    public void setPagata(boolean pagata) {

	this.pagata = pagata;
    }

    @XmlTransient
    public boolean isAnnullata() {

	return annullata;
    }

    public void setAnnullata(boolean annullata) {

	this.annullata = annullata;
    }
}
