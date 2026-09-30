
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VerificaPosizioneResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VerificaPosizioneResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioneDebitoria" type="{http://entranext.it/}PosizioneDebitoriaResult" minOccurs="0"/&gt;
 *         &lt;element name="Pagamenti" type="{http://entranext.it/}RendicontazionePagamenti" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificaPosizioneResponse", propOrder = {
    "posizioneDebitoria",
    "pagamenti"
})
public class VerificaPosizioneResponse
    extends LinkNextResponse
{

    @XmlElement(name = "PosizioneDebitoria")
    protected PosizioneDebitoriaResult posizioneDebitoria;
    @XmlElement(name = "Pagamenti", nillable = true)
    protected List<RendicontazionePagamenti> pagamenti;

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
     * Gets the value of the pagamenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagamenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagamenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RendicontazionePagamenti }
     * 
     * 
     */
    public List<RendicontazionePagamenti> getPagamenti() {
        if (pagamenti == null) {
            pagamenti = new ArrayList<RendicontazionePagamenti>();
        }
        return this.pagamenti;
    }

}
