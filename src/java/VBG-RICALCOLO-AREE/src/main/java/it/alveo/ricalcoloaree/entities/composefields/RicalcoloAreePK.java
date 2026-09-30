package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class RicalcoloAreePK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 919771855313710430L;
    private String idcomune;
    private String id;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
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
	RicalcoloAreePK other = (RicalcoloAreePK) obj;
	return Objects.equals(id, other.id) && Objects.equals(idcomune, other.idcomune);
    }

    public static RicalcoloAreePK getIsttance(String idcomune, String id) {

	RicalcoloAreePK pk1 = new RicalcoloAreePK();
	pk1.setIdcomune(idcomune);
	pk1.setId(id);
	return pk1;
    }
}
