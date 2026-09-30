
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per AttivitaISTAT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AttivitaISTAT"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="codice-istat" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceISTATAttivita" /&gt;
 *       &lt;attribute name="catalogo" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa"&gt;
 *             &lt;enumeration value="ATECO2002"/&gt;
 *             &lt;enumeration value="ATECO2004"/&gt;
 *             &lt;enumeration value="ATECO2007"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="principale" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
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
     * Recupera il valore della proprietà value.
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
     * Imposta il valore della proprietà value.
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
     * Recupera il valore della proprietà codiceIstat.
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
     * Imposta il valore della proprietà codiceIstat.
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
     * Recupera il valore della proprietà catalogo.
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
     * Imposta il valore della proprietà catalogo.
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
     * Recupera il valore della proprietà principale.
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
     * Imposta il valore della proprietà principale.
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
