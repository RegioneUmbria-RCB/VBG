
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CampoStaticoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CampoStaticoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoCampo" type="{http://sigepro.init.it/rte/types}TipoCampoStaticoType"/>
 *         &lt;element name="posizione" type="{http://sigepro.init.it/rte/types}PosizioneCampoType"/>
 *         &lt;element name="testoFisso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampoStaticoType", propOrder = {
    "tipoCampo",
    "posizione",
    "testoFisso"
})
public class CampoStaticoType {

    @XmlElement(required = true)
    protected TipoCampoStaticoType tipoCampo;
    @XmlElement(required = true)
    protected PosizioneCampoType posizione;
    @XmlElement(required = true)
    protected String testoFisso;

    /**
     * Gets the value of the tipoCampo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCampoStaticoType }
     *     
     */
    public TipoCampoStaticoType getTipoCampo() {
        return tipoCampo;
    }

    /**
     * Sets the value of the tipoCampo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCampoStaticoType }
     *     
     */
    public void setTipoCampo(TipoCampoStaticoType value) {
        this.tipoCampo = value;
    }

    /**
     * Gets the value of the posizione property.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneCampoType }
     *     
     */
    public PosizioneCampoType getPosizione() {
        return posizione;
    }

    /**
     * Sets the value of the posizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneCampoType }
     *     
     */
    public void setPosizione(PosizioneCampoType value) {
        this.posizione = value;
    }

    /**
     * Gets the value of the testoFisso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestoFisso() {
        return testoFisso;
    }

    /**
     * Sets the value of the testoFisso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestoFisso(String value) {
        this.testoFisso = value;
    }

}
