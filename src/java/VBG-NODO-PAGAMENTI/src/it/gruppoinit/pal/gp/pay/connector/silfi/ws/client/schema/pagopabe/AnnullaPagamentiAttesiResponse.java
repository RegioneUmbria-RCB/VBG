
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pagamentoAttesoRispostaWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}pagamentoAttesoRispostaWs" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "codiceErrore",
    "descrizioneErrore",
    "pagamentoAttesoRispostaWs"
})
@XmlRootElement(name = "annullaPagamentiAttesiResponse")
public class AnnullaPagamentiAttesiResponse {

    @XmlElement(required = true)
    protected String codiceErrore;
    @XmlElement(required = true)
    protected String descrizioneErrore;
    protected List<PagamentoAttesoRispostaWs> pagamentoAttesoRispostaWs;

    /**
     * Recupera il valore della proprietà codiceErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceErrore() {
        return codiceErrore;
    }

    /**
     * Imposta il valore della proprietà codiceErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceErrore(String value) {
        this.codiceErrore = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneErrore() {
        return descrizioneErrore;
    }

    /**
     * Imposta il valore della proprietà descrizioneErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneErrore(String value) {
        this.descrizioneErrore = value;
    }

    /**
     * Gets the value of the pagamentoAttesoRispostaWs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagamentoAttesoRispostaWs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagamentoAttesoRispostaWs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PagamentoAttesoRispostaWs }
     * 
     * 
     */
    public List<PagamentoAttesoRispostaWs> getPagamentoAttesoRispostaWs() {
        if (pagamentoAttesoRispostaWs == null) {
            pagamentoAttesoRispostaWs = new ArrayList<PagamentoAttesoRispostaWs>();
        }
        return this.pagamentoAttesoRispostaWs;
    }

}
