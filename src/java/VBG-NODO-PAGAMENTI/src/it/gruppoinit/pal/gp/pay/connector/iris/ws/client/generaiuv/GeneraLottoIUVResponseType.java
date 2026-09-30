
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generaiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GeneraLottoIUVResponseType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GeneraLottoIUVResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://idp.tasgroup.it/GenerazioneIUV/}ResponseBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Body" type="{http://idp.tasgroup.it/GenerazioneIUV/}GeneraLottoIUVResponseBodyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraLottoIUVResponseType", propOrder = {
    "body"
})
public class GeneraLottoIUVResponseType
    extends ResponseBase
{

    @XmlElement(name = "Body")
    protected GeneraLottoIUVResponseBodyType body;

    /**
     * Recupera il valore della proprietà body.
     * 
     * @return
     *     possible object is
     *     {@link GeneraLottoIUVResponseBodyType }
     *     
     */
    public GeneraLottoIUVResponseBodyType getBody() {
        return body;
    }

    /**
     * Imposta il valore della proprietà body.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneraLottoIUVResponseBodyType }
     *     
     */
    public void setBody(GeneraLottoIUVResponseBodyType value) {
        this.body = value;
    }

}
