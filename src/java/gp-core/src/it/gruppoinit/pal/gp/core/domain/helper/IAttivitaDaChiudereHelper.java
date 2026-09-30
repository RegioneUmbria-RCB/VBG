package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

public class IAttivitaDaChiudereHelper {

    private String idcomune;
    private Integer codiceattivita;
    private String attivita;
    private String software;
    private Date datafine;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceattivita() {

	return codiceattivita;
    }

    public void setCodiceattivita(Integer codiceattivita) {

	this.codiceattivita = codiceattivita;
    }

    public String getAttivita() {

	return attivita;
    }

    public void setAttivita(String attivita) {

	this.attivita = attivita;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Date getDatafine() {

	return datafine;
    }

    public void setDatafine(Date datafine) {

	this.datafine = datafine;
    }
}
