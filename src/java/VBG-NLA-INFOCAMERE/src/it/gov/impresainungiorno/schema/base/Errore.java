
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Errore complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Errore"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="causa" type="{http://www.impresainungiorno.gov.it/schema/base}Errore" minOccurs="0"/&gt;
 *         &lt;element name="parametro" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="codice" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" /&gt;
 *       &lt;attribute name="gravita" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" default="GRAVE" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Errore", propOrder = {
    "causa",
    "parametro"
})
public class Errore {

    protected Errore causa;
    protected List<String> parametro;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;
    @XmlAttribute(name = "gravita")
    protected String gravita;

    /**
     * Recupera il valore della proprietà causa.
     * 
     * @return
     *     possible object is
     *     {@link Errore }
     *     
     */
    public Errore getCausa() {
        return causa;
    }

    /**
     * Imposta il valore della proprietà causa.
     * 
     * @param value
     *     allowed object is
     *     {@link Errore }
     *     
     */
    public void setCausa(Errore value) {
        this.causa = value;
    }

    /**
     * Gets the value of the parametro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getParametro() {
        if (parametro == null) {
            parametro = new ArrayList<String>();
        }
        return this.parametro;
    }

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprietà gravita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGravita() {
        if (gravita == null) {
            return "GRAVE";
        } else {
            return gravita;
        }
    }

    /**
     * Imposta il valore della proprietà gravita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGravita(String value) {
        this.gravita = value;
    }

}
