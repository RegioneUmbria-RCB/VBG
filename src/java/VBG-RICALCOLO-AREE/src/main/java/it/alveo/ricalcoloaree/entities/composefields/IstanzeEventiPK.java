package it.alveo.ricalcoloaree.entities.composefields;

import java.util.Objects;

public class IstanzeEventiPK {

    private String idcomune;
    private Long idevento;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Long getIdevento() {

	return idevento;
    }

    public void setIdevento(Long idevento) {

	this.idevento = idevento;
    }

    @Override
    public int hashCode() {

	return Objects.hash(idcomune, idevento);
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	IstanzeEventiPK other = (IstanzeEventiPK) obj;
	return Objects.equals(idcomune, other.idcomune) && Objects.equals(idevento, other.idevento);
    }
}
