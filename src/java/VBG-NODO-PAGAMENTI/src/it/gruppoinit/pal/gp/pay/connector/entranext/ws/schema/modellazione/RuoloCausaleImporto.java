
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Ruolo_CausaleImporto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Ruolo_CausaleImporto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CausaleImporto" type="{http://entranext.it/}CausaliImporti"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "Ruolo_CausaleImporto", propOrder = {
    "causaleImporto",
    "descrizione",
    "importo"
})
public class RuoloCausaleImporto {

    @XmlElement(name = "CausaleImporto", required = true)
    @XmlSchemaType(name = "string")
    protected CausaliImporti causaleImporto;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;

    /**
     * Recupera il valore della proprietà causaleImporto.
     * 
     * @return
     *     possible object is
     *     {@link CausaliImporti }
     *     
     */
    public CausaliImporti getCausaleImporto() {
        return causaleImporto;
    }

    /**
     * Imposta il valore della proprietà causaleImporto.
     * 
     * @param value
     *     allowed object is
     *     {@link CausaliImporti }
     *     
     */
    public void setCausaleImporto(CausaliImporti value) {
        this.causaleImporto = value;
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
