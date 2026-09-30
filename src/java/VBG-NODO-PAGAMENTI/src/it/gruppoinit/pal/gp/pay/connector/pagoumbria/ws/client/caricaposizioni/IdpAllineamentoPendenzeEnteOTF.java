
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.IdpAllineamentoPendenzeOTF;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenzeOTF"/&gt;
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
    "idpAllineamentoPendenzeOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeEnteOTF")
public class IdpAllineamentoPendenzeEnteOTF {

    @XmlElement(name = "IdpAllineamentoPendenzeOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze", required = true)
    protected IdpAllineamentoPendenzeOTF idpAllineamentoPendenzeOTF;

    /**
     * Recupera il valore della proprietà idpAllineamentoPendenzeOTF.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenzeOTF }
     *     
     */
    public IdpAllineamentoPendenzeOTF getIdpAllineamentoPendenzeOTF() {
        return idpAllineamentoPendenzeOTF;
    }

    /**
     * Imposta il valore della proprietà idpAllineamentoPendenzeOTF.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenzeOTF }
     *     
     */
    public void setIdpAllineamentoPendenzeOTF(IdpAllineamentoPendenzeOTF value) {
        this.idpAllineamentoPendenzeOTF = value;
    }

}
