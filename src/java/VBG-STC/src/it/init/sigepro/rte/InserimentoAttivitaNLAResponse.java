
package it.init.sigepro.rte;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="dettaglioAttivita" type="{http://sigepro.init.it/rte/types}RiferimentiAttivitaType" minOccurs="0"/>
 *         &lt;element name="dettaglioErrore" type="{http://sigepro.init.it/rte/types}ErroreType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dettaglioAttivita",
    "dettaglioErrore"
})
@XmlRootElement(name = "InserimentoAttivitaNLAResponse")
public class InserimentoAttivitaNLAResponse {

    protected RiferimentiAttivitaType dettaglioAttivita;
    protected List<ErroreType> dettaglioErrore;

    /**
     * Gets the value of the dettaglioAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentiAttivitaType }
     *     
     */
    public RiferimentiAttivitaType getDettaglioAttivita() {
        return dettaglioAttivita;
    }

    /**
     * Sets the value of the dettaglioAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentiAttivitaType }
     *     
     */
    public void setDettaglioAttivita(RiferimentiAttivitaType value) {
        this.dettaglioAttivita = value;
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

}
