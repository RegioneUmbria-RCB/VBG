
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiceviSottoServiziResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviSottoServiziResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SottoServizi" type="{http://entranext.it/}ArrayOfSottoServizi" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviSottoServiziResponse", propOrder = {
    "sottoServizi"
})
public class RiceviSottoServiziResponse
    extends LinkNextResponse
{

    @XmlElement(name = "SottoServizi")
    protected ArrayOfSottoServizi sottoServizi;

    /**
     * Recupera il valore della proprietà sottoServizi.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSottoServizi }
     *     
     */
    public ArrayOfSottoServizi getSottoServizi() {
        return sottoServizi;
    }

    /**
     * Imposta il valore della proprietà sottoServizi.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSottoServizi }
     *     
     */
    public void setSottoServizi(ArrayOfSottoServizi value) {
        this.sottoServizi = value;
    }

}
