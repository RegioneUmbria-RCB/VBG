package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.util.Date;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class NumeroDataProtocolloZipLogico {

    public NumeroDataProtocolloZipLogico() {

	super();
    }

    public NumeroDataProtocolloZipLogico(String numeroProtocollo, Date dataProtocollo) {

	this();
	this.numeroProtocollo = numeroProtocollo;
	this.dataProtocollo = dataProtocollo;
    }

    private String numeroProtocollo;
    private Date dataProtocollo;

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public String getDataProtocolloFormattata() {

	if (this.dataProtocollo == null) {
	    return "";
	}
	return Utilities.formatDate(this.dataProtocollo, false);
    }
}
