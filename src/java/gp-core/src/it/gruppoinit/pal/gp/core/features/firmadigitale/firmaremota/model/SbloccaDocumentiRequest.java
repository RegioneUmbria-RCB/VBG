package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class SbloccaDocumentiRequest {

    @XmlElement(name = "idoggetti")
    private Integer[] idOggetti;

    public Integer[] getIdOggetti() {

	return idOggetti;
    }

    public void setIdOggetti(Integer[] idOggetti) {

	this.idOggetti = idOggetti;
    }
}
