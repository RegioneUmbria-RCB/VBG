
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per datiAnnullaPagamentoAttesoWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="datiAnnullaPagamentoAttesoWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annullaPagamentoAttesoWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}annullaPagamentoAttesoWs"/&gt;
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
@XmlType(name = "datiAnnullaPagamentoAttesoWs", propOrder = {
    "annullaPagamentoAttesoWs",
    "motivazione"
})
public class DatiAnnullaPagamentoAttesoWs {

    @XmlElement(required = true)
    protected AnnullaPagamentoAttesoWs annullaPagamentoAttesoWs;
    @XmlElement(required = true)
    protected String motivazione;

    /**
     * Recupera il valore della proprietà annullaPagamentoAttesoWs.
     * 
     * @return
     *     possible object is
     *     {@link AnnullaPagamentoAttesoWs }
     *     
     */
    public AnnullaPagamentoAttesoWs getAnnullaPagamentoAttesoWs() {
        return annullaPagamentoAttesoWs;
    }

    /**
     * Imposta il valore della proprietà annullaPagamentoAttesoWs.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnullaPagamentoAttesoWs }
     *     
     */
    public void setAnnullaPagamentoAttesoWs(AnnullaPagamentoAttesoWs value) {
        this.annullaPagamentoAttesoWs = value;
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
