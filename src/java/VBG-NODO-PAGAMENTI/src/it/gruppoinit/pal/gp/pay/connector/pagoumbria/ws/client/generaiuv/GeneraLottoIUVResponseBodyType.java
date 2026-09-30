
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GeneraLottoIUVResponseBodyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GeneraLottoIUVResponseBodyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentificativoDominio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TipoDebito" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DimensioneLotto" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ElencoIdentificativi" type="{http://idp.tasgroup.it/GenerazioneIUV/}ElencoIdentificativiType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraLottoIUVResponseBodyType", propOrder = {
    "identificativoDominio",
    "tipoDebito",
    "dimensioneLotto",
    "elencoIdentificativi"
})
public class GeneraLottoIUVResponseBodyType {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlElement(name = "DimensioneLotto")
    protected int dimensioneLotto;
    @XmlElement(name = "ElencoIdentificativi", required = true)
    protected List<ElencoIdentificativiType> elencoIdentificativi;

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
     * Recupera il valore della proprietà dimensioneLotto.
     * 
     */
    public int getDimensioneLotto() {
        return dimensioneLotto;
    }

    /**
     * Imposta il valore della proprietà dimensioneLotto.
     * 
     */
    public void setDimensioneLotto(int value) {
        this.dimensioneLotto = value;
    }

    /**
     * Gets the value of the elencoIdentificativi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elencoIdentificativi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElencoIdentificativi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElencoIdentificativiType }
     * 
     * 
     */
    public List<ElencoIdentificativiType> getElencoIdentificativi() {
        if (elencoIdentificativi == null) {
            elencoIdentificativi = new ArrayList<ElencoIdentificativiType>();
        }
        return this.elencoIdentificativi;
    }

}
