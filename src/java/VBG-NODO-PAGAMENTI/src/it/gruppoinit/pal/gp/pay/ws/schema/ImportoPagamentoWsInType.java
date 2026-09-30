
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ImportoPagamentoWsInType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ImportoPagamentoWsInType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="importo" type="{http://www.paevolution.com/ws/pagamenti_types/}ImportoType"/&gt;
 *         &lt;element name="codiceMappatura" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportoPagamentoWsInType", propOrder = {
    "importo",
    "codiceMappatura"
})
public class ImportoPagamentoWsInType {

    @XmlElement(required = true)
    protected BigDecimal importo;
    @XmlElement(required = true)
    protected String codiceMappatura;

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà codiceMappatura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceMappatura() {
        return codiceMappatura;
    }

    /**
     * Imposta il valore della proprietà codiceMappatura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceMappatura(String value) {
        this.codiceMappatura = value;
    }

}
