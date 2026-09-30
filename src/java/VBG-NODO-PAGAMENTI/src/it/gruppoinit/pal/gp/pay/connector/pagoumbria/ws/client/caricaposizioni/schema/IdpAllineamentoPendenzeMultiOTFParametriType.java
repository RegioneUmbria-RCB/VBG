
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdpAllineamentoPendenzeMultiOTFParametriType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdpAllineamentoPendenzeMultiOTFParametriType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AnnullaPagamentiInCorso" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpAllineamentoPendenzeMultiOTFParametriType", propOrder = {
    "annullaPagamentiInCorso"
})
public class IdpAllineamentoPendenzeMultiOTFParametriType {

    @XmlElement(name = "AnnullaPagamentiInCorso")
    protected Boolean annullaPagamentiInCorso;

    /**
     * Recupera il valore della proprietà annullaPagamentiInCorso.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAnnullaPagamentiInCorso() {
        return annullaPagamentiInCorso;
    }

    /**
     * Imposta il valore della proprietà annullaPagamentiInCorso.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAnnullaPagamentiInCorso(Boolean value) {
        this.annullaPagamentiInCorso = value;
    }

}
