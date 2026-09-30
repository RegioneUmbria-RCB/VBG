
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
 *         &lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="iuv"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="17"/&gt;
 *               &lt;maxLength value="18"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ricevutaTelematicaRispostaWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}getRicevutaTelematicaRispostaWs"/&gt;
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
    "codiceErrore",
    "descrizioneErrore",
    "iuv",
    "ricevutaTelematicaRispostaWs"
})
@XmlRootElement(name = "getRicevutaTelematicaByIuvResponse")
public class GetRicevutaTelematicaByIuvResponse {

    @XmlElement(required = true)
    protected String codiceErrore;
    @XmlElement(required = true)
    protected String descrizioneErrore;
    @XmlElement(required = true)
    protected String iuv;
    @XmlElement(required = true)
    protected GetRicevutaTelematicaRispostaWs ricevutaTelematicaRispostaWs;

    /**
     * Recupera il valore della proprietà codiceErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceErrore() {
        return codiceErrore;
    }

    /**
     * Imposta il valore della proprietà codiceErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceErrore(String value) {
        this.codiceErrore = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneErrore() {
        return descrizioneErrore;
    }

    /**
     * Imposta il valore della proprietà descrizioneErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneErrore(String value) {
        this.descrizioneErrore = value;
    }

    /**
     * Recupera il valore della proprietà iuv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIuv() {
        return iuv;
    }

    /**
     * Imposta il valore della proprietà iuv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIuv(String value) {
        this.iuv = value;
    }

    /**
     * Recupera il valore della proprietà ricevutaTelematicaRispostaWs.
     * 
     * @return
     *     possible object is
     *     {@link GetRicevutaTelematicaRispostaWs }
     *     
     */
    public GetRicevutaTelematicaRispostaWs getRicevutaTelematicaRispostaWs() {
        return ricevutaTelematicaRispostaWs;
    }

    /**
     * Imposta il valore della proprietà ricevutaTelematicaRispostaWs.
     * 
     * @param value
     *     allowed object is
     *     {@link GetRicevutaTelematicaRispostaWs }
     *     
     */
    public void setRicevutaTelematicaRispostaWs(GetRicevutaTelematicaRispostaWs value) {
        this.ricevutaTelematicaRispostaWs = value;
    }

}
