package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;
import java.util.Objects;

public class IstanzeAreePk implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7159283173163334364L;
    private String idcomune;
    private Integer codiceistanza;
    private Integer codicearea;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public Integer getCodicearea() {

	return codicearea;
    }

    public void setCodicearea(Integer codicearea) {

	this.codicearea = codicearea;
    }

    @Override
    public int hashCode() {

	return Objects.hash(codicearea, codiceistanza, idcomune);
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	IstanzeAreePk other = (IstanzeAreePk) obj;
	return Objects.equals(codicearea, other.codicearea) && Objects.equals(codiceistanza, other.codiceistanza)
		&& Objects.equals(idcomune, other.idcomune);
    }
}
