
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RichiestaCreaAvvisoPdf complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaCreaAvvisoPdf"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/GeneratorPdf}GeneratorPdfBytesAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceEnteCreditore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="identificativoPosizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaCreaAvvisoPdf", propOrder = {
    "codiceEnteCreditore",
    "identificativoPosizione"
})
public class RichiestaCreaAvvisoPdf
    extends GeneratorPdfBytesAuthenticatedRequestBase
{

    @XmlElement(required = true, nillable = true)
    protected String codiceEnteCreditore;
    @XmlElement(required = true, nillable = true)
    protected String identificativoPosizione;

    /**
     * Recupera il valore della proprietà codiceEnteCreditore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnteCreditore() {
        return codiceEnteCreditore;
    }

    /**
     * Imposta il valore della proprietà codiceEnteCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnteCreditore(String value) {
        this.codiceEnteCreditore = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPosizione() {
        return identificativoPosizione;
    }

    /**
     * Imposta il valore della proprietà identificativoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPosizione(String value) {
        this.identificativoPosizione = value;
    }

}
