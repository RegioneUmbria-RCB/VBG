package it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AmministrazioniProtocolloRequest {

    @XmlElement(name = "codiceComune")
    public String codiceComune;
    @XmlElement(name = "software")
    public String software;

    public AmministrazioniProtocolloRequest() {

    }

    public AmministrazioniProtocolloRequest(String codiceComune, String software) {

	this.codiceComune = codiceComune;
	this.software = software;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public String getSoftware() {

	return software;
    }
}
