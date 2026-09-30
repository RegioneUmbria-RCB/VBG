package it.gov.pagopa.rendicontazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "identificativoUnivocoRicevente" //
	, "denominazioneRicevente" //
})
public class IstitutoRicevente {

    @XmlElement
    private IdentificativoUnivocoIstituto identificativoUnivocoRicevente;
    @XmlElement
    private String denominazioneRicevente;

    public IdentificativoUnivocoIstituto getIdentificativoUnivocoRicevente() {

	return identificativoUnivocoRicevente;
    }

    public void setIdentificativoUnivocoRicevente(IdentificativoUnivocoIstituto identificativoUnivocoRicevente) {

	this.identificativoUnivocoRicevente = identificativoUnivocoRicevente;
    }

    public String getDenominazioneRicevente() {

	return denominazioneRicevente;
    }

    public void setDenominazioneRicevente(String denominazioneRicevente) {

	this.denominazioneRicevente = denominazioneRicevente;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
