package it.gruppoinit.pal.gp.backoffice.schemas.messages.base;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for EsitoOperazioneType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EsitoOperazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="listaErrori" type="{http://gruppoinit.it/sigepro/schemas/messages/base}ErroreBackofficeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoOperazioneType", propOrder = { "esito", "listaErrori" })
public class EsitoOperazioneType {

    protected int esito;
    protected List<ErroreBackofficeType> listaErrori = new ArrayList<ErroreBackofficeType>();

    public EsitoOperazioneType() {

    }

    public EsitoOperazioneType(int esito) {

	this.esito = esito;
    }

    public EsitoOperazioneType(int esito, String errore) {

	this.esito = esito;
	this.listaErrori = new ArrayList<ErroreBackofficeType>();
	this.listaErrori.add(new ErroreBackofficeType("1", errore));
    }

    public EsitoOperazioneType(int esito, ErroreBackofficeType errore) {

	this.esito = esito;
	this.listaErrori = new ArrayList<ErroreBackofficeType>();
	this.listaErrori.add(errore);
    }

    public EsitoOperazioneType(int esito, List<ErroreBackofficeType> listaErrori) {

	this.esito = esito;
	this.listaErrori = listaErrori;
    }

    public EsitoOperazioneType(ErroreBackofficeType errore) {

	this.esito = 0;
	this.listaErrori.add(errore);
    }

    /**
     * Gets the value of the esito property.
     * 
     */
    public int getEsito() {

	return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     */
    public void setEsito(int value) {

	this.esito = value;
    }

    /**
     * Gets the value of the listaErrori property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the listaErrori property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getListaErrori().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ErroreBackofficeType }
     * 
     * 
     */
    public List<ErroreBackofficeType> getListaErrori() {

	if (listaErrori == null) {
	    listaErrori = new ArrayList<ErroreBackofficeType>();
	}
	return this.listaErrori;
    }
}
