
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.include.TipoPagamento;


/**
 * <p>Classe Java per RiferimentoPagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoPagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
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
@XmlType(name = "RiferimentoPagamento", propOrder = {
    "idPagamento"
})
public class RiferimentoPagamento {

    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlAttribute(name = "TipoPagamento", required = true)
    protected TipoPagamento tipoPagamento;

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
