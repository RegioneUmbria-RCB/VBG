package it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2;

public class ScadenzaIstanza {

    private String comune;
    private String sportello;
    private String numeroIstanza;
    private String richiedente;
    private String inQualitaDi;
    private String azienda;
    private MovimentoPrecedente movimentoPrecedente;
    private MovimentoDaEffettuare movimentoDaEffettuare;

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getSportello() {

	return sportello;
    }

    public void setSportello(String sportello) {

	this.sportello = sportello;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public String getInQualitaDi() {

	return inQualitaDi;
    }

    public void setInQualitaDi(String inQualitaDi) {

	this.inQualitaDi = inQualitaDi;
    }

    public String getAzienda() {

	return azienda;
    }

    public void setAzienda(String azienda) {

	this.azienda = azienda;
    }

    public MovimentoPrecedente getMovimentoPrecedente() {

	return movimentoPrecedente;
    }

    public void setMovimentoPrecedente(MovimentoPrecedente movimentoPrecedente) {

	this.movimentoPrecedente = movimentoPrecedente;
    }

    public MovimentoDaEffettuare getMovimentoDaEffettuare() {

	return movimentoDaEffettuare;
    }

    public void setMovimentoDaEffettuare(MovimentoDaEffettuare movimentoDaEffettuare) {

	this.movimentoDaEffettuare = movimentoDaEffettuare;
    }
}
