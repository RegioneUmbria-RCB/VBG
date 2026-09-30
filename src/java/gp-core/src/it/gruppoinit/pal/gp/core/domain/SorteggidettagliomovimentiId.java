package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SorteggidettagliomovimentiId implements Serializable {

    private static final long serialVersionUID = -1320453799573866107L;
    private String idcomune;
    private Integer sdmFkSdid;
    private Integer codicemovimento;

    public SorteggidettagliomovimentiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public SorteggidettagliomovimentiId(Integer sdmFkSdid, Integer codicemovimento) {

	this();
	this.sdmFkSdid = sdmFkSdid;
	this.codicemovimento = codicemovimento;
    }

    public SorteggidettagliomovimentiId(String idcomune, Integer sdmFkSdid, Integer codicemovimento) {

	super();
	this.idcomune = idcomune;
	this.sdmFkSdid = sdmFkSdid;
	this.codicemovimento = codicemovimento;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "SDM_FK_SDID", nullable = false, precision = 9, scale = 0)
    public Integer getSdmFkSdid() {

	return sdmFkSdid;
    }

    public void setSdmFkSdid(Integer sdmFkSdid) {

	this.sdmFkSdid = sdmFkSdid;
    }

    @Column(name = "CODICEMOVIMENTO", nullable = false, precision = 6, scale = 0)
    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codicemovimento == null) ? 0 : codicemovimento.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((sdmFkSdid == null) ? 0 : sdmFkSdid.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	SorteggidettagliomovimentiId other = (SorteggidettagliomovimentiId) obj;
	if (codicemovimento == null) {
	    if (other.codicemovimento != null) {
		return false;
	    }
	} else if (!codicemovimento.equals(other.codicemovimento)) {
	    return false;
	}
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	if (sdmFkSdid == null) {
	    if (other.sdmFkSdid != null) {
		return false;
	    }
	} else if (!sdmFkSdid.equals(other.sdmFkSdid)) {
	    return false;
	}
	return true;
    }
}
