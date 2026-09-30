package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "comunicazione")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioneProtocolloModel {

    @XmlElement(name = "oggetto")
    private String oggetto;

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }

    public static ComunicazioneProtocolloModel fromMailTipo(Mailtipo protMailtipo) {

	if (protMailtipo == null) {
	    return null;
	}
	ComunicazioneProtocolloModel model = new ComunicazioneProtocolloModel();
	model.setOggetto(protMailtipo.getOggetto());
	return model;
    }
}
