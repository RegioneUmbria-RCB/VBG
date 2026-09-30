
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ValidaRuoloPosizioniDebitorieRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ValidaRuoloPosizioniDebitorieRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RuoloPosizioniDebitorie" type="{http://entranext.it/}RuoloPosizioniDebitorie" minOccurs="0"/&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="PosizioniDebitorie" type="{http://entranext.it/}ArrayOfPosizioneDebitoria" minOccurs="0"/&gt;
 *         &lt;element name="NoteDiCredito" type="{http://entranext.it/}ArrayOfPosizioniNoteDiCredito" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidaRuoloPosizioniDebitorieRequest", propOrder = {
    "ruoloPosizioniDebitorie",
    "idruolo",
    "posizioniDebitorie",
    "noteDiCredito"
})
public class ValidaRuoloPosizioniDebitorieRequest
    extends LinkNextRequest
{

    @XmlElement(name = "RuoloPosizioniDebitorie")
    protected RuoloPosizioniDebitorie ruoloPosizioniDebitorie;
    @XmlElementRef(name = "ID_RUOLO", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idruolo;
    @XmlElement(name = "PosizioniDebitorie")
    protected ArrayOfPosizioneDebitoria posizioniDebitorie;
    @XmlElement(name = "NoteDiCredito")
    protected ArrayOfPosizioniNoteDiCredito noteDiCredito;

    /**
     * Recupera il valore della proprietà ruoloPosizioniDebitorie.
     * 
     * @return
     *     possible object is
     *     {@link RuoloPosizioniDebitorie }
     *     
     */
    public RuoloPosizioniDebitorie getRuoloPosizioniDebitorie() {
        return ruoloPosizioniDebitorie;
    }

    /**
     * Imposta il valore della proprietà ruoloPosizioniDebitorie.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloPosizioniDebitorie }
     *     
     */
    public void setRuoloPosizioniDebitorie(RuoloPosizioniDebitorie value) {
        this.ruoloPosizioniDebitorie = value;
    }

    /**
     * Recupera il valore della proprietà idruolo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIDRUOLO(JAXBElement<Integer> value) {
        this.idruolo = value;
    }

    /**
     * Recupera il valore della proprietà posizioniDebitorie.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPosizioneDebitoria }
     *     
     */
    public ArrayOfPosizioneDebitoria getPosizioniDebitorie() {
        return posizioniDebitorie;
    }

    /**
     * Imposta il valore della proprietà posizioniDebitorie.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPosizioneDebitoria }
     *     
     */
    public void setPosizioniDebitorie(ArrayOfPosizioneDebitoria value) {
        this.posizioniDebitorie = value;
    }

    /**
     * Recupera il valore della proprietà noteDiCredito.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPosizioniNoteDiCredito }
     *     
     */
    public ArrayOfPosizioniNoteDiCredito getNoteDiCredito() {
        return noteDiCredito;
    }

    /**
     * Imposta il valore della proprietà noteDiCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPosizioniNoteDiCredito }
     *     
     */
    public void setNoteDiCredito(ArrayOfPosizioniNoteDiCredito value) {
        this.noteDiCredito = value;
    }

}
