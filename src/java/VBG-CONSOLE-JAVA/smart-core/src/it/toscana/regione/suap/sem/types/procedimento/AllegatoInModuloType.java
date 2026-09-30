
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for allegatoInModuloType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="allegatoInModuloType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.suap.regione.toscana.it/sem/types/procedimento}abstractAllegatoType">
 *       &lt;sequence>
 *         &lt;element name="nomefileOriginale" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}fileName"/>
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idSemantico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegatoInModuloType", propOrder = {
    "nomefileOriginale",
    "codice",
    "idSemantico"
})
public class AllegatoInModuloType
    extends AbstractAllegatoType
{

    @XmlElement(required = true)
    protected String nomefileOriginale;
    protected String codice;
    @XmlElement(required = true)
    protected String idSemantico;

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

    /**
     * Gets the value of the codice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Gets the value of the idSemantico property.
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
     * Sets the value of the idSemantico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSemantico(String value) {
        this.idSemantico = value;
    }

}
