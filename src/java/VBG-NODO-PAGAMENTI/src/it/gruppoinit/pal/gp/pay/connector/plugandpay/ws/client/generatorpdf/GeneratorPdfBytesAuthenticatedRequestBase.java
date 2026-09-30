
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GeneratorPdfBytesAuthenticatedRequestBase complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GeneratorPdfBytesAuthenticatedRequestBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdApplicazione" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneratorPdfBytesAuthenticatedRequestBase", propOrder = {
    "idApplicazione"
})
@XmlSeeAlso({
    RichiestaCreaAvvisoPdf.class,
    RichiestaCreaRTPdf.class
})
public class GeneratorPdfBytesAuthenticatedRequestBase {

    @XmlElement(name = "IdApplicazione", required = true)
    protected String idApplicazione;

    /**
     * Recupera il valore della proprietà idApplicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdApplicazione() {
        return idApplicazione;
    }

    /**
     * Imposta il valore della proprietà idApplicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdApplicazione(String value) {
        this.idApplicazione = value;
    }

}
