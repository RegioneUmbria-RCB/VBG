
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Albero complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Albero">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="iniziaDallaRadice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nodoIniziale" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}NodoAlbero" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Albero", propOrder = {
    "iniziaDallaRadice",
    "nodoIniziale"
})
public class Albero {

    protected boolean iniziaDallaRadice;
    protected NodoAlbero nodoIniziale;

    /**
     * Gets the value of the iniziaDallaRadice property.
     * 
     */
    public boolean isIniziaDallaRadice() {
        return iniziaDallaRadice;
    }

    /**
     * Sets the value of the iniziaDallaRadice property.
     * 
     */
    public void setIniziaDallaRadice(boolean value) {
        this.iniziaDallaRadice = value;
    }

    /**
     * Gets the value of the nodoIniziale property.
     * 
     * @return
     *     possible object is
     *     {@link NodoAlbero }
     *     
     */
    public NodoAlbero getNodoIniziale() {
        return nodoIniziale;
    }

    /**
     * Sets the value of the nodoIniziale property.
     * 
     * @param value
     *     allowed object is
     *     {@link NodoAlbero }
     *     
     */
    public void setNodoIniziale(NodoAlbero value) {
        this.nodoIniziale = value;
    }

}
