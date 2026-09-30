
package it.alveo.ricalcoloaree.sigeprosecurity;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
 *         <element name="authlevel" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
    "authlevel"
})
@XmlRootElement(name = "GetAuthLevelResponse")
public class GetAuthLevelResponse {

    @XmlElement(required = true)
    protected BigInteger authlevel;

    /**
     * Recupera il valore della proprietà authlevel.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAuthlevel() {
        return authlevel;
    }

    /**
     * Imposta il valore della proprietà authlevel.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAuthlevel(BigInteger value) {
        this.authlevel = value;
    }

}
