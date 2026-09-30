
package it.gruppoinit.impresainungiorno.schema.base.v_1_0_0;

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
 * <p>Java class for DettaglioImpresa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioImpresa">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}AnagraficaImpresa">
 *       &lt;sequence>
 *         &lt;element name="codice-REA" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceREA" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti" minOccurs="0"/>
 *         &lt;element name="legale-rappresentante" type="{http://www.impresainungiorno.gov.it/schema/base}LegaleRappresentante" minOccurs="0"/>
 *         &lt;element name="rappresentante" type="{http://www.impresainungiorno.gov.it/schema/base}AnagraficaRappresentante" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attivita" type="{http://www.impresainungiorno.gov.it/schema/base}AttivitaISTAT" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="data-inizio-attivita" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
     * Gets the value of the codiceREA property.
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
     * Sets the value of the codiceREA property.
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
     * Gets the value of the indirizzo property.
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
     * Sets the value of the indirizzo property.
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
     * Gets the value of the legaleRappresentante property.
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
     * Sets the value of the legaleRappresentante property.
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
     * Gets the value of the dataInizioAttivita property.
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
     * Sets the value of the dataInizioAttivita property.
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
