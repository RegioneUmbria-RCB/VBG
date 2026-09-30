package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

public class EstremiAutDTO {

    private String autCodiceComune;
    private String autNumero;
    private Date autData;
    private Integer autTipologiaRegistro;

    public String getAutCodiceComune() {

	return autCodiceComune;
    }

    public void setAutCodiceComune(String autCodiceComune) {

	this.autCodiceComune = autCodiceComune;
    }

    public String getAutNumero() {

	return autNumero;
    }

    public void setAutNumero(String autNumero) {

	this.autNumero = autNumero;
    }

    public Date getAutData() {

	return autData;
    }

    public void setAutData(Date autData) {

	this.autData = autData;
    }

    public Integer getAutTipologiaRegistro() {

	return autTipologiaRegistro;
    }

    public void setAutTipologiaRegistro(Integer autTipologiaRegistro) {

	this.autTipologiaRegistro = autTipologiaRegistro;
    }
}
