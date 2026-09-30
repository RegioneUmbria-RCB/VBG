
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioCanalePagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioCanalePagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Filiale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Sportello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdTerminale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdOperazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioCanalePagamento", propOrder = {
    "filiale",
    "sportello",
    "idTerminale",
    "idOperazione"
})
public class DettaglioCanalePagamento {

    @XmlElement(name = "Filiale")
    protected String filiale;
    @XmlElement(name = "Sportello")
    protected String sportello;
    @XmlElement(name = "IdTerminale")
    protected String idTerminale;
    @XmlElement(name = "IdOperazione")
    protected String idOperazione;

    /**
     * Recupera il valore della proprietà filiale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiliale() {
        return filiale;
    }

    /**
     * Imposta il valore della proprietà filiale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiliale(String value) {
        this.filiale = value;
    }

    /**
     * Recupera il valore della proprietà sportello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSportello() {
        return sportello;
    }

    /**
     * Imposta il valore della proprietà sportello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSportello(String value) {
        this.sportello = value;
    }

    /**
     * Recupera il valore della proprietà idTerminale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTerminale() {
        return idTerminale;
    }

    /**
     * Imposta il valore della proprietà idTerminale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTerminale(String value) {
        this.idTerminale = value;
    }

    /**
     * Recupera il valore della proprietà idOperazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOperazione() {
        return idOperazione;
    }

    /**
     * Imposta il valore della proprietà idOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOperazione(String value) {
        this.idOperazione = value;
    }

}
