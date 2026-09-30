
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AttivaSessionePagamentoResponseType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AttivaSessionePagamentoResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="descEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idSessione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="securityDigest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="payUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="httpMethodRequired" type="{http://www.paevolution.com/ws/pagamenti_types/}HttpMethodType" minOccurs="0"/&gt;
 *         &lt;element name="formParams" type="{http://www.paevolution.com/ws/pagamenti_types/}FormParametersType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttivaSessionePagamentoResponseType", propOrder = {
    "esito",
    "descEsito",
    "idSessione",
    "securityDigest",
    "payUrl",
    "httpMethodRequired",
    "formParams"
})
public class AttivaSessionePagamentoResponseType {

    protected boolean esito;
    protected String descEsito;
    protected String idSessione;
    protected String securityDigest;
    protected String payUrl;
    @XmlSchemaType(name = "string")
    protected HttpMethodType httpMethodRequired;
    protected FormParametersType formParams;

    /**
     * Recupera il valore della proprietà esito.
     * 
     */
    public boolean isEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     */
    public void setEsito(boolean value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà descEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEsito() {
        return descEsito;
    }

    /**
     * Imposta il valore della proprietà descEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEsito(String value) {
        this.descEsito = value;
    }

    /**
     * Recupera il valore della proprietà idSessione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSessione() {
        return idSessione;
    }

    /**
     * Imposta il valore della proprietà idSessione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSessione(String value) {
        this.idSessione = value;
    }

    /**
     * Recupera il valore della proprietà securityDigest.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityDigest() {
        return securityDigest;
    }

    /**
     * Imposta il valore della proprietà securityDigest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityDigest(String value) {
        this.securityDigest = value;
    }

    /**
     * Recupera il valore della proprietà payUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayUrl() {
        return payUrl;
    }

    /**
     * Imposta il valore della proprietà payUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayUrl(String value) {
        this.payUrl = value;
    }

    /**
     * Recupera il valore della proprietà httpMethodRequired.
     * 
     * @return
     *     possible object is
     *     {@link HttpMethodType }
     *     
     */
    public HttpMethodType getHttpMethodRequired() {
        return httpMethodRequired;
    }

    /**
     * Imposta il valore della proprietà httpMethodRequired.
     * 
     * @param value
     *     allowed object is
     *     {@link HttpMethodType }
     *     
     */
    public void setHttpMethodRequired(HttpMethodType value) {
        this.httpMethodRequired = value;
    }

    /**
     * Recupera il valore della proprietà formParams.
     * 
     * @return
     *     possible object is
     *     {@link FormParametersType }
     *     
     */
    public FormParametersType getFormParams() {
        return formParams;
    }

    /**
     * Imposta il valore della proprietà formParams.
     * 
     * @param value
     *     allowed object is
     *     {@link FormParametersType }
     *     
     */
    public void setFormParams(FormParametersType value) {
        this.formParams = value;
    }

}
