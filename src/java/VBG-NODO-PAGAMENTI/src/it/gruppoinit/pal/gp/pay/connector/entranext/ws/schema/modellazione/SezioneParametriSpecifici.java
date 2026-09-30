
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SezioneParametriSpecifici complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SezioneParametriSpecifici"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ParametriSpecifici" type="{http://entranext.it/}ArrayOfParametroSpecifico" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Sezione" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SezioneParametriSpecifici", propOrder = {
    "parametriSpecifici"
})
public class SezioneParametriSpecifici {

    @XmlElement(name = "ParametriSpecifici")
    protected ArrayOfParametroSpecifico parametriSpecifici;
    @XmlAttribute(name = "Sezione")
    protected String sezione;

    /**
     * Recupera il valore della proprietà parametriSpecifici.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParametroSpecifico }
     *     
     */
    public ArrayOfParametroSpecifico getParametriSpecifici() {
        return parametriSpecifici;
    }

    /**
     * Imposta il valore della proprietà parametriSpecifici.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParametroSpecifico }
     *     
     */
    public void setParametriSpecifici(ArrayOfParametroSpecifico value) {
        this.parametriSpecifici = value;
    }

    /**
     * Recupera il valore della proprietà sezione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Imposta il valore della proprietà sezione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSezione(String value) {
        this.sezione = value;
    }

}
