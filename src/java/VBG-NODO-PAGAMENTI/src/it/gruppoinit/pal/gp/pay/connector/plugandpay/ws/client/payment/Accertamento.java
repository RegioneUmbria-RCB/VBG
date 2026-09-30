
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Accertamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Accertamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Codice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ImportoInCentesimi" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="PeriodoDiRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Accertamento", propOrder = {
    "codice",
    "descrizione",
    "importoInCentesimi",
    "periodoDiRiferimento"
})
public class Accertamento {

    @XmlElement(name = "Codice", required = true, nillable = true)
    protected String codice;
    @XmlElement(name = "Descrizione", required = true, nillable = true)
    protected String descrizione;
    @XmlElement(name = "ImportoInCentesimi")
    protected long importoInCentesimi;
    @XmlElement(name = "PeriodoDiRiferimento", required = true, nillable = true)
    protected String periodoDiRiferimento;

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
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
     * Recupera il valore della proprietà importoInCentesimi.
     * 
     */
    public long getImportoInCentesimi() {
        return importoInCentesimi;
    }

    /**
     * Imposta il valore della proprietà importoInCentesimi.
     * 
     */
    public void setImportoInCentesimi(long value) {
        this.importoInCentesimi = value;
    }

    /**
     * Recupera il valore della proprietà periodoDiRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriodoDiRiferimento() {
        return periodoDiRiferimento;
    }

    /**
     * Imposta il valore della proprietà periodoDiRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriodoDiRiferimento(String value) {
        this.periodoDiRiferimento = value;
    }

}
