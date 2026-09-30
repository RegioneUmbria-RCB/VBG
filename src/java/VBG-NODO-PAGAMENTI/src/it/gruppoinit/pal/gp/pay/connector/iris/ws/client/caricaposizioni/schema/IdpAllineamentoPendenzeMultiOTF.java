
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.HeaderTRT;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.IdpOTF;


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
 *         &lt;element name="HeaderTRT" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}HeaderTRT"/&gt;
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpOTF"/&gt;
 *         &lt;element name="Parametri" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenzeMultiOTFParametriType" minOccurs="0"/&gt;
 *         &lt;element name="IdpAllineamentoPendenzeOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenzeMultiOTFElementType" maxOccurs="unbounded"/&gt;
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
    "headerTRT",
    "idpOTF",
    "parametri",
    "idpAllineamentoPendenzeOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeMultiOTF")
public class IdpAllineamentoPendenzeMultiOTF {

    @XmlElement(name = "HeaderTRT", required = true)
    protected HeaderTRT headerTRT;
    @XmlElement(name = "IdpOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpOTF idpOTF;
    @XmlElement(name = "Parametri")
    protected IdpAllineamentoPendenzeMultiOTFParametriType parametri;
    @XmlElement(name = "IdpAllineamentoPendenzeOTF", required = true)
    protected List<IdpAllineamentoPendenzeMultiOTFElementType> idpAllineamentoPendenzeOTF;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Recupera il valore della proprietà headerTRT.
     * 
     * @return
     *     possible object is
     *     {@link HeaderTRT }
     *     
     */
    public HeaderTRT getHeaderTRT() {
        return headerTRT;
    }

    /**
     * Imposta il valore della proprietà headerTRT.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderTRT }
     *     
     */
    public void setHeaderTRT(HeaderTRT value) {
        this.headerTRT = value;
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
     * Recupera il valore della proprietà parametri.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenzeMultiOTFParametriType }
     *     
     */
    public IdpAllineamentoPendenzeMultiOTFParametriType getParametri() {
        return parametri;
    }

    /**
     * Imposta il valore della proprietà parametri.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenzeMultiOTFParametriType }
     *     
     */
    public void setParametri(IdpAllineamentoPendenzeMultiOTFParametriType value) {
        this.parametri = value;
    }

    /**
     * Gets the value of the idpAllineamentoPendenzeOTF property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idpAllineamentoPendenzeOTF property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdpAllineamentoPendenzeOTF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdpAllineamentoPendenzeMultiOTFElementType }
     * 
     * 
     */
    public List<IdpAllineamentoPendenzeMultiOTFElementType> getIdpAllineamentoPendenzeOTF() {
        if (idpAllineamentoPendenzeOTF == null) {
            idpAllineamentoPendenzeOTF = new ArrayList<IdpAllineamentoPendenzeMultiOTFElementType>();
        }
        return this.idpAllineamentoPendenzeOTF;
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
