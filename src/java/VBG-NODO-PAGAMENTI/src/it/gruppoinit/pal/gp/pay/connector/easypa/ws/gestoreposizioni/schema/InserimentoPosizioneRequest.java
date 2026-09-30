
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per inserimentoPosizioneRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="inserimentoPosizioneRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="inserimentoPosizioneInput" type="{http://services.sia.eu/}inserimentoPosizioneInputType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserimentoPosizioneRequest", propOrder = {
    "inserimentoPosizioneInput"
})
public class InserimentoPosizioneRequest {

    @XmlElement(required = true)
    protected InserimentoPosizioneInputType inserimentoPosizioneInput;

    /**
     * Recupera il valore della proprietà inserimentoPosizioneInput.
     * 
     * @return
     *     possible object is
     *     {@link InserimentoPosizioneInputType }
     *     
     */
    public InserimentoPosizioneInputType getInserimentoPosizioneInput() {
        return inserimentoPosizioneInput;
    }

    /**
     * Imposta il valore della proprietà inserimentoPosizioneInput.
     * 
     * @param value
     *     allowed object is
     *     {@link InserimentoPosizioneInputType }
     *     
     */
    public void setInserimentoPosizioneInput(InserimentoPosizioneInputType value) {
        this.inserimentoPosizioneInput = value;
    }

}
