
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciRuoloPosizioniResponseBase complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciRuoloPosizioniResponseBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RuoloPosizioniDebitorie" type="{http://entranext.it/}RuoloPosizioniDebitorieResult" minOccurs="0"/&gt;
 *         &lt;element name="NumeroPosizioniInserite" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="NumeroPosizioniScartate" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="NumeroPosizioniGiaInserite" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="PosizioniInserite" type="{http://entranext.it/}ArrayOfEsitoPosizioneDebitoriaInserita" minOccurs="0"/&gt;
 *         &lt;element name="PosizioniScartate" type="{http://entranext.it/}ArrayOfEsitoPosizioneDebitoriaScartata" minOccurs="0"/&gt;
 *         &lt;element name="PosizioniGiaInserite" type="{http://entranext.it/}ArrayOfEsitoPosizioneDebitoriaInserita" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciRuoloPosizioniResponseBase", propOrder = {
    "ruoloPosizioniDebitorie",
    "numeroPosizioniInserite",
    "numeroPosizioniScartate",
    "numeroPosizioniGiaInserite",
    "posizioniInserite",
    "posizioniScartate",
    "posizioniGiaInserite"
})
@XmlSeeAlso({
    InserisciRuoloPosizioniResponse.class
})
public abstract class InserisciRuoloPosizioniResponseBase
    extends LinkNextResponse
{

    @XmlElement(name = "RuoloPosizioniDebitorie")
    protected RuoloPosizioniDebitorieResult ruoloPosizioniDebitorie;
    @XmlElement(name = "NumeroPosizioniInserite")
    protected Integer numeroPosizioniInserite;
    @XmlElement(name = "NumeroPosizioniScartate")
    protected Integer numeroPosizioniScartate;
    @XmlElement(name = "NumeroPosizioniGiaInserite")
    protected Integer numeroPosizioniGiaInserite;
    @XmlElement(name = "PosizioniInserite")
    protected ArrayOfEsitoPosizioneDebitoriaInserita posizioniInserite;
    @XmlElement(name = "PosizioniScartate")
    protected ArrayOfEsitoPosizioneDebitoriaScartata posizioniScartate;
    @XmlElement(name = "PosizioniGiaInserite")
    protected ArrayOfEsitoPosizioneDebitoriaInserita posizioniGiaInserite;

    /**
     * Recupera il valore della proprietà ruoloPosizioniDebitorie.
     * 
     * @return
     *     possible object is
     *     {@link RuoloPosizioniDebitorieResult }
     *     
     */
    public RuoloPosizioniDebitorieResult getRuoloPosizioniDebitorie() {
        return ruoloPosizioniDebitorie;
    }

    /**
     * Imposta il valore della proprietà ruoloPosizioniDebitorie.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloPosizioniDebitorieResult }
     *     
     */
    public void setRuoloPosizioniDebitorie(RuoloPosizioniDebitorieResult value) {
        this.ruoloPosizioniDebitorie = value;
    }

    /**
     * Recupera il valore della proprietà numeroPosizioniInserite.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroPosizioniInserite() {
        return numeroPosizioniInserite;
    }

    /**
     * Imposta il valore della proprietà numeroPosizioniInserite.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroPosizioniInserite(Integer value) {
        this.numeroPosizioniInserite = value;
    }

    /**
     * Recupera il valore della proprietà numeroPosizioniScartate.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroPosizioniScartate() {
        return numeroPosizioniScartate;
    }

    /**
     * Imposta il valore della proprietà numeroPosizioniScartate.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroPosizioniScartate(Integer value) {
        this.numeroPosizioniScartate = value;
    }

    /**
     * Recupera il valore della proprietà numeroPosizioniGiaInserite.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroPosizioniGiaInserite() {
        return numeroPosizioniGiaInserite;
    }

    /**
     * Imposta il valore della proprietà numeroPosizioniGiaInserite.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroPosizioniGiaInserite(Integer value) {
        this.numeroPosizioniGiaInserite = value;
    }

    /**
     * Recupera il valore della proprietà posizioniInserite.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaInserita }
     *     
     */
    public ArrayOfEsitoPosizioneDebitoriaInserita getPosizioniInserite() {
        return posizioniInserite;
    }

    /**
     * Imposta il valore della proprietà posizioniInserite.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaInserita }
     *     
     */
    public void setPosizioniInserite(ArrayOfEsitoPosizioneDebitoriaInserita value) {
        this.posizioniInserite = value;
    }

    /**
     * Recupera il valore della proprietà posizioniScartate.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaScartata }
     *     
     */
    public ArrayOfEsitoPosizioneDebitoriaScartata getPosizioniScartate() {
        return posizioniScartate;
    }

    /**
     * Imposta il valore della proprietà posizioniScartate.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaScartata }
     *     
     */
    public void setPosizioniScartate(ArrayOfEsitoPosizioneDebitoriaScartata value) {
        this.posizioniScartate = value;
    }

    /**
     * Recupera il valore della proprietà posizioniGiaInserite.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaInserita }
     *     
     */
    public ArrayOfEsitoPosizioneDebitoriaInserita getPosizioniGiaInserite() {
        return posizioniGiaInserite;
    }

    /**
     * Imposta il valore della proprietà posizioniGiaInserite.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaInserita }
     *     
     */
    public void setPosizioniGiaInserite(ArrayOfEsitoPosizioneDebitoriaInserita value) {
        this.posizioniGiaInserite = value;
    }

}
