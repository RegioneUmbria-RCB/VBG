package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

public class AbbonamentoTabellaModelCompleta extends AbbonamentoTabellaModel {

    private String cognome;
    private String nome;
    private String tipoanagrafe;
    private String cf;
    private String piva;
    private String formagiuridica;

    public AbbonamentoTabellaModelCompleta() {

	super();
    }

    public String getCognome() {

	return cognome;
    }

    public void setCognome(String cognome) {

	this.cognome = cognome;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getTipoanagrafe() {

	return tipoanagrafe;
    }

    public void setTipoanagrafe(String tipoanagrafe) {

	this.tipoanagrafe = tipoanagrafe;
    }

    public String getCf() {

	return cf;
    }

    public void setCf(String cf) {

	this.cf = cf;
    }

    public String getPiva() {

	return piva;
    }

    public void setPiva(String piva) {

	this.piva = piva;
    }

    public String getFormagiuridica() {

	return formagiuridica;
    }

    public void setFormagiuridica(String formagiuridica) {

	this.formagiuridica = formagiuridica;
    }
}
