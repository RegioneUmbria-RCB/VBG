
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema.EsitoAggiornaPosizioniDebitorieRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema.EsitoInserimentoListaDiCaricoRequest;


/**
 * <p>Classe Java per ResponseType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Result" type="{http://www.csi.it/epay/epaywso/types}ResultType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseType", propOrder = {
    "result"
})
@XmlSeeAlso({
    EsitoAggiornaPosizioniDebitorieRequest.class,
    EsitoInserimentoListaDiCaricoRequest.class
})
public class ResponseType {

    @XmlElement(name = "Result", required = true)
    protected ResultType result;

    /**
     * Recupera il valore della proprietà result.
     * 
     * @return
     *     possible object is
     *     {@link ResultType }
     *     
     */
    public ResultType getResult() {
        return result;
    }

    /**
     * Imposta il valore della proprietà result.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultType }
     *     
     */
    public void setResult(ResultType value) {
        this.result = value;
    }

}
