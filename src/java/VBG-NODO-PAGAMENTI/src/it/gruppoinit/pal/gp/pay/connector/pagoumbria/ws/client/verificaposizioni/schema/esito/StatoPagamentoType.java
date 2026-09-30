
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.include.VerificaStatoPagamento;


/**
 * <p>Classe Java per StatoPagamentoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="StatoPagamentoType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude&gt;VerificaStatoPagamento"&gt;
 *       &lt;attribute name="IdPagamento" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="TipoPendenza" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatoPagamentoType", propOrder = {
    "value"
})
public class StatoPagamentoType {

    @XmlValue
    protected VerificaStatoPagamento value;
    @XmlAttribute(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlAttribute(name = "TipoPendenza", required = true)
    protected String tipoPendenza;

    /**
     * Recupera il valore della proprietà value.
     * 
     * @return
     *     possible object is
     *     {@link VerificaStatoPagamento }
     *     
     */
    public VerificaStatoPagamento getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificaStatoPagamento }
     *     
     */
    public void setValue(VerificaStatoPagamento value) {
        this.value = value;
    }

    /**
     * Recupera il valore della proprietà idPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPagamento() {
        return idPagamento;
    }

    /**
     * Imposta il valore della proprietà idPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPagamento(String value) {
        this.idPagamento = value;
    }

    /**
     * Recupera il valore della proprietà tipoPendenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPendenza() {
        return tipoPendenza;
    }

    /**
     * Imposta il valore della proprietà tipoPendenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPendenza(String value) {
        this.tipoPendenza = value;
    }

}
