
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DatiFatturaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DatiFatturaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}RiferimentoPosizioneDebitoriaType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="modalitaPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dettaglio" type="{http://www.paevolution.com/ws/pagamenti_types/}DettaglioFatturaType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiFatturaType", propOrder = {
    "modalitaPagamento",
    "dettaglio"
})
@XmlRootElement
public class DatiFatturaType
    extends RiferimentoPosizioneDebitoriaType
{

    protected String modalitaPagamento;
    protected DettaglioFatturaType dettaglio;

    /**
     * Recupera il valore della proprietà modalitaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalitaPagamento() {
        return modalitaPagamento;
    }

    /**
     * Imposta il valore della proprietà modalitaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalitaPagamento(String value) {
        this.modalitaPagamento = value;
    }

    /**
     * Recupera il valore della proprietà dettaglio.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioFatturaType }
     *     
     */
    public DettaglioFatturaType getDettaglio() {
        return dettaglio;
    }

    /**
     * Imposta il valore della proprietà dettaglio.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioFatturaType }
     *     
     */
    public void setDettaglio(DettaglioFatturaType value) {
        this.dettaglio = value;
    }

}
