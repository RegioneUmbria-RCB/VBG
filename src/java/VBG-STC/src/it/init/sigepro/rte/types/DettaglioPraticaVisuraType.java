
package it.init.sigepro.rte.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DettaglioPraticaVisuraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioPraticaVisuraType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dettaglioPratica" type="{http://sigepro.init.it/rte/types}DettaglioPraticaType"/>
 *         &lt;element name="listaAttivita" type="{http://sigepro.init.it/rte/types}DettaglioAttivitaType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="statoPratica" type="{http://sigepro.init.it/rte/types}StatoPraticaType"/>
 *         &lt;element name="statoIter" type="{http://sigepro.init.it/rte/types}StatoIterType" minOccurs="0"/>
 *         &lt;element name="passwordMD5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="responsabileProcedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="istruttorePratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listaAtti" type="{http://sigepro.init.it/rte/types}EstremiAttoEstesoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tempisticaProcedimento" type="{http://sigepro.init.it/rte/types}TempisticaProcedimentoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioPraticaVisuraType", propOrder = {
    "dettaglioPratica",
    "listaAttivita",
    "statoPratica",
    "statoIter",
    "passwordMD5",
    "responsabileProcedimento",
    "istruttorePratica",
    "listaAtti",
    "tempisticaProcedimento"
})
public class DettaglioPraticaVisuraType {

    @XmlElement(required = true)
    protected DettaglioPraticaType dettaglioPratica;
    protected List<DettaglioAttivitaType> listaAttivita;
    @XmlElement(required = true)
    protected StatoPraticaType statoPratica;
    protected StatoIterType statoIter;
    protected String passwordMD5;
    protected String responsabileProcedimento;
    protected String istruttorePratica;
    protected List<EstremiAttoEstesoType> listaAtti;
    protected TempisticaProcedimentoType tempisticaProcedimento;

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

    /**
     * Gets the value of the listaAttivita property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaAttivita property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaAttivita().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DettaglioAttivitaType }
     * 
     * 
     */
    public List<DettaglioAttivitaType> getListaAttivita() {
        if (listaAttivita == null) {
            listaAttivita = new ArrayList<DettaglioAttivitaType>();
        }
        return this.listaAttivita;
    }

    /**
     * Gets the value of the statoPratica property.
     * 
     * @return
     *     possible object is
     *     {@link StatoPraticaType }
     *     
     */
    public StatoPraticaType getStatoPratica() {
        return statoPratica;
    }

    /**
     * Sets the value of the statoPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPraticaType }
     *     
     */
    public void setStatoPratica(StatoPraticaType value) {
        this.statoPratica = value;
    }

    /**
     * Gets the value of the statoIter property.
     * 
     * @return
     *     possible object is
     *     {@link StatoIterType }
     *     
     */
    public StatoIterType getStatoIter() {
        return statoIter;
    }

    /**
     * Sets the value of the statoIter property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoIterType }
     *     
     */
    public void setStatoIter(StatoIterType value) {
        this.statoIter = value;
    }

    /**
     * Gets the value of the passwordMD5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPasswordMD5() {
        return passwordMD5;
    }

    /**
     * Sets the value of the passwordMD5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPasswordMD5(String value) {
        this.passwordMD5 = value;
    }

    /**
     * Gets the value of the responsabileProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    /**
     * Sets the value of the responsabileProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsabileProcedimento(String value) {
        this.responsabileProcedimento = value;
    }

    /**
     * Gets the value of the istruttorePratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstruttorePratica() {
        return istruttorePratica;
    }

    /**
     * Sets the value of the istruttorePratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstruttorePratica(String value) {
        this.istruttorePratica = value;
    }

    /**
     * Gets the value of the listaAtti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaAtti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaAtti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EstremiAttoEstesoType }
     * 
     * 
     */
    public List<EstremiAttoEstesoType> getListaAtti() {
        if (listaAtti == null) {
            listaAtti = new ArrayList<EstremiAttoEstesoType>();
        }
        return this.listaAtti;
    }

    /**
     * Gets the value of the tempisticaProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link TempisticaProcedimentoType }
     *     
     */
    public TempisticaProcedimentoType getTempisticaProcedimento() {
        return tempisticaProcedimento;
    }

    /**
     * Sets the value of the tempisticaProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TempisticaProcedimentoType }
     *     
     */
    public void setTempisticaProcedimento(TempisticaProcedimentoType value) {
        this.tempisticaProcedimento = value;
    }

}
