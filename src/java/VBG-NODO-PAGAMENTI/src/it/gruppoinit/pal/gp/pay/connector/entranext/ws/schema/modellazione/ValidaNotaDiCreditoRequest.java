
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ValidaNotaDiCreditoRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ValidaNotaDiCreditoRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NotaDiCredito" type="{http://entranext.it/}PosizioniNoteDiCredito" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidaNotaDiCreditoRequest", propOrder = {
    "notaDiCredito"
})
public class ValidaNotaDiCreditoRequest
    extends LinkNextRequest
{

    @XmlElement(name = "NotaDiCredito")
    protected PosizioniNoteDiCredito notaDiCredito;

    /**
     * Recupera il valore della proprietà notaDiCredito.
     * 
     * @return
     *     possible object is
     *     {@link PosizioniNoteDiCredito }
     *     
     */
    public PosizioniNoteDiCredito getNotaDiCredito() {
        return notaDiCredito;
    }

    /**
     * Imposta il valore della proprietà notaDiCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioniNoteDiCredito }
     *     
     */
    public void setNotaDiCredito(PosizioniNoteDiCredito value) {
        this.notaDiCredito = value;
    }

}
