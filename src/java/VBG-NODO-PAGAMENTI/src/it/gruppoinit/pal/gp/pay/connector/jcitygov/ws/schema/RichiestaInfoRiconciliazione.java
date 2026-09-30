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
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="Operazione" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stOperazioneRiconciliazione"/>
 *         &lt;element name="CodiceIpaBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ListaIdentificativoFlusso" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IdentificativoFlusso" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ListaSospesi" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IdentificativoSospeso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctIdentificativoSospeso" maxOccurs="unbounded" minOccurs="0"/>
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
    "operazione",
    "codiceIpaBeneficiario",
    "listaIdentificativoFlusso",
    "listaSospesi"
})
@XmlRootElement(name = "RichiestaInfoRiconciliazione")
public class RichiestaInfoRiconciliazione {

    @XmlElement(name = "Operazione", required = true)
    @XmlSchemaType(name = "string")
    protected StOperazioneRiconciliazione operazione;
    @XmlElement(name = "CodiceIpaBeneficiario", required = true)
    protected String codiceIpaBeneficiario;
    @XmlElement(name = "ListaIdentificativoFlusso")
    protected RichiestaInfoRiconciliazione.ListaIdentificativoFlusso listaIdentificativoFlusso;
    @XmlElement(name = "ListaSospesi")
    protected RichiestaInfoRiconciliazione.ListaSospesi listaSospesi;

    /**
     * Recupera il valore della proprietà operazione.
     * 
     * @return
     *     possible object is
     *     {@link StOperazioneRiconciliazione }
     *     
     */
    public StOperazioneRiconciliazione getOperazione() {
        return operazione;
    }

    /**
     * Imposta il valore della proprietà operazione.
     * 
     * @param value
     *     allowed object is
     *     {@link StOperazioneRiconciliazione }
     *     
     */
    public void setOperazione(StOperazioneRiconciliazione value) {
        this.operazione = value;
    }

    /**
     * Recupera il valore della proprietà codiceIpaBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIpaBeneficiario() {
        return codiceIpaBeneficiario;
    }

    /**
     * Imposta il valore della proprietà codiceIpaBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIpaBeneficiario(String value) {
        this.codiceIpaBeneficiario = value;
    }

    /**
     * Recupera il valore della proprietà listaIdentificativoFlusso.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaInfoRiconciliazione.ListaIdentificativoFlusso }
     *     
     */
    public RichiestaInfoRiconciliazione.ListaIdentificativoFlusso getListaIdentificativoFlusso() {
        return listaIdentificativoFlusso;
    }

    /**
     * Imposta il valore della proprietà listaIdentificativoFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaInfoRiconciliazione.ListaIdentificativoFlusso }
     *     
     */
    public void setListaIdentificativoFlusso(RichiestaInfoRiconciliazione.ListaIdentificativoFlusso value) {
        this.listaIdentificativoFlusso = value;
    }

    /**
     * Recupera il valore della proprietà listaSospesi.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaInfoRiconciliazione.ListaSospesi }
     *     
     */
    public RichiestaInfoRiconciliazione.ListaSospesi getListaSospesi() {
        return listaSospesi;
    }

    /**
     * Imposta il valore della proprietà listaSospesi.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaInfoRiconciliazione.ListaSospesi }
     *     
     */
    public void setListaSospesi(RichiestaInfoRiconciliazione.ListaSospesi value) {
        this.listaSospesi = value;
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
     *         &lt;element name="IdentificativoFlusso" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "identificativoFlusso"
    })
    public static class ListaIdentificativoFlusso {

        @XmlElement(name = "IdentificativoFlusso")
        protected List<String> identificativoFlusso;

        /**
         * Gets the value of the identificativoFlusso property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the identificativoFlusso property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIdentificativoFlusso().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getIdentificativoFlusso() {
            if (identificativoFlusso == null) {
                identificativoFlusso = new ArrayList<String>();
            }
            return this.identificativoFlusso;
        }

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
     *         &lt;element name="IdentificativoSospeso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctIdentificativoSospeso" maxOccurs="unbounded" minOccurs="0"/>
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
        "identificativoSospeso"
    })
    public static class ListaSospesi {

        @XmlElement(name = "IdentificativoSospeso")
        protected List<CtIdentificativoSospeso> identificativoSospeso;

        /**
         * Gets the value of the identificativoSospeso property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the identificativoSospeso property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIdentificativoSospeso().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CtIdentificativoSospeso }
         * 
         * 
         */
        public List<CtIdentificativoSospeso> getIdentificativoSospeso() {
            if (identificativoSospeso == null) {
                identificativoSospeso = new ArrayList<CtIdentificativoSospeso>();
            }
            return this.identificativoSospeso;
        }

    }

}
