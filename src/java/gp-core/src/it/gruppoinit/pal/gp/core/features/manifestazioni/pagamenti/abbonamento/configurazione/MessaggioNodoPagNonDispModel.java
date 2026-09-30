package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessaggioNodoPagNonDispModel {

    @XmlElement(name = "comuni")
    private List<ComuneModel> comuni;
    @XmlElement(name = "messaggio")
    private String messaggio;

    public List<ComuneModel> getComuni() {

	return comuni;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setComuni(List<ComuneModel> comuni) {

	this.comuni = comuni;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
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
