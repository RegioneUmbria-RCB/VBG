
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EstremiDichiarante complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiDichiarante"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Anagrafica"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pec" type="{http://www.impresainungiorno.gov.it/schema/base}EMailIndirizzo" minOccurs="0"/&gt;
 *         &lt;element name="telefono" type="{http://www.impresainungiorno.gov.it/schema/base}NumeroTelefono" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="qualifica" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="ALTRO PREVISTO DALLA VIGENTE NORMATIVA"/&gt;
 *             &lt;enumeration value="AMMINISTRATORE"/&gt;
 *             &lt;enumeration value="ASSOCIAZIONE DI CATEGORIA"/&gt;
 *             &lt;enumeration value="CENTRO ELABORAZIONE DATI"/&gt;
 *             &lt;enumeration value="COMMISSARIO GIUDIZIARIO"/&gt;
 *             &lt;enumeration value="CONSULENTE"/&gt;
 *             &lt;enumeration value="CURATORE FALLIMENTARE"/&gt;
 *             &lt;enumeration value="DELEGATO"/&gt;
 *             &lt;enumeration value="LEGALE RAPPRESENTANTE"/&gt;
 *             &lt;enumeration value="LIQUIDATORE"/&gt;
 *             &lt;enumeration value="NOTAIO"/&gt;
 *             &lt;enumeration value="PROFESSIONISTA INCARICATO"/&gt;
 *             &lt;enumeration value="SOCIO"/&gt;
 *             &lt;enumeration value="STUDIO ASSOCIATO"/&gt;
 *             &lt;enumeration value="TITOLARE"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiDichiarante", propOrder = {
    "pec",
    "telefono"
})
public class EstremiDichiarante
    extends Anagrafica
{

    protected String pec;
    protected String telefono;
    @XmlAttribute(name = "qualifica", required = true)
    protected String qualifica;

    /**
     * Recupera il valore della proprietà pec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Imposta il valore della proprietà pec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Recupera il valore della proprietà telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della proprietà telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Recupera il valore della proprietà qualifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifica() {
        return qualifica;
    }

    /**
     * Imposta il valore della proprietà qualifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifica(String value) {
        this.qualifica = value;
    }

}
