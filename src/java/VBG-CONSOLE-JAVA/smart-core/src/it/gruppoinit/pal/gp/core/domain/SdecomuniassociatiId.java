package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Embeddable
public class SdecomuniassociatiId implements java.io.Serializable {

    private static final long serialVersionUID = 5548458211652588200L;
    private String idente;
    private String codicecatastalecomune;

    @Column(name = "IDENTE", length = 5)
    public String getIdente() {

	return idente;
    }

    public void setIdente(String idente) {

	this.idente = idente;
    }

    @Column(name = "CODICECATASTALECOMUNE", length = 5)
    public String getCodicecatastalecomune() {

	return codicecatastalecomune;
    }

    public void setCodicecatastalecomune(String codicecatastalecomune) {

	this.codicecatastalecomune = codicecatastalecomune;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof SdecomuniassociatiId))
	    return false;
	SdecomuniassociatiId castOther = (SdecomuniassociatiId) other;
	return (((this.getCodicecatastalecomune() == castOther.getCodicecatastalecomune()) || (this.getCodicecatastalecomune() != null
		&& castOther.getCodicecatastalecomune() != null && this.getCodicecatastalecomune().equals(castOther.getCodicecatastalecomune()))) && ((this
		.getIdente() == castOther.getIdente()) || (this.getIdente() != null && castOther.getIdente() != null && this.getIdente().equals(
		castOther.getIdente()))));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getCodicecatastalecomune() == null ? 0 : this.getCodicecatastalecomune().hashCode());
	result = 37 * result + (getIdente() == null ? 0 : this.getIdente().hashCode());
	return result;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
