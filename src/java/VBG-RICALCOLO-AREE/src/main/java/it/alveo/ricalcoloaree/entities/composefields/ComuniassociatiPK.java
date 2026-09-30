package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;
import java.util.Objects;

public class ComuniassociatiPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8875649963264336665L;
    private String idcomune;
    private String codicecomune;

    // Getters, setters, equals e hashCode
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    @Override
    public boolean equals(Object o) {

	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	ComuniassociatiPK that = (ComuniassociatiPK) o;
	return idcomune.equals(that.idcomune) && codicecomune.equals(that.codicecomune);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idcomune, codicecomune);
    }
}
