
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="esito"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="OK"/&gt;
 *               &lt;enumeration value="KO"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="statoRevoca" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="IN CORSO"/&gt;
 *               &lt;enumeration value="ESITO POSITIVO"/&gt;
 *               &lt;enumeration value="ESITO NEGATIVO"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dataOraMessaggioEsito" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime"&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="esitoPositivoStorno" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="causaleEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="datiAggiuntiviEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "esito",
    "statoRevoca",
    "dataOraMessaggioEsito",
    "esitoPositivoStorno",
    "causaleEsito",
    "datiAggiuntiviEsito",
    "xml",
    "codiceErrore",
    "descrizioneErrore"
})
@XmlRootElement(name = "leggiEsitoStornoResponse")
public class LeggiEsitoStornoResponse {

    @XmlElement(required = true)
    protected String esito;
    protected String statoRevoca;
    protected XMLGregorianCalendar dataOraMessaggioEsito;
    protected Boolean esitoPositivoStorno;
    protected String causaleEsito;
    protected String datiAggiuntiviEsito;
    protected byte[] xml;
    protected String codiceErrore;
    protected String descrizioneErrore;

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
     * Recupera il valore della proprietà statoRevoca.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoRevoca() {
        return statoRevoca;
    }

    /**
     * Imposta il valore della proprietà statoRevoca.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoRevoca(String value) {
        this.statoRevoca = value;
    }

    /**
     * Recupera il valore della proprietà dataOraMessaggioEsito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraMessaggioEsito() {
        return dataOraMessaggioEsito;
    }

    /**
     * Imposta il valore della proprietà dataOraMessaggioEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraMessaggioEsito(XMLGregorianCalendar value) {
        this.dataOraMessaggioEsito = value;
    }

    /**
     * Recupera il valore della proprietà esitoPositivoStorno.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsitoPositivoStorno() {
        return esitoPositivoStorno;
    }

    /**
     * Imposta il valore della proprietà esitoPositivoStorno.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsitoPositivoStorno(Boolean value) {
        this.esitoPositivoStorno = value;
    }

    /**
     * Recupera il valore della proprietà causaleEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleEsito() {
        return causaleEsito;
    }

    /**
     * Imposta il valore della proprietà causaleEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleEsito(String value) {
        this.causaleEsito = value;
    }

    /**
     * Recupera il valore della proprietà datiAggiuntiviEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiAggiuntiviEsito() {
        return datiAggiuntiviEsito;
    }

    /**
     * Imposta il valore della proprietà datiAggiuntiviEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiAggiuntiviEsito(String value) {
        this.datiAggiuntiviEsito = value;
    }

    /**
     * Recupera il valore della proprietà xml.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXml() {
        return xml;
    }

    /**
     * Imposta il valore della proprietà xml.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXml(byte[] value) {
        this.xml = value;
    }

    /**
     * Recupera il valore della proprietà codiceErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceErrore() {
        return codiceErrore;
    }

    /**
     * Imposta il valore della proprietà codiceErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceErrore(String value) {
        this.codiceErrore = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneErrore() {
        return descrizioneErrore;
    }

    /**
     * Imposta il valore della proprietà descrizioneErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneErrore(String value) {
        this.descrizioneErrore = value;
    }

}
