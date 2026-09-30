
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.IdpVerificaStatoPagamento;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}IdpVerificaStatoPagamento"/&gt;
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
    "idpVerificaStatoPagamento"
})
@XmlRootElement(name = "IdpVerificaStatoPagamenti")
public class IdpVerificaStatoPagamenti {

    @XmlElement(name = "IdpVerificaStatoPagamento", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento", required = true)
    protected IdpVerificaStatoPagamento idpVerificaStatoPagamento;

    /**
     * Recupera il valore della proprietà idpVerificaStatoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link IdpVerificaStatoPagamento }
     *     
     */
    public IdpVerificaStatoPagamento getIdpVerificaStatoPagamento() {
        return idpVerificaStatoPagamento;
    }

    /**
     * Imposta il valore della proprietà idpVerificaStatoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpVerificaStatoPagamento }
     *     
     */
    public void setIdpVerificaStatoPagamento(IdpVerificaStatoPagamento value) {
        this.idpVerificaStatoPagamento = value;
    }

}
