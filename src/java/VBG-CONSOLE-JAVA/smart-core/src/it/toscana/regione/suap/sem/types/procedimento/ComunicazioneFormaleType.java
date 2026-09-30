
package it.toscana.regione.suap.sem.types.procedimento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for comunicazioneFormaleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="comunicazioneFormaleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comunicazionePDF" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}allegatoPDFType"/>
 *         &lt;element name="comunicazioniSecondarie" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}allegatoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comunicazioneFormaleType", propOrder = {
    "messaggio",
    "comunicazionePDF",
    "comunicazioniSecondarie"
})
public class ComunicazioneFormaleType {

    protected String messaggio;
    @XmlElement(required = true)
    protected AllegatoPDFType comunicazionePDF;
    protected List<AllegatoType> comunicazioniSecondarie;

    /**
     * Gets the value of the messaggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Sets the value of the messaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggio(String value) {
        this.messaggio = value;
    }

    /**
     * Gets the value of the comunicazionePDF property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoPDFType }
     *     
     */
    public AllegatoPDFType getComunicazionePDF() {
        return comunicazionePDF;
    }

    /**
     * Sets the value of the comunicazionePDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoPDFType }
     *     
     */
    public void setComunicazionePDF(AllegatoPDFType value) {
        this.comunicazionePDF = value;
    }

    /**
     * Gets the value of the comunicazioniSecondarie property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comunicazioniSecondarie property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComunicazioniSecondarie().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoType }
     * 
     * 
     */
    public List<AllegatoType> getComunicazioniSecondarie() {
        if (comunicazioniSecondarie == null) {
            comunicazioniSecondarie = new ArrayList<AllegatoType>();
        }
        return this.comunicazioniSecondarie;
    }

}
