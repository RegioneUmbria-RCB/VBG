
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AggiornaPosizioneICPRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AggiornaPosizioneICPRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TipoChiaveApplicativa" type="{http://entranext.it/}TipoChiaveApplicativa"/&gt;
 *         &lt;element name="ChiaveApplicativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PosizioniDebitoria" type="{http://entranext.it/}PosizioneDebitoriaICP" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggiornaPosizioneICPRequest", propOrder = {
    "tipoChiaveApplicativa",
    "chiaveApplicativa",
    "posizioniDebitoria"
})
public class AggiornaPosizioneICPRequest
    extends LinkNextRequest
{

    @XmlElement(name = "TipoChiaveApplicativa", required = true)
    @XmlSchemaType(name = "string")
    protected TipoChiaveApplicativa tipoChiaveApplicativa;
    @XmlElement(name = "ChiaveApplicativa")
    protected String chiaveApplicativa;
    @XmlElement(name = "PosizioniDebitoria")
    protected PosizioneDebitoriaICP posizioniDebitoria;

    /**
     * Recupera il valore della proprietà tipoChiaveApplicativa.
     * 
     * @return
     *     possible object is
     *     {@link TipoChiaveApplicativa }
     *     
     */
    public TipoChiaveApplicativa getTipoChiaveApplicativa() {
        return tipoChiaveApplicativa;
    }

    /**
     * Imposta il valore della proprietà tipoChiaveApplicativa.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoChiaveApplicativa }
     *     
     */
    public void setTipoChiaveApplicativa(TipoChiaveApplicativa value) {
        this.tipoChiaveApplicativa = value;
    }

    /**
     * Recupera il valore della proprietà chiaveApplicativa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChiaveApplicativa() {
        return chiaveApplicativa;
    }

    /**
     * Imposta il valore della proprietà chiaveApplicativa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChiaveApplicativa(String value) {
        this.chiaveApplicativa = value;
    }

    /**
     * Recupera il valore della proprietà posizioniDebitoria.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneDebitoriaICP }
     *     
     */
    public PosizioneDebitoriaICP getPosizioniDebitoria() {
        return posizioniDebitoria;
    }

    /**
     * Imposta il valore della proprietà posizioniDebitoria.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneDebitoriaICP }
     *     
     */
    public void setPosizioniDebitoria(PosizioneDebitoriaICP value) {
        this.posizioniDebitoria = value;
    }

}
