
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema;

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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}TestataAggiornaPosizioniDebitorie"/&gt;
 *         &lt;element name="ElencoPosizioniDaAggiornare" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}ElencoPosizioniDaAggiornareType"/&gt;
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
    "testata",
    "elencoPosizioniDaAggiornare"
})
@XmlRootElement(name = "AggiornaPosizioniDebitorieRequest")
public class AggiornaPosizioniDebitorieRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataAggiornaPosizioniDebitorie testata;
    @XmlElement(name = "ElencoPosizioniDaAggiornare", required = true)
    protected ElencoPosizioniDaAggiornareType elencoPosizioniDaAggiornare;

    /**
     * Recupera il valore della proprietà testata.
     * 
     * @return
     *     possible object is
     *     {@link TestataAggiornaPosizioniDebitorie }
     *     
     */
    public TestataAggiornaPosizioniDebitorie getTestata() {
        return testata;
    }

    /**
     * Imposta il valore della proprietà testata.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataAggiornaPosizioniDebitorie }
     *     
     */
    public void setTestata(TestataAggiornaPosizioniDebitorie value) {
        this.testata = value;
    }

    /**
     * Recupera il valore della proprietà elencoPosizioniDaAggiornare.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPosizioniDaAggiornareType }
     *     
     */
    public ElencoPosizioniDaAggiornareType getElencoPosizioniDaAggiornare() {
        return elencoPosizioniDaAggiornare;
    }

    /**
     * Imposta il valore della proprietà elencoPosizioniDaAggiornare.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPosizioniDaAggiornareType }
     *     
     */
    public void setElencoPosizioniDaAggiornare(ElencoPosizioniDaAggiornareType value) {
        this.elencoPosizioniDaAggiornare = value;
    }

}
