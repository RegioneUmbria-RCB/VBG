package it.gruppoinit.pal.gp.backoffice.web.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.service.helper.TempirispostaValoriHelper;

@XmlAccessorType(XmlAccessType.FIELD)
public class TempiRispostaJson {

    @XmlElement()
    private Integer codiceamministrazione;
    @XmlElement()
    private Integer codiceprocedura;
    @XmlElement()
    private Short tempo;
    @XmlElement()
    private Boolean calcolaDaInizioIstanza;

    public Integer getCodiceamministrazione() {

	return codiceamministrazione;
    }

    public void setCodiceamministrazione(Integer codiceamministrazione) {

	this.codiceamministrazione = codiceamministrazione;
    }

    public Integer getCodiceprocedura() {

	return codiceprocedura;
    }

    public void setCodiceprocedura(Integer codiceprocedura) {

	this.codiceprocedura = codiceprocedura;
    }

    public Short getTempo() {

	return tempo;
    }

    public void setTempo(Short tempo) {

	this.tempo = tempo;
    }

    public Boolean getCalcolaDaInizioIstanza() {

	return calcolaDaInizioIstanza;
    }

    public void setCalcolaDaInizioIstanza(Boolean calcolaDaInizioIstanza) {

	this.calcolaDaInizioIstanza = calcolaDaInizioIstanza;
    }

    @XmlTransient
    public static TempirispostaValoriHelper toTempirispostaValoriHelper(TempiRispostaJson in) {

	return new TempirispostaValoriHelper(in.getCodiceprocedura(), in.getCodiceamministrazione(), in.getTempo(), in.getCalcolaDaInizioIstanza());
    }
}
