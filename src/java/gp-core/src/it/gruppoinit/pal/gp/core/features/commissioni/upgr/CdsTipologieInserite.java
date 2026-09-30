package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

public class CdsTipologieInserite {

    private Integer codicetipologia;
    private String idcomune;
    private String software;

    public CdsTipologieInserite(Integer codicetipologia, String idcomune, String software) {

	this.codicetipologia = codicetipologia;
	this.idcomune = idcomune;
	this.software = software;
    }

    public Integer getCodicetipologia() {

	return codicetipologia;
    }

    public void setCodicetipologia(Integer codicetipologia) {

	this.codicetipologia = codicetipologia;
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
}
