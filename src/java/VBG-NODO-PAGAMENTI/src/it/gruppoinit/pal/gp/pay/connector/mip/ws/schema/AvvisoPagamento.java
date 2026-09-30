/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.LocalDate;

/**
 * Risorsa che rappresenta l&#x27;avviso di pagamento
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AvvisoPagamento", propOrder = { "debitore", "codiceAvviso", "importo", "servizio", "anno", "causale", "dataCreazioneAvviso",
	"numeroDocumento", "pdfAvviso", "attualizzato" })
public class AvvisoPagamento {

    @XmlElement(name = "debitore")
    private String debitore = null;
    @XmlElement(name = "codiceAvviso")
    private String codiceAvviso = null;
    @XmlElement(name = "importo")
    private Double importo = null;
    @XmlElement(name = "servizio")
    private String servizio = null;
    @XmlElement(name = "anno")
    private Long anno = null;
    @XmlElement(name = "causale")
    private String causale = null;
    @XmlElement(name = "dataCreazioneAvviso")
    private LocalDate dataCreazioneAvviso = null;
    @XmlElement(name = "numeroDocumento")
    private String numeroDocumento = null;
    @XmlElement(name = "pdfAvviso")
    private FileAllegato pdfAvviso = null;
    @XmlElement(name = "attualizzato")
    private Attualizzato attualizzato = null;

    public AvvisoPagamento debitore(String debitore) {

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

    public AvvisoPagamento codiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
	return this;
    }

    /**
     * Get codiceAvviso
     * 
     * @return codiceAvviso
     **/
    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public AvvisoPagamento importo(Double importo) {

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

    public AvvisoPagamento servizio(String servizio) {

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

    public AvvisoPagamento anno(Long anno) {

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

    public AvvisoPagamento causale(String causale) {

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

    public AvvisoPagamento dataCreazioneAvviso(LocalDate dataCreazioneAvviso) {

	this.dataCreazioneAvviso = dataCreazioneAvviso;
	return this;
    }

    /**
     * Get dataCreazioneAvviso
     * 
     * @return dataCreazioneAvviso
     **/
    public LocalDate getDataCreazioneAvviso() {

	return dataCreazioneAvviso;
    }

    public void setDataCreazioneAvviso(LocalDate dataCreazioneAvviso) {

	this.dataCreazioneAvviso = dataCreazioneAvviso;
    }

    public AvvisoPagamento numeroDocumento(String numeroDocumento) {

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

    public AvvisoPagamento pdfAvviso(FileAllegato pdfAvviso) {

	this.pdfAvviso = pdfAvviso;
	return this;
    }

    /**
     * Get pdfAvviso
     * 
     * @return pdfAvviso
     **/
    public FileAllegato getPdfAvviso() {

	return pdfAvviso;
    }

    public void setPdfAvviso(FileAllegato pdfAvviso) {

	this.pdfAvviso = pdfAvviso;
    }

    public AvvisoPagamento attualizzato(Attualizzato attualizzato) {

	this.attualizzato = attualizzato;
	return this;
    }

    /**
     * Get attualizzato
     * 
     * @return attualizzato
     **/
    public Attualizzato getAttualizzato() {

	return attualizzato;
    }

    public void setAttualizzato(Attualizzato attualizzato) {

	this.attualizzato = attualizzato;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	AvvisoPagamento avvisoPagamento = (AvvisoPagamento) o;
	return Objects.equals(this.debitore, avvisoPagamento.debitore) && Objects.equals(this.codiceAvviso, avvisoPagamento.codiceAvviso)
		&& Objects.equals(this.importo, avvisoPagamento.importo) && Objects.equals(this.servizio, avvisoPagamento.servizio)
		&& Objects.equals(this.anno, avvisoPagamento.anno) && Objects.equals(this.causale, avvisoPagamento.causale)
		&& Objects.equals(this.dataCreazioneAvviso, avvisoPagamento.dataCreazioneAvviso)
		&& Objects.equals(this.numeroDocumento, avvisoPagamento.numeroDocumento) && Objects.equals(this.pdfAvviso, avvisoPagamento.pdfAvviso)
		&& Objects.equals(this.attualizzato, avvisoPagamento.attualizzato);
    }

    @Override
    public int hashCode() {

	return Objects.hash(debitore, codiceAvviso, importo, servizio, anno, causale, dataCreazioneAvviso, numeroDocumento, pdfAvviso, attualizzato);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AvvisoPagamento {\n");
	sb.append("    debitore: ").append(toIndentedString(debitore)).append("\n");
	sb.append("    codiceAvviso: ").append(toIndentedString(codiceAvviso)).append("\n");
	sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
	sb.append("    servizio: ").append(toIndentedString(servizio)).append("\n");
	sb.append("    anno: ").append(toIndentedString(anno)).append("\n");
	sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
	sb.append("    dataCreazioneAvviso: ").append(toIndentedString(dataCreazioneAvviso)).append("\n");
	sb.append("    numeroDocumento: ").append(toIndentedString(numeroDocumento)).append("\n");
	sb.append("    pdfAvviso: ").append(toIndentedString(pdfAvviso)).append("\n");
	sb.append("    attualizzato: ").append(toIndentedString(attualizzato)).append("\n");
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
