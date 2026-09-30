package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

public class WsExportBean {

    private String idcomune;
    private Integer codice;
    private Date data;
    private String codicecomune;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }
}
