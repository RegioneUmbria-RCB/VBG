
package it.gruppoinit.impresainungiorno.schema.base.v_1_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for AttivitaISTAT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttivitaISTAT">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="codice-istat" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceISTATAttivita" />
 *       &lt;attribute name="catalogo" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa">
 *             &lt;enumeration value="ATECO2002"/>
 *             &lt;enumeration value="ATECO2004"/>
 *             &lt;enumeration value="ATECO2007"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="principale" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttivitaISTAT", propOrder = {
    "value"
})
public class AttivitaISTAT {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice-istat", required = true)
    protected String codiceIstat;
    @XmlAttribute(name = "catalogo", required = true)
    protected String catalogo;
    @XmlAttribute(name = "principale")
    protected Boolean principale;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the codiceIstat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstat() {
        return codiceIstat;
    }

    /**
     * Sets the value of the codiceIstat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstat(String value) {
        this.codiceIstat = value;
    }

    /**
     * Gets the value of the catalogo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalogo() {
        return catalogo;
    }

    /**
     * Sets the value of the catalogo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalogo(String value) {
        this.catalogo = value;
    }

    /**
     * Gets the value of the principale property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrincipale() {
        return principale;
    }

    /**
     * Sets the value of the principale property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrincipale(Boolean value) {
        this.principale = value;
    }

}
