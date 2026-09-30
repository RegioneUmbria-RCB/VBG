package it.gruppoinit.pal.gp.core.domain.web;

public class MovimentiFilter {

    private String numeroistanza;
    private String tipomovimento;
    private String richiedentenominativo;
    private String responsabiledescrizione;

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    public String getRichiedentenominativo() {

	return richiedentenominativo;
    }

    public void setRichiedentenominativo(String richiedentenominativo) {

	this.richiedentenominativo = richiedentenominativo;
    }

    public String getResponsabiledescrizione() {

	return responsabiledescrizione;
    }

    public void setResponsabiledescrizione(String responsabiledescrizione) {

	this.responsabiledescrizione = responsabiledescrizione;
    }
}
