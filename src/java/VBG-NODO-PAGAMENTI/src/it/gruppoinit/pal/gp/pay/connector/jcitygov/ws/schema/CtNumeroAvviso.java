//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per ctNumeroAvviso complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctNumeroAvviso">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2>stNumeroAvviso18">
 *       &lt;attribute name="FlagAttivaDebito" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="VersioneNumeroAvviso" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctNumeroAvviso", propOrder = {
    "value"
})
public class CtNumeroAvviso {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "FlagAttivaDebito")
    protected Boolean flagAttivaDebito;
    @XmlAttribute(name = "VersioneNumeroAvviso")
    protected Integer versioneNumeroAvviso;

    /**
     * Recupera il valore della proprietà value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Recupera il valore della proprietà flagAttivaDebito.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isFlagAttivaDebito() {
        if (flagAttivaDebito == null) {
            return false;
        } else {
            return flagAttivaDebito;
        }
    }

    /**
     * Imposta il valore della proprietà flagAttivaDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagAttivaDebito(Boolean value) {
        this.flagAttivaDebito = value;
    }

    /**
     * Recupera il valore della proprietà versioneNumeroAvviso.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersioneNumeroAvviso() {
        return versioneNumeroAvviso;
    }

    /**
     * Imposta il valore della proprietà versioneNumeroAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersioneNumeroAvviso(Integer value) {
        this.versioneNumeroAvviso = value;
    }

}
