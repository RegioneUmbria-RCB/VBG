package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class BollGestOneriSequenceId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7423697002038571806L;
    private String idcomune;
    private Integer idbollegestdett;
    private Integer idbollegestoneri;

    public BollGestOneriSequenceId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public BollGestOneriSequenceId(Integer idbollegestdett) {

	this.idcomune = ORMHelper.getIdcomune();
	this.idbollegestdett = idbollegestdett;
    }

    public BollGestOneriSequenceId(Integer idbollegestdett, Integer idbollegestoneri) {

	this.idcomune = ORMHelper.getIdcomune();
	this.idbollegestdett = idbollegestdett;
	this.idbollegestoneri = idbollegestoneri;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "ID_BOLLEGEST_DETT", nullable = false, length = 10)
    public Integer getIdbollegestdett() {

	return idbollegestdett;
    }

    public void setIdbollegestdett(Integer idbollegestdett) {

	this.idbollegestdett = idbollegestdett;
    }

    @Column(name = "ID_BOLLEGEST_ONERI", nullable = false, length = 10)
    public Integer getIdbollegestoneri() {

	return idbollegestoneri;
    }

    public void setIdbollegestoneri(Integer idbollegestoneri) {

	this.idbollegestoneri = idbollegestoneri;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idbollegestdett == null) ? 0 : idbollegestdett.hashCode());
	result = prime * result + ((idbollegestoneri == null) ? 0 : idbollegestoneri.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	BollGestOneriSequenceId other = (BollGestOneriSequenceId) obj;
	if (idbollegestdett == null) {
	    if (other.idbollegestdett != null)
		return false;
	} else if (!idbollegestdett.equals(other.idbollegestdett))
	    return false;
	if (idbollegestoneri == null) {
	    if (other.idbollegestoneri != null)
		return false;
	} else if (!idbollegestoneri.equals(other.idbollegestoneri))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("idcomune", this.idcomune);
	toStringBuilder.append("idbollegestdett", this.idbollegestdett);
	toStringBuilder.append("idbollegestoneri", this.idbollegestoneri);
	return toStringBuilder.toString();
    }
}
