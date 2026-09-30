
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.TipoPagamento;


/**
 * <p>Classe Java per InfoPagamento.InsertReplace complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InfoPagamento.InsertReplace"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="TipoPagamento" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoPagamento" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoPagamento.InsertReplace")
@XmlSeeAlso({
    it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.PendenzaInsertReplace.InfoPagamento.class,
    it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.PendenzaUpdateMassivo.InfoPagamento.class
})
public class InfoPagamentoInsertReplace {

    @XmlAttribute(name = "TipoPagamento", required = true)
    protected TipoPagamento tipoPagamento;

    /**
     * Recupera il valore della proprietà tipoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link TipoPagamento }
     *     
     */
    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    /**
     * Imposta il valore della proprietà tipoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPagamento }
     *     
     */
    public void setTipoPagamento(TipoPagamento value) {
        this.tipoPagamento = value;
    }

}
