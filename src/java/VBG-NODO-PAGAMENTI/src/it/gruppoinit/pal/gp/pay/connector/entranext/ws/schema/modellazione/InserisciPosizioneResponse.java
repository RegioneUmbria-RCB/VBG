
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciPosizioneResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciPosizioneResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioneDebitoria" type="{http://entranext.it/}PosizioneDebitoriaResult" minOccurs="0"/&gt;
 *         &lt;element name="Documento" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciPosizioneResponse", propOrder = {
    "posizioneDebitoria",
    "documento"
})
public class InserisciPosizioneResponse
    extends LinkNextResponse
{

    @XmlElement(name = "PosizioneDebitoria")
    protected PosizioneDebitoriaResult posizioneDebitoria;
    @XmlElement(name = "Documento")
    protected byte[] documento;

    /**
     * Recupera il valore della proprietà posizioneDebitoria.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneDebitoriaResult }
     *     
     */
    public PosizioneDebitoriaResult getPosizioneDebitoria() {
        return posizioneDebitoria;
    }

    /**
     * Imposta il valore della proprietà posizioneDebitoria.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneDebitoriaResult }
     *     
     */
    public void setPosizioneDebitoria(PosizioneDebitoriaResult value) {
        this.posizioneDebitoria = value;
    }

    /**
     * Recupera il valore della proprietà documento.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Imposta il valore della proprietà documento.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumento(byte[] value) {
        this.documento = value;
    }

}
