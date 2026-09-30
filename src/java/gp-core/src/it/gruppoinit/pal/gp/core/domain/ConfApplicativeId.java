package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConfApplicativeId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4057567371007156778L;
    private String idcomune;
    private String chiave;

    public ConfApplicativeId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CHIAVE", nullable = false, length = 50)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof ConfApplicativeId))
	    return false;
	ConfApplicativeId castOther = (ConfApplicativeId) other;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getChiave() == castOther.getChiave()) || (this.getChiave() != null && castOther.getChiave() != null && this.getChiave()
			.equals(castOther.getChiave())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getChiave() == null ? 0 : this.getChiave().hashCode());
	return result;
    }
}
