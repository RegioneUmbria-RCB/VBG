package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class EliminaAlberoProcTempiRequest {

    @XmlElement(name = "codiceIntervento")
    private Integer codiceIntervento;
    @XmlElement(name = "codiceTempoFo")
    private Integer codiceTempoFo;

    public Integer getCodiceIntervento() {

	return codiceIntervento;
    }

    public void setCodiceIntervento(Integer codiceIntervento) {

	this.codiceIntervento = codiceIntervento;
    }

    public Integer getCodiceTempoFo() {

	return codiceTempoFo;
    }

    public void setCodiceTempoFo(Integer codiceTempoFo) {

	this.codiceTempoFo = codiceTempoFo;
    }
}
