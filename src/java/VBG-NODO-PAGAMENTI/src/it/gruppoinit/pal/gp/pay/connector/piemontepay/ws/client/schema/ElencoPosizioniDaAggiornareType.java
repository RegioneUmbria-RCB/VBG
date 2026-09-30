
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ElencoPosizioniDaAggiornareType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ElencoPosizioniDaAggiornareType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioniDaAggiornare"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="PosizioneDaAggiornare" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}PosizioneDaAggiornareType" maxOccurs="1000"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
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
@XmlType(name = "ElencoPosizioniDaAggiornareType", propOrder = {
    "posizioniDaAggiornare"
})
public class ElencoPosizioniDaAggiornareType {

    @XmlElement(name = "PosizioniDaAggiornare", required = true)
    protected ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare posizioniDaAggiornare;

    /**
     * Recupera il valore della proprietà posizioniDaAggiornare.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare }
     *     
     */
    public ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare getPosizioniDaAggiornare() {
        return posizioniDaAggiornare;
    }

    /**
     * Imposta il valore della proprietà posizioniDaAggiornare.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare }
     *     
     */
    public void setPosizioniDaAggiornare(ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare value) {
        this.posizioniDaAggiornare = value;
    }


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
     *         &lt;element name="PosizioneDaAggiornare" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}PosizioneDaAggiornareType" maxOccurs="1000"/&gt;
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
        "posizioneDaAggiornare"
    })
    public static class PosizioniDaAggiornare {

        @XmlElement(name = "PosizioneDaAggiornare", required = true)
        protected List<PosizioneDaAggiornareType> posizioneDaAggiornare;

        /**
         * Gets the value of the posizioneDaAggiornare property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the posizioneDaAggiornare property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPosizioneDaAggiornare().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PosizioneDaAggiornareType }
         * 
         * 
         */
        public List<PosizioneDaAggiornareType> getPosizioneDaAggiornare() {
            if (posizioneDaAggiornare == null) {
                posizioneDaAggiornare = new ArrayList<PosizioneDaAggiornareType>();
            }
            return this.posizioneDaAggiornare;
        }

    }

}
