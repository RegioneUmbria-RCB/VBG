
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for allegatoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="allegatoType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.suap.regione.toscana.it/sem/types/procedimento}abstractAllegatoType">
 *       &lt;sequence>
 *         &lt;element name="nomefileOriginale" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}fileName"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegatoType", propOrder = {
    "nomefileOriginale"
})
@XmlSeeAlso({
    AllegatoDaSuapSDType.class
})
public class AllegatoType
    extends AbstractAllegatoType
{

    @XmlElement(required = true)
    protected String nomefileOriginale;

    /**
     * Gets the value of the nomefileOriginale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomefileOriginale() {
        return nomefileOriginale;
    }

    /**
     * Sets the value of the nomefileOriginale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomefileOriginale(String value) {
        this.nomefileOriginale = value;
    }

}
