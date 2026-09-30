
package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentificativiAnagrafeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentificativiAnagrafeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="codiceAnagrafica" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;/choice>
 *         &lt;element name="tipoanagrafeEnum" type="{http://gruppoinit.it/sigepro/schemas/messages/istanze}TipoanagrafeEnum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificativiAnagrafeType", namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze", propOrder = {
    "partitaIva",
    "codiceFiscale",
    "codiceAnagrafica",
    "tipoanagrafeEnum"
})
public class IdentificativiAnagrafeType {

    protected String partitaIva;
    protected String codiceFiscale;
    protected Integer codiceAnagrafica;
    @XmlElement(required = true, defaultValue = "NON_SPECIFICATO")
    protected TipoanagrafeEnum tipoanagrafeEnum;

    /**
     * Gets the value of the partitaIva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Sets the value of the partitaIva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the codiceAnagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiceAnagrafica() {
        return codiceAnagrafica;
    }

    /**
     * Sets the value of the codiceAnagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiceAnagrafica(Integer value) {
        this.codiceAnagrafica = value;
    }

    /**
     * Gets the value of the tipoanagrafeEnum property.
     * 
     * @return
     *     possible object is
     *     {@link TipoanagrafeEnum }
     *     
     */
    public TipoanagrafeEnum getTipoanagrafeEnum() {
        return tipoanagrafeEnum;
    }

    /**
     * Sets the value of the tipoanagrafeEnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoanagrafeEnum }
     *     
     */
    public void setTipoanagrafeEnum(TipoanagrafeEnum value) {
        this.tipoanagrafeEnum = value;
    }

}
