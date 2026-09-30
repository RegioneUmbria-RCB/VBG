package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;

public class IstanzeDaChiudereHelper implements Serializable {

    private static final long serialVersionUID = 2250431288178249719L;
    private String idcomune;
    private Integer codiceIstanza;
    private String numeroistanza;
    private String software;
    private String statochiusura;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getStatochiusura() {

	return statochiusura;
    }

    public void setStatochiusura(String statochiusura) {

	this.statochiusura = statochiusura;
    }
}
