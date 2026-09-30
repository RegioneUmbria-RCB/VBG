
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.IdpEsitoOTF;


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
 *         &lt;element name="IdpEsitoOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpEsitoOTF"/&gt;
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
    "idpEsitoOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeEnteOTF.Esito")
public class IdpAllineamentoPendenzeEnteOTFEsito {

    @XmlElement(name = "IdpEsitoOTF", required = true, namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito")
    protected IdpEsitoOTF idpEsitoOTF;

    /**
     * Recupera il valore della proprietà idpEsitoOTF.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoOTF }
     *     
     */
    public IdpEsitoOTF getIdpEsitoOTF() {
        return idpEsitoOTF;
    }

    /**
     * Imposta il valore della proprietà idpEsitoOTF.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoOTF }
     *     
     */
    public void setIdpEsitoOTF(IdpEsitoOTF value) {
        this.idpEsitoOTF = value;
    }

}
