
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per InformazioniIUVRuolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InformazioniIUVRuolo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoDocumento" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IUV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NumeroRata" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IUVSoluzioneUnica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ScadenzaSoluzioneUnica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformazioniIUVRuolo", propOrder = {
    "riferimentoDocumento",
    "iuv",
    "riferimentoPraticaEsterna",
    "numeroRata",
    "iuvSoluzioneUnica",
    "scadenzaSoluzioneUnica"
})
public class InformazioniIUVRuolo {

    @XmlElement(name = "RiferimentoDocumento")
    protected int riferimentoDocumento;
    @XmlElement(name = "IUV")
    protected String iuv;
    @XmlElement(name = "RiferimentoPraticaEsterna")
    protected String riferimentoPraticaEsterna;
    @XmlElement(name = "NumeroRata")
    protected int numeroRata;
    @XmlElement(name = "IUVSoluzioneUnica", required = true, nillable = true)
    protected String iuvSoluzioneUnica;
    @XmlElement(name = "ScadenzaSoluzioneUnica", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar scadenzaSoluzioneUnica;

    /**
     * Recupera il valore della proprietà riferimentoDocumento.
     * 
     */
    public int getRiferimentoDocumento() {
        return riferimentoDocumento;
    }

    /**
     * Imposta il valore della proprietà riferimentoDocumento.
     * 
     */
    public void setRiferimentoDocumento(int value) {
        this.riferimentoDocumento = value;
    }

    /**
     * Recupera il valore della proprietà iuv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUV() {
        return iuv;
    }

    /**
     * Imposta il valore della proprietà iuv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUV(String value) {
        this.iuv = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPraticaEsterna() {
        return riferimentoPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPraticaEsterna(String value) {
        this.riferimentoPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà numeroRata.
     * 
     */
    public int getNumeroRata() {
        return numeroRata;
    }

    /**
     * Imposta il valore della proprietà numeroRata.
     * 
     */
    public void setNumeroRata(int value) {
        this.numeroRata = value;
    }

    /**
     * Recupera il valore della proprietà iuvSoluzioneUnica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUVSoluzioneUnica() {
        return iuvSoluzioneUnica;
    }

    /**
     * Imposta il valore della proprietà iuvSoluzioneUnica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUVSoluzioneUnica(String value) {
        this.iuvSoluzioneUnica = value;
    }

    /**
     * Recupera il valore della proprietà scadenzaSoluzioneUnica.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScadenzaSoluzioneUnica() {
        return scadenzaSoluzioneUnica;
    }

    /**
     * Imposta il valore della proprietà scadenzaSoluzioneUnica.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScadenzaSoluzioneUnica(XMLGregorianCalendar value) {
        this.scadenzaSoluzioneUnica = value;
    }

}
