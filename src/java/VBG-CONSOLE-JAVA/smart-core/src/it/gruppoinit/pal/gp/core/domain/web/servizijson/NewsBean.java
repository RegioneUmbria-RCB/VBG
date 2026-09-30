package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class NewsBean {

    private String id;
    private String data;
    private String titolo;
    private String sottotitolo;
    private String corpo;
    private String codiceoggetto;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public String getSottotitolo() {

	return sottotitolo;
    }

    public void setSottotitolo(String sottotitolo) {

	this.sottotitolo = sottotitolo;
    }

    public String getCorpo() {

	return corpo;
    }

    public void setCorpo(String corpo) {

	this.corpo = corpo;
    }

    public String getCodiceoggetto() {

	return codiceoggetto;
    }

    public void setCodiceoggetto(String codiceoggetto) {

	this.codiceoggetto = codiceoggetto;
    }
}
