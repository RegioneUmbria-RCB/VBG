
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Descrive un file di tipo modello-attivita
 *         
 * 
 * <p>Classe Java per ModelloAttivita complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ModelloAttivita"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tracciato-xml" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP"&gt;
 *                 &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoFileAllegatoModelloAttivita" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoFileAllegatoDistintaModelloAttivita" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModelloAttivita", propOrder = {
    "tracciatoXml"
})
public class ModelloAttivita
    extends BaseAllegatoSUAP
{

    @XmlElement(name = "tracciato-xml")
    protected ModelloAttivita.TracciatoXml tracciatoXml;
    @XmlAttribute(name = "nome-file", required = true)
    protected String nomeFile;

    /**
     * Recupera il valore della proprietà tracciatoXml.
     * 
     * @return
     *     possible object is
     *     {@link ModelloAttivita.TracciatoXml }
     *     
     */
    public ModelloAttivita.TracciatoXml getTracciatoXml() {
        return tracciatoXml;
    }

    /**
     * Imposta il valore della proprietà tracciatoXml.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelloAttivita.TracciatoXml }
     *     
     */
    public void setTracciatoXml(ModelloAttivita.TracciatoXml value) {
        this.tracciatoXml = value;
    }

    /**
     * Recupera il valore della proprietà nomeFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della proprietà nomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP"&gt;
     *       &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoFileAllegatoModelloAttivita" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TracciatoXml
        extends BaseAllegatoSUAP
    {

        @XmlAttribute(name = "nome-file", required = true)
        protected String nomeFile;

        /**
         * Recupera il valore della proprietà nomeFile.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNomeFile() {
            return nomeFile;
        }

        /**
         * Imposta il valore della proprietà nomeFile.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNomeFile(String value) {
            this.nomeFile = value;
        }

    }

}
