package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class JSonBorsellinoAppMovimenti {

    @XmlElement(name = "uuid_borsellino")
    private String uuidBorsellino;
    @XmlElement
    private BorsellinoAppMovimenti movimento;

    protected JSonBorsellinoAppMovimenti() {

	super();
    }

    public JSonBorsellinoAppMovimenti(String uuidBorsellino, BorsellinoAppMovimenti movimento) {

	this();
	this.uuidBorsellino = uuidBorsellino;
	this.movimento = movimento;
    }

    public String getUuidBorsellino() {

	return uuidBorsellino;
    }

    public void setUuidBorsellino(String uuidBorsellino) {

	this.uuidBorsellino = uuidBorsellino;
    }

    public BorsellinoAppMovimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(BorsellinoAppMovimenti movimento) {

	this.movimento = movimento;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
