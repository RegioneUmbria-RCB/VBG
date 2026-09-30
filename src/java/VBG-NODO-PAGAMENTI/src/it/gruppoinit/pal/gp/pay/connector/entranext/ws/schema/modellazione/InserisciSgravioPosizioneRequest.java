
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciSgravioPosizioneRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciSgravioPosizioneRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TipoChiaveApplicativa" type="{http://entranext.it/}TipoChiaveApplicativa"/&gt;
 *         &lt;element name="ChiaveApplicativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NumeroMandato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DettagliSgravio" type="{http://entranext.it/}DettaglioSgravio" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciSgravioPosizioneRequest", propOrder = {
    "tipoChiaveApplicativa",
    "chiaveApplicativa",
    "descrizione",
    "numeroMandato",
    "dettagliSgravio"
})
public class InserisciSgravioPosizioneRequest
    extends LinkNextRequest
{

    @XmlElement(name = "TipoChiaveApplicativa", required = true)
    @XmlSchemaType(name = "string")
    protected TipoChiaveApplicativa tipoChiaveApplicativa;
    @XmlElement(name = "ChiaveApplicativa")
    protected String chiaveApplicativa;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "NumeroMandato")
    protected String numeroMandato;
    @XmlElement(name = "DettagliSgravio")
    protected List<DettaglioSgravio> dettagliSgravio;

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
     * Recupera il valore della proprietà numeroMandato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroMandato() {
        return numeroMandato;
    }

    /**
     * Imposta il valore della proprietà numeroMandato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroMandato(String value) {
        this.numeroMandato = value;
    }

    /**
     * Gets the value of the dettagliSgravio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dettagliSgravio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDettagliSgravio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DettaglioSgravio }
     * 
     * 
     */
    public List<DettaglioSgravio> getDettagliSgravio() {
        if (dettagliSgravio == null) {
            dettagliSgravio = new ArrayList<DettaglioSgravio>();
        }
        return this.dettagliSgravio;
    }

}
