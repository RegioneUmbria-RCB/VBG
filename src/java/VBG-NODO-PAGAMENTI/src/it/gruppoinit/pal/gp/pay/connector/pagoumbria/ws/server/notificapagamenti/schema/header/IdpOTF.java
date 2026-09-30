
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.header;

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
 *         &lt;element name="URL_BACK"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *               &lt;maxLength value="512"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="URL_CANCEL"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *               &lt;maxLength value="512"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OFFLINE_PAYMENT_METHODS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="ID_PSP" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max100Text" minOccurs="0"/&gt;
 *         &lt;element name="DATI_VERSANTE" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}DATI_VERSANTE_Type" minOccurs="0"/&gt;
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
    "urlback",
    "urlcancel",
    "offlinepaymentmethods",
    "idpsp",
    "dativersante"
})
@XmlRootElement(name = "IdpOTF")
public class IdpOTF {

    @XmlElement(name = "URL_BACK", required = true)
    protected String urlback;
    @XmlElement(name = "URL_CANCEL", required = true)
    protected String urlcancel;
    @XmlElement(name = "OFFLINE_PAYMENT_METHODS")
    protected Boolean offlinepaymentmethods;
    @XmlElement(name = "ID_PSP")
    protected String idpsp;
    @XmlElement(name = "DATI_VERSANTE")
    protected DATIVERSANTEType dativersante;

    /**
     * Recupera il valore della proprietà urlback.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURLBACK() {
        return urlback;
    }

    /**
     * Imposta il valore della proprietà urlback.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURLBACK(String value) {
        this.urlback = value;
    }

    /**
     * Recupera il valore della proprietà urlcancel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURLCANCEL() {
        return urlcancel;
    }

    /**
     * Imposta il valore della proprietà urlcancel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURLCANCEL(String value) {
        this.urlcancel = value;
    }

    /**
     * Recupera il valore della proprietà offlinepaymentmethods.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOFFLINEPAYMENTMETHODS() {
        return offlinepaymentmethods;
    }

    /**
     * Imposta il valore della proprietà offlinepaymentmethods.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOFFLINEPAYMENTMETHODS(Boolean value) {
        this.offlinepaymentmethods = value;
    }

    /**
     * Recupera il valore della proprietà idpsp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDPSP() {
        return idpsp;
    }

    /**
     * Imposta il valore della proprietà idpsp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDPSP(String value) {
        this.idpsp = value;
    }

    /**
     * Recupera il valore della proprietà dativersante.
     * 
     * @return
     *     possible object is
     *     {@link DATIVERSANTEType }
     *     
     */
    public DATIVERSANTEType getDATIVERSANTE() {
        return dativersante;
    }

    /**
     * Imposta il valore della proprietà dativersante.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIVERSANTEType }
     *     
     */
    public void setDATIVERSANTE(DATIVERSANTEType value) {
        this.dativersante = value;
    }

}
