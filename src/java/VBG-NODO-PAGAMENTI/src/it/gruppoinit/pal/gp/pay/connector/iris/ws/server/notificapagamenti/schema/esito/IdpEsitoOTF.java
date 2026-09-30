
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.header.IdpHeader;


/**
 * <p>Classe Java per IdpEsitoOTF complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdpEsitoOTF"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpHeader"/&gt;
 *         &lt;element name="IdpOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpOTFType" minOccurs="0"/&gt;
 *         &lt;element name="IdpBody" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpBodyType"/&gt;
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
@XmlType(name = "IdpEsitoOTF", propOrder = {
    "idpHeader",
    "idpOTF",
    "idpBody"
})
public class IdpEsitoOTF {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpOTF")
    protected IdpOTFType idpOTF;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBodyType idpBody;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Recupera il valore della proprietà idpHeader.
     * 
     * @return
     *     possible object is
     *     {@link IdpHeader }
     *     
     */
    public IdpHeader getIdpHeader() {
        return idpHeader;
    }

    /**
     * Imposta il valore della proprietà idpHeader.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpHeader }
     *     
     */
    public void setIdpHeader(IdpHeader value) {
        this.idpHeader = value;
    }

    /**
     * Recupera il valore della proprietà idpOTF.
     * 
     * @return
     *     possible object is
     *     {@link IdpOTFType }
     *     
     */
    public IdpOTFType getIdpOTF() {
        return idpOTF;
    }

    /**
     * Imposta il valore della proprietà idpOTF.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpOTFType }
     *     
     */
    public void setIdpOTF(IdpOTFType value) {
        this.idpOTF = value;
    }

    /**
     * Recupera il valore della proprietà idpBody.
     * 
     * @return
     *     possible object is
     *     {@link IdpBodyType }
     *     
     */
    public IdpBodyType getIdpBody() {
        return idpBody;
    }

    /**
     * Imposta il valore della proprietà idpBody.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpBodyType }
     *     
     */
    public void setIdpBody(IdpBodyType value) {
        this.idpBody = value;
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
