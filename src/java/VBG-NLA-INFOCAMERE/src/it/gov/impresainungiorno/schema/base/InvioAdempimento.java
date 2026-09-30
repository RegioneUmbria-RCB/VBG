
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Un invio � un tentativo da parte del portale di inviare un adempimento
 *         ad un ente. Fintanto che l'adempimento non � accettato o respinto dall'ente, ossia
 *         finch� problemi di connettivit� impediscono all'ente di prendere in carico
 *         l'adempimento, il portale permette all'utente di tentare l'invio dell'adempimento.
 *         Tutti gli invii falliti vengono memorizzati
 * 
 * <p>Classe Java per InvioAdempimento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InvioAdempimento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="esito" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/&gt;
 *         &lt;element name="messaggio" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base&gt;Stringa"&gt;
 *                 &lt;attribute name="titolo" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" /&gt;
 *                 &lt;attribute name="attributo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="tempo-invio" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvioAdempimento", propOrder = {
    "esito",
    "messaggio"
})
public class InvioAdempimento {

    @XmlElement(required = true)
    protected String esito;
    protected List<InvioAdempimento.Messaggio> messaggio;
    @XmlAttribute(name = "tempo-invio", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tempoInvio;

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }

    /**
     * Gets the value of the messaggio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messaggio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessaggio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvioAdempimento.Messaggio }
     * 
     * 
     */
    public List<InvioAdempimento.Messaggio> getMessaggio() {
        if (messaggio == null) {
            messaggio = new ArrayList<InvioAdempimento.Messaggio>();
        }
        return this.messaggio;
    }

    /**
     * Recupera il valore della proprietà tempoInvio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTempoInvio() {
        return tempoInvio;
    }

    /**
     * Imposta il valore della proprietà tempoInvio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTempoInvio(XMLGregorianCalendar value) {
        this.tempoInvio = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base&gt;Stringa"&gt;
     *       &lt;attribute name="titolo" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" /&gt;
     *       &lt;attribute name="attributo" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
    public static class Messaggio {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "titolo", required = true)
        protected String titolo;
        @XmlAttribute(name = "attributo")
        protected String attributo;

        /**
         *  Questo elemento non puo' assumere valore stringa vuota, al piu' non c'e' 
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
         * Recupera il valore della proprietà titolo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitolo() {
            return titolo;
        }

        /**
         * Imposta il valore della proprietà titolo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitolo(String value) {
            this.titolo = value;
        }

        /**
         * Recupera il valore della proprietà attributo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttributo() {
            return attributo;
        }

        /**
         * Imposta il valore della proprietà attributo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttributo(String value) {
            this.attributo = value;
        }

    }

}
