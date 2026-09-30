//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.28 alle 03:27:39 PM CEST 
//


package it.gruppoinit.pal.gp.core.domain.cart.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ValueBuilder complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ValueBuilder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="vbg-value" type="{http://gruppoinit.it/sigepro/schemas/cartmapping}VbgValue"/>
 *       &lt;/sequence>
 *       &lt;attribute name="indexed-property" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="value-elaboration" type="{http://gruppoinit.it/sigepro/schemas/cartmapping}ElaborationType" default="GET_FIRST" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueBuilder", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", propOrder = {
    "vbgValues"
})
public class ValueBuilder {

    @XmlElement(name = "vbg-value", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", required = true)
    protected List<VbgValue> vbgValues;
    @XmlAttribute(name = "indexed-property")
    protected String indexedProperty;
    @XmlAttribute(name = "value-elaboration")
    protected ElaborationType valueElaboration;

    /**
     * Gets the value of the vbgValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vbgValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVbgValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VbgValue }
     * 
     * 
     */
    public List<VbgValue> getVbgValues() {
        if (vbgValues == null) {
            vbgValues = new ArrayList<VbgValue>();
        }
        return this.vbgValues;
    }

    /**
     * Recupera il valore della proprietà indexedProperty.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexedProperty() {
        return indexedProperty;
    }

    /**
     * Imposta il valore della proprietà indexedProperty.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexedProperty(String value) {
        this.indexedProperty = value;
    }

    /**
     * Recupera il valore della proprietà valueElaboration.
     * 
     * @return
     *     possible object is
     *     {@link ElaborationType }
     *     
     */
    public ElaborationType getValueElaboration() {
        if (valueElaboration == null) {
            return ElaborationType.GET_FIRST;
        } else {
            return valueElaboration;
        }
    }

    /**
     * Imposta il valore della proprietà valueElaboration.
     * 
     * @param value
     *     allowed object is
     *     {@link ElaborationType }
     *     
     */
    public void setValueElaboration(ElaborationType value) {
        this.valueElaboration = value;
    }

}
