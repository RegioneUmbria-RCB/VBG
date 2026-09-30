
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.IdpHeader;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.IdpOTF;


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
 *         &lt;element name="IdpHeader" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpHeader"/&gt;
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpOTF" minOccurs="0"/&gt;
 *         &lt;element name="IdpBody" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpBody"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Versione" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idpHeader",
    "idpOTF",
    "idpBody"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeOTF")
public class IdpAllineamentoPendenzeOTF {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader")
    protected IdpOTF idpOTF;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBody idpBody;
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
     *     {@link IdpOTF }
     *     
     */
    public IdpOTF getIdpOTF() {
        return idpOTF;
    }

    /**
     * Imposta il valore della proprietà idpOTF.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpOTF }
     *     
     */
    public void setIdpOTF(IdpOTF value) {
        this.idpOTF = value;
    }

    /**
     * Recupera il valore della proprietà idpBody.
     * 
     * @return
     *     possible object is
     *     {@link IdpBody }
     *     
     */
    public IdpBody getIdpBody() {
        return idpBody;
    }

    /**
     * Imposta il valore della proprietà idpBody.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpBody }
     *     
     */
    public void setIdpBody(IdpBody value) {
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
