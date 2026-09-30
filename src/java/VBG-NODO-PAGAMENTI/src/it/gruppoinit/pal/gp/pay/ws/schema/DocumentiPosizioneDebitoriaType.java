
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}PayRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="riferimentoPosizione" type="{http://www.paevolution.com/ws/pagamenti_types/}RiferimentoPosizioneDebitoriaType"/&gt;
 *         &lt;element name="tipoDocumento" type="{http://www.paevolution.com/ws/pagamenti_types/}TipoDocumentoType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "riferimentoPosizione",
    "tipoDocumento"
})
@XmlRootElement(name = "DocumentiPosizioneDebitoriaType")
public class DocumentiPosizioneDebitoriaType
    extends PayRequestType
{

    @XmlElement(required = true)
    protected RiferimentoPosizioneDebitoriaType riferimentoPosizione;
    @XmlSchemaType(name = "string")
    protected TipoDocumentoType tipoDocumento;

    /**
     * Recupera il valore della proprietà riferimentoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoPosizioneDebitoriaType }
     *     
     */
    public RiferimentoPosizioneDebitoriaType getRiferimentoPosizione() {
        return riferimentoPosizione;
    }

    /**
     * Imposta il valore della proprietà riferimentoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoPosizioneDebitoriaType }
     *     
     */
    public void setRiferimentoPosizione(RiferimentoPosizioneDebitoriaType value) {
        this.riferimentoPosizione = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoType }
     *     
     */
    public TipoDocumentoType getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Imposta il valore della proprietà tipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumentoType }
     *     
     */
    public void setTipoDocumento(TipoDocumentoType value) {
        this.tipoDocumento = value;
    }

}
