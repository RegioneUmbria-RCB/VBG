
package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="intExecuteDistantResult" type="{http://easybridge.eu/bridge/}Output" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "intExecuteDistantResult"
})
@XmlRootElement(name = "intExecuteDistantResponse")
public class IntExecuteDistantResponse {

    protected Output intExecuteDistantResult;

    /**
     * Recupera il valore della proprietà intExecuteDistantResult.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getIntExecuteDistantResult() {
        return intExecuteDistantResult;
    }

    /**
     * Imposta il valore della proprietà intExecuteDistantResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setIntExecuteDistantResult(Output value) {
        this.intExecuteDistantResult = value;
    }

}
