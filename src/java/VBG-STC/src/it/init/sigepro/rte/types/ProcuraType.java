
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcuraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcuraType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cfRappresentato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cfProcuratore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="procura" type="{http://sigepro.init.it/rte/types}DocumentiType" minOccurs="0"/>
 *         &lt;element name="documentoIdentita" type="{http://sigepro.init.it/rte/types}DocumentiType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcuraType", propOrder = {
    "cfRappresentato",
    "cfProcuratore",
    "procura",
    "documentoIdentita"
})
public class ProcuraType {

    @XmlElement(required = true)
    protected String cfRappresentato;
    @XmlElement(required = true)
    protected String cfProcuratore;
    protected DocumentiType procura;
    protected DocumentiType documentoIdentita;

    /**
     * Gets the value of the cfRappresentato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfRappresentato() {
        return cfRappresentato;
    }

    /**
     * Sets the value of the cfRappresentato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfRappresentato(String value) {
        this.cfRappresentato = value;
    }

    /**
     * Gets the value of the cfProcuratore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfProcuratore() {
        return cfProcuratore;
    }

    /**
     * Sets the value of the cfProcuratore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfProcuratore(String value) {
        this.cfProcuratore = value;
    }

    /**
     * Gets the value of the procura property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentiType }
     *     
     */
    public DocumentiType getProcura() {
        return procura;
    }

    /**
     * Sets the value of the procura property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentiType }
     *     
     */
    public void setProcura(DocumentiType value) {
        this.procura = value;
    }

    /**
     * Gets the value of the documentoIdentita property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentiType }
     *     
     */
    public DocumentiType getDocumentoIdentita() {
        return documentoIdentita;
    }

    /**
     * Sets the value of the documentoIdentita property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentiType }
     *     
     */
    public void setDocumentoIdentita(DocumentiType value) {
        this.documentoIdentita = value;
    }

}
