
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;


/**
 * <p>Java class for aggiornaStatoPraticaReqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aggiornaStatoPraticaReqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mittente" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}soggettoReteSuapType"/>
 *         &lt;element name="idPratica" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}idPraticaSUAP"/>
 *         &lt;element name="fase" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}stringaNonVuota"/>
 *         &lt;element name="giorni" type="{http://www.w3.org/2001/XMLSchema}duration"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aggiornaStatoPraticaReqType", propOrder = {
    "mittente",
    "idPratica",
    "fase",
    "giorni"
})
public class AggiornaStatoPraticaReqType {

    @XmlElement(required = true)
    protected SoggettoReteSuapType mittente;
    @XmlElement(required = true)
    protected String idPratica;
    @XmlElement(required = true)
    protected String fase;
    @XmlElement(required = true)
    protected Duration giorni;

    /**
     * Gets the value of the mittente property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoReteSuapType }
     *     
     */
    public SoggettoReteSuapType getMittente() {
        return mittente;
    }

    /**
     * Sets the value of the mittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoReteSuapType }
     *     
     */
    public void setMittente(SoggettoReteSuapType value) {
        this.mittente = value;
    }

    /**
     * Gets the value of the idPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPratica() {
        return idPratica;
    }

    /**
     * Sets the value of the idPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPratica(String value) {
        this.idPratica = value;
    }

    /**
     * Gets the value of the fase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFase() {
        return fase;
    }

    /**
     * Sets the value of the fase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFase(String value) {
        this.fase = value;
    }

    /**
     * Gets the value of the giorni property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getGiorni() {
        return giorni;
    }

    /**
     * Sets the value of the giorni property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setGiorni(Duration value) {
        this.giorni = value;
    }

}
