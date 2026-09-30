
package it.gruppoinit.sigepro.schemas.messages.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EsitoOperazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EsitoOperazioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="listaErrori" type="{http://gruppoinit.it/sigepro/schemas/messages/base}ErroreBackofficeType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoOperazioneType", propOrder = {
    "esito",
    "listaErrori"
})
public class EsitoOperazioneType {

    protected int esito;
    protected List<ErroreBackofficeType> listaErrori;

    /**
     * Recupera il valore della proprietà esito.
     * 
     */
    public int getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     */
    public void setEsito(int value) {
        this.esito = value;
    }

    /**
     * Gets the value of the listaErrori property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaErrori property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaErrori().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErroreBackofficeType }
     * 
     * 
     */
    public List<ErroreBackofficeType> getListaErrori() {
        if (listaErrori == null) {
            listaErrori = new ArrayList<ErroreBackofficeType>();
        }
        return this.listaErrori;
    }

}
