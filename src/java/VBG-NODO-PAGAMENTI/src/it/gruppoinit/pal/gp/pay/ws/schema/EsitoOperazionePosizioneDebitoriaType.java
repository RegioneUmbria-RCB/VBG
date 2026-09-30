
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EsitoOperazionePosizioneDebitoriaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EsitoOperazionePosizioneDebitoriaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}RiferimentoPosizioneDebitoriaType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codice_errore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="stato" type="{http://www.paevolution.com/ws/pagamenti_types/}StatoPagamentoType"/&gt;
 *         &lt;element name="errore_temporaneo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoOperazionePosizioneDebitoriaType", propOrder = {
    "messaggio",
    "codiceErrore",
    "esito",
    "stato",
    "erroreTemporaneo"
})
@XmlSeeAlso({
    StatoPosizioneType.class,
    EsitoDocumentoPosizioneDebitoriaType.class
})
public class EsitoOperazionePosizioneDebitoriaType
    extends RiferimentoPosizioneDebitoriaType
{

    protected String messaggio;
    @XmlElement(name = "codice_errore")
    protected String codiceErrore;
    protected boolean esito;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected StatoPagamentoType stato;
    @XmlElement(name = "errore_temporaneo")
    protected Boolean erroreTemporaneo = Boolean.FALSE;

    /**
     * Recupera il valore della proprietà messaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il valore della proprietà messaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggio(String value) {
        this.messaggio = value;
    }

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
     * Recupera il valore della proprietà esito.
     * 
     */
    public boolean isEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     */
    public void setEsito(boolean value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link StatoPagamentoType }
     *     
     */
    public StatoPagamentoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPagamentoType }
     *     
     */
    public void setStato(StatoPagamentoType value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della proprietà erroreTemporaneo.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isErroreTemporaneo() {
        return erroreTemporaneo;
    }

    /**
     * Imposta il valore della proprietà erroreTemporaneo.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setErroreTemporaneo(Boolean value) {
        this.erroreTemporaneo = value;
    }

}
