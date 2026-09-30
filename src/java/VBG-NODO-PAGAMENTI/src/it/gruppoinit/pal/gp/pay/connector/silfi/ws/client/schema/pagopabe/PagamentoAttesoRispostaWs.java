
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Propriet� relative al
 *         Pagamento
 *       
 * 
 * <p>Classe Java per pagamentoAttesoRispostaWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pagamentoAttesoRispostaWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceEsito"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="descrizioneEsito"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="iuv"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="17"/&gt;
 *               &lt;maxLength value="18"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="idBoPagamentoAtteso"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="100"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="partiteDebitorieRispostaWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}partitaDebitoriaRispostaWs" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagamentoAttesoRispostaWs", propOrder = {
    "codiceEsito",
    "descrizioneEsito",
    "iuv",
    "idBoPagamentoAtteso",
    "partiteDebitorieRispostaWs"
})
public class PagamentoAttesoRispostaWs {

    @XmlElement(required = true)
    protected String codiceEsito;
    @XmlElement(required = true)
    protected String descrizioneEsito;
    @XmlElement(required = true)
    protected String iuv;
    @XmlElement(required = true)
    protected String idBoPagamentoAtteso;
    protected List<PartitaDebitoriaRispostaWs> partiteDebitorieRispostaWs;

    /**
     * Recupera il valore della proprietà codiceEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEsito() {
        return codiceEsito;
    }

    /**
     * Imposta il valore della proprietà codiceEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEsito(String value) {
        this.codiceEsito = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneEsito() {
        return descrizioneEsito;
    }

    /**
     * Imposta il valore della proprietà descrizioneEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneEsito(String value) {
        this.descrizioneEsito = value;
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
     * Recupera il valore della proprietà idBoPagamentoAtteso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBoPagamentoAtteso() {
        return idBoPagamentoAtteso;
    }

    /**
     * Imposta il valore della proprietà idBoPagamentoAtteso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBoPagamentoAtteso(String value) {
        this.idBoPagamentoAtteso = value;
    }

    /**
     * Gets the value of the partiteDebitorieRispostaWs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partiteDebitorieRispostaWs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartiteDebitorieRispostaWs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartitaDebitoriaRispostaWs }
     * 
     * 
     */
    public List<PartitaDebitoriaRispostaWs> getPartiteDebitorieRispostaWs() {
        if (partiteDebitorieRispostaWs == null) {
            partiteDebitorieRispostaWs = new ArrayList<PartitaDebitoriaRispostaWs>();
        }
        return this.partiteDebitorieRispostaWs;
    }

}
