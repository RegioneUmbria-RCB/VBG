
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per E2ESender complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="E2ESender"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="E2ESndrId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
 *         &lt;element name="E2ESndrSys" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "E2ESender", propOrder = {
    "e2ESndrId",
    "e2ESndrSys"
})
public class E2ESender {

    @XmlElement(name = "E2ESndrId", required = true)
    protected String e2ESndrId;
    @XmlElement(name = "E2ESndrSys", required = true)
    protected String e2ESndrSys;

    /**
     * Recupera il valore della proprietà e2ESndrId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ESndrId() {
        return e2ESndrId;
    }

    /**
     * Imposta il valore della proprietà e2ESndrId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ESndrId(String value) {
        this.e2ESndrId = value;
    }

    /**
     * Recupera il valore della proprietà e2ESndrSys.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ESndrSys() {
        return e2ESndrSys;
    }

    /**
     * Imposta il valore della proprietà e2ESndrSys.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ESndrSys(String value) {
        this.e2ESndrSys = value;
    }

}
