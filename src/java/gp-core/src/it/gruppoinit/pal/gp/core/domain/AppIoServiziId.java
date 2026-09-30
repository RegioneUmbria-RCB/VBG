package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class AppIoServiziId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4087839661277080987L;
    private String idcomune;
    private String identificativoServizio;

    public AppIoServiziId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    @NotNull
    @Length(max = 6)
    @Column(name = "IDCOMUNE")
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @NotNull
    @Length(max = 50)
    @Column(name = "IDENTIFICATIVO_SERVIZIO")
    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AppIoServiziId))
	    return false;
	AppIoServiziId castOther = (AppIoServiziId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getIdentificativoServizio() == castOther.getIdentificativoServizio())
			|| (this.getIdentificativoServizio() != null && castOther.getIdentificativoServizio() != null
				&& this.getIdentificativoServizio().equals(castOther.getIdentificativoServizio())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getIdentificativoServizio() == null ? 0 : this.getIdentificativoServizio().hashCode());
	return result;
    }
}
