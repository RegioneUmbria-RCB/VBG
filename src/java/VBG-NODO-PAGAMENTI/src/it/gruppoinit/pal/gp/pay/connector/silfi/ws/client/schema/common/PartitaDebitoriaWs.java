
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Singolo dettaglio del
 * 				pagamento. Per esempio, la singola multa, la rata della mensa, etc.
 * 			
 * 
 * <p>Classe Java per partitaDebitoriaWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="partitaDebitoriaWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ArticoliDebitoriWs" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}articoloDebitorioWs" maxOccurs="unbounded"/&gt;
 *         &lt;element name="idBoPartitaDebitoria"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="100"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="descrizione"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="256"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="importo"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "partitaDebitoriaWs", propOrder = {
    "articoliDebitoriWs",
    "idBoPartitaDebitoria",
    "descrizione",
    "importo"
})
public class PartitaDebitoriaWs {

    @XmlElement(name = "ArticoliDebitoriWs", required = true)
    protected List<ArticoloDebitorioWs> articoliDebitoriWs;
    @XmlElement(required = true)
    protected String idBoPartitaDebitoria;
    @XmlElement(required = true)
    protected String descrizione;
    @XmlElement(required = true)
    protected String importo;

    /**
     * Gets the value of the articoliDebitoriWs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the articoliDebitoriWs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArticoliDebitoriWs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArticoloDebitorioWs }
     * 
     * 
     */
    public List<ArticoloDebitorioWs> getArticoliDebitoriWs() {
        if (articoliDebitoriWs == null) {
            articoliDebitoriWs = new ArrayList<ArticoloDebitorioWs>();
        }
        return this.articoliDebitoriWs;
    }

    /**
     * Recupera il valore della proprietà idBoPartitaDebitoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBoPartitaDebitoria() {
        return idBoPartitaDebitoria;
    }

    /**
     * Imposta il valore della proprietà idBoPartitaDebitoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBoPartitaDebitoria(String value) {
        this.idBoPartitaDebitoria = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporto(String value) {
        this.importo = value;
    }

}
