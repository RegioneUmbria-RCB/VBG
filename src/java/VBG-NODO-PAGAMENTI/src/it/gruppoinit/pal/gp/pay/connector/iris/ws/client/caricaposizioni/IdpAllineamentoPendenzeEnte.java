
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.IdpAllineamentoPendenze;


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
 *         &lt;element name="IdpAllineamentoPendenze" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenze"/&gt;
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
    "idpAllineamentoPendenze"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeEnte")
public class IdpAllineamentoPendenzeEnte {

    @XmlElement(name = "IdpAllineamentoPendenze", required = true)
    protected IdpAllineamentoPendenze idpAllineamentoPendenze;

    /**
     * Recupera il valore della proprietà idpAllineamentoPendenze.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenze }
     *     
     */
    public IdpAllineamentoPendenze getIdpAllineamentoPendenze() {
        return idpAllineamentoPendenze;
    }

    /**
     * Imposta il valore della proprietà idpAllineamentoPendenze.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenze }
     *     
     */
    public void setIdpAllineamentoPendenze(IdpAllineamentoPendenze value) {
        this.idpAllineamentoPendenze = value;
    }

}
