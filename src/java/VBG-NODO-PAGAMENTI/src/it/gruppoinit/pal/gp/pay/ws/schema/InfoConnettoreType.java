
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InfoConnettoreType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InfoConnettoreType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="supportaPagamentoOTF" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaInvioAvviso" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaGenerazioneFattura" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaDownloadRicevuta" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="generazioneFatturaObbligatoria" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaAttivaSessionePagamento" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaPagamentoOffLine" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaModificaDataScadenza" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaRataUnica" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="supportaCaricamentoMassivo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoConnettoreType", propOrder = {
    "nome",
    "supportaPagamentoOTF",
    "supportaInvioAvviso",
    "supportaGenerazioneFattura",
    "supportaDownloadRicevuta",
    "generazioneFatturaObbligatoria",
    "supportaAttivaSessionePagamento",
    "supportaPagamentoOffLine",
    "supportaModificaDataScadenza",
    "supportaRataUnica",
    "supportaCaricamentoMassivo",
    "supportaModificaDataFineValidita"
})
public class InfoConnettoreType {

    @XmlElement(required = true)
    protected String nome;
    protected boolean supportaPagamentoOTF;
    protected boolean supportaInvioAvviso;
    protected boolean supportaGenerazioneFattura;
    protected boolean supportaDownloadRicevuta;
    protected boolean generazioneFatturaObbligatoria;
    protected boolean supportaAttivaSessionePagamento;
    protected boolean supportaPagamentoOffLine;
    protected boolean supportaModificaDataScadenza;
    protected boolean supportaRataUnica;
    protected boolean supportaCaricamentoMassivo;
    protected boolean supportaModificaDataFineValidita;

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietà supportaPagamentoOTF.
     * 
     */
    public boolean isSupportaPagamentoOTF() {
        return supportaPagamentoOTF;
    }

    /**
     * Imposta il valore della proprietà supportaPagamentoOTF.
     * 
     */
    public void setSupportaPagamentoOTF(boolean value) {
        this.supportaPagamentoOTF = value;
    }

    /**
     * Recupera il valore della proprietà supportaInvioAvviso.
     * 
     */
    public boolean isSupportaInvioAvviso() {
        return supportaInvioAvviso;
    }

    /**
     * Imposta il valore della proprietà supportaInvioAvviso.
     * 
     */
    public void setSupportaInvioAvviso(boolean value) {
        this.supportaInvioAvviso = value;
    }

    /**
     * Recupera il valore della proprietà supportaGenerazioneFattura.
     * 
     */
    public boolean isSupportaGenerazioneFattura() {
        return supportaGenerazioneFattura;
    }

    /**
     * Imposta il valore della proprietà supportaGenerazioneFattura.
     * 
     */
    public void setSupportaGenerazioneFattura(boolean value) {
        this.supportaGenerazioneFattura = value;
    }

    /**
     * Recupera il valore della proprietà supportaDownloadRicevuta.
     * 
     */
    public boolean isSupportaDownloadRicevuta() {
        return supportaDownloadRicevuta;
    }

    /**
     * Imposta il valore della proprietà supportaDownloadRicevuta.
     * 
     */
    public void setSupportaDownloadRicevuta(boolean value) {
        this.supportaDownloadRicevuta = value;
    }

    /**
     * Recupera il valore della proprietà generazioneFatturaObbligatoria.
     * 
     */
    public boolean isGenerazioneFatturaObbligatoria() {
        return generazioneFatturaObbligatoria;
    }

    /**
     * Imposta il valore della proprietà generazioneFatturaObbligatoria.
     * 
     */
    public void setGenerazioneFatturaObbligatoria(boolean value) {
        this.generazioneFatturaObbligatoria = value;
    }

    /**
     * Recupera il valore della proprietà supportaAttivaSessionePagamento.
     * 
     */
    public boolean isSupportaAttivaSessionePagamento() {
        return supportaAttivaSessionePagamento;
    }

    /**
     * Imposta il valore della proprietà supportaAttivaSessionePagamento.
     * 
     */
    public void setSupportaAttivaSessionePagamento(boolean value) {
        this.supportaAttivaSessionePagamento = value;
    }

    /**
     * Recupera il valore della proprietà supportaPagamentoOffLine.
     * 
     */
    public boolean isSupportaPagamentoOffLine() {
        return supportaPagamentoOffLine;
    }

    /**
     * Imposta il valore della proprietà supportaPagamentoOffLine.
     * 
     */
    public void setSupportaPagamentoOffLine(boolean value) {
        this.supportaPagamentoOffLine = value;
    }

    /**
     * Recupera il valore della proprietà supportaModificaDataScadenza.
     * 
     */
    public boolean isSupportaModificaDataScadenza() {
        return supportaModificaDataScadenza;
    }

    /**
     * Imposta il valore della proprietà supportaModificaDataScadenza.
     * 
     */
    public void setSupportaModificaDataScadenza(boolean value) {
        this.supportaModificaDataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà supportaRataUnica.
     * 
     */
    public boolean isSupportaRataUnica() {
        return supportaRataUnica;
    }

    /**
     * Imposta il valore della proprietà supportaRataUnica.
     * 
     */
    public void setSupportaRataUnica(boolean value) {

	this.supportaRataUnica = value;
    }

    /**
     * Recupera il valore della proprietà supportaRataUnica.
     * 
     */
    public boolean isSupportaCaricamentoMassivo() {

	return supportaCaricamentoMassivo;
    }

    /**
     * Imposta il valore della proprietà supportaCaricamentoMassivo.
     * 
     */
    public void setSupportaCaricamentoMassivo(boolean supportaCaricamentoMassivo) {

	this.supportaCaricamentoMassivo = supportaCaricamentoMassivo;
    }

    /**
     * Recupera il valore della proprietà supportaModificaDataFineValidita.
     * 
     */
	public boolean isSupportaModificaDataFineValidita() {
		return supportaModificaDataFineValidita;
	}

	/**
     * Imposta il valore della proprietà supportaModificaDataFineValidita.
     * 
     */
	public void setSupportaModificaDataFineValidita(boolean supportaModificaDataFineValidita) {
		this.supportaModificaDataFineValidita = supportaModificaDataFineValidita;
	}
    
    
}
