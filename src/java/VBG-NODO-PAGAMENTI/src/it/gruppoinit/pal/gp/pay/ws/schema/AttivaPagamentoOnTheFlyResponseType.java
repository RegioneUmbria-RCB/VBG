
package it.gruppoinit.pal.gp.pay.ws.schema;

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
 *         &lt;element name="sessionePagamento" type="{http://www.paevolution.com/ws/pagamenti_types/}AttivaSessionePagamentoResponseType"/&gt;
 *         &lt;element name="posizioneInserita" type="{http://www.paevolution.com/ws/pagamenti_types/}EsitoOperazionePosizioneDebitoriaType" maxOccurs="unbounded"/&gt;
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
    "sessionePagamento",
    "posizioneInserita"
})
@XmlRootElement(name = "AttivaPagamentoOnTheFlyResponseType")
public class AttivaPagamentoOnTheFlyResponseType {

    @XmlElement(required = true)
    protected AttivaSessionePagamentoResponseType sessionePagamento;
    @XmlElement(required = true)
    protected List<EsitoOperazionePosizioneDebitoriaType> posizioneInserita;

    /**
     * Recupera il valore della proprietà sessionePagamento.
     * 
     * @return
     *     possible object is
     *     {@link AttivaSessionePagamentoResponseType }
     *     
     */
    public AttivaSessionePagamentoResponseType getSessionePagamento() {
        return sessionePagamento;
    }

    /**
     * Imposta il valore della proprietà sessionePagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link AttivaSessionePagamentoResponseType }
     *     
     */
    public void setSessionePagamento(AttivaSessionePagamentoResponseType value) {
        this.sessionePagamento = value;
    }

    /**
     * Gets the value of the posizioneInserita property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioneInserita property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioneInserita().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsitoOperazionePosizioneDebitoriaType }
     * 
     * 
     */
    public List<EsitoOperazionePosizioneDebitoriaType> getPosizioneInserita() {
        if (posizioneInserita == null) {
            posizioneInserita = new ArrayList<EsitoOperazionePosizioneDebitoriaType>();
        }
        return this.posizioneInserita;
    }

}
