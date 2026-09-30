
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @deprecated
 * 
 * <p>Classe Java per LegaleRappresentante complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="LegaleRappresentante"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}DatiPersona"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="carica" type="{http://www.impresainungiorno.gov.it/schema/base}Carica"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegaleRappresentante", propOrder = {
    "carica"
})
public class LegaleRappresentante
    extends DatiPersona
{

    @XmlElement(required = true)
    protected Carica carica;

    /**
     * Recupera il valore della proprietà carica.
     * 
     * @return
     *     possible object is
     *     {@link Carica }
     *     
     */
    public Carica getCarica() {
        return carica;
    }

    /**
     * Imposta il valore della proprietà carica.
     * 
     * @param value
     *     allowed object is
     *     {@link Carica }
     *     
     */
    public void setCarica(Carica value) {
        this.carica = value;
    }

}
