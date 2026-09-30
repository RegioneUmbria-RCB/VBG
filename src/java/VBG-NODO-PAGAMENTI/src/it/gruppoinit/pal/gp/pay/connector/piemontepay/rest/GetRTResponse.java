package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;

public class GetRTResponse {

    @XmlElement(name = "identificativoPagamento")
    private String identificativoPagamento;
    @XmlElement(name = "codiceEsito")
    private String codiceEsito;
    @XmlElement(name = "descrizioneEsito")
    private String descrizioneEsito;
    @XmlElement(name = "descrizioneStatoPagamento")
    private String descrizioneStatoPagamento;
    @XmlElement(name = "iuvOriginario")
    private String iuvOriginario;
    @XmlElement(name = "iuvEffettivo")
    private String iuvEffettivo;
    @XmlElement(name = "ricevutaPdf")
    private String ricevutaPDF;
    @XmlElement(name = "rtXml")
    private String rtXml;
    private CtRicevutaTelematica ricevutaXML;

    public String getRicevutaPDF() {

	return ricevutaPDF;
    }

    public void setRicevutaPDF(String ricevutaPDF) {

	this.ricevutaPDF = ricevutaPDF;
    }

    public CtRicevutaTelematica getRicevutaXML() {

	if (this.ricevutaXML != null) {
	    return this.ricevutaXML;
	}
	if (StringUtils.isBlank(this.getRtXml())) {
	    throw new RuntimeException("Impossibile utilizzare la proprietà ricevutaXML se prima non viene valorizzata la proprietà rtXml");
	}
	this.ricevutaXML = RTHelper.parseRicevutaTelematicaBase64Binary(this.getRtXml());
	return this.ricevutaXML;
    }

    public byte[] ricevutaXMLToByte() {

	if (StringUtils.isBlank(this.getRtXml())) {
	    return null;
	}
	return DatatypeConverter.parseBase64Binary(this.getRtXml());
    }

    public byte[] ricevutaPDFToByte() {

	if (StringUtils.isBlank(this.getRicevutaPDF())) {
	    return null;
	}
	return DatatypeConverter.parseBase64Binary(this.getRicevutaPDF());
    }

    public String getRtXml() {

	return rtXml;
    }

    public void setRtXml(String rtXml) {

	this.rtXml = rtXml;
    }

    public String getCodiceEsito() {

	return codiceEsito;
    }

    public void setCodiceEsito(String codiceEsito) {

	this.codiceEsito = codiceEsito;
    }

    public String getDescrizioneEsito() {

	return descrizioneEsito;
    }

    public void setDescrizioneEsito(String descrizioneEsito) {

	this.descrizioneEsito = descrizioneEsito;
    }

    public String getIdentificativoPagamento() {

	return identificativoPagamento;
    }

    public void setIdentificativoPagamento(String identificativoPagamento) {

	this.identificativoPagamento = identificativoPagamento;
    }

    public String getDescrizioneStatoPagamento() {

	return descrizioneStatoPagamento;
    }

    public void setDescrizioneStatoPagamento(String descrizioneStatoPagamento) {

	this.descrizioneStatoPagamento = descrizioneStatoPagamento;
    }

    public String getIuvOriginario() {

	return iuvOriginario;
    }

    public void setIuvOriginario(String iuvOriginario) {

	this.iuvOriginario = iuvOriginario;
    }

    public String getIuvEffettivo() {

	return iuvEffettivo;
    }

    public void setIuvEffettivo(String iuvEffettivo) {

	this.iuvEffettivo = iuvEffettivo;
    }
}
