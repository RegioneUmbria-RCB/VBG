package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement
public class ElencoSoggettiIstanzaModel {

    @XmlElement(name = "codiceistanza")
    private Integer codiceIstanza;
    @XmlElement(name = "numeroistanza")
    private String numeroIstanza;
    @XmlElement(name = "data")
    private Date data;
    private String riferimentiIstanza;
    @XmlElement(name = "numeroprotocollo")
    private String numeroProtocollo;
    @XmlElement(name = "dataprotocollo")
    private Date dataProtocollo;
    private String riferimentiProtocollo;
    @XmlElement(name = "soggetti")
    private List<SoggettoIstanzaModel> soggetti = new ArrayList<SoggettoIstanzaModel>();

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public List<SoggettoIstanzaModel> getSoggetti() {

	return soggetti;
    }

    public void setSoggetti(List<SoggettoIstanzaModel> soggetti) {

	this.soggetti = soggetti;
    }

    @XmlElement(name = "riferimentiprotocollo")
    public String getRiferimentiProtocollo() {

	if (StringUtils.isBlank(this.numeroProtocollo)) {
	    return "";
	}
	return this.numeroProtocollo + " del " + Utilities.formatDate(this.dataProtocollo, false);
    }

    @XmlElement(name = "riferimentiistanza")
    public String getRiferimentiIstanza() {

	if (StringUtils.isBlank(this.numeroIstanza)) {
	    return "";
	}
	return this.numeroIstanza + " del " + Utilities.formatDate(this.data, false);
    }
}
