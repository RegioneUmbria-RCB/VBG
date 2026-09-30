package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "", propOrder = { "esito", //
	"nomeFlusso", //
	"idFlusso", //
	"contentJson", //
	"errore" //
})
public class EsitoMultiDovuto {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "nome_flusso")
    private String nomeFlusso;
    @XmlElement(name = "id_flusso")
    private String idFlusso;
    @XmlElement(name = "content_json")
    private String contentJson;
    @XmlElement(name = "errore")
    private String errore;
    @XmlTransient
    private List<Errori> errori;

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getNomeFlusso() {

	return nomeFlusso;
    }

    public void setNomeFlusso(String nomeFlusso) {

	this.nomeFlusso = nomeFlusso;
    }

    public String getIdFlusso() {

	return idFlusso;
    }

    public void setIdFlusso(String idFlusso) {

	this.idFlusso = idFlusso;
    }

    public String getContentJson() {

	return contentJson;
    }

    public void setContentJson(String contentJson) {

	this.contentJson = contentJson;
    }

    public List<Errori> getErrori() {

	if (this.errori == null) {
	    this.errori = new ArrayList<>();
	}
	return errori;
    }

    public void setErrori(List<Errori> errori) {

	this.errori = errori;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
