package it.gruppoinit.pal.gp.core.features.movimenti.metadati;

public abstract class AbstractMovimentiMetadato implements IMovimentoMetadato {

    private String idComune;
    private Integer codiceMovimento;
    private String valore;

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public abstract String getChiave();

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
