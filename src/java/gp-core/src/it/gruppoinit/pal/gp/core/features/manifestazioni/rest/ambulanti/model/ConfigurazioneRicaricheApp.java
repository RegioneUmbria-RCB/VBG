package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConfigurazioneRicaricheApp {

    @XmlElement
    private List<ConfigurazioneRicaricaComune> comuni;
    @XmlElement
    private BigDecimal importoMassimoBorsellino;

    public List<ConfigurazioneRicaricaComune> getComuni() {

	if (this.comuni == null) {
	    this.comuni = new ArrayList<ConfigurazioneRicaricaComune>();
	}
	return comuni;
    }

    public void setComuni(List<ConfigurazioneRicaricaComune> comuni) {

	this.comuni = comuni;
    }

    
    public BigDecimal getImportoMassimoBorsellino() {
    
        return importoMassimoBorsellino;
    }

    
    public void setImportoMassimoBorsellino(BigDecimal importoMassimoBorsellino) {
    
        this.importoMassimoBorsellino = importoMassimoBorsellino;
    }
    
    
}
