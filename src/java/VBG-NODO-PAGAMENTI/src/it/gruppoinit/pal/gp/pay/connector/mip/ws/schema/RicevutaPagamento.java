/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * RicevutaPagamento
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RicevutaPagamento", propOrder = { "debitore", "esitoPagamento", "importo", "anno", "numeroDocumento", "causale", "dataPagamento",
	"iuv", "codiceAvviso", "pdfRicevuta" })
public class RicevutaPagamento {

    @XmlElement(name = "debitore")
    private String debitore = null;
    @XmlElement(name = "esitoPagamento")
    private EsitoPagamento esitoPagamento = null;
    @XmlElement(name = "importo")
    private Double importo = null;
    @XmlElement(name = "servizio")
    private String servizio = null;
    @XmlElement(name = "anno")
    private Long anno = null;
    @XmlElement(name = "numeroDocumento")
    private String numeroDocumento = null;
    @XmlElement(name = "causale")
    private String causale = null;
    @XmlElement(name = "dataPagamento")
    private Date dataPagamento = null;
    @XmlElement(name = "iuv")
    private String iuv = null;
    @XmlElement(name = "codiceAvviso")
    private String codiceAvviso = null;
    @XmlElement(name = "pdfRicevuta")
    private FileAllegato pdfRicevuta = null;

    public RicevutaPagamento debitore(String debitore) {

	this.debitore = debitore;
	return this;
    }

    /**
     * Get debitore
     * 
     * @return debitore
     **/
    public String getDebitore() {

	return debitore;
    }

    public void setDebitore(String debitore) {

	this.debitore = debitore;
    }

    public RicevutaPagamento esitoPagamento(EsitoPagamento esitoPagamento) {

	this.esitoPagamento = esitoPagamento;
	return this;
    }

    /**
     * Get esitoPagamento
     * 
     * @return esitoPagamento
     **/
    public EsitoPagamento getEsitoPagamento() {

	return esitoPagamento;
    }

    public void setEsitoPagamento(EsitoPagamento esitoPagamento) {

	this.esitoPagamento = esitoPagamento;
    }

    public RicevutaPagamento importo(Double importo) {

	this.importo = importo;
	return this;
    }

    /**
     * Get importo
     * 
     * @return importo
     **/
    public Double getImporto() {

	return importo;
    }

    public void setImporto(Double importo) {

	this.importo = importo;
    }

    public RicevutaPagamento servizio(String servizio) {

	this.servizio = servizio;
	return this;
    }

    /**
     * nome del servizio (tipologia entrata)
     * 
     * @return servizio
     **/
    public String getServizio() {

	return servizio;
    }

    public void setServizio(String servizio) {

	this.servizio = servizio;
    }

    public RicevutaPagamento anno(Long anno) {

	this.anno = anno;
	return this;
    }

    /**
     * Get anno
     * 
     * @return anno
     **/
    public Long getAnno() {

	return anno;
    }

    public void setAnno(Long anno) {

	this.anno = anno;
    }

    public RicevutaPagamento numeroDocumento(String numeroDocumento) {

	this.numeroDocumento = numeroDocumento;
	return this;
    }

    /**
     * Get numeroDocumento
     * 
     * @return numeroDocumento
     **/
    public String getNumeroDocumento() {

	return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {

	this.numeroDocumento = numeroDocumento;
    }

    public RicevutaPagamento causale(String causale) {

	this.causale = causale;
	return this;
    }

    /**
     * Get causale
     * 
     * @return causale
     **/
    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public RicevutaPagamento dataPagamento(Date dataPagamento) {

	this.dataPagamento = dataPagamento;
	return this;
    }

    /**
     * Get dataPagamento
     * 
     * @return dataPagamento
     **/
    public Date getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    public RicevutaPagamento iuv(String iuv) {

	this.iuv = iuv;
	return this;
    }

    /**
     * Codice IUV relativo al debito
     * 
     * @return iuv
     **/
    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public RicevutaPagamento codiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
	return this;
    }

    /**
     * identificativo univoco a livello nazionale dell’AvvisoPagamento
     * 
     * @return codiceAvviso
     **/
    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public RicevutaPagamento pdfRicevuta(FileAllegato pdfRicevuta) {

	this.pdfRicevuta = pdfRicevuta;
	return this;
    }

    /**
     * Get pdfRicevuta
     * 
     * @return pdfRicevuta
     **/
    public FileAllegato getPdfRicevuta() {

	return pdfRicevuta;
    }

    public void setPdfRicevuta(FileAllegato pdfRicevuta) {

	this.pdfRicevuta = pdfRicevuta;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	RicevutaPagamento ricevutaPagamento = (RicevutaPagamento) o;
	return Objects.equals(this.debitore, ricevutaPagamento.debitore) && Objects.equals(this.esitoPagamento, ricevutaPagamento.esitoPagamento)
		&& Objects.equals(this.importo, ricevutaPagamento.importo) && Objects.equals(this.servizio, ricevutaPagamento.servizio)
		&& Objects.equals(this.anno, ricevutaPagamento.anno) && Objects.equals(this.numeroDocumento, ricevutaPagamento.numeroDocumento)
		&& Objects.equals(this.causale, ricevutaPagamento.causale) && Objects.equals(this.dataPagamento, ricevutaPagamento.dataPagamento)
		&& Objects.equals(this.iuv, ricevutaPagamento.iuv) && Objects.equals(this.codiceAvviso, ricevutaPagamento.codiceAvviso)
		&& Objects.equals(this.pdfRicevuta, ricevutaPagamento.pdfRicevuta);
    }

    @Override
    public int hashCode() {

	return Objects.hash(debitore, esitoPagamento, importo, servizio, anno, numeroDocumento, causale, dataPagamento, iuv, codiceAvviso,
		pdfRicevuta);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class RicevutaPagamento {\n");
	sb.append("    debitore: ").append(toIndentedString(debitore)).append("\n");
	sb.append("    esitoPagamento: ").append(toIndentedString(esitoPagamento)).append("\n");
	sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
	sb.append("    servizio: ").append(toIndentedString(servizio)).append("\n");
	sb.append("    anno: ").append(toIndentedString(anno)).append("\n");
	sb.append("    numeroDocumento: ").append(toIndentedString(numeroDocumento)).append("\n");
	sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
	sb.append("    dataPagamento: ").append(toIndentedString(dataPagamento)).append("\n");
	sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
	sb.append("    codiceAvviso: ").append(toIndentedString(codiceAvviso)).append("\n");
	sb.append("    pdfRicevuta: ").append(toIndentedString(pdfRicevuta)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
