
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaPersona complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaPersona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaPersone" type="{urn:AAEPCSI}ArrayOfPersona"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaPersona", propOrder = {
    "listaPersone"
})
public class ListaPersona {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfPersona listaPersone;

    /**
     * Gets the value of the listaPersone property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPersona }
     *     
     */
    public ArrayOfPersona getListaPersone() {
        return listaPersone;
    }

    /**
     * Sets the value of the listaPersone property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPersona }
     *     
     */
    public void setListaPersone(ArrayOfPersona value) {
        this.listaPersone = value;
    }

}
