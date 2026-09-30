
package it.init.sigepro.rte.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OneriScadenzeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OneriScadenzeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroRata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importoRata" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="pagamenti" type="{http://sigepro.init.it/rte/types}OneriPagamentiType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OneriScadenzeType", propOrder = {
    "numeroRata",
    "importoRata",
    "dataScadenza",
    "pagamenti"
})
public class OneriScadenzeType {

    protected String numeroRata;
    protected double importoRata;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;
    protected List<OneriPagamentiType> pagamenti;

    /**
     * Gets the value of the numeroRata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRata() {
        return numeroRata;
    }

    /**
     * Sets the value of the numeroRata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRata(String value) {
        this.numeroRata = value;
    }

    /**
     * Gets the value of the importoRata property.
     * 
     */
    public double getImportoRata() {
        return importoRata;
    }

    /**
     * Sets the value of the importoRata property.
     * 
     */
    public void setImportoRata(double value) {
        this.importoRata = value;
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
     * Gets the value of the pagamenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagamenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagamenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OneriPagamentiType }
     * 
     * 
     */
    public List<OneriPagamentiType> getPagamenti() {
        if (pagamenti == null) {
            pagamenti = new ArrayList<OneriPagamentiType>();
        }
        return this.pagamenti;
    }

}
