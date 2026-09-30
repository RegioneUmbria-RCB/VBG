
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 *                     La descrizione dell'elemeto forma giridica e' quella corrispondente all'annotation del valore possibile dell'atributo. Ai fini dei controlli fa fede il valore dell'attributo codice
 *                 
 * 
 * <p>Classe Java per FormaGiuridica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FormaGiuridica"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="codice" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="XX"/&gt;
 *             &lt;enumeration value="SZ"/&gt;
 *             &lt;enumeration value="SV"/&gt;
 *             &lt;enumeration value="SU"/&gt;
 *             &lt;enumeration value="ST"/&gt;
 *             &lt;enumeration value="SS"/&gt;
 *             &lt;enumeration value="SR"/&gt;
 *             &lt;enumeration value="SP"/&gt;
 *             &lt;enumeration value="SO"/&gt;
 *             &lt;enumeration value="SN"/&gt;
 *             &lt;enumeration value="SM"/&gt;
 *             &lt;enumeration value="SL"/&gt;
 *             &lt;enumeration value="SI"/&gt;
 *             &lt;enumeration value="SG"/&gt;
 *             &lt;enumeration value="SF"/&gt;
 *             &lt;enumeration value="SE"/&gt;
 *             &lt;enumeration value="SD"/&gt;
 *             &lt;enumeration value="SC"/&gt;
 *             &lt;enumeration value="SA"/&gt;
 *             &lt;enumeration value="RS"/&gt;
 *             &lt;enumeration value="RR"/&gt;
 *             &lt;enumeration value="PS"/&gt;
 *             &lt;enumeration value="PC"/&gt;
 *             &lt;enumeration value="PA"/&gt;
 *             &lt;enumeration value="OS"/&gt;
 *             &lt;enumeration value="OO"/&gt;
 *             &lt;enumeration value="OC"/&gt;
 *             &lt;enumeration value="MA"/&gt;
 *             &lt;enumeration value="LL"/&gt;
 *             &lt;enumeration value="IR"/&gt;
 *             &lt;enumeration value="IF"/&gt;
 *             &lt;enumeration value="ID"/&gt;
 *             &lt;enumeration value="IC"/&gt;
 *             &lt;enumeration value="GE"/&gt;
 *             &lt;enumeration value="FO"/&gt;
 *             &lt;enumeration value="FI"/&gt;
 *             &lt;enumeration value="ES"/&gt;
 *             &lt;enumeration value="ER"/&gt;
 *             &lt;enumeration value="EP"/&gt;
 *             &lt;enumeration value="EN"/&gt;
 *             &lt;enumeration value="EM"/&gt;
 *             &lt;enumeration value="EL"/&gt;
 *             &lt;enumeration value="EI"/&gt;
 *             &lt;enumeration value="EE"/&gt;
 *             &lt;enumeration value="ED"/&gt;
 *             &lt;enumeration value="EC"/&gt;
 *             &lt;enumeration value="DI"/&gt;
 *             &lt;enumeration value="CZ"/&gt;
 *             &lt;enumeration value="CS"/&gt;
 *             &lt;enumeration value="CR"/&gt;
 *             &lt;enumeration value="CO"/&gt;
 *             &lt;enumeration value="CN"/&gt;
 *             &lt;enumeration value="CM"/&gt;
 *             &lt;enumeration value="CL"/&gt;
 *             &lt;enumeration value="CI"/&gt;
 *             &lt;enumeration value="CF"/&gt;
 *             &lt;enumeration value="CE"/&gt;
 *             &lt;enumeration value="CC"/&gt;
 *             &lt;enumeration value="AZ"/&gt;
 *             &lt;enumeration value="AU"/&gt;
 *             &lt;enumeration value="AT"/&gt;
 *             &lt;enumeration value="AS"/&gt;
 *             &lt;enumeration value="AR"/&gt;
 *             &lt;enumeration value="AP"/&gt;
 *             &lt;enumeration value="AN"/&gt;
 *             &lt;enumeration value="AM"/&gt;
 *             &lt;enumeration value="AL"/&gt;
 *             &lt;enumeration value="AI"/&gt;
 *             &lt;enumeration value="AF"/&gt;
 *             &lt;enumeration value="AE"/&gt;
 *             &lt;enumeration value="AC"/&gt;
 *             &lt;enumeration value="AA"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="categoria" type="{http://www.impresainungiorno.gov.it/schema/base}StringaLunga" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormaGiuridica", propOrder = {
    "value"
})
public class FormaGiuridica {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;
    @XmlAttribute(name = "categoria")
    protected String categoria;

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
     * Recupera il valore della proprietà categoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Imposta il valore della proprietà categoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
    }

}
