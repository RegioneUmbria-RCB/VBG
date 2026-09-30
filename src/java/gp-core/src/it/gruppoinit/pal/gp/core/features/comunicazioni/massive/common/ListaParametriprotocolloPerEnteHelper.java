package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "listParamprotoPerEnte" })
public class ListaParametriprotocolloPerEnteHelper {

    public List<ParametriprotocolloPerEnteHelper> getListParamprotoPerEnte() {

	return listParamprotoPerEnte;
    }

    public void setListParamprotoPerEnte(List<ParametriprotocolloPerEnteHelper> listParamprotoPerEnte) {

	this.listParamprotoPerEnte = listParamprotoPerEnte;
    }

    @XmlElement(name = "listParamprotoPerEnte")
    private List<ParametriprotocolloPerEnteHelper> listParamprotoPerEnte;
}
