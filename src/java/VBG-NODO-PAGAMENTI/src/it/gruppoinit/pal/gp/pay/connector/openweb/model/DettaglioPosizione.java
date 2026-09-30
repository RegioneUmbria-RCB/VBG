package it.gruppoinit.pal.gp.pay.connector.openweb.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "", propOrder = { "pagatore", //
	"versante", //
	"dataDocumento", // 
	"numeroProtocollo", // 
	"dataProtocollo", //
	"dettaglioRiga1", //
	"dettaglioRiga2", //
	"dettaglioRiga3", //
	"dettaglioRiga4", //
	"dettaglioRiga5", //
	"istruttoreProcedimento", // 
	"telefonoProcedimento", //
	"emailProcedimento", //
	"note", //
	"idDocCivilianext", // 
	"urlDocumento", //
	"nomeFileB64", //
	"contenutoFileB64", //
	"rate" })
public class DettaglioPosizione {

    @XmlElement(name = "pagatore")
    private SoggettoPagatore pagatore;
    @XmlElement(name = "versante")
    private SoggettoPagatore versante;
    @XmlElement(name = "data_documento")
    protected String dataDocumento;
    @XmlElement(name = "numero_protocollo")
    private String numeroProtocollo;
    @XmlElement(name = "data_protocollo")
    private String dataProtocollo;
    @XmlElement(name = "dettaglio_riga1")
    private String dettaglioRiga1;
    @XmlElement(name = "dettaglio_riga2")
    private String dettaglioRiga2;
    @XmlElement(name = "dettaglio_riga3")
    private String dettaglioRiga3;
    @XmlElement(name = "dettaglio_riga4")
    private String dettaglioRiga4;
    @XmlElement(name = "dettaglio_riga5")
    private String dettaglioRiga5;
    @XmlElement(name = "istruttore_procedimento")
    private String istruttoreProcedimento;
    @XmlElement(name = "telefono_procedimento")
    private String telefonoProcedimento;
    @XmlElement(name = "email_procedimento")
    private String emailProcedimento;
    @XmlElement(name = "note")
    private String note;
    @XmlElement(name = "id_doc_civilianext")
    private String idDocCivilianext;
    @XmlElement(name = "url_documento")
    private String urlDocumento;
    @XmlElement(name = "nome_file_b64")
    private String nomeFileB64;
    @XmlElement(name = "contenuto_file_b64")
    private String contenutoFileB64;
    @XmlElement(name = "rate")
    private List<Rata> rate;

    public SoggettoPagatore getPagatore() {

	return pagatore;
    }

    public void setPagatore(SoggettoPagatore pagatore) {

	this.pagatore = pagatore;
    }

    public SoggettoPagatore getVersante() {

	return versante;
    }

    public void setVersante(SoggettoPagatore versante) {

	this.versante = versante;
    }

    public String getDataDocumento() {

	return dataDocumento;
    }

    public void setDataDocumento(String dataDocumento) {

	this.dataDocumento = dataDocumento;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public String getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(String dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public String getDettaglioRiga1() {

	return dettaglioRiga1;
    }

    public void setDettaglioRiga1(String dettaglioRiga1) {

	this.dettaglioRiga1 = dettaglioRiga1;
    }

    public String getDettaglioRiga2() {

	return dettaglioRiga2;
    }

    public void setDettaglioRiga2(String dettaglioRiga2) {

	this.dettaglioRiga2 = dettaglioRiga2;
    }

    public String getDettaglioRiga3() {

	return dettaglioRiga3;
    }

    public void setDettaglioRiga3(String dettaglioRiga3) {

	this.dettaglioRiga3 = dettaglioRiga3;
    }

    public String getDettaglioRiga4() {

	return dettaglioRiga4;
    }

    public void setDettaglioRiga4(String dettaglioRiga4) {

	this.dettaglioRiga4 = dettaglioRiga4;
    }

    public String getDettaglioRiga5() {

	return dettaglioRiga5;
    }

    public void setDettaglioRiga5(String dettaglioRiga5) {

	this.dettaglioRiga5 = dettaglioRiga5;
    }

    public String getIstruttoreProcedimento() {

	return istruttoreProcedimento;
    }

    public void setIstruttoreProcedimento(String istruttoreProcedimento) {

	this.istruttoreProcedimento = istruttoreProcedimento;
    }

    public String getTelefonoProcedimento() {

	return telefonoProcedimento;
    }

    public void setTelefonoProcedimento(String telefonoProcedimento) {

	this.telefonoProcedimento = telefonoProcedimento;
    }

    public String getEmailProcedimento() {

	return emailProcedimento;
    }

    public void setEmailProcedimento(String emailProcedimento) {

	this.emailProcedimento = emailProcedimento;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getIdDocCivilianext() {

	return idDocCivilianext;
    }

    public void setIdDocCivilianext(String idDocCivilianext) {

	this.idDocCivilianext = idDocCivilianext;
    }

    public String getUrlDocumento() {

	return urlDocumento;
    }

    public void setUrlDocumento(String urlDocumento) {

	this.urlDocumento = urlDocumento;
    }

    public String getNomeFileB64() {

	return nomeFileB64;
    }

    public void setNomeFileB64(String nomeFileB64) {

	this.nomeFileB64 = nomeFileB64;
    }

    public String getContenutoFileB64() {

	return contenutoFileB64;
    }

    public void setContenutoFileB64(String contenutoFileB64) {

	this.contenutoFileB64 = contenutoFileB64;
    }

    public List<Rata> getRate() {

	if (this.rate == null) {
	    this.rate = new ArrayList<Rata>();
	}
	return rate;
    }
}
