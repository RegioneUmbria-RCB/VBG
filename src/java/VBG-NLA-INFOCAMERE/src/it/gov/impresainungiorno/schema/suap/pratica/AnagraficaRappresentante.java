
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AnagraficaRappresentante complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaRappresentante"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Anagrafica"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="carica" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Carica"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagraficaRappresentante", propOrder = {
    "carica"
})
public class AnagraficaRappresentante
    extends Anagrafica
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
