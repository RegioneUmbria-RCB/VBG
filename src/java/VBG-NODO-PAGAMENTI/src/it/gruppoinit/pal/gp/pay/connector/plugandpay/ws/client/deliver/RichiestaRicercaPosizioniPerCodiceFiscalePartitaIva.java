
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/PnP/PlugAndPayDeliver}DeliverAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="StatoPosizione" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}StatoPosizioneFilter"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva", propOrder = {
    "codiceEnte",
    "codiceFiscale",
    "statoPosizione"
})
public class RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva
    extends DeliverAuthenticatedRequestBase
{

    @XmlElement(name = "CodiceEnte", required = true, nillable = true)
    protected String codiceEnte;
    @XmlElement(name = "CodiceFiscale", required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(name = "StatoPosizione", required = true)
    @XmlSchemaType(name = "string")
    protected StatoPosizioneFilter statoPosizione;

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà statoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link StatoPosizioneFilter }
     *     
     */
    public StatoPosizioneFilter getStatoPosizione() {
        return statoPosizione;
    }

    /**
     * Imposta il valore della proprietà statoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPosizioneFilter }
     *     
     */
    public void setStatoPosizione(StatoPosizioneFilter value) {
        this.statoPosizione = value;
    }

}
