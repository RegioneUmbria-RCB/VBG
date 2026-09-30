
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.esito.InfoMessaggio;


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
 *         &lt;element name="infoMessaggio" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InfoMessaggio"/&gt;
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
    "infoMessaggio"
})
@XmlRootElement(name = "IdpInformativaPagamentoResponse")
public class IdpInformativaPagamentoResponse {

    @XmlElement(namespace = "", required = true)
    protected InfoMessaggio infoMessaggio;

    /**
     * Recupera il valore della proprietà infoMessaggio.
     * 
     * @return
     *     possible object is
     *     {@link InfoMessaggio }
     *     
     */
    public InfoMessaggio getInfoMessaggio() {
        return infoMessaggio;
    }

    /**
     * Imposta il valore della proprietà infoMessaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoMessaggio }
     *     
     */
    public void setInfoMessaggio(InfoMessaggio value) {
        this.infoMessaggio = value;
    }

}
