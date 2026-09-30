
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ValidaPosizioneDebitoriaICPRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ValidaPosizioneDebitoriaICPRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioneDebitoria" type="{http://entranext.it/}PosizioneDebitoriaICP" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidaPosizioneDebitoriaICPRequest", propOrder = {
    "posizioneDebitoria"
})
public class ValidaPosizioneDebitoriaICPRequest
    extends LinkNextRequest
{

    @XmlElement(name = "PosizioneDebitoria")
    protected PosizioneDebitoriaICP posizioneDebitoria;

    /**
     * Recupera il valore della proprietà posizioneDebitoria.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneDebitoriaICP }
     *     
     */
    public PosizioneDebitoriaICP getPosizioneDebitoria() {
        return posizioneDebitoria;
    }

    /**
     * Imposta il valore della proprietà posizioneDebitoria.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneDebitoriaICP }
     *     
     */
    public void setPosizioneDebitoria(PosizioneDebitoriaICP value) {
        this.posizioneDebitoria = value;
    }

}
