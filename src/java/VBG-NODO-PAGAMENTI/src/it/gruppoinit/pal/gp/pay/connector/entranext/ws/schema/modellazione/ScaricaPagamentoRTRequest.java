
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ScaricaPagamentoRTRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ScaricaPagamentoRTRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_PAGAMENTO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScaricaPagamentoRTRequest", propOrder = {
    "idpagamento"
})
public class ScaricaPagamentoRTRequest
    extends LinkNextRequest
{

    @XmlElement(name = "ID_PAGAMENTO")
    protected int idpagamento;

    /**
     * Recupera il valore della proprietà idpagamento.
     * 
     */
    public int getIDPAGAMENTO() {
        return idpagamento;
    }

    /**
     * Imposta il valore della proprietà idpagamento.
     * 
     */
    public void setIDPAGAMENTO(int value) {
        this.idpagamento = value;
    }

}
