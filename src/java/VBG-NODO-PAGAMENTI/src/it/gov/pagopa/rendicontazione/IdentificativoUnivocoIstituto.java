package it.gov.pagopa.rendicontazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tipoIdentificativoUnivoco" //
	, "codiceIdentificativoUnivoco" //
})
public class IdentificativoUnivocoIstituto {

    @XmlElement
    private String tipoIdentificativoUnivoco;
    @XmlElement
    private String codiceIdentificativoUnivoco;

    public String getTipoIdentificativoUnivoco() {

	return tipoIdentificativoUnivoco;
    }

    public void setTipoIdentificativoUnivoco(String tipoIdentificativoUnivoco) {

	this.tipoIdentificativoUnivoco = tipoIdentificativoUnivoco;
    }

    public String getCodiceIdentificativoUnivoco() {

	return codiceIdentificativoUnivoco;
    }

    public void setCodiceIdentificativoUnivoco(String codiceIdentificativoUnivoco) {

	this.codiceIdentificativoUnivoco = codiceIdentificativoUnivoco;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
