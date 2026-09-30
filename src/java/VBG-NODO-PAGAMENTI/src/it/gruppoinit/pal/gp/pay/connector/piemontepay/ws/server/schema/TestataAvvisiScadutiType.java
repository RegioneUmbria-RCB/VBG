
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TestataAvvisiScadutiType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TestataAvvisiScadutiType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdMessaggio" type="{http://www.csi.it/epay/epaywso/types}String35Type"/&gt;
 *         &lt;element name="CFEnteCreditore" type="{http://www.csi.it/epay/epaywso/types}String35Type"/&gt;
 *         &lt;element name="CodiceVersamento" type="{http://www.csi.it/epay/epaywso/types}CodiceVersamentoType"/&gt;
 *         &lt;element name="NumeroAvvisiScaduti" type="{http://www.csi.it/epay/epaywso/types}Numero6CifreType"/&gt;
 *         &lt;element name="ImportoTotaleAvvisiScaduti" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestataAvvisiScadutiType", propOrder = {
    "idMessaggio",
    "cfEnteCreditore",
    "codiceVersamento",
    "numeroAvvisiScaduti",
    "importoTotaleAvvisiScaduti"
})
public class TestataAvvisiScadutiType {

    @XmlElement(name = "IdMessaggio", required = true)
    protected String idMessaggio;
    @XmlElement(name = "CFEnteCreditore", required = true)
    protected String cfEnteCreditore;
    @XmlElement(name = "CodiceVersamento", required = true)
    protected String codiceVersamento;
    @XmlElement(name = "NumeroAvvisiScaduti")
    @XmlSchemaType(name = "integer")
    protected int numeroAvvisiScaduti;
    @XmlElement(name = "ImportoTotaleAvvisiScaduti", required = true)
    protected BigDecimal importoTotaleAvvisiScaduti;

    /**
     * Recupera il valore della proprietà idMessaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMessaggio() {
        return idMessaggio;
    }

    /**
     * Imposta il valore della proprietà idMessaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMessaggio(String value) {
        this.idMessaggio = value;
    }

    /**
     * Recupera il valore della proprietà cfEnteCreditore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFEnteCreditore() {
        return cfEnteCreditore;
    }

    /**
     * Imposta il valore della proprietà cfEnteCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFEnteCreditore(String value) {
        this.cfEnteCreditore = value;
    }

    /**
     * Recupera il valore della proprietà codiceVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceVersamento() {
        return codiceVersamento;
    }

    /**
     * Imposta il valore della proprietà codiceVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceVersamento(String value) {
        this.codiceVersamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroAvvisiScaduti.
     * 
     */
    public int getNumeroAvvisiScaduti() {
        return numeroAvvisiScaduti;
    }

    /**
     * Imposta il valore della proprietà numeroAvvisiScaduti.
     * 
     */
    public void setNumeroAvvisiScaduti(int value) {
        this.numeroAvvisiScaduti = value;
    }

    /**
     * Recupera il valore della proprietà importoTotaleAvvisiScaduti.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotaleAvvisiScaduti() {
        return importoTotaleAvvisiScaduti;
    }

    /**
     * Imposta il valore della proprietà importoTotaleAvvisiScaduti.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotaleAvvisiScaduti(BigDecimal value) {
        this.importoTotaleAvvisiScaduti = value;
    }

}
