
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdpHeader complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdpHeader"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TRT" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}HeaderTRT"/&gt;
 *         &lt;element name="E2E" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}HeaderE2E"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpHeader", propOrder = {
    "trt",
    "e2E"
})
public class IdpHeader {

    @XmlElement(name = "TRT", required = true)
    protected HeaderTRT trt;
    @XmlElement(name = "E2E", required = true)
    protected HeaderE2E e2E;

    /**
     * Recupera il valore della proprietà trt.
     * 
     * @return
     *     possible object is
     *     {@link HeaderTRT }
     *     
     */
    public HeaderTRT getTRT() {
        return trt;
    }

    /**
     * Imposta il valore della proprietà trt.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderTRT }
     *     
     */
    public void setTRT(HeaderTRT value) {
        this.trt = value;
    }

    /**
     * Recupera il valore della proprietà e2E.
     * 
     * @return
     *     possible object is
     *     {@link HeaderE2E }
     *     
     */
    public HeaderE2E getE2E() {
        return e2E;
    }

    /**
     * Imposta il valore della proprietà e2E.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderE2E }
     *     
     */
    public void setE2E(HeaderE2E value) {
        this.e2E = value;
    }

}
