
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="tokenInfo" type="{http://sigeprosecurity.gruppoinit.it/schema}TokenInfoType" minOccurs="0"/&gt;
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
    "valid",
    "tokenInfo"
})
@XmlRootElement(name = "CheckTokenResponse")
public class CheckTokenResponse {

    protected boolean valid;
    protected TokenInfoType tokenInfo;

    /**
     * Recupera il valore della proprietà valid.
     * 
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Imposta il valore della proprietà valid.
     * 
     */
    public void setValid(boolean value) {
        this.valid = value;
    }

    /**
     * Recupera il valore della proprietà tokenInfo.
     * 
     * @return
     *     possible object is
     *     {@link TokenInfoType }
     *     
     */
    public TokenInfoType getTokenInfo() {
        return tokenInfo;
    }

    /**
     * Imposta il valore della proprietà tokenInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link TokenInfoType }
     *     
     */
    public void setTokenInfo(TokenInfoType value) {
        this.tokenInfo = value;
    }

}
