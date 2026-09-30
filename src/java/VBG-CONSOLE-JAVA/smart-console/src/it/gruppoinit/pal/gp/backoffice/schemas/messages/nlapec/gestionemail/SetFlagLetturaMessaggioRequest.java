//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.27 alle 03:53:35 PM CEST 
//


package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificativoMessaggio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flagValue" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "software",
    "identificativoMessaggio",
    "flagValue"
})
@XmlRootElement(name = "SetFlagLetturaMessaggioRequest", namespace = "http://gruppoinit.it/nlapec")
public class SetFlagLetturaMessaggioRequest {

    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String token;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String software;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String identificativoMessaggio;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected boolean flagValue;

    /**
     * Recupera il valore della proprietà token.
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
     * Imposta il valore della proprietà token.
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
     * Recupera il valore della proprietà software.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Imposta il valore della proprietà software.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

    /**
     * Recupera il valore della proprietà identificativoMessaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoMessaggio() {
        return identificativoMessaggio;
    }

    /**
     * Imposta il valore della proprietà identificativoMessaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoMessaggio(String value) {
        this.identificativoMessaggio = value;
    }

    /**
     * Recupera il valore della proprietà flagValue.
     * 
     */
    public boolean isFlagValue() {
        return flagValue;
    }

    /**
     * Imposta il valore della proprietà flagValue.
     * 
     */
    public void setFlagValue(boolean value) {
        this.flagValue = value;
    }

}
