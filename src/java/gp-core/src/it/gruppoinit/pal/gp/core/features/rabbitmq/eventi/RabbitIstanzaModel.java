package it.gruppoinit.pal.gp.core.features.rabbitmq.eventi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "", propOrder = { // 
	"alias", //
	"idcomune", //
	"uuidPratica", //
	"software" })
public class RabbitIstanzaModel {

    @XmlElement
    protected String alias;
    @XmlElement
    protected String idcomune;
    @XmlElement
    protected String software;
    @XmlElement
    protected String uuidPratica;

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

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getUuidPratica() {

	return uuidPratica;
    }

    public void setUuidPratica(String uuidPratica) {

	this.uuidPratica = uuidPratica;
    }
}
