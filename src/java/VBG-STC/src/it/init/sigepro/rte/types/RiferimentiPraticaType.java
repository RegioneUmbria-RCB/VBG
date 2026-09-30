
package it.init.sigepro.rte.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RiferimentiPraticaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiferimentiPraticaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataPratica" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="oraDataPratica" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-2][0-9]:[0-5][0-9]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="numeroProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="altriDati" type="{http://sigepro.init.it/rte/types}ParametroType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentiPraticaType", propOrder = {
    "idPratica",
    "numeroPratica",
    "dataPratica",
    "oraDataPratica",
    "numeroProtocolloGenerale",
    "dataProtocolloGenerale",
    "altriDati"
})
public class RiferimentiPraticaType {

    protected String idPratica;
    protected String numeroPratica;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataPratica;
    protected String oraDataPratica;
    protected String numeroProtocolloGenerale;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProtocolloGenerale;
    protected List<ParametroType> altriDati;

    /**
     * Gets the value of the idPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPratica() {
        return idPratica;
    }

    /**
     * Sets the value of the idPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPratica(String value) {
        this.idPratica = value;
    }

    /**
     * Gets the value of the numeroPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPratica() {
        return numeroPratica;
    }

    /**
     * Sets the value of the numeroPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPratica(String value) {
        this.numeroPratica = value;
    }

    /**
     * Gets the value of the dataPratica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPratica() {
        return dataPratica;
    }

    /**
     * Sets the value of the dataPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPratica(XMLGregorianCalendar value) {
        this.dataPratica = value;
    }

    /**
     * Gets the value of the oraDataPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOraDataPratica() {
        return oraDataPratica;
    }

    /**
     * Sets the value of the oraDataPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOraDataPratica(String value) {
        this.oraDataPratica = value;
    }

    /**
     * Gets the value of the numeroProtocolloGenerale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloGenerale() {
        return numeroProtocolloGenerale;
    }

    /**
     * Sets the value of the numeroProtocolloGenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloGenerale(String value) {
        this.numeroProtocolloGenerale = value;
    }

    /**
     * Gets the value of the dataProtocolloGenerale property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocolloGenerale() {
        return dataProtocolloGenerale;
    }

    /**
     * Sets the value of the dataProtocolloGenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocolloGenerale(XMLGregorianCalendar value) {
        this.dataProtocolloGenerale = value;
    }

    /**
     * Gets the value of the altriDati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altriDati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltriDati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParametroType }
     * 
     * 
     */
    public List<ParametroType> getAltriDati() {
        if (altriDati == null) {
            altriDati = new ArrayList<ParametroType>();
        }
        return this.altriDati;
    }

}
