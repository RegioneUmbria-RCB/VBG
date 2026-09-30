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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Richiesta per poter modificare i dettagli dell'importo
 * 
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChiaviDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito"/>
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
@XmlType(name = "", propOrder = {
    "chiaviDebito",
    "dettagliImporto"
})
@XmlRootElement(name = "RichiestaModificaDettagliImporto")
public class RichiestaModificaDettagliImporto {

    @XmlElement(name = "ChiaviDebito", required = true)
    protected CtChiaveDebito chiaviDebito;
    @XmlElement(name = "DettagliImporto")
    protected CtDettagliImporto dettagliImporto;

    /**
     * Recupera il valore della proprietà chiaviDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaveDebito }
     *     
     */
    public CtChiaveDebito getChiaviDebito() {
        return chiaviDebito;
    }

    /**
     * Imposta il valore della proprietà chiaviDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaveDebito }
     *     
     */
    public void setChiaviDebito(CtChiaveDebito value) {
        this.chiaviDebito = value;
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
