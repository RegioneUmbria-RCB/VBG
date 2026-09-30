
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciPosizioniInAttesaCARequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciPosizioniInAttesaCARequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UrlBack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="UrlReturn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="SoggettoVersante" type="{http://entranext.it/}SoggettoVersante" minOccurs="0"/&gt;
 *         &lt;element name="ChiaviApplicativePosizioni" type="{http://entranext.it/}ChiaveApplicativaPosizione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="UrlNotificaRT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciPosizioniInAttesaCARequest", propOrder = {
    "urlBack",
    "urlReturn",
    "soggettoVersante",
    "chiaviApplicativePosizioni",
    "urlNotificaRT"
})
public class InserisciPosizioniInAttesaCARequest
    extends LinkNextRequest
{

    @XmlElement(name = "UrlBack", namespace = "")
    protected String urlBack;
    @XmlElement(name = "UrlReturn", namespace = "")
    protected String urlReturn;
    @XmlElement(name = "SoggettoVersante")
    protected SoggettoVersante soggettoVersante;
    @XmlElement(name = "ChiaviApplicativePosizioni")
    protected List<ChiaveApplicativaPosizione> chiaviApplicativePosizioni;
    @XmlElement(name = "UrlNotificaRT", namespace = "")
    protected String urlNotificaRT;

    /**
     * Recupera il valore della proprietà urlBack.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlBack() {
        return urlBack;
    }

    /**
     * Imposta il valore della proprietà urlBack.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlBack(String value) {
        this.urlBack = value;
    }

    /**
     * Recupera il valore della proprietà urlReturn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlReturn() {
        return urlReturn;
    }

    /**
     * Imposta il valore della proprietà urlReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlReturn(String value) {
        this.urlReturn = value;
    }

    /**
     * Recupera il valore della proprietà soggettoVersante.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoVersante }
     *     
     */
    public SoggettoVersante getSoggettoVersante() {
        return soggettoVersante;
    }

    /**
     * Imposta il valore della proprietà soggettoVersante.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoVersante }
     *     
     */
    public void setSoggettoVersante(SoggettoVersante value) {
        this.soggettoVersante = value;
    }

    /**
     * Gets the value of the chiaviApplicativePosizioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chiaviApplicativePosizioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChiaviApplicativePosizioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChiaveApplicativaPosizione }
     * 
     * 
     */
    public List<ChiaveApplicativaPosizione> getChiaviApplicativePosizioni() {
        if (chiaviApplicativePosizioni == null) {
            chiaviApplicativePosizioni = new ArrayList<ChiaveApplicativaPosizione>();
        }
        return this.chiaviApplicativePosizioni;
    }

    /**
     * Recupera il valore della proprietà urlNotificaRT.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlNotificaRT() {
        return urlNotificaRT;
    }

    /**
     * Imposta il valore della proprietà urlNotificaRT.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlNotificaRT(String value) {
        this.urlNotificaRT = value;
    }

}
