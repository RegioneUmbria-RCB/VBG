package it.gov.pagopa.rendicontazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "identificativoUnivocoMittente" //
	, "denominazioneMittente" //
})
public class IstitutoMittente {

    @XmlElement
    private IdentificativoUnivocoIstituto identificativoUnivocoMittente;
    @XmlElement
    private String denominazioneMittente;

    public IdentificativoUnivocoIstituto getIdentificativoUnivocoMittente() {

	return identificativoUnivocoMittente;
    }

    public void setIdentificativoUnivocoMittente(IdentificativoUnivocoIstituto identificativoUnivocoMittente) {

	this.identificativoUnivocoMittente = identificativoUnivocoMittente;
    }

    public String getDenominazioneMittente() {

	return denominazioneMittente;
    }

    public void setDenominazioneMittente(String denominazioneMittente) {

	this.denominazioneMittente = denominazioneMittente;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
