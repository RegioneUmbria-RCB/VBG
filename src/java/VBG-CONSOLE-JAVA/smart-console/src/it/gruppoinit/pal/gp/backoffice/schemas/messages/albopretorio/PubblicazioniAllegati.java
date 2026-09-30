
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pubblicazioniAllegati complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pubblicazioniAllegati">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="CODICEALLEGATO" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CODICEOGGETTO" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NOMEFILE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DIMENSIONEFILE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIZIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORDINE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pubblicazioniAllegati", propOrder = {

})
public class PubblicazioniAllegati {

    @XmlElement(name = "CODICEALLEGATO")
    protected int codiceallegato;
    @XmlElement(name = "CODICEOGGETTO")
    protected int codiceoggetto;
    @XmlElement(name = "NOMEFILE", required = true)
    protected String nomefile;
    @XmlElement(name = "DIMENSIONEFILE", required = true)
    protected String dimensionefile;
    @XmlElement(name = "DESCRIZIONE", required = true)
    protected String descrizione;
    @XmlElement(name = "ORDINE")
    protected int ordine;

    /**
     * Gets the value of the codiceallegato property.
     * 
     */
    public int getCODICEALLEGATO() {
        return codiceallegato;
    }

    /**
     * Sets the value of the codiceallegato property.
     * 
     */
    public void setCODICEALLEGATO(int value) {
        this.codiceallegato = value;
    }

    /**
     * Gets the value of the codiceoggetto property.
     * 
     */
    public int getCODICEOGGETTO() {
        return codiceoggetto;
    }

    /**
     * Sets the value of the codiceoggetto property.
     * 
     */
    public void setCODICEOGGETTO(int value) {
        this.codiceoggetto = value;
    }

    /**
     * Gets the value of the nomefile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMEFILE() {
        return nomefile;
    }

    /**
     * Sets the value of the nomefile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMEFILE(String value) {
        this.nomefile = value;
    }

    /**
     * Gets the value of the dimensionefile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIMENSIONEFILE() {
        return dimensionefile;
    }

    /**
     * Sets the value of the dimensionefile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIMENSIONEFILE(String value) {
        this.dimensionefile = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIZIONE() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIZIONE(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the ordine property.
     * 
     */
    public int getORDINE() {
        return ordine;
    }

    /**
     * Sets the value of the ordine property.
     * 
     */
    public void setORDINE(int value) {
        this.ordine = value;
    }

}
