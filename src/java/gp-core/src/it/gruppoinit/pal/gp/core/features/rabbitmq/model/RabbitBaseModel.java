package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "", propOrder = { // 
	"alias", //
	"idcomune", //
	"software", // 
	"codiceIstanza", //
	"codiceMovimento" //
})
public class RabbitBaseModel {

    @XmlElement
    protected String alias;
    @XmlElement
    protected String idcomune;
    @XmlElement
    protected String software;
    @XmlElement
    protected Integer codiceIstanza;
    @XmlElement
    protected Integer codiceMovimento;

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
