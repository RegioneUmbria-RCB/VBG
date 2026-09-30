
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.RicevutaTelematicaWs;


/**
 * Propriet� della Ricevuta
 *         Telematica associata al Pagamento Atteso
 *       
 * 
 * <p>Classe Java per getRicevutaTelematicaRispostaWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getRicevutaTelematicaRispostaWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ricevutaTelematicaWs" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}ricevutaTelematicaWs"/&gt;
 *         &lt;element name="pdfRicevutaTelematicaWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}pdfRicevutaTelematicaWs" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRicevutaTelematicaRispostaWs", propOrder = {
    "ricevutaTelematicaWs",
    "pdfRicevutaTelematicaWs"
})
public class GetRicevutaTelematicaRispostaWs {

    @XmlElement(required = true)
    protected RicevutaTelematicaWs ricevutaTelematicaWs;
    protected PdfRicevutaTelematicaWs pdfRicevutaTelematicaWs;

    /**
     * Recupera il valore della proprietà ricevutaTelematicaWs.
     * 
     * @return
     *     possible object is
     *     {@link RicevutaTelematicaWs }
     *     
     */
    public RicevutaTelematicaWs getRicevutaTelematicaWs() {
        return ricevutaTelematicaWs;
    }

    /**
     * Imposta il valore della proprietà ricevutaTelematicaWs.
     * 
     * @param value
     *     allowed object is
     *     {@link RicevutaTelematicaWs }
     *     
     */
    public void setRicevutaTelematicaWs(RicevutaTelematicaWs value) {
        this.ricevutaTelematicaWs = value;
    }

    /**
     * Recupera il valore della proprietà pdfRicevutaTelematicaWs.
     * 
     * @return
     *     possible object is
     *     {@link PdfRicevutaTelematicaWs }
     *     
     */
    public PdfRicevutaTelematicaWs getPdfRicevutaTelematicaWs() {
        return pdfRicevutaTelematicaWs;
    }

    /**
     * Imposta il valore della proprietà pdfRicevutaTelematicaWs.
     * 
     * @param value
     *     allowed object is
     *     {@link PdfRicevutaTelematicaWs }
     *     
     */
    public void setPdfRicevutaTelematicaWs(PdfRicevutaTelematicaWs value) {
        this.pdfRicevutaTelematicaWs = value;
    }

}
