
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.IdpMultiEsitoOTF;


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
 *         &lt;element name="IdpMultiEsitoOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpMultiEsitoOTF"/&gt;
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
    "idpMultiEsitoOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeMultiEnteOTF.Esito")
public class IdpAllineamentoPendenzeMultiEnteOTFEsito {

    @XmlElement(name = "IdpMultiEsitoOTF", required = true)
    protected IdpMultiEsitoOTF idpMultiEsitoOTF;

    /**
     * Recupera il valore della proprietà idpMultiEsitoOTF.
     * 
     * @return
     *     possible object is
     *     {@link IdpMultiEsitoOTF }
     *     
     */
    public IdpMultiEsitoOTF getIdpMultiEsitoOTF() {
        return idpMultiEsitoOTF;
    }

    /**
     * Imposta il valore della proprietà idpMultiEsitoOTF.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpMultiEsitoOTF }
     *     
     */
    public void setIdpMultiEsitoOTF(IdpMultiEsitoOTF value) {
        this.idpMultiEsitoOTF = value;
    }

}
