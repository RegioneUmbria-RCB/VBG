
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VociDiCosto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VociDiCosto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_VOCE_DI_COSTO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AliquotaIva" type="{http://entranext.it/}IVA"/&gt;
 *         &lt;element name="DescrizioneAliquota" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Entrata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Servizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Sottoservizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VoceDiCosto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NomeVoceDiCosto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VociDiCosto", propOrder = {
    "idvocedicosto",
    "aliquotaIva",
    "descrizioneAliquota",
    "entrata",
    "servizio",
    "sottoservizio",
    "voceDiCosto",
    "nomeVoceDiCosto"
})
public class VociDiCosto {

    @XmlElement(name = "ID_VOCE_DI_COSTO")
    protected int idvocedicosto;
    @XmlElement(name = "AliquotaIva", required = true)
    @XmlSchemaType(name = "string")
    protected IVA aliquotaIva;
    @XmlElement(name = "DescrizioneAliquota")
    protected String descrizioneAliquota;
    @XmlElement(name = "Entrata")
    protected String entrata;
    @XmlElement(name = "Servizio")
    protected String servizio;
    @XmlElement(name = "Sottoservizio")
    protected String sottoservizio;
    @XmlElement(name = "VoceDiCosto")
    protected String voceDiCosto;
    @XmlElement(name = "NomeVoceDiCosto")
    protected String nomeVoceDiCosto;

    /**
     * Recupera il valore della proprietà idvocedicosto.
     * 
     */
    public int getIDVOCEDICOSTO() {
        return idvocedicosto;
    }

    /**
     * Imposta il valore della proprietà idvocedicosto.
     * 
     */
    public void setIDVOCEDICOSTO(int value) {
        this.idvocedicosto = value;
    }

    /**
     * Recupera il valore della proprietà aliquotaIva.
     * 
     * @return
     *     possible object is
     *     {@link IVA }
     *     
     */
    public IVA getAliquotaIva() {
        return aliquotaIva;
    }

    /**
     * Imposta il valore della proprietà aliquotaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link IVA }
     *     
     */
    public void setAliquotaIva(IVA value) {
        this.aliquotaIva = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneAliquota.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAliquota() {
        return descrizioneAliquota;
    }

    /**
     * Imposta il valore della proprietà descrizioneAliquota.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAliquota(String value) {
        this.descrizioneAliquota = value;
    }

    /**
     * Recupera il valore della proprietà entrata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntrata() {
        return entrata;
    }

    /**
     * Imposta il valore della proprietà entrata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntrata(String value) {
        this.entrata = value;
    }

    /**
     * Recupera il valore della proprietà servizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServizio() {
        return servizio;
    }

    /**
     * Imposta il valore della proprietà servizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServizio(String value) {
        this.servizio = value;
    }

    /**
     * Recupera il valore della proprietà sottoservizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSottoservizio() {
        return sottoservizio;
    }

    /**
     * Imposta il valore della proprietà sottoservizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSottoservizio(String value) {
        this.sottoservizio = value;
    }

    /**
     * Recupera il valore della proprietà voceDiCosto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoceDiCosto() {
        return voceDiCosto;
    }

    /**
     * Imposta il valore della proprietà voceDiCosto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoceDiCosto(String value) {
        this.voceDiCosto = value;
    }

    /**
     * Recupera il valore della proprietà nomeVoceDiCosto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeVoceDiCosto() {
        return nomeVoceDiCosto;
    }

    /**
     * Imposta il valore della proprietà nomeVoceDiCosto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeVoceDiCosto(String value) {
        this.nomeVoceDiCosto = value;
    }

}
