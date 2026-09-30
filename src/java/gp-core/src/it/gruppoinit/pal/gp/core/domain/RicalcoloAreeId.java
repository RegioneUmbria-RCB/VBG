package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class RicalcoloAreeId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4386562033699551167L;
    private String idcomune;
    private String id;

    public RicalcoloAreeId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public RicalcoloAreeId(String id) {

	this.idcomune = ORMHelper.getIdcomune();
	this.id = id;
    }

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

	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	RicalcoloAreeId other = (RicalcoloAreeId) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }

}
