
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdpMultiEsitoOTF complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdpMultiEsitoOTF"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdpOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpOTFType" minOccurs="0"/&gt;
 *         &lt;element name="IdpEsitoOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpMultiEsitoOTFElement" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "IdpMultiEsitoOTF", propOrder = {
    "idpOTF",
    "idpEsitoOTF"
})
public class IdpMultiEsitoOTF {

    @XmlElement(name = "IdpOTF")
    protected IdpOTFType idpOTF;
    @XmlElement(name = "IdpEsitoOTF")
    protected List<IdpMultiEsitoOTFElement> idpEsitoOTF;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

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
     * Gets the value of the idpEsitoOTF property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idpEsitoOTF property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdpEsitoOTF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdpMultiEsitoOTFElement }
     * 
     * 
     */
    public List<IdpMultiEsitoOTFElement> getIdpEsitoOTF() {
        if (idpEsitoOTF == null) {
            idpEsitoOTF = new ArrayList<IdpMultiEsitoOTFElement>();
        }
        return this.idpEsitoOTF;
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
