/*
 * MIP TO SG API - WS Conversion Libreria API del ***SG*** per il Modulo Incassi e Pagamenti del Comune di Genova
 *
 * OpenAPI spec version: 0.5 Contact: ....@fornitore.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * NotificaStatoPagamentoDati
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificaStatoPagamentoDati", propOrder = { "idEnte", "idPortale", "numeroOperazione", "codiceAvviso", "datiDebitore",
	"istitutoAttestante", "datiPagamento" })
public class NotificaStatoPagamentoDati {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "IDPortale")
    private String idPortale = null;
    @XmlElement(name = "NumeroOperazione")
    private String numeroOperazione = null;
    @XmlElement(name = "CodiceAvviso")
    private String codiceAvviso = null;
    @XmlElement(name = "DatiDebitore")
    private DatiDebitore datiDebitore = null;
    @XmlElement(name = "IstitutoAttestante")
    private IstitutoAttestante istitutoAttestante = null;
    @XmlElement(name = "DatiPagamento")
    private DatiPagamento datiPagamento = null;

    public NotificaStatoPagamentoDati idEnte(String idEnte) {

	this.idEnte = idEnte;
	return this;
    }

    /**
     * Get idEnte
     * 
     * @return idEnte
     **/
    public String getIdEnte() {

	return idEnte;
    }

    public void setIdEnte(String idEnte) {

	this.idEnte = idEnte;
    }

    public NotificaStatoPagamentoDati idPortale(String idPortale) {

	this.idPortale = idPortale;
	return this;
    }

    /**
     * Get idPortale
     * 
     * @return idPortale
     **/
    public String getIdPortale() {

	return idPortale;
    }

    public void setIdPortale(String idPortale) {

	this.idPortale = idPortale;
    }

    public NotificaStatoPagamentoDati numeroOperazione(String numeroOperazione) {

	this.numeroOperazione = numeroOperazione;
	return this;
    }

    /**
     * Get numeroOperazione
     * 
     * @return numeroOperazione
     **/
    public String getNumeroOperazione() {

	return numeroOperazione;
    }

    public void setNumeroOperazione(String numeroOperazione) {

	this.numeroOperazione = numeroOperazione;
    }

    public NotificaStatoPagamentoDati codiceAvviso(String codiceAvviso) {

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

    public NotificaStatoPagamentoDati datiDebitore(DatiDebitore datiDebitore) {

	this.datiDebitore = datiDebitore;
	return this;
    }

    /**
     * Get datiDebitore
     * 
     * @return datiDebitore
     **/
    public DatiDebitore getDatiDebitore() {

	return datiDebitore;
    }

    public void setDatiDebitore(DatiDebitore datiDebitore) {

	this.datiDebitore = datiDebitore;
    }

    public NotificaStatoPagamentoDati istitutoAttestante(IstitutoAttestante istitutoAttestante) {

	this.istitutoAttestante = istitutoAttestante;
	return this;
    }

    /**
     * Get istitutoAttestante
     * 
     * @return istitutoAttestante
     **/
    public IstitutoAttestante getIstitutoAttestante() {

	return istitutoAttestante;
    }

    public void setIstitutoAttestante(IstitutoAttestante istitutoAttestante) {

	this.istitutoAttestante = istitutoAttestante;
    }

    public NotificaStatoPagamentoDati datiPagamento(DatiPagamento datiPagamento) {

	this.datiPagamento = datiPagamento;
	return this;
    }

    /**
     * Get datiPagamento
     * 
     * @return datiPagamento
     **/
    public DatiPagamento getDatiPagamento() {

	return datiPagamento;
    }

    public void setDatiPagamento(DatiPagamento datiPagamento) {

	this.datiPagamento = datiPagamento;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NotificaStatoPagamentoDati notificaStatoPagamentoDati = (NotificaStatoPagamentoDati) o;
	return Objects.equals(this.idEnte, notificaStatoPagamentoDati.idEnte) && Objects.equals(this.idPortale, notificaStatoPagamentoDati.idPortale)
		&& Objects.equals(this.numeroOperazione, notificaStatoPagamentoDati.numeroOperazione)
		&& Objects.equals(this.codiceAvviso, notificaStatoPagamentoDati.codiceAvviso)
		&& Objects.equals(this.datiDebitore, notificaStatoPagamentoDati.datiDebitore)
		&& Objects.equals(this.istitutoAttestante, notificaStatoPagamentoDati.istitutoAttestante)
		&& Objects.equals(this.datiPagamento, notificaStatoPagamentoDati.datiPagamento);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, idPortale, numeroOperazione, codiceAvviso, datiDebitore, istitutoAttestante, datiPagamento);
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
