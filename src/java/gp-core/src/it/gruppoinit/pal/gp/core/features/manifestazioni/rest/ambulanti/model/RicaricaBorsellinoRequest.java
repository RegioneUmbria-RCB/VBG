package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement(name = "ricarica")
@XmlType(name = "RicaricaBorsellinoRequest", propOrder = { "importo", "codiceComune", "idInformative" })
@XmlAccessorType(XmlAccessType.FIELD)
public class RicaricaBorsellinoRequest {

    @XmlElement
    private BigDecimal importo;
    @XmlElement(name = "codice_comune")
    private String codiceComune;
    @XmlElement(name = "informative_accettate")
    private List<InformativeAbbonamento> idInformative;

    public BigDecimal getImporto() {

	return importo;
    }

    public List<InformativeAbbonamento> getIdInformative() {

	if (this.idInformative == null) {
	    this.idInformative = new ArrayList<InformativeAbbonamento>();
	}
	return idInformative;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
