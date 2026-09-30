
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for copiaTrovata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="copiaTrovata">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.hostingtemporaneo.people.phoops.it/}fileTrovato">
 *       &lt;sequence>
 *         &lt;element name="dataCreazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *       &lt;attribute name="uriOriginale" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "copiaTrovata", propOrder = {
    "dataCreazione"
})
public class CopiaTrovata
    extends FileTrovato
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCreazione;
    @XmlAttribute(name = "uriOriginale", required = true)
    protected String uriOriginale;

    /**
     * Gets the value of the dataCreazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCreazione() {
        return dataCreazione;
    }

    /**
     * Sets the value of the dataCreazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCreazione(XMLGregorianCalendar value) {
        this.dataCreazione = value;
    }

    /**
     * Gets the value of the uriOriginale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUriOriginale() {
        return uriOriginale;
    }

    /**
     * Sets the value of the uriOriginale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUriOriginale(String value) {
        this.uriOriginale = value;
    }

}
