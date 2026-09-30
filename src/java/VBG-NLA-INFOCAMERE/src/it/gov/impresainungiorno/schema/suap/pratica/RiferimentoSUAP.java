
package it.gov.impresainungiorno.schema.suap.pratica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Questo elemento permette di indicare altre pratiche a cui quella corrente fa riferimento.
 * Permette di ricostruire la gerarchia dei riferimenti in caso di pratiche legate a pratiche precedenti (ad esempio per collegare le pratiche di integrazione a quella principale).
 * 
 * <p>Classe Java per RiferimentoSUAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoSUAP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sportello" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiSuap"/&gt;
 *         &lt;element name="codice-pratica" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/&gt;
 *         &lt;element name="riferimento" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}RiferimentoSUAP" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoSUAP", propOrder = {
    "sportello",
    "codicePratica",
    "riferimento"
})
public class RiferimentoSUAP {

    @XmlElement(required = true)
    protected EstremiSuap sportello;
    @XmlElement(name = "codice-pratica", required = true)
    protected String codicePratica;
    protected List<RiferimentoSUAP> riferimento;

    /**
     * Recupera il valore della proprietà sportello.
     * 
     * @return
     *     possible object is
     *     {@link EstremiSuap }
     *     
     */
    public EstremiSuap getSportello() {
        return sportello;
    }

    /**
     * Imposta il valore della proprietà sportello.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiSuap }
     *     
     */
    public void setSportello(EstremiSuap value) {
        this.sportello = value;
    }

    /**
     * Recupera il valore della proprietà codicePratica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicePratica() {
        return codicePratica;
    }

    /**
     * Imposta il valore della proprietà codicePratica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicePratica(String value) {
        this.codicePratica = value;
    }

    /**
     * Gets the value of the riferimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riferimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiferimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RiferimentoSUAP }
     * 
     * 
     */
    public List<RiferimentoSUAP> getRiferimento() {
        if (riferimento == null) {
            riferimento = new ArrayList<RiferimentoSUAP>();
        }
        return this.riferimento;
    }

}
