
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiceviRuoloIUVResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviRuoloIUVResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InformazioniIUVRuolo" type="{http://entranext.it/}ArrayOfInformazioniIUVRuolo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviRuoloIUVResponse", propOrder = {
    "informazioniIUVRuolo"
})
public class RiceviRuoloIUVResponse
    extends LinkNextResponse
{

    @XmlElement(name = "InformazioniIUVRuolo")
    protected ArrayOfInformazioniIUVRuolo informazioniIUVRuolo;

    /**
     * Recupera il valore della proprietà informazioniIUVRuolo.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInformazioniIUVRuolo }
     *     
     */
    public ArrayOfInformazioniIUVRuolo getInformazioniIUVRuolo() {
        return informazioniIUVRuolo;
    }

    /**
     * Imposta il valore della proprietà informazioniIUVRuolo.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInformazioniIUVRuolo }
     *     
     */
    public void setInformazioniIUVRuolo(ArrayOfInformazioniIUVRuolo value) {
        this.informazioniIUVRuolo = value;
    }

}
