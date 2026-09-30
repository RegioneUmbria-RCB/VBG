
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}PayRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificativoOperazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="registrazione" type="{http://www.paevolution.com/ws/pagamenti_types/}RegistrazioneContabileWsInType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "identificativoOperazione",
    "registrazione"
})
@XmlRootElement(name = "CaricamentoMassivoPosizioniDebitorieType")
public class CaricamentoMassivoPosizioniDebitorieType
    extends PayRequestType
{

    @XmlElement(required = true)
    protected String identificativoOperazione;
    @XmlElement(required = true)
    protected List<RegistrazioneContabileWsInType> registrazione;

    /**
     * Recupera il valore della proprietà identificativoOperazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoOperazione() {
        return identificativoOperazione;
    }

    /**
     * Imposta il valore della proprietà identificativoOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoOperazione(String value) {
        this.identificativoOperazione = value;
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
