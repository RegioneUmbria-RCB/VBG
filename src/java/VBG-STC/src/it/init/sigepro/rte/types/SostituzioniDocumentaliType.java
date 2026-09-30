
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Sono i documenti che devono andare a sostituire quelli presenti 
 * 			in fase di integrazione.L'oggetto conterrà sia il codice del docuemnto da sostituire che quello che deve essere
 * 			sostituito.
 * 			
 * 
 * <p>Java class for SostituzioniDocumentaliType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SostituzioniDocumentaliType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoDocumentoPratica">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="DOC_ISTANZA"/>
 *               &lt;enumeration value="DOC_ENDO"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="documentoDaSostituire" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nuovoDocumento" type="{http://sigepro.init.it/rte/types}AllegatiType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SostituzioniDocumentaliType", propOrder = {
    "tipoDocumentoPratica",
    "documentoDaSostituire",
    "nuovoDocumento"
})
public class SostituzioniDocumentaliType {

    @XmlElement(required = true)
    protected String tipoDocumentoPratica;
    @XmlElement(required = true)
    protected String documentoDaSostituire;
    @XmlElement(required = true)
    protected AllegatiType nuovoDocumento;

    /**
     * Gets the value of the tipoDocumentoPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumentoPratica() {
        return tipoDocumentoPratica;
    }

    /**
     * Sets the value of the tipoDocumentoPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumentoPratica(String value) {
        this.tipoDocumentoPratica = value;
    }

    /**
     * Gets the value of the documentoDaSostituire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoDaSostituire() {
        return documentoDaSostituire;
    }

    /**
     * Sets the value of the documentoDaSostituire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoDaSostituire(String value) {
        this.documentoDaSostituire = value;
    }

    /**
     * Gets the value of the nuovoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatiType }
     *     
     */
    public AllegatiType getNuovoDocumento() {
        return nuovoDocumento;
    }

    /**
     * Sets the value of the nuovoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatiType }
     *     
     */
    public void setNuovoDocumento(AllegatiType value) {
        this.nuovoDocumento = value;
    }

}
