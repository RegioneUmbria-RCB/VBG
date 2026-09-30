
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ScaricaPagamentiRTPosizioniDebitorieResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ScaricaPagamentiRTPosizioniDebitorieResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PagamentiPosizioniDebitorie" type="{http://entranext.it/}ArrayOfScaricaPagamentoRTResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScaricaPagamentiRTPosizioniDebitorieResponse", propOrder = {
    "pagamentiPosizioniDebitorie"
})
public class ScaricaPagamentiRTPosizioniDebitorieResponse
    extends LinkNextResponse
{

    @XmlElement(name = "PagamentiPosizioniDebitorie")
    protected ArrayOfScaricaPagamentoRTResponse pagamentiPosizioniDebitorie;

    /**
     * Recupera il valore della proprietà pagamentiPosizioniDebitorie.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfScaricaPagamentoRTResponse }
     *     
     */
    public ArrayOfScaricaPagamentoRTResponse getPagamentiPosizioniDebitorie() {
        return pagamentiPosizioniDebitorie;
    }

    /**
     * Imposta il valore della proprietà pagamentiPosizioniDebitorie.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfScaricaPagamentoRTResponse }
     *     
     */
    public void setPagamentiPosizioniDebitorie(ArrayOfScaricaPagamentoRTResponse value) {
        this.pagamentiPosizioniDebitorie = value;
    }

}
