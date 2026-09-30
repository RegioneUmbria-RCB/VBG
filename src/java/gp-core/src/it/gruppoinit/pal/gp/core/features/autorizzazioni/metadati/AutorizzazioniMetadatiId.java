package it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class AutorizzazioniMetadatiId implements java.io.Serializable {

    private static final long serialVersionUID = 9220500232576652784L;
    private String idcomune;
    private Integer fkIdAutorizzazione;
    private String chiave;

    public AutorizzazioniMetadatiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public AutorizzazioniMetadatiId(String chiave) {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.setChiave(chiave);
    }

    public AutorizzazioniMetadatiId(String idcomune, Integer fkIdAutorizzazione, String chiave) {

	super();
	this.idcomune = idcomune;
	this.fkIdAutorizzazione = fkIdAutorizzazione;
	this.setChiave(chiave);
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FKIDAUTORIZZAZIONE ", nullable = false, precision = 6, scale = 0)
    public Integer getFkIdAutorizzazione() {

	return fkIdAutorizzazione;
    }

    public void setFkIdAutorizzazione(Integer fkIdAutorizzazione) {

	this.fkIdAutorizzazione = fkIdAutorizzazione;
    }

    @Column(name = "CHIAVE", nullable = false, length = 100)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	if (!StringUtils.isEmpty(chiave)) {
	    this.chiave = chiave.toUpperCase();
	} else {
	    this.chiave = null;
	}
    }

    @Override
    public int hashCode() {

	int result = 1;
	final int prime = 31;
	result = prime * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = prime * result + (getFkIdAutorizzazione() == null ? 0 : this.getFkIdAutorizzazione().hashCode());
	result = prime * result + (getChiave() == null ? 0 : this.getChiave().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if (this == other)
	    return true;
	if (other == null)
	    return false;
	if (getClass() != other.getClass())
	    return false;
	AutorizzazioniMetadatiId castOther = (AutorizzazioniMetadatiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkIdAutorizzazione() == castOther.getFkIdAutorizzazione()) || (this.getFkIdAutorizzazione() != null
			&& castOther.getFkIdAutorizzazione() != null && this.getFkIdAutorizzazione().equals(castOther.getFkIdAutorizzazione())))
		&& ((this.getChiave() == castOther.getChiave())
			|| (this.getChiave() != null && castOther.getChiave() != null && this.getChiave().equals(castOther.getChiave())));
    }
}
