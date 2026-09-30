
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfScaricaPagamentoRTResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfScaricaPagamentoRTResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ScaricaPagamentoRTResponse" type="{http://entranext.it/}ScaricaPagamentoRTResponse" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfScaricaPagamentoRTResponse", propOrder = {
    "scaricaPagamentoRTResponse"
})
public class ArrayOfScaricaPagamentoRTResponse {

    @XmlElement(name = "ScaricaPagamentoRTResponse", nillable = true)
    protected List<ScaricaPagamentoRTResponse> scaricaPagamentoRTResponse;

    /**
     * Gets the value of the scaricaPagamentoRTResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scaricaPagamentoRTResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScaricaPagamentoRTResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScaricaPagamentoRTResponse }
     * 
     * 
     */
    public List<ScaricaPagamentoRTResponse> getScaricaPagamentoRTResponse() {
        if (scaricaPagamentoRTResponse == null) {
            scaricaPagamentoRTResponse = new ArrayList<ScaricaPagamentoRTResponse>();
        }
        return this.scaricaPagamentoRTResponse;
    }

}
