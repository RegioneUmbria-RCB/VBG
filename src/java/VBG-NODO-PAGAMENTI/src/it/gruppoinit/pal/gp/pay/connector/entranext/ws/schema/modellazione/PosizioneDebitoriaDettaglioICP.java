
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PosizioneDebitoria_DettaglioICP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoria_DettaglioICP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}PosizioneDebitoria_DettaglioBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Oggetto" type="{http://entranext.it/}ICP_Oggetto" minOccurs="0"/&gt;
 *         &lt;element name="Ubicazione" type="{http://entranext.it/}Ubicazione" minOccurs="0"/&gt;
 *         &lt;element name="Autorizzazione" type="{http://entranext.it/}ICP_Autorizzazione" minOccurs="0"/&gt;
 *         &lt;element name="Denuncia" type="{http://entranext.it/}ICP_Denuncia" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoria_DettaglioICP", propOrder = {
    "oggetto",
    "ubicazione",
    "autorizzazione",
    "denuncia"
})
public class PosizioneDebitoriaDettaglioICP
    extends PosizioneDebitoriaDettaglioBase
{

    @XmlElement(name = "Oggetto")
    protected ICPOggetto oggetto;
    @XmlElement(name = "Ubicazione")
    protected Ubicazione ubicazione;
    @XmlElement(name = "Autorizzazione")
    protected ICPAutorizzazione autorizzazione;
    @XmlElement(name = "Denuncia")
    protected ICPDenuncia denuncia;

    /**
     * Recupera il valore della proprietà oggetto.
     * 
     * @return
     *     possible object is
     *     {@link ICPOggetto }
     *     
     */
    public ICPOggetto getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della proprietà oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPOggetto }
     *     
     */
    public void setOggetto(ICPOggetto value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della proprietà ubicazione.
     * 
     * @return
     *     possible object is
     *     {@link Ubicazione }
     *     
     */
    public Ubicazione getUbicazione() {
        return ubicazione;
    }

    /**
     * Imposta il valore della proprietà ubicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Ubicazione }
     *     
     */
    public void setUbicazione(Ubicazione value) {
        this.ubicazione = value;
    }

    /**
     * Recupera il valore della proprietà autorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link ICPAutorizzazione }
     *     
     */
    public ICPAutorizzazione getAutorizzazione() {
        return autorizzazione;
    }

    /**
     * Imposta il valore della proprietà autorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPAutorizzazione }
     *     
     */
    public void setAutorizzazione(ICPAutorizzazione value) {
        this.autorizzazione = value;
    }

    /**
     * Recupera il valore della proprietà denuncia.
     * 
     * @return
     *     possible object is
     *     {@link ICPDenuncia }
     *     
     */
    public ICPDenuncia getDenuncia() {
        return denuncia;
    }

    /**
     * Imposta il valore della proprietà denuncia.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPDenuncia }
     *     
     */
    public void setDenuncia(ICPDenuncia value) {
        this.denuncia = value;
    }

}
