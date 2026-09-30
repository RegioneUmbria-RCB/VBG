
package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for protocolloRI complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocolloRI">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataProtocolloRi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroProtocolloRi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sottoNumeroProtocolloRi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ufficioRi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocolloRI", propOrder = {
    "anno",
    "dataProtocolloRi",
    "numeroProtocolloRi",
    "sottoNumeroProtocolloRi",
    "ufficioRi"
})
public class ProtocolloRI {

    protected String anno;
    protected String dataProtocolloRi;
    protected String numeroProtocolloRi;
    protected String sottoNumeroProtocolloRi;
    protected String ufficioRi;

    /**
     * Gets the value of the anno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnno() {
        return anno;
    }

    /**
     * Sets the value of the anno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnno(String value) {
        this.anno = value;
    }

    /**
     * Gets the value of the dataProtocolloRi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocolloRi() {
        return dataProtocolloRi;
    }

    /**
     * Sets the value of the dataProtocolloRi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocolloRi(String value) {
        this.dataProtocolloRi = value;
    }

    /**
     * Gets the value of the numeroProtocolloRi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloRi() {
        return numeroProtocolloRi;
    }

    /**
     * Sets the value of the numeroProtocolloRi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloRi(String value) {
        this.numeroProtocolloRi = value;
    }

    /**
     * Gets the value of the sottoNumeroProtocolloRi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSottoNumeroProtocolloRi() {
        return sottoNumeroProtocolloRi;
    }

    /**
     * Sets the value of the sottoNumeroProtocolloRi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSottoNumeroProtocolloRi(String value) {
        this.sottoNumeroProtocolloRi = value;
    }

    /**
     * Gets the value of the ufficioRi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficioRi() {
        return ufficioRi;
    }

    /**
     * Sets the value of the ufficioRi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioRi(String value) {
        this.ufficioRi = value;
    }

}
