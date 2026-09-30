
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Contribuente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Contribuente"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}SoggettoBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Codice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Pec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Cellulare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceIsoCittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Domicilio" type="{http://entranext.it/}Ubicazione" minOccurs="0"/&gt;
 *         &lt;element name="Domiciliazione" type="{http://entranext.it/}Contribuente_Domiciliazione"/&gt;
 *         &lt;element name="SoggettoASplitPayment" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="NomeSede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceDestinatarioSDI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Sede" type="{http://entranext.it/}UbicazioneSede" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contribuente", propOrder = {
    "codice",
    "pec",
    "telefono",
    "cellulare",
    "codiceIsoCittadinanza",
    "domicilio",
    "domiciliazione",
    "soggettoASplitPayment",
    "nomeSede",
    "codiceDestinatarioSDI",
    "sede"
})
public class Contribuente
    extends SoggettoBase
{

    @XmlElement(name = "Codice")
    protected String codice;
    @XmlElement(name = "Pec")
    protected String pec;
    @XmlElement(name = "Telefono")
    protected String telefono;
    @XmlElement(name = "Cellulare")
    protected String cellulare;
    @XmlElement(name = "CodiceIsoCittadinanza")
    protected String codiceIsoCittadinanza;
    @XmlElement(name = "Domicilio")
    protected Ubicazione domicilio;
    @XmlElement(name = "Domiciliazione", required = true, nillable = true)
    protected ContribuenteDomiciliazione domiciliazione;
    @XmlElement(name = "SoggettoASplitPayment")
    protected Boolean soggettoASplitPayment;
    @XmlElement(name = "NomeSede")
    protected String nomeSede;
    @XmlElement(name = "CodiceDestinatarioSDI")
    protected String codiceDestinatarioSDI;
    @XmlElement(name = "Sede")
    protected UbicazioneSede sede;

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprietà pec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Imposta il valore della proprietà pec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Recupera il valore della proprietà telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della proprietà telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Recupera il valore della proprietà cellulare.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellulare() {
        return cellulare;
    }

    /**
     * Imposta il valore della proprietà cellulare.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellulare(String value) {
        this.cellulare = value;
    }

    /**
     * Recupera il valore della proprietà codiceIsoCittadinanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIsoCittadinanza() {
        return codiceIsoCittadinanza;
    }

    /**
     * Imposta il valore della proprietà codiceIsoCittadinanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIsoCittadinanza(String value) {
        this.codiceIsoCittadinanza = value;
    }

    /**
     * Recupera il valore della proprietà domicilio.
     * 
     * @return
     *     possible object is
     *     {@link Ubicazione }
     *     
     */
    public Ubicazione getDomicilio() {
        return domicilio;
    }

    /**
     * Imposta il valore della proprietà domicilio.
     * 
     * @param value
     *     allowed object is
     *     {@link Ubicazione }
     *     
     */
    public void setDomicilio(Ubicazione value) {
        this.domicilio = value;
    }

    /**
     * Recupera il valore della proprietà domiciliazione.
     * 
     * @return
     *     possible object is
     *     {@link ContribuenteDomiciliazione }
     *     
     */
    public ContribuenteDomiciliazione getDomiciliazione() {
        return domiciliazione;
    }

    /**
     * Imposta il valore della proprietà domiciliazione.
     * 
     * @param value
     *     allowed object is
     *     {@link ContribuenteDomiciliazione }
     *     
     */
    public void setDomiciliazione(ContribuenteDomiciliazione value) {
        this.domiciliazione = value;
    }

    /**
     * Recupera il valore della proprietà soggettoASplitPayment.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSoggettoASplitPayment() {
        return soggettoASplitPayment;
    }

    /**
     * Imposta il valore della proprietà soggettoASplitPayment.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSoggettoASplitPayment(Boolean value) {
        this.soggettoASplitPayment = value;
    }

    /**
     * Recupera il valore della proprietà nomeSede.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeSede() {
        return nomeSede;
    }

    /**
     * Imposta il valore della proprietà nomeSede.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeSede(String value) {
        this.nomeSede = value;
    }

    /**
     * Recupera il valore della proprietà codiceDestinatarioSDI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceDestinatarioSDI() {
        return codiceDestinatarioSDI;
    }

    /**
     * Imposta il valore della proprietà codiceDestinatarioSDI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceDestinatarioSDI(String value) {
        this.codiceDestinatarioSDI = value;
    }

    /**
     * Recupera il valore della proprietà sede.
     * 
     * @return
     *     possible object is
     *     {@link UbicazioneSede }
     *     
     */
    public UbicazioneSede getSede() {
        return sede;
    }

    /**
     * Imposta il valore della proprietà sede.
     * 
     * @param value
     *     allowed object is
     *     {@link UbicazioneSede }
     *     
     */
    public void setSede(UbicazioneSede value) {
        this.sede = value;
    }

}
