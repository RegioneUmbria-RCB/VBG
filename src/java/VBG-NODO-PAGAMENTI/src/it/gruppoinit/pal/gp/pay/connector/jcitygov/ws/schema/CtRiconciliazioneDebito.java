//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctRiconciliazioneDebito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctRiconciliazioneDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChiaveDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito" minOccurs="0"/>
 *         &lt;element name="importoDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto"/>
 *         &lt;element name="DettagliImporto" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDettagliImporto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctRiconciliazioneDebito", propOrder = {
    "chiaveDebito",
    "importoDebito",
    "dettagliImporto"
})
public class CtRiconciliazioneDebito {

    @XmlElement(name = "ChiaveDebito")
    protected CtChiaveDebito chiaveDebito;
    @XmlElement(required = true)
    protected BigDecimal importoDebito;
    @XmlElement(name = "DettagliImporto")
    protected CtDettagliImporto dettagliImporto;

    /**
     * Recupera il valore della proprietà chiaveDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaveDebito }
     *     
     */
    public CtChiaveDebito getChiaveDebito() {
        return chiaveDebito;
    }

    /**
     * Imposta il valore della proprietà chiaveDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaveDebito }
     *     
     */
    public void setChiaveDebito(CtChiaveDebito value) {
        this.chiaveDebito = value;
    }

    /**
     * Recupera il valore della proprietà importoDebito.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoDebito() {
        return importoDebito;
    }

    /**
     * Imposta il valore della proprietà importoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoDebito(BigDecimal value) {
        this.importoDebito = value;
    }

    /**
     * Recupera il valore della proprietà dettagliImporto.
     * 
     * @return
     *     possible object is
     *     {@link CtDettagliImporto }
     *     
     */
    public CtDettagliImporto getDettagliImporto() {
        return dettagliImporto;
    }

    /**
     * Imposta il valore della proprietà dettagliImporto.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDettagliImporto }
     *     
     */
    public void setDettagliImporto(CtDettagliImporto value) {
        this.dettagliImporto = value;
    }

}
