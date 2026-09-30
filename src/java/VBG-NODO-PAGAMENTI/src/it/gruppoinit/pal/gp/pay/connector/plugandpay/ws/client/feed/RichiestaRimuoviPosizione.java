
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RichiestaRimuoviPosizione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaRimuoviPosizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/PnP/PlugAndPayFeed}FeedAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodiceIdentificativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaRimuoviPosizione", propOrder = {
    "codiceEnte",
    "codiceIdentificativo"
})
public class RichiestaRimuoviPosizione
    extends FeedAuthenticatedRequestBase
{

    @XmlElement(name = "CodiceEnte", required = true, nillable = true)
    protected String codiceEnte;
    @XmlElement(name = "CodiceIdentificativo", required = true, nillable = true)
    protected String codiceIdentificativo;

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
     * Recupera il valore della proprietà codiceIdentificativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdentificativo() {
        return codiceIdentificativo;
    }

    /**
     * Imposta il valore della proprietà codiceIdentificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdentificativo(String value) {
        this.codiceIdentificativo = value;
    }

}
