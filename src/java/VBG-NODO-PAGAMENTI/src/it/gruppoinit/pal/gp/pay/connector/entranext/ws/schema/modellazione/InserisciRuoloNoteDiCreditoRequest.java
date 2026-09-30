
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciRuoloNoteDiCreditoRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciRuoloNoteDiCreditoRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NoteDiCredito" type="{http://entranext.it/}PosizioniNoteDiCredito" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RuoloNoteDiCredito" type="{http://entranext.it/}RuoloPosizioniNoteDiCredito" minOccurs="0"/&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RiferimentoRuoloEsterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciRuoloNoteDiCreditoRequest", propOrder = {
    "noteDiCredito",
    "ruoloNoteDiCredito",
    "idruolo",
    "riferimentoRuoloEsterno"
})
public class InserisciRuoloNoteDiCreditoRequest
    extends LinkNextRequest
{

    @XmlElement(name = "NoteDiCredito")
    protected List<PosizioniNoteDiCredito> noteDiCredito;
    @XmlElement(name = "RuoloNoteDiCredito")
    protected RuoloPosizioniNoteDiCredito ruoloNoteDiCredito;
    @XmlElement(name = "ID_RUOLO", required = true, type = Integer.class, nillable = true)
    protected Integer idruolo;
    @XmlElement(name = "RiferimentoRuoloEsterno")
    protected String riferimentoRuoloEsterno;

    /**
     * Gets the value of the noteDiCredito property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noteDiCredito property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNoteDiCredito().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioniNoteDiCredito }
     * 
     * 
     */
    public List<PosizioniNoteDiCredito> getNoteDiCredito() {
        if (noteDiCredito == null) {
            noteDiCredito = new ArrayList<PosizioniNoteDiCredito>();
        }
        return this.noteDiCredito;
    }

    /**
     * Recupera il valore della proprietà ruoloNoteDiCredito.
     * 
     * @return
     *     possible object is
     *     {@link RuoloPosizioniNoteDiCredito }
     *     
     */
    public RuoloPosizioniNoteDiCredito getRuoloNoteDiCredito() {
        return ruoloNoteDiCredito;
    }

    /**
     * Imposta il valore della proprietà ruoloNoteDiCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloPosizioniNoteDiCredito }
     *     
     */
    public void setRuoloNoteDiCredito(RuoloPosizioniNoteDiCredito value) {
        this.ruoloNoteDiCredito = value;
    }

    /**
     * Recupera il valore della proprietà idruolo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIDRUOLO(Integer value) {
        this.idruolo = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoRuoloEsterno() {
        return riferimentoRuoloEsterno;
    }

    /**
     * Imposta il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoRuoloEsterno(String value) {
        this.riferimentoRuoloEsterno = value;
    }

}
