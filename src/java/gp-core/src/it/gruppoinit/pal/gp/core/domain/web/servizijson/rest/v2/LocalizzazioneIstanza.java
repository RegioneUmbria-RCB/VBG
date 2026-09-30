package it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2;

public class LocalizzazioneIstanza {

    private String indirizzo;
    private String comune;
    private String nazione;
    private String datiCatastali;

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getNazione() {

	return nazione;
    }

    public void setNazione(String nazione) {

	this.nazione = nazione;
    }

    public String getDatiCatastali() {

	return datiCatastali;
    }

    public void setDatiCatastali(String datiCatastali) {

	this.datiCatastali = datiCatastali;
    }
}
