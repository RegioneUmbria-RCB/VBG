package it.gruppoinit.service.helper;

public class CodiceDescrizioneBean {

    private String codice;
    private String descrizione;

    public CodiceDescrizioneBean(String codice, String descrizione) {

	this.codice = codice;
	this.descrizione = descrizione;
    }

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
