
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Un allegato pdf 'restringe' il tipo 'allegatoType' imponendo il vincolo che il nome del file abbia estensione pdf
 * 				(vedi 'pdfFile').
 * 			
 * 
 * <p>Java class for allegatoPDFType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="allegatoPDFType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.suap.regione.toscana.it/sem/types/procedimento}abstractAllegatoType">
 *       &lt;sequence>
 *         &lt;element name="nomefileOriginale" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}pdfFile"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegatoPDFType", propOrder = {
    "nomefileOriginale"
})
public class AllegatoPDFType
    extends AbstractAllegatoType
{

    @XmlElement(required = true)
    protected String nomefileOriginale;

    /**
     * Gets the value of the nomefileOriginale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomefileOriginale() {
        return nomefileOriginale;
    }

    /**
     * Sets the value of the nomefileOriginale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomefileOriginale(String value) {
        this.nomefileOriginale = value;
    }

}
