
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tokenPartnerApp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
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
