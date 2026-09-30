package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlRootElement
public class DebtPositionData implements IDebtPositionData{

    @XmlElement
    private String causale;
    @XmlElement
    private String codiceFiscalePartitaIVAPagatore;
    @XmlElement
    private String cognome;
    @XmlElement(name = "componentiPagamento", required = false)
    private Set<ComponentePagamentoType> componentiPagamento;
    @XmlElement(name = "dataFineValidita")
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar dataFineValidita;
    @XmlElement(name = "dataInizioValidita")
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar dataInizioValidita;
    @XmlElement(name = "dataScadenza")
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar dataScadenza;
    @XmlElement
    private String email;
    @XmlElement
    private String identificativoPagamento;
    @XmlElement
    private BigDecimal importo;
    @XmlElement
    private String nome;
    @XmlElement
    private String notePerIlPagatore;
    @XmlElement
    private String ragioneSociale;
    @XmlElement
    private Boolean requiresCostUpdate;

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public String getCodiceFiscalePartitaIVAPagatore() {

	return codiceFiscalePartitaIVAPagatore;
    }

    public void setCodiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {

	this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
    }

    public String getCognome() {

	return cognome;
    }

    public void setCognome(String cognome) {

	this.cognome = cognome;
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

    public XMLGregorianCalendar getDataFineValidita() {

	return dataFineValidita;
    }

    public void setDataFineValidita(XMLGregorianCalendar dataFineValidita) {

	this.dataFineValidita = dataFineValidita;
    }

    public XMLGregorianCalendar getDataInizioValidita() {

	return dataInizioValidita;
    }

    public void setDataInizioValidita(XMLGregorianCalendar dataInizioValidita) {

	this.dataInizioValidita = dataInizioValidita;
    }

    public XMLGregorianCalendar getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(XMLGregorianCalendar dataScadenza) {

	this.dataScadenza = dataScadenza;
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

    public BigDecimal getImporto() {

	if (this.componentiPagamento == null) {
	    return BigDecimal.ONE;
	}
	BigDecimal retVal = BigDecimal.ZERO;
	for (ComponentePagamentoType componente : this.componentiPagamento) {
	    retVal = retVal.add(componente.getImporto());
	}
	this.importo = retVal;
	return retVal;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getNotePerIlPagatore() {

	return notePerIlPagatore;
    }

    public void setNotePerIlPagatore(String notePerIlPagatore) {

	this.notePerIlPagatore = notePerIlPagatore;
    }

    public String getRagioneSociale() {

	return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
    }

    public Boolean getRequiresCostUpdate() {

	return requiresCostUpdate;
    }

    public void setRequiresCostUpdate(Boolean requiresCostUpdate) {

	this.requiresCostUpdate = requiresCostUpdate;
    }
}
