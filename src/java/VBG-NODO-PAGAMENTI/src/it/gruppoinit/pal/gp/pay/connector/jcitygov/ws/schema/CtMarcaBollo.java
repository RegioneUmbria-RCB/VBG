//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctMarcaBollo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctMarcaBollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HashDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProvinciaResidenzaSoggettoPagatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctMarcaBollo", propOrder = {
    "hashDocumento",
    "provinciaResidenzaSoggettoPagatore"
})
public class CtMarcaBollo {

    @XmlElement(name = "HashDocumento", required = true)
    protected String hashDocumento;
    @XmlElement(name = "ProvinciaResidenzaSoggettoPagatore", required = true)
    protected String provinciaResidenzaSoggettoPagatore;

    /**
     * Recupera il valore della proprietà hashDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashDocumento() {
        return hashDocumento;
    }

    /**
     * Imposta il valore della proprietà hashDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashDocumento(String value) {
        this.hashDocumento = value;
    }

    /**
     * Recupera il valore della proprietà provinciaResidenzaSoggettoPagatore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaResidenzaSoggettoPagatore() {
        return provinciaResidenzaSoggettoPagatore;
    }

    /**
     * Imposta il valore della proprietà provinciaResidenzaSoggettoPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaResidenzaSoggettoPagatore(String value) {
        this.provinciaResidenzaSoggettoPagatore = value;
    }

}
