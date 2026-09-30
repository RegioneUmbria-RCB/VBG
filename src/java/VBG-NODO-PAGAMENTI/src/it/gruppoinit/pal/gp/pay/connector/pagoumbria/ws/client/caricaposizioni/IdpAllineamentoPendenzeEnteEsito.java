
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.IdpEsito;


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
 *         &lt;element name="IdpEsito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpEsito"/&gt;
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
    "idpEsito"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeEnte.Esito")
public class IdpAllineamentoPendenzeEnteEsito {

    @XmlElement(name = "IdpEsito", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", required = true)
    protected IdpEsito idpEsito;

    /**
     * Recupera il valore della proprietà idpEsito.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsito }
     *     
     */
    public IdpEsito getIdpEsito() {
        return idpEsito;
    }

    /**
     * Imposta il valore della proprietà idpEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsito }
     *     
     */
    public void setIdpEsito(IdpEsito value) {
        this.idpEsito = value;
    }

}
