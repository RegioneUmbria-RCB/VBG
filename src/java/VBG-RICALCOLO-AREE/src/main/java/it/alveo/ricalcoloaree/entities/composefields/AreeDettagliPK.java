package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;
import java.util.Objects;

public class AreeDettagliPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5131603092429052973L;
    private String id;
    private String idcomune;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Override
    public int hashCode() {

	return Objects.hash(id, idcomune);
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AreeDettagliPK other = (AreeDettagliPK) obj;
	return Objects.equals(id, other.id) && Objects.equals(idcomune, other.idcomune);
    }
}
