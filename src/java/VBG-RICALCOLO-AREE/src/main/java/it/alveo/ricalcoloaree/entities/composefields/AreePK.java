package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;
import java.util.Objects;

public class AreePK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7325875670922873975L;
    private String idcomune;
    private Integer codicearea;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodicearea() {

	return codicearea;
    }

    public void setCodicearea(Integer codicearea) {

	this.codicearea = codicearea;
    }

    @Override
    public int hashCode() {

	return Objects.hash(codicearea, idcomune);
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AreePK other = (AreePK) obj;
	return Objects.equals(codicearea, other.codicearea) && Objects.equals(idcomune, other.idcomune);
    }
}
