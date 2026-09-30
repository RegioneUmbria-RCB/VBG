
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datiSingoloVersamentoListaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiSingoloVersamentoListaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="marcaBolloDigitale" type="{http://services.sia.eu/}marcaBolloDigitaleType" minOccurs="0"/>
 *         &lt;element name="importoSingoloVersamento" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="commissioniCaricoPa" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ibanAccredito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bicAccredito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ibanAppoggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bicAppoggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="credenzialiPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="causaleVersamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiSingoloVersamentoListaType", propOrder = {
    "marcaBolloDigitale",
    "importoSingoloVersamento",
    "commissioniCaricoPa",
    "ibanAccredito",
    "bicAccredito",
    "ibanAppoggio",
    "bicAppoggio",
    "credenzialiPagatore",
    "causaleVersamento"
})
public class DatiSingoloVersamentoListaType {

    protected MarcaBolloDigitaleType marcaBolloDigitale;
    @XmlElement(required = true)
    protected BigDecimal importoSingoloVersamento;
    @XmlElement(required = true)
    protected BigDecimal commissioniCaricoPa;
    protected String ibanAccredito;
    protected String bicAccredito;
    protected String ibanAppoggio;
    protected String bicAppoggio;
    protected String credenzialiPagatore;
    @XmlElement(required = true)
    protected String causaleVersamento;

    /**
     * Gets the value of the marcaBolloDigitale property.
     * 
     * @return
     *     possible object is
     *     {@link MarcaBolloDigitaleType }
     *     
     */
    public MarcaBolloDigitaleType getMarcaBolloDigitale() {
        return marcaBolloDigitale;
    }

    /**
     * Sets the value of the marcaBolloDigitale property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarcaBolloDigitaleType }
     *     
     */
    public void setMarcaBolloDigitale(MarcaBolloDigitaleType value) {
        this.marcaBolloDigitale = value;
    }

    /**
     * Gets the value of the importoSingoloVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoSingoloVersamento() {
        return importoSingoloVersamento;
    }

    /**
     * Sets the value of the importoSingoloVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoSingoloVersamento(BigDecimal value) {
        this.importoSingoloVersamento = value;
    }

    /**
     * Gets the value of the commissioniCaricoPa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCommissioniCaricoPa() {
        return commissioniCaricoPa;
    }

    /**
     * Sets the value of the commissioniCaricoPa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCommissioniCaricoPa(BigDecimal value) {
        this.commissioniCaricoPa = value;
    }

    /**
     * Gets the value of the ibanAccredito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIbanAccredito() {
        return ibanAccredito;
    }

    /**
     * Sets the value of the ibanAccredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIbanAccredito(String value) {
        this.ibanAccredito = value;
    }

    /**
     * Gets the value of the bicAccredito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBicAccredito() {
        return bicAccredito;
    }

    /**
     * Sets the value of the bicAccredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBicAccredito(String value) {
        this.bicAccredito = value;
    }

    /**
     * Gets the value of the ibanAppoggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIbanAppoggio() {
        return ibanAppoggio;
    }

    /**
     * Sets the value of the ibanAppoggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIbanAppoggio(String value) {
        this.ibanAppoggio = value;
    }

    /**
     * Gets the value of the bicAppoggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBicAppoggio() {
        return bicAppoggio;
    }

    /**
     * Sets the value of the bicAppoggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBicAppoggio(String value) {
        this.bicAppoggio = value;
    }

    /**
     * Gets the value of the credenzialiPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredenzialiPagatore() {
        return credenzialiPagatore;
    }

    /**
     * Sets the value of the credenzialiPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredenzialiPagatore(String value) {
        this.credenzialiPagatore = value;
    }

    /**
     * Gets the value of the causaleVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleVersamento() {
        return causaleVersamento;
    }

    /**
     * Sets the value of the causaleVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleVersamento(String value) {
        this.causaleVersamento = value;
    }

}
