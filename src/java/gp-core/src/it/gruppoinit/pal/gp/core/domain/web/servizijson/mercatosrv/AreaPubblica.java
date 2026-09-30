package it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
public class AreaPubblica {

    @XmlElement
    private String tipologia;
    @XmlElement
    private String denominazione;
    @XmlElement
    private String giorno;
    @XmlElement
    private String posteggio;

    public String getTipologia() {

	return tipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    public String getGiorno() {

	return giorno;
    }

    public void setGiorno(String giorno) {

	this.giorno = giorno;
    }

    public String getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(String posteggio) {

	this.posteggio = posteggio;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
