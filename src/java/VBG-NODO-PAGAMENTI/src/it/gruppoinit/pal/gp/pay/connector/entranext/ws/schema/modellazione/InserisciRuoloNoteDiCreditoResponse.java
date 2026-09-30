
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciRuoloNoteDiCreditoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciRuoloNoteDiCreditoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NoteDiCreditoResult" type="{http://entranext.it/}NotaDiCreditoResult" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RuoloNoteDiCredito" type="{http://entranext.it/}RuoloNoteDiCreditoResult" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciRuoloNoteDiCreditoResponse", propOrder = {
    "noteDiCreditoResult",
    "ruoloNoteDiCredito"
})
public class InserisciRuoloNoteDiCreditoResponse
    extends LinkNextResponse
{

    @XmlElement(name = "NoteDiCreditoResult")
    protected List<NotaDiCreditoResult> noteDiCreditoResult;
    @XmlElement(name = "RuoloNoteDiCredito")
    protected RuoloNoteDiCreditoResult ruoloNoteDiCredito;

    /**
     * Gets the value of the noteDiCreditoResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noteDiCreditoResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNoteDiCreditoResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NotaDiCreditoResult }
     * 
     * 
     */
    public List<NotaDiCreditoResult> getNoteDiCreditoResult() {
        if (noteDiCreditoResult == null) {
            noteDiCreditoResult = new ArrayList<NotaDiCreditoResult>();
        }
        return this.noteDiCreditoResult;
    }

    /**
     * Recupera il valore della proprietà ruoloNoteDiCredito.
     * 
     * @return
     *     possible object is
     *     {@link RuoloNoteDiCreditoResult }
     *     
     */
    public RuoloNoteDiCreditoResult getRuoloNoteDiCredito() {
        return ruoloNoteDiCredito;
    }

    /**
     * Imposta il valore della proprietà ruoloNoteDiCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloNoteDiCreditoResult }
     *     
     */
    public void setRuoloNoteDiCredito(RuoloNoteDiCreditoResult value) {
        this.ruoloNoteDiCredito = value;
    }

}
