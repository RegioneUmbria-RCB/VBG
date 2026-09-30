package it.alveo.ricalcoloaree.entities.composefields;

import java.util.Objects;

public class SequenceTabPK {

    private String idcomune;
    private String sequencename;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getSequencename() {

	return sequencename;
    }

    public void setSequencename(String sequencename) {

	this.sequencename = sequencename;
    }

    @Override
    public int hashCode() {

	return Objects.hash(idcomune, sequencename);
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SequenceTabPK other = (SequenceTabPK) obj;
	return Objects.equals(idcomune, other.idcomune) && Objects.equals(sequencename, other.sequencename);
    }
}
