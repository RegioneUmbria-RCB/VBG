package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

@XmlRootElement(name = "numeri_istanza")
public class NumeriIstanzaResponse {

    @XmlElement(name = "numero_istanza")
    List<IdentificativoDescrizioneBean> numeriIstanza;

    public void setNumeriIstanza(List<IdentificativoDescrizioneBean> numeriIstanza) {

	this.numeriIstanza = numeriIstanza;
    }
}
