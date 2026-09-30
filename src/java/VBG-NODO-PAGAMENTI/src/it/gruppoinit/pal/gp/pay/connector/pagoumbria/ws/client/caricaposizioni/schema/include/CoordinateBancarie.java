
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CoordinateBancarie complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CoordinateBancarie"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceIBAN" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Beneficiario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoordinateBancarie", propOrder = {
    "codiceIBAN",
    "beneficiario"
})
public class CoordinateBancarie {

    @XmlElement(name = "CodiceIBAN", required = true)
    protected String codiceIBAN;
    @XmlElement(name = "Beneficiario", required = true)
    protected String beneficiario;

    /**
     * Recupera il valore della proprietà codiceIBAN.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIBAN() {
        return codiceIBAN;
    }

    /**
     * Imposta il valore della proprietà codiceIBAN.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIBAN(String value) {
        this.codiceIBAN = value;
    }

    /**
     * Recupera il valore della proprietà beneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiario() {
        return beneficiario;
    }

    /**
     * Imposta il valore della proprietà beneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiario(String value) {
        this.beneficiario = value;
    }

}
