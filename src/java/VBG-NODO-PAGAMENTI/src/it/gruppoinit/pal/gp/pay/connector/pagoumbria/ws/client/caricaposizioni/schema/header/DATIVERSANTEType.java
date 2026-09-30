
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.StTipoSoggetto;


/**
 * <p>Classe Java per DATI_VERSANTE_Type complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DATI_VERSANTE_Type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TIPO_SOGGETTO" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}stTipoSoggetto"/&gt;
 *         &lt;element name="ID_FISCALE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ANAGRAFICA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="E_MAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INDIRIZZO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NUMERO_CIVICO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LOCALITA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PROVINCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NAZIONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATI_VERSANTE_Type", propOrder = {
    "tiposoggetto",
    "idfiscale",
    "anagrafica",
    "email",
    "indirizzo",
    "numerocivico",
    "cap",
    "localita",
    "provincia",
    "nazione"
})
public class DATIVERSANTEType {

    @XmlElement(name = "TIPO_SOGGETTO", required = true)
    @XmlSchemaType(name = "string")
    protected StTipoSoggetto tiposoggetto;
    @XmlElement(name = "ID_FISCALE", required = true)
    protected String idfiscale;
    @XmlElement(name = "ANAGRAFICA", required = true)
    protected String anagrafica;
    @XmlElement(name = "E_MAIL")
    protected String email;
    @XmlElement(name = "INDIRIZZO")
    protected String indirizzo;
    @XmlElement(name = "NUMERO_CIVICO")
    protected String numerocivico;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "LOCALITA")
    protected String localita;
    @XmlElement(name = "PROVINCIA")
    protected String provincia;
    @XmlElement(name = "NAZIONE")
    protected String nazione;

    /**
     * Recupera il valore della proprietà tiposoggetto.
     * 
     * @return
     *     possible object is
     *     {@link StTipoSoggetto }
     *     
     */
    public StTipoSoggetto getTIPOSOGGETTO() {
        return tiposoggetto;
    }

    /**
     * Imposta il valore della proprietà tiposoggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link StTipoSoggetto }
     *     
     */
    public void setTIPOSOGGETTO(StTipoSoggetto value) {
        this.tiposoggetto = value;
    }

    /**
     * Recupera il valore della proprietà idfiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDFISCALE() {
        return idfiscale;
    }

    /**
     * Imposta il valore della proprietà idfiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDFISCALE(String value) {
        this.idfiscale = value;
    }

    /**
     * Recupera il valore della proprietà anagrafica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANAGRAFICA() {
        return anagrafica;
    }

    /**
     * Imposta il valore della proprietà anagrafica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANAGRAFICA(String value) {
        this.anagrafica = value;
    }

    /**
     * Recupera il valore della proprietà email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * Imposta il valore della proprietà email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della proprietà indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIRIZZO() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietà indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDIRIZZO(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietà numerocivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROCIVICO() {
        return numerocivico;
    }

    /**
     * Imposta il valore della proprietà numerocivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROCIVICO(String value) {
        this.numerocivico = value;
    }

    /**
     * Recupera il valore della proprietà cap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
        return cap;
    }

    /**
     * Imposta il valore della proprietà cap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della proprietà localita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCALITA() {
        return localita;
    }

    /**
     * Imposta il valore della proprietà localita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCALITA(String value) {
        this.localita = value;
    }

    /**
     * Recupera il valore della proprietà provincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIA() {
        return provincia;
    }

    /**
     * Imposta il valore della proprietà provincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIA(String value) {
        this.provincia = value;
    }

    /**
     * Recupera il valore della proprietà nazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAZIONE() {
        return nazione;
    }

    /**
     * Imposta il valore della proprietà nazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAZIONE(String value) {
        this.nazione = value;
    }

}
