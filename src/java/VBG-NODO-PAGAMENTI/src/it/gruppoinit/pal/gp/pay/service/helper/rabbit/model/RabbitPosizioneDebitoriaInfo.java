package it.gruppoinit.pal.gp.pay.service.helper.rabbit.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RabbitPosizioneDebitoriaInfo {

    @XmlElement
    private String alias;
    @XmlElement
    private String idcomune;
    @XmlElement
    private String cfEnteCreditore;
    @XmlElement
    private String uuid;
    @XmlElement
    private List<String> riferimentoClient;
    @XmlElement
    private String cfPiva;
    @XmlElement
    private String nominativo;

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public String getCfEnteCreditore() {

	return cfEnteCreditore;
    }

    public void setCfEnteCreditore(String cfEnteCreditore) {

	this.cfEnteCreditore = cfEnteCreditore;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public void setCfPiva(String cfPiva) {

	this.cfPiva = cfPiva;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public List<String> getRiferimentoClient() {

	return riferimentoClient;
    }

    public void setRiferimentoClient(List<String> riferimentoClient) {

	this.riferimentoClient = riferimentoClient;
    }

    public String tornaPIVA() {

	if (StringUtils.isNotBlank(this.cfPiva) && StringUtils.length(this.cfPiva) == 11) {
	    return this.cfPiva;
	}
	return null;
    }

    public String tornaCF() {

	if (StringUtils.isNotBlank(this.cfPiva) && StringUtils.length(this.cfPiva) == 16) {
	    return this.cfPiva;
	}
	return null;
    }

    public boolean validaDatiMinimi() {

	return !(StringUtils.isBlank(this.alias) || StringUtils.isBlank(this.idcomune) || StringUtils.isBlank(this.cfEnteCreditore)
		|| StringUtils.isBlank(this.uuid));
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public RabbitPosizioneDebitoriaInfo posizioneDebitoriaInfo() {

	return this;
    }
}
