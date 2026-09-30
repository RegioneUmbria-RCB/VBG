package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecuperaParametriByIdRequest {

    @XmlElement(name = "idComponente")
    private Integer idComponente;

    public Integer getIdComponente() {

	return idComponente;
    }

    public void setIdComponente(Integer idComponente) {

	this.idComponente = idComponente;
    }
}
