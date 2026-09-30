
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaMotiviAnnullamentoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaMotiviAnnullamentoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MotivoAnnullamento" type="{http://it.gruppoinit/Protocollazione}ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://it.gruppoinit/Protocollazione}ErroreProtocolloType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaMotiviAnnullamentoResponseType", propOrder = {
    "motivoAnnullamento",
    "errore"
})
public class ListaMotiviAnnullamentoResponseType {

    @XmlElement(name = "MotivoAnnullamento", nillable = true)
    protected ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType motivoAnnullamento;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;

    /**
     * Gets the value of the motivoAnnullamento property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType }
     *     
     */
    public ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType getMotivoAnnullamento() {
        return motivoAnnullamento;
    }

    /**
     * Sets the value of the motivoAnnullamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType }
     *     
     */
    public void setMotivoAnnullamento(ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType value) {
        this.motivoAnnullamento = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public ErroreProtocolloType getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public void setErrore(ErroreProtocolloType value) {
        this.errore = value;
    }

}
