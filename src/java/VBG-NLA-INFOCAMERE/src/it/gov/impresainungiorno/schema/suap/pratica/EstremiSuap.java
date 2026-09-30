
package it.gov.impresainungiorno.schema.suap.pratica;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per EstremiSuap complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiSuap"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="codice-amministrazione" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}CodiceAmministrazione" /&gt;
 *       &lt;attribute name="codice-aoo" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}CodiceAOO" /&gt;
 *       &lt;attribute name="identificativo-suap" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="codice-catastale"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa"&gt;
 *             &lt;pattern value="[A-Z]{1}\d{3}"/&gt;
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
@XmlType(name = "EstremiSuap", propOrder = {
    "value"
})
public class EstremiSuap {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice-amministrazione", required = true)
    protected String codiceAmministrazione;
    @XmlAttribute(name = "codice-aoo", required = true)
    protected String codiceAoo;
    @XmlAttribute(name = "identificativo-suap", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger identificativoSuap;
    @XmlAttribute(name = "codice-catastale")
    protected String codiceCatastale;

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
     * Recupera il valore della proprietà codiceAmministrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    /**
     * Imposta il valore della proprietà codiceAmministrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAmministrazione(String value) {
        this.codiceAmministrazione = value;
    }

    /**
     * Recupera il valore della proprietà codiceAoo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAoo() {
        return codiceAoo;
    }

    /**
     * Imposta il valore della proprietà codiceAoo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAoo(String value) {
        this.codiceAoo = value;
    }

    /**
     * Recupera il valore della proprietà identificativoSuap.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoSuap() {
        return identificativoSuap;
    }

    /**
     * Imposta il valore della proprietà identificativoSuap.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoSuap(BigInteger value) {
        this.identificativoSuap = value;
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

}
