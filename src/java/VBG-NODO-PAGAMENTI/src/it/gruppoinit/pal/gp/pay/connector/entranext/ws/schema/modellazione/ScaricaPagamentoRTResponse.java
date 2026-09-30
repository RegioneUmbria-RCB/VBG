
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ScaricaPagamentoRTResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ScaricaPagamentoRTResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Documento" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="DocumentoQuietanza" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScaricaPagamentoRTResponse", propOrder = {
    "documento",
    "documentoQuietanza"
})
public class ScaricaPagamentoRTResponse
    extends LinkNextResponse
{

    @XmlElement(name = "Documento")
    protected byte[] documento;
    @XmlElement(name = "DocumentoQuietanza", required = true, nillable = true)
    protected byte[] documentoQuietanza;

    /**
     * Recupera il valore della proprietà documento.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Imposta il valore della proprietà documento.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumento(byte[] value) {
        this.documento = value;
    }

    /**
     * Recupera il valore della proprietà documentoQuietanza.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumentoQuietanza() {
        return documentoQuietanza;
    }

    /**
     * Imposta il valore della proprietà documentoQuietanza.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumentoQuietanza(byte[] value) {
        this.documentoQuietanza = value;
    }

}
