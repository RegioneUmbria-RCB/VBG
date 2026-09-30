
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CampoSchedaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CampoSchedaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="campoDinamico" type="{http://sigepro.init.it/rte/types}CampoDinamicoType" minOccurs="0"/>
 *         &lt;element name="campoStatico" type="{http://sigepro.init.it/rte/types}CampoStaticoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampoSchedaType", propOrder = {
    "codice",
    "descrizione",
    "campoDinamico",
    "campoStatico"
})
public class CampoSchedaType {

    @XmlElement(required = true)
    protected String codice;
    @XmlElement(required = true)
    protected String descrizione;
    protected CampoDinamicoType campoDinamico;
    protected CampoStaticoType campoStatico;

    /**
     * Gets the value of the codice property.
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
     * Sets the value of the codice property.
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
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
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
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the campoDinamico property.
     * 
     * @return
     *     possible object is
     *     {@link CampoDinamicoType }
     *     
     */
    public CampoDinamicoType getCampoDinamico() {
        return campoDinamico;
    }

    /**
     * Sets the value of the campoDinamico property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampoDinamicoType }
     *     
     */
    public void setCampoDinamico(CampoDinamicoType value) {
        this.campoDinamico = value;
    }

    /**
     * Gets the value of the campoStatico property.
     * 
     * @return
     *     possible object is
     *     {@link CampoStaticoType }
     *     
     */
    public CampoStaticoType getCampoStatico() {
        return campoStatico;
    }

    /**
     * Sets the value of the campoStatico property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampoStaticoType }
     *     
     */
    public void setCampoStatico(CampoStaticoType value) {
        this.campoStatico = value;
    }

}
