package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per InserisciPosizioniInAttesaRequest complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciPosizioniInAttesaRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UrlBack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="UrlReturn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="PosizioniDebitorie" type="{http://entranext.it/}PosizioneDebitoriaInAttesa" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "InserisciPosizioniInAttesaRequest", propOrder = { "urlBack", "urlReturn", "posizioniDebitorie", "urlNotificaRT" })
public class InserisciPosizioniInAttesaRequest extends LinkNextRequest {

    @XmlElement(name = "UrlBack", namespace = "")
    protected String urlBack;
    @XmlElement(name = "UrlReturn", namespace = "")
    protected String urlReturn;
    @XmlElement(name = "PosizioniDebitorie")
    protected List<PosizioneDebitoriaInAttesa> posizioniDebitorie;
    @XmlElement(name = "UrlNotificaRT", namespace = "")
    protected String urlNotificaRT;

    /**
     * Recupera il valore della proprietà urlBack.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUrlBack() {

	return urlBack;
    }

    /**
     * Imposta il valore della proprietà urlBack.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUrlBack(String value) {

	this.urlBack = value;
    }

    /**
     * Recupera il valore della proprietà urlReturn.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUrlReturn() {

	return urlReturn;
    }

    /**
     * Imposta il valore della proprietà urlReturn.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUrlReturn(String value) {

	this.urlReturn = value;
    }

    /**
     * Gets the value of the posizioniDebitorie property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the posizioniDebitorie property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getPosizioniDebitorie().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link PosizioneDebitoriaInAttesa }
     * 
     * 
     */
    public List<PosizioneDebitoriaInAttesa> getPosizioniDebitorie() {

	if (posizioniDebitorie == null) {
	    posizioniDebitorie = new ArrayList<PosizioneDebitoriaInAttesa>();
	}
	return this.posizioniDebitorie;
    }

    public void setPosizioniDebitorie(List<PosizioneDebitoriaInAttesa> posizioniDebitorie) {

	this.posizioniDebitorie = posizioniDebitorie;
    }

    /**
     * Recupera il valore della proprietà urlNotificaRT.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUrlNotificaRT() {

	return urlNotificaRT;
    }

    /**
     * Imposta il valore della proprietà urlNotificaRT.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUrlNotificaRT(String value) {

	this.urlNotificaRT = value;
    }
}
