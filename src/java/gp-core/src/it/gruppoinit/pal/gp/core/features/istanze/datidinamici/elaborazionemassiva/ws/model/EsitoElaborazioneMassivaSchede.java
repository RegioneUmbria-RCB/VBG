
package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EsitoElaborazioneMassivaSchede complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EsitoElaborazioneMassivaSchede">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Esito" type="{http://schemas.datacontract.org/2004/07/Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza}EsitoElaborazioneMassivaSchedeEnum" minOccurs="0"/>
 *         &lt;element name="IstanzeConErrori" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IstanzeElaborate" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoElaborazioneMassivaSchede", namespace = "http://schemas.datacontract.org/2004/07/Sigepro.net.WebServices.WsSIGePro.Wcf.ElaborazioneMassiva", propOrder = {
    "esito",
    "istanzeConErrori",
    "istanzeElaborate"
})
public class EsitoElaborazioneMassivaSchede {

    @XmlElement(name = "Esito")
    protected EsitoElaborazioneMassivaSchedeEnum esito;
    @XmlElement(name = "IstanzeConErrori")
    protected Integer istanzeConErrori;
    @XmlElement(name = "IstanzeElaborate")
    protected Integer istanzeElaborate;

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoElaborazioneMassivaSchedeEnum }
     *     
     */
    public EsitoElaborazioneMassivaSchedeEnum getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoElaborazioneMassivaSchedeEnum }
     *     
     */
    public void setEsito(EsitoElaborazioneMassivaSchedeEnum value) {
        this.esito = value;
    }

    /**
     * Gets the value of the istanzeConErrori property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIstanzeConErrori() {
        return istanzeConErrori;
    }

    /**
     * Sets the value of the istanzeConErrori property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIstanzeConErrori(Integer value) {
        this.istanzeConErrori = value;
    }

    /**
     * Gets the value of the istanzeElaborate property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIstanzeElaborate() {
        return istanzeElaborate;
    }

    /**
     * Sets the value of the istanzeElaborate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIstanzeElaborate(Integer value) {
        this.istanzeElaborate = value;
    }

}
