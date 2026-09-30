
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <pClasse Java per RispostaRicercaPagamentiGiornalieri complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="RispostaRicercaPagamentiGiornalieri"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Pagamenti" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}ArrayOfPagamentoGiornaliero" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaRicercaPagamentiGiornalieri", propOrder = {
    "pagamenti"
})
public class RispostaRicercaPagamentiGiornalieri {

    @XmlElement(name = "Pagamenti", required = false)
    protected ArrayOfPagamentoGiornaliero pagamenti;

    /**
     * Recupera il valore della proprietà pagamenti.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPagamentoGiornaliero }{@code }
     *     
     */
    public ArrayOfPagamentoGiornaliero getPagamenti() {
        return pagamenti;
    }

    /**
     * Imposta il valore della proprietà pagamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPagamentoGiornaliero }{@code }
     *     
     */
    public void setPagamenti(ArrayOfPagamentoGiornaliero value) {
        this.pagamenti = value;
    }

}
