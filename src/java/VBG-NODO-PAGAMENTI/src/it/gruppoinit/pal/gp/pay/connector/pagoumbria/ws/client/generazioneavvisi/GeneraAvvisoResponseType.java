package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per GeneraAvvisoResponseType complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GeneraAvvisoResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://idp.tasgroup.it/GenerazioneAvvisi/}ResponseBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Body" type="{http://idp.tasgroup.it/GenerazioneAvvisi/}GeneraAvvisoResponseBodyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraAvvisoResponseType", propOrder = { "body" })
public class GeneraAvvisoResponseType extends ResponseBase {

    @XmlElement(name = "Body")
    protected GeneraAvvisoResponseBodyType body;

    /**
     * Recupera il valore della proprietà body.
     * 
     * @return possible object is {@link GeneraAvvisoResponseBodyType }
     * 
     */
    public GeneraAvvisoResponseBodyType getBody() {

	return body;
    }

    /**
     * Imposta il valore della proprietà body.
     * 
     * @param value
     *            allowed object is {@link GeneraAvvisoResponseBodyType }
     * 
     */
    public void setBody(GeneraAvvisoResponseBodyType value) {

	this.body = value;
    }
}
