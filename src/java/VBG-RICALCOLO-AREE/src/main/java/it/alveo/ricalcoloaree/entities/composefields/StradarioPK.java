package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;
import java.util.Objects;

public class StradarioPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3075339782543010598L;
    private String idcomune;
    private String codicestradario;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getCodicestradario() {

	return codicestradario;
    }

    public void setCodicestradario(String codicestradario) {

	this.codicestradario = codicestradario;
    }

    @Override
    public int hashCode() {

	return Objects.hash(codicestradario, idcomune);
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	StradarioPK other = (StradarioPK) obj;
	return Objects.equals(codicestradario, other.codicestradario) && Objects.equals(idcomune, other.idcomune);
    }
}
