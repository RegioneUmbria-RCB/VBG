
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciPosizioneRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciPosizioneRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioniDebitoria" type="{http://entranext.it/}PosizioneDebitoria" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciPosizioneRequest", propOrder = {
    "posizioniDebitoria"
})
public class InserisciPosizioneRequest
    extends LinkNextRequest
{

    @XmlElement(name = "PosizioniDebitoria")
    protected PosizioneDebitoria posizioniDebitoria;

    /**
     * Recupera il valore della proprietà posizioniDebitoria.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneDebitoria }
     *     
     */
    public PosizioneDebitoria getPosizioniDebitoria() {
        return posizioniDebitoria;
    }

    /**
     * Imposta il valore della proprietà posizioniDebitoria.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneDebitoria }
     *     
     */
    public void setPosizioniDebitoria(PosizioneDebitoria value) {
        this.posizioniDebitoria = value;
    }

}
