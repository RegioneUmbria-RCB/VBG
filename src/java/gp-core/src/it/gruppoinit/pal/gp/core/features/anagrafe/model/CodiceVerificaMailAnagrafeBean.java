package it.gruppoinit.pal.gp.core.features.anagrafe.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class CodiceVerificaMailAnagrafeBean {

    @XmlElement(name = "codice_anagrafe")
    private Integer codiceAnagrafe;
    @XmlElement(name = "codice_verifica")
    private String codiceVerifica;
    @XmlElement(name = "data_scadenza")
    private Date dataScadenza;
    @XmlElement(name = "nuova_mail")
    private String nuovaMail;

    protected CodiceVerificaMailAnagrafeBean() {

	// 
	super();
    }

    public CodiceVerificaMailAnagrafeBean(Integer codiceAnagrafe, String codiceVerifica, Date dataScadenza, String nuovaMail) {

	this.codiceAnagrafe = codiceAnagrafe;
	this.codiceVerifica = codiceVerifica;
	this.dataScadenza = dataScadenza;
	this.nuovaMail = nuovaMail;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public String getCodiceVerifica() {

	return codiceVerifica;
    }

    public void setCodiceVerifica(String codiceVerifica) {

	this.codiceVerifica = codiceVerifica;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getNuovaMail() {

	return nuovaMail;
    }

    public void setNuovaMail(String nuovaMail) {

	this.nuovaMail = nuovaMail;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
