
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioSgravio complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioSgravio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoDettaglio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioSgravio", propOrder = {
    "riferimentoDettaglio",
    "importo"
})
public class DettaglioSgravio {

    @XmlElement(name = "RiferimentoDettaglio")
    protected int riferimentoDettaglio;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;

    /**
     * Recupera il valore della proprietà riferimentoDettaglio.
     * 
     */
    public int getRiferimentoDettaglio() {
        return riferimentoDettaglio;
    }

    /**
     * Imposta il valore della proprietà riferimentoDettaglio.
     * 
     */
    public void setRiferimentoDettaglio(int value) {
        this.riferimentoDettaglio = value;
    }

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

}
