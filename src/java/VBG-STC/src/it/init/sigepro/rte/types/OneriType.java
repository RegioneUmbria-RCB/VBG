
package it.init.sigepro.rte.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OneriType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OneriType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="causale" type="{http://sigepro.init.it/rte/types}CausaleOnereType"/>
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="codiceProcedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scadenze" type="{http://sigepro.init.it/rte/types}OneriScadenzeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="segno" type="{http://sigepro.init.it/rte/types}segnoType"/>
 *         &lt;element name="annotazioni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nonDovuto" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OneriType", propOrder = {
    "causale",
    "importo",
    "codiceProcedimento",
    "scadenze",
    "segno",
    "annotazioni",
    "nonDovuto"
})
public class OneriType {

    @XmlElement(required = true)
    protected CausaleOnereType causale;
    protected double importo;
    protected String codiceProcedimento;
    protected List<OneriScadenzeType> scadenze;
    @XmlElement(required = true)
    protected SegnoType segno;
    protected String annotazioni;
    protected Boolean nonDovuto;

    /**
     * Gets the value of the causale property.
     * 
     * @return
     *     possible object is
     *     {@link CausaleOnereType }
     *     
     */
    public CausaleOnereType getCausale() {
        return causale;
    }

    /**
     * Sets the value of the causale property.
     * 
     * @param value
     *     allowed object is
     *     {@link CausaleOnereType }
     *     
     */
    public void setCausale(CausaleOnereType value) {
        this.causale = value;
    }

    /**
     * Gets the value of the importo property.
     * 
     */
    public double getImporto() {
        return importo;
    }

    /**
     * Sets the value of the importo property.
     * 
     */
    public void setImporto(double value) {
        this.importo = value;
    }

    /**
     * Gets the value of the codiceProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceProcedimento() {
        return codiceProcedimento;
    }

    /**
     * Sets the value of the codiceProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceProcedimento(String value) {
        this.codiceProcedimento = value;
    }

    /**
     * Gets the value of the scadenze property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scadenze property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScadenze().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OneriScadenzeType }
     * 
     * 
     */
    public List<OneriScadenzeType> getScadenze() {
        if (scadenze == null) {
            scadenze = new ArrayList<OneriScadenzeType>();
        }
        return this.scadenze;
    }

    /**
     * Gets the value of the segno property.
     * 
     * @return
     *     possible object is
     *     {@link SegnoType }
     *     
     */
    public SegnoType getSegno() {
        return segno;
    }

    /**
     * Sets the value of the segno property.
     * 
     * @param value
     *     allowed object is
     *     {@link SegnoType }
     *     
     */
    public void setSegno(SegnoType value) {
        this.segno = value;
    }

    /**
     * Gets the value of the annotazioni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotazioni() {
        return annotazioni;
    }

    /**
     * Sets the value of the annotazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotazioni(String value) {
        this.annotazioni = value;
    }

    /**
     * Gets the value of the nonDovuto property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNonDovuto() {
        return nonDovuto;
    }

    /**
     * Sets the value of the nonDovuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNonDovuto(Boolean value) {
        this.nonDovuto = value;
    }

}
