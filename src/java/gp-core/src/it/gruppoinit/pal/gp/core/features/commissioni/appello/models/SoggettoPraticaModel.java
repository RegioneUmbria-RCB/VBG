package it.gruppoinit.pal.gp.core.features.commissioni.appello.models;

public class SoggettoPraticaModel {

    private Integer codiceAnagrafe;
    private boolean selezionato;
    private Integer codiceCarica;

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public boolean isSelezionato() {

	return selezionato;
    }

    public void setSelezionato(boolean selezionato) {

	this.selezionato = selezionato;
    }

    public Integer getCodiceCarica() {

	return codiceCarica;
    }

    public void setCodiceCarica(Integer codiceCarica) {

	this.codiceCarica = codiceCarica;
    }
}
