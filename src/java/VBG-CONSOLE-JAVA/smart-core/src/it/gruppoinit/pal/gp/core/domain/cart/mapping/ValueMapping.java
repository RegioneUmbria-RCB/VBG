//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.28 alle 03:27:39 PM CEST 
//


package it.gruppoinit.pal.gp.core.domain.cart.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ValueMapping complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ValueMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="vbg-value-match" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cart-value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueMapping", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", propOrder = {

})
public class ValueMapping {

    @XmlElement(name = "vbg-value-match", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", required = true)
    protected String vbgValueMatch;
    @XmlElement(name = "cart-value", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", required = true)
    protected String cartValue;

    /**
     * Recupera il valore della proprietà vbgValueMatch.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVbgValueMatch() {
        return vbgValueMatch;
    }

    /**
     * Imposta il valore della proprietà vbgValueMatch.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVbgValueMatch(String value) {
        this.vbgValueMatch = value;
    }

    /**
     * Recupera il valore della proprietà cartValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCartValue() {
        return cartValue;
    }

    /**
     * Imposta il valore della proprietà cartValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCartValue(String value) {
        this.cartValue = value;
    }

}
