
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.EsitoAggiornamentoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.ResponseType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.csi.it/epay/epaywso/types}ResponseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoAggiornamento" type="{http://www.csi.it/epay/epaywso/types}EsitoAggiornamentoType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "esitoAggiornamento"
})
@XmlRootElement(name = "AggiornaPosizioniDebitorieResponse")
public class AggiornaPosizioniDebitorieResponse
    extends ResponseType
{

    @XmlElement(name = "EsitoAggiornamento")
    protected EsitoAggiornamentoType esitoAggiornamento;

    /**
     * Recupera il valore della proprietà esitoAggiornamento.
     * 
     * @return
     *     possible object is
     *     {@link EsitoAggiornamentoType }
     *     
     */
    public EsitoAggiornamentoType getEsitoAggiornamento() {
        return esitoAggiornamento;
    }

    /**
     * Imposta il valore della proprietà esitoAggiornamento.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoAggiornamentoType }
     *     
     */
    public void setEsitoAggiornamento(EsitoAggiornamentoType value) {
        this.esitoAggiornamento = value;
    }

}
