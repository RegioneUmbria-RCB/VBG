
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.E2ESender;


/**
 * <p>Classe Java per IdpAllineamentoPendenzeMultiOTFElementType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdpAllineamentoPendenzeMultiOTFElementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="E2ESender" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}E2ESender"/&gt;
 *         &lt;element name="E2EMsgId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IdpBody" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpBody"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpAllineamentoPendenzeMultiOTFElementType", propOrder = {
    "e2ESender",
    "e2EMsgId",
    "idpBody"
})
public class IdpAllineamentoPendenzeMultiOTFElementType {

    @XmlElement(name = "E2ESender", required = true)
    protected E2ESender e2ESender;
    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBody idpBody;

    /**
     * Recupera il valore della proprietà e2ESender.
     * 
     * @return
     *     possible object is
     *     {@link E2ESender }
     *     
     */
    public E2ESender getE2ESender() {
        return e2ESender;
    }

    /**
     * Imposta il valore della proprietà e2ESender.
     * 
     * @param value
     *     allowed object is
     *     {@link E2ESender }
     *     
     */
    public void setE2ESender(E2ESender value) {
        this.e2ESender = value;
    }

    /**
     * Recupera il valore della proprietà e2EMsgId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2EMsgId() {
        return e2EMsgId;
    }

    /**
     * Imposta il valore della proprietà e2EMsgId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2EMsgId(String value) {
        this.e2EMsgId = value;
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

}
