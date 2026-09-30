
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}IdpInformativaPagamento"/&gt;
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
    "idpInformativaPagamento"
})
@XmlRootElement(name = "IdpInformativaPagamento")
public class IdpInformativaPagamento {

    @XmlElement(name = "IdpInformativaPagamento", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento", required = true)
    protected it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpInformativaPagamento idpInformativaPagamento;

    /**
     * Recupera il valore della proprietà idpInformativaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpInformativaPagamento }
     *     
     */
    public it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpInformativaPagamento getIdpInformativaPagamento() {
        return idpInformativaPagamento;
    }

    /**
     * Imposta il valore della proprietà idpInformativaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpInformativaPagamento }
     *     
     */
    public void setIdpInformativaPagamento(it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpInformativaPagamento value) {
        this.idpInformativaPagamento = value;
    }

}
