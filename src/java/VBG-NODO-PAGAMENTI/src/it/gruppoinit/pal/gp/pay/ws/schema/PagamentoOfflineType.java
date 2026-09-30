
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PagamentoOfflineType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PagamentoOfflineType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}RiferimentoPosizioneDebitoriaType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="datiPagamento" type="{http://www.paevolution.com/ws/pagamenti_types/}DatiPagamentoType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagamentoOfflineType", propOrder = {
    "datiPagamento"
})
public class PagamentoOfflineType
    extends RiferimentoPosizioneDebitoriaType
{

    protected DatiPagamentoType datiPagamento;

    /**
     * Recupera il valore della proprietà datiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link DatiPagamentoType }
     *     
     */
    public DatiPagamentoType getDatiPagamento() {
        return datiPagamento;
    }

    /**
     * Imposta il valore della proprietà datiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiPagamentoType }
     *     
     */
    public void setDatiPagamento(DatiPagamentoType value) {
        this.datiPagamento = value;
    }

}
