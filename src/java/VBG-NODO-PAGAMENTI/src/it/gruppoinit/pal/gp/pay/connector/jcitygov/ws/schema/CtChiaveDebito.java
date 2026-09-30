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
 * <p>Classe Java per ctChiaveDebito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctChiaveDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDPos" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stText256"/>
 *         &lt;element name="IDDeb" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stText256"/>
 *         &lt;element name="CodiceTipoDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctChiaveDebito", propOrder = {
    "idPos",
    "idDeb",
    "codiceTipoDebito"
})
public class CtChiaveDebito {

    @XmlElement(name = "IDPos", required = true)
    protected String idPos;
    @XmlElement(name = "IDDeb", required = true)
    protected String idDeb;
    @XmlElement(name = "CodiceTipoDebito", required = true)
    protected String codiceTipoDebito;

    /**
     * Recupera il valore della proprietà idPos.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDPos() {
        return idPos;
    }

    /**
     * Imposta il valore della proprietà idPos.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDPos(String value) {
        this.idPos = value;
    }

    /**
     * Recupera il valore della proprietà idDeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDDeb() {
        return idDeb;
    }

    /**
     * Imposta il valore della proprietà idDeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDDeb(String value) {
        this.idDeb = value;
    }

    /**
     * Recupera il valore della proprietà codiceTipoDebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTipoDebito() {
        return codiceTipoDebito;
    }

    /**
     * Imposta il valore della proprietà codiceTipoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTipoDebito(String value) {
        this.codiceTipoDebito = value;
    }

}
