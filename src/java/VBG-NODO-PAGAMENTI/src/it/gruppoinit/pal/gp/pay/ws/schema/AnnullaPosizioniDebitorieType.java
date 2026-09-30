
package it.gruppoinit.pal.gp.pay.ws.schema;

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
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}PayRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="posizioniAnnullate" type="{http://www.paevolution.com/ws/pagamenti_types/}ElencoPosizioniDebitorieType"/&gt;
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
    "posizioniAnnullate"
})
@XmlRootElement(name = "AnnullaPosizioniDebitorieType")
public class AnnullaPosizioniDebitorieType
    extends PayRequestType
{

    @XmlElement(required = true)
    protected ElencoPosizioniDebitorieType posizioniAnnullate;

    /**
     * Recupera il valore della proprietà posizioniAnnullate.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPosizioniDebitorieType }
     *     
     */
    public ElencoPosizioniDebitorieType getPosizioniAnnullate() {
        return posizioniAnnullate;
    }

    /**
     * Imposta il valore della proprietà posizioniAnnullate.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPosizioniDebitorieType }
     *     
     */
    public void setPosizioniAnnullate(ElencoPosizioniDebitorieType value) {
        this.posizioniAnnullate = value;
    }

}
