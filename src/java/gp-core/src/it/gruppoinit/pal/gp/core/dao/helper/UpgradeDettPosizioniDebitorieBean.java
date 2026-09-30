package it.gruppoinit.pal.gp.core.dao.helper;

import java.math.BigDecimal;
import java.util.Date;

public class UpgradeDettPosizioniDebitorieBean {

    private String idcomune;
    private Integer id;
    private Integer idposizionedebitoria;
    private Date dataultimostato;
    private String iuv;
    private String codiceavviso;
    private String qrcode;
    private BigDecimal importo;
    private Date dataregistrazione;
    private String descrizionecausale;
    private Integer codiceanagrafe;
    private String cfEnteCreditore;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getIdposizionedebitoria() {

	return idposizionedebitoria;
    }

    public void setIdposizionedebitoria(Integer idposizionedebitoria) {

	this.idposizionedebitoria = idposizionedebitoria;
    }

    public Date getDataultimostato() {

	return dataultimostato;
    }

    public void setDataultimostato(Date dataultimostato) {

	this.dataultimostato = dataultimostato;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCodiceavviso() {

	return codiceavviso;
    }

    public void setCodiceavviso(String codiceavviso) {

	this.codiceavviso = codiceavviso;
    }

    public String getQrcode() {

	return qrcode;
    }

    public void setQrcode(String qrcode) {

	this.qrcode = qrcode;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public Date getDataregistrazione() {

	return dataregistrazione;
    }

    public void setDataregistrazione(Date dataregistrazione) {

	this.dataregistrazione = dataregistrazione;
    }

    public String getDescrizionecausale() {

	return descrizionecausale;
    }

    public void setDescrizionecausale(String descrizionecausale) {

	this.descrizionecausale = descrizionecausale;
    }

    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    public String getCfEnteCreditore() {

	return cfEnteCreditore;
    }

    public void setCfEnteCreditore(String cfEnteCreditore) {

	this.cfEnteCreditore = cfEnteCreditore;
    }
}
