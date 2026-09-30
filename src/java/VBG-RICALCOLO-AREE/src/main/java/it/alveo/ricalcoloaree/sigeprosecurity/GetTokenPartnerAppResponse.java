
package it.alveo.ricalcoloaree.sigeprosecurity;

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
 *         <element name="tokenPartnerApp" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "tokenPartnerApp"
})
@XmlRootElement(name = "GetTokenPartnerAppResponse")
public class GetTokenPartnerAppResponse {

    @XmlElement(required = true)
    protected String tokenPartnerApp;

    /**
     * Recupera il valore della proprietà tokenPartnerApp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenPartnerApp() {
        return tokenPartnerApp;
    }

    /**
     * Imposta il valore della proprietà tokenPartnerApp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenPartnerApp(String value) {
        this.tokenPartnerApp = value;
    }

}
