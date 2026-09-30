
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EsitoPosizioneDebitoriaScartata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EsitoPosizioneDebitoriaScartata"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Errori" type="{http://entranext.it/}ArrayOfAnomaliaPosizioneDebitoria" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoPosizioneDebitoriaScartata", propOrder = {
    "riferimentoPraticaEsterna",
    "errori"
})
public class EsitoPosizioneDebitoriaScartata {

    @XmlElement(name = "RiferimentoPraticaEsterna")
    protected String riferimentoPraticaEsterna;
    @XmlElement(name = "Errori")
    protected ArrayOfAnomaliaPosizioneDebitoria errori;

    /**
     * Recupera il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPraticaEsterna() {
        return riferimentoPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPraticaEsterna(String value) {
        this.riferimentoPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà errori.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAnomaliaPosizioneDebitoria }
     *     
     */
    public ArrayOfAnomaliaPosizioneDebitoria getErrori() {
        return errori;
    }

    /**
     * Imposta il valore della proprietà errori.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAnomaliaPosizioneDebitoria }
     *     
     */
    public void setErrori(ArrayOfAnomaliaPosizioneDebitoria value) {
        this.errori = value;
    }

}
