
package it.gruppoinit.impresainungiorno.schema.suap.ri.iscrizione;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.AttivitaISTAT;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.IndirizzoConRecapiti;


/**
 * <p>Java class for UnitaLocale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitaLocale">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti">
 *       &lt;sequence>
 *         &lt;element name="attivita" type="{http://www.impresainungiorno.gov.it/schema/base}AttivitaISTAT" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tipo-localizzazione" type="{http://www.impresainungiorno.gov.it/schema/suap/ri/spc}TipoLocalizzazione" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="progressivo" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitaLocale", propOrder = {
    "attivita",
    "tipoLocalizzazione"
})
public class UnitaLocale
    extends IndirizzoConRecapiti
{

    protected List<AttivitaISTAT> attivita;
    @XmlElement(name = "tipo-localizzazione")
    protected List<TipoLocalizzazione> tipoLocalizzazione;
    @XmlAttribute(name = "progressivo", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger progressivo;

    /**
     * Gets the value of the attivita property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attivita property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttivita().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttivitaISTAT }
     * 
     * 
     */
    public List<AttivitaISTAT> getAttivita() {
        if (attivita == null) {
            attivita = new ArrayList<AttivitaISTAT>();
        }
        return this.attivita;
    }

    /**
     * Gets the value of the tipoLocalizzazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipoLocalizzazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipoLocalizzazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoLocalizzazione }
     * 
     * 
     */
    public List<TipoLocalizzazione> getTipoLocalizzazione() {
        if (tipoLocalizzazione == null) {
            tipoLocalizzazione = new ArrayList<TipoLocalizzazione>();
        }
        return this.tipoLocalizzazione;
    }

    /**
     * Gets the value of the progressivo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProgressivo() {
        return progressivo;
    }

    /**
     * Sets the value of the progressivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProgressivo(BigInteger value) {
        this.progressivo = value;
    }

}
