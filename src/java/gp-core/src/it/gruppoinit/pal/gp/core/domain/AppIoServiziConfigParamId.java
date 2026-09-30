package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class AppIoServiziConfigParamId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1978333794014474244L;
    private String idcomune;
    private String codiceComune;
    private String software;
    private String identificativoServizio;
    private String parametro;

    public AppIoServiziConfigParamId() {

	this.idcomune = ORMHelper.getIdcomune();
	this.software = ORMHelper.getSoftware();
    }

    @NotNull
    @Length(max = 6)
    @Column(name = "IDCOMUNE")
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idComune) {

	this.idcomune = idComune;
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

    @NotNull
    @Length(max = 50)
    @Column(name = "PARAMETRO")
    public String getParametro() {

	return parametro;
    }

    public void setParametro(String parametro) {

	this.parametro = parametro;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AppIoServiziConfigParamId))
	    return false;
	AppIoServiziConfigParamId castOther = (AppIoServiziConfigParamId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getIdentificativoServizio() == castOther.getIdentificativoServizio())
			|| (this.getIdentificativoServizio() != null && castOther.getIdentificativoServizio() != null
				&& this.getIdentificativoServizio().equals(castOther.getIdentificativoServizio())))
		&& ((this.getParametro() == castOther.getParametro())
			|| (this.getParametro() != null && castOther.getParametro() != null && this.getParametro().equals(castOther.getParametro())))
		&& ((this.getCodiceComune() == castOther.getCodiceComune()) || (this.getCodiceComune() != null && castOther.getCodiceComune() != null
			&& this.getCodiceComune().equals(castOther.getCodiceComune())))
		&& ((this.getSoftware() == castOther.getSoftware())
			|| (this.getSoftware() != null && castOther.getSoftware() != null && this.getSoftware().equals(castOther.getSoftware())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getIdentificativoServizio() == null ? 0 : this.getIdentificativoServizio().hashCode());
	result = 37 * result + (getParametro() == null ? 0 : this.getParametro().hashCode());
	result = 37 * result + (getCodiceComune() == null ? 0 : this.getCodiceComune().hashCode());
	result = 37 * result + (getSoftware() == null ? 0 : this.getSoftware().hashCode());
	return result;
    }
}
