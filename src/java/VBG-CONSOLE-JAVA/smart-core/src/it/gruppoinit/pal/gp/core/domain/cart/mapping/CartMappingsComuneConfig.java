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
 * <p>Classe Java per CartMappingsComuneConfig complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CartMappingsComuneConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="cart-mapping" type="{http://gruppoinit.it/sigepro/schemas/cartmapping}CartMapping"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id-comune" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartMappingsComuneConfig", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", propOrder = {
    "cartMappings"
})
public class CartMappingsComuneConfig {

    @XmlElement(name = "cart-mapping", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", required = true)
    protected List<CartMapping> cartMappings;
    @XmlAttribute(name = "id-comune")
    protected String idComune;

    /**
     * Gets the value of the cartMappings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cartMappings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCartMappings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CartMapping }
     * 
     * 
     */
    public List<CartMapping> getCartMappings() {
        if (cartMappings == null) {
            cartMappings = new ArrayList<CartMapping>();
        }
        return this.cartMappings;
    }

    /**
     * Recupera il valore della proprietà idComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdComune() {
        return idComune;
    }

    /**
     * Imposta il valore della proprietà idComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdComune(String value) {
        this.idComune = value;
    }

}
