
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema;

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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TestataAvvisiScadutiType"/&gt;
 *         &lt;element name="CorpoAvvisiScaduti" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}CorpoAvvisiScadutiType"/&gt;
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
    "corpoAvvisiScaduti"
})
@XmlRootElement(name = "TrasmettiAvvisiScadutiRequest")
public class TrasmettiAvvisiScadutiRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataAvvisiScadutiType testata;
    @XmlElement(name = "CorpoAvvisiScaduti", required = true)
    protected CorpoAvvisiScadutiType corpoAvvisiScaduti;

    /**
     * Recupera il valore della proprietà testata.
     * 
     * @return
     *     possible object is
     *     {@link TestataAvvisiScadutiType }
     *     
     */
    public TestataAvvisiScadutiType getTestata() {
        return testata;
    }

    /**
     * Imposta il valore della proprietà testata.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataAvvisiScadutiType }
     *     
     */
    public void setTestata(TestataAvvisiScadutiType value) {
        this.testata = value;
    }

    /**
     * Recupera il valore della proprietà corpoAvvisiScaduti.
     * 
     * @return
     *     possible object is
     *     {@link CorpoAvvisiScadutiType }
     *     
     */
    public CorpoAvvisiScadutiType getCorpoAvvisiScaduti() {
        return corpoAvvisiScaduti;
    }

    /**
     * Imposta il valore della proprietà corpoAvvisiScaduti.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoAvvisiScadutiType }
     *     
     */
    public void setCorpoAvvisiScaduti(CorpoAvvisiScadutiType value) {
        this.corpoAvvisiScaduti = value;
    }

}
