
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdpOTFType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdpOTFType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdSessioneGW" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UrlGW" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Versione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Versione" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpOTFType", propOrder = {
    "idSessioneGW",
    "urlGW"
})
public class IdpOTFType {

    @XmlElement(name = "IdSessioneGW", required = true)
    protected String idSessioneGW;
    @XmlElement(name = "UrlGW", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String urlGW;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Recupera il valore della proprietà idSessioneGW.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSessioneGW() {
        return idSessioneGW;
    }

    /**
     * Imposta il valore della proprietà idSessioneGW.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSessioneGW(String value) {
        this.idSessioneGW = value;
    }

    /**
     * Recupera il valore della proprietà urlGW.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlGW() {
        return urlGW;
    }

    /**
     * Imposta il valore della proprietà urlGW.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlGW(String value) {
        this.urlGW = value;
    }

    /**
     * Recupera il valore della proprietà versione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersione() {
        return versione;
    }

    /**
     * Imposta il valore della proprietà versione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersione(String value) {
        this.versione = value;
    }

}
