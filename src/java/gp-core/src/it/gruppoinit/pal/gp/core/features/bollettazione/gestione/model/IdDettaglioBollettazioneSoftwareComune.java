package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

/**
 * @author riccardob
 *
 */
public class IdDettaglioBollettazioneSoftwareComune {

    private Integer id;
    private String codiceComune;
    private String software;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
