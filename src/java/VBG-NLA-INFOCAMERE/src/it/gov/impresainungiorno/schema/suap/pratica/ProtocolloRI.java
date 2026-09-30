
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ProtocolloRI complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ProtocolloRI"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="ufficio-ri" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}SiglaProvincia" /&gt;
 *       &lt;attribute name="anno" use="required" type="{http://www.w3.org/2001/XMLSchema}gYear" /&gt;
 *       &lt;attribute name="numero-protocollo-ri" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"&gt;
 *             &lt;maxInclusive value="999999999"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="sotto-numero-protocollo-ri" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"&gt;
 *             &lt;maxInclusive value="9999"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="data-protocollo-ri" use="required" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtocolloRI")
public class ProtocolloRI {

    @XmlAttribute(name = "ufficio-ri", required = true)
    protected String ufficioRi;
    @XmlAttribute(name = "anno", required = true)
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar anno;
    @XmlAttribute(name = "numero-protocollo-ri", required = true)
    protected int numeroProtocolloRi;
    @XmlAttribute(name = "sotto-numero-protocollo-ri", required = true)
    protected int sottoNumeroProtocolloRi;
    @XmlAttribute(name = "data-protocollo-ri", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProtocolloRi;

    /**
     * Recupera il valore della proprietà ufficioRi.
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
     * Imposta il valore della proprietà ufficioRi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioRi(String value) {
        this.ufficioRi = value;
    }

    /**
     * Recupera il valore della proprietà anno.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAnno() {
        return anno;
    }

    /**
     * Imposta il valore della proprietà anno.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAnno(XMLGregorianCalendar value) {
        this.anno = value;
    }

    /**
     * Recupera il valore della proprietà numeroProtocolloRi.
     * 
     */
    public int getNumeroProtocolloRi() {
        return numeroProtocolloRi;
    }

    /**
     * Imposta il valore della proprietà numeroProtocolloRi.
     * 
     */
    public void setNumeroProtocolloRi(int value) {
        this.numeroProtocolloRi = value;
    }

    /**
     * Recupera il valore della proprietà sottoNumeroProtocolloRi.
     * 
     */
    public int getSottoNumeroProtocolloRi() {
        return sottoNumeroProtocolloRi;
    }

    /**
     * Imposta il valore della proprietà sottoNumeroProtocolloRi.
     * 
     */
    public void setSottoNumeroProtocolloRi(int value) {
        this.sottoNumeroProtocolloRi = value;
    }

    /**
     * Recupera il valore della proprietà dataProtocolloRi.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocolloRi() {
        return dataProtocolloRi;
    }

    /**
     * Imposta il valore della proprietà dataProtocolloRi.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocolloRi(XMLGregorianCalendar value) {
        this.dataProtocolloRi = value;
    }

}
