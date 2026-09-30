package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

import org.apache.commons.lang.StringUtils;

public class FiltriRicercaTestataBase {

    private Integer idTestataMassiva;
    private String nomeFiltroEscludiDestinatariSenzaMail;
    private String nomeFiltroSceltaMailAnagrafe;
    private String nomeFiltroConvertiInPDF;

    public FiltriRicercaTestataBase(Integer idTestataMassiva, String nomeFiltroEscludiDestinatariSenzaMail, String nomeFiltroSceltaMailAnagrafe,
	    String nomeFiltroConvertiInPDF) {

	super();
	if (idTestataMassiva == null) {
	    throw new IllegalArgumentException("Impossibile istanziare la classe FiltriRicercaTestata senza passare il parametro idTestataMassiva");
	}
	if (StringUtils.isBlank(nomeFiltroEscludiDestinatariSenzaMail)) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare la classe FiltriRicercaTestata senza passare il parametro nomeFiltroEscludiDestinatariSenzaMail");
	}
	if (StringUtils.isBlank(nomeFiltroSceltaMailAnagrafe)) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare la classe FiltriRicercaTestata senza passare il parametro nomeFiltroSceltaMailAnagrafe");
	}
	if (StringUtils.isBlank(nomeFiltroConvertiInPDF)) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare la classe FiltriRicercaTestata senza passare il parametro nomeFiltroConvertiInPDF");
	}
	this.idTestataMassiva = idTestataMassiva;
	this.nomeFiltroEscludiDestinatariSenzaMail = nomeFiltroEscludiDestinatariSenzaMail;
	this.nomeFiltroSceltaMailAnagrafe = nomeFiltroSceltaMailAnagrafe;
	this.nomeFiltroConvertiInPDF = nomeFiltroConvertiInPDF;
    }

    public Integer getIdTestataMassiva() {

	return idTestataMassiva;
    }

    public String getNomeFiltroEscludiDestinatariSenzaMail() {

	return nomeFiltroEscludiDestinatariSenzaMail;
    }

    public String getNomeFiltroSceltaMailAnagrafe() {

	return nomeFiltroSceltaMailAnagrafe;
    }

    public String getNomeFiltroConvertiInPDF() {

	return nomeFiltroConvertiInPDF;
    }
}
