
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPosizioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="StatoPosizioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}EsitoOperazionePosizioneDebitoriaType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cronologiaStatiPosizione" type="{http://www.paevolution.com/ws/pagamenti_types/}DettaglioStatoPosizioneType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="datiPagamento" type="{http://www.paevolution.com/ws/pagamenti_types/}DatiPagamentoType" minOccurs="0"/&gt;
 *         &lt;element name="statoPagamentoNativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatoPosizioneType", propOrder = {
    "cronologiaStatiPosizione",
    "datiPagamento",
    "statoPagamentoNativo"
})
public class StatoPosizioneType
    extends EsitoOperazionePosizioneDebitoriaType
{

    protected List<DettaglioStatoPosizioneType> cronologiaStatiPosizione;
    protected DatiPagamentoType datiPagamento;
    protected String statoPagamentoNativo;

    /**
     * Gets the value of the cronologiaStatiPosizione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cronologiaStatiPosizione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCronologiaStatiPosizione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DettaglioStatoPosizioneType }
     * 
     * 
     */
    public List<DettaglioStatoPosizioneType> getCronologiaStatiPosizione() {
        if (cronologiaStatiPosizione == null) {
            cronologiaStatiPosizione = new ArrayList<DettaglioStatoPosizioneType>();
        }
        return this.cronologiaStatiPosizione;
    }

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

    /**
     * Recupera il valore della proprietà statoPagamentoNativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoPagamentoNativo() {
        return statoPagamentoNativo;
    }

    /**
     * Imposta il valore della proprietà statoPagamentoNativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoPagamentoNativo(String value) {
        this.statoPagamentoNativo = value;
    }

}
