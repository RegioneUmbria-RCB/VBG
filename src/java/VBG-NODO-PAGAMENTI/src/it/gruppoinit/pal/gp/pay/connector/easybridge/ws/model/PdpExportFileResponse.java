
package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="pdpExportFileResult" type="{http://easybridge.eu/bridge/}Output" minOccurs="0"/&gt;
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
    "pdpExportFileResult"
})
@XmlRootElement(name = "pdpExportFileResponse")
public class PdpExportFileResponse {

    protected Output pdpExportFileResult;

    /**
     * Recupera il valore della proprietà pdpExportFileResult.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getPdpExportFileResult() {
        return pdpExportFileResult;
    }

    /**
     * Imposta il valore della proprietà pdpExportFileResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setPdpExportFileResult(Output value) {
        this.pdpExportFileResult = value;
    }

}
