package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "posizione_debitoria")
@XmlType(name = "posizione_debitoria", propOrder = { "codiceFiscaleEnte", "causale", "tipoPagamento", "importo", "nome", "cognome", "ragioneSociale",
	"codiceFiscalePartitaIVAPagatore", "email", "identificativoPagamento", "componentiPagamento" })
public class GetIUVChiamanteEsternoRequest {

    @XmlElement(name = "codiceFiscaleEnte", required = true)
    private String codiceFiscaleEnte;
    @XmlElement(name = "causale", required = true)
    private String causale;
    @XmlElement(name = "tipoPagamento", required = true)
    private String tipoPagamento;
    @XmlElement(name = "nome", required = false)
    private String nome;
    @XmlElement(name = "cognome", required = false)
    private String cognome;
    @XmlElement(name = "ragioneSociale", required = false)
    private String ragioneSociale;
    @XmlElement(name = "codiceFiscalePartitaIVAPagatore", required = true)
    private String codiceFiscalePartitaIVAPagatore;
    @XmlElement(name = "email", required = false)
    private String email;
    @XmlElement(name = "identificativoPagamento", required = true)
    private String identificativoPagamento;
    @XmlElement(name = "componentiPagamento", required = false)
    private Set<ComponentePagamentoType> componentiPagamento;

    public String getCodiceFiscaleEnte() {

	return codiceFiscaleEnte;
    }

    public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {

	this.codiceFiscaleEnte = codiceFiscaleEnte;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public String getTipoPagamento() {

	return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {

	this.tipoPagamento = tipoPagamento;
    }

    @XmlElement(name = "importo", required = true)
    public BigDecimal getImporto() {

	if (this.componentiPagamento == null) {
	    return BigDecimal.ONE;
	}
	BigDecimal retVal = BigDecimal.ZERO;
	for (ComponentePagamentoType componente : this.componentiPagamento) {
	    retVal = retVal.add(componente.getImporto());
	}
	return retVal;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCognome() {

	return cognome;
    }

    public void setCognome(String cognome) {

	this.cognome = cognome;
    }

    public String getRagioneSociale() {

	return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
    }

    public String getCodiceFiscalePartitaIVAPagatore() {

	return codiceFiscalePartitaIVAPagatore;
    }

    public void setCodiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {

	this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getIdentificativoPagamento() {

	return identificativoPagamento;
    }

    public void setIdentificativoPagamento(String identificativoPagamento) {

	this.identificativoPagamento = identificativoPagamento;
    }

    public Set<ComponentePagamentoType> getComponentiPagamento() {

	if (this.componentiPagamento == null) {
	    this.componentiPagamento = new HashSet<>();
	}
	return this.componentiPagamento;
    }

    public void setComponentiPagamento(Set<ComponentePagamentoType> componentiPagamento) {

	this.componentiPagamento = componentiPagamento;
    }
}
