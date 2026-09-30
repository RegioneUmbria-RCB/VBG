
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per inserimentoPosizioneResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="inserimentoPosizioneResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="inserimentoPosizioneOutput" type="{http://services.sia.eu/}inserimentoPosizioneOutputType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserimentoPosizioneResponse", propOrder = {
    "inserimentoPosizioneOutput"
})
public class InserimentoPosizioneResponse {

    @XmlElement(required = true)
    protected InserimentoPosizioneOutputType inserimentoPosizioneOutput;

    /**
     * Recupera il valore della proprietà inserimentoPosizioneOutput.
     * 
     * @return
     *     possible object is
     *     {@link InserimentoPosizioneOutputType }
     *     
     */
    public InserimentoPosizioneOutputType getInserimentoPosizioneOutput() {
        return inserimentoPosizioneOutput;
    }

    /**
     * Imposta il valore della proprietà inserimentoPosizioneOutput.
     * 
     * @param value
     *     allowed object is
     *     {@link InserimentoPosizioneOutputType }
     *     
     */
    public void setInserimentoPosizioneOutput(InserimentoPosizioneOutputType value) {
        this.inserimentoPosizioneOutput = value;
    }

}
