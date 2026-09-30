
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="salvaRavvedimento" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="elencoPagamentiRequest" type="{it/lineacomune/pagopa/be/ws/endpoint/public}elencoPagamentiRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codiceEnte",
    "salvaRavvedimento",
    "elencoPagamentiRequest"
})
@XmlRootElement(name = "getRavvedimentoOperosoRequest")
public class GetRavvedimentoOperosoRequest {

    @XmlElement(required = true)
    protected String codiceEnte;
    protected boolean salvaRavvedimento;
    @XmlElement(required = true)
    protected ElencoPagamentiRequest elencoPagamentiRequest;

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Recupera il valore della proprietà salvaRavvedimento.
     * 
     */
    public boolean isSalvaRavvedimento() {
        return salvaRavvedimento;
    }

    /**
     * Imposta il valore della proprietà salvaRavvedimento.
     * 
     */
    public void setSalvaRavvedimento(boolean value) {
        this.salvaRavvedimento = value;
    }

    /**
     * Recupera il valore della proprietà elencoPagamentiRequest.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPagamentiRequest }
     *     
     */
    public ElencoPagamentiRequest getElencoPagamentiRequest() {
        return elencoPagamentiRequest;
    }

    /**
     * Imposta il valore della proprietà elencoPagamentiRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPagamentiRequest }
     *     
     */
    public void setElencoPagamentiRequest(ElencoPagamentiRequest value) {
        this.elencoPagamentiRequest = value;
    }

}
