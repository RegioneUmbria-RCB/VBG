package it.alveo.ricalcoloaree.entities.composefields;

import java.util.Objects;

public class PkId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5278656804249885831L;
    private String idcomune;
    private Integer codice;

    public PkId() {

    }

    public PkId(String idcomune, Integer codice) {

	this.idcomune = idcomune;
	this.codice = codice;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    @Override
    public int hashCode() {

	return Objects.hash(codice, idcomune);
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PkId other = (PkId) obj;
	return Objects.equals(codice, other.codice) && Objects.equals(idcomune, other.idcomune);
    }
}
