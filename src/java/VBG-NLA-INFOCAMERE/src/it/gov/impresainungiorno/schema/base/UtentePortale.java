
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 *  Questo tipo contiene tutti i dati relativi al profilo dell'utente,
 *         ossia i dati immessi dall'utente al momento della registrazione: obbligatori sono codice fiscale, 
 *         nome e cognome, cos� da poter creare l'utente di scrivania (non Archeometra)
 *         nel caso l'utente sia sconosciuto al sistema 
 * 
 * <p>Classe Java per UtentePortale complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="UtentePortale"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="delegato" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base&gt;CodiceFiscalePersona"&gt;
 *                 &lt;attribute name="ente" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="domicilio" type="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtentePortale", propOrder = {
    "delegato",
    "domicilio"
})
public class UtentePortale
    extends Anagrafica
{

    protected List<UtentePortale.Delegato> delegato;
    protected IndirizzoConRecapiti domicilio;

    /**
     * Gets the value of the delegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UtentePortale.Delegato }
     * 
     * 
     */
    public List<UtentePortale.Delegato> getDelegato() {
        if (delegato == null) {
            delegato = new ArrayList<UtentePortale.Delegato>();
        }
        return this.delegato;
    }

    /**
     * Recupera il valore della proprietà domicilio.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public IndirizzoConRecapiti getDomicilio() {
        return domicilio;
    }

    /**
     * Imposta il valore della proprietà domicilio.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public void setDomicilio(IndirizzoConRecapiti value) {
        this.domicilio = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base&gt;CodiceFiscalePersona"&gt;
     *       &lt;attribute name="ente" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Delegato {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ente", required = true)
        protected String ente;

        /**
         * Codice fiscale, di 16 caratteri, appartenente a persona fisica
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Imposta il valore della proprietà value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Recupera il valore della proprietà ente.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEnte() {
            return ente;
        }

        /**
         * Imposta il valore della proprietà ente.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEnte(String value) {
            this.ente = value;
        }

    }

}
