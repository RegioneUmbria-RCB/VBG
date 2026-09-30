package it.gruppoinit.pal.gp.core.features.decodifiche;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.core.domain.Decodifiche;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaDecodifiche", propOrder = {})
public class ListaDecodifiche {

    @XmlElement(name = "decodificheList")
    @XmlElementWrapper
    private List<Decodifiche> decodificheList = new ArrayList<Decodifiche>();

    public List<Decodifiche> getDecodificheList() {

	if (this.decodificheList == null) {
	    this.decodificheList = new ArrayList<Decodifiche>();
	}
	return decodificheList;
    }

    public void setDecodificheList(List<Decodifiche> decodificheList) {

	this.decodificheList = decodificheList;
    }
}
