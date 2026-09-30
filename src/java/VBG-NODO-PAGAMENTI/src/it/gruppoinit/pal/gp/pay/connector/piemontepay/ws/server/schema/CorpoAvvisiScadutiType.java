
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CorpoAvvisiScadutiType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CorpoAvvisiScadutiType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ElencoAvvisiScaduti"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="AvvisoScaduto" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}AvvisoScadutoType" maxOccurs="1000"/&gt;
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
@XmlType(name = "CorpoAvvisiScadutiType", propOrder = {
    "elencoAvvisiScaduti"
})
public class CorpoAvvisiScadutiType {

    @XmlElement(name = "ElencoAvvisiScaduti", required = true)
    protected CorpoAvvisiScadutiType.ElencoAvvisiScaduti elencoAvvisiScaduti;

    /**
     * Recupera il valore della proprietà elencoAvvisiScaduti.
     * 
     * @return
     *     possible object is
     *     {@link CorpoAvvisiScadutiType.ElencoAvvisiScaduti }
     *     
     */
    public CorpoAvvisiScadutiType.ElencoAvvisiScaduti getElencoAvvisiScaduti() {
        return elencoAvvisiScaduti;
    }

    /**
     * Imposta il valore della proprietà elencoAvvisiScaduti.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoAvvisiScadutiType.ElencoAvvisiScaduti }
     *     
     */
    public void setElencoAvvisiScaduti(CorpoAvvisiScadutiType.ElencoAvvisiScaduti value) {
        this.elencoAvvisiScaduti = value;
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
     *         &lt;element name="AvvisoScaduto" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}AvvisoScadutoType" maxOccurs="1000"/&gt;
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
        "avvisoScaduto"
    })
    public static class ElencoAvvisiScaduti {

        @XmlElement(name = "AvvisoScaduto", required = true)
        protected List<AvvisoScadutoType> avvisoScaduto;

        /**
         * Gets the value of the avvisoScaduto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the avvisoScaduto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAvvisoScaduto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AvvisoScadutoType }
         * 
         * 
         */
        public List<AvvisoScadutoType> getAvvisoScaduto() {
            if (avvisoScaduto == null) {
                avvisoScaduto = new ArrayList<AvvisoScadutoType>();
            }
            return this.avvisoScaduto;
        }

    }

}
