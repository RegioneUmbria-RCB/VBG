
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per E2EReceiver complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="E2EReceiver"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="E2ERcvrId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
 *         &lt;element name="E2ERcvrSys" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "E2EReceiver", propOrder = {
    "e2ERcvrId",
    "e2ERcvrSys"
})
public class E2EReceiver {

    @XmlElement(name = "E2ERcvrId", required = true)
    protected String e2ERcvrId;
    @XmlElement(name = "E2ERcvrSys", required = true)
    protected String e2ERcvrSys;

    /**
     * Recupera il valore della proprietà e2ERcvrId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ERcvrId() {
        return e2ERcvrId;
    }

    /**
     * Imposta il valore della proprietà e2ERcvrId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ERcvrId(String value) {
        this.e2ERcvrId = value;
    }

    /**
     * Recupera il valore della proprietà e2ERcvrSys.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ERcvrSys() {
        return e2ERcvrSys;
    }

    /**
     * Imposta il valore della proprietà e2ERcvrSys.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ERcvrSys(String value) {
        this.e2ERcvrSys = value;
    }

}
