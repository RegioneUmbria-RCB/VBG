
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Propriet� relative
 *         all'inserimento del nuovo Pagamento
 *       
 * 
 * <p>Classe Java per nuovoPagamentoAttesoWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="nuovoPagamentoAttesoWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pagamentoAttesoWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}pagamentoAttesoWs"/&gt;
 *         &lt;element name="datiSpecificiRiscossione"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="138"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nuovoPagamentoAttesoWs", propOrder = {
    "pagamentoAttesoWs",
    "datiSpecificiRiscossione"
})
public class NuovoPagamentoAttesoWs {

    @XmlElement(required = true)
    protected PagamentoAttesoWs pagamentoAttesoWs;
    @XmlElement(required = true)
    protected String datiSpecificiRiscossione;

    /**
     * Recupera il valore della proprietà pagamentoAttesoWs.
     * 
     * @return
     *     possible object is
     *     {@link PagamentoAttesoWs }
     *     
     */
    public PagamentoAttesoWs getPagamentoAttesoWs() {
        return pagamentoAttesoWs;
    }

    /**
     * Imposta il valore della proprietà pagamentoAttesoWs.
     * 
     * @param value
     *     allowed object is
     *     {@link PagamentoAttesoWs }
     *     
     */
    public void setPagamentoAttesoWs(PagamentoAttesoWs value) {
        this.pagamentoAttesoWs = value;
    }

    /**
     * Recupera il valore della proprietà datiSpecificiRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    /**
     * Imposta il valore della proprietà datiSpecificiRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiSpecificiRiscossione(String value) {
        this.datiSpecificiRiscossione = value;
    }

}
