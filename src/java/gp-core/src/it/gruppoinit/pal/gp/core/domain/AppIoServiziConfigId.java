package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class AppIoServiziConfigId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3985843948020573352L;
    private String idcomune;
    private String codiceComune;
    private String software;
    private String identificativoServizio;

    public AppIoServiziConfigId() {

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
    @Length(max = 5)
    @Column(name = "CODICECOMUNE")
    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    @NotNull
    @Length(max = 2)
    @Column(name = "SOFTWARE")
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
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
	if (!(other instanceof AppIoServiziConfigId))
	    return false;
	AppIoServiziConfigId castOther = (AppIoServiziConfigId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getIdentificativoServizio() == castOther.getIdentificativoServizio())
			|| (this.getIdentificativoServizio() != null && castOther.getIdentificativoServizio() != null
				&& this.getIdentificativoServizio().equals(castOther.getIdentificativoServizio())))
		&& ((this.getCodiceComune() == castOther.getCodiceComune()) || (this.getCodiceComune() != null && castOther.getCodiceComune() != null
			&& this.getCodiceComune().equals(castOther.getCodiceComune())))
		&& ((this.getSoftware() == castOther.getSoftware())
			|| (this.getSoftware() != null && castOther.getSoftware() != null && this.getSoftware().equals(castOther.getSoftware())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getIdentificativoServizio() == null ? 0 : this.getIdentificativoServizio().hashCode());
	result = 37 * result + (getCodiceComune() == null ? 0 : this.getCodiceComune().hashCode());
	result = 37 * result + (getSoftware() == null ? 0 : this.getSoftware().hashCode());
	return result;
    }
}
