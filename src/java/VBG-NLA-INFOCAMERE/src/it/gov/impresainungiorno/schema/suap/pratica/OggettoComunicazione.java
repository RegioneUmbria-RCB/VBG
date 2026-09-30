
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per OggettoComunicazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OggettoComunicazione"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base&gt;Stringa"&gt;
 *       &lt;attribute name="tipo-procedimento"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="SCIA"/&gt;
 *             &lt;enumeration value="silenzio-assenso"/&gt;
 *             &lt;enumeration value="ordinario"/&gt;
 *             &lt;enumeration value="comunicazione"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="tipo-intervento" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoIntervento" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OggettoComunicazione", propOrder = {
    "value"
})
public class OggettoComunicazione {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "tipo-procedimento")
    protected String tipoProcedimento;
    @XmlAttribute(name = "tipo-intervento")
    protected TipoIntervento tipoIntervento;

    /**
     *  Questo elemento non puo' assumere valore stringa vuota, al piu' non c'e' 
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
     * Recupera il valore della proprietà tipoProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoProcedimento() {
        return tipoProcedimento;
    }

    /**
     * Imposta il valore della proprietà tipoProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoProcedimento(String value) {
        this.tipoProcedimento = value;
    }

    /**
     * Recupera il valore della proprietà tipoIntervento.
     * 
     * @return
     *     possible object is
     *     {@link TipoIntervento }
     *     
     */
    public TipoIntervento getTipoIntervento() {
        return tipoIntervento;
    }

    /**
     * Imposta il valore della proprietà tipoIntervento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoIntervento }
     *     
     */
    public void setTipoIntervento(TipoIntervento value) {
        this.tipoIntervento = value;
    }

}
