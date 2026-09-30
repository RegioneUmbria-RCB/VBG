
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoSmistamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Flusso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Classifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumProtMitt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataProtMitt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mittenti" type="{http://it.gruppoinit/Protocollazione}DatiMittentiType" minOccurs="0"/>
 *         &lt;element name="Destinatari" type="{http://it.gruppoinit/Protocollazione}DatiDestinatariType" minOccurs="0"/>
 *         &lt;element name="Allegati" type="{http://it.gruppoinit/Protocollazione}ArrayOfAllegatoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiRequestType", propOrder = {
    "tipoDocumento",
    "tipoSmistamento",
    "oggetto",
    "flusso",
    "classifica",
    "numProtMitt",
    "dataProtMitt",
    "mittenti",
    "destinatari",
    "allegati"
})
public class DatiRequestType {

    @XmlElement(name = "TipoDocumento", nillable = true)
    protected String tipoDocumento;
    @XmlElement(name = "TipoSmistamento", nillable = true)
    protected String tipoSmistamento;
    @XmlElement(name = "Oggetto", nillable = true)
    protected String oggetto;
    @XmlElement(name = "Flusso", nillable = true)
    protected String flusso;
    @XmlElement(name = "Classifica", nillable = true)
    protected String classifica;
    @XmlElement(name = "NumProtMitt", nillable = true)
    protected String numProtMitt;
    @XmlElement(name = "DataProtMitt", nillable = true)
    protected String dataProtMitt;
    @XmlElement(name = "Mittenti", nillable = true)
    protected DatiMittentiType mittenti;
    @XmlElement(name = "Destinatari", nillable = true)
    protected DatiDestinatariType destinatari;
    @XmlElement(name = "Allegati", nillable = true)
    protected ArrayOfAllegatoType allegati;

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the tipoSmistamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoSmistamento() {
        return tipoSmistamento;
    }

    /**
     * Sets the value of the tipoSmistamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoSmistamento(String value) {
        this.tipoSmistamento = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the flusso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlusso() {
        return flusso;
    }

    /**
     * Sets the value of the flusso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlusso(String value) {
        this.flusso = value;
    }

    /**
     * Gets the value of the classifica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassifica() {
        return classifica;
    }

    /**
     * Sets the value of the classifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassifica(String value) {
        this.classifica = value;
    }

    /**
     * Gets the value of the numProtMitt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProtMitt() {
        return numProtMitt;
    }

    /**
     * Sets the value of the numProtMitt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProtMitt(String value) {
        this.numProtMitt = value;
    }

    /**
     * Gets the value of the dataProtMitt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtMitt() {
        return dataProtMitt;
    }

    /**
     * Sets the value of the dataProtMitt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtMitt(String value) {
        this.dataProtMitt = value;
    }

    /**
     * Gets the value of the mittenti property.
     * 
     * @return
     *     possible object is
     *     {@link DatiMittentiType }
     *     
     */
    public DatiMittentiType getMittenti() {
        return mittenti;
    }

    /**
     * Sets the value of the mittenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiMittentiType }
     *     
     */
    public void setMittenti(DatiMittentiType value) {
        this.mittenti = value;
    }

    /**
     * Gets the value of the destinatari property.
     * 
     * @return
     *     possible object is
     *     {@link DatiDestinatariType }
     *     
     */
    public DatiDestinatariType getDestinatari() {
        return destinatari;
    }

    /**
     * Sets the value of the destinatari property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiDestinatariType }
     *     
     */
    public void setDestinatari(DatiDestinatariType value) {
        this.destinatari = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAllegatoType }
     *     
     */
    public ArrayOfAllegatoType getAllegati() {
        return allegati;
    }

    /**
     * Sets the value of the allegati property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAllegatoType }
     *     
     */
    public void setAllegati(ArrayOfAllegatoType value) {
        this.allegati = value;
    }

}
