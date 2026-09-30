package it.gruppoinit.pal.gp.core.features.datidinamici.metadati;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2MetadatiRestBeanList", propOrder = {})
public class Dyn2MetadatiRestBeanList {

    @XmlElement(name = "listaMetadatiRestBean")
    @XmlElementWrapper
    private List<Dyn2MetadatiRestBean> listaMetadatiRestBean;

    public List<Dyn2MetadatiRestBean> getListaMetadatiRestBean() {

	return listaMetadatiRestBean;
    }

    public void setListaMetadatiRestBean(List<Dyn2MetadatiRestBean> listaMetadatiRestBean) {

	this.listaMetadatiRestBean = listaMetadatiRestBean;
    }
}
