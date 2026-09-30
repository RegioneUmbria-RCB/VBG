
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiceviVociDiCostoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviVociDiCostoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VociDiCosto" type="{http://entranext.it/}ArrayOfVociDiCosto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviVociDiCostoResponse", propOrder = {
    "vociDiCosto"
})
public class RiceviVociDiCostoResponse
    extends LinkNextResponse
{

    @XmlElement(name = "VociDiCosto")
    protected ArrayOfVociDiCosto vociDiCosto;

    /**
     * Recupera il valore della proprietà vociDiCosto.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVociDiCosto }
     *     
     */
    public ArrayOfVociDiCosto getVociDiCosto() {
        return vociDiCosto;
    }

    /**
     * Imposta il valore della proprietà vociDiCosto.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVociDiCosto }
     *     
     */
    public void setVociDiCosto(ArrayOfVociDiCosto value) {
        this.vociDiCosto = value;
    }

}
