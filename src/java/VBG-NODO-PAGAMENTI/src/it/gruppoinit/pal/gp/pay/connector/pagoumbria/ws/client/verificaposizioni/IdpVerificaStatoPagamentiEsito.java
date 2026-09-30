
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito.IdpEsitoVerifica;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpEsitoVerifica"/&gt;
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
    "idpEsitoVerifica"
})
@XmlRootElement(name = "IdpVerificaStatoPagamenti.Esito")
public class IdpVerificaStatoPagamentiEsito {

    @XmlElement(name = "IdpEsitoVerifica", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", required = true)
    protected IdpEsitoVerifica idpEsitoVerifica;

    /**
     * Recupera il valore della proprietà idpEsitoVerifica.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoVerifica }
     *     
     */
    public IdpEsitoVerifica getIdpEsitoVerifica() {
        return idpEsitoVerifica;
    }

    /**
     * Imposta il valore della proprietà idpEsitoVerifica.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoVerifica }
     *     
     */
    public void setIdpEsitoVerifica(IdpEsitoVerifica value) {
        this.idpEsitoVerifica = value;
    }

}
