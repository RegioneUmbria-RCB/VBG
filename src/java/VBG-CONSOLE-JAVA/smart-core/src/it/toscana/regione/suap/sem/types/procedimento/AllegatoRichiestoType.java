
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Un allegato richiesto e' costituito da un allegato (vedi 'allegatoType') o da un link che consenta lo scarico dell'allegato 
 * 				per il quale viene indicato se deve essere o meno firmato.
 * 			
 * 
 * <p>Java class for allegatoRichiestoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="allegatoRichiestoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="link" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}stringaNonVuota"/>
 *         &lt;element name="attach" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}allegatoType"/>
 *       &lt;/choice>
 *       &lt;attribute name="firmato" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegatoRichiestoType", propOrder = {
    "link",
    "attach"
})
public class AllegatoRichiestoType {

    protected String link;
    protected AllegatoType attach;
    @XmlAttribute(required = true)
    protected boolean firmato;

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * Gets the value of the attach property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoType }
     *     
     */
    public AllegatoType getAttach() {
        return attach;
    }

    /**
     * Sets the value of the attach property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoType }
     *     
     */
    public void setAttach(AllegatoType value) {
        this.attach = value;
    }

    /**
     * Gets the value of the firmato property.
     * 
     */
    public boolean isFirmato() {
        return firmato;
    }

    /**
     * Sets the value of the firmato property.
     * 
     */
    public void setFirmato(boolean value) {
        this.firmato = value;
    }

}
