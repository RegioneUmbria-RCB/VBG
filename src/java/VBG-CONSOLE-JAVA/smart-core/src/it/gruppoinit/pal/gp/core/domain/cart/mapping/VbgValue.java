//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.28 alle 03:27:39 PM CEST 
//


package it.gruppoinit.pal.gp.core.domain.cart.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VbgValue complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VbgValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="campo-dinamico" type="{http://gruppoinit.it/sigepro/schemas/cartmapping}CampoDinamico" minOccurs="0"/>
 *         &lt;element name="campo-statico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valore-fisso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="if-property" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="if-operator" type="{http://gruppoinit.it/sigepro/schemas/cartmapping}OperatorType" default="EXISTS" />
 *       &lt;attribute name="if-value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VbgValue", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping", propOrder = {
    "campoDinamico",
    "campoStatico",
    "valoreFisso"
})
public class VbgValue {

    @XmlElement(name = "campo-dinamico", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping")
    protected CampoDinamico campoDinamico;
    @XmlElement(name = "campo-statico", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping")
    protected String campoStatico;
    @XmlElement(name = "valore-fisso", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping")
    protected String valoreFisso;
    @XmlAttribute(name = "if-property")
    protected String ifProperty;
    @XmlAttribute(name = "if-operator")
    protected OperatorType ifOperator;
    @XmlAttribute(name = "if-value")
    protected String ifValue;

    /**
     * Recupera il valore della proprietà campoDinamico.
     * 
     * @return
     *     possible object is
     *     {@link CampoDinamico }
     *     
     */
    public CampoDinamico getCampoDinamico() {
        return campoDinamico;
    }

    /**
     * Imposta il valore della proprietà campoDinamico.
     * 
     * @param value
     *     allowed object is
     *     {@link CampoDinamico }
     *     
     */
    public void setCampoDinamico(CampoDinamico value) {
        this.campoDinamico = value;
    }

    /**
     * Recupera il valore della proprietà campoStatico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampoStatico() {
        return campoStatico;
    }

    /**
     * Imposta il valore della proprietà campoStatico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampoStatico(String value) {
        this.campoStatico = value;
    }

    /**
     * Recupera il valore della proprietà valoreFisso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValoreFisso() {
        return valoreFisso;
    }

    /**
     * Imposta il valore della proprietà valoreFisso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValoreFisso(String value) {
        this.valoreFisso = value;
    }

    /**
     * Recupera il valore della proprietà ifProperty.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIfProperty() {
        return ifProperty;
    }

    /**
     * Imposta il valore della proprietà ifProperty.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIfProperty(String value) {
        this.ifProperty = value;
    }

    /**
     * Recupera il valore della proprietà ifOperator.
     * 
     * @return
     *     possible object is
     *     {@link OperatorType }
     *     
     */
    public OperatorType getIfOperator() {
        if (ifOperator == null) {
            return OperatorType.EXISTS;
        } else {
            return ifOperator;
        }
    }

    /**
     * Imposta il valore della proprietà ifOperator.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatorType }
     *     
     */
    public void setIfOperator(OperatorType value) {
        this.ifOperator = value;
    }

    /**
     * Recupera il valore della proprietà ifValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIfValue() {
        return ifValue;
    }

    /**
     * Imposta il valore della proprietà ifValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIfValue(String value) {
        this.ifValue = value;
    }

}
