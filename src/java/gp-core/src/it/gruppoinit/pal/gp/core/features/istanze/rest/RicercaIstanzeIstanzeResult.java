package it.gruppoinit.pal.gp.core.features.istanze.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaIstanzeIstanzeResult {

    @XmlElement(name = "lista_istanze")
    private List<IstanzaRestBean> listaIstanze;
    @XmlElement(name = "total")
    private Integer total;
    @XmlElement(name = "offset")
    private Integer offset;
    @XmlElement(name = "limit")
    private Integer limit;

    public List<IstanzaRestBean> getListaIstanze() {

	return listaIstanze;
    }

    public void setListaIstanze(List<IstanzaRestBean> listaIstanze) {

	this.listaIstanze = listaIstanze;
    }

    public Integer getTotal() {

	return total;
    }

    public void setTotal(Integer total) {

	this.total = total;
    }

    public Integer getOffset() {

	return offset;
    }

    public void setOffset(Integer offset) {

	this.offset = offset;
    }

    public Integer getLimit() {

	return limit;
    }

    public void setLimit(Integer limit) {

	this.limit = limit;
    }
}
