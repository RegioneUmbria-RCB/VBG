
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RichiestaValidaPosizioni complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaValidaPosizioni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/PnP/PlugAndPayFeed}FeedAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Posizioni" type="{http://e-fil.eu/PnP/PlugAndPayFeed}ArrayOfPosizione"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaValidaPosizioni", propOrder = {
    "posizioni"
})
public class RichiestaValidaPosizioni
    extends FeedAuthenticatedRequestBase
{

    @XmlElement(name = "Posizioni", required = true, nillable = true)
    protected ArrayOfPosizione posizioni;

    /**
     * Recupera il valore della proprietà posizioni.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPosizione }
     *     
     */
    public ArrayOfPosizione getPosizioni() {
        return posizioni;
    }

    /**
     * Imposta il valore della proprietà posizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPosizione }
     *     
     */
    public void setPosizioni(ArrayOfPosizione value) {
        this.posizioni = value;
    }

}
