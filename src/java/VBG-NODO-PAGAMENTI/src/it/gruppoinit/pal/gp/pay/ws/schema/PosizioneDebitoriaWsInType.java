
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PosizioneDebitoriaWsInType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoriaWsInType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroRata" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element name="importi" type="{http://www.paevolution.com/ws/pagamenti_types/}ImportoPagamentoWsInType" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="riferimentiClient" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoriaWsInType", propOrder = {
    "descrizione",
    "numeroRata",
    "importi",
    "dataScadenza",
    "riferimentiClient"
})
public class PosizioneDebitoriaWsInType {

    @XmlElement(required = true)
    protected String descrizione;
    protected BigInteger numeroRata;
    @XmlElement(required = true)
    protected List<ImportoPagamentoWsInType> importi;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;
    protected List<String> riferimentiClient;

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the numeroRata property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroRata() {
        return numeroRata;
    }

    /**
     * Sets the value of the numeroRata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroRata(BigInteger value) {
        this.numeroRata = value;
    }

    /**
     * Gets the value of the importi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImporti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportoPagamentoWsInType }
     * 
     * 
     */
    public List<ImportoPagamentoWsInType> getImporti() {
        if (importi == null) {
            importi = new ArrayList<ImportoPagamentoWsInType>();
        }
        return this.importi;
    }

    /**
     * Gets the value of the dataScadenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Sets the value of the dataScadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Gets the value of the riferimentiClient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riferimentiClient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiferimentiClient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRiferimentiClient() {
        if (riferimentiClient == null) {
            riferimentiClient = new ArrayList<String>();
        }
        return this.riferimentiClient;
    }

}
