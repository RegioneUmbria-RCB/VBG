package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "metadati-file")
@XmlSeeAlso({ DocumentiCondivisiMetadato.class })
public class ElencoMetadati {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    // @XmlElement(name = "metadato")
    protected List<DocumentiCondivisiMetadato> metadati = new ArrayList<DocumentiCondivisiMetadato>();

    public ElencoMetadati() {

    }

    public ElencoMetadati(List<DocumentiCondivisiMetadato> metadati) {

	this.metadati = metadati;
    }

    public List<DocumentiCondivisiMetadato> getMetadati() {

	return metadati;
    }

    public void setMetadati(List<DocumentiCondivisiMetadato> metadati) {

	this.metadati = metadati;
    }

    public String toXmlString() {

	StringWriter stringWriter = new StringWriter();
	try {
	    ElencoMetadatiSerialized var = new ElencoMetadatiSerialized(this);
	    Marshaller marshaller = JAXBContext.newInstance(var.getClass()).createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.marshal(var, stringWriter);
	    return stringWriter.toString();
	} catch (Exception ex) {
	    log.error("toXmlString: {}", ex.getMessage());
	    throw new RuntimeException(ex);
	}
    }
}
