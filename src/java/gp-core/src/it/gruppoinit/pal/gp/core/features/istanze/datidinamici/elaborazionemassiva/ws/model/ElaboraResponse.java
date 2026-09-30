
package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ElaboraResult" type="{http://schemas.datacontract.org/2004/07/Sigepro.net.WebServices.WsSIGePro.Wcf.ElaborazioneMassiva}EsitoElaborazioneMassivaSchede" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "elaboraResult"
})
@XmlRootElement(name = "ElaboraResponse")
public class ElaboraResponse {

    @XmlElement(name = "ElaboraResult", namespace = "http://tempuri.org/", required = false)
    protected EsitoElaborazioneMassivaSchede elaboraResult;

    /**
     * Recupera il valore della propriet� elaboraResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EsitoElaborazioneMassivaSchede }{@code >}
     *     
     */
    public EsitoElaborazioneMassivaSchede getElaboraResult() {
        return elaboraResult;
    }

    /**
     * Imposta il valore della propriet� elaboraResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EsitoElaborazioneMassivaSchede }{@code >}
     *     
     */
    public void setElaboraResult(EsitoElaborazioneMassivaSchede value) {
        this.elaboraResult = value;
    }

}
