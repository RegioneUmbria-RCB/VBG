package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "borsellino")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorselliniListModel {

    @XmlElement(name = "borsellini")
    public List<BorsellinoListModel> borsellini;

    public List<BorsellinoListModel> getBorsellini() {

	return borsellini;
    }

    public void setBorsellini(List<BorsellinoListModel> borsellini) {

	this.borsellini = borsellini;
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
