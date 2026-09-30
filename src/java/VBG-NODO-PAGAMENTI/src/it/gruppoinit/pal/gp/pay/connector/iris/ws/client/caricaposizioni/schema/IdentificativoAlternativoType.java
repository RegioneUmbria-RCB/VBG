
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdentificativoAlternativoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdentificativoAlternativoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TipoCodiceIdenficativoAlternativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodiceIdenficativoAlternativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificativoAlternativoType", propOrder = {
    "tipoCodiceIdenficativoAlternativo",
    "codiceIdenficativoAlternativo"
})
public class IdentificativoAlternativoType {

    @XmlElement(name = "TipoCodiceIdenficativoAlternativo", required = true)
    protected String tipoCodiceIdenficativoAlternativo;
    @XmlElement(name = "CodiceIdenficativoAlternativo", required = true)
    protected String codiceIdenficativoAlternativo;

    /**
     * Recupera il valore della proprietà tipoCodiceIdenficativoAlternativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCodiceIdenficativoAlternativo() {
        return tipoCodiceIdenficativoAlternativo;
    }

    /**
     * Imposta il valore della proprietà tipoCodiceIdenficativoAlternativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCodiceIdenficativoAlternativo(String value) {
        this.tipoCodiceIdenficativoAlternativo = value;
    }

    /**
     * Recupera il valore della proprietà codiceIdenficativoAlternativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdenficativoAlternativo() {
        return codiceIdenficativoAlternativo;
    }

    /**
     * Imposta il valore della proprietà codiceIdenficativoAlternativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdenficativoAlternativo(String value) {
        this.codiceIdenficativoAlternativo = value;
    }

}
