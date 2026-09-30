
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PosizioneDebitoria_RiepilogoIva complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoria_RiepilogoIva"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AliquotaIva" type="{http://entranext.it/}IVA"/&gt;
 *         &lt;element name="BaseImponibile" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Iva" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Dovuto" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoria_RiepilogoIva", propOrder = {
    "aliquotaIva",
    "baseImponibile",
    "iva",
    "dovuto"
})
public class PosizioneDebitoriaRiepilogoIva {

    @XmlElement(name = "AliquotaIva", required = true)
    @XmlSchemaType(name = "string")
    protected IVA aliquotaIva;
    @XmlElement(name = "BaseImponibile", required = true)
    protected BigDecimal baseImponibile;
    @XmlElement(name = "Iva", required = true)
    protected BigDecimal iva;
    @XmlElement(name = "Dovuto", required = true)
    protected BigDecimal dovuto;

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
     * Recupera il valore della proprietà baseImponibile.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseImponibile() {
        return baseImponibile;
    }

    /**
     * Imposta il valore della proprietà baseImponibile.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseImponibile(BigDecimal value) {
        this.baseImponibile = value;
    }

    /**
     * Recupera il valore della proprietà iva.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIva() {
        return iva;
    }

    /**
     * Imposta il valore della proprietà iva.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIva(BigDecimal value) {
        this.iva = value;
    }

    /**
     * Recupera il valore della proprietà dovuto.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDovuto() {
        return dovuto;
    }

    /**
     * Imposta il valore della proprietà dovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDovuto(BigDecimal value) {
        this.dovuto = value;
    }

}
