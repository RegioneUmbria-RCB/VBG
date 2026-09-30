
package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificativiAnagrafeType" type="{http://gruppoinit.it/sigepro/schemas/messages/istanze}IdentificativiAnagrafeType"/>
 *         &lt;element name="codiceTipologiaSoggetto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiceIstanza" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="isNonInserireSeTipologiaPresente" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "identificativiAnagrafeType",
    "codiceTipologiaSoggetto",
    "codiceIstanza",
    "isNonInserireSeTipologiaPresente"
})
@XmlRootElement(name = "SoggettiCollegatiInsertByIdentificativoRequest", namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
public class SoggettiCollegatiInsertByIdentificativoRequest {

    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze", required = true)
    protected String token;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze", required = true)
    protected IdentificativiAnagrafeType identificativiAnagrafeType;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
    protected int codiceTipologiaSoggetto;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
    protected int codiceIstanza;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
    protected boolean isNonInserireSeTipologiaPresente;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the identificativiAnagrafeType property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificativiAnagrafeType }
     *     
     */
    public IdentificativiAnagrafeType getIdentificativiAnagrafeType() {
        return identificativiAnagrafeType;
    }

    /**
     * Sets the value of the identificativiAnagrafeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificativiAnagrafeType }
     *     
     */
    public void setIdentificativiAnagrafeType(IdentificativiAnagrafeType value) {
        this.identificativiAnagrafeType = value;
    }

    /**
     * Gets the value of the codiceTipologiaSoggetto property.
     * 
     */
    public int getCodiceTipologiaSoggetto() {
        return codiceTipologiaSoggetto;
    }

    /**
     * Sets the value of the codiceTipologiaSoggetto property.
     * 
     */
    public void setCodiceTipologiaSoggetto(int value) {
        this.codiceTipologiaSoggetto = value;
    }

    /**
     * Gets the value of the codiceIstanza property.
     * 
     */
    public int getCodiceIstanza() {
        return codiceIstanza;
    }

    /**
     * Sets the value of the codiceIstanza property.
     * 
     */
    public void setCodiceIstanza(int value) {
        this.codiceIstanza = value;
    }

    /**
     * Gets the value of the isNonInserireSeTipologiaPresente property.
     * 
     */
    public boolean isIsNonInserireSeTipologiaPresente() {
        return isNonInserireSeTipologiaPresente;
    }

    /**
     * Sets the value of the isNonInserireSeTipologiaPresente property.
     * 
     */
    public void setIsNonInserireSeTipologiaPresente(boolean value) {
        this.isNonInserireSeTipologiaPresente = value;
    }

}
