
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generaiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GeneraIUVResponseBodyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GeneraIUVResponseBodyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentificativoDominio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TipoDebito" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ElencoIdentificativi" type="{http://idp.tasgroup.it/GenerazioneIUV/}ElencoIdentificativiType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraIUVResponseBodyType", propOrder = {
    "identificativoDominio",
    "tipoDebito",
    "elencoIdentificativi"
})
public class GeneraIUVResponseBodyType {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlElement(name = "ElencoIdentificativi", required = true)
    protected ElencoIdentificativiType elencoIdentificativi;

    /**
     * Recupera il valore della proprietà identificativoDominio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDominio() {
        return identificativoDominio;
    }

    /**
     * Imposta il valore della proprietà identificativoDominio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDominio(String value) {
        this.identificativoDominio = value;
    }

    /**
     * Recupera il valore della proprietà tipoDebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDebito() {
        return tipoDebito;
    }

    /**
     * Imposta il valore della proprietà tipoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDebito(String value) {
        this.tipoDebito = value;
    }

    /**
     * Recupera il valore della proprietà elencoIdentificativi.
     * 
     * @return
     *     possible object is
     *     {@link ElencoIdentificativiType }
     *     
     */
    public ElencoIdentificativiType getElencoIdentificativi() {
        return elencoIdentificativi;
    }

    /**
     * Imposta il valore della proprietà elencoIdentificativi.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoIdentificativiType }
     *     
     */
    public void setElencoIdentificativi(ElencoIdentificativiType value) {
        this.elencoIdentificativi = value;
    }

}
