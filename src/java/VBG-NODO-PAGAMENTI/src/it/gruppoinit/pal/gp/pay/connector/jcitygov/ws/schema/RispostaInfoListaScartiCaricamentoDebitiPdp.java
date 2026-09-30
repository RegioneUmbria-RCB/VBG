//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="ListaScartiCaricamentoDebitiPdp" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NumeroAvviso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctNumeroAvviso"/>
 *                   &lt;element name="ChiaveDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito"/>
 *                   &lt;element name="StatoSincronizzazionePdp" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = {
    "listaScartiCaricamentoDebitiPdp"
})
@XmlRootElement(name = "RispostaInfoListaScartiCaricamentoDebitiPdp")
public class RispostaInfoListaScartiCaricamentoDebitiPdp {

    @XmlElement(name = "ListaScartiCaricamentoDebitiPdp")
    protected List<RispostaInfoListaScartiCaricamentoDebitiPdp.ListaScartiCaricamentoDebitiPdp> listaScartiCaricamentoDebitiPdp;

    /**
     * Gets the value of the listaScartiCaricamentoDebitiPdp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaScartiCaricamentoDebitiPdp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaScartiCaricamentoDebitiPdp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RispostaInfoListaScartiCaricamentoDebitiPdp.ListaScartiCaricamentoDebitiPdp }
     * 
     * 
     */
    public List<RispostaInfoListaScartiCaricamentoDebitiPdp.ListaScartiCaricamentoDebitiPdp> getListaScartiCaricamentoDebitiPdp() {
        if (listaScartiCaricamentoDebitiPdp == null) {
            listaScartiCaricamentoDebitiPdp = new ArrayList<RispostaInfoListaScartiCaricamentoDebitiPdp.ListaScartiCaricamentoDebitiPdp>();
        }
        return this.listaScartiCaricamentoDebitiPdp;
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
     *         &lt;element name="NumeroAvviso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctNumeroAvviso"/>
     *         &lt;element name="ChiaveDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito"/>
     *         &lt;element name="StatoSincronizzazionePdp" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "numeroAvviso",
        "chiaveDebito",
        "statoSincronizzazionePdp"
    })
    public static class ListaScartiCaricamentoDebitiPdp {

        @XmlElement(name = "NumeroAvviso", required = true)
        protected CtNumeroAvviso numeroAvviso;
        @XmlElement(name = "ChiaveDebito", required = true)
        protected CtChiaveDebito chiaveDebito;
        @XmlElement(name = "StatoSincronizzazionePdp", required = true)
        protected String statoSincronizzazionePdp;

        /**
         * Recupera il valore della proprietà numeroAvviso.
         * 
         * @return
         *     possible object is
         *     {@link CtNumeroAvviso }
         *     
         */
        public CtNumeroAvviso getNumeroAvviso() {
            return numeroAvviso;
        }

        /**
         * Imposta il valore della proprietà numeroAvviso.
         * 
         * @param value
         *     allowed object is
         *     {@link CtNumeroAvviso }
         *     
         */
        public void setNumeroAvviso(CtNumeroAvviso value) {
            this.numeroAvviso = value;
        }

        /**
         * Recupera il valore della proprietà chiaveDebito.
         * 
         * @return
         *     possible object is
         *     {@link CtChiaveDebito }
         *     
         */
        public CtChiaveDebito getChiaveDebito() {
            return chiaveDebito;
        }

        /**
         * Imposta il valore della proprietà chiaveDebito.
         * 
         * @param value
         *     allowed object is
         *     {@link CtChiaveDebito }
         *     
         */
        public void setChiaveDebito(CtChiaveDebito value) {
            this.chiaveDebito = value;
        }

        /**
         * Recupera il valore della proprietà statoSincronizzazionePdp.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatoSincronizzazionePdp() {
            return statoSincronizzazionePdp;
        }

        /**
         * Imposta il valore della proprietà statoSincronizzazionePdp.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatoSincronizzazionePdp(String value) {
            this.statoSincronizzazionePdp = value;
        }

    }

}
