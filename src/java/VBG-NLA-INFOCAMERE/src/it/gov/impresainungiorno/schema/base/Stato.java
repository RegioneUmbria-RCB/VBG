
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per Stato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Stato"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base&gt;Stringa"&gt;
 *       &lt;attribute name="codice" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa"&gt;
 *             &lt;pattern value="[A-Za-z0-9]{1,4}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="codice-istat"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa"&gt;
 *             &lt;pattern value="\d{3}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="codice-catastale"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa"&gt;
 *             &lt;pattern value="[A-Z]\d{3}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="iso"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa"&gt;
 *             &lt;pattern value="[A-Z]{3}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Stato", propOrder = {
    "value"
})
public class Stato {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;
    @XmlAttribute(name = "codice-istat")
    protected String codiceIstat;
    @XmlAttribute(name = "codice-catastale")
    protected String codiceCatastale;
    @XmlAttribute(name = "iso")
    protected String iso;

    /**
     *  Questo elemento non puo' assumere valore stringa vuota, al piu' non c'e' 
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
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
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
     * Recupera il valore della proprietà codiceCatastale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    /**
     * Imposta il valore della proprietà codiceCatastale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCatastale(String value) {
        this.codiceCatastale = value;
    }

    /**
     * Recupera il valore della proprietà iso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIso() {
        return iso;
    }

    /**
     * Imposta il valore della proprietà iso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIso(String value) {
        this.iso = value;
    }

}
