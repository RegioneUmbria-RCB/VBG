
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <pClasse Java per EsitoInvioCarrelloPosizioni complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="EsitoInvioCarrelloPosizioni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ErroriDiValidazione" type="{http://e-fil.eu/PnP/PlugAndPayPayment}ArrayOfErroreDiValidazionePosizione" minOccurs="0"/&gt;
 *         &lt;element name="Esito" type="{http://e-fil.eu/PnP/PlugAndPayPayment}Esito" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoPosizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoInvioCarrelloPosizioni", propOrder = {
    "codiceRiferimentoCreditore",
    "erroriDiValidazione",
    "esito",
    "identificativoPosizione",
    "tipoRiferimentoCreditore"
})
public class EsitoInvioCarrelloPosizioni {

    @XmlElement(name = "CodiceRiferimentoCreditore", required = false)
    protected String codiceRiferimentoCreditore;
    @XmlElement(name = "ErroriDiValidazione", required = false)
    protected ArrayOfErroreDiValidazionePosizione erroriDiValidazione;
    @XmlElement(name = "Esito")
    @XmlSchemaType(name = "string")
    protected Esito esito;
    @XmlElement(name = "IdentificativoPosizione", required = false)
    protected String identificativoPosizione;
    @XmlElement(name = "TipoRiferimentoCreditore", required = false)
    protected String tipoRiferimentoCreditore;

    /**
     * Recupera il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCodiceRiferimentoCreditore() {
        return codiceRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCodiceRiferimentoCreditore(String value) {
        this.codiceRiferimentoCreditore = value;
    }

    /**
     * Recupera il valore della proprietà erroriDiValidazione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code }
     *     
     */
    public ArrayOfErroreDiValidazionePosizione getErroriDiValidazione() {
        return erroriDiValidazione;
    }

    /**
     * Imposta il valore della proprietà erroriDiValidazione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code }
     *     
     */
    public void setErroriDiValidazione(ArrayOfErroreDiValidazionePosizione value) {
        this.erroriDiValidazione = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link Esito }
     *     
     */
    public Esito getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link Esito }
     *     
     */
    public void setEsito(Esito value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getIdentificativoPosizione() {
        return identificativoPosizione;
    }

    /**
     * Imposta il valore della proprietà identificativoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIdentificativoPosizione(String value) {
        this.identificativoPosizione = value;
    }

    /**
     * Recupera il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getTipoRiferimentoCreditore() {
        return tipoRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setTipoRiferimentoCreditore(String value) {
        this.tipoRiferimentoCreditore = value;
    }

}
