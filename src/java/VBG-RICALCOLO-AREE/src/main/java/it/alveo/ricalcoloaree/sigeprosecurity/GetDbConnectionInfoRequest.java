
package it.alveo.ricalcoloaree.sigeprosecurity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.</p>
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="ambiente" type="{http://sigeprosecurity.gruppoinit.it/schema}AmbienteType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "alias",
    "ambiente"
})
@XmlRootElement(name = "GetDbConnectionInfoRequest")
public class GetDbConnectionInfoRequest {

    @XmlElement(required = true)
    protected String alias;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AmbienteType ambiente;

    /**
     * Recupera il valore della proprietà alias.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Imposta il valore della proprietà alias.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Recupera il valore della proprietà ambiente.
     * 
     * @return
     *     possible object is
     *     {@link AmbienteType }
     *     
     */
    public AmbienteType getAmbiente() {
        return ambiente;
    }

    /**
     * Imposta il valore della proprietà ambiente.
     * 
     * @param value
     *     allowed object is
     *     {@link AmbienteType }
     *     
     */
    public void setAmbiente(AmbienteType value) {
        this.ambiente = value;
    }

}
