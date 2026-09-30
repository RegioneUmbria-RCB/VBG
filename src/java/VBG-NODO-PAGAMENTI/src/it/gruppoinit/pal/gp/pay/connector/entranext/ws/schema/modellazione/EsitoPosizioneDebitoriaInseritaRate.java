
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EsitoPosizioneDebitoriaInserita_Rate complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EsitoPosizioneDebitoriaInserita_Rate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumeroRata" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IUV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoPosizioneDebitoriaInserita_Rate", propOrder = {
    "numeroRata",
    "iuv"
})
public class EsitoPosizioneDebitoriaInseritaRate {

    @XmlElement(name = "NumeroRata")
    protected int numeroRata;
    @XmlElement(name = "IUV")
    protected String iuv;

    /**
     * Recupera il valore della proprietà numeroRata.
     * 
     */
    public int getNumeroRata() {
        return numeroRata;
    }

    /**
     * Imposta il valore della proprietà numeroRata.
     * 
     */
    public void setNumeroRata(int value) {
        this.numeroRata = value;
    }

    /**
     * Recupera il valore della proprietà iuv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUV() {
        return iuv;
    }

    /**
     * Imposta il valore della proprietà iuv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUV(String value) {
        this.iuv = value;
    }

}
