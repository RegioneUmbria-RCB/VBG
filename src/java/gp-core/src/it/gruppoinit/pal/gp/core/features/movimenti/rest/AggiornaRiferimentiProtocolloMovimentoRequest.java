package it.gruppoinit.pal.gp.core.features.movimenti.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement(name = "riferimenti")
public class AggiornaRiferimentiProtocolloMovimentoRequest {

    @XmlElement(name = "codice_movimento")
    private Integer codiceMovimento;
    @XmlElement(name = "numero_protocollo")
    private String numeroProtocollo;
    @XmlElement(name = "data_protocollo")
    private String dataProtocollo;
    @XmlElement(name = "id_protocollo")
    private String fkidProtocollo;

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public String getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(String dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public String getFkidProtocollo() {

	return fkidProtocollo;
    }

    public void setFkidProtocollo(String fkidProtocollo) {

	this.fkidProtocollo = fkidProtocollo;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
