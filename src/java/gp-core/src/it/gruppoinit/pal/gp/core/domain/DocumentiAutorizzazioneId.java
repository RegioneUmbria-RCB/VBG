package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import javax.persistence.Column;

public class DocumentiAutorizzazioneId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8687150636693029056L;
    private String idcomune;
    private Integer codiceoggetto;
    private Integer idautorizzazione;

    public DocumentiAutorizzazioneId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public DocumentiAutorizzazioneId(Integer codiceoggetto, Integer idautorizzazione) {

	this();
	this.codiceoggetto = codiceoggetto;
	this.idautorizzazione = idautorizzazione;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEOGGETTO", nullable = false, precision = 10, scale = 0)
    public Integer getCodiceoggetto() {

	return codiceoggetto;
    }

    public void setCodiceoggetto(Integer codiceoggetto) {

	this.codiceoggetto = codiceoggetto;
    }

    @Column(name = "ID_AUTORIZZAZIONE", nullable = false, precision = 6, scale = 0)
    public Integer getIdautorizzazione() {

	return idautorizzazione;
    }

    public void setIdautorizzazione(Integer idautorizzazione) {

	this.idautorizzazione = idautorizzazione;
    }

    /**
     * private Integer idoggetto; private Integer idautorizzazione;
     */
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof DocumentiAutorizzazioneId))
	    return false;
	DocumentiAutorizzazioneId castOther = (DocumentiAutorizzazioneId) other;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodiceoggetto() == castOther.getCodiceoggetto()) || (this.getCodiceoggetto() != null
			&& castOther.getCodiceoggetto() != null && this.getCodiceoggetto().equals(castOther.getCodiceoggetto())))
		&& ((this.getIdautorizzazione() == castOther.getIdautorizzazione()) || (this.getIdautorizzazione() != null
			&& castOther.getIdautorizzazione() != null && this.getIdautorizzazione().equals(castOther.getIdautorizzazione())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodiceoggetto() == null ? 0 : this.getCodiceoggetto().hashCode());
	result = 37 * result + (getIdautorizzazione() == null ? 0 : this.getIdautorizzazione().hashCode());
	return result;
    }
}
