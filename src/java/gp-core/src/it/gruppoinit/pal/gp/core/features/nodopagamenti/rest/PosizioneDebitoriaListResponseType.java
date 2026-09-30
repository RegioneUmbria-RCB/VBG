package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "posizioni_debitorie")
@XmlSeeAlso({ PosizioneDebitoriaResponseType.class })
public class PosizioneDebitoriaListResponseType {

    @XmlElement(name = "total")
    private Integer total; // totale degli elementi restituiti dal WS
    @XmlElement(name = "offset")
    private Integer offset;
    @XmlElement(name = "limit")
    private Integer limit;
    @XmlElement(name = "posizioni")
    private List<PosizioneDebitoriaResponseType> posizioni;

    public PosizioneDebitoriaListResponseType() {

	super();
    }

    public PosizioneDebitoriaListResponseType(Integer total, Integer offset, Integer limit, List<PosizioneDebitoriaResponseType> posizioni) {

	this();
	this.total = total;
	this.offset = offset;
	this.limit = limit;
	this.posizioni = posizioni;
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

    public List<PosizioneDebitoriaResponseType> getPosizioni() {

	if (this.posizioni == null) {
	    this.posizioni = new ArrayList<PosizioneDebitoriaResponseType>();
	}
	return posizioni;
    }

    public void setPosizioni(List<PosizioneDebitoriaResponseType> posizioni) {

	this.posizioni = posizioni;
    }
}
