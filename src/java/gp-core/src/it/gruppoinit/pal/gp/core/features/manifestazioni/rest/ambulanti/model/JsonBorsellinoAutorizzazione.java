package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class JsonBorsellinoAutorizzazione {

    @XmlElement(name = "uuid_borsellino")
    public String uuidBorsellino;
    @XmlElement(name = "id_autorizzazione")
    public Integer idAutorizzazione;

    public JsonBorsellinoAutorizzazione() {

    }

    public JsonBorsellinoAutorizzazione(String uuidBorsellino, Integer idAutorizzazione) {

	this.uuidBorsellino = uuidBorsellino;
	this.idAutorizzazione = idAutorizzazione;
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
