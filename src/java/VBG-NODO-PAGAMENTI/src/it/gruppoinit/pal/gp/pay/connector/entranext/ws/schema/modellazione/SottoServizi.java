
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SottoServizi complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SottoServizi"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_SOTTOSERVIZIO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Servizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Sottoservizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Entrata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SottoServizi", propOrder = {
    "idsottoservizio",
    "nome",
    "servizio",
    "sottoservizio",
    "entrata"
})
public class SottoServizi {

    @XmlElement(name = "ID_SOTTOSERVIZIO")
    protected int idsottoservizio;
    @XmlElement(name = "Nome")
    protected String nome;
    @XmlElement(name = "Servizio")
    protected String servizio;
    @XmlElement(name = "Sottoservizio")
    protected String sottoservizio;
    @XmlElement(name = "Entrata")
    protected String entrata;

    /**
     * Recupera il valore della proprietà idsottoservizio.
     * 
     */
    public int getIDSOTTOSERVIZIO() {
        return idsottoservizio;
    }

    /**
     * Imposta il valore della proprietà idsottoservizio.
     * 
     */
    public void setIDSOTTOSERVIZIO(int value) {
        this.idsottoservizio = value;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
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

}
