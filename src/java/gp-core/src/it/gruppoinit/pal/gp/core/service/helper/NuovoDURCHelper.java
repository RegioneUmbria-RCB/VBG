package it.gruppoinit.pal.gp.core.service.helper;

import it.cassaedileweb.serviziodurc.insert.in.DURCInsertRequest;

public class NuovoDURCHelper {

    private Integer codiceAnagrafe;
    private Integer codiceIstanza;
    private DURCInsertRequest durc;
    private boolean validato = false;
    private String messaggioValidazione;

    public NuovoDURCHelper() {

	durc = new DURCInsertRequest();
    }

    public String getMessaggioValidazione() {

	return messaggioValidazione;
    }

    public void setMessaggioValidazione(String messaggioValidazione) {

	this.messaggioValidazione = messaggioValidazione;
    }

    public DURCInsertRequest getDurc() {

	return durc;
    }

    public void setDurc(DURCInsertRequest durc) {

	this.durc = durc;
    }

    public boolean isValidato() {

	return validato;
    }

    public void setValidato(boolean validato) {

	this.validato = validato;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }
}
