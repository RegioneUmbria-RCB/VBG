
package it.init.sigepro.rte;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.SportelloType;


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
 *         &lt;element name="dettaglio" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="sportello" type="{http://sigepro.init.it/rte/types}SportelloType"/>
 *                   &lt;element name="dettaglioPratica" type="{http://sigepro.init.it/rte/types}DettaglioPraticaType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="dettaglioErrore" type="{http://sigepro.init.it/rte/types}ErroreType" maxOccurs="unbounded" minOccurs="0"/>
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
    "dettaglio",
    "dettaglioErrore"
})
@XmlRootElement(name = "RichiestaPraticaCollegataResponse")
public class RichiestaPraticaCollegataResponse {

    protected RichiestaPraticaCollegataResponse.Dettaglio dettaglio;
    protected List<ErroreType> dettaglioErrore;

    /**
     * Gets the value of the dettaglio property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaPraticaCollegataResponse.Dettaglio }
     *     
     */
    public RichiestaPraticaCollegataResponse.Dettaglio getDettaglio() {
        return dettaglio;
    }

    /**
     * Sets the value of the dettaglio property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaPraticaCollegataResponse.Dettaglio }
     *     
     */
    public void setDettaglio(RichiestaPraticaCollegataResponse.Dettaglio value) {
        this.dettaglio = value;
    }

    /**
     * Gets the value of the dettaglioErrore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dettaglioErrore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDettaglioErrore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErroreType }
     * 
     * 
     */
    public List<ErroreType> getDettaglioErrore() {
        if (dettaglioErrore == null) {
            dettaglioErrore = new ArrayList<ErroreType>();
        }
        return this.dettaglioErrore;
    }


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
     *         &lt;element name="sportello" type="{http://sigepro.init.it/rte/types}SportelloType"/>
     *         &lt;element name="dettaglioPratica" type="{http://sigepro.init.it/rte/types}DettaglioPraticaType"/>
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
        "sportello",
        "dettaglioPratica"
    })
    public static class Dettaglio {

        @XmlElement(required = true)
        protected SportelloType sportello;
        @XmlElement(required = true)
        protected DettaglioPraticaType dettaglioPratica;

        /**
         * Gets the value of the sportello property.
         * 
         * @return
         *     possible object is
         *     {@link SportelloType }
         *     
         */
        public SportelloType getSportello() {
            return sportello;
        }

        /**
         * Sets the value of the sportello property.
         * 
         * @param value
         *     allowed object is
         *     {@link SportelloType }
         *     
         */
        public void setSportello(SportelloType value) {
            this.sportello = value;
        }

        /**
         * Gets the value of the dettaglioPratica property.
         * 
         * @return
         *     possible object is
         *     {@link DettaglioPraticaType }
         *     
         */
        public DettaglioPraticaType getDettaglioPratica() {
            return dettaglioPratica;
        }

        /**
         * Sets the value of the dettaglioPratica property.
         * 
         * @param value
         *     allowed object is
         *     {@link DettaglioPraticaType }
         *     
         */
        public void setDettaglioPratica(DettaglioPraticaType value) {
            this.dettaglioPratica = value;
        }

    }

}
