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
 * <p>Classe Java per CartMapping complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CartMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id-semantico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="value" type="{http://gruppoinit.it/sigepro/schemas/cartmapping}ValueBuilder"/>
 *         &lt;element name="value-mappings" type="{http://gruppoinit.it/sigepro/schemas/cartmapping}ValueMappings" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartMapping", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", propOrder = {

})
public class CartMapping {

    @XmlElement(name = "id-semantico", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", required = true)
    protected String idSemantico;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", required = true)
    protected ValueBuilder value;
    @XmlElement(name = "value-mappings", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping")
    protected ValueMappings valueMappings;

    /**
     * Recupera il valore della proprietà idSemantico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSemantico() {
        return idSemantico;
    }

    /**
     * Imposta il valore della proprietà idSemantico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSemantico(String value) {
        this.idSemantico = value;
    }

    /**
     * Recupera il valore della proprietà value.
     * 
     * @return
     *     possible object is
     *     {@link ValueBuilder }
     *     
     */
    public ValueBuilder getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueBuilder }
     *     
     */
    public void setValue(ValueBuilder value) {
        this.value = value;
    }

    /**
     * Recupera il valore della proprietà valueMappings.
     * 
     * @return
     *     possible object is
     *     {@link ValueMappings }
     *     
     */
    public ValueMappings getValueMappings() {
        return valueMappings;
    }

    /**
     * Imposta il valore della proprietà valueMappings.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueMappings }
     *     
     */
    public void setValueMappings(ValueMappings value) {
        this.valueMappings = value;
    }

}
