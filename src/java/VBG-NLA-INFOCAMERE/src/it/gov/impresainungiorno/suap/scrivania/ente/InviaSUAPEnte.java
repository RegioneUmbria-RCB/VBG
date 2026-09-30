
package it.gov.impresainungiorno.suap.scrivania.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;


/**
 * <p>Classe Java per inviaSUAPEnte complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="inviaSUAPEnte"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CooperazioneSUAPEnte" type="{http://www.impresainungiorno.gov.it/schema/suap/ente}CooperazioneSUAPEnte"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inviaSUAPEnte", propOrder = {
    "cooperazioneSUAPEnte"
})
public class InviaSUAPEnte {

    @XmlElement(name = "CooperazioneSUAPEnte", required = true)
    protected CooperazioneSUAPEnte cooperazioneSUAPEnte;

    /**
     * Recupera il valore della proprietà cooperazioneSUAPEnte.
     * 
     * @return
     *     possible object is
     *     {@link CooperazioneSUAPEnte }
     *     
     */
    public CooperazioneSUAPEnte getCooperazioneSUAPEnte() {
        return cooperazioneSUAPEnte;
    }

    /**
     * Imposta il valore della proprietà cooperazioneSUAPEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link CooperazioneSUAPEnte }
     *     
     */
    public void setCooperazioneSUAPEnte(CooperazioneSUAPEnte value) {
        this.cooperazioneSUAPEnte = value;
    }

}
