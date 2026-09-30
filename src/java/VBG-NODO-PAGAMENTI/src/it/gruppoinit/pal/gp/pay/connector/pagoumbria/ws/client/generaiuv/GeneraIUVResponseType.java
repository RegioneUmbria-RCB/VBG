
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GeneraIUVResponseType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GeneraIUVResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://idp.tasgroup.it/GenerazioneIUV/}ResponseBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Body" type="{http://idp.tasgroup.it/GenerazioneIUV/}GeneraIUVResponseBodyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraIUVResponseType", propOrder = {
    "body"
})
public class GeneraIUVResponseType
    extends ResponseBase
{

    @XmlElement(name = "Body")
    protected GeneraIUVResponseBodyType body;

    /**
     * Recupera il valore della proprietà body.
     * 
     * @return
     *     possible object is
     *     {@link GeneraIUVResponseBodyType }
     *     
     */
    public GeneraIUVResponseBodyType getBody() {
        return body;
    }

    /**
     * Imposta il valore della proprietà body.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneraIUVResponseBodyType }
     *     
     */
    public void setBody(GeneraIUVResponseBodyType value) {
        this.body = value;
    }

}
