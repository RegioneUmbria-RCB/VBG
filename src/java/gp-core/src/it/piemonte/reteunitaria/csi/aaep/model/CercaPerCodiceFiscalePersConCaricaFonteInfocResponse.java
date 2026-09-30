
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cercaPerCodiceFiscalePersConCaricaFonteInfocReturn" type="{urn:AAEPCSI}ListaPersona" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaPerCodiceFiscalePersConCaricaFonteInfocReturn"
})
@XmlRootElement(name = "cercaPerCodiceFiscalePersConCaricaFonteInfocResponse")
public class CercaPerCodiceFiscalePersConCaricaFonteInfocResponse {

    protected ListaPersona cercaPerCodiceFiscalePersConCaricaFonteInfocReturn;

    /**
     * Gets the value of the cercaPerCodiceFiscalePersConCaricaFonteInfocReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ListaPersona }
     *     
     */
    public ListaPersona getCercaPerCodiceFiscalePersConCaricaFonteInfocReturn() {
        return cercaPerCodiceFiscalePersConCaricaFonteInfocReturn;
    }

    /**
     * Sets the value of the cercaPerCodiceFiscalePersConCaricaFonteInfocReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaPersona }
     *     
     */
    public void setCercaPerCodiceFiscalePersConCaricaFonteInfocReturn(ListaPersona value) {
        this.cercaPerCodiceFiscalePersConCaricaFonteInfocReturn = value;
    }

}
