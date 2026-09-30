package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "comunicazioni_massive")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioniMassiveModel {

    @XmlElement(name = "alias")
    private String alias;
    @XmlElement(name = "software")
    private String software;
    @XmlElement(name = "codice_manifestazione")
    private Integer codiceManifestazione;
    @XmlElement(name = "manifestazione")
    private String manifestazione;
    @XmlElement(name = "comunicazioni")
    private List<ListaComunicazioniResoconti> comunicazioni;

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Integer getCodiceManifestazione() {

	return codiceManifestazione;
    }

    public void setCodiceManifestazione(Integer codiceManifestazione) {

	this.codiceManifestazione = codiceManifestazione;
    }

    public String getManifestazione() {

	return manifestazione;
    }

    public void setManifestazione(String manifestazione) {

	this.manifestazione = manifestazione;
    }

    public List<ListaComunicazioniResoconti> getComunicazioni() {

	return comunicazioni;
    }

    public void setComunicazioni(List<ListaComunicazioniResoconti> comunicazioni) {

	this.comunicazioni = comunicazioni;
    }

    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
