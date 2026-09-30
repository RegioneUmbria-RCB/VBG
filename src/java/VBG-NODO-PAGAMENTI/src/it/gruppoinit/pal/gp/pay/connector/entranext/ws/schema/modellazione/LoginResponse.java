
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per LoginResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="LoginResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TokenAuth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginResponse", propOrder = {
    "tokenAuth"
})
public class LoginResponse
    extends LinkNextResponse
{

    @XmlElement(name = "TokenAuth")
    protected String tokenAuth;

    /**
     * Recupera il valore della proprietà tokenAuth.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenAuth() {
        return tokenAuth;
    }

    /**
     * Imposta il valore della proprietà tokenAuth.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenAuth(String value) {
        this.tokenAuth = value;
    }

}
