package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.TipoRicaricaEnum;

public class ConfigurazioneRicaricaComuneImportoLibero {

    @XmlElement(name = "importo_massimo")
    private Integer importoMassimo;

    public Integer getImportoMassimo() {

	return importoMassimo;
    }

    public void setImportoMassimo(Integer importoMassimo) {

	this.importoMassimo = importoMassimo;
    }

    public static ConfigurazioneRicaricaComuneImportoLibero fromRicaricheLibere(List<BorsellinoRicariche> ricariche) {

	if (ricariche == null || ricariche.isEmpty()) {
	    return null;
	}
	ConfigurazioneRicaricaComuneImportoLibero ret = new ConfigurazioneRicaricaComuneImportoLibero();
	for (BorsellinoRicariche b : ricariche) {
	    if (TipoRicaricaEnum.LIBERO.name().equalsIgnoreCase(b.getTipo())) {
		ret.setImportoMassimo(b.getImporto().intValue());
		break;
	    }
	}
	return ret;
    }
}
