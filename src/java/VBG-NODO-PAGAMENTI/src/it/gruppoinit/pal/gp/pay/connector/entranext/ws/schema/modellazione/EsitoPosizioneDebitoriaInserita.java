
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EsitoPosizioneDebitoriaInserita complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EsitoPosizioneDebitoriaInserita"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IUVSoluzioneUnica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Rate" type="{http://entranext.it/}ArrayOfEsitoPosizioneDebitoriaInserita_Rate" minOccurs="0"/&gt;
 *         &lt;element name="TipoDocumentoPagamento" type="{http://entranext.it/}TipiDocumentoPagamento" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoPosizioneDebitoriaInserita", propOrder = {
    "riferimentoPraticaEsterna",
    "iuvSoluzioneUnica",
    "rate",
    "tipoDocumentoPagamento"
})
public class EsitoPosizioneDebitoriaInserita {

    @XmlElement(name = "RiferimentoPraticaEsterna")
    protected String riferimentoPraticaEsterna;
    @XmlElement(name = "IUVSoluzioneUnica")
    protected String iuvSoluzioneUnica;
    @XmlElement(name = "Rate")
    protected ArrayOfEsitoPosizioneDebitoriaInseritaRate rate;
    @XmlElement(name = "TipoDocumentoPagamento")
    @XmlSchemaType(name = "string")
    protected TipiDocumentoPagamento tipoDocumentoPagamento;

    /**
     * Recupera il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPraticaEsterna() {
        return riferimentoPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPraticaEsterna(String value) {
        this.riferimentoPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà iuvSoluzioneUnica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUVSoluzioneUnica() {
        return iuvSoluzioneUnica;
    }

    /**
     * Imposta il valore della proprietà iuvSoluzioneUnica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUVSoluzioneUnica(String value) {
        this.iuvSoluzioneUnica = value;
    }

    /**
     * Recupera il valore della proprietà rate.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaInseritaRate }
     *     
     */
    public ArrayOfEsitoPosizioneDebitoriaInseritaRate getRate() {
        return rate;
    }

    /**
     * Imposta il valore della proprietà rate.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEsitoPosizioneDebitoriaInseritaRate }
     *     
     */
    public void setRate(ArrayOfEsitoPosizioneDebitoriaInseritaRate value) {
        this.rate = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocumentoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link TipiDocumentoPagamento }
     *     
     */
    public TipiDocumentoPagamento getTipoDocumentoPagamento() {
        return tipoDocumentoPagamento;
    }

    /**
     * Imposta il valore della proprietà tipoDocumentoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiDocumentoPagamento }
     *     
     */
    public void setTipoDocumentoPagamento(TipiDocumentoPagamento value) {
        this.tipoDocumentoPagamento = value;
    }

}
