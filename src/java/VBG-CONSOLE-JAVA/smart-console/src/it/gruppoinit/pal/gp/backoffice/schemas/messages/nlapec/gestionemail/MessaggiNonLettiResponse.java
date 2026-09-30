//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.27 alle 03:53:35 PM CEST 
//


package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proprietarioCasella" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numMessaggi" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "proprietarioCasella",
    "numMessaggi"
})
@XmlRootElement(name = "MessaggiNonLettiResponse", namespace = "http://gruppoinit.it/nlapec")
public class MessaggiNonLettiResponse {

    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String proprietarioCasella;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected int numMessaggi;

    /**
     * Recupera il valore della proprietà proprietarioCasella.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProprietarioCasella() {
        return proprietarioCasella;
    }

    /**
     * Imposta il valore della proprietà proprietarioCasella.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProprietarioCasella(String value) {
        this.proprietarioCasella = value;
    }

    /**
     * Recupera il valore della proprietà numMessaggi.
     * 
     */
    public int getNumMessaggi() {
        return numMessaggi;
    }

    /**
     * Imposta il valore della proprietà numMessaggi.
     * 
     */
    public void setNumMessaggi(int value) {
        this.numMessaggi = value;
    }

}
