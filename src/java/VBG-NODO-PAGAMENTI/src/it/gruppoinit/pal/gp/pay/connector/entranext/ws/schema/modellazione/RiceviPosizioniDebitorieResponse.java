
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiceviPosizioniDebitorieResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviPosizioniDebitorieResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioniDebitorie" type="{http://entranext.it/}ArrayOfPosizioneDebitoriaResult" minOccurs="0"/&gt;
 *         &lt;element name="NumeroTotalePosizioni" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AltrePosizioniPresenti" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviPosizioniDebitorieResponse", propOrder = {
    "posizioniDebitorie",
    "numeroTotalePosizioni",
    "altrePosizioniPresenti"
})
public class RiceviPosizioniDebitorieResponse
    extends LinkNextResponse
{

    @XmlElement(name = "PosizioniDebitorie")
    protected ArrayOfPosizioneDebitoriaResult posizioniDebitorie;
    @XmlElement(name = "NumeroTotalePosizioni")
    protected int numeroTotalePosizioni;
    @XmlElement(name = "AltrePosizioniPresenti")
    protected boolean altrePosizioniPresenti;

    /**
     * Recupera il valore della proprietà posizioniDebitorie.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPosizioneDebitoriaResult }
     *     
     */
    public ArrayOfPosizioneDebitoriaResult getPosizioniDebitorie() {
        return posizioniDebitorie;
    }

    /**
     * Imposta il valore della proprietà posizioniDebitorie.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPosizioneDebitoriaResult }
     *     
     */
    public void setPosizioniDebitorie(ArrayOfPosizioneDebitoriaResult value) {
        this.posizioniDebitorie = value;
    }

    /**
     * Recupera il valore della proprietà numeroTotalePosizioni.
     * 
     */
    public int getNumeroTotalePosizioni() {
        return numeroTotalePosizioni;
    }

    /**
     * Imposta il valore della proprietà numeroTotalePosizioni.
     * 
     */
    public void setNumeroTotalePosizioni(int value) {
        this.numeroTotalePosizioni = value;
    }

    /**
     * Recupera il valore della proprietà altrePosizioniPresenti.
     * 
     */
    public boolean isAltrePosizioniPresenti() {
        return altrePosizioniPresenti;
    }

    /**
     * Imposta il valore della proprietà altrePosizioniPresenti.
     * 
     */
    public void setAltrePosizioniPresenti(boolean value) {
        this.altrePosizioniPresenti = value;
    }

}
