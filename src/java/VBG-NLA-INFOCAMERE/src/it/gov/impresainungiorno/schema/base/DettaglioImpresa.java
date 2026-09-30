
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DettaglioImpresa complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioImpresa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}AnagraficaImpresa"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codice-REA" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceREA" minOccurs="0"/&gt;
 *         &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti" minOccurs="0"/&gt;
 *         &lt;element name="legale-rappresentante" type="{http://www.impresainungiorno.gov.it/schema/base}LegaleRappresentante" minOccurs="0"/&gt;
 *         &lt;element name="rappresentante" type="{http://www.impresainungiorno.gov.it/schema/base}AnagraficaRappresentante" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="attivita" type="{http://www.impresainungiorno.gov.it/schema/base}AttivitaISTAT" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="data-inizio-attivita" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioImpresa", propOrder = {
    "codiceREA",
    "indirizzo",
    "legaleRappresentante",
    "rappresentante",
    "attivita"
})
public class DettaglioImpresa
    extends AnagraficaImpresa
{

    @XmlElement(name = "codice-REA")
    protected CodiceREA codiceREA;
    protected IndirizzoConRecapiti indirizzo;
    @XmlElement(name = "legale-rappresentante")
    protected LegaleRappresentante legaleRappresentante;
    protected List<AnagraficaRappresentante> rappresentante;
    protected List<AttivitaISTAT> attivita;
    @XmlAttribute(name = "data-inizio-attivita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioAttivita;

    /**
     * Recupera il valore della proprietà codiceREA.
     * 
     * @return
     *     possible object is
     *     {@link CodiceREA }
     *     
     */
    public CodiceREA getCodiceREA() {
        return codiceREA;
    }

    /**
     * Imposta il valore della proprietà codiceREA.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceREA }
     *     
     */
    public void setCodiceREA(CodiceREA value) {
        this.codiceREA = value;
    }

    /**
     * Recupera il valore della proprietà indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public IndirizzoConRecapiti getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietà indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public void setIndirizzo(IndirizzoConRecapiti value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietà legaleRappresentante.
     * 
     * @return
     *     possible object is
     *     {@link LegaleRappresentante }
     *     
     */
    public LegaleRappresentante getLegaleRappresentante() {
        return legaleRappresentante;
    }

    /**
     * Imposta il valore della proprietà legaleRappresentante.
     * 
     * @param value
     *     allowed object is
     *     {@link LegaleRappresentante }
     *     
     */
    public void setLegaleRappresentante(LegaleRappresentante value) {
        this.legaleRappresentante = value;
    }

    /**
     * Gets the value of the rappresentante property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rappresentante property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRappresentante().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnagraficaRappresentante }
     * 
     * 
     */
    public List<AnagraficaRappresentante> getRappresentante() {
        if (rappresentante == null) {
            rappresentante = new ArrayList<AnagraficaRappresentante>();
        }
        return this.rappresentante;
    }

    /**
     * Gets the value of the attivita property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attivita property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttivita().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttivitaISTAT }
     * 
     * 
     */
    public List<AttivitaISTAT> getAttivita() {
        if (attivita == null) {
            attivita = new ArrayList<AttivitaISTAT>();
        }
        return this.attivita;
    }

    /**
     * Recupera il valore della proprietà dataInizioAttivita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioAttivita() {
        return dataInizioAttivita;
    }

    /**
     * Imposta il valore della proprietà dataInizioAttivita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioAttivita(XMLGregorianCalendar value) {
        this.dataInizioAttivita = value;
    }

}
