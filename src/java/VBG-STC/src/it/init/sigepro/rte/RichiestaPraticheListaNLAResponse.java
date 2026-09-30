
package it.init.sigepro.rte;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.init.sigepro.rte.types.DettaglioPraticaBreveType;
import it.init.sigepro.rte.types.ErroreType;


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
 *         &lt;element name="dettaglioPratica" type="{http://sigepro.init.it/rte/types}DettaglioPraticaBreveType" maxOccurs="unbounded" minOccurs="0"/>
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
    "dettaglioPratica",
    "dettaglioErrore"
})
@XmlRootElement(name = "RichiestaPraticheListaNLAResponse")
public class RichiestaPraticheListaNLAResponse {

    protected List<DettaglioPraticaBreveType> dettaglioPratica;
    protected List<ErroreType> dettaglioErrore;

    /**
     * Gets the value of the dettaglioPratica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dettaglioPratica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDettaglioPratica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DettaglioPraticaBreveType }
     * 
     * 
     */
    public List<DettaglioPraticaBreveType> getDettaglioPratica() {
        if (dettaglioPratica == null) {
            dettaglioPratica = new ArrayList<DettaglioPraticaBreveType>();
        }
        return this.dettaglioPratica;
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
