//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.17 alle 10:12:21 AM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Richiesta utilizzata per notificare gli esiti dei debiti caricati ed i numeri di avviso / iuv calcolati da terzi 
 * 
 * <p>Classe Java per RichiestaNotificaCaricamentoDebito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaNotificaCaricamentoDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esitiDebitiCaricati">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="esitoDebitoCaricato" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/notify/1_2}ctEsitoDebitoCaricato" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaNotificaCaricamentoDebito", propOrder = {
    "esitiDebitiCaricati"
})
public class RichiestaNotificaCaricamentoDebito {

    @XmlElement(required = true)
    protected RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati esitiDebitiCaricati;

    /**
     * Recupera il valore della proprietà esitiDebitiCaricati.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati }
     *     
     */
    public RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati getEsitiDebitiCaricati() {
        return esitiDebitiCaricati;
    }

    /**
     * Imposta il valore della proprietà esitiDebitiCaricati.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati }
     *     
     */
    public void setEsitiDebitiCaricati(RichiestaNotificaCaricamentoDebito.EsitiDebitiCaricati value) {
        this.esitiDebitiCaricati = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="esitoDebitoCaricato" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/notify/1_2}ctEsitoDebitoCaricato" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "esitoDebitoCaricato"
    })
    public static class EsitiDebitiCaricati {

        protected List<CtEsitoDebitoCaricato> esitoDebitoCaricato;

        /**
         * Gets the value of the esitoDebitoCaricato property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the esitoDebitoCaricato property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEsitoDebitoCaricato().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CtEsitoDebitoCaricato }
         * 
         * 
         */
        public List<CtEsitoDebitoCaricato> getEsitoDebitoCaricato() {
            if (esitoDebitoCaricato == null) {
                esitoDebitoCaricato = new ArrayList<CtEsitoDebitoCaricato>();
            }
            return this.esitoDebitoCaricato;
        }

    }

}
