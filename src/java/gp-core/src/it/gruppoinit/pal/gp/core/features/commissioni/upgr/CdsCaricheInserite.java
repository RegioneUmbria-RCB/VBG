package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

public class CdsCaricheInserite {

    private Integer codice;
    private String idcomune;
    private String software;

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public CdsCaricheInserite(Integer codice, String idcomune, String software) {

	this.codice = codice;
	this.idcomune = idcomune;
	this.software = software;
    }
}
