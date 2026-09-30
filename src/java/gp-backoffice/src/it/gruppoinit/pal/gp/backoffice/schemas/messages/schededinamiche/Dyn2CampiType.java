
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dyn2CampiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2CampiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="etichetta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="obbligatorio" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tipodato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dyn2BasecontestiType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2BasecontestiType" minOccurs="0"/>
 *         &lt;element name="dyn2CampiScriptType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2CampiScriptType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dyn2Campiproprieta" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2CampiproprietaType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2CampiType", propOrder = {
    "idCampo",
    "software",
    "nomeCampo",
    "etichetta",
    "obbligatorio",
    "tipodato",
    "descrizione",
    "dyn2BasecontestiType",
    "dyn2CampiScriptType",
    "dyn2Campiproprieta"
})
public class Dyn2CampiType {

    @XmlElement(required = true)
    protected String idCampo;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String nomeCampo;
    protected String etichetta;
    protected boolean obbligatorio;
    protected String tipodato;
    protected String descrizione;
    protected Dyn2BasecontestiType dyn2BasecontestiType;
    protected List<Dyn2CampiScriptType> dyn2CampiScriptType;
    protected List<Dyn2CampiproprietaType> dyn2Campiproprieta;

    /**
     * Gets the value of the idCampo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCampo() {
        return idCampo;
    }

    /**
     * Sets the value of the idCampo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCampo(String value) {
        this.idCampo = value;
    }

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
     * Gets the value of the nomeCampo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCampo() {
        return nomeCampo;
    }

    /**
     * Sets the value of the nomeCampo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCampo(String value) {
        this.nomeCampo = value;
    }

    /**
     * Gets the value of the etichetta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEtichetta() {
        return etichetta;
    }

    /**
     * Sets the value of the etichetta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEtichetta(String value) {
        this.etichetta = value;
    }

    /**
     * Gets the value of the obbligatorio property.
     * 
     */
    public boolean isObbligatorio() {
        return obbligatorio;
    }

    /**
     * Sets the value of the obbligatorio property.
     * 
     */
    public void setObbligatorio(boolean value) {
        this.obbligatorio = value;
    }

    /**
     * Gets the value of the tipodato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipodato() {
        return tipodato;
    }

    /**
     * Sets the value of the tipodato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipodato(String value) {
        this.tipodato = value;
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
     * Gets the value of the dyn2BasecontestiType property.
     * 
     * @return
     *     possible object is
     *     {@link Dyn2BasecontestiType }
     *     
     */
    public Dyn2BasecontestiType getDyn2BasecontestiType() {
        return dyn2BasecontestiType;
    }

    /**
     * Sets the value of the dyn2BasecontestiType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dyn2BasecontestiType }
     *     
     */
    public void setDyn2BasecontestiType(Dyn2BasecontestiType value) {
        this.dyn2BasecontestiType = value;
    }

    /**
     * Gets the value of the dyn2CampiScriptType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dyn2CampiScriptType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDyn2CampiScriptType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dyn2CampiScriptType }
     * 
     * 
     */
    public List<Dyn2CampiScriptType> getDyn2CampiScriptType() {
        if (dyn2CampiScriptType == null) {
            dyn2CampiScriptType = new ArrayList<Dyn2CampiScriptType>();
        }
        return this.dyn2CampiScriptType;
    }

    /**
     * Gets the value of the dyn2Campiproprieta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dyn2Campiproprieta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDyn2Campiproprieta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dyn2CampiproprietaType }
     * 
     * 
     */
    public List<Dyn2CampiproprietaType> getDyn2Campiproprieta() {
        if (dyn2Campiproprieta == null) {
            dyn2Campiproprieta = new ArrayList<Dyn2CampiproprietaType>();
        }
        return this.dyn2Campiproprieta;
    }

}
