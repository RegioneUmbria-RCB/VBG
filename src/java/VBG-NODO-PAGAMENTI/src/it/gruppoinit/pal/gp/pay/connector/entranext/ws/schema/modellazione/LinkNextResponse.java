
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per LinkNextResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="LinkNextResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Esito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Dettagli" type="{http://entranext.it/}ArrayOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinkNextResponse", propOrder = {
    "esito",
    "descrizione",
    "dettagli"
})
@XmlSeeAlso({
    LoginResponse.class,
    InserisciPosizioniInAttesaResponse.class,
    InserisciPosizioneResponse.class,
    InserisciRuoloNoteDiCreditoResponse.class,
    InserisciNotaDiCreditoResponse.class,
    InserisciPagamentiResponse.class,
    RiceviEsitoTransazioneResponse.class,
    ScaricaDocumentoPDFResponse.class,
    AggiornaPosizioneResponse.class,
    VerificaPosizioneResponse.class,
    RiceviSottoServiziResponse.class,
    RiceviVociDiCostoResponse.class,
    RiceviPosizioniDebitorieResponse.class,
    RiceviRendicontazionePagamentiResponse.class,
    ScaricaDocumentiPDFRuoloResponse.class,
    VerificaStatoRuoloResponse.class,
    RiceviRuoloIUVResponse.class,
    AnnullaPosizioneDebitoriaResponse.class,
    ValidaPosizioneDebitoriaResponse.class,
    ValidaNotaDiCreditoResponse.class,
    ValidaRuoloPosizioniDebitorieResponse.class,
    AnnullaRuoloPosizioniResponse.class,
    ApprovaRuoloPosizioniResponse.class,
    ScaricaPagamentiRTPosizioniDebitorieResponse.class,
    ScaricaPagamentoRTResponse.class,
    InserisciSgravioPosizioneResponse.class,
    InserisciPosizioniInAttesaCAResponse.class,
    VersioneResponse.class,
    InserisciRuoloPosizioniResponseBase.class
})
public class LinkNextResponse {

    @XmlElement(name = "Esito")
    protected String esito;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "Dettagli")
    protected ArrayOfString dettagli;

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà dettagli.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getDettagli() {
        return dettagli;
    }

    /**
     * Imposta il valore della proprietà dettagli.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setDettagli(ArrayOfString value) {
        this.dettagli = value;
    }

}
