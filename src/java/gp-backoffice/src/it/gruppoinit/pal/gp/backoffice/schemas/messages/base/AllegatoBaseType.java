
package it.gruppoinit.pal.gp.backoffice.schemas.messages.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AllegatoBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllegatoBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="riferimento" type="{http://gruppoinit.it/sigepro/schemas/messages/base}RiferimentoOggettoBackofficeType" minOccurs="0"/>
 *         &lt;element name="datiFile" type="{http://gruppoinit.it/sigepro/schemas/messages/base}FileBaseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoBaseType", propOrder = {
    "riferimento",
    "datiFile"
})
public class AllegatoBaseType {

    protected RiferimentoOggettoBackofficeType riferimento;
    @XmlElement(required = true)
    protected FileBaseType datiFile;

    /**
     * Gets the value of the riferimento property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoOggettoBackofficeType }
     *     
     */
    public RiferimentoOggettoBackofficeType getRiferimento() {
        return riferimento;
    }

    /**
     * Sets the value of the riferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoOggettoBackofficeType }
     *     
     */
    public void setRiferimento(RiferimentoOggettoBackofficeType value) {
        this.riferimento = value;
    }

    /**
     * Gets the value of the datiFile property.
     * 
     * @return
     *     possible object is
     *     {@link FileBaseType }
     *     
     */
    public FileBaseType getDatiFile() {
        return datiFile;
    }

    /**
     * Sets the value of the datiFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileBaseType }
     *     
     */
    public void setDatiFile(FileBaseType value) {
        this.datiFile = value;
    }

}
