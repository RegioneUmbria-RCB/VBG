package it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dettaglio")
@XmlAccessorType(XmlAccessType.FIELD)
public class AmministrazioneProtocolloResponse {

    @XmlElement(name = "amministrazioni")
    List<AmministrazioneProtocolloModel> amministrazioni;

    public AmministrazioneProtocolloResponse() {

	this.amministrazioni = new ArrayList<AmministrazioneProtocolloModel>(0);
    }

    public AmministrazioneProtocolloResponse(List<AmministrazioneProtocolloModel> amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public List<AmministrazioneProtocolloModel> getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(List<AmministrazioneProtocolloModel> amministrazioni) {

	this.amministrazioni = amministrazioni;
    }
}
