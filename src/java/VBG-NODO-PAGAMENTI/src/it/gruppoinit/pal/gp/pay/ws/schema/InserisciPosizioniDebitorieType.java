
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}PayRequestType">
 *       &lt;sequence>
 *         &lt;element name="accorpaPosizioni" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="oggettoPagamentoUnico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registrazione" type="{http://www.paevolution.com/ws/pagamenti_types/}RegistrazioneContabileWsInType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accorpaPosizioni",
    "oggettoPagamentoUnico",
    "registrazione"
})
@XmlRootElement(name = "InserisciPosizioniDebitorieType")
public class InserisciPosizioniDebitorieType
    extends PayRequestType
{

    protected boolean accorpaPosizioni;
    protected String oggettoPagamentoUnico;
    @XmlElement(required = true)
    protected List<RegistrazioneContabileWsInType> registrazione;

    /**
     * Gets the value of the accorpaPosizioni property.
     * 
     */
    public boolean isAccorpaPosizioni() {
        return accorpaPosizioni;
    }

    /**
     * Sets the value of the accorpaPosizioni property.
     * 
     */
    public void setAccorpaPosizioni(boolean value) {
        this.accorpaPosizioni = value;
    }

    /**
     * Gets the value of the oggettoPagamentoUnico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoPagamentoUnico() {
        return oggettoPagamentoUnico;
    }

    /**
     * Sets the value of the oggettoPagamentoUnico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoPagamentoUnico(String value) {
        this.oggettoPagamentoUnico = value;
    }

    /**
     * Gets the value of the registrazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registrazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistrazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegistrazioneContabileWsInType }
     * 
     * 
     */
    public List<RegistrazioneContabileWsInType> getRegistrazione() {
        if (registrazione == null) {
            registrazione = new ArrayList<RegistrazioneContabileWsInType>();
        }
        return this.registrazione;
    }

}
