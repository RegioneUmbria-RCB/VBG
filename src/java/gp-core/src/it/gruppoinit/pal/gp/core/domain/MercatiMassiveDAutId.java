package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

@Embeddable
public class MercatiMassiveDAutId implements java.io.Serializable {

    
    /**
     * 
     */
    private static final long serialVersionUID = -314328089420936135L;
    private String idcomune;
    private String id;

    public MercatiMassiveDAutId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public MercatiMassiveDAutId(String id) {

	this();
	this.id = id;
    }

    public MercatiMassiveDAutId(String idcomune, String id) {

	this.idcomune = idcomune;
	this.id = id;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }


    @Column(name = "ID", nullable = false)
    public String getId() {

	return this.id;
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
	MercatiMassiveDAutId other = (MercatiMassiveDAutId) obj;
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
