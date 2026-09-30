
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.IdpAllineamentoPendenzeMultiOTF;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenzeMultiOTF"/&gt;
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
    "idpAllineamentoPendenzeMultiOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeMultiEnteOTF")
public class IdpAllineamentoPendenzeMultiEnteOTF {

    @XmlElement(name = "IdpAllineamentoPendenzeMultiOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze", required = true)
    protected IdpAllineamentoPendenzeMultiOTF idpAllineamentoPendenzeMultiOTF;

    /**
     * Recupera il valore della proprietà idpAllineamentoPendenzeMultiOTF.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenzeMultiOTF }
     *     
     */
    public IdpAllineamentoPendenzeMultiOTF getIdpAllineamentoPendenzeMultiOTF() {
        return idpAllineamentoPendenzeMultiOTF;
    }

    /**
     * Imposta il valore della proprietà idpAllineamentoPendenzeMultiOTF.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenzeMultiOTF }
     *     
     */
    public void setIdpAllineamentoPendenzeMultiOTF(IdpAllineamentoPendenzeMultiOTF value) {
        this.idpAllineamentoPendenzeMultiOTF = value;
    }

}
