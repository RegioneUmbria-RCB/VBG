
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IndirizzoConRecapitiObbligatori complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoConRecapitiObbligatori"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Indirizzo"&gt;
 *       &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}RecapitiObbligatori"/&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoConRecapitiObbligatori", propOrder = {
    "eMail",
    "telefono",
    "sitoWeb"
})
public class IndirizzoConRecapitiObbligatori
    extends Indirizzo
{

    @XmlElement(name = "e-mail", required = true)
    protected List<EMail> eMail;
    @XmlElement(required = true)
    protected Telefono telefono;
    @XmlElement(name = "sito-web")
    @XmlSchemaType(name = "anyURI")
    protected List<String> sitoWeb;

    /**
     * Gets the value of the eMail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eMail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEMail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EMail }
     * 
     * 
     */
    public List<EMail> getEMail() {
        if (eMail == null) {
            eMail = new ArrayList<EMail>();
        }
        return this.eMail;
    }

    /**
     * Recupera il valore della proprietà telefono.
     * 
     * @return
     *     possible object is
     *     {@link Telefono }
     *     
     */
    public Telefono getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della proprietà telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link Telefono }
     *     
     */
    public void setTelefono(Telefono value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the sitoWeb property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sitoWeb property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSitoWeb().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSitoWeb() {
        if (sitoWeb == null) {
            sitoWeb = new ArrayList<String>();
        }
        return this.sitoWeb;
    }

}
