
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RichiestaCaricaPosizione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaCaricaPosizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/PnP/PlugAndPayFeed}FeedAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Posizione" type="{http://e-fil.eu/PnP/PlugAndPayFeed}Posizione"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaCaricaPosizione", propOrder = {
    "posizione"
})
public class RichiestaCaricaPosizione
    extends FeedAuthenticatedRequestBase
{

    @XmlElement(name = "Posizione", required = true, nillable = true)
    protected Posizione posizione;

    /**
     * Recupera il valore della proprietà posizione.
     * 
     * @return
     *     possible object is
     *     {@link Posizione }
     *     
     */
    public Posizione getPosizione() {
        return posizione;
    }

    /**
     * Imposta il valore della proprietà posizione.
     * 
     * @param value
     *     allowed object is
     *     {@link Posizione }
     *     
     */
    public void setPosizione(Posizione value) {
        this.posizione = value;
    }

}
