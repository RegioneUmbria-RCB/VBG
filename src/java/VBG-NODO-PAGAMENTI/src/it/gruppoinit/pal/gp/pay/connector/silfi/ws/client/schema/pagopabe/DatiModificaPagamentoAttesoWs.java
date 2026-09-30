
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per datiModificaPagamentoAttesoWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="datiModificaPagamentoAttesoWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="modificaPagamentoAttesoWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}pagamentoAttesoWs"/&gt;
 *         &lt;element name="motivazione"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
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
@XmlType(name = "datiModificaPagamentoAttesoWs", propOrder = {
    "modificaPagamentoAttesoWs",
    "motivazione"
})
public class DatiModificaPagamentoAttesoWs {

    @XmlElement(required = true)
    protected PagamentoAttesoWs modificaPagamentoAttesoWs;
    @XmlElement(required = true)
    protected String motivazione;

    /**
     * Recupera il valore della proprietà modificaPagamentoAttesoWs.
     * 
     * @return
     *     possible object is
     *     {@link PagamentoAttesoWs }
     *     
     */
    public PagamentoAttesoWs getModificaPagamentoAttesoWs() {
        return modificaPagamentoAttesoWs;
    }

    /**
     * Imposta il valore della proprietà modificaPagamentoAttesoWs.
     * 
     * @param value
     *     allowed object is
     *     {@link PagamentoAttesoWs }
     *     
     */
    public void setModificaPagamentoAttesoWs(PagamentoAttesoWs value) {
        this.modificaPagamentoAttesoWs = value;
    }

    /**
     * Recupera il valore della proprietà motivazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivazione() {
        return motivazione;
    }

    /**
     * Imposta il valore della proprietà motivazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivazione(String value) {
        this.motivazione = value;
    }

}
