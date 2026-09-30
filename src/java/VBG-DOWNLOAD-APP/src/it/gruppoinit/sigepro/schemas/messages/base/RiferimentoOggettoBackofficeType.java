
package it.gruppoinit.sigepro.schemas.messages.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Riferimento alla colonna OGGETTI.CODICEOGGETTO
 * 
 * <p>Java class for RiferimentoOggettoBackofficeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoOggettoBackofficeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoOggettoBackofficeType", propOrder = {
    "codice"
})
public class RiferimentoOggettoBackofficeType {

    protected int codice;

    /**
     * Gets the value of the codice property.
     * 
     */
    public int getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     */
    public void setCodice(int value) {
        this.codice = value;
    }

}
