
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idScheda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceScheda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneScheda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dyn2CampiType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2CampiType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "software",
    "idScheda",
    "codiceScheda",
    "descrizioneScheda",
    "dyn2CampiType"
})
@XmlRootElement(name = "ModellitFindResponse")
public class ModellitFindResponse {

    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String idScheda;
    @XmlElement(required = true)
    protected String codiceScheda;
    @XmlElement(required = true)
    protected String descrizioneScheda;
    @XmlElement(required = true)
    protected List<Dyn2CampiType> dyn2CampiType;

    /**
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

    /**
     * Gets the value of the idScheda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdScheda() {
        return idScheda;
    }

    /**
     * Sets the value of the idScheda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdScheda(String value) {
        this.idScheda = value;
    }

    /**
     * Gets the value of the codiceScheda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceScheda() {
        return codiceScheda;
    }

    /**
     * Sets the value of the codiceScheda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceScheda(String value) {
        this.codiceScheda = value;
    }

    /**
     * Gets the value of the descrizioneScheda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneScheda() {
        return descrizioneScheda;
    }

    /**
     * Sets the value of the descrizioneScheda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneScheda(String value) {
        this.descrizioneScheda = value;
    }

    /**
     * Gets the value of the dyn2CampiType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dyn2CampiType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDyn2CampiType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dyn2CampiType }
     * 
     * 
     */
    public List<Dyn2CampiType> getDyn2CampiType() {
        if (dyn2CampiType == null) {
            dyn2CampiType = new ArrayList<Dyn2CampiType>();
        }
        return this.dyn2CampiType;
    }

}
